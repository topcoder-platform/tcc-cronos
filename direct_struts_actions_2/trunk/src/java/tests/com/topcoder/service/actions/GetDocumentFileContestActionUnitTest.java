/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.studio.UploadedDocument;

/**
 * <p>
 * Unit tests for <code>GetDocumentFileContestAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetDocumentFileContestActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private GetDocumentFileContestAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/mockGetDocumentFileContestAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof GetDocumentFileContestAction);

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
        instance = new MockGetDocumentFileContestAction();
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
        TestHelper.assertSuperclass(instance.getClass().getSuperclass(), GetDocumentsContestAction.class);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for performLogic. The uploaded file should be added to the action successfully.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_performLogic_Accuracy1() throws Exception {
        try {
            UploadedDocument doc = new UploadedDocument();
            doc.setDocumentId(1);
            doc.setContestId(1);
            doc.setFileName("test.txt");
            doc.setPath("test_files");
            doc.setMimeTypeId(new MimeTypeRetriever().getMimeTypeIdFromFileName("test_files/test.txt"));
            List<UploadedDocument> docUploads = new ArrayList<UploadedDocument>();
            docUploads.add(doc);

            instance.performLogic(docUploads);

            String expectedContents = TestHelper.getFileContents("test_files/test.txt");

            // make sure file was added
            assertEquals("content type is wrong", "text/plain", instance.getContentType());
            assertEquals("content length is wrong", expectedContents.length(), instance.getContentLength());
            assertEquals("content disposition is wrong", "attachment;filename=test.txt", instance
                .getContentDisposition());

            // make sure input stream was set correctly and file contents are correct
            StringWriter writer = new StringWriter();
            IOUtils.copy(instance.getInputStream(), writer);
            String actualContents = writer.toString();
            assertEquals("input stream is wrong", expectedContents, actualContents);
        } finally {
            IOUtils.closeQuietly(instance.getInputStream());
        }
    }

    /**
     * Accuracy test for performLogic. The argument is null, but no exception should be thrown since the
     * documents should be ignored.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_performLogic_Accuracy2() throws Exception {
        instance.performLogic(null);
    }

    /**
     * Accuracy test for performLogic. The argument is empty list, but no exception should be thrown since the
     * documents should be ignored.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_performLogic_Accuracy3() throws Exception {
        instance.performLogic(new ArrayList<UploadedDocument>());
    }

    /**
     * Accuracy test for performLogic. The document could not be found, but no exception should be thrown.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_performLogic_Accuracy4() throws Exception {
        UploadedDocument doc = new UploadedDocument();
        doc.setDocumentId(500);
        doc.setContestId(1);
        doc.setFileName("test.txt");
        doc.setPath("test_files");
        doc.setMimeTypeId(new MimeTypeRetriever().getMimeTypeIdFromFileName("test_files/test.txt"));
        List<UploadedDocument> docUploads = new ArrayList<UploadedDocument>();
        docUploads.add(doc);

        instance.performLogic(docUploads);
    }

    /**
     * Accuracy test for performLogic. The uploaded file should be added to the action successfully.
     * In this test, the documents list contains null elements, but this shouldn't cause any exception.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_performLogic_Accuracy5() throws Exception {
        try {
            UploadedDocument doc = new UploadedDocument();
            doc.setDocumentId(1);
            doc.setContestId(1);
            doc.setFileName("test.txt");
            doc.setPath("test_files");
            doc.setMimeTypeId(new MimeTypeRetriever().getMimeTypeIdFromFileName("test_files/test.txt"));
            List<UploadedDocument> docUploads = new ArrayList<UploadedDocument>();

            // add null element for test
            docUploads.add(null);

            docUploads.add(doc);

            instance.performLogic(docUploads);

            String expectedContents = TestHelper.getFileContents("test_files/test.txt");

            // make sure file was added
            assertEquals("content type is wrong", "text/plain", instance.getContentType());
            assertEquals("content length is wrong", expectedContents.length(), instance.getContentLength());
            assertEquals("content disposition is wrong", "attachment;filename=test.txt", instance
                .getContentDisposition());

            // make sure input stream was set correctly and file contents are correct
            StringWriter writer = new StringWriter();
            IOUtils.copy(instance.getInputStream(), writer);
            String actualContents = writer.toString();
            assertEquals("input stream is wrong", expectedContents, actualContents);
        } finally {
            IOUtils.closeQuietly(instance.getInputStream());
        }
    }

    /**
     * Failure test for performLogic. The file wasn't found, so FileNotFoundException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_performLogic_Failure1() throws Exception {
        try {
            UploadedDocument doc = new UploadedDocument();
            doc.setDocumentId(1);
            doc.setContestId(1);
            doc.setFileName("fake.txt");
            doc.setPath("test_files");
            doc.setMimeTypeId(new MimeTypeRetriever().getMimeTypeIdFromFileName("test_files/test.txt"));
            List<UploadedDocument> docUploads = new ArrayList<UploadedDocument>();
            docUploads.add(doc);

            instance.performLogic(docUploads);
            fail("FileNotFoundException is expected");
        } catch (FileNotFoundException e) {
            // success
        }
    }

    /**
     * Failure test for performLogic. The passed list has objects of wrong type, so IllegalArgumentException
     * is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_performLogic_Failure2() throws Exception {
        try {
            List<Object> docUploads = new ArrayList<Object>();
            docUploads.add(new Object());

            instance.performLogic(docUploads);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for getMimeTypeRetriever. Verifies the returned value is correct.
     */
    @Test
    public void test_getMimeTypeRetriever_Accuracy() {
        instance = new GetDocumentFileContestAction();
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

    /**
     * Accuracy test for getDocumentId. Verifies the returned value is correct.
     */
    @Test
    public void test_getDocumentId_Accuracy() {
        instance = new GetDocumentFileContestAction();
        assertEquals("incorrect default value", 0, instance.getDocumentId());
        instance.setDocumentId(1);
        assertEquals("incorrect value after setting", 1, instance.getDocumentId());
    }

    /**
     * Accuracy Test for setDocumentId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setDocumentId_Accuracy() {
        instance.setDocumentId(1);
        assertEquals("incorrect value after setting", 1, instance.getDocumentId());
    }

    /**
     * Failure test for setDocumentId method in struts environment. The value for the setter is 0, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setDocumentId_Zero() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        GetDocumentFileContestAction action = (GetDocumentFileContestAction) proxy.getAction();

        TestHelper.getFieldValues().put("documentId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "documentId", "documentId must be > 0");
    }

    /**
     * Accuracy test for getInputStream. Verifies the returned value is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getInputStream_Accuracy() throws Exception {
        assertNull("incorrect default value", instance.getInputStream());
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(new File("test_files/test.txt"));
            instance.setInputStream(stream);
            assertEquals("incorrect value after setting", stream, instance.getInputStream());
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    /**
     * Accuracy Test for setInputStream. Verifies the assigned value is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setInputStream_Accuracy() throws Exception {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(new File("test_files/test.txt"));
            instance.setInputStream(stream);
            assertEquals("incorrect value after setting", stream, instance.getInputStream());
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    /**
     * Accuracy test for getContestId. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestId_Accuracy() {
        instance = new GetDocumentFileContestAction();
        assertEquals("incorrect default value", 0, instance.getContestId());
        instance.setContestId(1);
        assertEquals("incorrect value after setting", 1, instance.getContestId());
    }

    /**
     * Accuracy Test for setContestId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestId_Accuracy() {
        MimeTypeRetriever val = new MimeTypeRetriever();
        instance.setMimeTypeRetriever(val);
        assertEquals("incorrect value after setting", val, instance.getMimeTypeRetriever());
    }

    /**
     * Accuracy test for getContentType. Verifies the returned value is correct.
     */
    @Test
    public void test_getContentType_Accuracy() {
        assertEquals("incorrect default value", null, instance.getContentType());
        instance.setContentType("a");
        assertEquals("incorrect value after setting", "a", instance.getContentType());
    }

    /**
     * Accuracy Test for setContentType. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContentType_Accuracy() {
        instance.setContentType("a");
        assertEquals("incorrect value after setting", "a", instance.getContentType());
    }

    /**
     * Accuracy test for getContentLength. Verifies the returned value is correct.
     */
    @Test
    public void test_getContentLength_Accuracy() {
        assertEquals("incorrect default value", 0, instance.getContentLength());
        instance.setContentLength(2);
        assertEquals("incorrect value after setting", 2, instance.getContentLength());
    }

    /**
     * Accuracy Test for setContentLength. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContentLength_Accuracy() {
        instance.setContentLength(2);
        assertEquals("incorrect value after setting", 2, instance.getContentLength());
    }

    /**
     * Accuracy test for getContentDisposition. Verifies the returned value is correct.
     */
    @Test
    public void test_getContentDisposition_Accuracy() {
        assertEquals("incorrect default value", null, instance.getContentDisposition());
        instance.setContentDisposition("a");
        assertEquals("incorrect value after setting", "a", instance.getContentDisposition());
    }

    /**
     * Accuracy Test for setContentDisposition. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContentDisposition_Accuracy() {
        instance.setContentDisposition("a");
        assertEquals("incorrect value after setting", "a", instance.getContentDisposition());
    }

}
