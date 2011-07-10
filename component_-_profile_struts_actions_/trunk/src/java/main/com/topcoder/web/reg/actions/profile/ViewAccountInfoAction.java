/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.rmi.RemoteException;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.NoSuchUserException;
import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.CoderReferral;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This action extends BaseDisplayProfileAction and processes the output by taking the passed user, which includes the
 * account info, as well as the handle of the referral user, and sets it to the output.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewAccountInfoAction extends BaseDisplayProfileAction {

    /**
     * <p>
     * Represents processOutputData() method constant signature name.
     * </p>
     */
    private static final String PROCESS_OUTPUT_DATA_METHOD_SIGNATURE = "ViewAccountInfoAction.processOutputData()";

    /**
     * <p>
     * Represents the handle of the user that may have referred the registrant to TopCoder.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value. This field will be set by the execute method.
     * </p>
     */
    private String referralUserHandle;

    /**
     * <p>
     * Represents the user's password.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value. This field will be set by the execute method.
     * </p>
     */
    private String password;

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
     * Creates an instance of ViewAccountInfoAction.
     * </p>
     */
    public ViewAccountInfoAction() {
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
     * This method is called by the execute method to process the output data.
     * </p>
     * <p>
     * This method will simply set this to the output and audit the call It will also get the handle of the referral
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    protected void processOutputData(User user) throws ProfileActionException {
        setDisplayedUser(user);
        // check if referral user exists, if exist get it
        Coder userCoder = getDisplayedUser().getCoder();
        CoderReferral userCoderReferral = userCoder == null ? null : userCoder.getCoderReferral();
        Integer referralId =
                userCoderReferral == null || userCoderReferral.getReferral() == null ? null : userCoderReferral
                        .getReferral().getId();
        if (referralId != null) {
            User referralUser =
                    Helper.retrieveUserFromDatabase(this, referralId.longValue(), PROCESS_OUTPUT_DATA_METHOD_SIGNATURE);
            this.referralUserHandle = referralUser.getHandle();
        }
        // get user password
        try {
            this.password = principalMgr.getPassword(user.getId());
        } catch (NoSuchUserException e) {
            throw Helper.logAndWrapException(getLogger(), PROCESS_OUTPUT_DATA_METHOD_SIGNATURE,
                    "No such user exception occurred.", e);
        } catch (RemoteException e) {
            throw Helper.logAndWrapException(getLogger(), PROCESS_OUTPUT_DATA_METHOD_SIGNATURE,
                    "Remote exception occurred.", e);
        } catch (GeneralSecurityException e) {
            throw Helper.logAndWrapException(getLogger(), PROCESS_OUTPUT_DATA_METHOD_SIGNATURE,
                    "General security exception occurred.", e);
        }
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

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the principalMgr field instance value
     */
    public PrincipalMgrRemote getPrincipalMgr() {
        return principalMgr;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param principalMgr the principalMgr to set
     */
    public void setPrincipalMgr(PrincipalMgrRemote principalMgr) {
        this.principalMgr = principalMgr;
    }
}