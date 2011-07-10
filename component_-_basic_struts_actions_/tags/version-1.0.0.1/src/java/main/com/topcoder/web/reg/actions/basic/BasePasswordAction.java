/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.dao.PasswordRecoveryDAO;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;

/**
 * <p>
 * This is the base action of all password-related actions of this component. It simply has a
 * passwordRecoveryDAO for subclasses to use.
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe because it's mutable. However, dedicated instance of struts
 * action will be created by the struts framework to process the user request, so the struts actions don't
 * need to be thread-safe.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BasePasswordAction extends BaseUserDAOAwareAction {
    /**
     * <p>
     * The PasswordRecoveryDAO instance used to perform persistence operation on PasswordRecovery. It has both
     * getter and setter. It cannot be null. (Note that the above statement applies only after the setter has
     * been called as part of the IoC initialization. This field's value has no restriction before the IoC
     * initialization) It does not need to be initialized when the instance is created. It is used in
     * getPasswordRecoveryDAO(), setPasswordRecoveryDAO().
     * </p>
     */
    private PasswordRecoveryDAO passwordRecoveryDAO;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    protected BasePasswordAction() {
        // Empty
    }

    /**
     * <p>
     * Getter method for passwordRecoveryDAO, simply return the value of the namesake field.
     * </p>
     *
     * @return the passwordRecoveryDAO
     */
    protected PasswordRecoveryDAO getPasswordRecoveryDAO() {
        return passwordRecoveryDAO;
    }

    /**
     * <p>
     * Setter method for the passwordRecoveryDAO, simply set the value to the namesake field.
     * </p>
     *
     * @param passwordRecoveryDAO
     *            The PasswordRecoveryDAO instance used to perform persistence operation on PasswordRecovery
     */
    public void setPasswordRecoveryDAO(PasswordRecoveryDAO passwordRecoveryDAO) {
        this.passwordRecoveryDAO = passwordRecoveryDAO;
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
        ValidationUtility.checkNotNull(passwordRecoveryDAO, "passwordRecoveryDAO",
            BasicStrutsActionsConfigurationException.class);
    }
}
