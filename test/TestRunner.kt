import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class TestRunner(val input: List<String>, val expectedOutput: List<String>, val solution: Solution) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(this.perpareList("+1, +1, +1"), listOf("3"), Day1_1()),
                arrayOf(this.perpareList("+1, +1, -2"), listOf("0"), Day1_1()),
                arrayOf(this.perpareList("-1, -2, -3"), listOf("-6"), Day1_1()),
                arrayOf(this.perpareList("+1, -1"), listOf("0"), Day1_2()),
                arrayOf(this.perpareList("+3, +3, +4, -2, -4"), listOf("10"), Day1_2()),
                arrayOf(this.perpareList("-6, +3, +8, +5, -6"), listOf("5"), Day1_2()),
                arrayOf(this.perpareList("+7, +7, -2, -7, -4"), listOf("14"), Day1_2())
            )
        }

        fun perpareList(input: String): List<String>
        {
            return input.split(",").map(String::trim)
        }
    }

    @Test
    fun testLevel() {
        Assert.assertEquals(expectedOutput, solution.solve(input))
    }
}