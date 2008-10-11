/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.bridge.lookup;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.ClientStatusService;
import com.topcoder.clients.webservices.ClientStatusServiceException;
import com.topcoder.clients.webservices.LookupService;
import com.topcoder.clients.webservices.LookupServiceException;
import com.topcoder.clients.webservices.ProjectStatusService;
import com.topcoder.clients.webservices.ProjectStatusServiceException;
import com.topcoder.clients.webservices.beans.AuthorizationFailedException;
import com.topcoder.clients.webservices.webserviceclients.ClientStatusServiceClient;
import com.topcoder.clients.webservices.webserviceclients.LookupServiceClient;
import com.topcoder.clients.webservices.webserviceclients.ProjectStatusServiceClient;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.NamespaceConflictException;
import com.topcoder.configuration.persistence.UnrecognizedFileTypeException;
import com.topcoder.configuration.persistence.UnrecognizedNamespaceException;
import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONObject;
import com.topcoder.json.object.io.JSONDecoder;
import com.topcoder.json.object.io.JSONDecodingException;
import com.topcoder.json.object.io.JSONEncoder;
import com.topcoder.json.object.io.JSONEncodingException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This class extends the HttpServlet class, and it will decode AJAXrequests from the JavaScript
 * part into parameters used to call Web Service methods in the Client Project Lookup Services
 * component. Returned values should be encoded into the AJAX response.
 * </p>
 * <p>
 * <b>Thread-Safety</b>: This class is mutable and not thread-safe.<br>
 * But as its mutable variables are all initialized in the init method and would never change
 * afterwards, thus this class can be used thread-safely by the container.
 * </p>
 * <p>
 * Simple Usage:
 * </p>
 * <p>
 * This is HttpServlet, it's used in service. Following code is demo in client.
 * </p>
 *
 * <pre>
 * var url = &quot;http://localhost:8080/topcoder/servlet&quot;;
 * var lookupService = new js.topcoder.clients.bridge.lookup.LookupService(url);
 *
 * // Get Client By Id
 *
 * // get the client with id = 1
 * lookupService.retrieveClient(1, onSuccess1, onError1);
 * // get the client with id = 2
 * lookupService.retrieveClient(2, onSuccess2, onError2);
 *
 * // Update Project Status
 *
 * // create a ProjectStatus to update the name and description
 * var projectStatus = new js.topcoder.clients.bridge.lookup.entities.ProjectStatus();
 * projectStatus.setId(1);
 * projectStatus.setName(&quot;complete&quot;);
 * projectStatus.setDescription(&quot;The complete status.&quot;);
 * // update project status
 * var projectStatusService = new js.topcoder.clients.bridge.lookup.ProjectStatusService(url);
 * projectStatusService.updateProjectStatus(projectStatus, onSuccess3, onError3);
 * </pre>
 *
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
public class ClientProjectLookupServiceBridgeServlet extends HttpServlet {
    /**
     * <P>
     * Represent the default lookupServiceClient key.
     * </P>
     */
    private static final String DEFAULT_LOOKUP_SERVICE_CLIENT_KEY = "lookupServiceClient";
    /**
     * <P>
     * Represent the default clientStatusServiceClient key.
     * </P>
     */
    private static final String DEFAULT_CLIENT_STATUS_SERVICE_CLIENT_KEY = "clientStatusServiceClient";
    /**
     * <P>
     * Represent the default projectStatusServiceClient key.
     * </P>
     */
    private static final String DEFAULT_PROJECT_STATUS_SERVICE_CLIENT_KEY = "projectStatusServiceClient";
    /**
     * <P>
     * Represent the default jsonEncoder.
     * </P>
     */
    private static final String DEFAULT_JSON_ENCODER = "jsonEncoder";
    /**
     * <P>
     * Represent the default jsonDecoder.
     * </P>
     */
    private static final String DEFAULT_JSON_DECODER = "jsonDecoder";

    /**
     * <p>
     * Represents the JSONEncoder to encoder the json object into string.<br>
     * Initialized in init method, and never changed afterwards.<br>
     * Must be non-null after initialized.<br>
     * Used in doPost method.<br>
     * </p>
     */
    private JSONEncoder jsonEncoder;

