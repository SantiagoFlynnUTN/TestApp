package com.flynn.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flynn.data.local.room.entity.AboutEntity

@Dao
interface AboutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveContent(singleStringEntity: AboutEntity)

    @Query("SELECT * FROM about WHERE id = 1")
    suspend fun getAboutContent(): AboutEntity?

    @Query("DELETE FROM about")
    suspend fun clearLocalData()
}