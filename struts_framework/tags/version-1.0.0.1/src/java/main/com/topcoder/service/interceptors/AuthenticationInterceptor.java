/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.interceptors;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.service.actions.Helper;

/**
 * <p>
 * This interceptor will be responsible for checking if the user info is in the session. It will look for user info
 * in the session using <code>userSessionIdentityKey</code>. If found, it allows the action to proceed. If not, it
 * sends control to the login page.
 * </p>
 *
 * <p>
 * This class extends the AbstractInterceptor class. The user session key and login page are configurable.
 * </p>
 *
 * <p>
 * <strong>Demo:</strong>
 * <pre>
 * // create the struts2 action proxy
 * ActionProxy proxy = getActionProxy("/login");
 * TestHelper.prepareActionProxy(proxy);
 *
 * // call the intercept method, result will be the login page
 * String result = authenticationInterceptorInstance.intercept(
 *     proxy.getInvocation());
 * System.out.println(
 *     "result of authenticationInterceptor when credential "
 *     + "data is missing is " + result);
 *
 * // create a new ActionProxy
 * proxy = getActionProxy("/login");
 * TestHelper.prepareActionProxy(proxy);
 *
 * // set the login credential
 * proxy.getInvocation().getInvocationContext().getSession().put(
 *     TestHelper.KEY_FOR_LOGIN_CHECK, "topcoder");
 *
 * // inject the login and password parameters
 * Map&lt;String, Object&gt; parameters = new HashMap&lt;String, Object&gt;();
 * parameters.put("loginName", "topcoder");
 * parameters.put("password", "password");
 * proxy.getInvocation().getInvocationContext().setParameters(parameters);
 *
 * // call the intercept method, result will be success since
 * // credential information was provided
 * result = authenticationInterceptorInstance.intercept(proxy.getInvocation());
 * System.out.println(
 *     "result of authenticationInterceptor when credential data "
 *     + "is provided is " + result);
 * </pre>
 * </p>
 *
 * <p>
 * <strong>Example configuration for struts2 (shows how to configure the interceptors and actions):</strong>
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;!DOCTYPE struts PUBLIC
 *     "-//Apache Software Foundation//DTD Struts Configuration 2.0//EN"
 *     "http://struts.apache.org/dtds/struts-2.0.dtd"&gt;
 *
 * &lt;struts&gt;
 *     &lt;constant name="struts.devMode" value="true" /&gt;
 *
 *     &lt;package name="tcs-default" extends="struts-default" abstract="true"&gt;
 *         &lt;!-- Setup interceptors stack --&gt;
 *         &lt;interceptors&gt;
 *             &lt;interceptor name="authentication" class="authenticationInterceptor" /&gt;
 *             &lt;interceptor name="logging" class="loggingInterceptor" /&gt;
 *
 *             &lt;interceptor-stack name="defaultTCSStack"&gt;
 *                 &lt;interceptor-ref name="authentication"/&gt;
 *                 &lt;interceptor-ref name="logging"/&gt;
 *                 &lt;interceptor-ref name="defaultStack"/&gt;
 *             &lt;/interceptor-stack&gt;
 *         &lt;/interceptors&gt;
 *
 *         &lt;!-- Make the default one used for all actions unless otherwise configured. --&gt;
 *         &lt;default-interceptor-ref name="defaultTCSStack" /&gt;
 *
 *         &lt;!-- Configure global results for AuthenticationInterceptor --&gt;
 *         &lt;global-results&gt;
 *             &lt;result name="login"&gt;/login.jsp&lt;/result&gt;
 *         &lt;/global-results&gt;
 *
 *     &lt;/package&gt;
 *
 *     &lt;package name="default" namespace="/" extends="tcs-default"&gt;
 *
 *         &lt;interceptors&gt;
 *             &lt;!-- for the login stack, we exclude the authentication interceptor --&gt;
 *             &lt;interceptor-stack name="loginTCSStack"&gt;
 *                 &lt;interceptor-ref name="logging"/&gt;
 *                 &lt;interceptor-ref name="defaultStack"/&gt;
 *             &lt;/interceptor-stack&gt;
 *         &lt;/interceptors&gt;
 *
 *         &lt;action name="login" class="loginAction"&gt;
 *             &lt;interceptor-ref name="loginTCSStack" /&gt;
 *             &lt;result name="input"&gt;/login.jsp&lt;/result&gt;
 *             &lt;result type="redirect"&gt;employees&lt;/result&gt;
 *         &lt;/action&gt;
 *
 *         &lt;action name="employees" class="employeeAction"&gt;
 *             &lt;result&gt;/employees.jsp&lt;/result&gt;
 *         &lt;/action&gt;
 *
 *     &lt;/package&gt;
 *
 * &lt;/struts&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <strong>Example configuration (shows how to configure AuthenticationInterceptor bean in
 * applicationContext.xml):</strong>
 * <pre>
 * &lt;bean id="authenticationInterceptor"
 *   class="com.topcoder.service.interceptors.AuthenticationInterceptor"&gt;
 *     &lt;property name="loginPageName" value="login"&gt;&lt;/property&gt;
 *     &lt;property name="userSessionIdentityKey" value="USER_ID_KEY"&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class AuthenticationInterceptor extends AbstractInterceptor {

    /**
     * <p>
     * Represents the login page name attribute.
     * <p>
     *
     * <p>
     * It can be set and accessed in the set/get methods.
     * It must be non-null and non-empty string.
     * </p>
     */
    private String loginPageName;

    /**
     * <p>
     * Represents the user session identity key attribute.
     * </p>
     *
     * <p>
     * It can be set and accessed in the set/get methods.
     * It must be non-null and non-empty string.
     * </p>
     */
    private String userSessionIdentityKey;

    /**
     * Default constructor, constructs an instance of this class.
     */
    public AuthenticationInterceptor() {
        // does nothing
    }

    /**
     * <p>
     * This method provides the intercept logic.
     * </p>
     *
     * <p>
     * It retrieves the user credentials from session and based on this data, it redirects to login page
     * if a user needs authentication, or invokes the action and returns its result otherwise.
     * </p>
     *
     * @param invocation the action invocation to intercept
     *
     * @return the interception result, which will either be the login page if user needs authentication, or
     *         the action result otherwise
     *
     * @throws IllegalStateException if the login page name or user session identity key are not injected (they
     *         must be not null and not empty)
     *
     * @throws Exception if an error occurs when invoking the action
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        if (loginPageName == null) {
            throw new IllegalStateException("loginPageName has not been injected.");
        }

        if (userSessionIdentityKey == null) {
            throw new IllegalStateException("userSessionIdentityKey has not been injected.");
        }

        // get the session object
        Map<String, Object> session = invocation.getInvocationContext().getSession();

        // if user needs authentication then redirect to login page
        if (session.get(userSessionIdentityKey) == null) {
            return loginPageName;
        }

        // process the action and return its result
        return invocation.invoke();
    }

    /**
     * Getter for the login page name.
     *
     * @return the login page name
     */
    public String getLoginPageName() {
        return loginPageName;
    }

    /**
     * Setter for the login page name.
     *
     * @param loginPageName login page name
     *
     * @throws IllegalArgumentException if argument is null or empty
     */
    public void setLoginPageName(String loginPageName) {
        Helper.checkNotNullOrEmpty(loginPageName, "loginPageName");
        this.loginPageName = loginPageName;
    }

    /**
     * Getter for the user session identity key.
     *
     * @return the user session identity key
     */
    public String getUserSessionIdentityKey() {
        return userSessionIdentityKey;
    }

    /**
     * Setter for the user session identity key.
     *
     * @param userSessionIdentityKey user session identity key
     *
     * @throws IllegalArgumentException if argument is null or empty
     */
    public void setUserSessionIdentityKey(String userSessionIdentityKey) {
        Helper.checkNotNullOrEmpty(userSessionIdentityKey, "userSessionIdentityKey");
        this.userSessionIdentityKey = userSessionIdentityKey;
    }
}
