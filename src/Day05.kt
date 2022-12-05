fun main() {
    println(day5(readFile("Day05"), true))
    println(day5(readFile("Day05"), false))
}

fun day5(input: String, reverse: Boolean): String {
    val (moves, startingStack) = input.split("\n").partition { it.startsWith("move") }
    val stack = buildStack(startingStack.dropLast(2))
    moves.forEach { move ->
        val (count,fromIdx,toIdx) = move.removePrefix("move ").split(" from ", " to ").map { it.toInt() }
        val temp = if (reverse) (stack[fromIdx-1].subList(0, count)).reversed() else (stack[fromIdx-1].subList(0, count))
        stack[fromIdx-1] = stack[fromIdx-1].subList(count, stack[fromIdx-1].size)
        stack[toIdx-1] = (temp + stack[toIdx-1]) as MutableList<Char>
    }
    return stack.map { it[0] }.joinToString("")
}

fun buildStack(input: List<String>): MutableList<MutableList<Char>> =
    input.foldRight(generateSequence { mutableListOf<Char>() }.take(9).toMutableList())
    { line, acc ->
        acc.mapIndexed { idx, stack ->
            if ((line.getOrNull(idx * 4 + 1) ?: ' ') != ' ')
                stack.add(0, line.getOrNull(idx * 4 + 1) ?: ' ')
            stack
        }.toMutableList()
    }