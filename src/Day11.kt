fun main() {
    println(day11A(parseMonkeys(readFile("Day11"))))
    println(day11B(parseMonkeys(readFile("Day11"))))
}

fun parseMonkeys(input: String): List<Monkey>
     = input.split("\n\n").map { mk ->
        Monkey(
            items = mk.split("\n").filter{ it.contains("Starting items: ")}[0].split("  Starting items: ",", ").mapNotNull{it.toLongOrNull()}.toMutableList(),
            op = { item: Long ->
                val line = mk.split("\n").filter{ it.contains("Operation: new = ")}[0].split(" ")
                when(line[6]){
                    "+" -> item + (line[7].toLongOrNull() ?: item)
                    "-" -> item - (line[7].toLongOrNull() ?: item)
                    "/" -> item / (line[7].toLongOrNull() ?: item)
                    "*" -> item * (line[7].toLongOrNull() ?: item)
                    else -> throw Exception("help")
                }
            },
            divider = mk.split("\n").filter{ it.contains("Test: divisible by ")}[0].split("Test: divisible by ")[1].toLong(),
            pass = mk.split("\n").filter{ it.contains("If true: throw to monkey ")}[0].split("If true: throw to monkey ")[1].toInt(),
            fail = mk.split("\n").filter{ it.contains("If false: throw to monkey ")}[0].split("If false: throw to monkey ")[1].toInt(),
        )
    }
fun day11A(monkeys: List<Monkey>): Long {
    repeat(20) {
        monkeys.forEach {
            it.inspectAll(monkeys)
        }
    }
//    monkeys.forEachIndexed { idx, monkey ->
//        println("Round 20 Monkey $idx items ${monkey.items} inspected ${monkey.count}")
//    }
    return monkeys.map { it.count }.sorted().reversed().take(2).fold(1L) { acc, it -> acc * it }
}

fun day11B(mkys: List<Monkey>): Long {
    val monkeys = mkys.map { it.copy(worry = true) }
    repeat(10000) {
        monkeys.forEach {
            it.inspectAll(monkeys)
        }
    }
//    monkeys.forEachIndexed { idx, monkey ->
//        println("Monkey $idx inspected ${monkey.count} ${monkey.items}")
//    }

    return monkeys.map { it.count }.sorted().reversed().take(2).fold(1L) { acc, it -> acc * it }
}

data class Monkey(
    val items: MutableList<Long>,
    private val op: (item: Long) -> Long,
    private val divider: Long,
    private val pass: Int,
    private val fail: Int,
    private val worry: Boolean = false
) {
    var count: Long = 0
    fun inspectAll(monkeys: List<Monkey>) {
        items.forEach { item ->
            val x: Long = if (worry) op(item) % monkeys.map { it.divider }.reduce{ acc, crr -> acc * crr } else op(item) / 3L
            if (test(x)) {
                monkeys[pass].add(x)
            } else {
                monkeys[fail].add(x)
            }
//            println("item $item op ${x} test ${test(op(item))} true $pass false $fail")
            count++
        }
        items.clear()
    }

    private fun add(item: Long) {
        items.add(item)
    }

    private fun test(x: Long): Boolean = ((x % divider) == 0L)

}
