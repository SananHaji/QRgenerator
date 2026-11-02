package com.sananhaji.qrgenerator.utils

import android.content.Context

/** Must match `android:authorities` of the FileProvider in the manifest. */
private const val PROVIDER_SUFFIX = ".provider"

/** Built from the package name so the store and company builds can be installed side by side. */
val Context.fileProviderAuthority: String
    get() = packageName + PROVIDER_SUFFIX
