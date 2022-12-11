fun main() {
    println(day10A(readFile("Day10")))
    println(day10B(readFile("Day10")))
}

fun day10B(input: String): String {
    val ops = input.trim().split("\n")
    var cycle = 1
    var pos = 0
    var reg = 1
    var screen = ""

    ops.forEach { op ->
        if (op == "noop") {
            println("pos: $pos reg $reg in ${pos in (reg - 1)..(reg + 1)}")

            screen += if (pos in (reg - 1)..(reg + 1)) "#" else "."
            screen += if (pos == 39) "\n" else ""
            cycle++
            pos = (pos + 1) % 40
        } else {
            println("pos: $pos reg $reg in ${pos in (reg - 1)..(reg + 1)}")
            screen += if (pos in (reg - 1)..(reg + 1)) "#" else "."
            screen += if (pos == 39) "\n" else ""
            cycle++
            pos = (pos + 1) % 40

            println("pos: $pos reg $reg in ${pos in (reg - 1)..(reg + 1)}")
            screen += if (pos in (reg - 1)..(reg + 1)) "#" else "."
            screen += if (pos == 39) "\n" else ""
            cycle++
            pos = (pos + 1) % 40

            reg += op.split(" ")[1].toInt()
        }

    }
    return screen
}

fun day10A(input: String): Int {
    val ops = input.trim().split("\n")
    val cycles = listOf<Int>(20, 60, 100, 140, 180, 220)
    var signal = 0;
    var reg = 1
    var cycle = 0

    ops.forEach { op ->
        if (op == "noop") {
            cycle++
            if (cycles.contains(cycle)) {
                signal += reg * cycle
            }
        } else {
            cycle++
            if (cycles.contains(cycle)) {
                signal += reg * cycle
            }
            cycle++
            if (cycles.contains(cycle)) {
                signal += reg * cycle
            }
//            println(op.split(" ")[1].toInt())
            reg += op.split(" ")[1].toInt()
        }

    }
    return signal
}

