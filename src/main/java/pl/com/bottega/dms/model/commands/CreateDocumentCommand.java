package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.DocumentType;
import pl.com.bottega.dms.model.EmployeeId;

public class CreateDocumentCommand implements EmployeeAware, Validatable {

    private String title;

    private String content;

    private EmployeeId employeeId;

    private DocumentType documentType;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    @Override
    public void validate(Validatable.ValidationErrors errors) {
        if (title == null || title.isEmpty())
            errors.add("title", "can't be blanked");
        if (content == null || content.isEmpty())
            errors.add("content", "can't be blanked");
        if (documentType == null)
            errors.add("documentType", "can't be blanked");
    }
}