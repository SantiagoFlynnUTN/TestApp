package com.flynn.feature_home.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

fun Modifier.shimmerEffect(shape: Shape, animationTime: Int = 1500): Modifier =
    composed {
        val transition = rememberInfiniteTransition(label = "")
        var size by remember { mutableStateOf(IntSize.Zero) }
        val startOffsetX by transition.animateFloat(
            initialValue = -2 * size.width.toFloat(),
            targetValue = 2 * size.width.toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(animationTime)
            ), label = ""
        )

        background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFEBECEF),
                    Color(0xFFD2D5DA),
                    Color(0xFFF3F4F6),
                ),
                start = Offset(startOffsetX, 0F),
                end = Offset(startOffsetX + size.width.toFloat(), 0F)
            ),
            shape = shape
        ).onGloballyPositioned { size = it.size }
    }
