/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Element;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.user.profile.manager.UserProfileManagerException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * <code>LoginHandler</code> implements the <code>Handler</code> interface
 * from FrontController component. This handler uses the search condition
 * provided by user request to obtain the appropriate profile from the User
 * Profile Manager. And then check the user's credentials (email address and
 * password, for instance) to determine whether they are consistent with that
 * profile. This handler will
 * <li>Return a configurable result name string if there is no profile obtain
 * using search condition;</li>
 * <li>Throw <code>HandlerExecutionException</code> if there is more then one
 * profile obtain using search condition;</li>
 * <li>Return a configurable result name string if the credentials are not
 * consistent;</li>
 * <li>Return null if the profile is found and the credentials match.</li>
 * The search condition and credentials are both configurable through construction
 * of this handler.
 * </p>
 * <p>
 * This handler also provides a static method: getAuthenticatedUser to get
 * profile of logged-in user from session.
 * </p>
 * <p>
 * Two constructors are provided, one takes a map of configurable attributes,
 * and the other loads the attributes from a xml Element object, refer to
 * {@link LoginHandler#LoginHandler(Element)}'s class doc to see the format of
 * the xml element.
 * </p>
 * <p>
 * <strong>Thread safety: </strong> This class is thread-safe, since it is
 * immutable, and the UserProfileManager object is only used to read UserProfile
 * objects from persistence, concrete UserProfileManager implementation must
 * ensure the search operation is thread-safe. The UserProfile object is stored
 * in session by this handler, and then it can be accessed by the other
 * handlers. Session objects are not necessarily to be thread-safe, and it is up
 * to end-user to not modify it from different threads - This strategy is widely
 * used in web-applications.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class LoginHandler implements Handler {

    /**
     * <p>
     * Represents the session key to get (or store) UserProfile object from (or
     * into) the session (HttpSession object) in getAuthenticated (or execute)
     * method.
     * </p>
     * <p>
     * It is initialized in static initializer, and never changed afterwards. It
     * must be non-null, non-empty string.
     * </p>
     */
    private static final String PROFILE_SESSION_KEY;

    /**
     * <p>
     * Represents the UserProfileManager object used to get the corresponding
     * UserProfile object of search-profile-keys. It is used in execute method
     * to search the matched UserProfile.
     * </p>
     * <p>
     * It is initialized in constructor, and never changed afterwards. Could
     * never be null.
     * </p>
     */
    private final UserProfileManager profileManager;

    /**
     * <p>
     * Represents an array of request parameter keys, it is used to get
     * parameter values from the request (HttpServletRequest object) obtained
     * from the context in execute method. The length of this array equals to
     * searchProfileKeys.length + credentialProfileKeys.length, and the first n
     * (n = searchProfileKeys.length) elements in this array map to the elements
     * in searchProfileKeys one by one, the last m (m =
     * credentialProfileKeys.length) elements in this array map to the elements
     * in credentialProfileKeys one by one. To make it more clear, we use
     * request-param-key to get the parameter value from the request, and then
     * save (or search) in UserProfile with the corresponding profile-key.
     * </p>
     * <p>
     * It is intialized in constructor, and never changed afterwards. It is not
     * null, and must contains at least 2 element, and each element in this
     * array must be non-null, non-empty string.
     * </p>
     */
    private final String[] requestParamKeys;

    /**
     * <p>
     * Represents an array of profile keys used to retrieve UserProfile from
     * UserProfileManager, it is used in execute method. The length of this
     * array must adhere to the following condition: searchProfileKeys.length +
     * credentialProfileKeys.length = requestParamKeys.length The first n (n =
     * searchProfileKeys.length) elements of requestParamKeys map to the
     * elements in this array one by one,
     * </p>
     * <p>
     * It is intialized in constructor, and never changed afterwards. It is not
     * null, and must contains at least 1 element, and each element in this
     * array must be non-null, non-empty string.
     * </p>
     */
    private final String[] searchProfileKeys;

    /**
     * <p>
     * Represents an array of profile keys used to save credential values into
     * UserProfile, it is used in execute method. The length of this array must
     * adhere to the following condition: searchProfileKeys.length +
     * credentialProfileKeys.length = requestParamKeys.length The last m (m =
     * credentialProfileKeys.length) elements of requestParamKeys map to the
     * elements in this array one by one,
     * </p>
     * <p>
     * It is intialized in constructor, and never changed afterwards. It is not
     * null, and must contains at least 1 element, and each element in this
     * array must be non-null, non-empty string.
     * </p>
     */
    private final String[] credentialProfileKeys;

    /**
     * <p>
     * Represents the result name to return in execute method if there is no
     * matching UserProfile in UserProfileManager.
     * <p>
     * <p>
     * It is initialized in constructor, and never changed afterwards. It must
     * be non-null, non-empty string.
     * </p>
     *
     */
    private final String noSuchProfileResultName;

    /**
     * <p>
     * Represents the result name to return in execute method if the credential
     * values in the request is incorrect.
     * </p>
     * <p>
     * It is initialized in constructor, and never changed afterwards. It must
     * be non-null, non-empty string.
     * </p>
     *
     */
    private final String incorrectCredentialResultName;

    /**
     * <p>
     * It is a static block to initialize the PROFILE_SESSION_KEY variable, it
     * tries to load this value from the ConfigManager. If failed to load
     * 'profile_session_key' property under namespace
     * <code>LoginHandler.class.getName()</code>. "user_profile" is used
     * instead.
     * </p>
     */
    static {
        String key = null;
        try { // try to get key from config manager
            key = ConfigManager.getInstance().getString(
                    LoginHandler.class.getName(), "profile_session_key");
            if (key == null || key.trim().length() == 0) {
                key = "user_profile";
            }
        } catch (UnknownNamespaceException e) {
            key = "user_profile";
        }
        PROFILE_SESSION_KEY = key;
    }

    /**
     * <p>
     * Constructor with a map of attributes. The attrs Map contains the
     * following key-value mappings, the keys are always String values: (without
     * quotes)
     * <li>1. key- "profile_manager" value- a non-null UserProfileManager
     * object. </li>
     * <li>2. key- "request_param_keys" value- an array of String values, each
     * element must be non-null, non-empty string, it must contain at least 2
     * elements. </li>
     * <li>3. key- "search_profile_keys" value- an array of String values, each
     * element must be non-null, non-empty string, it must contain at least 1
     * elements. </li>
     * <li>4. key- "credential_profile_keys" value- an array of String values,
     * each element must be non-null, non-empty string, it must contain at least
     * 1 elements. </li>
     * <li>5. key- "no_such_profile_result_name" value- a non-null, non-empty
     * String value. </li>
     * <li>6. key- "incorrect_credential_result_name" value- a non-null,
     * non-empty String value.
     * </p>
     * <p>
     * NOTE: the array values are shallowly copied.
     * </p>
     *
     *
     * @param attrs
     *            a map of attributes assigned to initialize this object.
     * @throws IllegalArgumentException
     *             if attrs is null, or any key is missing in attrs Map, or any
     *             value fails the validation constraints, or the length of
     *             request_param_keys value doesn't equal to the sum of the
     *             length of search_profile_keys value and the length of
     *             credential_profile_keys value.
     */
    public LoginHandler(Map attrs) {
        Helper.checkNull(attrs, "attrs");
        // get parameters
        profileManager = (UserProfileManager) Helper.getAttribute(attrs,
                "profile_manager", UserProfileManager.class);
        requestParamKeys = (String[]) Helper.getAttribute(attrs,
                "request_param_keys", String[].class);
        if (requestParamKeys.length < 2) {
            throw new IllegalArgumentException(
                    "The 'request_param_keys' should contains at least 2 elements.");
        }
        searchProfileKeys = (String[]) Helper.getAttribute(attrs,
                "search_profile_keys", String[].class);
        credentialProfileKeys = (String[]) Helper.getAttribute(attrs,
                "credential_profile_keys", String[].class);
        // check the sum of searchProfileKeys and credentialProfileKeys
        if (requestParamKeys.length != searchProfileKeys.length
                + credentialProfileKeys.length) {
            throw new IllegalArgumentException(
                    "the length of request_param_keys value should equal to the sum of the "
                            + "length of search_profile_keys value and the length of credential_profile_keys value. ");
        }
        noSuchProfileResultName = (String) Helper.getAttribute(attrs,
                "no_such_profile_result_name", String.class);
        incorrectCredentialResultName = (String) Helper.getAttribute(attrs,
                "incorrect_credential_result_name", String.class);
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
     *         &lt;object_factory&gt;
     *             &lt;namespace&gt;com.topcoder.web.user.objectfactory&lt;/namespace&gt;
     *             &lt;user_profile_manager_key&gt;user_profile_manager&lt;/user_profile_manager_key&gt;
     *         &lt;/object_factory&gt;
     *
     *         &lt;request_param_keys&gt;
     *             &lt;value&gt;userName&lt;/value&gt;
     *             &lt;value&gt;password&lt;/value&gt;
     *             &lt;value&gt;emailAddr&lt;/value&gt;
     *         &lt;/request_param_keys&gt;
     *         &lt;profile_keys&gt;
     *             &lt;value type=&quot;search&quot;&gt;user_name&lt;/value&gt;
     *             &lt;value type=&quot;credential&quot;&gt;password&lt;/value&gt;
     *             &lt;value type=&quot;credential&quot;&gt;email_addr&lt;/value&gt;
     *         &lt;/profile_keys&gt;
     *
     *         &lt;failure_results&gt;
     *             &lt;value type=&quot;no_such_profile&quot;&gt;no_matched_profile&lt;/value&gt;
     *             &lt;value type=&quot;incorrect_credential&quot;&gt;incorrect_credential&lt;/value&gt;
     *         &lt;/failure_results&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * </p>
     * <p>
     * Note: The object_factory/namespace property value is used to create a
     * {@link ConfigManagerSpecificationFactory} object, and then create an
     * {@link ObjectFactory} object with
     * {@link ConfigManagerSpecificationFactory} object. use
     * {@link ObjectFactory#createObject} with the
     * object_factory/user_profile_manager_key node value to create a
     * {@link UserProfileManager} object.
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
     *             {@link LoginHandler#LoginHandler(Map)}).
     */
    public LoginHandler(Element handlerElement) {
        Helper.checkNull(handlerElement, "element");
        String ns = Helper.getValue(handlerElement,
                "/handler/object_factory/namespace");
        String key = Helper.getValue(handlerElement,
                "/handler/object_factory/user_profile_manager_key");
        try { // create profileManager via 'ns' and 'key'
            ConfigManagerSpecificationFactory cmsf = new ConfigManagerSpecificationFactory(
                    ns);
            this.profileManager = (UserProfileManager) new ObjectFactory(cmsf)
                    .createObject(key);
        } catch (SpecificationConfigurationException e) {
            throw new IllegalArgumentException(
                    "Failed to create ConfigManagerSpecificationFactory, caused by "
                            + e.getMessage());
        } catch (IllegalReferenceException e) {
            throw new IllegalArgumentException(
                    "Failed to create ConfigManagerSpecificationFactory, caused by "
                            + e.getMessage());
        } catch (InvalidClassSpecificationException e) {
            throw new IllegalArgumentException(
                    "Failed to create ProfileManager, caused by "
                            + e.getMessage());
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(
                    "Failed to create ProfileManager, The key:" + key
                            + " defined in object factory is"
                            + "not type of ProfileManager.");
        }

        this.requestParamKeys = Helper.getValues(handlerElement,
                "/handler/request_param_keys");
        this.searchProfileKeys = Helper.getValues(handlerElement,
                "/handler/profile_keys", "search");
        this.credentialProfileKeys = Helper.getValues(handlerElement,
                "/handler/profile_keys", "credential");
        if (requestParamKeys.length != searchProfileKeys.length
                + credentialProfileKeys.length) {
            throw new IllegalArgumentException(
                    "the length of request_param_keys value should equal to the sum of the "
                            + "length of search_profile_keys value and the length of credential_profile_keys value. ");
        }
        // Only the first incorrect_credential && no_such_profile is loaded
        this.incorrectCredentialResultName = Helper.getValues(handlerElement,
                "/handler/failure_results", "incorrect_credential")[0];
        this.noSuchProfileResultName = Helper.getValues(handlerElement,
                "/handler/failure_results", "no_such_profile")[0];
    }

    /**
     * <p>
     * Process the login request and respond accordingly. This method will:
     * </p>
     * <p>
     * <ul>
     * <li>Return a configurable result name string if there is no profile
     * obtain using search condition;</li>
     * <li>Throw <code>HandlerExecutionException</code> if there is more then
     * one profile obtain using search condition;</li>
     * <li>Return a configurable result name string if the credentials are not
     * consistent;</li>
     * <li>Return null if the profile is found and the credentials match.</li>
     * </ul>
     * </p>
     * <p>
     * If all credential values are matched, this method will invalidate the
     * session and then save the UserProfile object into the session (obtained
     * from
     * <code>ActionContext#getRequest()#getSession(true)</code>) with the profileSessionKey.
     * </p>
     * <p>
     * Note: credential match. If the credential value of user profile is null.
     * The credential value of request should also be null to match it.(Although
     * this condition will never happen, the strategy is provided.)
     * </p>
     *
     *
     * @param context
     *            the ActionContext object wrapping the context info.
     * @return null if the user logs in correctly, otherwise return proper
     *         configured result name.
     * @throws IllegalArgumentException
     *             if the context is null.
     * @throws HandlerExecutionException
     *             if fail to execute this handler (e.g. to wrap exceptions).
     */
    public String execute(ActionContext context)
        throws HandlerExecutionException {
        Helper.checkNull(context, "context");
        HttpServletRequest request = context.getRequest();
        // map search profile key to parameter-values
        Map searchAttrs = new HashMap();
        UserProfile userProfile = null;
        // the index used to get searchProfileKeys and credentialProfileKeys
        int i = 0;
        for (; i < this.searchProfileKeys.length; i++) {
            searchAttrs.put(this.searchProfileKeys[i], request
                    .getParameter(this.requestParamKeys[i]));
        }
        // get matched UserProfile(s)
        try {
            UserProfile[] userProfiles = profileManager
                    .searchUserProfiles(searchAttrs);
            if (userProfiles.length == 0) {
                return this.noSuchProfileResultName;
            }
            if (userProfiles.length > 1) {
                throw new HandlerExecutionException(
                        "The UserProfile corresponding to the search condition is not unique.");
            }
            userProfile = userProfiles[0];
        } catch (UserProfileManagerException e) {
            throw new HandlerExecutionException(
                    "Failed to retrieve UserProfile, caused by "
                            + e.getMessage(), e);
        }

        return vaildateUserProfile(userProfile, i, request);
    }

    /**
     * Gets the last m parameter values (m = the number of keys in
     * credentialProfileKeys), and then match them to the corresponding value in
     * UserProfile object with the credential-profile-keys. If all credential
     * values are matched, invalidate the session and then save the UserProfile
     * object into the session (obtained from
     * context#getRequest()#getSession(true)) with the profileSessionKey, and
     * finally return null. If any credential value is not matched, return
     * incorrectCredentialResultName.
     *
     * @param userProfile
     *            the UserProfile to match
     * @param index
     *            the index of first m parameter values
     * @param request
     *            the user request
     * @return incorrectCredentialResultName if not match, or return null.
     */
    private String vaildateUserProfile(UserProfile userProfile, int index,
            HttpServletRequest request) {
        for (int i = 0; i < this.credentialProfileKeys.length; i++) {
            String requestValue = request
                    .getParameter(requestParamKeys[index++]);
            Object expectedValue = userProfile
                    .getProperty(credentialProfileKeys[i]);
            if (expectedValue == null && requestValue == null) {
                // both are null
                continue;
            }
            if (expectedValue != null && expectedValue.equals(requestValue)) {
                // non-null and equal
                continue;
            }
            return incorrectCredentialResultName;
        }
        request.getSession(true).invalidate();
        request.getSession(true).setAttribute(PROFILE_SESSION_KEY, userProfile);
        return null;
    }

    /**
     * <p>
     * Returns a UserProfile representing the user, if any, that has
     * successfully logged in the context of the specified HTTP session, and
     * returns null if the UserProfile object is not present in session.
     * </p>
     *
     *
     * @param session
     *            the HttpSession object.
     * @return the UserProfile object stored in session, or null if it is not
     *         present.
     * @throws IllegalArgumentException
     *             if the session is null.
     */
    public static UserProfile getAuthenticatedUser(HttpSession session) {
        Helper.checkNull(session, "session");
        Object obj = session.getAttribute(PROFILE_SESSION_KEY);
        return (UserProfile) ((obj instanceof UserProfile) ? obj : null);
    }
}
