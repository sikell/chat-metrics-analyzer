package de.sikeller.tools.metrics.chat.utils

data class OperationResultImpl<out A>(
    private val data: A,
    private val duration: Long
) : OperationResult<A> {
    override fun get(): A = data

    override fun duration(): Long = duration
}