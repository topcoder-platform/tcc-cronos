/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.UploadFileSpec;

import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.springframework.mock.web.MockHttpServletRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Provide some utilities methods for unit tests.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {
    /**
     * <p>
     * Constant for test files location.
     * </p>
     */
    private static final String TEST_FILES = "test_files/";

    /**
     * <p>
     * Represents the url of the web server for testing. e.g. http://localhost:8080. The value can be
     * arbitrary since we do not do the real job in the web server.
     * </p>
     */
    private static final String URL = "http://localhost:8080/";

    /**
     * <p>
     * Represents the value of content type for testing.
     * </p>
     */
    private static final String CONTENT_TYPE = "multipart/form-data; boundary=--HttpUnit-part0-aSgQ2M";

    /**
     * <p>
     * Represents the <code>ServletRunner</code> instance for testing.
     * </p>
     */
    private static ServletRunner runner = null;

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Get bean with the specified configuration file name.
     * </p>
     * @param configName the configuration name
     * @return the removal bean
     */
    public static MemberPhotoRemovalServlet genRemovalServletBean(String configName) {
        Resource rs = new ClassPathResource(TEST_FILES + configName + ".xml");
        BeanFactory factory = new XmlBeanFactory(rs);
        MemberPhotoRemovalServlet bean = (MemberPhotoRemovalServlet) factory.getBean("removalServlet");

        return bean;
    }

    /**
     * <p>
     * Get bean with the specified configuration file name.
     * </p>
     * @return the removal bean
     */
    public static MemberPhotoRemovalServlet genRemovalServletBean() {
        return genRemovalServletBean("removal");
    }

    /**
     * <p>
     * Get bean with the specified configuration file name.
     * </p>
     * @param configName the configuration name
     * @return the removal bean
     */
    public static MemberPhotoListServlet genListServletBean(String configName) {
        Resource rs = new ClassPathResource(TEST_FILES + configName + ".xml");
        BeanFactory factory = new XmlBeanFactory(rs);
        MemberPhotoListServlet bean = (MemberPhotoListServlet) factory.getBean("listServlet");

        return bean;
    }

    /**
     * <p>
     * Get bean with the specified configuration file name.
     * </p>
     * @return the removal bean
     */
    public static MemberPhotoListServlet genListServletBean() {
        return genListServletBean("list");
    }

    /**
     * <p>
     * Get bean with the specified configuration file name.
     * </p>
     * @param configName the configuration name
     * @return the upload bean
     */
    public static MemberPhotoUploadServlet genUploadServletBean(String configName) {
        Resource rs = new ClassPathResource(TEST_FILES + configName + ".xml");
        BeanFactory factory = new XmlBeanFactory(rs);
        MemberPhotoUploadServlet bean = (MemberPhotoUploadServlet) factory.getBean("uploadServlet");

        return bean;
    }

    /**
     * <p>
     * Get bean with the specified configuration file name.
     * </p>
     * @return the upload bean
     */
    public static MemberPhotoUploadServlet genUploadServletBean() {
        return genUploadServletBean("upload");
    }

    /**
     * <p>
     * Read content and convert input stream to byte array.
     * </p>
     * @param inputStream the input stream
     * @return byte array
     */
    public static byte[] readContent(InputStream inputStream) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;

            while ((len = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }

            inputStream.close();
            output.close();

            return output.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * <p>
     * Prepare request.
     * </p>
     * @return request
     */
    public static MockHttpServletRequest prepareRequest() {
        try {
            Map<String, List<UploadFileSpec>> files = new HashMap<String, List<UploadFileSpec>>();

            List<UploadFileSpec> uploads1 = new ArrayList<UploadFileSpec>();
            uploads1.add(new UploadFileSpec(new File(TEST_FILES + "test.jpg")));

            List<UploadFileSpec> uploads2 = new ArrayList<UploadFileSpec>();
            uploads2.add(new UploadFileSpec(new File(TEST_FILES + "test.png")));

            List<UploadFileSpec> uploads3 = new ArrayList<UploadFileSpec>();
            uploads3.add(new UploadFileSpec(new File(TEST_FILES + "test.gif")));

            List<UploadFileSpec> uploads4 = new ArrayList<UploadFileSpec>();
            uploads3.add(new UploadFileSpec(new File(TEST_FILES + "test.jp2")));

            files.put("test.jpg", uploads1);
            files.put("test.png", uploads2);
            files.put("test.gif", uploads3);
            files.put("test.jp2", uploads4);

            // create the webRequest
            PostMethodWebRequest webRequest = new PostMethodWebRequest(URL);
            webRequest.setMimeEncoded(true);

            for (Map.Entry<String, List<UploadFileSpec>> entry : files.entrySet()) {
                List<UploadFileSpec> value = entry.getValue();
                webRequest.setParameter(entry.getKey(),
                    (UploadFileSpec[]) value.toArray(new UploadFileSpec[value.size()]));
            }

            // run this webRequest
            if (runner == null) {
                runner = new ServletRunner();
            }

            ServletUnitClient client = runner.newClient();
            InvocationContext ic = client.newInvocation(webRequest);

            return new MyMockHttpServletRequest(readContent(ic.getRequest().getInputStream()), CONTENT_TYPE);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * <p>
     * Delete directory.
     * </p>
     * @param filepath the file path
     */
    public static void deldir(String filepath) {
        File f = new File(filepath);

        if (f.exists() && f.isDirectory()) {
            if (f.listFiles().length == 0) {
                f.delete();
            } else {
                File[] delFile = f.listFiles();
                int i = f.listFiles().length;

                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        deldir(delFile[j].getAbsolutePath());
                    }

                    delFile[j].delete();
                }
            }

            deldir(filepath);
        }
    }

    /**
     * <p>
     * Shut down servlet runner thread.
     * </p>
     */
    public static void shutDownRunner() {
        if (runner != null) {
            runner.shutDown();
        }
    }
}
