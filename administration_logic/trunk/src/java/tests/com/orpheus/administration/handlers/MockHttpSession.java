/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * <p>
 * Dummy class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockHttpSession implements HttpSession {
    /**
     * a map instance.
     */
    private Map map = new HashMap();

    /**
     * get Attribute.
     * @param arg0 arg0
     * @return Attribute
     */
    public Object getAttribute(String arg0) {

        return map.get(arg0);
    }

    /**
     * Do nothing.
     * @return null
     */
    public Enumeration getAttributeNames() {

        return null;
    }

    /**
     * Do nothing.
     * @return 0
     */
    public long getCreationTime() {

        return 0;
    }

    /**
     * Do nothing.
     * @return null
     */
    public String getId() {

        return null;
    }

    /**
     * Do nothing.
     * @return 0
     */
    public long getLastAccessedTime() {

        return 0;
    }

    /**
     * Do nothing.
     * @return 0
     */
    public int getMaxInactiveInterval() {

        return 0;
    }

    /**
     * Do nothing.
     * @return null
     */
    public ServletContext getServletContext() {

        return null;
    }

    /**
     * Do nothing.
     * @return null
     */
    public HttpSessionContext getSessionContext() {

        return null;
    }

    /**
     * Do nothing.
     * @param arg0 arg0
     * @return null
     */
    public Object getValue(String arg0) {

        return null;
    }

    /**
     * Do nothing.
     * @return null
     */
    public String[] getValueNames() {

        return null;
    }

    /**
     * Do nothing.
     */
    public void invalidate() {

    }

    /**
     * Do nothing.
     * @return false
     */
    public boolean isNew() {

        return false;
    }

    /**
     * Do nothing.
     * @param arg0 arg0
     * @param arg1 arg1
     */
    public void putValue(String arg0, Object arg1) {

    }

    /**
     * Do nothing.
     * @param arg0 arg0
     */
    public void removeAttribute(String arg0) {

    }

    /**
     * Do nothing.
     * @param arg0 arg0
     */
    public void removeValue(String arg0) {

    }

    /**
     * set Attribute.
     * @param arg0 arg0
     * @param arg1 arg1
     */
    public void setAttribute(String arg0, Object arg1) {
        map.put(arg0, arg1);

    }

    /**
     * Do nothing.
     * @param arg0 arg0
     */
    public void setMaxInactiveInterval(int arg0) {

    }

}
