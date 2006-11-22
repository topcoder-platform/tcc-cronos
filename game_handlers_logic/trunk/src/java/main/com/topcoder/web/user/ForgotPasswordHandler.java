/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;

import com.topcoder.security.forgotpassword.ConfigurationException;
import com.topcoder.security.forgotpassword.ForgotPassword;
import com.topcoder.security.forgotpassword.ForgotPasswordManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * <code>ForgotPasswordHandler</code> implements <code>Handler</code>
 * interface from FrontController component. This handler wraps the Forgot
 * Password component. Some validation values (e.g. email address) will be
 * provided as request parameters (with configurable parameter names).
 * <li>If Forgot Password validates the request then it will be asked to
 * process the appropriate action ({@link ForgotPassword#processAction(boolean, Object)}),
 * and the handler will return null.</li>
 * <li>If Forgot Password finds the request invalid then it is not asked to
 * process an action; instead the handler returns a (configurable) result
 * string.</li>
 * In the success case, the effect of processing the action is expected to be
 * that the user's plain-text password is e-mailed to him, which will be handled
 * by the Forgot Password component.
 * </p>
 * <p>
 * <strong>Thread safety: </strong> This class is thread-safe since it is
 * immutable, and the ForgotPassword implementation is expected to be
 * thread-safe as Forgot Password component spec emphasizes it.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class ForgotPasswordHandler implements Handler {

    /**
     * <p>
     * Represents the result name to return in the execute method if the
     * validation done by Forgot Password component fails.
     * </p>
     * <p>
     * It is initialized in constructor, and never changed afterwards. It must
     * be non-null, non-empty string.
     * </p>
     */
    private final String validationFailedResultName;

    /**
     * <p>
     * Represents the ForgotPassword object to validate the request parameter
     * values, and process the request, it is used in execute method.
     * </p>
     * <p>
     * It is initialized in constructor, and never changed afterwards. It must
     * be non-null.
     * </p>
     */
    private final ForgotPassword forgotPwd;

    /**
     * <p>
     * Represents an array of request parameter keys to get parameter values
     * from the request (HttpServletRequest object) in execute method.
     * </p>
     * <p>
     * It is initialized in constructor, and never changed afterwards. It is
     * non-null, and must contain at least 1 element, each element in this array
     * must be non-null, non-empty string, and its length must be the same as
     * the forgotPwdKeys.
     * </p>
     * <p>
     * Each element in this array map to the element of forgotPwdKeys array at
     * the same position (array index).
     * </p>
     */
    private final String[] requestParamKeys;

    /**
     * <p>
     * Represents an array of keys to store parameter values into a map in order
     * to used in ForgotPassword component.
     * </p>
     * <p>
     * It is initialized in constructor, and never changed afterwards. It is
     * non-null, and must contain at least 1 element, each element in this array
     * must be non-null, non-empty string, and its length must be the same as
     * the requestParamKeys array.
     * </p>
     * <p>
     * Each element in this array map to the element of requestParamKeys array
     * at the same position (array index).
     * </p>
     */
    private final String[] forgotPwdKeys;

    /**
     * <p>
     * Constructor with a map of attributes. It will assign the values of the
     * attrs Map to corresponding instance variables. The attrs Map contains the
     * following key-value mappings, the keys are always String values: (without
     * quotes)
     * <li>1. key- "validation_failed_result_name" value- a non-null, non-empty
     * String value. </li>
     * <li>2. key- "request_param_keys" value- an array of String values, each
     * element must be non-null, non-empty string, it must contain at least 1
     * elements. </li>
     * <li>3. key- "forgot_pwd_keys" value- an array of String values, each
     * element must be non-null, non-empty string, it must contain at least 1
     * elements. </li>
     * <li>4. key- "forgot_pwd" value- a non-null ForgotPassword object.</li>
     * </p>
     * <p>
     * NOTE: the array values are shallowly copied.
     * </p>
     *
     * @param attrs
     *            a map of attributes to initialize this object.
     * @throws IllegalArgumentException
     *             if attrs is null, or any key is missing in attrs Map, or any
     *             value fails the validation constraints, or the length of
     *             request_param_keys value doesn't equal to the length of
     *             forgot_pwd_keys.
     */
    public ForgotPasswordHandler(Map attrs) {
        Helper.checkNull(attrs, "attrs");
        validationFailedResultName = (String) Helper.getAttribute(attrs,
                "validation_failed_result_name", String.class);
        requestParamKeys = (String[]) Helper.getAttribute(attrs,
                "request_param_keys", String[].class);
        forgotPwdKeys = (String[]) Helper.getAttribute(attrs,
                "forgot_pwd_keys", String[].class);
        if (forgotPwdKeys.length != requestParamKeys.length) {
            throw new IllegalArgumentException(
                    "The requestParamKeys number should be equal to forgotPwdKeys number.");
        }
        forgotPwd = (ForgotPassword) Helper.getAttribute(attrs, "forgot_pwd",
                ForgotPassword.class);
    }

    /**
     * <p>
     * Constructor taking an xml Element object. It will extract values from the
     * xml Element to initialize <code>ForgotPasswordHandler</code>.
     * </p>
     * <p>
     *
     * <pre>
     *    Here is an example of the xml element:
     *     &lt;handler type=&quot;x&quot;&gt;
     *         &lt;forgot_password_manager_ns&gt;com.topcoder.security.forgotpassword&lt;/forgot_password_manager_ns&gt;
     *         &lt;forgot_password_name&gt;name1&lt;/forgot_password_name&gt;
     *
     *         &lt;request_param_keys&gt;
     *             &lt;value&gt;emailAddr&lt;/value&gt;
     *             &lt;value&gt;userName&lt;/value&gt;
     *         &lt;/request_param_keys&gt;
     *
     *         &lt;forgot_pwd_keys&gt;
     *             &lt;value&gt;email_addr&lt;/value&gt;
     *             &lt;value&gt;user_name&lt;/value&gt;
     *         &lt;/forgot_pwd_keys&gt;
     *
     *         &lt;failure_result&gt;forgot_password_validation_failed&lt;/failure_result&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * </p>
     *
     *
     * @param handlerElement
     *            the xml Element to extract configured values to initialize
     *            this object.
     * @throws IllegalArgumentException
     *             if the arg is null, or the configured value in the xml
     *             element is invalid. (all values in the xml should follow the
     *             same constraints defined in
     *             {@link ForgotPasswordHandler#ForgotPasswordHandler(Map)}).
     *             Or failed to construct ForgotPasswordManager via
     *             'forgot_password_name'
     */
    public ForgotPasswordHandler(Element handlerElement) {
        Helper.checkNull(handlerElement, "handlerElement");
        // initialize forgotPwd
        String forgotPasswordManagerNamespace = Helper.getValue(handlerElement,
                "/handler/forgot_password_manager_ns");
        String forgotPasswordNameXPath = "/handler/forgot_password_name";
        String forgotPasswordName = Helper.getValue(handlerElement,
                forgotPasswordNameXPath);
        try {
            ForgotPasswordManager fpm = new ForgotPasswordManager(
                    forgotPasswordManagerNamespace);
            this.forgotPwd = fpm.getForgotPassword(forgotPasswordName);
        } catch (ConfigurationException e) {
            throw new IllegalArgumentException(
                    "Failed to create ForgotPasswordManager via value:"
                            + forgotPasswordName + " get in xpath '"
                            + forgotPasswordNameXPath + "'.");
        }

        // initialize requestParamKeys && forgotPwdKeys
        this.requestParamKeys = Helper.getValues(handlerElement,
                "/handler/request_param_keys");
        this.forgotPwdKeys = Helper.getValues(handlerElement,
                "/handler/forgot_pwd_keys");
        if (forgotPwdKeys.length != requestParamKeys.length) {
            throw new IllegalArgumentException(
                    "The requestParamKeys number should be equal to forgotPwdKeys number.");
        }
        this.validationFailedResultName = Helper.getValue(handlerElement,
                "/handler/failure_result");
    }

    /**
     * <p>
     * Process the forgot password request. This method will delegate a
     * <code>Map</code> of 'forgotPwdKeys' to the corresponding request values
     * to {@link ForgotPassword#isValid(Object)} and
     * {@link ForgotPassword#processAction(boolean, Object)}.
     * </p>
     * <p>
     * The returned value will be:
     * <li>If the request parameter values cannot pass the validation done by
     * ForgotPassword component, return a configured result name;</li>
     * <li>If request parameter values pass validation and get processed
     * successfully by ForgotPassword component, return null.</li>
     * </p>
     *
     *
     *
     * @param context
     *            the ActionContext object wrapping the context info.
     * @return a configured result name if the request parameter values cannot
     *         pass the validation done by ForgotPassword component, return null
     *         if request parameter values pass validation and get processed
     *         successfully by ForgotPassword component.
     * @throws IllegalArgumentException
     *             if the context is null.
     * @throws HandlerExecutionException
     *             fail to process the request (used to wrap exceptions from
     *             Forgot Password component).
     */
    public String execute(ActionContext context)
        throws HandlerExecutionException {
        Helper.checkNull(context, "context");
        HttpServletRequest request = context.getRequest();
        // map forgotPwdKeys to parameter-values
        Map map = new HashMap();
        for (int i = 0; i < requestParamKeys.length; i++) {
            map
                    .put(forgotPwdKeys[i], request
                            .getParameter(requestParamKeys[i]));
        }

        // validate values
        if (!forgotPwd.isValid(map)) {
            return this.validationFailedResultName;
        }
        try { // send the map to ForgotPassword component to handle it
            forgotPwd.processAction(true, map);
        } catch (Exception e) {
            throw new HandlerExecutionException(
                    "Failed to process forgot password action.", e);
        }

        return null;
    }
}
