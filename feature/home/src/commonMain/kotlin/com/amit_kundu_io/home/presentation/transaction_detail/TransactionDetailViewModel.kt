/**
 * TransactionDetailViewModel.kt
 *
 * Author      : Amit Kundu
 * Created On  : 04/04/2026
 *
 * Description :
 * Part of the project codebase. This file contributes to the overall
 * functionality and follows standard coding practices and architecture.
 *
 * Notes :
 * Ensure changes are consistent with project guidelines and maintain
 * code readability and quality.
 */

package com.amit_kundu_io.home.presentation.transaction_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit_kundu_io.database.domain.Repo.TransactionRepository
import com.amit_kundu_io.home.utility.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionDetailViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(TransactionDetailState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = TransactionDetailState()
        )

    fun onAction(action: TransactionDetailAction) {
        when (action) {
            is TransactionDetailAction.ObserveTransaction -> {
                observeTransaction(action.id)
            }

            is TransactionDetailAction.OnDelete -> {
                delete(action.id)
            }
        }
    }

    fun observeTransaction(id: String) {
        repository.observeById(id)
            .onEach { txn ->
                _state.update { it ->
                    it.copy(data = txn?.toUi())
                }
            }
            .launchIn(viewModelScope)
    }

    fun delete(id: String) {
        viewModelScope.launch {
            try {
                repository.deleteById(id = id)
                _state.update { it.copy(deleteSuccess = true) }

            } catch (e: Exception) {

            }
        }
    }

}