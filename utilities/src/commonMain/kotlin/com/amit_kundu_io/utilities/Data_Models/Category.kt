/**
 * Category.kt
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

enum class Category(val id: Int, val key: String, val icon: String, val label: String) {
    FOOD(100, "food", "🍽", "Food"),
    TRANSPORT(101, "transport", "🚗", "Transport"),
    SHOPPING(102, "shopping", "🛍", "Shopping"),
    UTILITIES(103, "util", "⚡", "Utilities"),
    HEALTH(104, "health", "💊", "Health"),
    ENTERTAINMENT(105, "ent", "🎮", "Entertainment"),
    HOUSING(106, "housing", "🏠", "Housing"),
    EDUCATION(107, "edu", "📚", "Education");

    companion object {
        private val map = values().associateBy(Category::id)

        fun fromId(id: Int): Category = map[id] ?: Category.FOOD

        val all = values().toList()
    }
}
