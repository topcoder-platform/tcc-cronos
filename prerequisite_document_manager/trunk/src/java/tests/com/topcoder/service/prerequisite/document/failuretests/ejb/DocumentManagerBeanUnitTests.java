/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.failuretests.ejb;

import java.util.Date;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import com.topcoder.service.prerequisite.document.AuthorizationException;
import com.topcoder.service.prerequisite.document.ConfigurationException;
import com.topcoder.service.prerequisite.document.DocumentManager;
import com.topcoder.service.prerequisite.document.ejb.DocumentManagerRemote;
import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;
import com.topcoder.service.prerequisite.document.model.MemberAnswer;

/**
 * <p>
 * Failure test for DocumentManagerBean class.
 * </p>
 * 
 * @author 80x86
 * @version 1.0
 */
public class DocumentManagerBeanUnitTests extends TestCase {

    /**
     * <p>
     * Represents the JNDI name to look up the DocumentManagerBean.
     * </p>
     */
    private static final String JNDI_NAME = "prerequisite_document_manager/DMB/remote";

    /**
     * <p>
     * Represents the <code>InitialContext</code> to do lookup.
     * </p>
     */
    private InitialContext context;

    /**
     * <p>
     * Represents the <code>DocumentManager</code> instance for testing.
     * </p>
     */
    private DocumentManager documentManager;

