package pl.com.bottega.dms.infrastructure;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.DocumentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional
public class JPADocumentRepository implements DocumentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void put(Document document) {
        entityManager.persist(document);
    }

    @Override
    public Document get(DocumentNumber documentNumber) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> criteriaQuery = builder.createQuery(Document.class);
        Root<Document> root = criteriaQuery.from(Document.class);
        criteriaQuery.where(builder.equal(root.get("number"), documentNumber));
        TypedQuery<Document> query = entityManager.createQuery(criteriaQuery);
        return documentQuery(query, documentNumber);
    }

    private Document documentQuery(TypedQuery<Document> query, DocumentNumber documentNumber) {
        List<Document> documents = query.getResultList();
        if (documents.size() == 0) {
            throw new DocumentNotFoundException(String.format("Document number %s does not exists", documentNumber.getNumber()));
        } else
            return documents.get(0);
    }

}