/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;

import com.topcoder.clients.webservices.ClientStatusService;
import com.topcoder.clients.webservices.Util;

/**
 * <p>
 * This class is a client of <code>ClientStatusService</code> service. This class has tree constructors that
 * use the wsdlDocumentLocation and service name to create this class instance. This class has only one method
 * getClientStatusServicePort() that return the service endpoint interface.
 * </p>
 * <p>
 * Thread safety: This class is immutable, but the thread safety depends on the base class's thread safety.
 * The base class might be mutable by <code>public void addPort(QName portName, String bindingId, String
 * endpointAddress)</code> method, but we may safely assume that this will not happen.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@WebServiceClient(name = "ClientStatusService",
        targetNamespace = "com.topcoder.clients.webservices.ClientStatusService")
public class ClientStatusServiceClient extends Service {
    /**
     * <p>
     * This field represents the default service name. It is initialized with javax.xml.namespace.QName
     * constructor specifying the Namespace URI and local part: new QName(ClientStatusService.class.getName(),
     * "ClientStatusService"). Accessed by ClientStatusServiceClient(wsdlDocumentLocation:URL) constructor and
     * ClientStatusServiceClient(wsdlDocumentLocation:String) constructor. Will not change after
     * initialization. Valid values: can not be null.
     * </p>
     */
    private static final QName DEFAULT_SERVICE_NAME = new QName(ClientStatusService.class.getName(),
            "ClientStatusService");

    /**
     * <p>
     * Constructor. Constructs a new 'ClientStatusServiceClient' instance using location of WSDL document and
     * default service name (DEFAULT_SERVICE_NAME).
     * </p>
     *
     * @param wsdlDocumentLocation
     *            the given location of WSDL document
     * @throws IllegalArgumentException
     *             if wsdlDocumentLocation argument is null
     * @throws ClientStatusServiceClientCreationException
     *             if the wsdlDocumentLocation is mallformed and this instance can not be created
     */
    public ClientStatusServiceClient(String wsdlDocumentLocation) {
        super(getURL(wsdlDocumentLocation), DEFAULT_SERVICE_NAME);
    }

    /**
     * <p>
     * Constructor. Constructs a new 'ClientStatusServiceClient' instance using location of WSDL document and
     * default service name (DEFAULT_SERVICE_NAME).
     * </p>
     *
     * @param wsdlDocumentLocation
     *            the given location of WSDL document
     * @throws IllegalArgumentException
     *             if wsdlDocumentLocation argument is null
     */
    public ClientStatusServiceClient(URL wsdlDocumentLocation) {
        this(wsdlDocumentLocation, DEFAULT_SERVICE_NAME);
    }

    /**
     * <p>
     * Constructor. Constructs a new 'ClientStatusServiceClient' instance using location of WSDL document and
     * the given service name.
     * </p>
     *
     * @param wsdlDocumentLocation
     *            the given location of WSDL document
     * @param serviceName
     *            the given service name
     * @throws IllegalArgumentException
     *             if wsdlDocumentLocation or serviceName argument are null
     */
    public ClientStatusServiceClient(URL wsdlDocumentLocation, QName serviceName) {
        super(Util.checkNullAndReturn(wsdlDocumentLocation, "wsdlDocumentLocation"), Util.checkNullAndReturn(
                serviceName, "serviceName"));
    }

    /**
     * <p>
     * Simply return the ClientStatusService web service endpoint interface.
     * </p>
     *
     * @return the ClientStatusService web service
     * @throws WebServiceException
     *             if appears any problem during retrieving the endpoint interface
     */
    @WebEndpoint(name = "ClientStatusService")
    public ClientStatusService getClientStatusServicePort() {
        return super.getPort(ClientStatusService.class);
    }

    /**
     * <p>
     * Get <code>URL</code> from given URL string.
     * </p>
     *
     * @param url
     *            The WSDL URL.
     * @return <code>URL</code> from given URL string.
     * @throws IllegalArgumentException
     *             If given URL string is null.
     * @throws ClientStatusServiceClientCreationException
     *             If error occurs when creating <code>URL</code> from given URL string.
     */
    private static URL getURL(String url) {
        Util.checkNull(url, "url");
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new ClientStatusServiceClientCreationException("Error while creating URL from [" + url
                    + "] string.", e);
        }
    }

}
