/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.log.Level;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Element;
import java.io.IOException;

/**
 * <p>
 * RequestHandlerManager is used to route the incoming request to the RequestHandler based on the request
 * message type. The request handlers are loaded from the configuration file. If the request message is
 * invalid, or no matching handler is found for the request, this manage will set the failure message on the
 * http response and return.
 * </p>
 * 
 * <p>
 * This class is thread safe since it does not contain any mutable status.
 * </p>
 * 
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class RequestHandlerManager {

    /**
     * The handlers keeps the RequestHandler to service the http request. The keys of the map are the non-null
     * non-empty strings representing the handler types. The values are non-null request handler instances.
     */
    private final Map handlers = new HashMap();

    /**
     * Create the manager from the default namespace. The default namespace is the fully qualified class name
     * of this manager.
     * 
     * 
     * @throws IMAjaxConfigurationException
     *             if any other error ocurred
     */
    public RequestHandlerManager() throws IMAjaxConfigurationException {
        this(RequestHandlerManager.class.getName());
    }

    /**
     * Create the manager from the namespace.
     * 
     * @param namespace
     *            the namespace to create the instance
     * @throws IllegalArgumentException
     *             if argument is null or empty string
     * @throws IMAjaxConfigurationException
     *             if any other error ocurred
     */
    public RequestHandlerManager(String namespace) throws IMAjaxConfigurationException {
        IMHelper.checkString(namespace, "namespace");
        // get object factory namespace
        String ofns = IMHelper.getRequiredConfigString(namespace, "object_factory_namespace");
        // create object factory
        ObjectFactory of;
        try {
            of = new ObjectFactory(new ConfigManagerSpecificationFactory(ofns));
        } catch (IllegalReferenceException e) {
            throw new IMAjaxConfigurationException("The object factory configuration is invalid.", e);
        } catch (SpecificationConfigurationException e) {
            throw new IMAjaxConfigurationException(
                    "Fail to create object factory due to error of config manager.", e);
        }
        // get handler types
        String[] types = IMHelper.getRequiredConfigStrings(namespace, "handler_types");
        // create handlers and fill the handlers mapping
        for (int i = 0; i < types.length; i++) {
            String handlerKey = IMHelper.getRequiredConfigString(namespace, types[i]);
            try {
                RequestHandler handler = (RequestHandler) of.createObject(handlerKey);
                handlers.put(types[i], handler);
            } catch (InvalidClassSpecificationException e) {
                throw new IMAjaxConfigurationException("Fail to create request handler.", e);
            } catch (ClassCastException e) {
                throw new IMAjaxConfigurationException(
                        "The created request handler does not implement RequestHandler interface.", e);
            }
        }
    }

    /**
     * Handle the request.
     * 
     * @param req
     *            the http request
     * @param res
     *            the http request
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws IOException
     *             if IO error occurs
     */
    public void handle(HttpServletRequest req, HttpServletResponse res) throws IOException {
        IMHelper.checkNull(req, "req");
        IMHelper.checkNull(res, "res");
        try {
            // set response content type
            res.setContentType("text/xml");
            // get XML
            String xml = req.getParameter(IMAjaxSupportUtility.getXMLRequestParamKey());

            if (xml == null) {
                Log log = LogFactory.getLog(this.getClass().getName());
                log.log(Level.WARN, "No request parameter");

                // no request parameter found
                res.getWriter().write(
                        "<response><failure>No request parameter</failure></response>");
                return;
            }

            // parse it to an Element
            Element docElement = IMHelper.getElementFromString(xml);
            // get type
            String type = docElement.getAttribute("type");
            // find handler
            RequestHandler handler = (RequestHandler) handlers.get(type);
            if (handler == null) {
                // no handler for the requested type
                res.getWriter().write(
                        "<response><failure>unrecognized request type: " + type + "</failure></response>");
            } else {
                // delegate to the handler
                handler.handle(docElement, req, res);
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            res.getWriter().write(
                    "<response><failure>Error occured during handling the request</failure></response>");
        }
    }

    /**
     * Get all the types defined in this manager.
     * 
     * 
     * @return all the types defined in this manager.
     */
    public Map getAllHandlers() {
        return new HashMap(handlers);
    }

}
