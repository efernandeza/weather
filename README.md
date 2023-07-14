# Weather
A basic weather app to render current and forecast weather data provided by openweather.

# Features
- Search for current and forecast weather by city.
- When coarse location permissions is granted, render weather for the device's current location.

# Weather API
https://openweathermap.org/api/one-call-api

# Development
[Link to Contribution Guide]

## Build Tasks

### Static Analysis
To maintain a consistent code base Weather uses pre-commit to run lint and ktlint, given more time
I would also consider detekt. I would also automatically install the pre-commit scripts in the
expected `.git/hooks` directory.

Lint
```shell
./gradlew lint
```

Kt Lint
Attempts to format source according to code style.
```shell
./gradlew ktlintFormat
```

Checks all SourceSet s and project Kotlin script files.
```shell
./gradlew ktlintCheck
```
