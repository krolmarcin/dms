package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

import java.util.Collection;
import java.util.List;

public class PublishDocumentCommand implements EmployeeAware {

    private EmployeeId employeeId;

    private Collection<EmployeeId> recipients;

    private String number;

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    public void setRecipients(Collection<EmployeeId> recipients) {
        this.recipients = recipients;
    }

    public Collection<EmployeeId> getRecipients() {
        return recipients;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
