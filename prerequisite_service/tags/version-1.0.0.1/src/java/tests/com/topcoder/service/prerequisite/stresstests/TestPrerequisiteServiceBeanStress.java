/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.stresstests;

import com.topcoder.service.prerequisite.PrerequisiteDocument;
import com.topcoder.service.prerequisite.PrerequisiteService;
import com.topcoder.service.prerequisite.document.DocumentManager;
import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;
import com.topcoder.service.prerequisite.document.model.MemberAnswer;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.File;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Stress test cases for class <code>PrerequisiteServiceBean </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestPrerequisiteServiceBeanStress extends TestCase {
    /** Represents theInitialContext to look up the entities. */
    private InitialContext context;

    /** Represents the PrerequisiteService instance for testing. */
    private PrerequisiteService service = null;

    /** Represents the DocumentManager for testing. */
    private DocumentManager documentManager;

    /** Represents the  PrerequisiteDocument instance for testing. */
    private PrerequisiteDocument prerequisiteDocument;

    /** Represents the MemberAnswer instance for testing. */
    private MemberAnswer memberAnswer;

    /** Represents the CompetitionDocument instance for testing. */
    private CompetitionDocument cd1;

    /**
     * Set up.
     *
     * @throws Exception to junit
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        if (!cm.existsNamespace("stresstests")) {
            cm.add(new File("test_files/stresstestfile/InformixDBConnectionFactory.xml").getCanonicalPath());
        }

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "user");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        context = new InitialContext(env);

        service = (PrerequisiteService) context.lookup("remote/PrerequisiteService");
        documentManager = (DocumentManager) context.lookup("remote/DocumentManager");

        DBUtil.clearTables();

        // first insert some records into the database.
        List<String> list = new ArrayList<String>();

        // first insert records into table document.
        list.add("insert into document (description, create_date, document_id) values" +
            " ('test1', '2008-03-16 17:53:45', 1)");
        list.add("insert into document (description, create_date, document_id) values" +
            " ('test2', '2008-03-16 17:53:45', 2)");
        list.add("insert into document (description, create_date, document_id) values" +
            " ('test3', '2008-03-16 17:53:45', 3)");

        // insert records into document_name.
        list.add("insert into document_name (document_name_id, name) values (1, 'name1')");
        list.add("insert into document_name (document_name_id, name) values (2, 'name2')");
        list.add("insert into document_name (document_name_id, name) values (3, 'name1')");

        DBUtil.insertRecords(list.toArray(new String[0]));

        Document document1 = new Document();
        document1.setDocumentId(1L);

        Document document2 = new Document();
        document2.setDocumentId(2L);

        Document document3 = new Document();
        document3.setDocumentId(3L);

        DocumentName name1 = new DocumentName();
        name1.setName("name1");
        name1.setDocumentNameId(1L);

        DocumentName name2 = new DocumentName();
        name2.setName("name2");
        name2.setDocumentNameId(2L);

        DocumentName name3 = new DocumentName();
        name3.setDocumentNameId(3L);
        name3.setName("name3");

        // create DocumentVersion instances.
        DocumentVersion[] versions = new DocumentVersion[5];

        for (int i = 0; i < versions.length; i++) {
            versions[i] = new DocumentVersion();
            versions[i].setContent("content" + i);
            versions[i].setDocumentVersion(new Integer(i + 1));
            versions[i].setVersionDate(new Date());
        }

        versions[0].setDocument(document1);
        versions[0].setDocumentName(name1);

        versions[1].setDocument(document2);
        versions[1].setDocumentName(name1);

        versions[2].setDocument(document3);
        versions[2].setDocumentName(name2);

        versions[3].setDocument(document1);
        versions[3].setDocumentName(name3);

        versions[4].setDocument(document2);
        versions[4].setDocumentName(name2);

        for (int i = 0; i < versions.length; i++) {
            versions[i] = documentManager.addDocumentVersion(versions[i]);
        }

        cd1 = new CompetitionDocument();
        cd1.setDocumentVersion(versions[0]);
        cd1.setRoleId(1L);
        cd1.setCompetitionId(1L);

        CompetitionDocument cd2 = new CompetitionDocument();
        cd2.setDocumentVersion(versions[2]);
        cd2.setRoleId(1L);
        cd2.setCompetitionId(1L);

        cd1 = documentManager.addCompetitionDocument(cd1);
        cd2 = documentManager.addCompetitionDocument(cd2);

        memberAnswer = new MemberAnswer();
        memberAnswer.setCompetitionDocument(cd1);
        memberAnswer.setMemberId(1L);
        memberAnswer.setAnswerDate(new Date());

        memberAnswer = documentManager.addMemberAnswer(memberAnswer);

        prerequisiteDocument = new PrerequisiteDocument();
        prerequisiteDocument.setContent("content");
        prerequisiteDocument.setName("stress");
        prerequisiteDocument.setVersion(versions[0].getDocumentVersion());
        prerequisiteDocument.setDocumentId(document1.getDocumentId());
    }

    /**
     * Tear down.
     *
     * @throws Exception to junit
     */
    public void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        if (cm.existsNamespace("stresstests")) {
            cm.removeNamespace("stresstests");
        }
    }

    /**
     * Test method <code>getPrerequisiteDocument(long documentId, Integer version) </code>.
     *
     * @throws Exception to junit
     */
    public void testGetPrerequisiteDocument() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            PrerequisiteDocument ret = service.getPrerequisiteDocument(1, new Integer(1));
            assertNotNull("The PrerequisiteDocument must be returned.", ret);
        }

        long end = System.currentTimeMillis();

        System.out.println("calling getPrerequisiteDocument(long documentId, Integer version) 10 times " + " costs " +
            ((end - start) / 1000.0) + " seconds.");
    }

    /**
     * Stress test case for method <code>getPrerequisiteDocuments(long competitionId, long roleId) </code>.
     *
     * @throws Exception to junit
     */
    public void testGetPrerequisiteDocuments() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            List<PrerequisiteDocument> ret = service.getPrerequisiteDocuments(1, 1);
            assertNotNull("The PrerequisiteDocument must be returned.", ret);
            assertEquals("Size should be 2.", 2, ret.size());
        }

        long end = System.currentTimeMillis();

        System.out.println("calling getPrerequisiteDocument(long documentId, Integer version) 10 times " + " costs " +
            ((end - start) / 1000.0) + " seconds.");
    }

    /**
     * Stress test cases for method <code>recordMemberAnswer(long competitionId, XMLGregorianCalendar timestamp,
     * boolean agrees, PrerequisiteDocument prerequisiteDocument, long roleId) </code>.
     *
     * @throws Exception to junit
     */
    public void testRecordMemberAnswer() throws Exception {
        XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();

        long start = System.currentTimeMillis();

        service.recordMemberAnswer(cd1.getCompetitionId(), calendar, true, prerequisiteDocument, 1);

        long end = System.currentTimeMillis();

        System.out.println("Calling RecordMemberAnswer once costs:" + ((end - start) / 1000.0) + " seconds.");
    }

    /**
     * Test method <code> getAllPrerequisiteDocuments() </code> .
     * 
     * <p>
     * Must login as admin.
     * </p>
     *
     * @throws Exception to junit
     */
    public void testGetAllPrerequisiteDocuments() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        context = new InitialContext(env);

        service = (PrerequisiteService) context.lookup("remote/PrerequisiteService");
        documentManager = (DocumentManager) context.lookup("remote/DocumentManager");

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            List<PrerequisiteDocument> ret = service.getAllPrerequisiteDocuments();

            assertEquals("Equal to 5.", 5, ret.size());
        }

        long end = System.currentTimeMillis();

        System.out.println("Calling getAllPrerequisiteDocuments 10 times costs:" + ((end - start) / 1000.0));
    }
}
