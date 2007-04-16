/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

import com.cronos.im.ajax.IMAjaxSupportServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The IMAjaxSupportServletTester.
 *
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

    /**
     * Service the request.
     *
     *
     * @param req
     *            the http request
     * @param res
     *            the http response
     * @throws IOException
     *             if any i/o error ocurred
     * @throws ServletException
     *             if any other error occurred
     */
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException,
            ServletException {
        super.service(req, res);
    }

}
