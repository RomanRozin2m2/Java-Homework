package kt

import java.util.*


fun main() {



}


class Kt {

    var ty = 123
    val rt = 456

    fun sdf(a1 : Array<Int>) : Int {
        val t = a1[0]

        val func : () -> Int = {
            val c1 = 5
            c1 + 10
        }


        asdf(func)

        asdf { 10 }

        val q = Thread { println(123) }

        val we : String = "3234234"
        val wr : String? = null

        if (wr != null) {
            sdf1(wr)
        }

        sdf2(wr)
        sdf2(we)

        val  s = Scanner(System.`in`)

        when (s.nextInt()) {
            0 -> println(0)
            1 -> println(1)
            in 2..8 -> println(2)
            else -> println(123)
        }


        return t
    }

    fun asdf(f : () -> Int) {
        print(f())
    }


    fun sdf1(fg : String) {
        print(fg.toLowerCase())
    }

    fun sdf2(fg : String?) {
        if (fg != null) {
            print(fg.toLowerCase())
        }

    }

}

