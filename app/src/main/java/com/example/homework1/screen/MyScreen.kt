package com.example.homework1.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homework1.R
import com.example.homework1.data.ItemText
import com.example.homework1.viewModel.MainActivityViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homework1.LocalCustomColors


@Composable
fun MyScreen() {

    val viewModel: MainActivityViewModel = viewModel()

    val configuration = LocalConfiguration.current
    val columns =
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            COLUMNS_LANDSCAPE
        } else COLUMNS_VERTICAL
    val list = List(viewModel.state.value) {
        ItemText(
            id = it,
            text = "$it",
        )
    }

    val colors = LocalCustomColors.current

    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(bottom = 56.dp, top = 12.dp),
        ) {
            items(
                key = { it.id },
                items = list,
            ) { item ->
                viewModel.addAnimationFlag()
                val backgroundColor =
                    if (item.id % 2 == 0) colors.firstColumnColor
                    else  colors.secondColumnColor
                var isVisible by remember { mutableStateOf(viewModel.animatedStates[item.id]) }

                val enterAnimation = if (item.id % columns == 0) {
                    slideInVertically(initialOffsetY = { ANIMATION_OFFSET }) + fadeIn()
                } else {
                    slideInHorizontally(initialOffsetX = { ANIMATION_OFFSET }) + fadeIn()
                }

                LaunchedEffect(item.id) {
                    if (!isVisible) {
                        viewModel.animatedStates[item.id] = true
                        isVisible = true
                    }
                }

                AnimatedVisibility(
                    visible = isVisible,
                    enter = enterAnimation,
                    exit = fadeOut(animationSpec = tween(durationMillis = 1000))
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .background(backgroundColor)
                                .align(Alignment.Center)
                        ) {
                            Text(
                                text = item.text,
                                fontSize = dimensionResource(id = R.dimen.text_size_large).value.sp,
                                color = colors.background,
                                modifier = Modifier
                                    .align(Alignment.Center),
                            )
                        }
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Button1(viewModel.state, { viewModel.addItem() })
    }
}

@Composable
fun Button1(state: MutableState<Int>, onClick:() -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
    ) {
        Text(
            text = stringResource(R.string.button_text) + state.value
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ActivityPreview() {
    MyScreen()
}

const val COLUMNS_VERTICAL = 3
const val COLUMNS_LANDSCAPE = 4
const val START_ELEMENTS_COUNT = 1
const val ANIMATION_OFFSET = -400
