/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.stresstests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.UploadFileSpec;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.log.log4j.Log4jLogFactory;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.servlet.MemberPhotoListServlet;
import com.topcoder.web.memberphoto.servlet.MemberPhotoRemovalServlet;
import com.topcoder.web.memberphoto.servlet.MemberPhotoUploadServlet;

/**
 * Tests both servlets for usage in concurrent environment.
 * @author orange_cloud, mumujava
 * @version 2.0
 */
public class ConcurrentTest extends TestCase {
    /**
     * How much times single operation need to be repeated.
     */
    private static final int REPEAT = 100;

    /**
     * Upload servlet to test.
     */
    private MemberPhotoUploadServlet uploadPreviewServlet;

    /**
     * Upload servlet to test.
     */
    private MemberPhotoUploadServlet uploadCropServlet;

    /**
     * Upload servlet to test.
     */
    private MemberPhotoUploadServlet uploadCommitServlet;

    /**
     * Remove servlet to test.
     */
    private MemberPhotoRemovalServlet removeServlet;

    /**
     * List servlet to test.
     */
    private MemberPhotoListServlet listServlet;

    /**
     * Instance to test servlets (to create natural request).
     */
    private ServletRunner runner;

    /**
     * Request used to remove photo from server.
     */
    private MockHttpServletRequest removeRequest;

    /**
     * Request used to upload photo on server.
     */
    private MockHttpServletRequest uploadPreviewRequest;

    /**
     * Request used to upload photo on server.
     */
    private MockHttpServletRequest uploadCropRequest;

    /**
     * Request used to upload photo on server.
     */
    private MockHttpServletRequest uploadCommitRequest;

    /**
     * Request used to list photos.
     */
    private MockHttpServletRequest listRequest;

    /**
     * This instance used to notify about errors in other threads. In nothing bad happened, it will be left
     * null.
     */
    private Throwable error;

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(ConcurrentTest.class);
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();

        runner = new ServletRunner();

        // initialize upload Preview servlet
        uploadPreviewServlet = new MemberPhotoUploadServlet();
        uploadPreviewServlet.setFileUploaderNamespace("MemoryFileUpload");
        uploadPreviewServlet.setMemberIdErrorName("b");
        uploadPreviewServlet.setMemberIdParameterName("member_id");
        uploadPreviewServlet.setMemberIdSessionKey("member_id_session_key");
        uploadPreviewServlet.setMemberPhotoManager(new StressMockMemberPhotoManager());
        uploadPreviewServlet.setPhotoImageErrorName("e");
        uploadPreviewServlet.setPhotoImageFormFileName("homer.png");
        uploadPreviewServlet.setPhotoImagePreviewDirectory("test_files/stresstests/preview");
        uploadPreviewServlet.setPhotoImageSubmittedDirectory("h");
        uploadPreviewServlet.setPreviewForwardUrl("i");
        uploadPreviewServlet.setPreviewPhotoImagePathName("j");
        uploadPreviewServlet.setTargetImageLeftCornerXParameterName("lx");
        uploadPreviewServlet.setTargetImageLeftCornerYParameterName("ly");
        uploadPreviewServlet.setTargetImageRightCornerXParameterName("rx");
        uploadPreviewServlet.setTargetImageRightCornerYParameterName("ry");
        uploadPreviewServlet.setPhotoImageCropDirectory("test_files/stresstests/crop");
        uploadPreviewServlet.setSubmittedRedirectUrl("l");
        uploadPreviewServlet.setTargetImageHeight(100);
        uploadPreviewServlet.setTargetImageWidth(100);
        uploadPreviewServlet.setValidationErrorForwardUrl("m");
        uploadPreviewServlet.setMaxFileSize(100000);
        uploadPreviewServlet.setSubmittedActionParameterName("sap");
        uploadPreviewServlet.setMaxFileExceededErrorName("maxfileError");
        inject(MemberPhotoUploadServlet.class, "log", uploadPreviewServlet,
            new Log4jLogFactory().createLog("MemberPhotoUploadServlet"));

