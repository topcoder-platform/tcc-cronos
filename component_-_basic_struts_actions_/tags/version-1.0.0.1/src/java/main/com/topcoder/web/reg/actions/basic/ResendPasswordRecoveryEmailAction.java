/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.dao.PasswordRecoveryDAO;
import com.topcoder.web.common.model.PasswordRecovery;
import com.topcoder.web.common.model.User;

/**
 * <p>
 * This action handles resending the password recovery email. It simply resends the email by calling the base
 * class's sendPasswordRecoveryEmail().
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe because it's mutable. However, dedicated instance of struts
 * action will be created by the struts framework to process the user request, so the struts actions don't
 * need to be thread-safe.
 * </p>
 * <b>Usage</b>
 * <p>
 *
 * <pre>
 * &lt;action name="resendPasswordRecoveryEmail" class="resendPasswordRecoveryEmailAction"&gt;
 *             &lt;result name="success"&gt;
 *                 /recoverPasswordEmailSent.jsp
 *             &lt;/result&gt;
 *             &lt;result name="input"&gt;
 *                 /error.jsp
 *             &lt;/result&gt;
 *         &lt;/action&gt;
 * </pre>
 *
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class ResendPasswordRecoveryEmailAction extends BasePasswordRecoveryAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = ResendPasswordRecoveryEmailAction.class.getName();

    /**
     * <p>
     * The Log object used for logging. It's a constant. So it can only be that constant value It is final and
     * won't change once it is initialized as part of variable declaration to:
     * LogManager.getLog(ResendPasswordRecoveryEmailAction.class.toString()). It is used throughout the class
     * wherever logging is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(ResendPasswordRecoveryEmailAction.class.toString());

    /**
     * <p>
     * The id of the PasswordRecovery representing this password recovery operation. It has both getter and
     * setter. It can be any value. It does not need to be initialized when the instance is created. It is
     * used in execute(), getPasswordRecoveryId(), setPasswordRecoveryId().
     * </p>
     */
    private long passwordRecoveryId;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public ResendPasswordRecoveryEmailAction() {
        // Empty
    }

    /**
     * <p>
     * Execute the action logic of resending password recovery email.
     * </p>
     *
     * @return SUCCESS if no error occurs
     * @throws BasicActionException
     *             if any error occurs
     */
    @Override
    public String execute() throws BasicActionException {
        // Log the entry
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(LOG, signature, null, null);

        try {
            PasswordRecoveryDAO passwordRecoveryDAO = this.getPasswordRecoveryDAO();
            // Find the PasswordRecovery by id:
            PasswordRecovery pr = passwordRecoveryDAO.find(passwordRecoveryId);
            if (pr == null) {
                throw new BasicActionException("PasswordRecovery can not find with id[" + passwordRecoveryId
                    + "]");
            }
            // Get the user:
            User u = pr.getUser();
            // Audit this action:
            audit(u.getHandle(), null, null);
            // Resend the email:
            this.sendPasswordRecoveryEmail(pr);

            // Log the exit
            LoggingWrapperUtility.logExit(LOG, signature, new Object[] {SUCCESS});

            return SUCCESS;
        } catch (BasicActionException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, e);
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "RuntimeException occurs", e));
        }
    }

    /**
     * <p>
     * Getter method for passwordRecoveryId, simply return the value of the namesake field.
     * </p>
     *
     * @return the passwordRecoveryId
     */
    public long getPasswordRecoveryId() {
        return passwordRecoveryId;
    }

    /**
     * <p>
     * Setter method for the passwordRecoveryId, simply set the value to the namesake field.
     * </p>
     *
     * @param passwordRecoveryId
     *            The id of the PasswordRecovery representing this password recovery operation
     */
    public void setPasswordRecoveryId(long passwordRecoveryId) {
        this.passwordRecoveryId = passwordRecoveryId;
    }

}
