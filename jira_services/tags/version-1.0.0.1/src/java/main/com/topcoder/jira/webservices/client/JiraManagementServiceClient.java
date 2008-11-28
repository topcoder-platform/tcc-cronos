/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices.client;

import com.topcoder.jira.webservices.JiraManagementService;
import com.topcoder.jira.webservices.Util;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The service client class that is used to obtain the JiraManagementService proxy. In effect it is a factory of
 * JiraManagementService instances.
 *
 * Thread Safety: No information is provided about the thread-safety of the underlying Service class, but it is assumed
 * that it is thread-safe. This extension does not change that as it is immutable.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@WebServiceClient(name = "JiraManagementServiceClient")
public class JiraManagementServiceClient extends Service {
    /**
     * Represents the default qualified name of the service. It will be used in the constructors that only accept URL
     * information.
     */
    public static final QName DEFAULT_SERVICE_NAME =
            new QName("http://bean.webservices.jira.topcoder.com/", "JiraManagementServiceBeanService");

    /**
     * Creates an instance of this client with the given URL string representation of the location of the wsdl file and
     * the default QName.
     *
     * @param wsdlDocumentLocation the URL string representation of the location of the wsdl file
     * @throws IllegalArgumentException if wsdlDocumentLocation is null/empty or is a malformed URL
     */
    public JiraManagementServiceClient(String wsdlDocumentLocation) {
        this(createURL(wsdlDocumentLocation), DEFAULT_SERVICE_NAME);
    }

    /**
     * Creates an instance of this client with the given URL location of the wsdl file and the default QName.
     *
     * @param wsdlDocumentLocation the URL of the location of the wsdl file
     * @throws IllegalArgumentException if wsdlDocumentLocation is null
     */
    public JiraManagementServiceClient(URL wsdlDocumentLocation) {
        this(wsdlDocumentLocation, DEFAULT_SERVICE_NAME);
    }

    /**
     * Creates an instance of this client with the given URL location of the wsdl file and the service QName.
     *
     * @param wsdlDocumentLocation the URL of the location of the wsdl file
     * @param serviceName          the QName of the service
     * @throws IllegalArgumentException if wsdlDocumentLocation or serviceName is null
     */
    public JiraManagementServiceClient(URL wsdlDocumentLocation, QName serviceName) {
        super(Util.checkNull("wsdlDocumentLocation", wsdlDocumentLocation), Util.checkNull("serviceName", serviceName));
    }

    /**
     * Gets the proxy of the web service for JIRA management using the service name passed in the constructor.
     *
     * @return The JiraManagementService proxy instance
     * @throws WebServiceException If there is an error in creation of the proxy, or If there is any missing WSDL
     *                             metadata as required by this method, or If an illegal serviceEndpointInterface or
     *                             portName is specified.
     */
    @WebEndpoint(name = "JiraManagementServicePort")
    public JiraManagementService getJiraManagementServicePort() {
        return getPort(JiraManagementService.class);
    }

    /**
     * Converts provided string into URL object.
     *
     * @param location a string to parse as url
     * @return newly created url
     * @throws IllegalArgumentException if url can't be created because of bad location
     */
    private static URL createURL(String location) {
        Util.checkString("location", location);
        try {
            return new URL(location);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Can't create URL instance from [" + location + "] string.", e);
        }
    }
}
