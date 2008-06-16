/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.widgets.bridge;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONInvalidKeyException;
import com.topcoder.json.object.JSONObject;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.SubmissionData;

/**
 * <p>
 * Tests functionality of <code>AjaxBridgeServlet</code> class.
 * </p>
 *
 * @author pinoydream
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AjaxBridgeServletTest extends TestCase {
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
    private MockHttpServletRequest request = null;

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
     * @throws Exception wraps all exception
     */
    protected void setUp() throws Exception {
        // initialize the AjaxBridgeServlet
        ajaxServlet = new AjaxBridgeServlet();
        // initialize the MockServletConfig
        servletConfig = new MockServletConfig();
        // initialize the MockHttpServletRequest
        request = new MockHttpServletRequest();
        // initialize the MockHttpServletResponse
        response = new MockHttpServletResponse();
    }

    /**
     * <p>
     * Destroy instances.
     * </p>
     *
     * @throws Exception wraps all exception
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
     * Tests successful execution of Studio - uploadDocument  .
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioUploadDocumentSuccess () throws Exception {
        
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "uploadDocument");
        request.setMethod("post");
        request.setContentType("multipart/form-data");
        
        // set the project
        // call doPost
        ajaxServlet.doPost(request, response);

        checkVoidSuccessResponse();
    }
    
    /**
     * <p>
     * Tests the Constructor.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testCtor() throws Exception {
        assertNotNull("Failed to instantiate the servlet", ajaxServlet);
    }

    /**
     * <p>
     * Tests the servlet with null servlet config.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitNullConfig() throws Exception {
        try {
            ajaxServlet.init();
        } catch (ServletException e) {
            fail("The servletConfig is null.");
        }
    }

    /**
     * <p>
     * Tests the servlet init with valid init parameters.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitWithValidParams() throws Exception {
        try {
            // initialize the ServletConfig with valid
            // initParameters
            TestHelper.initSCWithValidValues(servletConfig);
            // call init with the ServletConfig
            ajaxServlet.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
            fail("The ServletConfig is valid. " + e.getMessage());

        }
    }

    /**
     * <p>
     * Tests the servlet with invalid 'ajaxBridgeConfigFile' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitInvalidParamAjaxBridge() throws Exception {
        try {
            // initialize the ServletConfig with valid
            // initParameters first so we can isolate
            // the source of error to 'ajaxBridgeConfigFile'
            TestHelper.initSCWithValidValues(servletConfig);
            // set the invalid 'ajaxBridgeConfigFile' param
            TestHelper.initSCWithInvalidAjaxBridgeParam(servletConfig);
            // call init with the ServletConfig
            ajaxServlet.init(servletConfig);
            fail("The ajaxBridgeConfigFile is invalid.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the servlet with invalid 'ajaxBridgeConfigFile' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitNullParamAjaxBridge() throws Exception {
        try {
            // set the invalid 'ajaxBridgeConfigFile' param & other valid parameters
            TestHelper.initSCWithNullAjaxBridgeParam(servletConfig);
            // call init with the ServletConfig
            ajaxServlet.init(servletConfig);
            fail("The ajaxBridgeConfigFile is invalid.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the servlet with invalid 'ajaxBridgeConfigFile' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitEmptyParamAjaxBridge2() throws Exception {
        try {
            // initialize the ServletConfig with valid
            // initParameters first so we can isolate
            // the source of error to 'ajaxBridgeConfigFile'
            TestHelper.initSCWithValidValues(servletConfig);
            // set the invalid 'ajaxBridgeConfigFile' param
            TestHelper.initSCWithEmptyAjaxBridgeParam(servletConfig);
            // call init with the ServletConfig
            ajaxServlet.init(servletConfig);
            fail("The ajaxBridgeConfigFile is invalid.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the servlet with invalid 'objectFactoryNamespace' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitInvalidParamObjNS() throws Exception {
        try {
            // initialize the ServletConfig with valid
            // initParameters first so we can isolate
            // the source of error to 'objectFactoryNamespace'
            TestHelper.initSCWithValidValues(servletConfig);
            // set the invalid 'objectFactoryNamespace' param
            TestHelper.initSCWithInvalidObjectNS(servletConfig);
            // call init with the ServletConfig
            ajaxServlet.init(servletConfig);
            fail("The objectFactoryNamespace is invalid.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the servlet with invalid 'jsonEncoderKey' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitInvalidParamEncoderKey() throws Exception {
        try {
            // initialize the ServletConfig with valid
            // initParameters first so we can isolate
            // the source of error to 'jsonEncoderKey'
            TestHelper.initSCWithValidValues(servletConfig);
            // set the invalid 'jsonEncoderKey' param
            TestHelper.initSCWithInvalidEncoderKey(servletConfig);
            // call init with the ServletConfig
            ajaxServlet.init(servletConfig);
            fail("The jsonEncoderKey is invalid.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the servlet with invalid 'jsonDecoderKey' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitInvalidParamDecoderKey() throws Exception {
        try {
            // initialize the ServletConfig with valid
            // initParameters first so we can isolate
            // the source of error to 'jsonDecoderKey'
            TestHelper.initSCWithValidValues(servletConfig);
            // set the invalid 'jsonDecoderKey' param
            TestHelper.initSCWithInvalidDecoderKey(servletConfig);
            // call init with the ServletConfig
            ajaxServlet.init(servletConfig);
            fail("The jsonDecoderKey is invalid.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the servlet with invalid 'projectServiceKey' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitInvalidParamProjectKey() throws Exception {
        try {
            // initialize the ServletConfig with valid
            // initParameters first so we can isolate
            // the source of error to 'projectServiceKey'
            TestHelper.initSCWithValidValues(servletConfig);
            // set the invalid 'projectServiceKey' param
            TestHelper.initSCWithInvalidProjectKey(servletConfig);
            // call init with the ServletConfig
            ajaxServlet.init(servletConfig);
            fail("The projectServiceKey is invalid.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the servlet with empty 'projectServiceKey' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitEmptyParamProjectKey() throws Exception {
        try {
            // initialize the ServletConfig with valid
            // initParameters first so we can isolate
            // the source of error to 'projectServiceKey'
            TestHelper.initSCWithValidValues(servletConfig);
            // set the invalid 'projectServiceKey' param
            TestHelper.initSCWithEmptyProjectKey(servletConfig);
            // call init with the ServletConfig
            ajaxServlet.init(servletConfig);
        } catch (ServletException e) {
            fail("The projectServiceKey should use default.");
        }
    }

    /**
     * <p>
     * Tests the servlet with invalid 'prerequisiteServiceKey' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitInvalidParamPrerequisiteKey() throws Exception {
        try {
            // initialize the ServletConfig with valid
            // initParameters first so we can isolate
            // the source of error to 'prerequisiteServiceKey'
            TestHelper.initSCWithValidValues(servletConfig);
            // set the invalid 'prerequisiteServiceKey' param
            TestHelper.initSCWithInvalidPrerequisiteKey(servletConfig);
            // call init with the ServletConfig
            ajaxServlet.init(servletConfig);
            fail("The prerequisiteServiceKey is invalid.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the servlet with invalid 'studioServiceKey' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitInvalidParamStudioKey() throws Exception {
        try {
            // initialize the ServletConfig with valid
            // initParameters first so we can isolate
            // the source of error to 'studioServiceKey'
            TestHelper.initSCWithValidValues(servletConfig);
            // set the invalid 'studioServiceKey' param
            TestHelper.initSCWithInvalidStudioKey(servletConfig);
            // call init with the ServletConfig
            ajaxServlet.init(servletConfig);
            fail("The studioServiceKey is invalid.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the servlet with invalid 'fileUploadNamespace' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInitInvalidParamUploadKey() throws Exception {
        try {
            // initialize the ServletConfig with valid
            // initParameters first so we can isolate
            // the source of error to 'fileUploadNamespace'
            TestHelper.initSCWithValidValues(servletConfig);
            // set the invalid 'fileUploadNamespace' param
            TestHelper.initSCWithInvalidUploadKey(servletConfig);
            // call init with the ServletConfig
            ajaxServlet.init(servletConfig);
            fail("The fileUploadNamespace is invalid.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the doGet with invalid null request parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testDoGetNullRequest() throws Exception {
        try {
            // initialize the servlet
            initializeValidAjaxBridgeServlet();
            // call doGet
            ajaxServlet.doGet(null, response);
            fail("The 'request' is null.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the doGet with invalid null response parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testDoGetNullResponse() throws Exception {
        try {
            // initialize the servlet
            initializeValidAjaxBridgeServlet();
            // call doGet
            ajaxServlet.doGet(request, null);
            fail("The 'response' is null.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the doPost with invalid null request parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testDoPostNullRequest() throws Exception {
        try {
            // initialize the servlet
            initializeValidAjaxBridgeServlet();
            // call doPost
            ajaxServlet.doPost(null, response);
            fail("The 'request' is null.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the doPost with invalid null response parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testDoPostNullResponse() throws Exception {
        try {
            // initialize the servlet
            initializeValidAjaxBridgeServlet();
            // call doPost
            ajaxServlet.doPost(request, null);
            fail("The 'response' is null.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests invalid 'service' request parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInvalidServiceParam() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "none");
        // call doPost
        ajaxServlet.doPost(request, response);
        // now get the response as JSONObject
        JSONObject jsonObjResp = TestHelper.decodeResponseString(response.getContentAsString());

        assertFalse("The 'success' parameter should be false.", jsonObjResp.getBoolean("success"));
        assertNotNull("The 'error' parameter should not be null", jsonObjResp.getString("error"));
    }

    /**
     * <p>
     * Tests invalid 'service' request parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInvalidServiceParam1() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "");
        // call doPost
        ajaxServlet.doPost(request, response);
        // now get the response as JSONObject
        JSONObject jsonObjResp = TestHelper.decodeResponseString(response.getContentAsString());

        assertFalse("The 'success' parameter should be false.", jsonObjResp.getBoolean("success"));
        assertNotNull("The 'error' parameter should not be null", jsonObjResp.getString("error"));
    }

    /**
     * <p>
     * Tests invalid 'service' request parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInvalidServiceParam2() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // call doPost
        ajaxServlet.doPost(request, response);
        // now get the response as JSONObject
        JSONObject jsonObjResp = TestHelper.decodeResponseString(response.getContentAsString());

        assertFalse("The 'success' parameter should be false.", jsonObjResp.getBoolean("success"));
        assertNotNull("The 'error' parameter should not be null", jsonObjResp.getString("error"));
    }

    /**
     * <p>
     * Tests invalid 'method' request parameter for a valid project 'service' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInvalidMethodParamForProject() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "project");
        request.setParameter("method", "invalid");
        // call doPost
        ajaxServlet.doPost(request, response);
        // now get the response as JSONObject
        JSONObject jsonObjResp = TestHelper.decodeResponseString(response.getContentAsString());

        assertFalse("The 'success' parameter should be false.", jsonObjResp.getBoolean("success"));
        assertNotNull("The 'error' parameter should not be null", jsonObjResp.getString("error"));
    }

    /**
     * <p>
     * Tests invalid 'method' request parameter for a valid studio 'service' parameter.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testInvalidMethodParamForStudio() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "invalid");
        // call doPost
        ajaxServlet.doPost(request, response);
        // now get the response as JSONObject
        JSONObject jsonObjResp = TestHelper.decodeResponseString(response.getContentAsString());

        assertFalse("The 'success' parameter should be false.", jsonObjResp.getBoolean("success"));
        assertNotNull("The 'error' parameter should not be null", jsonObjResp.getString("error"));
    }

    /**
     * <p>
     * Tests successful execution of Project - createProject.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testProjectCreateProjectSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "project");
        request.setParameter("method", "createProject");
        // set the project
        request.setParameter("project", TestHelper.getProjectJSONStringValid());
        // call doGet to test if doGet delegates it properly
        ajaxServlet.doGet(request, response);
        // now get the response as JSONObject
        JSONObject jsonProj = checkValidJSONSuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonProj);
        // if this throws an exception, it means the JSONObject is not for a project
        ProjectData proj = TestHelper.getProjectFromJSON(jsonProj);
        assertNotNull("The Project should not be null", proj);
    }

    /**
     * <p>
     * Tests unsuccessful execution of Project - createProject.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testProjectCreateProjectFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "project");
        request.setParameter("method", "createProject");
        // set the project
        request.setParameter("project", TestHelper.getProjectJSONStringError());
        // call doGet to test if doGet delegates it properly
        ajaxServlet.doGet(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the project
        request.setParameter("project", "");
        // call doGet to test if doGet delegates it properly
        ajaxServlet.doGet(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Project - updateProject.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testProjectUpdateProjectSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "project");
        request.setParameter("method", "updateProject");
        // set the project
        request.setParameter("project", TestHelper.getProjectJSONStringValid());
        // call doPost
        ajaxServlet.doPost(request, response);
        // now get the response as JSONObject
        JSONObject jsonProj = checkValidJSONSuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonProj);
        // if this throws an exception, it means the JSONObject is not for a project
        ProjectData proj = TestHelper.getProjectFromJSON(jsonProj);
        assertNotNull("The Project should not be null", proj);
    }

    /**
     * <p>
     * Tests unsuccessful execution of Project - updateProject.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testProjectUpdateProjectFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "project");
        request.setParameter("method", "updateProject");
        // set the project
        request.setParameter("project", TestHelper.getProjectJSONStringError());
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the project
        request.setParameter("project", "");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Project - deleteProject.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testProjectDeleteProjectSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "project");
        request.setParameter("method", "deleteProject");
        // set the project
        request.setParameter("project", TestHelper.getProjectJSONStringValid());
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if response is valid
        checkVoidSuccessResponse();
    }

    /**
     * <p>
     * Tests unsuccessful execution of Project - deleteProject.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testProjectDeleteProjectFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "project");
        request.setParameter("method", "deleteProject");
        // set the project
        request.setParameter("project", TestHelper.getProjectJSONStringError());
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Project - getProject.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testProjectGetProjectSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "project");
        request.setParameter("method", "getProject");
        // set the project
        request.setParameter("projectID", "1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // now get the response as JSONObject
        JSONObject jsonProj = checkValidJSONSuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonProj);
        // if this throws an exception, it means the JSONObject is not for a project
        ProjectData proj = TestHelper.getProjectFromJSON(jsonProj);
        assertNotNull("The Project should not be null", proj);
    }

    /**
     * <p>
     * Tests unsuccessful execution of Project - getProject.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testProjectGetProjectFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "project");
        request.setParameter("method", "getProject");
        // set the project
        request.setParameter("projectID", "50");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the project
        request.setParameter("projectID", "-1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Project - getProjectsForUser.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testProjectGetProjectsForUserSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "project");
        request.setParameter("method", "getProjectsForUser");
        // set the project
        request.setParameter("userID", "1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // get response as Array
        JSONArray jsonProj = checkValidJSONArraySuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonProj);
        // if this throws an exception, it means the JSONObject is not for a project
        Object[] objects = jsonProj.getObjects();
        for (int i = 0; i < objects.length; i++) {
            ProjectData proj = TestHelper.getProjectFromJSON((JSONObject) objects[i]);
            assertNotNull("The Project should not be null", proj);
        }
    }

    /**
     * <p>
     * Tests unsuccessful execution of Project - getProjectsForUser.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testProjectGetProjectsForUserFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "project");
        request.setParameter("method", "getProjectsForUser");
        // set the userID
        request.setParameter("userID", "50");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the userID
        request.setParameter("userID", "51");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the userID
        request.setParameter("userID", "52");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the userID
        request.setParameter("userID", "-1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Project - getAllProjects.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testProjectGetAllProjectsSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "project");
        request.setParameter("method", "getAllProjects");
        // call doPost
        ajaxServlet.doPost(request, response);
        // get response as Array
        JSONArray jsonProj = checkValidJSONArraySuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonProj);
        // if this throws an exception, it means the JSONObject is not for a project
        Object[] objects = jsonProj.getObjects();
        for (int i = 0; i < objects.length; i++) {
            ProjectData proj = TestHelper.getProjectFromJSON((JSONObject) objects[i]);
            assertNotNull("The Project should not be null", proj);
        }
    }

    /**
     * <p>
     * Tests successful execution of Studio - createContest.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioCreateContestSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "createContest");
        // set the project
        request.setParameter("projectID", "1");
        request.setParameter("contest", TestHelper.getContestJSONStringValid());
        // call doPost
        ajaxServlet.doPost(request, response);
        // now get the response as JSONObject
        JSONObject jsonContest = checkValidJSONSuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonContest);
        ContestData contest = TestHelper.getContestFromJSON(jsonContest);
        assertNotNull("The 'contest' should not be null", contest);
    }

    /**
     * <p>
     * Tests unsuccessful execution of Studio - createContest.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioCreateContestFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "createContest");
        // set the project
        request.setParameter("projectID", "50");
        request.setParameter("contest", TestHelper.getContestJSONStringError());
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the project
        request.setParameter("projectID", "1");
        request.setParameter("contest", "");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the project
        request.setParameter("projectID", "-1");
        request.setParameter("contest", TestHelper.getContestJSONStringError());
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - getContest.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioGetContestSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "getContest");
        // set the contest ID
        request.setParameter("contestID", "1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // now get the response as JSONObject
        JSONObject jsonContest = checkValidJSONSuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonContest);
        ContestData contest = TestHelper.getContestFromJSON(jsonContest);
        assertNotNull("The 'contest' should not be null", contest);
    }

    /**
     * <p>
     * Tests unsuccessful execution of Studio - getContest.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioGetContestFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "getContest");
        // set the contest ID
        request.setParameter("contestID", "50");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the contest ID
        request.setParameter("contestID", "-1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the contest ID
        request.setParameter("contestID", "10");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests execution of Studio - getAllContestTypes. It will return a list of contest types back without any error.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioGetAllContestTypes() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "getAllContestTypes");
        // call doPost
        ajaxServlet.doPost(request, response);

        JSONArray jsonContest = checkValidJSONArraySuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonContest);
        Object[] objects = jsonContest.getObjects();
        for (int i = 0; i < objects.length; i++) {
            assertNotNull("Each json object should not be null", objects[i]);
        }
    }

    /**
     * <p>
     * Tests execution of Studio - getAllContests. It will return a list of contests back without any error.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioGetAllContestsSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "getAllContests");
        // call doPost
        ajaxServlet.doPost(request, response);

        // not supported yet
        // check if error response is valid
        // get response as Array
        JSONArray jsonContest = checkValidJSONArraySuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonContest);
        Object[] object = jsonContest.getObjects();
        for (int i = 0; i < object.length; i++) {
            ContestData contest = TestHelper.getContestFromJSON((JSONObject) object[i]);
            assertNotNull("The 'contest' should not be null", contest);
        }
    }

    /**
     * <p>
     * Tests execution of Studio - getContestsForClient.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioGetContestsForClientSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "getContestsForClient");
        // set the contest ID
        request.setParameter("clientID", "1");
        request.setParameter("contestStatusID", "1");
        // call doPost
        ajaxServlet.doPost(request, response);

        // not supported yet
        // check if error response is valid
        // get response as Array
        JSONArray jsonContest = checkValidJSONArraySuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonContest);
        Object[] object = jsonContest.getObjects();
        for (int i = 0; i < object.length; i++) {
            ContestData contest = TestHelper.getContestFromJSON((JSONObject) object[i]);
            assertNotNull("The 'contest' should not be null", contest);
        }
    }

    /**
     * <p>
     * Tests unsuccessful execution of Studio - getContestsForClient.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioGetContestsForClientFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "getContestsForClient");
        // set the contest ID
        request.setParameter("clientID", "50");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the contest ID
        request.setParameter("clientID", "-1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - getContestsForProject.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioGetContestsForProjectSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "getContestsForProject");
        // set the project ID
        request.setParameter("projectID", "1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // get response as Array
        JSONArray jsonContest = checkValidJSONArraySuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonContest);
        Object[] object = jsonContest.getObjects();
        for (int i = 0; i < object.length; i++) {
            ContestData contest = TestHelper.getContestFromJSON((JSONObject) object[i]);
            assertNotNull("The 'contest' should not be null", contest);
        }
    }

    /**
     * <p>
     * Tests unsuccessful execution of Studio - getContestsForProject.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioGetContestsForProjectFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "getContestsForProject");
        // set the project ID
        request.setParameter("projectID", "50");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the project ID
        request.setParameter("projectID", "-1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - updateContestStatus.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioUpdateContestStatusSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "updateContestStatus");
        // set the contest ID
        request.setParameter("contestID", "1");
        request.setParameter("newStatusID", "50");
        // call doPost
        ajaxServlet.doPost(request, response);
        // now get the response as JSONObject
        JSONObject jsonContest = checkValidJSONSuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonContest);
        ContestData contest = TestHelper.getContestFromJSON(jsonContest);
        assertNotNull("The 'contest' should not be null", contest);
    }

    /**
     * <p>
     * Tests unsuccessful execution of Studio - updateContestStatus.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioUpdateContestStatusFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "updateContestStatus");
        // set the contest ID
        request.setParameter("contestID", "50");
        request.setParameter("newStatusID", "50");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the contest ID
        request.setParameter("contestID", "-1");
        request.setParameter("newStatusID", "1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the contest ID
        request.setParameter("contestID", "1");
        request.setParameter("newStatusID", "-1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - removeDocumentFromContest.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioRemoveDocumentFromContestSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "removeDocumentFromContest");
        // set the document
        request.setParameter("document", TestHelper.getUploadDocJSONStringValid());
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if response is valid
        checkVoidSuccessResponse();
    }

    /**
     * <p>
     * Tests unsuccessful execution of Studio - removeDocumentFromContest.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioRemoveDocumentFromContestFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "removeDocumentFromContest");
        // set the document
        request.setParameter("document", TestHelper.getUploadDocJSONStringError());
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - retrieveSubmissionsForContest.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioRetrieveSubmissionsForContestSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "retrieveSubmissionsForContest");
        // set the contest ID
        request.setParameter("contestID", "1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // get response as Array
        JSONArray jsonSubmission = checkValidJSONArraySuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonSubmission);
        Object[] object = jsonSubmission.getObjects();
        for (int i = 0; i < object.length; i++) {
            SubmissionData submission = TestHelper.getSubmissionFromJSON((JSONObject) object[i]);
            assertNotNull("The 'submission' should not be null", submission);
        }
    }

    /**
     * <p>
     * Tests unsuccessful execution of Studio - retrieveSubmissionsForContest.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioRetrieveSubmissionsForContestFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "retrieveSubmissionsForContest");
        // set the contest ID
        request.setParameter("contestID", "50");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the contest ID
        request.setParameter("contestID", "-1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - updateSubmission.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioUpdateSubmissionSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "updateSubmission");
        // set the submission
        request.setParameter("submission", TestHelper.getSubmissionJSONStringValid());
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if response is valid
        checkVoidSuccessResponse();
    }

    /**
     * <p>
     * Tests unsuccessful execution of Studio - updateSubmission.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioUpdateSubmissionFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "updateSubmission");
        // set the submission
        request.setParameter("submission", TestHelper.getSubmissionJSONStringError());
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the submission
        request.setParameter("submission", "");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - updateContest.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioUpdateContestSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "updateContest");
        // set the contest
        request.setParameter("contest", TestHelper.getContestJSONStringValid());
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if response is valid
        checkVoidSuccessResponse();
    }

    /**
     * <p>
     * Tests unsuccessful execution of Studio - updateContest.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioUpdateContestFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "updateContest");
        // set the contest
        request.setParameter("contest", TestHelper.getContestJSONStringError());
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the contest
        request.setParameter("contest", "");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests execution of Studio - retrieveSubmission. It is not supported yet. Returns an error response.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioRetrieveSubmission() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "retrieveSubmission");
        // set the submission ID
        request.setParameter("submissionID", "1");
        // call doPost
        ajaxServlet.doPost(request, response);

        // now get the response as JSONObject
        JSONObject jsonContest = checkValidJSONSuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonContest);
        SubmissionData submission = TestHelper.getSubmissionFromJSON(jsonContest);
        assertNotNull("The 'submission' should not be null", submission);
    }

    /**
     * <p>
     * Tests unsuccessful execution of Studio - removeSubmission.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioRetrieveSubmissionFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "retrieveSubmission");
        // set the submission ID
        request.setParameter("submissionID", "50");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the submission ID
        request.setParameter("submissionID", "-1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - removeSubmission.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioRemoveSubmissionSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "removeSubmission");
        // set the submission ID
        request.setParameter("submissionID", "1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if response is valid
        checkVoidSuccessResponse();
    }

    /**
     * <p>
     * Tests unsuccessful execution of Studio - updateContest.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioRemoveSubmissionFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "removeSubmission");
        // set the submission ID
        request.setParameter("submissionID", "50");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the submission ID
        request.setParameter("submissionID", "zzz");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - retrieveAllSubmissionsByMember.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioRetrieveAllSubmissionsByMemberSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "retrieveAllSubmissionsByMember");
        // set the user ID
        request.setParameter("userID", "1");
        // call doPost
        ajaxServlet.doPost(request, response);
        // get response as Array
        JSONArray jsonSubmission = checkValidJSONArraySuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonSubmission);
        Object[] object = jsonSubmission.getObjects();
        for (int i = 0; i < object.length; i++) {
            SubmissionData submission = TestHelper.getSubmissionFromJSON((JSONObject) object[i]);
            assertNotNull("The 'submission' should not be null", submission);
        }
    }

    /**
     * <p>
     * Tests unsuccessful execution of Studio - retrieveAllSubmissionsByMember.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioRetrieveAllSubmissionsByMemberFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "retrieveAllSubmissionsByMember");
        // set the contest ID
        request.setParameter("userID", "50");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();

        // set the contest ID
        request.setParameter("userID", "yyy");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests failed execution of Studio - getContestCategories.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioGetContestCategoriesFail() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "getContestCategories");
        // set the user ID
        request.setParameter("parameters", "");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - getSubmissionFileTypes.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioGetSubmissionFileTypesSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "getSubmissionFileTypes");
        // call doPost
        ajaxServlet.doPost(request, response);
        // now get the response as JSONObject
        JSONArray jsonFileTypes = checkValidJSONArraySuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonFileTypes);
    }

    /**
     * <p>
     * Tests successful execution of Studio - getContestStatuses.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioGetContestStatusesSuccess() throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "getContestStatuses");
        // call doPost
        ajaxServlet.doPost(request, response);
        // now get the response as JSONObject
        JSONArray jsonContestStatuses = checkValidJSONArraySuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonContestStatuses);
    }

    /**
     * <p>
     * Tests successful execution of Studio - addDocumentToContest .
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioAddDocumentToContestSuccess () throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "addDocumentToContest");
        // set the project
        request.setParameter("documentId", "1");
        request.setParameter("contestId", "2");
        // call doPost
        ajaxServlet.doPost(request, response);
        checkVoidSuccessResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - addDocumentToContest .
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioAddDocumentToContestFail1 () throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "addDocumentToContest");
        // set the project
        request.setParameter("documentId", "-1");
        request.setParameter("contestId", "2");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - addDocumentToContest .
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioAddDocumentToContestFail2 () throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "addDocumentToContest");
        // set the project
        request.setParameter("documentId", "1");
        request.setParameter("contestId", "-2");
        // call doPost
        ajaxServlet.doPost(request, response);
        // check if error response is valid
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Studio - uploadDocument  .
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testStudioUploadDocumentFail () throws Exception {
        // initialize the servlet
        initializeValidAjaxBridgeServlet();
        // set invalid service
        request.setParameter("service", "studio");
        request.setParameter("method", "uploadDocument");
        // set the project
        // call doPost
        ajaxServlet.doPost(request, response);

        // check if error response is valid
        checkValidErrorResponse();
    }
    
    /**
     * <p>
     * Tests successful execution of Prerequisite - getAllPrerequisiteDocuments.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteGetAllPrerequisiteDocumentsSuccess() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "getAllPrerequisiteDocuments");
        ajaxServlet.doPost(request, response);
        JSONArray jsonDocuments = checkValidJSONArraySuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonDocuments);
        assertEquals("Document count should be 2.", 2, jsonDocuments.getSize());
    }

    /**
     * <p>
     * Tests successful execution of Prerequisite - getPrerequisiteDocuments.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteGetPrerequisiteDocumentsSuccess() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "getPrerequisiteDocuments");
        request.setParameter("competitionID", "1");
        request.setParameter("roleID", "2");
        ajaxServlet.doPost(request, response);
        JSONArray jsonDocuments = checkValidJSONArraySuccessResponse();
        assertNotNull("The 'json' parameter should not be null", jsonDocuments);
        assertEquals("Document count should be 1.", 1, jsonDocuments.getSize());
    }

    /**
     * <p>
     * Tests failed execution of Prerequisite - getPrerequisiteDocuments. competition id is not a long type.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteGetPrerequisiteDocumentsFail1() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "getPrerequisiteDocuments");
        request.setParameter("competitionID", "no a id");
        request.setParameter("roleID", "2");
        ajaxServlet.doPost(request, response);
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests failed execution of Prerequisite - getPrerequisiteDocuments. role id is not a long type.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteGetPrerequisiteDocumentsFail2() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "getPrerequisiteDocuments");
        request.setParameter("competitionID", "1");
        request.setParameter("roleID", "not a id");
        ajaxServlet.doPost(request, response);
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests failed execution of Prerequisite - getPrerequisiteDocuments. role id is less than 0.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteGetPrerequisiteDocumentsFail3() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "getPrerequisiteDocuments");
        request.setParameter("competitionID", "1");
        request.setParameter("roleID", "-1");
        ajaxServlet.doPost(request, response);
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Prerequisite - getPrerequisiteDocument.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteGetPrerequisiteDocumentSuccess() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "getPrerequisiteDocument");
        request.setParameter("documentID", "1");
        request.setParameter("version", "1");
        ajaxServlet.doPost(request, response);
        JSONObject document = checkValidJSONSuccessResponse();
        assertNotNull("The 'json' parameter should not be null", document);
    }

    /**
     * <p>
     * Tests failed execution of Prerequisite - getPrerequisiteDocument.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteGetPrerequisiteDocumentFail1() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "getPrerequisiteDocument");
        // trigger mock service exception
        request.setParameter("documentID", "3");
        request.setParameter("version", "1");
        ajaxServlet.doPost(request, response);
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests failed execution of Prerequisite - getPrerequisiteDocument. both ids are not set.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteGetPrerequisiteDocumentFail2() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "getPrerequisiteDocument");
        ajaxServlet.doPost(request, response);
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests failed execution of Prerequisite - getPrerequisiteDocument. version is not an integer.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteGetPrerequisiteDocumentFail3() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "getPrerequisiteDocument");
        // trigger mock service exception
        request.setParameter("documentID", "3");
        request.setParameter("version", "wrong");
        ajaxServlet.doPost(request, response);
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests successful execution of Prerequisite - recordMemberAnswer.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiterecordMemberAnswerSuccess() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "recordMemberAnswer");
        request.setParameter("competitionID", "1");
        request.setParameter("timestamp", "2008-03-04 09:00");
        request.setParameter("agrees", "true");
        request.setParameter("prerequisiteDocument", TestHelper.getPrerequisiteDocumentJSONStringValid());
        request.setParameter("roleID", "1");
        ajaxServlet.doPost(request, response);
        checkVoidSuccessResponse();
    }

    /**
     * <p>
     * Tests failed execution of Prerequisite - recordMemberAnswer.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteRecordMemberAnswerFail1() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "recordMemberAnswer");
        // trigger exception in mock service
        request.setParameter("competitionID", "2");
        request.setParameter("timestamp", "2008-03-04 09:00");
        request.setParameter("agrees", "true");
        request.setParameter("prerequisiteDocument", TestHelper.getPrerequisiteDocumentJSONStringValid());
        request.setParameter("roleID", "1");
        ajaxServlet.doPost(request, response);
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests failed execution of Prerequisite - recordMemberAnswer.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteRecordMemberAnswerFail2() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "recordMemberAnswer");
        // trigger exception in mock service
        request.setParameter("competitionID", "1");
        request.setParameter("timestamp", "2008-03-04 09:00");
        request.setParameter("agrees", "true");
        request.setParameter("prerequisiteDocument", TestHelper.getPrerequisiteDocumentJSONStringError());
        request.setParameter("roleID", "1");
        ajaxServlet.doPost(request, response);
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests failed execution of Prerequisite - recordMemberAnswer. Missing competition id.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteRecordMemberAnswerFail3() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "recordMemberAnswer");
        request.setParameter("timestamp", "2008-03-04 09:00");
        request.setParameter("agrees", "true");
        request.setParameter("prerequisiteDocument", TestHelper.getPrerequisiteDocumentJSONStringError());
        request.setParameter("roleID", "1");
        ajaxServlet.doPost(request, response);
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests failed execution of Prerequisite - recordMemberAnswer. role id is less than 0.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiteRecordMemberAnswerFail4() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "recordMemberAnswer");
        // trigger exception in mock service
        request.setParameter("competitionID", "1");
        request.setParameter("timestamp", "2008-03-04 09:00");
        request.setParameter("agrees", "true");
        request.setParameter("prerequisiteDocument", TestHelper.getPrerequisiteDocumentJSONStringError());
        request.setParameter("roleID", "-2");
        ajaxServlet.doPost(request, response);
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Tests failed execution of Prerequisite - recordMemberAnswer. Method name wrong.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    public void testPrerequisiterecordMemberAnswerFail5() throws Exception {
        initializeValidAjaxBridgeServlet();
        request.setParameter("service", "prerequisite");
        request.setParameter("method", "recordMemberAnswer_wrong");
        // trigger exception in mock service
        request.setParameter("competitionID", "1");
        request.setParameter("timestamp", "2008-03-04 09:00");
        request.setParameter("agrees", "true");
        request.setParameter("prerequisiteDocument", TestHelper.getPrerequisiteDocumentJSONStringError());
        request.setParameter("roleID", "1");
        ajaxServlet.doPost(request, response);
        checkValidErrorResponse();
    }

    /**
     * <p>
     * Checks the json object response if valid for a JSONObject success response.
     * </p>
     *
     * @throws Exception wraps all exception
     * @return JSONObject the json object for the response
     */
    private JSONObject checkValidJSONSuccessResponse() throws Exception {
        // now get the response as JSONObject
        JSONObject jsonObjResp = TestHelper.decodeResponseString(response.getContentAsString());

        assertTrue("The 'success' parameter should be true.", jsonObjResp.getBoolean("success"));
        // check if the 'error' parameter is present
        try {
            jsonObjResp.getString("error");
            fail("The 'error' parameter should be null");
        } catch (JSONInvalidKeyException e) {
            // expected
        }
        JSONObject jsonProj = jsonObjResp.getNestedObject("json");
        return jsonProj;
    }

    /**
     * <p>
     * Checks the json object response if valid for a JSONArray success response.
     * </p>
     *
     * @throws Exception wraps all exception
     * @return JSONArray the json array for the response
     */
    private JSONArray checkValidJSONArraySuccessResponse() throws Exception {
        // now get the response as JSONObject
        JSONObject jsonObjResp = TestHelper.decodeResponseString(response.getContentAsString());
        assertTrue("The 'success' parameter should be true.", jsonObjResp.getBoolean("success"));
        // check if the 'error' parameter is present
        try {
            jsonObjResp.getString("error");
            fail("The 'error' parameter should be null");
        } catch (JSONInvalidKeyException e) {
            // expected
        }
        JSONArray jsonProj = jsonObjResp.getArray("json");
        return jsonProj;
    }

    /**
     * <p>
     * Checks the json object response if valid for a void success response.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    private void checkVoidSuccessResponse() throws Exception {
        // now get the response as JSONObject
        JSONObject jsonObjResp = TestHelper.decodeResponseString(response.getContentAsString());

        assertTrue("The 'success' parameter should be true.", jsonObjResp.getBoolean("success"));
        // check if the 'error' parameter is present
        try {
            jsonObjResp.getString("error");
            fail("The 'error' parameter should be null");
        } catch (JSONInvalidKeyException e) {
            // expected
        }
        try {
            jsonObjResp.getString("json");
            fail("The 'json' parameter should be null");
        } catch (JSONInvalidKeyException e) {
            // expected
        }
    }

    /**
     * <p>
     * Checks the json object response if valid for an error response.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    private void checkValidErrorResponse() throws Exception {
        // now get the response as JSONObject
        JSONObject jsonObjResp = TestHelper.decodeResponseString(response.getContentAsString());

        assertFalse("The 'success' parameter should be false.", jsonObjResp.getBoolean("success"));
        // check if the 'error' parameter is present
        try {
            jsonObjResp.getString("json");
            fail("The 'json' parameter should be null");
        } catch (JSONInvalidKeyException e) {
            // expected
        }
        assertNotNull("The 'error' parameter should not be null", jsonObjResp.getString("error"));
    }

    /**
     * <p>
     * Initializes a valid AjaxBridgeServlet for testing.
     * </p>
     *
     * @throws Exception wraps all exception
     */
    private void initializeValidAjaxBridgeServlet() throws Exception {
        TestHelper.initSCWithValidValues(servletConfig);
        ajaxServlet.init(servletConfig);
    }
}
