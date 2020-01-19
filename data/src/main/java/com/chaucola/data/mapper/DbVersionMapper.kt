package com.chaucola.data.mapper

import com.chaucola.data.source.dbversion.DbVersionAnswer

internal fun dbVersionMapper(answer: DbVersionAnswer): String {
    return answer.answer
}