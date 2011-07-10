/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import java.util.GregorianCalendar;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.dao.PasswordRecoveryDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.PasswordRecovery;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.validation.StringInput;
import com.topcoder.web.common.validation.ValidationResult;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.validation.EmailValidator;

/**
 * <p>
 * This action handles the password recovery. It basically gets the user from UserDAO, and persists a new
 * PasswordRecovery into database, and then send the password recovery email to the user.
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
 * &gt;action name="recoverPassword" class="recoverPasswordAction"&lt;
 *             &gt;result name="success"&lt;
 *                 /recoverPasswordEmailSent.jsp
 *             &gt;/result&lt;
 *             &gt;result name="input"&lt;
 *                 /recoverPassword.jsp
 *             &gt;/result&lt;
 *         &gt;/action&lt;
 * </pre>
 *
 * </p>
 *
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class RecoverPasswordAction extends BasePasswordRecoveryAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = RecoverPasswordAction.class.getName();

    /**
     * <p>
     * The Log object used for logging. It's a constant. So it can only be that constant value It is final and
     * won't change once it is initialized as part of variable declaration to:
     * LogManager.getLog(RecoverPasswordAction.class.toString()). It is used throughout the class wherever
     * logging is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(RecoverPasswordAction.class.toString());

    /**
     * <p>
     * The user handle. It has both getter and setter. It can be any value. It does not need to be initialized
     * when the instance is created. It is used in getHandle(), execute(), setHandle(), validate().
     * </p>
     */
    private String handle;

    /**
     * <p>
     * The user email. It has both getter and setter. It can be any value. It does not need to be initialized
     * when the instance is created. It is used in execute(), setEmail(), validate(), getEmail().
     * </p>
     */
    private String email;

    /**
     * <p>
     * The number of minutes that the password recovery will expire in. It is set through setter and doesn't
     * have a getter. It must be non-negative. (Note that the above statement applies only after the setter
     * has been called as part of the IoC initialization. This field's value has no restriction before the IoC
     * initialization) It does not need to be initialized when the instance is created. It is used in
     * setPasswordRecoveryExpiration(), execute(). Its value legality is checked in checkConfiguration()
     * method.
     * </p>
     */
    private int passwordRecoveryExpiration;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public RecoverPasswordAction() {
        // Empty
    }

    /**
     * <p>
     * Execute the action logic of recovering password.
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
            // Get the user dao:
            UserDAO userDAO = this.getUserDAO();
            User user = null;
            if (!checkNullOrEmpty(handle)) {
                // Get the user by handle:
                user = userDAO.find(handle, false);
            } else if (!checkNullOrEmpty(email)) {
                // Find the user by email:
                user = userDAO.find(email);
            }

            if (user == null) {
                throw new BasicActionException("The user can not be found with handle[" + handle
                    + "], or email[" + email + "]");
            }
            // Audit the action:
            audit(user.getHandle(), null, null);
            // Create a new PasswordRecovery:
            PasswordRecovery pr = new PasswordRecovery();
            // This block populates PasswordRecovery
            // Set the user:
            pr.setUser(user);
            // Set the recovery address:
            Email primaryEmail = user.getPrimaryEmailAddress();
            ValidationUtility.checkNotNull(primaryEmail, "The primaryEmail of user with id:" + user.getId(),
                BasicActionException.class);
            pr.setRecoveryAddress(email != null ? email : primaryEmail.getAddress());
            GregorianCalendar expire = new GregorianCalendar();
            // Compute the expiration date:
            expire.add(GregorianCalendar.MINUTE, passwordRecoveryExpiration);
            // Set the expiration date:
            pr.setExpireDate(expire.getTime());

            PasswordRecoveryDAO passwordRecoveryDAO = this.getPasswordRecoveryDAO();
            // Persist the PasswordRecovery:
            passwordRecoveryDAO.saveOrUpdate(pr);
            // Send the password recovery email:
            this.sendPasswordRecoveryEmail(pr);

            // Log the exit
            LoggingWrapperUtility.logExit(LOG, signature, new Object[] {SUCCESS});

            return SUCCESS;
        } catch (BasicActionException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, e);
        } catch (IllegalStateException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "IllegalStateException occurs while getting primary email address", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "RuntimeException occurs", e));
        }
    }

    /**
     * <p>
     * Validate the input parameters. If any error is caught, log it and add the error message to
     * this.getActionErrors() or this.addFieldError() and return.
     * </p>
     */
    @Override
    public void validate() {
        // Log the entry
        final String signature = CLASS_NAME + ".validate()";
        LoggingWrapperUtility.logEntrance(LOG, signature, null, null);

        // Get the user dao:
        UserDAO userDAO = this.getUserDAO();
        if (checkNullOrEmpty(handle) && checkNullOrEmpty(email)) {
            this.addActionError(getText("passwordRecovery.handleOrEmail"));
        }

        if (handle != null) {
            // Get the user by handle:
            User user = userDAO.find(handle, false);

            if (user == null) {
                this.addFieldError("handle", getText("handle.invalid"));
            }
        }

        if (email != null) {
            // Validate the email:
            ValidationResult validationResult = new EmailValidator().validate(new StringInput(email));

            if (!validationResult.isValid()) {
                this.addFieldError("email", getText("email.invalid"));
            } else {
                // Find the user by email:
                User user = userDAO.find(email);

                if (user == null) {
                    this.addFieldError("email", getText("email.invalid"));
                }
            }
        }

        // Log the exit
        LoggingWrapperUtility.logExit(LOG, signature, null);
    }

    /**
     * <p>
     * Check the string whether null or empty.
     * </p>
     *
     * @param value
     *            the string
     * @return the string whether null or empty
     */
    private static boolean checkNullOrEmpty(String value) {
        if (value == null || value.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Getter method for handle, simply return the value of the namesake field.
     * </p>
     *
     * @return the handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * <p>
     * Setter method for the handle, simply set the value to the namesake field.
     * </p>
     *
     * @param handle
     *            The user handle
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * <p>
     * Getter method for email, simply return the value of the namesake field.
     * </p>
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>
     * Setter method for the email, simply set the value to the namesake field.
     * </p>
     *
     * @param email
     *            The user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>
     * Setter method for the passwordRecoveryExpiration, simply set the value to the namesake field.
     * </p>
     *
     * @param passwordRecoveryExpiration
     *            The number of minutes that the password recovery will expire in
     */
    public void setPasswordRecoveryExpiration(int passwordRecoveryExpiration) {
        this.passwordRecoveryExpiration = passwordRecoveryExpiration;
    }

    /**
     * <p>
     * This method is called right after the dependency of this class is fully injected. It checks if the
     * injected values are valid.
     * </p>
     *
     * @throws BasicStrutsActionsConfigurationException
     *             if any of the injected values is invalid.
     */
    @Override
    public void checkConfiguration() {
        super.checkConfiguration();
        // Check the value of the following fields according to their legal value specification in the field
        // variable documentation:
        ValidationUtility.checkNotNegative(passwordRecoveryExpiration, "passwordRecoveryExpiration",
            BasicStrutsActionsConfigurationException.class);
    }
}
