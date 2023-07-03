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
#### Interaction with fields
```{uml}
@startuml

actor User as user
participant "UI" as ui
participant Game << (C,#ADD1B2) >>


skinparam actorStyle awesome

loop until game ends or user restarts
    user -> ui: click on (row, col)
    ui -> Game: game.click(row, col)
    Game -> ui: {{\nclass GameState{\n+int a\n~method()\n}\n}}
    ui -> user: apply changes from GameState

end 
@enduml

```
#### GameState class
```{uml}
@startuml

class GameState{
    +gameStatus
    +fieldEvents
}

enum FieldEvent{
    REVEAL_MINE
    REVEAL_NUMBER
    FLAG
    UNFLAG
}

enum GameStatus{
    PLAYING
    WON
    LOST
}

class FieldToDisplay{
    +row
    +col
    +value
    +event
}


GameStatus --> GameState::gameStatus
FieldEvent --> FieldToDisplay::event
FieldToDisplay --o GameState::fieldEvents
@enduml

```
#### Interaction with flag toggle
```{uml}
@startuml

actor User as user
participant "UI" as ui
participant Game << (C,#ADD1B2) >>


skinparam actorStyle awesome

loop until game ends or user restarts
    user -> ui: click on toggle
    ui -> Game: game.setClickMode(...)
    ui -> user: change of icon
end 
@enduml

```

