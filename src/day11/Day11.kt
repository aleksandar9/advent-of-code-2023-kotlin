package day11

import readInput
import kotlin.math.abs

fun main() {

    fun Pair<Int, Int>.distanceTo(pair: Pair<Int, Int>): Int {
        return abs(this.first - pair.first) + abs(this.second - pair.second)
    }

    fun List<List<Char>>.expand(): List<List<Char>> {
        val expandedUniverse = mutableListOf<MutableList<Char>>()
        this.forEach { row ->
            if (row.all { it == '.' }) {
                expandedUniverse.add(row.toMutableList())
                expandedUniverse.add(row.toMutableList())
            } else {
                expandedUniverse.add(row.toMutableList())
            }
        }

        val emptyColumns = this.indices
            .map { columnIndex -> this.map { it[columnIndex] } }
            .mapIndexedNotNull { index, column ->
                if (column.all { it == '.' }) {
                    index
                } else {
                    null
                }
            }

        expandedUniverse.forEach { row ->
            emptyColumns.forEachIndexed { index, emptyColumnIndex ->
                row.add(emptyColumnIndex + index, '.')
            }
        }

        return expandedUniverse
    }

    fun part1(input: List<String>): Int {
        var sum = 0

        val expandedUniverse = input.map(String::toList).expand()

        val galaxies = mutableListOf<Pair<Int, Int>>()
        expandedUniverse.forEachIndexed { x, row ->
            row.forEachIndexed { y, char ->
                if (char == '#') {
                    galaxies.add(Pair(x, y))
                }
                print(char)
            }
            println()
        }

        galaxies.forEachIndexed { index, galaxy ->
            for (i in index + 1 until galaxies.size) {
                sum += galaxy.distanceTo(galaxies[i])
            }
        }

        return sum
    }

    fun List<List<Char>>.galaxiesByExpanding(multiplier: Int): List<Pair<Int, Int>> {
        var rowSum = 0
        val rowsIndex: List<Int> = this.mapIndexed { index, row ->
            if (row.all { it == '.' }) {
                rowSum += multiplier
                rowSum
            } else {
                rowSum += 1
                rowSum
            }
        }

        var columnSum = 0
        val columnsIndex = this.indices
            .map { columnIndex -> this.map { it[columnIndex] } }
            .mapIndexed { index, column ->
                if (column.all { it == '.' }) {
                    columnSum += multiplier
                    columnSum
                } else {
                    columnSum += 1
                    columnSum
                }
            }

        val galaxies = mutableListOf<Pair<Int, Int>>()
        this.forEachIndexed { x, row ->
            row.forEachIndexed { y, char ->
                if (char == '#') {
                    galaxies.add(Pair(rowsIndex[x], columnsIndex[y]))
                }
            }
        }

        return galaxies
    }

    fun part2(input: List<String>, multiplier: Int): Long {
        var sum = 0L

        val galaxies = input.map(String::toList).galaxiesByExpanding(multiplier)

        galaxies.forEachIndexed { index, galaxy ->
            for (i in index + 1 until galaxies.size) {
                sum += galaxy.distanceTo(galaxies[i])
            }
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(pkg = "day11", name = "Day11_test")
    check(part1(testInput) == 374)
    check(part2(testInput, multiplier = 10) == 1030L)
    check(part2(testInput, multiplier = 100) == 8410L)

    val input = readInput(pkg = "day11", name = "Day11")
    println(part1(input))
    println(part2(input, multiplier = 1_000_000))
}
