/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.Map;

import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.web.common.TCWebException;

/**
 * This is a base class for all Struts actions defined in this component that need to check whether the user is rated
 * or not. It just defines a protected method isRated().
 * The injected DataAccessInt instance for actions that extend this class must represent "topcoder_dw" database.
 * <p>
 * Thread Safety:
 * This class is mutable and not thread safe. But it will be used in thread safe manner by Struts. It's assumed that
 * request scope will be set up for all Struts actions in Spring configuration, thus actions will be accessed from a
 * single thread only.
 *
 * @author saarixx, mumujava
 * @version 1.0
 */
public abstract class BaseRatedUserCommunityManagementAction extends
    BaseDataAccessUserCommunityManagementAction {
    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = -7019242991185749750L;
    /**
     * <p>
     * The rated contest types.
     * </p>
     */
    private static final String[] RATED_CONTEST_TYPES = {"rating", "hs_rating", "mm_rating", "design_rating",
        "development_rating", "concept_rating", "spec_rating", "arch_rating",
        "assembly_rating", "test_rating", "test_scenarios_rating", "ui_prototype_rating", "ria_build_rating"};

    /**
     * Creates an instance of BaseRatedUserCommunityManagementAction.
     */
    protected BaseRatedUserCommunityManagementAction() {
    }

    /**
     * Checks whether the active user is rated.
     *
     * @throws TCWebException if any error occurred
     * @return true if the user is rated, false otherwise
     */
    protected boolean isRated() throws TCWebException {
        //Create a data access request instance:
        Request request = new Request();
        //Set content handle to "member_profile":
        request.setContentHandle("member_profile");
        // Get ID of the user:
        long userId = getUserId();
        // Set user ID to the request:
        request.setProperty("cr", String.valueOf(userId));
        //Get data access instance to be used:
        DataAccessInt dataAccess = getDataAccess();
        //Perform request:
        Map<String, ResultSetContainer> resultData = null;
        try {
            resultData = dataAccess.getData(request);
        } catch (Exception e) {
            throw new TCWebException("Error occurs when retriving the member profile", e);
        }
        if (resultData == null) {
            throw new TCWebException("Unable to retrive the member profile");
        }
        // Get coder info from the result data:
        ResultSetContainer coderInfo = resultData.get("Coder_Data");
        if (coderInfo == null || coderInfo.isEmpty()) {
            return false;
        }
        for (String item : RATED_CONTEST_TYPES) {
            if (coderInfo.getItem(0, item).getResultData() != null && coderInfo.getIntItem(0, item) > 0) {
                return true;
            }
        }
        return false;
    }
}
