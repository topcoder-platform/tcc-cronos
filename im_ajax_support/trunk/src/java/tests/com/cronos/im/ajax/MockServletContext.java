/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.Servlet;
import javax.servlet.RequestDispatcher;

/**
 * This is a simple class implementing the ServletContext interface.
 *
 * @author TCSDEVELOPER
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
    public ServletContext getContext(String uripath) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public String getInitParameter(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public java.util.Enumeration getInitParameterNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public int getMajorVersion() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public String getMimeType(String file) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public int getMinorVersion() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public RequestDispatcher getNamedDispatcher(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public String getRealPath(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public java.net.URL getResource(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public java.io.InputStream getResourceAsStream(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public java.util.Set getResourcePaths(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public String getServerInfo() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public Servlet getServlet(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public String getServletContextName() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public java.util.Enumeration getServletNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public java.util.Enumeration getServlets() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public void log(Exception exception, String msg) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public void log(String msg) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public void log(String message, Throwable throwable) {
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
    public void setAttribute(String name, Object object) {
        attrs.put(name, object);
    }

}
