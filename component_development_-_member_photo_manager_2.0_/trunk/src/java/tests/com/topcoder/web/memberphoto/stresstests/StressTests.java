/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress test cases.
 * </p>
 *
 * @author King_Bette, Lunarmony
 * @version 2.0
 * @since 1.0
 */
public class StressTests extends TestCase {
    /**
     * Returns the stress test cases.
     *
     * @return the stress test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(MemberPhotoManagerImplStressTest.suite());

        return suite;
    }
}