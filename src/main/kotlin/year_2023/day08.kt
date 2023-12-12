package year_2023

import utils.println
import utils.readFile

fun main() {
    val filename = "src/main/kotlin/year_2023/day08.txt"
    val lines: MutableList<String> = readFile(filename)

    part_08_02(lines).println()
}

private enum class Instruction {
    LEFT,
    RIGHT
}

private data class Node(val source: String, val left: String, val right: String)
private data class GameMap(val instructions: Sequence<Instruction>, val map: Map<String, Node>)


fun part_08_01(lines: List<String>): Int {
    val gameMap = GameMap(parseInstructions(lines[0]), parseNodes(lines.drop(2)))

    val map = gameMap.map
    var cur = map["AAA"]
    var counter = 0

    for (instruction in gameMap.instructions.repeat()) {
        if (cur!!.source == "ZZZ") break
        cur = when (instruction) {
            Instruction.LEFT -> map[cur.left]
            Instruction.RIGHT -> map[cur.right]
        }
        counter++
    }
    return counter
}

fun part_08_02(lines: List<String>): Long {
    val gameMap = GameMap(parseInstructions(lines[0]), parseNodes(lines.drop(2)))

    val map = gameMap.map
    val cur = map.filter { it.key.endsWith('A') }
    val loopLengths = arrayListOf<Long>()

    for (aSource in cur.keys) {
        var tmp = map[aSource]!!
        var counter = 0L

        for (instruction in gameMap.instructions.repeat()) {
            if (tmp.source.endsWith('Z')) {
                loopLengths.add(counter)
                break
            }
            tmp = when (instruction) {
                Instruction.LEFT -> map[tmp.left]!!
                Instruction.RIGHT -> map[tmp.right]!!
            }
            counter++
        }

    }
    return lcm(loopLengths)
}

fun lcmFor2(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) return lcm
        lcm += larger
    }
    return maxLcm
}

fun lcm(loopLengths: ArrayList<Long>): Long {
    var lcm = 1L
    for (number in loopLengths) {
        lcm = lcmFor2(lcm, number)
    }
    return lcm
}

fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

private fun parseNodes(list: List<String>): MutableMap<String, Node> {
    val map = mutableMapOf<String, Node>()
    list.forEach { s ->
        val tmp = s.filter { it != ' ' }
        val source = tmp.substring(0, tmp.indexOf('='))
        val left = tmp.substring(tmp.indexOf('(') + 1, tmp.indexOf(','))
        val right = tmp.substring(tmp.indexOf(',') + 1, tmp.indexOf(')'))
        map[source] = Node(source, left, right)
    }
    return map
}

private fun parseInstructions(str: String): Sequence<Instruction> {
    val list = mutableListOf<Instruction>()
    str.forEach {
        when (it) {
            'L' -> list.add(Instruction.LEFT)
            'R' -> list.add(Instruction.RIGHT)
        }
    }

    return list.asSequence()
}