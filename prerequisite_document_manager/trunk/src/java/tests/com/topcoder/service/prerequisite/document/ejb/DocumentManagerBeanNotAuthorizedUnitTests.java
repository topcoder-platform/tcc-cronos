/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.service.prerequisite.document.AuthorizationException;
import com.topcoder.service.prerequisite.document.BaseTestCase;
import com.topcoder.service.prerequisite.document.DocumentManager;
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
public class DocumentManagerBeanNotAuthorizedUnitTests extends BaseTestCase {

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
        return new TestSuite(DocumentManagerBeanNotAuthorizedUnitTests.class);
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

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "user1");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password1");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext context = new InitialContext(env);

        documentManager = (DocumentManager) context.lookup(JNDI_NAME);
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocument(Document)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocument_NotAuthorized() throws Exception {
        try {
            documentManager.addDocument(new Document());
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocument(long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocument_NotAuthorized() throws Exception {
        try {
            documentManager.getDocument(1);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getAllDocuments()</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllDocuments_NotAuthorized() throws Exception {
        try {
            documentManager.getAllDocuments();
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentVersion_NotAuthorized() throws Exception {
        try {
            documentManager.addDocumentVersion(createDocumentVersion(0));
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateDocumentVersion_NotAuthorized() throws Exception {
        DocumentVersion documentVersion = createDocumentVersion(0);
        documentVersion.setDocumentVersionId(1L);
        try {
            documentManager.updateDocumentVersion(documentVersion);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocumentVersion(long, Integer)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocumentVersion1_NotAuthorized() throws Exception {
        try {
            documentManager.getDocumentVersion(1, 1);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocumentVersion(long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocumentVersion2_NotAuthorized() throws Exception {
        try {
            documentManager.getDocumentVersion(1);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddMemberAnswer_NotAuthorized() throws Exception {
        try {
            documentManager.addMemberAnswer(createMemberAnswer(0));
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateMemberAnswer_NotAuthorized() throws Exception {
        MemberAnswer memberAnswer = createMemberAnswer(0);
        memberAnswer.setMemberAnswerId(1L);
        try {
            documentManager.updateMemberAnswer(memberAnswer);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#deleteMemberAnswer(long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteMemberAnswer_NotAuthorized() throws Exception {
        try {
            documentManager.deleteMemberAnswer(1);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getMemberAnswer(long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetMemberAnswer_NotAuthorized() throws Exception {
        try {
            documentManager.getMemberAnswer(1);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddCompetitionDocument_NotAuthorized() throws Exception {
        try {
            documentManager.addCompetitionDocument(createCompetitionDocument(0));
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompetitionDocument_NotAuthorized() throws Exception {
        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setCompetitionDocumentId(1L);
        try {
            documentManager.updateCompetitionDocument(competitionDocument);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#deleteCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompetitionDocument_NotAuthorized() throws Exception {
        CompetitionDocument competitionDocument = new CompetitionDocument();
        competitionDocument.setCompetitionDocumentId(1L);
        try {
            documentManager.deleteCompetitionDocument(competitionDocument);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#deleteCompetitionDocuments(long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompetitionDocuments1_NotAuthorized() throws Exception {
        try {
            documentManager.deleteCompetitionDocuments(1L);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#deleteCompetitionDocuments(long, long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompetitionDocuments2_NotAuthorized() throws Exception {
        try {
            documentManager.deleteCompetitionDocuments(1L, 1L);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocument(long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocument1_NotAuthorized() throws Exception {
        try {
            documentManager.getCompetitionDocument(1L);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocument(long, long, long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocument2_NotAuthorized() throws Exception {
        try {
            documentManager.getCompetitionDocument(1L, 1L, 1L);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocumentsByRole(long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocumentsByRole_NotAuthorized() throws Exception {
        try {
            documentManager.getCompetitionDocumentsByRole(1L);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocumentsByCompetition(long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocumentsByCompetition_NotAuthorized() throws Exception {
        try {
            documentManager.getCompetitionDocumentsByCompetition(1L);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getCompetitionDocuments(long, long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionDocuments_NotAuthorized() throws Exception {
        try {
            documentManager.getCompetitionDocuments(1L, 1L);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentName(DocumentName)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentName_NotAuthorized() throws Exception {
        try {
            documentManager.addDocumentName(new DocumentName());
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getDocumentName(long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetDocumentName_NotAuthorized() throws Exception {
        try {
            documentManager.getDocumentName(1);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#getAllDocumentNames()</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllDocumentNames_NotAuthorized() throws Exception {
        try {
            documentManager.getAllDocumentNames();
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#isCompetitionDocumentAnswered(long, long)</code> method.
     * </p>
     * <p>
     * If the request is not authorized, should throw AuthorizationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testIsCompetitionDocumentAnswered_NotAuthorized() throws Exception {
        try {
            documentManager.isCompetitionDocumentAnswered(1L, 1L);
            fail("If the request is not authorized, should throw AuthorizationException.");
        } catch (AuthorizationException e) {
            // expected
        }
    }
}
