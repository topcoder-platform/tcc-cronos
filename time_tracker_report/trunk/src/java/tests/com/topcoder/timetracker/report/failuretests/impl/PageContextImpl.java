/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests.impl;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;

/**
 * <p>A basic implementation of <code>PageContext</code> maintaining the attributes only.</p>
 *
 * @author  isv
 * @version 2.0
 */
public class PageContextImpl extends PageContext {

    private Map attributes= new HashMap();

    public void initialize(Servlet servlet, ServletRequest servletRequest, ServletResponse servletResponse,
        String string, boolean b, int i, boolean b1) throws IOException, IllegalStateException,
        IllegalArgumentException {
    }

    public void release() {
    }

    public void setAttribute(String string, Object object) {
        this.attributes.put(string, object);
    }

    public void setAttribute(String string, Object object, int i) {
    }

    public Object getAttribute(String string) {
        return this.attributes.get(string);
    }

    public Object getAttribute(String string, int i) {
        return null;
    }

    public Object findAttribute(String string) {
        return null;
    }

    public void removeAttribute(String string) {
    }

    public void removeAttribute(String string, int i) {
    }

    public int getAttributesScope(String string) {
        return 0;
    }

    public Enumeration getAttributeNamesInScope(int i) {
        return null;
    }

    public JspWriter getOut() {
        return null;
    }

    public HttpSession getSession() {
        return null;
    }

    public Object getPage() {
        return null;
    }

    public ServletRequest getRequest() {
        return null;
    }

    public ServletResponse getResponse() {
        return null;
    }

    public Exception getException() {
        return null;
    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public ServletContext getServletContext() {
        return null;
    }

    public void forward(String string) throws ServletException, IOException {
    }

    public void include(String string) throws ServletException, IOException {
    }

    public void handlePageException(Exception exception) throws ServletException, IOException {
    }

    public void handlePageException(Throwable throwable) throws ServletException, IOException {
    }

    public void include(String arg0, boolean arg1) throws ServletException, IOException {
    }

    public ExpressionEvaluator getExpressionEvaluator() {
        return null;
    }

    public VariableResolver getVariableResolver() {
        return null;
    }
}
