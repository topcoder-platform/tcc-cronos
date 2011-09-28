/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This is the base class for all actions of this component. It extends
 * ActionSupport, and implement ServletRequestAware to get access to the HTTP
 * request object. It defines a method for auditing that all subclasses can use.
 * Its billingCostAuditService and servletRequest are also available for the
 * subclasses.
 * </p>
 * <p>
 * <strong> Thread Safety:</strong> This class is not thread-safe because it's
 * mutable. However, dedicated instance of struts action will be created by the
 * struts framework to process the user request, so the struts actions don't
 * need to be thread-safe. And because the BillingCostAuditService it uses is
 * thread-safe, so the action is effectively thread-safe.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseAction extends ActionSupport implements
        ServletRequestAware {
    /**
     * The Log object used for logging. It's a constant. So it can only be that
     * constant value It is final and won't change once it is initialized as
     * part of variable.
     */
    private static final Log LOG = LogManager.getLog(BaseAction.class
            .toString());
    /**
     * The BillingCostAuditService used for auditing purpose and for the
     * subclasses to use. It has both getter and setter. It cannot be null. It
     * does not need to be initialized when the instance is created.
     */
    private BillingCostAuditService billingCostAuditService;
    /**
     * The HTTP Servlet request. This is injected by Struts. It has both getter
     * and setter.It cannot be null. It does not need to be initialized when the
     * instance is created.
     */
    private HttpServletRequest servletRequest;
    /**
     * The action type of this action. This is configured for each subclass for
     * auditing purpose. It is set through setter and doesn't have a getter. It
     * does not need to be initialized when the instance is created.
     */
    private String action;

    /**
     * This is the default constructor for the class.
     *
     */
    protected BaseAction() {
    }

    /**
     * Perform auditing.
     *
     * @throws Exception if any error occurs
     *
     */
    protected void audit() throws Exception {
        final String methodName = "BaseAction.audit";
        Helper.logEnterMethod(LOG, methodName);
        try {
            AccountingAuditRecord record = new AccountingAuditRecord();
            // Set the action
            record.setAction(action);
            // Set the timestamp
            record.setTimestamp(new Date());
            // Get the session from servletRequest
            HttpSession session = servletRequest.getSession();
            SessionData sessionData = new SessionData(session);
            // Set the user name
            record.setUserName(sessionData.getCurrentUserHandle());
            // Persist the audit record
            billingCostAuditService.auditAccountingAction(record);
        } catch (Exception e) {
            Helper.logAndThrow(LOG, methodName, e);
        }
        Helper.logExitMethod(LOG, methodName, null);
    }

    /**
     * Getter for the billing cost audit service.
     *
     * @return the billing cost audit service
     */
    protected BillingCostAuditService getBillingCostAuditService() {
        return billingCostAuditService;
    }

    /**
     * Getter for the servletRequest.
     *
     * @return the servletRequest
     */
    protected HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    /**
     * Setter for the billingCostAuditService variable.
     *
     * @param billingCostAuditService The BillingCostAuditService used for
     *            auditing purpose and for the subclasses to use
     */
    public void setBillingCostAuditService(
            BillingCostAuditService billingCostAuditService) {
        this.billingCostAuditService = billingCostAuditService;
    }

    /**
     * Setter for the HTTP Servlet request.
     *
     * @param servletRequest the HTTP servlet request. This is injected by
     *            Struts.
     */
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    /**
     * Setter for the action type of this action.
     *
     * @param action The action type of this action. This is configured for each
     *            subclass for auditing purpose.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * This method is called right after the dependency of this class is fully
     * injected. It checks if the injected values are valid.
     *
     * @throws BillingCostActionConfigurationException If any of the injected
     *             values is invalid.
     *
     */
    public void checkConfiguration() {
        Helper.checkConfig(billingCostAuditService, "billingCostAuditService");
        // action should not be null or empty.
        Helper.checkConfig(action, "action");
        if (action.trim().length() == 0) {
            throw new BillingCostActionConfigurationException(
                    "Action should not be empty.");
        }
    }
}
