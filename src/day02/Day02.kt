package day02

import readInput

data class Set(val red: Int, val green: Int, val blue: Int)
data class Game(val id: Int, val sets: List<Set>)

fun String.game(): Game {
    val gameRegex = Regex("Game (\\d+)\$")
    val input = this.split(':')
    val (gameId) = gameRegex.find(input[0])!!.destructured
    val sets = mutableListOf<Set>()
    input[1].split(';').forEach { set ->
        var (red, green, blue) = listOf(0, 0, 0)
        set.split(',').forEach { cube ->
            val cubeValues = cube.trim().split(' ')
            when (cubeValues[1]) {
                "red" -> red = cubeValues[0].toInt()
                "green" -> green = cubeValues[0].toInt()
                "blue" -> blue = cubeValues[0].toInt()
            }
        }
        sets.add(Set(red, green, blue))
    }
    return Game(gameId.toInt(), sets)
}

fun Set.isPossible(givenSet: Set): Boolean =
    this.red <= givenSet.red && this.green <= givenSet.green && this.blue <= givenSet.blue

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        val givenSet = Set(red = 12, green = 13, blue = 14)
        input.forEach {
            val game = it.game()
            var gameIsPossible = true
            for (set in game.sets) {
                if (!set.isPossible(givenSet)) {
                    gameIsPossible = false
                    break
                }
            }
            if (gameIsPossible) sum += game.id
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val game = it.game()
            val minRed = game.sets.maxOf { it.red }
            val minGreen = game.sets.maxOf { it.green }
            val minBlue = game.sets.maxOf { it.blue }
            sum += minRed * minGreen * minBlue
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(pkg = "day02", name = "Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput(pkg = "day02", name = "Day02")
    println(part1(input))
    println(part2(input))
}
