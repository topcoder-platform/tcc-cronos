/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.ejb.StudioServiceBeanTest;

/**
 * This is demo test to demonstrate basic functionality of the component.
 *
 * @author fabrizyo, saarixx, TCSDEVELOPER
 * @version 1.3
 * @since 1.0
 */
public class Demo extends TestCase {
    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
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
        StudioService studioService = test.getBean();

        // Create the competition and set its values
        ContestData contestData = new ContestData();
        contestData.setName("contestName");
        contestData.setContestId(20);
        contestData.setShortSummary("ShortSummary");
        contestData.setContestDescriptionAndRequirements("contestDescriptionAndRequirements");
        contestData.setFinalFileFormat("zip");
        contestData.setContestTypeId(99990);
        contestData.setLaunchDateAndTime(getXMLGregorianCalendar(new Date()));

        ContestSpecificationsData specData = new ContestSpecificationsData();
        contestData.setSpecifications(specData);

        MilestonePrizeData prizeData = new MilestonePrizeData();
        contestData.setMilestonePrizeData(prizeData);

        ContestMultiRoundInformationData infoData = new ContestMultiRoundInformationData();
        contestData.setMultiRoundData(infoData);

        ContestGeneralInfoData generalInfoData = new ContestGeneralInfoData();
        contestData.setGeneralInfo(generalInfoData);

        // Create the upload documentation
        List<UploadedDocument> documentationUpload = new ArrayList<UploadedDocument>();
        UploadedDocument document = new UploadedDocument();
        document.setContestId(20);
        document.setDescription("Design Studio Distribution for Kink DAO");
        document.setFile("designDistribution".getBytes());
        documentationUpload.add(document);

        // create the competition associated with a tc project
        studioService.createContest(contestData, 40);
        // now the competition is created and persisted

        // Get this competition back
        contestData = studioService.getContest(20);
        // the competition is retrieved
        // You can get the previous values through the getters, the getters are
        // the same as the setters

        // get the previous competition which is related with the project with id=40
        List<ContestData> contestDatas = studioService.getContestsForProject(40);
        // the competition is retrieved (only 1 item)

        // get the file types
        String fileTypes = studioService.getSubmissionFileTypes();
        // it now contains "pdf","rtf","jpg","png",etc... separated by commas

        // remove the previous created document from the related competition
        // the design distribution is removed now

        // New features of the version 1.3
        // Get all contests for which test_user is a resource
        List<ContestData> testUserContests = studioService.getUserContests("test_user");

        // Get milestone submissions for contest with ID=1
        List<SubmissionData> milestoneSubmissions = studioService.getMilestoneSubmissionsForContest(1);

        // Get final submissions for contest with ID=1
        List<SubmissionData> finalSubmissions = studioService.getFinalSubmissionsForContest(1);

        // Set milestone prize with ID=1 to submission with ID=2
        studioService.setSubmissionMilestonePrize(2, 1);
    }

    /**
     * Converts standard java Date object into XMLGregorianCalendar instance.
     * Returns null if parameter is null.
     *
     * @param date Date object to convert
     * @return converted calendar instance
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            // can't create calendar, return null
            return null;
        }
    }
}
