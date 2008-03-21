/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.ejb;

import javax.ejb.EJBException;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.service.prerequisite.document.BaseTestCase;
import com.topcoder.service.prerequisite.document.DocumentManager;

/**
 * <p>
 * Unit test for <code>{@link DocumentManagerBean}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DocumentManagerBeanInvalidParametersUnitTests extends BaseTestCase {

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
        return new TestSuite(DocumentManagerBeanInvalidParametersUnitTests.class);
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
        documentManager = (DocumentManagerRemote) getInitialContext().lookup(JNDI_NAME);
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocument(Document)</code> method.
     * </p>
     * <p>
     * If the parameter is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocument_null() throws Exception {
        try {
            documentManager.addDocument(null);
            fail("If the parameter is null, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion is null, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentVersion_null() throws Exception {
        try {
            documentManager.addDocumentVersion(null);
            fail("If the documentVersion is null, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's versionDate is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentVersion_documentVersion_versionDateNotSet() throws Exception {
        try {
            documentManager.addDocumentVersion(createDocumentVersion(3));
            fail("If the documentVersion's versionDate is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's content is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentVersion_documentVersion_contentNotSet() throws Exception {
        try {
            documentManager.addDocumentVersion(createDocumentVersion(4));
            fail("If the documentVersion's content is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's document is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentVersion_documentVersion_documentNotSet() throws Exception {
        try {
            documentManager.addDocumentVersion(createDocumentVersion(5));
            fail("If the documentVersion's document is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's document's id is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentVersion_documentVersion_documentIdNotSet() throws Exception {
        try {
            documentManager.addDocumentVersion(createDocumentVersion(6));
            fail("If the documentVersion's document's id is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's documentName is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentVersion_documentVersion_documentNameNotSet() throws Exception {
        try {
            documentManager.addDocumentVersion(createDocumentVersion(7));
            fail("If the documentVersion's documentName is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's documentNameId is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentVersion_documentVersion_documentNameIdNotSet() throws Exception {
        try {
            documentManager.addDocumentVersion(createDocumentVersion(8));
            fail("If the documentVersion's documentNameId is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion is null, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateDocumentVersion_null() throws Exception {
        try {
            documentManager.updateDocumentVersion(null);
            fail("If the documentVersion is null, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's id is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateDocumentVersion_documentVersion_idNotSet() throws Exception {
        try {
            documentManager.updateDocumentVersion(createDocumentVersion(0));
            fail("If the documentVersion's id is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's version is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateDocumentVersion_documentVersion_versionNotSet() throws Exception {
        try {
            documentManager.updateDocumentVersion(createDocumentVersion(2));
            fail("If the documentVersion's version is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's versionDate is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateDocumentVersion_documentVersion_versionDateNotSet() throws Exception {
        try {
            documentManager.updateDocumentVersion(createDocumentVersion(3));
            fail("If the documentVersion's versionDate is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's content is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateDocumentVersion_documentVersion_contentNotSet() throws Exception {
        try {
            documentManager.updateDocumentVersion(createDocumentVersion(4));
            fail("If the documentVersion's content is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's document is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateDocumentVersion_documentVersion_documentNotSet() throws Exception {
        try {
            documentManager.updateDocumentVersion(createDocumentVersion(5));
            fail("If the documentVersion's document is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's document's id is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateDocumentVersion_documentVersion_documentIdNotSet() throws Exception {
        try {
            documentManager.updateDocumentVersion(createDocumentVersion(6));
            fail("If the documentVersion's document's id is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's documentName is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateDocumentVersion_documentVersion_documentNameNotSet() throws Exception {
        try {
            documentManager.updateDocumentVersion(createDocumentVersion(7));
            fail("If the documentVersion's documentName is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the documentVersion's documentNameId is not set, should throw IllegalAgrumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateDocumentVersion_documentVersion_documentNameIdNotSet() throws Exception {
        try {
            documentManager.updateDocumentVersion(createDocumentVersion(8));
            fail("If the documentVersion's documentNameId is not set, should throw IllegalAgrumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the memberAnswer instance is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddMemberAnswer_null() throws Exception {
        try {
            documentManager.addMemberAnswer(null);
            fail("If the memberAnswer instance is null, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the memberId of memberAnswer is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddMemberAnswer_MemberAnswer_memberIdNotSet() throws Exception {
        try {
            documentManager.addMemberAnswer(createMemberAnswer(1));
            fail("If the memberId of memberAnswer is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the answerDate of memberAnswer is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddMemberAnswer_MemberAnswer_answerDateNotSet() throws Exception {
        try {
            documentManager.addMemberAnswer(createMemberAnswer(2));
            fail("If the answerDate of memberAnswer is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the competitionDocument of memberAnswer is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddMemberAnswer_MemberAnswer_competitionDocumentNotSet() throws Exception {
        try {
            documentManager.addMemberAnswer(createMemberAnswer(3));
            fail("If the competitionDocument of memberAnswer is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the competitionDocumentId of memberAnswer is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddMemberAnswer_MemberAnswer_competitionDocumentIdNotSet() throws Exception {
        try {
            documentManager.addMemberAnswer(createMemberAnswer(4));
            fail("If the competitionDocumentId of memberAnswer is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the memberAnswer instance is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateMemberAnswer_null() throws Exception {
        try {
            documentManager.updateMemberAnswer(null);
            fail("If the memberAnswer instance is null, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the memberAnswerId of memberAnswer is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateMemberAnswer_MemberAnswer_memberAnswerIdNotSet() throws Exception {
        try {
            documentManager.updateMemberAnswer(createMemberAnswer(0));
            fail("If the memberAnswerId of memberAnswer is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the memberId of memberAnswer is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateMemberAnswer_MemberAnswer_memberIdNotSet() throws Exception {
        try {
            documentManager.updateMemberAnswer(createMemberAnswer(1));
            fail("If the memberId of memberAnswer is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the answerDate of memberAnswer is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateMemberAnswer_MemberAnswer_answerDateNotSet() throws Exception {
        try {
            documentManager.updateMemberAnswer(createMemberAnswer(2));
            fail("If the answerDate of memberAnswer is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the competitionDocument of memberAnswer is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateMemberAnswer_MemberAnswer_competitionDocumentNotSet() throws Exception {
        try {
            documentManager.updateMemberAnswer(createMemberAnswer(3));
            fail("If the competitionDocument of memberAnswer is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateMemberAnswer(MemberAnswer)</code> method.
     * </p>
     * <p>
     * If the competitionDocumentId of memberAnswer is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateMemberAnswer_MemberAnswer_competitionDocumentIdNotSet() throws Exception {
        try {
            documentManager.updateMemberAnswer(createMemberAnswer(4));
            fail("If the competitionDocumentId of memberAnswer is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the competitionDocument is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddCompetitionDocument_null() throws Exception {
        try {
            documentManager.addCompetitionDocument(null);
            fail("If the competitionDocument is null, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("The cause should be IllegalArgumentException.",
                    e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the competitionId of competitionDocument is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddCompetitionDocument_competitionDocument_competitionIdNotSet() throws Exception {
        try {
            documentManager.addCompetitionDocument(createCompetitionDocument(1));
            fail("If the competitionId of competitionDocument is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("The cause should be IllegalArgumentException.",
                    e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the roleId of competitionDocument is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddCompetitionDocument_competitionDocument_roleIdNotSet() throws Exception {
        try {
            documentManager.addCompetitionDocument(createCompetitionDocument(2));
            fail("If the roleId of competitionDocument is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("The cause should be IllegalArgumentException.",
                    e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the documentVersion of competitionDocument is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddCompetitionDocument_competitionDocument_documentVersionNotSet() throws Exception {
        try {
            documentManager.addCompetitionDocument(createCompetitionDocument(3));
            fail("If the documentVersion of competitionDocument is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("The cause should be IllegalArgumentException.",
                    e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the documentVersionId of competitionDocument is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddCompetitionDocument_competitionDocument_documentVersionIdNotSet() throws Exception {
        try {
            documentManager.addCompetitionDocument(createCompetitionDocument(4));
            fail("If the documentVersionId of competitionDocument is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("The cause should be IllegalArgumentException.",
                    e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the competitionDocument is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompetitionDocument_null() throws Exception {
        try {
            documentManager.updateCompetitionDocument(null);
            fail("If the competitionDocument is null, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("The cause should be IllegalArgumentException.",
                    e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the competitionDocumentId of competitionDocument is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompetitionDocument_competitionDocument_competitionDocumentIdNotSet() throws Exception {
        try {
            documentManager.updateCompetitionDocument(createCompetitionDocument(0));
            fail("If the competitionDocumentId is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("The cause should be IllegalArgumentException.",
                    e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the competitionId of competitionDocument is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompetitionDocument_competitionDocument_competitionIdNotSet() throws Exception {
        try {
            documentManager.updateCompetitionDocument(createCompetitionDocument(1));
            fail("If the competitionId of competitionDocument is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("The cause should be IllegalArgumentException.",
                    e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the roleId of competitionDocument is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompetitionDocument_competitionDocument_roleIdNotSet() throws Exception {
        try {
            documentManager.updateCompetitionDocument(createCompetitionDocument(2));
            fail("If the roleId of competitionDocument is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("The cause should be IllegalArgumentException.",
                    e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the documentVersion of competitionDocument is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompetitionDocument_competitionDocument_documentVersionNotSet() throws Exception {
        try {
            documentManager.updateCompetitionDocument(createCompetitionDocument(3));
            fail("If the documentVersion of competitionDocument is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("The cause should be IllegalArgumentException.",
                    e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#updateCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the documentVersionId of competitionDocument is not set, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompetitionDocument_competitionDocument_documentVersionIdNotSet() throws Exception {
        try {
            documentManager.updateCompetitionDocument(createCompetitionDocument(4));
            fail("If the documentVersionId of competitionDocument is not set, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("The cause should be IllegalArgumentException.",
                    e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>DocumentManagerBean#addDocumentName(DocumentName)</code> method.
     * </p>
     * <p>
     * If the documentName is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddDocumentName_null() throws Exception {
        try {
            documentManager.addDocumentName(null);
            fail("If the documentName is null, should throw IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("The cause should be IllegalArgumentException.",
                    e.getCause() instanceof IllegalArgumentException);
        }
    }
}
