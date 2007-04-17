/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * This test case aggregates all accuracy test cases.
     * </p>
     *
     * @return all accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(IDGenerationExceptionAccuracyTests.suite());
        suite.addTest(InvalidPropertyExceptionAccuracyTests.suite());
        suite.addTest(EntityNotFoundExceptionAccuracyTests.suite());
        suite.addTest(ContactExceptionAccuracyTests.suite());
        suite.addTest(ContactFilterFactoryAccuracyTests.suite());
        suite.addTest(ConfigurationExceptionAccuracyTests.suite());
        suite.addTest(StateAccuracyTests.suite());
        suite.addTest(ContactTypeAccuracyTests.suite());
        suite.addTest(AuditExceptionAccuracyTests.suite());
        suite.addTest(AssociationExceptionAccuracyTests.suite());
        suite.addTest(CountryAccuracyTests.suite());
        suite.addTest(ContactAccuracyTests.suite());
        suite.addTest(AddressFilterFactoryAccuracyTests.suite());
        suite.addTest(AddressAccuracyTests.suite());
        suite.addTest(AddressTypeAccuracyTests.suite());
        suite.addTest(PersistenceExceptionAccuracyTests.suite());
        suite.addTest(BatchOperationExceptionAccuracyTests.suite());
        suite.addTest(ContactBeanAccuracyTests.suite());
        suite.addTest(AddressBeanAccuracyTests.suite());
        suite.addTest(InformixContactDAOAccuracyTests.suite());
        suite.addTest(InformixAddressDAOAccuracyTests.suite());

        return suite;
    }

}
