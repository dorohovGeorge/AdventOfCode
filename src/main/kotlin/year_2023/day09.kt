package year_2023

import utils.println
import java.io.File

fun main() {
    val lines = File("src/main/kotlin/year_2023/day09.txt").readLines().map { it.split(" ").map { it.toInt() } }

    fun List<Int>.extrapolate(): Int = if (all { it == 0 }) 0 else {
        last() + windowed(2) { it.last() - it.first() }.extrapolate()
    }
    lines.sumOf { it.extrapolate() }.println()

    lines.sumOf { it.reversed().extrapolate() }.println()
}
