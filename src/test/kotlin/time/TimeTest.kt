package time

import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test
import org.junit.Assert.*
import java.util.*

/**
 * Created by Kizito Nwose on 15/10/2017
 */

class TimeTest {

    @Test
    fun `time comparisons work as expected`() {
        assertTrue(5.minutes > 120.seconds)
        assertTrue(2.days < 48.5.hours)
        assertTrue(1000.microseconds > 2000.nanoseconds)
        assertEquals(60.seconds, 60000.milliseconds)
    }

    @Test
    fun `time conversions work as expected`() {
        val twentyFourHours = 24.hours

        val valueInDays = twentyFourHours.inSeconds.inMinutes.inNanoseconds
                .inMicroseconds.inHours.inMilliseconds.inDays

        assertThat(valueInDays.value, equalTo(1.0))
    }

    @Test
    fun `basic time operators work as expected`() {
        val sixtySecs = 60.seconds

        var newValue = sixtySecs + 2.minutes
        newValue -= 20.seconds
        newValue += 10.seconds

        assertThat(newValue.value, equalTo(170.0))
    }

    @Test
    fun `time "in" operator works as expected`() {
        assertTrue(60.minutes in 4.hours)
        assertFalse(2.days in 24.hours)
        assertTrue(120.seconds in 2.minutes)
    }

    @Test
    fun `time operators with numbers work as expected`() {
        val sixtySecs = 60.seconds

        val newValue = sixtySecs * 2

        assertEquals(newValue, 120.seconds)
    }

    @Test
    fun `ten minutes in the future is greater than now`() {
        val now = Calendar.getInstance()

        val tenMinutesLater = now + 10.minutes

        assertTrue(tenMinutesLater > now)
    }

    @Test
    fun `ten days ago is less than now`() {
        val now = Calendar.getInstance()

        val tenDaysAgo = now - 10.days

        assertTrue(tenDaysAgo < now)
    }
}
