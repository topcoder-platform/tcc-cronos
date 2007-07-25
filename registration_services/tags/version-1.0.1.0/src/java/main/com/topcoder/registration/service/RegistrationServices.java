/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.resource.Resource;

/**
 * <p>
 * This represents the interface that defines all business methods for registration services. The
 * services include being able to register a member in a project under a specific role, validating,
 * retrieving and deleting the registration. Furthermore, one can find all available positions in
 * any given project that is in a registration phase, one can get all current members in a project,
 * and all projects a member is registered in.
 * </p>
 * <p>
 * It has one implementation: RegistrationServicesImpl.
 * </p>
 * <p>
 * Thread Safety: There are no restrictions on thread safety in implementations.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public interface RegistrationServices {

    /**
     * <p>
     * Registers the user as a new resource for the given project with the given role. Returns the
     * result of this process. If successful, returns the previous registration resource if one is
     * available. Otherwise, returns any available error messages. Note that validation is performed
     * on the registration information.
     * </p>
     * @return The result of the registration attempt. Will never be null
     * @param registrationInfo
     *            RegistrationInfo containing the user to register in the given project under the
     *            given role
     * @throws IllegalArgumentException
     *             If registrationInfo is null
     * @throws RegistrationServiceException
     *             If any unexpected error occurs
     */
    public RegistrationResult registerForProject(RegistrationInfo registrationInfo);

    /**
     * <p>
     * Validates the registration info and, if successful, returns the previous registration
     * resource if one is available. Otherwise, returns any available error messages.
     * </p>
     * @return The result of the registration validation attempt. Will never be null.
     * @param registrationInfo
     *            RegistrationInfo containing the project, role, and user to validate
     * @throws IllegalArgumentException
     *             If registrationInfo is null
     * @throws RegistrationServiceException
     *             If any unexpected error occurs
     */
    public RegistrationResult validateRegistration(RegistrationInfo registrationInfo);

    /**
     * <p>
     * Obtains the registration information for the user in the given project. In essence, it
     * retrieves the role the user has in the given project. It is possible that the user is not
     * registered in this project, upon which a null will be returned.
     * </p>
     * @return The registration information for the user in the given project, or null if no
     *         information is available for this user in this project
     * @param userId
     *            The ID of the user
     * @param projectId
     *            The ID of the project
     * @throws IllegalArgumentException
     *             If userId or projectId is negative
     * @throws RegistrationServiceException
     *             If any unexpected error occurs
     */
    public RegistrationInfo getRegistration(long userId, long projectId);

    /**
     * <p>
     * Obtains all available positions in all active projects that fit the passed project category.
     * The position information will detail the roles one can participate in for a project, and the
     * phase details for each project. The project will be in registration phase.
     * </p>
     * @return Information about available positions in active projects that are part of the passed
     *         category
     * @param category
     *            The project category to determine which projects are checked
     * @throws IllegalArgumentException
     *             If category is null
     * @throws RegistrationServiceException
     *             If any unexpected error occurs
     */
    public RegistrationPosition[] findAvailableRegistrationPositions(ProjectCategory category);

    /**
     * <p>
     * Removes the registration for the given user in the given project, and potentially bans the
     * resource for a period of days. The registration must match the given role. If the user does
     * not currently have a registration with this project, it will return an unsuccessful result
     * with a message stating that.
     * </p>
     * @param registrationInfo
     *            RegistrationInfo containing the user, project, and role to remove.
     * @param banDays
     *            The number of days the resource should be banned
     * @return The result of the removal attempt
     * @throws IllegalArgumentException
     *             If registrationInfo is null, or banDays is negative
     * @throws InvalidRoleException
     *             If the role in the passed RegistrationInfo does not match the role in the
     *             resource for this user in the project
     * @throws RegistrationServiceException
     *             If any unexpected error occurs
     */
    public RemovalResult removeRegistration(RegistrationInfo registrationInfo, int banDays);

    /**
     * <p>
     * Gets all registered resources for the given project. It will return an array with zero to
     * many registrants, as Resource objects. The array will not contain null elements.
     * </p>
     * @return All registered resources for the given project, or empty if none yet registered
     * @param projectId
     *            The ID of the project whose members we want to see
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws RegistrationServiceException
     *             If any unexpected error occurs
     */
    public Resource[] getRegisteredResources(long projectId);

    /**
     * <p>
     * Gets all projects the user has registered for. It will return an array with zero to many
     * projects, as <code>Project</code> objects. The array will not contain null elements.
     * </p>
     * @return All projects the user has registered for, or empty if not registered in any
     * @param userId
     *            The ID of the user whose registered projects we want
     * @throws IllegalArgumentException
     *             If userId is negative
     * @throws RegistrationServiceException
     *             If any unexpected error occurs
     */
    public Project[] getRegisteredProjects(long userId);
}
