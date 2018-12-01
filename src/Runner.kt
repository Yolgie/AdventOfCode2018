import java.io.File

val INPUT_DIR = "input/"
val OUTPUT_DIR = "output/"

interface Solution {
    val day: String
    val part: String
    fun solve(input: List<String>): List<String>
}

fun run(solution: Solution) {
    val inputFile: File = getInputFile(solution.day)
    val input: List<String> = inputFile.readLines()
    val output: List<String> = solution.solve(input)
    val outputFile: File = getOutputFile(solution.day, solution.part)
    outputFile.writeText(output.joinToString("/n"))
}

private fun getInputFile(day: String): File {
    return File("$INPUT_DIR/day$day.input")
}

private fun getOutputFile(day: String, part: String = "1", count: Int = 1): File {
    val outputFile = File("$OUTPUT_DIR/day$day.$part.$count.output")
    return if (outputFile.exists()) {
        getOutputFile(day, part, count + 1)
    } else {
        outputFile
    }
}

fun main(args: Array<String>) {
    run(Day1_2())
}
