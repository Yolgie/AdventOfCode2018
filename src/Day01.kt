
class Day1_1 : IntPuzzle("01", "1") {
    override fun solve(input: List<Int>): Int {
        return input.sum()
    }
}

class Day1_2 : IntPuzzle("01", "2") {
    override fun solve(input: List<Int>): Int {
        val frequencyChanges = input
        val reachedFrequencies = mutableListOf<Int>(0)
        val infiniteLoop = generateSequence(0) { (it + 1).rem(frequencyChanges.size) }

        infiniteLoop.forEach { index ->
            val nextFrequency = reachedFrequencies.last() + frequencyChanges[index]
            if (nextFrequency in reachedFrequencies) {
                return nextFrequency
            } else {
                reachedFrequencies.add(nextFrequency)
            }
        }
        throw IllegalStateException()
    }
}