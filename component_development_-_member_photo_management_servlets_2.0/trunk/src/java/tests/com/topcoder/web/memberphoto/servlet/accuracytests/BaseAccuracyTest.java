/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.accuracytests;

import junit.framework.TestCase;

/**
 * <p>
 * This is base test for Accuracy tests.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class BaseAccuracyTest extends TestCase {

    /**
     * <p>
     * Represents member id parameter name.
     * </p>
     */
    protected static final String MEMBER_ID_PARAMETER_NAME = "memberId";

    /**
     * <p>
     * Represents administration parameter name.
     * </p>
     */
    protected static final String IS_ADMIN_PARAMETER = "isAdmin";

    /**
     * <p>
     * Represents successful result url.
     * </p>
     */
    protected static final String SUCCESSFUL_RESULT_URL = "successResultUrl";

    /**
     * <p>
     * Represents error result url.
     * </p>
     */
    protected static final String ERROR_RESULT_URL = "validationErrorUrl";
}
