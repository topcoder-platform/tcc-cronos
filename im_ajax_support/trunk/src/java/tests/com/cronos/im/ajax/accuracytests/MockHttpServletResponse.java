/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.accuracytests;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * This is a simple class implementing the HttpServletResponse interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockHttpServletResponse implements HttpServletResponse {
    /** The print writer of the response. */
    private PrintWriter printWriter = null;

    /** The underlying writer for the print writer. */
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
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public ServletOutputStream getOutputStream() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param cookie the cookie
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void addCookie(Cookie cookie) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     * @param date the date
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void addDateHeader(String name, long date) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     * @param value the value
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void addHeader(String name, String value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     * @param value the value
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void addIntHeader(String name, int value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     *
     * @return the result
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public boolean containsHeader(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param url the url
     *
     * @return the result
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public String encodeRedirectUrl(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param url the url
     *
     * @return the result
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public String encodeRedirectURL(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param url the url
     *
     * @return the result
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public String encodeUrl(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param url the url
     *
     * @return the result
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public String encodeURL(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param sc the sc
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void sendError(int sc) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param sc the sc
     * @param msg the msg
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void sendError(int sc, String msg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param location the location
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void sendRedirect(String location) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     * @param date the date
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void setDateHeader(String name, long date) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     * @param value the value
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void setHeader(String name, String value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param name the name
     * @param value the value
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void setIntHeader(String name, int value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param sc the sc
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void setStatus(int sc) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param sc the sc
     * @param sm the sm
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void setStatus(int sc, String sm) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void flushBuffer() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return the result
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public int getBufferSize() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return the result
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return the result
     *
     * @throws UnsupportedOperationException to  Junit
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
     * Not supported.
     *
     * @return the result
     */
    public PrintWriter getWriter() {
        writer = new StringWriter();
        printWriter = new PrintWriter(writer);

        return printWriter;
    }

    /**
     * Not supported.
     *
     * @return the result
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public boolean isCommitted() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void reset() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void resetBuffer() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param size the size
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void setBufferSize(int size) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param len the len
     *
     * @throws UnsupportedOperationException to  Junit
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
     * Not supported.
     *
     * @param loc the loc
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void setLocale(Locale loc) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param s input string
     *
     * @return to  Junit
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public String getCharacterEncoding(String s) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @return to  Junit
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public String getContentType() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported.
     *
     * @param charset character set
     *
     * @throws UnsupportedOperationException to  Junit
     */
    public void setCharacterEncoding(String charset) {
        throw new UnsupportedOperationException();
    }
}
