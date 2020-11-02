# YourNews
Simple android application that display list of news. You can search and save news in your local database.

## How To Use
1. Register to [NewsApi](https://newsapi.org/)
2. Create a file `apikey.properties` in your root project
3. Last, add `NewsApiKey = "your api key"` inside apikey.properties

## Libraries
1. [Android Architecture Component](https://developer.android.com/topic/libraries/architecture)
2. [Dagger Hilt](https://dagger.dev/hilt/)
3. [Room](https://developer.android.com/jetpack/androidx/releases/room)
4. [Retrofit2](https://square.github.io/retrofit/)
5. [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html)
6. [Glide](https://github.com/bumptech/glide)
7. [PagedList](https://developer.android.com/topic/libraries/architecture/paging)
8. [Mockito](https://site.mockito.org/)

## Testing Dynamic Module 
You can upload the .aab file to internal sharing app Google Play Store or try to run `./gradlew installApkSplitsForTestDebug`

<img src="https://raw.githubusercontent.com/bimovall/YourNews/master/screenshot/dynamic_feature_test.gif" width="450" height="870"/>

reference: https://medium.com/androiddevelopers/local-development-and-testing-with-fakesplitinstallmanager-57083e1840a4
