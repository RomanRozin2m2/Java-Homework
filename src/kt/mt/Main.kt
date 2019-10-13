package kt.mt

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock


fun main() {
    var i = 0L
    val threadCount = 4
    val lock = ReentrantLock()

    val ai = AtomicInteger()

    val threads = Array(threadCount) {
        Thread {
            repeat(1_000_00000) {
                ai.incrementAndGet()
            }
        }
    }
    repeat (threadCount) {
        threads[it].start()
    }
    repeat (threadCount) {
        threads[it].join()
    }
    println(ai.get())
}
