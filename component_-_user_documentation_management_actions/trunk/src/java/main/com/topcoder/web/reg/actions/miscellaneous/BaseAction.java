/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.WebAuthentication;

/**
 * <p>
 * This is the base action of all actions of this component. It extends ActionSupport and implement SessionAware and
 * ServletRequestAware to get access to request and session. It provides the method to get the id of the current user
 * from the WebAuthentication in the session. It has a method that performs auditing.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread-safe because it's mutable. However, dedicated instance of
 * struts action will be created by the struts framework to process the user request, so the struts actions don’t need
 * to be thread-safe.
 * </p>
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseAction extends ActionSupport implements ServletRequestAware, SessionAware {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 936948567580191032L;
    /**
     * <p>
     * The Log object used for logging.It's a constant. So it can only be that constant value It is final and won't
     * change once it is initialized as part of variable declaration to:
     * LogManager.getLog(BaseAction.class.toString()).It is used throughout the class wherever logging is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(BaseAction.class.toString());
    /**
     * <p>
     * The UserDAO instance used to perform persistence operation on User. It is set through setter and doesn't have a
     * getter.It cannot be null. (Note that the above statement applies only after the setter has been called as part of
     * the IoC initialization. This field's value has no restriction before the IoC initialization) It does not need to
     * be initialized when the instance is created.It is used in setUserDAO(), audit().
     * </p>
     */
    private UserDAO userDAO;
    /**
     * <p>
     * The AuditDAO instance used to perform auditing. It is set through setter and doesn't have a getter. It cannot be
     * null. (Note that the above statement applies only after the setter has been called as part of the IoC
     * initialization. This field's value has no restriction before the IoC initialization).It does not need to be
     * initialized when the instance is created.It is used in setAuditDAO(), audit().
     * </p>
     */
    private AuditDAO auditDAO;
    /**
     * <p>
     * The session key for WebAuthentication that is put into session. It is set through setter and doesn't have a
     * getter.It cannot be null or empty. (Note that the above statement applies only after the setter has been called
     * as part of the IoC initialization. This field's value has no restriction before the IoC initialization).It can be
     * changed after it is initialized as part of variable declaration to: "webAuthentication".It is used in
     * getUserId(), setWebAuthenticationSessionKey().
     * </p>
     */
    private String webAuthenticationSessionKey = "webAuthentication";
    /**
     * <p>
     * The http session's map. This is injected by Struts. It has both getter and setter.It cannot be null but can be
     * empty. The key can be any value. The contained values can be any value. It does not need to be initialized when
     * the instance is created.It is used in getUserId(), setSession(), getSession().
     * </p>
     */
    private Map < String, Object > session;
    /**
     * <p>
     * The http servlet request. This is injected by Struts. It is set through setter and doesn't have a getter. It
     * cannot be null.It does not need to be initialized when the instance is created.It is used in audit(),
     * setServletRequest().
     * </p>
     */
    private HttpServletRequest servletRequest;
    /**
     * <p>
     * The operation type of this action. This is configured for each subclass for auditing purpose. It is set through
     * setter and doesn't have a getter.It cannot be null or empty. (Note that the above statement applies only after
     * the setter has been called as part of the IoC initialization. This field's value has no restriction before the
     * IoC initialization). It does not need to be initialized when the instance is created.It is used in
     * setOperationType(), audit().
     * </p>
     */
    private String operationType;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    protected BaseAction() {
        // do nothing
    }

    /**
     * <p>
     * Perform auditing for the current user.
     * </p>
     * @throws UserDocumentationManagementActionsException
     *             if any error occurs
     */
    protected void audit() throws UserDocumentationManagementActionsException {
        User user = userDAO.find(getUserId());
        AuditRecord auditRecord = new AuditRecord();
        auditRecord.setHandle(user.getHandle());
        auditRecord.setIpAddress(servletRequest.getRemoteAddr());
        auditRecord.setTimestamp(new Date());
        auditRecord.setOperationType(operationType);

        auditDAO.audit(auditRecord);
    }

    /**
     * <p>
     * Get the id of the current logged in user.
     * </p>
     * @throws UserDocumentationManagementActionsException
     *             if any error occurs
     * @return the user id retrieved from session
     */
    protected long getUserId() throws UserDocumentationManagementActionsException {
        try {
            WebAuthentication authentication = (WebAuthentication) session.get(webAuthenticationSessionKey);
            return authentication.getActiveUser().getId();
        } catch (NullPointerException e) {
            LoggingWrapperUtility.logException(LOG, "BaseAction#getUserId()", e);
            throw new UserDocumentationManagementActionsException("Unable to get User from session", e);
        }

    }

    /**
     * <p>
     * Getter for the namesake instance variable.
     * </p>
     * @return the session object
     */
    protected Map < String, Object > getSession() {
        return session;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param auditDAO
     *            The AuditDAO instance used to perform auditing.
     */
    public void setAuditDAO(AuditDAO auditDAO) {
        this.auditDAO = auditDAO;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param session
     *            The http session's map. This is injected by Struts.
     */
    public void setSession(Map < String, Object > session) {
        this.session = session;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param servletRequest
     *            The http servlet request. This is injected by Struts.
     */
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param webAuthenticationSessionKey
     *            The session key for WebAuthentication that is put into session.
     */
    public void setWebAuthenticationSessionKey(String webAuthenticationSessionKey) {
        this.webAuthenticationSessionKey = webAuthenticationSessionKey;
    }

    /**
     * <p>
     * Setter for the namesake instance variable. Simply assign the value to the instance variable.
     * </p>
     * @param operationType
     *            The operation type of this action. This is configured for each subclass for auditing purpose.
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    /**
     * <p>
     * Setter for the namesake instance variable. Simply assign the value to the instance variable.
     * </p>
     * @param userDAO
     *            The UserDAO instance used to perform persistence operation on User.
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * <p>
     * This method is called right after the dependency of this class is fully injected. It checks if the injected
     * values are valid.
     * </p>
     * @throws UserDocumentationManagementActionsConfigurationException
     *             if any of the injected values is invalid.
     */
    @PostConstruct
    public void checkConfiguration() {
        ValidationUtility.checkNotNull(userDAO, "userDAO",
                UserDocumentationManagementActionsConfigurationException.class);
        ValidationUtility.checkNotNull(auditDAO, "auditDAO",
                UserDocumentationManagementActionsConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(webAuthenticationSessionKey, "webAuthenticationSessionKey",
                UserDocumentationManagementActionsConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(operationType, "operationType",
                UserDocumentationManagementActionsConfigurationException.class);
    }
}
