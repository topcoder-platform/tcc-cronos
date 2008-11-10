/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.StudioService;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.ejb.StudioServiceBeanTest;
import junit.framework.TestCase;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;

/**
 * <p>
 * Stress test for the basic functionality of this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StudioServiceStressTest extends TestCase {
    /**
     * <p>
     * Stress test for Studio Service.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testStudioServiceStress() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            // sets up studio service
            StudioServiceBeanTest test = new StudioServiceBeanTest();
            test.setUp();
            StudioService studioService = test.target;

            // Create the competition and set its values
            ContestData contestData = new ContestData();
            contestData.setContestId(20);

            // Create the upload documentation
            List<UploadedDocument> documentationUpload = new ArrayList<UploadedDocument>();
            UploadedDocument document = new UploadedDocument();
            document.setContestId(20);
            document.setDescription("Design Studio Distribution for Kink DAO");
            document.setDocumentId(40);
            document.setFile("designDistribution".getBytes());
            documentationUpload.add(document);

            // add the design distribution documentation to competition

            // create the timestamp with the current date
            GregorianCalendar cal = new GregorianCalendar();
            // create the XMLGregorianCalendar
            XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
            contestData.setName("Kink DAO studio design");

            // create the competition associated with a tc project
            studioService.createContest(contestData, 40);
            // now the competition is created and persisted

            // Get this competition back
            contestData = studioService.getContest(20);
            // get the previous competition which is related with the project with id=40
            studioService.getContestsForProject(40);
            // get the file types
            studioService.getSubmissionFileTypes();
            // remove the previous created document from the related competition
        }

        long cost = System.currentTimeMillis() - start;
        System.out.println("Stress test Studio Service for 50 times, cost " + (cost / 1000.0) + "seconds.");
    }
}
