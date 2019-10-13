package kt

inline operator fun <reified T> Array<T>.plus(other : Array<T>) =
    Array(this.size + other.size) {
        if (it < this.size) this[it]
        else other[it - this.size]
    }


fun main() {

    val arr1 = arrayOf(1, 2, 3)
    val arr2 = arrayOf(2, 3, 4)

    for (s: Int in arr2 + arr1) {
        println(s)
    }

    val m = mapOf(1 to 2, 2 to 3, 3 to 4, 4 to 5)
    val n = mutableMapOf(1 to 2, 2 to 3, 3 to 4, 4 to 5)

}

class Kt (private val val1: Int) {

    operator fun minus(par1: Int) : Int {
        println("INFIX CALLED")
        return val1 + par1
    }

}
