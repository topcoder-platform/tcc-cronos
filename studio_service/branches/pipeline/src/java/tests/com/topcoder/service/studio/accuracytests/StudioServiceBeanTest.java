/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.TestCase;

import com.topcoder.service.studio.ContestGeneralInfoData;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestMultiRoundInformationData;
import com.topcoder.service.studio.ContestSpecificationsData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.MilestonePrizeData;
import com.topcoder.service.studio.MockSessionContext;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.ejb.StudioServiceBean;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionStatus;

/**
 * <p>
 * Accuracy tests for the <code>StudioServiceBean</code>.
 * In this test, the mocked managers are used.
 * </p>
 *
 * @author moon.river, myxgyy
 * @version 1.3
 */
public class StudioServiceBeanTest extends TestCase {
    /**
     * <p>Represents the contest name used in this test.
     */
    private static final String CONTEST_NAME = "contestName";

    /**
     * <p>Represents the bean to test.</p>
     */
    public StudioServiceBean bean;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        bean = new StudioServiceBean();
        setPrivateField("sessionContext", new MockSessionContext());
        setPrivateField("submissionManager", new SubmissionManagerImpl());
        setPrivateField("contestManager", new ContestManagerImpl());
    }

    /**
     * <p>
     * Tests the method <code>createContest</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateContest() throws Exception {
        ContestData result = bean.createContest(createContest(), 10);

        assertEquals("The contest is not created", CONTEST_NAME, ContestManagerImpl.contests.get(33l).getName());
        assertEquals("The name of the contest is wrong.", CONTEST_NAME, result.getName());
        assertEquals("The contest id is wrong.", 33, result.getContestId());
    }

    /**
     * <p>
     * Tests the method <code>createContestWithDocuments</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateContestWithDocuments() throws Exception {

        setPrivateField("logName", "log");

        ContestData cd = createContest();

        // create a list of documents
        List<UploadedDocument> docs = new ArrayList<UploadedDocument>();
        docs.add(createUploadedDocument());

        UploadedDocument ud = new UploadedDocument();
        ud.setFile("abcdefg".getBytes());
        ud.setDocumentId(50);
        docs.add(ud);
        cd.setDocumentationUploads(docs);

        // create the document
        bean.createContest(cd, 5);

        assertEquals("There should be 2 documents", 2, ContestManagerImpl.documents.size());
        assertNull("The file name is wrong.", ContestManagerImpl.documents.get(50l).getOriginalFileName());
    }

    /**
     * <p>
     * Tests the method <code>getContest</code>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetContest() throws Exception {
        setPrivateField("logName", "log");

        // create a contest in the persistence first
        ContestData cd = createContest();
        List<UploadedDocument> docs = new ArrayList<UploadedDocument>();
        docs.add(createUploadedDocument());
        UploadedDocument ud = new UploadedDocument();
        ud.setFile("xyz".getBytes());
        ud.setDocumentId(50);
        docs.add(ud);
        cd.setDocumentationUploads(docs);
        bean.createContest(cd, 5);

        // get it out
        ContestData constent = bean.getContest(33);
        assertEquals("The constest name is wrong.", CONTEST_NAME, constent.getName());

        assertEquals("The number of documents is wrong.", 2, constent.getDocumentationUploads().size());
    }

    /**
     * <p>
     * Tests the method <code>getContestsForProject</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetContestsForProject() throws Exception {

        // create the data
        bean.createContest(createContest(), 5);
        ContestData cd = createContest();
        cd.setContestId(1);
        bean.createContest(cd, 2);
        cd = createContest();
        cd.setContestId(2);
        bean.createContest(cd, 5);

        // get the contests for project 5
        List<ContestData> contests = bean.getContestsForProject(5);
        Set<Long> ids = new HashSet<Long>();
        for (ContestData contestData : contests) {
            ids.add(contestData.getContestId());
        }
        assertTrue("The ids are wrong.", ids.contains(33l));
        assertTrue("The ids are wrong.", ids.contains(2l));
    }

    /**
     * <p>
     * Tests the method <code>updateContest</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContest() throws Exception {
        ContestData cd = createContest();
        bean.createContest(cd, 5);
        cd.setName("name");
        cd.setTcDirectProjectId(13);
        bean.updateContest(cd);

        ContestData contestData = bean.getContest(33);
        assertEquals("The name is wrong.", "name", contestData.getName());
        assertEquals("The project id is wrong.", 13, contestData.getTcDirectProjectId());
    }

    /**
     * <p>
     * Tests the method <code>updateContest</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateWithDocuments() throws Exception {
        // set up the data
        ContestData cd = createContest();
        bean.createContest(cd, 5);

        cd.setName("name");
        cd.setTcDirectProjectId(13);
        List<UploadedDocument> docs = new ArrayList<UploadedDocument>();
        docs.add(createUploadedDocument());
        cd.setDocumentationUploads(docs);

        bean.updateContest(cd);

        ContestData contestData = bean.getContest(33);
        assertEquals("The contest name is wrong.", "name", contestData.getName());
        assertEquals("The contest id is wrong.", 33, contestData.getContestId());
    }

    /**
     * <p>
     * Tests the method <code>updateContestStatus</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContestStatus() throws Exception {
        setPrivateField("logName", "log");
        bean.createContest(createContest(), 5);

        // update the contest status
        bean.updateContestStatus(33, 7);

        assertEquals("The update call for contest status is wrong",
                "updateContestStatus(33, 7)", ContestManagerImpl.lastCall);
    }


    /**
     * <p>
     * Tests the method <code>uploadDocumentForContest</code>.
     *
     * @throws Exception to JUnit
     */
    public void testUploadDocumentForContest() throws Exception {
        bean.createContest(createContest(), 5);
        bean.uploadDocumentForContest(createUploadedDocument());
        assertEquals("The number of documents is wrong.", 1, ContestManagerImpl.documentsForContest.get(33l).size());
    }

    /**
     * <p>
     * Tests the method <code>removeDocumentFromContest</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveDocumentForContest() throws Exception {
        ContestData cd = createContest();
        List<UploadedDocument> docs = new ArrayList<UploadedDocument>();
        docs.add(createUploadedDocument());

        UploadedDocument ud = new UploadedDocument();
        ud.setFile("xyz".getBytes());
        ud.setDocumentId(50);
        docs.add(ud);
        cd.setDocumentationUploads(docs);
        bean.createContest(cd, 5);

        // remove the document
        ud.setContestId(33);
        MockSessionContext.isAdmin = false;
        MockSessionContext.userId = 33;
        bean.removeDocumentFromContest(ud);

        assertEquals("The number of documents is wrong.", 1, ContestManagerImpl.documentsForContest.get(33l).size());
    }

    /**
     * <p>
     * Tests the method <code>getAllContestStatuses</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllContestStatuses() throws Exception {
        int i = 0;
    	
        List<ContestStatusData> statuses = bean.getStatusList();
    	assertEquals("Wrong number of statuses", 3, statuses.size());

    	for (ContestStatusData data : statuses) {
            assertEquals("The description is wrong.", "desc" + i, data.getDescription());
            assertEquals("The name is wrong.", "name" + i, data.getName());
            ++i;
        }
    }

    /**
     * <p>
     * Tests the method <code>retrieveSubmissionsForContest</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveSubmissionsForContest() throws Exception {
        bean.createContest(createContest(), 5);
        Contest c = ContestManagerImpl.contests.get(33l);

        // add first submission
        Submission s = new Submission();
        s.setSubmissionId(1l);
        s.setContest(c);
        s.setRank(2);
        s.setSubmissionDate(new Date());
        s.setSubmitterId(10l);
        s.setPrizes(new HashSet<Prize>());
        s.setStatus(new SubmissionStatus());
        ContestType type = new ContestType();
        type.setDescription("Flex");
        c.setContestType(type);
        s.setContest(c);
        s.setStatus(new SubmissionStatus());
        s.getPrizes().add(newPrize(3));
        s.getPrizes().add(newPrize(8));
        SubmissionManagerImpl.submissions.add(s);

        // add second submission
        s = new Submission();
        s.setSubmissionId(2l);
        s.setStatus(new SubmissionStatus());
        s.setContest(c);
        SubmissionManagerImpl.submissions.add(s);

        List<SubmissionData> result = bean.retrieveSubmissionsForContest(33);
        assertEquals("The number of submissions is wrong.", 2, result.size());

        SubmissionData sd = result.get(0);
        assertEquals("The submission is wrong.", 1, sd.getSubmissionId());
        assertEquals("The contest id is wrong.", 33, sd.getContestId());
        assertEquals("The prize amount is wrong.", 3.0, sd.getPrice());

        sd = result.get(1);
        assertEquals("The contest id is wrong.", 33, sd.getContestId());
        assertEquals("The placement is wrong.", -1, sd.getPlacement());
    }

    /**
     * <p>
     * Tests the method <code>getSubmissionFileTypes</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSubmissionFileTypes() throws Exception {
        assertEquals("file types", "ex0,ex1,ex2,zip", bean.getSubmissionFileTypes());
    }

    /**
     * <p>
     * Tests the method <code>updateSubmission</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateSubmission() throws Exception {
        SubmissionData sd = new SubmissionData();
        sd.setContestId(1);
        sd.setPlacement(2);
        sd.setSubmissionId(3);
        sd.setSubmitterId(4);

        bean.updateSubmission(sd);
        Submission s = SubmissionManagerImpl.submissions.get(0);
        assertEquals("The submission id is wrong.", 3l, s.getSubmissionId().longValue());
    }

    /**
     * <p>
     * Tests the method <code>updateSubmission</code>.
     * </p>
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveSubmission() throws Exception {
        bean.removeSubmission(3);
        assertNull("The remove failed.", SubmissionManagerImpl.submissions);
    }

    /**
     * <p>Sets the private field for the bean.</p>
     *
     * @param name name of the field to set
     * @param value value to set for the field
     * @throws Exception to invoker
     */
    private void setPrivateField(String name, Object value) throws Exception {
        Field field = StudioServiceBean.class.getDeclaredField(name);
        try {
            field.setAccessible(true);
            field.set(bean, value);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Creates a contest data.
     * </p>
     *
     * @return created ContestData object
     */
    private ContestData createContest() {
        ContestData contestData = new ContestData();

        contestData.setName("contestName");
        contestData.setContestId(33);

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

        return contestData;
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

    /**
     * <p>
     * Creates an uploaded document instance.
     * </p>
     *
     * @return created UploadedDocument instance
     */
    private UploadedDocument createUploadedDocument() {
        UploadedDocument doc = new UploadedDocument();

        doc.setPath("/work/tc");
        doc.setMimeTypeId(5);
        doc.setFileName("someFile");
        doc.setFile("abc".getBytes());
        doc.setDocumentTypeId(6);
        doc.setDocumentId(7);
        doc.setDescription("desc");
        doc.setContestId(33);

        return doc;
    }

    /**
     * Creates new Prize object with specified amount and unique id.
     *
     * @param amount amount parameter of new Prize
     * @return created Prize object
     */
    private Prize newPrize(double amount) {
        Prize p = new Prize();
        p.setAmount(amount);
        p.setPrizeId(100l);
        return p;
    }
}
