/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.accuracytests;

import java.io.BufferedReader;

import java.security.Principal;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * This is a simple class implementing the HttpServletRequest interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockHttpServletRequest implements HttpServletRequest {
    /** Parameters of this http servlet request. */
    private Map parameters = new HashMap();

    /** Http session. */
    private HttpSession httpSession = new MockHttpSession();

    /**
     * Empty Constructor.
     */
    public MockHttpServletRequest() {
    }

    /**
     * The method type.
     *
     * @return the method type
     *
     * @throws UnsupportedOperationException DOCUMENT ME!
     */
    public String getMethod() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the request URL.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public StringBuffer getRequestURL() {
        throw new UnsupportedOperationException();
    }

    /**
     * The length of the body in bytes.
     *
     * @return the length.
     *
     * @throws UnsupportedOperationException
     */
    public int getContentLength() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the input stream of this request.
     *
     * @return the input stream
     *
     * @throws UnsupportedOperationException
     */
    public ServletInputStream getInputStream() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the accept languages.
     *
     * @param name the header name.
     *
     * @return the accept languages.
     *
     * @throws UnsupportedOperationException
     */
    public String getHeader(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getAuthType() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getContextPath() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public Cookie[] getCookies() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public long getDateHeader(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public Enumeration getHeaderNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public Enumeration getHeaders(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public int getIntHeader(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getPathInfo() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getPathTranslated() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getQueryString() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getRemoteUser() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getRequestedSessionId() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getRequestURI() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getServletPath() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     */
    public HttpSession getSession() {
        return this.httpSession;
    }

    /**
     * Not supported.
     *
     * @param create whether create or not
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public HttpSession getSession(boolean create) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public Principal getUserPrincipal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public boolean isRequestedSessionIdFromCookie() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public boolean isRequestedSessionIdFromUrl() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public boolean isRequestedSessionIdFromURL() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public boolean isRequestedSessionIdValid() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param role the role
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public boolean isUserInRole(String role) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public Object getAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public Enumeration getAttributeNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getContentType() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public Locale getLocale() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public Enumeration getLocales() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     *
     * @param name name
     * @param value value
     */
    public void setParameter(String name, String value) {
        parameters.put(name, value);
    }

    /**
     * Not supported.
     *
     * @param name the name
     *
     * @return result
     */
    public String getParameter(String name) {
        return (String) parameters.get(name);
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public Map getParameterMap() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public Enumeration getParameterNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String[] getParameterValues(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getProtocol() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public BufferedReader getReader() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param path the path
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getRealPath(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getRemoteAddr() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getRemoteHost() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param path the path
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getScheme() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public String getServerName() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public int getServerPort() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return result
     *
     * @throws UnsupportedOperationException
     */
    public boolean isSecure() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     *
     * @throws UnsupportedOperationException
     */
    public void removeAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     * @param o the object
     *
     * @throws UnsupportedOperationException
     */
    public void setAttribute(String name, Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param env the environment
     *
     * @throws UnsupportedOperationException
     */
    public void setCharacterEncoding(String env) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public String getLocalAddr() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public String getLocalName() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public int getLocalPort() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return
     *
     * @throws UnsupportedOperationException
     */
    public int getRemotePort() {
        throw new UnsupportedOperationException();
    }
}
