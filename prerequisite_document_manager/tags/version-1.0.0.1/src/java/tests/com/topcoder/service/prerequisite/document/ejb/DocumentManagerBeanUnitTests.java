/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.ejb;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.service.prerequisite.document.BaseTestCase;
import com.topcoder.service.prerequisite.document.ConfigurationException;
import com.topcoder.service.prerequisite.document.DocumentManager;
import com.topcoder.service.prerequisite.document.DocumentPersistenceException;
import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;
import com.topcoder.service.prerequisite.document.model.MemberAnswer;

/**
 * <p>
 * Unit test for <code>{@link DocumentManagerBean}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DocumentManagerBeanUnitTests extends BaseTestCase {

    /**
     * <p>
     * Represents the <code>DocumentManager</code> instance.
     * </p>
     */
    private DocumentManager documentManager;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DocumentManagerBeanUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        executeScriptFile("test_files" + File.separator + "clean.sql");
        documentManager = (DocumentManagerRemote) getInitialContext().lookup(JNDI_NAME);
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        executeScriptFile("test_files" + File.separator + "clean.sql");

        super.tearDown();
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#init()</code> method.
     * </p>
     * <p>
     * If the persistenceUnitName is not configured, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInit_MissingPersistenceUnitName() throws Exception {
        documentManager = (DocumentManager) getInitialContext().lookup(
                "prerequisite_document_manager/DocumentManagerBeanNoPersistenceUnitName/remote");
        try {
            documentManager.getDocument(1);
            fail("If the persistenceUnitName is not configured, should throw ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("expected ConfigurationException cause", e.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#init()</code> method.
     * </p>
     * <p>
     * If the persistenceUnitName is not String type, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInit_PersistenceUnitNameInvalidType() throws Exception {
        documentManager = (DocumentManager) getInitialContext().lookup(
                "prerequisite_document_manager/DocumentManagerBeanPersistenceUnitNameInvalidType/remote");
        try {
            documentManager.getDocument(1);
            fail("If the persistenceUnitName is not String type, should throw ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("expected ConfigurationException cause", e.getCause() instanceof ConfigurationException);
        }
    }


    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#init()</code> method.
     * </p>
     * <p>
     * If the allowedRoleNames is not configured, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInit_MissingAllowedRoleNames() throws Exception {
        documentManager = (DocumentManager) getInitialContext().lookup(
                "prerequisite_document_manager/DocumentManagerBeanNoAllowedRoleNames/remote");
        try {
            documentManager.getDocument(1);
            fail("If the allowedRoleNames is not configured, should throw ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("expected ConfigurationException cause", e.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#init()</code> method.
     * </p>
     * <p>
     * If the allowedRoleNames is not String type, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInit_AllowedRoleNamesInvalidType() throws Exception {
        documentManager = (DocumentManager) getInitialContext().lookup(
                "prerequisite_document_manager/DocumentManagerBeanAllowedRoleNamesInvalidType/remote");
        try {
            documentManager.getDocument(1);
            fail("If the allowedRoleNames is not String type, should throw ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("expected ConfigurationException cause", e.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#init()</code> method.
     * </p>
     * <p>
     * If the allowedRoleNames is empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInit_AllowedRoleNamesEmpty() throws Exception {
        documentManager = (DocumentManager) getInitialContext().lookup(
                "prerequisite_document_manager/DocumentManagerBeanAllowedRoleNamesEmpty/remote");
        try {
            documentManager.getDocument(1);
            fail("If the allowedRoleNames is empty, should throw ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("expected ConfigurationException cause", e.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#init()</code> method.
     * </p>
     * <p>
     * If the allowedRoleNames is trimmed empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInit_AllowedRoleNamesTrimmedEmpty() throws Exception {
        documentManager = (DocumentManager) getInitialContext().lookup(
                "prerequisite_document_manager/DocumentManagerBeanAllowedRoleNamesTrimmedEmpty/remote");
        try {
            documentManager.getDocument(1);
            fail("If the allowedRoleNames is trimmed empty, should throw ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("expected ConfigurationException cause", e.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#init()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInit_accuracy1() throws Exception {
        documentManager.getDocument(1);
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#init()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInit_accuracy2() throws Exception {
        documentManager = (DocumentManager) getInitialContext().lookup(
                "prerequisite_document_manager/DocumentManagerBeanWithoutLogging/remote");
        documentManager.getDocument(1);
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocument(Document)</code> method.
     * </p>
     * <p>
     * If the createDate attribute of document is not set, should throw DocumentPersistenceException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocument_document_createDateNotSet() throws Exception {
        try {
            documentManager.addDocument(new Document());
            fail("If the createDate attribute of document is not set, should throw DocumentPersistenceException.");
        } catch (DocumentPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocument(Document)</code> method.
     * </p>
     * <p>
     * If the document contains all required information, should save in database.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocument_accuracy1() throws Exception {
        Document document = new Document();
        Date now = new Date();
        document.setCreateDate(now);

        Set<DocumentVersion> documentVersions = new HashSet<DocumentVersion>();
        documentVersions.add(new DocumentVersion());
        document.setDocumentVersions(documentVersions);

        document = documentManager.addDocument(document);

        assertNotNull("The document id should be populated.", document.getDocumentId());
        assertFalse("The document version set should be not empty.", document.getDocumentVersions().isEmpty());
        // verify the result.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM document WHERE document_id=?");
            pstmt.setLong(1, document.getDocumentId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("incorrect create date.", now.getTime(), rs.getTimestamp("create_date").getTime(), 1000);
            assertNull("The description should be null.", rs.getString("description"));
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocument(Document)</code> method.
     * </p>
     * <p>
     * If the document contains all required information, should save in database.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocument_accuracy2() throws Exception {
        Document document = new Document();
        Date now = new Date();
        document.setCreateDate(now);
        document.setDescription("I am not empty!");

        document = documentManager.addDocument(document);

        assertNotNull("The document id should be populated.", document.getDocumentId());
        // verify the result.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM document WHERE document_id=?");
            pstmt.setLong(1, document.getDocumentId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("incorrect create date.", now.getTime(), rs.getTimestamp("create_date").getTime(), 1000);
            assertEquals("incorrect description.", "I am not empty!", rs.getString("description"));
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocument(Document)</code> method.
     * </p>
     * <p>
     * If the document contains all required information, should save in database.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocument_accuracy3() throws Exception {
        Document document = new Document();
        Date now = new Date();
        document.setCreateDate(now);
        document.setDescription("I am not empty!");

        Set<DocumentVersion> documentVersions = new HashSet<DocumentVersion>();
        DocumentVersion documentVersion = new DocumentVersion();
        documentVersions.add(documentVersion);
        document.setDocumentVersions(documentVersions);
        document = documentManager.addDocument(document);

        assertNotNull("The document id should be populated.", document.getDocumentId());
        // verify the result.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM document WHERE document_id=?");
            pstmt.setLong(1, document.getDocumentId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("incorrect create date.", now.getTime(), rs.getTimestamp("create_date").getTime(), 1000);
            assertEquals("incorrect description.", "I am not empty!", rs.getString("description"));
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocument(long)</code> method.
     * </p>
     * <p>
     * If no record with the specified id, should return null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocument_NotFound() throws Exception {
        assertNull("Null should return.", documentManager.getDocument(1));
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocument(long)</code> method.
     * </p>
     * <p>
     * Should return the corresponding document object.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocument_accuracy1() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document (description, create_date, document_id) values ('Hello1', '2008-02-21 12:53:45', 2)"};
        executeSQL(sqls);

        Document document = documentManager.getDocument(1);
        assertNotNull("Null should return.", document);
        assertEquals("Incorrect description.", "Hello", document.getDescription());
        assertEquals("Incorrect create date.", "2008-02-20 12:53:45", DATE_FORMAT.format(document.getCreateDate()));
        assertTrue("The document versions set should be empty.", document.getDocumentVersions().isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocument(long)</code> method.
     * </p>
     * <p>
     * Should return the corresponding document object.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocument_accuracy2() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        documentManager.addDocumentVersion(createDocumentVersion(2));

        Document document = documentManager.getDocument(1);
        assertNotNull("Null should return.", document);
        assertEquals("Incorrect description.", "Hello", document.getDescription());
        assertEquals("Incorrect create date.", "2008-02-20 12:53:45", DATE_FORMAT.format(document.getCreateDate()));
        assertFalse("The document versions set should not be empty.", document.getDocumentVersions().isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getAllDocuments()</code> method.
     * </p>
     * <p>
     * Should return the corresponding documents list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllDocuments_accuracy1() throws Exception {
        List<Document> documents = documentManager.getAllDocuments();
        assertNotNull("Null should return.", documents);
        assertTrue("The list should be empty.", documents.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getAllDocuments()</code> method.
     * </p>
     * <p>
     * Should return the corresponding documents list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllDocuments_accuracy2() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 2)"};
        executeSQL(sqls);

        List<Document> documents = documentManager.getAllDocuments();
        assertNotNull("Null should return.", documents);
        assertEquals("The list should have two elements.", sqls.length, documents.size());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * The Entity should be saved successfully.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentVersion_accuracy1() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        DocumentVersion documentVersion = documentManager.addDocumentVersion(createDocumentVersion(2));

        assertNotNull("The document version id should be populated.", documentVersion.getDocumentVersionId());
        // verify the result.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM document_version WHERE document_version_id=?");
            pstmt.setLong(1, documentVersion.getDocumentVersionId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("The version date is incorrect.", documentVersion.getVersionDate().getTime(), rs.getTimestamp(
                    "version_date").getTime(), 1000);
            assertEquals("The content is incorrect.", "Content", rs.getString("content"));
            assertEquals("The version is incorrect.", 1, rs.getInt("version"));
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * The Entity should be saved successfully.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentVersion_accuracy2() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        DocumentVersion documentVersion = documentManager.addDocumentVersion(createDocumentVersion(1));

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM document_version WHERE document_version_id=?");
            pstmt.setLong(1, documentVersion.getDocumentVersionId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("The version date is incorrect.", documentVersion.getVersionDate().getTime(), rs.getTimestamp(
                    "version_date").getTime(), 1000);
            assertEquals("The content is incorrect.", "Content", rs.getString("content"));
            assertEquals("The version is incorrect.", 1, rs.getInt("version"));
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion does not exist in persistence, should throw DocumentPersistenceException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateDocumentVersion_NotFound() throws Exception {
        DocumentVersion documentVersion = createDocumentVersion(1);
        documentVersion.setDocumentVersionId(1L);

        try {
            documentManager.updateDocumentVersion(documentVersion);
            fail("If the documentVersion does not exist in persistence, should throw DocumentPersistenceException.");
        } catch (DocumentPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * The documentVersion should be updated correspondingly.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateDocumentVersion_accuracy() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 2)",
            "insert into document_name (document_name_id, name) values (1, 'Design')",
            "insert into document_name (document_name_id, name) values (2, 'Develop')"};
        executeSQL(sqls);

        DocumentVersion documentVersion = documentManager.addDocumentVersion(createDocumentVersion(1));

        documentVersion.setContent("I love it.");
        Date now = new Date();
        documentVersion.setVersionDate(now);
        documentVersion.setDocumentVersion(10);
        documentVersion.getDocument().setDocumentId(2L);
        documentVersion.getDocumentName().setDocumentNameId(2L);

        documentManager.updateDocumentVersion(documentVersion);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM document_version WHERE document_version_id=?");
            pstmt.setLong(1, documentVersion.getDocumentVersionId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("The version date is incorrect.", now.getTime(), rs.getTimestamp("version_date").getTime(),
                    1000);
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

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocumentVersion(long, Integer)</code> method.
     * </p>
     * <p>
     * If duplicate records exist, should throw DocumentPersistenceException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocumentVersion1_duplicateRecord1() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        documentManager.addDocumentVersion(createDocumentVersion(1));
        documentManager.addDocumentVersion(createDocumentVersion(1));

        try {
            documentManager.getDocumentVersion(1L, null);
            fail("If duplicate records exist, should throw DocumentPersistenceException.");
        } catch (DocumentPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocumentVersion(long, Integer)</code> method.
     * </p>
     * <p>
     * If duplicate records exist, should throw DocumentPersistenceException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocumentVersion1_duplicateRecord2() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        documentManager.addDocumentVersion(createDocumentVersion(1));
        documentManager.addDocumentVersion(createDocumentVersion(1));

        try {
            documentManager.getDocumentVersion(1L, 1);
            fail("If duplicate records exist, should throw DocumentPersistenceException.");
        } catch (DocumentPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocumentVersion(long, Integer)</code> method.
     * </p>
     * <p>
     * Should return null, if does not exist in persistence.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocumentVersion1_accuracy1() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        documentManager.addDocumentVersion(createDocumentVersion(1));

        assertNull("Should return null.", documentManager.getDocumentVersion(2l, null));
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocumentVersion(long, Integer)</code> method.
     * </p>
     * <p>
     * Should return null, if does not exist in persistence.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocumentVersion1_accuracy2() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        DocumentVersion dv = documentManager.addDocumentVersion(createDocumentVersion(1));

        DocumentVersion documentVersion = documentManager.getDocumentVersion(1L, new Integer(1));
        assertNotNull("The documentVersion should not null.", documentVersion);
        assertEquals("The version date is incorrect.", DATE_FORMAT.format(dv.getVersionDate()), DATE_FORMAT
                .format(documentVersion.getVersionDate()));
        assertEquals("The content is incorrect.", "Content", documentVersion.getContent());
        assertEquals("The document id is incorrect.", 1, documentVersion.getDocument().getDocumentId().longValue());
        assertEquals("The document name id is incorrect.", 1, documentVersion.getDocumentName().getDocumentNameId()
                .longValue());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocumentVersion(long)</code> method.
     * </p>
     * <p>
     * Should return null, if does not exist in persistence.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocumentVersion2_accuracy1() throws Exception {
        assertNull("Should return null.", documentManager.getDocumentVersion(1L));
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocumentVersion(long)</code> method.
     * </p>
     * <p>
     * Should return matched DocumentVersion object.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocumentVersion2_accuracy2() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 2)",
            "insert into document_name (document_name_id, name) values (1, 'Design')",
            "insert into document_name (document_name_id, name) values (2, 'Develop')"};
        executeSQL(sqls);

        DocumentVersion documentVersion = documentManager.addDocumentVersion(createDocumentVersion(1));
        DocumentVersion retrievedDocumentVersion = documentManager.getDocumentVersion(documentVersion
                .getDocumentVersionId());

        assertEquals("Incorrect document version", documentVersion.getDocumentVersion(), retrievedDocumentVersion
                .getDocumentVersion());

        assertEquals("The version date is incorrect.", documentVersion.getVersionDate().getTime(),
                retrievedDocumentVersion.getVersionDate().getTime(), 1000);
        assertEquals("The content is incorrect.", documentVersion.getContent(), retrievedDocumentVersion.getContent());
        assertEquals("The document id is incorrect.", documentVersion.getDocument().getDocumentId(),
                retrievedDocumentVersion.getDocument().getDocumentId());
        assertEquals("The document name id is incorrect.", documentVersion.getDocumentName().getDocumentNameId(),
                retrievedDocumentVersion.getDocumentName().getDocumentNameId());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addMemberAnswer(MemberAnswer)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddMemberAnswer_accuracy() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        MemberAnswer memberAnswer = createMemberAnswer(0);
        memberAnswer.setCompetitionDocument(competitionDocument);

        memberAnswer = documentManager.addMemberAnswer(memberAnswer);

        assertNotNull("The id attribute should be populated.", memberAnswer.getMemberAnswerId());

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM member_answer WHERE member_answer_id=?");
            pstmt.setLong(1, memberAnswer.getMemberAnswerId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("incorrect member id.", memberAnswer.getMemberId().longValue(), rs.getLong("member_id"));
            assertEquals("incorrect answer.", memberAnswer.getAnswer(), rs.getBoolean("answer"));
            assertEquals("incorrect answer date.", memberAnswer.getAnswerDate().getTime(), rs.getTimestamp(
                    "answer_date").getTime(), 1000);
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If no corresponding memberAnswer in persistence, should throw DocumentPersistenceException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateMemberAnswer_MemberAnswer_NotFound() throws Exception {
        MemberAnswer memberAnswer = createMemberAnswer(0);
        memberAnswer.setMemberAnswerId(1L);
        try {
            documentManager.updateMemberAnswer(memberAnswer);
            fail("If no corresponding memberAnswer in persistence, should throw DocumentPersistenceException.");
        } catch (DocumentPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateMemberAnswer(MemberAnswer)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateMemberAnswer_accuracy() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        MemberAnswer memberAnswer = createMemberAnswer(0);
        memberAnswer.setCompetitionDocument(competitionDocument);

        memberAnswer = documentManager.addMemberAnswer(memberAnswer);

        Date now = new Date(memberAnswer.getAnswerDate().getTime() - 10000);

        memberAnswer.setAnswerDate(now);
        memberAnswer.setMemberId(100l);
        memberAnswer.setAnswer(true);

        documentManager.updateMemberAnswer(memberAnswer);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM member_answer WHERE member_answer_id=?");
            pstmt.setLong(1, memberAnswer.getMemberAnswerId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("incorrect member id.", 100l, rs.getLong("member_id"));
            assertTrue("The answer is incorrect.", rs.getBoolean("answer"));
            assertEquals("incorrect answer date.", now.getTime(), rs.getTimestamp("answer_date").getTime(), 1000);
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#deleteMemberAnswer(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteMemberAnswer_accuracy1() throws Exception {
        assertFalse("No record exist, should return false.", documentManager.deleteMemberAnswer(1L));
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#deleteMemberAnswer(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteMemberAnswer_accuracy2() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        MemberAnswer memberAnswer = createMemberAnswer(0);
        memberAnswer.setCompetitionDocument(competitionDocument);

        memberAnswer = documentManager.addMemberAnswer(memberAnswer);
        assertTrue("record should be deleted.", documentManager.deleteMemberAnswer(memberAnswer.getMemberAnswerId()));

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM member_answer WHERE member_answer_id=?");
            pstmt.setLong(1, memberAnswer.getMemberAnswerId());

            rs = pstmt.executeQuery();

            assertFalse("No record should be found.", rs.next());
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getMemberAnswer(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetMemberAnswer_accuracy1() throws Exception {
        assertNull("No record exist, should return null.", documentManager.getMemberAnswer(1L));
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getMemberAnswer(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetMemberAnswer_accuracy2() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        MemberAnswer memberAnswer = createMemberAnswer(0);
        memberAnswer.setCompetitionDocument(competitionDocument);

        memberAnswer = documentManager.addMemberAnswer(memberAnswer);

        MemberAnswer ma = documentManager.getMemberAnswer(memberAnswer.getMemberAnswerId());

        assertEquals("The member id is incorrect.", memberAnswer.getMemberId(), ma.getMemberId());
        assertEquals("The answer is incorrect.", memberAnswer.getAnswer(), ma.getAnswer());
        assertEquals("The answer_date is incorrect.", memberAnswer.getAnswerDate().getTime(), ma.getAnswerDate()
                .getTime(), 1000);
        assertEquals("The compeition document id is incorrect.", memberAnswer.getCompetitionDocument()
                .getCompetitionDocumentId(), ma.getCompetitionDocument().getCompetitionDocumentId());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddCompetitionDocument_accuracy() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        assertNotNull("The competition document id should be populated.", competitionDocument);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM competition_document WHERE competition_document_id=?");
            pstmt.setLong(1, competitionDocument.getCompetitionDocumentId());

            rs = pstmt.executeQuery();

            assertTrue("Record should exist.", rs.next());

            assertEquals("Incorrect document_version_id.", documentVersion.getDocumentVersionId().longValue(), rs
                    .getLong("document_version_id"));
            assertEquals("Incorrect competition_id.", competitionDocument.getCompetitionId().longValue(), rs
                    .getLong("competition_id"));
            assertEquals("Incorrect role_id.", competitionDocument.getRoleId().longValue(), rs.getLong("role_id"));
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the competitionDocument does not exist in database, should throw DocumentPersistenceException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompetitionDocument_competitionDocument_NotFound() throws Exception {
        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setCompetitionDocumentId(1L);
        try {
            documentManager.updateCompetitionDocument(competitionDocument);
            fail("If the competitionDocument does not exist in database, should throw DocumentPersistenceException.");
        } catch (DocumentPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompetitionDocument_accuracy() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        competitionDocument.setCompetitionId(100l);
        competitionDocument.setRoleId(108l);

        documentManager.updateCompetitionDocument(competitionDocument);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM competition_document WHERE competition_document_id=?");
            pstmt.setLong(1, competitionDocument.getCompetitionDocumentId());

            rs = pstmt.executeQuery();

            assertTrue("Record should exist.", rs.next());

            assertEquals("Incorrect document_version_id.", documentVersion.getDocumentVersionId().longValue(), rs
                    .getLong("document_version_id"));
            assertEquals("Incorrect competition_id.", 100l, rs.getLong("competition_id"));
            assertEquals("Incorrect role_id.", 108l, rs.getLong("role_id"));
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#deleteCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompetitionDocument_accuracy() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        documentManager.deleteCompetitionDocument(competitionDocument);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM competition_document WHERE competition_document_id=?");
            pstmt.setLong(1, competitionDocument.getCompetitionDocumentId());

            rs = pstmt.executeQuery();

            assertFalse("No record should exist.", rs.next());
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#deleteCompetitionDocuments(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompetitionDocuments1_accuracy1() throws Exception {
        assertFalse("False should return.", documentManager.deleteCompetitionDocuments(1L));
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#deleteCompetitionDocuments(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompetitionDocuments1_accuracy2() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        long competitionId = -1;
        for (int i = 0; i < 5; i++) {
            CompetitionDocument competitionDocument = createCompetitionDocument(0);
            competitionDocument.setDocumentVersion(documentVersion);

            competitionDocument = documentManager.addCompetitionDocument(competitionDocument);
            competitionId = competitionDocument.getCompetitionId();
        }

        documentManager.deleteCompetitionDocuments(competitionId);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM competition_document WHERE competition_id=?");
            pstmt.setLong(1, competitionId);

            rs = pstmt.executeQuery();

            assertFalse("No record should exist.", rs.next());
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#deleteCompetitionDocuments(long, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompetitionDocuments2_accuracy1() throws Exception {
        assertFalse("False should return.", documentManager.deleteCompetitionDocuments(1L, 1L));
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#deleteCompetitionDocuments(long, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompetitionDocuments2_accuracy2() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        long competitionId = -1;
        long roleId = -1;
        for (int i = 0; i < 5; i++) {
            CompetitionDocument competitionDocument = createCompetitionDocument(0);
            competitionDocument.setDocumentVersion(documentVersion);

            competitionDocument = documentManager.addCompetitionDocument(competitionDocument);
            competitionId = competitionDocument.getCompetitionId();
            roleId = competitionDocument.getRoleId();
        }

        documentManager.deleteCompetitionDocuments(competitionId);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM competition_document WHERE competition_id=? AND role_id=?");
            pstmt.setLong(1, competitionId);
            pstmt.setLong(2, roleId);

            rs = pstmt.executeQuery();

            assertFalse("No record should exist.", rs.next());
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocument(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocument1_accuracy1() throws Exception {
        assertNull("Null should return.", documentManager.getCompetitionDocument(1L));
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocument(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocument1_accuracy2() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        CompetitionDocument cd = documentManager.getCompetitionDocument(competitionDocument.getCompetitionDocumentId());

        assertNotNull("Null should not return.", cd);
        assertEquals("Incorrect document_version_id.", documentVersion.getDocumentVersionId(), cd.getDocumentVersion()
                .getDocumentVersionId());
        assertEquals("Incorrect competition_id.", competitionDocument.getCompetitionId(), cd.getCompetitionId());
        assertEquals("Incorrect role_id.", competitionDocument.getRoleId(), cd.getRoleId());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocument(long, long, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocument2_accuracy1() throws Exception {
        assertNull("Null should return.", documentManager.getCompetitionDocument(1L, 1L, 1L));
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocument(long, long, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocument2_accuracy2() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        CompetitionDocument cd = documentManager.getCompetitionDocument(competitionDocument.getDocumentVersion()
                .getDocumentVersionId(), competitionDocument.getCompetitionId(), competitionDocument.getRoleId());

        assertNotNull("Null should not return.", cd);
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocumentsByRole(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocumentsByRole_accuracy1() throws Exception {
        List<CompetitionDocument> competitionDocuments = documentManager.getCompetitionDocumentsByRole(1L);
        assertNotNull("Never return null.", competitionDocuments);
        assertTrue("The list should be empty.", competitionDocuments.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocumentsByRole(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocumentsByRole_accuracy2() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        for (int i = 0; i < 5; i++) {
            CompetitionDocument competitionDocument = createCompetitionDocument(0);
            competitionDocument.setDocumentVersion(documentVersion);

            competitionDocument.setRoleId(i % 2l);
            competitionDocument = documentManager.addCompetitionDocument(competitionDocument);
        }

        List<CompetitionDocument> competitionDocuments = documentManager.getCompetitionDocumentsByRole(0);

        assertNotNull("Null should not return.", competitionDocuments);
        assertEquals("Incorrect list size.", 3, competitionDocuments.size());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocumentsByCompetition(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocumentsByCompetition_accuracy1() throws Exception {
        List<CompetitionDocument> competitionDocuments = documentManager.getCompetitionDocumentsByCompetition(1L);
        assertNotNull("Never return null.", competitionDocuments);
        assertTrue("The list should be empty.", competitionDocuments.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocumentsByCompetition(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocumentsByCompetition_accuracy2() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        for (int i = 0; i < 5; i++) {
            CompetitionDocument competitionDocument = createCompetitionDocument(0);
            competitionDocument.setDocumentVersion(documentVersion);

            competitionDocument.setCompetitionId(i % 2l);
            competitionDocument = documentManager.addCompetitionDocument(competitionDocument);
        }

        List<CompetitionDocument> competitionDocuments = documentManager.getCompetitionDocumentsByCompetition(1);

        assertNotNull("Null should not return.", competitionDocuments);
        assertEquals("Incorrect list size.", 2, competitionDocuments.size());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocuments(long, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocuments_accuracy1() throws Exception {
        List<CompetitionDocument> competitionDocuments = documentManager.getCompetitionDocuments(1L, 1L);
        assertNotNull("Never return null.", competitionDocuments);
        assertTrue("The list should be empty.", competitionDocuments.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocuments(long, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocuments_accuracy2() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        for (int i = 0; i < 5; i++) {
            CompetitionDocument competitionDocument = createCompetitionDocument(0);
            competitionDocument.setDocumentVersion(documentVersion);

            competitionDocument.setCompetitionId(i % 2l);
            competitionDocument.setRoleId(i % 2l);
            competitionDocument = documentManager.addCompetitionDocument(competitionDocument);
        }

        List<CompetitionDocument> competitionDocuments = documentManager.getCompetitionDocuments(1L, 1L);

        assertNotNull("Null should not return.", competitionDocuments);
        assertEquals("Incorrect list size.", 2, competitionDocuments.size());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentName(DocumentName)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentName_accuracy() throws Exception {
        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");

        documentName = documentManager.addDocumentName(documentName);

        assertNotNull("The document name id should be populated.", documentName.getDocumentNameId());
        // verify the result.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnectionFactory().createConnection();
            pstmt = conn.prepareStatement("SELECT * FROM document_name WHERE document_name_id=?");
            pstmt.setLong(1, documentName.getDocumentNameId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("The name is incorrect.", "TopCoder", rs.getString("name"));
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocumentName(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocumentName_accuracy1() throws Exception {
        assertNull("Null should return.", documentManager.getDocumentName(1L));
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocumentName(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocumentName_accuracy2() throws Exception {
        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");

        documentName = documentManager.addDocumentName(documentName);

        DocumentName dn = documentManager.getDocumentName(documentName.getDocumentNameId());

        assertNotNull("Null should not return.", dn);
        assertEquals("Incorrect name.", documentName.getName(), dn.getName());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getAllDocumentNames()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllDocumentNames_accuracy1() throws Exception {
        List<DocumentName> documentNames = documentManager.getAllDocumentNames();
        assertNotNull("Never return null.", documentNames);
        assertTrue("The list should be empty.", documentNames.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getAllDocumentNames()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllDocumentNames_accuracy2() throws Exception {
        String[] sqls = new String[] {"insert into document_name (document_name_id, name) values (1, 'Design')",
            "insert into document_name (document_name_id, name) values (2, 'Develop')"};
        executeSQL(sqls);

        List<DocumentName> documentNames = documentManager.getAllDocumentNames();
        assertNotNull("Never return null.", documentNames);
        assertEquals("The list should have two elements.", 2, documentNames.size());
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#isCompetitionDocumentAnswered(long, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testIsCompetitionDocumentAnswered_accuracy1() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        assertFalse("the competition document is not answered.", documentManager.isCompetitionDocumentAnswered(
                competitionDocument.getCompetitionDocumentId(), 100));
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#isCompetitionDocumentAnswered(long, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testIsCompetitionDocumentAnswered_accuracy2() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);

        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        MemberAnswer memberAnswer = createMemberAnswer(0);
        memberAnswer.setCompetitionDocument(competitionDocument);

        memberAnswer = documentManager.addMemberAnswer(memberAnswer);
        assertTrue("The competition document is answered.", documentManager.isCompetitionDocumentAnswered(
                competitionDocument.getCompetitionDocumentId(), memberAnswer.getMemberId()));
    }
}
