package com.kizitonwose.time

import java.io.Serializable

/**
 * Created by Kizito Nwose on 14/10/2017
 */


interface TimeUnit {
    val timeIntervalRatio: Double
    fun <OtherUnit : TimeUnit> conversionRate(otherTimeUnit: OtherUnit): Double {
        return timeIntervalRatio / otherTimeUnit.timeIntervalRatio
    }
}


class Interval<out T : TimeUnit>(value: Number, factory: () -> T) : Serializable {

    companion object {
        inline operator fun <reified K : TimeUnit> invoke(value: Number) = Interval(value) {
            K::class.java.newInstance()
        }
    }

    val unit: T = factory()

    val value = value.toDouble()

    val longValue = Math.round(this.value)

    val inDays: Interval<Day>
        get() = converted()

    val inHours: Interval<Hour>
        get() = converted()

    val inMinutes: Interval<Minute>
        get() = converted()

    val inSeconds: Interval<Second>
        get() = converted()

    val inMilliseconds: Interval<Millisecond>
        get() = converted()

    val inMicroseconds: Interval<Microsecond>
        get() = converted()

    val inNanoseconds: Interval<Nanosecond>
        get() = converted()


    inline fun <reified OtherUnit : TimeUnit> converted(): Interval<OtherUnit> {
        val otherInstance = OtherUnit::class.java.newInstance()
        return Interval(value * unit.conversionRate(otherInstance))
    }

    operator fun plus(other: Interval<TimeUnit>): Interval<T> {
        val newValue = value + other.value * other.unit.conversionRate(unit)
        return Interval(newValue) { unit }
    }

    operator fun minus(other: Interval<TimeUnit>): Interval<T> {
        val newValue = value - other.value * other.unit.conversionRate(unit)
        return Interval(newValue) { unit }
    }

    operator fun times(other: Number): Interval<T> {
        return Interval(value * other.toDouble()) { unit }
    }

    operator fun div(other: Number): Interval<T> {
        return Interval(value / other.toDouble()) { unit }
    }

    operator fun inc() = Interval(value + 1) { unit }

    operator fun dec() = Interval(value - 1) { unit }

    operator fun compareTo(other: Interval<TimeUnit>) = inMilliseconds.value.compareTo(other.inMilliseconds.value)

    operator fun contains(other: Interval<TimeUnit>) = inMilliseconds.value >= other.inMilliseconds.value

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Interval<TimeUnit>) return false
        return compareTo(other) == 0
    }

    override fun hashCode() = inMilliseconds.value.hashCode()

    override fun toString(): String {
        val unitString = unit::class.java.simpleName.toLowerCase()
        val isWhole = value % 1 == 0.0
        return (if (isWhole) longValue.toString() else value.toString())
                .plus(" ")
                .plus(if (value == 1.0) unitString else unitString.plus("s"))
    }
}


class Day : TimeUnit {
    override val timeIntervalRatio = 86400.0
}

class Hour : TimeUnit {
    override val timeIntervalRatio = 3600.0
}

class Minute : TimeUnit {
    override val timeIntervalRatio = 60.0
}

class Second : TimeUnit {
    override val timeIntervalRatio = 1.0
}

class Millisecond : TimeUnit {
    override val timeIntervalRatio = 0.001
}

class Microsecond : TimeUnit {
    override val timeIntervalRatio = 0.000001
}

class Nanosecond : TimeUnit {
    override val timeIntervalRatio = 1e-9
}


val Number.days: Interval<Day>
    get() = Interval(this)

val Number.hours: Interval<Hour>
    get() = Interval(this)

val Number.minutes: Interval<Minute>
    get() = Interval(this)

val Number.seconds: Interval<Second>
    get() = Interval(this)

val Number.milliseconds: Interval<Millisecond>
    get() = Interval(this)

val Number.microseconds: Interval<Microsecond>
    get() = Interval(this)

val Number.nanoseconds: Interval<Nanosecond>
    get() = Interval(this)



