import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AdventTests() {
    @Test
    fun day1() {
        assertEquals(69795, day1A(readFile("Day01")))
        assertEquals(208437, day1B(readFile("Day01")))
    }
}
