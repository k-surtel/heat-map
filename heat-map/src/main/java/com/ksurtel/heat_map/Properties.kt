package com.ksurtel.heat_map

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Properties(
    val squareSideLength: Dp = 25.dp,
    val squaresPadding: Dp = 3.dp,
    val weekLabelsPosition: Position = Position.RIGHT_FIXED,
    val roundedCornerSize: Dp = 5.dp,
    val fontSize: TextUnit = 12.sp,
    val showDayNumber: Boolean = true,

    val labelsFontColor: Color = Color(0xFF8f8f8f),
    val inactiveDayColor: Color = Color(0x80E2E2E2),
    val activeDayMinColor: Color = Color(0xFFFFEF8A),
    val activeDayMaxColor: Color = Color(0xFF0FB104)
)

enum class Position {
    LEFT_FIXED, RIGHT_FIXED, RIGHT
}