package de.sikeller.tools.metrics.chat.core.model

data class ChatMetric(
    val personMetrics: List<PersonMetric>,
    val timeMetric: TimeMetric
)