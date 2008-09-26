/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.ejb;

import com.topcoder.forum.service.CategoryConfiguration;
import com.topcoder.forum.service.EntityType;
import com.topcoder.forum.service.JiveForumManagementException;
import com.topcoder.forum.service.UserRole;


/**
 * <p>
 * This interface defines contract to manage the Jive Forum. It provides functionalities in
 * line with the <code>JiveForumManager</code> class.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * Implementations need to be thread-safe.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public interface JiveForumService {
    /**
     * <p>
     * Creates a watch for a user in a category, forum or thread. Given a user id and either
     * a category, forum or thread id, if the user was not previously watching it, add the watch.
     * </p>
     *
     * @param userId the id of the user to watch the entity
     * @param entityId the entity id
     * @param entityType the entity type
     *
     * @throws IllegalArgumentException if the userId or entityId isn't above 0, or if entityType is null
     * @throws UserNotFoundException if there is no user with the given userId in jive forum
     * @throws ForumEntityNotFoundException if there is no entity for the given entityId in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIs
     */
    public void watch(long userId, long entityId, EntityType entityType)
        throws JiveForumManagementException;

    /**
     * <p>
     * Gets whether a user is watching a category, forum or thread. Given a user id and either
     * a category, forum or thread id, return whether the user is already watching it.
     * </p>
     *
     * @param userId the id of the user to get watching status for
     * @param entityId the entity id
     * @param entityType the entity type
     * @return true if the forum entity is watched, false otherwise
     *
     * @throws IllegalArgumentException if the userId or entityId isn't above 0, or if entityType is null
     * @throws UserNotFoundException if there is no user with the given userId in jive forum
     * @throws ForumEntityNotFoundException if there is no entity for the given entityId in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIs
     */
    public boolean isWatched(long userId, long entityId, EntityType entityType)
        throws JiveForumManagementException;

    /**
     * <p>
     * Sets the user's role in the specified category. Given a user id and a category id, one of these
     * roles can be assigned:<br/>
     * No access: the user must not belong to moderators or users groups<br/>
     * Contributor: the user must just belong to users group<br/>
     * Moderator: the user must just belong to moderators group
     * </p>
     *
     * @param userId the id of the user to set role for
     * @param categoryId the category id
     * @param role the role
     *
     * @throws IllegalArgumentException if the userId or categoryId isn't above 0, or if the role is null
     * @throws UserNotFoundException if there is no user with the given userId in the jive forum
     * @throws ForumEntityNotFoundException if there is no category for the given categoryId in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIS
    */
    public void setUserRole(long userId, long categoryId, UserRole role)
        throws JiveForumManagementException;

    /**
     * <p>
     * Gets the user's role in a category. Given a user id and a category id, return whether the
     * user has no access, is contributor or moderator, depending on the groups the user belongs.
     * </p>
     *
     * @param userId the id of user to get role for
     * @param categoryId the category id
     * @return the user's role in the category
     * @throws IllegalArgumentException if the userId or categoryId isn't above 0
     * @throws UserNotFoundException if there is no user with the given userId in jive forum
     * @throws ForumEntityNotFoundException if there is no category for the given categoryId in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIS
    */
    public UserRole getUserRole(long userId, long categoryId)
        throws JiveForumManagementException;

    /**
     * <p>
     * Creates a new category according to the category configuration. It creates a new category, and if a
     * template category is specified, it will also copy all the contents. It will create the category and
     * set its properties and then create groups and set permissions. User should provide either template
     * category id or template category type if a template category is necessary.
     * </p>
     *
     * @param categoryConfiguration the new category configuration.
     * @return the newly created category id.
     * @throws IllegalArgumentException
     *         if categoryConfiguration is null, or its name/description/versionText is null/empty string, or
     *         if its componentId/versionId/rootCategoryId is isn't above 0
     * @throws UserNotFoundException if there is no user with given moderationUserId in jive forum
     * @throws ForumEntityNotFoundException
     *         if there is no category for the given template category id / root category id in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIs
     */
    public long createCategory(CategoryConfiguration categoryConfiguration)
        throws JiveForumManagementException;
}
