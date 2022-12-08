val sizes = mutableMapOf<String, Int>()
val folders = mutableMapOf<String, List<String>>()
fun main() {
    setUp(readFile("Day07"))
    println(folders)
    println(sizes)
    println(day7A())
    println(day7B())
}

fun day7A(): Int = sizes.toList().sumOf {if(!it.first.contains("FILE") && it.second<=100000) it.second else 0}
fun day7B(): Int {
    val spaceNeeded = -(70000000 - 30000000 - sizes["/"]!!)
    return sizes.toList().filter { it.second > spaceNeeded }.sortedBy { it.second }.first().second
}


fun setUp(input: String) {
    val cmds = input.split("\n")
    val dirTrace = ArrayDeque<String>()
    cmds.forEach {
        if(it == "$ cd .."){
            dirTrace.removeFirst()
        }
        if (it =="$ cd /") {
            folders["/"] = listOf()
            dirTrace.addFirst("/")
        }
        if (it.startsWith("$ cd ") && it != "$ cd .." && it != "$ cd /") {
            dirTrace.addFirst(it.substring(5))
            folders[dirTrace.reversed().joinToString (separator = "/",postfix="")] = listOf()
        }
        if (it.startsWith("dir ")) {
            folders[dirTrace.reversed().joinToString (separator = "/",postfix="")] = folders[dirTrace.reversed().joinToString (separator = "/",postfix="")]!!.plus(listOf(dirTrace.reversed().joinToString (separator = "/",postfix="")+"/"+ it.substring(4)))
        }
        if (it.split(" ")[0].toIntOrNull() != null) {
            sizes[dirTrace.reversed().joinToString (separator = "/",postfix="") + "/FILE_" + it.split(" ")[1]] = it.split(" ")[0].toInt()
            folders[dirTrace.reversed().joinToString (separator = "/",postfix="")] = folders[dirTrace.reversed().joinToString (separator = "/",postfix="")]!!.plus(listOf(dirTrace.reversed().joinToString (separator = "/",postfix="")+ "/FILE_" + it.split(" ")[1]))
        }

    }

    calcDirSizes("/")
}

fun calcDirSizes(dir: String): Int {
    sizes[dir] = folders[dir]!!.sumOf { item: String ->
        if (sizes.containsKey(item))
            sizes[item]!!
        else {
            sizes[item] = calcDirSizes(item)
            sizes[item]!!
        }
    }
    return sizes[dir]!!
}
