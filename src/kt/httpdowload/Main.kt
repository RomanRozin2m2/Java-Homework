package kt.httpdowload

import java.lang.StringBuilder
import java.net.URL
import java.util.ArrayList
import java.util.regex.Pattern


val sites = "https://en.wikipedia.org/"



fun ArrayList<String>.customToString(): String {
    val result = StringBuilder()
    for (value in this) {
        result.append(value)
        result.append("\n")
    }
    return result.toString()
}

fun getLinks(site: String): List<String> {
    val regex = "\"(https?://[^> ]*)\""

    val m = Pattern.compile(regex).matcher(site)

    val matches = ArrayList<String>()
    while (m.find()) {
        matches.add(m.group(1))
    }

    return matches
}


fun main() {
    val dataAnalyzer = WebDataAnalyzer()
    println(dataAnalyzer.analyze("https://en.wikipedia.org", 25).customToString())
}
