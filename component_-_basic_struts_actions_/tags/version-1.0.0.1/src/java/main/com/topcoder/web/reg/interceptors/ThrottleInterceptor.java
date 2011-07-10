/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.interceptors;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.error.RequestRateExceededException;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.common.throttle.Throttle;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;

/**
 * <p>
 * This interceptor is responsible for checking if a user hits the site too frequently and should be throttled
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe because it's mutable. However, dedicated instance of struts
 * interceptor will be created by the struts framework to process the user request, so the struts interceptor
 * don't need to be thread-safe.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class ThrottleInterceptor extends BaseInterceptor {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = ThrottleInterceptor.class.getName();

    /**
     * <p>
     * The Log object used for logging. It's a constant. So it can only be that constant value It is final and
     * won't change once it is initialized as part of variable declaration to:
     * LogManager.getLog(ThrottleInterceptor.class.toString());. It is used throughout the class wherever
     * logging is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(ThrottleInterceptor.class.toString());;

    /**
     * <p>
     * The flag indicating whether the throttle checking is enabled. It is set through setter and doesn't have
     * a getter. Not Applicable It can be changed after it is initialized as part of variable declaration to:
     * false. It is used in setThrottleEnabled(), intercept().
     * </p>
     */
    private boolean throttleEnabled = false;

    /**
     * <p>
     * Maximum number of hits a user can do within an interval before they start getting throttled. It is set
     * through setter and doesn't have a getter. It must be non-negative. (Note that the above statement
     * applies only after the setter has been called as part of the IoC initialization. This field's value has
     * no restriction before the IoC initialization) It can be changed after it is initialized as part of
     * variable declaration to: 10. It is used in setThrottleMaxHits(), intercept(). Its value legality is
     * checked in checkConfiguration() method.
     * </p>
     */
    private int throttleMaxHits = 10;

    /**
     * <p>
     * Length of a throttle interval in milliseconds. It is set through setter and doesn't have a getter. It
     * must be non-negative. (Note that the above statement applies only after the setter has been called as
     * part of the IoC initialization. This field's value has no restriction before the IoC initialization) It
     * can be changed after it is initialized as part of variable declaration to: 5000. It is used in
     * intercept(), setThrottleInterval(). Its value legality is checked in checkConfiguration() method.
     * </p>
     */
    private int throttleInterval = 5000;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public ThrottleInterceptor() {
        // Empty
    }

    /**
     * <p>
     * Intercept the request to check if the user should be throttled.
     * </p>
     *
     * @param actionInvocation
     *            the action invocation to intercept
     * @return whatever the downstream action returns
     * @throws IllegalArgumentException
     *             if actionInvocation is null
     * @throws RequestRateExceededException
     *             if the user is throttled for generating too many hits
     * @throws Exception
     *             if any other error occurs
     */
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        // Log the entry
        final String signature = CLASS_NAME + ".intercept(ActionInvocation)";
        LoggingWrapperUtility.logEntrance(LOG, signature, new String[] {"actionInvocation"},
            new Object[] {actionInvocation});

        try {
            ParameterCheckUtility.checkNotNull(actionInvocation, "actionInvocation");

            if (throttleEnabled) {
                // Create a new Throttle:
                Throttle throttle = new Throttle(throttleMaxHits, throttleInterval);
                HttpSession session = ServletActionContext.getRequest().getSession();
                // If throttle.throttle(session.getId()), which means the user is throttled
                if (throttle.throttle(session.getId())) {
                    WebAuthentication authentication = this.createWebAuthentication();
                    throw new RequestRateExceededException(session.getId(), authentication.getActiveUser()
                                    .getUserName());
                }
            }
            // Invoke the downstream action:
            String result = actionInvocation.invoke();

            // Log the exit
            LoggingWrapperUtility.logExit(LOG, signature, new Object[] {result});

            return result;
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(LOG, signature, e);
        }

    }

    /**
     * <p>
     * Setter method for the throttleEnabled, simply set the value to the namesake field.
     * </p>
     *
     * @param throttleEnabled
     *            The flag indicating whether the throttle checking is enabled
     */
    public void setThrottleEnabled(boolean throttleEnabled) {
        this.throttleEnabled = throttleEnabled;
    }

    /**
     * <p>
     * Setter method for the throttleMaxHits, simply set the value to the namesake field.
     * </p>
     *
     * @param throttleMaxHits
     *            Maximum number of hits a user can do within an interval before they start getting throttled
     */
    public void setThrottleMaxHits(int throttleMaxHits) {
        this.throttleMaxHits = throttleMaxHits;
    }

    /**
     * <p>
     * Setter method for the throttleInterval, simply set the value to the namesake field.
     * </p>
     *
     * @param throttleInterval
     *            Length of a throttle interval in milliseconds
     */
    public void setThrottleInterval(int throttleInterval) {
        this.throttleInterval = throttleInterval;
    }

    /**
     * <p>
     * This method is called right after the dependency of this class is fully injected. It checks if the
     * injected values are valid.
     * </p>
     *
     * @throws BasicStrutsActionsConfigurationException
     *             if any of the injected values is invalid.
     */
    public void checkConfiguration() {
        // Check the value of the following fields according to their legal value specification in the field
        // variable documentation:
        ValidationUtility.checkNotNegative(throttleMaxHits, "throttleMaxHits",
            BasicStrutsActionsConfigurationException.class);
        ValidationUtility.checkNotNegative(throttleInterval, "throttleInterval",
            BasicStrutsActionsConfigurationException.class);
    }
}
