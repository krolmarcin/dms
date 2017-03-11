package pl.com.bottega.dms.acceptance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.com.bottega.dms.application.*;
import pl.com.bottega.dms.application.user.AuthProcess;
import pl.com.bottega.dms.application.user.AuthRequiedException;
import pl.com.bottega.dms.application.user.CreateAccountCommand;
import pl.com.bottega.dms.application.user.LoginCommand;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.EmployeeId;
import pl.com.bottega.dms.model.commands.ConfirmDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmForDocumentCommand;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConfirmationTest {

    @Autowired
    private DocumentFlowProcess documentFlowProcess;

    @Autowired
    private DocumentCatalog documentCatalog;

    @Autowired
    private ReadingConfirmator readingConfirmator;

    @Autowired
    private AuthProcess authProcess;

    @Before
    public void authenticate() {
        CreateAccountCommand cmd = new CreateAccountCommand();
        cmd.setUserName("czesiek");
        cmd.setEmployeeId(2L);
        cmd.setPassword("xxxx");
        authProcess.createAccount(cmd);

        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setLogin("czesiek");
        loginCommand.setPassword("xxxx");
        authProcess.login(loginCommand);
    }



    @Test
    public void shouldConfirmDocument() throws AuthRequiedException {
        // given
        DocumentNumber documentNumber = publishedDocument();

        //when
        ConfirmDocumentCommand confirmDocumentCommand = new ConfirmDocumentCommand();
        confirmDocumentCommand.setEmployeeId(new EmployeeId(1L));
        confirmDocumentCommand.setNumber(documentNumber.getNumber());
        readingConfirmator.confirm(confirmDocumentCommand);

        //then
        DocumentDto dto = documentCatalog.get(documentNumber);
        assertThat(dto.getConfirmations().size()).isEqualTo(1);
        ConfirmationDto confirmationDto = dto.getConfirmations().get(0);
        assertThat(confirmationDto.isConfirmed()).isTrue();
        assertThat(confirmationDto.getOwnerEmployeeId()).isEqualTo(1L);
    }

    @Test
    public void shouldConfirmDocumentForAnotherEmployee() throws AuthRequiedException {
        // given
        DocumentNumber documentNumber = publishedDocument();

        //when
        ConfirmForDocumentCommand confirmDocumentCommand = new ConfirmForDocumentCommand();
        confirmDocumentCommand.setEmployeeId(new EmployeeId(1L));
        confirmDocumentCommand.setConfirmingEmployeeId(new EmployeeId(2L));
        confirmDocumentCommand.setNumber(documentNumber.getNumber());
        readingConfirmator.confirmFor(confirmDocumentCommand);

        //then
        DocumentDto dto = documentCatalog.get(documentNumber);
        assertThat(dto.getConfirmations().size()).isEqualTo(1);
        ConfirmationDto confirmationDto = dto.getConfirmations().get(0);
        assertThat(confirmationDto.isConfirmed()).isTrue();
        assertThat(confirmationDto.getOwnerEmployeeId()).isEqualTo(1L);
        assertThat(confirmationDto.getProxyEmployeeId()).isEqualTo(2L);
    }

    private DocumentNumber publishedDocument() throws AuthRequiedException {
        DocumentNumber documentNumber = createDocument();
        documentFlowProcess.verify(documentNumber);
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();
        publishDocumentCommand.setNumber(documentNumber.getNumber());
        publishDocumentCommand.setRecipients(Arrays.asList(new EmployeeId(1L)));
        documentFlowProcess.publish(publishDocumentCommand);
        return documentNumber;
    }

    private DocumentNumber createDocument() throws AuthRequiedException {
        CreateDocumentCommand cmd = new CreateDocumentCommand();
        cmd.setTitle("test");
        return documentFlowProcess.create(cmd);
    }

}