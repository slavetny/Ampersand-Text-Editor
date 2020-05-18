package com.slavetny.ampersand.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (

    @PrimaryKey
    var title: String,

    var text: String = ""

)