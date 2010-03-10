/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.actions.ajax.processors.DefaultAJAXResultPreProcessorUnitTest;
import com.topcoder.service.actions.ajax.serializers.JSONDataSerializerUnitTest;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Return the aggregated suite.
     * </p>
     *
     * @return the aggregated suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(AJAXDataPostProcessingExceptionUnitTest.suite());
        suite.addTest(AJAXDataPreProcessingExceptionUnitTest.suite());
        suite.addTest(AJAXDataSerializationExceptionUnitTest.suite());
        suite.addTest(HelperUnitTest.suite());
        suite.addTest(CustomFormatAJAXResultUnitTest.suite());


        suite.addTest(DefaultAJAXResultPreProcessorUnitTest.suite());
        suite.addTest(JSONDataSerializerUnitTest.suite());
        suite.addTest(Demo.suite());

        return suite;
    }

}
