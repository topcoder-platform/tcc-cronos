/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.rmi.RemoteException;
import java.sql.SQLException;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.model.User;
import com.topcoder.web.tc.Constants;
import com.topcoder.web.tc.controller.legacy.pacts.bean.DataInterfaceBean;

/**
 * <p>
 * This action extends the BasePreferencesAction, and it allows user to manage the payment accrual amount.
 * </p>
 * <p>
 * All its instance variables have setters/getters and will be injected.
 * </p>
 * <p>
 * Thread safety: This class is mutable and not thread safe, however will be used thread-safely in Struts framework.
 * </p>
 * @author maksimilian, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PaymentPreferencesAction extends BasePreferencesAction {

    /**
     * <p>
     * Represents audit operation type.
     * </p>
     */
    private static final String AUDIT_OPERATION_TYPE = "Payment Preferences Update";

    /**
     * <p>
     * Represents execute() method signature.
     * </p>
     */
    private static final String EXECUTE_METHOD_SIGNATURE = "PaymentPreferencesAction.execute()";

    /**
     * <p>
     * Represents validate() method signature.
     * </p>
     */
    private static final String VALIDATE_METHOD_SIGNATURE = "PaymentPreferencesAction.validate()";

    /**
     * <p>
     * Represents the current user payment accrual amount.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private int currentPaymentAccrualAmount;

    /**
     * <p>
     * Represents the data interface bean to manage the user's payment accrual amount.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null. Used in getter/setter and execute method.
     * </p>
     */
    private DataInterfaceBean dataInterfaceBean;

    /**
     * <p>
     * Creates an instance of PaymentPreferencesAction.
     * </p>
     */
    public PaymentPreferencesAction() {
        super();
    }

    /**
     * <p>
     * Execute method is responsible for viewing/updating the user payment preferences.
     * </p>
     * @return a string representing the logical result of the execution. If an action succeeds, it returns
     *         Action.SUCCESS. If some error occurs, the exception will be thrown.
     * @throws UserPreferencesActionExecutionException if any unexpected error occurs. (it's used to wrap all
     *             underlying exceptions)
     * @throws PreferencesActionDiscardException if error occurs when attempting to discard
     */
    public String execute() throws UserPreferencesActionExecutionException, PreferencesActionDiscardException {
        LoggingWrapperUtility.logEntrance(getLogger(), EXECUTE_METHOD_SIGNATURE, null, null);
        // get the user:
        User user = getLoggedInUser();
        if (getAction().equals(Helper.SUBMIT_ACTION)) {
            try {
                // backup the accrual amount:
                int oldValue = (int) dataInterfaceBean.getUserAccrualThreshold(user.getId());
                ActionContext.getContext().getSession().put(getBackupSessionKey(), oldValue);
                // update the user payment preferences in persistence:
                dataInterfaceBean.saveUserAccrualThreshold(user.getId(), currentPaymentAccrualAmount);
                audit(String.valueOf(oldValue), String.valueOf(currentPaymentAccrualAmount), AUDIT_OPERATION_TYPE);
            } catch (RemoteException e) {
                throw Helper.logAndWrapException(getLogger(), EXECUTE_METHOD_SIGNATURE,
                        "Remote exception occurs while saving or getting user accrual threshold.", e,
                        UserPreferencesActionExecutionException.class);
            } catch (SQLException e) {
                throw Helper.logAndWrapException(getLogger(), EXECUTE_METHOD_SIGNATURE,
                        "SQL exception occurs while saving or getting user accrual threshold.", e,
                        UserPreferencesActionExecutionException.class);
            }
        }
        // handle discard action
        if (getAction().equals(Helper.DISCARD_ACTION)) {
            Integer amount = (Integer) ActionContext.getContext().getSession().get(getBackupSessionKey());
            if (amount == null) {
                throw Helper.logAndWrapException(getLogger(), EXECUTE_METHOD_SIGNATURE,
                        "Backup payment accrual amount is not present in session.", null,
                        PreferencesActionDiscardException.class);
            }
            try {
                dataInterfaceBean.saveUserAccrualThreshold(user.getId(), amount.intValue());
            } catch (RemoteException e) {
                throw Helper.logAndWrapException(getLogger(), EXECUTE_METHOD_SIGNATURE,
                        "Remote exception occurs while saving user accrual threshold.", e,
                        PreferencesActionDiscardException.class);
            }
        }
        // send email if needed
        if (isEmailSendFlag()) {
            sendEmail();
        }
        LoggingWrapperUtility.logExit(getLogger(), EXECUTE_METHOD_SIGNATURE, new String[] {SUCCESS});
        return SUCCESS;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the currentPaymentAccrualAmount
     */
    public int getCurrentPaymentAccrualAmount() {
        return currentPaymentAccrualAmount;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the dataInterfaceBean
     */
    public DataInterfaceBean getDataInterfaceBean() {
        return dataInterfaceBean;
    }

    /**
     * <p>
     * Prepares the action.
     * </p>
     * <p>
     * It will get the logged in user and expose its payment preferences, so then they can be updated.
     * </p>
     * @throws Exception if any field has invalid value
     */
    public void prepare() throws Exception {
        super.prepare();
        ValidationUtility.checkNotNull(dataInterfaceBean, "dataInterfaceBean",
                UserPreferencesActionConfigurationException.class);
        try {
            currentPaymentAccrualAmount = (int) dataInterfaceBean.getUserAccrualThreshold(getLoggedInUser().getId());
        } catch (RemoteException e) {
            throw new UserPreferencesActionConfigurationException(
                    "Remote exception occurs while getting user's accrual threshold", e);
        } catch (SQLException e) {
            throw new UserPreferencesActionConfigurationException(
                    "SQL exception occurs while getting user's accrual threshold", e);
        }
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param currentPaymentAccrualAmount the currentPaymentAccrualAmount to set
     */
    public void setCurrentPaymentAccrualAmount(int currentPaymentAccrualAmount) {
        this.currentPaymentAccrualAmount = currentPaymentAccrualAmount;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param dataInterfaceBean the dataInterfaceBean to set
     */
    public void setDataInterfaceBean(DataInterfaceBean dataInterfaceBean) {
        this.dataInterfaceBean = dataInterfaceBean;
    }

    /**
     * <p>
     * Validates the inputed parameters.
     * </p>
     */
    public void validate() {
        LoggingWrapperUtility.logEntrance(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null);
        Helper.checkIntegerFieldGreater(getLogger(), VALIDATE_METHOD_SIGNATURE, this, currentPaymentAccrualAmount,
                "currentPaymentAccrualAmount", Constants.MINIMUM_PAYMENT_ACCRUAL_AMOUNT);
        LoggingWrapperUtility.logExit(getLogger(), VALIDATE_METHOD_SIGNATURE, null);
    }
}
