package pl.com.bottega.dms.model;

import java.time.LocalDateTime;

public interface DocumentBuilder {

    void buildNumber(DocumentNumber documentNumber);

    void buildTitle(String title);

    void buildContent(String content);

    void buildStatus(DocumentStatus documentStatus);

    void buildCreatedAt(LocalDateTime createdAt);

    void buildDocumentType(DocumentType documentType);

    void buildConfirmations(EmployeeId owner, EmployeeId proxy, LocalDateTime confirmationDate);

}
