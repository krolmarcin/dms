package pl.com.bottega.dms.infrastructure;

public class DocumentNotFoundException extends RuntimeException {

    public DocumentNotFoundException(String msg) {
        super(msg);
    }

}
