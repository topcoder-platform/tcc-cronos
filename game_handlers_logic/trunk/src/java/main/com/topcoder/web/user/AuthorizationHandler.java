/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.user;

import org.w3c.dom.Element;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * <code>AuthorizationHandler</code> implements the <code>Handler</code>
 * interface from FrontController component. This handler performs lightweight
 * authorization by verifying that a user is logged in to the server in the
 * current session and that his profile contains a particular (configurable)
 * profile type or types. If so, its execute method returns null, which means
 * the authorization succeeds; otherwise it returns a result name string to
 * indicate authorization fails.
 * </p>
 * <p>
 * <strong>Thread safety: </strong> This class is thread-safe, since it is
 * immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class AuthorizationHandler implements Handler {

    /**
     * <p>
     * Represents an array of profile type names that is allowed by this
     * handler. It is used in execute method to check the user-profile's profile
     * type of the logged-in user is one of them. It is initialized in
     * constructor, and never changed afterwards. It is non-null, and must
     * contains at least 1 element, each element in this array must be non-null,
     * non-empty string.
     * </p>
     */
    private final String[] allowedProfileTypes;

    /**
     * <p>
     * Represents the result name to return in execute method if the
     * authorization fails. It is initialized in constructor, and never changed
     * afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String authFailedResultName;

    /**
     * <p>
     * Constructor with allowed-profile-types and auth-failed-result-name.
     * </p>
     * <p>
     * NOTE: the array is shallowly copied.
     * </p>
     *
     * @param allowedProfileTypes
     *            an array of allowed profile type names.
     * @param authFailedResultName
     *            the result name to return if authorization fails.
     * @throws IllegalArgumentException
     *             if any argument is null, or allowedProfileTypes contains null
     *             element or empty string or zero elements, or
     *             authFailedResultName is empty string.
     */
    public AuthorizationHandler(String[] allowedProfileTypes,
            String authFailedResultName) {
        Helper.checkArrayNullEmptyContainsNullEmpty(allowedProfileTypes,
                "allowedProfileTypes");

        this.authFailedResultName = Helper.checkString(authFailedResultName,
                "authFailedResultName");
        this.allowedProfileTypes = Helper.copy(allowedProfileTypes);
    }

    /**
     * <p>
     * Constructor taking an xml Element object, it will extract values from the
     * xml Element, and then assign them to the corresponding instance
     * variables.
     *
     * Here is an example of the xml element:
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *         &lt;allowed_profile_types&gt;
     *             &lt;value&gt;teacher&lt;/value&gt;
     *             &lt;value&gt;student&lt;/value&gt;
     *         &lt;/allowed_profile_types&gt;
     *
     *         &lt;failure_result&gt;auth_failed_result&lt;/failure_result&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     *
     *
     * @param handlerElement
     *            the xml Element to extract configured values to initialize
     *            this object.
     * @throws IllegalArgumentException
     *             if the arg is null, or the configured value in the xml
     *             element is invalid. (all values in the xml should follow the
     *             same constraints defined in
     *             {@link AuthorizationHandler#AuthorizationHandler(String[], String)}).
     */
    public AuthorizationHandler(Element handlerElement) {
        // Assume the current context handler, and xpath is used to reference
        // specific node
        Helper.checkNull(handlerElement, "handlerElement");
        allowedProfileTypes = Helper.getValues(handlerElement,
                "/handler/allowed_profile_types");
        authFailedResultName = Helper.getValue(handlerElement,
                "/handler/failure_result");
    }

    /**
     * <p>
     * Process the authorization request, return null if the authorization
     * succeeds, and return a configured result name if the authorization fails.
     * The user must be logged in. If there is no user log in
     * HandlerExecutionException is thrown. If the user-profile stored in
     * session is one of the specified profile types, return null. Or
     * authFailedResultName configed in this component is returned.
     * </p>
     *
     *
     *
     * @param context
     *            the ActionContext object wrapping the context info.
     * @return null if the authorization succeeds, and return a configured
     *         result name if the authorization fails.
     * @throws IllegalArgumentException
     *             if the context is null.
     * @throws HandlerExecutionException
     *             if the user is not logged in.
     */
    public String execute(ActionContext context)
        throws HandlerExecutionException {
        Helper.checkNull(context, "context");
        UserProfile userProfile = LoginHandler.getAuthenticatedUser(context
                .getRequest().getSession(true));
        if (userProfile == null) {
            throw new HandlerExecutionException(
                    "There is not any authenticated user.");
        }
        for (int i = 0; i < allowedProfileTypes.length; i++) {
            if (userProfile.getProfileType(allowedProfileTypes[i]) != null) {
                return null;
            }
        }

        return authFailedResultName;
    }
}
