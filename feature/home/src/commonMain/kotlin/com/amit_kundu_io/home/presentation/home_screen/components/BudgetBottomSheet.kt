/**
 * BudgetBottomSheet.kt
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

package com.amit_kundu_io.home.presentation.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.home.presentation.home_screen.HomeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetBottomSheet(
    state: HomeState,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val months = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )
    var amount by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = "Set Monthly Budget",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(16.dp))

            // Month Picker
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(months[state.selectedMonth])
                    }
                }

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(state.selectedYear.toString())
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            //  Amount Input
            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                },
                label = { Text("Budget Amount") },
                prefix = { Text("₹") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            //  Save Button
            Button(
                onClick = { onSave(amount) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Budget")
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}