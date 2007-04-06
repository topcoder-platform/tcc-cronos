/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.util.datavalidator.ObjectValidator;


/**
 * <p>This is the Mock for test.</p>
 *
 * @author kzhu
 * @version 3.2
 */
public class MockValidator implements ObjectValidator {
    /**
     * <p> Default Constructor.</p>
     *
     */
    public MockValidator() {
    }

    /**
     * <p> Always returns null, since this validator considers any object to be valid. </p>
     *
     * @return null always.
     * @param obj The object to validate (this parameter is ignored)
     */
    public String getMessage(Object obj) {
        return null;
    }

    /**
     * <p>Always returns true, since this validator considers any object to be valid.</p>
     *
     * @return True always.
     * @param obj The object to validate (this parameter is ignored)
     */
    public boolean valid(Object obj) {
        return true;
    }
}
