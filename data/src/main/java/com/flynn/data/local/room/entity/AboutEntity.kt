package com.flynn.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "about")
data class AboutEntity(
    @PrimaryKey val id: Int = 1,
    val content: String
)