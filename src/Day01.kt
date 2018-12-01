class Day1_1 : Solution {
    override val day = "01"
    override val part = "1"

    override fun solve(input: List<String>): List<String> {
        val result = input.map { it.toInt() }.sum()
        return listOf(result.toString())
    }
}

class Day1_2 : Solution {
    override val day = "01"
    override val part = "2"

    override fun solve(input: List<String>): List<String> {
        val frequencyChanges = input.map { it.toInt() }
        val reachedFrequencies = mutableListOf<Int>(0)
        val infiniteLoop = generateSequence(0) { (it + 1).rem(frequencyChanges.size) }

        infiniteLoop.forEach { index ->
            val nextFrequency = reachedFrequencies.last() + frequencyChanges[index]
            if (nextFrequency in reachedFrequencies) {
                return listOf(nextFrequency.toString())
            } else {
                reachedFrequencies.add(nextFrequency)
            }
        }
        throw IllegalStateException()
    }
}