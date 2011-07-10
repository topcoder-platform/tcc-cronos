/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.util.Date;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;
import com.topcoder.security.UserPrincipal;
import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.shared.security.LoginException;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.util.DBMS;
import com.topcoder.util.keygenerator.random.RandomStringGenerator;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.dao.PasswordRecoveryDAO;
import com.topcoder.web.common.model.PasswordRecovery;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;

/**
 * <p>
 * This action handles resetting the password of a user. It basically gets the PasswordRecovery from database,
 * checks if it's not expired, uses RandomStringGenerator to generate a new password for the user, calls
 * PrincipalMgrRemote to save the new password, and finally automatically logs the user in.
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
 * &gt;action name="resetPassword" class="resetPasswordAction"&gt;
 *             &gt;result name="success"&gt;
 *                 /resetPasswordConfirmation.jsp
 *             &gt;/result&gt;
 *             &gt;result name="input"&gt;
 *                 /error.jsp
 *             &gt;/result&gt;
 *         &gt;/action&gt;
 * </pre>
 *
 * </p>
 *
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class ResetPasswordAction extends BasePasswordAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = ResetPasswordAction.class.getName();

    /**
     * <p>
     * The Log object used for logging. It's a constant. So it can only be that constant value It is final and
     * won't change once it is initialized as part of variable declaration to:
     * LogManager.getLog(ResetPasswordAction.class.toString()). It is used throughout the class wherever
     * logging is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(ResetPasswordAction.class.toString());

    /**
     * <p>
     * The id of the PasswordRecovery for resetting the password. It has both getter and setter. It can be any
     * value. It does not need to be initialized when the instance is created. It is used in execute(),
     * getPasswordRecoveryId(), setPasswordRecoveryId(), validate().
     * </p>
     */
    private long passwordRecoveryId;

    /**
     * <p>
     * The hash code of the PasswordRecovery for resetting the password. It has both getter and setter. It
     * cannot be null or empty. (Note that the above statement applies only after the field has passed Struts
     * validation. This field's value has no restriction before then.) It does not need to be initialized when
     * the instance is created. It is used in getHashCode(), validate(), setHashCode().
     * </p>
     */
    private String hashCode;

    /**
     * <p>
     * The newly generated password. It is accessed through getter and doesn't have a setter. It cannot be
     * null or empty. It does not need to be initialized when the instance is created. It is used in
     * execute(), getNewPassword().
     * </p>
     */
    private String newPassword;

    /**
     * <p>
     * The minimal password length. It is set through setter and doesn't have a getter. It must be positive.
     * (Note that the above statement applies only after the setter has been called as part of the IoC
     * initialization. This field's value has no restriction before the IoC initialization) It does not need
     * to be initialized when the instance is created. It is used in execute(), setMinimalPasswordLength().
     * Its value legality is checked in checkConfiguration() method.
     * </p>
     */
    private int minimalPasswordLength;

    /**
     * <p>
     * The maximal password length. It is set through setter and doesn't have a getter. It must be positive.
     * (Note that the above statement applies only after the setter has been called as part of the IoC
     * initialization. This field's value has no restriction before the IoC initialization) It does not need
     * to be initialized when the instance is created. It is used in execute(), setMaximalPasswordLength().
     * Its value legality is checked in checkConfiguration() method.
     * </p>
     */
    private int maximalPasswordLength;

    /**
     * <p>
     * The EJB used for editing the password. It is set through setter and doesn't have a getter. It cannot be
     * null. (Note that the above statement applies only after the setter has been called as part of the IoC
     * initialization. This field's value has no restriction before the IoC initialization) It does not need
     * to be initialized when the instance is created. It is used in execute(), setPrincipalMgrRemote(). Its
     * value legality is checked in checkConfiguration() method.
     * </p>
     */
    private PrincipalMgrRemote principalMgrRemote;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public ResetPasswordAction() {
        // Empty
    }

    /**
     * <p>
     * Execute the action logic of resetting the password.
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
            // Find the PasswordRecover by id:
            PasswordRecovery pr = passwordRecoveryDAO.find(passwordRecoveryId);
            // Get the user:
            if (pr == null) {
                throw new BasicActionException("There is no PasswordRecovery record with id:"
                    + passwordRecoveryId);
            }
            User user = pr.getUser();
            // Set the PasswordRecovery as 'used':
            pr.setUsed(true);
            // Update the PasswordRecovery:
            passwordRecoveryDAO.saveOrUpdate(pr);
            // Create a RandomStringGenerator that generates alphanumeric strings within given length
            // restrictions:
            RandomStringGenerator generator = new RandomStringGenerator(
                RandomStringGenerator.ALPHANUMERIC_SYMBOLS, minimalPasswordLength, maximalPasswordLength,
                new SecureRandom());
            // Get the new password:
            newPassword = generator.nextString();
            // Audit this action:
            audit(user.getHandle(), null, newPassword);
            TCSubject tcs = new TCSubject(user.getId());
            // Create a UserPrincipal from user:
            UserPrincipal myPrincipal = new UserPrincipal("", user.getId());
            // Save the new password:
            principalMgrRemote.editPassword(myPrincipal, newPassword, tcs, DBMS.JTS_OLTP_DATASOURCE_NAME);
            // Get the authentication from session:
            WebAuthentication authentication = this.getWebAuthenticationFromSession();
            // Login the user:
            authentication.login(new SimpleUser(0, user.getHandle(), newPassword), false);

            // Log the exit
            LoggingWrapperUtility.logExit(LOG, signature, new Object[] {SUCCESS});

            return SUCCESS;
        } catch (BasicActionException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, e);
        } catch (RemoteException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "RemoteException occurs while editing password", e));
        } catch (GeneralSecurityException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "RemoteException occurs while editing password", e));
        } catch (LoginException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "LoginException occurs while logging in", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "RuntimeException occurs", e));
        }

    }

    /**
     * <p>
     * Validate the input parameters. If any error is caught, log it and add the error message to
     * this.getActionErrors() and return.
     * </p>
     */
    @Override
    public void validate() {
        // Log the entry
        final String signature = CLASS_NAME + ".validate()";
        LoggingWrapperUtility.logEntrance(LOG, signature, null, null);

        try {
            PasswordRecoveryDAO passwordRecoveryDAO = this.getPasswordRecoveryDAO();
            // Find the PasswordRecover by id:
            PasswordRecovery pr = passwordRecoveryDAO.find(passwordRecoveryId);
            if (!pr.hash().equals(hashCode)) {
                this.addActionError(getText("hashCode.invalid"));
            }
            if (pr.getExpireDate().before(new Date())) {
                this.addActionError(getText("passwordRecovery.expired"));
            }
            if (pr.isUsed()) {
                this.addActionError(getText("passwordRecovery.used"));
            }

        } catch (NullPointerException e) {
            LoggingWrapperUtility.logException(LOG, signature, e);
            this.addActionError("NullPointerException occurs, the message is:" + e.getMessage());
        }

        // Log the exit
        LoggingWrapperUtility.logExit(LOG, signature, null);
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
     *            The id of the PasswordRecovery for resetting the password
     */
    public void setPasswordRecoveryId(long passwordRecoveryId) {
        this.passwordRecoveryId = passwordRecoveryId;
    }

    /**
     * <p>
     * Getter method for hashCode, simply return the value of the namesake field.
     * </p>
     *
     * @return the hashCode
     */
    public String getHashCode() {
        return hashCode;
    }

    /**
     * <p>
     * Setter method for the hashCode, simply set the value to the namesake field.
     * </p>
     *
     * @param hashCode
     *            The hash code of the PasswordRecovery for resetting the password
     */
    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    /**
     * <p>
     * Getter method for newPassword, simply return the value of the namesake field.
     * </p>
     *
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * <p>
     * Setter method for the minimalPasswordLength, simply set the value to the namesake field.
     * </p>
     *
     * @param minimalPasswordLength
     *            The minimal password length
     */
    public void setMinimalPasswordLength(int minimalPasswordLength) {
        this.minimalPasswordLength = minimalPasswordLength;
    }

    /**
     * <p>
     * Setter method for the maximalPasswordLength, simply set the value to the namesake field.
     * </p>
     *
     * @param maximalPasswordLength
     *            The maximal password length
     */
    public void setMaximalPasswordLength(int maximalPasswordLength) {
        this.maximalPasswordLength = maximalPasswordLength;
    }

    /**
     * <p>
     * Setter method for the principalMgrRemote, simply set the value to the namesake field.
     * </p>
     *
     * @param principalMgrRemote
     *            The EJB used for editing the password
     */
    public void setPrincipalMgrRemote(PrincipalMgrRemote principalMgrRemote) {
        this.principalMgrRemote = principalMgrRemote;
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
        ValidationUtility.checkNotNull(principalMgrRemote, "principalMgrRemote",
            BasicStrutsActionsConfigurationException.class);
        ValidationUtility.checkPositive(minimalPasswordLength, "minimalPasswordLength",
            BasicStrutsActionsConfigurationException.class);
        ValidationUtility.checkPositive(maximalPasswordLength, "maximalPasswordLength",
            BasicStrutsActionsConfigurationException.class);
        if (minimalPasswordLength > maximalPasswordLength) {
            throw new BasicStrutsActionsConfigurationException(
                "minimalPasswordLength can not greater than maximalPasswordLength");
        }
    }
}
