package com.ssu.better.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.R

private val TextFieldRound = 3.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = BetterAndroidTheme.typography.body,
    enabled: Boolean = true,
    counterMaxLength: Int = 0,
    hint: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
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
            modifier = modifier,
            enabled = enabled,
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, // for remove underline
            ),
            keyboardActions = keyboardActions,
            singleLine = true,
            interactionSource = interactionSource,
            visualTransformation = visualTransformation,
            decorationBox = @Composable { innerTextField ->
                Surface(
                    modifier = modifier,
                    color = BetterColors.White,
                    shape = RoundedCornerShape(TextFieldRound),
                    shadowElevation = 2.dp,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = hint,
                                    color = BetterColors.Gray20,
                                    style = textStyle,
                                )
                            }
                            innerTextField()
                        }

                        Box(
                            modifier = Modifier.padding(8.dp).clickable {
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search_bar),
                                contentDescription = null,
                                tint = BetterColors.Primary50,
                                modifier = Modifier.width(20.dp).height(20.dp),
                            )
                        }
                    }
                }
            },
        )
    }
}
