package pl.com.bottega.dms.model;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;
import pl.com.bottega.dms.model.numbers.NumberGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.com.bottega.dms.model.DocumentStatus.*;

public class DocumentTest {

    private static final Long DATE_EPS = 500L;
    private CreateDocumentCommand cmd;
    private NumberGenerator numberGenerator;
    private Document document;
    private EmployeeId creatorEmployeeId, changerEmployeeId, publisherEmployeeId;

    @Before
    public void setUp() {
        cmd = new CreateDocumentCommand();
        cmd.setTitle("test title");
        numberGenerator = new StubNumberGenerator();
        creatorEmployeeId = new EmployeeId(100L);
        changerEmployeeId = new EmployeeId(200L);
        publisherEmployeeId = new EmployeeId(300L);
        document = new Document(cmd, numberGenerator, creatorEmployeeId);
    }

    @Test
    public void shouldBeDraftAfterCreate() {
        assertEquals(DocumentStatus.DRAFT, document.getStatus());
    }

    @Test
    public void shouldGenerateNumberOnCreate() {
        assertEquals(new DocumentNumber("1"), document.getNumber());
    }

    @Test
    public void shouldSetTitleOnCreate() {
        assertEquals("test title", document.getTitle());
    }

    @Test
    public void shouldChangeTitleAndContent() {
        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();
        changeDocumentCommand.setTitle("new title test");
        changeDocumentCommand.setContent("new content test");

        document.change(changeDocumentCommand, changerEmployeeId);

        assertEquals("new title test", document.getTitle());
        assertEquals("new content test", document.getContent());
    }

    @Test
    public void shouldBeVerifiedAfterVerify() {
        EmployeeId employeeId = new EmployeeId(100L);

        document.verify(employeeId);

        assertEquals(VERIFIED, document.getStatus());
    }

    @Test(expected = DocumentStatusException.class)
    public void shouldNotAllowVeryfingAlreadyVerifiedDocument() {
        EmployeeId someEmployeeId = new EmployeeId(100L);
        EmployeeId otherEmployeeId = new EmployeeId(200L);

        // given - document is already verified
        document.verify(someEmployeeId);

        // when - second verification attempt
        document.verify(otherEmployeeId);

        // then - nie ma tutaj żadych assercji bo testujemy czy kodzik produkcyjny wyrzuci wyjątek klasy DocumentStatusException
    }

    @Test
    public void shouldBeDraftAfterEdit() {
        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();
        EmployeeId someEmployeeId = new EmployeeId(100L);

        document.verify(someEmployeeId);
        changeDocumentCommand.setContent("test edycji");
        document.change(changeDocumentCommand, someEmployeeId);

        assertEquals(DRAFT, document.getStatus());
    }

    @Test
    public void shouldBePublishedAfterPublish() {
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();

        document.verify(publisherEmployeeId);
        document.publish(publishDocumentCommand, publisherEmployeeId);

        assertEquals(PUBLISHED, document.getStatus());
    }

    @Test(expected = DocumentStatusException.class)
    public void shouldNotAllowEditStatusDifferentDraftOrVerified() {
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();
        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();

        document.publish(publishDocumentCommand, publisherEmployeeId);
        changeDocumentCommand.setContent("test edycji");
        document.change(changeDocumentCommand, changerEmployeeId);
    }

    @Test
    public void shouldRememberCreateDate() {
        assertSameTime(document.getCreateDate(), LocalDateTime.now());
    }

    @Test
    public void shouldRememberVerifyDate() {
        EmployeeId someEmployeeId = new EmployeeId(123L);

        document.verify(someEmployeeId);

        assertSameTime(document.getVerifyDate(), LocalDateTime.now());
    }

    @Test
    public void shouldRememberChangeDate() {
        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();

        document.change(changeDocumentCommand, changerEmployeeId);

        assertSameTime(document.getChangeDate(), LocalDateTime.now());
    }

    @Test
    public void shouldRememberPublishDate() {
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();

        document.verify(publisherEmployeeId);
        document.publish(publishDocumentCommand, publisherEmployeeId);

        assertSameTime(document.getPublishDate(), LocalDateTime.now());
    }

    @Test
    public void shouldRememberCreatorEmployeeId() {
        EmployeeId someEmployeeId = new EmployeeId(100L);

        assertEquals(document.getCreator(), someEmployeeId);
    }

    @Test
    public void shouldRememberVerifierEmployeeId() {
        EmployeeId someEmployeeId = new EmployeeId(100L);

        document.verify(someEmployeeId);

        assertEquals(document.getVerifier(), someEmployeeId);
    }

    @Test
    public void shouldRememberChangerEmployeeId() {
        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();

        document.change(changeDocumentCommand, changerEmployeeId);

        assertEquals(document.getChanger(), changerEmployeeId);
    }

    @Test
    public void shouldRememberPublisherEmployeeId() {
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();
        EmployeeId verifierEmployeeId = new EmployeeId(100L);

        document.verify(verifierEmployeeId);
        document.publish(publishDocumentCommand, publisherEmployeeId);

        assertEquals(document.getPublisher(), publisherEmployeeId);
    }

    @Test
    public void shouldBeArchivedAferArchive(){
        EmployeeId archiverEmployeeId = new EmployeeId(500L);

        document.archive(archiverEmployeeId);

        assertEquals(ARCHIVED, document.getStatus());
    }

    @Test(expected = DocumentStatusException.class)
    public void shouldNotAllowChangeStatusAferArchive(){
        EmployeeId archiverEmployeeId = new EmployeeId(500L);
        EmployeeId someEmployee = new EmployeeId(100L);
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();
        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();

        document.archive(archiverEmployeeId);
        //document.publish(publishDocumentCommand, someEmployee);
        //document.verify(someEmployee);
        document.change(changeDocumentCommand, someEmployee);

    }

    private void assertSameTime(LocalDateTime expected, LocalDateTime actual) {
        assertTrue(ChronoUnit.MILLIS.between(expected, actual) < DATE_EPS);
    }

    class StubNumberGenerator implements NumberGenerator {
        public DocumentNumber generate() {
            return new DocumentNumber("1");
        }
    }

}
