package com.ksurtel.heat_map

import java.time.LocalDate

data class Week(
    val firstDayOfTheWeek: LocalDate,
    val records: MutableList<Record> = mutableListOf(),
)
