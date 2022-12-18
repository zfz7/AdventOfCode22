fun main() {
    println(day18A(readFile("Day18")))
    println(day18B(readFile("Day18")))
}

fun day18A(input: String): Int {
    var count = 0

    cords.forEach { cord ->
        if (!cords.contains(listOf(cord[0] + 1, cord[1], cord[2]))) {
            count++
        }
        if (!cords.contains(listOf(cord[0] - 1, cord[1], cord[2]))) {
            count++
        }
        if (!cords.contains(listOf(cord[0], cord[1] + 1, cord[2]))) {
            count++
        }
        if (!cords.contains(listOf(cord[0], cord[1] - 1, cord[2]))) {
            count++
        }
        if (!cords.contains(listOf(cord[0], cord[1], cord[2] + 1))) {
            count++
        }
        if (!cords.contains(listOf(cord[0], cord[1], cord[2] - 1))) {
            count++
        }
    }
    return count
}

val cords = readFile("Day18").trim().split("\n")
    .map { line -> line.split(",").map { it.toInt() } }
    .toSet()

val cordCache: MutableMap<List<Int>, Boolean> = mutableMapOf()
val maxX = cords.maxBy { it[0] }[0]
val maxY = cords.maxBy { it[1] }[1]
val maxZ = cords.maxBy { it[2] }[2]


fun day18B(input: String): Int {
    var count = 0
    cords.forEach { cord ->
        println(cord)
        if (!cords.contains(listOf(cord[0] + 1, cord[1], cord[2])) &&
            canReachOutside(listOf(cord[0] + 1, cord[1], cord[2]))
        ) {
            count++
        }
        if (!cords.contains(listOf(cord[0] - 1, cord[1], cord[2])) &&
            canReachOutside(listOf(cord[0] - 1, cord[1], cord[2]))
        ) {
            count++
        }
        if (!cords.contains(listOf(cord[0], cord[1] + 1, cord[2])) &&
            canReachOutside(listOf(cord[0], cord[1] + 1, cord[2]))
        ) {
            count++
        }
        if (!cords.contains(listOf(cord[0], cord[1] - 1, cord[2])) &&
            canReachOutside(listOf(cord[0], cord[1] - 1, cord[2]))
        ) {
            count++
        }
        if (!cords.contains(listOf(cord[0], cord[1], cord[2] + 1)) &&
            canReachOutside(listOf(cord[0], cord[1], cord[2] + 1))
        ) {
            count++
        }
        if (!cords.contains(listOf(cord[0], cord[1], cord[2] - 1)) &&
            canReachOutside(listOf(cord[0], cord[1], cord[2] - 1))
        ) {
            count++
        }
    }
    return count
}


fun canReachOutside(cord: List<Int>): Boolean {
    val list = mutableSetOf(cord)
    val visited = mutableSetOf<List<Int>>()
    while (list.isNotEmpty()) {
        val current = list.first().also{ list.remove(it) }
        if(visited.contains(current)){
            continue
        }
        visited.add(current)
        if (cordCache.contains(current)) {
            cordCache[cord] = cordCache[current]!!
            if (cordCache[current]!!)
                return true
            else
                continue
        }
        if (cords.contains(current)) {
            cordCache[current] = false
            continue
        }
        if (current[0] !in 0..maxX || current[1] !in 0..maxY || current[2] !in 0..maxZ) {
            cordCache[cord] = true
            cordCache[current] = true
            return true
        }
        list.add(listOf(current[0] - 1, current[1], current[2]))
        list.add(listOf(current[0], current[1] - 1, current[2]))
        list.add(listOf(current[0], current[1], current[2] - 1))
        list.add(listOf(current[0] + 1, current[1], current[2]))
        list.add(listOf(current[0], current[1] + 1, current[2]))
        list.add(listOf(current[0], current[1], current[2] + 1))
    }

    return false
}