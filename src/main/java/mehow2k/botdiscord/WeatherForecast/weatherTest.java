package mehow2k.botdiscord.WeatherForecast;

import org.json.simple.*;

import java.util.Scanner;

import static mehow2k.botdiscord.WeatherForecast.WeatherData.*;

public class weatherTest {
    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner(System.in);
            String city;
            do{
                // Retrieve user input
                System.out.print("Wpisz nazwę miejscowości: (Say \"q\" to Quit): ");
                city = scanner.nextLine();

                if(city.equalsIgnoreCase("q")) break;

                // Get location data
                JSONObject cityLocationData = (JSONObject) getLocationData(city);
                double latitude = (double) cityLocationData.get("latitude");
                double longitude = (double) cityLocationData.get("longitude");

                getWeatherData(latitude, longitude);
            }while(!city.equalsIgnoreCase("q"));

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
