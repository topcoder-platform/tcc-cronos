/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestGeneralInfoData;
import com.topcoder.service.studio.MockContestManager;
import com.topcoder.service.studio.ContestMultiRoundInformationData;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.ContestSpecificationsData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.IllegalArgumentWSException;
import com.topcoder.service.studio.MilestonePrizeData;
import com.topcoder.service.studio.MockSessionContext;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.StatusNotAllowedException;
import com.topcoder.service.studio.StatusNotFoundException;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.MockSubmissionManager;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.UserNotAuthorizedException;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionStatus;

/**
 * Tests for main class in this component, StudioServiceBean.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StudioServiceBeanTest extends TestCase {
    /**
     * Bean to test. Made public, so it can be used outside of this test class
     * (in demo, for example).
     */
    private StudioServiceBean target;

    /**
     * ContestManagerImpl used for test.
     */
    private MockContestManager contestMgr;
    /**
     * Index used in tests to build always different ids.
     */
    private long index = 0;

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(StudioServiceBeanTest.class);
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to junit
     */
    public void setUp() throws Exception {
        target = new StudioServiceBean();
        contestMgr = new MockContestManager();
        MockSessionContext ctx = new MockSessionContext();
        setPrivateField("sessionContext", new MockSessionContext());
        setPrivateField("submissionManager", new MockSubmissionManager());
        setPrivateField("contestManager", contestMgr);
        callInit();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to junit
     */
    public void tearDown() throws Exception {
    }

    /**
     * Tests init method with different configuration values.
     * IllegalStateException expected at the end (empty log name).
     *
     * @throws Exception when it occurs deeper
     */
    public void testInit() throws Exception {
        callInit();
        assertNull("no logger", target.getLog());

        setPrivateField("logName", "someName");
        callInit();
        assertNotNull("logger presented", target.getLog());

        setPrivateField("logName", "  ");
        try {
            callInit();
            fail("IllegalStateException expected.");
        } catch (InvocationTargetException ex) {
            // not ISE, since method was called through reflection api
        }
    }

    /**
     * This method checks who three simple getters works: for SubmissionManager,
     * ContestManager and SessionContext.
     */
    public void testGetters() {
        assertTrue("correct submission manager",
                target.getSubmissionManager() instanceof MockSubmissionManager);
        assertTrue("correct contest manager", target.getContestManager() instanceof MockContestManager);
        assertTrue("correct session context", target.getSessionContext() instanceof MockSessionContext);
    }

    /**
     * Tests createContest method for valid data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateContest() throws Exception {
        ContestData result = target.createContest(newContestData(), 5);

        // check if contest saved in persistence
        assertEquals("name of saved contest", "contestName", MockContestManager.contests.get(new Long(33))
                .getName());

        // check result
        assertEquals("name of contest", "contestName", result.getName());
        assertEquals("contestId", 33, result.getContestId());
    }

    /**
     * Tests createContest method with some documents to upload.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateContestWithDocuments() throws Exception {
        setPrivateField("logName", "someName");
        callInit();
        ContestData cd = newContestData();
        List<UploadedDocument> docs = new ArrayList<UploadedDocument>();
        docs.add(newUploadedDocument());

        UploadedDocument ud = new UploadedDocument();
        ud.setFile("xyz".getBytes());
        ud.setDocumentId(50);
        docs.add(ud);
        cd.setDocumentationUploads(docs);

        target.createContest(cd, 5);

        // check if contest saved in persistence
        assertEquals("number of documents", 2, MockContestManager.documents.size());
        assertEquals("file name", "someFile", MockContestManager.documents.get(7l).getOriginalFileName());
        assertEquals("file path", "/studiofiles/documents/", MockContestManager.documents.get(7l).getPath()
                .getPath());

        assertNull("file name", MockContestManager.documents.get(50l).getOriginalFileName());
        assertNotNull("file path", MockContestManager.documents.get(50l).getPath().getPath());

        // check doc contents
        assertEquals("content of the first document", "abc", new String(
                (byte[]) MockContestManager.documentsContent.get(7l)));
        assertEquals("content of the second document", "xyz", new String(
                (byte[]) MockContestManager.documentsContent.get(50l)));

        assertEquals("name of saved contest", "contestName", MockContestManager.contests.get(33l).getName());
    }

    /**
     * Tests method when user is not authorized to perform operation.
     * UserNotAuthorizedException expected.
     *
     * @throws Exception when it occurs deeper
     */
    /*
     * public void testCreateContestForAuthorizationFail() throws Exception {
     * MockSessionContext.isAdmin = false; MockSessionContext.userId = 10l; //
     * no such user try { target.createContest(newContestData(), 5);
     * fail("UserNotAuthorizedException expected."); } catch
     * (UserNotAuthorizedException ex) { // success } }
     */

    /**
     * Tests getContest method for valid data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetContest() throws Exception {
        setPrivateField("logName", "someName");
        callInit();
        // populate storage
        ContestData cd = newContestData();
        List<UploadedDocument> docs = new ArrayList<UploadedDocument>();
        docs.add(newUploadedDocument());
        UploadedDocument ud = new UploadedDocument();
        ud.setFile("xyz".getBytes());
        ud.setDocumentId(50);
        docs.add(ud);
        cd.setDocumentationUploads(docs);
        target.createContest(cd, 5);

        // retrieve contest and check it
        ContestData contestData = target.getContest(33);
        assertEquals("name of contest", "contestName", contestData.getName());
        assertEquals("contestId", 33, contestData.getContestId());

        // check returned documents
        assertEquals("number of documents", 2, contestData.getDocumentationUploads().size());
        Map<Long, UploadedDocument> resDocs = new HashMap<Long, UploadedDocument>();
        for (UploadedDocument doc : contestData.getDocumentationUploads()) {
            resDocs.put(doc.getDocumentId(), doc);
        }

        assertEquals("file name", "someFile", resDocs.get(7l).getFileName());
        assertEquals("file path", "/studiofiles/documents/", resDocs.get(7l).getPath());
        // assertEquals("document data", "abc", new
        // String(resDocs.get(7l).getFile()));
        assertNull("file name", resDocs.get(50l).getFileName());
        assertNotNull("file path", resDocs.get(50l).getPath());
        // assertEquals("document data", "xyz", new
        // String(resDocs.get(50l).getFile()));
    }

    /**
     * Tests getContest method when some documents retrieved also.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetContestWithDocuments() throws Exception {
        target.createContest(newContestData(), 5);

        Set<Submission> submissions = new HashSet<Submission>();
        Submission submission = new Submission();
        submission.setSubmissionId(1L);
        submissions.add(submission);
        submission.setSubmissionId(2L);
        submissions.add(submission);
        MockContestManager.contests.get(newContestData().getContestId()).setSubmissions(submissions);

        // retrieve result and check it
        ContestData contestData = target.getContest(33);
        assertEquals("submissionCount", 2, contestData.getSubmissionCount());

        assertEquals("name of contest", "contestName", contestData.getName());
        assertEquals("contestId", 33, contestData.getContestId());
    }

    /**
     * Tests getContest method when no contest with such id presented.
     * ContestNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetContestForNoContest() throws Exception {
        try {
            target.getContest(33);
            fail("ContestNotFoundException expected.");
        } catch (ContestNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests getContestsForProject method for valid data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetContestsForProject() throws Exception {
        // populate contests
        target.createContest(newContestData(), 5);
        ContestData cd = newContestData();
        cd.setContestId(1);
        target.createContest(cd, 2);
        cd = newContestData();
        cd.setContestId(2);
        target.createContest(cd, 5);

        Set<Submission> submissions = new HashSet<Submission>();
        Submission submission = new Submission();
        submissions.add(submission);
        MockContestManager.contests.get(33L).setSubmissions(submissions);

        // retrieve result and check it
        List<ContestData> contests = target.getContestsForProject(5);
        Set<Long> ids = new HashSet<Long>();
        for (ContestData contestData : contests) {
            ids.add(contestData.getContestId());
        }

        assertTrue("right ids were returned", ids.contains(33l));
        assertTrue("right ids were returned", ids.contains(2l));
        assertEquals("submissionCount", 0, contests.get(0).getSubmissionCount());
        assertEquals("submissionCount", 1, contests.get(1).getSubmissionCount());
    }

    /**
     * Tests getContestsForProject method internal error from ContestManager.
     * PersistenceException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetContestsForProjectForError() throws Exception {
        setPrivateField("logName", "someName");
        callInit();
        MockContestManager.errorRequested = true;
        try {
            target.getContestsForProject(5);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // success
        }
    }

    /**
     * Tests updateContest method for valid data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateContest() throws Exception {
        ContestData cd = newContestData();
        target.createContest(cd, 5);
        cd.setName("anotherName");
        cd.setTcDirectProjectId(13);
        target.updateContest(cd);

        // retrieve result and check it
        ContestData contestData = target.getContest(33);
        assertEquals("name of contest", "contestName", contestData.getName());
        assertEquals("contestId", 33, contestData.getContestId());
    }

    /**
     * Tests updateContest method for valid data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateWithDocuments() throws Exception {
        // prepare data for test
        ContestData cd = newContestData();
        target.createContest(cd, 5);

        cd.setName("anotherName");
        cd.setTcDirectProjectId(13);
        List<UploadedDocument> docs = new ArrayList<UploadedDocument>();
        docs.add(newUploadedDocument());
        cd.setDocumentationUploads(docs);

        target.updateContest(cd);

        // retrieve result and check it
        ContestData contestData = target.getContest(33);
        assertEquals("name of contest", "contestName", contestData.getName());
        assertEquals("contestId", 33, contestData.getContestId());
    }

    /**
     * Tests updateContest method when user is authorized to perform operation.
     * UserNotAuthorizedException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateContestForUserAuthorization() throws Exception {
        ContestData cd = newContestData();
        target.createContest(cd, 5);
        cd.setName("anotherName");
        cd.setTcDirectProjectId(13);

        // now try non-admin
        MockSessionContext.isAdmin = false;
        MockSessionContext.userId = 33l; // right user
        target.updateContest(cd);
    }

    /**
     * Tests updateContest method when passed contest is not persisted.
     * ContestNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateContestForMissedId() throws Exception {
        try {
            target.updateContest(newContestData());
            fail("ContestNotFoundException expected.");
        } catch (ContestNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests updateContestStatus method for valid data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateContestStatus() throws Exception {
        setPrivateField("logName", "someName");
        callInit();
        target.createContest(newContestData(), 5);

        // perform update
        target.updateContestStatus(33, 7);
        assertEquals("contestManager call", "updateContestStatus(33, 7)", MockContestManager.lastCall);
    }

    /**
     * Tests updateContestStatus method when user is not admin (only admins can
     * do it). UserNotAuthorizedException expected.
     *
     * @throws Exception when it occurs deeper
     */
    /*
     * public void testUpdateContestStatusForAuthorizationFail() throws
     * Exception { setPrivateField("logName", "someName"); callInit();
     *
     * target.createContest(newContestData(), 5);
     *
     * MockSessionContext.isAdmin = false; MockSessionContext.userId = 33; try { //
     * only admin can do this target.updateContestStatus(33, 55);
     * fail("UserNotAuthorizedException expected."); } catch
     * (UserNotAuthorizedException ex) { // success } }
     */

    /**
     * Tests updateContestStatus method when contest is not presented in
     * persistence. ContestNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateContestStatusForMissedContest() throws Exception {
        setPrivateField("logName", "someName");
        callInit();

        try {
            target.updateContestStatus(100, 7);
            fail("ContestNotFoundException expected.");
        } catch (ContestNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests updateContestStatus method when such status is not presented in
     * persistence. StatusNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateContestStatusForMissedStatus() throws Exception {
        setPrivateField("logName", "someName");
        callInit();

        target.createContest(newContestData(), 5);
        try {
            target.updateContestStatus(33, 8);
            fail("StatusNotFoundException expected.");
        } catch (StatusNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests updateContestStatus method when status change forbidden.
     * StatusNotAllowedException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateContestStatusForForbiddenStatus() throws Exception {
        target.createContest(newContestData(), 5);
        try {
            target.updateContestStatus(33, 9);
            fail("StatusNotAllowedException expected.");
        } catch (StatusNotAllowedException ex) {
            // success
        }
    }

    /**
     * Tests uploadDocumentForContest method for valid data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUploadDocumentForContest() throws Exception {
        target.createContest(newContestData(), 5);
        target.uploadDocumentForContest(newUploadedDocument());
        assertEquals("number of documents", 1, MockContestManager.documentsForContest.get(33l).size());
    }

    /**
     * Tests uploadDocumentForContest method when contest is not presented in
     * persistence. ContestNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUploadDocumentForContestForMissedContest() throws Exception {
        try {
            target.uploadDocumentForContest(newUploadedDocument());
            fail("ContestNotFoundException expected.");
        } catch (ContestNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests removeDocumentFromContest method for valid data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveDocumentForContest() throws Exception {
        // prepare data for test
        ContestData cd = newContestData();
        List<UploadedDocument> docs = new ArrayList<UploadedDocument>();
        docs.add(newUploadedDocument());

        UploadedDocument ud = new UploadedDocument();
        ud.setFile("xyz".getBytes());
        ud.setDocumentId(50);
        docs.add(ud);
        cd.setDocumentationUploads(docs);
        target.createContest(cd, 5);

        // tested method call
        ud.setContestId(33);
        MockSessionContext.isAdmin = false;
        MockSessionContext.userId = 33;
        target.removeDocumentFromContest(ud);

        // check result
        assertEquals("number of documents", 1, MockContestManager.documentsForContest.get(33l).size());
        assertTrue("valid document removed", MockContestManager.documentsForContest.get(33l).contains(7l));
    }

    /**
     * Tests removeDocumentFromContest method when document is not presented in
     * persistence. DocumentNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveDocumentFromContestForMissedDocument() throws Exception {
        target.createContest(newContestData(), 5);
        try {
            target.removeDocumentFromContest(newUploadedDocument());
            fail("DocumentNotFoundException expected.");
        } catch (DocumentNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests getAllContestStatuses method for accuracy.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetAllContestStatuses() throws Exception {
        List<ContestStatusData> statuses = target.getStatusList();
        assertEquals("Wrong number of statuses", 3, statuses.size());

        int i = 0;
        for (ContestStatusData data : statuses) {
            assertEquals("category id", i, data.getStatusId());
            assertEquals("description", "desc" + i, data.getDescription());
            assertEquals("name", "name" + i, data.getName());
            ++i;
        }
    }

    /**
     * Tests retrieveSubmissionsForContest method for valid data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRetrieveSubmissionsForContest() throws Exception {
        target.createContest(newContestData(), 5);
        Contest c = MockContestManager.contests.get(33l);
        // c.setContestId(33l);

        // add first submission
        Submission s = new Submission();
        s.setSubmissionId(1l);
        s.setContest(c);
        s.setRank(2);
        s.setSubmissionDate(new Date());
        s.setSubmitterId(10l);
        s.setPrizes(new HashSet<Prize>());
        s.getPrizes().add(newPrize(3));
        s.getPrizes().add(newPrize(8));
        MockSubmissionManager.submissions.add(s);

        // add second (empty) submission
        s = new Submission();
        s.setSubmissionId(2l);
        MockSubmissionManager.submissions.add(s);

        // call tested method and check result
        List<SubmissionData> result = target.retrieveSubmissionsForContest(33);
        assertEquals("number of submissions", 0, result.size());
        /*
         * SubmissionData sd = result.get(0); assertEquals("submission id", 1,
         * sd.getSubmissionId()); assertEquals("contest id", 33,
         * sd.getContestId()); assertEquals("placement", 2, sd.getPlacement());
         * assertEquals("prize amount", 11.0, sd.getPrice());
         * assertEquals("submitter id", 10, sd.getSubmitterId());
         *
         * sd = result.get(1); assertEquals("contest id", -1,
         * sd.getContestId()); assertEquals("placement", -1, sd.getPlacement());
         * assertEquals("prize amount", 0.0, sd.getPrice());
         * assertEquals("submitter id", -1, sd.getSubmitterId());
         */
    }

    /**
     * Tests retrieveSubmissionsForContest method for unknown contest id.
     * ContestNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRetrieveSubmissionsForMissedContest() throws Exception {
        setPrivateField("logName", "someName");
        try {
            target.retrieveSubmissionsForContest(55);
            fail("ContestNotFoundException expected.");
        } catch (ContestNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests retrieveAllSubmissionsByMember method for valid data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRetrieveAllSubmissionsByMember() throws Exception {
        setPrivateField("logName", "someName");
        callInit();

        Contest c = new Contest();
        c.setContestId(100l);
        c.setContestType(new ContestType());

        // add first submission
        Submission s = new Submission();
        s.setSubmissionId(1l);
        s.setContest(c);
        s.setRank(2);
        s.setSubmissionDate(new Date());
        s.setSubmitterId(10l);
        s.setPrizes(new HashSet<Prize>());
        s.getPrizes().add(newPrize(3));
        s.setStatus(new SubmissionStatus());
        s.getPrizes().add(newPrize(8));

        MockSubmissionManager.submissions.add(s);

        // add second (empty) submission
        s = new Submission();
        s.setSubmissionId(2l);
        s.setContest(c);
        s.setStatus(new SubmissionStatus());
        MockSubmissionManager.submissions.add(s);

        // call tested method and check result
        List<SubmissionData> result = target.retrieveAllSubmissionsByMember(3);
        assertEquals("number of submissions", 2, result.size());
        SubmissionData sd = result.get(0);
        assertEquals("submission id", 1, sd.getSubmissionId());
        assertEquals("contest id", 100, sd.getContestId());
        assertEquals("prize amount", 11.0, sd.getPrice());
        assertEquals("submitter id", 10, sd.getSubmitterId());

        sd = result.get(1);
        assertEquals("placement", -1, sd.getPlacement());
        assertEquals("prize amount", 0.0, sd.getPrice());
        assertEquals("submitter id", -1, sd.getSubmitterId());
    }

    /**
     * Only admins can use this method. UserNotAuthorizedException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRetrieveAllSubmissionsByMemberForNotAdmin() throws Exception {
        MockSessionContext.isAdmin = false;
        try {
            target.retrieveAllSubmissionsByMember(3);
            fail("UserNotAuthorizedException expected.");
        } catch (UserNotAuthorizedException ex) {
            // success
        }
    }

    /**
     * Tests getSubmissionFileTypes method for accuracy.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSubmissionFileTypes() throws Exception {
        assertEquals("file types", "ex0,ex1,ex2,zip", target.getSubmissionFileTypes());
    }

    /**
     * Tests getSubmissionFileTypes method internal error from ContestManager.
     * PersistenceException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSubmissionFileTypesForError() throws Exception {
        MockContestManager.errorRequested = true;
        try {
            target.getSubmissionFileTypes();
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // success
        }
    }

    /**
     * Tests updateSubmission method for accuracy.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateSubmission() throws Exception {
        SubmissionData sd = new SubmissionData();
        sd.setContestId(1);
        sd.setPlacement(2);
        sd.setSubmissionId(3);
        sd.setSubmitterId(4);

        // perform update and check result
        target.updateSubmission(sd);
        Submission s = MockSubmissionManager.submissions.get(0);
        assertEquals("submission id", 3l, s.getSubmissionId().longValue());
        assertEquals("submitter id", 4l, s.getSubmitterId().longValue());
    }

    /**
     * Tests updateSubmission method for accuracy.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveSubmission() throws Exception {
        target.removeSubmission(3);
        assertNull("removed", MockSubmissionManager.submissions);
    }

    /**
     * Tests createContest method for null parameter. IllegalArgumentWSException
     * expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateContestForNull() throws Exception {
        try {
            target.createContest(null, 10);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests createContest method for negative parameter.
     * IllegalArgumentWSException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateContestForNegative() throws Exception {
        try {
            target.createContest(newContestData(), -10);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests getContest method for negative parameter.
     * IllegalArgumentWSException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetContestForNegative() throws Exception {
        try {
            target.getContest(-10);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests getContestForProject method for negative parameter.
     * IllegalArgumentWSException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetContestsForProjectForNegative() throws Exception {
        try {
            target.getContestsForProject(-11);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests updateContest method for null parameter. IllegalArgumentWSException
     * expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateContestForNull() throws Exception {
        try {
            target.updateContest(null);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests updateContestStatus method for negative first parameter.
     * IllegalArgumentWSException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateContestStatusForNegative1() throws Exception {
        try {
            target.updateContestStatus(-12, 3);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests updateContestStatus method for negative second parameter.
     * IllegalArgumentWSException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateContestStatusForNegative2() throws Exception {
        try {
            target.updateContestStatus(2, -13);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests uploadDocumentForContest method for null parameter.
     * IllegalArgumentWSException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUploadDocumentForContestForNull() throws Exception {
        try {
            target.uploadDocumentForContest(null);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests removeDocumentFromContest method for null parameter.
     * IllegalArgumentWSException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveDocumentFromContestForNull() throws Exception {
        try {
            target.removeDocumentFromContest(null);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests retrieveSubmissionsForContest method for negative parameter.
     * IllegalArgumentWSException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRetrieveSubmissionsForContestForNegative() throws Exception {
        try {
            target.retrieveSubmissionsForContest(-14);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests retrieveAllSubmissionsByMember method for negative parameter.
     * IllegalArgumentWSException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRetrieveAllSubmissionsByMemberForNegative() throws Exception {
        try {
            target.retrieveAllSubmissionsByMember(-15);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests updateSubmission method for null parameter.
     * IllegalArgumentWSException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateSubmissionForNull() throws Exception {
        try {
            target.updateSubmission(null);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests removeSubmission method for negative parameter.
     * IllegalArgumentWSException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveSubmissionForNegative() throws Exception {
        try {
            target.removeSubmission(-1);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests getAllContests with admin user.
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetAllContests_Admin() throws Exception {
        MockSessionContext.isAdmin = true;

        // ContestManager will return clientId 33 when contestId <= 555 or
        // contestId >= 666
        // Otherwise, it returns the passed clientId.

        // ClientId = 556
        ContestData contestData1 = newContestData();
        contestData1.setContestId(556);
        contestData1.setForumId(1);
        target.createContest(contestData1, 1);

        // ClientId = 557
        ContestData contestData2 = newContestData();
        contestData2.setContestId(557);
        contestData1.setForumId(1);
        target.createContest(contestData2, 1);

        // ClientId = 33
        ContestData contestData3 = newContestData();
        contestData3.setContestId(3);
        contestData1.setForumId(1);
        target.createContest(contestData3, 2);

        // ClientId = 33
        ContestData contestData4 = newContestData();
        contestData4.setContestId(4);
        contestData1.setForumId(1);
        target.createContest(contestData4, 2);

        List<ContestData> allContests = target.getAllContests();
        assertEquals("Contests count is incorrect", 0, allContests.size());
    }

    /**
     * Tests getAllContests with non-admin user.
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetAllContests_NonAdmin1() throws Exception {
        MockSessionContext.isAdmin = false;

        // ContestManager will return clientId 33 when contestId <= 555 or
        // contestId >= 666
        // Otherwise, it returns the passed clientId.

        // ClientId = 556
        ContestData contestData1 = newContestData();
        contestData1.setContestId(556);
        target.createContest(contestData1, 1);

        // ClientId = 557
        ContestData contestData2 = newContestData();
        contestData2.setContestId(557);
        target.createContest(contestData2, 1);

        // ClientId = 33
        ContestData contestData3 = newContestData();
        contestData3.setContestId(3);
        target.createContest(contestData3, 2);

        // ClientId = 33
        ContestData contestData4 = newContestData();
        contestData4.setContestId(4);
        target.createContest(contestData4, 2);

        List<ContestData> allContests = target.getAllContests();
        assertEquals("Contests count is incorrect", 4, allContests.size());
    }

    /**
     * Tests getAllContests with non-admin user.
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetAllContests_NonAdmin2() throws Exception {
        MockSessionContext.isAdmin = false;

        // ContestManager will return clientId 33 when contestId <= 555 or
        // contestId >= 666
        // Otherwise, it returns the passed clientId.

        // ClientId = 556
        ContestData contestData1 = newContestData();
        contestData1.setContestId(556);
        target.createContest(contestData1, 1);

        // ClientId = 557
        ContestData contestData2 = newContestData();
        contestData2.setContestId(557);
        target.createContest(contestData2, 1);

        // ClientId = 558
        ContestData contestData3 = newContestData();
        contestData3.setContestId(558);
        target.createContest(contestData3, 2);

        List<ContestData> allContests = target.getAllContests();
        assertEquals("Contests count is incorrect", 3, allContests.size());
    }

    /**
     * Tests getContestsForClient method.
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetContestsForClient1() throws Exception {
        // ContestManager will return clientId 33 when contestId <= 555 or
        // contestId >= 666
        // Otherwise, it returns the passed clientId.

        // ClientId = 556
        ContestData contestData1 = newContestData();
        contestData1.setContestId(556);
        target.createContest(contestData1, 1);

        // ClientId = 557
        ContestData contestData2 = newContestData();
        contestData2.setContestId(557);
        target.createContest(contestData2, 1);

        // ClientId = 33
        ContestData contestData3 = newContestData();
        contestData3.setContestId(3);
        target.createContest(contestData3, 2);

        // ClientId = 33
        ContestData contestData4 = newContestData();
        contestData4.setContestId(4);
        target.createContest(contestData4, 2);

        List<ContestData> contestsForClient = target.getContestsForClient(33);
        assertEquals("Contests count is incorrect", 0, contestsForClient.size());
    }

    /**
     * Tests getContestsForClient method.
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetContestsForClient2() throws Exception {
        // ContestManager will return clientId 33 when contestId <= 555 or
        // contestId >= 666
        // Otherwise, it returns the passed clientId.

        // ClientId = 556
        ContestData contestData1 = newContestData();
        contestData1.setContestId(556);
        target.createContest(contestData1, 1);

        // ClientId = 557
        ContestData contestData2 = newContestData();
        contestData2.setContestId(557);
        target.createContest(contestData2, 1);

        // ClientId = 33
        ContestData contestData3 = newContestData();
        contestData3.setContestId(3);
        target.createContest(contestData3, 2);

        // ClientId = 33
        ContestData contestData4 = newContestData();
        contestData4.setContestId(4);
        target.createContest(contestData4, 2);

        List<ContestData> contestsForClient = target.getContestsForClient(44);
        assertEquals("Contests count is incorrect", 0, contestsForClient.size());
    }

    /**
     * Used to set contents of private field in tested bean (inject resources).
     *
     * @param fieldName name of the field to set
     * @param value value to set in the field
     * @throws Exception when it occurs deeper
     */
    public void setPrivateField(String fieldName, Object value) throws Exception {
        Field f = StudioServiceBean.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }

    /**
     * Creates ContestData instance with some parameters set to default values.
     *
     * @return created ContestData object
     */
    private ContestData newContestData() {
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
     * Creates new UploadedDocument object with default content.
     *
     * @return created UploadedDocument instance
     */
    private UploadedDocument newUploadedDocument() {
        UploadedDocument doc = new UploadedDocument();

        doc.setPath("/studiofiles/documents/");
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
        p.setPrizeId(index++);
        return p;
    }

    /**
     * Simple routine to call private method init through reflection api.
     *
     * @throws Exception when it occurs deeper
     */
    private void callInit() throws Exception {
        Method m = target.getClass().getDeclaredMethod("init");
        m.setAccessible(true);
        m.invoke(target);
    }

    /**
     * Tests uploadDocument method for valid data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUploadDocument() throws Exception {
        UploadedDocument doc = target.uploadDocument(newUploadedDocument());
        assertNotNull("uploadDocument fails.", MockContestManager.documents.get(doc.getDocumentId()));
    }

    /**
     * Tests addDocumentToContest method for valid data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testAddDocumentToContest() throws Exception {
        // prepare data for test
        ContestData cd = newContestData();
        target.createContest(cd, 5);

        UploadedDocument doc = target.uploadDocument(newUploadedDocument());
        assertNotNull("uploadDocument fails.", MockContestManager.documents.get(doc.getDocumentId()));

        target.addDocumentToContest(doc.getDocumentId(), cd.getContestId());
        assertEquals("number of documents", 1, MockContestManager.documentsForContest.get(33l).size());
    }

    /**
     * Tests removeSubmission method for accuracy.
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveDocument() throws Exception {
        target.removeDocument(3);
        assertFalse("remove failed.", MockContestManager.documents.containsKey(3));
    }

    /**
     * Tests removeDocument method for negative parameter.
     * IllegalArgumentWSException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveDocumentForNegative() throws Exception {
        try {
            target.removeDocument(-1);
            fail("IllegalArgumentWSException expected.");
        } catch (IllegalArgumentWSException ex) {
            // success
        }
    }

    /**
     * Tests createContestPayment method for valid data.
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateContestPayment() throws Exception {
        ContestData contest = target.createContest(newContestData(), 5);

        ContestPaymentData data = new ContestPaymentData();
        data.setContestId(contest.getContestId());
        data.setPaymentStatusId(1L);
        data.setPrice(500.34);
        data.setPaymentTypeId(1L);

        target.createContestPayment(data, "un");
    }


    /**
     * Tests getUserContests(username) method for null parameter.
     * IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper.
     * @since 1.3
     */
    public void testGetUserContestsNull() throws Exception {
        try {
            target.getUserContests(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getUserContests(username) method for empty parameter.
     * IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper.
     * @since 1.3
     */
    public void testGetUserContestsEmpty() throws Exception {
        try {
            target.getUserContests("   ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getUserContests(username) method for invalid case.
     * PersistenceException expected.
     *
     * @throws Exception when it occurs deeper.
     * @since 1.3
     */
    public void testGetUserContestsInvalid() throws Exception {
        try {
            target.getUserContests("-1");
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // success
        }
    }

    /**
     * Tests getUserContests(username) method for valid data.
     *
     * @throws Exception to JUnit.
     * @since 1.3
     */
    public void testGetUserContests() throws Exception {
        List<ContestData> result = target.getUserContests("2");
        assertEquals("getUserContests fails", 2, result.size());
    }

    /**
     * Tests getMilestoneSubmissionsForContest(long contestId) method for invalid case.
     * PersistenceException expected.
     *
     * @throws Exception when it occurs deeper.
     * @since 1.3
     */
    public void testGetMilestoneSubmissionsForContestInvalid() throws Exception {
        try {
            target.getMilestoneSubmissionsForContest(-1);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // success
        }
    }

    /**
     * Tests getMilestoneSubmissionsForContest(long contestId) method for valid data.
     *
     * @throws Exception to JUnit.
     * @since 1.3
     */
    public void testGetMilestoneSubmissionsForContest() throws Exception {
        List<SubmissionData> result = target.getMilestoneSubmissionsForContest(1);
        assertEquals("getUserContests fails", 1, result.size());
    }

    /**
     * Tests getFinalSubmissionsForContest(long contestId) method for invalid case.
     * PersistenceException expected.
     *
     * @throws Exception when it occurs deeper.
     * @since 1.3
     */
    public void testGetFinalSubmissionsForContestInvalid() throws Exception {
        try {
            target.getFinalSubmissionsForContest(-1);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // success
        }
    }

    /**
     * Tests getFinalSubmissionsForContest(long contestId) method for valid data.
     *
     * @throws Exception to JUnit.
     * @since 1.3
     */
    public void testGetFinalSubmissionsForContest() throws Exception {
        List<SubmissionData> result = target.getFinalSubmissionsForContest(1);
        assertEquals("getUserContests fails", 1, result.size());
    }

    /**
     * Tests setSubmissionMilestonePrize(long submissionId, long milestonePrizeId) method for valid data.
     *
     * @throws Exception to JUnit.
     * @since 1.3
     */
    public void testSetSubmissionMilestonePrize() throws Exception {
        target.setSubmissionMilestonePrize(1, 1);
    }

    /**
     * Tests setSubmissionMilestonePrize(long submissionId, long milestonePrizeId) method for invalid case.
     * PersistenceException expected.
     *
     * @throws Exception when it occurs deeper.
     * @since 1.3
     */
    public void testSetSubmissionMilestonePrizeInvalid() throws Exception {
        try {
            target.setSubmissionMilestonePrize(-1, 1);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // success
        }
    }

    /**
     * Get the StudioServiceBean instance.
     * @return the StudioServiceBean.
     */
    public StudioServiceBean getBean() {
        return target;
    }
}
