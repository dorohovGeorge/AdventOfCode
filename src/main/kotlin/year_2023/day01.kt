package year_2023

import utils.println
import utils.readFile

fun main() {
    println("Trebuchet?!")
    elvesMixedUpNumbers("src/main/kotlin/year_2023/day01.txt").println()
}
fun elvesMixedUpNumbers(filename: String): Int {
    val rightNumbers = mutableListOf<Int>()
    var lines: MutableList<String> = readFile(filename)
    val tmpLines = mutableListOf<String>()
    val map = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    lines.forEach {
        var reformedLine = it
        map.forEach { sub ->
            while (reformedLine.indexOf(sub.key) != -1) {
                val index = reformedLine.indexOf(sub.key)
                if (index != -1) {
                    reformedLine = reformedLine.replaceRange(index + 2, index + 2, sub.value)
                }
            }
        }
        tmpLines.add(reformedLine)
    }
    lines = tmpLines
    lines = lines.map { s -> s.filter { it.isDigit() } }.toMutableList()
    lines.forEach {
        when (it.length) {
            1 -> rightNumbers.add(it.toInt() * 10 + it.toInt())
            2 -> rightNumbers.add(it[0].digitToInt() * 10 + it[1].digitToInt())
            else -> rightNumbers.add(it[0].digitToInt() * 10 + it[it.length - 1].digitToInt())
        }
    }
    return rightNumbers.stream().mapToInt(Int::toInt).sum()
}
