import java.util.*

fun main() {
    println(day12A(readFile("Day12")))
    println(day12B(readFile("Day12")))
}

val heightMap = listOf(
    'a',
    'b',
    'c',
    'd',
    'e',
    'f',
    'g',
    'h',
    'i',
    'j',
    'k',
    'l',
    'm',
    'n',
    'o',
    'p',
    'q',
    'r',
    's',
    't',
    'u',
    'v',
    'w',
    'x',
    'y',
    'z'
)


fun day12A(input: String): Int {
    var start: Pair<Int, Int> = Pair(-1, -1)
    var end: Pair<Int, Int> = Pair(-1, -1)
    val heights: List<List<Int>> = input.trim().split("\n")
        .mapIndexed { i, line ->
            line.mapIndexed { j, it ->
                when (it) {
                    'S' -> {
                        start = Pair(i, j);0
                    }

                    'E' -> {
                        end = Pair(i, j);25
                    }

                    else -> heightMap.indexOf(it)
                }
            }
        }
    val priorityQueue = PriorityQueue<Path> { a, b -> a.depth - b.depth }
    val visited = mutableSetOf<Pair<Int, Int>>()
    priorityQueue.add(Path(pos = start, depth = 0))
    while (!priorityQueue.isEmpty()) {
        val current = priorityQueue.remove()!!
        if (current.pos == end) {
            return current.depth
        }
        //up
        val up = Pair(current.pos.first - 1, current.pos.second)
        if (heights[current.pos.first][current.pos.second] + 1 >= (heights.getOrNull(up.first)
                ?.getOrNull(up.second) ?: Int.MAX_VALUE) && !visited.contains(up)
        ) {
            visited.add(up)
            priorityQueue.add(Path(pos = up, depth = current.depth + 1))
        }
        //down
        val down = Pair(current.pos.first + 1, current.pos.second)
        if (heights[current.pos.first][current.pos.second] + 1 >= (heights.getOrNull(down.first)
                ?.getOrNull(down.second) ?: Int.MAX_VALUE) && !visited.contains(down)
        ) {
            visited.add(down)
            priorityQueue.add(Path(pos = down, depth = current.depth + 1))
        }
        //left
        val left = Pair(current.pos.first, current.pos.second - 1)
        if (heights[current.pos.first][current.pos.second] + 1 >= (heights.getOrNull(left.first)
                ?.getOrNull(left.second) ?: Int.MAX_VALUE) && !visited.contains(left)
        ) {
            visited.add(left)
            priorityQueue.add(Path(pos = left, depth = current.depth + 1))
        }
        //right
        val right = Pair(current.pos.first, current.pos.second + 1)

        if (heights[current.pos.first][current.pos.second] + 1 >= (heights.getOrNull(right.first)
                ?.getOrNull(right.second) ?: Int.MAX_VALUE) && !visited.contains(right)
        ) {
            visited.add(right)
            priorityQueue.add(Path(pos = right, depth = current.depth + 1))
        }
    }
    return -1
}
fun day12B(input: String): Int {
    val start: MutableList<Pair<Int, Int>> = mutableListOf()
    var end: Pair<Int, Int> = Pair(-1, -1)

    val heights: List<List<Int>> = input.trim().split("\n")
        .mapIndexed { i, line ->
            line.mapIndexed { j, it ->
                when (it) {
                    'S','a' -> {
                        start.add(Pair(i, j));0
                    }
                    'E' -> {
                        end = Pair(i, j);25
                    }
                    else -> heightMap.indexOf(it)
                }
            }
        }
    val priorityQueue = PriorityQueue<Path> { a, b -> a.depth - b.depth }
    val visited = start.toSet().toMutableSet()
    visited.forEach {
        priorityQueue.add(Path(pos = it, depth = 0))
    }
    while (!priorityQueue.isEmpty()) {
        val current = priorityQueue.remove()!!
        if (current.pos == end) {
            return current.depth
        }
        //up
        val up = Pair(current.pos.first - 1, current.pos.second)
        if (heights[current.pos.first][current.pos.second] + 1 >= (heights.getOrNull(up.first)
                ?.getOrNull(up.second) ?: Int.MAX_VALUE) && !visited.contains(up)
        ) {
            visited.add(up)
            priorityQueue.add(Path(pos = up, depth = current.depth + 1))
        }
        //down
        val down = Pair(current.pos.first + 1, current.pos.second)
        if (heights[current.pos.first][current.pos.second] + 1 >= (heights.getOrNull(down.first)
                ?.getOrNull(down.second) ?: Int.MAX_VALUE) && !visited.contains(down)
        ) {
            visited.add(down)
            priorityQueue.add(Path(pos = down, depth = current.depth + 1))
        }
        //left
        val left = Pair(current.pos.first, current.pos.second - 1)
        if (heights[current.pos.first][current.pos.second] + 1 >= (heights.getOrNull(left.first)
                ?.getOrNull(left.second) ?: Int.MAX_VALUE) && !visited.contains(left)
        ) {
            visited.add(left)
            priorityQueue.add(Path(pos = left, depth = current.depth + 1))
        }
        //right
        val right = Pair(current.pos.first, current.pos.second + 1)

        if (heights[current.pos.first][current.pos.second] + 1 >= (heights.getOrNull(right.first)
                ?.getOrNull(right.second) ?: Int.MAX_VALUE) && !visited.contains(right)
        ) {
            visited.add(right)
            priorityQueue.add(Path(pos = right, depth = current.depth + 1))
        }
    }
    return -1
}
data class Path(
    val pos: Pair<Int, Int>,
    val depth: Int
)