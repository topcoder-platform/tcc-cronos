/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions.mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.reg.profilecompleteness.filter.impl.UserIdRetriever;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.dao.StateDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.Address;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.CoderType;
import com.topcoder.web.common.model.Company;
import com.topcoder.web.common.model.Contact;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.DemographicAnswer;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.State;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserSecurityKey;
import com.topcoder.web.reg.profilecompleteness.actions.BaseUnitTest;
import com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileAction;

/**
 * <p>
 * This class is a factory for creating mock DAOs. Methods used only by Spring.
 * </p>
 * @author sokol
 * @version 1.0
 */
/**
 * @author sokol
 * @version 1.0
 */
public class MockFactory {

    /**
     * <p>
     * Represents ID using for testing.
     * </p>
     */
    public static final long ID = 1;

    /**
     * <p>
     * Represents servlet redirected url for testing.
     * </p>
     */
    public static final String REDIRECTED_URL = "/success.jsp";

    /**
     * <p>
     * Represents age question ID for testing.
     * </p>
     */
    private static final long AGE_QUESTION_ID = 2;

    /**
     * <p>
     * Represents gender question ID for testing.
     * </p>
     */
    private static final long GENDER_QUESTION_ID = 1;

    /**
     * <p>
     * Represents security ID for testing.
     * </p>
     */
    private static final int SECURITY_KEY_TYPE_ID = 1;

    /**
     * <p>
     * Represents time zones for this component.
     * </p>
     */
    private static final String[] TIME_ZONES = {"GMT+02", "GMT-02"};

    /**
     * <p>
     * Represents answers for age demographic question.
     * </p>
     */
    private static final String[] AGE_DEMOGRAPHIC_ANSWER = {"13-17", "18-24", "45 and up"};

    /**
     * <p>
     * Represents answers for gender demographic question.
     * </p>
     */
    private static final String[] GENDER_DEMOGRAPHIC_ANSWER = {"Decline to answer", "Male", "Female"};

    /**
     * <p>
     * Represents country list for this component.
     * </p>
     */
    private static final String[] COUNTRY_LIST = {"482", "840", "507"};

    /**
     * <p>
     * Represents state list for this component.
     * </p>
     */
    private static final String[] STATE_LIST = {"CA", "DC", "OK"};

    /**
     * <p>
     * Represents coder types for this component.
     * </p>
     */
    private static final int[] CODER_TYPE = {1, 2};

    /**
     * <p>
     * Represents AuditDAO instance for testing.
     * </p>
     */
    private static AuditDAO auditDAO;

    /**
     * <p>
     * Represents UserDAO instance for testing.
     * </p>
     */
    private static UserDAO userDAO;

    /**
     * <p>
     * Represents UserIdRetriever instance for testing.
     * </p>
     */
    private static UserIdRetriever userIdRetriever;

    /**
     * <p>
     * Represents CoderTypeDAO instance for testing.
     * </p>
     */
    private static CoderTypeDAO coderTypeDAO;

    /**
     * <p>
     * Represents TimeZoneDAO instance for testing.
     * </p>
     */
    private static TimeZoneDAO timeZoneDAO;

    /**
     * <p>
     * Represents StateDAO instance for testing.
     * </p>
     */
    private static StateDAO stateDAO;

    /**
     * <p>
     * Represents DemographicQuestionDAO instance for testing.
     * </p>
     */
    private static DemographicQuestionDAO demographicQuestionDAO;

    /**
     * <p>
     * Represents CountryDAO instance for testing.
     * </p>
     */
    private static CountryDAO countryDAO;

    /**
     * <p>
     * Represents state for testing.
     * </p>
     */
    private static String state = "CA";

    /**
     * <p>
     * Represents address1 for testing.
     * </p>
     */
    private static String currentAddress1 = "address1";

    /**
     * <p>
     * Represents address2 for testing.
     * </p>
     */
    private static String currentAddress2 = "address2";

    /**
     * <p>
     * Represents address2 for testing.
     * </p>
     */
    private static String currentAddress3 = "address3";

    /**
     * <p>
     * Represents province for testing.
     * </p>
     */
    private static String province = "Some province";

    /**
     * <p>
     * Represents postal code for testing.
     * </p>
     */
    private static String postalCode = "0123456789";

