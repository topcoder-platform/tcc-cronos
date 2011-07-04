/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers;

import java.util.ArrayList;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.topcoder.reg.profilecompleteness.filter.Helper;
import com.topcoder.reg.profilecompleteness.filter.impl.ProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.ProfileCompletenessCheckerException;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.model.Address;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserGroup;

/**
 * This class is the base class for checking the user profile completeness. It provides default
 * implementation of isProfileComplete() method, which checks mandatory fields for every action of
 * the user.
 * <p>
 * These mandatory fields are: user first name, last name, address(including address line, city and
 * country) and phone. It also provides methods to check, whether the user is competitor or
 * customer.
 * </p>
 * <b>Thread-safety</b>
 * <p>
 * The class is mutable. However, the inserted configuration parameters will be set only once by
 * Spring framework and in the thread-safe manner. Under these conditions the class is thread-safe.
 * </p>
 *
 * @author faeton, nowind_lee
 * @version 1.0
 */
public abstract class BaseProfileCompletenessChecker implements ProfileCompletenessChecker {

    /**
     * This is the log to perform logging. It is modified by setter and used in isProfileComplete,
     * isCompetitor, isCustomer methods. It can be any value, but should not be null after spring
     * initialization. Its' legality is checked in the checkInitialization() method.
     */
    private Log log;

    /**
     * The default do nothing constructor.
     */
    protected BaseProfileCompletenessChecker() {
        // do nothing
    }

    /**
     * This is method is used to check whether all dependencies of this class were properly inserted
     * by Spring framework. This method should be configured as "init-method" in the bean definition
     * in spring configuration file.
     */
    @PostConstruct
    protected void checkInitialization() {
        // do nothing
    }

    /**
     * This method checks whether the profile is complete for specific action.
     *
     * @param user
     *            the user to check user profile
     * @return True, if the user has completed enough fields in the profile, false otherwise
     *
     * @throws IllegalArgumentException
     *             if user is null
     * @throws ProfileCompletenessCheckerException
     *             if any exception occurs while checking profile completeness.
     */
    public boolean isProfileComplete(User user) throws ProfileCompletenessCheckerException {
        String method = "BaseProfileCompletenessChecker.isProfileComplete";
        Helper.logEntry(log, method, "user: %s", user);
        Helper.notNull(user, "user", log, method);

        Set<Address> addresses = user.getAddresses();
        Set<Phone> phoneNumbers = user.getPhoneNumbers();
        if (user.getFirstName() == null
                || user.getLastName() == null
                || addresses == null
                || addresses.size() == 0
                || phoneNumbers == null
                || phoneNumbers.size() == 0) {
            boolean result = false;
            return Helper.logExit(log, method, result);
        }

        Address address = new ArrayList<Address>(addresses).get(0);
        boolean result = address.getAddress1() != null && address.getCity() != null
            && address.getCountry() != null;

        return Helper.logExit(log, method, result);
    }

    /**
     * This method checks whether the user is a competitor. It is used by the subclasses of this
     * class.
     *
     * @param user
     *            the user to obtain user role
     * @return true, if the user is competitor, false otherwise
     */
    protected boolean isCompetitor(User user) {
        String method = "BaseProfileCompletenessChecker.isCompetitor";
        Helper.logEntry(log, method, "user: %s", user);
        boolean result = checkUserType(user, RegistrationType.COMPETITION_ID);
        return Helper.logExit(log, method, result);
    }

    /**
     * This method checks whether the user is a customer. It is used by the subclasses of this
     * class.
     *
     * @param user
     *            the user to obtain user role
     * @return True, if the user is customer, false otherwise
     */
    protected boolean isCustomer(User user) {
        String method = "BaseProfileCompletenessChecker.isCustomer";
        Helper.logEntry(log, method, "user: %s", user);
        boolean result = checkUserType(user, RegistrationType.CORPORATE_ID);
        return Helper.logExit(log, method, result);
    }

    /**
     * This is a simple getter for field "log". It is used to obtain the field by subclasses.
     *
     * @return The value of field "log"
     */
    protected Log getLog() {
        return this.log;
    }

    /**
     * This is a simple setter for field "log". It will be called by Spring framework in order to
     * properly initialize the object.
     *
     * @param log
     *            the value of field "log"
     */
    public void setLog(Log log) {
        this.log = log;
    }

    /**
     * This method checks whether the user is expected type.
     *
     * @param user
     *            the user to be checked
     * @param expectedType
     *            the expected type
     * @return whether the user is expected type
     */
    private static boolean checkUserType(User user, Integer expectedType) {
        Set<UserGroup> groups = user.getSecurityGroups();
        for (UserGroup group : groups) {
            for (RegistrationType type : group.getSecurityGroup().getRegistrationTypes()) {
                if (type.getId().equals(expectedType)) {
                    return true;
                }
            }
        }
        return false;
    }
}
