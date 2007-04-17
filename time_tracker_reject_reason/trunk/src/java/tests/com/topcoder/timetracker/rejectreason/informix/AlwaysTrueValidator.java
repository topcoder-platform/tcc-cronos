/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.informix;

import com.topcoder.util.datavalidator.ObjectValidator;


/**
 * This validator is always true for any provided value.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AlwaysTrueValidator implements ObjectValidator {
    /**
     * Default Constructor.
     */
    public AlwaysTrueValidator() {
    }

    /**
     * Always returns true, since this validator considers any object to be valid.
     *
     * @param obj the object to validate (this parameter is ignored).
     *
     * @return true.
     */
    public boolean valid(Object obj) {
        return true;
    }

    /**
     * Always returns null, since this validator considers any object to be valid.
     *
     * @param obj the object to validate (this parameter is ignored).
     *
     * @return null.
     */
    public String getMessage(Object obj) {
        return null;
    }
}
