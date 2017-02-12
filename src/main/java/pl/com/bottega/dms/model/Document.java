package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.*;
import pl.com.bottega.dms.model.numbers.NumberGenerator;

import static pl.com.bottega.dms.model.DocumentStatus.DRAFT;

public class Document {

    private DocumentNumber number;

    private DocumentStatus documentStatus;

    private DocumentStatus status;
    private String title;
    private String content;

    public Document(CreateDocumentCommand cmd, NumberGenerator numberGenerator) {
        this.title = cmd.getTitle();
        this.number = numberGenerator.generate();
        this.status = DRAFT;
    }

    public void change(ChangeDocumentCommand cmd) {
        this.title = cmd.getTitle();
        this.content = cmd.getContent();
    }

    public void verify() {

    }

    public void archive() {

    }

    public void publish(PublishDocumentCommand cmd) {

    }

    public void confirm(ConfirmDocumentCommand cmd) {

    }

    public void confirmFor(ConfirmForDocumentCommand cmd) {

    }

    public DocumentStatus getStatus() {
        return status;
    }

    public DocumentNumber getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
