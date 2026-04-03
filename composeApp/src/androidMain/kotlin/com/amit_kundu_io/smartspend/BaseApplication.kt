/**
 * BaseApplication.kt
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

package com.amit_kundu_io.smartspend

import android.app.Application
import com.amit_kundu_io.smartspend.root_di.initKoin
import org.koin.android.ext.koin.androidContext

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BaseApplication)
        }
    }
}