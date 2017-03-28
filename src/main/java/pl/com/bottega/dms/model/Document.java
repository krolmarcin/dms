package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.*;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static pl.com.bottega.dms.model.DocumentStatus.*;
import static pl.com.bottega.dms.model.DocumentType.MANUAL;

@Entity
public class Document {

    private static final int CHARS_COUNT_PER_PAGE = 1800;

    @EmbeddedId
    private DocumentNumber number;

    @Transient
    DocumentState documentState;

    @Enumerated(EnumType.STRING)
    DocumentStatus status;
    String title;
    String content;
    LocalDateTime createdAt;
    LocalDateTime verifiedAt;
    LocalDateTime publishedAt;
    LocalDateTime changedAt;
    LocalDateTime expitesAt;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "creatorId"))
    private EmployeeId creatorId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "verifierId"))
    EmployeeId verifierId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "editorId"))
    EmployeeId editorId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "publisherId"))
    EmployeeId publisherId;
    BigDecimal printCost;
    @Enumerated(EnumType.STRING)
    DocumentType documentType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "documentNumber")
    Set<Confirmation> confirmations;

    @PostLoad
    void initStatus() {
        documentType = MANUAL;
    }

    Document() {
    }

    public Document(CreateDocumentCommand cmd, DocumentNumber documentNumber) {
        this.number = documentNumber;
        this.status = DRAFT;
        this.documentState = new DraftState(this);
        this.title = cmd.getTitle();
        this.createdAt = LocalDateTime.now();
        this.creatorId = cmd.getEmployeeId();
        this.confirmations = new HashSet<>();
        this.documentType = cmd.getDocumentType();
    }

    public void change(ChangeDocumentCommand cmd) {
        documentState.change(cmd);
    }

    public void verify(EmployeeId employeeId) {
        documentState.verify(employeeId);
    }

    public void archive(EmployeeId employeeId) {
        documentState.archive(employeeId);
    }

    public void publish(PublishDocumentCommand cmd, PrintCostCalculator printCostCalculator) {
        documentState.publish(cmd, printCostCalculator);
    }

    public void confirm(ConfirmDocumentCommand cmd) {
        documentState.confirm(cmd);
    }

    public void confirmFor(ConfirmForDocumentCommand cmd) {
        documentState.confirmFor(cmd);
    }

    public void export(DocumentBuilder builder) {
        builder.buildNumber(number);
        builder.buildTitle(title);
        builder.buildContent(content);
        builder.buildDocumentType(documentType);
        builder.buildStatus(status);
        builder.buildCreatedAt(createdAt);
        for (Confirmation confirmation : confirmations)
            builder.buildConfirmations(confirmation.getOwner(), confirmation.getProxy(), confirmation.getConfirmationDate());

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public EmployeeId getCreatorId() {
        return creatorId;
    }

    public EmployeeId getVerifierId() {
        return verifierId;
    }

    public EmployeeId getEditorId() {
        return editorId;
    }

    public EmployeeId getPublisherId() {
        return publisherId;
    }

    public BigDecimal getPrintCost() {
        return printCost;
    }

    public boolean isConfirmedBy(EmployeeId employeeId) {
        return getConfirmation(employeeId).isConfirmed();
    }

    public Set<Confirmation> getConfirmations() {
        return Collections.unmodifiableSet(confirmations);
    }

    public Confirmation getConfirmation(EmployeeId employeeId) {
        for (Confirmation confirmation : confirmations) {
            if (confirmation.isOwnedBy(employeeId))
                return confirmation;
        }
        throw new DocumentStatusException(String.format("No confirmation for %s", employeeId));
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public LocalDateTime getExpiresAt() {
        return expitesAt;
    }

    public int getPagesCount() {
        if (content == null)
            return 0;
        return content.length() / CHARS_COUNT_PER_PAGE + (content.length() % CHARS_COUNT_PER_PAGE == 0 ? 0 : 1);
    }

}