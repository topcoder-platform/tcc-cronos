/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link RecordMemberAnswerResponse}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RecordMemberAnswerResponseUnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(RecordMemberAnswerResponseUnitTests.class);
    }

    /**
     * <p>
     * Unit test for <code>RecordMemberAnswerResponse#RecordMemberAnswerResponse()</code> constructor.
     * </p>
     * <p>
     * Instance should always created.
     * </p>
     */
    public void testRecordMemberAnswerResponse_accuracy() {
        RecordMemberAnswerResponse recordMemberAnswerResponse = new RecordMemberAnswerResponse();

        assertNotNull("Instance should always created.", recordMemberAnswerResponse);
    }
}
