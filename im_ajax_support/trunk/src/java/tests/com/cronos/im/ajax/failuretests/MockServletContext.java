/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.failuretests;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;

/**
 * This is a simple class implementing the ServletContext interface.
 *
 * @author waits
 * @version 1.0
 */
public class MockServletContext implements ServletContext {

    /**
     * Attributes of this servlet context.
     */
    private Map attrs = new HashMap();

    /**
     * Empty Constructor.
     */
    public MockServletContext() {
    }

    /**
     * get attributes.
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
    public ServletContext getContext(String uripath) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public String getInitParameter(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public java.util.Enumeration getInitParameterNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public int getMajorVersion() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public String getMimeType(String file) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public int getMinorVersion() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public RequestDispatcher getNamedDispatcher(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public String getRealPath(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public java.net.URL getResource(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public java.io.InputStream getResourceAsStream(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public java.util.Set getResourcePaths(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public String getServerInfo() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public Servlet getServlet(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public String getServletContextName() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public java.util.Enumeration getServletNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public java.util.Enumeration getServlets() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public void log(Exception exception, String msg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public void log(String msg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public void log(String message, Throwable throwable) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     */
    public void removeAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * set attributes.
     */
    public void setAttribute(String name, Object object) {
        attrs.put(name, object);
    }

}
