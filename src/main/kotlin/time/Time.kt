package time

/**
 * Created by Kizito Nwose on 14/10/2017
 */

//region Number
val Number.seconds: Interval<Second>
    get() = Interval(this.toDouble())

val Number.minutes: Interval<Minute>
    get() = Interval(this.toDouble())

val Number.milliseconds: Interval<Millisecond>
    get() = Interval(this.toDouble())

val Number.microseconds: Interval<Microsecond>
    get() = Interval(this.toDouble())

val Number.nanoseconds: Interval<Nanosecond>
    get() = Interval(this.toDouble())

val Number.hours: Interval<Hour>
    get() = Interval(this.toDouble())

val Number.days: Interval<Day>
    get() = Interval(this.toDouble())
//endregion


class Interval<out T : TimeUnit>(val value: Double , val factory: () -> T) {

    val inSeconds: Interval<Second>
        get() = converted()

    val inMinutes: Interval<Minute>
        get() = converted()

    val inMilliseconds: Interval<Millisecond>
        get() = converted()

    val inMicroseconds: Interval<Microsecond>
        get() = converted()

    val inNanoseconds: Interval<Nanosecond>
        get() = converted()

    val inHours: Interval<Hour>
        get() = converted()

    val inDays: Interval<Day>
        get() = converted()

    inline fun <reified OtherUnit : TimeUnit> converted(): Interval<OtherUnit> {
        val otherInstance = OtherUnit::class.java.newInstance()
        return Interval(value * factory().conversionRate(otherInstance))
    }
//    inline fun <reified OtherUnit : TimeUnit> converted(otherTimeUnit: OtherUnit): Interval<OtherUnit> {
//        return Interval(value * factory().conversionRate(otherTimeUnit))
//    }
    companion object {
        inline operator fun <reified K : TimeUnit> invoke(value: Double) = Interval(value) {
            K::class.java.newInstance()
        }
    }

    operator fun plus(other: Interval<TimeUnit>): Interval<T> {
        val newValue = value + other.value * other.factory().conversionRate(factory())
        return Interval(newValue){return@Interval factory()}
    }

    operator fun minus(other: Interval<TimeUnit>): Interval<T> {
        val newValue = value - other.value * other.factory().conversionRate(factory())
        return Interval(newValue){return@Interval factory()}
    }

    operator fun times(other: Number): Interval<T> {
        return Interval(value * other.toDouble()){return@Interval factory()}
    }

    operator fun div(other: Number): Interval<T> {
        return Interval(value / other.toDouble()){return@Interval factory()}
    }
}

interface TimeUnit {
    val timeIntervalRatio: Double
    fun <OtherUnit : TimeUnit> conversionRate(otherTimeUnit: OtherUnit): Double {
        return timeIntervalRatio / otherTimeUnit.timeIntervalRatio
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
    override val timeIntervalRatio =  0.000001
}

class Nanosecond : TimeUnit {
    override val timeIntervalRatio = 1e-9
}

