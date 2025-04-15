## 📦 Navigation 인자 전달 방법 정리

멀티모듈 Compose 프로젝트에서 화면 간 데이터 전달 시 사용 되는 방법


###  1. 객체를 Navigation에 직접 전달 (`NavType` 사용)
```kotlin
@Serializable
data class Detail(val article: Article) : RouteScreen() {
    companion object {
        val typeMap = mapOf(typeOf<Detail>() to ArticlesType)
    }
}
object ArticlesType : NavType<Article>(isNullableAllowed = false) {
    override fun put(bundle: Bundle, key: String, value: Article) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): Article? =
        BundleCompat.getParcelable(bundle, key, Article::class.java)

    override fun parseValue(value: String): Article =
        Json.decodeFromString(Uri.decode(value))

    override fun serializeAsValue(value: Article): String =
        Uri.encode(Json.encodeToString(value))
}
```
장점: 컴파일 타임에 타입 체크 가능 (타입 안전성), 객체 직렬화를 통해 route에 직접 encode 가능
단점: 객체마다 NavType 클래스를 따로 구현해야 함, 유지보수, 코드량 증가

###  2. ID 값만 전달하고, Detail 화면에서 ViewModel로 다시 조회
장점: NavType을 만들 필요 없음, 경로가 가볍고 명확함, Deep link 지원이 쉬움
단점: 화면에서 데이터를 다시 조회해야 함, (cache구현 or API)

### 3. SharedViewModel 사용 (상태 공유)
장점: NavArgs 없이 복잡한 객체 전달 가능, 빠름 (메모리에서 직접 가져옴)
단점: ViewModel 생명주기를 잘 관리해야 함, 화면 복원, deep link 처리 어려움

###  4. 단순한 데이터 (String, Int 등)는 기본 NavArgs 사용
```kotlin
NavHost(navController = navHostController, startDestination = "home") {
    composable("home") {
        HomeScreen(
            onNavigateToDetail = { id, title ->
                navHostController.navigate("detail/$id/$title")
            }
        )
    }
    composable(
        route = "detail/{id}/{title}",
        arguments = listOf(
            navArgument("id") { type = NavType.IntType },
            navArgument("title") { type = NavType.StringType }
        )
    ) {
        val id = it.arguments?.getInt("id")
        val title = it.arguments?.getString("title")
        DetailScreen(id = id, title = title)
    }
}
```
장점: 설정이 간단하고 직관적, 기본 타입은 NavType 자동 지원
단점: 복잡한 객체는 전달할 수 없음

📌 상황에 따라 추천되는 방식
단순 타입(String, Int 등) 전달 -> 기본 NavArgs 사용
객체 전달 & deep link 필요	-> NavType + Serializable 사용
ID 기반 조회 구조 -> ID만 넘기고 ViewModel 에서 조회 (캐시 or API)