package dtos;


public class CombinedDTO {
    private String dadJoke;
    private String dadJokeURL;
    private String chuckJoke;
    private String chuckJokeURL;
    private String dogDTOMessage;
    private String weatherCountry;
    private String weatherCity;
    private String weatherTemp;
    private String weatherURL;
    
    public CombinedDTO(DadDTO dadDTO, ChuckDTO chuckDTO, DogImgDTO diDTO, WeatherDTO weatherDTO){
        this.dadJoke = dadDTO.getJoke();
        this.dadJokeURL = "https://icanhazdadjoke.com";
        this.chuckJoke = chuckDTO.getValue();
        this.chuckJokeURL = "https://api.chucknorris.io/jokes/random";
        this.dogDTOMessage = diDTO.getMessage();
        this.weatherCountry = weatherDTO.getCountryCode();
        this.weatherCity = weatherDTO.getCityName();
        this.weatherTemp = weatherDTO.getTemp();
        this.weatherURL = "https://api.weatherbit.io/v2.0/current?city=Copenhagen,DK&key=de4ff00ad5a24948967c5a21d3892aea";
    }
}
