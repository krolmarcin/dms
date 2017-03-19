package pl.com.bottega.dms.model.validation;

import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentStatus;

public abstract class DocumentValidator {

    protected DocumentValidator next = new DocumentValidator() {
        @Override
        public boolean isValid(Document document, DocumentStatus targetStatus) {
            return true;
        }
    };

    public void setNext(DocumentValidator next) {
        this.next = next;
    }

    public abstract boolean isValid(Document document, DocumentStatus targetStatus);

}
