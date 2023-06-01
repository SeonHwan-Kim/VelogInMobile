package org.seonhwan.android.veloginmobile.util.extension

import android.content.res.Resources

fun Int.toPx() = this * Resources.getSystem().displayMetrics.density