    /**
     * <p>
     * Represents the JSONDecoder to decode string into json object.<br>
     * Initialized in init method, and never changed afterwards.<br>
     * Must be non-null after initialized.<br>
     * Used in doPost method.
     * </p>
     */
    private JSONDecoder jsonDecoder;

    /**
     * <p>
     * Represents the project status service.<br>
     * Initialized in init method, and never changed afterwards.<br>
     * Must be non-null after initialized.<br>
     * Used in doPost method.
     * </p>
     */
    private ProjectStatusService projectStatusService;

    /**
     * <p>
     * Represents the client status service.<br>
     * Initialized in init method, and never changed afterwards.<br>
     * Must be non-null after initialized.<br>
     * Used in doPost method.
     * </p>
     */
    private ClientStatusService clientStatusService;

    /**
     * <p>
     * Represents the lookup service.<br>
     * Initialized in init method, and never changed afterwards.<br>
     * Must be non-null after initialized.<br>
     * Used in doPost method.
     * </p>
     */
    private LookupService lookupService;

    /**
     * <p>
     * Represents the logger to do the logging.<br>
     * Initialized in init method, and never changed afterwards.<br>
     * Must be non-null after initialized.<br>
     * Used in doPost method.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ClientProjectLookupServiceBridgeServlet() {
        // Empty
    }

    /**
     * <p>
     * This will Initialize the servlet.
     * </p>
     *
     * @param config
     *            the ServletConfig object that contains configuration information for this servlet
     * @throws IllegalArgumentException
     *             if the config argument is null.
     * @throws ServletException
     *             if an exception occurs when initializing instance variables, or the configured
     *             value is invalid or missing
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        if (config == null) {
            throw new IllegalArgumentException("The config can not be null.");
        }

        try {
            // create configuration
            String configFile = config.getInitParameter("ajaxBridgeConfigFile");
            ConfigurationFileManager cm = new ConfigurationFileManager(configFile);
            // create object factory
            String ofNamespace = config.getInitParameter("objectFactoryNamespace");
            ConfigurationObject ofConfig = cm.getConfiguration(ofNamespace).getChild("default");
            ObjectFactory objectFactory = new ObjectFactory(new ConfigurationObjectSpecificationFactory(
                    ofConfig));

            // create encoder & decoder
            jsonEncoder = (JSONEncoder) createObjectByOB(config, objectFactory, "jsonEncoderKey",
                    DEFAULT_JSON_ENCODER);
            jsonDecoder = (JSONDecoder) createObjectByOB(config, objectFactory, "jsonDecoderKey",
                    DEFAULT_JSON_DECODER);

            // get the LookupService using LookupServiceClient
            LookupServiceClient lookupServiceClient = (LookupServiceClient) createObjectByOB(config,
                    objectFactory, "lookupServiceClientKey", DEFAULT_LOOKUP_SERVICE_CLIENT_KEY);
            lookupService = lookupServiceClient.getLookupServicePort();
            // get the ClientStatusService using the ClientStatusServiceClient
            ClientStatusServiceClient clientStatusServiceClient = (ClientStatusServiceClient) createObjectByOB(
                    config, objectFactory, "clientStatusServiceClientKey",
                    DEFAULT_CLIENT_STATUS_SERVICE_CLIENT_KEY);
            clientStatusService = clientStatusServiceClient.getClientStatusServicePort();
            // get the ProjectStatusService using the ProjectStatusServiceClient
            ProjectStatusServiceClient projectStatusServiceClient = (ProjectStatusServiceClient) createObjectByOB(
                    config, objectFactory, "projectStatusServiceClientKey",
                    DEFAULT_PROJECT_STATUS_SERVICE_CLIENT_KEY);
            projectStatusService = projectStatusServiceClient.getProjectStatusServicePort();

            String logName = config.getInitParameter("loggerName");
            // if it's not configured, use the full qualified class name of AjaxBridgeServlet class.
            if (logName == null || logName.trim().length() == 0) {
                logName = ClientProjectLookupServiceBridgeServlet.class.getName();
            }
            logger = LogManager.getLog(logName);

        } catch (ConfigurationParserException e) {
            throw new ServletException("ConfigurationParserException occurs", e);
        } catch (NamespaceConflictException e) {
            throw new ServletException("NamespaceConflictException occurs", e);
        } catch (UnrecognizedFileTypeException e) {
            throw new ServletException("UnrecognizedFileTypeException occurs", e);
        } catch (IOException e) {
            throw new ServletException("IOException occurs", e);
        } catch (UnrecognizedNamespaceException e) {
            throw new ServletException("UnrecognizedNamespaceException occurs", e);
        } catch (IllegalReferenceException e) {
            throw new ServletException("IllegalReferenceException occurs", e);
        } catch (SpecificationConfigurationException e) {
            throw new ServletException("SpecificationConfigurationException occurs", e);
        } catch (InvalidClassSpecificationException e) {
            throw new ServletException("InvalidClassSpecificationException occurs", e);
        } catch (ConfigurationAccessException e) {
            throw new ServletException("ConfigurationAccessException occurs", e);
        } catch (ClassCastException e) {
            throw new ServletException("ClassCastException occurs", e);
        } catch (IllegalArgumentException e) {
            throw new ServletException("IllegalArgumentException occurs", e);
        }

    }

    /**
     * <p>
     * Handle the POST request.<br>
     * The request will contain parameters for "service" (corresponds to which service should be
     * called, its value can be: "lookup", "clientStatus" and "projectStatus"), "method"
     * (corresponds to the method name of the matched service. ), and some other parameters
     * corresponding to the service method's arguments. The implementation will call the matched
     * service method with extracted parameters and get the returned value. Then it will convert the
     * returned value into json string and write to the response.
     * </p>
     *
     * @param request
     *            this is the request coming to the servlet
     * @param response
     *            this is the response that will be returned by the servlet
     * @throws IOException
     *             if an input or output error is detected when the servlet handles the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.DEBUG, "Enter into ClientProjectLookupServiceBridgeServlet#doPost");

        String service = request.getParameter("service");
        String method = request.getParameter("method");

        logger.log(Level.DEBUG, "The service name is [" + service + "]." + " The method name is [" + method
                + "].");

        PrintWriter pw = response.getWriter();
        String res;
        try {
            if ("lookup".equals(service)) {
                res = doLookupService(request, method);
            } else if ("clientStatus".equals(service)) {
                res = doClientStatusService(request, method);
            } else if ("projectStatus".equals(service)) {
                res = doProjectStatusService(request, method);
            } else {
                res = createResultString(false, "The service [" + service + "] is invalid.");
            }
        } catch (AuthorizationFailedException e) {
            res = createResultString(false, "AuthorizationFailedException occurs. " + e.getMessage());
            logger.log(Level.WARN, e, "The exception message is [" + res + "].");
        } catch (LookupServiceException e) {
            res = createResultString(false, "LookupServiceException occurs. " + e.getMessage());
            logger.log(Level.WARN, e, "The exception message is [" + res + "].");
        } catch (ClientStatusServiceException e) {
            res = createResultString(false, "ClientStatusServiceException occurs. " + e.getMessage());
            logger.log(Level.WARN, e, "The exception message is [" + res + "].");
        } catch (ProjectStatusServiceException e) {
            res = createResultString(false, "ProjectStatusServiceException occurs. " + e.getMessage());
            logger.log(Level.WARN, e, "The exception message is [" + res + "].");
        } catch (JSONDecodingException e) {
            res = createResultString(false, "JSONDecodingException occurs. " + e.getMessage());
            logger.log(Level.WARN, e, "The exception message is [" + res + "].");
        } catch (JSONEncodingException e) {
            res = createResultString(false, "JSONEncodingException occurs. " + e.getMessage());
            logger.log(Level.WARN, e, "The exception message is [" + res + "].");
        }

        logger.log(Level.DEBUG, "The result string is [" + res + "].");
        response.setContentType("text/json");
        pw.print(res);
        pw.close();

        logger.log(Level.DEBUG, "Exit ClientProjectLookupServiceBridgeServlet#doPost");
    }

    /**
     * <p>
     * Handle the GET request. This is essentially a delegation to the POST handling method.
     * </p>
     *
     * @param request
     *            this is the request coming to the servlet
     * @param response
     *            this is the response that will be returned by the servlet
     * @throws IOException
     *             if an input or output error is detected when the servlet handles the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.DEBUG, "Enter into ClientProjectLookupServiceBridgeServlet#doGet");

        // delegate to doPost
        doPost(request, response);

        logger.log(Level.DEBUG, "Exit ClientProjectLookupServiceBridgeServlet#doGet");
    }

    /**
     * <p>
     * Do look up service.
     * </p>
     *
     * @param request
     *            this is the request coming to the servlet
     * @param method
     *            the method name
     * @return the result string
     * @throws JSONDecodingException
     *             the exception while doing json decoding
     * @throws JSONEncodingException
     *             the exception while doing json encoding
     * @throws ClientStatusServiceException
     *             the exception while doing lookup service
     * @throws AuthorizationFailedException
     *             the exception while doing lookup service
     */
    private String doLookupService(HttpServletRequest request, String method) throws JSONDecodingException,
        JSONEncodingException, LookupServiceException, AuthorizationFailedException {
        Object lookupRes = null;
        String parameter = null;
        if ("retrieveClient".equals(method) || "getClientStatus".equals(method)
                || "retrieveProject".equals(method) || "retrieveCompany".equals(method)
                || "getProjectStatus".equals(method) || "retrieveCompany".equals(method)) {
            parameter = request.getParameter("id");
            logger.log(Level.DEBUG, "The lookup' parameter is [id=" + parameter + "].");
        } else if ("searchClientsByName".equals(method) || "searchProjectsByName".equals(method)
                || "searchCompaniesByName".equals(method)) {
            parameter = request.getParameter("name");
            logger.log(Level.DEBUG, "The lookup' parameter is [name=" + parameter + "].");
        } else if ("getClientsForStatus".equals(method)) {
            parameter = request.getParameter("clientStatus");
            logger.log(Level.DEBUG, "The lookup' parameter is [clientStatus=" + parameter + "].");
        } else if ("retrieveProjectsForClient".equals(method)) {
            parameter = request.getParameter("client");
            logger.log(Level.DEBUG, "The lookup' parameter is [client=" + parameter + "].");
        } else if ("getProjectsForStatus".equals(method)) {
            parameter = request.getParameter("projectStatus");
            logger.log(Level.DEBUG, "The lookup' parameter is [projectStatus=" + parameter + "].");
        } else if ("getClientsForCompany".equals(method) || "getProjectsForCompany".equals(method)) {
            parameter = request.getParameter("company");
            logger.log(Level.DEBUG, "The lookup' parameter is [company=" + parameter + "].");
        }

        if ("retrieveClient".equals(method)) {
            lookupRes = lookupService.retrieveClient(Long.parseLong(parameter));
        } else if ("retrieveAllClients".equals(method)) {
            lookupRes = lookupService.retrieveAllClients();
        } else if ("searchClientsByName".equals(method)) {
            lookupRes = lookupService.searchClientsByName(parameter);
        } else if ("getClientStatus".equals(method)) {
            lookupRes = lookupService.getClientStatus(Long.parseLong(parameter));
        } else if ("getAllClientStatuses".equals(method)) {
            lookupRes = lookupService.getAllClientStatuses();
        } else if ("getClientsForStatus".equals(method)) {
            lookupRes = lookupService.getClientsForStatus(jsonToClientStatus(jsonDecoder
                    .decodeObject(parameter)));
        } else if ("retrieveProject".equals(method)) {
            lookupRes = lookupService.retrieveProject(Long.parseLong(parameter));
        } else if ("retrieveProjectsForClient".equals(method)) {
            lookupRes = lookupService.retrieveProjectsForClient(jsonToClient(jsonDecoder
                    .decodeObject(parameter)));
        } else if ("retrieveAllProjects".equals(method)) {
            lookupRes = lookupService.retrieveAllProjects();
        } else if ("searchProjectsByName".equals(method)) {
            lookupRes = lookupService.searchProjectsByName(parameter);
        } else if ("getProjectStatus".equals(method)) {
            lookupRes = lookupService.getProjectStatus(Long.parseLong(parameter));
        } else if ("getAllProjectStatuses".equals(method)) {
            lookupRes = lookupService.getAllProjectStatuses();
        } else if ("getProjectsForStatus".equals(method)) {
            lookupRes = lookupService.getProjectsForStatus(jsonToProjectStatus(jsonDecoder
                    .decodeObject(parameter)));
        } else if ("retrieveCompany".equals(method)) {
            lookupRes = lookupService.retrieveCompany(Long.parseLong(parameter));
        } else if ("retrieveAllCompanies".equals(method)) {
            lookupRes = lookupService.retrieveAllCompanies();
        } else if ("searchCompaniesByName".equals(method)) {
            lookupRes = lookupService.searchCompaniesByName(parameter);
        } else if ("getClientsForCompany".equals(method)) {
            lookupRes = lookupService
                    .getClientsForCompany(jsonToCompany(jsonDecoder.decodeObject(parameter)));
        } else if ("getProjectsForCompany".equals(method)) {
            lookupRes = lookupService
                    .getProjectsForCompany(jsonToCompany(jsonDecoder.decodeObject(parameter)));
        }

        String res;
        if (lookupRes != null) {
            String jsonStr;
            if (lookupRes instanceof List) {
                // The result is List, change it into json string
                StringBuffer buf = new StringBuffer();
                buf.append("[");

                for (Object obj : (List<?>) lookupRes) {
                    buf.append(jsonEncoder.encode(objectToJson(obj)));
                    buf.append(", ");
                }

                if (((List<?>) lookupRes).size() != 0) {
                    buf.delete(buf.length() - 2, buf.length());
                }

                buf.append("]");
                jsonStr = buf.toString();
            } else {
                jsonStr = jsonEncoder.encode(objectToJson(lookupRes));
            }
            res = createResultString(true, jsonStr);
        } else {
            res = createResultString(false, "The method [" + method + "] is invalid in LookupService.");
        }
        return res;
    }

