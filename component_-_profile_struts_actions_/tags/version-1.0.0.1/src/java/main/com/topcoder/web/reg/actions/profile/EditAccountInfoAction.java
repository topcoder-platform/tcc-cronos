/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.common.model.CoderType;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This action extends ViewAccountInfoAction, but also gets the additional lookup lists required for the page.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class EditAccountInfoAction extends ViewAccountInfoAction {

    /**
     * <p>
     * This is the CoderTypeDAO instance used to get coder type info.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private CoderTypeDAO coderTypeDAO;

    /**
     * <p>
     * Represents the list of coder types.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * Initially null, it will be set to a non-null value during the execute method. This field will be set by the
     * execute method.
     * </p>
     */
    private List<CoderType> coderTypes;

    /**
     * <p>
     * Represents flag whether handle can be changed.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * Initially false, it will be set to a non-null value during the execute method. This field will be set by the
     * execute method.
     * </p>
     */
    private boolean canChangeHandle;

    /**
     * <p>
     * Creates an instance of EditAccountInfoAction.
     * </p>
     */
    public EditAccountInfoAction() {
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
    @PostConstruct
    @Override
    protected void checkInitialization() {
        super.checkInitialization();
        ValidationUtility.checkNotNull(coderTypeDAO, "coderTypeDAO", ProfileActionConfigurationException.class);
    }

    /**
     * <p>
     * This method is called by the execute method to process the output data.
     * </p>
     * <p>
     * This method will add a flag whether the user's handle can be changed.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    @Override
    protected void processOutputData(User user) throws ProfileActionException {
        super.processOutputData(user);
        try {
            this.canChangeHandle = getUserDAO().canChangeHandle(user.getHandle());
        } catch (RuntimeException e) {
            throw Helper.logAndWrapException(getLogger(), "EditAccountInfoAction.processOutputData()",
                    "Error occurred in underlying userDAO.", e);
        }
    }

    /**
     * <p>
     * This method is called by the execute method to perform any additional tasks that the extension may require.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    @SuppressWarnings("unchecked")
    protected void performAdditionalTasks(User user) throws ProfileActionException {
        try {
            this.coderTypes = new ArrayList<CoderType>(this.coderTypeDAO.getCoderTypes());
        } catch (RuntimeException e) {
            throw Helper.logAndWrapException(getLogger(), "EditAccountInfoAction.performAdditionalTasks()",
                    "Error occurred in underlying coderTypeDAO.", e);
        }
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public CoderTypeDAO getCoderTypeDAO() {
        return coderTypeDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param coderTypeDAO the namesake field instance value to set
     */
    public void setCoderTypeDAO(CoderTypeDAO coderTypeDAO) {
        this.coderTypeDAO = coderTypeDAO;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public List<CoderType> getCoderTypes() {
        return coderTypes;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param coderTypes the namesake field instance value to set
     */
    public void setCoderTypes(List<CoderType> coderTypes) {
        this.coderTypes = coderTypes;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public boolean getCanChangeHandle() {
        return canChangeHandle;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param canChangeHandle the namesake field instance value to set
     */
    public void setCanChangeHandle(boolean canChangeHandle) {
        this.canChangeHandle = canChangeHandle;
    }
}