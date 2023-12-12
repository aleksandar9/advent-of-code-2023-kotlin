package day10

import readInput

fun main() {

    class Node(
        val x: Int,
        val y: Int,
        var value: Char,
        val adjacentNodes: MutableList<Node> = mutableListOf(),
        var distance: Int? = null,
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
        val start = grid.find { it.value == 'S' }!!
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

    fun Node.isInsidePolygon(grid: List<Node>) {
        val left = grid.filter { it.x < this.x && it.y == this.y && it.distance != null }
        val leftEmpty = grid.filter { it.x < this.x && it.y == this.y && it.distance == null }
        val right = grid.filter { it.x > this.x && it.y == this.y && it.distance != null }
        val rightEmpty = grid.filter { it.x > this.x && it.y == this.y && it.distance == null }
        val up = grid.filter { it.x == this.x && it.y < this.y && it.distance != null }
        val upEmpty = grid.filter { it.x == this.x && it.y < this.y && it.distance == null }
        val down = grid.filter { it.x == this.x && it.y > this.y && it.distance != null }
        val downEmpty = grid.filter { it.x == this.x && it.y > this.y && it.distance == null }
        if (left.count() % 2 == 1 && right.count() % 2 == 1 && up.count() % 2 == 1 && down.count() % 2 == 1) {
            this.value = '*'
        } else if (value == '.' && left.count() > 0 && right.count() > 0 && up.count() > 0 && down.count() > 0) {
            this.value = 'O'
        }
    }

    fun part2(input: List<String>): Int {
        val grid = mutableListOf<Node>()
        input.forEachIndexed { y, string ->
            string.forEachIndexed { x, char ->
                grid.add(Node(x, y, char))
            }
        }

        grid.forEach { node ->
            if (node.value != '.' && node.value != 'I') {
                node.adjacentNodes.addAll(
                    node.adjacentNodes(grid)
                )
            }
        }

        val deque = ArrayDeque<Node>()
        val start = grid.find { it.value == 'S' }!!
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

        grid.forEach {
            if (it.distance == null) {
                it.isInsidePolygon(grid)
            }
        }

        for (y in 0 until input.size) {
            for (x in 0 until input[0].count()) {
                val node = grid.find { it.x == x && it.y == y }
                print(node!!.value)
            }
            println()
        }

        println("(*) = ${grid.count { it.value == '*' }}")
        println("(O) = ${grid.count { it.value == 'O' }}")

        return grid.count { it.value == '*' }
    }

    fun List<Node>.enrich(): List<Node> {
        val enrichedGrid = mutableListOf<Node>()

        for (y in 0..this.maxOf(Node::y)) {
            for (x in 0..this.maxOf(Node::x)) {
                val node = this.find { it.x == x && it.y == y }
                print(node!!.value)
            }
        }


        return enrichedGrid
    }

    fun part3(input: List<String>): Int {
        val grid = mutableListOf<Node>()
        input.forEachIndexed { y, string ->
            string.forEachIndexed { x, char ->
                grid.add(Node(x, y, char))
            }
        }

        grid.forEach { node ->
            if (node.value != '.' && node.value != 'I') {
                node.adjacentNodes.addAll(
                    node.adjacentNodes(grid)
                )
            }
        }

        val deque = ArrayDeque<Node>()
        val start = grid.find { it.value == 'S' }!!
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

        grid.forEach {
            if (it.distance == null && it.value != '.') {
                it.value = 'O'
            }
        }

        val enrichedGrid = grid.enrich()

        for (y in 0 until input.size) {
            for (x in 0 until input[0].count()) {
                val node = grid.find { it.x == x && it.y == y }
                print(node!!.value)
            }
            println()
        }

        println("Stars (*) = ${grid.count { it.value == '*' }}")
        println("Zeros (O) = ${grid.count { it.value == 'O' }}")

        return grid.count { it.value == '*' }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(pkg = "day10", name = "Day10_test")
    val testInput2 = readInput(pkg = "day10", name = "Day10_test2")
    check(part1(testInput) == 8)
    check(part2(testInput2) == 1)

    val input = readInput(pkg = "day10", name = "Day10")
    println(part1(input))
    println(part2(input))
}
