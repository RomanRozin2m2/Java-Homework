package kt.httpdowload

import java.net.URL
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class WebDataAnalyzer {
    private val linkPattern = Pattern.compile("\"(https?://[^\"]*)\"")
    private val domainPattern = Pattern.compile("(https?://[^/]*)/")
    private val hrefPattern = Pattern.compile("href=\"(/[^/][^\"]*)\"")

    fun analyze(url: String, depth: Int) {
        getLinksRecursively(url, depth, ArrayList())
    }

    private fun getLinks(url: String): ArrayList<String> {
        println(url)
        val page = URL(url).
                openConnection().
                getInputStream().
                readAllBytes().
                toString(Charsets.UTF_8)
        val matcher = linkPattern.matcher(page)
        val links = ArrayList<String>()
        while (matcher.find()) {
            val link = matcher.group(1)
            links.add(link)
        }
        val matcherHref = hrefPattern.matcher(page)
        while (matcherHref.find()) {
            val link = matcherHref.group(1)
            val matcherDomain = domainPattern.matcher(url)
            matcherDomain.find()
            links.add("${matcherDomain.group(1)}$link")
        }
        return links
    }

    private fun getLinksRecursively(url: String, depth: Int, analyzed: ArrayList<String>) {
        val links = getLinks(url)
        for (link in links) {
            if (!analyzed.contains(link)) {
                analyzed.add(link)
                Thread {
                    getLinksRecursively(link, depth - 1, analyzed)
                }.start()
            }
        }
    }
}