/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document;

import java.io.File;
import java.util.Date;

import com.topcoder.service.prerequisite.document.ejb.DocumentManagerRemote;
import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;
import com.topcoder.service.prerequisite.document.model.MemberAnswer;

/**
 * <p>
 * This is the demonstration for common usage of the <b>Prerequisite Document Manager 1.0</b> component.
 * </p>
 *
 * @author biotrail, TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends BaseTestCase {

    /**
     * <p>
     * Represents the <code>DocumentManager</code> instance used in client code.
     * </p>
     */
    private DocumentManager documentManager;

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
     * Demonstrates the common document management process.
     * </p>
     * <p>
     * It only demonstrates part of functionality provided by DocumentManager.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAssignmentDocumentManagement() throws Exception {
        // add assign document
        Document doc = new Document();
        doc.setCreateDate(new Date());
        doc.setDescription("assignment document for user confirmation!");
        doc = documentManager.addDocument(doc);

        // add assign document name
        DocumentName docName = new DocumentName();
        docName.setName("Assignment Document");
        docName = documentManager.addDocumentName(docName);

        // add the first assignment document version
        DocumentVersion docVersion = new DocumentVersion();
        docVersion.setDocumentVersion(1);
        docVersion.setVersionDate(new Date());
        docVersion.setContent("assignment document");
        docVersion.setDocument(doc);
        doc.getDocumentVersions().add(docVersion);
        docVersion.setDocumentName(docName);
        docName.getDocumentVersions().add(docVersion);
        docVersion = documentManager.addDocumentVersion(docVersion);

        // add a competition document that requires assignment document
        CompetitionDocument competitionDoc = new CompetitionDocument();
        competitionDoc.setRoleId(new Long(1));
        competitionDoc.setCompetitionId(new Long(1));
        competitionDoc.setDocumentVersion(docVersion);
        docVersion.getCompetitionDocuments().add(competitionDoc);
        competitionDoc = documentManager.addCompetitionDocument(competitionDoc);

        // do some updates to assignment document version as requirement changes
        DocumentVersion newVersion = documentManager.getDocumentVersion(doc.getDocumentId(), null);
        newVersion.setContent("updated assignment document");
        documentManager.updateDocumentVersion(newVersion);

        // record user answer to the competition document
        MemberAnswer memberAnswer = new MemberAnswer();
        memberAnswer.setAnswer(true);
        memberAnswer.setCompetitionDocument(competitionDoc);
        memberAnswer.setMemberId(new Long(1));
        memberAnswer.setAnswerDate(new Date());
        memberAnswer = documentManager.addMemberAnswer(memberAnswer);

        // remove the user answer
        documentManager.deleteMemberAnswer(memberAnswer.getMemberAnswerId());
    }

}
