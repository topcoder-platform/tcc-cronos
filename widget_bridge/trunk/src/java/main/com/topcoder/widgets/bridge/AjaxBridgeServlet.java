/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.widgets.bridge;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONDataAccessTypeException;
import com.topcoder.json.object.JSONInvalidKeyException;
import com.topcoder.json.object.JSONObject;
import com.topcoder.json.object.io.JSONDecoder;
import com.topcoder.json.object.io.JSONDecodingException;
import com.topcoder.json.object.io.JSONEncoder;
import com.topcoder.json.object.io.JSONEncodingException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.service.prerequisite.PrerequisiteDocument;
import com.topcoder.service.prerequisite.PrerequisiteService;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestPayload;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.PrizeData;
import com.topcoder.service.studio.StudioService;
import com.topcoder.service.studio.StudioServiceException;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.servlet.request.FileUploadResult;
import com.topcoder.servlet.request.HttpRequestParser;
import com.topcoder.servlet.request.MemoryFileUpload;
import com.topcoder.servlet.request.UploadedFile;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;

/**
 * <p>
 * This class extends the HttpServlet class, and it will decode AJAXrequests
 * from the JavaScript part into parameters used to call API methods in the
 * Prerequisite/Project/Studio services, which will in turn make calls to
 * service servers. They can be EJB clients to make calls. Returned values
 * should be encoded into the AJAX response.
 * </p>
 * <p>
 * <b>Thread-safety:</b> Mutable and not thread-safe. But as its mutable
 * variables are all initialized in the init method and would never change
 * afterwards. So this class can be used thread-safely by the container.
 * </p>
 * 
 * @author StandLove,pinoydream
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AjaxBridgeServlet extends HttpServlet {
    /**
     * <p>
     * Constant for accessing the Service parameter from request.
     * </p>
     */
    private static final String SERVICE_PARAM_KEY = "service";

    /**
     * <p>
     * Constant for accessing the Method parameter from request.
     * </p>
     */
    private static final String METHOD_PARAM_KEY = "method";

    /**
     * <p>A <code>String</code> providing the name of session attribute which may hold the order ID returned by
     * <code>PayPal</code> service.</p>
     */
    private static final String PAYPAL_ORDER_ID_ATTR = "paypalOrderID";

    /**
     * <p>A <code>String</code> providing the name of session attribute which may hold the user ID returned by
     * <code>PayPal</code> service.</p>
     */
    private static final String PAYPAL_ORDER_USER_ATTR = "paypalOrderUser";

    /**
     * <p>
     * Serial version ID.
     * </p>
     */
    private static final long serialVersionUID = 8677531520816392834L;

    /**
     * <p>
     * Represents the JSONEncoder to encoder the json object into string.
     * Initialized in init method, and never changed afterwards. Must be
     * non-null after initialized. Used in doPost method.
     * </p>
     */
    private JSONEncoder jsonEncoder;

    /**
     * <p>
     * Represents the JSONDecoder to decode string into json object. Initialized
     * in init method, and never changed afterwards. Must be non-null after
     * initialized. Used in doPost method.
     * </p>
     */
    private JSONDecoder jsonDecoder;

    /**
     * <p>
     * Represents the client to access Project Service. Initialized in init
     * method, and never changed afterwards. Must be non-null after initialized.
     * Used in doPost method.
     * </p>
     */
    private ProjectService projectService;

    /**
     * <p>
     * Represents the client to access Prerequisite Service. Initialized in init
     * method, and never changed afterwards. Must be non-null after initialized.
     * Used in doPost method.
     * </p>
     */
    private PrerequisiteService prerequisiteService;

    /**
     * <p>
     * Represents the client to access Studio Service. Initialized in init
     * method, and never changed afterwards. Must be non-null after initialized.
     * Used in doPost method.
     * </p>
     */
    private StudioService studioService;

    /**
     * <p>
     * Represents the logger to do the logging. Initialized in init method, and
     * never changed afterwards. Must be non-null after initialized. Used in
     * doPost method.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * Represents the configuration object used to create the MemoryFileUpload
     * object. Initialized in init method, and never changed afterwards. Must be
     * non-null. (Though the ConfigurationObject is mutable, but it's never
     * changed after loaded.) Used in doPost method.
     * </p>
     */
    private ConfigurationObject fileUploadConfig;

    /**
     * <p>
     * Date Formatter for the date strings received from request.
     * </p>
     */
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * <p>
     * Empty Constructor.
     * </p>
     */
    public AjaxBridgeServlet() {
        // do nothing
    }

    /**
     * <p>
     * Initializes the servlet. Gets the init parameters from web.xml and uses
     * that to properly initialize the instance variables. When and exception
     * occurs while creating these objects, the exception is not thrown but only
     * thrown back to the requesting entity.
     * </p>
     * 
     * @param config
     *            the servlet config object passed by the container for this
     *            servlet
     * @throws ServletException
     *             - if an exception occurs when initializing instance
     *             variables, or the configured value is invalid. All exceptions
     *             that occurred here in init are wrapped in a ServletException
     *             object since this is the only exception that can be thrown
     *             here.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        if (config == null) {
            throw new ServletException("config can't be null!");
        }
        super.init(config);

        try {
            // check first if ServletConfig is not null
            if (getServletConfig() == null) {
                throw new ServletException("ServletConfig can't be null!");
            }

            // initialize the ConfigurationFileManager
            ConfigurationFileManager cm = new ConfigurationFileManager(getParameter("ajaxBridgeConfigFile", null));
            // get the ConfigurationObject for ObjectFactory
            ConfigurationObject rootConfig = cm.getConfiguration(getParameter("objectFactoryNamespace", null))
                    .getChild("default");
            ConfigurationObjectSpecificationFactory cosf = new ConfigurationObjectSpecificationFactory(rootConfig);

            ObjectFactory objectFactory = new ObjectFactory(cosf);

            // Create objects for JSON
            // initialize the JSONEncoder
            jsonEncoder = (JSONEncoder) objectFactory.createObject(getParameter("jsonEncoderKey", "jsonEncoder"));
            // initialize the JSONDecoder
            jsonDecoder = (JSONDecoder) objectFactory.createObject(getParameter("jsonDecoderKey", "jsonDecoder"));

            // Create the Services
            projectService = getServiceObject(ProjectService.class, objectFactory.createObject(getParameter(
                    "projectServiceKey", "projectService")));
            prerequisiteService = getServiceObject(PrerequisiteService.class, objectFactory.createObject(getParameter(
                    "prerequisiteServiceKey", "prerequisiteService")));
            studioService = getServiceObject(StudioService.class, objectFactory.createObject(getParameter(
                    "studioServiceKey", "studioService")));

            logger = LogManager.getLog(getParameter("loggerName", "com.topcoder.widget.bridge.AjaxBridgeServlet"));

            // initialize the File Upload Configuration Object
            fileUploadConfig = cm.getConfiguration(getParameter("fileUploadNamespace", null));
        } catch (Exception e) {
            // see
            // http://forums.topcoder.com/?module=Thread&threadID=614361&start=0
            throw new ServletException(e);
        }
    }

    /**
     * <p>
     * Gets service object either using service locator or just service itself.
     * </p>
     * 
     * @param <T>
     *            type of the created service
     * @param clazz
     *            class of the desired type
     * @param service
     *            service object
     * @return service object
     * @throws Exception
     *             if the service is not the right type or any other error
     */
    private <T> T getServiceObject(Class<T> clazz, Object service) throws Exception {
        if (ServiceLocator.class.isAssignableFrom(service.getClass())) {
            return clazz.cast(((ServiceLocator) service).getService());
        } else if (clazz.isAssignableFrom(service.getClass())) {
            return clazz.cast(service);
        } else {
            throw new ClassCastException("The created service object is neither service locator.");
        }
    }

    /**
     * <p>
     * Handle the GET request. This method will simply call doPost(request,
     * response).
     * </p>
     * 
     * @param request
     *            the request object that comes from the web container
     * @param response
     *            the response object that comes from the web container
     * @throws ServletException
     *             this is for all the servlet related exceptions
     * @throws IOException
     *             this is for input / output errors when handling the servlet
     *             request
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        debug("Entering doGet() method.");
        // check first if request and response is not null
        // no longer needed to delegate if one of these parameters are null
        if (request == null || response == null) {
            logger.log(Level.ERROR, "The request or response object should not be null!");
            throw new ServletException("The request or response object should not be null!");
        }
        // delegate to doPost
        this.doPost(request, response);
        debug("Exiting doGet() method.");
    }

    /**
     * <p>
     * Handle the POST request. The request will contain parameters for
     * "service" (corresponds to which service should be called, its value can
     * be: "project", "payment" and "studio"), "method" (corresponds to the
     * method name of the matched service. ), and some other parameters
     * corresponding to the service method's arguments. The implementation will
     * call the matched service method with extracted parameters and get the
     * returned value. Then it will convert the returned value into json string
     * and write to the response.
     * </p>
     * 
     * @param request
     *            the request object that comes from the web container
     * @param response
     *            the response object that comes from the web container
     * @throws ServletException
     *             this is for all the servlet related exceptions
     * @throws IOException
     *             this is for input / output errors when handling the servlet
     *             request
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        debug("Entering doPost() method.");
        // check first if request and response is not null
        if (request == null || response == null) {
            logger.log(Level.ERROR, "The request or response object should not be null!");
            throw new ServletException("The request or response object should not be null!");
        }

        try {
            String service = null;
            String method = null;
            boolean isMultiPart = isMultipartContent(request);
            FileUploadResult result = null;

            if (isMultiPart) {
                MemoryFileUpload mfu = new MemoryFileUpload(fileUploadConfig);
                mfu.setSkipInvalidFiles(true);
                HttpRequestParser requestParser = new HttpRequestParser();
                result = mfu.uploadFiles(request, requestParser);
                service = result.getParameter(SERVICE_PARAM_KEY);
                method = result.getParameter(METHOD_PARAM_KEY);
            } else {
                service = request.getParameter(SERVICE_PARAM_KEY);
                method = request.getParameter(METHOD_PARAM_KEY);
            }
            if (checkIfNullOrEmpty(service, "service", response)) {
                return;
            }
            if (checkIfNullOrEmpty(method, "method", response)) {
                return;
            }

            debug("main parameters = [service] : " + service + " [method] : " + method);

            if ("project".equals(service)) {
                //**************************************************************
                // *******************************
                // ::Project Service::
                //**************************************************************
                // *******************************
                if ("createProject".equals(method)) {
                    String strProject = request.getParameter("project");
                    if (checkIfNullOrEmpty(strProject, "project", response)) {
                        return;
                    }
                    JSONObject jsonProj = jsonDecoder.decodeObject(strProject);
                    debug("received ID = [project id] : " + jsonProj.getLong("projectID"));

                    ProjectData project = getProjectFromJSON(jsonProj);
                    if (checkIfNullOrEmpty(project.getName(), "project name", response)) {
                        return;
                    }
                    ProjectData respProj = projectService.createProject(project);
                    JSONObject respJSON = getJSONFromProject(respProj);
                    sendJSONObjectAsResponse(respJSON, response);

                    debug("createProject success!");
                } else if ("updateProject".equals(method)) {
                    String strProject = request.getParameter("project");
                    if (checkIfNullOrEmpty(strProject, "project", response)) {
                        return;
                    }
                    JSONObject jsonProj = jsonDecoder.decodeObject(strProject);
                    debug("received ID = [project id] : " + jsonProj.getLong("projectID"));

                    ProjectData project = getProjectFromJSON(jsonProj);
                    projectService.updateProject(project);
                    ProjectData respProj = projectService.getProject(project.getProjectId());
                    JSONObject respJSON = getJSONFromProject(respProj);
                    sendJSONObjectAsResponse(respJSON, response);

                    debug("updateProject success!");
                } else if ("deleteProject".equals(method)) {
                    String strProject = request.getParameter("project");
                    if (checkIfNullOrEmpty(strProject, "project", response)) {
                        return;
                    }
                    JSONObject jsonProj = jsonDecoder.decodeObject(strProject);
                    debug("received ID = [project id] : " + jsonProj.getLong("projectID"));

                    ProjectData project = getProjectFromJSON(jsonProj);
                    projectService.deleteProject(project.getProjectId());
                    printSuccessResponse(getSuccessJSONResponse(), response);

                    debug("deleteProject success!");
                } else if ("getProject".equals(method)) {
                    String strProjectID = request.getParameter("projectID");
                    if (checkLongIfLessThanZero(strProjectID, "projectID", response)) {
                        return;
                    }
                    debug("received ID = [project id] : " + strProjectID);

                    ProjectData respProj = projectService.getProject(Long.parseLong(strProjectID));
                    JSONObject respJSON = getJSONFromProject(respProj);
                    sendJSONObjectAsResponse(respJSON, response);

                    debug("getProject success!");
                } else if ("getProjectsForUser".equals(method)) {
                    String userID = request.getParameter("userID");
                    if (checkLongIfLessThanZero(userID, "userID", response)) {
                        return;
                    }
                    debug("received ID = [user id] : " + userID);

                    List<ProjectData> projects = projectService.getProjectsForUser(Long.parseLong(userID));
                    JSONArray projectArr = new JSONArray();
                    for (ProjectData proj : projects) {
                        JSONObject respJSON = getJSONFromProject(proj);
                        projectArr.addJSONObject(respJSON);
                    }
                    sendJSONObjectWithArrayAsResponse(projectArr, response);

                    debug("getProjectsForUser success!");
                } else if ("getAllProjects".equals(method)) {
                    List<ProjectData> projects = projectService.getAllProjects();
                    JSONArray projectArr = new JSONArray();

                    for (ProjectData proj : projects) {
                        JSONObject respJSON = getJSONFromProject(proj);
                        projectArr.addJSONObject(respJSON);
                    }
                    debug("getAllProjects success!");
                    sendJSONObjectWithArrayAsResponse(projectArr, response);
                } else {
                    sendErrorJSONResponse("The 'method' param passed is invalid.", response);
                }

            } else if ("prerequisite".equals(service)) {
                //**************************************************************
                // *******************************
                // ::Prerequisite Service::
                //**************************************************************
                // *******************************
                if ("getAllPrerequisiteDocuments".equals(method)) {
                    debug("Handle request of getAllPrerequisiteDocuments");

                    List<PrerequisiteDocument> documents = prerequisiteService.getAllPrerequisiteDocuments();
                    JSONArray documentArray = new JSONArray();
                    for (PrerequisiteDocument document : documents) {
                        documentArray.addJSONObject(getJSONFromPrerequisteDocument(document));
                    }
                    sendJSONObjectWithArrayAsResponse(documentArray, response);

                    debug("getAllPrerequisiteDocuments success!");
                } else if ("getPrerequisiteDocuments".equals(method)) {
                    String paramCompetitionId = request.getParameter("competitionID");
                    String paramRoleId = request.getParameter("roleID");
                    if (checkLongIfLessThanZero(paramCompetitionId, "competitionID", response)) {
                        return;
                    }
                    if (checkLongIfLessThanZero(paramRoleId, "roleID", response)) {
                        return;
                    }
                    debug("received parameters = [competitionID] : " + paramCompetitionId + " [roleID] : "
                            + paramRoleId);

                    List<PrerequisiteDocument> documents = prerequisiteService.getPrerequisiteDocuments(Long
                            .parseLong(paramCompetitionId), Long.parseLong(paramRoleId));

                    JSONArray documentArray = new JSONArray();
                    for (PrerequisiteDocument document : documents) {
                        documentArray.addJSONObject(getJSONFromPrerequisteDocument(document));
                    }
                    sendJSONObjectWithArrayAsResponse(documentArray, response);

                    debug("getPrerequisiteDocuments success!");
                } else if ("getPrerequisiteDocument".equals(method)) {
                    String paramDocumentId = request.getParameter("documentID");
                    String paramVersion = request.getParameter("version");
                    if (checkLongIfLessThanZero(paramDocumentId, "documentID", response)) {
                        return;
                    }
                    if (checkIntegerIfLessThanZero(paramVersion, "version", response)) {
                        return;
                    }
                    debug("received parameters = [documentID] : " + paramDocumentId + " [version] : " + paramVersion);

                    PrerequisiteDocument document = prerequisiteService.getPrerequisiteDocument(Long
                            .parseLong(paramDocumentId), Integer.parseInt(paramVersion));
                    sendJSONObjectAsResponse(getJSONFromPrerequisteDocument(document), response);

                    debug("getPrerequisiteDocument success!");
                } else if ("recordMemberAnswer".equals(method)) {
                    String paramCompetitionId = request.getParameter("competitionID");
                    String paramTimestamp = request.getParameter("timestamp");
                    String paramAgrees = request.getParameter("agrees");
                    String paramRoleId = request.getParameter("roleID");
                    if (checkLongIfLessThanZero(paramCompetitionId, "competitionID", response)) {
                        return;
                    }
                    if (checkLongIfLessThanZero(paramRoleId, "roleID", response)) {
                        return;
                    }
                    JSONObject jsonDocument = jsonDecoder.decodeObject(request.getParameter("prerequisiteDocument"));
                    debug("received IDs = [competitionID] : " + paramCompetitionId + " [roleID] : " + paramRoleId);

                    prerequisiteService.recordMemberAnswer(Long.parseLong(paramCompetitionId),
                            getXMLGregorianCalendar(paramTimestamp), Boolean.parseBoolean(paramAgrees),
                            getPrerequisiteDocumentFromJSON(jsonDocument), Long.parseLong(paramRoleId));
                    printSuccessResponse(getSuccessJSONResponse(), response);

                    debug("recordMemberAnswer success!");
                } else {
                    // if we reach here this means the method param is invalid
                    sendErrorJSONResponse("The 'method' param passed is invalid.", response);
                }
            } else if ("studio".equals(service)) { // for STUDIO SERVICES
                //**************************************************************
                // *******************************
                // ::Studio Service::
                //**************************************************************
                // *******************************
                if ("createContest".equals(method)) {
                    String strContest = request.getParameter("contest");
                    String strProjectID = request.getParameter("projectID");
                    if (checkIfNullOrEmpty(strContest, "contest", response)) {
                        return;
                    }
                    if (checkLongIfLessThanZero(strProjectID, "projectID", response)) {
                        return;
                    }
                    debug("contest json received = " + strContest);

                    long projectID = Long.parseLong(strProjectID);
                    
                    // Get the project just to check that the user is authorized to use it
                    projectService.getProject(projectID);
                    
                    JSONObject jsonContest = jsonDecoder.decodeObject(strContest);
                    debug("received IDs = [contest ID] : " + jsonContest.getLong("contestID") + " [project ID] : "
                            + strProjectID);

                    ContestData contest = getContestFromJSON(jsonContest);
                    ContestData respContest = studioService.createContest(contest, projectID);
                    sendJSONObjectAsResponse(getJSONFromContest(respContest), response);

                    debug("createContest success!");
                } else if ("createContestPayment".equals(method)) {
                    String strContestPayment = request.getParameter("contestPayment");
                    if (checkIfNullOrEmpty(strContestPayment, "strContestPayment", response)) {
                        return;
                    }
                    debug("contest payment json received = " + strContestPayment);

                    JSONObject jsonContestPayment = jsonDecoder.decodeObject(strContestPayment);
                    debug("received IDs = [contest ID] : " + jsonContestPayment.getLong("contestId"));

                    ContestPaymentData contestPayment = getContestPaymentFromJSON(jsonContestPayment);
                
                    if (checkPaypalOrderIdFraud(contestPayment.getPaypalOrderId() + "",
                                                jsonContestPayment.getDouble("price") + "",
                                                jsonContestPayment.getLong("contestId") + "",
                                                request, response)) {
                        return;
                    }
                    
                    ContestPaymentData respContestPayment = studioService.createContestPayment(contestPayment);
                    sendJSONObjectAsResponse(getJSONFromContestPayment(respContestPayment), response);

                    debug("createContestPayment success!");
                } else if ("getContestPayment".equals(method)) {
                    String strContestPaymentID = request.getParameter("contestPaymentId");
                    if (checkLongIfLessThanZero(strContestPaymentID, "contestPaymentId", response)) {
                        return;
                    }
                    debug("received ID = [contest payment ID] : " + strContestPaymentID);

                    ContestPaymentData respContest = studioService.getContestPayment(Long
                            .parseLong(strContestPaymentID));
                    sendJSONObjectAsResponse(getJSONFromContestPayment(respContest), response);

                    debug("getContestPayment success!");
                } else if ("editContestPayment".equals(method)) {
                    String strContestPayment = request.getParameter("contestPayment");
                    if (checkIfNullOrEmpty(strContestPayment, "contestPayment", response)) {
                        return;
                    }
                    JSONObject jsonContestPayment = jsonDecoder.decodeObject(strContestPayment);
                    debug("received ID = [contestPayment Id] : " + jsonContestPayment.getLong("contestId"));

                    ContestPaymentData contestPayment = getContestPaymentFromJSON(jsonContestPayment);
                    studioService.editContestPayment(contestPayment);

                    JSONObject succJson = getSuccessJSONResponse();
                    printSuccessResponse(succJson, response);

                    debug("editContestPayment success!");
                } else if ("removeContestPayment".equals(method)) {
                    String strContestPaymentID = request.getParameter("contestPaymentID");
                    if (checkLongIfLessThanZero(strContestPaymentID, "contestPaymentID", response)) {
                        return;
                    }
                    debug("received ID = [contest payment ID] : " + strContestPaymentID);

                    studioService.removeContestPayment(Long.parseLong(strContestPaymentID));

                    JSONObject succJson = getSuccessJSONResponse();
                    printSuccessResponse(succJson, response);

                    debug("removeContestPayment success!");
                } else if ("getContest".equals(method)) {
                    String strContestID = request.getParameter("contestID");
                    if (checkLongIfLessThanZero(strContestID, "contestID", response)) {
                        return;
                    }
                    debug("received ID = [contest ID] : " + strContestID);

                    ContestData respContest = studioService.getContest(Long.parseLong(strContestID));
                    sendJSONObjectAsResponse(getJSONFromContest(respContest), response);

                    debug("getContest success!");
                } else if ("getAllContests".equals(method)) {
                	// onlyDirectProjects is ignored [TCCC-257] 
                    String strOnlyDirectProjects = request.getParameter("onlyDirectProjects");
                    if (checkBoolean(strOnlyDirectProjects, "onlyDirectProjects", response)) {
                        return;
                    }
                    List<ContestData> contests = studioService.getAllContests();

                    JSONArray contestArr = new JSONArray();

                    for (ContestData contest : contests) {
                        try {
                            JSONObject respJSON = getJSONFromContest(contest);
                            contestArr.addJSONObject(respJSON);
                        } catch (JSONDataAccessTypeException ex) {
                            error("JSONDataAccessTypeException " + ex.getMessage()
                                    + " occurred while parsed json from contest.");
                        } catch (JSONInvalidKeyException ex) {
                            error("JSONDataAccessTypeException " + ex.getMessage()
                                    + " occurred while parsed json from contest.");
                        } catch (IllegalArgumentException ex) {
                            error("JSONDataAccessTypeException " + ex.getMessage()
                                    + " occurred while parsed json from contest.");
                        } catch (RuntimeException ex) {
                            error("RuntimeException " + ex.getMessage() + " occurred while parsed json from contest.");
                        }
                    }
                    sendJSONObjectWithArrayAsResponse(contestArr, response);

                    debug("getAllContests success!");
                } else if ("getContestsForProject".equals(method)) {
                    String projectID = request.getParameter("projectID");
                    if (checkLongIfLessThanZero(projectID, "projectID", response)) {
                        return;
                    }
                    debug("received ID = [project ID] : " + projectID);

                    List<ContestData> contests = studioService.getContestsForProject(Long.parseLong(projectID));
                    JSONArray contestArr = new JSONArray();
                    for (ContestData contest : contests) {
                        try {
                            JSONObject respJSON = getJSONFromContest(contest);
                            contestArr.addJSONObject(respJSON);
                        } catch (JSONDataAccessTypeException ex) {
                            error("JSONDataAccessTypeException " + ex.getMessage()
                                    + " occurred while parsed json from contest.");
                        } catch (JSONInvalidKeyException ex) {
                            error("JSONDataAccessTypeException " + ex.getMessage()
                                    + " occurred while parsed json from contest.");
                        } catch (IllegalArgumentException ex) {
                            error("JSONDataAccessTypeException " + ex.getMessage()
                                    + " occurred while parsed json from contest.");
                        } catch (RuntimeException ex) {
                            error("RuntimeException " + ex.getMessage() + " occurred while parsed json from contest.");
                        }
                    }
                    sendJSONObjectWithArrayAsResponse(contestArr, response);

                    debug("getContestsForProject success!");
                } else if ("updateContestStatus".equals(method)) {
                    // get the contestID parameter from request
                    String strContestID = request.getParameter("contestID");
                    String strNewStatusID = request.getParameter("newStatusID");
                    if (checkLongIfLessThanZero(strContestID, "contestID", response)) {
                        return;
                    }
                    if (checkLongIfLessThanZero(strNewStatusID, "newStatusID", response)) {
                        return;
                    }
                    // log the received ID
                    debug("received IDs = [contest ID] : " + strContestID + " [new status ID] : " + strNewStatusID);

                    long contestId = Long.parseLong(strContestID);
                    studioService.updateContestStatus(contestId, Long.parseLong(strNewStatusID));
                    ContestData respContest = studioService.getContest(contestId);
                    sendJSONObjectAsResponse(getJSONFromContest(respContest), response);

                    debug("updateContestStatus success!");
                } else if ("getAllContestTypes".equals(method)) {
                    debug("start to get all contest types.");
                    List<ContestTypeData> types = studioService.getAllContestTypes();
                    JSONArray typeArr = new JSONArray();
                    for (ContestTypeData type : types) {
                        JSONObject respJSON = getJSONFromContestType(type);
                        typeArr.addJSONObject(respJSON);
                    }
                    sendJSONObjectWithArrayAsResponse(typeArr, response);

                    debug("getAllContestTypes success!");
                } else if ("getAllMedia".equals(method)) {
                    debug("start to get all media.");
                    List<MediumData> media = studioService.getAllMediums();
                    JSONArray mediumArr = new JSONArray();
                    for (MediumData medium : media) {
                        JSONObject respJSON = getJSONFromMedium(medium);
                        mediumArr.addJSONObject(respJSON);
                    }
                    sendJSONObjectWithArrayAsResponse(mediumArr, response);

                    debug("getAllMedia success!");
                } else if ("uploadDocumentForContest".equals(method)) {
                    // verify here again if the form we received is
                    // multipart/form-data
                    if (isMultiPart) {
                        // get the uploaded file
                        UploadedFile uploadedFile = result.getUploadedFile("document");

                        // Fix bug [TCCC-135]
                        // Initialize the UploadedDocument
                        String strDocument = request.getParameter("document");
                        JSONObject jsonDocument = jsonDecoder.decodeObject(strDocument);
                        UploadedDocument document = getDocumentUploadsFromJSON(jsonDocument);

                        // Fix bug [27074484-35]
                        long mimeTypeId = getMimeTypeId(uploadedFile);
                        if (mimeTypeId == -1) {
                            sendErrorJSONResponse("No matched mime type found in database for "
                                    + uploadedFile.getContentType(), response);
                            return;
                        }

                        document.setMimeTypeId(mimeTypeId);

                        String contestID = result.getParameter("contestID");
                        String documentID = result.getParameter("documentID");
                        if (checkLongIfLessThanZero(contestID, "contestID", response)) {
                            return;
                        }
                        // log the received ID
                        debug("received IDs = [contest ID] : " + contestID + " [document ID] : " + documentID);
                        // check if null
                        if (contestID != null) {
                            // set the contest ID
                            document.setContestId(Long.parseLong(contestID));
                        }
                        // check if null
                        if (documentID != null) {
                            // set the document ID
                            document.setDocumentId(Long.parseLong(documentID));
                        }
                        // set the File here
                        document.setFile(readContent(uploadedFile.getInputStream()));
                        // uploadDocumentForContest
                        UploadedDocument respDoc = studioService.uploadDocumentForContest(document);

                        // now create the corresponding JSON object for the
                        // response
                        JSONObject respJSON = getJSONFromUploadedDocument(respDoc);
                        debug("uploadDocumentForContest success!");
                        // send the JSONObject as a response
                        sendJSONObjectAsResponse(respJSON, response);

                    } else {
                        sendErrorJSONResponse("The 'enctype' of the request submitted"
                                + " should be of type 'multipart/*'", response);
                    }

                } else if ("removeDocumentFromContest".equals(method)) {
                    String strDocument = request.getParameter("document");
                    if (checkIfNullOrEmpty(strDocument, "document", response)) {
                        return;
                    }
                    JSONObject jsonDocument = jsonDecoder.decodeObject(strDocument);
                    debug("received ID = [document ID] : " + jsonDocument.getLong("documentID"));

                    UploadedDocument document = getDocumentUploadsFromJSON(jsonDocument);
                    studioService.removeDocumentFromContest(document);
                    JSONObject succJson = getSuccessJSONResponse();
                    printSuccessResponse(succJson, response);

                    debug("removeDocumentFromContest success!");
                } else if ("retrieveSubmissionsForContest".equals(method)) {
                    String contestID = request.getParameter("contestID");
                    if (checkLongIfLessThanZero(contestID, "contestID", response)) {
                        return;
                    }
                    debug("received ID = [contest ID] : " + contestID);

                    List<SubmissionData> submissions = studioService.retrieveSubmissionsForContest(Long
                            .parseLong(contestID));
                    JSONArray submissionArr = new JSONArray();

                    for (SubmissionData submission : submissions) {
                        JSONObject respJSON = getJSONFromSubmission(submission);
                        submissionArr.addJSONObject(respJSON);
                    }
                    sendJSONObjectWithArrayAsResponse(submissionArr, response);

                    debug("retrieveSubmissionsForContest success!");
                } else if ("updateSubmission".equals(method)) {
                    String strSubmission = request.getParameter("submission");
                    if (checkIfNullOrEmpty(strSubmission, "submission", response)) {
                        return;
                    }
                    JSONObject jsonSubmission = jsonDecoder.decodeObject(strSubmission);
                    debug("received ID = [submission ID] : " + jsonSubmission.getLong("submissionID"));

                    SubmissionData submission = getSubmissionFromJSON(jsonSubmission);
                    studioService.updateSubmission(submission);
                    JSONObject succJson = getSuccessJSONResponse();
                    printSuccessResponse(succJson, response);

                    debug("updateSubmission success!");
                } else if ("updateContest".equals(method)) {
                    String strContest = request.getParameter("contest");
                    if (checkIfNullOrEmpty(strContest, "contest", response)) {
                        return;
                    }
                    JSONObject jsonContest = jsonDecoder.decodeObject(strContest);
                    debug("received ID = [contest ID] : " + jsonContest.getLong("contestID"));

                    ContestData contest = getContestFromJSON(jsonContest);
                    studioService.updateContest(contest);

                    JSONObject succJson = getSuccessJSONResponse();
                    printSuccessResponse(succJson, response);

                    debug("updateContest success!");
                } else if ("retrieveSubmission".equals(method)) {
                    String strSubmissionID = request.getParameter("submissionID");
                    if (checkLongIfLessThanZero(strSubmissionID, "submissionID", response)) {
                        return;
                    }
                    debug("received ID = [submission ID] : " + strSubmissionID);

                    SubmissionData submission = studioService.retrieveSubmission(Long.valueOf(strSubmissionID));
                    if (submission == null) {
                        throw new StudioServiceException("There is no submission for submission id<" + strSubmissionID
                                + ">");
                    }
                    sendJSONObjectAsResponse(getJSONFromSubmission(submission), response);

                    debug("retrieveSubmission success!");
                } else if ("removeSubmission".equals(method)) {
                    String strSubmissionID = request.getParameter("submissionID");
                    if (checkLongIfLessThanZero(strSubmissionID, "submissionID", response)) {
                        return;
                    }
                    debug("received ID = [submission ID] : " + strSubmissionID);

                    studioService.removeSubmission(Long.parseLong(strSubmissionID));
                    printSuccessResponse(getSuccessJSONResponse(), response);

                    debug("removeSubmission success!");
                } else if ("removeDocument".equals(method)) {
                    String strDocumentId = request.getParameter("documentId");
                    if (checkLongIfLessThanZero(strDocumentId, "documentId", response)) {
                        return;
                    }
                    debug("received ID = [document ID] : " + strDocumentId);

                    studioService.removeDocument(Long.parseLong(strDocumentId));
                    printSuccessResponse(getSuccessJSONResponse(), response);

                    debug("removeDocument success!");
                } else if ("retrieveAllSubmissionsByMember".equals(method)) {
                    // get the userID parameter from request
                    String userID = request.getParameter("userID");
                    if (checkLongIfLessThanZero(userID, "userID", response)) {
                        return;
                    }
                    // log the received ID
                    debug("received ID = [user ID] : " + userID);

                    List<SubmissionData> subs = studioService.retrieveAllSubmissionsByMember(Long.parseLong(userID));
                    JSONArray submissionArr = new JSONArray();
                    for (SubmissionData submission : subs) {
                        JSONObject respJSON = getJSONFromSubmission(submission);
                        submissionArr.addJSONObject(respJSON);
                    }
                    sendJSONObjectWithArrayAsResponse(submissionArr, response);

                    debug("retrieveAllSubmissionsByMember success!");
                } else if ("getSubmissionFileTypes".equals(method)) {
                    String[] fileTypes = splitString(studioService.getSubmissionFileTypes());
                    JSONArray fileTypesArr = new JSONArray();

                    for (String fileType : fileTypes) {
                        fileTypesArr.addString(fileType);
                    }
                    sendJSONObjectWithArrayAsResponse(fileTypesArr, response);

                    debug("getSubmissionFileTypes success!");
                } else if ("getContestStatuses".equals(method)) {
                    List<ContestStatusData> contestStatuses = studioService.getStatusList();
                    JSONArray contestStatusArray = new JSONArray();

                    for (ContestStatusData contestStatus : contestStatuses) {
                        JSONObject respJSON = getJSONFromContestStatus(contestStatus);
                        contestStatusArray.addJSONObject(respJSON);
                    }
                    sendJSONObjectWithArrayAsResponse(contestStatusArray, response);

                    debug("getContestStatuses success!");
                } else if ("addDocumentToContest".equals(method)) {
                    // get the documentId and contestId parameter from request
                    String documentId = request.getParameter("documentId");
                    String contestId = request.getParameter("contestId");
                    if (checkLongIfLessThanZero(documentId, "documentId", response)) {
                        return;
                    }
                    if (checkLongIfLessThanZero(contestId, "contestId", response)) {
                        return;
                    }
                    // log the received ID
                    debug("received ID = [documen ID] : " + documentId);
                    debug("received ID = [contest ID] : " + contestId);

                    studioService.addDocumentToContest(Long.parseLong(documentId), Long.parseLong(contestId));

                    printSuccessResponse(getSuccessJSONResponse(), response);
                    debug("addDocumentToContest success!");
                } else if ("uploadDocument".equals(method)) {
                    // verify here again if the form we received is
                    // multipart/form-data
                    if (isMultiPart) {
                        String strDocument = request.getParameter("document");
                        if (checkIfNullOrEmpty(strDocument, "document", response)) {
                            return;
                        }
                        JSONObject jsonDocument = jsonDecoder.decodeObject(strDocument);
                        UploadedDocument document = getDocumentUploadsFromJSON(jsonDocument);

                        // get the uploaded file
                        UploadedFile uploadedFile = result.getUploadedFile("document");

                        // Fix bug [27074484-35]
                        long mimeTypeId = getMimeTypeId(uploadedFile);
                        if (mimeTypeId != -1) {
                            document.setMimeTypeId(mimeTypeId);
                        }

                        // Initialize the UploadedDocument
                        // set the File here
                        document.setFile(readContent(uploadedFile.getInputStream()));
                        // uploadDocumentForContest
                        UploadedDocument respDoc = studioService.uploadDocument(document);
                        // now create the corresponding JSON object for the
                        // response
                        JSONObject respJSON = getJSONFromUploadedDocument(respDoc);
                        debug("uploadDocument success!");
                        // send the JSONObject as a response
                        sendJSONObjectAsResponse(respJSON, response);
                    } else {
                        // if we reach here this means the method param is
                        // invalid
                        sendErrorJSONResponse("The 'method' param passed is invalid.", response);
                    }

                } else if ("purchaseSubmission".equals(method)) {
                    // get the submissionId and price parameter from request
                    String submissionId = request.getParameter("submissionId");
                    // [TCCC-125]
                    String payPalOrderId = request.getParameter("payPalOrderId");
                    if (checkLongIfLessThanZero(submissionId, "submissionId", response)) {
                        return;
                    }
                    if (checkPaypalOrderIdFraud(payPalOrderId, null, null, request, response)) {
                        return;
                    }

                    // log the received ID
                    debug("received ID = [submissionId ID] : " + submissionId);
                    debug("received payPalOrderId = [payPalOrderId] : " + payPalOrderId);

                    studioService.purchaseSubmission(Long.parseLong(submissionId), payPalOrderId);

                    printSuccessResponse(getSuccessJSONResponse(), response);
                    debug("purchaseSubmission success!");
                } else if ("setSubmissionPlacement".equals(method)) {
                    // get the submissionId and price parameter from request
                    String submissionId = request.getParameter("submissionId");
                    String placement = request.getParameter("placement");

                    if (checkLongIfLessThanZero(submissionId, "submissionId", response)) {
                        return;
                    }
                    if (checkLongIfLessThanZero(submissionId, "placement", response)) {
                        return;
                    }

                    // log the received ID
                    debug("received ID = [submissionId ID] : " + submissionId);
                    debug("received placement = [placement] : " + placement);

                    studioService.setSubmissionPlacement(Long.parseLong(submissionId), Integer.parseInt(placement));

                    printSuccessResponse(getSuccessJSONResponse(), response);
                    debug("setSubmissionPlacement success!");
                } else if ("markForPurchase".equals(method)) {
                    // get the submissionId and price parameter from request
                    String submissionId = request.getParameter("submissionId");

                    if (checkLongIfLessThanZero(submissionId, "submissionId", response)) {
                        return;
                    }

                    // log the received ID
                    debug("received ID = [submissionId ID] : " + submissionId);

                    studioService.markForPurchase(Long.parseLong(submissionId));

                    printSuccessResponse(getSuccessJSONResponse(), response);
                    debug("markForPurchase success!");
                } else {
                    // if we reach here this means the service param is invalid
                    sendErrorJSONResponse("The 'service' param passed is invalid.", response);
                }
            }
        } catch (IOException e) {
            logError(e, null);
            throw e;
        } catch (Exception e) {
            // see
            // http://forums.topcoder.com/?module=Thread&threadID=614361&start=0
            handleException(e, response);
        }

        debug("Exiting doPost() method.");
    }

    /**
     * <p>
     * Handles exception occurred in handling request.
     * </p>
     * 
     * @param exception
     *            to be dealt
     * @param response
     *            http response
     * @throws IOException
     *             any io error happens
     * @throws ServletException
     *             any servlet error
     */
    private void handleException(Exception exception, HttpServletResponse response) throws IOException,
            ServletException {
        try {
            logError(exception, null);
            sendErrorJSONResponse(exception.getClass().getSimpleName()
                    + " exception occurred while handling request : " + exception.getMessage(), response);
        } catch (IOException e) {
            // we caught the exception here for logging
            logError(e, "An exception occurred while sending an error message to the user. ");
            // throw it back again after logging
            throw e;
        } catch (JSONEncodingException e) {
            // we only log the error here
            logError(e, "An exception occurred while sending an error message to the user. ");
            // no need to send the error to the user since we
            // are already sending an error message to the user when the error
            // occurred, so it is useless to try it again.
            throw new ServletException(e);
        }
    }

    /**
     * <p>
     * Convenience method in getting a Contest object from a JSONObject.
     * </p>
     * 
     * @param jsonContest
     *            the JSONObject where the values will be coming from
     * @return the project object created from this json object
     * @throws ParseException
     *             when parsing the date gets an error
     * @throws JSONDecodingException
     *             when an exception occurs while decoding a json string
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private ContestData getContestFromJSON(JSONObject jsonContest) throws ParseException, JSONDecodingException {
        debug("received contest json string = " + jsonContest.toJSONString());
        ContestData contest = new ContestData();
        contest.setContestId(jsonContest.getLong("contestID"));
        contest.setProjectId(jsonContest.getLong("projectID"));
        contest.setName(jsonContest.getString("name"));
        contest.setShortSummary(jsonContest.getString("shortSummary"));
        contest.setDurationInHours(jsonContest.getDouble("durationInHours"));
        contest.setOtherFileFormats(jsonContest.getString("finalFileFormatOther"));
        contest.setStatusId(jsonContest.getLong("statusID"));
        contest.setContestDescriptionAndRequirements(jsonContest.getString("contestDescriptionAndRequirements"));
        contest.setRequiredOrRestrictedColors(jsonContest.getString("requiredOrRestrictedColors"));
        contest.setRequiredOrRestrictedFonts(jsonContest.getString("requiredOrRestrictedFonts"));
        contest.setSizeRequirements(jsonContest.getString("sizeRequirements"));
        contest.setOtherRequirementsOrRestrictions(jsonContest.getString("otherRequirementsOrRestrictions"));
        contest.setTcDirectProjectId(jsonContest.getLong("tcDirectProjectID"));
        contest.setCreatorUserId(jsonContest.getLong("creatorUserID"));
        contest.setLaunchDateAndTime(getXMLGregorianCalendar(jsonContest.getString("launchDateAndTime")));
        contest.setWinnerAnnoucementDeadline(getXMLGregorianCalendar(jsonContest
                .getString("winnerAnnouncementDeadline")));
        
        // [TCCC-284]
        contest.setRequiresPreviewFile(jsonContest.getBoolean("requiresPreviewFile"));
        contest.setRequiresPreviewImage(jsonContest.getBoolean("requiresPreviewImage"));
        contest.setMaximumSubmissions(jsonContest.getLong("maximumSubmissions"));

        contest.setContestTypeId(jsonContest.getLong("contestTypeID"));
        // [27128642-6]
        contest.setContestChannelId(jsonContest.getLong("contestChannelID"));
        // [TCCC-147]

        JSONArray jsonPrizes = jsonContest.getArray("prizes");
        if (jsonPrizes != null) {
            Object[] prizes = jsonPrizes.getObjects();
            List<PrizeData> listOfPrizes = new ArrayList<PrizeData>();
            if (prizes != null) {
                for (int i = 0; i < prizes.length; i++) {
                    listOfPrizes.add(getPrizeFromJSON((JSONObject) prizes[i]));
                }
            }
            contest.setPrizes(listOfPrizes);
        }

        JSONArray jsonFileFormat = jsonContest.getArray("finalFileFormatList");
        // check if it is null
        if (jsonFileFormat != null) {
            Object[] fileformat = jsonFileFormat.getObjects();
            List<String> fileFormats = new ArrayList<String>();
            if (fileformat != null) {
                for (int i = 0; i < fileformat.length; i++) {
                    fileFormats.add((String) fileformat[i]);
                }
            }
            contest.setFinalFileFormat(joinStrings(fileFormats));
        }

        JSONArray jsonUpDoc = jsonContest.getArray("documentationUploads");
        if (jsonUpDoc != null) {
            Object[] updocs = jsonUpDoc.getObjects();
            List<UploadedDocument> listOfUpDocs = new ArrayList<UploadedDocument>();
            if (updocs != null) {
                for (int i = 0; i < updocs.length; i++) {
                    listOfUpDocs.add(getDocumentUploadsFromJSON((JSONObject) updocs[i]));
                }
            }
            contest.setDocumentationUploads(listOfUpDocs);
        }

        JSONArray jsonPayloads = jsonContest.getArray("contestPayloads");
        if (jsonPayloads != null) {
            Object[] payloads = jsonPayloads.getObjects();
            List<ContestPayload> listOfPayloads = new ArrayList<ContestPayload>();
            if (payloads != null) {
                for (int i = 0; i < payloads.length; i++) {
                    listOfPayloads.add(getContestPayloadFromJSON((JSONObject) payloads[i]));
                }
            }
            contest.setContestPayloads(listOfPayloads);
        }

        JSONArray jsonMedia = jsonContest.getArray("media");
        if (jsonMedia != null) {
            Object[] media = jsonMedia.getObjects();
            List<MediumData> listOfMedium = new ArrayList<MediumData>();
            if (media != null) {
                for (int i = 0; i < media.length; i++) {
                    listOfMedium.add(getMediumFromJSON((JSONObject) media[i]));
                }
            }
            contest.setMedia(listOfMedium);
        }
        return contest;
    }

    /**
     * <p>
     * Convenience method in getting a Prize object from a JSONObject.
     * </p>
     * 
     * @param jsonPrize
     *            the JSONObject where the values will be coming from
     * @return the prize object created from this json object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private PrizeData getPrizeFromJSON(JSONObject jsonPrize) {
        PrizeData prize = new PrizeData();
        prize.setPlace(jsonPrize.getInt("place"));
        prize.setAmount(jsonPrize.getDouble("amount"));

        return prize;
    }

    /**
     * <p>
     * Convenience method in getting a JSON object from a Prize.
     * </p>
     * 
     * @param prize
     *            the Prize object where the values will be coming from
     * @return the json object created from this prize object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private JSONObject getJSONFromPrize(PrizeData prize) {
        // initialize the JSON object using the Prize
        JSONObject respJSON = new JSONObject();
        respJSON.setInt("place", prize.getPlace());
        respJSON.setDouble("amount", prize.getAmount());
        return respJSON;
    }

    /**
     * <p>
     * Convenience method in getting a UploadedDocument object from a
     * JSONObject.
     * </p>
     * 
     * @param jsonUpDoc
     *            the JSONObject where the values will be coming from
     * @return the uploaded document object created from this json object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private UploadedDocument getDocumentUploadsFromJSON(JSONObject jsonUpDoc) {
        UploadedDocument upDoc = new UploadedDocument();
        upDoc.setContestId(jsonUpDoc.getLong("contestID"));
        upDoc.setDescription(jsonUpDoc.getString("description"));
        upDoc.setDocumentId(jsonUpDoc.getLong("documentID"));
        upDoc.setFileName(jsonUpDoc.getString("fileName"));
        upDoc.setPath(jsonUpDoc.getString("path"));
        upDoc.setDocumentTypeId(jsonUpDoc.getLong("documentTypeID"));
        
        if (jsonUpDoc.isKeyDefined("mimeTypeID")) {
        	upDoc.setMimeTypeId(jsonUpDoc.getLong("mimeTypeID"));
        }
        return upDoc;
    }

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
        respJSON.setLong("mimeTypeID", upDoc.getMimeTypeId());
        return respJSON;
    }

    /**
     * <p>
     * Convenience method in getting a Contest Payload object from a JSONObject.
     * </p>
     * 
     * @param jsonPayload
     *            the JSONObject where the values will be coming from
     * @return the contest payload object created from this json object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private ContestPayload getContestPayloadFromJSON(JSONObject jsonPayload) {
        ContestPayload payload = new ContestPayload();
        payload.setName(jsonPayload.getString("name"));
        payload.setValue(jsonPayload.getString("value"));
        payload.setRequired(jsonPayload.getBoolean("required"));
        payload.setDescription(jsonPayload.getString("description"));
        payload.setContestTypeId(jsonPayload.getLong("contestTypeID"));
        return payload;
    }

    /**
     * <p>
     * Convenience method in getting a medium object from a JSONObject.
     * </p>
     * 
     * @param jsonMedium
     *            the JSONObject where the values will be coming from
     * @return the contest medium object created from this json object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private MediumData getMediumFromJSON(JSONObject jsonMedium) {
        MediumData medium = new MediumData();
        medium.setMediumId(jsonMedium.getLong("mediumId"));
        medium.setDescription(jsonMedium.getString("description"));
        return medium;
    }
    
    /**
     * <p>
     * Convenience method in getting a JSON object from an ContestPayload.
     * </p>
     * 
     * @param payload
     *            the ContestPayload object where the values will be coming from
     * @return the json object created from this payload object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private JSONObject getJSONFromContestPayload(ContestPayload payload) {
        JSONObject respJSON = new JSONObject();
        respJSON.setString("name", payload.getName());
        respJSON.setString("value", payload.getValue());
        respJSON.setBoolean("required", payload.isRequired());
        respJSON.setString("description", getString(payload.getDescription()));
        respJSON.setLong("contestTypeID", payload.getContestTypeId());
        return respJSON;
    }

    /**
     * <p>
     * Convenience method in getting a JSON object from an Medium.
     * </p>
     * 
     * @param medium
     *            the MediumData object where the values will be coming from
     * @return the json object created from this medium object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private JSONObject getJSONFromMedium(MediumData medium) {
        JSONObject respJSON = new JSONObject();
        respJSON.setString("description", medium.getDescription());
        respJSON.setLong("mediumId", medium.getMediumId());
        return respJSON;
    }

    /**
     * <p>
     * Convenience method in getting a JSON object from an Contest Status.
     * </p>
     * 
     * @param contestStatus
     *            the Contest Status object where the values will be coming from
     * @return the json object created from this contest status object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private JSONObject getJSONFromContestStatus(ContestStatusData contestStatus) {
        JSONObject respJSON = new JSONObject();
        respJSON.setLong("statusID", contestStatus.getStatusId());
        respJSON.setString("name", contestStatus.getName());
        respJSON.setString("description", getString(contestStatus.getDescription()));
        respJSON.setString("displayIcon", getString(contestStatus.getDisplayIcon()));

        JSONArray allowableNextStatuses = new JSONArray();
        // iterate over the allowable statuses
        List<Long> statuses = contestStatus.getAllowableNextStatus();
        for (Long status : statuses) {
            allowableNextStatuses.addLong(status);
        }
        // set in json object
        respJSON.setArray("allowableNextStatuses", allowableNextStatuses);

        return respJSON;
    }

    /**
     * <p>
     * Convenience method in getting a JSON object from a Contest.
     * </p>
     * 
     * @param contest
     *            the Contest object where the values will be coming from
     * @return the json object created from this contest object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private JSONObject getJSONFromContest(ContestData contest) {
        debug("getJSONFromContest - contestID=" + contest.getContestId());

        // initialize the JSON object using the Contest
        JSONObject respJSON = new JSONObject();
        respJSON.setLong("contestID", contest.getContestId());
        respJSON.setLong("projectID", contest.getProjectId());
        respJSON.setString("name", contest.getName());
        respJSON.setString("shortSummary", contest.getShortSummary());
        respJSON.setDouble("durationInHours", contest.getDurationInHours());
        respJSON.setString("finalFileFormatOther", getString(contest.getOtherFileFormats()));
        respJSON.setLong("statusID", contest.getStatusId());
        respJSON.setString("contestDescriptionAndRequirements", getString(contest.getContestDescriptionAndRequirements()));
        respJSON.setString("requiredOrRestrictedColors", getString(contest.getRequiredOrRestrictedColors()));
        respJSON.setString("requiredOrRestrictedFonts", getString(contest.getRequiredOrRestrictedFonts()));
        respJSON.setString("sizeRequirements", getString(contest.getSizeRequirements()));
        respJSON.setString("otherRequirementsOrRestrictions", getString(contest.getOtherRequirementsOrRestrictions()));
        respJSON.setLong("tcDirectProjectID", contest.getTcDirectProjectId());
        respJSON.setLong("creatorUserID", contest.getCreatorUserId());
        respJSON.setLong("contestTypeID", contest.getContestTypeId()); 
        respJSON.setLong("contestChannelID", contest.getContestChannelId()); 

        respJSON.setString("launchDateAndTime", getDateString(contest.getLaunchDateAndTime()));
        respJSON.setString("winnerAnnouncementDeadline", getDateString(contest.getWinnerAnnoucementDeadline()));
        respJSON.setLong("submissionCount", contest.getSubmissionCount());
        respJSON.setLong("numberOfRegistrants", contest.getNumberOfRegistrants());

        respJSON.setBoolean("requiresPreviewFile", contest.isRequiresPreviewFile());
        respJSON.setBoolean("requiresPreviewImage", contest.isRequiresPreviewImage());
        respJSON.setLong("maximumSubmissions", contest.getMaximumSubmissions());
        
        // [TCCC-283]
        respJSON.setString("eligibility", contest.getEligibility());
        respJSON.setString("notesOnWinnerSelection", contest.getNotesOnWinnerSelection());
        respJSON.setString("prizeDescription", contest.getPrizeDescription());
        
        // set up ARRAYS
        // Prizes[]
        // setup the JSONArray of prizes
        JSONArray jsonPrizes = new JSONArray();
        List<PrizeData> prizes = contest.getPrizes();
        // check if it is null
        if (prizes != null) {
            for (PrizeData prize : prizes) {
                jsonPrizes.addJSONObject(getJSONFromPrize(prize));
            }
        }
        respJSON.setArray("prizes", jsonPrizes);

        // final file format list
        // setup the JSONArray of Final File Format List
        JSONArray jsonFileFormat = new JSONArray();
        String fileFormats = contest.getFinalFileFormat();
        // check if it is null
        if (fileFormats != null) {
            for (String fileFormat : splitString(fileFormats)) {
                jsonFileFormat.addString(fileFormat);
            }
        }
        respJSON.setArray("finalFileFormatList", jsonFileFormat);

        // document uploads
        // setup the JSONArray of Uploaded Document
        JSONArray jsonUpDoc = new JSONArray();
        List<UploadedDocument> uploadedDocs = contest.getDocumentationUploads();
        // check if it is null
        if (uploadedDocs != null) {
            for (UploadedDocument document : uploadedDocs) {
                jsonUpDoc.addJSONObject(getJSONFromUploadedDocument(document));
            }
        }
        respJSON.setArray("documentationUploads", jsonUpDoc);

        // contest payloads
        // setup the JSONArray of Contest Payloads
        JSONArray jsonPayloads = new JSONArray();
        List<ContestPayload> payloads = contest.getContestPayloads();
        // check if it is null
        if (payloads != null) {
            for (ContestPayload payload : payloads) {
                jsonPayloads.addJSONObject(getJSONFromContestPayload(payload));
            }
        }
        respJSON.setArray("contestPayloads", jsonPayloads);

        // contest media
        // setup the JSONArray of Contest media
        JSONArray jsonMedia = new JSONArray();
        List<MediumData> media = contest.getMedia();
        // check if it is null
        if (media != null) {
            for (MediumData medium : media) {
                jsonMedia.addJSONObject(getJSONFromMedium(medium));
            }
        }
        respJSON.setArray("media", jsonMedia);
        
        // TCCC-457
        respJSON.setLong("forumId", contest.getForumId());
        respJSON.setInt("forumPostCount", contest.getForumPostCount());

        return respJSON;
    }

    /**
     * <p>
     * Gets JSON string from contest type value.
     * </p>
     * 
     * @param type
     *            contest type data
     * @return JSON string
     */
    private JSONObject getJSONFromContestType(ContestTypeData type) {
        JSONObject json = new JSONObject();
        json.setLong("contestTypeID", type.getContestTypeId());
        json.setString("description", type.getDescription());
        json.setBoolean("requirePreviewFile", type.isRequirePreviewFile());
        // BUG in studio service
        json.setBoolean("requirePreviewImage", type.isRequirePreviewImage());

        // config : array of payloads
        JSONArray config = new JSONArray();
        if (type.getConfig() != null) {
            for (ContestPayload payload : type.getConfig()) {
                config.addJSONObject(getJSONFromContestPayload(payload));
            }
        }
        json.setArray("config", config);

        return json;
    }

    /**
     * <p>
     * Convenience method in getting a Project object from a JSONObject.
     * </p>
     * 
     * @param jsonProj
     *            the JSONObject where the values will be coming from
     * @return the project object created from this json object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private ProjectData getProjectFromJSON(JSONObject jsonProj) {
        // initialize the project using the JSON object
        ProjectData project = new ProjectData();
        project.setProjectId(jsonProj.getLong("projectID"));
        project.setName(jsonProj.getString("name"));
        project.setDescription(jsonProj.getString("description"));

        return project;
    }

    /**
     * <p>
     * Convenience method in getting a PrerequisiteDocument object from a
     * JSONObject.
     * </p>
     * 
     * @param json
     *            the JSONObject which will be used to construct the document
     * @return the document created from the json object
     * @throws ParseException
     *             if any parsing error
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private PrerequisiteDocument getPrerequisiteDocumentFromJSON(JSONObject json) throws ParseException {
        PrerequisiteDocument document = new PrerequisiteDocument();
        document.setDocumentId(json.getLong("documentID"));
        document.setVersion(json.getInt("version"));
        document.setVersionDate(getXMLGregorianCalendar(json.getString("versionDate")));
        document.setName(json.getString("name"));
        document.setContent(json.getString("content"));
        return document;
    }

    /**
     * <p>
     * Convenience method in getting a JSON object from a Project.
     * </p>
     * 
     * @param project
     *            the Project object where the values will be coming from
     * @return the json object created from this project object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     */
    private JSONObject getJSONFromProject(ProjectData project) {
        // initialize the JSON object using the Project
        JSONObject respJSON = new JSONObject();
        respJSON.setLong("projectID", project.getProjectId());
        respJSON.setString("name", project.getName());
        respJSON.setString("description", getString(project.getDescription()));

        return respJSON;
    }

    /**
     * <p>
     * Convenience method in getting a Submission object from a JSONObject.
     * </p>
     * 
     * @param jsonSubmission
     *            the JSONObject where the values will be coming from
     * @return the Submission object created from this json object
     * @throws ParseException
     *             when an exception occurs while parsing the date string
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private SubmissionData getSubmissionFromJSON(JSONObject jsonSubmission) throws ParseException {
        // initialize the Submission using the JSON object
        SubmissionData submission = new SubmissionData();
        submission.setSubmissionId(jsonSubmission.getLong("submissionID"));
        submission.setSubmitterId(jsonSubmission.getLong("submitterID"));
        submission.setSubmittedDate(getXMLGregorianCalendar(jsonSubmission.getString("submissionTimeStamp")));
        submission.setSubmissionContent(jsonSubmission.getString("submissionContent"));
        submission.setContestId(jsonSubmission.getLong("contestID"));
        submission.setPassedScreening(new Boolean(jsonSubmission.getBoolean("passedScreening")));
        submission.setPlacement(jsonSubmission.getInt("placement"));
        submission.setPaidFor(jsonSubmission.getBoolean("paidFor"));
        submission.setPrice(jsonSubmission.getDouble("price"));
        submission.setMarkedForPurchase(jsonSubmission.getBoolean("markedForPurchase"));
        submission.setPassedScreening(jsonSubmission.getBoolean("passedReview"));
        submission.setRemoved(jsonSubmission.getBoolean("removed"));

        return submission;
    }

    /**
     * <p>
     * Convenience method in getting a JSON object from a Submission.
     * </p>
     * 
     * @param submission
     *            the Submission object where the values will be coming from
     * @return the json object created from this Submission object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     */
    private JSONObject getJSONFromSubmission(SubmissionData submission) {
        // initialize the JSON object using the Submission
        JSONObject respJSON = new JSONObject();
        respJSON.setLong("submissionID", submission.getSubmissionId());
        respJSON.setLong("submitterID", submission.getSubmitterId());
        respJSON.setString("submissionTimeStamp", getDateString(submission.getSubmittedDate()));
        respJSON.setString("submissionContent", submission.getSubmissionContent());
        respJSON.setLong("contestID", submission.getContestId());
        respJSON.setBoolean("passedScreening", submission.isPassedScreening());
        // although passedReview is not existing in current version of
        // Submission,
        // this was still added per instruction of Architect
        respJSON.setBoolean("passedReview", false);
        respJSON.setInt("placement", submission.getPlacement());
        respJSON.setBoolean("paidFor", submission.isPaidFor());
        respJSON.setDouble("price", submission.getPrice());
        respJSON.setBoolean("markedForPurchase", submission.isMarkedForPurchase());
        //http://forums.topcoder.com/?module=Thread&threadID=614379&start=0&mc=1
        // #979408
        // make the removed as false all the time now
        respJSON.setBoolean("removed", submission.isRemoved());

        return respJSON;
    }

    /**
     * <p>
     * Convenience method to get a JSON object from a PrerequisiteDocument
     * instance.
     * </p>
     * 
     * @param document
     *            prerequisite document
     * @return the json object created from the document
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     */
    private JSONObject getJSONFromPrerequisteDocument(PrerequisiteDocument document) {
        JSONObject json = new JSONObject();
        json.setLong("documentID", document.getDocumentId());
        json.setInt("version", document.getVersion());
        json.setString("versionDate", getDateString(document.getVersionDate()));
        json.setString("name", document.getName());
        json.setString("content", document.getContent());
        return json;
    }

    /**
     * <p>
     * This is a helper method in getting the init parameters from the servlet
     * config. When the init parameter is null/empty and default value is null,
     * throw an <code>IllegalArgumentException</code> or if the default value is
     * not null, return the default value. If the init parameter is neither null
     * nor empty, return it.
     * </p>
     * 
     * <p>
     * A null value in defaultValue parameter means that the init parameter is
     * required.
     * </p>
     * 
     * @param name
     *            the name of the init parameter to retrieve
     * @param defaultValue
     *            the default value to use in case the init parameter is not
     *            found
     * @return the value of the init parameter defined in web.xml
     * @throws IllegalArgumentException
     *             when a required parameter is not supplied
     */
    private String getParameter(String name, String defaultValue) {
        String value = getServletConfig().getInitParameter(name);
        if ((value == null || "".equals(value.trim()))) {
            if (defaultValue == null) {
                throw new IllegalArgumentException("The init parameter '" + name + "' is null/empty");
            } else {
                return defaultValue;
            }
        } else {
            return value;
        }
    }

    /**
     * <p>
     * Convenience method for sending the response of this servlet to the entity
     * who initiated the request. This first properly setups the response object
     * before sending the response.
     * </p>
     * 
     * @param s
     *            the response to be send to the requesting entity
     * @param response
     *            the response object to be operated on
     * @throws IOException
     *             when an exception occurs while sending the response to the
     *             requesting entity
     */
    private void printAjaxResponseString(String s, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        // properly setups the response
        setupAjaxResponseHeaders(response);
        pw.print(s);
        pw.close();
    }

    /**
     * <p>
     * Convenience method for setting the Content Type of the response of this
     * servlet. This also in turn calls
     * <code>setupAjaxCommonResponseHeaders()</code> to setup the common HTTP
     * Headers needed for this response.
     * </p>
     * 
     * @param response
     *            the response object to be operated on
     * @throws IOException
     *             when an exception occurs while setting the Content Type of
     *             the response
     */
    private void setupAjaxResponseHeaders(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        setupAjaxCommonResponseHeaders(response);
    }

    /**
     * <p>
     * Convenience method for setting the HTTP Headers so that the response from
     * this servlet is not cached by the receiving browser.
     * </p>
     * 
     * @param response
     *            the response object where to set the HTTP Headers
     * @throws IOException
     *             when an exception occurs while setting the HTTP Headers
     */
    private void setupAjaxCommonResponseHeaders(HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "no-store");
    }

    /**
     * <p>
     * Convenience method for logging error that occurred in this servlet.
     * </p>
     * 
     * @param e
     *            the exception that occurred.
     * @param customErrorMsg
     *            the error message in string format
     */
    private void logError(Exception e, String customErrorMsg) {
        String errorMsg = customErrorMsg != null ? customErrorMsg : e.getClass().getSimpleName()
                + " exception occurred while handling request";
        logger.log(Level.ERROR, e, errorMsg + " : " + e.getMessage());
        // print the stack trace
        e.printStackTrace();
    }

    /**
     * <p>
     * Convenience method to get Success <code>JSONObject</code> to return to as
     * a response. This by default sets "success" to true.
     * </p>
     * 
     * @return json object that corresponds to a success operation.
     */
    private JSONObject getSuccessJSONResponse() {
        JSONObject json = new JSONObject();
        json.setBoolean("success", true);
        return json;
    }

    /**
     * <p>
     * Convenience method to get Error <code>JSONObject</code> to return to as a
     * response. This by default sets "success" to false.
     * </p>
     * 
     * @param errorMsg
     *            the error message to be sent back to the user
     * @param response
     *            the HttpServletResponse object where to get the writer for the
     *            response
     * @throws IOException
     *             this exception is propagated from the other methods called
     *             inside this method
     * @throws JSONEncodingException
     *             when an error occurred while encoding the json object
     */
    private void sendErrorJSONResponse(String errorMsg, HttpServletResponse response) throws IOException,
            JSONEncodingException {

        JSONObject json = new JSONObject();
        json.setBoolean("success", false);
        json.setString("error", errorMsg);

        printAjaxResponseString(jsonEncoder.encode(json), response);
    }

    /**
     * <p>
     * Convenience method to send a success JSONObject response to the
     * requesting entity.
     * </p>
     * 
     * @param respJSON
     *            the json object to be send as a response
     * @param response
     *            object where to get the print writer to send the response
     * 
     * @throws IOException
     *             this exception is propagated from the other methods called
     *             inside this method
     * @throws JSONEncodingException
     *             when an error occurred while encoding the json object
     */
    private void sendJSONObjectAsResponse(JSONObject respJSON, HttpServletResponse response) throws IOException,
            JSONEncodingException {
        // set the success message with json object
        JSONObject succJson = getSuccessJSONResponse();
        succJson.setNestedObject("json", respJSON);
        // send the success response
        printSuccessResponse(succJson, response);
    }

    /**
     * <p>
     * Convenience method to send a success JSONObject embedded with JSONArray
     * response to the requesting entity.
     * </p>
     * 
     * @param jsonArray
     *            the json array to be send as a response
     * @param response
     *            object where to get the print writer to send the response
     * 
     * @throws IOException
     *             this exception is propagated from the other methods called
     *             inside this method
     * @throws JSONEncodingException
     *             when an error occurred while encoding the json object
     */
    private void sendJSONObjectWithArrayAsResponse(JSONArray jsonArray, HttpServletResponse response)
            throws IOException, JSONEncodingException {
        // set the success message with json object
        JSONObject succJson = getSuccessJSONResponse();
        succJson.setArray("json", jsonArray);
        // send the success response
        printSuccessResponse(succJson, response);
    }

    /**
     * <p>
     * Convenience method to send a success response to the requesting entity.
     * Before the response is written, we first log the Ajax response.
     * </p>
     * 
     * @param succJson
     *            the json object to be send as a response
     * @param response
     *            object where to get the print writer to send the response
     * 
     * @throws IOException
     *             this exception is thrown when an error occurs while writing
     *             the response
     * @throws JSONEncodingException
     *             when an error occurred while encoding the json object
     */
    private void printSuccessResponse(JSONObject succJson, HttpServletResponse response) throws IOException,
            JSONEncodingException {
        // encode the json object for response
        String strResponse = jsonEncoder.encode(succJson);
        // log the ajax response
        logger.log(Level.INFO, "the Ajax Response is : " + strResponse);
        // now we encode the success response
        printAjaxResponseString(strResponse, response);
    }

    /**
     * <p>
     * Convenience method to check if request is multipart or not
     * </p>
     * <p>
     * Based from apache commons file upload.
     * </p>
     * 
     * @param request
     *            the HttpServletRequest
     * @return true if multipart, false if not
     */
    private boolean isMultipartContent(HttpServletRequest request) {
        if (!"post".equals(request.getMethod().toLowerCase())) {
            return false;
        }
        String contentType = request.getContentType();
        debug("\n\n\n content type : " + contentType);
        if (contentType == null) {
            return false;
        }
        if (contentType.toLowerCase().startsWith("multipart/")) {
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Reads the contents of an input stream into a byte[].
     * </p>
     * 
     * @param inputStream
     *            where the data will come from
     * @return byte array equivalent of the contents from the input stream
     * @throws IOException
     *             where there's a problem reading the input stream
     */
    private byte[] readContent(InputStream inputStream) throws IOException {
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

    /**
     * <p>
     * Checks if the parameter passed is null or empty.
     * </p>
     * 
     * @param param
     *            the parameter to be check
     * @param paramName
     *            the name of parameter
     * @param response
     *            where the response will be sent
     * @return true if it is null/empty, false if it is not null/empty
     * @throws JSONEncodingException
     *             when an error occurs in encoding the response
     * @throws IOException
     *             when an error occurs in sending the response
     */
    private boolean checkIfNullOrEmpty(String param, String paramName, HttpServletResponse response)
            throws JSONEncodingException, IOException {
        if (param == null || "".equals(param.trim())) {
            sendErrorJSONResponse("Invalid [" + paramName + "] parameter. It cannot be null or empty.", response);
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Checks if the parameter of long type passed is less than zero. This will
     * be used to check for parameters that needs to be greater than zero.
     * </p>
     * 
     * @param param
     *            the parameter to be check
     * @param paramName
     *            the name of parameter
     * @param response
     *            where the response will be sent
     * @return true if it is null/empty, false if it is not null/empty
     * @throws JSONEncodingException
     *             when an error occurs in encoding the response
     * @throws IOException
     *             when an error occurs in sending the response
     */
    private boolean checkLongIfLessThanZero(String param, String paramName, HttpServletResponse response)
            throws JSONEncodingException, IOException {
        return checkIfLessThanZero(param, paramName, true, response);
    }

    /**
     * <p>
     * Checks if the parameter of integer type passed is less than zero. This
     * will be used to check for parameters that needs to be greater than zero.
     * </p>
     * 
     * @param param
     *            the parameter to be check
     * @param paramName
     *            the name of parameter
     * @param response
     *            where the response will be sent
     * @return true if it is null/empty, false if it is not null/empty
     * @throws JSONEncodingException
     *             when an error occurs in encoding the response
     * @throws IOException
     *             when an error occurs in sending the response
     */
    private boolean checkIntegerIfLessThanZero(String param, String paramName, HttpServletResponse response)
            throws JSONEncodingException, IOException {
        return checkIfLessThanZero(param, paramName, false, response);
    }

    /**
     * <p>
     * Checks if the parameter passed is less than zero. This will be used to
     * check for parameters that needs to be greater than zero.
     * </p>
     * 
     * @param param
     *            the parameter to be check
     * @param name
     *            the name of parameter
     * @param isLongType
     *            true if it is long and false if it is integer
     * @param response
     *            where the response will be sent
     * @return true if it is null/empty, false if it is not null/empty
     * @throws JSONEncodingException
     *             when an error occurs in encoding the response
     * @throws IOException
     *             when an error occurs in sending the response
     */
    private boolean checkIfLessThanZero(String param, String name, boolean isLongType, HttpServletResponse response)
            throws JSONEncodingException, IOException {
        // check first if null or empty
        if (checkIfNullOrEmpty(param, name, response)) {
            return true;
        } else {
            try {
                if (isLongType && Long.parseLong(param) < 0 || !isLongType && Integer.parseInt(param) < 0) {
                    sendErrorJSONResponse("Invalid [" + name + "] parameter. It cannot be less than zero.", response);
                    return true;
                }
            } catch (NumberFormatException e) {
                sendErrorJSONResponse("Invalid [" + name + "] parameter of number type. It cannot have the value of ["
                        + param + "].", response);
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the parameter is a valid boolean value ('true' or 'false')
     * 
     * @param param
     *            the parameter to be check
     * @param name
     *            the name of parameter
     * @param response
     *            where the response will be sent
     * @return true if it is null/empty, false if it is not null/empty
     * @throws JSONEncodingException
     *             when an error occurs in encoding the response
     * @throws IOException
     *             when an error occurs in sending the response
     */
    private boolean checkBoolean(String param, String name, HttpServletResponse response) throws JSONEncodingException,
            IOException {

        if (checkIfNullOrEmpty(param, name, response)) {
            return true;
        }

        if (!(param.equals("true") || param.equals("false"))) {
            sendErrorJSONResponse("Invalid [" + name + "] parameter. It must be either 'true' or 'false'.", response);
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Checks if the parameter passed is less than zero. This will be used to
     * check for parameters that needs to be greater than or equal to zero.
     * </p>
     * 
     * @param param
     *            the parameter to be check
     * @param name
     *            the name of parameter
     * @param response
     *            where the response will be sent
     * @return false if it could be parsed to a valid non-negative double.
     * @throws JSONEncodingException
     *             when an error occurs in encoding the response
     * @throws IOException
     *             when an error occurs in sending the response
     */
    private boolean checkDoubleIfLessThanZero(String param, String name, HttpServletResponse response)
            throws JSONEncodingException, IOException {
        try {
            if (Double.parseDouble(param) < 0) {
                sendErrorJSONResponse("Invalid [" + name + "] parameter. It cannot be less than zero.", response);
                return true;
            }
        } catch (NumberFormatException e) {
            sendErrorJSONResponse("Invalid [" + name + "] parameter of double type. It cannot have the value of ["
                    + param + "].", response);
            return true;
        }

        return false;
    }

    /**
     * <p>Verifies that the specified data for <code>PayPal</code> order ID matches the one stored in current
     * application context to prevent fraud.</p>
     *
     * @param payPalOrderId a <code>String</code> providing the order ID ot be verified.
     * @param amount a <code>String</code> providing the payment amount to be verified. May be <code>null</code> in case
     *        amount verification has to be skipped.
     * @param contestId a <code>String</code> providing the ID of a contest associated with payment to be verified. May
     *        be <code>null</code> in case contest verification has to be skipped.
     * @param request an <code>HttpServletRequest</code> representing the incoming request from the client.
     * @param response a <code>HttpServletResponse</code> representing the outgoing response.
     * @return <code>true</code> if specified <code>PayPal</code> order ID does not match one stored in session or there
     *         is no active session currently; <code>false</code> otherwise.
     * @throws IOException if an I/O error occurs while sending response to client.
     * @throws JSONEncodingException if an error occurs while encoding JSON response.
     */
    private boolean checkPaypalOrderIdFraud(String payPalOrderId, String amount, String contestId,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws IOException, JSONEncodingException {
        boolean failed = false;
        String message = null;
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                ServletContext servletContext = session.getServletContext();
                PayPalResponseListener paypalListener
                    = (PayPalResponseListener) servletContext.getAttribute("paypalListener");
                String[] paypalOrderData = paypalListener.getPayPalOrderData(payPalOrderId);
                if (paypalOrderData != null) {
                    // Verify user principal name
                    final String paypalUserId = paypalOrderData[1];
                    final String currentUserName = request.getUserPrincipal().getName();
                    if (!currentUserName.equalsIgnoreCase(paypalUserId)) {
                        error("Paypal Order ID verification failed. Current user ID = [" + currentUserName
                              + "]. User ID returned from PayPal = [" + paypalUserId + "]");
                        failed = true;
                        message = "Wrong user";
                    } else {
                        // Verify user session ID
                        final String sessionId = paypalOrderData[0];
                        final String currentSessionId = session.getId();
                        if (!currentSessionId.equals(sessionId)) {
                            error("Paypal Order ID verification failed. Current session ID = [" + currentSessionId
                                  + "]. Session ID returned from PayPal = [" + sessionId + "]");
                            failed = true;
                            message = "Wrong session";
                        } else {
                            // Verify payment amount
                            if (amount != null) {
                                final Double numericAmount = new Double(amount);
                                final String paypalAmount = paypalOrderData[4];
                                final Double numericPaypalAmount = new Double(paypalAmount);
                                if (numericAmount.compareTo(numericPaypalAmount) != 0) {
                                    error("Paypal Order ID verification failed. Submitted payment amount = ["
                                          + numericAmount + "]. Payment amount returned by PayPal = ["
                                          + numericPaypalAmount + "]");
                                    failed = true;
                                    message = "Wrong payment amount";
                                }
                            }
                            // Verify contest ID
                            if (contestId != null) {
                                final String paypalContestId = paypalOrderData[2];
                                if (!contestId.equals(paypalContestId)) {
                                    error("Paypal Order ID verification failed. Submitted contest ID = ["
                                          + contestId + "]. Contest ID returned by PayPal = ["
                                          + paypalContestId + "]");
                                    failed = true;
                                    message = "Wrong contest";
                                }
                            }
                        }
                    }
                } else {
                    error("Paypal Order ID verification failed. Submitted PayPal order ID = [" + payPalOrderId + "] "
                          + "was not registered by PayPal first");
                    failed = true;
                    message = "Unknown order";
                }
            } else {
                error("Paypal Order ID verification failed. There is no active session.");
                failed = true;
                message = "No active session";
            }
        } catch (Exception e) {
            e.printStackTrace();
            error("Paypal Order ID verification failed. Unexpected error: " + e);
            failed = true;
            message = "Unexpected error";
        }
        if (failed) {
            sendErrorJSONResponse("Paypal Order ID verification failed [" + message + "]. "
                                  + "Please contact TopCoder support", response);
        }
        return failed;
    }

    /**
     * <p>
     * Converts date string into XMLGregorianCalendar instance. Returns null if
     * parameter is null or empty.
     * </p>
     * 
     * @param dateString
     *            Date string to convert
     * @return converted calendar instance
     * @throws ParseException
     *             if any parse error
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(String dateString) throws ParseException {
        if (dateString == null || dateString.trim().length() == 0) {
            return null;
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dateFormatter.parse(dateString));
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            // can't create calendar, return null
            return null;
        }
    }

    /**
     * <p>
     * Converts XMLGregorianCalendar date into standard java Date object.
     * Returns empty string if argument is null.
     * </p>
     * 
     * @param calendar
     *            calendar instance to convert
     * @return converted date string
     */
    private String getDateString(XMLGregorianCalendar calendar) {
        return calendar == null ? "" : dateFormatter.format(calendar.toGregorianCalendar().getTime());
    }

    /**
     * <p>
     * Join strings together as comma deliminated string.
     * </p>
     * 
     * @param strings
     *            string list to be joined
     * @return concatenated string
     */
    private String joinStrings(List<String> strings) {
        if (strings == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string).append(",");
        }

        // remove last ,
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    /**
     * <p>
     * Split a string into string array by ",". It is a reverse function of
     * above.
     * </p>
     * 
     * @param string
     *            to be split
     * @return array of strings
     */
    private String[] splitString(String string) {
        return (string == null || string.trim().length() == 0) ? new String[] {} : string.split(",");
    }

    /**
     * <p>
     * Logs debug information.
     * </p>
     * 
     * @param message
     *            debug message
     */
    private void debug(String message) {
        if (logger.isEnabled(Level.DEBUG)) {
            logger.log(Level.DEBUG, message);
        }
    }

    /**
     * <p>
     * Logs error information.
     * </p>
     * 
     * @param message
     *            error message
     */
    private void error(String message) {
        if (logger.isEnabled(Level.ERROR)) {
            logger.log(Level.ERROR, message);
        }
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

    /**
     * <p>
     * Get the matched mime type for uploaded file.
     * </p>
     * 
     * @param file
     *            uploaded file.
     * @return the matched mime type.
     * 
     * @throws PersistenceException
     *             if any error occurs when getting MimeType
     */
    private long getMimeTypeId(UploadedFile file) throws PersistenceException {
        return studioService.getMimeTypeId(file.getContentType());
    }

    /**
     * <p>
     * Convenience method in getting a ContestPayment object from a JSONObject.
     * </p>
     * 
     * @param jsonContestPayment
     *            the JSONObject where the values will be coming from
     * @return the project object created from this json object
     * @throws ParseException
     *             when parsing the date gets an error
     * @throws JSONDecodingException
     *             when an exception occurs while decoding a json string
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private ContestPaymentData getContestPaymentFromJSON(JSONObject jsonContestPayment) throws ParseException,
            JSONDecodingException {
        debug("received contest json string = " + jsonContestPayment.toJSONString());
        ContestPaymentData contestPayment = new ContestPaymentData();
        contestPayment.setContestId(jsonContestPayment.getLong("contestId"));
        contestPayment.setPaymentStatusId(jsonContestPayment.getLong("paymentStatusId"));
        contestPayment.setPaypalOrderId(jsonContestPayment.getString("paypalOrderId"));
        contestPayment.setPrice(jsonContestPayment.getDouble("price"));

        return contestPayment;
    }

    /**
     * <p>
     * Convenience method in getting a JSON object from a Prize.
     * </p>
     * 
     * @param contestPayment
     *            the Prize object where the values will be coming from
     * @return the json object created from this prize object
     * @throws IllegalArgumentException
     *             If any parameter is <code>null</code> or <code>value</code>
     *             is NaN, positive infinity, or negative infinity.
     * @throws JSONInvalidKeyException
     *             If there is no data for the given key.
     * @throws JSONDataAccessTypeException
     *             If the data associated with the key can not be retrieved as a
     *             long.
     */
    private JSONObject getJSONFromContestPayment(ContestPaymentData contestPayment) {
        // initialize the JSON object using the ContestPayment
        JSONObject respJSON = new JSONObject();
        respJSON.setLong("contestId", contestPayment.getContestId());
        respJSON.setString("paypalOrderId", contestPayment.getPaypalOrderId());
        respJSON.setLong("paymentStatusId", contestPayment.getPaymentStatusId());
        respJSON.setDouble("price", contestPayment.getPrice());
        return respJSON;
    }
}
