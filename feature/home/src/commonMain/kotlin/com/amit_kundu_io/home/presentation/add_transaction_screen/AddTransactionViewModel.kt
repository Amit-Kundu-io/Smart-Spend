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
import com.amit_kundu_io.database.data.database.dao.TransactionDao
import com.amit_kundu_io.database.data.database.entity.TransactionEntity
import com.amit_kundu_io.database.domain.Repo.TransactionRepository
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import com.amit_kundu_io.utilities.Logger.Logger
import com.amit_kundu_io.utilities.global_utility.PlatformGlobulUtility
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class AddTransactionViewModel(
    private val repo: TransactionRepository,
    private val dao: TransactionDao
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(AddTransactionState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
               // seedIfEmpty()
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
            is AddTransactionAction.LoadTransaction -> {
                loadTransaction(action.id)

            }

            is AddTransactionAction.OnAmountChange -> {
                _state.update { it.copy(amount = action.value) }
            }

            is AddTransactionAction.OnCategoryChange -> {
                _state.update { it.copy(category = action.value) }
            }

            is AddTransactionAction.OnNoteChange -> {
                _state.update { it.copy(note = action.value) }
            }

            is AddTransactionAction.OnPaymentChange -> {
                _state.update { it.copy(paymentMethod = action.value) }
            }

            AddTransactionAction.OnSave -> {
                val currentState = _state.value
                if (state.value.id == null) {
                    val transaction = TransactionEntity(
                        id = PlatformGlobulUtility.generateUUID(),
                        title = currentState.title,
                        amount = currentState.amount.toDoubleOrNull() ?: 0.0,
                        transactionType = currentState.type.value,
                        category = currentState.category,
                        paymentMethod = currentState.paymentMethod,
                        note = currentState.note.ifBlank { null },
                        date = Clock.System.now().toEpochMilliseconds() / 1000
                    )
                    saveTransaction(transaction)

                } else {
                    val transaction = TransactionEntity(
                        id = state.value.id ?: PlatformGlobulUtility.generateUUID(),
                        title = currentState.title,
                        amount = currentState.amount.toDoubleOrNull() ?: 0.0,
                        transactionType = currentState.type.value,
                        category = currentState.category,
                        paymentMethod = currentState.paymentMethod,
                        note = currentState.note.ifBlank { null },
                        date = Clock.System.now().toEpochMilliseconds() / 1000
                    )
                    updateTransaction(transaction)
                }


            }

            is AddTransactionAction.OnTitleChange -> {
                _state.update { it.copy(title = action.value) }
            }

            is AddTransactionAction.OnTypeChange -> {
                _state.update { it.copy(type = action.type) }
            }
        }
    }

    fun saveTransaction(transection: TransactionEntity) {
        viewModelScope.launch {
            try {
                 repo.insert(transection)
                _state.update {  it.copy(isAddSuccessfully = true) }
            }
            catch (e : Exception){

            }
        }
    }

    fun updateTransaction(transection: TransactionEntity) {
        viewModelScope.launch {
            try {
                repo.update(transection)
                _state.update {  it.copy(isAddSuccessfully = true) }
            } catch (e: Exception) {

            }
        }
    }

    private fun loadTransaction(id: String) {
        viewModelScope.launch {
            val action = repo.observeById(id).firstOrNull()
            _state.update {
                it.copy(
                    id = action?.id,
                    type = TransactionType.fromValue(action?.transactionType)
                        ?: TransactionType.EXPENSE,
                    amount = (action?.amount ?: 0.0).toString(),
                    title = action?.title ?: "",
                    note = action?.note ?: "",
                    category = action?.category ?: 100,
                    paymentMethod = action?.paymentMethod ?: 100,
                    isEditMode = true
                )
            }

        }
    }


    suspend fun seedIfEmpty() {
        Logger.d("ScreaptRun","Run1")

        val now = Clock.System.now()

        val transactions = (1..100).map { index ->

            val daysAgo = when {
                index <= 15 -> (0..1).random()      // Today / Yesterday
                index <= 40 -> (2..7).random()      // Last week
                index <= 70 -> (8..30).random()     // Last month
                else -> (31..90).random()           // Older
            }

            val epochSeconds = getPastEpochSeconds(daysAgo) // ✅ 10 digit
            Logger.d("ScreaptRun","indx $index")
            TransactionEntity(
                id = "txn_seed_$index", // 🔥 stable unique id
                title = generateTitle(index),
                amount = generateAmount(index),
                transactionType = generateType(index),
                category = generateCategory(),
                paymentMethod = generatePayment(),
                note = null,
                date = epochSeconds
            )
        }
        Logger.d("ScreaptRun","Run2")

        dao.insertAll(transactions)
    }

    private fun generateTitle(index: Int): String {
        return listOf(
            "Food", "Groceries", "Fuel", "Shopping",
            "Recharge", "Salary", "Bills"
        )[index % 7]
    }

    private fun generateAmount(index: Int): Double {
        return when (index % 5) {
            0 -> 1200.0
            1 -> 250.0
            2 -> 80.0
            3 -> 560.0
            else -> 999.0
        }
    }

    private fun generateType(index: Int): Int {
        return if (index % 6 == 0)
            TransactionType.INCOME.value
        else
            TransactionType.EXPENSE.value
    }

    private fun generateCategory(): Int {
        return (100..107).random()
    }

    private fun generatePayment(): Int {
        return listOf(100, 101, 102).random()
    }
    fun getPastEpochSeconds(daysAgo: Int): Long {
        val zone = TimeZone.currentSystemDefault()

        return Clock.System.now()
            .toLocalDateTime(zone)
            .date
            .minus(daysAgo, DateTimeUnit.DAY)
            .atStartOfDayIn(zone)
            .toEpochMilliseconds() / 1000
    }
}
