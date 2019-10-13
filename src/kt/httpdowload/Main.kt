package kt.httpdowload

import java.net.URL
import java.util.ArrayList
import java.util.regex.Pattern


val sites = "https://en.wikipedia.org/"


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
    val connection = URL(sites).openConnection()
    val site: String = connection.getInputStream().readAllBytes().toString(Charsets.UTF_8)

    for (m in getLinks(site)) {
        println(m)
    }
}
