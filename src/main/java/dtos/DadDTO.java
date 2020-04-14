package dtos;

public class DadDTO {
    private String id;
    private String joke;

    public DadDTO(String id, String joke) {
        this.id = id;
        this.joke = joke;
    }

    public String getId() {
        return id;
    }

    public String getJoke() {
        return joke;
    }
}
