package com.sananhaji.qrgenerator.analytics

/** Usage reporting the screens and view models depend on, so they never touch the Firebase SDK. */
interface AppAnalytics {

    fun logScreen(screenName: String)

    fun log(event: AnalyticsEvent)
}
