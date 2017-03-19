package pl.com.bottega.dms.infrastructure;

import pl.com.bottega.dms.application.ConfirmationDto;
import pl.com.bottega.dms.application.DocumentDto;
import pl.com.bottega.dms.model.*;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class DocumentDtoBuilder implements DocumentBuilder {

    private DocumentDto documentDto = new DocumentDto();

    public DocumentDto getResult() {
        return documentDto;
    }

    public DocumentDtoBuilder() {
        documentDto.setConfirmations(new LinkedList<>());
    }

    @Override
    public void buildNumber(DocumentNumber documentNumber) {
        documentDto.setNumber(documentNumber.getNumber());
    }

    @Override
    public void buildTitle(String title) {
        documentDto.setTitle(title);

    }

    @Override
    public void buildContent(String content) {
        documentDto.setContent(content);
    }

    @Override
    public void buildStatus(DocumentStatus documentStatus) {
        documentDto.setStatus(documentStatus.name());
    }

    @Override
    public void buildCreatedAt(LocalDateTime createdAt) {
        documentDto.setCreatedAt(createdAt);
    }

    @Override
    public void buildDocumentType(DocumentType documentType) {
        documentDto.setDocumentType(documentType.name());
    }

    @Override
    public void buildConfirmations(EmployeeId owner, EmployeeId proxy, LocalDateTime confirmationDate) {
        ConfirmationDto confirmationDto = new ConfirmationDto();
        confirmationDto.setOwnerEmployeeId(owner.getId());
        confirmationDto.setConfirmedAt(confirmationDate);
        confirmationDto.setConfirmed(confirmationDate != null);
        if (proxy != null)
            confirmationDto.setProxyEmployeeId(proxy.getId());
        documentDto.getConfirmations().add(confirmationDto);
    }


}
