/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.topcoder.service.TestHelper;

/**
 * <p>
 * The login action used in the unit tests. It extends AbstractAction and implements SessionAware.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LoginAction extends AbstractAction implements SessionAware {

    /**
     * Indicates whether exception should be thrown when executing action.
     */
    private static boolean throwException;

    /**
     * The login name used for logging in. Accessed and modified using the namesake getter/setter.
     */
    private String loginName;

    /**
     * The password used for logging in. Accessed and modified using the namesake getter/setter.
     */
    private String password;

    /**
     * Represents the session in which the action is executing.
     */
    private Map<String, Object> session;

    /**
     * Setter for throwException.
     *
     * @param value the value to set for throwException
     */
    public static void setThrowException(boolean value) {
        throwException = value;
    }

    /**
     * The execute method for the action. It processes the login credentials and validates them, returning
     * success if they are valid or the login page otherwise.
     *
     * @return success if login credentials are valid or the login page otherwise
     *
     * @throws Exception if the action failed to execute
     */
    public String execute() throws Exception {
        if (throwException) {
            throw new ArithmeticException("test");
        }

        if (loginName == null) {
            // login credential data not entered yet
            return "login";
        }

        // login credential data was entered, make sure it's valid
        if (loginName.equals("topcoder") && password.equals("password")) {
            return SUCCESS;
        }

        // login credential data was invalid
        addActionError("Your login credentials were incorrect, try again.");
        return "login";
    }

    /**
     * Getter for the login name.
     *
     * @return the login name
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Setter for the login name.
     *
     * @param loginName the login name
     */
    public void setLoginName(String loginName) {
        session.put(TestHelper.KEY_FOR_LOGIN_CHECK, loginName);
        this.loginName = loginName;
    }

    /**
     * Getter for the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter for the session.
     *
     * @param session the session
     */
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}
