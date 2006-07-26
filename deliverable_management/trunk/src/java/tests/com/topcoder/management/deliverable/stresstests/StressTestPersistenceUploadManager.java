/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.stresstests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.PersistenceUploadManager;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.management.deliverable.persistence.sql.MyUploadPersistence;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.management.deliverable.search.UploadFilterBuilder;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.datavalidator.LongValidator;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>PersistenceUploadManager </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class StressTestPersistenceUploadManager extends TestCase {

    /**
     * Represents the UploadPersistence instance for test.
     */
    private UploadPersistence persistence = null;

    /**
     * Represents the SearchBundleManager instance for test.
     */
    private SearchBundleManager sm = null;

    /**
     * Represents the PersistenceUploadManager instance for test.
     */
    private PersistenceUploadManager manager = null;

    /**
     * Set up the testing environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }

        cm.add("stresstests/DBConnectionFactory.xml");
        cm.add("stresstests/SearchBundleManager.xml");

        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        persistence = new MyUploadPersistence(factory);

        String s_n = "com.topcoder.search.builder.SearchBundleManager";
        sm = new SearchBundleManager(s_n);

        Map uploadFields = new HashMap();
        uploadFields.put("project_id", LongValidator.greaterThanOrEqualTo(0));

        SearchBundle uploadSearchBundle = sm.getSearchBundle("Upload Search Bundle");
        uploadSearchBundle.setSearchableFields(uploadFields);

        Map submissionFields = new HashMap();
        submissionFields.put("resource_id", LongValidator.greaterThanOrEqualTo(0));
        SearchBundle submissionSearchBundle = sm.getSearchBundle("Submission Search Bundle");
        submissionSearchBundle.setSearchableFields(submissionFields);

        manager = new PersistenceUploadManager(persistence, sm);

        DBTestUtil.setUpTest();
    }

    /**
     * Clear the tables for testing and remove all the namespaces in the config manager.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        DBTestUtil.tearDownTest();

        DBTestUtil.removeAllNamespace();
    }

    /**
     * Test constructor <code> PersistenceUploadManager(UploadPersistence, SearchBundleManager) </code>.
     */
    public void testConstructor() {
        assertNotNull("Should not be null.", manager);
    }

    /**
     * Stress test for method <code>void createUpload(Upload upload, String operator) </code>.
     *
     */
    public void testCreateUpload() throws Exception {
        long total = 0;
        long id = 0;

        for (int i = 0; i < 20; i++) {
            Upload upload = DBTestUtil.createUploadWithIdNoSet(1, 1);
            long start = System.currentTimeMillis();
            manager.createUpload(upload, "stress");

            total += System.currentTimeMillis() - start;
            id = upload.getId();
        }

        // check if the create Upload is correct.
        Upload ret = persistence.loadUpload(id);

        assertNotNull("Should not be null.", ret);

        System.out.println("Invoke createUpload method 20 times cost " + total / 1000.0 + " second.");
    }

    /**
     * Stress test for method <code>void updateUpload(Upload upload, String operator) </code>.
     *
     */
    public void testUpdateUpload() throws Exception {
        Upload upload = DBTestUtil.createUploadWithIdNoSet(1, 1);
        upload.setId(10);

        persistence.addUpload(upload);

        long total = 0;
        for (int i = 0; i < 20; i++) {
            long start = System.currentTimeMillis();
            manager.updateUpload(upload, "stress");
            total += System.currentTimeMillis() - start;
        }

        // check if the update is correct.
        Upload ret = persistence.loadUpload(upload.getId());
        assertEquals("Equal is expected.", "stress", ret.getModificationUser());

        System.out.println("Invoke updateUpload method 20 times cost " + total / 1000.0 + " second.");
    }

    /**
     * Test method <code> void removeUpload(Upload upload, String operator) </code>.
     *
     * @throws Exception
     */
    public void testRemoveUpload() throws Exception {
        Upload upload = DBTestUtil.createUploadWithIdNoSet(1, 1);
        upload.setId(100);

        long total = 0;

        for (int i = 0; i < 20; i++) {
            long start = System.currentTimeMillis();

            manager.removeUpload(upload, "stress");

            total += System.currentTimeMillis() - start;
        }

        // check if the upload with id 100 is removed.
        assertNull("should be null", persistence.loadUpload(100));

        System.out.println("Invoke removeUpload method 20 times cost " + total / 1000.0 + " second.");
    }

    /**
     * Stress test for method <code>Upload getUpload(long id) </code>.
     *
     */
    public void testGetUpload() throws Exception {
        long total = 0;
        Upload upload = null;
        for (int i = 0; i < 20; i++) {
            long start = System.currentTimeMillis();

            upload = manager.getUpload(1);

            total += System.currentTimeMillis() - start;
        }

        // check if the project id is 2.
        assertEquals("The project id should be 2.", 2, upload.getProject());

        System.out.println("Invoke getUpload method 20 times cost " + total / 1000.0 + " second.");
    }

    /**
     * Stress test for method <code>Upload[] searchUploads(Filter filter) </code>.
     *
     * <p>
     * It will search all upload instance for project id = 1;
     * </p>
     *
     * @throws Exception
     */
    public void testSearchUploads() throws Exception {
        long total = 0;
        for (int i = 0; i < 20; i++) {
            long start = System.currentTimeMillis();
            Upload[] uploads = manager.searchUploads(UploadFilterBuilder.createProjectIdFilter(1));

            total += System.currentTimeMillis() - start;

            assertEquals("2 upload should be returned.", 2, uploads.length);
        }

        System.out.println("Invoke SearchUploads method 20 times cost " + total / 1000.0 + " second.");
    }

    /**
     * Stress test for method <code>void createUploadType(UploadType uploadType, String operator) </code>.
     *
     */
    public void testCreateUploadType() throws Exception {
        long total = 0;
        long id = 0;

        for (int i = 0; i < 20; i++) {
            UploadType type = DBTestUtil.createUploadType();
            long start = System.currentTimeMillis();

            manager.createUploadType(type, "stress");

            total += System.currentTimeMillis() - start;
            id = type.getId();
        }

        // check if the UploadType is created.
        UploadType ret = persistence.loadUploadType(id);
        assertNotNull("Should not be null.", ret);

        System.out.println("Invoke createUploadType method 20 times cost " + total / 1000.0 + " second.");
    }

    /**
     * Stress test for method <code>void updateUploadType(UploadType uploadType, String operator) </code>.
     *
     */
    public void testUpdateUploadType() throws Exception {
        UploadType type = DBTestUtil.createUploadType();

        manager.createUploadType(type, "create");

        long total = 0;

        for (int i = 0; i < 20; i++) {
            long start = System.currentTimeMillis();
            manager.updateUploadType(type, "stress");
            total += System.currentTimeMillis() - start;
        }

        assertEquals("Equal is expected.", "stress", persistence.loadUploadType(type.getId()).getModificationUser());
        System.out.println("Invoke updateUploadTye method for 20 times cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>void removeUploadType(UploadType uploadType, String operator) </code>.
     *
     */
    public void testRemoveUploadType() throws Exception {
        UploadType type = DBTestUtil.createUploadType();
        type.setId(100);

        long total = 0;
        for (int i = 0; i < 20; i++) {
            long start = System.currentTimeMillis();
            manager.removeUploadType(type, "stress");

            total += System.currentTimeMillis() - start;
        }

        System.out.println("Invoke removeUploadType for 20 times cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>UploadType[] getAllUploadTypes() </code>.
     *
     */
    public void testGetAllUploadTypes() throws Exception {
        long total = 0;

        for (int i = 0; i < 20; i++) {
            long start = System.currentTimeMillis();
            UploadType[] types = manager.getAllUploadTypes();
            total += System.currentTimeMillis() - start;
            assertTrue("True is expected.", types.length > 0);
        }

        System.out.println("Invoke getAllUploadTypes method for 20 times cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test method for <code>void createUploadStatus(UploadStatus uploadStatus, String operator)</code>.
     *
     */
    public void testCreateUploadStatus() throws Exception {
        long total = 0;
        for (int i = 0; i < 20; i++) {
            UploadStatus status = DBTestUtil.createUploadStatus();

            long start = System.currentTimeMillis();

            manager.createUploadStatus(status, "stress");

            total += System.currentTimeMillis() - start;
        }

        System.out.println("Invoke createUploadStatus method 2o times cost " + total / 1000.0 + " second.");

    }

    /**
     * Stress for test method <code>void updateUploadStatus(UploadStatus uploadStatus, String operator)
     * </code>.
     *
     */
    public void testUpdateUploadStatus() throws Exception {
        UploadStatus status = DBTestUtil.createUploadStatus();
        manager.createUploadStatus(status, "create");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            manager.updateUploadStatus(status, "stress");
        }

        long end = System.currentTimeMillis();
        System.out.println("Invoke updateUploadStatus for 20 times cost " + (end - start) / 1000.0 + " seconds");
    }

    /**
     * Stress test for method <code>void removeUploadStatus(UploadStatus uploadStatus, String operator) </code>.
     *
     */
    public void testRemoveUploadStatus() throws Exception {
        UploadStatus status = DBTestUtil.createUploadStatus();
        status.setId(100);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            manager.removeUploadStatus(status, "stress");
        }
        long end = System.currentTimeMillis();

        System.out.println("Invoke removeUploadStatus for 20 times cost " + (end - start) / 1000.0 + " second.");
    }

    /**
     * Stress tset for method <code>UploadStatus[] getAllUploadStatuses() </code>.
     *
     */
    public void testGetAllUploadStatuses() throws Exception {
        long total = 0;

        for (int i = 0; i < 20; i++) {
            long start = System.currentTimeMillis();
            UploadStatus[] status = manager.getAllUploadStatuses();
            total += System.currentTimeMillis() - start;

            assertTrue("True is expected.", status.length > 0);
        }

        System.out.println("Invoke getAllUploadStatus for 20 times cost " + total / 1000.0 + " seconds");
    }

    /**
     * Stress test for method <code>void createSubmission(Submission submission, String operator) </code>.
     *
     * @throws Exception
     */
    public void testCreateSubmission() throws Exception {
        long total = 0;
        long id = 0;
        for (int i = 0; i < 20; i++) {

            Submission submission = DBTestUtil.createSubmission();

            long start = System.currentTimeMillis();
            manager.createSubmission(submission, "operator");
            total = System.currentTimeMillis() - start;

            id = submission.getId();
        }

        assertNotNull("Should not be null.", persistence.loadSubmission(id));

        System.out.println("Invoke createSubmission for 20 times cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>void updateSubmission(Submission submission, String operator) </code>.
     *
     */
    public void testUpdateSubmission() throws Exception {

        Submission submission = DBTestUtil.createSubmission();

        manager.createSubmission(submission, "create");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            manager.updateSubmission(submission, "stress");
        }

        long end = System.currentTimeMillis();

        System.out.println("Invoke updateSubmission for 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>void removeSubmission(Submission submission, String operator) </code>.
     *
     */
    public void testRemoveSubmission() throws Exception {
        Submission submission = DBTestUtil.createSubmission();
        submission.setId(100);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            manager.removeSubmission(submission, "stress");
        }

        long end = System.currentTimeMillis();

        System.out.println("Invoke removeSubmission for 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>Submission getSubmission(long id) </code>.
     *
     */
    public void testGetSubmission() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            assertNotNull("Should not be null.", manager.getSubmission(1));
        }

        long end = System.currentTimeMillis();

        System.out.println("Invoke getSubmission for 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>Submission[] searchSubmissions(Filter filter) </code>.
     *
     */
    public void testSearchSubmissions() throws Exception {
        Submission[] s = null;
        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            s = manager.searchSubmissions(SubmissionFilterBuilder.createResourceIdFilter(1));
        }

        long end = System.currentTimeMillis();
        assertTrue("True is expected.", s.length > 0);
        System.out.println("Invoke searchSubmission for 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>void createSubmissionStatus(SubmissionStatus submissionStatus,
     * String operator) </code>.
     *
     * @throws Exception
     */
    public void testCreateSubmissionStatus() throws Exception {
        long total = 0;
        for (int i = 0; i < 20; i++) {
            SubmissionStatus status = DBTestUtil.createSubmissionStatus();

            long start = System.currentTimeMillis();
            manager.createSubmissionStatus(status, "stress");

            total += System.currentTimeMillis() - start;
        }

        System.out.println("Invoke createSubmissionStatus 20 times cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method
     * <p>
     * <code>void updateSubmissionStatus(SubmissionStatus submissionStatus, String operator) </code>.
     * </p>
     *
     */
    public void testUpdateSubmissionStatus() throws Exception {
        SubmissionStatus status = DBTestUtil.createSubmissionStatus();

        manager.createSubmissionStatus(status, "created");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            manager.updateSubmissionStatus(status, "stress");
        }
        long end = System.currentTimeMillis();

        System.out.println("Invoke updateSubmissionStatus 20 times cost " + (end - start) / 1000.0 + " seconds");
    }

    /**
     * Stress test for method
     * <code> void removeSubmissionStatus(SubmissionStatus submissionStatus, String operator) </code>
     *
     */
    public void testRemoveSubmissionStatus() throws Exception {
        SubmissionStatus status = DBTestUtil.createSubmissionStatus();
        status.setId(1000);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            manager.removeSubmissionStatus(status, "stress");
        }
        long end = System.currentTimeMillis();

        System.out.println("Invoke removeSubmissionStatus for 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>SubmissionStatus[] getAllSubmissionStatuses() </code>.
     *
     */
    public void testGetAllSubmissionStatuses() throws Exception {
        long total = 0;
        for (int i = 0; i < 20; i++) {
            long start = System.currentTimeMillis();

            SubmissionStatus[] s = manager.getAllSubmissionStatuses();

            total += System.currentTimeMillis() - start;

            assertTrue("True is expected.", s.length > 0);
        }

        System.out.println("Invoke getAllSubmissionStatus for 20 times cost " + total / 1000.0 + " seconds.");
    }
}
