package year_2023

import utils.println
import utils.readFile

private var strengthCard = hashMapOf<Char, Int>()

fun main() {
    val filename = "src/main/resources/day07.txt"
    val lines: MutableList<String> = readFile(filename)
    strengthCard.put('A', 13)
    strengthCard.put('K', 12)
    strengthCard.put('Q', 11)
    strengthCard.put('T', 9)
    strengthCard.put('9', 8)
    strengthCard.put('8', 7)
    strengthCard.put('7', 6)
    strengthCard.put('6', 5)
    strengthCard.put('5', 4)
    strengthCard.put('4', 3)
    strengthCard.put('3', 2)
    strengthCard.put('2', 1)
    strengthCard.put('J', 0)

    lines.println()
    parse2(lines).println()
    "AJJA2".findStrongestChar().println()
    var hand = Hand("8J9KJ", Power.ONE_PAIR)
    hand.transformCardWithJoker()
    hand.println()
//    "KTJJT".findPowerfulCharInList().println()
}


enum class Power(var power: Int) {
    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIR(3),
    THREE_KIND(4),
    FULL_HOUSE(5),
    FOUR_KIND(6),
    FIVE_KIND(7)
}

fun String.findStrongestChar(): Char {
    var strongest = 'J'
    this.forEachIndexed { _, c ->
        if (strengthCard[c]!! > strengthCard[strongest]!!) {
            strongest = c
        }
    }
    return strongest
}

fun String.findRepeatableChars(): List<Char> {
    val map = hashMapOf<Char, Int>()
    this.forEach {
        if (map.containsKey(it)) map[it] = map[it]!! + 1
        else map[it] = 1
    }
    if (map.containsKey('J')) {
        if (map['J']!! > 1) {
            return map.filter { it.key != 'J' || (it.value > 1 && it.key != 'J') }.keys.toList()
        }
    }
    return map.filter { it.value != 1 }.keys.toList()
}

fun String.findPowerfulCharInList(): Char {
    var list = this.findRepeatableChars()
    val charComporator = Comparator { char1: Char, char2: Char -> strengthCard[char1]!! - strengthCard[char2]!! }
    list = list.sortedWith(charComporator).reversed()
    return list[0]
}

fun Hand.transformCardWithJoker() {
    when (this.power) {
        Power.HIGH_CARD -> this.power = Hand(this.combination.replace('J', this.combination.findStrongestChar())).power
        Power.ONE_PAIR -> this.power =
            Hand(this.combination.replace('J', this.combination.findPowerfulCharInList())).power

        Power.TWO_PAIR -> this.power =
            Hand(this.combination.replace('J', this.combination.findPowerfulCharInList())).power

        Power.THREE_KIND -> this.power =
            Hand(this.combination.replace('J', this.combination.findPowerfulCharInList())).power

        Power.FULL_HOUSE -> this.power =
            Hand(this.combination.replace('J', this.combination.findPowerfulCharInList())).power

        Power.FOUR_KIND -> this.power =
            Hand(this.combination.replace('J', this.combination.findPowerfulCharInList())).power

        Power.FIVE_KIND -> this.power = Hand(this.combination.replace('J', this.combination.findStrongestChar())).power
    }
}

fun compareHands(hand1: Hand, hand2: Hand): Int {
    return if (hand1.power == hand2.power) {
        compareEqualHand(hand1.combination, hand2.combination)
    } else {
        var tmp1 = hand1.copy()
        tmp1.transformCardWithJoker()
        var tmp2 = hand2.copy()
        tmp2.transformCardWithJoker()
        if (tmp1.power == tmp2.power) {
            return compareEqualHand(hand1.combination, hand2.combination)
        }
        if (tmp1.power > tmp2.power) return 1
        else return -1
    }
}

fun compareEqualHand(str1: String, str2: String): Int {
    str1.forEachIndexed { index, c ->
        if (c != str2[index]) {
            return strengthCard[c]!! - strengthCard[str2[index]]!!
        }
    }
    return 0
}

