package com.kth.mockapi.core.designsystem.image

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.palette.graphics.Palette
import com.kth.mockapi.core.designsystem.R

@Composable
fun GlidePaletteImage(
    imageUrl: String, // 로드할 이미지의 URL
    modifier: Modifier = Modifier, // 외부에서 Modifier 커스터마이징할 수 있도록
    contentScale: ContentScale = ContentScale.Crop, // 이미지 크기 조절 방법 설정
    onPaletteLoaded: (Palette) -> Unit = {}, // 팔레트 추출 후 결과 콜백
    placeholderRes: Int = R.drawable.placeholder, // 이미지 로딩 전/실패 시 표시할 기본 이미지
) {
    val context = LocalContext.current // 현재 Context 가져오기 (Glide는 Context 필요)
    var bitmap by remember { mutableStateOf<Bitmap?>(null) } // 추출된 Bitmap을 저장할 상태

    // 이미지 URL이 변경될 때마다 실행되는 비동기 작업
    LaunchedEffect(imageUrl) {
        context.loadBitmapWithPalette(imageUrl) { loadedBitmap, palette ->
            bitmap = loadedBitmap // 비트맵 상태 저장
            onPaletteLoaded(palette) // 콜백으로 Palette 전달
        }
    }

    // 비트맵이 성공적으로 로딩된 경우 화면에 표시
    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(), // Bitmap → Compose 이미지로 변환
            contentDescription = null,
            contentScale = contentScale, // 외부에서 받은 ContentScale 적용
            modifier = modifier, // 외부 Modifier 그대로 전달
        )
    } ?: Image(
        // 아직 비트맵이 없을 경우 (로딩 중이거나 실패 시) placeholder 표시
        painter = painterResource(id = placeholderRes),
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier,
    )
}

fun Context.loadBitmapWithPalette(
    imageUrl: String,
    onComplete: (Bitmap, Palette) -> Unit
) {
    Glide.with(this)
        .asBitmap()
        .load(imageUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Palette.from(resource).generate { palette ->
                    if (palette != null) {
                        onComplete(resource, palette)
                    }
                }
            }

            override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {

            }
        })
}
