/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.accuracytests;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;


/**
 * This is a simple class implementing the ServletContext interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockServletContext implements ServletContext {
    /** Attributes of this servlet context. */
    private Map attrs = new HashMap();

    /**
     * Empty Constructor.
     */
    public MockServletContext() {
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
     * @param uripath
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public ServletContext getContext(String uripath) {
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
    public String getInitParameter(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public java.util.Enumeration getInitParameterNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public int getMajorVersion() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param file
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public String getMimeType(String file) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public int getMinorVersion() {
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
    public RequestDispatcher getNamedDispatcher(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param path
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public String getRealPath(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param path
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param path
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public java.net.URL getResource(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param path
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public java.io.InputStream getResourceAsStream(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param path
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public java.util.Set getResourcePaths(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public String getServerInfo() {
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
    public Servlet getServlet(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public String getServletContextName() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public java.util.Enumeration getServletNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public java.util.Enumeration getServlets() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param exception
     * @param msg
     *
     * @throws UnsupportedOperationException
     */
    public void log(Exception exception, String msg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param msg
     *
     * @throws UnsupportedOperationException
     */
    public void log(String msg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param message
     * @param throwable
     *
     * @throws UnsupportedOperationException
     */
    public void log(String message, Throwable throwable) {
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
     * @param object
     */
    public void setAttribute(String name, Object object) {
        attrs.put(name, object);
    }
}
