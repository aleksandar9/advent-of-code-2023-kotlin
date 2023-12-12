package day04

import readInput
import kotlin.math.pow

data class Card(
    val cardNumber: Int,
    val winningNumbers: List<Int>,
    val scratchedNumbers: List<Int>,
    val matches: Int,
    val points: Int,
    var copies: Int,
)

fun main() {

    fun String.card(): Card {
        val gameRegex = Regex("Card\\ +(\\d+): (( \\d |\\d{2} )+)\\| ((\\d{2} | \\d |\\d{1,2}| \\d)+)$")
        val (number, winning, _, scratched, _) = gameRegex.find(this)!!.destructured
        val winningNumbers = winning.trim().split(Regex("\\s+")).map(String::toInt)
        val scratchedNumbers = scratched.trim().split(Regex("\\s+")).map(String::toInt)
        val matches = winningNumbers.intersect(scratchedNumbers.toSet())
        val points = 2.0.pow(matches.size - 1).toInt()
        return Card(
            cardNumber = number.toInt(),
            winningNumbers = winningNumbers,
            scratchedNumbers = scratchedNumbers,
            matches = matches.size,
            points = points,
            copies = 1,
        )
    }

    fun part1(input: List<String>): Int {
        var sum = 0

        input.forEach {
            sum += it.card().points
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val cards = mutableListOf<Card>()
        input.forEach {
            cards.add(it.card())
        }

        cards.forEachIndexed { index, card ->
            if (card.matches > 0) {
                for (i in index + card.matches downTo index + 1) {
                    cards[i].copies += card.copies
                }
            }
        }

        return cards.sumOf { it.copies }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(pkg = "day04", name = "Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput(pkg = "day04", name = "Day04")
    println(part1(input))
    println(part2(input))
}
