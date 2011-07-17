/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.reg.actions.profile.EditAccountInfoActionFailureTests;
import com.topcoder.web.reg.actions.profile.EditNameAndContactActionFailureTests;
import com.topcoder.web.reg.actions.profile.SaveAccountInfoActionFailureTests;
import com.topcoder.web.reg.actions.profile.SaveDemographicInfoActionFailureTests;
import com.topcoder.web.reg.actions.profile.ViewAccountInfoActionFailureTests;
import com.topcoder.web.reg.actions.profile.ViewDemographicInfoActionFailureTests;
import com.topcoder.web.reg.actions.profile.ViewProfileCompletenessActionFailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Test suite for the failure tests.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(EditNameAndContactActionFailureTests.suite());
        suite.addTest(AccountInfoTaskCheckerFailureTests.suite());
        suite.addTest(DefaultProfileCompletenessRetrieverFailureTests.suite());
        suite.addTest(ViewProfileCompletenessActionFailureTests.suite());
        suite.addTest(EditAccountInfoActionFailureTests.suite());
        suite.addTest(DemographicInfoTaskCheckerFailureTests.suite());
        suite.addTest(ContactInfoTaskCheckerFailureTests.suite());
        suite.addTest(SaveAccountInfoActionFailureTests.suite());
        suite.addTest(BaseSaveProfileActionFailureTests.suite());
        suite.addTest(BaseDisplayProfileActionFailureTests.suite());
        suite.addTest(SaveDemographicInfoActionFailureTests.suite());
        suite.addTest(ViewAccountInfoActionFailureTests.suite());
        suite.addTest(ViewDemographicInfoActionFailureTests.suite());

        return suite;
    }

}
