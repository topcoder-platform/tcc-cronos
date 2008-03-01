/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.service.prerequisite.document.DocumentManager;
import com.topcoder.service.prerequisite.document.ejb.DocumentManagerRemote;
import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;
import com.topcoder.service.prerequisite.document.model.MemberAnswer;

/**
 * <p>
 * Stress test cases for Prerequisite Document manager.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class PrerequisiteDocumentManagerStressTests extends BaseTestCase {
    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    private static final long NUMBER = 5;

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    private long current = -1;

    /**
     * <p>
     * Represents the <code>DocumentManager</code> instance.
     * </p>
     */
    private DocumentManager instance;

    /**
     * <p>Setup test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        super.setUp();
        executeScriptFile("test_files/stresstests/clean.sql");
        instance = (DocumentManagerRemote) getInitialContext().lookup(JNDI_NAME);
    }

    /**
     * <p>Tears down test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        executeScriptFile("test_files/stresstests/clean.sql");

        super.tearDown();
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(PrerequisiteDocumentManagerStressTests.class);
    }

    /**
     * <p>Tests DocumentManagerBean#addDocument(Document) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testAddDocument() throws Exception {
        Document document = new Document();
        Date now = new Date();
        document.setCreateDate(now);

        Set<DocumentVersion> documentVersions = new HashSet<DocumentVersion>();
        documentVersions.add(new DocumentVersion());
        document.setDocumentVersions(documentVersions);

        start();

        for (int i = 0; i < NUMBER; i++) {
            Document newDocument = instance.addDocument(document);

            assertNotNull("The document id should be populated.", newDocument.getDocumentId());
            assertTrue("The document version set should be empty.", newDocument.getDocumentVersions().isEmpty());
        }

        this.printResult("DocumentManagerBean#addDocument(Document)", NUMBER);

    }

    /**
     * <p>Tests DocumentManagerBean#getAllDocuments() for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetAllDocuments() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 2)"};
        executeSQL(sqls);

        start();

        for (int i = 0; i < NUMBER; i++) {
            List<Document> documents = instance.getAllDocuments();
            assertNotNull("Failed to get all documents.", documents);
            assertEquals("The list should have two elements.", sqls.length, documents.size());
        }

        this.printResult("DocumentManagerBean#getAllDocuments()", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#addDocumentVersion(DocumentVersion) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testAddDocumentVersion() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        start();

        for (int i = 0; i < NUMBER; i++) {
            DocumentVersion documentVersion = instance.addDocumentVersion(createDocumentVersion(2));

            assertNotNull("The document version id should be populated.", documentVersion.getDocumentVersionId());
        }

        this.printResult("DocumentManagerBean#addDocumentVersion(DocumentVersion)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#updateDocumentVersion(DocumentVersion) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testUpdateDocumentVersion() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 2)",
            "insert into document_name (document_name_id, name) values (1, 'Design')",
            "insert into document_name (document_name_id, name) values (2, 'Develop')"};
        executeSQL(sqls);

        DocumentVersion documentVersion = instance.addDocumentVersion(createDocumentVersion(1));

        documentVersion.setContent("I love it.");
        Date now = new Date();
        documentVersion.setVersionDate(now);
        documentVersion.setDocumentVersion(10);
        documentVersion.getDocument().setDocumentId(2L);
        documentVersion.getDocumentName().setDocumentNameId(2L);

        start();

        for (int i = 0; i < NUMBER; i++) {
            instance.updateDocumentVersion(documentVersion);

            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                conn = getDBConnectionFactory().createConnection();
                pstmt = conn.prepareStatement("SELECT * FROM document_version WHERE document_version_id=?");
                pstmt.setLong(1, documentVersion.getDocumentVersionId());

                rs = pstmt.executeQuery();

                assertTrue("No record found.", rs.next());

                assertEquals("The version date is incorrect.", now.getTime(),
                    rs.getTimestamp("version_date").getTime(), 1000);
                assertEquals("The content is incorrect.", "I love it.", rs.getString("content"));
                assertEquals("The version is incorrect.", 10, rs.getInt("version"));
                assertEquals("The document id is incorrect.", 2, rs.getLong("document_id"));
                assertEquals("The document name id is incorrect.", 2, rs.getLong("document_name_id"));
            } finally {
                closeResultSet(rs);
                closeStatement(pstmt);
                closeConnection(conn);
            }
        }

        this.printResult("DocumentManagerBean#updateDocumentVersion(DocumentVersion)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#getDocumentVersion(long,Integer) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetDocumentVersion1() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        start();

        for (int i = 0; i < NUMBER; i++) {
            instance.addDocumentVersion(createDocumentVersion(1));

            assertNull("Failed to get document version.", instance.getDocumentVersion(2l, null));
        }

        this.printResult("DocumentManagerBean#getDocumentVersion(long,Integer)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#getDocumentVersion(long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetDocumentVersion2() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 2)",
            "insert into document_name (document_name_id, name) values (1, 'Design')",
            "insert into document_name (document_name_id, name) values (2, 'Develop')"};
        executeSQL(sqls);

        start();

        for (int i = 0; i < NUMBER; i++) {
            DocumentVersion documentVersion = instance.addDocumentVersion(createDocumentVersion(1));
            DocumentVersion retrievedDocumentVersion = instance.getDocumentVersion(
                documentVersion.getDocumentVersionId());

            assertEquals("Incorrect document version", documentVersion.getDocumentVersion(),
                retrievedDocumentVersion.getDocumentVersion());

            assertEquals("The version date is incorrect.", documentVersion.getVersionDate().getTime(),
                retrievedDocumentVersion.getVersionDate().getTime(), 1000);
            assertEquals("The content is incorrect.", documentVersion.getContent(),
                retrievedDocumentVersion.getContent());
            assertEquals("The document id is incorrect.", documentVersion.getDocument().getDocumentId(),
                retrievedDocumentVersion.getDocument().getDocumentId());
            assertEquals("The document name id is incorrect.", documentVersion.getDocumentName().getDocumentNameId(),
                retrievedDocumentVersion.getDocumentName().getDocumentNameId());
        }

        this.printResult("DocumentManagerBean#getDocumentVersion(long)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#addMemberAnswer(MemberAnswer) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testAddMemberAnswer() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            Document document = new Document();
            document.setCreateDate(new Date());
            document = instance.addDocument(document);

            DocumentName documentName = new DocumentName();
            documentName.setName("TopCoder");
            documentName = instance.addDocumentName(documentName);

            DocumentVersion documentVersion = createDocumentVersion(0);
            documentVersion.setDocument(document);
            documentVersion.setDocumentName(documentName);

            documentVersion = instance.addDocumentVersion(documentVersion);

            CompetitionDocument competitionDocument = createCompetitionDocument(0);
            competitionDocument.setDocumentVersion(documentVersion);

            competitionDocument = instance.addCompetitionDocument(competitionDocument);

            MemberAnswer memberAnswer = createMemberAnswer(0);
            memberAnswer.setCompetitionDocument(competitionDocument);

            MemberAnswer newMemberAnswer = instance.addMemberAnswer(memberAnswer);

            assertNotNull("The id attribute should be populated.", newMemberAnswer.getMemberAnswerId());
        }

        this.printResult("DocumentManagerBean#addMemberAnswer(MemberAnswer)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#deleteMemberAnswer(long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testDeleteMemberAnswer() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertFalse("Failed to delete member answer.", instance.deleteMemberAnswer(1L));
        }

        this.printResult("DocumentManagerBean#deleteMemberAnswer(long)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#getMemberAnswer(long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetMemberAnswer() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertNull("Failed to get member answer.", instance.getMemberAnswer(1L));
        }

        this.printResult("DocumentManagerBean#getMemberAnswer(long)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#addCompetitionDocument(CompetitionDocument) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testAddCompetitionDocument() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = instance.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = instance.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = instance.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        start();

        for (int i = 0; i < NUMBER; i++) {
            CompetitionDocument newCompetitionDocument = instance.addCompetitionDocument(competitionDocument);

            assertNotNull("The competition document id should be populated.", newCompetitionDocument);
        }

        this.printResult("DocumentManagerBean#addCompetitionDocument(CompetitionDocument)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#deleteCompetitionDocuments(long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testDeleteCompetitionDocuments1() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertFalse("False should return.", instance.deleteCompetitionDocuments(1L));
        }

        this.printResult("DocumentManagerBean#deleteCompetitionDocument(long)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#deleteCompetitionDocuments(long,long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testDeleteCompetitionDocuments2() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertFalse("False should return.", instance.deleteCompetitionDocuments(1L, 1L));
        }

        this.printResult("DocumentManagerBean#deleteCompetitionDocument(long,long)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#getCompetitionDocument(long,long,long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetCompetitionDocument1() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertNull("Null should return.", instance.getCompetitionDocument(1L, 1L, 1L));
        }

        this.printResult("DocumentManagerBean#getCompetitionDocument(long,long,long)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#getCompetitionDocument(long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetCompetitionDocument2() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertNull("Null should return.", instance.getCompetitionDocument(1L));
        }

        this.printResult("DocumentManagerBean#getCompetitionDocument(long)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#getCompetitionDocumentsByRole(long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetCompetitionDocumentsByRole() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            List<CompetitionDocument> competitionDocuments = instance.getCompetitionDocumentsByRole(1L);
            assertNotNull("Never return null.", competitionDocuments);
            assertTrue("The list should be empty.", competitionDocuments.isEmpty());
        }

        this.printResult("DocumentManagerBean#getCompetitionDocumentsByRole(long)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#getCompetitionDocumentsByCompetition(long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetCompetitionDocumentsByCompetition() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            List<CompetitionDocument> competitionDocuments = instance.getCompetitionDocumentsByCompetition(1L);
            assertNotNull("Never return null.", competitionDocuments);
            assertTrue("The list should be empty.", competitionDocuments.isEmpty());
        }

        this.printResult("DocumentManagerBean#getCompetitionDocumentsByCompetition(long)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#getCompetitionDocuments(long,long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetCompetitionDocuments() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            List<CompetitionDocument> competitionDocuments = instance.getCompetitionDocuments(1L, 1L);
            assertNotNull("Never return null.", competitionDocuments);
            assertTrue("The list should be empty.", competitionDocuments.isEmpty());
        }

        this.printResult("DocumentManagerBean#getCompetitionDocuments(long,long)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#addDocumentName(DocumentName) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testAddDocumentName() throws Exception {
        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");

        start();

        for (int i = 0; i < NUMBER; i++) {
            DocumentName newDocumentName = instance.addDocumentName(documentName);

            assertNotNull("The document name id should be populated.", newDocumentName.getDocumentNameId());
        }

        this.printResult("DocumentManagerBean#addDocumentName(DocumentName)", NUMBER);

    }

    /**
     * <p>Tests DocumentManagerBean#getDocumentName(long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetDocumentName() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertNull("Null should return.", instance.getDocumentName(1L));
        }

        this.printResult("DocumentManagerBean#getDocumentName(long)", NUMBER);

    }

    /**
     * <p>Tests DocumentManagerBean#getAllDocumentNames() for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetAllDocumentNames() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            List<DocumentName> documentNames = instance.getAllDocumentNames();
            assertNotNull("Never return null.", documentNames);
            assertTrue("The list should be empty.", documentNames.isEmpty());
        }

        this.printResult("DocumentManagerBean#getAllDocumentNames()", NUMBER);

    }

    /**
     * <p>Tests DocumentManagerBean#isCompetitionDocumentAnswered(long,long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testIsCompetitionDocumentAnswered() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = instance.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = instance.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = instance.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument = instance.addCompetitionDocument(competitionDocument);

        start();

        for (int i = 0; i < NUMBER; i++) {
            assertFalse("the competition document is not answered.", instance.isCompetitionDocumentAnswered(
                competitionDocument.getCompetitionDocumentId(), 100));
        }
        this.printResult("DocumentManagerBean#isCompetitionDocumentAnswered(long,long)", NUMBER);
    }

    /**
     * <p>Tests DocumentManagerBean#getDocument(long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetDocument() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document (description, create_date, document_id) values ('Hello1', '2008-02-21 12:53:45', 2)"};
        executeSQL(sqls);

        start();

        for (int i = 0; i < NUMBER; i++) {
            Document document = instance.getDocument(1);
            assertNotNull("Null should return.", document);
            assertEquals("Incorrect description.", "Hello", document.getDescription());
            assertEquals("Incorrect create date.", "2008-02-20 12:53:45", DATE_FORMAT.format(document.getCreateDate()));
            assertTrue("The document versions set should be empty.", document.getDocumentVersions().isEmpty());
        }

        this.printResult("DocumentManagerBean#getAllDocumentNames()", NUMBER);

    }

    /**
     * <p>
     * Starts to count time.
     * </p>
     */
    private void start() {
        current = System.currentTimeMillis();
    }

    /**
     * <p>
     * Prints test result.
     * </p>
     *
     * @param name the test name
     * @param count the run count
     */
    private void printResult(String name, long count) {
        System.out.println("The test [" + name + "] run " + count + " times, took time: "
            + (System.currentTimeMillis() - current) + " ms");
    }
}