/*
 * FailureTests.java    Created on 2005-7-7
 *
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 * 
 * @author qiucx0161
 *
 * @version 1.0
 */
 package com.topcoder.buildutility.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(TemplateSelectorFailureTest.suite());
        return suite;
    }

}
