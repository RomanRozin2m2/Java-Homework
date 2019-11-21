package kt.lections

import java.lang.StringBuilder
import java.net.URL
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap


fun getAllRegEx(str: String, regex: String, group: Int): List<String> {
    val m = Pattern.compile(regex).matcher(str)
    val matches = ArrayList<String>()
    while (m.find()) {
        matches.add(m.group(group))
    }
    return matches
}


fun getLinks(site: String): List<String> =
        getAllRegEx(site, "\"(https?://[^ ?&=#\"]*)", 1)


fun getContent(url: String): String =
        URL(url).openConnection().getInputStream().readAllBytes().toString(Charsets.UTF_8)


fun mainn(args: Array<String>) {
    Downloader("https://en.wikipedia.org/wiki/Main_Page")
}


data class Link(val link: String, val depth: Int)


class AtomicQueue<T> {
    private val queue = LinkedList<T>()
    private val addLock = ReentrantLock()
    private val popLock = ReentrantLock()

    fun add(elem: T) {
        addLock.lock()
        queue.add(elem)
        addLock.unlock()
    }

    fun pop(): T {
        popLock.lock()
        val elem = queue.remove()
        popLock.unlock()
        return elem
    }

    fun isEmpty(): Boolean {
        return queue.isEmpty()
    }

    fun getSize(): Int {
        return queue.size
    }
}


class AtomicArrayList<T> {
    private val list = ArrayList<T>()

    private val readWriteLock = ReentrantReadWriteLock()

    val size: Int
        get() = list.size

    fun add(element: T) {
        readWriteLock.writeLock().lock()
        list.add(element)
        readWriteLock.writeLock().unlock()
    }

    fun add(index: Int, element: T) {
        readWriteLock.writeLock().lock()
        list.add(index, element)
        readWriteLock.writeLock().unlock()
    }

    fun get(index: Int): T {
        readWriteLock.readLock().lock()
        val elem = list[index]
        readWriteLock.readLock().unlock()
        return elem
    }

    fun remove(index: Int): T {
        readWriteLock.readLock().lock()
        val removed = list.removeAt(index)
        readWriteLock.readLock().unlock()
        return removed
    }

    fun remove(element: T): Boolean {
        readWriteLock.readLock().lock()
        val removed = list.remove(element)
        readWriteLock.readLock().unlock()
        return removed
    }
}


data class Locked<T> (val elem: T, val lock: ReentrantLock = ReentrantLock())


class AtomicLinkedList<T> {
    private val list = LinkedList<Locked<T>>()

    private val readWriteLock = ReentrantReadWriteLock()

    val size: Int
        get() = list.size

    fun add(index: Int, element: T) {
        list[index].lock.lock()
        list.add(index, Locked(element))
        list[index].lock.unlock()
    }

    fun get(index: Int): T {
        list[index].lock.lock()
        val elem = list[index].elem
        list[index].lock.unlock()
        return elem
    }

    fun remove(index: Int): T {
        list[index].lock.lock()
        val elem = list.removeAt(index).elem
        list[index].lock.unlock()
        return elem
    }
}






class Downloader(initial: String) {

    private val sitesQueue = AtomicQueue<Link>()
    private val counts = LinkedHashMap<String, Int>()

    private val queueLock = ReentrantLock()
    private val countsLock = ReentrantLock()

    private val threads = ArrayList<Thread>()

    private val threadsCount = 5
    private val recursiveMax = 10
    private val sitesMax = 5000

    private val threadsWorking = BooleanArray(threadsCount) { false }

    private var stop = false

    init {
        sitesQueue.add(Link(initial, 0))
        repeat(threadsCount) { threads.add(Thread { download(it) }) }
        threads.forEach(Thread::start)
        threads.forEach(Thread::join)
        val sorted = counts.toList().sortedBy { (_, value) -> -value }
        for (elem in sorted) {
            println("${elem.second}    ${elem.first}")
        }
    }

