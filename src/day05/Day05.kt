package day05

import readInput

class Almanac() {

    constructor(seed: Long) : this() {
        this.seed = seed
        soil = seed
        fertilizer = seed
        water = seed
        light = seed
        temperature = seed
        humidity = seed
        location = seed
    }

    var seed: Long = 0L

    var soil: Long = 0L
        set(value) {
            field = value
            fertilizer = value
            water = value
            light = value
            temperature = value
            humidity = value
            location = value
        }

    var fertilizer: Long = 0L
        set(value) {
            field = value
            water = value
            light = value
            temperature = value
            humidity = value
            location = value
        }

    var water: Long = 0L
        set(value) {
            field = value
            light = value
            temperature = value
            humidity = value
            location = value
        }

    var light: Long = 0L
        set(value) {
            field = value
            temperature = value
            humidity = value
            location = value
        }

    var temperature: Long = 0L
        set(value) {
            field = value
            humidity = value
            location = value
        }

    var humidity: Long = 0L
        set(value) {
            field = value
            location = value
        }

    var location: Long = 0L

    override fun toString(): String {
        return "Seed: $seed, " +
            "Soil: $soil, " +
            "Fertilizer: $fertilizer, " +
            "Water: $water, " +
            "Light: $light, " +
            "Temperature: $temperature, " +
            "Humidity: $humidity, " +
            "Location: $location"
    }
}

