/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import java.io.File;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.service.prerequisite.document.DocumentManager;
import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;

/**
 * <p>
 * This test case demonstrates the common usage of this component.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends BaseTestCase {
    /**
     * <p>
     * Represents the <code>PrerequisiteService</code> instance.
     * </p>
     */
    private PrerequisiteService prerequisiteService;

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
        return new TestSuite(Demo.class);
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

        documentManager = (DocumentManager) getInitialContext().lookup("remote/DocumentManager");

        // prepare data

        String[] sqls = new String[] {
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 20)",
            "insert into document (description, create_date, document_id) values ('Hello', '2008-02-20 12:53:45', 10)",
            "insert into document_name (document_name_id, name) values (1, 'Design')"};
        executeSQL(sqls);

        DocumentVersion documentVersion = createDocumentVersion(1);
        documentVersion.getDocument().setDocumentId(20L);
        documentVersion.setDocumentVersion(1);
        documentManager.addDocumentVersion(documentVersion);

        documentVersion = createDocumentVersion(1);
        documentVersion.getDocument().setDocumentId(20L);
        documentVersion.setDocumentVersion(2);
        documentManager.addDocumentVersion(documentVersion);

        documentVersion = createDocumentVersion(1);
        documentVersion.getDocument().setDocumentId(10L);
        documentVersion.setDocumentVersion(1);
        documentVersion = documentManager.addDocumentVersion(documentVersion);

        documentVersion = createDocumentVersion(1);
        documentVersion.getDocument().setDocumentId(10L);
        documentVersion.setContent("final version of production");
        documentVersion.setDocumentVersion(2);
        documentVersion = documentManager.addDocumentVersion(documentVersion);

        CompetitionDocument competitionDocument = createCompetitionDocument(0);
        competitionDocument.setDocumentVersion(documentVersion);

        competitionDocument.setCompetitionId(20L);
        competitionDocument = documentManager.addCompetitionDocument(competitionDocument);
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
     * demonstrates the common usage of this components.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDemo() throws Exception {
        // retrieve the web service
        // we can successfully retrieve the instance, but we can not call any operations because of authentication
        // problem.
        Service service = Service.create(new URL(
                "http://127.0.0.1:7070/prerequisite_service-prerequisite_service/PrerequisiteServiceBean?wsdl"),
                new QName("http://ejb.prerequisite.service.topcoder.com/", "PrerequisiteServiceBeanService"));
        prerequisiteService = service.getPort(PrerequisiteService.class);

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);
        // retrieve the service by ejb.
        prerequisiteService = (PrerequisiteService) initCtx.lookup("remote/PrerequisiteService");

        // get all documents, only administrator can do this operation.
        List<PrerequisiteDocument> allDocuments = prerequisiteService.getAllPrerequisiteDocuments();
        // the list now contains the 2 documents, and using the entities of prerequisite
        // manager you can get all the sub-entities: version, competition documents etc..

        // get the prerequisite document (similar to document version) of document with
        // id=10 and version=2
        PrerequisiteDocument prerequisiteDocument = prerequisiteService.getPrerequisiteDocument(10, 2);
        // get the content of document versions
        String content = prerequisiteDocument.getContent();

        // get the document id
        long documentId = prerequisiteDocument.getDocumentId();

        // get the name of document
        String name = prerequisiteDocument.getName();

        // get the version
        int version = prerequisiteDocument.getVersion();

        // get the timestamp of document version date
        XMLGregorianCalendar calendar = prerequisiteDocument.getVersionDate();
        // for example get the day
        int day = calendar.getDay();

        // get the prerequisite documents of competition 20 and role id = 1, it is the role
        // of "designer"
        List<PrerequisiteDocument> prerequisiteDocuments = prerequisiteService.getPrerequisiteDocuments(20, 1);
        // it returns a single prerequisite document (document version) associated with this
        // competition and role

        // create the timestamp and set to 27 February 2008
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, 2008);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 27);

        // create the XMLGregorianCalendar
        XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);

        // record the member answer for the competition id = 20, the date previous created,
        // the prerequisite document retrieved and role of designer: role id =1
        prerequisiteService.recordMemberAnswer(20, xmlCal, true, prerequisiteDocument, 1);
        // now a MemberAnswer entity is saved into persistence layer and the previous
        // prerequisite document has an answer "true" for the competition id = 20, and the member
        // id = 17, the member id is the id user id of the caller (in UserProfilePrincipal)
    }
}
