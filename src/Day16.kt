fun main() {
    println(day16A(readFile("Day16")))
    println(day16B(readFile("Day16")))
}

fun parse16Input(input: String): Map<String, Valve> {
    val list = input.trim().split("\n").map { line ->
        val splitLine = line
            .replace("valves", "valve")
            .replace("leads", "lead")
            .replace("tunnels", "tunnel")
            .split("Valve ", " has flow rate=", "; tunnel lead to valve ")
        Valve(
            name = splitLine[1],
            flowRate = splitLine[2].toInt(),
            tunnels = splitLine[3].split(", ").toSet()
        )
    }
    return list.associateBy { valve -> valve.name }
}
val MAX_TIME_A = 30
val MAX_TIME_B = 26

fun day16A(input: String): Int {
    val valves = parse16Input(input)
    val valesWithWater = valves.values.filter { it.flowRate > 0 }.size
    val totalFlows:MutableList<TotalFlow> = mutableListOf()
    totalFlows.add(TotalFlow(humanValve = valves["AA"]!!, elephantValve = valves["AA"]!!, humanTime = 0, elephantTime = 0, humanTotalEventualPressure = 0, elephantTotalEventualPressure = 0, opened = emptySet()))
    val ret = mutableSetOf<Int>()
    while (!totalFlows.isEmpty()) {
        val current = totalFlows.removeAt(0)
        if (current.humanTime > MAX_TIME_A || current.opened.size == valesWithWater) {
            ret.add(current.humanTotalEventualPressure)
        } else {
            current.humanValve.tunnels.forEach { tunnel ->
                 if (valves[tunnel]!!.flowRate > 0 && (MAX_TIME_A - current.humanTime) >= 2 && !current.opened.contains(tunnel)) {
                    totalFlows.add(
                        TotalFlow(
                            humanTime = current.humanTime + current.humanValve.travelTime + current.humanValve.openingTime,//opening valve
                            humanValve = valves[tunnel]!!,
                            humanTotalEventualPressure = current.humanTotalEventualPressure + ((MAX_TIME_A - current.humanTime - 2) * valves[tunnel]!!.flowRate),
                            elephantTime = current.elephantTime,
                            elephantValve = current.elephantValve,
                            elephantTotalEventualPressure = current.elephantTotalEventualPressure,
                            opened = current.opened.toSet() + setOf(tunnel)
                        )
                    )
                }
                //dont open
                    totalFlows.add(
                        TotalFlow(
                            humanTime = current.humanTime + current.humanValve.travelTime,//dont open valve
                            humanValve = valves[tunnel]!!,
                            humanTotalEventualPressure = current.humanTotalEventualPressure,
                            elephantValve = current.elephantValve,
                            elephantTotalEventualPressure = current.elephantTotalEventualPressure,
                            elephantTime = current.elephantTime,
                            opened = current.opened.toSet(),
                        )
                    )

            }
            if(totalFlows.size > 100000){
                val new = totalFlows.sortedByDescending { it.humanTotalEventualPressure }.take(50000)
                totalFlows.clear()
                totalFlows.addAll(new)
            }
        }
    }
    return ret.maxOf { it }
}

fun day16B(input: String): Int {
    val valves = parse16Input(input)
    val valesWithWater = valves.values.filter { it.flowRate > 0 }.size
    val totalFlows:MutableList<TotalFlow> = mutableListOf()
    totalFlows.add(TotalFlow(humanValve = valves["AA"]!!, elephantValve = valves["AA"]!!, humanTime = 0, elephantTime = 0, humanTotalEventualPressure = 0, elephantTotalEventualPressure = 0, opened = emptySet()))
    val ret = mutableSetOf<Int>()
    while (totalFlows.isNotEmpty()) {
        val current = totalFlows.removeAt(0)
        if ((current.humanTime >= MAX_TIME_B && current.elephantTime >= MAX_TIME_B)|| current.opened.size == valesWithWater) {
            ret.add(current.humanTotalEventualPressure + current.elephantTotalEventualPressure)
        } else {
            //move human
            if(current.humanTime < MAX_TIME_B)
            current.humanValve.tunnels.forEach { tunnel ->
                if (valves[tunnel]!!.flowRate > 0 && (MAX_TIME_B - current.humanTime) >= 2 && !current.opened.contains(tunnel)) {
                    totalFlows.add(
                        TotalFlow(
                            humanTime = current.humanTime + current.humanValve.travelTime + current.humanValve.openingTime,//opening valve
                            humanValve = valves[tunnel]!!,
                            humanTotalEventualPressure = current.humanTotalEventualPressure + ((MAX_TIME_B - current.humanTime - 2) * valves[tunnel]!!.flowRate),
                            elephantTime = current.elephantTime,
                            elephantValve = current.elephantValve,
                            elephantTotalEventualPressure = current.elephantTotalEventualPressure,
                            opened = current.opened.toSet() + setOf(tunnel)
                        )
                    )
                }
                //dont open
                totalFlows.add(
                    TotalFlow(
                        humanValve = valves[tunnel]!!,
                        humanTotalEventualPressure = current.humanTotalEventualPressure,
                        elephantTime = current.elephantTime,
                        elephantValve = current.elephantValve,
                        elephantTotalEventualPressure = current.elephantTotalEventualPressure,
                        humanTime = current.humanTime + current.humanValve.travelTime,//dont open valve
                        opened = current.opened.toSet()
                    )
                )

            }
            //move elephant
            if(current.elephantTime <MAX_TIME_B)
            current.elephantValve.tunnels.forEach { tunnel ->
                if (valves[tunnel]!!.flowRate > 0 && (MAX_TIME_B - current.elephantTime) >= 2 && !current.opened.contains(tunnel)) {
                    totalFlows.add(
                        TotalFlow(
                            humanTime = current.humanTime,
                            humanValve = current.humanValve,
                            humanTotalEventualPressure = current.humanTotalEventualPressure,
                            elephantValve = valves[tunnel]!!,
                            elephantTotalEventualPressure = current.elephantTotalEventualPressure + ((MAX_TIME_B - current.elephantTime - 2) * valves[tunnel]!!.flowRate),
                            elephantTime = current.elephantTime + current.elephantValve.travelTime + current.elephantValve.openingTime,//opening valve
                            opened = current.opened.toSet() + setOf(tunnel)
                        )
                    )
                }
                //dont open
                totalFlows.add(
                    TotalFlow(
                        humanTime = current.humanTime,
                        humanValve = current.humanValve,
                        humanTotalEventualPressure = current.humanTotalEventualPressure,
                        elephantValve = valves[tunnel]!!,
                        elephantTotalEventualPressure = current.elephantTotalEventualPressure,
                        elephantTime = current.elephantTime + current.elephantValve.travelTime,//dont open valve
                        opened = current.opened,
                    )
                )

            }
            if(totalFlows.size > 100000){
                val new = totalFlows.sortedByDescending { it.humanTotalEventualPressure +  it.elephantTotalEventualPressure}.take(70000)
                totalFlows.clear()
                totalFlows.addAll(new)
            }
        }
    }
    return ret.maxOf { it}
}

data class Valve(
    val name: String,
    val travelTime: Int = 1,
    val openingTime: Int = 1,
    val flowRate: Int,
    val tunnels: Set<String>
)

data class TotalFlow(
    val humanTime: Int,
    val humanValve: Valve,
    val humanTotalEventualPressure: Int,
    val elephantTime: Int,
    val elephantValve: Valve,
    val elephantTotalEventualPressure: Int,
    val opened: Set<String>
)