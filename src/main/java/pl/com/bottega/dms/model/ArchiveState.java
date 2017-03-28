package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmForDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

public class ArchiveState implements DocumentState {

    private Document document;

    public ArchiveState(Document document) {
        this.document = document;
    }

    @Override
    public void change(ChangeDocumentCommand cmd) {
        throw new DocumentStatusException("Document is ARCHIVED");
    }

    @Override
    public void verify(EmployeeId employeeId) {
        throw new DocumentStatusException("Document is ARCHIVED");
    }

    @Override
    public void archive(EmployeeId employeeId) {
        throw new DocumentStatusException("Document is ARCHIVED");
    }

    @Override
    public void publish(PublishDocumentCommand cmd, PrintCostCalculator printCostCalculator) {
        throw new DocumentStatusException("Document is ARCHIVED");
    }

    @Override
    public void confirm(ConfirmDocumentCommand cmd) {
        throw new DocumentStatusException("Document is ARCHIVED");
    }

    @Override
    public void confirmFor(ConfirmForDocumentCommand cmd) {
        throw new DocumentStatusException("Document is ARCHIVED");
    }
}
