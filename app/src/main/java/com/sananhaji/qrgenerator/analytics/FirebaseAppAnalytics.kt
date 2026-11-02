package com.sananhaji.qrgenerator.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAppAnalytics(context: Context) : AppAnalytics {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context.applicationContext)

    override fun logScreen(screenName: String) {
        val params = Bundle().apply { putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName) }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
    }

    override fun log(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.name, event.params.toBundle())
    }

    private fun Map<String, Any>.toBundle(): Bundle = Bundle().also { bundle ->
        forEach { (key, value) ->
            when (value) {
                is Long -> bundle.putLong(key, value)
                else -> bundle.putString(key, value.toString())
            }
        }
    }
}
