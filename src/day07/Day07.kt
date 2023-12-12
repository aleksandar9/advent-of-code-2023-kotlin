package day07

import readInput

enum class Card(val strength: Int) {
    A(14),
    K(13),
    Q(12),
    J(11),
    T(10),
    `9`(9),
    `8`(8),
    `7`(7),
    `6`(6),
    `5`(5),
    `4`(4),
    `3`(3),
    `2`(2),
    Y(1), // Joker
}

data class Hand(val cards: List<Card>, val bid: Int) {
    enum class Type(val strength: Int) {
        FiveOfAKind(7),
        FourOfAKind(6),
        FullHouse(5),
        ThreeOfAKind(4),
        TwoPair(3),
        OnePair(2),
        HighCard(1),
    }

    val type: Type
        get() {
            val groups = cards.groupingBy { it }.eachCount()
            return if (groups.containsKey(Card.Y)) {
                val jokers = groups[Card.Y]!!
                if (jokers > 3) {
                    Type.FiveOfAKind
                } else if (jokers == 3) {
                    if (groups.containsValue(2)) {
                        Type.FiveOfAKind
                    } else {
                        Type.FourOfAKind
                    }
                } else if (jokers == 2) {
                    if (groups.containsValue(3)) {
                        Type.FiveOfAKind
                    } else if (groups.size == 3) {
                        Type.FourOfAKind
                    } else {
                        Type.ThreeOfAKind
                    }
                } else {
                    if (groups.containsValue(4)) {
                        Type.FiveOfAKind
                    } else if (groups.containsValue(3)) {
                        Type.FourOfAKind
                    } else if (groups.size == 3 && groups.containsValue(2)) {
                        Type.FullHouse
                    } else if (groups.size == 4) {
                        Type.ThreeOfAKind
                    } else {
                        Type.OnePair
                    }
                }
            } else if (groups.size == 1) {
                Type.FiveOfAKind
            } else if (groups.containsValue(4)) {
                Type.FourOfAKind
            } else if (groups.size == 2 && groups.containsValue(3)) {
                Type.FullHouse
            } else if (groups.size == 3 && groups.containsValue(3)) {
                Type.ThreeOfAKind
            } else if (groups.size == 3 && groups.containsValue(2)) {
                Type.TwoPair
            } else if (groups.size == 4 && groups.containsValue(2)) {
                Type.OnePair
            } else {
                Type.HighCard
            }
        }
}

fun main() {

    fun Char.toCard(): Card {
        return when (this) {
            'A' -> Card.A
            'K' -> Card.K
            'Q' -> Card.Q
            'J' -> Card.J
            'T' -> Card.T
            '9' -> Card.`9`
            '8' -> Card.`8`
            '7' -> Card.`7`
            '6' -> Card.`6`
            '5' -> Card.`5`
            '4' -> Card.`4`
            '3' -> Card.`3`
            '2' -> Card.`2`
            else -> throw Exception("Invalid card")
        }
    }

    fun part1(input: List<String>): Int {
        var sum = 0

        val hands = mutableListOf<Hand>()
        input.forEach {
            val line = it.split(' ')
            val hand = Hand(line[0].map(Char::toCard), line[1].toInt())
            hands.add(hand)
        }

        hands.sortWith(
            compareBy<Hand> { it.type.strength }
                .thenByDescending { it.cards[0] }
                .thenByDescending { it.cards[1] }
                .thenByDescending { it.cards[2] }
                .thenByDescending { it.cards[3] }
                .thenByDescending { it.cards[4] }
        )

        hands.forEachIndexed { index, hand ->
            sum += hand.bid * (index + 1)
        }

        return sum
    }

    fun Char.toCardWithJoker(): Card {
        return when (this) {
            'A' -> Card.A
            'K' -> Card.K
            'Q' -> Card.Q
            'J' -> Card.Y
            'T' -> Card.T
            '9' -> Card.`9`
            '8' -> Card.`8`
            '7' -> Card.`7`
            '6' -> Card.`6`
            '5' -> Card.`5`
            '4' -> Card.`4`
            '3' -> Card.`3`
            '2' -> Card.`2`
            else -> throw Exception("Invalid card")
        }
    }

    fun part2(input: List<String>): Int {
        var sum = 0

        val hands = mutableListOf<Hand>()
        input.forEach {
            val line = it.split(' ')
            val hand = Hand(line[0].map(Char::toCardWithJoker), line[1].toInt())
            hands.add(hand)
        }

        hands.sortWith(
            compareBy<Hand> { it.type.strength }
                .thenByDescending { it.cards[0] }
                .thenByDescending { it.cards[1] }
                .thenByDescending { it.cards[2] }
                .thenByDescending { it.cards[3] }
                .thenByDescending { it.cards[4] }
        )

        hands.forEachIndexed { index, hand ->
            sum += hand.bid * (index + 1)
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(pkg = "day07", name = "Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput(pkg = "day07", name = "Day07")
    println(part1(input))
    println(part2(input))
}
