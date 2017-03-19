INSERT INTO DOCUMENT (number, content, title, status, creator_id, editor_id, verifier_id, publisher_id ,created_at, changed_at, published_at, verified_at, document_type)
VALUES ('1', 'test fancy content0', 'test title0', 'DRAFT', '1','2','3','1', TIMESTAMP '2017-01-01 10:44:00', TIMESTAMP '2017-01-01 10:44:00',TIMESTAMP '2017-01-01 10:44:00',TIMESTAMP '2017-01-01 10:44:00','MANUAL');

INSERT INTO DOCUMENT (number, content, title, status, creator_id, editor_id, verifier_id, publisher_id ,created_at, changed_at, published_at, verified_at, document_type)
VALUES ('2', 'test content0', 'test fancy title0', 'PUBLISHED', '1','2','3','1', TIMESTAMP '2017-01-01 10:50:00', TIMESTAMP '2017-01-01 10:50:00', TIMESTAMP '2017-01-01 10:50:00', TIMESTAMP '2017-01-01 10:50:00','MANUAL');

INSERT INTO DOCUMENT (number, content, title, status, creator_id, editor_id, verifier_id, publisher_id ,created_at, changed_at, published_at, verified_at, document_type)
VALUES ('3fancy', 'test content1', 'test title0', 'DRAFT', '10','20','30','40', TIMESTAMP '2017-01-02 10:44:00', TIMESTAMP '2017-01-02 10:44:00', TIMESTAMP '2017-01-02 10:44:00', TIMESTAMP '2017-01-02 10:44:00','MANUAL');

INSERT INTO DOCUMENT (number, content, title, status, creator_id, editor_id, verifier_id, publisher_id ,created_at, changed_at, published_at, verified_at, document_type)
VALUES ('4', 'test content1', 'test title1', 'DRAFT', '10','20','30','40', TIMESTAMP '2017-01-05 10:44:00', TIMESTAMP '2017-01-05 10:44:00', TIMESTAMP '2017-01-05 10:44:00', TIMESTAMP '2017-01-05 10:44:00','MANUAL');

INSERT INTO DOCUMENT (number, content, title, status, creator_id, editor_id, verifier_id, publisher_id ,created_at, changed_at, published_at, verified_at, document_type)
VALUES ('5', 'test content0', 'test fancy title0', 'ARCHIVED', '1','2','3','1', TIMESTAMP '2017-01-01 10:50:00', TIMESTAMP '2017-01-05 10:44:00', TIMESTAMP '2017-01-05 10:50:00', TIMESTAMP '2017-01-01 10:50:00','MANUAL');