fun main() {

    fun part1(input: List<String>): Long {
        val seeds = input[0].substring(7).split(' ').map {
            Almanac(it.toLong())
        }

        val a = input.indexOf("seed-to-soil map:")
        val b = input.indexOf("soil-to-fertilizer map:")
        val c = input.indexOf("fertilizer-to-water map:")
        val d = input.indexOf("water-to-light map:")
        val e = input.indexOf("light-to-temperature map:")
        val f = input.indexOf("temperature-to-humidity map:")
        val g = input.indexOf("humidity-to-location map:")

        // seed-to-soil map: 50 98 2
        // seed | soil
        //  98  |  50
        //  99  |  51
        for (i in a + 1 until b - 1) {
            val line = input[i].split(' ').map(String::toLong)
            val fromSoil = line[0]
            val toSoil = line[0] + line[2]
            val fromSeed = line[1]
            val toSeed = line[1] + line[2]
            seeds.forEach {
                if (it.seed in fromSeed until toSeed) {
                    val seedIndex = it.seed - fromSeed
                    val soil = fromSoil + seedIndex
                    it.soil = soil
                }
            }
        }

        // soil-to-fertilizer map: 0 15 37
        for (i in b + 1 until c - 1) {
            val line = input[i].split(' ').map(String::toLong)
            val fromFertilizer = line[0]
            val toFertilizer = line[0] + line[2]
            val fromSoil = line[1]
            val toSoil = line[1] + line[2]
            seeds.forEach {
                if (it.soil in fromSoil until toSoil) {
                    val soilIndex = it.soil - fromSoil
                    val fertilizer = fromFertilizer + soilIndex
                    it.fertilizer = fertilizer
                }
            }
        }

        // fertilizer-to-water map: 49 53 8
        for (i in c + 1 until d - 1) {
            val line = input[i].split(' ').map(String::toLong)
            val fromWater = line[0]
            val toWater = line[0] + line[2]
            val fromFertilizer = line[1]
            val toFertilizer = line[1] + line[2]
            seeds.forEach {
                if (it.fertilizer in fromFertilizer until toFertilizer) {
                    val fertilizerIndex = it.fertilizer - fromFertilizer
                    val water = fromWater + fertilizerIndex
                    it.water = water
                }
            }
        }

        // water-to-light map: 88 18 7
        for (i in d + 1 until e - 1) {
            val line = input[i].split(' ').map(String::toLong)
            val fromLight = line[0]
            val toLight = line[0] + line[2]
            val fromWater = line[1]
            val toWater = line[1] + line[2]
            seeds.forEach {
                if (it.water in fromWater until toWater) {
                    val waterIndex = it.water - fromWater
                    val light = fromLight + waterIndex
                    it.light = light
                }
            }
        }

        // light-to-temperature map: 45 77 23
        for (i in e + 1 until f - 1) {
            val line = input[i].split(' ').map(String::toLong)
            val fromTemperature = line[0]
            val toTemperature = line[0] + line[2]
            val fromLight = line[1]
            val toLight = line[1] + line[2]
            seeds.forEach {
                if (it.light in fromLight until toLight) {
                    val lightIndex = it.light - fromLight
                    val temperature = fromTemperature + lightIndex
                    it.temperature = temperature
                }
            }
        }

        // temperature-to-humidity map: 0 69 1
        for (i in f + 1 until g - 1) {
            val line = input[i].split(' ').map(String::toLong)
            val fromHumidity = line[0]
            val toHumidity = line[0] + line[2]
            val fromTemperature = line[1]
            val toTemperature = line[1] + line[2]
            seeds.forEach {
                if (it.temperature in fromTemperature until toTemperature) {
                    val temperatureIndex = it.temperature - fromTemperature
                    val humidity = fromHumidity + temperatureIndex
                    it.humidity = humidity
                }
            }
        }

        // humidity-to-location map: 60 56 37
        for (i in g + 1 until input.size) {
            val line = input[i].split(' ').map(String::toLong)
            val fromLocation = line[0]
            val toLocation = line[0] + line[2]
            val fromHumidity = line[1]
            val toHumidity = line[1] + line[2]
            seeds.forEach {
                if (it.humidity in fromHumidity until toHumidity) {
                    val humidityIndex = it.humidity - fromHumidity
                    val location = fromLocation + humidityIndex
                    it.location = location
                }
            }
        }

        return seeds.minOf(Almanac::location)
    }

    data class InputRange(val a: Long, val b: Long, val c: Long, val d: Long)

    fun Pair<Long, Long>.findMatch(inputRanges: List<InputRange>): List<Pair<Long, Long>> {
        val givenStart = this.first
        val givenEnd = this.second
        val result = mutableListOf<Pair<Long, Long>>()

        if (inputRanges.find { givenStart >= it.a && givenStart <= it.b } != null) {
            val range = inputRanges.find { givenStart >= it.a && givenStart <= it.b }!!
            val newStart = givenStart + (range.c - range.a)
            if (givenEnd > range.b) {
                result.add(Pair(newStart, range.d))
                val nextRange = Pair(range.b + 1, givenEnd)
                result.addAll(nextRange.findMatch(inputRanges))
            } else {
                val newEnd = givenEnd + (range.c - range.a)
                result.add(Pair(newStart, newEnd))
            }
        } else if (inputRanges.find { givenEnd >= it.a && givenEnd <= it.b } != null) {
            val range = inputRanges.find { givenEnd >= it.a && givenEnd <= it.b }!!
            result.add(Pair(givenStart, range.a - 1))
            val nextRange = Pair(range.a, givenEnd)
            result.addAll(nextRange.findMatch(inputRanges))
        } else {
            result.add(this)
        }

        return result
    }

    fun part2(input: List<String>): Long {
        val seedRanges = mutableListOf<Pair<Long, Long>>()
        val seeds = input[0].substring(7).split(' ').map(String::toLong)
        for (i in seeds.indices step 2) {
            seedRanges.add(Pair(seeds[i], seeds[i] + seeds[i + 1] - 1))
        }

        val a = input.indexOf("seed-to-soil map:")
        val b = input.indexOf("soil-to-fertilizer map:")
        val c = input.indexOf("fertilizer-to-water map:")
        val d = input.indexOf("water-to-light map:")
        val e = input.indexOf("light-to-temperature map:")
        val f = input.indexOf("temperature-to-humidity map:")
        val g = input.indexOf("humidity-to-location map:")

        // Seed to Soil

        val seedToSoil = mutableListOf<InputRange>()
        for (i in a + 1 until b - 1) {
            val line = input[i].split(' ').map(String::toLong)
            seedToSoil.add(InputRange(line[1], line[1] + line[2] - 1, line[0], line[0] + line[2] - 1))
        }
        seedToSoil.sortBy { it.a }

        val soilRanges = mutableListOf<Pair<Long, Long>>()
        seedRanges.forEach {
            soilRanges.addAll(it.findMatch(seedToSoil))
        }
        soilRanges.sortBy { it.first }

        // Soil to Fertilizer

        val soilToFertilizer = mutableListOf<InputRange>()
        for (i in b + 1 until c - 1) {
            val line = input[i].split(' ').map(String::toLong)
            soilToFertilizer.add(InputRange(line[1], line[1] + line[2] - 1, line[0], line[0] + line[2] - 1))
        }
        soilToFertilizer.sortBy { it.a }

        val fertilizerRanges = mutableListOf<Pair<Long, Long>>()
        soilRanges.forEach {
            fertilizerRanges.addAll(it.findMatch(soilToFertilizer))
        }
        fertilizerRanges.sortBy { it.first }

        // Fertilizer to Water

        val fertilizerToWater = mutableListOf<InputRange>()
        for (i in c + 1 until d - 1) {
            val line = input[i].split(' ').map(String::toLong)
            fertilizerToWater.add(InputRange(line[1], line[1] + line[2] - 1, line[0], line[0] + line[2] - 1))
        }
        fertilizerToWater.sortBy { it.a }

        val waterRanges = mutableListOf<Pair<Long, Long>>()
        fertilizerRanges.forEach {
            waterRanges.addAll(it.findMatch(fertilizerToWater))
        }
        waterRanges.sortBy { it.first }

        // Water to Light

        val waterToLight = mutableListOf<InputRange>()
        for (i in d + 1 until e - 1) {
            val line = input[i].split(' ').map(String::toLong)
            waterToLight.add(InputRange(line[1], line[1] + line[2] - 1, line[0], line[0] + line[2] - 1))
        }
        waterToLight.sortBy { it.a }

        val lightRanges = mutableListOf<Pair<Long, Long>>()
        waterRanges.forEach {
            lightRanges.addAll(it.findMatch(waterToLight))
        }
        lightRanges.sortBy { it.first }

        // Light to Temperature

        val lightToTemperature = mutableListOf<InputRange>()
        for (i in e + 1 until f - 1) {
            val line = input[i].split(' ').map(String::toLong)
            lightToTemperature.add(InputRange(line[1], line[1] + line[2] - 1, line[0], line[0] + line[2] - 1))
        }
        lightToTemperature.sortBy { it.a }

        val temperatureRanges = mutableListOf<Pair<Long, Long>>()
        lightRanges.forEach {
            temperatureRanges.addAll(it.findMatch(lightToTemperature))
        }
        temperatureRanges.sortBy { it.first }

        // Temperature to Humidity

        val temperatureToHumidity = mutableListOf<InputRange>()
        for (i in f + 1 until g - 1) {
            val line = input[i].split(' ').map(String::toLong)
            temperatureToHumidity.add(InputRange(line[1], line[1] + line[2] - 1, line[0], line[0] + line[2] - 1))
        }
        temperatureToHumidity.sortBy { it.a }

        val humidityRanges = mutableListOf<Pair<Long, Long>>()
        temperatureRanges.forEach {
            humidityRanges.addAll(it.findMatch(temperatureToHumidity))
        }
        humidityRanges.sortBy { it.first }

        // Humidity to Location

        val humidityToLocation = mutableListOf<InputRange>()
        for (i in g + 1 until input.size) {
            val line = input[i].split(' ').map(String::toLong)
            humidityToLocation.add(InputRange(line[1], line[1] + line[2] - 1, line[0], line[0] + line[2] - 1))
        }
        humidityToLocation.sortBy { it.a }

        val locationRanges = mutableListOf<Pair<Long, Long>>()
        humidityRanges.forEach {
            locationRanges.addAll(it.findMatch(humidityToLocation))
        }
        locationRanges.sortBy { it.first }

        return locationRanges[0].first
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(pkg = "day05", name = "Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput(pkg = "day05", name = "Day05")
    println(part1(input))
    println(part2(input))
}
