/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * <p>
 * Mock implementation of HttpSession.
 * </p>
 *
 * @author catcher
 * @version 1.0
 */
public class MockHttpSession implements HttpSession {
    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public long getCreationTime() {
        return 0;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public String getId() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public long getLastAccessedTime() {
        return 0;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public ServletContext getServletContext() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param maxInactiveInterval
     *        value
     * @return null.
     */
    public void setMaxInactiveInterval(int arg0) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public int getMaxInactiveInterval() {
        return 0;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public HttpSessionContext getSessionContext() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0
     *        attribute to get
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
    public Object getValue(String arg0) {
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
    public String[] getValueNames() {
        return null;
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0
     *        key
     * @param arg1
     *        value to put
     * @return null.
     */
    public void setAttribute(String arg0, Object arg1) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0
     *        key
     * @param arg1
     *        value to put
     * @return null.
     */
    public void putValue(String arg0, Object arg1) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0
     *        attribute to remove
     * @return null.
     */
    public void removeAttribute(String arg0) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param arg0
     *        to remove
     * @return null.
     */
    public void removeValue(String arg0) {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public void invalidate() {
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @return null.
     */
    public boolean isNew() {
        return false;
    }
}

