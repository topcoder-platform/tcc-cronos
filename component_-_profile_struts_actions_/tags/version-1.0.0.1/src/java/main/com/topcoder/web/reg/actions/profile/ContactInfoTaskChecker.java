/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.util.Iterator;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileCompletenessRetrievalException;

/**
 * <p>
 * This class implements ProfileTaskChecker to provide a check of the contact information task and determine how many
 * of the required fields in this task has the passed user provided. The check on a field is simple and only verifies
 * that the field is not null/empty.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ContactInfoTaskChecker extends BaseProfileTaskChecker {

    /**
     * <p>
     * Creates an instance of ContactInfoTaskChecker.
     * </p>
     */
    public ContactInfoTaskChecker() {
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
        Iterator<Email> iterator = user.getEmailAddresses().iterator();
        // check email address
        if (iterator.hasNext() && Helper.checkNotNullNorEmpty(iterator.next().getAddress())) {
            completedFieldCount++;
        }
        report.setTotalFieldCount(totalFieldCount);
        report.setCompletedFieldCount(completedFieldCount);
        report.setCompleted(totalFieldCount == completedFieldCount);
        return report;
    }
}