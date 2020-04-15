package dtos;


public class CombinedDTO {
    private String dadJoke;
    private String dadJokeURL;
    private String chuckJoke;
    private String chuckJokeURL;
    private String dogDTOMessage;
    
    public CombinedDTO(DadDTO dadDTO, ChuckDTO chuckDTO, DogImgDTO diDTO){
        this.dadJoke = dadDTO.getJoke();
        this.dadJokeURL = "https://icanhazdadjoke.com";
        this.chuckJoke = chuckDTO.getValue();
        this.chuckJokeURL = "https://api.chucknorris.io/jokes/random";
        this.dogDTOMessage = diDTO.getMessage();
    }
}
