/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.accuracytests;

import com.topcoder.configuration.persistence.ConfigurationFileManager;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.service.prerequisite.PrerequisiteDocument;
import com.topcoder.service.prerequisite.PrerequisiteService;
import com.topcoder.service.prerequisite.document.DocumentManager;
import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Accuracy Test Case for the PrerequisiteServiceBean class.
 */
public class PrerequisiteServiceBeanAccTests extends TestCase {
    /**
     * <p>
     * Represents the property file for configuration persistence.
     * </p>
     */
    private static final String ACC_PROPERTIES_FILE = "test_files/accuracy/tests.properties";

    /** The service to test against. */
    private PrerequisiteService service = null;

    /**
     * <p>
     * Represents the <code>InitialContext</code> instance for looking up.
     * </p>
     */
    private InitialContext ctx;

    /**
     * <p>
     * Represents the <code>DocumentManager</code> instance.
     * </p>
     */
    private DocumentManager documentManager;

    /**
     * <p>
     * Represents the <code>DBConnectionFactory</code> instance for testing.
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * Setup the EJB test.
     * </p>
     */
    @Override
    protected void setUp() throws Exception {
        ConfigurationFileManager configurationFileManager = new ConfigurationFileManager(ACC_PROPERTIES_FILE);

        dbConnectionFactory = new DBConnectionFactoryImpl(configurationFileManager.getConfiguration(
                    "InformixDBConnectionFactory"));

        executeScriptFile("test_files" + File.separator + "clean.sql");

        lookupPrerequisiteServiceRemoteWithUserRole();
    }

    /**
     * <p>
     * Tear down the EJB test.
     * </p>
     */
    @Override
    protected void tearDown() throws Exception {
        executeScriptFile("test_files" + File.separator + "clean.sql");

        ctx.close();
        service = null;
    }

    /**
     * <p>
     * Test the getPrerequisiteDocuments method with the competitonId and the roleId.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetPrerequisiteDocumentsWithCompetitionIdAndRoleId()
        throws Exception {
        // prepare data.
        Document document = new Document();
        document.setDescription("document 1");
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(document, documentName);
        documentVersion = documentManager.addDocumentVersion(documentVersion);

        for (int i = 0; i < 5; i++) {
            CompetitionDocument competitionDocument = createCompetitionDocument(documentVersion);
            competitionDocument.setCompetitionId(i % 2L);
            competitionDocument = documentManager.addCompetitionDocument(competitionDocument);
        }

        List<PrerequisiteDocument> prerequisiteDocuments = service.getPrerequisiteDocuments(1, 1);

        assertNotNull("Null should not return.", prerequisiteDocuments);
        // no duplicate
        assertEquals("Incorrect list size.", 1, prerequisiteDocuments.size());

        prerequisiteDocuments = service.getPrerequisiteDocuments(0, 1);
        assertEquals("Incorrect list size.", 1, prerequisiteDocuments.size());

        //test with admin role
        this.lookupPrerequisiteServiceRemoteWithAdminRole();
        prerequisiteDocuments = service.getPrerequisiteDocuments(1, 1);

        assertNotNull("Null should not return.", prerequisiteDocuments);
        // no duplicate
        assertEquals("Incorrect list size.", 1, prerequisiteDocuments.size());
    }

    /**
     * Test the getPrerequisiteDocument(long documentId, Integer version) method.
     *
     * @throws Exception into JUnit
     */
    public void testGetPrerequisiteDocumentWithDocumentIdAndVersion()
        throws Exception {
        // prepare data.
        Document document = new Document();
        document.setDescription("document 1");
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(document, documentName);
        documentVersion = documentManager.addDocumentVersion(documentVersion);

        PrerequisiteDocument prerequisiteDocument = service.getPrerequisiteDocument(document.getDocumentId(), 1);

        assertNotNull("Null should not return.", prerequisiteDocument);
        assertEquals("The content is invalid.", "Content", prerequisiteDocument.getContent());
        assertEquals("The content is invalid.", documentName.getName(), prerequisiteDocument.getName());
        assertEquals("The content is invalid.", documentVersion.getDocumentVersion().intValue(),
            prerequisiteDocument.getVersion().intValue());
    }