    /**
     * <p>
     * Do client status service.
     * </p>
     *
     * @param request
     *            this is the request coming to the servlet
     * @param method
     *            the method name
     * @return the result string
     * @throws JSONDecodingException
     *             the exception while doing json decoding
     * @throws JSONEncodingException
     *             the exception while doing json encoding
     * @throws ClientStatusServiceException
     *             the exception while doing client status service
     */
    private String doClientStatusService(HttpServletRequest request, String method)
        throws JSONDecodingException, JSONEncodingException, ClientStatusServiceException {
        ClientStatus clientStatusRes = null;
        String json = request.getParameter("clientStatus");
        logger.log(Level.DEBUG, "The client json is [" + json + "].");
        ClientStatus clientStatus = jsonToClientStatus(jsonDecoder.decodeObject(json));
        if ("createClientStatus".equals(method)) {
            clientStatusRes = clientStatusService.createClientStatus(clientStatus);
        } else if ("updateClientStatus".equals(method)) {
            clientStatusRes = clientStatusService.updateClientStatus(clientStatus);
        } else if ("deleteClientStatus".equals(method)) {
            clientStatusRes = clientStatusService.deleteClientStatus(clientStatus);
        }

        String res;
        if (clientStatusRes != null) {
            res = createResultString(true, jsonEncoder.encode(clientStatusToJson(clientStatusRes)));
        } else {
            res = createResultString(false, "The method [" + method + "] is invalid in ClientStatusService.");
        }
        return res;
    }

