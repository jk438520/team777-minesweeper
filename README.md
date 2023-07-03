# Android minesweeper game
[![Build](https://github.com/jk438520/team777-minesweeper/actions/workflows/android.yml/badge.svg)](https://github.com/jk438520/team777-minesweeper/actions/workflows/android.yml)
[![Tests](https://github.com/jk438520/team777-minesweeper/actions/workflows/test.yml/badge.svg)](https://github.com/jk438520/team777-minesweeper/actions/workflows/test.yml)
[![codecov](https://codecov.io/gh/jk438520/team777-minesweeper/branch/main/graph/badge.svg?token=1GOF3DNS2O)](https://codecov.io/gh/jk438520/team777-minesweeper)
[![Lint](https://github.com/jk438520/team777-minesweeper/actions/workflows/lint.yml/badge.svg)](https://github.com/jk438520/team777-minesweeper/actions/workflows/lint.yml)


Simple Android minesweeper game written in Java.

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
