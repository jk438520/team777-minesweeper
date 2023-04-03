## Gradle commands

### Lint check

You can run code inspections from command line using:
```
./gradlew lint
```
Or you can do it manually by clicking in Android Studio: ___Code > Inspect Code___.

For more information check [lint guidelines](https://developer.android.com/studio/write/lint.html).

### Tests

You can run tests from command line using:
```
./gradlew test
```
Or you can do it manually by clicking in Android Studio by doing right-click on a directory or file with test and click ___Run___

For connected tests (run on android emulator) you can use:
```
./gradlew connectedAndroidTest
```
