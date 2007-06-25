/*
 * Copyright (C) 2006-2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.ajax;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * Main servlet class of the component.
 * This class extends HttpServlet class in order to process Ajax requests and produce Ajax responses.
 *
 * This class keeps a map of all Ajax request handlers,
 * and when the doPost method is called it follows these steps to process the request:
 * <ol>
 * <li>Get the user ID from the HttpSession.</li>
 * <li>Parse the Ajax request XML stream to produce an AjaxRequest object.</li>
 * <li>Pass the user id and the Ajax request to the correct request handler.</li>
 * <li>Write back the Ajax response XML produced by the request handler.</li>
 * </ol>
 *
 * When the Ajax request is incorrect, invalid or the target handler was not found,
 * then this servlet will return an error response to the client,
 * and log that error using the Logging Wrapper component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong>
 * This class is immutable and thread safe. all accesses to its internal state are read only once.
 * </p>
 *
 * @author topgear
 * @author assistant
 * @author George1
 * @version 1.0.1
 */
public final class AjaxSupportServlet extends HttpServlet {
	/**
     * Represents the property name of handlers.
     */
    private static final String HANDLERS_PROPERTY = "Handlers";

    /**
     * Represents the property name of user id attribute.
     */
    private static final String USER_ID_PROPERTY_NAME = "UserIdAttributeName";

    /**
     * Represents the namespace to retrieve the properties.
     */
    private static final String NAMESPACE = "com.cronos.onlinereview.ajax";
    /**
     * The logger.
     */
    private static final Log logger = LogFactory.getLog(AjaxSupportServlet.class.getName());
    
    /**
     * <p>
     * The Ajax request handlers map, as defined in the configuration.
     * This variable is immutable, both the variable and its content.
     * It is filled by the "init" method with handlers data. The "destroy" method must clear its content.<br><br>
     * <ul>
     * <li>Map Keys - are of type String, they can't be null, or empty strings,
     *                they represents the operation name handled by the handler</li>
     * <li>Map Values - are of type AjaxRequestHandler, they can't be null</li>
     * </ul>
     * </p>
     */
    private final Map handlers = new HashMap();

    /**
     * <p>
     * Represents the user Id attribute's name used to store the user ID in the HttpSession.
     * This variable is mutable, it is initialized by the "init" method to a not null and not empty String,
     * but must never assigned a new value after that. the "destroy" method put it to null.
     * </p>
     */
    private String userIdAttributeName;

    /**
     * <p>
     * Creates an instance of this class.
     * </p>
     */
    public AjaxSupportServlet() {
        // do nothing
    }

    /**
     * <p>
     * Initialize the state of this servlet with configuration data.
     * </p>
     *
     * @param config the initial configuration of the servlet
     * @throws ServletException if an exception was caught
     */
    public void init(ServletConfig config) throws ServletException {

    	logger.log(Level.INFO, "Init Ajax Support Servlet from namespace:" + NAMESPACE);
        super.init(config);

        // create a new instance of ConfigManager class
        ConfigManager cm = ConfigManager.getInstance();

        try {
            // get the userId from the config manager
            this.userIdAttributeName = cm.getString(NAMESPACE, USER_ID_PROPERTY_NAME);

            if (userIdAttributeName == null || userIdAttributeName.trim().length() == 0) {
            	logger.log(Level.FATAL, "The UserIdAttributeName is missing in the namespace:" + NAMESPACE);
                throw new ServletException("The UserIdAttributeName is required.");
            }

            logger.log(Level.INFO, "Get property[" + USER_ID_PROPERTY_NAME
            		+ "] with value[" + userIdAttributeName + "] from namespace:" + NAMESPACE);
            ObjectFactory factory = AjaxSupportHelper.createObjectFactory();

            
            // get the list of all the Ajax request handlers names from the config manager
            String[] handlerNames = cm.getStringArray(NAMESPACE, HANDLERS_PROPERTY);
            if (handlerNames != null) {
            	for (int i = 0; i < handlerNames.length; i++) {
                    if (handlerNames[i] == null || handlerNames[i].trim().length() == 0) {
                    	logger.log(Level.FATAL, "The handler name should not be null/empty in namespace:" + NAMESPACE);
                        throw new ServletException("The handler name should not be null/empty.");
                    }
                    logger.log(Level.INFO, "Get property array [" + HANDLERS_PROPERTY
                    		+ "] with one value :" + handlerNames[i] + " from namespace:" + NAMESPACE);
                    AjaxRequestHandler handler = (AjaxRequestHandler) factory.createObject(handlerNames[i]);
                    this.handlers.put(handlerNames[i], handler);
                }
            }
        } catch (UnknownNamespaceException e) {
        	logger.log(Level.FATAL, "The namespace[" + NAMESPACE + "] is not loaded.");
            throw new ServletException("The namespace can't be found.", e);
        } catch (InvalidClassSpecificationException e) {
        	logger.log(Level.FATAL, "Can not create object.\n" + AjaxSupportHelper.getExceptionStackTrace(e));
            throw new ServletException("Can't create handler : " + e.getMessage() + ", " + e.getCause().getMessage(), e);
        } catch (ConfigurationException e) {
        	logger.log(Level.FATAL, "Can not create object.\n" + AjaxSupportHelper.getExceptionStackTrace(e));
            throw new ServletException("Can't create factory.", e);
        }
    }

