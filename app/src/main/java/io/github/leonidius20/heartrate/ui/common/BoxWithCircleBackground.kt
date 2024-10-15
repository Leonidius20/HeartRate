package io.github.leonidius20.heartrate.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import io.github.leonidius20.heartrate.ui.theme.bgCircleColor

@Composable
fun BoxWithCircleBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    BoxWithConstraints(modifier) {
        val scope = this

        Box(
            Modifier.drawBehind {
                // todo: better values esp. in horizontal mode
                val centerUpwardOffset = 100.dp.toPx()

                drawCircle(
                    color = bgCircleColor,
                    radius = (scope.maxHeight / 2).toPx() + centerUpwardOffset, // radius = from circle center (center of pager? up to the end of pager)
                    center = center.copy(y = center.y - centerUpwardOffset)
                )
            }
        ) {
            content()
        }
    }
}