        // initialize upload Crop servlet
        uploadCropServlet = new MemberPhotoUploadServlet();
        uploadCropServlet.setFileUploaderNamespace("MemoryFileUpload");
        uploadCropServlet.setMemberIdErrorName("b");
        uploadCropServlet.setMemberIdParameterName("member_id");
        uploadCropServlet.setMemberIdSessionKey("member_id_session_key");
        uploadCropServlet.setMemberPhotoManager(new StressMockMemberPhotoManager());
        uploadCropServlet.setPhotoImageErrorName("e");
        uploadCropServlet.setPhotoImageFormFileName("homer.png");
        uploadCropServlet.setPhotoImagePreviewDirectory("test_files/stresstests/preview");
        uploadCropServlet.setPhotoImageSubmittedDirectory("h");
        uploadCropServlet.setPreviewForwardUrl("i");
        uploadCropServlet.setPreviewPhotoImagePathName("j");
        uploadCropServlet.setTargetImageLeftCornerXParameterName("lx");
        uploadCropServlet.setTargetImageLeftCornerYParameterName("ly");
        uploadCropServlet.setTargetImageRightCornerXParameterName("rx");
        uploadCropServlet.setTargetImageRightCornerYParameterName("ry");
        uploadCropServlet.setPhotoImageCropDirectory("test_files/stresstests/crop");
        uploadCropServlet.setSubmittedRedirectUrl("l");
        uploadCropServlet.setTargetImageHeight(100);
        uploadCropServlet.setTargetImageWidth(100);
        uploadCropServlet.setValidationErrorForwardUrl("m");
        uploadCropServlet.setMaxFileSize(100000);
        uploadCropServlet.setSubmittedActionParameterName("sap");
        uploadCropServlet.setMaxFileExceededErrorName("maxfileError");
        uploadCropServlet.setMemberInformationRetriever(new MockMemberInformationRetriever());
        inject(MemberPhotoUploadServlet.class, "log", uploadCropServlet,
            new Log4jLogFactory().createLog("MemberPhotoUploadServlet"));

        // initialize upload Commit servlet
        uploadCommitServlet = new MemberPhotoUploadServlet();
        uploadCommitServlet.setFileUploaderNamespace("MemoryFileUpload");
        uploadCommitServlet.setMemberIdErrorName("b");
        uploadCommitServlet.setMemberIdParameterName("member_id");
        uploadCommitServlet.setMemberIdSessionKey("member_id_session_key");
        uploadCommitServlet.setMemberPhotoManager(new StressMockMemberPhotoManager());
        uploadCommitServlet.setPhotoImageErrorName("e");
        uploadCommitServlet.setPhotoImageFormFileName("homer.png");
        uploadCommitServlet.setPhotoImagePreviewDirectory("test_files/stresstests/preview");
        uploadCommitServlet.setPhotoImageSubmittedDirectory("h");
        uploadCommitServlet.setPreviewForwardUrl("i");
        uploadCommitServlet.setPreviewPhotoImagePathName("j");
        uploadCommitServlet.setTargetImageLeftCornerXParameterName("lx");
        uploadCommitServlet.setTargetImageLeftCornerYParameterName("ly");
        uploadCommitServlet.setTargetImageRightCornerXParameterName("rx");
        uploadCommitServlet.setTargetImageRightCornerYParameterName("ry");
        uploadCommitServlet.setPhotoImageCropDirectory("./test_files/stresstests/crop");
        uploadCommitServlet.setSubmittedRedirectUrl("l");
        uploadCommitServlet.setTargetImageHeight(100);
        uploadCommitServlet.setTargetImageWidth(100);
        uploadCommitServlet.setValidationErrorForwardUrl("m");
        uploadCommitServlet.setMaxFileSize(100000);
        uploadCommitServlet.setSubmittedActionParameterName("sap");
        uploadCommitServlet.setMaxFileExceededErrorName("maxfileError");
        uploadCommitServlet.setMemberInformationRetriever(new MockMemberInformationRetriever());
        inject(MemberPhotoUploadServlet.class, "log", uploadCommitServlet,
            new Log4jLogFactory().createLog("MemberPhotoUploadServlet"));

