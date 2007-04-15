/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import com.cronos.im.ajax.IMAjaxSupportServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;


/**
 * The IMAjaxSupportServletTester.
 *
 * @author brain_cn
 * @version 1.0
 */
public class IMAjaxSupportServletTester extends IMAjaxSupportServlet {
    /** ServletConfig instance. */
    private ServletConfig config = new ServletConfigTester();

    /**
     * Return ServletConfig
     *
     * @return ServletConfig
     */
    public ServletConfig getServletConfig() {
        return config;
    }

    /**
     * Return ServletContext
     *
     * @return ServletContext
     */
    public ServletContext getServletContext() {
        return config.getServletContext();
    }
}
