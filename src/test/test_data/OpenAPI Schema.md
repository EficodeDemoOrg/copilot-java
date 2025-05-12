# OpenWeather API Schema in JSON 

```json
{
   "coord": {
      "lon": 7.367,
      "lat": 45.133
   },
   "weather": [
      {
         "id": 501,
         "main": "Rain",
         "description": "moderate rain",
         "icon": "10d"
      }
   ],
   "base": "stations",
   "main": {
      "temp": 284.2,
      "feels_like": 282.93,
      "temp_min": 283.06,
      "temp_max": 286.82,
      "pressure": 1021,
      "humidity": 60,
      "sea_level": 1021,
      "grnd_level": 910
   },
   "visibility": 10000,
   "wind": {
      "speed": 4.09,
      "deg": 121,
      "gust": 3.47
   },
   "rain": {
      "1h": 2.73
   },
   "clouds": {
      "all": 83
   },
   "dt": 1726660758,
   "sys": {
      "type": 1,
      "id": 6736,
      "country": "IT",
      "sunrise": 1726636384,
      "sunset": 1726680975
   },
   "timezone": 7200,
   "id": 3165523,
   "name": "Province of Turin",
   "cod": 200
}                    
```

## Explanation for schema ##

| Field                | Description                                                                                                                                      |
|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| coord                | Coordinates of the location                                                                               |
| coord.lon            | Longitude of the location                                                                                 |
| coord.lat            | Latitude of the location                                                                                  |
| weather              | Weather condition codes                                                                                   |
| weather.id           | Weather condition id                                                                                      |
| weather.main         | Group of weather parameters (Rain, Snow, Clouds etc.)                                                     |
| weather.description  | Weather condition within the group                                                                        |
| weather.icon         | Weather icon id                                                                                           |
| base                 | Internal parameter                                                                                        |
| main                 | Main weather data                                                                                         |
| main.temp            | Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit                                  |
| main.feels_like      | Temperature accounting for human perception. Same units as temp                                           |
| main.pressure        | Atmospheric pressure on the sea level, hPa                                                                |
| main.humidity        | Humidity, %                                                                                               |
| main.temp_min        | Minimum temperature at the moment. Same units as temp                                                     |
| main.temp_max        | Maximum temperature at the moment. Same units as temp                                                     |
| main.sea_level       | Atmospheric pressure on the sea level, hPa                                                                |
| main.grnd_level      | Atmospheric pressure on the ground level, hPa                                                             |
| visibility           | Visibility, meter. Max value is 10 km                                                                     |
| wind                 | Wind data                                                                                                 |
| wind.speed           | Wind speed. Unit: meter/sec (default/metric), miles/hour (imperial)                                       |
| wind.deg             | Wind direction, degrees (meteorological)                                                                  |
| wind.gust            | Wind gust. Same units as wind.speed                                                                       |
| clouds               | Cloud data                                                                                                |
| clouds.all           | Cloudiness, %                                                                                             |
| rain                 | Rain data                                                                                                 |
| rain.1h              | Precipitation, mm/h (if available)                                                                        |
| snow                 | Snow data                                                                                                 |
| snow.1h              | Precipitation, mm/h (if available)                                                                        |
| dt                   | Time of data calculation, unix, UTC                                                                       |
| sys                  | System data                                                                                               |
| sys.type             | Internal parameter                                                                                        |
| sys.id               | Internal parameter                                                                                        |
| sys.message          | Internal parameter                                                                                        |
| sys.country          | Country code (GB, JP etc.)                                                                                |
| sys.sunrise          | Sunrise time, unix, UTC                                                                                   |
| sys.sunset           | Sunset time, unix, UTC                                                                                    |
| timezone             | Shift in seconds from UTC                                                                                 |
| id                   | City ID                                                                                                   |
| name                 | City name                                                                                                 |
| cod                  | Internal parameter                                                                                        |
