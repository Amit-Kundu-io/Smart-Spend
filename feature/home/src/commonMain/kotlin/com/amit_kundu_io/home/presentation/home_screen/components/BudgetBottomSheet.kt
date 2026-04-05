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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amit_kundu_io.home.presentation.home_screen.HomeState
import com.amit_kundu_io.theme.ui.GradientEnd

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
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = Color.White,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 8.dp)
                    .size(width = 40.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFFE8E8F0))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {

            // ── Header ─────────────────────────────────────────────────────
            Text(
                text = "Set Monthly Budget",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A2E)
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Choose period and enter your limit",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF8A94A6)
                )
            )

            Spacer(Modifier.height(24.dp))

            // ── Section label ──────────────────────────────────────────────
            Text(
                text = "PERIOD",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color(0xFF8A94A6),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.08.sp
                )
            )
            Spacer(Modifier.height(8.dp))

            // ── Month / Year row ───────────────────────────────────────────
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .weight(1.6f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = months[state.selectedMonth],
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                            tint = Color(0xFF8A94A6),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFE8E8F0)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF1A1A2E)
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.selectedYear.toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Icon(
                            Icons.Filled.ArrowDropDown, null, Modifier.size(18.dp), Color(0xFF8A94A6)
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Section label ──────────────────────────────────────────────
            Text(
                text = "BUDGET AMOUNT",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color(0xFF8A94A6),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.08.sp
                )
            )
            Spacer(Modifier.height(8.dp))

            // ── Amount Input ───────────────────────────────────────────────
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                placeholder = {
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Color(0xFFC0C0CC)
                        )
                    )
                },
                prefix = {
                    Text(
                        text = "₹ ",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = if (amount.isNotEmpty()) GradientEnd
                            else Color(0xFFC0C0CC),
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                textStyle = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A2E)
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                shape = RoundedCornerShape(16.dp),
            )

            Spacer(Modifier.height(28.dp))

            // ── Save Button ────────────────────────────────────────────────
            Button(
                onClick = { onSave(amount) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                enabled = amount.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GradientEnd,
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFE8E8F0),
                    disabledContentColor = Color(0xFF8A94A6)
                )
            ) {
                Text(
                    text = "Save Budget",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Spacer(Modifier.height(10.dp))

            // ── Cancel ─────────────────────────────────────────────────────
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF8A94A6)
                    )
                )
            }
        }
    }
}