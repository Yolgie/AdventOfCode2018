import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class GuardLogEntry(val id: Int?, val timestamp: LocalDateTime, val action: Action) {
    constructor(id: Int, entry: GuardLogEntry) : this(id, entry.timestamp, entry.action)
}

data class Guard(val id: Int, val entrys: List<GuardLogEntry>) {
    val sleepRanges: List<IntRange> = entrys
        .filter { it.action != Action.START }
        .zipWithNext()
        .filter { it.first.action == Action.FALL_ASLEEP }
        .map { it -> it.first.timestamp.minute until it.second.timestamp.minute }
    // <minute, countAsleep>
    val sleepTimesCount: Map<Int, Int> = sleepRanges
        .flatMap { intRange -> intRange.toList() }
        .groupingBy { it }.eachCount()
    val sleepTimesSum: Int = sleepTimesCount
        .values
        .sum()
    val mostOftenAsleepAtMinute: Int? = sleepTimesCount
        .toList()
        .sortedBy { (_, count) -> count }
        .reversed()
        .map { (minute, _) -> minute }
        .firstOrNull()

    override fun toString(): String {
        return "Guard(id=$id, sleepTimesCount=$sleepTimesCount, sleepTimesSum=$sleepTimesSum, mostOftenAsleepAtMinute=$mostOftenAsleepAtMinute)"
    }
}

enum class Action(val regex: Regex) {
    START(Regex("""begins shift""")),
    FALL_ASLEEP(Regex("""falls asleep""")),
    WAKE_UP(Regex("""wakes up"""))
}

class GuardLogParser {
    companion object {
        val guardIdRegex = Regex("""#([\d]+?) """)
        val timestampRegex = Regex("""\[(.*)]""")
        val timestampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        fun parseGuardLog(input: List<String>): List<GuardLogEntry> {
            return input.map { parseGuardLogEntry(it) }
        }

        private fun parseGuardLogEntry(input: String): GuardLogEntry {
            return GuardLogEntry(parseId(input), parseTimestamp(input), parseAction(input))
        }

        private fun parseId(input: String): Int? {
            return guardIdRegex.find(input)?.groupValues?.get(1)?.toInt()
        }

        private fun parseTimestamp(input: String): LocalDateTime {
            val timestampString = timestampRegex.find(input)?.groupValues?.get(1)
                ?: throw Exception("No timestamp found in: $input")
            return LocalDateTime.parse(timestampString, timestampFormat)
                ?: throw Exception("Could not parse timestamp: $timestampString in: $input")
        }

        private fun parseAction(input: String): Action {
            return Action.values().find { action -> action.regex.containsMatchIn(input) }
                ?: throw Exception("No action found in: $input")
        }
    }
}

class Day04part1 : Puzzle<String>(4, 1) {
    override fun solve(input: List<String>): String {
        val incompleteGuardLog =
            GuardLogParser.parseGuardLog(input).sortedBy { guardLogEntry -> guardLogEntry.timestamp }
        val guards = aggregateGuards(incompleteGuardLog)
        return guards
            .filter { guard -> guard.mostOftenAsleepAtMinute != null }
            .sortedBy { guard -> guard.sleepTimesSum }
            .reversed()
            .map { guard -> guard.id * guard.mostOftenAsleepAtMinute!! }
            .first().toString()
    }
}

class Day04part2 : Puzzle<String>(4, 2) {
    override fun solve(input: List<String>): String {
        val incompleteGuardLog =
            GuardLogParser.parseGuardLog(input).sortedBy { guardLogEntry -> guardLogEntry.timestamp }
        val guards = aggregateGuards(incompleteGuardLog)
        return guards
            .filter { guard -> guard.mostOftenAsleepAtMinute != null }
            .sortedBy { guard -> guard.sleepTimesCount.get(guard.mostOftenAsleepAtMinute) }
            .reversed()
            .map { guard -> guard.id * guard.mostOftenAsleepAtMinute!! }
            .first().toString()
    }
}

private fun aggregateGuards(incompleteGuardLog: List<GuardLogEntry>): List<Guard> {
    val newGuardLog = mutableListOf<GuardLogEntry>()
    var currentGuardId: Int = incompleteGuardLog.get(0).id
        ?: throw Exception("First action does not contain guard id in: ${incompleteGuardLog.get(0)}")
    for (entry in incompleteGuardLog) {
        if (entry.id != null) {
            currentGuardId = entry.id
        }
        newGuardLog.add(GuardLogEntry(currentGuardId, entry))
    }
    return newGuardLog
        .groupBy { guardLogEntry -> guardLogEntry.id }
        .map { entry -> Guard(entry.key!!, entry.value) }
}