package com.ksurtel.heat_map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.core.graphics.ColorUtils
import java.time.DayOfWeek
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HeatMap(
    modifier: Modifier = Modifier,
    properties: Properties,
    records: Records,
    onSquareClick: (Record) -> Unit
) {
    val weeks = records.weeks
    val valuesRange = records.getValuesRange()
    val elementsModifier = Modifier
        .height(properties.squareSideLength)
        .padding(properties.squaresPadding)

    Row(modifier = modifier) {
        if (properties.weekLabelsPosition == Position.LEFT_FIXED)
            DayLabels(elementsModifier, properties.fontSize, properties.labelsFontColor)
        LazyRow(
            reverseLayout = true,
            modifier = Modifier.weight(1f)
        ) {
            items(items = weeks) { week ->
                if (properties.weekLabelsPosition == Position.RIGHT && weeks.first() == week)
                    DayLabels(elementsModifier, properties.fontSize, properties.labelsFontColor)
                Column {
                    repeat(7) { dayOfWeek ->
                        if (dayOfWeek == 0)
                            MonthLabel(
                                week = week,
                                latestMonth = weeks[0].firstDayOfTheWeek.month,
                                modifier = elementsModifier,
                                fontSize = properties.fontSize,
                                fontColor = properties.labelsFontColor
                            )
                        val record = week.records.find { it.date.dayOfWeek.value == dayOfWeek + 1 }
                        val color =
                            if (record == null) properties.inactiveDayColor
                            else getColor(
                                record = record,
                                valuesRange = valuesRange,
                                activeDayMinColor = properties.activeDayMinColor,
                                activeDayMaxColor = properties.activeDayMaxColor
                            )
                        DaySquare(
                            modifier = elementsModifier,
                            week = week,
                            dayOfWeek = dayOfWeek,
                            color = color,
                            showDayNumber = properties.showDayNumber,
                            fontSize = properties.fontSize,
                            roundedCornerSize = properties.roundedCornerSize,
                            onSquareClick = onSquareClick
                        )
                    }
                }
            }
        }

        if (properties.weekLabelsPosition == Position.RIGHT_FIXED)
            DayLabels(elementsModifier, properties.fontSize, properties.labelsFontColor)
    }
}

@Composable
private fun DayLabels(modifier: Modifier, fontSize: TextUnit, fontColor: Color) {
    val dayNames = listOf(
        "",
        DayOfWeek.MONDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.TUESDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.THURSDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.FRIDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.SATURDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.SUNDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
    )
    Column {
        dayNames.forEach {
            DayLabel(
                label = it.capitalize(Locale.getDefault()),
                modifier = modifier,
                fontSize = fontSize,
                fontColor = fontColor
            )
        }
    }
}

@Composable
private fun DayLabel(label: String, modifier: Modifier, fontSize: TextUnit, fontColor: Color) {
    Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
        Text(text = label, color = fontColor, fontSize = fontSize)
    }
}

@Composable
private fun MonthLabel(
    week: Week,
    latestMonth: Month,
    modifier: Modifier,
    fontSize: TextUnit,
    fontColor: Color
) {
    Box(
        modifier = modifier
            .aspectRatio(1F)
            .wrapContentSize(unbounded = true, align = Alignment.BottomStart)
    ) {
        val month = week.firstDayOfTheWeek.month
        var text = ""
        if (month != week.firstDayOfTheWeek.minusWeeks(1).month) {
            text += month.getDisplayName(TextStyle.SHORT, Locale.getDefault()).capitalize(Locale.getDefault())
            if (latestMonth == month || month.value == 12) text += " ${week.firstDayOfTheWeek.year}"
        }
        Text(text = text, color = fontColor, fontSize = fontSize)
    }
}

@Composable
private fun DaySquare(
    modifier: Modifier,
    week: Week,
    dayOfWeek: Int,
    color: Color,
    showDayNumber: Boolean,
    fontSize: TextUnit,
    roundedCornerSize: Dp,
    onSquareClick: (Record) -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(roundedCornerSize))
            .background(color)
            .clickable {
                val date = week.firstDayOfTheWeek.plusDays(dayOfWeek.toLong())
                val record = week.records.find { it.date == date }
                if (record != null) onSquareClick(record)
                else onSquareClick(Record(date, 0.0))
            },
        contentAlignment = Alignment.Center
    ) {
        if (showDayNumber) {
            val text = week.firstDayOfTheWeek.plusDays(dayOfWeek.toLong()).dayOfMonth.toString()
            val backgroundLuminance = ColorUtils.calculateLuminance(color.hashCode())
            val textColor =
                if (backgroundLuminance > 0.5) Color(0xFF363636) else Color.White
            Text(text = text, color = textColor, fontSize = fontSize)
        }
    }
}

private fun getColor(
    record: Record,
    valuesRange: Pair<Double, Double>,
    activeDayMinColor: Color,
    activeDayMaxColor: Color
): Color {
    val fraction =
        ((100 * (record.value - valuesRange.first)) / (valuesRange.second - valuesRange.first)) / 100
    return lerp(activeDayMinColor, activeDayMaxColor, fraction.toFloat())
}



