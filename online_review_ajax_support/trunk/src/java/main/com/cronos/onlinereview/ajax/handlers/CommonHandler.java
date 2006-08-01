/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.ajax.handlers;
import com.cronos.onlinereview.ajax.AjaxRequestHandler;
import com.cronos.onlinereview.ajax.AjaxSupportHelper;
import com.cronos.onlinereview.ajax.ConfigurationException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * <p>
 * Defines a common Ajax request handler capable of getting a user¡¯s role using its ID,
 * and the Resource Management component;
 * this class implements the AjaxRequestHandler interface,
 * and keeps an instance of RessourceManager class in order to get resource related data.
 * <br>
 * This class main purpose is to simplify Ajax request handlers¡¯ implementation.
 * </p>
 *
 * <p>
 * <strong>Thread Safety : </strong>
 * This class is immutable an thread safe.
 * </p>
 *
 * @author topgear, TCSDEVELOPER
 * @version 1.0
 */
public abstract class CommonHandler implements AjaxRequestHandler {

    /**
     * <p>
     * The resource manager used to get resource data
     * This variable is immutable, it is initialized by the constructor to a not null ReviewManager object,
     * and retrieved using its corresponding getter method.
     * </p>
     */
    private final ResourceManager resourceManager;

    /**
     * <p>
     * The id of the manager role.
     * This variable is immutable, it is initialized by the constructor to a negative/0/positive long number,
     * and used in several methods in this class.
     * </p>
     */
    private final long managerRoleId;

    /**
     * <p>
     * Creates an instance of this class and initialize its internal state.
     * </p>
     *
     * @throws ConfigurationException if there is a configuration exception
     */
    protected CommonHandler() throws ConfigurationException {
        try {
            // create an object factory that uses only the specification
            ObjectFactory factory = AjaxSupportHelper.createObjectFactory();

            resourceManager = (ResourceManager) factory.createObject(ResourceManager.class);

            // get all the resource roles
            ResourceRole[] roles = resourceManager.getAllResourceRoles();

            // find the role with name "manager" and save its id
            boolean found = false;
            long id = 0;
            for (int i = 0; i < roles.length; i++) {
                if ("Manager".equals(roles[i].getName())) {
                    id = roles[i].getId();
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new ConfigurationException("Can't find manager role.");
            }
            this.managerRoleId = id;
        } catch (Exception e) {
            throw new ConfigurationException("Error happens during construct handler.", e);
        }
    }

    /**
     * <p>
     * Returns the role name for the user having userId as its ID.
     * </p>
     *
     * @return the role name or null if it is not found
     * @param userId the id of the user to get its role name
     * @throws RoleResolutionException if the resource manager has thrown an exception
     */
    protected String getUserRoleName(long userId) throws RoleResolutionException {
        // get the resource using the resource manager
        Resource resource = null;
        try {
            resource = resourceManager.getResource(userId);
        } catch (Exception e) {
            throw new RoleResolutionException("Error when get the resource.", e);
        }

        if (resource == null) {
            return null;
        }

        // get the resource role from the resource and return it's name
        ResourceRole role = resource.getResourceRole();
        return role.getName();
    }

    /**
     * <p>
     * Check if a user has the specified role or not.
     * </p>
     *
     * @return true if the user has the role
     * @param userId the id of the user to check its role
     * @param role the role to check for
     * @throws RoleResolutionException if the resource manager has thrown an exception
     * @throws IllegalArgumentException if role parameter is null or empty String
     */
    protected boolean checkUserHasRole(long userId, String role) throws RoleResolutionException {
        if (role == null) {
            throw new IllegalArgumentException("The role should not be null.");
        }
        if (role.trim().length() == 0) {
            throw new IllegalArgumentException("The role should not be empty.");
        }

        // get the user role name
        String roleName = getUserRoleName(userId);
        if (roleName == null) {
            return false;
        }
        return roleName.equals(role);
    }

    /**
     * <p>
     * Check if the user has the global manager role or not.
     * </p>
     *
     * @return true if the user has the global manager role
     * @param userId the id of the user to check its role
     * @throws RoleResolutionException if the resource manager has thrown an exception
     */
    protected boolean checkUserHasGlobalManagerRole(long userId) throws RoleResolutionException {
        // get the resource using the resource manager
        Resource resource = null;
        try {
            resource = resourceManager.getResource(userId);
        } catch (Exception e) {
            throw new RoleResolutionException("Error when get the resource.", e);
        }

        if (resource == null) {
            return false;
        }

        // get the resource project
        Long project = resource.getProject();
        if (project == null) {
            return false;
        }

        // get the resource role
        ResourceRole role = resource.getResourceRole();
        return role.getId() == managerRoleId;
    }

    /**
     * <p>
     * Returns the resource manager used to get resource data.
     * </p>
     *
     * @return the resource manager used to manage resources
     */
    protected ResourceManager getResourceManager() {
        return resourceManager;
    }
}