    /**
     * <p>
     * Do project status service.
     * </p>
     *
     * @param request
     *            this is the request coming to the servlet
     * @param method
     *            the method name
     * @return the result string
     * @throws JSONDecodingException
     *             the exception while doing json decoding
     * @throws JSONEncodingException
     *             the exception while doing json encoding
     * @throws ProjectStatusServiceException
     *             the exception while doing project status service
     */
    private String doProjectStatusService(HttpServletRequest request, String method)
        throws JSONDecodingException, JSONEncodingException, ProjectStatusServiceException {
        ProjectStatus projectStatusRes = null;
        String json = request.getParameter("projectStatus");
        logger.log(Level.DEBUG, "The projectStatus json is [" + json + "].");
        ProjectStatus projectStatus = jsonToProjectStatus(jsonDecoder.decodeObject(json));
        if ("createProjectStatus".equals(method)) {
            projectStatusRes = projectStatusService.createProjectStatus(projectStatus);
        } else if ("updateProjectStatus".equals(method)) {
            projectStatusRes = projectStatusService.updateProjectStatus(projectStatus);
        } else if ("deleteProjectStatus".equals(method)) {
            projectStatusRes = projectStatusService.deleteProjectStatus(projectStatus);
        }

        String res;
        if (projectStatusRes != null) {
            res = createResultString(true, jsonEncoder.encode(projectStatusToJson(projectStatusRes)));
        } else {
            res = createResultString(false, "The method [" + method + "] is invalid in ProjectStatusService.");
        }
        return res;
    }

