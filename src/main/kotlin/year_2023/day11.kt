package year_2023

import utils.println
import utils.readFile
import kotlin.math.abs

fun main() {
    val filename = "src/main/kotlin/year_2023/day11.txt"
    val lines: MutableList<String> = readFile(filename)
    solve(lines, 2).println()
}

fun solve(input: List<String>, expansionRate: Int): Long {
    val galaxies = input.flatMapIndexed { y: Int, line: String ->
        line.mapIndexedNotNull { x, c -> if (c == '#') x to y else null }
    }
    val freeLines = (galaxies.minOf { it.second }..galaxies.maxOf { it.second })
        .filter { col -> galaxies.none { it.second == col } }
    val freeCols = (galaxies.minOf { it.first }..galaxies.maxOf { it.first })
        .filter { row -> galaxies.none { it.first == row } }
    return galaxies.pairwise()
        .sumOf { (a, b) ->
            val (x1, y1) = a
            val (x2, y2) = b
            val expandedXCount = freeCols.count { it in minOf(x1, x2)..maxOf(x1, x2) }
            val expandedYCount = freeLines.count { it in minOf(y1, y2)..maxOf(y1, y2) }
            (expansionRate - 1L) * (expandedXCount + expandedYCount) + abs(y1 - y2) + abs(x1 - x2)
        }
}

fun <T> List<T>.pairwise() = sequence {
    for (i in indices) {
        for (j in i + 1..indices.last) {
            yield(get(i) to get(j))
        }
    }
}
