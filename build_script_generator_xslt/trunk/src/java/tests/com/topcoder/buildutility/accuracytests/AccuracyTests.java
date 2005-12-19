/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.buildutility.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 * 
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(BuildScriptGeneratorExceptionTests.class);
        suite.addTestSuite(GenerationProcessExceptionTests.class);
        suite.addTestSuite(GeneratorCreationExceptionTests.class);
        suite.addTestSuite(BuildScriptGeneratorImplTests.class);
        suite.addTestSuite(BuildScriptGeneratorFactoryImplTests.class);
        return suite;
    }

}
