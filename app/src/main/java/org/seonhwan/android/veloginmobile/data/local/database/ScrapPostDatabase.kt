package org.seonhwan.android.veloginmobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.seonhwan.android.veloginmobile.data.local.dao.ScrapPostDao
import org.seonhwan.android.veloginmobile.data.local.model.ScrapPost

@Database(entities = [ScrapPost::class], version = 3)
@TypeConverters(ScrapPostConverter::class)
abstract class ScrapPostDatabase : RoomDatabase() {
    abstract fun scrapPostDao(): ScrapPostDao
}
