package pl.com.bottega.dms.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.numbers.NumberGenerator;

import static org.junit.Assert.assertEquals;

public class DocumentTest {

    private CreateDocumentCommand cmd;
    private NumberGenerator numberGenerator;
    private Document document;

    @Before
    public void setUp() {
        cmd = new CreateDocumentCommand();
        cmd.setTitle("test title");
        numberGenerator = new StubNumberGenerator();
        document = new Document(cmd, numberGenerator);

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
    public void shouldChangeTitleAndContent(){
        String titleAfterChange = "new title test";
        String contentAfterChange = "new content test";

        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();
        changeDocumentCommand.setTitle(titleAfterChange);
        changeDocumentCommand.setContent(contentAfterChange);

        document.change(changeDocumentCommand);

        assertEquals(titleAfterChange, document.getTitle());
        assertEquals(contentAfterChange, document.getContent());
    }

    class StubNumberGenerator implements NumberGenerator {

        public DocumentNumber generate() {
            return new DocumentNumber("1");
        }
    }

}
