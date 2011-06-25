/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.dao.PreferenceGroupDAO;
import com.topcoder.web.common.model.Preference;
import com.topcoder.web.common.model.PreferenceGroup;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserPreference;

/**
 * <p>
 * This action extends the BasePreferencesAction, and it allows user to configure privacy preferences.
 * </p>
 * <p>
 * All its instance variables have setters/getters and will be injected.
 * </p>
 * <p>
 * Thread safety: This class is mutable and not thread safe, however will be used thread-safely in Struts framework.
 * </p>
 * @author maksimilian, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ConfigurePrivacyPreferencesAction extends BasePreferencesAction {

    /**
     * <p>
     * Represents user preferences update operation name.
     * </p>
     */
    private static final String USER_PREFERENCES_UPDATE_OPERATION = "User Privacy Preferences Update";

    /**
     * <p>
     * Represents validate() method signature.
     * </p>
     */
    private static final String VALIDATE_METHOD_SIGNATURE = "ConfigurePrivacyPreferencesAction.validate()";

    /**
     * <p>
     * Represents execute() method signature.
     * </p>
     */
    private static final String EXECUTE_METHOD_SIGNATURE = "ConfigurePrivacyPreferencesAction.execute()";

    /**
     * <p>
     * Represents the group input parameter.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null non-negative. Used in getter/setter,
     * prepare and execute method.
     * </p>
     */
    private Integer group;

    /**
     * <p>
     * Represents the preference group DAO which will be used to manage the preference group.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null. Used in getter/setter.
     * </p>
     */
    private PreferenceGroupDAO preferenceGroupDao;

    /**
     * <p>
     * Represents the set of user privacy preferences.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private List < UserPreference > preferences;

    /**
     * <p>
     * Creates an instance of ConfigurePrivacyPreferencesAction.
     * </p>
     */
    public ConfigurePrivacyPreferencesAction() {
        super();
    }

    /**
     * <p>
     * Execute method is responsible for viewing/updating the user privacy preferences.
     * </p>
     * @return a string representing the logical result of the execution. If an action succeeds, it returns
     *         Action.SUCCESS. If some error occurs, the exception will be thrown.
     * @throws UserPreferencesActionExecutionException if any unexpected error occurs. (it's used to wrap all
     *             underlying exceptions)
     * @throws PreferencesActionDiscardException if error occurs when attempting to discard
     */
    public String execute() throws UserPreferencesActionExecutionException, PreferencesActionDiscardException {
        LoggingWrapperUtility.logEntrance(getLogger(), EXECUTE_METHOD_SIGNATURE, null, null);
        // get the user:
        User user = getLoggedInUser();
        // handle submit action
        if (getAction().equals(Helper.SUBMIT_ACTION)) {
            // backup the user:
            try {
                ActionContext.getContext().getSession().put(getBackupSessionKey(), user);
                // update the user preferences in persistence
                for (UserPreference up : preferences) {
                    UserPreference preference = user.getUserPreference(up.getId().getPreference().getId());
                    if (preference != null) {
                        String oldValue = preference.getValue();
                        String newValue = up.getValue();
                        // check if value changed
                        if (!newValue.equals(oldValue)) {
                            audit(oldValue, newValue, USER_PREFERENCES_UPDATE_OPERATION);
                            preference.setValue(up.getValue());
                        }
                    }
                }
                getUserDao().saveOrUpdate(user);
            } catch (IllegalStateException e) {
                throw Helper.logAndWrapException(getLogger(), EXECUTE_METHOD_SIGNATURE, "User has invalid state.",
                        null, UserPreferencesActionExecutionException.class);
            }
        }
        // handle discard action
        if (getAction().equals(Helper.DISCARD_ACTION)) {
            Helper.handleDiscardAction(this, EXECUTE_METHOD_SIGNATURE);
        }
        // send email, if needed
        if (isEmailSendFlag()) {
            sendEmail();
        }
        LoggingWrapperUtility.logExit(getLogger(), EXECUTE_METHOD_SIGNATURE, new String[] {SUCCESS});
        return SUCCESS;
    }

    /**
     * <p>
     * Prepares the action and checks all attributes on valid values after injection.
     * </p>
     * @throws Exception if any attribute has invalid value after injection
     */
    public void prepare() throws Exception {
        // attributes check
        super.prepare();
        ValidationUtility.checkNotNull(group, "group", UserPreferencesActionConfigurationException.class);
        ValidationUtility.checkNotNegative(group, "group", UserPreferencesActionConfigurationException.class);
        ValidationUtility.checkNotNull(preferenceGroupDao, "preferenceGroupDao",
                UserPreferencesActionConfigurationException.class);
        // Prepare the action:
        PreferenceGroup preferenceGroup = preferenceGroupDao.find(group);
        Set < UserPreference > userPreferences = getLoggedInUser().getUserPreferences();
        preferences = new ArrayList < UserPreference >();
        for (Iterator it = preferenceGroup.getPreferences().iterator(); it.hasNext();) {
            Preference p = (Preference) it.next();
            for (RegistrationType rt : getLoggedInUser().getRegistrationTypes()) {
                if (rt.getPreferences().contains(p)) {
                    for (UserPreference up : userPreferences) {
                        if (up.getId().getPreference().getId() == p.getId()) {
                            preferences.add(up);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * <p>
     * Validates inputed parameters.
     * </p>
     */
    public void validate() {
        LoggingWrapperUtility.logEntrance(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null);
        for (UserPreference up : preferences) {
            String upValue = up.getValue();
            Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, upValue, "preference[id="
                    + up.getId().getPreference().getId() + "]");
        }
        LoggingWrapperUtility.logExit(getLogger(), VALIDATE_METHOD_SIGNATURE, null);
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the group
     */
    public Integer getGroup() {
        return group;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the preferenceGroupDao
     */
    public PreferenceGroupDAO getPreferenceGroupDao() {
        return preferenceGroupDao;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the preferences
     */
    public List < UserPreference > getPreferences() {
        return preferences;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param group the group to set
     */
    public void setGroup(Integer group) {
        this.group = group;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param preferenceGroupDao the preferenceGroupDao to set
     */
    public void setPreferenceGroupDao(PreferenceGroupDAO preferenceGroupDao) {
        this.preferenceGroupDao = preferenceGroupDao;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param preferences the preferences to set
     */
    public void setPreferences(List < UserPreference > preferences) {
        this.preferences = preferences;
    }
}
