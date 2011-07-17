/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.rmi.RemoteException;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;
import com.topcoder.security.UserPrincipal;
import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.shared.util.DBMS;
import com.topcoder.util.log.Level;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.CoderType;
import com.topcoder.web.common.model.SecretQuestion;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserSecurityKey;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This action extends BaseSaveProfileAction to save the account info for the user, and generates the appropriate
 * template data with the updated account info data
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SaveAccountInfoAction extends BaseSaveProfileAction {

    /**
     * <p>
     * Represents validate() method signature constant name.
     * </p>
     */
    private static final String VALIDATE_METHOD_SIGNATURE = "SaveAccountInfoAction.validate()";

    /**
     * <p>
     * This is the PrincipalMgrRemote instance used to manage the user security info.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value. This field will be injected by the container (expected to be done only once), and is not
     * expected to change afterward.
     * </p>
     */
    private PrincipalMgrRemote principalMgr;

    /**
     * <p>
     * Represents the handle of the user that may have referred the registrant to TopCoder.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value. This field will be set during the request phase by the container, and will not change
     * afterwards.
     * </p>
     */
    private String referralUserHandle;

    /**
     * <p>
     * Represents the password confirmation, used to make sure the password was typed correctly.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It will be non-null/empty, and validation requires it to be the same as the password in user. This field will be
     * set during the request phase by the container, and will not change afterwards.
     * </p>
     */
    private String confirmPassword;

    /**
     * <p>
     * Represents the user password.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It will be non-null/empty, and validation requires it to be the same as the confirm password. This field will be
     * set during the request phase by the container, and will not change afterwards.
     * </p>
     */
    private String password;

    /**
     * <p>
     * Creates an instance of SaveAccountInfoAction.
     * </p>
     */
    public SaveAccountInfoAction() {
    }

    /**
     * <p>
     * Checks that all required values have been injected by the system right after construction and injection.
     * </p>
     * <p>
     * This is used to obviate the need to check these values each time in the execute method.
     * </p>
     * @throws ProfileActionConfigurationException - If any required value has not been injected into this class
     */
    @Override
    @PostConstruct
    protected void checkInitialization() {
        super.checkInitialization();
        ValidationUtility.checkNotNull(principalMgr, "principalMgr", ProfileActionConfigurationException.class);
    }

    /**
     * <p>
     * Validates input parameters.
     * </p>
     */
    @Override
    @SuppressWarnings("unchecked")
    public void validate() {
        LoggingWrapperUtility.logEntrance(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null, true, Level.DEBUG);
        try {
            // Verify that the user can change his handle
            com.topcoder.shared.security.User user = Helper.retrieveUserFromSession(this, VALIDATE_METHOD_SIGNATURE);
            String existingHandle = user.getUserName();
            if (!getSavedUser().getHandle().equals(existingHandle)) {
                // Check if the user can change his handle:
                boolean canChange = getUserDAO().canChangeHandle(existingHandle);
                Helper.addFieldError(this, !canChange, "user.handle", "handle.change.ineligible.busy",
                        VALIDATE_METHOD_SIGNATURE);
                // Check that the provided handle is unique
                User existingUser = getUserDAO().find(getSavedUser().getHandle(), true);
                Helper.addFieldError(this, existingUser != null, "user.handle", "handle.duplicate",
                        VALIDATE_METHOD_SIGNATURE);
            }
            // Check that the provided referrerHandle exists and is not self, but only if it is provided
            if (referralUserHandle != null) {
                User existingReferralUser = getUserDAO().find(referralUserHandle, true);
                Helper.addFieldError(this, existingReferralUser == null, "referralUserHandle", "handle.notfound",
                        VALIDATE_METHOD_SIGNATURE);
                Helper.addFieldError(this, referralUserHandle.equals(getSavedUser().getHandle()),
                        "referralUserHandle", "handle.selfreferrential", VALIDATE_METHOD_SIGNATURE);
            }
            // Check that the provided confirmPassword is the same as the password
            Helper.addFieldError(this, !confirmPassword.equals(password), "confirmPassword",
                    "password.match.incorrect", VALIDATE_METHOD_SIGNATURE);
        } catch (ProfileActionException e) {
            Helper.logAndWrapException(getLogger(), VALIDATE_METHOD_SIGNATURE,
                    "Error occurred while retrieving user from session.", e);
            this.addActionError(Helper.FIELD_VALIDATION_ERROR_MESSAGE
                    + "Error occurred while retrieving user from session.");
            return;
        } catch (RuntimeException e) {
            Helper.logAndWrapException(getLogger(), VALIDATE_METHOD_SIGNATURE, "Error occurred in underlying DAO.", e);
            this.addActionError(Helper.FIELD_VALIDATION_ERROR_MESSAGE + "Error occurred in underlying DAO.");
            return;
        }
        LoggingWrapperUtility.logExit(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null, true, Level.DEBUG);
    }

    /**
     * <p>
     * This method is called by the execute method to process the output data.
     * </p>
     * <p>
     * This method will update the specific parts of the User that have been modified.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    protected void processInputData(User user) throws ProfileActionException {
        StringBuilder previousValues = new StringBuilder();
        StringBuilder newValues = new StringBuilder();
        // Edit the password:
        processUserPassword(user, previousValues, newValues);
        // copy handle
        String newHandle = getSavedUser().getHandle();
        String previousHandle = user.getHandle();
        user.setHandle(newHandle);
        Helper.processAuditData(previousValues, newValues, previousHandle, newHandle, "Username");
        // copy secret question and response
        SecretQuestion newSecretQuestion = getSavedUser().getSecretQuestion();
        SecretQuestion previousSecretQuestion = user.getSecretQuestion();
        user.setSecretQuestion(newSecretQuestion);
        Helper.processAuditData(previousValues, newValues, previousSecretQuestion == null ? null
                : previousSecretQuestion.getQuestion(),
                newSecretQuestion == null ? null : newSecretQuestion.getQuestion(), "Secret question");
        Helper.processAuditData(previousValues, newValues, previousSecretQuestion == null ? null
                : previousSecretQuestion.getResponse(),
                newSecretQuestion == null ? null : newSecretQuestion.getResponse(), "Secret question response");
        // copy coder type and coder referral
        Coder savedUserCoder = getSavedUser().getCoder();
        CoderType userCoderType = null;
        CoderType savedUserCoderType = savedUserCoder == null ? null : savedUserCoder.getCoderType();
        // check if user's coder is not null
        if (user.getCoder() != null) {
            userCoderType = user.getCoder().getCoderType();
            user.getCoder().setCoderType(savedUserCoder == null ? null : savedUserCoder.getCoderType());
            user.getCoder().setCoderReferral(savedUserCoder == null ? null : savedUserCoder.getCoderReferral());
        } else {
            if (savedUserCoder != null) {
                // set user's coder as it's null
                user.setCoder(savedUserCoder);
            }
            // both saved and user coders are null
        }
        Helper.processAuditData(previousValues, newValues, userCoderType == null ? null : userCoderType == null ? null
                : userCoderType.getDescription(),
                savedUserCoderType == null ? null : savedUserCoderType.getDescription(), "Student / Professional");
        // copy security key
        UserSecurityKey savedUserSecurityKey = getSavedUser().getUserSecurityKey();
        UserSecurityKey userSecurityKey = user.getUserSecurityKey();
        Helper.processAuditData(previousValues, newValues,
                userSecurityKey == null ? null : userSecurityKey.getSecurityKey(), savedUserSecurityKey == null ? null
                        : savedUserSecurityKey.getSecurityKey(), "Security Key");
        user.setUserSecurityKey(savedUserSecurityKey);
        // make audit
        Helper.makeAudit(this, previousValues, newValues);
    }

    /**
     * <p>
     * Processes user changed password.
     * </p>
     * @param user the user to get password
     * @param previousValues the previous values for audit
     * @param newValues the new values for audit
     * @throws ProfileActionException if any error occurs
     */
    private void processUserPassword(User user, StringBuilder previousValues, StringBuilder newValues)
        throws ProfileActionException {
        try {
            TCSubject subject = new TCSubject(user.getId());
            UserPrincipal principal = new UserPrincipal("", user.getId());
            principal = principalMgr.editPassword(principal, password, subject, DBMS.JTS_OLTP_DATASOURCE_NAME);
            // audit is not required as we don't know previous password value
        } catch (RemoteException e) {
            throw Helper.logAndWrapException(getLogger(), VALIDATE_METHOD_SIGNATURE,
                    "No such user exception occurred.", e);
        } catch (GeneralSecurityException e) {
            throw Helper.logAndWrapException(getLogger(), VALIDATE_METHOD_SIGNATURE,
                    "No such user exception occurred.", e);
        }
    }

    /**
     * <p>
     * This method is called by the execute method to generate the email template data.
     * </p>
     * @param user the user
     * @return the template data string
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    protected String generateEmailTemplateData(User user) {
        StringBuilder emailTemplateDataBuilder = new StringBuilder(Helper.getPreparedEmailTemplate(user));
        emailTemplateDataBuilder.append(Helper.createTag("USERNAME", user.getHandle(), true));
        emailTemplateDataBuilder.append(Helper.createTag("PASSWORD", password, true));
        emailTemplateDataBuilder.append(Helper.createTag("SECRET_QUESTION", user.getSecretQuestion() == null ? null
                : user.getSecretQuestion().getQuestion(), true));
        emailTemplateDataBuilder.append(Helper.createTag("SECRET_QUESTION_RESPONSE",
                user.getSecretQuestion() == null ? null : user.getSecretQuestion().getResponse(), true));
        emailTemplateDataBuilder.append(Helper.createTag("STUDENT_PROFESSIONAL", user.getCoder() == null
                || user.getCoder().getCoderType() == null ? null : user.getCoder().getCoderType().getDescription(),
                true));
        emailTemplateDataBuilder.append(Helper.createTag("SECURITY_KEY", user.getUserSecurityKey() == null ? null
                : user.getUserSecurityKey().getSecurityKey(), true));
        emailTemplateDataBuilder.append(Helper.createTag("REFERRER_HANDLE", this.referralUserHandle, true));
        return Helper.createTag("DATA", emailTemplateDataBuilder.toString(), false);
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public PrincipalMgrRemote getPrincipalMgr() {
        return principalMgr;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param principalMgr the namesake field instance value to set
     */
    public void setPrincipalMgr(PrincipalMgrRemote principalMgr) {
        this.principalMgr = principalMgr;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public String getReferralUserHandle() {
        return referralUserHandle;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param referralUserHandle the namesake field instance value to set
     */
    public void setReferralUserHandle(String referralUserHandle) {
        this.referralUserHandle = referralUserHandle;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param confirmPassword the namesake field instance value to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the password field instance value
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}