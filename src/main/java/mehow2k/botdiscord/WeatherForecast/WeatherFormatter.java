package mehow2k.botdiscord.WeatherForecast;
import java.util.Map;
import java.util.HashMap;
public class WeatherFormatter {
    public String formatWeatherCode(long weatherCode) {
        // Mapa kodów pogodowych i ich opisów
        Map<Long, String> weatherDescriptions = new HashMap<>();

        weatherDescriptions.put(0L, "Clear sky");
        weatherDescriptions.put(1L, "Mostly clear");
        weatherDescriptions.put(2L, "Partly cloudy");
        weatherDescriptions.put(3L, "Overcast");
        weatherDescriptions.put(45L, "Fog");
        weatherDescriptions.put(48L, "Rime fog");
        weatherDescriptions.put(51L, "Drizzle: light");
        weatherDescriptions.put(53L, "Drizzle: moderate");
        weatherDescriptions.put(55L, "Drizzle: dense");
        weatherDescriptions.put(56L, "Freezing drizzle: light");
        weatherDescriptions.put(57L, "Freezing drizzle: dense");
        weatherDescriptions.put(61L, "Rain: light");
        weatherDescriptions.put(63L, "Rain: moderate");
        weatherDescriptions.put(65L, "Rain: heavy");
        weatherDescriptions.put(66L, "Freezing rain: light");
        weatherDescriptions.put(67L, "Freezing rain: heavy");
        weatherDescriptions.put(71L, "Snowfall: light");
        weatherDescriptions.put(73L, "Snowfall: moderate");
        weatherDescriptions.put(75L, "Snowfall: heavy");
        weatherDescriptions.put(77L, "Snow grains");
        weatherDescriptions.put(80L, "Rain showers: light");
        weatherDescriptions.put(81L, "Rain showers: moderate");
        weatherDescriptions.put(82L, "Rain showers: heavy");
        weatherDescriptions.put(85L, "Snow showers: light");
        weatherDescriptions.put(86L, "Snow showers: heavy");
        weatherDescriptions.put(95L, "Thunderstorm: slight or moderate");
        weatherDescriptions.put(96L, "Thunderstorm with hail: slight");
        weatherDescriptions.put(99L, "Thunderstorm with hail: heavy");


        return weatherDescriptions.getOrDefault(weatherCode, "Unknown weather code.");
    }

    public String formatWindDirection(long windDirection) {
        //check
        windDirection = (windDirection % 360 + 360) % 360;

        String[] directions = {
                "North",            // 0°
                "North-East",       // 45°
                "East",             // 90°
                "South-East",       // 135°
                "South",            // 180°
                "South-West",       // 225°
                "West",             // 270°
                "North-West"        // 315°
        };

        int index = (int) Math.round(windDirection / 45.0) % 8;

        return directions[index];
    }
}
