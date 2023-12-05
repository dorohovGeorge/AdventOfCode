package year_2023

import utils.println
import utils.readFile

fun main() {
    val filename = "src/main/kotlin/year_2023/day03.txt"
    val lines: MutableList<String> = readFile(filename)
//    lines.println()
    part_03_2(lines).println()


}

//    println("*".containsSpecial())
var twoNumInLine = false
var first = -1
var second = -1
fun String.searchNumber(index: Int): Int {
    var strNum = ""
    if (this[index] == '.' || this[index] == '*') {
        val firstNum = index - 1
        val secondNum = index + 1

        //back
        var backNum = ""
        for (i in firstNum downTo 0) {
            if (!this[i].isDigit()) {
                break
            }
            backNum += this[i]
        }
        //forward
        var forwardNum = ""
        for (i in secondNum..<this.length) {
            if (!this[i].isDigit()) {
                break
            }
            forwardNum += this[i]
        }
        if (this[firstNum].isDigit() && this[secondNum].isDigit()) {
            twoNumInLine = true
            first = forwardNum.toInt()
            second = backNum.reversed().toInt()
            return 0
        } else {
            strNum += backNum.reversed() + forwardNum
        }

    } else {
        val firstNum = index
        val secondNum = index + 1

        //back
        var backNum = ""
        for (i in firstNum downTo 0) {
            if (!this[i].isDigit()) {
                break
            }
            backNum += this[i]
        }
        //forward
        var forwardNum = ""
        for (i in secondNum..<this.length) {
            if (!this[i].isDigit()) {
                break
            }
            forwardNum += this[i]
        }
        strNum += backNum.reversed() + forwardNum

    }
//    strNum.println()
    return strNum.toInt()
}

fun part_03_1(list: List<String>): Int {
    val squaredList = list.toMutableList()
    return analyzer(squaredList)
}

fun part_03_2(list: List<String>): Int {
    val squaredList = list.toMutableList()
    return analyzer2(squaredList)
}

fun searchNum(line: String, star: Star, index: Int, startPosNum: Int, endPosNum: Int, indexInLine: Int): Star {
    star.line = index

    if (star.firstNum == -1) {
        star.firstNum = line.searchNumber(indexInLine)
        if (twoNumInLine) {
            star.firstNum = first
            star.secondNum = second
            twoNumInLine = false
        }
//        star.firstNum = substr(line, startPosNum, endPosNum).toClearInt()
        star.indexInLine = indexInLine
    } else {
        if (star.line == index && star.indexInLine == indexInLine) {
            star.secondNum = line.searchNumber(indexInLine)
        } else {

            return searchNum(line, Star(), index, startPosNum, endPosNum, indexInLine)
        }
    }

    return star
}

