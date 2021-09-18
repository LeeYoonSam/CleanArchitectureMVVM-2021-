package com.albert.detail.navigation

import android.content.Context
import com.albert.detail.DetailActivity
import com.albert.navigator.DetailNavigator
import kotlinx.coroutines.NonCancellable.start
import javax.inject.Inject

class DetailNavigatorImpl @Inject constructor(): DetailNavigator {
    override fun openDetail(context: Context, sessionId: Int) {
        DetailActivity.start(context, sessionId)
    }
}