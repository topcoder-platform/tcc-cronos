/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.ServletContext;

/**
 * This is a simple class implementing the HttpSession interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockHttpSession implements HttpSession {

    /**
     * Attributes of this http session.
     */
    private Map attrs = new HashMap();

    /**
     * Servlet context of this HTTP session.
     */
    private ServletContext servletContext = new MockServletContext();

    /**
     * Empty Constructor.
     */
    public MockHttpSession() {
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public Object getAttribute(String name) {
        return attrs.get(name);
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public java.util.Enumeration getAttributeNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public long getCreationTime() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public String getId() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public long getLastAccessedTime() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public int getMaxInactiveInterval() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public Object getValue(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public String[] getValueNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public void invalidate() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public boolean isNew() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public void putValue(String name, Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public void removeAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public void removeValue(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public void setAttribute(String name, Object value) {
        attrs.put(name, value);
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public void setMaxInactiveInterval(int interval) {
        throw new UnsupportedOperationException();
    }

}