    /**
     * <p>
     * Represents city for testing.
     * </p>
     */
    private static String city = "Los Angeles";

    /**
     * <p>
     * Represents country for testing.
     * </p>
     */
    private static String country = "840";

    /**
     * <p>
     * Represents country to represent for testing.
     * </p>
     */
    private static String countryToRepresent = "482";

    /**
     * <p>
     * Represents gender for testing.
     * </p>
     */
    private static String gender = "Male";

    /**
     * <p>
     * Represents age for testing.
     * </p>
     */
    private static String age = "18-24";

    /**
     * <p>
     * Represents first name for testing.
     * </p>
     */
    private static String firstName = "John";

    /**
     * <p>
     * Represents last name for testing.
     * </p>
     */
    private static String lastName = "Doe";

    /**
     * <p>
     * Represents phone number for testing.
     * </p>
     */
    private static String phoneNumber = "(+123) 456 789-123";

    /**
     * <p>
     * Represents job title for testing.
     * </p>
     */
    private static String jobTitle = "Software developer";

    /**
     * <p>
     * Represents company name for testing.
     * </p>
     */
    private static String companyName = "Topcoder";

    /**
     * <p>
     * Represents securityKey for testing.
     * </p>
     */
    private static String securityKey = "hfgsdjfg238ryewsfsdiufy2387rysjfhg82376rsjhfk";

    /**
     * <p>
     * Represents timezone for testing.
     * </p>
     */
    private static String timezone = "GMT+02";

    /**
     * <p>
     * Represents primary flag for testing.
     * </p>
     */
    private static boolean primary = true;

    /**
     * <p>
     * Represents coder type ID for testing.
     * </p>
     */
    private static Integer coderTypeId = 1;

    /**
     * <p>
     * Represents user for testing.
     * </p>
     */
    private static User user;
    // Initialize mock DAOs.
    static {
        setUserDAO(mock(UserDAO.class));
        setAuditDAO(mock(AuditDAO.class));
        setUserIdRetriever(mock(UserIdRetriever.class));
        setCoderTypeDAO(mock(CoderTypeDAO.class));
        setTimeZoneDAO(mock(TimeZoneDAO.class));
        setStateDAO(mock(StateDAO.class));
        setDemographicQuestionDAO(mock(DemographicQuestionDAO.class));
        setCountryDAO(mock(CountryDAO.class));
    }

    /**
     * <p>
     * Prevents from instantiating.
     * </p>
     */
    private MockFactory() {
    }

    /**
     * <p>
     * Populates the CompleteProfileAction instance with mock DAOs.
     * </p>
     * @param action the action to be populated with DAOs
     * @param addParameters the flag that shows whether to add parameters to action or not
     */
    public static void populateCompleteProfileAction(CompleteProfileAction action, boolean addParameters) {
        if (addParameters) {
            // input parameters
            action.setAge(getAge());
            action.setFirstName(getFirstName());
            action.setLastName(getLastName());
            action.setCity(getCity());
            action.setCompanyName(getCompanyName());
            action.setCoderTypeId(getCoderTypeId());
            action.setCountry(getCountry());
            action.setCountryToRepresent(getCountryToRepresent());
            action.setCurrentAddress1(getCurrentAddress1());
            action.setCurrentAddress2(getCurrentAddress2());
            action.setCurrentAddress3(getCurrentAddress3());
            action.setGender(getGender());
            action.setPrimary(isPrimary());
            action.setJobTitle(getJobTitle());
            action.setTimezone(getTimezone());
            action.setPostalCode(getPostalCode());
            action.setProvince(getProvince());
            action.setSecurityKey(getSecurityKey());
            action.setState(getState());
        }
        // DAOs
        action.setCountryDAO(getCountryDAO());
        action.setAuditDAO(getAuditDAO());
        action.setDemographicQuestionDAO(getDemographicQuestionDAO());
        action.setUserDAO(getUserDAO());
        action.setCoderTypeDAO(getCoderTypeDAO());
        action.setStateDAO(getStateDAO());
        action.setUserIdRetriever(getUserIdRetriever());
        action.setTimeZoneDAO(getTimeZoneDAO());
    }

