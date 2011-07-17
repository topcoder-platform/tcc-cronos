/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.opensymphony.xwork2.Preparable;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Level;
import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.StateDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.model.Address;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.Contact;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.State;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This action extends BaseSaveProfileAction to save the name and contact info for the user, and generates the
 * appropriate template data with the updated contact info data
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SaveNameAndContactAction extends BaseSaveProfileAction implements Preparable {

    /**
     * <p>
     * Represents validate() method signature constant name.
     * </p>
     */
    private static final String VALIDATE_METHOD_SIGNATURE = "SaveNameAndContactAction.validate()";

    /**
     * <p>
     * Represents execute() method signature constant name.
     * </p>
     */
    private static final String EXECUTE_METHOD_SIGNATURE = "SaveNameAndContactAction.execute()";

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
     * This is the StateDAO instance used to get state info.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private StateDAO stateDAO;

    /**
     * <p>
     * This is the TimeZoneDAO instance used to get time zone info.
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
     * Represents the user's state.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It will be non-null/empty, and validation requires it to be the same as the confirm password. This field will be
     * set during the request phase by the container, and will not change afterwards.
     * </p>
     */
    private String state;

    /**
     * <p>
     * Represents the user's time zone id.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It will be non-null/empty, and validation requires it to be the same as the confirm password. This field will be
     * set during the request phase by the container, and will not change afterwards.
     * </p>
     */
    private int timeZoneId;

    /**
     * <p>
     * Creates an instance of SaveNameAndContactAction.
     * </p>
     */
    public SaveNameAndContactAction() {
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
        ValidationUtility.checkNotNull(stateDAO, "stateDAO", ProfileActionConfigurationException.class);
        ValidationUtility.checkNotNull(timeZoneDAO, "timeZoneDAO", ProfileActionConfigurationException.class);
    }

    /**
     * <p>
     * This method is called by the execute method to process the output data.
     * </p>
     * <p>
     * This method will update the specific parts of the User that have been modified.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    protected void processInputData(User user) throws ProfileActionException {
        // string builders for auditing
        StringBuilder previousValues = new StringBuilder();
        StringBuilder newValues = new StringBuilder();
        // process primary fields
        processAddress(user, previousValues, newValues);
        processPhone(user, previousValues, newValues);
        processEmail(user, previousValues, newValues);
        // process other fields
        Helper.processAuditData(previousValues, newValues, user.getFirstName(), getSavedUser().getFirstName(),
                "First name");
        user.setFirstName(getSavedUser().getFirstName());
        Helper.processAuditData(previousValues, newValues, user.getLastName(), getSavedUser().getLastName(),
                "Last name");
        user.setLastName(getSavedUser().getLastName());
        // save userContact information: job title, company name
        Contact userContact = user.getContact();
        Contact savedUserContact = getSavedUser().getContact();
        Helper.processAuditData(previousValues, newValues, userContact == null ? null : userContact.getTitle(),
                savedUserContact == null ? null : savedUserContact.getTitle(), "Job title");
        Helper.processAuditData(previousValues, newValues,
                userContact == null || userContact.getCompany() == null ? null : userContact.getCompany().getName(),
                savedUserContact == null || savedUserContact.getCompany() == null ? null : savedUserContact
                        .getCompany().getName(), "Company name");
        user.setContact(savedUserContact);
        // save country to represent
        Coder userCoder = user.getCoder();
        Coder savedUserCoder = getSavedUser().getCoder();
        Helper.processAuditData(previousValues, newValues,
                userCoder == null || userCoder.getCompCountry() == null ? null : userCoder.getCompCountry().getCode(),
                savedUserCoder == null || savedUserCoder.getCompCountry() == null ? null : savedUserCoder
                        .getCompCountry().getCode(), "Country to represent");
        // update country to represent, if user coder is not null
        if (userCoder != null) {
            user.getCoder().setCompCountry(savedUserCoder == null ? null : savedUserCoder.getCompCountry());
        } else {
            // set coder, if saved is not null
            if (savedUserCoder != null) {
                user.setCoder(savedUserCoder);
            }
        }
        // process time zone
        Helper.processAuditData(previousValues, newValues, user.getTimeZone() == null ? null : user.getTimeZone()
                .getDescription(), getSavedUser().getTimeZone().getDescription(), "Time zone");
        user.setTimeZone(getSavedUser().getTimeZone());
        // make audit
        Helper.makeAudit(this, previousValues, newValues);
        additionalProcessInputData(user);
    }

    /**
     * <p>
     * Makes additional process on input data. Retrieves all neccessary values from database and set to given user.
     * </p>
     * @param user the user to make additional process
     * @throws ProfileActionException if any error occurs in underlying DAO
     */
    private void additionalProcessInputData(User user) throws ProfileActionException {
        try {
            String countryCode = user.getHomeAddress().getCountry().getCode();
            user.getHomeAddress().setCountry(countryCode == null ? null : countryDAO.find(countryCode));
            Country compCountry = user.getCoder().getCompCountry();
            user.getCoder().setCompCountry(compCountry == null ? null : countryDAO.find(compCountry.getCode()));
        } catch (RuntimeException e) {
            throw Helper.logAndWrapException(getLogger(), EXECUTE_METHOD_SIGNATURE,
                    "Error occurred in underlying DAO.", e);
        }
    }

    /**
     * <p>
     * Processes user's address.
     * </p>
     * @param user the user to set address
     * @param previousValues the previous values builder for auditing
     * @param newValues the new values builder for auditing
     */
    private void processAddress(User user, StringBuilder previousValues, StringBuilder newValues) {
        // copy home address
        Address newUserHomeAddress = getSavedUser().getAddresses().iterator().next();
        newUserHomeAddress.setAddressTypeId(Address.HOME_TYPE_ID);
        Address previousUserHomeAddress = user.getHomeAddress();
        // audit fields
        Helper.processAuditData(previousValues, newValues, previousUserHomeAddress == null ? null
                : previousUserHomeAddress.getAddress1(), newUserHomeAddress.getAddress1(), "Current Address 1");
        Helper.processAuditData(previousValues, newValues, previousUserHomeAddress == null ? null
                : previousUserHomeAddress.getAddress2(), newUserHomeAddress.getAddress2(), "Current Address 2");
        Helper.processAuditData(previousValues, newValues, previousUserHomeAddress == null ? null
                : previousUserHomeAddress.getAddress3(), newUserHomeAddress.getAddress3(), "Current Address 3");
        Helper.processAuditData(previousValues, newValues, previousUserHomeAddress == null ? null
                : previousUserHomeAddress.getCity(), newUserHomeAddress.getCity(), "City");
        Helper.processAuditData(previousValues, newValues, previousUserHomeAddress == null ? null
                : previousUserHomeAddress.getState() == null ? null : previousUserHomeAddress.getState().getCode(),
                newUserHomeAddress.getState() == null ? null : newUserHomeAddress.getState().getCode(), "State");
        Helper.processAuditData(previousValues, newValues, previousUserHomeAddress == null ? null
                : previousUserHomeAddress.getPostalCode(), newUserHomeAddress.getPostalCode(), "Postal Code");
        Helper.processAuditData(previousValues, newValues, previousUserHomeAddress == null ? null
                : previousUserHomeAddress.getProvince(), newUserHomeAddress.getProvince(), "Province");
        Helper.processAuditData(previousValues, newValues,
                previousUserHomeAddress == null ? null : previousUserHomeAddress.getCountry() == null ? null
                        : previousUserHomeAddress.getCountry().getCode(),
                newUserHomeAddress.getCountry() == null ? null : newUserHomeAddress.getCountry().getCode(), "Country");
        if (previousUserHomeAddress != null) {
            newUserHomeAddress.setId(previousUserHomeAddress.getId());
            // remove previous home address
            user.getAddresses().remove(previousUserHomeAddress);
        }
        // add new home address
        user.addAddress(newUserHomeAddress);
    }

    /**
     * <p>
     * Processes user's phone.
     * </p>
     * @param user the user to set primary phone
     * @param previousValues the previous values builder for auditing
     * @param newValues the new values builder for auditing
     */
    private void processPhone(User user, StringBuilder previousValues, StringBuilder newValues) {
        // copy primary phone number
        Phone newUserPrimaryPhoneNumber = getSavedUser().getPhoneNumbers().iterator().next();
        newUserPrimaryPhoneNumber.setPhoneTypeId(Phone.PHONE_TYPE_HOME);
        newUserPrimaryPhoneNumber.setPrimary(true);
        Phone previousUserPrimaryPhone = user.getPrimaryPhoneNumber();
        Helper.processAuditData(previousValues, newValues, previousUserPrimaryPhone == null ? null
                : previousUserPrimaryPhone.getNumber(), newUserPrimaryPhoneNumber.getNumber(), "Phone Number");
        if (previousUserPrimaryPhone != null) {
            newUserPrimaryPhoneNumber.setId(previousUserPrimaryPhone.getId());
            // set is unmodifiable, so make copy
            Set<Phone> phoneNumbers = new HashSet<Phone>(user.getPhoneNumbers());
            // remove previous value
            phoneNumbers.remove(previousUserPrimaryPhone);
            user.setPhoneNumbers(phoneNumbers);
        }
        // add new primary phone
        user.addPhoneNumber(newUserPrimaryPhoneNumber);
    }

    /**
     * <p>
     * Processes user's email.
     * </p>
     * @param user the user to set primary email
     * @param previousValues the previous values builder for auditing
     * @param newValues the new values builder for auditing
     */
    private void processEmail(User user, StringBuilder previousValues, StringBuilder newValues) {
        // copy primary email
        Email newUserPrimaryEmail = getSavedUser().getEmailAddresses().iterator().next();
        newUserPrimaryEmail.setEmailTypeId(Email.TYPE_ID_PRIMARY);
        newUserPrimaryEmail.setPrimary(true);
        Email previousUserPrimaryEmail = user.getPrimaryEmailAddress();
        Helper.processAuditData(previousValues, newValues, previousUserPrimaryEmail == null ? null
                : previousUserPrimaryEmail.getAddress(), newUserPrimaryEmail.getAddress(), "Primary Email");
        if (previousUserPrimaryEmail != null) {
            newUserPrimaryEmail.setId(previousUserPrimaryEmail.getId());
            // set is unmodifiable, so make copy
            Set<Email> emailAddresses = new HashSet<Email>(user.getEmailAddresses());
            // remove previous address
            emailAddresses.remove(previousUserPrimaryEmail);
            user.setEmailAddresses(emailAddresses);
        }
        // add new primary email
        user.addEmailAddress(newUserPrimaryEmail);
    }

    /**
     * <p>
     * This method is called by the execute method to generate the email template data.
     * </p>
     * @param user the user
     * @return the template data string
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    protected String generateEmailTemplateData(User user) {
        StringBuilder emailTemplateDataBuilder = new StringBuilder(Helper.getPreparedEmailTemplate(user));
        emailTemplateDataBuilder.append(Helper.createTag("FIRST_NAME", user.getFirstName(), true));
        emailTemplateDataBuilder.append(Helper.createTag("LAST_NAME", user.getLastName(), true));
        emailTemplateDataBuilder.append(Helper.createTag("E-MAIL", user.getEmailAddresses().iterator().next()
                .getAddress(), true));
        Contact userContact = user.getContact();
        emailTemplateDataBuilder.append(Helper.createTag("JOB_TITLE",
                userContact == null ? null : userContact.getTitle(), true));
        emailTemplateDataBuilder.append(Helper.createTag("COMPANY_NAME",
                userContact == null || userContact.getCompany() == null ? null : userContact.getCompany().getName(),
                true));
        Address userAddress = user.getAddresses().iterator().next();
        emailTemplateDataBuilder.append(Helper.createTag("CURRENT_ADDRESS_1", userAddress.getAddress1(), true));
        emailTemplateDataBuilder.append(Helper.createTag("CURRENT_ADDRESS_2", userAddress.getAddress2(), true));
        emailTemplateDataBuilder.append(Helper.createTag("CURRENT_ADDRESS_3", userAddress.getAddress3(), true));
        emailTemplateDataBuilder.append(Helper.createTag("CITY", userAddress.getCity(), true));
        emailTemplateDataBuilder.append(Helper.createTag("STATE", userAddress.getState() == null ? null : userAddress
                .getState().getCode(), true));
        emailTemplateDataBuilder.append(Helper.createTag("POSTAL_CODE", userAddress.getPostalCode(), true));
        emailTemplateDataBuilder.append(Helper.createTag("PROVINCE", userAddress.getProvince(), true));
        emailTemplateDataBuilder.append(Helper.createTag("COUNTRY", userAddress.getCountry().getName(), true));
        Coder userCoder = user.getCoder();
        emailTemplateDataBuilder.append(Helper.createTag("COUNTRY_TO_REPRESENT",
                userCoder == null || userCoder.getCompCountry() == null ? null : userCoder.getCompCountry().getName(),
                true));
        emailTemplateDataBuilder.append(Helper.createTag("PHONE_NUMBER", user.getPhoneNumbers().iterator().next(),
                true));
        emailTemplateDataBuilder.append(Helper.createTag("TIMEZONE", user.getTimeZone() == null ? null : user
                .getTimeZone().getDescription(), true));
        return Helper.createTag("DATA", emailTemplateDataBuilder.toString(), false);
    }

    /**
     * <p>
     * Prepares action for execution.
     * </p>
     * @throws Exception if any error occurs
     */
    @Override
    public void prepare() throws Exception {
        User user = new User();
        // add primary email to user
        Set<Email> emailAddresses = new HashSet<Email>();
        Email primaryEmail = new Email();
        primaryEmail.setEmailTypeId(Email.TYPE_ID_PRIMARY);
        primaryEmail.setPrimary(true);
        emailAddresses.add(primaryEmail);
        user.setEmailAddresses(emailAddresses);
        // add home address to user
        Set<Address> addresses = new HashSet<Address>();
        Address homeAddress = new Address();
        homeAddress.setAddressTypeId(Address.HOME_TYPE_ID);
        addresses.add(homeAddress);
        user.setAddresses(addresses);
        // add home phone
        Set<Phone> phoneNumbers = new HashSet<Phone>();
        Phone primaryPhone = new Phone();
        primaryPhone.setPhoneTypeId(Phone.PHONE_TYPE_HOME);
        phoneNumbers.add(primaryPhone);
        primaryPhone.setPrimary(true);
        user.setPhoneNumbers(phoneNumbers);
        // update saved user
        setSavedUser(user);
    }

    /**
     * <p>
     * Validates input parameters.
     * </p>
     */
    @Override
    @SuppressWarnings("unchecked")
    public void validate() {
        LoggingWrapperUtility.logEntrance(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null, true, Level.DEBUG);
        try {
            // check whether state code is valid
            Iterator<Address> addressIterator = getSavedUser().getAddresses().iterator();
            if (addressIterator.hasNext()) {
                Address savedUserAddress = addressIterator.next();
                if (savedUserAddress != null && savedUserAddress.getState() != null) {
                    List<State> states = new ArrayList<State>(stateDAO.getStates());
                    getLogger().log(Level.DEBUG, "Retrieved states: " + states.size());
                    getLogger().log(Level.DEBUG, "Current state: " + savedUserAddress.getState().getCode());
                    boolean found = false;
                    for (State usState : states) {
                        if (usState.getCode().equals(this.state)) {
                            found = true;
                            savedUserAddress.setState(usState);
                            break;
                        }
                    }
                    Helper.addFieldError(this, !found, "state", "state.notfound", VALIDATE_METHOD_SIGNATURE);
                }
            }
            getLogger().log(Level.DEBUG, "TimeZoneId: " + timeZoneId);
            // get time zone
            getSavedUser().setTimeZone(timeZoneDAO.find(timeZoneId));
        } catch (RuntimeException e) {
            Helper.logAndWrapException(getLogger(), VALIDATE_METHOD_SIGNATURE, "Error occurred in underlying DAO.", e);
            this.addActionError(Helper.FIELD_VALIDATION_ERROR_MESSAGE + "Error occurred in underlying DAO.");
            return;
        }
        LoggingWrapperUtility.logExit(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null, true, Level.DEBUG);
    }

    public CountryDAO getCountryDAO() {
        return countryDAO;
    }

    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the stateDAO field instance value
     */
    public StateDAO getStateDAO() {
        return stateDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param stateDAO the stateDAO to set
     */
    public void setStateDAO(StateDAO stateDAO) {
        this.stateDAO = stateDAO;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public TimeZoneDAO getTimeZoneDAO() {
        return timeZoneDAO;
    }

    public void setTimeZoneDAO(TimeZoneDAO timeZoneDAO) {
        this.timeZoneDAO = timeZoneDAO;
    }

    public int getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(int timeZoneId) {
        this.timeZoneId = timeZoneId;
    }
}