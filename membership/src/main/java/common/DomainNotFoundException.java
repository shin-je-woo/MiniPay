package common;

public class DomainNotFoundException extends RuntimeException {

    public DomainNotFoundException() {
        super();
    }

    public DomainNotFoundException(String message) {
        super(message);
    }
}