    /**
     * <p>
     * Creates User instance with given values.
     * </p>
     * @param id the user's ID
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param handle the user's handle
     * @return created User instance with given values
     */
    public static User createUser(Long id, String firstName, String lastName, String handle) {
        User userEntity = new User();
        userEntity.setId(id);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setHandle(handle);
        Email email = new Email();
        email.setAddress("primary_email@test.com");
        email.setPrimary(true);
        email.setId(1L);
        userEntity.addEmailAddress(email);
        return userEntity;
    }

    /**
     * <p>
     * Retrieves UserDAO instance for this component.
     * </p>
     * @return UserDAO instance for this component
     */
    public static UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * <p>
     * Retrieves AuditDAO instance for this component.
     * </p>
     * @return AuditDAO instance for this component
     */
    public static AuditDAO getAuditDAO() {
        return auditDAO;
    }

    /**
     * <p>
     * Retrieves UserIdRetriever instance for this component.
     * </p>
     * @return UserIdRetriever instance for this component
     */
    public static UserIdRetriever getUserIdRetriever() {
        return userIdRetriever;
    }

    /**
     * <p>
     * Retrieves CoderTypeDAO instance for this component.
     * </p>
     * @return CoderTypeDAO instance for this component
     */
    public static CoderTypeDAO getCoderTypeDAO() {
        return coderTypeDAO;
    }

    /**
     * <p>
     * Retrieves TimeZoneDAO instance for this component.
     * </p>
     * @return TimeZoneDAO instance for this component
     */
    public static TimeZoneDAO getTimeZoneDAO() {
        return timeZoneDAO;
    }

    /**
     * <p>
     * Retrieves StateDAO instance for this component.
     * </p>
     * @return StateDAO instance for this component
     */
    public static StateDAO getStateDAO() {
        return stateDAO;
    }

    /**
     * <p>
     * Retrieves DemographicQuestionDAO instance for this component.
     * </p>
     * @return DemographicQuestionDAO instance for this component
     */
    public static DemographicQuestionDAO getDemographicQuestionDAO() {
        return demographicQuestionDAO;
    }

    /**
     * <p>
     * Retrieves CountryDAO instance for this component.
     * </p>
     * @return CountryDAO instance for this component
     */
    public static CountryDAO getCountryDAO() {
        return countryDAO;
    }

    /**
     * <p>
     * Resets mock DAOs.
     * </p>
     */
    public static void resetDAOs() {
        reset(getAuditDAO());
        reset(getUserDAO());
        reset(getCoderTypeDAO());
        reset(getUserIdRetriever());
        reset(getTimeZoneDAO());
        reset(getStateDAO());
        reset(getDemographicQuestionDAO());
        reset(getCountryDAO());
    }

    /**
     * <p>
     * Initializes DAOs with mock return data and fields.
     * </p>
     */
    public static void initDAOs() {
        setUser(MockFactory.createUser(ID, getFirstName(), getLastName(), "handle"));
        // prepare logged in User
        when(getUserIdRetriever().getUserId(any(HttpServletRequest.class))).thenReturn(ID);
        when(getUserDAO().find(ID)).thenReturn(getUser());
        // prepare countries
        for (String countryCode : COUNTRY_LIST) {
            createCountry(countryCode);
        }
        for (String stateCode : STATE_LIST) {
            createState(stateCode);
        }
        // add addresses
        Set < Address > addresses = new HashSet < Address >();
        addresses.add(createAddress());
        getUser().setAddresses(addresses);
        // add contact
        Contact contact = createContact();
        getUser().setContact(contact);
        // add phone numbers
        Set < Phone > phoneNumbers = new HashSet < Phone >();
        phoneNumbers.add(createPhone());
        getUser().setPhoneNumbers(phoneNumbers);
        // set coder
        getUser().setCoder(new Coder());
        // add coder type
        createCoderType(CODER_TYPE[0]);
        createCoderType(CODER_TYPE[1]);
        getUser().getCoder().setCoderType(coderTypeDAO.find(coderTypeId));
        // add security key
        getUser().setUserSecurityKey(createUserSecurityKey());
        // add country to represent
        getUser().getCoder().setCompCountry(countryDAO.find(countryToRepresent));
        // add demographic responses
        Set < DemographicResponse > demographicResponses = new HashSet < DemographicResponse >();
        // add age demographic response
        demographicResponses.add(createDemographicResponse(AGE_QUESTION_ID, AGE_DEMOGRAPHIC_ANSWER, getAge()));
        demographicResponses
                .add(createDemographicResponse(GENDER_QUESTION_ID, GENDER_DEMOGRAPHIC_ANSWER, getGender()));
        // prepare time zones
        createTimeZone(TIME_ZONES[0]);
        createTimeZone(TIME_ZONES[1]);
        getUser().setTimeZone(getTimeZoneDAO().find(timezone));
        getUser().setDemographicResponses(demographicResponses);
    }

