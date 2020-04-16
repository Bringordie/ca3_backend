package dtos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import utils.HttpUtils;

public class WeatherDTO implements DTOInterface{
    private List<WeatherDataObjectDTO> data;
    private int count;

    public WeatherDTO(List<WeatherDataObjectDTO> data, int count) {
        this.data = data;
        this.count = count;
    }

    public WeatherDTO() {
    }

    public List<WeatherDataObjectDTO> getData() {
        return data;
    }

    public void setData(List<WeatherDataObjectDTO> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void fetch() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String weather = HttpUtils.fetchData("https://api.weatherbit.io/v2.0/current?city=Copenhagen,DK&key=de4ff00ad5a24948967c5a21d3892aea", "", "");
        WeatherDTO wtDTO = gson.fromJson(weather, WeatherDTO.class);
        this.data = wtDTO.getData();
        this.count = wtDTO.getCount();
    }

    
    
}
