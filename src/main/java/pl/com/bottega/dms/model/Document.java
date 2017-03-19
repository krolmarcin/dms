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

@Entity
public class Document {

    private static final int CHARS_COUNT_PER_PAGE = 1800;
    @EmbeddedId
    private DocumentNumber number;
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime verifiedAt;
    private LocalDateTime publishedAt;
    private LocalDateTime changedAt;
    private LocalDateTime expitesAt;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "creatorId"))
    private EmployeeId creatorId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "verifierId"))
    private EmployeeId verifierId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "editorId"))
    private EmployeeId editorId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "publisherId"))
    private EmployeeId publisherId;
    private BigDecimal printCost;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "documentNumber")
    private Set<Confirmation> confirmations;

    Document() {
    }

    public Document(CreateDocumentCommand cmd, DocumentNumber documentNumber) {
        this.number = documentNumber;
        this.documentType = cmd.getDocumentType();
        this.status = DRAFT;
        this.title = cmd.getTitle();
        this.createdAt = LocalDateTime.now();
        this.creatorId = cmd.getEmployeeId();
        this.confirmations = new HashSet<>();
    }

    public void change(ChangeDocumentCommand cmd) {
        if (!this.status.equals(DRAFT) && !this.status.equals(VERIFIED))
            throw new DocumentStatusException("Document should be DRAFT or VERIFIED to PUBLISH");
        this.title = cmd.getTitle();
        this.content = cmd.getContent();
        this.status = DRAFT;
        this.changedAt = LocalDateTime.now();
        this.editorId = cmd.getEmployeeId();
    }

    public void verify(EmployeeId employeeId) {
        if (!this.status.equals(DRAFT))
            throw new DocumentStatusException("Document should be DRAFT to VERIFY");
        this.status = VERIFIED;
        this.verifiedAt = LocalDateTime.now();
        this.verifierId = employeeId;
    }

    public void archive(EmployeeId employeeId) {
        this.status = ARCHIVED;
    }

    public void publish(PublishDocumentCommand cmd, PrintCostCalculator printCostCalculator) {
        if (!this.status.equals(VERIFIED))
            throw new DocumentStatusException("Document should be VERIFIED to PUBLISH");
        this.status = PUBLISHED;
        this.publishedAt = LocalDateTime.now();
        this.publisherId = cmd.getEmployeeId();
        this.printCost = printCostCalculator.calculateCost(this);
        createConfirmations(cmd);
    }

    private void createConfirmations(PublishDocumentCommand cmd) {
        for (EmployeeId employeeId : cmd.getRecipients()) {
            confirmations.add(new Confirmation(employeeId));
        }
    }

    public void confirm(ConfirmDocumentCommand cmd) {
        Confirmation confirmation = getConfirmation(cmd.getEmployeeId());
        confirmation.confirm();
    }

    public void confirmFor(ConfirmForDocumentCommand cmd) {
        Confirmation confirmation = getConfirmation(cmd.getConfirmForEmployeeId());
        confirmation.confirmFor(cmd.getEmployeeId());
    }

    public void export(DocumentBuilder builder){
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

    public int getPagesCount() {
        if (content == null)
            return 0;
        return content.length() / CHARS_COUNT_PER_PAGE + (content.length() % CHARS_COUNT_PER_PAGE == 0 ? 0 : 1);
    }

    public LocalDateTime getExpiresAt() {
        return expitesAt;
    }
}