/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.HttpObjectFactory;
import com.topcoder.web.common.TCRequest;
import com.topcoder.web.common.TCResponse;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;

/**
 * <p>
 * This is the base class for the interceptors of this component. It simply provides a
 * createWebAuthentication() method.
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe because it's mutable. However, dedicated instance of struts
 * interceptor will be created by the struts framework to process the user request, so the struts interceptor
 * don't need to be thread-safe.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseInterceptor extends AbstractInterceptor {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = BaseInterceptor.class.getName();

    /**
     * <p>
     * The Log object used for logging. It's a constant. So it can only be that constant value It is final and
     * won't change once it is initialized as part of variable declaration to:
     * LogManager.getLog(BaseInterceptor.class.toString()). It is used throughout the class wherever logging
     * is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(BaseInterceptor.class.toString());

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    protected BaseInterceptor() {
        // Empty
    }

    /**
     * <p>
     * Create a WebAuthentication.
     * </p>
     *
     * @return the created WebAuthentication. null will be returned if any error occurs
     */
    protected WebAuthentication createWebAuthentication() {
        // Log the entry
        final String signature = CLASS_NAME + ".createWebAuthentication()";
        LoggingWrapperUtility.logEntrance(LOG, signature, null, null);

        try {
            HttpServletRequest servletRequest = ServletActionContext.getRequest();
            HttpServletResponse servletResponse = ServletActionContext.getResponse();
            TCRequest tcRequest = HttpObjectFactory.createRequest(servletRequest);
            TCResponse tcResponse = HttpObjectFactory.createResponse(servletResponse);
            // Create a new BasicAuthentication:
            WebAuthentication authentication = new BasicAuthentication(new SessionPersistor(servletRequest
                            .getSession()), tcRequest, tcResponse, BasicAuthentication.MAIN_SITE);

            // Log the exit
            LoggingWrapperUtility.logExit(LOG, signature, new Object[] {authentication});

            return authentication;
        } catch (Exception e) {
            // just log it
            LoggingWrapperUtility.logException(LOG, signature, e);
        }

        // Log the exit
        LoggingWrapperUtility.logExit(LOG, signature, null);

        return null;
    }
}
