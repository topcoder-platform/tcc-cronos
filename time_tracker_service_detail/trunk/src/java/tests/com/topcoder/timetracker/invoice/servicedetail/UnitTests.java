/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import com.topcoder.timetracker.invoice.servicedetail.dao.impl.ServiceDetailDAOImplTest;
import com.topcoder.timetracker.invoice.servicedetail.ejb.ServiceDetailBeanTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author enefem21
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Creates a test suite of the tests contained all unit tests.
     * </p>
     *
     * @return a test suite of the tests contained all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // demo
        suite.addTest(Demo.suite());

        // package com.topcoder.timetracker.invoice.servicedetail
        suite.addTest(ArgumentCheckUtilTest.suite());
        suite.addTest(BatchExecutionExceptionTest.suite());
        suite.addTest(ConfigurationExceptionTest.suite());
        suite.addTest(DataAccessExceptionTest.suite());
        suite.addTest(EntityNotFoundExceptionTest.suite());
        suite.addTest(InvalidDataExceptionTest.suite());
        suite.addTest(InvoiceServiceDetailExceptionTest.suite());
        suite.addTest(InvoiceServiceDetailTest.suite());
        suite.addTest(ServiceDetailManagerImplTest.suite());
        suite.addTest(TransactionCreationExceptionTest.suite());

        // package com.topcoder.timetracker.invoice.servicedetail.dao.impl
        suite.addTest(ServiceDetailDAOImplTest.suite());

        // package com.topcoder.timetracker.invoice.servicedetail.ejb
        suite.addTest(ServiceDetailBeanTest.suite());

        return suite;
    }

}
