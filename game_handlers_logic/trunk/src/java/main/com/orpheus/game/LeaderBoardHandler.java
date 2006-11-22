/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.util.Map;

import org.w3c.dom.Element;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UnknownProfileException;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.user.profile.manager.UserProfileManagerException;
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
 * <code>LeaderBoardHandler</code> class implements the <code>Handler</code> interface from the
 * <code>FrontController</code> component. It determines the current leaders in a specified game, creates an array
 * containing them (in order), and assigns the array to a request attribute of configurable name. The maximum number of
 * leaders to rank will be set as a configuration parameter. The leader ranking will be determined according to the
 * following criteria, listed from highest priority to lowest:
 * <ol>
 * <li> Proximity to the current host. Players who have completed slots that more recently hosted are ranked before
 * players who have completed only slots that hosted further in the past.</li>
 * <li> Contiguous slots completed. Players who have completed a longer contiguous sequence of slots culminating in the
 * last slot they completed are ranked before players with a shorted sequences.</li>
 * <li> Most recent completion timestamp. Players who completed their most recent slot earlier are ranked before those
 * who completed their most recent slot later Players who have not completed any slots are not ranked, finally an array
 * of UserProfile objects will be saved to request attributes Map with configurable key. </li>
 * </ol>
 * To follow the Handler implementation's contract in order to make it work properly with <code>FrontController</code>
 * component, two constructors are defined, one takes the configurable parameters, and the other takes an
 * <code>Element</code> object which contains all configurable parameters.
 *
 * Thread-safety: This class is immutable and thread-safe. Also the invocations to UserProfileManager is implemented in
 * thread-safe manner.
 * </p>
 *
 * @author Standlove, mittu
 * @version 1.0
 */
public class LeaderBoardHandler implements Handler {

    /**
     * <p>
     * Represents the key used to get the game id from the request parameter in execute method. It is initialized in
     * constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String gameIdParamKey;

    /**
     * <p>
     * Represents the key used to store the an array of <code>UserProfile</code> object into request's attribute Map
     * in execute method. It is initialized in constructor and never changed afterwards. It must be non-null, non-empty
     * string.
     * </p>
     */
    private final String profilesKey;

    /**
     * <p>
     * Represents the maximum players to return on the leader board of a specified game. Used in execute method. It is
     * initialized in constructor and never changed afterwards. It must be positive integer value.
     * </p>
     */
    private final int maxLeaders;

    /**
     * <p>
     * Represents the <code>UserProfileManager</code> object used to retrieve the <code>UserProfile</code> object by
     * profile id in execute method. It is initialized in constructor and never changed afterwards. It must be non-null
     * always.
     * </p>
     */
    private final UserProfileManager profileManager;

    /**
     * <p>
     * Constructor with a Map of attributes, this constructor will extract values from the attributes Map, and then
     * assign to corresponding instance variable. The map should have keys gameIdParamKey, profilesKey, maxLeaders and
     * profileManager with valid values.
     *
     * @param attributes
     *            a Map of attributes to initialize this handler.
     * @throws IllegalArgumentException
     *             if the argument is null or any value above is missing or doesn't meet the condition specified in
     *             implementation note above.
     */
    public LeaderBoardHandler(Map attributes) {
        ImplementationHelper.checkObjectValid(attributes, "attributes");
        profileManager = getProfileManager(attributes, "profileManager");
        gameIdParamKey = ImplementationHelper.getString(attributes, "gameIdParamKey");
        maxLeaders = getInt(attributes.get("maxLeaders"));
        profilesKey = ImplementationHelper.getString(attributes, "profilesKey");
    }

    /**
     * <p>
     * Constructor with an xml Element object. The constructor will extract necessary configurable values from this xml
     * element.
     *
     * Here is what the xml element likes:
     *
     * <pre>
     *    &lt;handler type=&quot;x&quot;&gt;
     *     &lt;game_id_param_key&gt;gameId&lt;/game_id_param_key&gt;
     *     &lt;profiles_key&gt;gameId&lt;/profiles_key&gt;
     *     &lt;max_leaders&gt;10&lt;/max_leaders&gt;
     *     &lt;object_factory_namespace&gt;com.topcoder.web.user&lt;/object_factory_namespace&gt;
     *     &lt;user_profile_manager_token&gt;user_profile_manager&lt;/user_profile_manager_token&gt;
     *    &lt;/handler&gt;
     * </pre>
     *
     * <code>UserProfileManager</code> is created using the object_factory_namespace and user_profile_manager_token
     * using <code>ObjectFactory</code>.
     * </p>
     *
     * @param element
     *            the xml Element to extract the configurable values.
     * @throws IllegalArgumentException
     *             if the argument is null, or any element mentioned above is missing, or the
     *             game_id_param_key/profiles_key/object_factory_namespace/user_profile_manager_token node values are
     *             empty string, or max_leaders node value cannot be converted to a non-positive int value, or we fail
     *             to create the UserProfileManager object using the ObjectFactory.
     */
    public LeaderBoardHandler(Element element) {
        ImplementationHelper.checkObjectValid(element, "element");
        profileManager = createProfileManager(element, "object_factory_namespace",
                "user_profile_manager_token");
        gameIdParamKey = ImplementationHelper.getElement(element, "game_id_param_key");
        maxLeaders = getInt(ImplementationHelper.getElement(element, "max_leaders"));
        profilesKey = ImplementationHelper.getElement(element, "profiles_key");
    }