    /**
     * <p>
     * Creates State instance with given values.
     * </p>
     * @param stateName the state name to set
     */
    private static void createState(String stateName) {
        State stateEntity = new State();
        BaseUnitTest.setFieldValue("name", stateName, stateEntity, State.class);
        when(getStateDAO().find(stateName)).thenReturn(stateEntity);
    }

    /**
     * <p>
     * Creates TimeZone instance with given values.
     * </p>
     * @param timeZoneDescription the time zone description
     * @return created TimeZone instance with given values
     */
    private static TimeZone createTimeZone(String timeZoneDescription) {
        TimeZone timeZoneEntity = new TimeZone();
        BaseUnitTest.setFieldValue("description", getTimezone(), timeZoneEntity, TimeZone.class);
        when(getTimeZoneDAO().find(timeZoneDescription)).thenReturn(timeZoneEntity);
        return timeZoneEntity;
    }

    /**
     * <p>
     * Creates DemographicResponse instance with given values.
     * </p>
     * @param demographicId the demographic response ID
     * @param texts the demographic answers
     * @param currentText current demographic question answer text
     * @return created DemographicResponse instance with given values
     */
    private static DemographicResponse createDemographicResponse(long demographicId, String[] texts,
            String currentText) {
        DemographicResponse response = new DemographicResponse();
        // prepare question
        DemographicQuestion question = new DemographicQuestion();
        question.setId(demographicId);
        // prepare answers
        DemographicAnswer tagetAnswer = null;
        for (int i = 0; i < texts.length; i++) {
            DemographicAnswer answer = new DemographicAnswer();
            answer.setId(demographicId);
            if (texts[i] == currentText) {
                tagetAnswer = answer;
            }
            answer.setText(texts[i]);
            answer.setSort(i + 1);
            question.addAnswer(answer);
        }
        // prepare DAO
        when(getDemographicQuestionDAO().find(demographicId)).thenReturn(question);
        response.setId(new DemographicResponse.Identifier(getUser(), question, tagetAnswer));
        response.setQuestion(question);
        response.setAnswer(tagetAnswer);
        response.setUser(getUser());
        return response;
    }

    /**
     * <p>
     * Creates UserSecurityKey instance with given values.
     * </p>
     * @return created UserSecurityKey instance with given values
     */
    private static UserSecurityKey createUserSecurityKey() {
        UserSecurityKey userSecurityKey = new UserSecurityKey();
        userSecurityKey.setId(ID);
        userSecurityKey.setSecurityKey(getSecurityKey());
        userSecurityKey.setSecurityKeyTypeId(SECURITY_KEY_TYPE_ID);
        return userSecurityKey;
    }

    /**
     * <p>
     * Creates CoderType instance with given values.
     * </p>
     * @param coderTypeIdValue the code type id value to set
     * @return created CoderType instance with given values
     */
    private static CoderType createCoderType(int coderTypeIdValue) {
        CoderType coderType = new CoderType();
        coderType.setId(getCoderTypeId());
        when(getCoderTypeDAO().find(coderTypeIdValue)).thenReturn(coderType);
        return coderType;
    }

    /**
     * <p>
     * Creates Phone instance with given values.
     * </p>
     * @return created Phone instance with given values
     */
    private static Phone createPhone() {
        Phone phoneEntity = new Phone();
        phoneEntity.setId(ID);
        phoneEntity.setNumber(getPhoneNumber());
        phoneEntity.setUser(getUser());
        phoneEntity.setPrimary(isPrimary());
        return phoneEntity;
    }

