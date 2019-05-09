# Time

[![JitPack](https://jitpack.io/v/kizitonwose/Time.svg)](https://jitpack.io/#kizitonwose/Time) 
[![Build Status](https://travis-ci.org/kizitonwose/Time.svg?branch=master)](https://travis-ci.org/kizitonwose/Time) 
[![Coverage](https://img.shields.io/codecov/c/github/kizitonwose/Time/master.svg)](https://codecov.io/gh/kizitonwose/Time) 
[![Codacy](https://api.codacy.com/project/badge/Grade/26b009748a0849f3973887fbbcd84900)](https://www.codacy.com/app/kizitonwose/Time) 
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://github.com/kizitonwose/Time/blob/master/LICENSE.md) 

This library is made for you if you have ever written something like this: 

```kotlin
val duration = 10 * 1000
```
to represent a duration of 10 seconds(in milliseconds) because most methods in Kotlin/Java take duration parameters in milliseconds.

## Usage

### Showcase

```kotlin
val tenSeconds = 10.seconds
val fiveMinutes = 5.minutes
val twoHours = 2.hours
val threeDays = 3.days
val tenMinutesFromNow = Calendar.getInstance() + 10.minutes
val tenSecondsInMilliseconds = 10.seconds.inMilliseconds
```

### Basics

The main advantage of the library is that all time units are *strongly-typed*. So, for example:

```kotlin
val tenMinutes = 10.minutes
```

In the example above,  `tenMinutes` will be of type `Interval<Minute>`. There are seven time units available, from nanoseconds to days:

```kotlin
val tenNanoseconds = 10.nanoseconds 
// type is Interval<Nanosecond>
```
```kotlin
val tenMicroseconds = 10.microseconds 
// type is Interval<Microsecond>
```
```kotlin
val tenMilliseconds = 10.milliseconds 
// type is Interval<Millisecond>
```
```kotlin
val tenSeconds = 10.seconds 
// type is Interval<Second>
```
```kotlin
val tenMinutes = 10.minutes 
// type is Interval<Minute>
```
```kotlin
val tenHours = 10.hours 
// type is Interval<Hour>
```
```kotlin
val tenDays = 10.days 
// type is Interval<Day>
```

### Operations

You can perform all basic arithmetic operations on time intervals, even of different units:

```kotlin
val duration = 10.minutes + 15.seconds - 3.minutes + 2.hours // Interval<Minute>
val doubled = duration * 2

val seconds = 10.seconds + 3.minutes // Interval<Second>
```

You can also use these operations with the `Calendar` class:

```kotlin
val twoHoursLater = Calendar.getInstance() + 2.hours
```

### Conversions

Time intervals are easily convertible:

```kotlin
val twoMinutesInSeconds = 2.minutes.inSeconds // Interval<Second>
val fourDaysInHours = 4.days.inHours // Interval<Hour>
```

You can also use the `converted()` method, although you would rarely need to:

```kotlin
val tenMinutesInSeconds: Interval<Second> = 10.minutes.converted()
```

### Comparison

You can compare different time units as well:

```kotlin
50.seconds < 2.hours // true
120.minutes == 2.hours // true
100.milliseconds > 2.seconds // false
48.hours in 2.days // true
```

### Creating your own time units

If, for some reason, you need to create your own time unit, that's super easy to do:

```kotlin
class Week : TimeUnit {
    // number of seconds in one week
    override val timeIntervalRatio = 604800.0
}
```

Now you can use it like any other time unit:

```kotlin
val fiveWeeks = Interval<Week>(5)
```

For the sake of convenience, don't forget to write those handy extensions:

```kotlin
class Week : TimeUnit {
    override val timeIntervalRatio = 604800.0
}

val Number.weeks: Interval<Week>
    get() = Interval(this)

val Interval<TimeUnit>.inWeeks: Interval<Week>
    get() = converted()
```
Now you can write:

```kotlin
val fiveWeeks = 5.weeks // Interval<Week>
```
You can also easily convert to weeks:

```kotlin
val valueInWeeks = 14.days.inWeeks // Interval<Week>
```

### Extras

The library includes some handy extensions for some classes:

```kotlin
val now = Calendar.getInstance() 
val sixHoursLater = now + 6.hours
val fourDaysAgo = now - 4.days
```

```kotlin
val timer = Timer()
timer.schedule(10.seconds) {
    println("This block will be called in 10 seconds")
}
```

The library also includes extensions for Android's Handler class, this is only available if you compile the "time-android" module.

```kotlin
val handler = Handler()
handler.postDelayed({
    Log.i("TAG", "This will be printed to the Logcat in 2 minutes")
}, 2.minutes)
```
More extensions will be added to the library in the future.

### Conversion safety everywhere

For time-related methods in other third-party libraries in your project, if such methods are frequently used, it's best to write extention functions that let you use the time units in this libary in those methods. This is mostly just one line of code. 

If such methods aren't frequently used, you can still benefit from the conversion safety that comes with this library.

An example method in a third-party library that does something after a delay period in milliseconds:

```kotlin
class Person {
    fun doSomething(delayMillis: Long) {
        // method body
    }
}
```

To call the method above with a value of 5 minutes, one would usually write:

```kotlin
val person = Person()
person.doSomething(5 * 60 * 1000)
```

The above line can be written in a safer and clearer way using this library:

```kotlin
val person = Person()
person.doSomething(5.minutes.inMilliseconds.longValue)
```

If the method is frequently used, you can write an extension function:

```kotlin
fun Person.doSomething(delay: Interval<TimeUnit>) {
    doSomething(delay.inMilliseconds.longValue)
}
```
Now you can write:

```kotlin
val person = Person()
person.doSomething(5.minutes)
```

## Installation

Add the JitPack repository to your `build.gradle`:

```groovy
allprojects {
 repositories {
    maven { url "https://jitpack.io" }
    }
}
```

Add the dependency to your `build.gradle`:

- For **non-Android** projects:

```groovy
dependencies {
    compile 'com.github.kizitonwose.time:time:<version>'
}
```

- For **Android** projects:

```groovy
dependencies {
    compile 'com.github.kizitonwose.time:time-android:<version>'
}
```

## Contributing
The goal is for the library to be used wherever possible. If there are extension functions or features you think the library should have, feel free to add them and send a pull request or open an issue. Core Kotlin extensions belong in the "[time][time-core-module-url]" module while Android extensions belong in "[time-android][time-android-module-url]" module.


## Inspiration
Time was inspired by a Swift library of the same name - [Time][time-swift-url].

The API of Time(Kotlin) has been designed to be as close as possible to Time(Swift) for consistency and because the two languages have many similarities. Check out [Time(Swift)][time-swift-url] if you want a library with the same functionality in Swift.


## License
Time is distributed under the MIT license. [See LICENSE](https://github.com/kizitonwose/Time/blob/master/LICENSE.md) for details.


[time-swift-url]: https://github.com/dreymonde/Time 
[time-core-module-url]: https://github.com/kizitonwose/Time/tree/master/time 
[time-android-module-url]: https://github.com/kizitonwose/Time/tree/master/time-android
