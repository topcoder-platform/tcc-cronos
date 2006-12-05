/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * <p>
 * Mock implementation of HttpServletResponse, for test purpose.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class MockHttpResponse implements HttpServletResponse {
    /**
     * The http parameters.
     */
    private Map parameters = new HashMap();

    /**
     * Set parameter.
     * @param key the key
     * @param value the value
     */
    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void addCookie(Cookie arg0) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @return false.
     */
    public boolean containsHeader(String arg0) {
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
    public String encodeURL(String arg0) {
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
    public String encodeRedirectURL(String arg0) {
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
    public String encodeUrl(String arg0) {
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
    public String encodeRedirectUrl(String arg0) {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     * @throws IOException when any io related problems.
     */
    public void sendError(int arg0, String arg1) throws IOException {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @throws IOException when any io related problems.
     */
    public void sendError(int arg0) throws IOException {
    }

    /**
     * <p>
     * Simply throw Exception for testing.
     * </p>
     *
     * @param arg0 arg0.
     * @throws IOException when any io related problems.
     */
    public void sendRedirect(String arg0) throws IOException {
        throw new IOException("Error to sendRedirect.");
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void setDateHeader(String arg0, long arg1) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void addDateHeader(String arg0, long arg1) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void setHeader(String arg0, String arg1) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void addHeader(String arg0, String arg1) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void setIntHeader(String arg0, int arg1) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void addIntHeader(String arg0, int arg1) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void setStatus(int arg0) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void setStatus(int arg0, String arg1) {
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
    public ServletOutputStream getOutputStream() throws IOException {
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
    public PrintWriter getWriter() throws IOException {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void setCharacterEncoding(String arg0) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void setContentLength(int arg0) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void setContentType(String arg0) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void setBufferSize(int arg0) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return 0.
     */
    public int getBufferSize() {
        return 0;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @throws IOException when any io related problems.
     */
    public void flushBuffer() throws IOException {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     */
    public void resetBuffer() {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return false.
     */
    public boolean isCommitted() {
        return false;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     */
    public void reset() {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void setLocale(Locale arg0) {
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
}
