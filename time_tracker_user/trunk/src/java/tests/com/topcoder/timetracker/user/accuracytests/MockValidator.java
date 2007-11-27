/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import com.topcoder.util.datavalidator.ObjectValidator;

/**
 * <p>
 * A default validator. This is used for validating the searchable fields in the Search Builder
 * </p>
 *
 * @author Chenhong
 * @version 3.2.1
 * @since 3.2.1
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
     * Determines if the given <code>Object</code> is valid.
     *
     * @param obj
     *            <code>Object</code> to validate.
     * @return <code>true</code> if <code>obj</code> is valid; <code>false</code> otherwise.
     */
    public boolean valid(Object obj) {
        return true;
    }

    /**
     * Gives error information about the given <code>Object</code>.
     *
     * @param obj
     *            <code>Object</code> to validate.
     * @return <code>null</code> if <code>obj</code> is valid.
     */
    public String getMessage(Object obj) {
        return null;
    }

    public String[] getAllMessages(Object arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getAllMessages(Object arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getMessages(Object arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setId(String arg0) {
        // TODO Auto-generated method stub

    }
}