        // initialize remove servlet
        removeServlet = new MemberPhotoRemovalServlet();
        // removeServlet.setDocumentGenerator(new DocumentGenerator("some_namespace"));
        removeServlet.setEmailBodyTemplateFileName("test_files/stresstests/template.txt");
        removeServlet.setEmailSubject("b");
        removeServlet.setFromEmailAddress("a@b.c");
        removeServlet.setIsAdministratorSessionKey("is_admin");
        removeServlet.setMemberHasNoPhotoErrorName("c");
        removeServlet.setMemberIdParameterName("member_id");
        removeServlet.setMemberInformationRetriever(new MockMemberInformationRetriever());
        removeServlet.setMemberPhotoManager(new StressMockMemberPhotoManager());
        removeServlet.setNotAdministratorErrorName("e");
        removeServlet.setPhotoImageRemovedDirectory("test_files/stresstests/preview");
        removeServlet.setRemovalReasonParameterName("f");
        removeServlet.setSuccessResultForwardUrl("g");
        removeServlet.setValidationErrorForwardUrl("h");
        removeServlet.setDocumentGenerator(DocumentGenerator.getInstance());
        // removeServlet.setPhotoImageDirectory("./test_files/stresstests/remove");
        inject(MemberPhotoRemovalServlet.class, "log", removeServlet,
            new Log4jLogFactory().createLog("MemberPhotoRemovalServlet"));

        listServlet = new MemberPhotoListServlet();
        listServlet.setIsAdministratorSessionKey("is_admin");
        listServlet.setMemberIdParameterName("member_id");
        listServlet.setMemberPhotoManager(new StressMockMemberPhotoManager());
        listServlet.setNotAdministratorErrorName("e");
        listServlet.setPageNumberParameterName("number");
        listServlet.setPageSizeParameterName("size");
        listServlet.setPhotoListName("list");
        listServlet.setSuccessResultForwardUrl("g");
        listServlet.setValidationErrorForwardUrl("h");
        inject(MemberPhotoListServlet.class, "log", listServlet,
            new Log4jLogFactory().createLog("MemberPhotoListServlet"));

        // load configuration
        TestHelper.clearConfiguration();
        TestHelper.loadConfiguration();

