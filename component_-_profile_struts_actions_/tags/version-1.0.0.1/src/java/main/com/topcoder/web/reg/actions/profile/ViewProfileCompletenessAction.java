/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.model.CoderType;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This action extends BaseDisplayProfileAction and processes the output by simply taking the passed user and sets it
 * to the output. It also provides all the necessary lookups.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewProfileCompletenessAction extends BaseDisplayProfileAction {

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
     * This is the DemographicQuestionDAO instance used to get demographic question info.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private DemographicQuestionDAO demographicQuestionDAO;

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
     * Represents the list of demographic questions.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * Initially null, it will be set to a non-null value during the execute method. This field will be set by the
     * execute method.
     * </p>
     */
    private List<DemographicQuestion> demographicQuestions;

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
     * Represents the tab that must be serviced.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * Initially null, it will be set to a non-null value during the execute method. This field will be set by the
     * execute method.
     * </p>
     */
    private CurrentTab currentTab;

    /**
     * <p>
     * Creates an instance of ViewProfileCompletenessAction.
     * </p>
     */
    public ViewProfileCompletenessAction() {
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
        ValidationUtility.checkNotNull(demographicQuestionDAO, "demographicQuestionDAO",
                ProfileActionConfigurationException.class);
        ValidationUtility.checkNotNull(coderTypeDAO, "coderTypeDAO", ProfileActionConfigurationException.class);
    }

    /**
     * <p>
     * This method is called by the execute method to process the output data.
     * </p>
     * <p>
     * This method will simply set this to the output and audit the call.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    protected void processOutputData(User user) throws ProfileActionException {
        setDisplayedUser(user);
        // Analyze the user to determine the stage
        this.currentTab = analyzeStage(user);
    }

    /**
     * <p>
     * Analyzes stage user edit tab.
     * </p>
     * @param user the user to determine stage
     * @return user edit stage tab
     */
    private static CurrentTab analyzeStage(User user) {
        CurrentTab stage;
        if (!user.getEmailAddresses().isEmpty()) {
            // contact tab, email is required
            stage = CurrentTab.CONTACT_TAB;
        } else if (!user.getDemographicResponses().isEmpty()) {
            // demographic tab, any demographic responses
            stage = CurrentTab.DEMOGRAPHIC_TAB;
        } else {
            // account tab
            stage = CurrentTab.ACCOUNT_TAB;
        }
        return stage;
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
            List countries = this.countryDAO.getCountries();
            this.countries = new ArrayList<Country>(countries);
            this.representationCountries = new ArrayList<Country>(countries);
            this.timezones = new ArrayList<TimeZone>(this.timeZoneDAO.getTimeZones());
            this.demographicQuestions = new ArrayList<DemographicQuestion>(this.demographicQuestionDAO.getQuestions());
        } catch (RuntimeException e) {
            throw Helper.logAndWrapException(getLogger(), "ViewProfileCompletenessAction.performAdditionalTasks()",
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
    public DemographicQuestionDAO getDemographicQuestionDAO() {
        return demographicQuestionDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param demographicQuestionDAO the namesake field instance value to set
     */
    public void setDemographicQuestionDAO(DemographicQuestionDAO demographicQuestionDAO) {
        this.demographicQuestionDAO = demographicQuestionDAO;
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

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public List<DemographicQuestion> getDemographicQuestions() {
        return demographicQuestions;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param demographicQuestions the namesake field instance value to set
     */
    public void setDemographicQuestions(List<DemographicQuestion> demographicQuestions) {
        this.demographicQuestions = demographicQuestions;
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
    public CurrentTab getCurrentTab() {
        return currentTab;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param currentTab the namesake field instance value to set
     */
    public void setCurrentTab(CurrentTab currentTab) {
        this.currentTab = currentTab;
    }
}