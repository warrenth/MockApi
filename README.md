## DeepDive 
Multi-module, DesignSystem, Navigation, SharedTransition, 
reference : cat-paywall-compose

## 📷 Previews
<p align="left">
<img src="preview/preview01.gif" alt="drawing" width="270px" />
</p>

## 1. core:navigation
## 1.1 멀티 모듈 Compose 에서 화면간 데이터 전달 방법. Navigation 사용법
### 1.1.1 객체를 Navigation에 직접 전달 (`NavType` 사용)
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

###  1.1.2 ID 값만 전달하고, Detail 화면에서 ViewModel로 다시 조회
장점: NavType을 만들 필요 없음, 경로가 가볍고 명확함, Deep link 지원이 쉬움  
단점: 화면에서 데이터를 다시 조회해야 함, (cache구현 or API)

### 1.1.3 SharedViewModel 사용 (상태 공유)
장점: NavArgs 없이 복잡한 객체 전달 가능, 빠름 (메모리에서 직접 가져옴)  
단점: ViewModel 생명주기를 잘 관리해야 함, 화면 복원, deep link 처리 어려움

### 1.1.4 단순한 데이터 (String, Int 등)는 기본 NavArgs 사용
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
- 단순 타입(String, Int 등) 전달 -> 기본 NavArgs 사용   
- 객체 전달 & deep link 필요	-> NavType + Serializable 사용  
- ID 기반 조회 구조 -> ID만 넘기고 ViewModel 에서 조회 (캐시 or API)  

# 2. Core:Data
## 2.1 코루틴 Dispatcher 전략 트레이드 오프
### 2.1.1 ViewModel 에서 Dispatchers.IO 직접 사용  
>장점: 간단하고 직관적   
단점: 테스트 시 Dispatcher.IO 대체 어려움  
대안: Dispatchers.setMain(...) 같은 별도 세팅 필요

### 2.1.2 ViewModelScope에서 Dispatcher 생략 (launch {})  
>장점: 코드 깔끔, Retrofit + suspend 조합에선 내부적으로 Dispatcher 사용하여 별도 정의 필요 없음    
단점: Room, 비-Retrofit 작업 을 같이 쓸 경우  MainThread 에서 실행 -> ANR  
참고 오픈소스 : DroidKnights → 대부분의 suspend 호출에서 Dispatcher를 생략

### 2.1.3 Dispatcher 주입 + Repository 내부에서 flowOn(...) 사용  
```kotlin
internal class ArticlesRepositoryImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) {
    override fun getArticles(): Flow<List<Article>> =
        flow {
            emit(api.getArticles())
        }.flowOn(ioDispatcher)
}
```
>장점: Dispatcher 주입으로 테스트 시 유연하게 변경 가능  
단점: 코드 초기 세팅 필요  
참고 오픈소스 : Now in Android, Skydoves → Repository에서 Dispatcher 주입 후 flowOn(...) 처리

📌 상황에 따라 추천 방식  
``` 
1. 빠르게 앱 구성 / Dispatcher 이해 낮음 ->  1번 방법  ViewModel에서 Dispatchers.IO   
2. 대부분 Retrofit이고, UI에 집중 -> 2번 Dispatcher 생략    
3. 테스트, 멀티모듈, 아키텍처 중요 -> ③ Dispatcher 주입 + flowOn 처리    
4. (추천) 실무용 + Dispatcher 주입 추가 + 파싱 최적화

ViewModel.launch {                        // 정의 없음 MainThread
    └── repository.getArticles()          // Flow 시작됨
        └── flowOn(IO)                    // API 호출 IO에서 수행
    └── .map { sort }                    
    └── .flowOn(Default)                  // 정렬, 파싱은 Default에서 수행
    └── .collect { _uiState.value = it }  // 최종 UI 반영은 Main (기본)
}
``` 
## 2.2 sandwich
### 2.2.1 왜 sandwich 를 쓰는가?
Retrofit 에 문제점
- try/catch + null 처리 지옥
- 공통 에러 처리 
- 반복적인 if (isSuccessful) 체크

```kotlin
// Retrofit의 기본 Call<T> → ApiResponse<T>로 자동 래핑
addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
// 기본 형태
suspend fun getArticles(): Response<List<Article>>
// Sandwich가 만든 형태
suspend fun getArticles(): ApiResponse<List<Article>>

```
#### 2.2.2 suspendOnError 란? 
>응답은 받았지만 응답코드가 200이 아닌 경우  
예) HTTP 4xx, 5xx 

#### 2.2.3 onException 란? 에러는 분기처리하고싶을때?  
>서버 연결 실패, 응답 파싱 실패   
예) 인터넷 없음 (UnknownHostException)   
   타임아웃 (SocketTimeoutException)   
   JSON 파싱 실패 (JsonParseException)

```kotlin
apiService.getSomething().onException {
    if (exception is UnknownHostException) {
        // 네트워크 끊김일 경우
    } else if (exception is SocketTimeoutException) {
        // 응답이 너무 늦는 경우
    }
}
```
#### 2.2.4 sandwich는 클린아키텍처 domain 계층에 의존성 생길까?
>`ApiResponse`는 Sandwich 라이브러리 타입으로, **data**계층에 속함   
domain 계층은 외부 라이브러리에 의존하면 안됨.  
APIResponse 로 받은걸 별도의 Result로 만들거나 순수 객체로 넘겨줘야 하기 때문에
클린아키텍처에서는 잘 사용하지 않는다. 클린아키텍처를 사용하지 않고 빠르게 앱을 개발하기에는 좋을 수 있다.

# 2. Core:DesignSystem
## 2.1 디자인 시스템
> Jetpack Compose 기반 앱에서 공통 디자인 묶음(색상, 배경, 테마)을 전역적으로 관리하고,  
다크/라이트 모드 대응 및 미리보기, 테스트, 재사용성을 향상시키기 위한 디자인 시스템 모듈
### 2.1.1 ArticleColors
> 앱 전반에 사용되는 색상값을 정의한 데이터 클래스입니다.
> `defaultLightColors()`, `defaultDarkColors()`를 통해 다크/라이트 테마를 자동 분기
### 2.1.2 ArticlesBackground
> 배경 색상과 elevation 정보를 묶은 테마 전용 클래스입니다.
> 라이트/다크 모드에 따라 배경을 자동 설정
### 2.1.3 ArticleTheme
> 앱 전체 또는 특정 화면에 테마를 적용하는 래퍼 함수
> CompositionLocalProvider 를 사용해 색상/배경을 하위 Composable 주입
> 예) ArticlesTheme.colors.backgroundLight 로 Composable 어디서든 전역으로 접근 가능
### 2.1.4 staticCompositionLocalOf, compositionLocalOf
```kotlin
val LocalBackgroundTheme = staticCompositionLocalOf { ArticleBackground() }
```
- ArticleBackground는 거의 고정 값 (예: 테마용 배경)  
- 변경될 일이 거의 없고, 변경되더라도 UI를 리렌더링하지 않아도 되는 경우  
- Recomposition 최적화됨 → 성능 향상  
```kotlin
val LocalColors = compositionLocalOf<ArticleColors> {
error("No colors provided")
}
```
- ArticleColors는 다크/라이트 등으로 바뀔 수 있는 동적 값  
- 이 값이 변경되면 해당 값을 사용한 컴포저블들이 자동으로 Recomposition 됨  
- UI가 동적으로 반응해야 하는 경우 사용  

# 2. Core:Feature
## 2.1 SharedTransition