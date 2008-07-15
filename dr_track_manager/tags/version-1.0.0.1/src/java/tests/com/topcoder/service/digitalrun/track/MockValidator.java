/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.util.datavalidator.ObjectValidator;


/**
 * <p>
 * This is the Mock for test.
 * </p>
 * @author waits
 * @version 1.0
 */
public class MockValidator implements ObjectValidator {
    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public MockValidator() {
    }

    /**
     * <p>
     * Always returns null, since this validator considers any object to be valid.
     * </p>
     *
     * @param obj The object to validate (this parameter is ignored)
     *
     * @return null always.
     */
    public String getMessage(Object obj) {
        return null;
    }

    /**
     * <p>
     * Always returns true, since this validator considers any object to be valid.
     * </p>
     *
     * @param obj The object to validate (this parameter is ignored)
     *
     * @return True always.
     */
    public boolean valid(Object obj) {
        return true;
    }

    /**
     * <p>Not used.</p>
     *
     * @param arg0 arg0
     *
     * @return null
     */
    public String[] getAllMessages(Object arg0) {
        return null;
    }

    /**
     * <p>Not used.</p>
     *
     * @param arg0 arg0
     * @param arg1 arg1
     *
     * @return null
     */
    public String[] getAllMessages(Object arg0, int arg1) {
        return null;
    }

    /**
     * <p>Not used.</p>
     *
     * @return null
     */
    public String getId() {
        return null;
    }

    /**
     * <p>Not used.</p>
     *
     * @param arg0 messages
     *
     * @return messages
     */
    public String[] getMessages(Object arg0) {
        return null;
    }

    /**
     *<p>Not used.</p>
     *
     * @param arg0 id
     */
    public void setId(String arg0) {
    }
}
