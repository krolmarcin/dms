package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;

public class CreateDocumentCommand implements EmployeeAware, Validatable {

    private String title;

    private String content;

    private EmployeeId employeeId;

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

    @Override
    public void validate(Validatable.ValidationErrors errors) {
        if (title == null || title.isEmpty())
            errors.add("number", "can't be blanked");
        if (content == null || content.isEmpty())
            errors.add("number", "can't be blanked");
    }

}