/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.accuracytests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import com.topcoder.clients.webservices.CompanyService;
import com.topcoder.clients.webservices.webserviceclients.CompanyServiceClient;

/**
 * This class contains unit tests for <code>CompanyServiceClient</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestCompanyServiceClient extends TestCase {
    /**
     * Represents the wsdl file location.
     */
    private String wsdl;

    /**
     * Set Up the test environment before testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        try {
            Properties p = new Properties();
            InputStream is = new FileInputStream(new File("test_files/accuracy/config.properties")
                .getAbsolutePath());
            p.load(is);
            wsdl = p.getProperty("company_wsdl");
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * Clean up the test environment after testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Function test : Tests <code>CompanyServiceClient(String wsdlDocumentLocation)</code> method
     * for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testCompanyServiceClientAccuracy1() throws Exception {
        assertNotNull("fail to create ctor.", new CompanyServiceClient(wsdl));
    }

    /**
     * Function test : Tests <code>CompanyServiceClient(URL wsdlDocumentLocation)</code> method
     * for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testCompanyServiceClientAccuracy2() throws Exception {
        assertNotNull("fail to create ctor.", new CompanyServiceClient(new URL(wsdl)));
    }

    /**
     * Function test : Tests
     * <code>CompanyServiceClient(URL wsdlDocumentLocation, QName serviceName)</code> method for
     * accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testCompanyServiceClientAccuracy3() throws Exception {
        assertNotNull("fail to create ctor.", new CompanyServiceClient(new URL(wsdl), new QName(
            CompanyService.class.getName(), "CompanyService")));
    }

    /**
     * Function test : Tests <code>getClientServicePort()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetClientServicePortAccuracy() throws Exception {
        assertNotNull(new CompanyServiceClient(wsdl).getCompanyServicePort());
    }
}