    /**
     * <p>
     * Transfer the json object into AuditableEntity.
     * </p>
     *
     * @param aeObj
     *            the AuditableEntity object
     * @param jsonObj
     *            the json object
     */
    private void jsonToAuditableEntity(AuditableEntity aeObj, JSONObject jsonObj) {
        if (jsonObj.isKeyDefined("id")) {
            aeObj.setId(jsonObj.getLong("id"));
        }
        if (jsonObj.isKeyDefined("createUsername")) {
            aeObj.setCreateUsername(jsonObj.getString("createUsername"));
        }
        if (jsonObj.isKeyDefined("createDate")) {
            aeObj.setCreateDate(new Date(Long.parseLong(jsonObj.getString("createDate"))));
        }
        if (jsonObj.isKeyDefined("modifyUsername")) {
            aeObj.setModifyUsername(jsonObj.getString("modifyUsername"));
        }
        if (jsonObj.isKeyDefined("modifyDate")) {
            aeObj.setModifyDate(new Date(Long.parseLong(jsonObj.getString("modifyDate"))));
        }
        if (jsonObj.isKeyDefined("name")) {
            aeObj.setName(jsonObj.getString("name"));
        }
        if (jsonObj.isKeyDefined("deleted")) {
            aeObj.setDeleted(jsonObj.getBoolean("deleted"));
        }
    }