        // initialize requests
        removeRequest = createRemoveRequest();
        uploadPreviewRequest = createUploadRequest();
        uploadCropRequest = createUploadRequest();
        uploadCommitRequest = createUploadRequest();
        listRequest = createListRequest();
        error = null;
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        runner.shutDown();
        new File("test_files/stresstests/preview/3.jpeg").delete();
        // new File("test_files/stresstests/crop/3.jpeg").delete();
        super.tearDown();
        com.topcoder.web.memberphoto.servlet.TestHelper.deldir("h");
    }

    /**
     * <p>
     * Injects the value.
     * </p>
     * @param clazz the class of the object
     * @param name the name of the field
     * @param obj the object to inject to
     * @param value the value of the field
     * @throws Exception if anything is wrong
     */
    public static void inject(Class<?> clazz, String name, Object obj, Object value) throws Exception {
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }

    /**
     * Tests servlets when several requests try to access same photo at the same time.
     * @throws Throwable if any error occur
     */
    public void testUploadPreviewTask() throws Throwable {
        // initialize threads
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(new UploadPreviewTask()));
        }

        // start them
        for (Thread t : threads) {
            t.start();
        }

        // wait until last finished
        for (Thread t : threads) {
            t.join();
        }

        // check if error was noticed
        if (error != null) {
            throw error;
        }
    }

    /**
     * Tests servlets when several requests try to access same photo at the same time.
     * @throws Throwable if any error occur
     */
    public void testUploadCropTask() throws Throwable {
        // initialize threads
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(new UploadCropTask()));
        }

        // start them
        for (Thread t : threads) {
            t.start();
        }

        // wait until last finished
        for (Thread t : threads) {
            t.join();
        }

        // check if error was noticed
        if (error != null) {
            throw error;
        }
    }

    /**
     * Tests servlets when several requests try to access same photo at the same time.
     * @throws Throwable if any error occur
     */
    public void testUploadCommitTask() throws Throwable {
        // initialize threads
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(new UploadCommitTask()));
        }

        // start them
        for (Thread t : threads) {
            t.start();
        }

        // wait until last finished
        for (Thread t : threads) {
            t.join();
        }

        // check if error was noticed
        if (error != null) {
            throw error;
        }
    }

    /**
     * Tests servlets when several requests try to access same photo at the same time.
     * @throws Throwable if any error occur
     */
    public void testListTask() throws Throwable {
        // initialize threads
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(new ListTask()));
        }

        // start them
        for (Thread t : threads) {
            t.start();
        }

        // wait until last finished
        for (Thread t : threads) {
            t.join();
        }

        // check if error was noticed
        if (error != null) {
            throw error;
        }
    }

    /**
     * Tests servlets when several requests try to access same photo at the same time.
     * @throws Throwable if any error occur
     */
    public void testRemoveTask() throws Throwable {
        // initialize threads
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(new RemoveTask()));
        }

        // start them
        for (Thread t : threads) {
            t.start();
        }

        // wait until last finished
        for (Thread t : threads) {
            t.join();
        }

        // check if error was noticed
        if (error != null) {
            throw error;
        }
    }

    /**
     * Reads content of given stream.
     * @param inputStream stream to read data from
     * @return data from stream
     * @throws IOException if data can't be read for some reason
     */
    public static byte[] readContent(InputStream inputStream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;

        while ((len = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }

        inputStream.close();
        output.close();

        return output.toByteArray();
    }

    /**
     * Creates and initializes http request to upload photo on the server.
     * @return request created
     * @throws Exception if it occurs deeper
     */
    public MockHttpServletRequest createUploadRequest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        request.setContentType("multipart/form-data; boundary=--HttpUnit-part0-aSgQ2M");
        request.addHeader("Content-type", "multipart/form-data; boundary=--HttpUnit-part0-aSgQ2M");

        // create the webRequest
        PostMethodWebRequest webRequest = new PostMethodWebRequest("http://localhost:8080/");
        webRequest.setMimeEncoded(true);
        webRequest.setParameter("homer.png", new UploadFileSpec[] {new UploadFileSpec(new File(
            "test_files/stresstests/homer.png")) });

        ServletUnitClient client = runner.newClient();
        InvocationContext ic = client.newInvocation(webRequest);

        byte[] data = readContent(ic.getRequest().getInputStream());
        request.setContent(data);

        request.setParameter("submitted_flag", "true");
        request.setParameter("member_id", "3");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", 3l);
        request.setSession(session);

        return request;
    }

    /**
     * Creates and initializes request to remove photo from the server.
     * @return request created
     * @throws Exception when it occurs deeper
     */
    public MockHttpServletRequest createRemoveRequest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        request.setParameter("member_id", "3");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin", true);
        request.setSession(session);

        return request;
    }

    /**
     * Creates and initializes request to list photos.
     * @return request created
     * @throws Exception when it occurs deeper
     */
    public MockHttpServletRequest createListRequest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        request.setParameter("member_id", "3");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin", true);
        request.setSession(session);

        return request;
    }

    /**
     * This class created to call upload servlet repeatedly from another thread. It's not static and can
     * access owner's
     * data.
     * @author orange_cloud
     * @version 1.0
     */
    public class UploadPreviewTask implements Runnable {
        /**
         * Calls upload servlet REPEAT times, each time response (forward url) checked. Errors reported
         * through error
         * variable of owner object.
         */
        public void run() {
            try {
                for (int i = 0; i < REPEAT; ++i) {
                    MockHttpServletResponse response = new MockHttpServletResponse();
                    // if the "sap" param is not set, preview should be default
                    // uploadPreviewRequest.setParameter("sap", "preview");
                    uploadPreviewServlet.doPost(uploadPreviewRequest, response);
                    assertEquals("forward url", "i", response.getForwardedUrl());
                }
            } catch (Throwable e) {
                error = e;
            }
        }
    }

    /**
     * This class created to call upload servlet repeatedly from another thread. It's not static and can
     * access owner's
     * data.
     * @author orange_cloud
     * @version 1.0
     */
    public class UploadCropTask implements Runnable {
        /**
         * Calls upload servlet REPEAT times, each time response (forward url) checked. Errors reported
         * through error
         * variable of owner object.
         */
        public void run() {
            try {
                for (int i = 0; i < REPEAT; ++i) {
                    MockHttpServletResponse response = new MockHttpServletResponse();
                    uploadCropRequest.setParameter("sap", "crop");
                    uploadCropRequest.setParameter("lx", "1");
                    uploadCropRequest.setParameter("ly", "5");
                    uploadCropRequest.setParameter("rx", "1");
                    uploadCropRequest.setParameter("ry", "5");
                    uploadCropServlet.doPost(uploadCropRequest, response);
                    assertEquals("forward url", "l", response.getRedirectedUrl());
                }
            } catch (Throwable e) {
                error = e;
            }
        }
    }

    /**
     * This class created to call upload servlet repeatedly from another thread. It's not static and can
     * access owner's
     * data.
     * @author orange_cloud
     * @version 1.0
     */
    public class UploadCommitTask implements Runnable {
        /**
         * Calls upload servlet REPEAT times, each time response (forward url) checked. Errors reported
         * through error
         * variable of owner object.
         */
        public void run() {
            try {
                for (int i = 0; i < REPEAT; ++i) {
                    MockHttpServletResponse response = new MockHttpServletResponse();
                    uploadCommitRequest.setParameter("sap", "commit");
                    uploadCommitServlet.doPost(uploadCommitRequest, response);
                    assertEquals("forward url", "l", response.getRedirectedUrl());
                }
            } catch (Throwable e) {
                error = e;
            }
        }
    }

    /**
     * This class created to call remove servlet repeatedly from another thread. It's not static and can
     * access owner's
     * data.
     * @author orange_cloud
     * @version 1.0
     */
    public class RemoveTask implements Runnable {
        /**
         * Calls remove servlet REPEAT times, each time response (forward url) checked. Errors reported
         * through error
         * variable of owner object.
         */
        public void run() {
            try {
                for (int i = 0; i < REPEAT; ++i) {
                    MockHttpServletResponse response = new MockHttpServletResponse();
                    removeServlet.doPost(removeRequest, response);
                    assertEquals("forward url", "g", response.getForwardedUrl());
                }
            } catch (Throwable e) {
                error = e;
            }
        }
    }

    /**
     * This class created to call list servlet repeatedly from another thread. It's not static and can access
     * owner's
     * data.
     * @author orange_cloud
     * @version 1.0
     */
    public class ListTask implements Runnable {
        /**
         * Calls list servlet REPEAT times, each time response (forward url) checked. Errors reported through
         * error
         * variable of owner object.
         */
        @SuppressWarnings("unchecked")
        public void run() {
            try {
                for (int i = 0; i < REPEAT; ++i) {
                    MockHttpServletResponse response = new MockHttpServletResponse();
                    listServlet.doPost(listRequest, response);
                    assertEquals("forward url", "g", response.getForwardedUrl());
                    List<MemberImage> attribute = (List<MemberImage>) listRequest.getAttribute("list");
                    assertEquals("The size should be one", 1, attribute.size());
                    assertEquals("The size should be one", "created by", attribute.get(0).getCreatedBy());
                }
            } catch (Throwable e) {
                error = e;
            }
        }
    }
}
