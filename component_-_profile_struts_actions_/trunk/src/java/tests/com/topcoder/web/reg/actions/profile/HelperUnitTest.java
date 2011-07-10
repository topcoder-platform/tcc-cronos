/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionException;
import com.topcoder.web.reg.ProfileCompletenessRetrievalException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for Helper.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents BaseProfileAction instance for testing.
     * </p>
     */
    private BaseProfileAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = (BaseProfileAction) getActionProxy("/baseProfileAction").getAction();
        MockFactory.initDAOs();
        action.setServletRequest(MockFactory.createServletRequest());
        MockFactory.createUserInSession(action);
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
        action.clearActionErrors();
        action.clearFieldErrors();
        action = null;
    }

    /**
     * <p>
     * Tests Helper#addFieldError() method with valid arguments passed.
     * </p>
     * <p>
     * Field error should be added successfully. No exception is expected.
     * </p>
     */
    public void testAddFieldError() {
        String fieldName = "fieldName";
        Helper.addFieldError(action, true, fieldName, "error", getName());
        assertNotNull("Field error should be added successfully.", action.getFieldErrors().get(fieldName));
    }

    /**
     * <p>
     * Tests Helper#addFieldError() method with valid arguments passed.
     * </p>
     * <p>
     * Field error should not be added successfully. No exception is expected.
     * </p>
     */
    public void testAddFieldError_False() {
        String fieldName = "fieldName";
        Helper.addFieldError(action, false, fieldName, "error", getName());
        assertNull("Field error should be added successfully.", action.getFieldErrors().get(fieldName));
    }

    /**
     * <p>
     * Tests Helper#createTag() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     */
    public void testCreateTag() {
        Object value = 1;
        String name = "name";
        assertEquals("Valid value should be retrieved.", "<name>1</name>", Helper.createTag(name, value, false));
    }

    /**
     * <p>
     * Tests Helper#createTag() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     */
    public void testCreateTag_Escaped() {
        Object value = "<>&'\"";
        String name = "name";
        assertEquals("Valid value should be retrieved.", "<name>&lt;&gt;&amp;&apos;&quot;</name>",
                Helper.createTag(name, value, true));
    }

    /**
     * <p>
     * Tests Helper#getCompletenessReport() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetCompletenessReport() throws Exception {
        User user = MockFactory.getUser();
        action.setProfileCompletenessReport(null);
        when(action.getProfileCompletenessRetriever().getProfileCompletenessReport(user)).thenReturn(
                new ProfileCompletenessReport());
        Helper.getCompletenessReport(action, user, getName());
        assertNotNull("Valid value should be retrieved.", action.getProfileCompletenessReport());
    }

    /**
     * <p>
     * Tests Helper#getCompletenessReport() method with valid arguments passed, but exception occurred in underlying
     * ProfileCompletenessRetriever.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetCompletenessReport_Exception() throws Exception {
        User user = MockFactory.getUser();
        action.setProfileCompletenessReport(null);
        when(action.getProfileCompletenessRetriever().getProfileCompletenessReport(user)).thenThrow(
                new ProfileCompletenessRetrievalException("just for testing."));
        try {
            Helper.getCompletenessReport(action, user, getName());
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests Helper#getPreparedEmailTemplate() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     */
    public void testGetPreparedEmailTemplate() {
        User user = MockFactory.createUser(1L, "first", "last", "handle");
        assertEquals("Valid value should be retrieved.", "<ID>1</ID><HANDLE>handle</HANDLE>",
                Helper.getPreparedEmailTemplate(user));
    }

    /**
     * <p>
     * Tests Helper#logAndWrapException() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     */
    public void testLogAndWrapException1() {
        assertNotNull("Valid value should be retrieved.",
                Helper.logAndWrapException(action.getLogger(), getName(), "error", null));
    }

    /**
     * <p>
     * Tests Helper#logAndWrapException() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     */
    public void testLogAndWrapException2() {
        assertNotNull("Valid value should be retrieved.", Helper.logAndWrapException(action.getLogger(), getName(),
                "error", new RuntimeException("just for testing.")));
    }

    /**
     * <p>
     * Tests Helper#logAndWrapException() method with valid arguments passed.
     * </p>
     * <p>
     * Audit should be done. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testMakeAudit1() throws Exception {
        Helper.makeAudit(action, null, null);
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests Helper#logAndWrapException() method with valid arguments passed.
     * </p>
     * <p>
     * Audit should be done. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testMakeAudit2() throws Exception {
        StringBuilder previousValues = new StringBuilder("name1:value1");
        StringBuilder newValues = new StringBuilder("name1:value2");
        Helper.makeAudit(action, previousValues, newValues);
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests Helper#logAndWrapException() method with valid arguments passed.
     * </p>
     * <p>
     * Audit should be done. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testMakeAudit3() throws Exception {
        StringBuilder previousValues = new StringBuilder();
        StringBuilder newValues = new StringBuilder();
        Helper.makeAudit(action, previousValues, newValues);
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests Helper#processAuditData() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     */
    public void testProcessAuditData1() {
        StringBuilder previousValues = new StringBuilder("name1:value1");
        StringBuilder newValues = new StringBuilder("name1:value2");
        Helper.processAuditData(previousValues, newValues, "previousValue", "newValue", "DataElement");
        assertEquals("Valid value should be retrieved.", "name1:value1, DataElement:previousValue",
                previousValues.toString());
        assertEquals("Valid value should be retrieved.", "name1:value2, DataElement:newValue", newValues.toString());
    }

    /**
     * <p>
     * Tests Helper#processAuditData() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     */
    public void testProcessAuditData2() {
        StringBuilder previousValues = new StringBuilder();
        StringBuilder newValues = new StringBuilder();
        Helper.processAuditData(previousValues, newValues, "previousValue", "newValue", "DataElement");
        assertEquals("Valid value should be retrieved.", "DataElement:previousValue", previousValues.toString());
        assertEquals("Valid value should be retrieved.", "DataElement:newValue", newValues.toString());
    }

    /**
     * <p>
     * Tests Helper#retrieveLoggedInUser() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testRetrieveLoggedInUser() throws Exception {
        assertNotNull("Valid value should be retrieved.", Helper.retrieveLoggedInUser(action, getName()));
    }

    /**
     * <p>
     * Tests Helper#retrieveUserFromDatabase() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testRetrieveUserFromDatabase() throws Exception {
        assertNotNull("Valid value should be retrieved.", Helper.retrieveUserFromDatabase(action, 1L, getName()));
    }

    /**
     * <p>
     * Tests Helper#retrieveUserFromDatabase() method with valid arguments passed, but user is not found.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testRetrieveUserFromDatabase_Exception1() throws Exception {
        try {
            long id = 3L;
            Helper.retrieveUserFromDatabase(action, id, getName());
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests Helper#retrieveUserFromDatabase() method with valid arguments passed, but exception occurred in underlying
     * DAO.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testRetrieveUserFromDatabase_Exception2() throws Exception {
        long id = 4L;
        when(action.getUserDAO().find(id)).thenThrow(new RuntimeException("just for testing."));
        try {
            Helper.retrieveUserFromDatabase(action, id, getName());
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests Helper#retrieveUserFromSession() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testRetrieveUserFromSession() throws Exception {
        assertNotNull("Valid value should be retrieved.", Helper.retrieveUserFromSession(action, getName()));
    }

    /**
     * <p>
     * Tests Helper#retrieveUserFromSession() method with valid arguments passed, but user is not found.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testRetrieveUserFromSession_Exception1() throws Exception {
        action.setSession(new HashMap<String, Object>());
        try {
            Helper.retrieveUserFromSession(action, getName());
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests Helper#retrieveUserFromSession() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckNotNullNorEmpty_Null() throws Exception {
        assertFalse("Valid value should be retrieved.", Helper.checkNotNullNorEmpty(null));
    }

    /**
     * <p>
     * Tests Helper#retrieveUserFromSession() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckNotNullNorEmpty_Empty() throws Exception {
        assertFalse("Valid value should be retrieved.", Helper.checkNotNullNorEmpty(""));
    }

    /**
     * <p>
     * Tests Helper#retrieveUserFromSession() method with valid arguments passed.
     * </p>
     * <p>
     * Valid value should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckNotNullNorEmpty() throws Exception {
        assertTrue("Valid value should be retrieved.", Helper.checkNotNullNorEmpty("NotNullNorEmpty"));
    }
}