fun Hand.getStrengthOfCombination() {
    if (this.combination.isHighCard()) this.power = Power.HIGH_CARD
    if (this.combination.isOnePair()) this.power = Power.ONE_PAIR
    if (this.combination.isTwoPair()) this.power = Power.TWO_PAIR
    if (this.combination.isThreeKind()) this.power = Power.THREE_KIND
    if (this.combination.isFullHouse()) this.power = Power.FULL_HOUSE
    if (this.combination.isFourKind()) this.power = Power.FOUR_KIND
    if (this.combination.isFiveKind()) this.power = Power.FIVE_KIND
}

fun String.getStrenght(): Power {
    if (this.isHighCard()) return Power.HIGH_CARD
    if (this.isOnePair()) return Power.ONE_PAIR
    if (this.isTwoPair()) return Power.TWO_PAIR
    if (this.isThreeKind()) return Power.THREE_KIND
    if (this.isFullHouse()) return Power.FULL_HOUSE
    if (this.isFourKind()) return Power.FOUR_KIND
    if (this.isFiveKind()) return Power.FIVE_KIND
    return Power.HIGH_CARD
}

fun String.isFiveKind(): Boolean {
    return this.toHashSet().size == 1
}

fun String.isFourKind(): Boolean {
    if (this.toHashSet().size == this.length - 3) {
        val map = hashMapOf<Char, Int>()
        this.forEach {
            if (map.containsKey(it)) map[it] = map[it]!! + 1
            else map[it] = 1
        }
        return map.count {
            it.value == 4
        } == 1 && map.size == 2
    }
    return false
}

fun String.isFullHouse(): Boolean {
    if (this.toHashSet().size == this.length - 3) {
        val map = hashMapOf<Char, Int>()
        this.forEach {
            if (map.containsKey(it)) map[it] = map[it]!! + 1
            else map[it] = 1
        }
        return map.count {
            it.value == 3 || it.value == 2
        } == 2
    }
    return false
}

fun String.isThreeKind(): Boolean {
    if (this.toHashSet().size == this.length - 2) {
        val map = hashMapOf<Char, Int>()
        this.forEach {
            if (map.containsKey(it)) map[it] = map[it]!! + 1
            else map[it] = 1
        }
        return map.count {
            it.value == 3
        } == 1
    }
    return false
}

fun String.isTwoPair(): Boolean {
    if (this.toHashSet().size == this.length - 2) {
        val map = hashMapOf<Char, Int>()
        this.forEach {
            if (map.containsKey(it)) map[it] = map[it]!! + 1
            else map[it] = 1
        }
        return map.count {
            it.value == 2
        } == 2
    }
    return false
}

fun String.isOnePair(): Boolean {
    return this.toHashSet().size == this.length - 1
}

fun String.isHighCard(): Boolean {
    return this.toHashSet().size == this.length
}

fun parse2(lines: List<String>): Int {
    val hands = mutableListOf<CamelCard>()
    lines.forEach {
        val list = it.split(' ')
        val hand = Hand(list[0])
        val card = CamelCard(hand, list[1].toInt())
        hands.add(card)
    }
    val cardComporator = Comparator { card1: CamelCard, card2: CamelCard -> compareHands(card1.hand, card2.hand) }
    hands.sortWith(cardComporator)
    var bindCounter = 0
    hands.forEachIndexed { index, camelCard ->
        bindCounter += camelCard.bind * (index + 1)
    }
    hands.forEach {
        it.println()
    }
    return bindCounter
}

fun parse(lines: List<String>): Int {
    val hands = mutableListOf<CamelCard>()
    lines.forEach {
        val list = it.split(' ')
        hands.add(CamelCard(Hand(list[0]), list[1].toInt()))
    }
    val cardComporator = Comparator { card1: CamelCard, card2: CamelCard -> compareHands(card1.hand, card2.hand) }
    hands.sortWith(cardComporator)
    var bindCounter = 0
    hands.forEachIndexed { index, camelCard ->
        bindCounter += camelCard.bind * (index + 1)
    }
    hands.forEach {
        it.println()
    }
    return bindCounter
}

data class Hand(
    var combination: String,
    var power: Power
) {
    constructor(str: String) : this(str, str.getStrenght())
}

data class CamelCard(var hand: Hand, var bind: Int)