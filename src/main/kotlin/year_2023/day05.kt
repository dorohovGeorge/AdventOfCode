package year_2023

import utils.println
import utils.readFile
import kotlin.system.measureTimeMillis

fun main() {
    val filename = "src/main/kotlin/year_2023/day05.txt"
    var lines: MutableList<String> = readFile(filename)
    lines = lines.filter { it != "" }.toMutableList()
    measureTimeMillis {
        getSeeds2(lines).println()
    }.println()

}

fun part_05_1(lines: MutableList<String>): Long {
    val seeds = getSeeds(lines[0])
    lines.removeAt(0)

    val seed_to_soil = lines.indexOf("seed-to-soil map:")
    val soil_to_fertilizer = lines.indexOf("soil-to-fertilizer map:")
    val fertilizer_to_water = lines.indexOf("fertilizer-to-water map:")
    val water_to_light = lines.indexOf("water-to-light map:")
    val light_to_temperature = lines.indexOf("light-to-temperature map:")
    val temperature_to_humidity = lines.indexOf("temperature-to-humidity map:")
    val humidity_to_location = lines.indexOf("humidity-to-location map:")

    val seed_to_soil_rules = parseRules(seed_to_soil, soil_to_fertilizer, lines)
    val soil_to_fertilizer_rules = parseRules(soil_to_fertilizer, fertilizer_to_water, lines)
    val fertilizer_to_water_rules = parseRules(fertilizer_to_water, water_to_light, lines)
    val water_to_light_rules = parseRules(water_to_light, light_to_temperature, lines)
    val light_to_temperature_rules = parseRules(light_to_temperature, temperature_to_humidity, lines)
    val temperature_to_humidity_rules = parseRules(temperature_to_humidity, humidity_to_location, lines)
    val humidity_to_location_rules = parseRules(humidity_to_location, lines.size, lines)

    var lowestLocation = Long.MAX_VALUE

    seeds.forEach { it ->
        val soil = seed_to_soil_rules.getRuleValue(it)
        val fertilize = soil_to_fertilizer_rules.getRuleValue(soil)
        val water = fertilizer_to_water_rules.getRuleValue(fertilize)
        val light = water_to_light_rules.getRuleValue(water)
        val temperature = light_to_temperature_rules.getRuleValue(light)
        val humidity = temperature_to_humidity_rules.getRuleValue(temperature)
        val location = humidity_to_location_rules.getRuleValue(humidity)
        if (lowestLocation >= location) lowestLocation = location
    }
    return lowestLocation
}

fun getSeeds2(lines: MutableList<String>): Long {
    var tmp = lines[0]
    lines.removeAt(0)
    tmp = tmp.substring(tmp.indexOf(":") + 1, tmp.length)
    val list = mutableListOf<Long>()
    tmp.split(" ").forEach {
        if (it != "") {
            list.add(it.toLong())
        }
    }
    var resultList = mutableListOf<Long>()
    var skip = 0
    var lowestLocation = Long.MAX_VALUE
    list.forEachIndexed { index, l ->

        if (skip != 0) {
            var start = list[index - 1]
            var plus = l
            var end = plus + list[index - 1]
            plus /= 100

            for (i in 1..100) {
                resultList.addAll(start + (i - 1) * plus..<start + i * plus)
                var location = part_05_2(resultList, lines)
                if (lowestLocation >= location) lowestLocation = location
                resultList.clear()
            }

            lowestLocation.println()
            skip = 0
        } else {
            skip = 1
        }

    }
    return lowestLocation
}

fun part_05_2(seed: List<Long>, lines: MutableList<String>): Long {
    val seeds = seed

    val seed_to_soil = lines.indexOf("seed-to-soil map:")
    val soil_to_fertilizer = lines.indexOf("soil-to-fertilizer map:")
    val fertilizer_to_water = lines.indexOf("fertilizer-to-water map:")
    val water_to_light = lines.indexOf("water-to-light map:")
    val light_to_temperature = lines.indexOf("light-to-temperature map:")
    val temperature_to_humidity = lines.indexOf("temperature-to-humidity map:")
    val humidity_to_location = lines.indexOf("humidity-to-location map:")

    val seed_to_soil_rules = parseRules(seed_to_soil, soil_to_fertilizer, lines)
    val soil_to_fertilizer_rules = parseRules(soil_to_fertilizer, fertilizer_to_water, lines)
    val fertilizer_to_water_rules = parseRules(fertilizer_to_water, water_to_light, lines)
    val water_to_light_rules = parseRules(water_to_light, light_to_temperature, lines)
    val light_to_temperature_rules = parseRules(light_to_temperature, temperature_to_humidity, lines)
    val temperature_to_humidity_rules = parseRules(temperature_to_humidity, humidity_to_location, lines)
    val humidity_to_location_rules = parseRules(humidity_to_location, lines.size, lines)

    var lowestLocation = Long.MAX_VALUE

    seeds.forEach { it ->
        val soil = seed_to_soil_rules.getRuleValue(it)
        val fertilize = soil_to_fertilizer_rules.getRuleValue(soil)
        val water = fertilizer_to_water_rules.getRuleValue(fertilize)
        val light = water_to_light_rules.getRuleValue(water)
        val temperature = light_to_temperature_rules.getRuleValue(light)
        val humidity = temperature_to_humidity_rules.getRuleValue(temperature)
        val location = humidity_to_location_rules.getRuleValue(humidity)
        if (lowestLocation >= location) lowestLocation = location
    }
    return lowestLocation
}

fun MutableList<Rule>.getRuleValue(value: Long): Long {
    this.forEach {
        if (it.source <= value && value < it.source + it.range) {
            return value - (it.source - it.destination)
        }
    }
    return value
}

fun parseRules(startIndex: Int, endIndex: Int, list: List<String>): MutableList<Rule> {
    var res = mutableListOf<Rule>()
    list.subList(startIndex + 1, endIndex).forEach { s ->
        res.add(s.parseRule())
    }
    return res
}

fun String.parseRule(): Rule {
    val list = this.split(' ')
    return Rule(list[0].toLong(), list[1].toLong(), list[2].toLong())
}

fun getSeeds(str: String): List<Long> {
    var tmp = str
    tmp = tmp.substring(tmp.indexOf(":") + 1, tmp.length)
    val list = mutableListOf<Long>()
    tmp.split(" ").forEach {
        if (it != "") {
            list.add(it.toLong())
        }
    }
    return list
}


data class Rule(
    var destination: Long, var source: Long, var range: Long
) {
    fun findNum(value: Long): Long {
        return value - (source - destination)
    }
}