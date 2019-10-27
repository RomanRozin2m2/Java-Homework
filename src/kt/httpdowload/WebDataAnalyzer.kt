package kt.httpdowload

import java.lang.StringBuilder
import java.net.URL
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class WebDataAnalyzer {
    private val linkPattern = Pattern.compile("\"(https?://[^> ]*)\"")
    private val articlePattern = Pattern.compile("<article.*?>(.*?)</article>")
    private val tagPattern = Pattern.compile("<.*?>")

    fun parseArticles(url: String): ArrayList<String> {
        val page = URL(url).
                openConnection().
                getInputStream().
                readAllBytes().
                toString(Charsets.UTF_8).
                replace("\n", "").replace("\t", "")
        val matcher = articlePattern.matcher(page)
        val articles = ArrayList<String>()
        while (matcher.find()) {
            val article = matcher.group(1)
            articles.add(article)
        }
        return articles
    }

    fun removeTags(source: ArrayList<String>): String {
        val builder = StringBuilder()
        for (value in source) {
            builder.append(value)
        }
        return builder.replace(tagPattern.toRegex(), " ").replace("  ", " ")
    }

    fun analyze(url: String, depth: Int): ArrayList<String> {
        return getLinksRecursively(url, depth, ArrayList())
    }

    private fun getLinks(url: String, analyzed: ArrayList<String>): ArrayList<String> {
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
            if (!analyzed.contains(link)) {
                analyzed.add(link)
                links.add(link)
            }
        }
        return links
    }

    private fun getLinksRecursively(url: String, depth: Int, analyzed: ArrayList<String>): ArrayList<String> {
        val result = ArrayList<String>()
        if (depth == 0) {
            return result
        }
        val links = getLinks(url, analyzed)
        result.addAll(links)
        for (link in links) {
            if (!analyzed.contains(link)) {
                analyzed.add(link)
                result.addAll(getLinksRecursively(link, depth - 1, analyzed))
            }
        }
        return result
    }
}