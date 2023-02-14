// Java program to get Weather from OpenWeatherMap.org and output to console
// using Maps and API calls

// Weather is represented as JSON in OpenWeatherMap, but is imported as a Map
// in the program through Google gson fromJson() method.

// io classes to import data from JSON
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// net classes to access the URL and open connections
import java.net.URL;
import java.net.URLConnection;
// HashMap and Map to represent weather data within the program 
import java.util.HashMap;
import java.util.Map;
// external Google gson class to convert JSON to Map (not inbuilt in Java)
import com.google.gson.*;
import com.google.gson.reflect.*;
// import arraylist to display weather condition data
import java.util.ArrayList;

// Driver class
public class Weather {
    
    // Commands to convert JSON to Map
    // function jsonToMap(String)
    public static Map<String, Object> jsonToMap(String str) {
        // Json stored as a Map<String key, Object return)
        Map<String, Object> map = new Gson().fromJson(
                // fromJson(String, type), type found using TypeToken<>.getType() method
                str, new TypeToken<HashMap<String, Object>>() {}.getType()
        );
        return map;
    }
    
    // main() function
    public static void main(String[] args) {
        
        // ********* ENTER API KEY AND LOCATION HERE *********
        // API key to access OpenWeatherMap
        String API_KEY = //"";
        // Location entered as "London,UK" (city,country)
        String LOCATION = //"";
        
        // URL String to access JSON of weather data, for particular LOCATION with given API_KEY and metric units
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&appid=" + API_KEY + "&units=metric";
        
        
        try {
            // Building the string that receives the JSON information (Mutable String)
            StringBuilder result = new StringBuilder();
            // Assigning URL
            URL url = new URL(urlString);
            // Opening the URL connection to OpenWeatherMap.org
            URLConnection conn = url.openConnection();
            // Start reading the information from OpenWeatherMap JSON and add to buffer
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            // copy buffer to string
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            // release memory allocated to buffer (prevent memory leak)
            rd.close();
            // Output the JSON as String within console
            System.out.print(result);
            System.out.println("\n");
            
            // Make JSON String to Map conversion
            Map<String, Object> respMap = jsonToMap(result.toString());
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());
            Map<String, Object> metaMap = jsonToMap(respMap.get("sys").toString());
            // To get weather conditions ArrayList Map (which is present as JSON objects array)
            ArrayList<Map<String, Object>> weather = (ArrayList<Map<String, Object>>) respMap.get("weather");
            
            // Print weather info
            // get(key) function of Map to return data mapped to the key
            System.out.println("City: " + respMap.get("name") + ", " + metaMap.get("country"));
            System.out.println("Weather: " + weather.get(0).get("description"));
            System.out.println("Current Temp: " + mainMap.get("temp") + "C");
            System.out.println("Feels like: " + mainMap.get("feels_like") + "C");
            System.out.println("Min Temp: " + mainMap.get("temp_min") + "C");
            System.out.println("Max Temp: " + mainMap.get("temp_max") + "C");
            System.out.println("Humidity: " + mainMap.get("humidity") + "%");
            System.out.println("Wind Speed: " + windMap.get("speed") + "kph");
        } catch (IOException e) {
            // Catch IOException if can't add during BufferedReader
            // Or if HTTP error occurs
            System.out.println(e.getMessage());
        }
    }
}
