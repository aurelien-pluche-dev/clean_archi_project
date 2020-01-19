package com.chaucola.data.repository

import com.chaucola.data.mapper.dbVersionMapper
import com.chaucola.data.source.dbversion.DbVersionService
import com.chaucola.domain.dbversion.DbVersionRepository
import io.reactivex.Single

class DbVersionDataRepository : DbVersionRepository {

    private val dbVersionService = DbVersionService()

    override fun getVersion(): Single<String> {
        return dbVersionService.getSongs().map {
            dbVersionMapper(it)
        }
    }
}