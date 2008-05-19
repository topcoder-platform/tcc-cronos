/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.converter;

import java.util.Date;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.Role;
import com.topcoder.registration.entities.User;
import com.topcoder.registration.persistence.ArgumentChecker;
import com.topcoder.registration.persistence.RegistrationEntitiesToResourceConversionException;
import com.topcoder.registration.persistence.RegistrationEntitiesToResourceConverter;


/**
 * <p>
 * This class is the default implementation of the <code>RegistrationEntitiesToResourceConverter</code> interface.
 * It has a method that will create a <code>Resource</code> object, set its fields using the parameters and then
 * return the <code>Resource</code> object.
 * </p>
 *
 * <p>
 *    <strong>Thread Safety:</strong>
 *    This class is thread safe because it has no state.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
*/
public class RegistrationEntitiesToResourceConverterImpl implements RegistrationEntitiesToResourceConverter {

    /**
     * <p>
     * The key of <code>User.createUserName</code> to be put into resource properties.
     * </p>
     */
    private static final String CREATE_USERNAME_KEY = "user.createUserName";

    /**
     * <p>
     * The key of <code>User.modifyUsername</code> to be put into resource properties.
     * </p>
     */
    private static final String MODIFY_USERNAME_KEY = "user.modifyUsername";

    /**
     * <p>
     * The key of <code>User.createDate</code> to be put into resource properties.
     * </p>
     */
    private static final String CREATE_DATE_KEY = "user.createDate";

    /**
     * <p>
     * The key of <code>User.modifyDate</code> to be put into resource properties.
     * </p>
     */
    private static final String MODIFY_DATE_KEY = "user.modifyDate";

    /**
     * <p>
     * The key of <code>User.email</code> to be put into resource properties.
     * </p>
     */
    private static final String EMAIL_KEY = "user.email";

    /**
     * <p>
     * The key of <code>User.handle</code> to be put into resource properties.
     * </p>
     */
    private static final String HANDLE_KEY = "user.handle";

    /**
     * <p>
     * The key of <code>User.suspended</code> to be put into resource properties.
     * </p>
     */
    private static final String SUSPENDED_KEY = "user.suspended";

    /**
     * <p>
     * Default empty constructor. Does nothing.
     * </p>
     */
    public RegistrationEntitiesToResourceConverterImpl() {
        //empty
    }