    private fun download(id: Int) {
        while (!stop) {
            threadsWorking[id] = false

            queueLock.lock()
            if (sitesQueue.isEmpty()) {
                if (threadsWorking.all { !it }) {
                    stop = true
                }
                queueLock.unlock()
                Thread.sleep(100)
                continue
            }
            val nextSite = sitesQueue.pop()
            threadsWorking[id] = true
            queueLock.unlock()

            if (nextSite.depth > recursiveMax) {
                continue
            }

            if (counts.size > sitesMax) {
                continue
            }

            val content: String
            try {
                content = getContent(nextSite.link)
            } catch (ex: Exception) {
                println("??? ${nextSite.link}")
                continue
            }
            println("${nextSite.depth} ${counts.size} ${sitesQueue} ${nextSite.link}")
            println(content)

            val links = getLinks(content)

            countsLock.lock()
            for (link in links) {
                if ("//en.wikipedia.org/wiki/" in link) {
                    if (!counts.containsKey(link)) {
                        queueLock.lock()
                        sitesQueue.add(Link(link, nextSite.depth + 1))
                        queueLock.unlock()
                    }
                    counts[link] = (counts[link] ?: 0) + 1
                }
            }
            countsLock.unlock()
        }
    }
}


class Node(var elem: Int) {
    lateinit var next: Node
    val lock = ReentrantLock()
}

class ConcList {
    private val start: Node = Node(Int.MIN_VALUE)
    private val end: Node = Node(Int.MAX_VALUE)

    init {
        start.next = end
    }

    fun add(elem: Int) {
        var curr = start
        curr.lock.lock()
        curr.next.lock.lock()
        while (true) {
            if (elem >= curr.elem && elem <= curr.next.elem) {
                val node = Node(elem)
                node.lock.lock()
                curr.next.lock.unlock()
                node.next = curr.next
                curr.next = node
                break
            }
            val oldLock = curr.lock
            curr = curr.next
            oldLock.unlock()
            curr.next.lock.lock()
        }
        curr.lock.unlock()
        curr.next.lock.unlock()
    }

    fun get(elem: Int): Int {
        var index = -1
        var curr = start
        curr.lock.lock()
        curr.next.lock.lock()
        while (curr.elem != elem) {
            val oldLock = curr.lock
            curr = curr.next
            oldLock.unlock()
            curr.next.lock.lock()
            index++
        }
        curr.lock.unlock()
        curr.next.lock.unlock()
        return index
    }

    fun remove(elem: Int): Boolean {
        var curr = start
        curr.lock.lock()
        curr.next.lock.lock()
        var deleted = true
        while (curr.next.elem != elem) {
            if (curr.next === end) {
                deleted = false
                break
            }
            val oldLock = curr.lock
            curr = curr.next
            oldLock.unlock()
            curr.next.lock.lock()
        }
        if (deleted) {
            curr.next.lock.unlock()
            curr.next = curr.next.next
            curr.next.lock.lock()
        }
        curr.lock.unlock()
        curr.next.lock.unlock()
        return deleted
    }

    override fun toString(): String {
        val builder = StringBuilder()
        var curr = start.next
        curr.lock.lock()
        curr.next.lock.lock()
        while (curr != end) {
            builder.append(curr.elem)
            builder.append(" ")
            val oldLock = curr.lock
            curr = curr.next
            oldLock.unlock()
            if (curr != end) {
                curr.next.lock.lock()
            }
        }
        curr.lock.unlock()
        if (curr != end) {
            curr.next.lock.unlock()
        }
        return builder.toString().trim()
    }

}

fun main(args: Array<String>) {
    val list = ConcList()
    val threads = ArrayList<Thread>()
    repeat(5) {
        threads.add(Thread {
            val random = Random()
            repeat(1000) {
                list.add(random.nextInt(1000))
            }
        })
    }
    repeat(20) {
        threads.add(Thread {
            val random = Random()
            repeat(1000) {
                list.remove(random.nextInt(1000))
            }
        })
    }
    threads.forEach {
        it.start()
    }
    threads.forEach {
        it.join()
    }
    println(list)
}
