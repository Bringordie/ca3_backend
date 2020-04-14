package dtos;

public class ChuckDTO {
    private String createdAt;
    private String id;
    private String value;

    public ChuckDTO(String createdAt, String id, String value) {
        this.createdAt = createdAt;
        this.id = id;
        this.value = value;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
    
    
}