    /**
     * <p>
     * Transfer the AuditableEntity into json object.
     * </p>
     *
     * @param aeObj
     *            the AuditableEntity object
     * @param jsonObj
     *            the json object
     */
    private void auditableEntityToJson(AuditableEntity aeObj, JSONObject jsonObj) {
        jsonObj.setLong("id", aeObj.getId());
        if (aeObj.getCreateUsername() != null) {
            jsonObj.setString("createUsername", aeObj.getCreateUsername());
        }
        if (aeObj.getCreateDate() != null) {
            jsonObj.setString("createDate", aeObj.getCreateDate().getTime() + "");
        }
        if (aeObj.getModifyUsername() != null) {
            jsonObj.setString("modifyUsername", aeObj.getModifyUsername());
        }
        if (aeObj.getModifyDate() != null) {
            jsonObj.setString("modifyDate", aeObj.getModifyDate().getTime() + "");
        }
        if (aeObj.getName() != null) {
            jsonObj.setString("name", aeObj.getName());
        }
        jsonObj.setBoolean("deleted", aeObj.isDeleted());
    }

    /**
     * <p>
     * Transfer the json object into Company.
     * </p>
     *
     * @param jsonObj
     *            the json object
     * @return the Company instance
     */
    private Company jsonToCompany(JSONObject jsonObj) {
        Company company = new Company();

        if (jsonObj.isKeyDefined("passcode")) {
            company.setPasscode(jsonObj.getString("passcode"));
        }
        jsonToAuditableEntity(company, jsonObj);

        return company;
    }

