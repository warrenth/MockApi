package com.kth.mocksy.core.designsystem.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SharedTransitionScope.OverlayClip
import androidx.compose.animation.SharedTransitionScope.PlaceHolderSize
import androidx.compose.animation.SharedTransitionScope.SharedContentState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

/**
 * 기본 SharedTransition 설정을 적용한 Modifier 확장 함수.
 */
context(SharedTransitionScope)
fun Modifier.sharedElement(
    isLocalInspectionMode: Boolean,
    state: SharedContentState,  // key
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform = DefaultBoundsTransform,
    // 전환 중에에 contentSize (기본값): 원래 콘텐츠 크기 그대로 사용
    placeHolderSize: PlaceHolderSize = PlaceHolderSize.contentSize,
    renderInOverlayDuringTransition: Boolean = true,
    zIndexInOverlay: Float = 0f,
    clipInOverlayDuringTransition: OverlayClip = ParentClip,
): Modifier {
    return if (isLocalInspectionMode) {
        this
    } else {
        this.sharedBounds(
            sharedContentState = state,
            animatedVisibilityScope = animatedVisibilityScope,
            boundsTransform = boundsTransform,
            placeHolderSize = placeHolderSize,
            renderInOverlayDuringTransition = renderInOverlayDuringTransition,
            zIndexInOverlay = zIndexInOverlay,
            clipInOverlayDuringTransition = clipInOverlayDuringTransition,
        )
    }
}

/**
 * 더 간단하게 사용할 수 있도록 감싼 SharedTransition 헬퍼.
 */
@Composable
fun Modifier.sharedTransition(
    scope: SharedTransitionScope,
    key: String,
    animatedVisibilityScope: AnimatedVisibilityScope
): Modifier = with(scope) {
    this@sharedTransition.sharedElement(
        isLocalInspectionMode = LocalInspectionMode.current,
        state = rememberSharedContentState("sharedTransition:$key"),
        animatedVisibilityScope = animatedVisibilityScope,
        boundsTransform = DefaultBoundsTransform,
    )
}


// 공통 설정 값
private val DefaultSpring = spring(
    stiffness = Spring.StiffnessMediumLow,
    visibilityThreshold = Rect.VisibilityThreshold,
)

private val DefaultBoundsTransform = BoundsTransform { _, _ -> DefaultSpring }

private val ParentClip: OverlayClip = object : OverlayClip {
    override fun getClipPath(
        state: SharedContentState,
        bounds: Rect,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Path? {
        return state.parentSharedContentState?.clipPathInOverlay
    }
}
