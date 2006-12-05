/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;

import com.orpheus.administration.Helper;
import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.AdminSummary;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * Provides a Handler implementation to return administrative summary data. It
 * will assign the AdminSummary to a configurably named request attribute. In
 * case of failure, i.e. an error when making remote calls, the execute method
 * will return a configurable result name and set a HandlerResult instance as a
 * request attribute so that an appropriate response or user message can be
 * generated. In case of success, the execute() method will return null.<br/>
 * For configuration details on this handler, please see Section 3.2.1 of Comp
 * Spec.<br/> Thread-Safety: This class is thread-safe as is required of
 * Handler implementations. This class is immutable and automatically
 * thread-safe.
 * 
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class AdminSummaryHandler implements Handler {

    /**
     * This holds the JNDI name to use to look up the AdminDataHome service.<br/>
     * AdminDataHome is defined in com.orpheus.administration.persistence
     * package.<br/> This variable is initialized in the constructor and does
     * not change after that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String adminDataJndiName;

    /**
     * This holds the name of the request attribute to which AdminSummary
     * instance should be assigned to, in case of successful execution.<br/> It
     * is initialized in the constructor and does not change after that.<br/>
     * It will never be null or empty.<br/>
     * 
     */
    private final String summaryRequestAttrName;

    /**
     * This holds the name of the result (as configured in Front Controller
     * component) which should execute in case of execution failure.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     * 
     */
    private final String failedResult;

    /**
     * This holds the name of the request attribute to which HandlerResult
     * instance should be assigned to, in case of execution failure.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     * 
     */
    private final String failRequestAttrName;

    /**
     * Creates a AdminSummaryHandler instance configured from the given xml
     * element. It will initialize the instance variables. It will throw an
     * IllegalArgumentException if configuration details are missing in the
     * handlerElement argument.
     * 
     * <p>
     * Expamle xml structure:
     * <pre>
     *  &lt;handler type=&quot;adminSummary&quot;&gt;
     *      &lt;!--  JNDI name to use to lookup the AdminDataHome interface --&gt;
     *      &lt;admin-data-jndi-name&gt;AdminDataHome&lt;/admin-data-jndi-name&gt;
     * 
     *      &lt;!--  name of request attribute to store AdminSummary in --&gt;
     *      &lt;summary-request-attribute&gt;AdminSummary&lt;/summary-request-attribute&gt;
     * 
     *      &lt;!--  name of result to return in case of execution failure --&gt;
     *      &lt;fail-result&gt;Failed&lt;/fail-result&gt;
     * 
     *      &lt;!--  name of request attribute to store HandlerResult in case of failure--&gt;
     *      &lt;fail-request-attribute&gt;Failed&lt;/fail-request-attribute&gt;
     *  &lt;/handler&gt;
     * 
     * </pre>
     * </p>
     * 
     * 
     * @param handlerElement
     *            the XML element containing configuration for this handler.
     * @throws IllegalArgumentException
     *             if handlerElement is null, or contains invalid data.
     */
    public AdminSummaryHandler(Element handlerElement) {
        Helper.checkHandlerElement(handlerElement);
        adminDataJndiName = Helper.getValue(handlerElement,
                "/handler/admin-data-jndi-name");
        summaryRequestAttrName = Helper.getValue(handlerElement,
                "/handler/summary-request-attribute");
        failedResult = Helper.getValue(handlerElement, "/handler/fail-result");
        failRequestAttrName = Helper.getValue(handlerElement,
                "/handler/fail-request-attribute");
    }

    /**
     * This method will use AdminDataHome to fetch AdminSummary and set it as a
     * request attribute.<br/> This method will return null in case of success
     * and will return a configurable result name in case of failure.
     * 
     * @param actionContext
     *            the ActionContext object.
     * @return null if operation succeeds, otherwise a configurable result name.
     * @throws IllegalArgumentException
     *             if the context is null.
     * @throws HandlerExecutionException
     *             never by this handler.
     */
    public String execute(ActionContext actionContext)
        throws HandlerExecutionException {
        Helper.checkNull(actionContext, "actionContext");
        HttpServletRequest request = actionContext.getRequest();

        AdminData adminData = Helper.getAdminData(adminDataJndiName, request,
                failRequestAttrName);
        if (adminData == null) {
            return failedResult;
        }
        try {
            AdminSummary summary = adminData.getAdminSummary();
            actionContext.getRequest().setAttribute(summaryRequestAttrName,
                    summary);
        } catch (Exception e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to get admin summary.", failRequestAttrName, e);
            return failedResult;
        }

        return null;
    }
}
