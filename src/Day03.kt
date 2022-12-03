fun main() {
    println(day3A(readFile("Day03")))
    println(day3B(readFile("Day03")))
}

fun day3A(input: String): Int = input.trim().split("\n").sumOf { charValue(findDupChar(it)) }


fun day3B(input: String) = input.trim().split("\n").chunked(3).sumOf { charValue(findBadge(it[0],it[1],it[2])) }

fun findDupChar(input: String): Char =
    (input.subSequence(0,input.length/2).toSet() intersect  input.substring(input.lastIndex/2 +1).toSet()).first()

fun charValue(input: Char): Int  =
    when(input.code) {
        in 97..122 -> input.code - 96
        in 65..90 -> input.code - 65 + 27
        else ->  throw Exception("help")
    }

fun findBadge(a: String, b: String, c: String): Char =
    (a.toCharArray().toSet() intersect b.toCharArray().toSet() intersect c.toCharArray().toSet()).first()