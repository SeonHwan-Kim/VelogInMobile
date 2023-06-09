package org.seonhwan.android.veloginmobile.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder")
data class Folder(
    @PrimaryKey
    var name: String,
    val size: Int,
)
