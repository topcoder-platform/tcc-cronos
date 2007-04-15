/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.stresstests;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple mock class for implementing the HttpServletResponse interface.
 *
 * @author 80x86
 * @version 1.0
 */
public class MockHttpServletResponse implements HttpServletResponse {

    /**
     * Returns a printer.
     *
     * @return the result
     */
    public PrintWriter getWriter() {
        return new PrintWriter(System.out);
    }
    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void addCookie(Cookie arg0) {
    }

    /**
     * <p>
     * Not implemented.
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
     * Not implemented.
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
     * Not implemented.
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
     * Not implemented.
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
     * Not implemented.
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
     * Not implemented.
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
     * Not implemented.
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
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void setDateHeader(String arg0, long arg1) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void addDateHeader(String arg0, long arg1) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void setHeader(String arg0, String arg1) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void addHeader(String arg0, String arg1) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void setIntHeader(String arg0, int arg1) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void addIntHeader(String arg0, int arg1) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void setStatus(int arg0) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     * @param arg1 arg1.
     */
    public void setStatus(int arg0, String arg1) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @return null.
     */
    public String getCharacterEncoding() {
        return null;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @return null.
     */
    public String getContentType() {
        return null;
    }

    /**
     * <p>
     * Not implemented.
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
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void setCharacterEncoding(String arg0) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void setContentLength(int arg0) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void setContentType(String arg0) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void setBufferSize(int arg0) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @return 0.
     */
    public int getBufferSize() {
        return 0;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @throws IOException when any io related problems.
     */
    public void flushBuffer() throws IOException {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     */
    public void resetBuffer() {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @return false.
     */
    public boolean isCommitted() {
        return false;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     */
    public void reset() {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 arg0.
     */
    public void setLocale(Locale arg0) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @return null.
     */
    public Locale getLocale() {
        return null;
    }
}
