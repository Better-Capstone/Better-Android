package com.ssu.better.presentation.state

sealed class IdleEvent() {
    object Idle : IdleEvent()
    object Finish : IdleEvent()
}
