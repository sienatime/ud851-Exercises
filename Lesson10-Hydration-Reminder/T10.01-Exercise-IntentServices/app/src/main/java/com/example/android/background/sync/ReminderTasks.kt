package com.example.android.background.sync

import android.content.Context
import com.example.android.background.utilities.PreferenceUtilities

internal class ReminderTasks {
    companion object {
        const val actionWaterIncrementCount = "actionWaterIncrementCount"

        fun executeTask(context: Context, action: String) {
            if (action.equals(actionWaterIncrementCount)) {
                incrementWaterCount(context)
            }
        }

        private fun incrementWaterCount(context: Context) {
            PreferenceUtilities.incrementWaterCount(context)
        }
    }
}