    /**
     * <p>
     * Converts the registration entities to a <code>Resource</code> object.
     * </p>
     *
     * <p>
     * This method will adapt the entities from the Registration Framework component to work with the entities
     * from Resource Management. The mapping is this:
     * <table border="1">
     *     <tr><td><b>Registration Framework</b></td><td><b>Resource Management</b></td></tr>
     *     <tr><td>ContestRole.Role.Name</td><td>Resource.ResourceRole.Name</td></tr>
     *     <tr><td>Contest.Id</td><td>Resource.Project</td></tr>
     *     <tr><td>User.Id</td><td>Resource.Id</td></tr>
     * </table>
     * </p>
     *
     * <p>
     * The other values of the <code>User</code> entity, including the <em>Handle</em>, <em>Email</em>,
     * <em>Suspended</em> and all the other properties from <code>AuditableRegistrationEntity</code> will
     * be added into the <code>Resource</code> properties map of this form:
     * <pre>
     * (user.fieldName, fieldValue)
     * </pre>
     * "user." is a prefix that will be added to the field name so that there will be no conflict with another
     * key from the map. Regarding the values, for the fields that are not String objects use the string
     * representation.
     * </p>
     *
     * @param contest The contest to convert.
     * @param user The user to convert.
     * @param contestRole The role to convert.
     * @param resourceRoles The resource roles.
     *
     * @throws IllegalArgumentException If any parameter is null. Or if the array <code>resourceRoles</code> is empty
     *         or contains null elements.
     * @throws RegistrationEntitiesToResourceConversionException If there is no <code>ResourceRole</code> object for
     *         which the name matches the <code>ContestRole.role.name</code>. Or if <code>ContestRole.role</code> is
     *         null. Or if <code>ContestRole.role.name</code> is null. Or if <code>ResourceRole.name</code> is null.
     *
     * @return The Resource instance converted.
     */
    public Resource convertRegistrationEntitiesToResource(Contest contest,
        User user, ContestRole contestRole, ResourceRole[] resourceRoles)
        throws RegistrationEntitiesToResourceConversionException {

        validateArguments(contest, user, contestRole, resourceRoles);

        Role role = contestRole.getRole();
        validateNotNull(role, "ContestRole.role should not be null.");
        validateNotNull(role.getName(), "ContestRole.role.name should not be null.");

        //loop through all the resource roles, finding the one that matches the name of the ContestRole.role.name
        ResourceRole matchedResourceRole = null;
        for (int i = 0; i < resourceRoles.length; i++) {
            ResourceRole resourceRole = resourceRoles[i];
            //The resourceRoles array cannot contain null element
            validateNotNull(resourceRole.getName(), "The name of ResourceRole at index " + i + " should not be null.");
            if (role.getName().equalsIgnoreCase(resourceRole.getName())) {
                matchedResourceRole = resourceRole;
                //the loop will stop when the match is found; it is unlikely that there will be more than one matches
                //but if there are the loop will not reach a second match
                break;
            }
        }

        //RegistrationEntitiesToResourceConversionException if there is no ResourceRole object for which the
        //name matches the ContestRole.role.name
        validateNotNull(matchedResourceRole, "No corresponding ResourceRole found for ContestRole: " + role.getName());

        try {
            //create a Resource instance using the default constructor
            Resource resource = new Resource();

            //Set Resource.id as User.id
            //See http://forums.topcoder.com/?module=Thread&threadID=613008&start=0&mc=7#973845
            resource.setId(user.getId());

            //set the Resource.project with Contest.id
            resource.setProject(contest.getId());

            //set the Resource.resourceRole using the ResourceRole found
            resource.setResourceRole(matchedResourceRole);

            //all the fields of the User entity (including those from the abstract class)
            //will be added into the Resource //properties map

            //the pairs added will be of the following form (user.fieldName,fieldValue)
            resource.setProperty(CREATE_USERNAME_KEY, user.getCreateUserName());
            resource.setProperty(MODIFY_USERNAME_KEY, user.getModifyUsername());

            //regarding the values, for the fields that are not String objects use the string representation
            Date createDate = user.getCreateDate();
            resource.setProperty(CREATE_DATE_KEY, createDate == null ? null : createDate.toString());
            Date modifyDate = user.getModifyDate();
            resource.setProperty(MODIFY_DATE_KEY, modifyDate == null ? null : modifyDate.toString());

            resource.setProperty(HANDLE_KEY, user.getHandle());
            resource.setProperty(EMAIL_KEY, user.getEmail());
            resource.setProperty(SUSPENDED_KEY, user.isSuspended() + "");
            return resource;
        } catch (IllegalArgumentException e) {
            throw new RegistrationEntitiesToResourceConversionException(
                "Error occurs while constructing Resource object.", e);
        }

    }

    /**
     * <p>
     * Validates arguments. <code>IllegalArgumentException</code> is thrown if the any argument is invalid.
     * </p>
     *
     * @param contest The contest to convert.
     * @param user The user to convert.
     * @param contestRole The role to convert.
     * @param resourceRoles The resource roles.
     *
     * @throws IllegalArgumentException If any parameter is null. Or if the array <code>resourceRoles</code>
     *         is empty or contains null elements.
     */
    private static final void validateArguments(Contest contest, User user, ContestRole contestRole,
        ResourceRole[] resourceRoles) {

        //Check null for all arguments
        ArgumentChecker.checkNull(contest, "Contest to be converted should not be null.");
        ArgumentChecker.checkNull(user, "User to be converted should not be null.");
        ArgumentChecker.checkNull(contestRole, "ContestRole to be converted should not be null.");
        ArgumentChecker.checkNull(resourceRoles, "ResourceRole[] to be converted should not be null.");

        //The resourceRoles array cannot be empty
        int length = resourceRoles.length;
        if (length == 0) {
            throw new IllegalArgumentException("The array of ResourceRoles should not be empty.");
        }
        for (int i = 0; i < length; i++) {
            //The resourceRoles array cannot contain null element
            ArgumentChecker.checkNull(resourceRoles[i],
                "The array of ResourceRoles should not contain null element at index " + i);
        }
    }

    /**
     * <p>
     * Checks if the value of an object is null. <code>RegistrationEntitiesToResourceConversionException</code>
     * is thrown if the given object is null.
     * </p>
     *
     * <p>
     * Used for the cases described here: http://forums.topcoder.com/?module=Thread&threadID=613005&start=0.
     * </p>
     *
     * @param obj The object to check.
     * @param errMsg The error message.
     *
     * @throws RegistrationEntitiesToResourceConversionException If given object is null.
     */
    private static final void validateNotNull(Object obj, String errMsg)
        throws RegistrationEntitiesToResourceConversionException {
        if (obj == null) {
            throw new RegistrationEntitiesToResourceConversionException(errMsg);
        }
    }
}
