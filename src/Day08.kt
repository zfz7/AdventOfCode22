fun main() {
    println(day8A(readFile("Day08")))
    println(day8B(readFile("Day08")))
}

fun visibleNorth(forest: List<List<Int>>, i: Int, j: Int): Boolean {
    val height = forest[i][j]
    var iPos = i -1
    while(iPos >= 0){
        if(forest[iPos][j] >= height)
            return false
        iPos--;
    }
    return true
}
fun visibleSouth(forest: List<List<Int>>, i: Int, j: Int): Boolean {
    val height = forest[i][j]
    var iPos = i +1
    while(iPos <= forest[j].size -1){
        if(forest[iPos][j] >= height)
            return false
        iPos++;
    }
    return true
}

fun visibleEast(forest: List<List<Int>>, i: Int, j: Int): Boolean {
    val height = forest[i][j]
    var jPos = j-1
    while(jPos >= 0){
        if(forest[i][jPos] >= height)
            return false
        jPos--;
    }
    return true
}

fun visibleWest(forest: List<List<Int>>, i: Int, j: Int): Boolean {
    val height = forest[i][j]
    var jPos = j +1
    while(jPos <= forest[j].size -1){
        if(forest[i][jPos] >= height)
            return false
        jPos++;
    }
    return true
}

fun day8A(input: String): Int {
    val forest: List<List<Int>> = input.trim().split("\n").map { line -> line.map { it.toString().toInt() } }
    var count = 0
    for (i in 1 until forest.size -1 ) {
        for (j in 1 until forest[i].size -1) {
            if(visibleNorth(forest,i,j)
               ||visibleSouth(forest,i,j)
               ||visibleEast(forest,i,j)
               ||visibleWest(forest,i,j)
           ){
               count++
           }
        }
    }
    return count + forest.size +  forest.size +  forest[0].size +forest[0].size -4
}

//--
fun scoreNorth(forest: List<List<Int>>, i: Int, j: Int): Int {
    val height = forest[i][j]
    var iPos = i -1
    var score = 1
    while(iPos > 0){
        if(forest[iPos][j] >= height)
            return score
        score ++
        iPos--
    }
    return score
}
fun scoreSouth(forest: List<List<Int>>, i: Int, j: Int): Int {
    val height = forest[i][j]
    var iPos = i+1
    var score = 1
    while(iPos < forest[j].size -1){
        if(forest[iPos][j] >= height)
            return score
        iPos++;
        score++
    }
    return score
}

fun scoreEast(forest: List<List<Int>>, i: Int, j: Int): Int {
    val height = forest[i][j]
    var jPos = j-1
    var score = 1
    while(jPos > 0){
        if(forest[i][jPos] >= height)
            return score
        jPos--
        score++
    }
    return score
}

fun scoreWest(forest: List<List<Int>>, i: Int, j: Int): Int {
    val height = forest[i][j]
    var jPos = j +1
    var score = 1
    while(jPos < forest[j].size -1){
        if(forest[i][jPos] >= height)
            return score
        jPos++
        score++
    }
    return score
}

fun day8B(input: String): Int {
    val forest: List<List<Int>> = input.trim().split("\n").map { line -> line.map { it.toString().toInt() } }
    var max = 0
    for (i in 1 until forest.size -1 ) {
        for (j in 1 until forest[i].size -1) {
            if(max< scoreNorth(forest,i,j) * scoreSouth(forest,i,j) *scoreEast(forest,i,j) *scoreWest(forest,i,j)){
                max = scoreNorth(forest,i,j) * scoreSouth(forest,i,j) *scoreEast(forest,i,j) *scoreWest(forest,i,j)
                println("max: $max - $i,$j")
            }
        }
    }
    return max
}