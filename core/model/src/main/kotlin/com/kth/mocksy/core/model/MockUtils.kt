/*
 * Copyright (c) 2025 MockApi, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kth.mocksy.core.model

object MockUtils {

    val mockArticle: Article
        get() = Article(
            title = "고양이의 낮잠 비밀",
            content = "고양이는 하루 평균 12~16시간을 잠으로 보냅니다. 충분한 수면은 고양이 건강에 매우 중요합니다.",
            description = "고양이의 수면 습관을 알아봅니다.",
            author = "Cat Lover",
            date = "2025-04-10",
            cover = "https://cdn2.thecatapi.com/images/MTY3ODIyMQ.jpg",
        )
}
