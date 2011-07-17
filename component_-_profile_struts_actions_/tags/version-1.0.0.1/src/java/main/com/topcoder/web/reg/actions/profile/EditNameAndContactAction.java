/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This action extends ViewNameAndContactAction, but also gets the additional lookup lists required for the page.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class EditNameAndContactAction extends ViewNameAndContactAction {

    /**
     * <p>
     * Represents performAdditionalTasks() method constant signature.
     * </p>
     */
    private static final String PERFORM_ADDITIONAL_TASKS_METHOD_SIGNATURE =
            "EditNameAndContactAction.performAdditionalTasks()";

    /**
     * <p>
     * This is the CountryDAO instance used to get country info.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private CountryDAO countryDAO;

    /**
     * <p>
     * This is the TimeZoneDAO instance used to get timezone info.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private TimeZoneDAO timeZoneDAO;

    /**
     * <p>
     * Represents the list of countries.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * Initially null, it will be set to a non-null value during the execute method. This field will be set by the
     * execute method.
     * </p>
     */
    private List<Country> countries;

    /**
     * <p>
     * Represents the list of countries that the user wants to choose for representation.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * Initially null, it will be set to a non-null value during the execute method. This field will be set by the
     * execute method.
     * </p>
     */
    private List<Country> representationCountries;

    /**
     * <p>
     * Represents the list of timezones.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * Initially null, it will be set to a non-null value during the execute method. This field will be set by the
     * execute method.
     * </p>
     */
    private List<TimeZone> timezones;

    /**
     * <p>
     * Creates an instance of EditNameAndContactAction.
     * </p>
     */
    public EditNameAndContactAction() {
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
    @Override
    @PostConstruct
    protected void checkInitialization() {
        super.checkInitialization();
        ValidationUtility.checkNotNull(countryDAO, "countryDAO", ProfileActionConfigurationException.class);
        ValidationUtility.checkNotNull(timeZoneDAO, "timeZoneDAO", ProfileActionConfigurationException.class);
    }

    /**
     * <p>
     * This method is called by the execute method to perform any additional tasks that the extension may require.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void performAdditionalTasks(User user) throws ProfileActionException {
        // prepare information
        try {
            List countries = this.countryDAO.getCountries();
            this.countries = new ArrayList<Country>(countries);
            this.representationCountries = new ArrayList<Country>(countries);
            this.timezones = new ArrayList<TimeZone>(this.timeZoneDAO.getTimeZones());
        } catch (RuntimeException e) {
            throw Helper.logAndWrapException(getLogger(), PERFORM_ADDITIONAL_TASKS_METHOD_SIGNATURE,
                    "Error occurred in underlying DAO.", e);
        }
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public CountryDAO getCountryDAO() {
        return countryDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param countryDAO the namesake field instance value to set
     */
    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public TimeZoneDAO getTimeZoneDAO() {
        return timeZoneDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param timeZoneDAO the namesake field instance value to set
     */
    public void setTimeZoneDAO(TimeZoneDAO timeZoneDAO) {
        this.timeZoneDAO = timeZoneDAO;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param countries the namesake field instance value to set
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public List<Country> getRepresentationCountries() {
        return representationCountries;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param representationCountries the namesake field instance value to set
     */
    public void setRepresentationCountries(List<Country> representationCountries) {
        this.representationCountries = representationCountries;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public List<TimeZone> getTimezones() {
        return timezones;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param timezones the namesake field instance value to set
     */
    public void setTimezones(List<TimeZone> timezones) {
        this.timezones = timezones;
    }
}