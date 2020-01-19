package com.chaucola.domain.dbversion

import io.reactivex.Single

interface DbVersionRepository {

    fun getVersion(): Single<String>
}