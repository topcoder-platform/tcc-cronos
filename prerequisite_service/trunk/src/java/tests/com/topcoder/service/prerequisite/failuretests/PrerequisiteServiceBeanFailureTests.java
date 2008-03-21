/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.failuretests;

import com.topcoder.service.prerequisite.CompetitionNotFoundException;
import com.topcoder.service.prerequisite.DocumentNotFoundException;
import com.topcoder.service.prerequisite.IllegalArgumentWSException;
import com.topcoder.service.prerequisite.PrerequisiteDocument;
import com.topcoder.service.prerequisite.PrerequisiteService;
import com.topcoder.service.prerequisite.RoleNotFoundException;
import com.topcoder.service.prerequisite.UserAlreadyAnsweredDocumentException;
import com.topcoder.service.prerequisite.UserNotAuthorizedException;
import com.topcoder.service.prerequisite.document.DocumentManager;
import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;

import junit.framework.TestCase;

import java.sql.Connection;

import java.util.Date;
import java.util.Properties;

import javax.ejb.EJBException;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>
 * Tests error cases of <code>PrerequisiteServiceBean</code> class.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class PrerequisiteServiceBeanFailureTests extends TestCase {
    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the <code>PrerequisiteServiceBean</code> instance used for testing. */
    private PrerequisiteService bean;

    /** Represents the timestamp for testing. */
    private XMLGregorianCalendar xmlGregorianCalendar;

    /** Represents the prerequisiteDocument for testing. */
    private PrerequisiteDocument prerequisiteDocument;

    /** Represents the DocumentManager for testing. */
    private DocumentManager documentManager;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        connection = FailureTestHelper.getConnection();
        FailureTestHelper.prepareData(connection);

        xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        prerequisiteDocument = new PrerequisiteDocument();

        Properties env = new Properties();

        env.setProperty(Context.SECURITY_PRINCIPAL, "user");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");

        InitialContext ic = new InitialContext(env);
        bean = (PrerequisiteService) ic.lookup("remote/PrerequisiteService");
        documentManager = (DocumentManager) ic.lookup("remote/DocumentManager");
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            FailureTestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * <p>
     * Tests <code>getPrerequisiteDocument</code> when the documentId is negative. Expect IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocument_NegativeDocumentId() throws Exception {
        try {
            bean.getPrerequisiteDocument(-1, null);
            fail("Expect EJBException.");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Tests <code>getPrerequisiteDocument</code> when the version is negative. Expect DocumentNotFoundException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocument_NegativeVersion() throws Exception {
        try {
            bean.getPrerequisiteDocument(1, -1);
            fail("Expect DocumentNotFoundException.");
        } catch (DocumentNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>getPrerequisiteDocument</code> when the documentId is not found. Expect DocumentNotFoundException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocument_DocumentNotFound() throws Exception {
        try {
            bean.getPrerequisiteDocument(1, 1);
            fail("Expect DocumentNotFoundException.");
        } catch (DocumentNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>getAllPrerequisiteDocuments</code> when the user is not authorized. Expect
     * UserNotAuthorizedException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetAllPrerequisiteDocuments_NotAuthorized() throws Exception {
        try {
            bean.getAllPrerequisiteDocuments();
            fail("Expect UserNotAuthorizedException.");
        } catch (UserNotAuthorizedException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>getPrerequisiteDocuments</code> when the competition id is negative. Expect
     * IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocuments_NegativeCompetiitionId()
        throws Exception {
        try {
            bean.getPrerequisiteDocuments(-1, 1);
            fail("Expect EJBException.");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Tests <code>getPrerequisiteDocuments</code> when the competition does not exist. Expect
     * CompetitionNotFoundException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocuments_CompetitionNotFound() throws Exception {
        try {
            bean.getPrerequisiteDocuments(1, 1);
            fail("Expect CompetitionNotFoundException.");
        } catch (CompetitionNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>getPrerequisiteDocuments</code> when the role id is negative. Expect IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocuments_NegativeRoleId() throws Exception {
        try {
            bean.getPrerequisiteDocuments(1, -1);
            fail("Expect EJBException.");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Tests <code>getPrerequisiteDocuments</code> when the role does not exist. Expect RoleNotFoundException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocuments_RoleNotFound() throws Exception {
        try {
            bean.getPrerequisiteDocuments(1, 2);
            fail("Expect RoleNotFoundException.");
        } catch (RoleNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>recordMemberAnswer</code> when the competition id is negative. Expect IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_NegativeCompetiitionId() throws Exception {
        try {
            bean.recordMemberAnswer(-1, xmlGregorianCalendar, true, prerequisiteDocument, 1);
            fail("Expect EJBException.");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Tests <code>recordMemberAnswer</code> when the competition does not exist. Expect CompetitionNotFoundException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_CompetitionNotFound() throws Exception {
        try {
            bean.recordMemberAnswer(1, xmlGregorianCalendar, true, prerequisiteDocument, 1);
            fail("Expect CompetitionNotFoundException.");
        } catch (CompetitionNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>recordMemberAnswer</code> when the role id is negative. Expect IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_NegativeRoleId() throws Exception {
        try {
            bean.recordMemberAnswer(1, xmlGregorianCalendar, true, prerequisiteDocument, -1);
            fail("Expect EJBException.");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Tests <code>recordMemberAnswer</code> when the role does not exist. Expect RoleNotFoundException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_RoleNotFound() throws Exception {
        try {
            bean.recordMemberAnswer(1, xmlGregorianCalendar, true, prerequisiteDocument, 2);
            fail("Expect RoleNotFoundException.");
        } catch (RoleNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>recordMemberAnswer</code> when the timestamp is null. Expect IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_NullTimestamp() throws Exception {
        try {
            bean.recordMemberAnswer(1, null, true, prerequisiteDocument, 1);
            fail("Expect EJBException.");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Tests <code>recordMemberAnswer</code> when the prerequisiteDocument is null. Expect IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_NullPrerequisiteDocument() throws Exception {
        try {
            bean.recordMemberAnswer(1, xmlGregorianCalendar, true, null, 1);
            fail("Expect EJBException.");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Tests <code>recordMemberAnswer</code> when the document does not exist. Expect DocumentNotFoundException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_DocumentNotFound() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = new DocumentVersion();
        documentVersion.setDocumentVersion(1);
        documentVersion.setVersionDate(new Date());
        documentVersion.setContent("Content");
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);
        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = new CompetitionDocument();
        competitionDocument.setCompetitionId(1L);
        competitionDocument.setRoleId(1L);
        competitionDocument.setDocumentVersion(documentVersion);
        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        prerequisiteDocument.setDocumentId(document.getDocumentId());
        prerequisiteDocument.setVersion(documentVersion.getDocumentVersion() + 1);

        try {
            bean.recordMemberAnswer(1, xmlGregorianCalendar, true, prerequisiteDocument, 1);
            fail("Expect DocumentNotFoundException.");
        } catch (DocumentNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>recordMemberAnswer</code> when it is already answered. Expect UserAlreadyAnsweredDocumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_AlreadyAnswered() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = documentManager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("TopCoder");
        documentName = documentManager.addDocumentName(documentName);

        DocumentVersion documentVersion = new DocumentVersion();
        documentVersion.setDocumentVersion(1);
        documentVersion.setVersionDate(new Date());
        documentVersion.setContent("Content");
        documentVersion.setDocument(document);
        documentVersion.setDocumentName(documentName);
        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = new CompetitionDocument();
        competitionDocument.setCompetitionId(1L);
        competitionDocument.setRoleId(1L);
        competitionDocument.setDocumentVersion(documentVersion);
        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);

        prerequisiteDocument.setDocumentId(document.getDocumentId());
        prerequisiteDocument.setVersion(documentVersion.getDocumentVersion());

        bean.recordMemberAnswer(1, xmlGregorianCalendar, true, prerequisiteDocument, 1);

        try {
            bean.recordMemberAnswer(1, xmlGregorianCalendar, true, prerequisiteDocument, 1);
            fail("Expect UserAlreadyAnsweredDocumentException.");
        } catch (UserAlreadyAnsweredDocumentException e) {
            // good
        }
    }
}