    /**
     * <p>
     * Transfer the Company into json object.
     * </p>
     *
     * @param company
     *            the Company instance
     * @return the json object
     */
    private JSONObject companyToJson(Company company) {
        JSONObject jsonObj = new JSONObject();

        if (company.getPasscode() != null) {
            jsonObj.setString("passcode", company.getPasscode());
        }
        auditableEntityToJson(company, jsonObj);

        return jsonObj;
    }

    /**
     * <p>
     * Transfer the json object into ClientStatus.
     * </p>
     *
     * @param jsonObj
     *            the json object
     * @return the ClientStatus instance
     */
    private ClientStatus jsonToClientStatus(JSONObject jsonObj) {
        ClientStatus status = new ClientStatus();

        if (jsonObj.isKeyDefined("description")) {
            status.setDescription(jsonObj.getString("description"));
        }
        jsonToAuditableEntity(status, jsonObj);

        return status;
    }

    /**
     * <p>
     * Transfer the ClientStatus into json object.
     * </p>
     *
     * @param status
     *            the ClientStatus instance
     * @return the json object
     */
    private JSONObject clientStatusToJson(ClientStatus status) {
        JSONObject jsonObj = new JSONObject();

        if (status.getDescription() != null) {
            jsonObj.setString("description", status.getDescription());
        }
        auditableEntityToJson(status, jsonObj);

        return jsonObj;
    }

    /**
     * <p>
     * Transfer the json object into Client.
     * </p>
     *
     * @param jsonObj
     *            the json object
     * @return the Client instance
     */
    private Client jsonToClient(JSONObject jsonObj) {
        Client client = new Client();

        if (jsonObj.isKeyDefined("company")) {
            client.setCompany(jsonToCompany(jsonObj.getNestedObject("company")));
        }
        if (jsonObj.isKeyDefined("paymentTermsId")) {
            client.setPaymentTermsId(jsonObj.getLong("paymentTermsId"));
        }
        if (jsonObj.isKeyDefined("clientStatus")) {
            client.setClientStatus(jsonToClientStatus(jsonObj.getNestedObject("clientStatus")));
        }
        if (jsonObj.isKeyDefined("salesTax")) {
            client.setSalesTax(jsonObj.getDouble("salesTax"));
        }
        if (jsonObj.isKeyDefined("startDate")) {
            client.setStartDate(new Date(Long.parseLong(jsonObj.getString("startDate"))));
        }
        if (jsonObj.isKeyDefined("endDate")) {
            client.setEndDate(new Date(Long.parseLong(jsonObj.getString("endDate"))));
        }
        if (jsonObj.isKeyDefined("codeName")) {
            client.setCodeName(jsonObj.getString("codeName"));
        }
        jsonToAuditableEntity(client, jsonObj);

        return client;
    }

    /**
     * <p>
     * Transfer the Client into json object.
     * </p>
     *
     * @param client
     *            the Client instance
     * @return the json object
     */
    private JSONObject clientToJson(Client client) {
        JSONObject jsonObj = new JSONObject();

        if (client.getCompany() != null) {
            jsonObj.setNestedObject("company", companyToJson(client.getCompany()));
        }
        jsonObj.setLong("paymentTermsId", client.getPaymentTermsId());
        if (client.getClientStatus() != null) {
            jsonObj.setNestedObject("clientStatus", clientStatusToJson(client.getClientStatus()));
        }
        jsonObj.setDouble("salesTax", client.getSalesTax());
        if (client.getStartDate() != null) {
            jsonObj.setString("startDate", client.getStartDate().getTime() + "");
        }
        if (client.getEndDate() != null) {
            jsonObj.setString("endDate", client.getEndDate().getTime() + "");
        }
        if (client.getCodeName() != null) {
            jsonObj.setString("codeName", client.getCodeName());
        }
        auditableEntityToJson(client, jsonObj);

        return jsonObj;
    }

    /**
     * <p>
     * Transfer the json object into ProjectStatus.
     * </p>
     *
     * @param jsonObj
     *            the json object
     * @return the ProjectStatus instance
     */
    private ProjectStatus jsonToProjectStatus(JSONObject jsonObj) {
        ProjectStatus status = new ProjectStatus();

        if (jsonObj.isKeyDefined("description")) {
            status.setDescription(jsonObj.getString("description"));
        }
        jsonToAuditableEntity(status, jsonObj);

        return status;
    }

