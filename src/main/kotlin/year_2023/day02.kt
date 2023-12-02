package year_2023

import utils.println
import utils.readFile

const val red = 12
const val green = 13
const val blue = 14

fun main() {
    val filename = "src/main/kotlin/year_2023/day02.txt"
    val lines: MutableList<String> = readFile(filename)
    part1(lines).println()
}

fun part1(list: List<String>): Int {
    var rightIds = 0
    list.forEach {
        rightIds += parseGame1(it.replace(" ", ""))
    }
    return rightIds
}

fun part2(list: List<String>): Int {
    var rightIds = 0
    list.forEach {
        rightIds += parseGame2(it.replace(" ", ""))
    }
    return rightIds
}

fun parseGame1(line: String): Int {
    val id = line.substring(4, line.indexOf(":")).toInt()
    val tmpLine = line.substring(line.indexOf(":") + 1, line.length)
    val gameLine = tmpLine.split(";")
    var lineRed = 0
    var lineBlue = 0
    var lineGreen = 0
    gameLine.forEach {
        val ballList = it.split(",")
        ballList.forEach {
            with(it) {
                when {
                    contains("red") -> {
                        lineRed = it.substring(0, it.indexOf("red")).toInt()
                        if (lineRed > red) return 0
                    }

                    contains("blue") -> {
                        lineBlue = it.substring(0, it.indexOf("blue")).toInt()
                        if (lineBlue > blue) return 0
                    }

                    contains("green") -> {
                        lineGreen = it.substring(0, it.indexOf("green")).toInt()
                        if (lineGreen > green) return 0
                    }
                }

            }
        }
    }

    return id
}

fun parseGame2(line: String): Int {
    val tmpLine = line.substring(line.indexOf(":") + 1, line.length)
    val gameLine = tmpLine.split(";")
    var lineRed = 1
    var lineBlue = 1
    var lineGreen = 1
    gameLine.forEach { s ->
        val ballList = s.split(",")
        ballList.forEach {
            with(it) {
                when {
                    contains("red") -> {
                        val tmpRed = it.substring(0, it.indexOf("red")).toInt()
                        if (lineRed < tmpRed) lineRed = tmpRed
                    }

                    contains("blue") -> {
                        val tmpBlue = it.substring(0, it.indexOf("blue")).toInt()
                        if (lineBlue < tmpBlue) lineBlue = tmpBlue
                    }

                    contains("green") -> {
                        val tmpGreen = it.substring(0, it.indexOf("green")).toInt()
                        if (lineGreen < tmpGreen) lineGreen = tmpGreen
                    }
                }

            }
        }
    }

    return lineRed * lineBlue * lineGreen
}
