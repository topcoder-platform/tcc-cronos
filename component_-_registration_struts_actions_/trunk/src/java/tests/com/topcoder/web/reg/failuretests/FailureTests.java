/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({ViewRegistrationPreferenceActionFailureTests.class,
	SelectRegistrationPreferenceActionFailureTests.class,
	ResendAccountActivationEmailActionFailureTests.class,
	PreCreateAccountActionFailureTests.class,
	CreateAccountActionFailureTests.class,
	ActivateAccountActionFailureTests.class
})
public class FailureTests {
}
