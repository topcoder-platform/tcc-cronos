/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;

import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.StateDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.model.Address;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.Company;
import com.topcoder.web.common.model.Contact;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.State;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for SaveNameAndContactAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SaveNameAndContactActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents SaveNameAndContactAction instance for testing.
     * </p>
     */
    private SaveNameAndContactAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        action = (SaveNameAndContactAction) getBean("saveNameAndContactAction");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        MockFactory.resetDAOs();
        action = null;
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction constructor.
     * </p>
     * <p>
     * SaveNameAndContactAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("SaveNameAndContactAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#processInputData() method with valid fields and user doesn't have any primary
     * addresses.
     * </p>
     * <p>
     * All values should be set successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessInputData() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        // clear all primary values
        User user = MockFactory.createUser(id, "first name", "last name", "handle");
        user.setEmailAddresses(new HashSet<Email>());
        when(action.getUserDAO().find(id)).thenReturn(user);
        User savedUser = MockFactory.createUser(id, "first name", "last name", "handle");
        savedUser.setCoder(new Coder());
        // new values
        String newEmailAddress = "somenewemail@mail.com";
        String newPrimaryPhone = "123456789";
        String newHomeAddress = "some new address";
        Address homeAddress = MockFactory.createAddress();
        homeAddress.setAddress1(newHomeAddress);
        Phone primaryPhoneNumber = MockFactory.createPhone();
        primaryPhoneNumber.setNumber(newPrimaryPhone);
        savedUser.getPrimaryEmailAddress().setAddress(newEmailAddress);
        savedUser.addAddress(homeAddress);
        savedUser.addPhoneNumber(primaryPhoneNumber);
        savedUser.setTimeZone(action.getTimeZoneDAO().find(1));
        action.setSavedUser(savedUser);
        // process input data
        action.processInputData(user);
        assertEquals("Value should be changed successfully.", newHomeAddress, user.getHomeAddress().getAddress1());
        assertEquals("Value should be changed successfully.", newPrimaryPhone, user.getPrimaryPhoneNumber()
                .getNumber());
        assertEquals("Value should be changed successfully.", newEmailAddress, user.getPrimaryEmailAddress()
                .getAddress());
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#processInputData() method with valid fields and user has all primary addresses.
     * </p>
     * <p>
     * All values should be set successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessInputData_HasValues() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        // clear all primary values
        User user = MockFactory.createUser(id, "first name", "last name", "handle");
        when(action.getUserDAO().find(id)).thenReturn(user);
        User savedUser = MockFactory.createUser(id, "first name", "last name", "handle");
        // new values
        String newEmailAddress = "somenewemail@mail.com";
        String newPrimaryPhone = "123456789";
        String newHomeAddress = "some new address";
        Address homeAddress = MockFactory.createAddress();
        homeAddress.setAddress1(newHomeAddress);
        Phone primaryPhoneNumber = MockFactory.createPhone();
        primaryPhoneNumber.setNumber(newPrimaryPhone);
        savedUser.getPrimaryEmailAddress().setAddress(newEmailAddress);
        user.getPrimaryEmailAddress().setAddress(newEmailAddress);
        savedUser.addAddress(homeAddress);
        user.addAddress(homeAddress);
        savedUser.addPhoneNumber(primaryPhoneNumber);
        user.addPhoneNumber(primaryPhoneNumber);
        Coder coder = new Coder();
        coder.setCompCountry(user.getHomeAddress().getCountry());
        savedUser.setCoder(coder);
        savedUser.setTimeZone(action.getTimeZoneDAO().find(1));
        action.validate();
        action.setSavedUser(savedUser);
        // process input data
        action.processInputData(user);
        assertEquals("Value should be changed successfully.", newHomeAddress, user.getHomeAddress().getAddress1());
        assertEquals("Value should be changed successfully.", newPrimaryPhone, user.getPrimaryPhoneNumber()
                .getNumber());
        assertEquals("Value should be changed successfully.", newEmailAddress, user.getPrimaryEmailAddress()
                .getAddress());
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#processInputData() method with valid fields and user has all some values.
     * </p>
     * <p>
     * All values should be set successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessInputData_HasValues1() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        // clear all primary values
        User user = MockFactory.getUser();
        when(action.getUserDAO().find(id)).thenReturn(user);
        User savedUser = MockFactory.getUser();
        // preset user's coder and contact value
        user.setCoder(new Coder());
        savedUser.setCoder(new Coder());
        user.setContact(new Contact());
        savedUser.setContact(new Contact());
        action.setSavedUser(savedUser);
        // process input data
        action.processInputData(user);
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#processInputData() method with valid fields and user has all some values.
     * </p>
     * <p>
     * All values should be set successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessInputData_HasValues2() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        // clear all primary values
        User user = MockFactory.createUser(1L, "first", "last", "handle");
        when(action.getUserDAO().find(id)).thenReturn(user);
        User savedUser = MockFactory.getUser();
        // preset user's coder and contact value
        Coder coder = new Coder();
        coder.setCompCountry(new Country());
        savedUser.setCoder(coder);
        Contact contact = new Contact();
        contact.setCompany(new Company());
        user.setContact(contact);
        savedUser.setContact(contact);
        action.setSavedUser(savedUser);
        // process input data
        action.processInputData(user);
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#generateEmailTemplateData(User) method with valid fields and user passed.
     * </p>
     * <p>
     * Template should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGenerateEmailTemplate() throws Exception {
        User user = MockFactory.getUser();
        String actualTemplate = action.generateEmailTemplateData(user);
        assertNotNull("Template should be generated successfully.", actualTemplate);
        assertTrue("Template should be generated successfully.", actualTemplate.startsWith("<DATA>"));
        assertTrue("Template should be generated successfully.", actualTemplate.endsWith("</DATA>"));
        assertTrue("Template should be generated successfully.", actualTemplate.contains("<HANDLE>handle</HANDLE>"));
        assertTrue("Template should be generated successfully.", actualTemplate.contains("<ID>1</ID>"));
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#generateEmailTemplateData(User) method with valid fields and user passed, some
     * fields that are not required are missing.
     * </p>
     * <p>
     * Template should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGenerateEmailTemplate_NotRequired1() throws Exception {
        User user = MockFactory.createUser(1L, "first", "last", "handle");
        user.setAddresses(MockFactory.getUser().getAddresses());
        user.getAddresses().iterator().next().setState(null);
        user.setPhoneNumbers(MockFactory.getUser().getPhoneNumbers());
        user.setEmailAddresses(MockFactory.getUser().getEmailAddresses());
        String actualTemplate = action.generateEmailTemplateData(user);
        assertNotNull("Template should be generated successfully.", actualTemplate);
        assertTrue("Template should be generated successfully.", actualTemplate.startsWith("<DATA>"));
        assertTrue("Template should be generated successfully.", actualTemplate.endsWith("</DATA>"));
        assertTrue("Template should be generated successfully.", actualTemplate.contains("<HANDLE>handle</HANDLE>"));
        assertTrue("Template should be generated successfully.", actualTemplate.contains("<ID>1</ID>"));
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#generateEmailTemplateData(User) method with valid fields and user passed, some
     * fields that are not required are missing.
     * </p>
     * <p>
     * Template should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGenerateEmailTemplate_NotRequired2() throws Exception {
        User user = MockFactory.getUser();
        user.setContact(new Contact());
        user.setCoder(new Coder());
        String actualTemplate = action.generateEmailTemplateData(user);
        assertNotNull("Template should be generated successfully.", actualTemplate);
        assertTrue("Template should be generated successfully.", actualTemplate.startsWith("<DATA>"));
        assertTrue("Template should be generated successfully.", actualTemplate.endsWith("</DATA>"));
        assertTrue("Template should be generated successfully.", actualTemplate.contains("<HANDLE>handle</HANDLE>"));
        assertTrue("Template should be generated successfully.", actualTemplate.contains("<ID>1</ID>"));
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#getStateDAO() method.
     * </p>
     * <p>
     * stateDAO should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetStateDAO() {
        StateDAO stateDAO = MockFactory.getStateDAO();
        action.setStateDAO(stateDAO);
        assertSame("getStateDAO() doesn't work properly.", stateDAO, action.getStateDAO());
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#setStateDAO(StateDAO) method.
     * </p>
     * <p>
     * stateDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetStateDAO() {
        StateDAO stateDAO = MockFactory.getStateDAO();
        action.setStateDAO(stateDAO);
        assertSame("setStateDAO() doesn't work properly.", stateDAO, action.getStateDAO());
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#getCountryDAO() method.
     * </p>
     * <p>
     * countryDAO should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCountryDAO() {
        CountryDAO countryDAO = MockFactory.getCountryDAO();
        action.setCountryDAO(countryDAO);
        assertSame("getCountryDAO() doesn't work properly.", countryDAO, action.getCountryDAO());
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#setCountryDAO(CountryDAO) method.
     * </p>
     * <p>
     * countryDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCountryDAO() {
        CountryDAO countryDAO = MockFactory.getCountryDAO();
        action.setCountryDAO(countryDAO);
        assertSame("setCountryDAO() doesn't work properly.", countryDAO, action.getCountryDAO());
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#getTimeZoneDAO() method.
     * </p>
     * <p>
     * timeZoneDAO should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetTimeZoneDAO() {
        TimeZoneDAO timeZoneDAO = MockFactory.getTimeZoneDAO();
        action.setTimeZoneDAO(timeZoneDAO);
        assertSame("getTimeZoneDAO() doesn't work properly.", timeZoneDAO, action.getTimeZoneDAO());
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#setTimeZoneDAO(TimeZoneDAO) method.
     * </p>
     * <p>
     * timeZoneDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetTimeZoneDAO() {
        TimeZoneDAO timeZoneDAO = MockFactory.getTimeZoneDAO();
        action.setTimeZoneDAO(timeZoneDAO);
        assertSame("setTimeZoneDAO() doesn't work properly.", timeZoneDAO, action.getTimeZoneDAO());
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#getState() method.
     * </p>
     * <p>
     * state should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetState() {
        String state = "test";
        action.setState(state);
        assertSame("getState() doesn't work properly.", state, action.getState());
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#setState(String) method.
     * </p>
     * <p>
     * state should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetState() {
        String state = "test";
        action.setState(state);
        assertSame("setState() doesn't work properly.", state, action.getState());
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#getTimeZoneId() method.
     * </p>
     * <p>
     * timeZoneId should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetTimeZoneId() {
        int timeZoneId = 1;
        action.setTimeZoneId(timeZoneId);
        assertEquals("getTimeZoneId() doesn't work properly.", timeZoneId, action.getTimeZoneId());
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#setTimeZoneId(int) method.
     * </p>
     * <p>
     * timeZoneId should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetTimeZoneId() {
        int timeZoneId = 1;
        action.setTimeZoneId(timeZoneId);
        assertEquals("setTimeZoneId() doesn't work properly.", timeZoneId, action.getTimeZoneId());
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#checkInitialization() method with null stateDAO.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_StateDAO() throws Exception {
        action.setStateDAO(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#checkInitialization() method with null countryDAO.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_CountryDAO() throws Exception {
        action.setCountryDAO(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#checkInitialization() method with null TimeZoneDAO.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_TimeZoneDAO() throws Exception {
        action.setTimeZoneDAO(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#validate() method with valid fields.
     * </p>
     * <p>
     * Validation should ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        User savedUser = MockFactory.getUser();
        // new values
        action.setSavedUser(savedUser);
        action.setState("CA");
        action.setTimeZoneId(1);
        // process input data
        action.validate();
        assertEquals("There should be no field errors.", 0, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests SaveNameAndContactAction#validate() method with valid fields, but user's state is not found.
     * </p>
     * <p>
     * Validation should ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_StateNotFound() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        User savedUser = MockFactory.getUser();
        // new values
        action.setTimeZoneId(1);
        savedUser.getAddresses().iterator().next().setState(new State());
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
        assertEquals("There should be no field errors.", 1, action.getFieldErrors().size());
    }
}
