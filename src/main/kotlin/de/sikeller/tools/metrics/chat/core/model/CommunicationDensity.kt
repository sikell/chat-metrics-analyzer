package de.sikeller.tools.metrics.chat.core.model

import java.util.*

data class CommunicationDensity(
    val countPerDay: Map<Date, Int>,
    val countPerMonth: Map<Date, Int>,
    val countPerYear: Map<Date, Int>,
    val countPerTimeHour: Map<Int, Int>
)