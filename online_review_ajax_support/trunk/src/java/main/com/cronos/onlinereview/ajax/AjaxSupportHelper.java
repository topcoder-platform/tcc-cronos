/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.ajax;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.SpecificationFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * A helper class for this component.
 * </p>
 *
 * It will do the following things :
 * <ul>
 * <li> Create object factory. </li>
 * <li> Write an ajax response. </li>
 * </ul>
 *
 * <strong>Thread safe : </strong>
 * This class has no state, so it is thread safe.
 *
 * @author topgear, TCSDEVELOPER
 * @version 1.0
 */
public final class AjaxSupportHelper {

    /**
     * Represents the namespace to create the object factory.
     */
    private static final String NAMESPACE = "com.cronos.onlinereview.ajax.factory";

    /**
     * Represents the data format.
     */
    private static final String DATE_FORMAT = "MM.dd.yyyy h:mm a";

    /**
     * Make this class uninstantiatable.
     */
    private AjaxSupportHelper() {
        // do nothing
    }

    /**
     * Create an object factory using default namespace.
     *
     * @return the object factory created
     * @throws ConfigurationException if any error happens when creating
     */
    public static ObjectFactory createObjectFactory() throws ConfigurationException {
        try {
            // create specification factory
            SpecificationFactory specFactory
                = new ConfigManagerSpecificationFactory(NAMESPACE);

            // create an object factory that uses only the specification
            ObjectFactory factory = new ObjectFactory(specFactory, ObjectFactory.SPECIFICATION_ONLY);

            return factory;
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException("Can't create object factory.", e);
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException("Can't create object factory.", e);
        }
    }

    /**
     * Create an object with type.
     *
     * @param type the type of the object
     * @return the object created
     * @throws ConfigurationException if any error happens
     */
    public static Object createObject(Class type) throws ConfigurationException {
        ObjectFactory of = createObjectFactory();
        try {
            return of.createObject(type);
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException("Can't create object.", e);
        }
    }

    /**
     * Response and log the error.
     *
     * @param type the response type
     * @param status the response status
     * @param message the error message
     * @param response the http servlet response object
     * @throws IOException if error happens when writing the response
     * @throws IllegalArgumentException if any parameter is null(except message) or type/status is empty
     */
    public static void responseAndLogError(String type, String status, String message, HttpServletResponse response)
        throws IOException {
        if (response == null) {
            throw new IllegalArgumentException("The response should not be null.");
        }

        AjaxResponse resp = createAndLogError(type, status, message, null);

        // response it
        doResponse(response, resp);
    }

    /**
     * Create the response and log the error.
     *
     * @param type the response type
     * @param status the response status
     * @param message the error message
     * @param misc misc message
     * @return The created ajax response object
     * @throws IllegalArgumentException if type/status is null/empty
     */
    public static AjaxResponse createAndLogError(String type, String status, String message, Object misc) {
        if (type == null) {
            throw new IllegalArgumentException("The type should not be null.");
        }
        if (type.trim().length() == 0) {
            throw new IllegalArgumentException("The type should not be empty.");
        }
        if (status == null) {
            throw new IllegalArgumentException("The status should not be null.");
        }
        if (status.trim().length() == 0) {
            throw new IllegalArgumentException("The status should not be empty.");
        }

        // create the response object
        AjaxResponse resp = new AjaxResponse(type, status, null);

        // log it
        Log log = LogFactory.getLog();
        log.log(Level.ERROR, type + " : " + status);
        log.log(Level.ERROR, type + " : " + message);
        if (misc != null) {
            log.log(Level.ERROR, type + " : " + misc);
        }

        return resp;
    }

    /**
     * Create the response and log the error.
     *
     * @param type the response type
     * @param status the response status
     * @param message the error message
     * @param data the data to return to the client
     * @param misc misc message
     * @return The created ajax response object
     * @throws IllegalArgumentException if type/status is null/empty
     */
    public static AjaxResponse createAndLogSucceess(String type, String status,
            String message, Object data, Object misc) {
        if (type == null) {
            throw new IllegalArgumentException("The type should not be null.");
        }
        if (type.trim().length() == 0) {
            throw new IllegalArgumentException("The type should not be empty.");
        }
        if (status == null) {
            throw new IllegalArgumentException("The status should not be null.");
        }
        if (status.trim().length() == 0) {
            throw new IllegalArgumentException("The status should not be empty.");
        }

        // create the response object
        AjaxResponse resp = new AjaxResponse(type, status, data);

        // log it
        Log log = LogFactory.getLog();
        log.log(Level.INFO, type + " : " + status);
        log.log(Level.INFO, type + " : " + message);
        log.log(Level.INFO, type + " : " + misc);

        return resp;
    }

    /**
     * Write the ajax response back to the client.
     *
     * @param response the http response object
     * @param ajax the ajax response object
     * @throws IOException if error happens when writing the response
     */
    public static void doResponse(HttpServletResponse response, AjaxResponse ajax) throws IOException {
        if (response == null) {
            throw new IllegalArgumentException("The response should not be null.");
        }
        if (ajax == null) {
            throw new IllegalArgumentException("The ajax response should not be null.");
        }

        // Set the content type of the response parameter to "text/xml"
        response.setContentType("text/xml");

        // Get the character writer from the response object
        Writer writer = response.getWriter();

        // Generate the XML representation of the AjaxResponse
        String xml = ajax.toXml(true);

        // Write the xml representation string to the character writer, and flush it
        writer.write(xml);
        writer.flush();

        writer.close();
    }

    /**
     * Convert a date object to string.
     *
     * @param date the date to convert
     * @return the string representation
     * @throws IllegalArgumentException if date is null
     */
    public static String dateToString(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date should not be null.");
        }
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        return formatter.format(date);
    }
}
