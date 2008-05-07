/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.service.prerequisite.BaseTestCase;
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
import com.topcoder.service.prerequisite.document.model.MemberAnswer;

/**
 * <p>
 * Unit test for <code>{@link PrerequisiteServiceBean}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PrerequisiteServiceBeanUnitTests extends BaseTestCase {

    /**
     * <p>
     * Represents the <code>PrerequisiteService</code> instance.
     * </p>
     */
    private PrerequisiteService prerequisiteService;

    /**
     * <p>
     * Represents the timestamp.
     * </p>
     */
    private XMLGregorianCalendar xmlGregorianCalendar;

    /**
     * <p>
     * Represents the <code>DocumentManager</code> instance.
     * </p>
     * <p>
     * As we can not insert record for document version, because of using BLOB data type.
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
        return new TestSuite(PrerequisiteServiceBeanUnitTests.class);
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

        prerequisiteService = (PrerequisiteService) getInitialContext().lookup("remote/PrerequisiteService");
        documentManager = (DocumentManager) getInitialContext().lookup("remote/DocumentManager");
        xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
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
     * Unit test for <code>PrerequisiteServiceBean#getPrerequisiteDocument(long, Integer)</code> method.
     * </p>
     * <p>
     * If the documentId is negative, should throw IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocument_NegatieDocumentId() throws Exception {
        try {
            prerequisiteService.getPrerequisiteDocument(-1, null);
            fail("expect IllegalArgumentWSException");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getPrerequisiteDocument(long, Integer)</code> method.
     * </p>
     * <p>
     * If no document version with the specified document id, should throw DocumentNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocument_DocumentVersionNotFound() throws Exception {
        try {
            prerequisiteService.getPrerequisiteDocument(1, null);
            fail("expect DocumentNotFoundException");
        } catch (DocumentNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getPrerequisiteDocument(long, Integer)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocument_accuracy1() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        DocumentVersion documentVersion = documentManager.addDocumentVersion(createDocumentVersion(1));

        PrerequisiteDocument prerequisiteDocument = prerequisiteService.getPrerequisiteDocument(1L, null);
        assertNotNull("The prerequisiteDocument should not null.", prerequisiteDocument);

        assertEquals("The version date is incorrect.", prerequisiteDocument.getVersionDate().toGregorianCalendar()
                .getTimeInMillis(), documentVersion.getVersionDate().getTime(), 1000);
        assertEquals("The content is incorrect.", "Content", prerequisiteDocument.getContent());
        assertEquals("Incorrect version.", documentVersion.getDocumentVersion(), prerequisiteDocument.getVersion());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getPrerequisiteDocument(long, Integer)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocument_accuracy2() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        DocumentVersion documentVersion = documentManager.addDocumentVersion(createDocumentVersion(1));

        PrerequisiteDocument prerequisiteDocument = prerequisiteService.getPrerequisiteDocument(1L, documentVersion
                .getDocumentVersion());
        assertNotNull("The prerequisiteDocument should not null.", prerequisiteDocument);

        assertEquals("The version date is incorrect.", prerequisiteDocument.getVersionDate().toGregorianCalendar()
                .getTimeInMillis(), documentVersion.getVersionDate().getTime(), 1000);
        assertEquals("The content is incorrect.", "Content", prerequisiteDocument.getContent());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getAllPrerequisiteDocuments()</code> method.
     * </p>
     * <p>
     * If the caller is not administrator, should throw UserNotAuthorizedException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllPrerequisiteDocuments_Unauthorized() throws Exception {
        try {
            prerequisiteService.getAllPrerequisiteDocuments();
            fail("expect UserNotAuthorizedException");
        } catch (UserNotAuthorizedException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getAllPrerequisiteDocuments()</code> method.
     * </p>
     * <p>
     * If no record in database, should return empty array.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllPrerequisiteDocuments_accuracy1() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);

        prerequisiteService = (PrerequisiteService) initCtx.lookup("remote/PrerequisiteService");

        List<PrerequisiteDocument> prerequisiteDocuments = prerequisiteService.getAllPrerequisiteDocuments();

        assertNotNull("Null should return.", prerequisiteDocuments);
        assertTrue("The list should be empty.", prerequisiteDocuments.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getAllPrerequisiteDocuments()</code> method.
     * </p>
     * <p>
     * Should return all the prerequisite documents.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllPrerequisiteDocuments_accuracy2() throws Exception {
        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 1)",
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 2)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        for (int i = 0; i < 6; i++) {
            DocumentVersion documentVersion = createDocumentVersion(1);
            if (i % 2 == 0) {
                documentVersion.getDocument().setDocumentId(2L);
            }

            documentManager.addDocumentVersion(documentVersion);
        }

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);

        prerequisiteService = (PrerequisiteService) initCtx.lookup("remote/PrerequisiteService");

        List<PrerequisiteDocument> prerequisiteDocuments = prerequisiteService.getAllPrerequisiteDocuments();

        assertNotNull("Null should return.", prerequisiteDocuments);
        assertEquals("The list should have six elements.", 6, prerequisiteDocuments.size());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getPrerequisiteDocuments(long, long)</code> method.
     * </p>
     * <p>
     * If the competitionId is negative, should throw IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocuments_NegatieCompetitionId() throws Exception {
        try {
            prerequisiteService.getPrerequisiteDocuments(-1, 1);
            fail("expect IllegalArgumentWSException");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getPrerequisiteDocuments(long, long)</code> method.
     * </p>
     * <p>
     * If the roleId is negative, should throw IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocuments_NegatieRoleId() throws Exception {
        try {
            prerequisiteService.getPrerequisiteDocuments(1, -1);
            fail("expect IllegalArgumentWSException");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getPrerequisiteDocuments(long, long)</code> method.
     * </p>
     * <p>
     * If the caller does not have 'User' or 'Administrator' role, should throw UserNotAuthorizedException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocuments_NotAuthorized() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "NoRolesUser");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);

        prerequisiteService = (PrerequisiteService) initCtx.lookup("remote/PrerequisiteService");

        try {
            prerequisiteService.getPrerequisiteDocuments(1, 1);
            fail("expect UserNotAuthorizedException");
        } catch (UserNotAuthorizedException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getPrerequisiteDocuments(long, long)</code> method.
     * </p>
     * <p>
     * If the role id does not found, should throw RoleNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocuments_RoleNotFoundException() throws Exception {
        try {
            prerequisiteService.getPrerequisiteDocuments(1, 2);
            fail("expect RoleNotFoundException");
        } catch (RoleNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getPrerequisiteDocuments(long, long)</code> method.
     * </p>
     * <p>
     * If no competition passed with the specified id, should throw CompetitionNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocuments_CompetitionNotFoundException1() throws Exception {
        try {
            prerequisiteService.getPrerequisiteDocuments(1, 1);
            fail("expect CompetitionNotFoundException");
        } catch (CompetitionNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getPrerequisiteDocuments(long, long)</code> method.
     * </p>
     * <p>
     * If no competition passed with the specified id, should throw CompetitionNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocuments_CompetitionNotFoundException2() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);

        prerequisiteService = (PrerequisiteService) initCtx.lookup("remote/PrerequisiteService");

        try {
            prerequisiteService.getPrerequisiteDocuments(1, 1);
            fail("expect CompetitionNotFoundException");
        } catch (CompetitionNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteServiceBean#getPrerequisiteDocuments(long, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrerequisiteDocuments_accuracy() throws Exception {
        // prepare data.
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

        List<PrerequisiteDocument> prerequisiteDocuments = prerequisiteService.getPrerequisiteDocuments(1, 1);

        assertNotNull("Null should not return.", prerequisiteDocuments);
        // no duplicate
        assertEquals("Incorrect list size.", 1, prerequisiteDocuments.size());
    }

    /**
     * <p>
     * Unit test for PrerequisiteServiceBean#recordMemberAnswer(long, XMLGregorianCalendar, boolean,
     * PrerequisiteDocument, long) method.
     * </p>
     * <p>
     * If the competitionId is negative, should throw IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_NegatieCompetitionId() throws Exception {
        try {
            prerequisiteService.recordMemberAnswer(-1, xmlGregorianCalendar, false, new PrerequisiteDocument(), 1);
            fail("expect IllegalArgumentWSException");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Unit test for
     * PrerequisiteServiceBean#recordMemberAnswer(long, XMLGregorianCalendar, boolean,PrerequisiteDocument, long)
     * method.
     * </p>
     * <p>
     * If the roleId is negative, should throw IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_NegatieRoleId() throws Exception {
        try {
            prerequisiteService.recordMemberAnswer(1, xmlGregorianCalendar, false, new PrerequisiteDocument(), -1);
            fail("expect IllegalArgumentWSException");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Unit test for
     * PrerequisiteServiceBean#recordMemberAnswer(long, XMLGregorianCalendar, boolean,PrerequisiteDocument, long)
     * method.
     * </p>
     * <p>
     * If the timestamp is null, should throw IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_NullTimestamp() throws Exception {
        try {
            prerequisiteService.recordMemberAnswer(1, null, false, new PrerequisiteDocument(), 1);
            fail("expect IllegalArgumentWSException");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Unit test for
     * PrerequisiteServiceBean#recordMemberAnswer(long, XMLGregorianCalendar, boolean,PrerequisiteDocument, long)
     * method.
     * </p>
     * <p>
     * If the pre-requisite document is null, should throw IllegalArgumentWSException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_NullPrerequisiteDocument() throws Exception {
        try {
            prerequisiteService.recordMemberAnswer(1, xmlGregorianCalendar, false, null, 1);
            fail("expect IllegalArgumentWSException");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.", e.getCause() instanceof IllegalArgumentWSException);
        }
    }

    /**
     * <p>
     * Unit test for
     * PrerequisiteServiceBean#recordMemberAnswer(long, XMLGregorianCalendar, boolean, PrerequisiteDocument, long)
     * method.
     * </p>
     * <p>
     * If the caller does not have 'User' or 'Administrator' role, should throw UserNotAuthorizedException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_NotAuthorized() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "NoRolesUser");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);

        prerequisiteService = (PrerequisiteService) initCtx.lookup("remote/PrerequisiteService");

        try {
            prerequisiteService.recordMemberAnswer(1, xmlGregorianCalendar, false, new PrerequisiteDocument(), 1);
            fail("expect UserNotAuthorizedException");
        } catch (UserNotAuthorizedException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * PrerequisiteServiceBean#recordMemberAnswer(long, XMLGregorianCalendar, boolean,PrerequisiteDocument, long)
     * method.
     * </p>
     * <p>
     * If the role id does not found, should throw RoleNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_RoleNotFoundException() throws Exception {
        try {
            prerequisiteService.recordMemberAnswer(1, xmlGregorianCalendar, false, new PrerequisiteDocument(), 2);
            fail("expect RoleNotFoundException");
        } catch (RoleNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * PrerequisiteServiceBean#recordMemberAnswer(long, XMLGregorianCalendar, boolean,PrerequisiteDocument, long)
     * method.
     * </p>
     * <p>
     * If no competition passed with the specified id, should throw CompetitionNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_CompetitionNotFoundException() throws Exception {
        try {
            prerequisiteService.recordMemberAnswer(1, xmlGregorianCalendar, false, new PrerequisiteDocument(), 1);
            fail("expect CompetitionNotFoundException");
        } catch (CompetitionNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * PrerequisiteServiceBean#recordMemberAnswer(long, XMLGregorianCalendar, boolean,PrerequisiteDocument, long)
     * method.
     * </p>
     * <p>
     * If the competition is already answered by the user, should throw UserAlreadyAnsweredDocumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_UserAlreadyAnsweredDocumentException() throws Exception {
        // prepared the data
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

        PrerequisiteDocument prerequisiteDocument = new PrerequisiteDocument();
        prerequisiteDocument.setDocumentId(document.getDocumentId());
        prerequisiteDocument.setVersion(documentVersion.getDocumentVersion());

        try {
            prerequisiteService.recordMemberAnswer(competitionDocument.getCompetitionId(), xmlGregorianCalendar, true,
                    prerequisiteDocument, competitionDocument.getRoleId());
            fail("expect UserAlreadyAnsweredDocumentException");
        } catch (UserAlreadyAnsweredDocumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * PrerequisiteServiceBean#recordMemberAnswer(long, XMLGregorianCalendar, boolean,PrerequisiteDocument, long)
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRecordMemberAnswer_accuracy() throws Exception {
        // prepared the data
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

        // member id is 0, because of mock
        assertFalse("the competition document should not be answered.", documentManager.isCompetitionDocumentAnswered(
                competitionDocument.getCompetitionDocumentId(), 0));

        PrerequisiteDocument prerequisiteDocument = new PrerequisiteDocument();
        prerequisiteDocument.setDocumentId(document.getDocumentId());
        prerequisiteDocument.setVersion(documentVersion.getDocumentVersion());
        prerequisiteService.recordMemberAnswer(competitionDocument.getCompetitionId(), xmlGregorianCalendar, true,
                prerequisiteDocument, competitionDocument.getRoleId());

        assertTrue("The competition document should be answered", documentManager.isCompetitionDocumentAnswered(
                competitionDocument.getCompetitionDocumentId(), 0));
    }
}