    /**
     * <p>
     * Creates Address instance with given values.
     * </p>
     * @return created Address instance with given values
     */
    private static Address createAddress() {
        Address address = new Address();
        address.setId(ID);
        address.setAddress1(getCurrentAddress1());
        address.setAddress2(getCurrentAddress2());
        address.setAddress3(getCurrentAddress3());
        address.setCity(getCity());
        address.setPostalCode(getPostalCode());
        address.setProvince(getProvince());
        address.setCountry(countryDAO.find(country));
        // init stateDAO
        address.setState(stateDAO.find(state));
        return address;
    }

    /**
     * <p>
     * Creates Country instance with given values.
     * </p>
     * @param countryCode the country code to set
     * @return created Country instance with given values
     */
    private static Country createCountry(String countryCode) {
        Country countryEntity = new Country();
        countryEntity.setCode(countryCode);
        // init countryDAO
        when(getCountryDAO().find(countryCode)).thenReturn(countryEntity);
        return countryEntity;
    }

    /**
     * <p>
     * Creates Contact instance with given values.
     * </p>
     * @return created Contact instance with given values
     */
    private static Contact createContact() {
        Contact contact = new Contact();
        contact.setId(ID);
        contact.setTitle(getJobTitle());
        contact.setUser(getUser());
        Company company = new Company();
        contact.setCompany(company);
        company.setId(ID);
        company.setName(getCompanyName());
        company.setPrimaryContact(contact);
        return contact;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the state field instance value
     */
    public static String getState() {
        return state;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param state the state to set
     */
    public static void setState(String state) {
        MockFactory.state = state;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the currentAddress1 field instance value
     */
    public static String getCurrentAddress1() {
        return currentAddress1;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param currentAddress1 the currentAddress1 to set
     */
    public static void setCurrentAddress1(String currentAddress1) {
        MockFactory.currentAddress1 = currentAddress1;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the currentAddress2 field instance value
     */
    public static String getCurrentAddress2() {
        return currentAddress2;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param currentAddress2 the currentAddress2 to set
     */
    public static void setCurrentAddress2(String currentAddress2) {
        MockFactory.currentAddress2 = currentAddress2;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the currentAddress3 field instance value
     */
    public static String getCurrentAddress3() {
        return currentAddress3;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param currentAddress3 the currentAddress3 to set
     */
    public static void setCurrentAddress3(String currentAddress3) {
        MockFactory.currentAddress3 = currentAddress3;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the province field instance value
     */
    public static String getProvince() {
        return province;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param province the province to set
     */
    public static void setProvince(String province) {
        MockFactory.province = province;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the postalCode field instance value
     */
    public static String getPostalCode() {
        return postalCode;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param postalCode the postalCode to set
     */
    public static void setPostalCode(String postalCode) {
        MockFactory.postalCode = postalCode;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the city field instance value
     */
    public static String getCity() {
        return city;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param city the city to set
     */
    public static void setCity(String city) {
        MockFactory.city = city;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the country field instance value
     */
    public static String getCountry() {
        return country;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param country the country to set
     */
    public static void setCountry(String country) {
        MockFactory.country = country;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the countryToRepresent field instance value
     */
    public static String getCountryToRepresent() {
        return countryToRepresent;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param countryToRepresent the countryToRepresent to set
     */
    public static void setCountryToRepresent(String countryToRepresent) {
        MockFactory.countryToRepresent = countryToRepresent;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the gender field instance value
     */
    public static String getGender() {
        return gender;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param gender the gender to set
     */
    public static void setGender(String gender) {
        MockFactory.gender = gender;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the age field instance value
     */
    public static String getAge() {
        return age;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param age the age to set
     */
    public static void setAge(String age) {
        MockFactory.age = age;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the firstName field instance value
     */
    public static String getFirstName() {
        return firstName;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param firstName the firstName to set
     */
    public static void setFirstName(String firstName) {
        MockFactory.firstName = firstName;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the lastName field instance value
     */
    public static String getLastName() {
        return lastName;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param lastName the lastName to set
     */
    public static void setLastName(String lastName) {
        MockFactory.lastName = lastName;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the phoneNumber field instance value
     */
    public static String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param phoneNumber the phoneNumber to set
     */
    public static void setPhoneNumber(String phoneNumber) {
        MockFactory.phoneNumber = phoneNumber;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the jobTitle field instance value
     */
    public static String getJobTitle() {
        return jobTitle;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param jobTitle the jobTitle to set
     */
    public static void setJobTitle(String jobTitle) {
        MockFactory.jobTitle = jobTitle;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the companyName field instance value
     */
    public static String getCompanyName() {
        return companyName;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param companyName the companyName to set
     */
    public static void setCompanyName(String companyName) {
        MockFactory.companyName = companyName;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the securityKey field instance value
     */
    public static String getSecurityKey() {
        return securityKey;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param securityKey the securityKey to set
     */
    public static void setSecurityKey(String securityKey) {
        MockFactory.securityKey = securityKey;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the timezone field instance value
     */
    public static String getTimezone() {
        return timezone;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param timezone the timezone to set
     */
    public static void setTimezone(String timezone) {
        MockFactory.timezone = timezone;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the primary field instance value
     */
    public static boolean isPrimary() {
        return primary;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param primary the primary to set
     */
    public static void setPrimary(boolean primary) {
        MockFactory.primary = primary;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the coderTypeId field instance value
     */
    public static Integer getCoderTypeId() {
        return coderTypeId;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param coderTypeId the coderTypeId to set
     */
    public static void setCoderTypeId(Integer coderTypeId) {
        MockFactory.coderTypeId = coderTypeId;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the user field instance value
     */
    public static User getUser() {
        return user;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param user the user to set
     */
    public static void setUser(User user) {
        MockFactory.user = user;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the id field instance value
     */
    public static long getId() {
        return ID;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the redirectedUrl field instance value
     */
    public static String getRedirectedUrl() {
        return REDIRECTED_URL;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param auditDAO the auditDAO to set
     */
    public static void setAuditDAO(AuditDAO auditDAO) {
        MockFactory.auditDAO = auditDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param userDAO the userDAO to set
     */
    public static void setUserDAO(UserDAO userDAO) {
        MockFactory.userDAO = userDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param userIdRetriever the userIdRetriever to set
     */
    public static void setUserIdRetriever(UserIdRetriever userIdRetriever) {
        MockFactory.userIdRetriever = userIdRetriever;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param coderTypeDAO the coderTypeDAO to set
     */
    public static void setCoderTypeDAO(CoderTypeDAO coderTypeDAO) {
        MockFactory.coderTypeDAO = coderTypeDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param timeZoneDAO the timeZoneDAO to set
     */
    public static void setTimeZoneDAO(TimeZoneDAO timeZoneDAO) {
        MockFactory.timeZoneDAO = timeZoneDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param stateDAO the stateDAO to set
     */
    public static void setStateDAO(StateDAO stateDAO) {
        MockFactory.stateDAO = stateDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param demographicQuestionDAO the demographicQuestionDAO to set
     */
    public static void setDemographicQuestionDAO(DemographicQuestionDAO demographicQuestionDAO) {
        MockFactory.demographicQuestionDAO = demographicQuestionDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param countryDAO the countryDAO to set
     */
    public static void setCountryDAO(CountryDAO countryDAO) {
        MockFactory.countryDAO = countryDAO;
    }

    /**
     * <p>
     * Updates user fields that were modified after action execution.
     * </p>
     * @param action the action to get modified fields
     */
    public static void updateFields(CompleteProfileAction action) {
        setAge(action.getAge());
        setCity(action.getCity());
        setCoderTypeId(action.getCoderTypeId());
        setCompanyName(action.getCompanyName());
        setCountry(action.getCountry());
        setCountryToRepresent(action.getCountry());
        setCurrentAddress1(action.getCurrentAddress1());
        setCurrentAddress2(action.getCurrentAddress2());
        setCurrentAddress3(action.getCurrentAddress3());
        setFirstName(action.getFirstName());
        setGender(action.getGender());
        setJobTitle(action.getJobTitle());
        setLastName(action.getLastName());
        setPhoneNumber(action.getPhoneNumber());
        setPostalCode(action.getPostalCode());
        setProvince(action.getProvince());
        setSecurityKey(action.getSecurityKey());
        setState(action.getState());
        setTimezone(action.getTimezone());
        setUser(action.getUser());
    }
}
