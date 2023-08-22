package com.ksurtel.heat_map

class Records(
    private val records: List<Record>
) {
    private val mergedRecords = mergeRecordsByDate()
    val weeks = recordsToWeeks()

    fun getValuesRange(): Pair<Double, Double> {
        val min = mergedRecords.minBy { it.value }
        val max = mergedRecords.maxBy { it.value }
        return Pair(min.value, max.value)
    }

    private fun mergeRecordsByDate(): List<Record> {
        val mergedRecords = mutableListOf<Record>()
        records.sortedByDescending { it.date }.forEach { record ->
            if (mergedRecords.isEmpty() || !mergedRecords.last().date.isEqual(record.date))
                mergedRecords.add(record)
            else if(mergedRecords.last().date.isEqual(record.date))
                mergedRecords.last().value += record.value
        }
        return mergedRecords
    }

    private fun recordsToWeeks(): List<Week> {
        val weeks = mutableListOf<Week>()
        mergedRecords.forEach {
            val dayOfWeek = it.date.dayOfWeek.value.toLong()
            val monday = it.date.minusDays(dayOfWeek - 1)
            if (weeks.isEmpty()) weeks.add(Week(monday))
            else if (!weeks.last().firstDayOfTheWeek.isEqual(monday)) {
                var weeksLastMon = weeks.last().firstDayOfTheWeek
                do {
                    weeksLastMon = weeksLastMon.minusWeeks(1)
                    weeks.add(Week(weeksLastMon))
                } while (!weeksLastMon.isEqual(monday))
            }
            weeks.last().records.add(it)
        }
        //if (weeks.size < 15) addFauxWeeks(weeks)
        return weeks
    }

    private fun addFauxWeeks(weeks: MutableList<Week>) {
        repeat(15 - weeks.size) {
            weeks.add(Week(weeks.last().firstDayOfTheWeek.minusWeeks(1)))
        }
    }
}