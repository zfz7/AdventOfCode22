fun main() {
    println(day17A(readFile("Day17").trim(), 2200))
    println(day17B(readFile("Day17").trim()))
}

//val shapes = listOf("1111", ".2.\n222\n.2.", "..3\n..3\n333", "3\n3\n3\n3", "33\n33")
val shapes = listOf("####", ".#.\n###\n.#.", "..#\n..#\n###", "#\n#\n#\n#", "##\n##")
fun day17A(input: String, blocks: Long): Int {
    val grid = Array(7) { CharArray(5000) { '.' }.toMutableList() }.toMutableList()
    var droppedRocks = 0
    var windBlown = 0
    var shape = 0
    var pos = Pair(2, grid[0].size - 4) //3 from bottom
//    var pos = Pair(2, grid[0].size - 4) //3 from bottom
    while (droppedRocks < blocks) {
        var stillFalling = true
        drawShape(shape, grid, pos, false)
//        printGrid(grid)
        while (stillFalling) {
            clearShape(grid)
            val sidewaysPos: Pair<Int, Int> = pushShape(shape, grid, input[windBlown], pos)
            windBlown = (windBlown + 1) % input.length
            drawShape(shape, grid, sidewaysPos, false)
//            printGrid(grid)

            //drop
            clearShape(grid)
            val droppedPos: Pair<Int, Int> = dropShape(shape, grid, sidewaysPos)
            stillFalling = sidewaysPos != droppedPos
            drawShape(shape, grid, droppedPos, !stillFalling)
//            printGrid(grid)
            pos = droppedPos
        }

        pos = Pair(2, findHeight(grid) - 4)
//        if(grid[0].size - pos.second - 3 - 1 >= 844){
//            println(droppedRocks)
//            break;
//        }
//        pos = Pair(2, pos.second - shapes[shape].split("\n").size - 3)
        shape = (shape + 1) % shapes.size
        droppedRocks++

    }
    printGrid(grid)
    return grid[0].size - pos.second - 3 - 1
}

fun day17B(input: String): Long {
    //1565517241411
    //1514285714288
    //1565517240376 < -LOW
    //1565517242226 <- WRONG
    //1565517241382 <- ?
    //notice that blocks repeats after some time
    //Block        ~ height
    //531 + 1740*a = 844 + 2724*a
    val initialNonRepeatingBlocks = 531L
    val repeatingBlocks = 1740L
    val initialNonRepeatingHeight = 844L
    val repeatingHeight = 2724L
    val repeatingTimes: Long = (1000000000000L -initialNonRepeatingBlocks) / repeatingBlocks
    val remainBlocks: Long = (1000000000000L -initialNonRepeatingBlocks) % repeatingBlocks
    println(repeatingTimes)
    println(remainBlocks)
    val height = initialNonRepeatingHeight + repeatingTimes * repeatingHeight
    return height + day17A(input, initialNonRepeatingBlocks+remainBlocks.toInt()) - initialNonRepeatingHeight
}

fun shiftGridDown(grid: MutableList<MutableList<Char>>, bottomLeft: Pair<Int, Int>, shape: Int): Int {
    for (j in bottomLeft.second - shapes[shape].split("\n").size .. bottomLeft.second) {
        if (grid[0][j] == '.' ||
            grid[1][j] == '.' ||
            grid[2][j] == '.' ||
            grid[3][j] == '.' ||
            grid[4][j] == '.' ||
            grid[5][j] == '.' ||
            grid[6][j] == '.'
        )
            continue
//            printGrid(grid)
//            println(j)
        for (j1 in 0 until j) {
            grid[0][grid[0].size - 1 - j1] = grid[0][j - j1]
            grid[1][grid[0].size - 1 - j1] = grid[1][j - j1]
            grid[2][grid[0].size - 1 - j1] = grid[2][j - j1]
            grid[3][grid[0].size - 1 - j1] = grid[3][j - j1]
            grid[4][grid[0].size - 1 - j1] = grid[4][j - j1]
            grid[5][grid[0].size - 1 - j1] = grid[5][j - j1]
            grid[6][grid[0].size - 1 - j1] = grid[6][j - j1]
            grid[0][j - j1] = '.'
            grid[1][j - j1] = '.'
            grid[2][j - j1] = '.'
            grid[3][j - j1] = '.'
            grid[4][j - j1] = '.'
            grid[5][j - j1] = '.'
            grid[6][j - j1] = '.'
        }
//            printGrid(grid)
//            println(j)
        return grid[0].size - j -1
    }
    return -1
}

fun findHeight(grid: MutableList<MutableList<Char>>): Int {
    for (j in 0 until grid[0].size) {
        for (i in 0 until grid.size) {
            if (grid[i][j] == '@' || grid[i][j] == '0' || grid[i][j] == '1' || grid[i][j] == '2' || grid[i][j] == '3' || grid[i][j] == '4') {
                return j
            }
        }

    }
    throw Exception("help")
}

fun dropShape(shape: Int, grid: MutableList<MutableList<Char>>, bottomLeft: Pair<Int, Int>): Pair<Int, Int> {
    shapes[shape].split("\n").reversed().forEachIndexed { idxY, line ->
        line.forEachIndexed { idxX, char ->
            if (isChar(char) && grid.getOrNull(bottomLeft.first + idxX)
                    ?.getOrNull(bottomLeft.second - idxY + 1) != '.'
            ) {
                // cant move
                return bottomLeft
            }
        }
    }
    return Pair(bottomLeft.first, bottomLeft.second + 1)
}

fun clearShape(grid: MutableList<MutableList<Char>>) {
    for (i in 0 until grid.size) {
        for (j in 0 until grid[0].size) {
            if (isChar(grid[i][j]))
                grid[i][j] = '.'
        }
    }
}

fun pushShape(
    shape: Int,
    grid: MutableList<MutableList<Char>>,
    direction: Char,
    bottomLeft: Pair<Int, Int>
): Pair<Int, Int> {
    val dir = when (direction) {
        '>' -> 1
        '<' -> -1
        else -> throw Exception("help")
    }
    shapes[shape].split("\n").reversed().forEachIndexed { idxY, line ->
        line.forEachIndexed { idxX, char ->
            if (isChar(char) && grid.getOrNull(bottomLeft.first + idxX + dir)
                    ?.getOrNull(bottomLeft.second - idxY) != '.'
            ) {
                // cant move
                return bottomLeft
            }
        }
    }
    return Pair(bottomLeft.first + dir, bottomLeft.second)
}

fun drawShape(shape: Int, grid: MutableList<MutableList<Char>>, bottomLeft: Pair<Int, Int>, atRest: Boolean) {
    shapes[shape].split("\n").reversed().forEachIndexed { idxY, line ->
        line.forEachIndexed { idxX, char ->
            if (grid[bottomLeft.first + idxX][bottomLeft.second - idxY] == '.')
                grid[bottomLeft.first + idxX][bottomLeft.second - idxY] =
                    if (atRest && isChar(char)) shape.toString()[0] else char
//                if (atRest && isChar(char)) '@' else char
        }
    }
}

fun isChar(char: Char) =
    when (char) {
//        '#', '0', '1', '2', '3', '4' -> true
        '#' -> true
        else -> false
    }

fun isRest(char: Char) =
    when (char) {
        '#', '0', '1', '2', '3', '4' -> true
        else -> false
    }
