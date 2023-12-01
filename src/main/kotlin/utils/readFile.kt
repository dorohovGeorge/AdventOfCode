package utils

import java.io.File

fun readFile(filename: String): MutableList<String> {
    val lineByLine = mutableListOf<String>()
    val inputStream = File(filename).inputStream()
    inputStream.bufferedReader().useLines { lines ->
        lines.forEach {
            lineByLine.add(it)
        }
    }
    return lineByLine
}
