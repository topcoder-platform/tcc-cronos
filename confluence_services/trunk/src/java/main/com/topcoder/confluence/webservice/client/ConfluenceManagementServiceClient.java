/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;

import com.topcoder.confluence.webservice.ConfluenceManagementService;
import com.topcoder.confluence.webservice.Helper;

/**
 * <p>
 * The service client class that is used to obtain the ConfluenceManagementService proxy. In effect it is a factory
 * of ConfluenceManagementService instances.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * String address =&quot;wsdl file location url string representation&quot;
 * // create a ConfluenceManagementServiceClient using the default qualified name and a specific address of
 * // the service, and obtain a proxy:
 * URL url = new URL(address);
 * ConfluenceManagementServiceClient client = new ConfluenceManagementServiceClient(url);
 * ConfluenceManagementService service = client.getConfluenceManagementServicePort();
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety:</b> No information is provided about the thread-safety of the underlying Service class, but
 * it is assumed that it is thread-safe. This extension does not change that as it is immutable.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@WebServiceClient(name = "ConfluenceManagementServiceClient")
public class ConfluenceManagementServiceClient extends Service {

    /**
     * <p>
     * Represents the default qualified name of the service.
     * </p>
     * <p>
     * It will be used in the constructors that only accept URL information.
     * </p>
     */
    public static final QName DEFAULT_SERVICE_NAME =
        new QName("http://bean.webservice.confluence.topcoder.com/", "ConfluenceManagementServiceBeanService");

    /**
     * <p>
     * The web service end point name used to get port.
     * </p>
     */
    private static final String END_POINT_NAME = "ConfluenceManagementServiceBeanPort";

    /**
     * <p>
     * The qualified name of the service endpoint in the WSDL service description.
     * </p>
     * <p>
     * It is set in the constructor. It is used in the getConfluenceManagementServicePort method.
     * </p>
     * <p>
     * It will not be null/empty.
     * </p>
     * <p>
     * Once set, it will not change.
     * </p>
     */
    private final QName serviceName;

    /**
     * <p>
     * Creates an instance of this client with the given URL string representation of the location of the wsdl file
     * and the default QName.
     * </p>
     *
     * @param wsdlDocumentLocation
     *            the URL string representation of the location of the wsdl file
     * @throws IllegalArgumentException
     *             if wsdlDocumentLocation is null/empty or is a malformed URL
     */
    public ConfluenceManagementServiceClient(String wsdlDocumentLocation) {
        this(getUrl(wsdlDocumentLocation));
    }

    /**
     * <p>
     * Creates an instance of this client with the given URL location of the wsdl file and the default QName .
     * </p>
     *
     * @param wsdlDocumentLocation
     *            the URL of the location of the wsdl file
     * @throws IllegalArgumentException
     *             if wsdlDocumentLocation is null
     */
    public ConfluenceManagementServiceClient(URL wsdlDocumentLocation) {
        this(wsdlDocumentLocation, DEFAULT_SERVICE_NAME);
    }

    /**
     * <p>
     * Creates an instance of this client with the given URL location of the wsdl file and the service QName.
     * </p>
     *
     * @param wsdlDocumentLocation
     *            the URL of the location of the wsdl file
     * @param serviceName
     *            the QName of the service
     * @throws IllegalArgumentException
     *             if wsdlDocumentLocation or serviceName is null
     */
    public ConfluenceManagementServiceClient(URL wsdlDocumentLocation, QName serviceName) {
        super((URL) Helper.checkNull(wsdlDocumentLocation, "wsdlDocumentLocation"), (QName) Helper.checkNull(
            serviceName, "serviceName"));
        this.serviceName = serviceName;
    }

    /**
     * <p>
     * Get the wsdl file url.
     * </p>
     *
     * @param wsdlDocumentLocation
     *            the url representation of wsdl file
     * @return the url of the wsdl file
     * @throws IllegalArgumentException
     *             if wsdlDocumentLocation is null/empty or is a malformed URL
     */
    private static URL getUrl(String wsdlDocumentLocation) {
        Helper.checkStringNullOrEmpty(wsdlDocumentLocation, "wsdlDocumentLocation");
        try {
            return new URL(wsdlDocumentLocation);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("The wsdlDocumentLocation should not be a malformed URL.", e);
        }
    }

    /**
     * <p>
     * Gets the proxy of the web service for Confluence management using the service namespace URI and the
     * annotated port name.
     * </p>
     *
     * @return the ConfluenceManagementService proxy instance
     * @throws WebServiceException
     *             If there is an error in creation of the proxy, or If there is any missing WSDL meta data as
     *             required by this method, or If an illegal serviceEndpointInterface or portName is specified
     */
    @WebEndpoint(name = END_POINT_NAME)
    public ConfluenceManagementService getConfluenceManagementServicePort() {
        return super.getPort(new QName(serviceName.getNamespaceURI(), END_POINT_NAME),
            ConfluenceManagementService.class);
    }
}