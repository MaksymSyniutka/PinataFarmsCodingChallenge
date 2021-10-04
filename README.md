### Project Overview

This is a two-screen application that shows:

1. A list of people read in from a `.json` file.
1. A detail-view of an individual person, drawing a box around their head.

#### Architecture overview

This app is written in Kotlin Language and utilizes MVVM architectural pattern and follows the Clean Architecture approach. In order to reduce the learning curve of the project and also make this project easier to develop - I've used [Jetpack's Viewmodels](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=CjwKCAjwzOqKBhAWEiwArQGwaHZqDhhw4b7O5FCkjEuEHsqYWHuEqoDUfdJGKayzDrv-oecleqdrRhoCqpsQAvD_BwE&gclsrc=aw.ds) which solved a couple of issues for me (like orientation change (not required as per coding challenge, but it's a nice bonus) and scopes management).

As for multithreading, I've used Kotlin [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flows](https://kotlinlang.org/docs/flow.html), which reduced the learning curve in comparison to [RxJava](https://github.com/ReactiveX/RxJava).

For `.json` de/serialization I've used the [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization) library as it was the easiest one to integrate. It almost does not require any configuration and works out of the box as well as it integrates really nicely to a Kotlin project. In addition, it performs really well. For small `json` files, it performs the same as [Moshi](https://github.com/square/moshi) or [Jackson](https://github.com/FasterXML/jackson), but it's much more convenient to configure and use.

This project does not use any networking clients, but once the remote server is up and running I would go with either of two options:
1. Industry-standard [OkHttp](https://square.github.io/okhttp/) and [Retrofit](https://square.github.io/retrofit/) with Kotlin [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flows](https://kotlinlang.org/docs/flow.html). This solution is easily testable and every Android Developer has experience working with this combination, which reduces the learning curve of the project.
1. Or I would use [Ktor Client](https://ktor.io/) which works really smooth in the Kotlin project with Kotlin [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flows](https://kotlinlang.org/docs/flow.html). It's easy to set up and test.

For hot-swappable local and remote data sources - I've used the Data Source pattern. As per the coding challenge assignment, the app should read the local `.json` file, but, once the remote server has been deployed - the app should easily switch to this remote data source. Right now, the app uses `PersonLocalDataSource` for reading the data from a local file, but in order to support remote fetching, a similar file should be created, but, with a remote-fetching logic (e.g. `PersonRemoteDataSource` which will use any of the networking solutions I've described above), and, it should substitute `PersonLocalDataSource` as constructor parameter of `PersonRepositoryImpl` (just swap the declaration in Koin's module). All the other logic should remain the same.

In order to optimize resources for image processing and image visualization on the device - I've used [LruCache](https://developer.android.com/reference/android/util/LruCache) which helped me to reduce unnecessary calculations when reading images from the local storage in different places. Also, I created `CoilCustomLruCacheInterceptor` which utilized this cache and loads images from it (if they are stored within this cache, otherwise, regular image loading logic applies). As per the image loading library, I've chosen [Coil](https://github.com/coil-kt/coil) as it's the easiest one to use and configure, and, as a nice bonus, it's open for customization.

I've also gone the [Kotlin DSL way](https://docs.gradle.org/current/userguide/kotlin_dsl.html) of dealing with Gradle scripts, which introduced new `buildSrc` module, and, in oppose to regular Groovy Gradle Scripts, all the dependencies are now stored in Kotlin Objects. This approach allowed me to optimize dependencies shared across the modules and made it easier to add/remove additional shared dependencies.
### Known issues

1. This app is written using Clean Architecture approach and by this software approach, the `domain` module should be a plain java library module and shouldn't contain any Android libraries. In this project, I implemented the `domain` module as an Android Library module because the latest Android Studio IDE (`Android Studio Arctic Fox | 2020.3.1 Patch 2; Build #AI-203.7717.56.2031.7678000`) has an issue with Kotlin IDE plugin and Java 11. More info:
    1. [KTIJ-7627 Android Studio Canary 13 can't resolve build.gradle.kts configuration](https://youtrack.jetbrains.com/issue/KTIJ-7627)
    1. [Plain Java library in Android Studio giving compile error inside the editor](https://stackoverflow.com/questions/67875687/plain-java-library-in-android-studio-giving-compile-error-inside-the-editor-can)
1. I'm also using a non-production version of the `lifecycle-runtime-ktx:2.4.0-rc01` library as it introduces a safer way to collect Kotlin Flows (more info here: [Medium Post](https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda)).
1. As I'm using the latest version of stable Android Studio IDE, it requires a new version of Gradle and Java 11 which might not be installed on every computer.

### Possible improvements
There are a couple of improvements I would have liked to make:
1. Write unit tests. The app's architecture (MVVM & Clean Architecture & DI) allows to easily cover almost the entire project with unit/instrumentation tests. For testing, I would choose the following libraries:
    1. [Mockk](https://mockk.io/) for mocking Kotlin objects.
    1. [Turbine](https://github.com/cashapp/turbine) for unit testing Kotlin Flows.
    1. [Koin DI](https://insert-koin.io/) for injecting mocked/dummy objects.
    1. [Jacoco](https://www.eclemma.org/jacoco/) for code coverage.
    1. [Junit5](https://github.com/mannodermaus/android-junit5) as unit tests framework.
    1. [AssertJ](https://joel-costigliola.github.io/assertj/) or [Truth](https://github.com/google/truth) for assertions.
    1. [Robolectric](http://robolectric.org/) for running Android instrumentation tests on workstation's JVM.
    1. For UI tests:
        1. [Andorid Test Orchestator](https://developer.android.com/training/testing/junit-runner) for running each UI test in a separate process (improves stability).
        1. [Kaspresso](https://github.com/KasperskyLab/Kaspresso) as a framework for UI testing.
        1. [Page Object Model](https://medium.com/software-testing-break-and-improve/espresso-framework-creating-ui-tests-using-page-object-model-c3c73c138534) for easier writing of UI tests.
1. If the project will scale - use a different to a service-locator DI solution, which the [Koin DI](https://insert-koin.io/) is. Switching to a different DI solution like [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) will improve runtime stability and it will ease up constructor injection, but, at the same time, it will increase the learning curve as well as it will affect compilation time.
1. Create [Android Module Gradle plugin](https://medium.com/wantedly-engineering/managing-android-multi-module-project-with-gradle-plugin-and-kotlin-4fcc126e7e49) which will make it easier to manage shared dependencies and configurations across the modules. I didn't implement it in this coding challenge as this app is relatively small and it would have not benefited from this plugin in comparison to a bigger, more scaled application.
1. Proper error handling, as for this coding challenge, I've used a pretty simple one.

### Coding challenge
[Link to a coding challenge assignment](Android%20Coding%20Challenge.pdf)