package com.ssu.better.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AnimatedTransitionDialogHelper(
    private val coroutineScope: CoroutineScope,
    private val onDismissFlow: MutableSharedFlow<Any>,
) {
    fun triggerAnimatedDismiss() {
        coroutineScope.launch {
            onDismissFlow.emit(Any())
        }
    }
}

@Composable
fun AnimatedTransitionDialog(
    onDismissRequest: () -> Unit,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable (AnimatedTransitionDialogHelper) -> Unit,
    DIALOG_BUILD_TIME: Long = 200L,
    ANIMATION_DURATION: Long = 500L,
    DISMISS_DURATION: Long = 500L,
    modifier: Modifier = Modifier,
) {
    val onDismissSharedFlow: MutableSharedFlow<Any> = remember { MutableSharedFlow() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val animateTrigger = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        launch {
            delay(DIALOG_BUILD_TIME)
            animateTrigger.value = true
        }
        launch {
            onDismissSharedFlow.asSharedFlow().collectLatest {
                startDismissWithExitAnimation(animateTrigger, onDismissRequest, DISMISS_DURATION)
            }
        }
    }

    Dialog(
        onDismissRequest = {
            coroutineScope.launch {
                startDismissWithExitAnimation(animateTrigger, onDismissRequest, DISMISS_DURATION)
            }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Box(
            contentAlignment = contentAlignment,
            modifier = modifier,
        ) {
            AnimatedScaleInTransition(
                visible = animateTrigger.value,
                duration = ANIMATION_DURATION,
            ) {
                content(AnimatedTransitionDialogHelper(coroutineScope, onDismissSharedFlow))
            }
        }
    }
}

@Composable
internal fun AnimatedScaleInTransition(
    visible: Boolean,
    duration: Long,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    val ANIMATION_TIME = duration
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(
            animationSpec = tween(ANIMATION_TIME.toInt()),
        ),
        exit = shrinkVertically(
            animationSpec = tween(ANIMATION_TIME.toInt()),
        ),
        content = content,
    )
}

suspend fun startDismissWithExitAnimation(
    animateTrigger: MutableState<Boolean>,
    onDismissRequest: () -> Unit,
    duration: Long = 500L,
) {
    val ANIMATION_TIME = duration
    animateTrigger.value = false
    delay(ANIMATION_TIME)
    onDismissRequest()
}

enum class CustomDialogPosition {
    BOTTOM, TOP
}

fun Modifier.customDialogModifier(pos: CustomDialogPosition) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(constraints.maxWidth, constraints.maxHeight) {
        when (pos) {
            CustomDialogPosition.BOTTOM -> {
                placeable.place(0, constraints.maxHeight - placeable.height, 10f)
            }

            CustomDialogPosition.TOP -> {
                placeable.place(0, 0, 10f)
            }
        }
    }
}
