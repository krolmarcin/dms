package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;

public class ChangeDocumentCommand implements EmployeeAware, Validatable {

    private String title;

    private String content;

    private String number;

    private EmployeeId employeeId;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void validate(ValidationErrors errors) {
        if (number == null || number.isEmpty())
            errors.add("number", "can't be blanked");
        if (content == null || content.isEmpty())
            errors.add("content", "can't be blanked");
        if (title == null || title.isEmpty())
            errors.add("title", "can't be blanked");
    }

}