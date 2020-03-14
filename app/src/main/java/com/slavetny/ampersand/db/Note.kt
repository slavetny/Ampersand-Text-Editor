package com.slavetny.ampersand.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Note (

    @PrimaryKey
    var title: String,

    var text: String = ""

)