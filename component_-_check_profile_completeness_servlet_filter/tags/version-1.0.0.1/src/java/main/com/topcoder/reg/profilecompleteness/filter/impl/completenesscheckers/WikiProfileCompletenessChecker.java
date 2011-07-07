/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers;

import com.topcoder.reg.profilecompleteness.filter.Helper;
import com.topcoder.reg.profilecompleteness.filter.impl.ProfileCompletenessCheckerException;
import com.topcoder.web.common.model.User;

/**
 * This class is the class for checking the user profile completeness for accessing Confluence wiki.
 * <p>
 * <b>Thread-safety</b>
 * <p>
 * The class is mutable. However, the inserted configuration parameters will be set only once by
 * Spring framework and in the thread-safe manner. Under these conditions the class is thread-safe.
 * <p>
 * <b>Usage</b>
 * </p>
 * <p>
 * We can create this checker programmatically or by spring:
 * </p>
 * <b>Programmatically:</b>
 * <pre>
 * WikiProfileCompletenessChecker checker = new WikiProfileCompletenessChecker();
 * checker.setLog(getLog());
 * checker.checkInitialization();
 * </pre>
 * <b>Spring:</b>
 * <pre>
 * &lt;bean id=&quot;WikiProfileCompletenessChecker&quot;
 * class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers
 * .WikiProfileCompletenessChecker&quot;
 * init-method=&quot;checkInitialization&quot;&gt;
 * &lt;property name=&quot;log&quot; ref=&quot;logger&quot;/&gt;
 * &lt;/bean&gt;
 * </pre>
 * <b>Invoking:</b>
 * <pre>
 * boolean complete = checker.isProfileComplete(user);
 * </pre>
 *
 * @author faeton, nowind_lee
 * @version 1.0
 */
public class WikiProfileCompletenessChecker extends BaseProfileCompletenessChecker {

    /**
     * The default do nothing constructor.
     */
    public WikiProfileCompletenessChecker() {
        // do nothing
    }

    /**
     * This method checks whether the profile is complete for specific action.
     *
     * @param user the user to check user profile
     * @return True, if the user has completed enough fields in the profile, false otherwise
     *
     * @throws IllegalArgumentException if user is null.
     * @throws ProfileCompletenessCheckerException
     *                                  if any exception occurs while checking profile completeness.
     */
    public boolean isProfileComplete(User user) throws ProfileCompletenessCheckerException {
        String method = "WikiProfileCompletenessChecker.isProfileComplete";
        Helper.logEntry(getLog(), method, "user: %s", user);
        boolean result = super.isProfileComplete(user);
        return Helper.logExit(getLog(), method, result);
    }

}
