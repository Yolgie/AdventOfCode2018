
abstract class Puzzle<T>(val day: String, val part: String) {
    abstract fun solve(input: List<T>): T
    abstract fun parse(value: String): T

    fun solve(input: List<String>): List<String> {
        return listOf(solve(input.map { parse(it) }).toString())
    }
}

abstract class IntPuzzle(day: String, part: String) : Puzzle<Int>(day, part) {
    override fun parse(value: String): Int = value.toInt()
}

abstract class StringPuzzle(day: String, part: String) : Puzzle<String>(day, part) {
    override fun parse(value: String): String = value
}
