package time

/**
 * Created by Kizito Nwose on 14/10/2017
 */

//region Float
val Float.seconds: Interval<Second>
    get() = Interval(this.toDouble())

val Float.minutes: Interval<Minute>
    get() = Interval(this.toDouble())

val Float.milliseconds: Interval<Millisecond>
    get() = Interval(this.toDouble())

val Float.microseconds: Interval<Microsecond>
    get() = Interval(this.toDouble())

val Float.nanoseconds: Interval<Nanosecond>
    get() = Interval(this.toDouble())

val Float.hours: Interval<Hour>
    get() = Interval(this.toDouble())

val Float.days: Interval<Day>
    get() = Interval(this.toDouble())
//endregion


//region Double
val Double.seconds: Interval<Second>
    get() = Interval(this)

val Double.minutes: Interval<Minute>
    get() = Interval(this)

val Double.milliseconds: Interval<Millisecond>
    get() = Interval(this)

val Double.microseconds: Interval<Microsecond>
    get() = Interval(this)

val Double.nanoseconds: Interval<Nanosecond>
    get() = Interval(this)

val Double.hours: Interval<Hour>
    get() = Interval(this)

val Double.days: Interval<Day>
    get() = Interval(this)
//endregion


//region Int
val Int.seconds: Interval<Second>
    get() = Interval(this.toDouble())

val Int.minutes: Interval<Minute>
    get() = Interval(this.toDouble())

val Int.milliseconds: Interval<Millisecond>
    get() = Interval(this.toDouble())

val Int.microseconds: Interval<Microsecond>
    get() = Interval(this.toDouble())

val Int.nanoseconds: Interval<Nanosecond>
    get() = Interval(this.toDouble())

val Int.hours: Interval<Hour>
    get() = Interval(this.toDouble())

val Int.days: Interval<Day>
    get() = Interval(this.toDouble())
//endregion


//region Long
val Long.seconds: Interval<Second>
    get() = Interval(this.toDouble())

val Long.minutes: Interval<Minute>
    get() = Interval(this.toDouble())

val Long.milliseconds: Interval<Millisecond>
    get() = Interval(this.toDouble())

val Long.microseconds: Interval<Microsecond>
    get() = Interval(this.toDouble())

val Long.nanoseconds: Interval<Nanosecond>
    get() = Interval(this.toDouble())

val Long.hours: Interval<Hour>
    get() = Interval(this.toDouble())

val Long.days: Interval<Day>
    get() = Interval(this.toDouble())
//endregion

class Interval<out T : TimeUnit>(val value: Double, private val factory: () -> T) {

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

    private inline fun <reified OtherUnit : TimeUnit> converted(): Interval<OtherUnit> {
        val otherInstance = OtherUnit::class.java.newInstance()
        return Interval(value * factory().conversionRate(otherInstance))
    }

    companion object {
        inline operator fun <reified K : TimeUnit> invoke(value: Double) = Interval(value) {
            K::class.java.newInstance()
        }
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
