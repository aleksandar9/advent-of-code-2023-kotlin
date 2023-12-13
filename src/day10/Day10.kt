package day10

import readInput

fun main() {

    class Node(
        val x: Int,
        val y: Int,
        var value: Char,
        val adjacentNodes: MutableList<Node> = mutableListOf(),
        var distance: Int? = null,
        var isStart: Boolean = false,
    ) {

        fun canGoLeft(): Boolean = this.value == '-' || this.value == 'F' || this.value == 'L'
        fun canGoRight(): Boolean = this.value == '-' || this.value == '7' || this.value == 'J'
        fun canGoUp(): Boolean = this.value == '|' || this.value == '7' || this.value == 'F'
        fun canGoDown(): Boolean = this.value == '|' || this.value == 'J' || this.value == 'L'

        override fun toString(): String {
            return "$value"
        }
    }

    fun Node.adjacentNodes(grid: List<Node>): List<Node> {
        val right = grid.find { it.x == this.x + 1 && it.y == this.y && it.canGoRight() }
        val left = grid.find { it.x == this.x - 1 && it.y == this.y && it.canGoLeft() }
        val up = grid.find { it.x == this.x && it.y == this.y - 1 && it.canGoUp() }
        val down = grid.find { it.x == this.x && it.y == this.y + 1 && it.canGoDown() }

        val adjacentNodes = when (this.value) {
            '-' -> listOf(left, right)
            '|' -> listOf(up, down)
            'L' -> listOf(right, up)
            'J' -> listOf(up, left)
            '7' -> listOf(left, down)
            'F' -> listOf(down, right)
            'S' -> listOf(up, right, down, left)
            else -> emptyList()
        }

        if (this.value == 'S') {
            this.isStart = true
            val startingValue = if (up != null && down != null) {
                '|'
            } else if (left != null && right != null) {
                '-'
            } else if (right != null && up != null) {
                'L'
            } else if (up != null && left != null) {
                'J'
            } else if (left != null && down != null) {
                '7'
            } else if (down != null && right != null) {
                'F'
            } else {
                '.'
            }
            this.value = startingValue
        }

        return adjacentNodes.filterNotNull()
    }

    fun part1(input: List<String>): Int {
        val grid = mutableListOf<Node>()
        input.forEachIndexed { y, string ->
            string.forEachIndexed { x, char ->
                grid.add(Node(x, y, char))
            }
        }

        grid.forEach { node ->
            if (node.value != '.') {
                node.adjacentNodes.addAll(
                    node.adjacentNodes(grid)
                )
            }
        }

        val deque = ArrayDeque<Node>()
        val start = grid.find { it.isStart }!!
        start.distance = 0
        deque.addFirst(start)

        while (deque.isNotEmpty()) {
            val current = deque.removeFirst()
            current.adjacentNodes.forEach {
                if (it.distance == null) {
                    it.distance = current.distance!! + 1
                    deque.addLast(it)
                }
            }
        }

        return grid.maxOf { it.distance ?: 0 }
    }

    fun Node.isInside(grid: List<Node>) {
        val left = grid.filter { it.x < this.x && it.y == this.y && it.value == '|' }
        val right = grid.filter { it.x > this.x && it.y == this.y && it.value == '|' }
        val up = grid.filter { it.x == this.x && it.y < this.y && it.value == '-' }
        val down = grid.filter { it.x == this.x && it.y > this.y && it.value == '-' }
        if (left.count() % 2 == 1 && right.count() % 2 == 1 && up.count() % 2 == 1 && down.count() % 2 == 1) {
            this.value = '*'
        } else {
            this.value = '0'
        }
    }

    fun List<Node>.enrich(): List<Node> {
        val enrichedGrid = mutableListOf<Node>()
        val enrichedChars: MutableList<MutableList<Char>> = mutableListOf()

        for (y in 0..this.maxOf(Node::y)) {
            enrichedChars.add(mutableListOf())
            enrichedChars.add(mutableListOf())
            enrichedChars.add(mutableListOf())
            for (x in 0..this.maxOf(Node::x)) {
                val node = this.find { it.x == x && it.y == y }!!
                val index = enrichedChars.size
                if (node.distance != null) {
                    when (node.value) {
                        '-' -> {
                            enrichedChars[index - 3].addAll(arrayOf(' ', ' ', ' '))
                            enrichedChars[index - 2].addAll(arrayOf('-', '-', '-'))
                            enrichedChars[index - 1].addAll(arrayOf(' ', ' ', ' '))
                        }

                        '|' -> {
                            enrichedChars[index - 3].addAll(arrayOf(' ', '|', ' '))
                            enrichedChars[index - 2].addAll(arrayOf(' ', '|', ' '))
                            enrichedChars[index - 1].addAll(arrayOf(' ', '|', ' '))
                        }

                        'L' -> {
                            enrichedChars[index - 3].addAll(arrayOf(' ', '|', ' '))
                            enrichedChars[index - 2].addAll(arrayOf(' ', 'L', '-'))
                            enrichedChars[index - 1].addAll(arrayOf(' ', ' ', ' '))
                        }

                        'J' -> {
                            enrichedChars[index - 3].addAll(arrayOf(' ', '|', ' '))
                            enrichedChars[index - 2].addAll(arrayOf('-', 'J', ' '))
                            enrichedChars[index - 1].addAll(arrayOf(' ', ' ', ' '))
                        }

                        '7' -> {
                            enrichedChars[index - 3].addAll(arrayOf(' ', ' ', ' '))
                            enrichedChars[index - 2].addAll(arrayOf('-', '7', ' '))
                            enrichedChars[index - 1].addAll(arrayOf(' ', '|', ' '))
                        }

                        'F' -> {
                            enrichedChars[index - 3].addAll(arrayOf(' ', ' ', ' '))
                            enrichedChars[index - 2].addAll(arrayOf(' ', 'F', '-'))
                            enrichedChars[index - 1].addAll(arrayOf(' ', '|', ' '))
                        }
                    }
                } else {
                    enrichedChars[index - 3].addAll(arrayOf('.', ' ', ' '))
                    enrichedChars[index - 2].addAll(arrayOf(' ', ' ', ' '))
                    enrichedChars[index - 1].addAll(arrayOf(' ', ' ', ' '))
                }
            }
        }

        for (x in 0 until enrichedChars.size) {
            for (y in 0 until enrichedChars[0].size) {
                enrichedGrid.add(Node(y, x, enrichedChars[x][y]))
            }
        }

        enrichedGrid.forEach {
            if (it.value == '.') {
                it.isInside(enrichedGrid)
            }
        }

        for (y in 0 until enrichedGrid.maxOf(Node::y)) {
            for (x in 0 until enrichedGrid.maxOf(Node::x)) {
                print(enrichedGrid.find { it.x == x && it.y == y }!!.value)
            }
            println()
        }

        return enrichedGrid
    }

    fun part2(input: List<String>): Int {
        val grid = mutableListOf<Node>()
        input.forEachIndexed { y, string ->
            string.forEachIndexed { x, char ->
                grid.add(Node(x, y, char))
            }
        }

        grid.forEach { node ->
            if (node.value != '.') {
                node.adjacentNodes.addAll(
                    node.adjacentNodes(grid)
                )
            }
        }

        val deque = ArrayDeque<Node>()
        val start = grid.find { it.isStart }!!
        start.distance = 0
        deque.addFirst(start)

        while (deque.isNotEmpty()) {
            val current = deque.removeFirst()
            current.adjacentNodes.forEach {
                if (it.distance == null) {
                    it.distance = current.distance!! + 1
                    deque.addLast(it)
                }
            }
        }

        return grid.enrich().count { it.value == '*' }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(pkg = "day10", name = "Day10_test")
    val testInput21 = readInput(pkg = "day10", name = "Day10_test2_1")
    val testInput22 = readInput(pkg = "day10", name = "Day10_test2_2")
    val testInput23 = readInput(pkg = "day10", name = "Day10_test2_3")
    check(part1(testInput) == 8)
    check(part2(testInput21) == 4)
    check(part2(testInput22) == 8)
    check(part2(testInput23) == 10)

    val input = readInput(pkg = "day10", name = "Day10")
    println(part1(input))
    println(part2(input))
}
