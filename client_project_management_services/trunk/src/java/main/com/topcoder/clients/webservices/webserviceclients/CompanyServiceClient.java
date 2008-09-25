/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

import com.topcoder.clients.webservices.CompanyService;

/**
 * <p>
 *  This class is a client of CompanyService service.
 *  This class has three constructors that use the wsdlDocumentLocation
 *  and service name to create this class instance.
 *  This class has only one method getCompanyServicePort()
 *  that return the service endpoint interface.
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  This class is immutable, but the thread safety depends on the base
 *  class's thread safety. The base class might be mutable by &lt;public void addPort(QName portName,
 *  String bindingId, String endpointAddress)> method, but we may safely assume that this will not happen.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
@WebServiceClient(name = "CompanyService",
        targetNamespace =  "com.topcoder.clients.webservices.CompanyService")
public class CompanyServiceClient extends Service {

    /**
     * This field represents the default service name.
     * Cannot be null.
     */
    private static final QName DEFAULT_SERVICE_NAME = new QName(CompanyService.class.getName(), "CompanyService");

    /**
     * <p>
     *  Constructs a new 'CompanyServiceClient' instance using location of
     *  WSDL document and default service name (DEFAULT_SERVICE_NAME).
     * </p>
     *
     * @param wsdlDocumentLocation
     *      the given location of WSDL document
     *
     * @throws IllegalArgumentException
     *      if wsdlDocumentLocation argument is null or empty string.
     * @throws CompanyServiceClientCreationException
     *      if the wsdlDocumentLocation is mallformed and this instance cannot be created
     */
    public CompanyServiceClient(String wsdlDocumentLocation) {
        super(ClientUtils.createURL(wsdlDocumentLocation, CompanyServiceClientCreationException.class),
                DEFAULT_SERVICE_NAME);
    }

    /**
     * Constructs a new 'CompanyServiceClient' instance using location of WSDL
     * document and default service name (DEFAULT_SERVICE_NAME).
     *
     * @param wsdlDocumentLocation the given location of WSDL document
     *
     * @throws IllegalArgumentException
     *      if wsdlDocumentLocation argument is null
     */
    public CompanyServiceClient(URL wsdlDocumentLocation) {
        super(ClientUtils.checkNull("URL of wsdl", wsdlDocumentLocation), DEFAULT_SERVICE_NAME);
    }

    /**
     * Constructs a new 'CompanyServiceClient' instance using location
     * of WSDL document and the given service name.
     *
     * @param wsdlDocumentLocation
     *      the given location of WSDL document
     * @param serviceName
     *      the given service name
     *
     * @throws IllegalArgumentException
     *      if wsdlDocumentLocation or serviceName argument are null
     */
    public CompanyServiceClient(URL wsdlDocumentLocation, QName serviceName) {
        super(ClientUtils.checkNull("URL of wsdl", wsdlDocumentLocation),
                ClientUtils.checkNull("QName of service", serviceName));
    }

    /**
     * Simply return the CompanyService web service endpoint interface.
     *
     * @return the CompanyService web service
     *
     * @throws javax.xml.ws.WebServiceException
     *      if appears any problem during retrieving the endpoint interface
     */
    @WebEndpoint(name = "CompanyService")
    public CompanyService getCompanyServicePort() {
        return super.getPort(CompanyService.class);
    }
}
