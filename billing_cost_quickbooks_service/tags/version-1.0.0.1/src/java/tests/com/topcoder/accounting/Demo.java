/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.topcoder.accounting.stub.QBWebConnectorSvcStub;
import com.topcoder.accounting.stub.QBWebConnectorSvcStub.Authenticate;
import com.topcoder.accounting.stub.QBWebConnectorSvcStub.AuthenticateResponse;
import com.topcoder.accounting.stub.QBWebConnectorSvcStub.ReceiveResponseXML;
import com.topcoder.accounting.stub.QBWebConnectorSvcStub.ReceiveResponseXMLResponse;
import com.topcoder.accounting.stub.QBWebConnectorSvcStub.SendRequestXML;
import com.topcoder.accounting.stub.QBWebConnectorSvcStub.SendRequestXMLResponse;

import junit.framework.TestCase;

/**
 * <p>
 * Demo test of this component.
 * </p>
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * <p>
     * Demo test of this component.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDemo() throws Exception {
        // create web service stub
        QBWebConnectorSvcStub stub = new QBWebConnectorSvcStub(loadEndPointUrl());

        // authenticate
        Authenticate authenticate = new Authenticate();
        AuthenticateResponse authenticateResponse = stub.authenticate(authenticate);

        // send request xml
        SendRequestXMLResponse sendRequestXMLResponse = stub.sendRequestXML(new SendRequestXML());
        String response = sendRequestXMLResponse.getSendRequestXMLResult();

        // receive response xml
        ReceiveResponseXMLResponse receiveResponseXMLResponse = stub.receiveResponseXML(new ReceiveResponseXML());
    }

    /**
     * <p>
     * Load the web service endpoint url from configuration file.
     * </p>
     * @return the web service endpoint url
     * @throws Exception to JUnit
     */
    private static String loadEndPointUrl() throws Exception {
        InputStream is = new FileInputStream("test_files/demo.properties");
        try {
            Properties properties = new Properties();
            properties.load(is);
            return properties.getProperty("endpoint");
        } finally {
            is.close();
        }
    }
}