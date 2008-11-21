/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.bridge.management;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.topcoder.clients.webservices.ClientService;
import com.topcoder.clients.webservices.ClientServiceException;
import com.topcoder.clients.webservices.CompanyService;
import com.topcoder.clients.webservices.CompanyServiceException;
import com.topcoder.clients.webservices.ProjectService;
import com.topcoder.clients.webservices.ProjectServiceException;
import com.topcoder.clients.webservices.beans.AuthorizationFailedException;
import com.topcoder.clients.webservices.webserviceclients.ClientServiceClient;
import com.topcoder.clients.webservices.webserviceclients.CompanyServiceClient;
import com.topcoder.clients.webservices.webserviceclients.ProjectServiceClient;
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
 * part into parameters used to call API methods in the Client Project Management Service 1.0
 * component, which will make web service calls. Returned values should be encoded into the AJAX
 * response.
 * </p>
 * <p>
 * <b>Thread-Safety</b>: This class is mutable and not thread-safe.<br>
 * NOTE: Its mutable variables are all initialized in the init method and would never change
 * afterwards. So this class can be used thread-safely by the container.
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
 * var projectService = new js.topcoder.clients.bridge.management.ProjectService(url);
 *
 * // create a Project to update:
 * var project = new js.topcoder.clients.bridge.management.model.Project();
 * project.setID(&quot;1&quot;);
 * project.setName(&quot;Millennium Wheel&quot;);
 * project.setDescription(&quot;Creation of the Millennium Wheel Experience&quot;);
 * project.setModifyUsername(&quot;mess&quot;);
 * project.setModifyDate(new Date());
 * project.setIsActive(false);
 * // update project
 * projectService.updateProject(project, onSuccess, onError);
 * </pre>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class ClientProjectManagementAjaxBridgeServlet extends HttpServlet {

    /**
     * <P>
     * Represent the default projectServiceClient key.
     * </P>
     */
    private static final String DEFAULT_PROJECT_SERVICE_CLIENT_KEY = "projectServiceClient";
    /**
     * <P>
     * Represent the default clientServiceClient key.
     * </P>
     */
    private static final String DEFAULT_CLIENT_SERVICE_CLIENT_KEY = "clientServiceClient";
    /**
     * <P>
     * Represent the default companyServiceClient key.
     * </P>
     */
    private static final String DEFAULT_COMPANY_SERVICE_CLIENT_KEY = "companyServiceClient";
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
     * Used in doPost method.
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
     * Represents the project service (obtained through project service client).<br>
     * Initialized in init method, and never changed afterwards.<br>
     * Must be non-null after initialized.<br>
     * Used in doPost method.
     * </p>
     */
    private ProjectService projectService;

    /**
     * <p>
     * Represents the client service. (obtained through client service client).<br>
     * Initialized in init method, and never changed afterwards.<br>
     * Must be non-null after initialized.<br>
     * Used in doPost method.
     * </p>
     */
    private ClientService clientService;

    /**
     * <p>
     * Represents the company service. (obtained through company service client).<br>
     * Initialized in init method, and never changed afterwards.<br>
     * Must be non-null after initialized.<br>
     * Used in doPost method.
     * </p>
     */
    private CompanyService companyService;

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
    public ClientProjectManagementAjaxBridgeServlet() {
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

            // get the ProjectService using ProjectServiceClient
            ProjectServiceClient projectServiceClient = (ProjectServiceClient) createObjectByOB(config,
                    objectFactory, "projectServiceClientKey", DEFAULT_PROJECT_SERVICE_CLIENT_KEY);
            projectService = projectServiceClient.getProjectServicePort();
            // get the ClientService using the ClientServiceClient
            ClientServiceClient clientServiceClient = (ClientServiceClient) createObjectByOB(config,
                    objectFactory, "clientServiceClientKey", DEFAULT_CLIENT_SERVICE_CLIENT_KEY);
            clientService = clientServiceClient.getClientServicePort();
            // get the CompanyService using the CompanyServiceClient
            CompanyServiceClient companyServiceClient = (CompanyServiceClient) createObjectByOB(config,
                    objectFactory, "companyServiceClientKey", DEFAULT_COMPANY_SERVICE_CLIENT_KEY);
            companyService = companyServiceClient.getCompanyServicePort();

            String logName = config.getInitParameter("loggerName");
            // if it's not configured, use the full qualified class name of AjaxBridgeServlet class.
            if (logName == null || logName.trim().length() == 0) {
                logName = ClientProjectManagementAjaxBridgeServlet.class.getName();
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
        }

    }

    /**
     * <p>
     * Handle the POST request.<br>
     * The request will contain parameters for "service"<br>
     * (corresponds to which service should be called, its value can be:<br> - "ProjectService"<br> -
     * "ClientService"<br> - "CompanyService")<br>
     * and "method" (corresponds to the method name of the matched service), and some other
     * parameters<br>
     * corresponding to the service method's arguments which are of course specific to the service
     * and the method.<br>
     * The implementation will call the matched service method<br>
     * with extracted parameters and get the returned value.<br>
     * Then it will convert or encode the returned value into json string and write to the response.
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
        logger.log(Level.DEBUG, "Enter into method doPost");

        String service = request.getParameter("service");
        String method = request.getParameter("method");

        logger.log(Level.DEBUG, "The service name is [" + service + "]." + " The method name is [" + method
                + "].");

        PrintWriter pw = response.getWriter();
        String res = null;
        try {
            if ("ProjectService".equals(service)) {
                Project projectRes = null;
                String json = request.getParameter("project");
                logger.log(Level.DEBUG, "The prject json is [" + json + "].");
                Project project = jsonToProject(jsonDecoder.decodeObject(json));
                System.out.println("vvvvvvvvvvvvvvvvvv");
                if ("createProject".equals(method)) {
                    projectRes = projectService.createProject(project, project.getClient());
                } else if ("updateProject".equals(method)) {
                    projectRes = projectService.updateProject(project);
                } else if ("deleteProject".equals(method)) {
                    projectRes = projectService.deleteProject(project);
                } else if ("setProjectStatus".equals(method)) {
                    String statusStr = request.getParameter("status");
                    ProjectStatus status = jsonToProjectStatus(jsonDecoder.decodeObject(statusStr));
                    projectRes = projectService.setProjectStatus(project, status);
                }

                if (projectRes != null) {
                    res = createResultString(true, jsonEncoder.encode(projectToJson(projectRes)));
                } else {
                    res = createResultString(false, "The method [" + method
                            + "] is invalid in ProjectService.");
                }
            } else if ("ClientService".equals(service)) {
                Client clientRes = null;
                String json = request.getParameter("client");
                logger.log(Level.DEBUG, "The client json is [" + json + "].");
                Client client = jsonToClient(jsonDecoder.decodeObject(json));
                if ("createClient".equals(method)) {
                    clientRes = clientService.createClient(client);
                } else if ("updateClient".equals(method)) {
                    clientRes = clientService.updateClient(client);
                } else if ("deleteClient".equals(method)) {
                    clientRes = clientService.deleteClient(client);
                } else if ("setClientCodeName".equals(method)) {
                    String name = request.getParameter("name");
                    clientRes = clientService.setClientCodeName(client, name);
                } else if ("setClientStatus".equals(method)) {
                    String statusStr = request.getParameter("status");
                    ClientStatus status = jsonToClientStatus(jsonDecoder.decodeObject(statusStr));
                    clientRes = clientService.setClientStatus(client, status);
                }

                if (clientRes != null) {
                    res = createResultString(true, jsonEncoder.encode(clientToJson(clientRes)));
                } else {
                    res = createResultString(false, "The method [" + method
                            + "] is invalid in ClientService.");
                }
            } else if ("CompanyService".equals(service)) {
                Company companyRes = null;
                String json = request.getParameter("company");
                logger.log(Level.DEBUG, "The company json is [" + json + "].");
                Company company = jsonToCompany(jsonDecoder.decodeObject(json));
                if ("createCompany".equals(method)) {
                    companyRes = companyService.createCompany(company);
                } else if ("updateCompany".equals(method)) {
                    companyRes = companyService.updateCompany(company);
                } else if ("deleteCompany".equals(method)) {
                    companyRes = companyService.deleteCompany(company);
                }

                if (companyRes != null) {
                    res = createResultString(true, jsonEncoder.encode(companyToJson(companyRes)));
                } else {
                    res = createResultString(false, "The method [" + method
                            + "] is invalid in CompanyService.");
                }
            } else {
                res = createResultString(false, "The service [" + service + "] is invalid.");
            }
        } catch (AuthorizationFailedException e) {
            res = createResultString(false, "AuthorizationFailedException occurs. " + e.getMessage());
            logger.log(Level.ERROR, "The exception message is [" + res + "].");
        } catch (ProjectServiceException e) {
            res = createResultString(false, "ProjectServiceException occurs. " + e.getMessage());
            logger.log(Level.ERROR, "The exception message is [" + res + "].");
        } catch (ClientServiceException e) {
            res = createResultString(false, "ClientServiceException occurs. " + e.getMessage());
            logger.log(Level.ERROR, "The exception message is [" + res + "].");
        } catch (CompanyServiceException e) {
            res = createResultString(false, "CompanyServiceException occurs. " + e.getMessage());
            logger.log(Level.ERROR, "The exception message is [" + res + "].");
        } catch (JSONDecodingException e) {
            res = createResultString(false, "JSONDecodingException occurs. " + e.getMessage());
            logger.log(Level.ERROR, "The exception message is [" + res + "].");
        } catch (JSONEncodingException e) {
            res = createResultString(false, "JSONEncodingException occurs. " + e.getMessage());
            logger.log(Level.ERROR, "The exception message is [" + res + "].");
        }

        logger.log(Level.DEBUG, "The result string is [" + res + "].");
        response.setContentType("text/json");
        pw.print(res);
        pw.close();

        logger.log(Level.DEBUG, "Exit method doPost");
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
        logger.log(Level.DEBUG, "Enter into method doGet");

        // delegate to doPost
        doPost(request, response);

        logger.log(Level.DEBUG, "Exit method doGet");
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
            aeObj.setId(Long.parseLong(jsonObj.getString("id")));
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
        jsonObj.setString("id", aeObj.getId() + "");
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
    private JSONObject clientStatusToJSON(ClientStatus status) {
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
        if (jsonObj.isKeyDefined("status")) {
            client.setClientStatus(jsonToClientStatus(jsonObj.getNestedObject("status")));
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
            jsonObj.setNestedObject("status", clientStatusToJSON(client.getClientStatus()));
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
     * Transfer the json object into Project.
     * </p>
     *
     * @param jsonObj
     *            the json object
     * @return the Project instance
     */
    private Project jsonToProject(JSONObject jsonObj) {
        Project project = new Project();

        if (jsonObj.isKeyDefined("company")) {
            project.setCompany(jsonToCompany(jsonObj.getNestedObject("company")));
        }
        if (jsonObj.isKeyDefined("active")) {
            project.setActive(jsonObj.getBoolean("active"));
        }
        if (jsonObj.isKeyDefined("salesTax")) {
            project.setSalesTax(jsonObj.getDouble("salesTax"));
        }
        if (jsonObj.isKeyDefined("poBoxNumber")) {
            project.setPOBoxNumber(jsonObj.getString("poBoxNumber"));
        }
        if (jsonObj.isKeyDefined("paymentTermsId")) {
            project.setPaymentTermsId(jsonObj.getInt("paymentTermsId"));
        }
        if (jsonObj.isKeyDefined("description")) {
            project.setDescription(jsonObj.getString("description"));
        }
        if (jsonObj.isKeyDefined("status")) {
            project.setProjectStatus(jsonToProjectStatus(jsonObj.getNestedObject("status")));
        }
        if (jsonObj.isKeyDefined("client")) {
            project.setClient(jsonToClient(jsonObj.getNestedObject("client")));
        }
        if (jsonObj.isKeyDefined("childProjects")) {
            JSONArray childs = jsonObj.getArray("childProjects");
            List<Project> childProjects = new ArrayList<Project>();
            for (int i = 0; i < childs.getSize(); i++) {
                childProjects.add(jsonToProject(childs.getJSONObject(i)));
            }
            if (childProjects.size() != 0) {
                project.setChildProjects(childProjects);
            }
        }
        if (jsonObj.isKeyDefined("parentProjectId")) {
            project.setParentProjectId(Long.parseLong(jsonObj.getString("parentProjectId")));
        }
        jsonToAuditableEntity(project, jsonObj);

        return project;
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
            jsonObj.setNestedObject("status", projectStatusToJson(project.getProjectStatus()));
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
        jsonObj.setString("parentProjectId", project.getParentProjectId() + "");
        auditableEntityToJson(project, jsonObj);

        return jsonObj;
    }

    /**
     * <p>
     * Get init parameter from config then create the instance by ObjectFactory.
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
