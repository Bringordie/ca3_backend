package dtos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import utils.HttpUtils;

public class WeatherDTO implements DTOInterface {
    
    private String country_code;
    private String city_name;
    private String temp;
//    private int lon;
//    private int lat;
//    private String coord = Integer.toString(lon) + " " + Integer.toString(lat);
    
    public WeatherDTO(String countryCode, String cityName, String temp) {
        this.country_code = countryCode;
        this.city_name = cityName;
        this.temp = temp;
    }

    public WeatherDTO() {
    }
    

    public void setCountryCode(String countryCode) {
        this.country_code = countryCode;
    }

    public void setCityName(String cityName) {
        this.city_name = cityName;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCountryCode() {
        return country_code;
    }

    public String getCityName() {
        return city_name;
    }

    public String getTemp() {
        return temp;
    }
   
    
    @Override
    public void fetch() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String weather = HttpUtils.fetchData("https://api.weatherbit.io/v2.0/current?city=Copenhagen,DK&key=de4ff00ad5a24948967c5a21d3892aea", "", "");
        WeatherDTO weatherDTO = gson.fromJson(weather, WeatherDTO.class);
        this.country_code = weatherDTO.country_code;
        this.city_name = weatherDTO.city_name;
        this.temp = weatherDTO.temp;
        
    }
    
}