    /**
     * <p>
     * Transfer the ProjectStatus into json object.
     * </p>
     *
     * @param status
     *            the ProjectStatus instance
     * @return the json object
     */
    private JSONObject projectStatusToJson(ProjectStatus status) {
        JSONObject jsonObj = new JSONObject();

        if (status.getDescription() != null) {
            jsonObj.setString("description", status.getDescription());
        }
        auditableEntityToJson(status, jsonObj);

        return jsonObj;
    }

    /**
     * <p>
     * Transfer the Project into json object.
     * </p>
     *
     * @param project
     *            the Project instance
     * @return the json object
     */
    private JSONObject projectToJson(Project project) {
        JSONObject jsonObj = new JSONObject();

        if (project.getCompany() != null) {
            jsonObj.setNestedObject("company", companyToJson(project.getCompany()));
        }
        jsonObj.setBoolean("active", project.isActive());
        jsonObj.setDouble("salesTax", project.getSalesTax());
        if (project.getPOBoxNumber() != null) {
            jsonObj.setString("poBoxNumber", project.getPOBoxNumber());
        }
        jsonObj.setLong("paymentTermsId", project.getPaymentTermsId());
        if (project.getDescription() != null) {
            jsonObj.setString("description", project.getDescription());
        }
        if (project.getProjectStatus() != null) {
            jsonObj.setNestedObject("projectStatus", projectStatusToJson(project.getProjectStatus()));
        }
        if (project.getClient() != null) {
            jsonObj.setNestedObject("client", clientToJson(project.getClient()));
        }
        if (project.getChildProjects() != null && project.getChildProjects().size() != 0) {
            JSONArray childs = new JSONArray();
            for (Project child : project.getChildProjects()) {
                childs.addJSONObject(projectToJson(child));
            }
            jsonObj.setArray("childProjects", childs);
        }
        jsonObj.setLong("parentProjectId", project.getParentProjectId());
        auditableEntityToJson(project, jsonObj);

        return jsonObj;
    }

    /**
     * <p>
     * Check the object type and transfer it into JSONObject.
     * </p>
     *
     * @param obj
     *            the object
     * @return the json object
     */
    private JSONObject objectToJson(Object obj) {
        JSONObject jsonObj = new JSONObject();

        if (obj instanceof Client) {
            jsonObj = clientToJson((Client) obj);
        } else if (obj instanceof ClientStatus) {
            jsonObj = clientStatusToJson((ClientStatus) obj);
        } else if (obj instanceof Project) {
            jsonObj = projectToJson((Project) obj);
        } else if (obj instanceof ProjectStatus) {
            jsonObj = projectStatusToJson((ProjectStatus) obj);
        } else if (obj instanceof Company) {
            jsonObj = companyToJson((Company) obj);
        }

        return jsonObj;
    }

    /**
     * <p>
     * Get initial parameter from config then create the instance by ObjectFactory.
     * </p>
     *
     * @param config
     *            the servlet config
     * @param objectFactory
     *            the object factory instance
     * @param keyName
     *            the key name
     * @param defaultKey
     *            the default key name
     * @return the instance created by ObjectFactory
     * @throws InvalidClassSpecificationException
     *             if any error occurs while creating object
     */
    private Object createObjectByOB(ServletConfig config, ObjectFactory objectFactory, String keyName,
            String defaultKey) throws InvalidClassSpecificationException {
        String keyString = config.getInitParameter(keyName);
        if (keyString == null) {
            keyString = defaultKey;
        }
        return objectFactory.createObject(keyString);
    }

    /**
     * <p>
     * Create the result json string.
     * </p>
     *
     * @param success
     *            the success flag
     * @param message
     *            the message string
     * @return the json string
     */
    private String createResultString(boolean success, String message) {
        if (success) {
            return "{\"success\":true, \"json\":" + message + "}";
        } else {
            return "{\"success\":false, \"error\":" + message + "}";
        }
    }

}
