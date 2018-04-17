package de.sikeller.tools.metrics.chat.core.model

import java.util.*

data class TimeMetric(
    val communicationDensity: Map<Date, Int>
)