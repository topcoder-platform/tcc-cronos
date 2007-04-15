/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.stresstests;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * This is a simple class implementing the HttpSession interface.
 *
 * @author 80x86
 * @version 1.0
 */
public class MockHttpSession implements HttpSession {

    /**
     * Attributes of this http session.
     */
    private Map attributes = new HashMap();

    /**
     * Servlet context of this HTTP session.
     */
    private ServletContext servletContext = new MockServletContext();

    /**
     * Gets the attribute value of specified name.
     */
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Not implemented.
     */
    public java.util.Enumeration getAttributeNames() {
        return null;
    }

    /**
     * Not implemented.
     */
    public long getCreationTime() {
        return 0;
    }

    /**
     * Not implemented.
     */
    public String getId() {
        return null;
    }

    /**
     * Not implemented.
     */
    public long getLastAccessedTime() {
        return 0;
    }

    /**
     * Not implemented.
     */
    public int getMaxInactiveInterval() {
        return 0;
    }

    /**
     * Returns a ServletContext instance.
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * Not implemented.
     */
    public HttpSessionContext getSessionContext() {
        return null;
    }

    /**
     * Not implemented.
     */
    public Object getValue(String name) {
        return null;
    }

    /**
     * Not implemented.
     */
    public String[] getValueNames() {
        return null;
    }

    /**
     * Not implemented.
     */
    public void invalidate() {
    }

    /**
     * Not implemented.
     */
    public boolean isNew() {
        return false;
    }

    /**
     * Not implemented.
     */
    public void putValue(String name, Object value) {
    }

    /**
     * Not implemented.
     */
    public void removeAttribute(String name) {
    }

    /**
     * Not implemented.
     */
    public void removeValue(String name) {
    }

    /**
     * Not implemented.
     */
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    /**
     * Not implemented.
     */
    public void setMaxInactiveInterval(int interval) {
    }

}
