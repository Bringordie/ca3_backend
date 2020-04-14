package dtos;


public class CombinedDTO {
    private String dadJoke;
    private String dadJokeURL;
    private String chuckJoke;
    private String chuckJokeURL;
    
    public CombinedDTO(DadDTO dadDTO, ChuckDTO chuckDTO){
        this.dadJoke = dadDTO.getJoke();
        this.dadJokeURL = "https://icanhazdadjoke.com";
        this.chuckJoke = chuckDTO.getValue();
        this.chuckJokeURL = "https://api.chucknorris.io/jokes/random";
    }
}