    /**
     * <p>
     * Destroy the state of this servlet.
     * </p>
     */
    public void destroy() {
        this.userIdAttributeName = null;
        this.handlers.clear();
    }

    /**
     * <p>
     * Process an Ajax request by forwarding it to the appropriate Ajax request handler.
     * </p>
     *
     * @param request an HttpServletRequest object that contains the request the client has made of the servlet
     * @param response an HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException if an input or output error is detected when the servlet handles the request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // get the HttpSession from the request
        HttpSession session = request.getSession(false);

        // the userId
        Long userId = null;

        // if this session is valid, get the user id from it
        // otherwise, leave it null
        if (session != null) {
            Object obj = session.getAttribute(this.userIdAttributeName);
            if (obj != null && !(obj instanceof Long)) {
            	logger.log(Level.ERROR,
            			"The user id should be Long in session with attribute name:" + userIdAttributeName);
                throw new ServletException("The user id should be Long.");
            }
            userId = (Long) obj;
        }

        // get the reader from the request
        Reader reader = request.getReader();

        try {
            // parse the content from the reader
            AjaxRequest ajaxRequest = AjaxRequest.parse(reader);

            // get the handler from the map
            AjaxRequestHandler handler = (AjaxRequestHandler) handlers.get(ajaxRequest.getType());

            // if the handler is null, response it with status "request error"
            if (handler == null) {
                AjaxSupportHelper.responseAndLogError(ajaxRequest.getType(), "Request error",
                        "There is no corresponding handler : " + ajaxRequest.getType(), response);
                return;
            }

            // serve the request and get the response
            AjaxResponse resp = handler.service(ajaxRequest, userId);
            
            if (resp == null) {
                AjaxSupportHelper.responseAndLogError(ajaxRequest.getType(),
                        "Server error", "Server can't satisfy this request", response);
                return;
            }
            if (!"success".equalsIgnoreCase(resp.getStatus())) {
            	StringBuffer buf = new StringBuffer();
            	for (Iterator i = ajaxRequest.getAllParameterNames().iterator(); i.hasNext();) {
            		String param = (String) i.next();
            		buf.append(',')
            			.append(param)
            			.append(" = ")
            			.append(ajaxRequest.getParameter(param));
            	}
            	logger.log(Level.WARN, "problem handling request, status: " + resp.getStatus() +
            			"\ntype: " + resp.getType() +
            			"\nparams: " + (buf.length() == 0 ? "" : buf.substring(1)) + 
            			"\nuserId: " + userId +
            			"\nencoding: " + request.getCharacterEncoding());
            }
            // response at last, this is the normal condition
            AjaxSupportHelper.doResponse(response, resp);

        } catch (RequestParsingException e) {
            // if there is a parsing error then create an AjaxResponse containing the error message,
            // use the status "invalid request error"
            AjaxSupportHelper.responseAndLogError("Unknown", "invalid request error", e.getMessage(), response, e);
            return;
        }
    }

}
