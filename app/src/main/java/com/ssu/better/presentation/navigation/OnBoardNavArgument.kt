package com.ssu.better.presentation.navigation

import javax.annotation.concurrent.Immutable

@Immutable
class OnBoardNavArgument {
    companion object {
        val QUERY = "?token={token}&nickname={nickname}"

        val TOKEN: String = "token"
        val NICKNAME: String = "nickname"
    }
}
