package day08

import readInput

data class Node(val node: String, val left: String, val right: String)

fun Map<String, Node>.steps(start: String, end: String, directions: String): Int {
    var counter = 0
    var currentNode = start

    while (true) {
        val direction = directions[counter % directions.length]
        val node = this[currentNode]!!
        currentNode = if (direction == 'L') {
            node.left
        } else {
            node.right
        }
        counter++
        if (currentNode == end) break
    }

    return counter
}

fun main() {

    val regex = Regex("(\\w+) = \\((\\w+), (\\w+)\\)$")

    fun part1(input: List<String>): Int {
        val directions = input[0]

        val nodes = mutableMapOf<String, Node>()
        for (i in 2 until input.size) {
            val (node, left, right) = regex.matchEntire(input[i])?.destructured!!
            nodes.put(node, Node(node, left, right))
        }

        return nodes.steps(start = "AAA", end = "ZZZ", directions)
    }

    fun gcd(x: Long, y: Long): Long = if (y == 0L) x else gcd(y, x % y)

    fun gcd(numbers: Array<Long>): Long {
        return numbers.reduce(::gcd)
    }

    fun lcm(numbers: Array<Long>): Long {
        return numbers.fold(1) { acc, i ->
            acc * (i / gcd(acc, i))
        }
    }

    fun part2(input: List<String>): Long {
        val directions = input[0]

        val nodes = mutableMapOf<String, Node>()
        for (i in 2 until input.size) {
            val (node, left, right) = regex.matchEntire(input[i])?.destructured!!
            nodes[node] = Node(node, left, right)
        }

        val currentNodes = nodes.values.filter { it.node.endsWith('A') }.toMutableList()
        val endNodes = nodes.values.filter { it.node.endsWith('Z') }.toMutableList()
        val distances = mutableListOf<Long>()

        currentNodes.forEach { start ->
            endNodes.forEach { end ->
                try {
                    distances.add(nodes.steps(start.node, end.node, directions).toLong())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return lcm(distances.toTypedArray())
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput(pkg = "day08", name = "Day08_test")
    val testInput2 = readInput(pkg = "day08", name = "Day08_test2")
    check(part1(testInput1) == 6)
    check(part2(testInput2) == 6L)

    val input = readInput(pkg = "day08", name = "Day08")
    println(part1(input))
    println(part2(input))
}
