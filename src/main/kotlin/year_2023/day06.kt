package year_2023

import utils.println
import utils.readFile

const val speed = 1
fun main() {
    val filename = "src/main/kotlin/year_2023/day06.txt"
    val lines: MutableList<String> = readFile(filename)
    lines.println()
    part_06_2(lines).println()
}

fun part_06_1(races: List<String>): Long {
    var winnedStrategy = 1L
    var parsedRaces = parseRace(races)
    parsedRaces.forEach {
        winnedStrategy *= it.countWinStrategy()
    }
    return winnedStrategy
}

fun part_06_2(races: List<String>): Long {
    var winnedStrategy = 1L
    var parsedRaces = parseRace2(races)
    parsedRaces.forEach {
        winnedStrategy *= it.countWinStrategy()
    }
    return winnedStrategy
}

fun parseRace2(lines: List<String>): List<Race> {
    val raceList = mutableListOf<Race>()
    var timeList = lines[0].substring(lines[0].indexOf(":") + 1, lines[0].length).trim(' ').split(' ')
        .filter { it.containsDigital() }
    timeList = mutableListOf(timeList[0] + timeList[1] + timeList[2] + timeList[3])
    var distanceList = lines[1].substring(lines[1].indexOf(":") + 1, lines[1].length).trim(' ').split(' ')
        .filter { it.containsDigital() }
    distanceList = mutableListOf(distanceList[0] + distanceList[1] + distanceList[2] + distanceList[3])
    timeList.forEachIndexed { index, s ->
        raceList.add(Race(s.toLong(), distanceList[index].toLong()))
    }

    return raceList
}


fun parseRace(lines: List<String>): List<Race> {
    val raceList = mutableListOf<Race>()
    val timeList = lines[0].substring(lines[0].indexOf(":") + 1, lines[0].length).trim(' ').split(' ')
        .filter { it.containsDigital() }
    val distanceList = lines[1].substring(lines[1].indexOf(":") + 1, lines[1].length).trim(' ').split(' ')
        .filter { it.containsDigital() }
    timeList.forEachIndexed { index, s ->
        raceList.add(Race(s.toLong(), distanceList[index].toLong()))
    }

    return raceList
}

data class Race(
    var time: Long,
    var distance: Long
) {
    fun countWinStrategy(): Long {
        var winStrategy = 0L
        (0..time).forEach {
            var result = ((time - it) * (it * speed))
            if (result > distance) {
                winStrategy++
            }
        }
        return winStrategy
    }
}