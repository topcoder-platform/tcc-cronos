/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileCompletenessRetrievalException;

/**
 * <p>
 * This class implements ProfileTaskChecker to provide a check of the demographic information task and determine how
 * many of the required fields in this task has the passed user provided. The check on a field is simple and only
 * verifies that the field is not null/empty.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class DemographicInfoTaskChecker extends BaseProfileTaskChecker {

    /**
     * <p>
     * Represents question text for "Show / Hide My School" constant.
     * </p>
     */
    private static final String SHOW_HIDE_MY_SCHOOL_QUESTION = "Show / Hide My School";

    /**
     * <p>
     * Creates an instance of DemographicInfoTaskChecker.
     * </p>
     */
    public DemographicInfoTaskChecker() {
    }

    /**
     * <p>
     * Retrieves a profile task report for the given user. The complete User instance is passed to it.
     * </p>
     * @param user the complete info about the user whose completion check is being done
     * @return the profile task report
     * @throws IllegalArgumentException - if user is null or is missing other fields required by the implementation
     * @throws ProfileCompletenessRetrievalException - If there are any errors while performing this operation.
     */
    public ProfileTaskReport getTaskReport(User user) {
        ParameterCheckUtility.checkNotNull(user, "user");
        ProfileTaskReport report = new ProfileTaskReport();
        report.setTaskName(getTaskName());
        int totalFieldCount = 1;
        int completedFieldCount = 0;
        // check demographic answer for School
        for (DemographicResponse demographicResponse : user.getDemographicResponses()) {
            if (SHOW_HIDE_MY_SCHOOL_QUESTION.equals(demographicResponse.getQuestion().getText())) {
                if (Helper.checkNotNullNorEmpty(demographicResponse.getAnswer().getText())) {
                    completedFieldCount++;
                }
            }
        }
        report.setTotalFieldCount(totalFieldCount);
        report.setCompletedFieldCount(completedFieldCount);
        report.setCompleted(totalFieldCount == completedFieldCount);
        return report;
    }
}