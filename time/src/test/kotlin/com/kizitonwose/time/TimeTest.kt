package com.kizitonwose.time

import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.*
import org.junit.Test
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

        assertThat(newValue, equalTo(170.seconds))
    }

    @Test
    fun `time "in" operator works as expected`() {
        assertTrue(60.minutes in 4.hours)
        assertFalse(2.days in 24.hours)
        assertTrue(120.seconds in 2.minutes)
    }

    @Test
    fun `time operators(multiplication and division) work as expected`() {
        val sixtySecs = 60.seconds

        val multiplied = sixtySecs * 2
        val divided = sixtySecs / 2

        assertEquals(multiplied, 120.seconds)
        assertEquals(divided, 30.seconds)
    }

    @Test
    fun `time operators(increment and decrement) work as expected`() {
        var days = 2.days

        days++
        assertEquals(days, 3.days)

        days--
        assertEquals(days, 2.days)
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

    @Test
    fun `custom time units work as expected`() {
        val twoWeeks = 2.weeks
        val fourteenDays = 14.days

        assertEquals(twoWeeks, fourteenDays)
        assertEquals(336.hours.inWeeks, twoWeeks)
    }
}


// Custom time unit.
class Week : TimeUnit {
    override val timeIntervalRatio = 604800.0
}

val Number.weeks: Interval<Week>
    get() = Interval(this.toDouble())

val Interval<TimeUnit>.inWeeks: Interval<Week>
    get() = converted()
