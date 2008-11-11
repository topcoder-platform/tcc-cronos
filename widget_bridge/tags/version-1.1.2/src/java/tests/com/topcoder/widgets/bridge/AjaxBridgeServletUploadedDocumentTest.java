/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.widgets.bridge;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import junit.framework.TestCase;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.protocol.UploadFileSpec;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONDataAccessTypeException;
import com.topcoder.json.object.JSONInvalidKeyException;
import com.topcoder.json.object.JSONObject;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.servlet.request.MockHttpServletRequest;

/**
 * <p>
 * Tests functionality of <code>AjaxBridgeServlet</code> class.
 * </p>
 * 
 * @author pinoydream
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AjaxBridgeServletUploadedDocumentTest extends TestCase {
    /**
     * <p>
     * The servlet to be tested.
     * </p>
     */
    private AjaxBridgeServlet ajaxServlet = null;

    /**
     * <p>
     * The Servlet Config to be mocked.
     * </p>
     */
    private MockServletConfig servletConfig = null;

    /**
     * <p>
     * The HttpSevletRequest to be mocked.
     * </p>
     */
    private PostMethodWebRequest request = null;

    /**
     * <p>
     * The HttpSevletResponse to be mocked.
     * </p>
     */
    private MockHttpServletResponse response = null;

    /**
     * <p>
     * Create instances.
     * </p>
     * 
     * @throws Exception
     *             wraps all exception
     */
    protected void setUp() throws Exception {
        // initialize the AjaxBridgeServlet
        ajaxServlet = new AjaxBridgeServlet();
        // initialize the MockServletConfig
        servletConfig = new MockServletConfig();

        // initialize the MockHttpServletResponse
        response = new MockHttpServletResponse();
    }

    /**
     * <p>
     * Destroy instances.
     * </p>
     * 
     * @throws Exception
     *             wraps all exception
     */
    protected void tearDown() throws Exception {
        // set the instances to null so they can be
        // garbage collected

        // set servlet to null
        ajaxServlet = null;
        // set servlet config to null
        servletConfig = null;
        // set the request to null
        request = null;
        // set the response to null
        response = null;
    }

    /**
     * <p>
     * Tests successful execution of Studio - uploadDocument .
     * </p>
     * 
     * @throws Exception
     *             wraps all exception
     */
    public void testStudioUploadDocumentSuccess() throws Exception {

        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service

        Map files = new HashMap();
        File file1 = new File(DIR, "1.txt");
        files.put("file1", file1);

        UploadedDocument doc = new UploadedDocument();
        doc.setDescription("desc");
        doc.setDocumentTypeId(1);
        doc.setFileName("file");
        doc.setMimeTypeId(3);
        doc.setPath("path");

        Map parameters = new HashMap();
        parameters.put("method", Arrays
                .asList(new String[] { "uploadDocument" }));
        parameters.put("service", Arrays.asList(new String[] { "studio" }));

        MockHttpServletRequest request = prepareRequest(files, parameters);
        request.setParameter("document", getJSONFromUploadedDocument(doc)
                .toJSONString());

        // set the project
        // call doPost
        ajaxServlet.doPost(request, response);

        JSONObject jsonObjResp = TestHelper.decodeResponseString(response
                .getContentAsString());
        assertTrue("documentID is expected.", jsonObjResp.toJSONString()
                .contains("documentID"));
    }

    /**
     * <p>
     * Initializes a valid AjaxBridgeServlet for testing.
     * </p>
     * 
     * @throws Exception
     *             wraps all exception
     */
    private void initializeValidAjaxBridgeServlet() throws Exception {
        TestHelper.initSCWithValidValues(servletConfig);
        ajaxServlet.init(servletConfig);
    }

    /**
     * <p>
     * Prepares the <code>ServletRequest</code> instance for testing. It will
     * has the input map values as the parameters.
     * </p>
     * 
     * @param files
     *            the files to set the parameter values into the request.
     * @param parameters
     *            the parameters to set the parameter values into the request.
     * 
     * @return the <code>ServletRequest</code> instance.
     * 
     * @throws Exception
     *             any exception to JUnit.
     */
    public static MockHttpServletRequest prepareRequest(Map files,
            Map parameters) throws Exception {
        // create the webRequest
        PostMethodWebRequest webRequest = new PostMethodWebRequest(URL, true);
        // webRequest.setMimeEncoded(true);

        for (Iterator iter = files.entrySet().iterator(); iter.hasNext();) {
            Entry entry = (Entry) iter.next();
            String name = (String) entry.getKey();
            webRequest.selectFile(name, (File) entry.getValue());
        }

        for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
            Entry entry = (Entry) iter.next();
            String name = (String) entry.getKey();
            List value = (List) entry.getValue();
            webRequest.setParameter(name, (String[]) value
                    .toArray(new String[value.size()]));
        }

        // run this webRequest
        if (runner == null) {
            runner = new ServletRunner();
        }

        ServletUnitClient client = runner.newClient();
        InvocationContext ic = client.newInvocation(webRequest);

        return new MockHttpServletRequest(readContent(ic.getRequest()
                .getInputStream()), CONTENT_TYPE);
    }

    /**
     * <p>
     * Gets the byte content from the given input stream.
     * </p>
     * 
     * @param inputStream
     *            the given input stream to get the content.
     * 
     * @return the byte content from the given input stream.
     * 
     * @throws Exception
     *             any exception when try to get the byte content from the given
     *             input stream.
     */
    public static byte[] readContent(InputStream inputStream) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        while ((len = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }

        inputStream.close();
        output.close();

        return output.toByteArray();
    }

    /** Represents the dir to store the testing files. */
    private static final String DIR = "test_files/files/";

    /** Represents the value of content type for testing. */
    private static final String CONTENT_TYPE = "multipart/form-data; boundary=--HttpUnit-part0-aSgQ2M";

    /** Represents the <code>ServletRunner</code> instance for testing. */
    private static ServletRunner runner = null;

    /**
     * Represents the url of the web server for testing. e.g.
     * http://localhost:8080. The value can be arbitrary since we do not do the
     * real job in the web server.
     */
    private static final String URL = "http://localhost:8080/";

    /**
     * <p>
     * Convenience method in getting a JSON object from an UploadedDocument.
     * </p>
     * 
     * @param upDoc
     *            the UploadedDocument object where the values will be coming
     *            from
     * @return the json object created from this uploaded document object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private JSONObject getJSONFromUploadedDocument(UploadedDocument upDoc) {
        JSONObject respJSON = new JSONObject();
        respJSON.setLong("contestID", upDoc.getContestId());
        respJSON.setLong("documentID", upDoc.getDocumentId());
        respJSON.setString("description", getString(upDoc.getDescription()));
        respJSON.setString("fileName", getString(upDoc.getFileName()));
        respJSON.setString("path", getString(upDoc.getPath()));
        respJSON.setLong("documentTypeID", upDoc.getDocumentTypeId());
        return respJSON;
    }

    /**
     * <p>
     * Convenience method to always return a non-null string value.
     * </p>
     * 
     * @param str
     *            value
     * @return string value. It will be converted to "" if it is null.
     */
    private String getString(String str) {
        return str == null ? "" : str;
    }
}
