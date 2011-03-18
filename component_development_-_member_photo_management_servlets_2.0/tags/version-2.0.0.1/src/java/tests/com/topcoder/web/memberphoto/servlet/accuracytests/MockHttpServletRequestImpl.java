/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.accuracytests;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Mock class for <code>HttpServletRequest</code> interface.
 * @author extra
 * @version 1.0
 */
public class MockHttpServletRequestImpl extends MockHttpServletRequest {

    /**
     * Represents the request data which will be hold by the ServletRequest.
     */
    private byte[] requestData;

    /**
     * Represents the value of the content-type.
     */
    private String strContentType;

    /**
     * Represents the header of the ServletRequest.
     */
    private Map<String, String> headers = new HashMap<String, String>();

    /**
     * Creates a new MyMockHttpServletRequest object.
     * @param requestData DOCUMENT ME!
     * @param strContentType DOCUMENT ME!
     */
    public MockHttpServletRequestImpl(final byte[] requestData, final String strContentType) {
        this.requestData = requestData;
        this.strContentType = strContentType;
        this.headers.put("Content-type", strContentType);
    }

    /**
     * Set header names.
     * @param key the header key
     * @param value the header value
     */
    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * Get head value with header name.
     * @param headerName DOCUMENT ME!
     * @return header
     */
    public String getHeader(String headerName) {
        return (String) headers.get(headerName);
    }

    /**
     * Get content length.
     * @return DOCUMENT ME!
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
     * Set input stream.
     * @param requestDate request date
     */
    public void setInputStream(byte[] requestDate) {
        this.requestData = requestDate;
    }

    /**
     * Mock get input stream.
     * @return input stream
     */
    public ServletInputStream getInputStream() {
        ServletInputStream sis = new MockServletInputStream(requestData);
        return sis;
    }

    /**
     * Get content type.
     * @return content type
     */
    public String getContentType() {
        return strContentType;
    }

    /**
     * A mock class which implements the <code>ServletInputStream</code> interface to hold the byte array data
     * for
     * testing.
     */
    private static class MockServletInputStream extends ServletInputStream {

        /**
         * Represents the byte array to hold.
         */
        private ByteArrayInputStream bais;

        /**
         * Creates a new MyServletInputStream instance with given byte array data to hold.
         * @param data the byte array to hold.
         */
        public MockServletInputStream(byte[] data) {
            bais = new ByteArrayInputStream(data);
        }

        /**
         * Reads from the byte array.
         * @return the count of the bytes has been read.
         */
        public int read() {
            return bais.read();
        }
    }
}
