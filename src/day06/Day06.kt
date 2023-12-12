package day06

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        var result = 1

        val times = input[0].substringAfter("Time:").trim().split(Regex("\\s+")).map(String::toInt)
        val distances = input[1].substringAfter("Distance:").trim().split(Regex("\\s+")).map(String::toInt)

        times.forEachIndexed { index, time ->
            val distance = distances[index]
            var winningCounter = 0
            for (i in 0..time) {
                val resultingDistance = i * (time - i)
                if (resultingDistance > distance) {
                    winningCounter++
                }
            }
            result *= winningCounter
        }

        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0

        val time = input[0].substringAfter("Time:").filter { !it.isWhitespace() }.toLong()
        val distance = input[1].substringAfter("Distance:").filter { !it.isWhitespace() }.toLong()

        for (i in 0..time) {
            val resultingDistance = i * (time - i)
            if (resultingDistance > distance) {
                result++
            }
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(pkg = "day06", name = "Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput(pkg = "day06", name = "Day06")
    println(part1(input))
    println(part2(input))
}
