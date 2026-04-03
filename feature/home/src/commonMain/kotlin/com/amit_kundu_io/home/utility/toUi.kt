/**
 * toUi.kt
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

package com.amit_kundu_io.home.utility

import com.amit_kundu_io.database.data.database.TransactionEntity
import com.amit_kundu_io.theme.Transaction
import com.amit_kundu_io.theme.utility.ThemeUtility.mapCategory
import com.amit_kundu_io.utilities.Data_Models.Category
import com.amit_kundu_io.utilities.Data_Models.PaymentMethod
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import com.amit_kundu_io.utilities.global_utility.GlobalUtility

fun TransactionEntity.toUi(): Transaction {


    val (emoji, color) = mapCategory(Category.fromId(category ?: 100).label)

    return Transaction(
        id = id,
        title = title,
        category = category ?: Category.FOOD.id,
        categoryEmoji = emoji,
        categoryColor = color,
        amount = amount,
        type = TransactionType.fromValue(type),
        date = GlobalUtility.epochToSmartDate(date),
        time = GlobalUtility.epochToTime(date),
        paymentMethod = PaymentMethod.fromId(paymentMethod)
    )
}