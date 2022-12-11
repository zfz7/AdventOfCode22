val m0 = Monkey(
    mutableListOf(79, 98),
    { item: Long -> item * 19 },
    23,
    2,
    3
)
val m1 = Monkey(
    mutableListOf(54, 65, 75, 74),
    { item: Long -> item + 6 },
    19,
    2,
    0
)
val m2 = Monkey(
    mutableListOf(79, 60, 97),
    { item: Long -> item * item },
    13,
    1,
    3
)
val m3 = Monkey(
    mutableListOf(74),
    { item: Long -> item + 3 },
    17,
    0,
    1
)

fun main() {

    println(day11A(testMonkeys()))
    println(day11A(parseMonkeys(readFile("Day11"))))
    println(day11B(testMonkeys()))
    println(day11B(parseMonkeys(readFile("Day11"))))
}

fun testMonkeys(): List<Monkey> =listOf<Monkey>(m0, m1, m2, m3).map{ mk -> mk.copy(items = mk.items.map {it}.toMutableList()) }

fun parseMonkeys(input: String): List<Monkey> {
    val m0 = Monkey(
        mutableListOf(66, 71, 94),
        { item: Long -> item * 5 },
        3,
        7,
        4
    )
    val m1 = Monkey(
        mutableListOf(70),
        { item: Long -> item + 6 },
        17,
        3,
        0
    )
    val m2 = Monkey(
        mutableListOf(62, 68, 56, 65, 94, 78),
        { item: Long -> item + 5 },
        2,
        3,
        1
    )
    val m3 = Monkey(
        mutableListOf(89, 94, 94, 67),
        { item: Long -> item + 2 },
        19,
        7,
        0
    )
    val m4 = Monkey(
        mutableListOf(71, 61, 73, 65, 98, 98, 63),
        { item: Long -> item * 7 },
        11,
        5,
        6
    )
    val m5 = Monkey(
        mutableListOf(55, 62, 68, 61, 60),
        { item: Long -> item + 7 },
        5,
        2,
        1
    )
    val m6 = Monkey(
        mutableListOf(93, 91, 69, 64, 72, 89, 50, 71),
        { item: Long -> item + 1 },
        13,
        5,
        2
    )
    val m7 = Monkey(
        mutableListOf(76, 50),
        { item: Long -> item * item },
        7,
        4,
        6
    )
    return listOf(m0, m1, m2, m3, m4, m5, m6, m7)
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
