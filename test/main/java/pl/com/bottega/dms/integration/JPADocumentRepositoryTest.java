package pl.com.bottega.dms.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.infrastructure.DocumentNotFoundException;
import pl.com.bottega.dms.infrastructure.JPADocumentRepository;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.DocumentStatusException;
import pl.com.bottega.dms.shared.AuthHelper;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class JPADocumentRepositoryTest {

    @Autowired
    private JPADocumentRepository documentRepository;

    @Test
    @Sql("/fixtures/documentByNumber.sql")
    public void shouldFindDocumentByNumber(){
        //given -sql
        //when
        DocumentNumber documentNumber = new DocumentNumber("1");
        Document document = documentRepository.get(documentNumber);

        //then
        assertThat(document.getNumber()).isEqualTo(new DocumentNumber("1"));
    }

    @Test(expected = DocumentNotFoundException.class)
    @Sql("/fixtures/documentByNumber.sql")
    public void shouldNotFindDocumentByNumber(){
        //given -sql
        //when
        DocumentNumber documentNumber = new DocumentNumber("123");
        documentRepository.get(documentNumber);

        //then - exception
    }

}
