import kotlin.math.abs

fun main() {
    println(day9A(readFile("Day09")))
    println(day9B(readFile("Day09")))
}

fun isTouching(head: Pair<Int, Int>, tail: Pair<Int, Int>): Boolean =
    abs(head.first - tail.first) <= 1 && abs(head.second - tail.second) <= 1

fun moveHead(head: Pair<Int, Int>, mv: String): Pair<Int, Int> =
    when (mv) {
        "U" -> Pair(head.first, head.second + 1)
        "D" -> Pair(head.first, head.second - 1)
        "R" -> Pair(head.first + 1, head.second)
        "L" -> Pair(head.first - 1, head.second)
        else -> throw Exception("help")
    }

fun day9B(input: String): Int {
    var head = Pair<Int, Int>(0, 0)
    val tails = generateSequence { Pair<Int,Int>(0,0) }.take(9).toList().toMutableList()
    val allTailPos = mutableListOf<Pair<Int, Int>>()
    input.split("\n").forEach { move ->
        val mv = move.split(" ")
        repeat(mv[1].toInt()) {
            head = moveHead(head, mv[0])
            if (!isTouching(head, tails[0])) {
                tails[0] = moveTail(head, tails[0])
            }
            repeat(8) {idx ->
                if (!isTouching(tails[idx], tails[idx+1])) {
                    tails[idx +1] = moveTail(tails[idx], tails[idx+1])
                }
            }

            allTailPos.add( tails[8] )
        }
    }
    return allTailPos.toSet().size
}


fun day9A(input: String): Int {
    var head = Pair<Int, Int>(0, 0)
    var tail = Pair<Int, Int>(0, 0)
    val allTailPos = mutableListOf<Pair<Int, Int>>()
    input.split("\n").forEach { move ->
        val mv = move.split(" ")
        repeat(mv[1].toInt()) {
            head = moveHead(head, mv[0])
            if (!isTouching(head, tail)) {
                tail = moveTail(head,tail)
            }

            allTailPos.add(tail)
        }
    }
    return allTailPos.toSet().size
}

fun moveTail(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
    val xDiff = head.first - tail.first
    val yDiff = head.second - tail.second

    if (xDiff > 1 && yDiff == 0)
        return Pair(tail.first + 1, tail.second)
    else if (xDiff < -1 && yDiff == 0)
        return Pair(tail.first - 1, tail.second)
    else if (xDiff == 0 && yDiff > 1)
        return Pair(tail.first, tail.second + 1)
    else if (xDiff == 0 && yDiff < -1)
        return Pair(tail.first, tail.second - 1)
    else if (xDiff >= 1 && yDiff >= 1)
        return Pair(tail.first + 1, tail.second + 1)
    else if (xDiff <= -1 && yDiff >= 1)
        return Pair(tail.first - 1, tail.second + 1)
    else if (xDiff >= 1 && yDiff <= -1)
        return Pair(tail.first + 1, tail.second - 1)
    else if (xDiff <= -1 && yDiff <= -1)
        return Pair(tail.first - 1, tail.second - 1)
    throw Exception("Help")
}

