/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.util.datavalidator.ObjectValidator;

/**
 * <p>
 * This is the Mock for test.
 * </p>
 *
 * @author enefem21
 * @version 3.2.1
 */
public class MockValidator implements ObjectValidator {

    /** Serial version UID. */
    private static final long serialVersionUID = 572703518120070399L;

    /**
     * <p>
     * Default Cosntructor.
     * </p>
     *
     */
    public MockValidator() {
    }

    /**
     * <p>
     * Always returns null, since this validator considers any object to be valid.
     * </p>
     *
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
     *
     * @return True always.
     * @param obj
     *            The object to validate (this parameter is ignored)
     */
    public boolean valid(Object obj) {
        return true;
    }

    /**
     * <p>
     * Always returns null, since this validator considers any object to be valid.
     * </p>
     *
     * @return null always.
     * @param obj
     *            The object to validate (this parameter is ignored)
     */
    public String[] getAllMessages(Object obj) {
        return null;
    }

    /**
     * <p>
     * Always returns null, since this validator considers any object to be valid.
     * </p>
     *
     * @return null always.
     * @param obj
     *            The object to validate (this parameter is ignored)
     * @param messageLimit -
     *            the max number of messages (this parameter is ignored)
     */
    public String[] getAllMessages(Object obj, int messageLimit) {
        return null;
    }

    /**
     * <p>
     * Always returns 100.
     * </p>
     *
     * @return 100 always.
     */
    public String getId() {
        return "100";
    }

    /**
     * <p>
     * Always returns null, since this validator considers any object to be valid.
     * </p>
     *
     * @return null always.
     * @param obj
     *            The object to validate (this parameter is ignored)
     */
    public String[] getMessages(Object obj) {
        return null;
    }

    /**
     * <p>
     * This method is not used.
     * </p>
     *
     * @param id
     *            The id (this parameter is ignored)
     */
    public void setId(String id) {
        // nothing to do
    }
}