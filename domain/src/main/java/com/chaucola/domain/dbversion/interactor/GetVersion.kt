package com.chaucola.domain.dbversion.interactor

import com.chaucola.domain.dbversion.DbVersionRepository
import io.reactivex.Single

class GetVersion(val dbVersionRepository: DbVersionRepository) {

    fun buildUseCaseSingle(): Single<String> =
        dbVersionRepository.getVersion()
}