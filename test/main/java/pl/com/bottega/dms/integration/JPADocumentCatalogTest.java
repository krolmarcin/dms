package pl.com.bottega.dms.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.DocumentQuery;
import pl.com.bottega.dms.application.DocumentSearchResults;
import pl.com.bottega.dms.application.user.AuthProcess;
import pl.com.bottega.dms.application.user.CreateAccountCommand;
import pl.com.bottega.dms.application.user.LoginCommand;
import pl.com.bottega.dms.infrastructure.DocumentNotFoundException;
import pl.com.bottega.dms.infrastructure.JPADocumentCatalog;
import pl.com.bottega.dms.infrastructure.JPQLDocumentCatalog;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.shared.AuthHelper;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class JPADocumentCatalogTest {

    @Autowired
    private JPADocumentCatalog catalog;
    //private JPQLDocumentCatalog catalog;

    @Autowired
    private AuthHelper authHelper;

    @Before
    public void authenticate() {
        authHelper.authenticate();
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    public void shouldFindDocumentsByPhrase() {
        //given to skrypt documentByPhase.sql
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setPhrase("fancy");
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(3);
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    public void shouldFindDocumentByPhraseAndStatus() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setPhrase("fancy");
        documentQuery.setStatus("DRAFT");
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(2);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("1");
        assertThat(searchResults.getDocuments().get(1).getNumber()).isEqualTo("3fancy");
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    public void shouldFindDocumentByCreatorId() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setCreatorId(1L);
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(2);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("1");
        assertThat(searchResults.getDocuments().get(1).getNumber()).isEqualTo("2");
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    public void shouldFindDocumentByCreatedAt() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setCreatedAfter(LocalDateTime.parse("2017-01-01T10:30"));
        documentQuery.setCreatedBefore(LocalDateTime.parse("2017-01-01T11:00"));
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(2);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("1");
        assertThat(searchResults.getDocuments().get(1).getNumber()).isEqualTo("2");
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    public void shouldReturnPaginatedResults() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setPageNumber(2);
        documentQuery.setPerPage(2);
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(2);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("3fancy");
        assertThat(searchResults.getDocuments().get(1).getNumber()).isEqualTo("4");
        assertThat(searchResults.getPagesCount()).isEqualTo(2);
        assertThat(searchResults.getPageNumber()).isEqualTo(2);
        assertThat(searchResults.getPerPage()).isEqualTo(2);
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    public void shouldNotFindDocumentsInArchivedStatus() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setStatus("ARCHIVED");
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(0);
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    public void shouldFindDocumentByChangedAtButNotInStatusArchived() {
        //changedAt = 2017-01-05 10:44:00, one in DRAFT, second in ARCHIVED
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setChangedAfter(LocalDateTime.parse("2017-01-05T10:43:00"));
        documentQuery.setChangedBefore(LocalDateTime.parse("2017-01-05T10:45:00"));
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then - only one in DRAFT, but not in ARCHIVED
        assertThat(searchResults.getDocuments().size()).isEqualTo(1);
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    public void shouldFindDocumentByCreatorEditorVerifierPublisherIdsSortByNumberAsc() {
        //when - creator_id, editor_id, verifier_id, publisher_id - '10','20','30','40' + sort by number + asc;
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setCreatorId(10L);
        documentQuery.setEditorId(20L);
        documentQuery.setVerifierId(30L);
        documentQuery.setPublisherId(40L);
        documentQuery.setSortBy("number");
        documentQuery.setSortOrder("asc");
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(2);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("4");
        assertThat(searchResults.getDocuments().get(1).getNumber()).isEqualTo("3fancy");
    }

    @Test(expected = DocumentNotFoundException.class)
    @Sql("/fixtures/documentByPhrase.sql")
    public void shouldFindDocumentsByNumber() {
        //when
        catalog.get(new DocumentNumber("1111"));

        //then
    }


    }
