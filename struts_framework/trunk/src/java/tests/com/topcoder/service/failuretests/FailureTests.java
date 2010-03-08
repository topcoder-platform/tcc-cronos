/*
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */

package com.topcoder.service.failuretests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author morehappiness
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {
    AuthenticationInterceptorFailureTests.class, LoggingInterceptorFailureTests.class,
    AggregateDataModelFailureTests.class
})
public class FailureTests {}
