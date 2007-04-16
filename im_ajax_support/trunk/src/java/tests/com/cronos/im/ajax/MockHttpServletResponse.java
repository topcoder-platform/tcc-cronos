/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax;

import java.util.Locale;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;

/**
 * This is a simple class implementing the HttpServletResponse interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockHttpServletResponse implements HttpServletResponse {

    /**
     * The print writer of the response.
     */
    private PrintWriter printWriter = null;

    /**
     * The underlying writer for the print writer.
     */
    private Writer writer = null;

    /**
     * Default constructor.
     */
    public MockHttpServletResponse() {
    }

    /**
     * Get the output stream.
     *
     * @return the output stream.
     */
    public ServletOutputStream getOutputStream() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param cookie
     *            the cookie
     */
    public void addCookie(Cookie cookie) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param name
     *            the name
     * @param date
     *            the date
     */
    public void addDateHeader(String name, long date) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public void addHeader(String name, String value) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public void addIntHeader(String name, int value) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return the result
     * @param name
     *            the name
     */
    public boolean containsHeader(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return the result
     * @param url
     *            the url
     */
    public String encodeRedirectUrl(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return the result
     * @param url
     *            the url
     */
    public String encodeRedirectURL(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return the result
     * @param url
     *            the url
     */
    public String encodeUrl(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return the result
     * @param url
     *            the url
     */
    public String encodeURL(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param sc
     *            the sc
     */
    public void sendError(int sc) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param sc
     *            the sc
     * @param msg
     *            the msg
     */
    public void sendError(int sc, String msg) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param location
     *            the location
     */
    public void sendRedirect(String location) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param name
     *            the name
     * @param date
     *            the date
     */
    public void setDateHeader(String name, long date) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public void setHeader(String name, String value) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public void setIntHeader(String name, int value) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param sc
     *            the sc
     */
    public void setStatus(int sc) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param sc
     *            the sc
     * @param sm
     *            the sm
     */
    public void setStatus(int sc, String sm) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public void flushBuffer() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return the result
     */
    public int getBufferSize() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return the result
     */
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return the result
     */
    public Locale getLocale() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get response content.
     *
     * @return response content.
     */
    public String getContent() {
        printWriter.close();
        return writer.toString();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return the result
     */
    public PrintWriter getWriter() {
        writer = new StringWriter();
        printWriter = new PrintWriter(writer);
        return printWriter;
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @return the result
     */
    public boolean isCommitted() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public void reset() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public void resetBuffer() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param size
     *            the size
     */
    public void setBufferSize(int size) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param len
     *            the len
     */
    public void setContentLength(int len) {
        throw new UnsupportedOperationException();
    }

    /**
     * Does nothing.
     *
     * @param type
     *            the type
     */
    public void setContentType(String type) {
        // does nothing
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param loc
     *            the loc
     */
    public void setLocale(Locale loc) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param s
     *            input string
     */
    public String getCharacterEncoding(String s) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     */
    public String getContentType() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not used in the tests, and it simply throws out the UnsupportedOperationException.
     *
     * @param charset
     *            character set
     */
    public void setCharacterEncoding(String charset) {
        throw new UnsupportedOperationException();
    }
}
