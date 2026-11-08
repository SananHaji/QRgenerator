package com.sananhaji.qrgenerator.review

import android.content.Context
import androidx.core.content.edit

/**
 * Decides when to ask for a Play rating: once, after the user has saved enough codes
 * to have got real value from the app. Play applies its own quota on top of this.
 */
class ReviewEligibility(context: Context) {

    private val preferences = context.applicationContext.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE,
    )

    fun recordSave() {
        preferences.edit { putInt(KEY_SAVE_COUNT, preferences.getInt(KEY_SAVE_COUNT, 0) + 1) }
    }

    /** True the first time the save count reaches [SAVES_BEFORE_REVIEW]; false ever after. */
    fun takeReviewRequest(): Boolean {
        val due = !preferences.getBoolean(KEY_ASKED, false) &&
            preferences.getInt(KEY_SAVE_COUNT, 0) >= SAVES_BEFORE_REVIEW
        if (due) preferences.edit { putBoolean(KEY_ASKED, true) }
        return due
    }

    private companion object {

        const val PREFERENCES_NAME = "review"

        const val KEY_SAVE_COUNT = "save_count"

        const val KEY_ASKED = "asked"

        const val SAVES_BEFORE_REVIEW = 2
    }
}
