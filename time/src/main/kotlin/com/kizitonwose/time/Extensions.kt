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
    newCalendar.add(Calendar.MILLISECOND, other.inMilliseconds.value.toInt())
    return newCalendar
}

operator fun Calendar.minus(other: Interval<TimeUnit>): Calendar {
    val newCalendar = clone() as Calendar
    newCalendar.add(Calendar.MILLISECOND, -other.inMilliseconds.value.toInt())
    return newCalendar
}
//endregion


//region Thread
fun Thread.sleep(interval: Interval<TimeUnit>)
        = Thread.sleep(interval.inMilliseconds.value.toLong())
//endregion


//region Java Timer
fun Timer.schedule(task: TimerTask, period: Interval<TimeUnit>)
        = schedule(task, period.inMilliseconds.value.toLong())

fun Timer.schedule(task: TimerTask, delay: Interval<TimeUnit>, period: Interval<TimeUnit>)
        = schedule(task, delay.inMilliseconds.value.toLong(), period.inMilliseconds.value.toLong())

fun Timer.schedule(task: TimerTask, firstTime: Date, period: Interval<TimeUnit>)
        = schedule(task, firstTime, period.inMilliseconds.value.toLong())

fun Timer.scheduleAtFixedRate(task: TimerTask, delay: Interval<TimeUnit>, period: Interval<TimeUnit>)
        = scheduleAtFixedRate(task, delay.inMilliseconds.value.toLong(), period.inMilliseconds.value.toLong())

fun Timer.scheduleAtFixedRate(task: TimerTask, firstTime: Date, period: Interval<TimeUnit>)
        = scheduleAtFixedRate(task, firstTime, period.inMilliseconds.value.toLong())
//endregion


//region Kotlin Timer
inline fun Timer.schedule(delay: Interval<TimeUnit>, crossinline action: TimerTask.() -> Unit)
        = schedule(delay.inMilliseconds.value.toLong(), action)

inline fun Timer.schedule(delay: Interval<TimeUnit>, period: Interval<TimeUnit>, crossinline action: TimerTask.() -> Unit)
        = schedule(delay.inMilliseconds.value.toLong(), period.inMilliseconds.value.toLong(), action)

inline fun Timer.schedule(time: Date, period: Interval<TimeUnit>, crossinline action: TimerTask.() -> Unit)
        = schedule(time, period.inMilliseconds.value.toLong(), action)

inline fun Timer.scheduleAtFixedRate(delay: Interval<TimeUnit>, period: Interval<TimeUnit>, crossinline action: TimerTask.() -> Unit)
        = scheduleAtFixedRate(delay.inMilliseconds.value.toLong(), period.inMilliseconds.value.toLong(), action)

inline fun Timer.scheduleAtFixedRate(time: Date, period: Interval<TimeUnit>, crossinline action: TimerTask.() -> Unit)
        = scheduleAtFixedRate(time, period.inMilliseconds.value.toLong(), action)
//endregion
