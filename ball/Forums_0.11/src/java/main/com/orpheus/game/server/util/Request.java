/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Iterator;

/**
 * <p>A helper class to be used to represent the incoming requet from the client. In context of this application is used
 * for detecting the duplicate requests from the client by </p>
 *
 * @author isv
 * @version 1.0
 */
public class Request implements Serializable {

    /**
     * <p>A <code>Map</code> collecting the parameters of incoming request. Maps <code>String</code> parameter names to
     * <code>String</code> arrays providing the request parameter values.</p>
     */
    private final Map parameters = new HashMap();

    /**
     * <p>A <code>Map</code> collecting the original attributes of the incoming request. Maps <code>String</code>
     * attribute names to <code>Object</code> instances providing the request attribute values.</p>
     */
    private final Map attributes = new HashMap();

    /**
     * <p>A <code>String</code> providing the URL of the incoming request.</p>
     */
    private final String url;

    /**
     * <p>A <code>String</code> providing the query string for the incoming request.</p>
     */
    private final String queryString;

    /**
     * <p>A <code>boolean</code> indicating whether this request is serviced or not.</p>
     */
    private boolean serviced = false;

    /**
     * <p>A <code>String</code> providing the URL which the request is forwarded to once it has been serviced.</p>
     */
    private String responseUrl = null;

    /**
     * <p>A <code>String</code> providing the ID intended to identify this request from other requests.</p>
     */
    private final String requestId;

    /**
     * <p>Constructs new <code>Request</code> instance to be used to represent the specified incoming request from the
     * client.</p>
     *
     * @param request a <code>HttpServletRequest</code> representing the incoming request from the client. 
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public Request(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("The parameter [request] is NULL");
        }
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            Arrays.sort(paramValues);
            this.parameters.put(paramName, paramValues);
        }
        Enumeration attrNames = request.getAttributeNames();
        while (attrNames.hasMoreElements()) {
            String attrName = (String) attrNames.nextElement();
            Object attrValue = request.getAttribute(attrName);
            this.attributes.put(attrName, attrValue);
        }
        this.url = request.getRequestURL().toString();
        this.queryString = request.getQueryString();
        this.requestId = getRequestId(request);
    }

    /**
     * <p>Gets the ID which could be used for referencing the specified request.</p>
     *
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @return a <code>String</code> providing the ID for request.
     */
    public static String getRequestId(HttpServletRequest request) {
        String requestString = request.toString();
        int pos = requestString.indexOf('@');
        if (pos > 0) {
            requestString = requestString.substring(pos + 1);
        }
        return requestString;
    }

    /**
     * <p>Checks if this request is equal to specified object which is also expected to be of <code>Request</code> type.
     * </p>
     *
     * @param o a <code>Object</code> to check this request against.
     * @return <code>true</code> if both requests are equal; <code>false</code> otherwise.
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        Request request = (Request) o;
        if (this.queryString != null ? !this.queryString.equals(request.queryString) : request.queryString != null) {
            return false;
        }
        if (this.url != null ? !this.url.equals(request.url) : request.url != null) {
            return false;
        }
        if (this.parameters != null
            ? !areParametersEqual(this.parameters, request.parameters) : request.parameters != null) {
            return false;
        }
        return true;
    }

    /**
     * <p>Gets the hash code value for this instance.</p>
     *
     * @return an <code>int</code> providing the hash code value.
     */
    public int hashCode() {
        int result;
        result = (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (queryString != null ? queryString.hashCode() : 0);
        return result;
    }

    /**
     * <p>Checks if this request has been serviced.</p>
     *
     * @return <code>true</code> if this request has been serviced; <code>false</code> otherwise.
     */
    public boolean isServiced() {
        return this.serviced;
    }

    /**
     *
     * @param serviced
     */
    public void setServiced(boolean serviced) {
        this.serviced = serviced;
    }

    /**
     * <p>Gets the URL which this request has been forwarded to after being serviced.</p>
     *
     * @return a <code>String</code> providing the <code>URL</code> to forward this request to. 
     */
    public String getResponseUrl() {
        return this.responseUrl;
    }

    /**
     * <p>Sets the URL which this request has been forwarded to after being serviced.</p>
     *
     * @param responseUrl a <code>String</code> providing the <code>URL</code> to forward this request to.
     */
    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    /**
     * <p>Gets the attributes of this request.</p>
     *
     * @return a <code>Map</code> providing the attributes of this request. 
     */
    public Map getAttributes() {
        return new HashMap(this.attributes);
    }

    /**
     * <p>Adds specified request attribute to this request.</p>
     *
     * @param name a <code>String</code> providing the attribute name.
     * @param value a <code>Object</code> providing the attribute value.
     * @throws IllegalArgumentException if specified <code>name</code> is <code>null</code>. 
     */
    public void addAttribute(String name, Object value) {
        if (name == null) {
            throw new IllegalArgumentException("The parameter [name] is NULL");
        }
        this.attributes.put(name, value);
    }

    /**
     * <p>Gets the ID which could be used for referencing this request.</p>
     *
     * @return a <code>String</code> providing the ID for this request.
     */
    public String getRequestId() {
        return this.requestId;
    }

    /**
     * <p>Checks if specified parameters (collected from separate requests) are equal. The specified maps are considered
     * equal if they are of same size; the <code>param2</code> contains all keys from <code>param1</code> map and each
     * value mapped to key in <code>param1</code> map is equal to respective value mapped to same key in <code>param2
     * </code> map.</p>
     *
     * @param param1 a <code>Map</code> mapping the <code>String</code> parameter names to <code>String</code> arrays
     *        providing the parameter values.
     * @param param2 a <code>Map</code> mapping the <code>String</code> parameter names to <code>String</code> arrays
     *        providing the parameter values.
     * @return <code>true</code> if specified parameters are equal; <code>false</code> otherwise.
     */
    private static boolean areParametersEqual(Map param1, Map param2) {
        if (param1.size() != param2.size()) {
            return false;
        }
        Iterator iterator = param1.entrySet().iterator();
        Map.Entry entry;
        while (iterator.hasNext()) {
            entry = (Map.Entry) iterator.next();
            String paramName = (String) entry.getKey();
            String[] paramValues = (String[]) entry.getValue();
            if (!param2.containsKey(paramName)) {
                return false;
            }
            String[] paramValues2 = (String[]) param2.get(paramName);
            if (!Arrays.equals(paramValues, paramValues2)) {
                return false;
            }
        }
        return true;
    }
}
