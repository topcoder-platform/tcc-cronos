/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.shared.dataAccess.DataAccessInt;

/**
 * This is a base class for all Struts actions defined in this component that need to access some data in persistence.
 * It holds DataAccessInt instance injected by Spring.
 * <p>
 * Thread Safety:
 * This class is mutable and not thread safe. But it will be used in thread safe manner by Struts. It's assumed that
 * request scope will be set up for all Struts actions in Spring configuration, thus actions will be accessed from a
 * single thread only.
 * The injected DataAccessInt instance is expected to be thread safe.
 *
 * @author saarixx, mumujava
 * @version 1.0
 */
public abstract class BaseDataAccessUserCommunityManagementAction extends BaseUserCommunityManagementAction {
    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = -749645968096416773L;
    /**
     * The DataAccessInt instance to be used for accessing user data in persistence. Is expected to be initialized by
     * Spring via setter injection. Cannot be null after initialization (validation is performed in @PostConstruct
     * method checkInit()). Has protected getter and public setter.
     */
    private DataAccessInt dataAccess;

    /**
     * Creates an instance of BaseDataAccessUserCommunityManagementAction.
     */
    protected BaseDataAccessUserCommunityManagementAction() {
    }

    /**
     * Checks whether this action was initialized by Spring properly.
     * @throws UserCommunityManagementInitializationException if the class was not initialized properly (e.g. when
     * required properly is not specified)
     */
    @Override
    @PostConstruct
    protected void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(dataAccess, "dataAccess",
                UserCommunityManagementInitializationException.class);
    }

    /**
     * Sets the DataAccessInt instance to be used for accessing user data in persistence.
     *
     * @param dataAccess the DataAccessInt instance to be used for accessing user data in persistence
     */
    public void setDataAccess(DataAccessInt dataAccess) {
        this.dataAccess = dataAccess;
    }

    /**
     * Retrieves the DataAccessInt instance to be used for accessing user data in persistence.
     *
     * @return the DataAccessInt instance to be used for accessing user data in persistence
     */
    protected DataAccessInt getDataAccess() {
        return dataAccess;
    }
}
