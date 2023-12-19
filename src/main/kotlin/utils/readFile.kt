package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

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

fun readInput(name: String): List<String> = Path(name).readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun readInputTxt(name: String) = Path(name).readText()
