import java.lang.Exception

fun main() {
    println(day13A(readFile("Day13")))
    println(day13B(readFile("Day13")))
}

private fun sort(a: List<Any>, b: List<Any>): Int {
    try {
        isSmaller(a, b)
    } catch (e: Exception) {
        if (e.message == "true") {
            return -1
        }
        if (e.message == "false") {
            return 1
        }
        if (e.message == "help") {
            throw e
        }
    }
    return 0
}

private fun isSmaller(a: List<Any>, b: List<Any>) {
    if (a.isEmpty() && b.isEmpty()) {
        return
    } else if (a.isNotEmpty() && b.isEmpty()) {
        throw Exception("false")
    } else if (a.isEmpty() && b.isNotEmpty()) {
        throw Exception("true")
    }
    if (a.getOrNull(0) is Int && b.getOrNull(0) is Int) {
        if (a[0] as Int == b[0] as Int) {
            isSmaller(a.subList(1, a.size), b.subList(1, b.size))
        } else {
            throw Exception("${(a[0] as Int) < (b[0] as Int)}")
        }
    } else if (a.getOrNull(0) is List<*> && b.getOrNull(0) is List<*>) {
        isSmaller((a[0] as List<Any>), (b[0] as List<Any>))
        isSmaller(a.subList(1, a.size), b.subList(1, b.size))
    } else if (a.getOrNull(0) is Int && b.getOrNull(0) is List<*>) {
        isSmaller(listOf(a[0]), b[0] as List<Any>)
        isSmaller(a.subList(1, a.size), b.subList(1, b.size))
    } else if (a.getOrNull(0) is List<*> && b.getOrNull(0) is Int) {
        isSmaller(a[0] as List<Any>, listOf(b[0]))
        isSmaller(a.subList(1, a.size), b.subList(1, b.size))
    }
//    throw Exception("help")
}
fun day13B(input: String): Int {
    var packets: List<List<Any>> = input.trim().split("\n\n").flatMap { pair ->
        val a: List<Any> = toList(pair.split("\n")[0])
        val b: List<Any> = toList(pair.split("\n")[1])
        if (a.toString().replace(" ", "") != pair.split("\n")[0].trim().replace(" ", "")) {
            println(a.toString().replace(" ", ""))
            println(pair.split("\n")[0])
        }
        if (b.toString().replace(" ", "") != pair.split("\n")[1].trim().replace(" ", "")) {
            println(b.toString().replace(" ", ""))
            println(pair.split("\n")[1])
        }
        listOf(a,b)
    }
    packets = packets + listOf(listOf(listOf(2))) +listOf(listOf(listOf(6)))
    val sorted = packets.sortedWith{a,b->sort(a,b)}
//   sorted.forEach{println(it)}
//    println(sorted.indexOf(listOf(listOf(2))))
//    println(sorted.indexOf(listOf(listOf(6))))
    return (sorted.indexOf(listOf(listOf(2))) +1)  * (sorted.indexOf(listOf(listOf(6)))+1)
}

fun day13A(input: String): Int {
    var sum = 0
    input.trim().split("\n\n").mapIndexed { idx, pair ->
        val a: List<Any> = toList(pair.split("\n")[0])
        val b: List<Any> = toList(pair.split("\n")[1])
        if (a.toString().replace(" ", "") != pair.split("\n")[0].trim().replace(" ", "")) {
            println(a.toString().replace(" ", ""))
            println(pair.split("\n")[0])
        }
        if (b.toString().replace(" ", "") != pair.split("\n")[1].trim().replace(" ", "")) {
            println(b.toString().replace(" ", ""))
            println(pair.split("\n")[1])
        }
        if (sort(a, b) < 0)
            sum += (idx + 1)
    }
    return sum
}

fun toList(s: String): List<Any> {
    var currentIdx = 0;
    return s.removePrefix("[").removeSuffix("]").mapIndexedNotNull { idx, it ->
        if (idx < currentIdx) {
            null
        } else if (it == '[') {
            var i = idx + 2
            var count = 1
            while (count != 0) {
                if (s[i] == ']') {
                    count--
                } else if (s[i] == '[') {
                    count++
                }
                i++
            }
            currentIdx = i
            toList(s.substring(idx + 1, i))
        } else if (it == ',' || it == ']') {
            null
        } else {
            currentIdx = idx + s.substring(idx + 1).split(",", "]")[0].length
            s.substring(idx + 1).split(",", "]")[0].toInt()
        }
    }
}

