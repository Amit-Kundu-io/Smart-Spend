/**
 * AddTransactionViewModel.kt
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

package com.amit_kundu_io.home.presentation.add_transaction_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit_kundu_io.database.data.database.TransactionEntity
import com.amit_kundu_io.database.domain.Repo.TransactionRepository
import com.amit_kundu_io.home.utility.toEntity
import com.amit_kundu_io.theme.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val repo: TransactionRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(AddTransactionState())
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
            initialValue = AddTransactionState()
        )

    fun onAction(action: AddTransactionAction) {
        when (action) {
            is AddTransactionAction.SaveTransaction -> {
                saveTransaction(action.transaction)
            }
        }
    }


    fun saveTransaction(transection: TransactionEntity) {
        viewModelScope.launch {
            repo.insert(transection)
        }
    }

}