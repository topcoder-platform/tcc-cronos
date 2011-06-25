/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;

/**
 * <p>
 * This is the base action of all actions that uses UserDAO. It simply has a userDAO for subclasses to use.
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
public abstract class BaseUserDAOAwareAction extends BaseAction {
    /**
     * <p>
     * The UserDAO instance used to perform persistence operation on User. It has both getter and setter. It
     * cannot be null. (Note that the above statement applies only after the setter has been called as part of
     * the IoC initialization. This field's value has no restriction before the IoC initialization) It does
     * not need to be initialized when the instance is created. It is used in setUserDAO(), getUserDAO().
     * </p>
     */
    private UserDAO userDAO;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    protected BaseUserDAOAwareAction() {
        // Empty
    }

    /**
     * <p>
     * Getter method for userDAO, simply return the value of the namesake field.
     * </p>
     *
     * @return the userDAO
     */
    protected UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * <p>
     * Setter method for the userDAO, simply set the value to the namesake field.
     * </p>
     *
     * @param userDAO
     *            The UserDAO instance used to perform persistence operation on User
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
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
        ValidationUtility.checkNotNull(userDAO, "userDAO", BasicStrutsActionsConfigurationException.class);
    }
}
