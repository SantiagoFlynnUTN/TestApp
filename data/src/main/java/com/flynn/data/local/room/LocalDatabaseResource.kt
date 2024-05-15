package com.flynn.data.local.room

import com.flynn.data.local.room.entity.AboutEntity


interface LocalDatabaseResource {
    suspend fun getContent(): String?
    suspend fun saveContent(content: String)
    suspend fun clearLocalData()
}

class LocalDatabaseResourceImpl(
    private val compassDb: CompassDb,
): LocalDatabaseResource {
    override suspend fun getContent(): String? {
        return compassDb.aboutDao().getAboutContent()?.content
    }

    override suspend fun saveContent(content: String) {
        compassDb.aboutDao().saveContent(AboutEntity(content = content))
    }

    override suspend fun clearLocalData() {
        compassDb.aboutDao().clearLocalData()
    }
}