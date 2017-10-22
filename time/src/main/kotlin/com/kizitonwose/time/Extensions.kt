package com.kizitonwose.time

import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate

/**
 * Created by Kizito Nwose on 15/10/2017
 */

//region Calendar
operator fun Calendar.plus(other: Interval<TimeUnit>): Calendar {
    val newCalendar = clone() as Calendar
    newCalendar.add(Calendar.MILLISECOND, other.inMilliseconds.longValue.toInt())
    return newCalendar
}

operator fun Calendar.minus(other: Interval<TimeUnit>): Calendar {
    val newCalendar = clone() as Calendar
    newCalendar.add(Calendar.MILLISECOND, -other.inMilliseconds.longValue.toInt())
    return newCalendar
}
//endregion


//region Thread
fun Thread.sleep(interval: Interval<TimeUnit>)
        = Thread.sleep(interval.inMilliseconds.longValue)
//endregion


//region Java Timer
fun Timer.schedule(task: TimerTask, period: Interval<TimeUnit>)
        = schedule(task, period.inMilliseconds.longValue)

fun Timer.schedule(task: TimerTask, delay: Interval<TimeUnit>, period: Interval<TimeUnit>)
        = schedule(task, delay.inMilliseconds.longValue, period.inMilliseconds.longValue)

fun Timer.schedule(task: TimerTask, firstTime: Date, period: Interval<TimeUnit>)
        = schedule(task, firstTime, period.inMilliseconds.longValue)

fun Timer.scheduleAtFixedRate(task: TimerTask, delay: Interval<TimeUnit>, period: Interval<TimeUnit>)
        = scheduleAtFixedRate(task, delay.inMilliseconds.longValue, period.inMilliseconds.longValue)

fun Timer.scheduleAtFixedRate(task: TimerTask, firstTime: Date, period: Interval<TimeUnit>)
        = scheduleAtFixedRate(task, firstTime, period.inMilliseconds.longValue)
//endregion


//region Kotlin Timer
inline fun Timer.schedule(delay: Interval<TimeUnit>, crossinline action: TimerTask.() -> Unit)
        = schedule(delay.inMilliseconds.longValue, action)

inline fun Timer.schedule(delay: Interval<TimeUnit>, period: Interval<TimeUnit>, crossinline action: TimerTask.() -> Unit)
        = schedule(delay.inMilliseconds.longValue, period.inMilliseconds.longValue, action)

inline fun Timer.schedule(time: Date, period: Interval<TimeUnit>, crossinline action: TimerTask.() -> Unit)
        = schedule(time, period.inMilliseconds.longValue, action)

inline fun Timer.scheduleAtFixedRate(delay: Interval<TimeUnit>, period: Interval<TimeUnit>, crossinline action: TimerTask.() -> Unit)
        = scheduleAtFixedRate(delay.inMilliseconds.longValue, period.inMilliseconds.longValue, action)

inline fun Timer.scheduleAtFixedRate(time: Date, period: Interval<TimeUnit>, crossinline action: TimerTask.() -> Unit)
        = scheduleAtFixedRate(time, period.inMilliseconds.longValue, action)
//endregion
