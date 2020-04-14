package dtos;

public class JSONPlaceholderDTO {
    private String title;
    private int id;

    public JSONPlaceholderDTO(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
