fun main() {
    println(day15A(readFile("Day15")))
    println(day15B(readFile("Day15")))
}

fun parseInput(input: String): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
    val sensorBeaconPair = input.trim().split("\n").map { line ->
        val ints = line.split("Sensor at x=", ", y=", ": closest beacon is at x=", ", y=")
        val sensor: Pair<Int, Int> = Pair(ints[1].toInt(), ints[2].toInt())
        val beacon: Pair<Int, Int> = Pair(ints[3].toInt(), ints[4].toInt())
        Pair(sensor, beacon)
    }
    return sensorBeaconPair
}

fun generateSensorGrid(sensorBeaconPair: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): MutableList<MutableList<Char>> {
    val ret = Array(100000) { CharArray(100000) { '.' }.toMutableList() }.toMutableList()
    sensorBeaconPair.forEach {
        ret[it.first.first][it.first.second] = 'S'
        ret[it.second.first][it.second.second] = 'B'
    }
    return ret
}

fun day15B(input: String): Long {
    val sensorBeaconPair = parseInput(input)
//    println(sensorBeaconPair)
    val things = sensorBeaconPair.flatMap { listOf(it.first, it.second) }.toSet()
    val points = sensorBeaconPair.flatMap { pair ->
        val dist = dist(pair) +1
        val points = mutableListOf<Pair<Int,Int>>()
        for(i in -dist..+dist){
            points.add(Pair(pair.first.first + i, pair.first.second + (dist - i)))
        }
        points
    }.toSet()
    points.forEach{ pos ->
        if (pos.first in 0..4000000 &&
            pos.second in 0..4000000 &&
            !things.contains(pos) &&
            sensorBeaconPair.all { pair ->  dist(pair.first, pos) > dist(pair)
        }) {
            println(pos)
            return pos.first * 4000000L + pos.second
        }
    }

    return -1
}

fun day15A(input: String): Int {
    val sensorBeaconPair = parseInput(input)
    println(sensorBeaconPair)
    val things = sensorBeaconPair.flatMap { listOf(it.first, it.second) }.toSet()
    var count = 0
    for (x in -50_000_000..50_000_000) {
        if (x % 10000000 == 0)
            println(x)
        if (!things.contains(Pair(x, 2000000)) &&
            sensorBeaconPair.any { pair -> dist(pair.first, Pair(x, 2000000)) <= dist(pair) }
        )
            count++
    }
    return count
}


fun dist(a: Pair<Int, Int>, b: Pair<Int, Int>) =
    Math.abs(a.first - b.first) + Math.abs(a.second - b.second)

fun dist(pair: Pair<Pair<Int, Int>, Pair<Int, Int>>) = dist(pair.first, pair.second)

fun printGrid(grid: MutableList<MutableList<Char>>) {
    for (i in 0 until grid[0].size) {
        for (j in 0 until grid.size) {
            print(grid[j][i])
        }
        print("\n")
    }
}