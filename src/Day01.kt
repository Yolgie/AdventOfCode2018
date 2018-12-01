
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
        val reached = mutableListOf<Int>(0)
        val loopSequence = generateSequence(0) { (it + 1).rem(frequencyChanges.size) }

        loopSequence.forEach {
            val nextFrequency = applyChange(frequencyChanges, reached.last(), it)
            if (nextFrequency in reached) {
                return listOf(nextFrequency.toString())
            } else {
                reached.add(nextFrequency)
            }
        }

        val result = input.map { it.toInt() }.sum()
        return listOf(result.toString())
    }

    fun applyChange(changes: List<Int>, currentValue: Int, currentPosition: Int) : Int {
        return currentValue + changes.get(currentPosition)
    }
}