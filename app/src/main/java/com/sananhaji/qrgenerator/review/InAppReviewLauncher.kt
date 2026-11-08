package com.sananhaji.qrgenerator.review

import android.app.Activity
import android.util.Log
import com.google.android.play.core.ktx.launchReview
import com.google.android.play.core.ktx.requestReview
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory

/**
 * Shows the Google Play in-app rating sheet over the current screen.
 *
 * Play decides whether the sheet actually appears; on devices without Play Store,
 * or for the sideloaded HHM build, the request fails and is only logged.
 */
class InAppReviewLauncher(private val activity: Activity) {

    private val manager = ReviewManagerFactory.create(activity)

    suspend fun launch() {
        try {
            manager.launchReview(
                activity = activity,
                reviewInfo = manager.requestReview(),
            )
        } catch (e: ReviewException) {
            Log.w(TAG, "In-app review is not available", e)
        }
    }

    private companion object {

        const val TAG = "InAppReview"
    }
}
