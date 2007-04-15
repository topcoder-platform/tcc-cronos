/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.stresstests;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;

/**
 * Simple mock class implementing the ServletContext interface.
 *
 * @author 80x86
 * @version 1.0
 */
public class MockServletContext implements ServletContext {

    /**
     * Attributes of the servlet context.
     */
    private Map attributes = new HashMap();

    /**
     * Gets the attribute of the name name.
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
    public ServletContext getContext(String uripath) {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getInitParameter(String name) {
        return null;
    }

    /**
     * Not implemented.
     */
    public java.util.Enumeration getInitParameterNames() {
        return null;
    }

    /**
     * Not implemented.
     */
    public int getMajorVersion() {
        return 0;
    }

    /**
     * Not implemented.
     */
    public String getMimeType(String file) {
        return null;
    }

    /**
     * Not implemented.
     */
    public int getMinorVersion() {
        return 0;
    }

    /**
     * Not implemented.
     */
    public RequestDispatcher getNamedDispatcher(String name) {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getRealPath(String path) {
        return null;
    }

    /**
     * Not implemented.
     */
    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    /**
     * Not implemented.
     */
    public java.net.URL getResource(String path) {
        return null;
    }

    /**
     * Not implemented.
     */
    public java.io.InputStream getResourceAsStream(String path) {
        return null;
    }

    /**
     * Not implemented.
     */
    public java.util.Set getResourcePaths(String path) {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getServerInfo() {
        return null;
    }

    /**
     * Not implemented.
     */
    public Servlet getServlet(String name) {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getServletContextName() {
        return null;
    }

    /**
     * Not implemented.
     */
    public java.util.Enumeration getServletNames() {
        return null;
    }

    /**
     * Not implemented.
     */
    public java.util.Enumeration getServlets() {
        return null;
    }

    /**
     * Not implemented.
     */
    public void log(Exception exception, String msg) {
    }

    /**
     * Not implemented.
     */
    public void log(String msg) {
    }

    /**
     * Not implemented.
     */
    public void log(String message, Throwable throwable) {
    }

    /**
     * Not implemented.
     */
    public void removeAttribute(String name) {
    }

    /**
     * Sets a attribute with the name name and value object.
     */
    public void setAttribute(String name, Object object) {
        attributes.put(name, object);
    }

}