    /**
     * <p>
     * Represents the <code>DocumentManager</code> instance for testing which is not authorized.
     * </p>
     */
    private DocumentManager documentManager2;

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
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "user");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        context = new InitialContext(env);
        documentManager = (DocumentManagerRemote) context.lookup(JNDI_NAME);
        documentManager2 = (DocumentManager) getInitialContextForAnotherRole().lookup(JNDI_NAME);
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
        context.close();
    }

    /**
     * Failure test for DocumentManagerBean#init with persistenceUnitName not configured, ConfigurationException is
     * expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testInit_NoPUName() throws Exception {
        documentManager = (DocumentManager) context.lookup("prerequisite_document_manager/DMBNoPU/remote");
        try {
            documentManager.getDocument(1);
            fail("ConfigurationException is expected.");
        } catch (RuntimeException e) {
            assertTrue("expected ConfigurationException cause", e.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * Failure test for addDocument with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddDocument_NotAuthorized() throws Exception {
        try {
            documentManager2.addDocument(new Document());
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for getDocument with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testGetDocument_NotAuthorized() throws Exception {
        try {
            documentManager2.getDocument(1);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for getAllDocuments with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testGetAllDocuments_NotAuthorized() throws Exception {
        try {
            documentManager2.getAllDocuments();
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for addDocumentVersion with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddDocumentVersion_NotAuthorized() throws Exception {
        try {
            documentManager2.addDocumentVersion(createDocumentVersion());
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for updateDocumentVersion with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testUpdateDocumentVersion_NotAuthorized() throws Exception {
        try {
            DocumentVersion dv = createDocumentVersion();
            dv.setDocumentVersionId(11L);
            documentManager2.updateDocumentVersion(dv);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for getDocumentVersion with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testGetDocumentVersion1_NotAuthorized() throws Exception {
        try {
            documentManager2.getDocumentVersion(1, 1);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for getDocumentVersion with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testGetDocumentVersion2_NotAuthorized() throws Exception {
        try {
            documentManager2.getDocumentVersion(1);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for addMemberAnswer with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddMemberAnswer_NotAuthorized() throws Exception {
        try {
            documentManager2.addMemberAnswer(createMemberAnswer());
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for updateMemberAnswer with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testUpdateMemberAnswer_NotAuthorized() throws Exception {
        MemberAnswer memberAnswer = createMemberAnswer();
        memberAnswer.setMemberAnswerId(1L);
        try {
            documentManager2.updateMemberAnswer(memberAnswer);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for deleteMemberAnswer with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testDeleteMemberAnswer_NotAuthorized() throws Exception {
        try {
            documentManager2.deleteMemberAnswer(1);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for getMemberAnswer with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testGetMemberAnswer_NotAuthorized() throws Exception {
        try {
            documentManager2.getMemberAnswer(1);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for addCompetitionDocument with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddCompetitionDocument_NotAuthorized() throws Exception {
        try {
            documentManager2.addCompetitionDocument(createCompetitionDocument());
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for updateCompetitionDocument with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testUpdateCompetitionDocument_NotAuthorized() throws Exception {
        CompetitionDocument competitionDocument = createCompetitionDocument();
        competitionDocument.setCompetitionDocumentId(1L);
        try {
            documentManager2.updateCompetitionDocument(competitionDocument);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for deleteCompetitionDocument with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testDeleteCompetitionDocument_NotAuthorized() throws Exception {
        CompetitionDocument competitionDocument = new CompetitionDocument();
        competitionDocument.setCompetitionDocumentId(1L);
        try {
            documentManager2.deleteCompetitionDocument(competitionDocument);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for deleteCompetitionDocuments with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testDeleteCompetitionDocuments1_NotAuthorized() throws Exception {
        try {
            documentManager2.deleteCompetitionDocuments(1L);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for deleteCompetitionDocuments with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testDeleteCompetitionDocuments2_NotAuthorized() throws Exception {
        try {
            documentManager2.deleteCompetitionDocuments(1L, 2L);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for getCompetitionDocument with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testGetCompetitionDocument1_NotAuthorized() throws Exception {
        try {
            documentManager2.getCompetitionDocument(1L);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for getCompetitionDocument with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testGetCompetitionDocument2_NotAuthorized() throws Exception {
        try {
            documentManager2.getCompetitionDocument(1L, 2L, 3L);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for getCompetitionDocumentsByRole with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testGetCompetitionDocumentsByRole_NotAuthorized() throws Exception {
        try {
            documentManager2.getCompetitionDocumentsByRole(1L);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for getCompetitionDocumentsByCompetition with user not authorized, AuthorizationException is
     * expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testGetCompetitionDocumentsByCompetition_NotAuthorized() throws Exception {
        try {
            documentManager2.getCompetitionDocumentsByCompetition(1L);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for getCompetitionDocuments with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testGetCompetitionDocuments_NotAuthorized() throws Exception {
        try {
            documentManager2.getCompetitionDocuments(1L, 2L);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for addDocumentName with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddDocumentName_NotAuthorized() throws Exception {
        try {
            documentManager2.addDocumentName(new DocumentName());
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for getDocumentName with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testGetDocumentName_NotAuthorized() throws Exception {
        try {
            documentManager2.getDocumentName(1);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for getAllDocumentNames with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testGetAllDocumentNames_NotAuthorized() throws Exception {
        try {
            documentManager2.getAllDocumentNames();
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for isCompetitionDocumentAnswered with user not authorized, AuthorizationException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testIsCompetitionDocumentAnswered_NotAuthorized() throws Exception {
        try {
            documentManager2.isCompetitionDocumentAnswered(1L, 1L);
            fail("AuthorizationException expected");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * Failure test for addDocument with null, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddDocument_Null() throws Exception {
        try {
            documentManager.addDocument(null);
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for addDocumentVersion with null, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddDocumentVersion_Null() throws Exception {
        try {
            documentManager.addDocumentVersion(null);
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for addDocumentVersion with invalid value, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddDocumentVersion_Invalid() throws Exception {
        try {
            documentManager.addDocumentVersion(new DocumentVersion());
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for updateDocumentVersion with null, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testUpdateDocumentVersion_Null() throws Exception {
        try {
            documentManager.updateDocumentVersion(null);
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for updateDocumentVersion with invalid value, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testUpdateDocumentVersion_Invalid() throws Exception {
        try {
            documentManager.updateDocumentVersion(new DocumentVersion());
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for addMemberAnswer with null, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddMemberAnswer_Null() throws Exception {
        try {
            documentManager.addMemberAnswer(null);
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for addMemberAnswer with invalid value, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddMemberAnswer_MemberAnswer_Invalid() throws Exception {
        try {
            documentManager.addMemberAnswer(new MemberAnswer());
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for updateMemberAnswer with null, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testUpdateMemberAnswer_Null() throws Exception {
        try {
            documentManager.updateMemberAnswer(null);
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for updateMemberAnswer with invalid value, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testUpdateMemberAnswer_Invalid() throws Exception {
        try {
            documentManager.updateMemberAnswer(new MemberAnswer());
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for addCompetitionDocument with null, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddCompetitionDocument_Null() throws Exception {
        try {
            documentManager.addCompetitionDocument(null);
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for addCompetitionDocument with invalid value, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddCompetitionDocument_Invalid() throws Exception {
        try {
            documentManager.addCompetitionDocument(new CompetitionDocument());
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for updateCompetitionDocument with null, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testUpdateCompetitionDocument_Null() throws Exception {
        try {
            documentManager.updateCompetitionDocument(null);
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for updateCompetitionDocument with invalid value, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testUpdateCompetitionDocument_Invalid() throws Exception {
        try {
            documentManager.updateCompetitionDocument(new CompetitionDocument());
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for addDocumentName with null, IllegalArgumentException is expected.
     * 
     * @throws Exception
     *             unexpected errors
     */
    public void testAddDocumentName_Null() throws Exception {
        try {
            documentManager.addDocumentName(null);
            fail("IllegalArgumentException is expected.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Creates a <code>CompetitionDocument</code> instance.
     * 
     * @return the created CompetitionDocument instance.
     * @throws Exception
     *             unexpected errors
     */
    private static CompetitionDocument createCompetitionDocument() throws Exception {
        CompetitionDocument competitionDocument = new CompetitionDocument();
        competitionDocument.setCompetitionId(1L);
        competitionDocument.setRoleId(1L);
        DocumentVersion documentVersion = new DocumentVersion();
        documentVersion.setDocumentVersionId(1L);
        competitionDocument.setDocumentVersion(documentVersion);
        return competitionDocument;
    }

    /**
     * <p>
     * Creates a <code>MemberAnswer</code> instance.
     * </p>
     * 
     * @return the created MemberAnswer instance.
     * @throws Exception
     *             unexpected errors
     */
    private static MemberAnswer createMemberAnswer() throws Exception {
        MemberAnswer memberAnswer = new MemberAnswer();
        memberAnswer.setMemberId(1L);
        memberAnswer.setAnswerDate(new Date());
        CompetitionDocument competitionDocument = new CompetitionDocument();
        competitionDocument.setCompetitionDocumentId(1L);
        memberAnswer.setCompetitionDocument(competitionDocument);
        return memberAnswer;
    }

    /**
     * <p>
     * Creates a <code>DocumentVersion</code> instance.
     * </p>
     * 
     * @return the created DocumentVersion instance.
     * @throws Exception
     *             unexpected errors
     */
    private static DocumentVersion createDocumentVersion() throws Exception {
        DocumentVersion documentVersion = new DocumentVersion();
        documentVersion.setDocumentVersion(1);
        documentVersion.setVersionDate(new Date());
        documentVersion.setContent("Content");
        Document document = new Document();
        document.setDocumentId(1L);
        documentVersion.setDocument(document);
        DocumentName documentName = new DocumentName();
        documentName.setDocumentNameId(1L);
        documentVersion.setDocumentName(documentName);
        return documentVersion;
    }

    /**
     * <p>
     * Returns another <code>InitialContext</code> instance for unit testing with different role.
     * </p>
     * 
     * @return the <code>InitialContext</code> instance.
     * @throws Exception
     *             if any error happens
     */
    private InitialContext getInitialContextForAnotherRole() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "user1");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password1");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext anotherContext = new InitialContext(env);
        return anotherContext;
    }
}
