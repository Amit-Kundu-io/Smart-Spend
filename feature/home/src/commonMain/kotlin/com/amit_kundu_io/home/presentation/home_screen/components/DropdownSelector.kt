/**
 * DropdownSelector.kt
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun DropdownSelector(
    label: String,
    items: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val safeIndex = selectedIndex.coerceIn(0, items.lastIndex)

    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(Modifier.height(4.dp))

        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(items[safeIndex])
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(0.dp, 4.dp),
                modifier = Modifier.heightIn(max = 200.dp)
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onSelect(index)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}