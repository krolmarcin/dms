package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;

public class ConfirmDocumentCommand {
    private EmployeeId emplyeeId;

    public void setEmplyeeId(EmployeeId emplyeeId) {
        this.emplyeeId = emplyeeId;
    }

    public EmployeeId getEmplyeeId() {
        return emplyeeId;
    }
}
