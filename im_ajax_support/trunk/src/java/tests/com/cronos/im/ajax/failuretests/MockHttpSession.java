/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.failuretests;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.ServletContext;

/**
 * This is a simple class implementing the HttpSession interface.
 *
 * @author waits
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
     * Not supported.
     */
    public Object getAttribute(String name) {
        return attrs.get(name);
    }

    /**
     * Not supported.
     */
    public java.util.Enumeration getAttributeNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public long getCreationTime() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public String getId() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public long getLastAccessedTime() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public int getMaxInactiveInterval() {
        throw new UnsupportedOperationException();
    }

    /**
     * return the servlet context.
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * Set servletContext.
     * @param ctx ServletContext
     */
    public void setServletContext(ServletContext ctx){
    	this.servletContext = ctx;
    }

    /**
     * Not supported.
     */
    public HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public Object getValue(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public String[] getValueNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public void invalidate() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public boolean isNew() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public void putValue(String name, Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public void removeAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public void removeValue(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public void setAttribute(String name, Object value) {
        attrs.put(name, value);
    }

    /**
     * Not supported.
     */
    public void setMaxInactiveInterval(int interval) {
        throw new UnsupportedOperationException();
    }

}
