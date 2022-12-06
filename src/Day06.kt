fun main() {
    println(day6A(readFile("Day06")))
    println(day6B(readFile("Day06")))
}

fun day6A(input: String): Int {
    var start = 0
    var end = 4
    while (end < input.length) {
        if (input.subSequence(start,end).toList().toSet().size == 4){
            return end
        }
        start++
        end++
    }
    throw Exception("help")
}

fun day6B(input: String): Int {
    var start = 0
    var end = 14
    while (end < input.length) {
        if (input.subSequence(start,end).toList().toSet().size == 14){
            return end
        }
        start++
        end++
    }
    throw Exception("help")
}
