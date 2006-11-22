/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.user;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Element;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * <code>WelcomePageHandler</code> implements the <code>Handler</code>
 * interface from FrontController component. This handler is particularly
 * intended to support directing users to the correct welcome page after they
 * log in, but could have other uses as well. Its execute method returns a
 * result name characteristic of the profile type of user that is logged in, or
 * null if no user is logged in or if there is no result configured for the
 * user's profile type(s).
 * </p>
 * <p>
 * NOTE: each user is expected to have only one profile-type (UserProfile object
 * supports more than 1 profile-types) to make sure its behaviour is
 * predictable. Anyway, if there are more then one profile-types, only one of
 * their type's result name is returned which lead to a un-predictable behavior.
 * </p>
 * <p>
 * <strong>Thread-safety: </strong>This class is thread-safe since it is
 * immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class WelcomePageHandler implements Handler {

    /**
     * <p>
     * Represents a map of profile type names to result names. The keys are
     * String objects, and the values are also String objects, both keys and
     * values must be non-null, non-empty string. It is used in execute to find
     * the corresponding result name by the profile type name.
     * </p>
     * <p>
     * It is initialized and populated in constructor, and never changed
     * afterwards. It is non-null and must contain at least 1 element after
     * initialization.
     * </p>
     *
     */
    private final Map profileTypeResultMappings;

    /**
     * <p>
     * Constructor with a map of profile type names to result names.
     * </p>
     * <p>
     * NOTE: the map content is shallowly copied.
     * </p>
     *
     *
     * @param profileTypeResultMappings
     *            a map of profile type names to result names.
     * @throws IllegalArgumentException
     *             if the profileTypeResultMappings map is null, or the map
     *             contains 0 elements, or any of the keys/values of the map is
     *             null object or empty string.
     */
    public WelcomePageHandler(Map profileTypeResultMappings) {
        Helper
                .checkNull(profileTypeResultMappings,
                        "profileTypeResultMappings");
        if (profileTypeResultMappings.size() == 0) {
            throw new IllegalArgumentException(
                    "The 'profileTypeResultMappings' should contain more than 0 element.");
        }
        for (Iterator iter = profileTypeResultMappings.entrySet().iterator(); iter
                .hasNext();) {
            Entry entry = (Entry) iter.next();
            try {
                Helper.checkString((String) entry.getKey(),
                        "the key of profileTypeResultMappings");
                Helper.checkString((String) entry.getValue(),
                        "the value of profileTypeResultMappings");
            } catch (ClassCastException e) {
                throw new IllegalArgumentException(
                        "The key/value of 'profileTypeResultMappings' should be String.");
            }
        }

        this.profileTypeResultMappings = new HashMap(profileTypeResultMappings);
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
     *         &lt;profile_types&gt;
     *             &lt;value&gt;teacher&lt;/value&gt;
     *             &lt;value&gt;student&lt;/value&gt;
     *         &lt;/profile_types&gt;
     *
     *         &lt;results&gt;
     *             &lt;value&gt;teacher_result&lt;/value&gt;
     *             &lt;value&gt;student_result&lt;/value&gt;
     *         &lt;/results&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * </p>
     *
     * <p>
     * NOTE: the number of profile_types should be as same as number of results.
     * </p>
     *
     *
     * @param handlerElement
     *            the xml Element to extract configured values to initialize
     *            this object.
     * @throws IllegalArgumentException
     *             if the arg is null, or the configured value in the xml
     *             element is invalid. (all values in the xml should follow the
     *             same constraints defined in the
     *             {@link WelcomePageHandler#WelcomePageHandler(Map)}).
     */
    public WelcomePageHandler(Element handlerElement) {
        Helper.checkNull(handlerElement, "handlerElement");
        String[] profileTypes = Helper.getValues(handlerElement,
                "/handler/profile_types");
        String[] results = Helper.getValues(handlerElement, "/handler/results");
        if (profileTypes.length != results.length) {
            // the length of profileTypes and results should be the same
            throw new IllegalArgumentException(
                    "The number of profile_types's value should equal to the number of results's value.");
        }
        this.profileTypeResultMappings = new HashMap();
        for (int i = 0; i < results.length; i++) {
            this.profileTypeResultMappings.put(profileTypes[i], results[i]);
        }
    }

    /**
     * <p>
     * Process the welcome page request, return null if the user is not logged
     * in or there is no result configured this user's profile type, otherwise
     * it returns a corresponding result name its profile-type.
     * </p>
     *
     *
     * @param context
     *            the ActionContext object wrapping the context info.
     * @return null if the user is not logged in or there is no result
     *         configured this user's profile type, otherwise it returns a
     *         corresponding result name its profile-type.
     * @throws IllegalArgumentException
     *             if the context is null.
     * @throws HandlerExecutionException
     *             this exception is never thrown in this method.
     */
    public String execute(ActionContext context) {
        Helper.checkNull(context, "context");
        UserProfile userProfile = LoginHandler.getAuthenticatedUser(context
                .getRequest().getSession(true));
        if (userProfile == null) {
            return null;
        }
        for (Iterator iter = this.profileTypeResultMappings.entrySet()
                .iterator(); iter.hasNext();) {
            Entry entry = (Entry) iter.next();
            if (userProfile.getProfileType((String) entry.getKey()) != null) {
                // if userProfile is type of entry.getKey()
                // only the first profile type meet is returned
                return (String) entry.getValue();
            }
        }
        return null;
    }
}
