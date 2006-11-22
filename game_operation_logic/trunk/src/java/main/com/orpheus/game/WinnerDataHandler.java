/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.user.profile.DuplicatePropertyValidatorException;
import com.topcoder.user.profile.InvalidKeyException;
import com.topcoder.user.profile.InvalidValueException;
import com.topcoder.user.profile.ProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.DuplicateProfileException;
import com.topcoder.user.profile.manager.ProfileTypeFactory;
import com.topcoder.user.profile.manager.UnknownProfileTypeException;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.user.profile.manager.UserProfileManagerException;

import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;


/**
 * A Handler that records contact information collected from players when they win the game. Information will be stored
 * in the winner¡¯s User Profile, and recorded using the User Profile Manager. The handler¡¯s configuration will specify
 * profile types that the handler must ensure are present in the profile, and will specify a map from parameter names
 * to corresponding user profile property names. The Handler will install the profile type(s) if necessary, then copy
 * the parameter values to the profile and store it. This class is thread safe since it does not contain any mutable
 * state.
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class WinnerDataHandler implements Handler {
    /**
     * a map from parameter names to corresponding user profile property names, string property name as key and string
     * parameter name as value.
     */
    private final Map propertyNameMap;

    /** ProfileTypeFactory is used to get the ProfileType by the profile type name. */
    private final ProfileTypeFactory profileTypeFactory;

    /** UserProfileManager instance used to store the UserProfile instance. */
    private final UserProfileManager profileManager;

    /** An array of profile type names. The name is used to get the ProfileType from the ProfileTypeFactory. */
    private final String[] profileTypeNames;

    /**
     * Create the instance with give arguments.
     *
     * @param manager the user profile manager
     * @param names the profile type names
     * @param propertyNameMap the property name map, string property name as key and string parameter name as value
     * @param factory the profile type factory namespace
     *
     * @throws IllegalArgumentException if any argument is null or empty, or invalid(see the variable document for the
     *         valid values)
     */
    public WinnerDataHandler(UserProfileManager manager, String[] names, Map propertyNameMap,
            ProfileTypeFactory factory) {
        ParameterCheck.checkNull("manager", manager);
        ParameterCheck.checkEmptyArray("names", names);
        ParameterCheck.checkEmptyMap("propertyNameMap", propertyNameMap);
        ParameterCheck.checkNull("factory", factory);

        this.profileTypeNames = new String[names.length];
        System.arraycopy(names, 0, this.profileTypeNames, 0, names.length);
        this.profileManager = manager;
        this.propertyNameMap = new HashMap(propertyNameMap);
        this.profileTypeFactory = factory;
    }

    /**
     * Create the instance from element.
     * <pre>Follow is a sample xml:
     *  &lt;handler type=&quot;x&quot; &gt;
     *  &lt;object_factory&gt;
     *  &lt;namespace&gt;com.orpheus.game&lt;/namespace&gt;
     *  &lt;user_profile_manager_key&gt;UserProfileManager&lt;/user_profile_manager_key&gt;
     *  &lt;profile_type_factory_key&gt; ProfileTypeFactory&lt;/profile_type_factory_key&gt;
     *  &lt;/object_factory&gt;
     *  &lt;profile_type_names&gt;
     *  &lt;value&gt;typeA&lt;/value&gt;
     *  &lt;value&gt;typeB&lt;/value&gt;
     *  &lt;/profile_type_names&gt;
     *  &lt;profile_property_param_names&gt;
     *  &lt;value&gt;firstName&lt;/value&gt;
     *  &lt;value&gt;email&lt;/value&gt;
     *  &lt;/profile_property_param_names&gt;
     *  &lt;profile_property_names&gt;
     *  &lt;value&gt;first_name&lt;/value&gt;
     *  &lt;value&gt;email_address&lt;/value&gt;
     *  &lt;/profile_property_names&gt;
     *  &lt;/handler&gt;
     *  Following is simple explanation of the above XML structure.
     *  The handler¡¯s type attribute is required by Front Controller component, it won¡¯t be used in this design.
     *  The object_factory node contains the values to create the UserProfileManager and ProfileTypeFactory
     *  from ObjectFactory.
     *  The profile_type_names node contains the profile type names that will be added to the UserProfile
     *  The profile_property_param_names node contains the http request parameter names used to get the property value.
     *  The profile_property_names node contains the profile property names.
     *  Please note that the profile_property_param_names must have the same number of children value nodes as the
     *  profile_property_names.
     *  </pre>
     *
     * @param element the xml element
     *
     * @throws IllegalArgumentException if element is null or invalid, or failed to create the profile manager
     *         instance.
     */
    public WinnerDataHandler(Element element) {
        ParameterCheck.checkNull("element", element);

        String namespace = XMLHelper.getNodeValue(element, "object_factory.namespace", true);
        String managerKey = XMLHelper.getNodeValue(element, "object_factory.user_profile_manager_key", true);
        String factoryKey = XMLHelper.getNodeValue(element, "object_factory.profile_type_factory_key", true);

        try {
            ObjectFactory factory = new ObjectFactory(new ConfigManagerSpecificationFactory(namespace));
            this.profileManager = (UserProfileManager) factory.createObject(managerKey);
            this.profileTypeFactory = (ProfileTypeFactory) factory.createObject(factoryKey);
        } catch (SpecificationConfigurationException e) {
            throw new IllegalArgumentException("can not create ObjectFactory with namespace:" + namespace + " " + e);
        } catch (IllegalReferenceException e) {
            throw new IllegalArgumentException("can not create ObjectFactory with namespace:" + namespace + " " + e);
        } catch (InvalidClassSpecificationException e) {
            throw new IllegalArgumentException("can not create Object with key:" + managerKey + " " + e);
        }

        this.profileTypeNames = XMLHelper.getNodeValues(element, "profile_type_names.value");

        String[] paramNames = XMLHelper.getNodeValues(element, "profile_property_param_names.value");
        String[] propNames = XMLHelper.getNodeValues(element, "profile_property_names.value");

        if (paramNames.length != propNames.length) {
            throw new IllegalArgumentException(
                "node handler.profile_property_param_names and handler.profile_property_names must have the same "
                + "number of elements");
        }

        Map propertyMap = new HashMap(paramNames.length);

        for (int i = 0; i < paramNames.length; i++) {
            propertyMap.put(propNames[i], paramNames[i]);
        }

        this.propertyNameMap = propertyMap;
    }

    /**
     * Records contact information collected from players when they win the game.
     *
     * @param context the action context
     *
     * @return null always
     *
     * @throws HandlerExecutionException if any other error ocurred
     * @throws IllegalArgumentException if the context is null
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ParameterCheck.checkNull("context", context);

        UserProfile profile;

        try {
            profile = new UserProfile();

            for (int i = 0; i < this.profileTypeNames.length; i++) {
                ProfileType type = profileTypeFactory.getProfileType(profileTypeNames[i]);
                profile.addProfileType(type);
            }

            Iterator iter = this.propertyNameMap.entrySet().iterator();
            Entry entry = null;
            String propertyName;
            String[] propertyValues;
            HttpServletRequest request = context.getRequest();

            while (iter.hasNext()) {
                entry = (Entry) iter.next();
                propertyName = (String) entry.getKey();
                propertyValues = request.getParameterValues((String) entry.getValue());

                if (propertyValues.length == 1) {
                    profile.setProperty(propertyName, propertyValues[0]);
                } else {
                    profile.setProperty(propertyName, propertyValues);
                }
            }

            profileManager.createUserProfile(profile);
        } catch (ConfigManagerException e) {
            throw new HandlerExecutionException("error occurred while creating UserProfile", e);
        } catch (IDGenerationException e) {
            throw new HandlerExecutionException("error occurred while creating UserProfile", e);
        } catch (UnknownProfileTypeException e) {
            throw new HandlerExecutionException("error occurred while obtaining ProfileType", e);
        } catch (DuplicatePropertyValidatorException e) {
            throw new HandlerExecutionException("duplicated property is added", e);
        } catch (InvalidKeyException e) {
            throw new HandlerExecutionException("error occurred while setting profile property", e);
        } catch (InvalidValueException e) {
            throw new HandlerExecutionException("error occurred while setting profile property", e);
        } catch (DuplicateProfileException e) {
            throw new HandlerExecutionException("error occurred while creating a UserProfile", e);
        } catch (UserProfileManagerException e) {
            throw new HandlerExecutionException("error occurred in UserProfile component", e);
        }

        return null;
    }
}
