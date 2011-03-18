/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import org.springframework.mock.web.MockHttpServletRequest;

import java.io.ByteArrayInputStream;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;

/**
 * <p>
 * Mock class for <code>HttpServletRequest</code> interface.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MyMockHttpServletRequest extends MockHttpServletRequest {
    /**
     * <p>
     * Represents the request data which will be hold by the ServletRequest.
     * </p>
     */
    private byte[] requestData;

    /**
     * <p>
     * Represents the value of the content-type.
     * </p>
     */
    private String strContentType;

    /**
     * <p>
     * Represents the header of the ServletRequest.
     * </p>
     */
    private Map<String, String> headers = new HashMap<String, String>();

    /**
     * Creates a new MyMockHttpServletRequest object.
     * @param requestData the request data
     * @param strContentType the content type
     */
    public MyMockHttpServletRequest(final byte[] requestData, final String strContentType) {
        this.requestData = requestData;
        this.strContentType = strContentType;
        this.headers.put("Content-type", strContentType);
    }

    /**
     * <p>
     * Set header names.
     * </p>
     * @param key the header key
     * @param value the header value
     */
    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * <p>
     * Get head value with header name.
     * </p>
     * @param headerName the header name
     * @return header
     */
    public String getHeader(String headerName) {
        return (String) headers.get(headerName);
    }

    /**
     * <p>
     * Get content length.
     * </p>
     * @return content length
     */
    public int getContentLength() {
        int iLength = 0;

        if (null == requestData) {
            iLength = -1;
        } else {
            iLength = requestData.length;
        }

        return iLength;
    }

    /**
     * <p>
     * Set input stream.
     * </p>
     * @param requestDate request date
     */
    public void setInputStream(byte[] requestDate) {
        this.requestData = requestDate;
    }

    /**
     * <p>
     * Mock get input stream.
     * </p>
     * @return input stream
     */
    public ServletInputStream getInputStream() {
        ServletInputStream sis = new MyServletInputStream(requestData);

        return sis;
    }

    /**
     * <p>
     * Get content type.
     * </p>
     * @return content type
     */
    public String getContentType() {
        return strContentType;
    }

    /**
     * <p>
     * A mock class which implements the <code>ServletInputStream</code> interface to hold the byte array data
     * for testing.
     * </p>
     */
    private static class MyServletInputStream extends ServletInputStream {
        /**
         * <p>
         * Represents the byte array to hold.
         * </p>
         */
        private ByteArrayInputStream bais;

        /**
         * <p>
         * Creates a new MyServletInputStream instance with given byte array data to hold.
         * </p>
         * @param data the byte array to hold.
         */
        public MyServletInputStream(byte[] data) {
            bais = new ByteArrayInputStream(data);
        }

        /**
         * <p>
         * Reads from the byte array.
         * </p>
         * @return the count of the bytes has been read.
         */
        public int read() {
            return bais.read();
        }
    }
}
