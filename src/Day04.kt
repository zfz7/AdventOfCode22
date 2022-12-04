fun main() {
    println(day4A(readFile("Day04")))
    println(day4B(readFile("Day04")))
}

fun day4A(input: String): Int = input.trim().split("\n").count { pair ->
    pair.split("-", ",").let { overlapAll(it[0].toInt(), it[1].toInt(), it[2].toInt(), it[3].toInt()) }
}

fun day4B(input: String): Int = input.trim().split("\n").count { pair ->
    pair.split("-", ",").let { overlapSome(it[0].toInt(), it[1].toInt(), it[2].toInt(), it[3].toInt()) }
}

fun overlapAll(a1: Int, a2: Int, b1: Int, b2: Int): Boolean = (a1 <= b1 && a2 >= b2) || (b1 <= a1 && b2 >= a2)
fun overlapSome(a1: Int, a2: Int, b1: Int, b2: Int): Boolean =
    b1 in a1..a2 || b2 in a1..a2 || a1 in b1..b2 || a2 in b1..b2