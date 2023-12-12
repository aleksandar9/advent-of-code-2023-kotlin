package day09

import readInput

fun main() {

    fun String.enrichHistory(): List<MutableList<Int>> {
        val grid = mutableListOf<MutableList<Int>>()
        grid.add(this.split(' ').map(String::toInt).toMutableList())

        var index = 0
        while (true) {
            val newList = mutableListOf<Int>()
            val previousList = grid[index]
            for (i in 0 until previousList.size - 1) {
                newList.add(previousList[i + 1] - previousList[i])
            }
            grid.add(newList)

            if (newList.all { it == 0 }) {
                break
            } else {
                index++
            }
        }

        return grid
    }

    fun String.extrapolateNextValue(): Int {
        val grid = enrichHistory()
        grid.last().add(0)

        for (i in grid.size - 1 downTo 1) {
            val newValue = grid[i - 1].last() + grid[i].last()
            grid[i - 1].add(newValue)
        }

        return grid[0].last()
    }

    fun part1(input: List<String>): Int {
        var sum = 0

        input.forEach {
            sum += it.extrapolateNextValue()
        }

        return sum
    }

    fun String.extrapolatePreviousValue(): Int {
        val grid = enrichHistory()
        grid.last().add(0, 0)

        for (i in grid.size - 1 downTo 1) {
            val newValue = grid[i - 1].first() - grid[i].first()
            grid[i - 1].add(0, newValue)
        }

        return grid[0].first()
    }

    fun part2(input: List<String>): Int {
        var sum = 0

        input.forEach {
            sum += it.extrapolatePreviousValue()
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(pkg = "day09", name = "Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput(pkg = "day09", name = "Day09")
    println(part1(input))
    println(part2(input))
}
