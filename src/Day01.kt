fun main() {
    println(day1A(readFile("Day01")))
    println(day1B(readFile("Day01")))
}

fun day1A(input: String) = input.trim().split("\n\n").maxOfOrNull { elfCals -> elfCals.split("\n").sumOf { it.toInt() } }
fun day1B(input: String) = input.trim().split("\n\n").map { elfCals -> elfCals.split("\n").sumOf { it.toInt() } }.sortedDescending().subList(0,3).sum()

