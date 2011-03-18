/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.accuracytests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.UploadFileSpec;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.topcoder.util.config.ConfigManager;

/**
 * The accuracy test helper.
 * @author extra, sokol
 * @version 2.0
 */
class AccuracyTestHelper {

    /**
     * The base path for failure tests.
     */
    static final String BASE_PATH = System.getProperty("user.dir") + "/" + "test_files" + "/"
        + "accuracytests" + "/";

    /**
     * The url.
     */
    private static final String URL = "http://localhost:8888/";

    /**
     * The runner.
     */
    private static ServletRunner runner = new ServletRunner();

    /**
     * The content type.
     */
    private static final String CONTENT_TYPE = "multipart/form-data; boundary=--HttpUnit-part0-aSgQ2M";

    /**
     * The buffer size.
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * The test folder.
     */
    private static final String TEST_FOLDER = "test_files/accuracytests/";

    /**
     * The constructor.
     */
    private AccuracyTestHelper() {
    }

    /**
     * Clear the namespaces.
     * @throws Exception if any error occurs
     */
    static void clearNameSpaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator<?> iterator = cm.getAllNamespaces(); iterator.hasNext();) {
            String namespace = (String) iterator.next();
            cm.removeNamespace(namespace);
        }
    }

    /**
     * Add the fileName to config manager.
     * @param fileName the file name
     * @throws Exception to JUnit
     */
    static void addConfigFile(String fileName) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(new File(TEST_FOLDER + fileName).getCanonicalPath());
    }

    /**
     * <p>
     * Prepare request.
     * </p>
     * @return request
     */
    static MockHttpServletRequest prepareRequest() {
        try {
            // create the webRequest
            PostMethodWebRequest webRequest = new PostMethodWebRequest(URL);
            webRequest.setMimeEncoded(true);
            webRequest.setParameter("accuracytests/topcoder.jpeg", new UploadFileSpec[] {new UploadFileSpec(
                new File(TEST_FOLDER + "topcoder.jpeg")) });
            webRequest.setParameter("accuracytests/topcoder.png", new UploadFileSpec[] {new UploadFileSpec(
                new File(TEST_FOLDER + "topcoder.png")) });
            webRequest.setParameter("accuracytests/topcoder.gif", new UploadFileSpec[] {new UploadFileSpec(
                new File(TEST_FOLDER + "topcoder.gif")) });
            // not image type
            webRequest.setParameter("accuracytests/template.txt", new UploadFileSpec[] {new UploadFileSpec(
                new File(TEST_FOLDER + "template.txt")) });
            ServletUnitClient client = runner.newClient();
            InvocationContext ic = client.newInvocation(webRequest);
            return new MockHttpServletRequestImpl(readContent(ic.getRequest().getInputStream()), CONTENT_TYPE);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * <p>
     * Read content and convert input stream to byte array.
     * </p>
     * @param inputStream the input stream
     * @return the content
     */
    static byte[] readContent(InputStream inputStream) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int count = 0;
            while ((count = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
            output.close();
            return output.toByteArray();
        } catch (IOException e) {
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * Delete directory.
     * @param filepath the file path
     */
    static void deletedir(String filepath) {
        File f = new File(filepath);
        if (f.exists() && f.isDirectory()) {
            if (f.listFiles().length == 0) {
                f.delete();
            } else {
                File[] delFile = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        deletedir(delFile[j].getAbsolutePath());
                    }
                    delFile[j].delete();
                }
            }
            deletedir(filepath);
        }
    }

    /**
     * <p>
     * Retrieves bean from Spring context.
     * </p>
     * @param <T> the type of bean
     * @param configName the configuration file name
     * @param beanName the bean name
     * @return bean of type T from Spring context
     */
    static <T> T getServletBean(String configName, String beanName) {
        ApplicationContext context = new FileSystemXmlApplicationContext(BASE_PATH + configName);
        return (T) context.getBean(beanName);
    }
}
