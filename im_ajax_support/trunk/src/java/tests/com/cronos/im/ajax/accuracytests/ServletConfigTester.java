/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;


/**
 * The ServletConfig for test.
 *
 * @author brain_cn
 * @version 1.0
 */
public class ServletConfigTester implements ServletConfig {
    /** The MockServletContext. */
    private ServletContext context = new MockServletContext();

    /**
     * Return ServletName
     *
     * @return ServletName
     */
    public String getServletName() {
        return "IM ajax servlet";
    }

    /**
     * Return ServletContext
     *
     * @return ServletContext
     */
    public ServletContext getServletContext() {
        return context;
    }

    /**
     * Return InitParameter(
     *
     * @param arg0 arg0
     *
     * @return InitParameter(
     */
    public String getInitParameter(String arg0) {
        return null;
    }

    /**
     * Return InitParameterNames
     *
     * @return InitParameterNames
     */
    public Enumeration getInitParameterNames() {
        return null;
    }
}
