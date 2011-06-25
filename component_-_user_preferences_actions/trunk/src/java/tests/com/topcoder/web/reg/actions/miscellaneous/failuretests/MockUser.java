/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.ext.SimpleUserAdapter;

/**
 * This is mock implementation of forum User.
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MockUser extends SimpleUserAdapter {

    /**
     * <p>
     * Represents user's properties.
     * </p>
     */
    private Map < String, String > properties = new HashMap < String, String >();

    /**
     * <p>
     * Creates an instance of MockUser.
     * </p>
     */
    public MockUser() {
    }

    /**
     * <p>
     * Sets user's property.
     * </p>
     * @param name the key
     * @param value the value
     * @throws UnauthorizedException never
     */
    public void setProperty(String name, String value) throws UnauthorizedException {
        this.properties.put(name, value);
    }

    /**
     * <p>
     * Retrieves property value.
     * </p>
     * @param name the property name
     * @return retrieved property value
     */
    public String getProperty(String name) {
        return properties.get(name);
    }

    /**
     * <p>
     * Retrieves properties key set iterator.
     * </p>
     * @return properties key set iterator
     */
    public Iterator getPropertyNames() {
        return properties.keySet().iterator();
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     * @return false
     */
    public boolean isPropertyEditSupported() {
        return false;
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     * @return false
     */
    public boolean isReadOnly() {
        return false;
    }
}