    /**
     * Test the getAllPrerequisiteDocuments method.
     */
    public void testGetAllPrerequisiteDocuments() throws Exception {
        try {
            this.service.getAllPrerequisiteDocuments();
            fail("Not admin");
        } catch (Exception e) {
            //good
        }

        // prepare data.
        Document document = new Document();
        document.setDescription("document 1");
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(document, documentName);
        documentVersion = documentManager.addDocumentVersion(documentVersion);

        this.lookupPrerequisiteServiceRemoteWithAdminRole();
        assertNotNull("Null should not return.", this.service.getAllPrerequisiteDocuments());
        assertEquals("The size is invalid.", 1, this.service.getAllPrerequisiteDocuments().size());
        assertEquals("The content is invalid.", "Content", service.getAllPrerequisiteDocuments().get(0).getContent());
        assertEquals("The content is invalid.", documentName.getName(),
            service.getAllPrerequisiteDocuments().get(0).getName());
        assertEquals("The content is invalid.", documentVersion.getDocumentVersion().intValue(),
            service.getAllPrerequisiteDocuments().get(0).getVersion().intValue());
    }

    /**
     * Test recordMemberAnswer(long competitionId, XMLGregorianCalendar timestamp, boolean agrees, PrerequisiteDocument
     * prerequisiteDocument, long roleId) method.
     */
    public void testRecordMemberAnswer() throws Exception {
        // prepare data.
        Document document = new Document();
        document.setDescription("document 1");
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = createDocumentVersion(document, documentName);
        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(documentVersion);
        competitionDocument.setCompetitionId(1L);
        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        PrerequisiteDocument prerequisiteDoc = service.getPrerequisiteDocument(document.getDocumentId(),
                documentVersion.getDocumentVersion());

        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();

        assertFalse("It should not answered.",
            documentManager.isCompetitionDocumentAnswered(competitionDocument.getCompetitionDocumentId(), 0));

        //record the member answer
        service.recordMemberAnswer(1, xmlGregorianCalendar, false, prerequisiteDoc, 1);

        assertTrue("It should answered.",
            documentManager.isCompetitionDocumentAnswered(competitionDocument.getCompetitionDocumentId(), 0));
    }

    /**
     * <p>
     * Creates a <code>CompetitionDocument</code> instance, possibly make part of the field not set.
     * </p>
     *
     * @param whichNotSet indicate which required field don't set
     *
     * @return the created CompetitionDocument instance.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    private static CompetitionDocument createCompetitionDocument(DocumentVersion docVersion)
        throws Exception {
        CompetitionDocument competitionDocument = new CompetitionDocument();
        competitionDocument.setRoleId(1L);

        competitionDocument.setDocumentVersion(docVersion);

        return competitionDocument;
    }

    /**
     * <p>
     * Creates a <code>DocumentVersion</code> instance, possibly make part of the field not set.
     * </p>
     *
     * @return the created module instance.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    private static DocumentVersion createDocumentVersion(Document doc, DocumentName docName)
        throws Exception {
        DocumentVersion documentVersion = new DocumentVersion();
        documentVersion.setDocumentVersion(1);
        documentVersion.setVersionDate(new Date());
        documentVersion.setContent("Content");

        documentVersion.setDocument(doc);
        documentVersion.setDocumentName(docName);

        return documentVersion;
    }

    /**
     * Lookup the projectService with admin role.
     *
     * @throws Exception into JUnit
     */
    private void lookupPrerequisiteServiceRemoteWithAdminRole()
        throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");

        InitialContext initCtx = new InitialContext(env);

        service = (PrerequisiteService) initCtx.lookup("remote/PrerequisiteService");
        documentManager = (DocumentManager) initCtx.lookup("remote/DocumentManager");
    }

    /**
     * Lookup the projectService with admin role.
     *
     * @throws Exception into JUnit
     */
    private void lookupPrerequisiteServiceRemoteWithUserRole()
        throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "user");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        ctx = new InitialContext(env);

        service = (PrerequisiteService) ctx.lookup("remote/PrerequisiteService");
        documentManager = (DocumentManager) ctx.lookup("remote/DocumentManager");
    }

    /**
     * <p>
     * Executes the sql script against the database.
     * </p>
     *
     * @param filename the file name.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    private void executeScriptFile(String filename) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        BufferedReader bufferedReader = null;

        try {
            conn = dbConnectionFactory.createConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String sql = null;
            bufferedReader = new BufferedReader(new FileReader(filename));

            while (null != (sql = bufferedReader.readLine())) {
                stmt.executeUpdate(sql);
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();

            throw e;
        } finally {
            closeStatement(stmt);
            closeConnection(conn);

            if (null != bufferedReader) {
                bufferedReader.close();
            }
        }
    }

    /**
     * <p>
     * Closes the connection. It will be used in finally block.
     * </p>
     *
     * @param conn the database connection.
     */
    private static void closeConnection(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Close the statement. It will be used in finally block.
     * </p>
     *
     * @param stmt the statement.
     */
    private static void closeStatement(Statement stmt) {
        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
