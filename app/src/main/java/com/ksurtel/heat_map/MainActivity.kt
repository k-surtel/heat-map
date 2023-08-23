package com.ksurtel.heat_map

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ksurtel.heat_map.ui.theme.HeatmapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val records = getRecords()

        setContent {
            HeatmapTheme {
                val properties1 = Properties()

                val isDarkTheme = isSystemInDarkTheme()
                val inactiveDayColor = if (isDarkTheme) Color(0xFF04060C) else Color(0xFFF6F6FF)
                val activeDayMinColor = if (isDarkTheme) Color(0xFF141238) else Color(0xffefb7db)
                val activeDayMaxColor = if (isDarkTheme) Color(0xFF0810AD) else Color(0xFFA82F7D)

                val properties2 = Properties(
                    squareSideLength = 30.dp,
                    squaresPadding = 1.dp,
                    weekLabelsPosition = Position.LEFT_FIXED,
                    roundedCornerSize = 1.dp,
                    showDayNumber = false,
                    inactiveDayColor = inactiveDayColor,
                    activeDayMinColor = activeDayMinColor,
                    activeDayMaxColor = activeDayMaxColor
                )

                val activeDayMinColor2 = if (isDarkTheme) Color(0xFF3F635F) else Color(0xFFFD9F3A)
                val activeDayMaxColor2 = if (isDarkTheme) Color(0xFF8A064A) else Color(0xFF7E0753)

                val properties3 = Properties(
                    squareSideLength = 20.dp,
                    squaresPadding = 0.5.dp,
                    weekLabelsPosition = Position.RIGHT,
                    roundedCornerSize = 10.dp,
                    fontSize = 10.sp,
                    inactiveDayColor = MaterialTheme.colorScheme.background,
                    activeDayMinColor = activeDayMinColor2,
                    activeDayMaxColor = activeDayMaxColor2
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            HeatMap(
                                properties = properties1,
                                records = records,
                                onSquareClick = {
                                    Toast.makeText(
                                        applicationContext,
                                        it.value.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                            HeatMap(
                                properties = properties2,
                                records = records,
                                onSquareClick = {
                                    Toast.makeText(
                                        applicationContext,
                                        it.value.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                            HeatMap(
                                modifier = Modifier.absolutePadding(top = 10.dp),
                                properties = properties3,
                                records = records,
                                onSquareClick = {
                                    Toast.makeText(
                                        applicationContext,
                                        it.value.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}