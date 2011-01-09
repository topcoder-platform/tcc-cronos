/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.persistence;

import com.topcoder.util.datavalidator.AbstractObjectValidator;

/**
 * <p>
 * This is the Mock for test.
 * </p>
 * @author TCSDEVELOPER
 */
public class MockValidator extends AbstractObjectValidator {
    /**
     * <p>
     * Default Cosntructor.
     * </p>
     */
    public MockValidator() {
    }

    /**
     * <p>
     * Always returns null, since this validator considers any object to be valid.
     * </p>
     * @return null always.
     * @param obj
     *            The object to validate (this parameter is ignored)
     */
    public String getMessage(Object obj) {
        return null;
    }

    /**
     * <p>
     * Always returns true, since this validator considers any object to be valid.
     * </p>
     * @return True always.
     * @param obj
     *            The object to validate (this parameter is ignored)
     */
    public boolean valid(Object obj) {
        return true;
    }
}
