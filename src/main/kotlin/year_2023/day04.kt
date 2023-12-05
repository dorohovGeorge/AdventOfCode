package year_2023

import utils.println
import utils.readFile

fun main() {
    val filename = "src/main/kotlin/year_2023/day04.txt"
    val lines: MutableList<String> = readFile(filename)
    part_04_2(lines).println()
}

fun part_04_1(lines: List<String>): Int {
    var points = 0
    lines.forEach {
        points += parseCard(it)
    }
    return points
}

fun part_04_2(lines: List<String>): Int {
    val cards = mutableListOf<Card>()

    lines.forEachIndexed { index, s ->
        cards.add(Card(index + 1, s))
    }

    val mutableCars = cards.map { it }.toMutableList()
    var length = mutableCars.size
    var i = 0

    while (i < length) {
        val tempPoint = parseCard2(mutableCars[i].value)
        if (tempPoint != 0) {
            mutableCars.addAll(getCards(mutableCars[i], tempPoint, cards))
            length = mutableCars.size
        }
        i++
    }

    return mutableCars.size
}

fun getCards(card: Card, amount: Int, list: List<Card>): List<Card> {
    val start = card.id
    val end = start + amount
    val copyCards = mutableListOf<Card>()
    list.forEach {
        if (it.id in (start + 1)..end) {
            copyCards.add(it)
        }
    }
    return copyCards
}

fun parseCard2(line: String): Int {

    val str = line.substring(line.indexOf(":") + 1)
    val list = str.split('|')

    val rightNum = parseWriteNumbers(list[0])
    val checkableNum = parseNumbers(list[1])

    var cardPoint = 0

    checkableNum.forEach {
        if (rightNum.containsKey(it)) {
            cardPoint++
        }
    }

    return cardPoint
}

fun parseCard(line: String): Int {

    val str = line.substring(line.indexOf(":") + 1)
    val list = str.split('|')

    val rightNum = parseWriteNumbers(list[0])
    val checkableNum = parseNumbers(list[1])

    var cardPoint = 0

    checkableNum.forEach {
        if (rightNum.containsKey(it)) {
            if (cardPoint == 0) {
                cardPoint = 1
            } else {
                cardPoint *= 2
            }
        }
    }

    return cardPoint
}

fun parseWriteNumbers(s: String): Map<Int, String> {
    var tmp = s
    val map = hashMapOf<Int, String>()
    while (tmp.containsDigital()) {
        val num = tmp.substring(0, 3).trim().toInt()
        map.put(num, "")
        tmp = tmp.substring(3, tmp.length)
    }
    return map
}

fun parseNumbers(s: String): List<Int> {
    var tmp = s
    val list = arrayListOf<Int>()
    while (tmp.containsDigital()) {
        val num = tmp.substring(0, 3).trim().toInt()
        list.add(num)
        tmp = tmp.substring(3, tmp.length)
    }
    return list
}

data class Card(
    var id: Int,
    var value: String
)
