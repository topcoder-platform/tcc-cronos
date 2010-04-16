/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.UploadedDocument;

/**
 * <p>
 * Unit tests for <code>FileUploadAttachContestFileAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FileUploadAttachContestFileActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private FileUploadAttachContestFileAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/mockFileUploadAttachContestFileAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof FileUploadAttachContestFileAction);

        TestHelper.injectSessionMapIntoProxy(proxy);

        return proxy;
    }

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        TestHelper.cleanupEnvironment();
        instance = new MockFileUploadAttachContestFileAction();
        instance.prepare();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        TestHelper.cleanupEnvironment();
        instance = null;
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testInheritance() throws Exception {
        TestHelper.assertSuperclass(instance.getClass().getSuperclass(), ContestAction.class);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for executeAction. The contest file should be attached successfully.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Accuracy1() throws Exception {
        instance.executeAction();

        // make sure contest file was attached properly
        UploadedDocument doc = MockContestServiceFacade.getStudioCompetitionDocument(1);
        assertNotNull("document should have been attached", doc);
        assertEquals("attachment file description is wrong", "test file", doc.getDescription());
        assertEquals("attachment file name is wrong", "test.txt", doc.getFileName());
        assertEquals("attachment file contest id is wrong", 1, doc.getContestId());
        assertEquals("attachment file document type ID is wrong", 5, doc.getDocumentTypeId());
    }

    /**
     * Failure test for executeAction. The contest wasn't found, so ContestNotFoundException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Failure1() throws Exception {
        try {
            instance.setContestId(100);
            instance.executeAction();
            fail("ContestNotFoundException is expected");
        } catch (ContestNotFoundException e) {
            // success
        }
    }

    /**
     * Failure test for executeAction. The <code>contestServiceFacade</code> hasn't been injected, so
     * IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Failure2() throws Exception {
        try {
            new DeleteDocumentContestAction().executeAction();
            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Accuracy test for getContestId. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestId_Accuracy() {
        instance = new FileUploadAttachContestFileAction();
        assertEquals("incorrect default value", 0, instance.getContestId());
        instance.setContestId(5);
        assertEquals("incorrect value after setting", 5, instance.getContestId());
    }

    /**
     * Accuracy Test for setContestId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestId_Accuracy() {
        instance.setContestId(5);
        assertEquals("incorrect value after setting", 5, instance.getContestId());
    }

    /**
     * Failure test for setContestId method in struts environment. The value for the setter is 0, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestId_Zero() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        FileUploadAttachContestFileAction action = (FileUploadAttachContestFileAction) proxy.getAction();

        TestHelper.getFieldValues().put("contestId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestId", "contestId must be > 0");
    }

    /**
     * Accuracy test for getContestFile. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestFile_Accuracy() {
        instance = new FileUploadAttachContestFileAction();
        assertNull("incorrect default value", instance.getContestFile());
        File file = new File("test_files/test.txt");
        instance.setContestFile(file);
        assertSame("incorrect value after setting", file, instance.getContestFile());
    }

    /**
     * Accuracy Test for setContestFile. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestFile_Accuracy() {
        File file = new File("test_files/test.txt");
        instance.setContestFile(file);
        assertSame("incorrect value after setting", file, instance.getContestFile());
    }

    /**
     * Accuracy test for getContestFileName. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestFileName_Accuracy() {
        instance = new FileUploadAttachContestFileAction();
        assertEquals("incorrect default value", null, instance.getContestFileName());
        instance.setContestFileName("a");
        assertEquals("incorrect value after setting", "a", instance.getContestFileName());
    }

    /**
     * Accuracy Test for setContestFile. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestFileName_Accuracy() {
        instance.setContestFileName("a");
        assertEquals("incorrect value after setting", "a", instance.getContestFileName());
    }

    /**
     * Accuracy test for getContestFileContentType. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestFileContentType_Accuracy() {
        instance = new FileUploadAttachContestFileAction();
        assertEquals("incorrect default value", null, instance.getContestFileContentType());
        instance.setContestFileContentType("a");
        assertEquals("incorrect value after setting", "a", instance.getContestFileContentType());
    }

    /**
     * Accuracy Test for setContestFile. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestFileContentType_Accuracy() {
        instance.setContestFileContentType("a");
        assertEquals("incorrect value after setting", "a", instance.getContestFileContentType());
    }

    /**
     * Accuracy test for getContestFileDescription. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestFileDescription_Accuracy() {
        instance = new FileUploadAttachContestFileAction();
        assertEquals("incorrect default value", null, instance.getContestFileDescription());
        instance.setContestFileDescription("a");
        assertEquals("incorrect value after setting", "a", instance.getContestFileDescription());
    }

    /**
     * Accuracy Test for setContestFile. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestFileDescription_Accuracy() {
        instance.setContestFileDescription("a");
        assertEquals("incorrect value after setting", "a", instance.getContestFileDescription());
    }

    /**
     * Failure test for setContestFileDescription method in struts environment. The value for the setter is
     * null, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestFileDescription_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        FileUploadAttachContestFileAction action = (FileUploadAttachContestFileAction) proxy.getAction();

        TestHelper.getFieldValues().put("contestFileDescription", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestFileDescription",
            "contestFileDescription cannot be null and must be between 1 and 4096 characters");
    }

    /**
     * Failure test for setContestFileDescription method in struts environment. The value for the setter is
     * empty, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestFileDescription_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        FileUploadAttachContestFileAction action = (FileUploadAttachContestFileAction) proxy.getAction();

        TestHelper.getFieldValues().put("contestFileDescription", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestFileDescription",
            "contestFileDescription cannot be null and must be between 1 and 4096 characters");
    }

    /**
     * Failure test for setContestFileDescription method in struts environment. The value for the setter is
     * too long, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestFileDescription_TooLong() throws Exception {
        final int maxLength = 4096;
        ActionProxy proxy = prepareActionProxy();
        FileUploadAttachContestFileAction action = (FileUploadAttachContestFileAction) proxy.getAction();

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < maxLength + 1; ++i) {
            s.append("a");
        }

        TestHelper.getFieldValues().put("contestFileDescription", s.toString());

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestFileDescription",
            "contestFileDescription cannot be null and must be between 1 and 4096 characters");
    }

    /**
     * Accuracy test for getDocumentTypeId. Verifies the returned value is correct.
     */
    @Test
    public void test_getDocumentTypeId_Accuracy() {
        instance = new FileUploadAttachContestFileAction();
        assertEquals("incorrect default value", 0, instance.getDocumentTypeId());
        instance.setDocumentTypeId(5);
        assertEquals("incorrect value after setting", 5, instance.getDocumentTypeId());
    }

    /**
     * Accuracy Test for setDocumentTypeId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setDocumentTypeId_Accuracy() {
        instance.setDocumentTypeId(5);
        assertEquals("incorrect value after setting", 5, instance.getDocumentTypeId());
    }

    /**
     * Failure test for setDocumentTypeId method in struts environment. The value for the setter is less than
     * 0, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setDocumentTypeId_LessThanZero() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        FileUploadAttachContestFileAction action = (FileUploadAttachContestFileAction) proxy.getAction();

        TestHelper.getFieldValues().put("documentTypeId", -1);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "documentTypeId", "documentTypeId must be >= 0 and < 25");
    }

    /**
     * Failure test for setDocumentTypeId method in struts environment. The value for the setter not less than
     * 25, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setDocumentTypeId_NotLessThan25() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        FileUploadAttachContestFileAction action = (FileUploadAttachContestFileAction) proxy.getAction();

        TestHelper.getFieldValues().put("documentTypeId", 25);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "documentTypeId", "documentTypeId must be >= 0 and < 25");
    }

    /**
     * Accuracy test for getMimeTypeRetriever. Verifies the returned value is correct.
     */
    @Test
    public void test_getMimeTypeRetriever_Accuracy() {
        instance = new FileUploadAttachContestFileAction();
        assertNull("incorrect default value", instance.getMimeTypeRetriever());
        MimeTypeRetriever val = new MimeTypeRetriever();
        instance.setMimeTypeRetriever(val);
        assertEquals("incorrect value after setting", val, instance.getMimeTypeRetriever());
    }

    /**
     * Accuracy test for setMimeTypeRetriever. Verifies the assigned value is correct.
     */
    @Test
    public void test_setMimeTypeRetriever_Accuracy() {
        MimeTypeRetriever val = new MimeTypeRetriever();
        instance.setMimeTypeRetriever(val);
        assertEquals("incorrect value after setting", val, instance.getMimeTypeRetriever());
    }

    /**
     * Failure test for setMimeTypeRetriever. The argument is null, so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setMimeTypeRetriever_Failure1() throws Exception {
        try {
            instance.setMimeTypeRetriever(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
