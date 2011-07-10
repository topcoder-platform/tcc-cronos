/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileCompletenessRetrievalException;

/**
 * <p>
 * This class implements ProfileCompletenessRetriever and provides an implementation that asks the ProfileTaskCheckers
 * to state if they are completed, and to provide their totals of all applicable fields, and how many have been
 * completed. It provides a report of the completion percentage by checking how many required fields have been set, and
 * also states which stages (tasks) have not yet been completed.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultProfileCompletenessRetriever implements ProfileCompletenessRetriever {

    /**
     * <p>
     * These are the ProfileTaskCheckers that are used to check for completeness.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must not be null/empty or contain null elements. This field will be injected by the
     * container (expected to be done only once), and is not expected to change afterward.
     * </p>
     */
    private List<ProfileTaskChecker> profileTaskCheckers;

    /**
     * <p>
     * Creates an instance of DefaultProfileCompletenessRetriever.
     * </p>
     */
    public DefaultProfileCompletenessRetriever() {
    }

    /**
     * <p>
     * This method checks that all required values have been injected by the system right after construction and
     * injection.
     * </p>
     * This is used to obviate the need to check these values each time in the getProfileCompletenessReport().
     * @throws ProfileActionConfigurationException - If any required value has not been injected into this class
     */
    @PostConstruct
    protected void checkInitialization() {
        ValidationUtility.checkNotNullNorEmpty(profileTaskCheckers, "profileTaskCheckers",
                ProfileActionConfigurationException.class);
        ValidationUtility.checkNotNullElements(profileTaskCheckers, "profileTaskCheckers",
                ProfileActionConfigurationException.class);
    }

    /**
     * <p>
     * Retrieves the profile completeness report for the given user. The complete User instance is passed to it.
     * </p>
     * @param user the complete info about the user whose completion check is being done
     * @return the completeness report
     * @throws IllegalArgumentException if user is null or is missing other fields required by the implementation
     * @throws ProfileCompletenessRetrievalException - If there are any errors while performing this operation.
     */
    public ProfileCompletenessReport getProfileCompletenessReport(User user)
        throws ProfileCompletenessRetrievalException {
        ParameterCheckUtility.checkNotNull(user, "user");
        // Prepare report entity
        ProfileCompletenessReport report = new ProfileCompletenessReport();
        report.setTaskCompletionStatuses(new ArrayList<TaskCompletionStatus>());
        // Prepare calculation fields:
        int totalFields = 0;
        int totalCompletedFields = 0;
        for (ProfileTaskChecker checker : profileTaskCheckers) {
            ProfileTaskReport profileTaskReport = checker.getTaskReport(user);
            totalFields += profileTaskReport.getTotalFieldCount();
            totalCompletedFields += profileTaskReport.getCompletedFieldCount();
            TaskCompletionStatus taskCompletionStatus = new TaskCompletionStatus();
            taskCompletionStatus.setTaskName(profileTaskReport.getTaskName());
            taskCompletionStatus.setCompleted(profileTaskReport.getCompleted());
            report.getTaskCompletionStatuses().add(taskCompletionStatus);
        }
        // Perform calculation:
        report.setCompletionPercentage(totalCompletedFields * 100 / totalFields);
        return report;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public List<ProfileTaskChecker> getProfileTaskCheckers() {
        return profileTaskCheckers;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param profileTaskCheckers the namesake field instance value to set
     */
    public void setProfileTaskCheckers(List<ProfileTaskChecker> profileTaskCheckers) {
        this.profileTaskCheckers = profileTaskCheckers;
    }
}