# Android minesweeper game

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

### User - UI - Engine interface

Interaction between UI and game engine is done using 2 methods. Game.click(row, column) and Game.setClickMode(...). 

Game.click(row, column) returns GameState object that contains information about state of the game (PLAYING, WON, LOST) and collection of fields and values to which thoes fields have change after a click was registered.

Game.setClickMode(...) doesn't return anything as it's assumed it has been succesfull.

#### Interaction with fields

![field interaction UML](UMLs/field_interaction.png)

#### GameState class

![GameState class](UMLs/GameState.png)

#### Interaction with flag toggle

![toggle flag](UMLs/flag_toggle.png)