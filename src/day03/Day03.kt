package day03

import readInput

data class Point(val char: Char, val x: Int, val y: Int)

fun main() {

    fun Char.isSymbol(): Boolean = !this.isDigit() && this != '.'

    fun List<List<Char>>.adjacent(x: Int, y: Int): Point? {
        val fromX = maxOf(0, x - 1)
        val toX = minOf(this.size - 1, x + 1)
        val fromY = maxOf(0, y - 1)
        val toY = minOf(this[0].size - 1, y + 1)
        for (row in fromX..toX) {
            for (column in fromY..toY) {
                if (this[row][column].isSymbol()) {
                    return Point(this[row][column], row, column)
                }
            }
        }
        return null
    }

    fun part1(input: List<String>): Int {
        var sum = 0

        val rows = mutableListOf<List<Char>>()
        input.forEach {
            rows.add(it.toList())
        }

        rows.forEachIndexed { x, row ->
            var part = ""
            var isAdjacent: Point? = null
            row.forEachIndexed { y, char ->
                if (char.isDigit()) {
                    part += char
                    if (isAdjacent == null) {
                        isAdjacent = rows.adjacent(x, y)
                    }
                } else {
                    if (isAdjacent != null) {
                        sum += part.toInt()
                    }
                    isAdjacent = null
                    part = ""
                }
            }
            if (isAdjacent != null) {
                sum += part.toInt()
                isAdjacent = null
                part = ""
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val potentialGears = mutableListOf<Pair<Int, Point>>()

        val rows = mutableListOf<List<Char>>()
        input.forEach {
            rows.add(it.toList())
        }

        rows.forEachIndexed { x, row ->
            var part = ""
            var adjacent: Point? = null
            row.forEachIndexed { y, char ->
                if (char.isDigit()) {
                    part += char
                    if (adjacent == null) {
                        adjacent = rows.adjacent(x, y)
                    }
                } else {
                    adjacent?.let {
                        if (it.char == '*') {
                            potentialGears.add(Pair(part.toInt(), it))
                        }
                    }
                    adjacent = null
                    part = ""
                }
            }
            adjacent?.let {
                if (it.char == '*') {
                    potentialGears.add(Pair(part.toInt(), it))
                }
                adjacent = null
                part = ""
            }
        }

        potentialGears.groupBy { it.second }.forEach {
            if (it.value.size == 2) {
                sum += it.value[0].first * it.value[1].first
            }
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(pkg = "day03", name = "Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput(pkg = "day03", name = "Day03")
    println(part1(input))
    println(part2(input))
}
