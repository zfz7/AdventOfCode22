fun main() {
    println(day2A(readFile("Day02")))
    println(day2B(readFile("Day02")))
}

fun day2A(input: String): Int = input.trim().split("\n").sumOf { playedValue(it[2]) + winner(it[0], it[2]) }
fun day2B(input: String): Int = input.trim().split("\n").sumOf { outcome(it[2]) + playedValue(it[0], it[2]) }

fun playedValue(input: Char): Int =
    when (input) {
        'X' -> 1
        'Y' -> 2
        'Z' -> 3
        else -> throw Exception("help")
    }

fun winner(them: Char, you: Char): Int =
    when (Pair(them, you)) {
        Pair('A', 'X'), Pair('B', 'Y'), Pair('C', 'Z') -> 3
        Pair('A', 'Y'), Pair('B', 'Z'), Pair('C', 'X') -> 6
        Pair('A', 'Z'), Pair('B', 'X'), Pair('C', 'Y') -> 0
        else -> throw Exception("help")
    }

fun outcome(input: Char): Int =
    when (input) {
        'X' -> 0
        'Y' -> 3
        'Z' -> 6
        else -> throw Exception("help")
    }

fun playedValue(them: Char, you: Char): Int =
    when (Pair(them, you)) {
        Pair('A', 'Y'), Pair('B', 'X'), Pair('C', 'Z') -> 1//Rock
        Pair('A', 'Z'), Pair('B', 'Y'), Pair('C', 'X') -> 2//Paper
        Pair('A', 'X'), Pair('B', 'Z'), Pair('C', 'Y') -> 3//Scissor
        else -> throw Exception("help")
    }