    /**
     * <p>
     * It determines the current leaders in a specified game, creates an array containing them (in order), and assigns
     * the array to a request attribute of configurable name. The maximum number of leaders to rank will be set as a
     * configuration parameter. This method will return null always if no exception occurs.
     * </p>
     *
     * @param context
     *            the ActionContext object.
     * @return null always.
     * @throws IllegalArgumentException
     *             if the given argument is null.
     * @throws HandlerExecutionException
     *             if the game id in request parameter is missing or invalid, or the GameDataHelper operation fails, or
     *             there is no corresponding UserProfile for the player on the leader board.
     *
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ImplementationHelper.checkObjectValid(context, "context");
        GameDataHelper helper = GameDataHelper.getInstance();
        try {
            // parses the game id from the request parameter.
            long gameId = Long.parseLong(context.getRequest().getParameter(gameIdParamKey));
            // get the leading profiles using helper.
            long[] profileIds = helper.getLeaderBoard(gameId, maxLeaders);
            UserProfile[] profiles = new UserProfile[profileIds.length];
            for (int i = 0; i < profileIds.length; i++) {
                // gets the profiles for leaders.
                profiles[i] = profileManager.getUserProfile(profileIds[i]);
            }
            // stores the profiles array in request attribute.
            context.getRequest().setAttribute(profilesKey, profiles);
        } catch (NumberFormatException formatException) {
            throw new HandlerExecutionException("Failed to execute leader board handler.", formatException);
        } catch (UnknownProfileException unknownProfileException) {
            throw new HandlerExecutionException("Failed to execute leader board handler.", unknownProfileException);
        } catch (UserProfileManagerException userProfileManagerException) {
            throw new HandlerExecutionException("Failed to execute leader board handler.", userProfileManagerException);
        }
        return null;
    }

    /**
     * Creates an object from the given element using the key. It uses <code>ObjectFactory</code> for object creation.
     *
     * @param element
     *            the xml element
     * @param nspace
     *            the object factory namespace
     * @param token
     *            the token to search
     * @return the object
     * @throws IllegalArgumentException
     *             if invalid data is present
     */
    private UserProfileManager createProfileManager(Element element, String nspace, String token) {
        String classkey = ImplementationHelper.getElement(element, token);
        String namespace = ImplementationHelper.getElement(element, nspace);
        try {
            ConfigManagerSpecificationFactory factory = new ConfigManagerSpecificationFactory(namespace);
            ObjectFactory objectFactory = new ObjectFactory(factory, ObjectFactory.BOTH);
            return (UserProfileManager) objectFactory.createObject(classkey);
        } catch (SpecificationConfigurationException exception) {
            throw new IllegalArgumentException("Failed to create the object using object factory."
                    + exception.getMessage());
        } catch (IllegalReferenceException exception) {
            throw new IllegalArgumentException("Failed to create the object using object factory."
                    + exception.getMessage());
        } catch (InvalidClassSpecificationException exception) {
            throw new IllegalArgumentException("Failed to create the object using object factory."
                    + exception.getMessage());
        } catch (ClassCastException castException) {
            throw new IllegalArgumentException("Failed to create the object using object factory."
                    + castException.getMessage());
        }
    }

    /**
     * <p>
     * Gets the int for the given object. Also it checks the number is greater than zero.
     * </p>
     *
     * @param object
     *            the object from which in value should be obtained.
     * @return the converted int.
     * @throws IllegalArgumentException
     *             if any error occurs.
     */
    private int getInt(Object object) {
        try {
            ImplementationHelper.checkObjectValid(object, "maxLeaders");
            return getInt(((Integer) object).toString());
        } catch (ClassCastException castException) {
            throw new IllegalArgumentException("Invalid value for 'maxLeaders'. " + castException.getMessage());
        }
    }

    /**
     * <p>
     * Gets the int for the given string. Also it checks the number is greater than zero.
     * </p>
     *
     * @param value
     *            the value to convert.
     * @return the converted int
     * @throws IllegalArgumentException
     *             if any error occurs.
     */
    private int getInt(String value) {
        try {
            int num = Integer.parseInt(value);
            if (num <= 0) {
                throw new IllegalArgumentException("Invalid value for 'maxLeaders'. Number is negative.");
            }
            return num;
        } catch (NumberFormatException formatException) {
            throw new IllegalArgumentException("Invalid value for 'maxLeaders'. " + formatException.getMessage());
        }
    }

    /**
     * <p>
     * Fetches the <code>UserProfileManager</code> from the map.
     * </p>
     *
     * @param map
     *            the map to get the data.
     * @param key
     *            the key to be used
     * @return the UserProfileManager
     * @throws IllegalArgumentException
     *             if any validation error happens.
     */
    private UserProfileManager getProfileManager(Map map, String key) {
        try {
            UserProfileManager userProfileManager = (UserProfileManager) map.get(key);
            ImplementationHelper.checkObjectValid(userProfileManager, key);
            return userProfileManager;
        } catch (ClassCastException classCastException) {
            throw new IllegalArgumentException("Invalid value for '" + key + "'. " + classCastException.getMessage());
        }
    }

}
