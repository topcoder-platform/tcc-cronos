/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * <p>
 * Mock implementation of HttpServletRequest, this class is just used for testing purpose.
 * </p>
 *
 * @author waits
 * @version 1.0
 */
public class MockHttpServletRequest implements HttpServletRequest {
    /**
     * <p>
     * headers contained in this request. The key is the name of the header, the value is String instance corresponding
     * to the name.
     * </p>
     */
    private Map headers = new HashMap();

    /**
     * The paramters.
     */
    private Map parameters = new HashMap();

    /**
     * HttpSession.
     */
    private HttpSession session = null;

    /**
     * <p>
     * Add a header.
     * </p>
     *
     * @param name  the name of header.
     * @param value the value of header.
     */
    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    /**
     * <p>
     * Returns the string value of the given header.
     * </p>
     *
     * @param name the name of header.
     * @return the string value of the given header.
     */
    public String getHeader(String name) {
        return (String) headers.get(name);
    }

    /**
     * <p>
     * Returns an Enumeration instance containing all of request headers.
     * </p>
     *
     * @return an Enumeration instance containing all of request headers.
     */
    public Enumeration getHeaderNames() {
        return Collections.enumeration(headers.keySet());
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getAuthType() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public Cookie[] getCookies() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0
     * @return null.
     */
    public long getDateHeader(String arg0) {
        return 0;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @return null.
     */
    public int getIntHeader(String arg0) {
        return 0;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getMethod() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getPathInfo() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getPathTranslated() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getContextPath() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getQueryString() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getRemoteUser() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0
     * @return false.
     */
    public boolean isUserInRole(String arg0) {
        return false;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public Principal getUserPrincipal() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getRequestedSessionId() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getRequestURI() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public StringBuffer getRequestURL() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getServletPath() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @return null.
     */
    public HttpSession getSession(boolean arg0) {
        return this.session;
    }

    /**
     * Set session.
     *
     */
    public void setHttpSession(HttpSession session){
    	this.session = session;
    }
    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public HttpSession getSession() {
    	return this.session;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return false.
     */
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return false.
     */
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return false.
     */
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return false.
     */
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @return null.
     */
    public Object getAttribute(String arg0) {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public Enumeration getAttributeNames() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getCharacterEncoding() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg9.
     * @throws UnsupportedEncodingException when the encoding is not supported.
     */
    public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return 0.
     */
    public int getContentLength() {
        return 0;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getContentType() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     * @throws IOException when any io related problems.
     */
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public Enumeration getParameterNames() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public Map getParameterMap() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getProtocol() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getScheme() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getServerName() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return 0.
     */
    public int getServerPort() {
        return 0;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     * @throws IOException when any io related problems.
     */
    public BufferedReader getReader() throws IOException {
        return null;
    }

    /**
     * <p>
     * Always return "127.0.0.1" ip addres.
     * </p>
     *
     * @return "127.0.0.1".
     */
    public String getRemoteAddr() {
        return "127.0.0.1";
    }

    /**
     * <p>
     * Always return "localhost".
     * </p>
     *
     * @return "localhost".
     */
    public String getRemoteHost() {
        return "localhost";
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void setAttribute(String arg0, Object arg1) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void removeAttribute(String arg0) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public Locale getLocale() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public Enumeration getLocales() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return false.
     */
    public boolean isSecure() {
        return false;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @return null.
     */
    public RequestDispatcher getRequestDispatcher(String arg0) {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @return null.
     */
    public String getRealPath(String arg0) {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return 0.
     */
    public int getRemotePort() {
        return 0;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getLocalName() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getLocalAddr() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return 0.
     */
    public int getLocalPort() {
        return 0;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @return null.
     */
    public Enumeration getHeaders(String arg0) {
        return null;
    }

    /**
     * <p>
     * Return the parameter.
     * </p>
     *
     * @param name name
     * @return parameters
     */
    public String getParameter(String name) {
        if ( name.equalsIgnoreCase("xml_request")){
        	return (String) this.parameters.get(name);
        }
        return null;
    }

    /**
     * Add parameters.
     */
    public void setParameter(String name, String value){
    	this.parameters.put(name, value);
    }
    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @return null.
     */
    public String[] getParameterValues(String arg0) {
        return null;
    }
}
