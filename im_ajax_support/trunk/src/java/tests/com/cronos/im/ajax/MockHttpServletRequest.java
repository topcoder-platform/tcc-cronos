/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax;

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
 * This is a simple class implementing the HttpServletRequest interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockHttpServletRequest implements HttpServletRequest {

    /**
     * Parameters of this http servlet request.
     */
    private Map parameters = new HashMap();

    /**
     * Http session.
     */
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
     */
    public String getMethod() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the request URL.
     *
     * @return result
     */
    public StringBuffer getRequestURL() {
        throw new UnsupportedOperationException();
    }

    /**
     * The length of the body in bytes.
     *
     * @return the length.
     */
    public int getContentLength() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the input stream of this request.
     *
     * @return the input stream
     */
    public ServletInputStream getInputStream() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the accept languages.
     *
     * @return the accept languages.
     * @param name
     *            the header name.
     */
    public String getHeader(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getAuthType() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getContextPath() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public Cookie[] getCookies() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     * @param name
     *            the name
     */
    public long getDateHeader(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public Enumeration getHeaderNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     * @param name
     *            the name
     */
    public Enumeration getHeaders(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     * @param name
     *            the name
     */
    public int getIntHeader(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getPathInfo() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getPathTranslated() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getQueryString() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getRemoteUser() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getRequestedSessionId() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getRequestURI() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getServletPath() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public HttpSession getSession() {
        return this.httpSession;
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     * @param create
     *            whether create or not
     */
    public HttpSession getSession(boolean create) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public Principal getUserPrincipal() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public boolean isRequestedSessionIdFromCookie() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public boolean isRequestedSessionIdFromUrl() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public boolean isRequestedSessionIdFromURL() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public boolean isRequestedSessionIdValid() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     * @param role
     *            the role
     */
    public boolean isUserInRole(String role) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     * @param name
     *            the name
     */
    public Object getAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public Enumeration getAttributeNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getContentType() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public Locale getLocale() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public Enumeration getLocales() {
        throw new UnsupportedOperationException();
    }

    public void setParameter(String name, String value) {
        parameters.put(name, value);
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     * @param name
     *            the name
     */
    public String getParameter(String name) {
        return (String) parameters.get(name);
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public Map getParameterMap() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public Enumeration getParameterNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     * @param name
     *            the name
     */
    public String[] getParameterValues(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getProtocol() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public BufferedReader getReader() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     * @param path
     *            the path
     */
    public String getRealPath(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getRemoteAddr() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getRemoteHost() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     * @param path
     *            the path
     */
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getScheme() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public String getServerName() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public int getServerPort() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return result
     */
    public boolean isSecure() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param name
     *            the name
     */
    public void removeAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param name
     *            the name
     * @param o
     *            the object
     */
    public void setAttribute(String name, Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param env
     *            the environment
     */
    public void setCharacterEncoding(String env) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public String getLocalAddr() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public String getLocalName() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public int getLocalPort() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public int getRemotePort() {
        throw new UnsupportedOperationException();
    }
}
