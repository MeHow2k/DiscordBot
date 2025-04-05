package mehow2k.botdiscord.WeatherForecast;

import org.json.simple.parser.JSONParser;
import org.json.simple.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class WeatherData {
    public WeatherData(){

    }

    public static JSONObject getLocationData(String city){
        city = city.replaceAll(" ", "+");

        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                city + "&count=1&language=en&format=json";

        try{
            HttpURLConnection apiConnection = fetchApiResponse(urlString);

            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: {getLocation} Could not connect to API. Status: "+apiConnection.getResponseCode());
                System.out.println("Error: {getLocation} Povided location: "+ city);
                return null;
            }
            //read
            String jsonResponse = readApiResponse(apiConnection);

            // parse
            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);

            // retrieve
            JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
            return (JSONObject) locationData.get(0);

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //read response
    private static String readApiResponse(HttpURLConnection apiConnection) {
        try {
            StringBuilder resultJson = new StringBuilder();
            //read inputstream
            Scanner scanner = new Scanner(apiConnection.getInputStream());

            // loop through response and append it to the stringBuilder
            while (scanner.hasNext()) {
                // Read and append line to the stringBuilder
                resultJson.append(scanner.nextLine());
            }
            scanner.close();
            return resultJson.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static HttpURLConnection fetchApiResponse(String urlString){
        try{
            // attempt to create connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set request method to get
            conn.setRequestMethod("GET");

            return conn;
        }catch(IOException e){
            e.printStackTrace();
        }

        // could not make connection
        return null;
    }
    public static String[] getWeatherData(double latitude, double longitude){
        String[] response = new String[]{"","","","","","","","",""};
        try{
//            String urltest = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
//                    "&longitude=" + longitude + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m";

            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude="+ longitude +"" +
                    "&current=temperature_2m,relative_humidity_2m,precipitation,weather_code," +
                    "cloud_cover,surface_pressure,wind_speed_10m,wind_direction_10m,precipitation_probability";
            HttpURLConnection apiConnection = fetchApiResponse(url);

            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error:{getWeather} Could not connect to API: Status: "+ apiConnection.getResponseCode());
                return response = new String[]{"Error:{getWeather} Could not connect to API: Status: "+ apiConnection.getResponseCode()};
            }

            String jsonResponse = readApiResponse(apiConnection);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current");

            //data format
            String time = (String) currentWeatherJson.get("time");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(time);
            String formattedDate = dateTime.format(formatter);
            response[0]=formattedDate;

            long weatherCode = (long) currentWeatherJson.get("weather_code");
            response[1]=new WeatherFormatter().formatWeatherCode(weatherCode);

            double temperature = (double) currentWeatherJson.get("temperature_2m");
            response[2]= String.valueOf(temperature);

            long relativeHumidity = (long) currentWeatherJson.get("relative_humidity_2m");
            response[3]= String.valueOf(relativeHumidity);

            double windSpeed = (double) currentWeatherJson.get("wind_speed_10m");
            long windDirection = (long) currentWeatherJson.get("wind_direction_10m");
            response[4]= windSpeed +" km/h      Kierunek: "+new WeatherFormatter().formatWindDirection(windDirection);

            long precipitationProbability = (long) currentWeatherJson.get("precipitation_probability");
            response[5]= String.valueOf(precipitationProbability);

            double precipitation = (double) currentWeatherJson.get("precipitation");
            response[6]= String.valueOf(precipitation);

            long cloudCover = (long) currentWeatherJson.get("cloud_cover");
            response[7]= String.valueOf(cloudCover);

            double surfacePressure = (double) currentWeatherJson.get("surface_pressure");
            response[8]= String.valueOf(surfacePressure);
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
