package pl.com.bottega.dms.application.user;

public class AuthRequiedException extends RuntimeException {

    public AuthRequiedException() {
    }

    public AuthRequiedException(String message) {
        super(message);
    }

}
