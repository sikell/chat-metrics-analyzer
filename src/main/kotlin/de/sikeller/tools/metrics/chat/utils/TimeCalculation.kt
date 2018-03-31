package de.sikeller.tools.metrics.chat.utils

class TimeCalculation {

    fun <A> run(operation: () -> A): OperationResult<A> {
        val now = System.currentTimeMillis()
        val result = operation.invoke()
        val then = System.currentTimeMillis()
        return OperationResultImpl<A>(result, then - now)
    }
}