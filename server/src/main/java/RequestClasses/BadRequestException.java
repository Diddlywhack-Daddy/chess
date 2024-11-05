package RequestClasses;

public class BadRequestException extends Exception{
    public BadRequestException(String message) {
        super(message);
    }
}
//throws a 400 error