fun analyzer2(list: List<String>): Int {
    var startPosNum = -1
    var endPosNum = -1
    var readNum = false
    var alreadyCounted = false
    var countRightNumber = 0
    var star = Star()
    list.forEachIndexed { bigIndex, s ->
        run {
            s.forEachIndexed { index, c ->
                if (c.isStar()) {
                    if (startPosNum == -1) {
                        startPosNum = index
                        readNum = true
                    }
                }
                if (!c.isStar() || (readNum && index == s.length - 1)) {
                    if (startPosNum != -1) {
                        endPosNum = index
                        /*
                        * Лево верх
                        * */
                        if (bigIndex == 0 && startPosNum == 0 && !alreadyCounted) {
                            val line = substr(s, startPosNum, endPosNum + 1)
                            val lowLine = substr(list[bigIndex + 1], startPosNum, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial()) {
                                if (line.containsDigital()) {

                                    star = searchNum(
                                        s,
                                        star,
                                        bigIndex,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (lowLine.containsDigital()) {

                                    star = searchNum(
                                        list[bigIndex + 1],
                                        star,
                                        bigIndex + 1,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (star.firstNum != -1 && star.secondNum != -1) {
                                    countRightNumber += star.firstNum * star.secondNum
                                    star = Star()
                                }
                            }
                        }
                        /*
                        * Середина верх
                        * */
                        if (bigIndex == 0 && endPosNum + 1 != s.length && !alreadyCounted) {
                            val line = substr(s, startPosNum - 1, endPosNum + 1)
                            val lowLine = substr(list[bigIndex + 1], startPosNum - 1, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial()) {
                                if (line.containsDigital()) {
                                    star = searchNum(
                                        s,
                                        star,
                                        bigIndex,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (lowLine.containsDigital()) {

                                    star = searchNum(
                                        list[bigIndex + 1],
                                        star,
                                        bigIndex + 1,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (star.firstNum != -1 && star.secondNum != -1) {
                                    countRightNumber += star.firstNum * star.secondNum
                                    star = Star()
                                }
                            }
                        }
                        /*
                        * Право верх
                        * */
                        if (bigIndex == 0 && endPosNum + 1 == s.length && !alreadyCounted) {
                            val line = substr(s, startPosNum - 1, endPosNum + 1)
                            val lowLine = substr(list[bigIndex + 1], startPosNum - 1, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial()) {
                                if (line.containsDigital()) {

                                    star = searchNum(
                                        s,
                                        star,
                                        bigIndex,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (lowLine.containsDigital()) {

                                    star = searchNum(
                                        list[bigIndex + 1],
                                        star,
                                        bigIndex + 1,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (star.firstNum != -1 && star.secondNum != -1) {
                                    countRightNumber += star.firstNum * star.secondNum
                                    star = Star()
                                }
                            }
                        }

                        /*
                        * Лево низ
                        * */
                        if (bigIndex == list.size - 1 && startPosNum == 0 && !alreadyCounted) {
                            val line = substr(s, startPosNum, endPosNum + 1)
                            val lowLine = substr(list[bigIndex - 1], startPosNum, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial()) {
                                if (line.containsDigital()) {

                                    star = searchNum(
                                        s,
                                        star,
                                        bigIndex,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (lowLine.containsDigital()) {
                                    star = searchNum(
                                        list[bigIndex - 1],
                                        star,
                                        bigIndex - 1,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (star.firstNum != -1 && star.secondNum != -1) {
                                    countRightNumber += star.firstNum * star.secondNum
                                    star = Star()
                                }
                            }
                        }
                        /*
                        * Середина низ
                        * */
                        if (bigIndex == list.size - 1 && endPosNum + 1 != s.length && !alreadyCounted) {
                            val line = substr(s, startPosNum - 1, endPosNum + 1)
                            val lowLine = substr(list[bigIndex - 1], startPosNum - 1, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial()) {
                                if (line.containsDigital()) {

                                    star = searchNum(
                                        s,
                                        star,
                                        bigIndex,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (lowLine.containsDigital()) {

                                    star = searchNum(
                                        list[bigIndex - 1],
                                        star,
                                        bigIndex - 1,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (star.firstNum != -1 && star.secondNum != -1) {
                                    countRightNumber += star.firstNum * star.secondNum
                                    star = Star()
                                }
                            }
                        }
                        /*
                        * Право низ
                        * */
                        if (bigIndex == list.size - 1 && endPosNum + 1 == s.length && !alreadyCounted) {
                            val line = substr(s, startPosNum - 1, endPosNum + 1)
                            val lowLine = substr(list[bigIndex - 1], startPosNum - 1, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial()) {
                                if (line.containsDigital()) {
                                    star = searchNum(
                                        s,
                                        star,
                                        bigIndex,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (lowLine.containsDigital()) {
                                    star = searchNum(
                                        list[bigIndex - 1],
                                        star,
                                        bigIndex - 1,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (star.firstNum != -1 && star.secondNum != -1) {
                                    countRightNumber += star.firstNum * star.secondNum
                                    star = Star()
                                }
                            }
                        }
                        /*
                        * Лево середина
                        * */
                        if (bigIndex != 0 && startPosNum == 0 && !alreadyCounted) {
                            val line = substr(s, startPosNum, endPosNum + 1)
                            val lowLine = substr(list[bigIndex + 1], startPosNum, endPosNum + 1)
                            val highLine = substr(list[bigIndex - 1], startPosNum, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial() || highLine.containsSpecial()) {
                                if (line.containsDigital()) {

                                    star = searchNum(
                                        s,
                                        star,
                                        bigIndex,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (lowLine.containsDigital()) {
                                    star = searchNum(
                                        list[bigIndex + 1],
                                        star,
                                        bigIndex + 1,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (highLine.containsDigital()) {
                                    star = searchNum(
                                        list[bigIndex - 1],
                                        star,
                                        bigIndex - 1,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (star.firstNum != -1 && star.secondNum != -1) {
                                    countRightNumber += star.firstNum * star.secondNum
                                    star = Star()
                                }
                            }
                        }
                        /*
                        * Середина
                        * */
                        if (bigIndex != 0 && endPosNum + 1 != s.length && !alreadyCounted) {
                            val line = substr(s, startPosNum - 1, endPosNum + 1)
                            val lowLine = substr(list[bigIndex + 1], startPosNum - 1, endPosNum + 1)
                            val highLine = substr(list[bigIndex - 1], startPosNum - 1, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial() || highLine.containsSpecial()) {
                                if (line.containsDigital()) {
                                    star = searchNum(
                                        s,
                                        star,
                                        bigIndex,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (lowLine.containsDigital()) {
                                    star = searchNum(
                                        list[bigIndex + 1],
                                        star,
                                        bigIndex + 1,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (highLine.containsDigital()) {
                                    star = searchNum(
                                        list[bigIndex - 1],
                                        star,
                                        bigIndex - 1,
                                        startPosNum,
                                        endPosNum,
                                        startPosNum
                                    )
                                }
                                if (star.firstNum != -1 && star.secondNum != -1) {
                                    countRightNumber += star.firstNum * star.secondNum
                                    star = Star()
                                }
                            }
                        }
                        /*
                        * Право середина
                        * */
                        if (bigIndex != 0 && endPosNum + 1 == s.length && !alreadyCounted) {
                            val line = substr(s, startPosNum - 1, endPosNum + 1)
                            val lowLine = substr(list[bigIndex + 1], startPosNum - 1, endPosNum + 1)
                            val highLine = substr(list[bigIndex - 1], startPosNum - 1, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial() || highLine.containsSpecial()) {
                                if (line.containsDigital()) {
                                    star = searchNum(
                                        s,
                                        star,
                                        bigIndex,
                                        startPosNum,
                                        endPosNum + 1,
                                        startPosNum
                                    )
                                }
                                if (lowLine.containsDigital()) {
                                    star = searchNum(
                                        list[bigIndex + 1],
                                        star,
                                        bigIndex + 1,
                                        startPosNum,
                                        endPosNum + 1,
                                        startPosNum
                                    )
                                }
                                if (highLine.containsDigital()) {
                                    star = searchNum(
                                        list[bigIndex - 1],
                                        star,
                                        bigIndex - 1,
                                        startPosNum,
                                        endPosNum + 1,
                                        startPosNum
                                    )
                                }
                                if (star.firstNum != -1 && star.secondNum != -1) {
                                    countRightNumber += star.firstNum * star.secondNum
                                    star = Star()
                                }
                            }
                        }

                        startPosNum = -1
                        endPosNum = -1
                        readNum = false
                        alreadyCounted = false
                    }
                }
            }
        }
    }
    return countRightNumber
}


fun analyzer(list: List<String>): Int {
    var startPosNum = -1
    var endPosNum = -1
    var readNum = false
    var alreadyCounted = false
    var countRightNumber = 0
    list.forEachIndexed { bigIndex, s ->
        run {
            s.forEachIndexed { index, c ->
                if (c.isDigit()) {
                    if (startPosNum == -1) {
                        startPosNum = index
                        readNum = true
                    }
                }
                if (!c.isDigit() || (readNum && index == s.length - 1)) {
                    if (startPosNum != -1) {
                        endPosNum = index
                        /*
                        * Лево верх
                        * */
                        if (bigIndex == 0 && startPosNum == 0 && !alreadyCounted) {
                            val line = substr(s, startPosNum, endPosNum + 1)
                            val lowLine = substr(list[bigIndex + 1], startPosNum, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial()) {
                                countRightNumber += substr(s, startPosNum, endPosNum).toClearInt()
                            }
                        }
                        /*
                        * Середина верх
                        * */
                        if (bigIndex == 0 && endPosNum + 1 != s.length && !alreadyCounted) {
                            val line = substr(s, startPosNum - 1, endPosNum + 1)
                            val lowLine = substr(list[bigIndex + 1], startPosNum - 1, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial()) {
                                countRightNumber += substr(s, startPosNum, endPosNum).toClearInt()
                            }
                        }
                        /*
                        * Право верх
                        * */
                        if (bigIndex == 0 && endPosNum + 1 == s.length && !alreadyCounted) {
                            val line = substr(s, startPosNum - 1, endPosNum + 1)
                            val lowLine = substr(list[bigIndex + 1], startPosNum - 1, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial()) {
                                countRightNumber += substr(s, startPosNum, endPosNum + 1).toClearInt()
                            }
                        }

                        /*
                        * Лево низ
                        * */
                        if (bigIndex == list.size - 1 && startPosNum == 0 && !alreadyCounted) {
                            val line = substr(s, startPosNum, endPosNum + 1)
                            val lowLine = substr(list[bigIndex - 1], startPosNum, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial()) {
                                countRightNumber += substr(s, startPosNum, endPosNum).toClearInt()
                            }
                        }
                        /*
                        * Середина низ
                        * */
                        if (bigIndex == list.size - 1 && endPosNum + 1 != s.length && !alreadyCounted) {
                            val line = substr(s, startPosNum - 1, endPosNum + 1)
                            val lowLine = substr(list[bigIndex - 1], startPosNum - 1, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial()) {
                                countRightNumber += substr(s, startPosNum, endPosNum).toClearInt()
                            }
                        }
                        /*
                        * Право низ
                        * */
                        if (bigIndex == list.size - 1 && endPosNum + 1 == s.length && !alreadyCounted) {
                            val line = substr(s, startPosNum - 1, endPosNum + 1)
                            val lowLine = substr(list[bigIndex - 1], startPosNum - 1, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial()) {
                                countRightNumber += substr(s, startPosNum, endPosNum + 1).toClearInt()
                            }
                        }
                        /*
                        * Лево середина
                        * */
                        if (bigIndex != 0 && startPosNum == 0 && !alreadyCounted) {
                            val line = substr(s, startPosNum, endPosNum + 1)
                            val highLine = substr(list[bigIndex - 1], startPosNum, endPosNum + 1)
                            val lowLine = substr(list[bigIndex + 1], startPosNum, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial() || highLine.containsSpecial()) {
                                countRightNumber += substr(s, startPosNum, endPosNum).toClearInt()
                            }
                        }
                        /*
                        * Середина
                        * */
                        if (bigIndex != 0 && endPosNum + 1 != s.length && !alreadyCounted) {
                            val line = substr(s, startPosNum - 1, endPosNum + 1)
                            val highLine = substr(list[bigIndex - 1], startPosNum - 1, endPosNum + 1)
                            val lowLine = substr(list[bigIndex + 1], startPosNum - 1, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial() || highLine.containsSpecial()) {
                                countRightNumber += substr(s, startPosNum, endPosNum).toClearInt()
                            }
                        }
                        /*
                        * Право середина
                        * */
                        if (bigIndex != 0 && endPosNum + 1 == s.length && !alreadyCounted) {
                            val line = substr(s, startPosNum - 1, endPosNum + 1)
                            val highLine = substr(list[bigIndex - 1], startPosNum - 1, endPosNum + 1)
                            val lowLine = substr(list[bigIndex + 1], startPosNum - 1, endPosNum + 1)
                            alreadyCounted = true
                            if (line.containsSpecial() || lowLine.containsSpecial() || highLine.containsSpecial()) {
                                countRightNumber += substr(s, startPosNum, endPosNum + 1).toClearInt()
                            }
                        }

                        startPosNum = -1
                        endPosNum = -1
                        readNum = false
                        alreadyCounted = false
                    }
                }
            }
        }
    }
    return countRightNumber
}

fun String.posStar(first: Int, second: Int): Int {

    this.forEachIndexed { index, c ->
        if (index >= first && index <= second) {
            if (c == '*') return index
        }
    }
    return 0
}

fun String.posStar(): Int {
    return this.indexOf("*")
}


fun Char.isStar(): Boolean {
    return this == '*'
}

fun String.containsDigital(): Boolean {
    this.forEach {
        if (it.isDigit()) return true
    }
    return false
}

fun String.containsSpecial(): Boolean {
    this.forEach {
        if ("!$%^@&*()-_+|~-=`{}[]#:;'<>?,/".contains(it)) return true
    }
    return false
}

fun substr(line: String, startIndex: Int, endIndex: Int): String = line.substring(startIndex, endIndex)

fun String.toClearInt(): Int {
    return this.filter { it.isDigit() }.toInt()
}
