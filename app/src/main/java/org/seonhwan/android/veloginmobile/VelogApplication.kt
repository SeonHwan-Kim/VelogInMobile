package org.seonhwan.android.veloginmobile

import android.app.Application
import androidx.room.Room
import dagger.hilt.android.HiltAndroidApp
import org.seonhwan.android.veloginmobile.data.local.database.ScrapPostDatabase
import org.seonhwan.android.veloginmobile.util.UserSharedPreferences
import timber.log.Timber

@HiltAndroidApp
class VelogApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        userPrefs = UserSharedPreferences(this)

        appDatabaseInstance = Room.databaseBuilder(
            appInstance.applicationContext,
            ScrapPostDatabase::class.java,
            "scrapDatabase.db",
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        lateinit var userPrefs: UserSharedPreferences
            private set

        lateinit var appInstance: VelogApplication
            private set

        lateinit var appDatabaseInstance: ScrapPostDatabase
            private set
    }
}
