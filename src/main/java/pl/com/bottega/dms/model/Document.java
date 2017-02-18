package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.*;
import pl.com.bottega.dms.model.numbers.NumberGenerator;

import java.time.LocalDateTime;

import static pl.com.bottega.dms.model.DocumentStatus.*;

public class Document {

    private DocumentNumber number;

    private DocumentStatus status;

    private String title;

    private String content;

    private ChangeDocumentCommand cmd;

    private LocalDateTime createDate, changeDate, verifyDate, publishDate, archiveDate;

    private EmployeeId creator, verifier, changer, publisher, archiver;


    public Document(CreateDocumentCommand cmd, NumberGenerator numberGenerator, EmployeeId creatorEmployeeId) {
        this.creator = creatorEmployeeId;
        this.title = cmd.getTitle();
        this.number = numberGenerator.generate();
        this.status = DRAFT;
        this.createDate = LocalDateTime.now();
    }

    public void change(ChangeDocumentCommand cmd, EmployeeId changerEmployeeId) {
        if (status.equals(DRAFT) || status.equals(VERIFIED)) {
            this.title = cmd.getTitle();
            this.content = cmd.getContent();
            this.status = DRAFT;
            this.changeDate = LocalDateTime.now();
            this.changer = changerEmployeeId;
        } else {
            String message = String.format("Document can be edit only in DRAFT or VERIFIED, status: %s", status);
            throw new DocumentStatusException(message);
        }
    }

    public void verify(EmployeeId verifierEmployeeId) {
        if (status != DRAFT) {
            String message = String.format("Document can be verify only in DRAFT, status: %s", status);
            throw new DocumentStatusException(message);
        }
        this.verifier = verifierEmployeeId;
        this.status = VERIFIED;
        this.verifyDate = LocalDateTime.now();
    }

    public void archive(EmployeeId archiverEmployeeId) {
        this.archiver = archiverEmployeeId;
        this.archiveDate = LocalDateTime.now();
        this.status = ARCHIVED;
    }

    public void publish(PublishDocumentCommand cmd, EmployeeId publisherEmployeeId) {
        if (status != VERIFIED) {
            String message = String.format("Only VERIFIED can be publish, status: %s", status);
            throw new DocumentStatusException(message);
        }
        this.publisher = publisherEmployeeId;
        this.status = PUBLISHED;
        this.publishDate = LocalDateTime.now();
    }

    private boolean isArchived() {
        return status.equals(ARCHIVED);
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getVerifyDate() {
        return verifyDate;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public LocalDateTime getArchiveDate() {
        return archiveDate;
    }


    public EmployeeId getCreator() {
        return creator;
    }

    public EmployeeId getVerifier() {
        return verifier;
    }

    public EmployeeId getChanger() {
        return changer;
    }

    public EmployeeId getPublisher() {
        return publisher;
    }

    public EmployeeId getArchiver() {
        return archiver;
    }

}
