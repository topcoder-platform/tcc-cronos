/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import com.cronos.im.ajax.IMAjaxSupportServlet;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;


/**
 * Test class extends the IMAjaxSupportServlet for testing.
 *
 * The methods used by this component is mocked.
 *
 * @author waits
 * @version 1.0
 * @since Apr 8, 2007
 */
public class MockIMAjaxSupportServlet extends IMAjaxSupportServlet {
    /**
     * count.
     */
	private static int count = 0;
	/**
     * count.
     */
	private static int count2 = 0;
	/**
     * return the init parameters.
     *
     * @param name parameter name
     *
     * @return value
     */
    public String getInitParameter(String name) {
        if (name.equalsIgnoreCase("object_factory_namespace")) {
            return "com.cronos.im.ajax.objectfactory";
        }

        return null;
    }

    /**
     * Return servletConfig.
     *
     * @return ServletConfig
     */
    public ServletConfig getServletConfig() {
        return new ServletConfig() {
                public String getInitParameter(String name) {
                    if (name.equalsIgnoreCase("object_factory_namespace")) {
                    	count++;
                        if ( count == 1){
                        	return "com.cronos.im.ajax.objectfactory";
                        } else if ( count == 2){
                        	return "com.cronos.im.ajax.objectfactory.notexist";
                        } else if ( count == 3){
                        	return "com.cronos.im.ajax.objectfactory.failure2";
                        } else if ( count == 4){
                        	return "com.cronos.im.ajax.objectfactory.failure3";
                        } else if ( count == 5){
                        	return "com.cronos.im.ajax.objectfactory.failure4";
                        } else if ( count == 6){
                        	return "com.cronos.im.ajax.objectfactory.failure5";
                        } else {
                        	return "com.cronos.im.ajax.objectfactory";
                        }
                    } else if ( name.equalsIgnoreCase("handler_manager_namespace")){
                    	count2++;
                    	if ( count2 == 1){
                    		return "com.cronos.im.ajax.RequestHandlerManager";
                    	} else if ( count2 == 2){
                    		return "com.cronos.im.ajax.RequestHandlerManager.notexist";
                    	}
                    	return "com.cronos.im.ajax.RequestHandlerManager";
                    }

                    return null;
                }

                public Enumeration getInitParameterNames() {
                    return null;
                }

                public ServletContext getServletContext() {
                    return new MockServletContext();
                }

                public String getServletName() {
                    return "IM_AJAX_SERVLET";
                }
            };
    }

    /**
     * Return the mock servletContext.
     *
     * @return ServletContext
     */
    public ServletContext getServletContext() {
        return new MockServletContext();
    }
}
