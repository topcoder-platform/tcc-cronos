/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.stresstests;

import java.util.Map;
import java.util.HashMap;
import java.util.Locale;
import java.util.Enumeration;
import java.io.BufferedReader;
import java.security.Principal;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletInputStream;
import javax.servlet.RequestDispatcher;

/**
 * Simple mock class implementing the HttpServletRequest interface.
 *
 * @author 80x86
 * @version 1.0
 */
public class MockHttpServletRequest implements HttpServletRequest {

    /**
     * Parameters of the http servlet request.
     */
    private Map parameters = new HashMap();

    /**
     * Http session.
     */
    private HttpSession httpSession = new MockHttpSession();

    /**
     * Not implemented.
     */
    public String getMethod() {
        return null;
    }

    /**
     * Not implemented.
     */
    public StringBuffer getRequestURL() {
        return null;
    }

    /**
     * Not implemented.
     */
    public int getContentLength() {
        return 0;
    }

    /**
     * Not implemented.
     */
    public ServletInputStream getInputStream() {
        return null;
    }

    /**
     * Not implemented.
     * @param name
     *            the header name.
     */
    public String getHeader(String name) {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getAuthType() {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getContextPath() {
        return null;
    }

    /**
     * Not implemented.
     */
    public Cookie[] getCookies() {
        return null;
    }

    /**
     * Not implemented.
     * @param name
     *            the name
     */
    public long getDateHeader(String name) {
        return 0;
    }

    /**
     * Not implemented.
     */
    public Enumeration getHeaderNames() {
        return null;
    }

    /**
     * Not implemented.
     * @param name
     *            the name
     */
    public Enumeration getHeaders(String name) {
        return null;
    }

    /**
     * Not implemented.
     * @param name
     *            the name
     */
    public int getIntHeader(String name) {
        return 0;
    }

    /**
     * Not implemented.
     */
    public String getPathInfo() {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getPathTranslated() {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getQueryString() {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getRemoteUser() {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getRequestedSessionId() {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getRequestURI() {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getServletPath() {
        return null;
    }

    /**
     * Returns the http session instance of the request.
     */
    public HttpSession getSession() {
        return this.httpSession;
    }

    /**
     * Not implemented.
     * @param create
     *            whether create or not
     */
    public HttpSession getSession(boolean create) {
        return null;
    }

    /**
     * Not implemented.
     */
    public Principal getUserPrincipal() {
        return null;
    }

    /**
     * Not implemented.
     */
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    /**
     * Not implemented.
     */
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    /**
     * Not implemented.
     */
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    /**
     * Not implemented.
     */
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    /**
     * Not implemented.
     * @param role
     *            the role
     */
    public boolean isUserInRole(String role) {
        return false;
    }

    /**
     * Not implemented.
     * @param name
     *            the name
     */
    public Object getAttribute(String name) {
        return null;
    }

    /**
     * Not implemented.
     */
    public Enumeration getAttributeNames() {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getCharacterEncoding() {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getContentType() {
        return null;
    }

    /**
     * Not implemented.
     */
    public Locale getLocale() {
        return null;
    }

    /**
     * Not implemented.
     */
    public Enumeration getLocales() {
        return null;
    }

    /**
     * Sets a parameter named name and valued value.
     *
     * @param name the name
     * @param value the value
     */
    public void setParameter(String name, String value) {
        parameters.put(name, value);
    }

    /**
     * Gets the value the specified parameter named name.
     *
     * @param name
     *            the name
     * @return the value of the parameter
     */
    public String getParameter(String name) {
        return (String) parameters.get(name);
    }

    /**
     * Not implemented.
     */
    public Map getParameterMap() {
        return null;
    }

    /**
     * Not implemented.
     */
    public Enumeration getParameterNames() {
        return null;
    }

    /**
     * Not implemented.
     * @param name
     *            the name
     */
    public String[] getParameterValues(String name) {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getProtocol() {
        return null;
    }

    /**
     * Not implemented.
     */
    public BufferedReader getReader() {
        return null;
    }

    /**
     * Not implemented.
     * @param path
     *            the path
     */
    public String getRealPath(String path) {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getRemoteAddr() {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getRemoteHost() {
        return null;
    }

    /**
     * Not implemented.
     * @param path
     *            the path
     */
    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getScheme() {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getServerName() {
        return null;
    }

    /**
     * Not implemented.
     */
    public int getServerPort() {
        return 0;
    }

    /**
     * Not implemented.
     */
    public boolean isSecure() {
        return false;
    }

    /**
     * Not implemented.
     *
     * @param name
     *            the name
     */
    public void removeAttribute(String name) {
    }

    /**
     * Not implemented.
     *
     * @param name
     *            the name
     * @param o
     *            the object
     */
    public void setAttribute(String name, Object o) {
    }

    /**
     * Not implemented.
     *
     * @param env
     *            the environment
     */
    public void setCharacterEncoding(String env) {
    }

    /**
     * Not implemented.
     */
    public String getLocalAddr() {
        return null;
    }

    /**
     * Not implemented.
     */
    public String getLocalName() {
        return null;
    }

    /**
     * Not implemented.
     */
    public int getLocalPort() {
        return 0;
    }

    /**
     * Not implemented.
     */
    public int getRemotePort() {
        return 0;
    }
}
