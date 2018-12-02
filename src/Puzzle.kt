abstract class Puzzle<T>(val day: String, val part: String, val parse: (String) -> T = { s -> s as T }) {
    abstract fun solve(input: List<T>): T

    open fun solve(input: List<String>): List<String> {
        return listOf(solve(input.map { parse(it) }).toString())
    }
}

abstract class MultiLinePuzzle<T>(day: String, part: String, parse: (String) -> T) : Puzzle<T>(day, part, parse) {
    override fun solve(input: List<T>): T {
        throw Exception("Not applicable method signature")
    }

    abstract fun solveMultiline(input: List<T>): List<T>

    override fun solve(input: List<String>): List<String> {
        return solveMultiline(input.map { parse(it) }).map { it.toString() }
    }
}

abstract class IntPuzzle(day: String, part: String) : Puzzle<Int>(day, part, String::toInt)