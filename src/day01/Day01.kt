package day01

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val firstDigit = it.first(Char::isDigit)
            val lastDigit = it.last(Char::isDigit)
            sum += firstDigit.digitToInt() * 10 + lastDigit.digitToInt()
        }
        return sum
    }

    fun String.indexOfFirstString(): Pair<Int, Char> {
        val arrayOfIndices = mutableListOf<Pair<Int, Char>>()
        arrayOfIndices.add(Pair(this.indexOf("one"), '1'))
        arrayOfIndices.add(Pair(this.indexOf("two"), '2'))
        arrayOfIndices.add(Pair(this.indexOf("three"), '3'))
        arrayOfIndices.add(Pair(this.indexOf("four"), '4'))
        arrayOfIndices.add(Pair(this.indexOf("five"), '5'))
        arrayOfIndices.add(Pair(this.indexOf("six"), '6'))
        arrayOfIndices.add(Pair(this.indexOf("seven"), '7'))
        arrayOfIndices.add(Pair(this.indexOf("eight"), '8'))
        arrayOfIndices.add(Pair(this.indexOf("nine"), '9'))
        return arrayOfIndices.filter { it.first > -1 }.minByOrNull { it.first } ?: Pair(-1, '0')
    }

    fun String.indexOfLastString(): Pair<Int, Char> {
        val arrayOfIndices = mutableListOf<Pair<Int, Char>>()
        arrayOfIndices.add(Pair(this.lastIndexOf("one"), '1'))
        arrayOfIndices.add(Pair(this.lastIndexOf("two"), '2'))
        arrayOfIndices.add(Pair(this.lastIndexOf("three"), '3'))
        arrayOfIndices.add(Pair(this.lastIndexOf("four"), '4'))
        arrayOfIndices.add(Pair(this.lastIndexOf("five"), '5'))
        arrayOfIndices.add(Pair(this.lastIndexOf("six"), '6'))
        arrayOfIndices.add(Pair(this.lastIndexOf("seven"), '7'))
        arrayOfIndices.add(Pair(this.lastIndexOf("eight"), '8'))
        arrayOfIndices.add(Pair(this.lastIndexOf("nine"), '9'))
        return arrayOfIndices.maxByOrNull { it.first } ?: Pair(-1, '0')
    }

    fun String.firstNumber(): Int {
        val arrayOfIndices = mutableListOf<Pair<Int, Int>>()
        arrayOfIndices.add(Pair(this.indexOf("one"), 1))
        arrayOfIndices.add(Pair(this.indexOf("1"), 1))
        arrayOfIndices.add(Pair(this.indexOf("two"), 2))
        arrayOfIndices.add(Pair(this.indexOf("2"), 2))
        arrayOfIndices.add(Pair(this.indexOf("three"), 3))
        arrayOfIndices.add(Pair(this.indexOf("3"), 3))
        arrayOfIndices.add(Pair(this.indexOf("four"), 4))
        arrayOfIndices.add(Pair(this.indexOf("4"), 4))
        arrayOfIndices.add(Pair(this.indexOf("five"), 5))
        arrayOfIndices.add(Pair(this.indexOf("5"), 5))
        arrayOfIndices.add(Pair(this.indexOf("six"), 6))
        arrayOfIndices.add(Pair(this.indexOf("6"), 6))
        arrayOfIndices.add(Pair(this.indexOf("seven"), 7))
        arrayOfIndices.add(Pair(this.indexOf("7"), 7))
        arrayOfIndices.add(Pair(this.indexOf("eight"), 8))
        arrayOfIndices.add(Pair(this.indexOf("8"), 8))
        arrayOfIndices.add(Pair(this.indexOf("nine"), 9))
        arrayOfIndices.add(Pair(this.indexOf("9"), 9))
        return arrayOfIndices.filter { it.first > -1 }.minBy { it.first }.second
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val indexOfFirstInt = it.indexOfFirst(Char::isDigit)
            val indexOfFirstString = it.indexOfFirstString()

            val firstDigit = if (indexOfFirstInt == -1) {
                indexOfFirstString.second
            } else if (indexOfFirstString.first == -1) {
                it.toCharArray().first(Char::isDigit)
            } else if (indexOfFirstInt < indexOfFirstString.first) {
                it.toCharArray().first(Char::isDigit)
            } else {
                indexOfFirstString.second
            }

            val indexOfLastInt = it.indexOfLast(Char::isDigit)
            val indexOfLastString = it.indexOfLastString()

            val lastDigit = if (indexOfLastString.first > indexOfLastInt) {
                indexOfLastString.second
            } else {
                it.toCharArray().last(Char::isDigit)
            }

            sum += lastDigit.digitToInt() + firstDigit.digitToInt() * 10
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(pkg = "day01", name = "Day01_test")
    check(part1(testInput) == 142)
    val testInput2 = readInput(pkg = "day01", name = "Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput(pkg = "day01", name = "Day01")
    println(part1(input))
    println(part2(input))
}
