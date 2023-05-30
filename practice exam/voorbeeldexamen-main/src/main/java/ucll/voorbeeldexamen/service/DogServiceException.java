package ucll.voorbeeldexamen.service;

public class DogServiceException extends Exception {
    private String field;

    public DogServiceException ( String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return this.field;
    }
}
   