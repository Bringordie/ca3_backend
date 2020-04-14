package dtos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import utils.HttpUtils;

public class DadDTO implements DTOInterface{
    private String id;
    private String joke;
    private String url;

    public DadDTO(String id, String joke) {
        this.id = id;
        this.joke = joke;
    }

    public DadDTO(String url) {
        this.url = url;
    }
    
    public String getId() {
        return id;
    }

    public String getJoke() {
        return joke;
    }

    @Override
    public void fetch() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String dad = HttpUtils.fetchData("https://icanhazdadjoke.com");
        DadDTO dadDTO = gson.fromJson(dad, DadDTO.class);
        this.id = dadDTO.getId();
        this.joke = dadDTO.getJoke();
    }
    
    
}
