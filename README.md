# Anga
Weather app. Because... why not 🙂

"Anga" is the Swahili word for atmosphere.

### Important!
- Please obtain an API key from [OpenWeatherMap](https://openweathermap.org/api) and save it in `local.properties` with the format `OPEN_WEATHER_API_KEY=your_api_key`
- **Please use a physical device when testing!** The `FusedLocationProviderClient` doesn't work great on emulators, the app might take long to obtain a location.

### Cloning and Setup
- Run `git clone https://github.com/doc2dev/anga.git`
- Open the project in Android Studio.
- As indicated above, make sure your API key is properly set up.

### Running Tests
- Run `./gradlew testDebug`

### Continuous Integration
CI for the project has been implemented using GitHub actions. See `.github/workflows/android.yml` for details.
