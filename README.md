# heat-map 
[![](https://jitpack.io/v/kasiasurtel/heat-map.svg)](https://jitpack.io/#kasiasurtel/heat-map)  
![alt text](https://github.com/kasiasurtel/heat-map/blob/master/preview/heat-map-previews.jpg "Previews")  
This library provides a visual way of representing data in a form of daily heatmap. 
## Getting started
### Kotlin
In your project's gradle settings (`settings.gradle.kts`) add following.
```kotlin
repositories {
  maven { url = uri("https://jitpack.io") }
}
```
Next, add dependency in your module's gradle file (`build.gradle.kts`).
```kotlin
dependencies {
  implementation("com.github.kasiasurtel:heat-map:1.0.1")
}
```
### Groovy
Add the following code to your project's root `build.gradle` file.
```groovy
repositories {
  maven { url "https://jitpack.io" }
}
```
Then add the project dependency in module's `build.gradle` file.
```groovy
dependencies {
  implementation 'com.github.kasiasurtel:heat-map:1.0.1'
}
```
## How to use
Configure properties using `Properties` class to match your project's design. All properties are optional.
```kotlin
 val properties = Properties(
  squareSideLength = 30.dp,
  squaresPadding = 1.dp,
  weekLabelsPosition = Position.RIGHT_FIXED,
  roundedCornerSize = 1.dp,
  showDayNumber = false,
  inactiveDayColor = Color(0xFFF6F6FF),
  activeDayMinColor = Color(0xffefb7db),
  activeDayMaxColor = Color(0xFFA82F7D)
)
```
Display composable `HeatMap` on your screen.
```kotlin
HeatMap(
  properties = properties,
  records = records,
  onSquareClick = {
    // replace with your code
    Toast.makeText(applicationContext, it.value.toString(), Toast.LENGTH_SHORT).show()
  }
)
```
