/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.mockrunner.mock.web.ActionMockObjectFactory;
import com.mockrunner.struts.BasicActionTestCaseAdapter;
import com.topcoder.chat.user.profile.ChatUserProfile;

/**
 * <p>
 * This unit test addresses the functionality provided by the <code>{@link ClientLoginAction}</code> class.
 * </p>
 *
 * @author tyrian
 * @version 1.0
 * @see ClientLoginAction
 */
public class ClientLoginActionTest extends BasicActionTestCaseAdapter {
    /**
     * <p>
     * The instance of <code>ClientLoginAction</code> for test.
     * </p>
     */
    private ClientLoginAction clientLoginAction;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests of <code>ClientLoginActionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ClientLoginActionTest.class);
    }

    /**
     * <p>
     * Sets up the environment for each TestCase.
     * </p>
     *
     * @throws Exception
     *             throws exception if any to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfigs(true, "config.xml");
        TestHelper.deployEJB();
        ActionMockObjectFactory factory = getActionMockObjectFactory();
        // add the necessary parameters for testing.
        factory.getMockRequest().setupAddParameter("user", "junit_user");
        factory.getMockRequest().setupAddParameter("pwd", "pwd");
        factory.getMockRequest().setupAddParameter("cat", "5");
        clientLoginAction = new ClientLoginAction();
    }

    /**
     * <p>
     * Tears down the environment after execution of each TestCase.
     * </p>
     *
     * @throws Exception
     *             throws exception if any to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        clientLoginAction = null;
    }

    /**
     * <p>
     * Detail test of <code>{@link ClientLoginAction#ClientLoginAction()}</code> constructor.
     * </p>
     *
     * <p>
     * Expects non null instance. Also check inheritance.
     * </p>
     *
     */
    public void testClientLoginActionAccuracy() {
        assertNotNull("failed to create ClientLoginAction", clientLoginAction);

        assertTrue("ClientLoginAction should inherit from LoginAction", clientLoginAction instanceof LoginAction);
    }

    /**
     * <p>
     * Detail test of <code>{@link ClientLoginAction#execute(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response)}</code>
     * method.
     * </p>
     *
     * <p>
     * Executes the client login as unregistered.
     * </p>
     *
     */
    public void testExecuteAccuracy1() {
        getActionMockObjectFactory().getMockRequest().setupAddParameter("type", "unreg");
        getActionMockObjectFactory().getMockRequest().setupAddParameter("fname", "test");
        getActionMockObjectFactory().getMockRequest().setupAddParameter("lname", "user");
        getActionMockObjectFactory().getMockRequest().setupAddParameter("email", "tester@topcoder.com");

        actionPerform(ClientLoginAction.class);

        // verify the results of the action processing
        verifyNoActionErrors();
        verifyNoActionMessages();
        verifyForward(TestHelper.LOGIN_SUCCESS_FWD);

        Object object = getSessionAttribute(clientLoginAction.getUserProfileKey());

        assertTrue("Chat profile expected in the session attribute", object instanceof ChatUserProfile);

        ChatUserProfile profile = (ChatUserProfile) object;

        assertEquals("Chat profile user name mismatch", profile.getUsername(), "test user");
        assertEquals("Chat profile role property mismatch", profile.getPropertyValue(clientLoginAction
                .getRoleKey())[0], "client");
        assertEquals("Chat profile category property mismatch", profile.getPropertyValue(clientLoginAction
                .getCategoryKey())[0], "5");
        assertEquals("Chat profile type mismatch", profile.getType(), "Unregistered");
    }

    /**
     * <p>
     * Detail test of <code>{@link ClientLoginAction#execute(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response)}</code>
     * method.
     * </p>
     *
     * <p>
     * Executes the client login as registered.
     * </p>
     *
     */
    public void testExecuteAccuracy2() {
        getActionMockObjectFactory().getMockRequest().setupAddParameter("type", "reg");

        actionPerform(ClientLoginAction.class);

        // verify the results of the action processing
        verifyNoActionErrors();
        verifyNoActionMessages();
        verifyForward(TestHelper.LOGIN_SUCCESS_FWD);

        Object object = getSessionAttribute(clientLoginAction.getUserProfileKey());

        assertTrue("Chat profile expected in the session attribute", object instanceof ChatUserProfile);

        ChatUserProfile profile = (ChatUserProfile) object;

        assertEquals("Chat profile user name mismatch", profile.getUsername(), "junit_user");
        assertEquals("Chat profile role property mismatch", profile.getPropertyValue(clientLoginAction
                .getRoleKey())[0], "client");
        assertEquals("Chat profile category property mismatch", profile.getPropertyValue(clientLoginAction
                .getCategoryKey())[0], "5");
        assertEquals("Chat profile type mismatch", profile.getType(), "Registered");
    }

    /**
     * <p>
     * Failure test of <code>{@link ClientLoginAction#execute(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response)}</code>
     * method.
     * </p>
     *
     * <p>
     * The category is unchattable.
     * </p>
     */
    public void testExecuteFailure1() {
        getActionMockObjectFactory().getMockRequest().setupAddParameter("cat", "10");
        actionPerform(ClientLoginAction.class);
        verifyNoActionErrors();
        verifyNoActionMessages();
        verifyForward(TestHelper.UNCHATTABLE);
    }

    /**
     * <p>
     * Failure test of <code>{@link ClientLoginAction#execute(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response)}</code>
     * method.
     * </p>
     *
     * <p>
     * Unknown registered user.
     * </p>
     */
    public void testExecuteFailure2() {
        getActionMockObjectFactory().getMockRequest().setupAddParameter("type", "reg");
        getActionMockObjectFactory().getMockRequest().setupAddParameter("user", "unknown_user");
        actionPerform(ClientLoginAction.class);
        verifyNoActionErrors();
        verifyNoActionMessages();
        verifyForward(TestHelper.LOGIN_FAIL_FWD);
    }

    /**
     * <p>
     * Detail test of <code>{@link ClientLoginAction#checkChattable(String category)}</code> method.
     * </p>
     *
     * <p>
     * Expects <code>true</code> for category id "5".
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCheckChattableAccuracy() throws Exception {
        assertTrue("failed to get the chattable flag", clientLoginAction.checkChattable("5"));
        assertFalse("failed to get the chattable flag", clientLoginAction.checkChattable("2"));
    }

    /**
     * <p>
     * Failure test of <code>{@link ClientLoginAction#checkChattable(String category)}</code> method.
     * </p>
     *
     * <p>
     * category is <code>null</code>.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code> to be thrown.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCheckChattableFailure1() throws Exception {
        try {
            clientLoginAction.checkChattable(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link ClientLoginAction#checkChattable(String category)}</code> method.
     * </p>
     *
     * <p>
     * category is empty.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code> to be thrown.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCheckChattableFailure2() throws Exception {
        try {
            clientLoginAction.checkChattable(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link ClientLoginAction#checkChattable(String category)}</code> method.
     * </p>
     *
     * <p>
     * Wrong config.
     * </p>
     *
     * <p>
     * Expects <code>com.topcoder.db.connectionfactory.ConfigurationException</code> to be thrown.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCheckChattableFailure() throws Exception {
        TestHelper.releaseConfigs();
        try {
            clientLoginAction.checkChattable("5");
            fail("com.topcoder.db.connectionfactory.ConfigurationException should be thrown.");
        } catch (com.topcoder.db.connectionfactory.ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Detail test of <code>{@link ClientLoginAction#getUnchattableForwardName()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetUnchattableForwardNameAccuracy() {
        assertEquals("failed to get unchattable forward name", TestHelper.UNCHATTABLE, clientLoginAction
                .getUnchattableForwardName());
    }

    /**
     * <p>
     * Detail test of <code>{@link ClientLoginAction#getConnectionName()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetConnectionNameAccuracy() {
        assertEquals("failed to get connection name", TestHelper.CONNECTION_NAME, clientLoginAction
                .getConnectionName());
    }

    /**
     * <p>
     * Detail test of <code>{@link ClientLoginAction#getFamilyNameKey()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetFamilyNameKeyAccuracy() {
        assertEquals("failed to get family name key", TestHelper.FAMILY_NAME_KEY, clientLoginAction
                .getFamilyNameKey());
    }

    /**
     * <p>
     * Detail test of <code>{@link ClientLoginAction#getLastNameKey()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetLastNameKeyAccuracy() {
        assertEquals("failed to get last name key", TestHelper.LAST_NAME_KEY, clientLoginAction.getLastNameKey());
    }

    /**
     * <p>
     * Detail test of <code>{@link ClientLoginAction#getTitleKey()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetTitleKeyAccuracy() {
        assertEquals("failed to get title key", TestHelper.TITLE, clientLoginAction.getTitleKey());
    }

    /**
     * <p>
     * Detail test of <code>{@link ClientLoginAction#getEmailKey()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetEmailKeyAccuracy() {
        assertEquals("failed to get email key", TestHelper.EMAIL, clientLoginAction.getEmailKey());
    }

    /**
     * <p>
     * Detail test of <code>{@link ClientLoginAction#getCompanyKey()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetCompanyKeyAccuracy() {
        assertEquals("failed to get company key", TestHelper.COMPANY, clientLoginAction.getCompanyKey());
    }
}
