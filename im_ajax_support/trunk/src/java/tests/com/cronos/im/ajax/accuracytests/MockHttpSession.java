/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.accuracytests;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * This is a simple class implementing the HttpSession interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockHttpSession implements HttpSession {
    /** Attributes of this http session. */
    private Map attrs = new HashMap();

    /** Servlet context of this HTTP session. */
    private ServletContext servletContext = new MockServletContext();

    /**
     * Empty Constructor.
     */
    public MockHttpSession() {
    }

    /**
     * Not supported.
     *
     * @param name
     *
     * @return
     */
    public Object getAttribute(String name) {
        return attrs.get(name);
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public java.util.Enumeration getAttributeNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public long getCreationTime() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public String getId() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public long getLastAccessedTime() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public int getMaxInactiveInterval() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public Object getValue(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public String[] getValueNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException
     */
    public void invalidate() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public boolean isNew() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name
     * @param value
     *
     * @throws UnsupportedOperationException
     */
    public void putValue(String name, Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name
     *
     * @throws UnsupportedOperationException
     */
    public void removeAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name
     *
     * @throws UnsupportedOperationException
     */
    public void removeValue(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name
     * @param value
     */
    public void setAttribute(String name, Object value) {
        attrs.put(name, value);
    }

    /**
     * Not supported.
     *
     * @param interval
     *
     * @throws UnsupportedOperationException
     */
    public void setMaxInactiveInterval(int interval) {
        throw new UnsupportedOperationException();
    }
}
