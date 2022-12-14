import kotlin.math.abs

fun main() {
    println(day14A(readFile("Day14")))
    println(day14B(readFile("Day14")))
}

fun generateGrid(input: String, addFloor: Boolean = false ): MutableList<MutableList<Char>> {
    val rocks = input.trim().split("\n").flatMap { line ->
        line.split(" -> ").windowed(2).flatMap { pair: List<String> ->
            val start: Pair<Int, Int> = Pair(pair[0].split(",")[0].toInt(), pair[0].split(",")[1].toInt())
            val end: Pair<Int, Int> = Pair(pair[1].split(",")[0].toInt(), pair[1].split(",")[1].toInt())
            if (start.first == end.first) {//line moves up/down0
                generateSequence(start) {
                    Pair(
                        it.first,
                        if (start.second < end.second) it.second + 1 else it.second - 1
                    )
                }.take(abs(start.second - end.second) + 1).toList()
            } else { //if (start.second == end.second) {//line moves left/right
                generateSequence(start) {
                    Pair(
                        if (start.first < end.first) it.first + 1 else it.first - 1,
                        it.second
                    )
                }.take(abs(start.first - end.first) + 1).toList()
            }
        }
    }
    val ret = Array(1000) { CharArray(1000){'.'}.toMutableList() }.toMutableList()
    rocks.forEach {
        ret[it.first][it.second] = '#'
    }
    if(addFloor) {
        val floor = rocks.maxBy { it.second }.second + 2
        repeat(ret[floor].size) {
            ret[it][floor] = '-'
        }
    }
    ret[0][500] = '+'
    return ret
}
fun day14B(input: String): Int {
    val grid = generateGrid(input,true)
    var count = 0
    while(dropSand2(grid)){
        count++
    }
    return count
}

fun day14A(input: String): Int {
    val grid = generateGrid(input)
    var count = 0
    while(dropSand(grid)){
        count++
    }
    return count
}

fun dropSand(grid: MutableList<MutableList<Char>>) :Boolean{
    var sandPos = Pair(500,0)
    var moved = true
    while(moved){
        if(sandPos.first !in 0 until grid.size-1 || sandPos.second !in 0 until grid[0].size-1)//out of bounds
            return false
        else if(grid[sandPos.first][sandPos.second+1] == '.'){ //can move down
            moved = true
            sandPos = Pair(sandPos.first, sandPos.second+1)
        }
        else if(grid[sandPos.first -1][sandPos.second +1 ] == '.') {//can move down + left
            moved = true
            sandPos = Pair(sandPos.first -1, sandPos.second+1)
        }
        else if(grid[sandPos.first +1][sandPos.second  +1 ] == '.') {//can move down + right
            moved = true
            sandPos = Pair(sandPos.first +1, sandPos.second +1)
        }else{
            moved = false
        }
    }
    grid[sandPos.first][sandPos.second] = '0'
    return true
}

fun dropSand2(grid: MutableList<MutableList<Char>>) :Boolean{
    var sandPos = Pair(500,0)
    var moved = true
    if(grid[sandPos.first][sandPos.second] == '0'){
        return false
    }
    while(moved){
        if(sandPos.first !in 0 until grid.size-1 || sandPos.second !in 0 until grid[0].size-1)//out of bounds
            return false
        else if(grid[sandPos.first][sandPos.second+1] == '.'){ //can move down
            moved = true
            sandPos = Pair(sandPos.first, sandPos.second+1)
        }
        else if(grid[sandPos.first -1][sandPos.second +1 ] == '.') {//can move down + left
            moved = true
            sandPos = Pair(sandPos.first -1, sandPos.second+1)
        }
        else if(grid[sandPos.first +1][sandPos.second  +1 ] == '.') {//can move down + right
            moved = true
            sandPos = Pair(sandPos.first +1, sandPos.second +1)
        }else{
            moved = false
        }
    }
    grid[sandPos.first][sandPos.second] = '0'
    return true
}

