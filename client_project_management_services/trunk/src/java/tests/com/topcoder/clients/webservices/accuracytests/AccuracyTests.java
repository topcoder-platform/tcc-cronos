/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.clients.webservices.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.clients.webservices.beans.AccuracyTestClientServiceBean;
import com.topcoder.clients.webservices.beans.AccuracyTestCompanyServiceBean;
import com.topcoder.clients.webservices.beans.AccuracyTestProjectServiceBean;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AccuracyTestClientProjectManagementServicesException.class);
        suite.addTestSuite(AccuracyTestProjectServiceException.class);
        suite.addTestSuite(AccuracyTestCompanyServiceException.class);
        suite.addTestSuite(AccuracyTestClientServiceException.class);
        
        suite.addTestSuite(AccuracyTestServiceClientCreationException.class);
        suite.addTestSuite(AccuracyTestClientServiceClientCreationException.class);
        suite.addTestSuite(AccuracyTestCompanyServiceClientCreationException.class);
        suite.addTestSuite(AccuracyTestProjectServiceClientCreationException.class);
        
        suite.addTestSuite(AccuracyTestUserMappingRetrievalException.class);
        suite.addTestSuite(AccuracyTestEntityNotFoundException.class);
        suite.addTestSuite(AccuracyTestUserNotFoundException.class);
        
        suite.addTestSuite(AccuracyTestAuthorizationFailedException.class);
        
        suite.addTestSuite(AccuracyTestServiceBeanConfigurationException.class);
        suite.addTestSuite(AccuracyTestCompanyServiceBeanConfigurationException.class);
        suite.addTestSuite(AccuracyTestClientServiceBeanConfigurationException.class);
        suite.addTestSuite(AccuracyTestProjectServiceBeanConfigurationException.class);
        
        suite.addTestSuite(AccuracyTestUserClientMapping.class);
        suite.addTestSuite(AccuracyTestUserProjectMapping.class);
        
        suite.addTestSuite(AccuracyTestClientServiceClient.class);
        suite.addTestSuite(AccuracyTestCompanyServiceClient.class);
        suite.addTestSuite(AccuracyTestProjectServiceClient.class);
        
        
        suite.addTestSuite(AccuracyTestClientServiceBean.class);
        suite.addTestSuite(AccuracyTestCompanyServiceBean.class);
        suite.addTestSuite(AccuracyTestProjectServiceBean.class);
        return suite;
    }

}
