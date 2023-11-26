package com.ssu.better.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

private val TextFieldMinHeight = 48.dp
private val TextFieldRound = 10.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BetterTextField(
    value: String,
    hint: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = BetterAndroidTheme.typography.body,
    helperTextEnabled: Boolean = false,
    helperText: String = "",
    counterMaxLength: Int = 0,
    enabled: Boolean = true,
    isError: Boolean = false,
    isSingleLine: Boolean = true,
    backgroundColor: Color = BetterColors.Gray00,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
    keyboardActions: KeyboardActions = KeyboardActions(),
    minHeight: Dp = TextFieldMinHeight,
    maxHeight: Dp = minHeight,
    hintAlignment: Alignment = Alignment.CenterStart,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val baseModifier = Modifier
        .fillMaxWidth()
        .heightIn(min = minHeight, max = maxHeight)
        .fillMaxWidth()
    val borderModifier = baseModifier.border(1.dp, color = BetterColors.Primary50, shape = RoundedCornerShape(TextFieldRound))

    val customTextSelectionColors = TextSelectionColors(
        handleColor = BetterColors.Primary50,
        backgroundColor = BetterColors.Primary00,
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        Column {
            BasicTextField(
                value = value,
                onValueChange = {
                    val newValue = if (counterMaxLength > 0) {
                        it.take(counterMaxLength)
                    } else {
                        it
                    }
                    onValueChange(newValue)
                },
                modifier = modifier
                    .heightIn(min = minHeight, max = maxHeight),
                enabled = enabled,
                textStyle = textStyle,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = isSingleLine,
                interactionSource = interactionSource,
                visualTransformation = visualTransformation,
                decorationBox = @Composable { innerTextField ->
                    Surface(
                        modifier = if (isError) borderModifier else baseModifier,
                        color = backgroundColor,
                        shape = RoundedCornerShape(TextFieldRound),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 14.dp, horizontal = 10.dp),
                            contentAlignment = hintAlignment,
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = hint,
                                    color = BetterColors.Gray30,
                                    style = BetterAndroidTheme.typography.body,
                                )
                            }
                            innerTextField()
                        }
                    }
                },
            )

            if (helperTextEnabled) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    text = helperText,
                    style = BetterAndroidTheme.typography.body,
                    color = if (isError) BetterColors.Primary50 else BetterColors.Gray70,
                )
            }
        }
    }
}
