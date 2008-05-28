/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import com.topcoder.service.studio.ejb.StudioServiceBeanTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;

/**
 * This is demo test to demonstrate basic functionality of the component.
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Some basic functionality for Studio Service.
     *
     * @throws Exception when it occurs deeper
     */
    public void test() throws Exception {
        // 'set up' studio service
        StudioServiceBeanTest test = new StudioServiceBeanTest();
        test.setUp();
        StudioService studioService = test.target;

        //Create the competition and set its values
        ContestData contestData = new ContestData();
        contestData.setContestId(20);
        contestData.setContestDescriptionAndRequirements("The requirements are...");
        contestData.setContestCategoryId(20);
        contestData.setCreatorUserId(30);

        //Create the upload documentation
        List<UploadedDocument> documentationUpload = new ArrayList<UploadedDocument>();
        UploadedDocument document = new UploadedDocument();
        document.setContestId(20);
        document.setDescription("Design Studio Distribution for Kink DAO");
        document.setDocumentId(40);
        document.setFile("designDistribution".getBytes());
        documentationUpload.add(document);

        //add the design distribution documentation to competition
        contestData.setDocumentationUploads(documentationUpload);

        //launch the competition now
        // create the timestamp with the current date
        GregorianCalendar cal = new GregorianCalendar();
        // create the XMLGregorianCalendar
        XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        contestData.setLaunchDateAndTime(xmlCal);
        contestData.setName("Kink DAO studio design");
        contestData.setOtherRequirementsOrRestrictions("nothing");

        //create the competition associated with a tc project
        studioService.createContest(contestData, 40);
        //now the competition is created and persisted

        //Get this competition back
        contestData = studioService.getContest(20);
        //the competition is retrieved
        //You can get the previous values through the getters, the getters are the same as the setters

        //get the previous competition which is related with the project with id=40
        List<ContestData> contestDatas = studioService.getContestsForProject(40);
        //the competition is retrieved (only 1 item)

        //get the file types
        String fileTypes = studioService.getSubmissionFileTypes();
        //it now contains "pdf","rtf","jpg","png",etc... separated by commas

        //remove the previous created document from the related competition
        studioService.removeDocumentFromContest(document);
        //the design distribution is removed now
    }
}
