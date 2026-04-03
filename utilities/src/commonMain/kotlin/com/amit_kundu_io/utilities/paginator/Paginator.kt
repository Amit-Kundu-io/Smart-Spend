/**
 * Paginator.kt
 *
 * Author      : Amit Kundu
 * Created On  : 03/04/2026
 *
 * Description :
 * Part of the project codebase. This file contributes to the overall
 * functionality and follows standard coding practices and architecture.
 *
 * Notes :
 * Ensure changes are consistent with project guidelines and maintain
 * code readability and quality.
 */

package com.amit_kundu_io.utilities.paginator

import kotlin.concurrent.Volatile

class Paginator<Key, Item>(
    private val initialKey: Key,
    private val request: suspend (Key) -> Item,
    private val getNextKey: (Key, Item) -> Key,
    private val isEndReached: (Key, Item) -> Boolean
) {

    private var currentKey: Key = initialKey

    @Volatile
    private var isLoading: Boolean = false

    private var endReached: Boolean = false

    suspend fun loadNext(
        onLoading: (Boolean) -> Unit,
        onSuccess: (Item) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        if (isLoading || endReached) return

        isLoading = true
        onLoading(true)

        try {
            val result = request(currentKey)

            endReached = isEndReached(currentKey, result)
            currentKey = getNextKey(currentKey, result)

            onSuccess(result)

        } catch (e: Throwable) {
            onError(e)
        }

        isLoading = false
        onLoading(false)
    }

    fun reset() {
        currentKey = initialKey
        endReached = false
        isLoading = false
    }
}