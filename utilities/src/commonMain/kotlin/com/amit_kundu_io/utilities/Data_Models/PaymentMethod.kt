/**
 * PaymentMethod.kt
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

package com.amit_kundu_io.utilities.Data_Models


enum class PaymentMethod(val value: Int, val icon: String, val label: String) {

        UPI(100, "💳", "UPI"),
        CARD(101, "🏦", "Card"),
        CASH(102, "💵", "Cash");

        companion object {
            private val map = values().associateBy(PaymentMethod::value)

            fun fromId(id: Int): PaymentMethod? = map[id]
            fun fromName(name: String): PaymentMethod? =
                values().find { it.label.equals(name, ignoreCase = true) }

            // Here’s your list for UI
            val all: List<PaymentMethod> = values().toList()
        }

        fun displayText(): String = "$icon $label"
    }

