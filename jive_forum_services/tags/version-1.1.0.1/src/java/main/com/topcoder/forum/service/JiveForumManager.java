/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import com.jivesoftware.base.Group;
import com.jivesoftware.base.GroupAlreadyExistsException;
import com.jivesoftware.base.GroupManager;
import com.jivesoftware.base.GroupNotFoundException;
import com.jivesoftware.base.PermissionType;
import com.jivesoftware.base.PermissionsManager;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;
import com.jivesoftware.base.UserManager;

import com.jivesoftware.forum.Attachment;
import com.jivesoftware.forum.AttachmentException;
import com.jivesoftware.forum.Forum;
import com.jivesoftware.forum.ForumCategory;
import com.jivesoftware.forum.ForumCategoryNotFoundException;
import com.jivesoftware.forum.ForumFactory;
import com.jivesoftware.forum.ForumMessage;
import com.jivesoftware.forum.ForumNotFoundException;
import com.jivesoftware.forum.ForumPermissions;
import com.jivesoftware.forum.ForumThread;
import com.jivesoftware.forum.ForumThreadNotFoundException;
import com.jivesoftware.forum.MessageRejectedException;
import com.jivesoftware.forum.WatchManager;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * <p>
 * This class provides methods to manage the Jive Forum. It provides the following functionalities:<br/>
 * 1. Create a watch for a user in a category, forum or thread.<br/>
 * 2. Get whether a user is watching a category, forum or thread.<br/>
 * 3. Set User Role in category.<br/>
 * 4. Get User Role for a category.<br/>
 * 5. Create Category with provided configuration.<br/>
 * The configuration can have an optional template category id or template category type to identify a
 * category used as template. This class will use the APIs provided by Jive Forum to manage the Jive Forum.
 * </p>
 *
 * <p>
 * Sample usage:
 * <pre>
 *  //create the JiveForumManager
 * long userId = 1;
 * Map&lt;CategoryType, Long&gt; categoryTemplateIds = new HashMap&lt;CategoryType, Long&gt;();
 * categoryTemplateIds.put(CategoryType.APPLICATION, new Long(1));
 * manager = new JiveForumManager(userId, categoryTemplateIds);
 *
 *  //create category
 * CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
 * categoryConfiguration.setName("category");
 * categoryConfiguration.setDescription("a new category");
 * categoryConfiguration.setVersionText("1.0");
 * categoryConfiguration.setComponentId(1);
 * categoryConfiguration.setVersionId(1);
 * categoryConfiguration.setRootCategoryId(10);
 * categoryConfiguration.setPublic(false);
 * categoryConfiguration.setModeratorUserId(1);
 * categoryConfiguration.setTemplateCategoryId(1);
 * manager.createCategory(categoryConfiguration);
 *
 *  //watch a forum
 * manager.watch(1, 1, EntityType.FORUM);
 *
 *  //gets the watch status
 * manager.isWatched(1, 1, EntityType.FORUM));
 *
 *  //set the user role
 * manager.setUserRole(1, 1, UserRole.NO_ACCESS);
 *
 *  //get the user role
 * UserRole role = manager.getUserRole(1, 1);
 * </pre>
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * The used Jive Forum APIs are all thread-safe, and this class is immutable. So this class is thread-safe.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class JiveForumManager {
    /**
     * <p>The constant represents the forum category property for component id.</p>
     */
    public static final String PROPERTY_COMPONENT_ID = "componentId";

    /**
     * <p>The constant represents the forum category property for component version id.</p>
     */
    public static final String PROPERTY_COMPONENT_VERSION_ID = "compVersionId";

    /**
     * <p>The constant represents the forum category property for component version text.</p>
     */
    public static final String PROPERTY_COMPONENT_VERSION_TEXT = "versionText";

    /**
     * <p>The constant represents the forum category property for component tech types.</p>
     */
    public static final String PROPERTY_COMPONENT_TECH_TYPES = "compTechTypes";

    /**
     * <p>The constant represents the forum category property for component archival status.</p>
     */
    public static final String PROPERTY_ARCHIVAL_STATUS = "archivalStatus";

    /**
     * <p>
     * The constant represents the forum category property value for component archival status
     * when the status is active.
     * </p>
     */
    public static final String PROPERTY_ARCHIVAL_STATUS_ACTIVE = "1";

    /**
     * <p>
     * The constant represents the forum category property value for component archival status
     * when the status is closed.
     * </p>
     */
    public static final String PROPERTY_ARCHIVAL_STATUS_CLOSED = "2";

    /**
     * <p>The constant represents the forum category property for modify forums flag.</p>
     */
    public static final String PROPERTY_MODIFY_FORUMS = "modifyForums";

    /**
     * <p>The constant represents the prefix for the software moderators group name.</p>
     */
    public static final String GROUP_SOFTWARE_MODERATORS_PREFIX = "Software_Moderators_";

    /**
     * <p>The constant represents the prefix for the software users group name.</p>
     */
    public static final String GROUP_SOFTWARE_USERS_PREFIX = "Software_Users_";

    /**
     * <p>The constant represents the software admin group name.</p>
     */
    public static final String GROUP_SOFTWARE_ADMINS = "Software Admins";

    /**
     * <p>
     * The default permissions for the registered users. It has permissions:
     * ForumPermissions.READ_FORUM, ForumPermissions.CREATE_THREAD,
     * ForumPermissions.CREATE_MESSAGE, ForumPermissions.RATE_MESSAGE,
     * ForumPermissions.CREATE_POLL, ForumPermissions.VOTE_IN_POLL
     * </p>
     */
    public static final long[] REGISTERED_PERMS = {
        ForumPermissions.READ_FORUM, ForumPermissions.CREATE_THREAD,
        ForumPermissions.CREATE_MESSAGE, ForumPermissions.RATE_MESSAGE,
        ForumPermissions.CREATE_POLL, ForumPermissions.VOTE_IN_POLL
    };

    /**
     * <p>
     * The default permissions for administrators. It has permissions:
     * ForumPermissions.READ_FORUM, ForumPermissions.CREATE_THREAD,
     * ForumPermissions.CREATE_MESSAGE, ForumPermissions.RATE_MESSAGE,
     * ForumPermissions.CREATE_MESSAGE_ATTACHMENT,
     * ForumPermissions.CREATE_POLL, ForumPermissions.VOTE_IN_POLL,
     * ForumPermissions.ANNOUNCEMENT_ADMIN,
     * ForumPermissions.FORUM_CATEGORY_ADMIN
     * </p>
     */
    public static final long[] ADMIN_PERMS = {
        ForumPermissions.READ_FORUM, ForumPermissions.CREATE_THREAD,
        ForumPermissions.CREATE_MESSAGE, ForumPermissions.RATE_MESSAGE,
        ForumPermissions.CREATE_MESSAGE_ATTACHMENT,
        ForumPermissions.CREATE_POLL, ForumPermissions.VOTE_IN_POLL,
        ForumPermissions.ANNOUNCEMENT_ADMIN,
        ForumPermissions.FORUM_CATEGORY_ADMIN
    };

    /**
     * <p>
     * The default permissions for moderators. It has permissions:
     * ForumPermissions.READ_FORUM, ForumPermissions.CREATE_THREAD,
     * ForumPermissions.CREATE_MESSAGE, ForumPermissions.RATE_MESSAGE,
     * ForumPermissions.FORUM_CATEGORY_ADMIN,
     * ForumPermissions.CREATE_MESSAGE_ATTACHMENT,
     * ForumPermissions.CREATE_POLL, ForumPermissions.VOTE_IN_POLL
     * </p>
     */
    public static final long[] MODERATOR_PERMS = {
        ForumPermissions.READ_FORUM, ForumPermissions.CREATE_THREAD,
        ForumPermissions.CREATE_MESSAGE, ForumPermissions.RATE_MESSAGE,
        ForumPermissions.FORUM_CATEGORY_ADMIN,
        ForumPermissions.CREATE_MESSAGE_ATTACHMENT,
        ForumPermissions.CREATE_POLL, ForumPermissions.VOTE_IN_POLL
    };

    /**
     * <p>
     * The blocked permissions for annoymous users when the category is private. It has permissions:
     * ForumPermissions.READ_FORUM, ForumPermissions.CREATE_THREAD,
     * ForumPermissions.CREATE_MESSAGE, ForumPermissions.RATE_MESSAGE,
     * ForumPermissions.CREATE_MESSAGE_ATTACHMENT,
     * ForumPermissions.CREATE_POLL, ForumPermissions.VOTE_IN_POLL,
     * ForumPermissions.ANNOUNCEMENT_ADMIN
     * </p>
     */
    public static final long[] SW_BLOCK_PERMS = {
        ForumPermissions.READ_FORUM, ForumPermissions.CREATE_THREAD,
        ForumPermissions.CREATE_MESSAGE, ForumPermissions.RATE_MESSAGE,
        ForumPermissions.CREATE_MESSAGE_ATTACHMENT,
        ForumPermissions.CREATE_POLL, ForumPermissions.VOTE_IN_POLL,
        ForumPermissions.ANNOUNCEMENT_ADMIN
    };

    /**
     * <p>
     * This field represents the <code>ForumFactory</code> used to manage the Jive Forum.
     * It is initialized in constructor, and never changed afterwards. It must be non-null.
     * </p>
     */
    private final ForumFactory forumFactory;

    /**
     * <p>
     * This field represents mappings from the category type to the category id. It is used
     * to get the template category id for the specified category type. The keys must be
     * non-null <code>CategoryType</code>, and the values must be non-null <code>Long</code>
     * object, and the value of <code>Long</code> object should be not less than 1. It will be
     * initialized in constructor, and never changed afterwards. It must be non-null, non-empty map.
     * </p>
     */
    private final Map<CategoryType, Long> categoryTemplateIds = new HashMap<CategoryType, Long>();

    /**
     * <p>
     * Creates a <code>JiveForumManager</code> instance, uses the <code>userId</code> to obtain
     * the <code>ForumFactory</code>, and initializes the categoryTemplateIds map.
     * </p>
     *
     * @param userId the id of the user to perform the operations
     * @param categoryTemplateIds the category template ids
     * @throws IllegalArgumentException
     *         if userId isn't above 0, or if categoryTemplatesIds is null or empty,
     *         or if it contains null key or value, or if it contains long value isn't above 0
     * @throws JiveForumManagementException if fails to obtain the ForumFactory
     */
    public JiveForumManager(long userId,
        Map<CategoryType, Long> categoryTemplateIds)
        throws JiveForumManagementException {
        Helper.checkId(userId, "userId");
        Helper.checkNull(categoryTemplateIds, "categoryTemplateIds");

        if (categoryTemplateIds.isEmpty()) {
            throw new IllegalArgumentException(
                "The categoryTemplateIds should not be empty.");
        }

        for (Entry<CategoryType, Long> entry : categoryTemplateIds.entrySet()) {
            Helper.checkNull(entry.getKey(), "categoryTemplateIds' key");
            Helper.checkNull(entry.getValue(), "categoryTemplateIds' value");
            Helper.checkId(entry.getValue().longValue(),
                "categoryTemplateIds' value");
        }

        this.categoryTemplateIds.putAll(categoryTemplateIds);

        forumFactory = ForumFactory.getInstance(new TCAuthToken(userId));

        if (forumFactory == null) {
            throw new JiveForumManagementException(
                "Unable to obtain the ForumFactory instance.");
        }
    }

    /**
     * <p>
     * Creates a watch for a user in a category, forum or thread. Given a user id and either a
     * category, forum or thread id, if the user was not previously watching it, add the watch.
     * </p>
     *
     * @param userId the id of the user to watch the entity
     * @param entityId the entity id
     * @param entityType the entity type
     * @throws IllegalArgumentException
     *         if the userId or entityId isn't above 0, or if entityType is null
     * @throws UserNotFoundException if there is no user with the given userId in jive forum
     * @throws ForumEntityNotFoundException if there is no entity for the given entityId in the jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIs
     */
    public void watch(long userId, long entityId, EntityType entityType)
        throws JiveForumManagementException {
        Helper.checkId(userId, "userId");
        Helper.checkId(entityId, "entityId");
        Helper.checkNull(entityType, "entityType");

        try {
            User user = getUserSafe(userId);

            WatchManager watchManager = forumFactory.getWatchManager();
            validateNotNull(watchManager, "watchManager");

            if (entityType == EntityType.FORUM_CATEGORY) {
                ForumCategory category = forumFactory.getForumCategory(entityId);

                if (!watchManager.isWatched(user, category)) {
                    watchManager.createWatch(user, category);
                }
            } else if (entityType == EntityType.FORUM) {
                Forum forum = forumFactory.getForum(entityId);

                if (!watchManager.isWatched(user, forum)) {
                    watchManager.createWatch(user, forum);
                }
            } else if (entityType == EntityType.FORUM_THREAD) {
                ForumThread thread = forumFactory.getForumThread(entityId);

                if (!watchManager.isWatched(user, thread)) {
                    watchManager.createWatch(user, thread);
                }
            }
        } catch (UnauthorizedException e) {
            throw new JiveForumManagementException(
                "Permission denied to watch: " + entityType + ":" + entityId, e);
        } catch (ForumNotFoundException e) {
            throw new ForumEntityNotFoundException("The forum with id '" + entityId + "' doesn't exist.", e);
        } catch (ForumThreadNotFoundException e) {
            throw new ForumEntityNotFoundException("The forum thread with id '" + entityId + "' doesn't exist.", e);
        } catch (ForumCategoryNotFoundException e) {
            throw new ForumEntityNotFoundException(
                "The forum category with id '" + entityId + "doesn't exist.", e);
        }
    }

    /**
     * <p>
     * Gets whether a user is watching a category, forum or thread. Given a user id and either
     * a category, forum or thread id, returns whether the user is already watching it.
     * </p>
     *
     * @param userId the id of the user to get watching status
     * @param entityId the entity id
     * @param entityType the entity type
     * @return true if the forum entity is watched, false otherwise
     * @throws IllegalArgumentException if the userId or entityId isn't above 0, or if entityType is null
     * @throws UserNotFoundException if there is no user with the given userId in jive forum
     * @throws ForumEntityNotFoundException if there is no entity for the given entityId in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIS.
     */
    public boolean isWatched(long userId, long entityId, EntityType entityType)
        throws JiveForumManagementException {
        Helper.checkId(userId, "userId");
        Helper.checkId(entityId, "entityId");
        Helper.checkNull(entityType, "entityType");

        try {
            User user = getUserSafe(userId);

            WatchManager watchManager = forumFactory.getWatchManager();
            validateNotNull(watchManager, "watchManager");

            if (entityType == EntityType.FORUM_CATEGORY) {
                return watchManager.isWatched(user,
                    forumFactory.getForumCategory(entityId));
            } else if (entityType == EntityType.FORUM) {
                return watchManager.isWatched(user,
                    forumFactory.getForum(entityId));
            } else if (entityType == EntityType.FORUM_THREAD) {
                return watchManager.isWatched(user,
                    forumFactory.getForumThread(entityId));
            }
        } catch (UnauthorizedException e) {
            throw new JiveForumManagementException(
                "Permission denied to get watch status for: " + entityType + ":" + entityId, e);
        } catch (ForumNotFoundException e) {
            throw new ForumEntityNotFoundException("The forum with id '" + entityId + "' doesn't exist.", e);
        } catch (ForumThreadNotFoundException e) {
            throw new ForumEntityNotFoundException("The forum thread with id '" + entityId + "' doesn't exist.", e);
        } catch (ForumCategoryNotFoundException e) {
            throw new ForumEntityNotFoundException(
                "The forum category with id '" + entityId + "doesn't exist.", e);
        }

        return false;
    }

    /**
     * <p>
     * Sets the user's role in Category. Given a user id and a category id, one of these roles can be assigned:<br/>
     * No access: the user must not belong to moderators or users groups.<br/>
     * Contributor: the user must just belong to users group<br/>
     * Moderator: the user must just belong to moderators group<br/>
     * The users group name for a category is: GROUP_SOFTWARE_USERS_PREFIX + categoryId. The moderators group name
     * for a category is: GROUP_SOFTWARE_MODERATORS_PREFIX + categoryId.
     * </p>
     *
     * @param userId the id of the user to set role for
     * @param role the new role
     * @param categoryId the category id
     * @throws IllegalArgumentException if the userId or categoryId isn't above 0, or if the role is null
     * @throws UserNotFoundException if there is no user with the given userId in the jive forum
     * @throws ForumEntityNotFoundException if there is no category for the given categoryId in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIS
     */
    public void setUserRole(long userId, long categoryId, UserRole role)
        throws JiveForumManagementException {
        Helper.checkId(userId, "userId");
        Helper.checkId(categoryId, "categoryId");
        Helper.checkNull(role, "role");

        try {
            User user = getUserSafe(userId);

            GroupManager groupManager = getGroupManagerSafe();

            Group userGroup = getUserGroup(groupManager, categoryId);
            Group moderatorGroup = getModeratorGroup(groupManager, categoryId);

            if (role == UserRole.NO_ACCESS) {
                //Remove user from both users group and moderators group
                userGroup.removeMember(user);
                moderatorGroup.removeMember(user);
            } else if (role == UserRole.CONTRIBUTOR) {
                //Just belong to user group
                removeUserFromGroups(groupManager, user);
                userGroup.addMember(user);
            } else if (role == UserRole.MODERATOR) {
                //Just belong to moderator group
                removeUserFromGroups(groupManager, user);
                moderatorGroup.addMember(user);
            }
        } catch (GroupNotFoundException e) {
            throw new ForumEntityNotFoundException("The group for category '" + categoryId + "' doesn't exist.", e);
        } catch (UnauthorizedException e) {
            throw new JiveForumManagementException("Permission denied to set the user's role.",
                e);
        }
    }

    /**
     * <p>
     * Removes the user from its belonging groups.
     * </p>
     *
     * @param groupManager the group manager
     * @param user the user to remove from groups
     * @throws UnauthorizedException if permission denied to do the operation
     */
    private void removeUserFromGroups(GroupManager groupManager, User user)
        throws UnauthorizedException {
        Iterator it = groupManager.getUserGroups(user);

        while (it.hasNext()) {
            Group group = (Group) it.next();
            group.removeMember(user);
        }
    }

    /**
     * <p>
     * Gets user's role for a category. Given a user id and a category id, return whether the
     * user has no access, is contributor or moderator, depending on the groups it belong.
     * </p>
     *
     * @param userId the id of the user to get role
     * @param categoryId the category id
     * @return the role of the user in the category
     * @throws IllegalArgumentException if the userId or categoryId isn't above 0
     * @throws UserNotFoundException if there is no user with the given userId in jive forum
     * @throws ForumEntityNotFoundException if there is no category for the given categoryId in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIS
     */
    public UserRole getUserRole(long userId, long categoryId)
        throws JiveForumManagementException {
        Helper.checkId(userId, "userId");
        Helper.checkId(categoryId, "categoryId");

        try {
            User user = getUserSafe(userId);

            GroupManager groupManager = getGroupManagerSafe();

            Group userGroup = getUserGroup(groupManager, categoryId);
            Group moderatorGroup = getModeratorGroup(groupManager, categoryId);

            Iterator it = groupManager.getUserGroups(user);

            UserRole role = UserRole.NO_ACCESS;

            // If the user in both group, returns MODERATOR
            while (it.hasNext()) {
                Group group = (Group) it.next();

                if (group.getID() == moderatorGroup.getID()) {
                    return UserRole.MODERATOR;
                } else if (group.getID() == userGroup.getID()) {
                    role = UserRole.CONTRIBUTOR;
                }
            }

            return role;
        } catch (GroupNotFoundException e) {
            throw new ForumEntityNotFoundException(
                "The group for category id: " + categoryId + " doesn't exist", e);
        }
    }

    /**
     * <p>
     * Creates a new category according to the passed category configuration. It creates a new category,
     * and if a template category is specified, it will also copy all the contents. It will create the
     * category and set its properties and then create groups and set permissions. User should provide
     * either template category id or template category type if a template category is necessary.
     * </p>
     *
     * <p>
     * If a Template Category Id is specified, all the forums, threads and announcements must be
     * copied from that category to the created category, maintaining the structure. If a Template
     * Category Type is specified, it will be used to get the preconfigured template category id,
     * and then do the same as above. If a Moderator User Id is specified, that user id must be
     * assigned to the moderators group for the category, and a watch for the user must be created
     * for the category. The category id for the created category must be returned.
     * </p>
     *
     * @param categoryConfiguration the category configuration
     * @return the id of the newly created category
     *
     * @throws IllegalArgumentException
     *         if categoryConfiguration is null or its name/description/versionText is null/empty String,
     *         or if its componentId/versionId/rootCategoryId isn't above 0.
     * @throws UserNotFoundException
     *         if there is no user with the given moderationUserId in jive forum
     * @throws ForumEntityNotFoundException
     *         if there is no category for the given template category id/root category id in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIS
     */
    public long createCategory(CategoryConfiguration categoryConfiguration)
        throws JiveForumManagementException {
        Helper.checkNull(categoryConfiguration, "categoryConfiguration");
        Helper.checkString(categoryConfiguration.getName(),
            "categoryConfiguration's name");
        Helper.checkString(categoryConfiguration.getDescription(),
            "categoryConfiguration's description");
        Helper.checkString(categoryConfiguration.getVersionText(),
            "categoryConfiguration's versionText");
        Helper.checkId(categoryConfiguration.getComponentId(),
            "categoryConfiguration's componetId");
        Helper.checkId(categoryConfiguration.getVersionId(),
            "categoryConfiguration's versionId");
        Helper.checkId(categoryConfiguration.getRootCategoryId(),
            "categoryConfiguration's rootCategoryId");

        try {
            // Create the new category under the root category
            ForumCategory newCategory = forumFactory.getForumCategory(categoryConfiguration.getRootCategoryId())
                                                    .createCategory(categoryConfiguration.getName(),
                    categoryConfiguration.getDescription());

            // Initialize the groups and permissions
            initializeGroups(newCategory, categoryConfiguration);

            // If moderatorId is specified, add the user to moderator group
            if (categoryConfiguration.getModeratorUserId() > 0) {
                setUserRole(categoryConfiguration.getModeratorUserId(),
                    newCategory.getID(), UserRole.MODERATOR);
                watch(categoryConfiguration.getModeratorUserId(),
                    newCategory.getID(), EntityType.FORUM_CATEGORY);
            }

            long templateCatId;

            if (categoryConfiguration.getTemplateCategoryType() != null) {
                // template category type is specified, get the configured template category id.
                Long value = categoryTemplateIds.get(categoryConfiguration.getTemplateCategoryType());
                validateNotNull(value,
                    "template id map's value for key '" + categoryConfiguration.getTemplateCategoryType() + "'");

                templateCatId = value.longValue();
            } else {
                templateCatId = categoryConfiguration.getTemplateCategoryId();
            }

            if (templateCatId > 0) {
                // template category id is specified. copy the contents.
                ForumCategory templateCategory = forumFactory.getForumCategory(templateCatId);
                cloneCategory(templateCategory, newCategory);
            }

            // Initialize the properties
            setProperties(newCategory, categoryConfiguration);

            return newCategory.getID();
        } catch (UnauthorizedException e) {
            throw new JiveForumManagementException("Permission denied to create the category.",
                e);
        } catch (ForumCategoryNotFoundException e) {
            throw new ForumEntityNotFoundException("The specified category isn't found.",
                e);
        } catch (GroupNotFoundException e) {
            throw new ForumEntityNotFoundException("The admin group doesn't exist.",
                e);
        } catch (GroupAlreadyExistsException e) {
            throw new JiveForumManagementException("The specified group already exist.",
                e);
        } catch (MessageRejectedException e) {
            throw new JiveForumManagementException("There are errors while copying the message.",
                e);
        } catch (AttachmentException e) {
            throw new JiveForumManagementException("There are errors while copying the attachment.",
                e);
        } catch (IOException e) {
            throw new JiveForumManagementException("There are errors while adding the attachment.",
                e);
        }
    }

    /**
     * <p>
     * Copy the category structure from template category to the destination category.
     * They should have the same tree structure, except that the properties aren't copied.
     * </p>
     *
     * @param template the template category
     * @param destCategory the destination category
     * @throws UnauthorizedException if permission denied to do the operations
     * @throws MessageRejectedException if error occurs while copying the messages
     * @throws IOException if error occurs while adding the attachment
     * @throws AttachmentException if error occurs while copying the attachment
     */
    private void copyTemplateCategoryWithoutProperties(ForumCategory template,
        ForumCategory destCategory)
        throws UnauthorizedException, MessageRejectedException,
            AttachmentException, IOException {
        //Copy all sub categories
        Iterator catId = template.getCategories();

        while (catId.hasNext()) {
            ForumCategory category = (ForumCategory) catId.next();

            //Create corresponding sub category
            ForumCategory newCategory = destCategory.createCategory(category.getName(),
                    category.getDescription());
            //Copy all data for sub category
            cloneCategory(category, newCategory);
        }

        //Copy all sub forums
        Iterator forumIt = template.getForums();

        while (forumIt.hasNext()) {
            Forum forum = (Forum) forumIt.next();

            //Create corresponding sub forum
            Forum newForum = forumFactory.createForum(forum.getName(),
                    forum.getDescription(), destCategory);
            //Copy all data for sub forum
            cloneForum(forum, newForum);
        }
    }

    /**
     * <p>
     * Copy all data from original category to the destination category.
     * </p>
     *
     * @param from the original category
     * @param to the destination category
     * @throws UnauthorizedException if permission denied to do the operations
     * @throws MessageRejectedException if any error occurs while copying the messages
     * @throws AttachmentException if any error occurs while copying the attachment
     * @throws IOException if any error occurs while adding the attachment
     */
    private void cloneCategory(ForumCategory from, ForumCategory to)
        throws UnauthorizedException, MessageRejectedException,
            AttachmentException, IOException {
        //copy properties
        Iterator propertyNamesIt = from.getPropertyNames();

        while (propertyNamesIt.hasNext()) {
            String name = (String) propertyNamesIt.next();

            //Make sure that the property value isn't null.
            if (from.getProperty(name) != null) {
                to.setProperty(name, from.getProperty(name));
            }
        }

        copyTemplateCategoryWithoutProperties(from, to);
    }

    /**
     * <p>
     * Copy the from's properties from original form to the destination forum.
     * </p>
     *
     * @param from the original forum
     * @param to the destination forum
     * @throws UnauthorizedException if permission denied to do the operations
     * @throws MessageRejectedException if error occurs while adding the message
     * @throws AttachmentException if error occurs while copying the attachment
     * @throws IOException if io error occurs
     */
    private void cloneForum(Forum from, Forum to)
        throws UnauthorizedException, MessageRejectedException,
            AttachmentException, IOException {
        //Copy the threads
        Iterator threadIt = from.getThreads();

        while (threadIt.hasNext()) {
            ForumThread thread = (ForumThread) threadIt.next();

            //The mapping between the original message, and cloned message
            Map<ForumMessage, ForumMessage> map = new HashMap<ForumMessage, ForumMessage>();

            ForumMessage message;
            ForumMessage rootMessage = thread.getRootMessage();

            if (rootMessage.getUser() != null) {
                message = to.createMessage(rootMessage.getUser());
            } else {
                message = to.createMessage();
            }

            cloneForumMessage(rootMessage, message);
            map.put(rootMessage, message);

            ForumThread newThread = to.createThread(message);
            to.addThread(newThread);
            cloneForumThread(thread, newThread, map);
        }

        //copy properties
        Iterator propertyNamesIt = from.getPropertyNames();

        while (propertyNamesIt.hasNext()) {
            String name = (String) propertyNamesIt.next();

            if (from.getProperty(name) != null) {
                to.setProperty(name, from.getProperty(name));
            }
        }
    }

    /**
     * <p>
     * Copy properties from original thread to the destination thread.
     * </p>
     *
     * @param from the original thread
     * @param to the destination thread
     * @param map this map contains the original message and destination message's map
     * @throws UnauthorizedException if permission denied to do the operations
     * @throws MessageRejectedException if error occurs in adding the message
     * @throws AttachmentException if error occurs in copying the attachment
     * @throws IOException if io error occurs
     */
    private void cloneForumThread(ForumThread from, ForumThread to,
        Map<ForumMessage, ForumMessage> map)
        throws UnauthorizedException, MessageRejectedException,
            AttachmentException, IOException {
        //Clone messages
        Iterator messageIt = from.getMessages();

        while (messageIt.hasNext()) {
            ForumMessage message = (ForumMessage) messageIt.next();

            //The root message is created in the cloneForum.
            //Use id to check whether it's root message instead of refrence.
            if (message.getID() != from.getRootMessage().getID()) {
                ForumMessage newMessage;

                if (message.getUser() != null) {
                    newMessage = to.getForum().createMessage(message.getUser());
                } else {
                    newMessage = to.getForum().createMessage();
                }

                cloneForumMessage(message, newMessage);

                //Sets up the original message's corresponding cloned message
                map.put(message, newMessage);

                //Add the message
                to.addMessage(map.get(message.getParentMessage()), newMessage);
            }
        }

        //clone properties
        Iterator propertyNamesIt = from.getPropertyNames();

        while (propertyNamesIt.hasNext()) {
            String name = (String) propertyNamesIt.next();

            //Make sure that the property value isn't null.
            if (from.getProperty(name) != null) {
                to.setProperty(name, from.getProperty(name));
            }
        }
    }

    /**
     * <p>
     * Copy the properties from original message to the destination message.
     * </p>
     *
     * @param from the original message
     * @param to the destination message
     * @throws AttachmentException if any error occurs in attachment operations
     * @throws UnauthorizedException if permissions denied to do the operations
     * @throws IOException if io error occurs
     */
    private void cloneForumMessage(ForumMessage from, ForumMessage to)
        throws AttachmentException, UnauthorizedException, IOException {
        //Clone the attachment information.
        Iterator attachIt = from.getAttachments();

        while (attachIt.hasNext()) {
            Attachment attach = (Attachment) attachIt.next();
            to.createAttachment(attach.getName(), attach.getContentType(),
                attach.getData());
        }

        //Clone the body and subject
        to.setBody(from.getBody());
        to.setSubject(from.getSubject());

        //Clone the properties
        Iterator propertyNamesIt = from.getPropertyNames();

        while (propertyNamesIt.hasNext()) {
            String name = (String) propertyNamesIt.next();

            // Make sure that the property value isn't null.
            if (from.getProperty(name) != null) {
                to.setProperty(name, from.getProperty(name));
            }
        }
    }

    /**
     * <p>
     * Initialize the newCategory's properties from category configuration.
     * </p>
     *
     * @param newCategory the category to set properties
     * @param categoryConfiguration the category's configuration
     * @throws UnauthorizedException if permission denied to do the operation
     */
    private void setProperties(ForumCategory newCategory,
        CategoryConfiguration categoryConfiguration)
        throws UnauthorizedException {
        newCategory.setProperty(PROPERTY_ARCHIVAL_STATUS,
            PROPERTY_ARCHIVAL_STATUS_ACTIVE);
        newCategory.setProperty(PROPERTY_COMPONENT_ID,
            Long.toString(categoryConfiguration.getComponentId()));
        newCategory.setProperty(PROPERTY_COMPONENT_VERSION_ID,
            Long.toString(categoryConfiguration.getVersionId()));
        newCategory.setProperty(PROPERTY_COMPONENT_VERSION_TEXT,
            categoryConfiguration.getVersionText());
        newCategory.setProperty(PROPERTY_MODIFY_FORUMS, "true");
    }

    /**
     * <p>
     * Initialize the group and permission information for the category.
     * </p>
     *
     * @param newCategory the category to initialize group and permission info
     * @param categoryConfiguration the category configuration
     * @throws GroupNotFoundException if the specified group doesn't exist
     * @throws GroupAlreadyExistsException if try to create an already exist group
     * @throws UnauthorizedException if permission denied to do the operations
     * @throws JiveForumManagementException if any other error occurs
     */
    private void initializeGroups(ForumCategory newCategory,
        CategoryConfiguration categoryConfiguration)
        throws JiveForumManagementException, GroupNotFoundException,
            GroupAlreadyExistsException, UnauthorizedException {
        //Get the group manager
        GroupManager groupManager = getGroupManagerSafe();

        //Gets the admin group
        Group swAdminGroup = groupManager.getGroup(GROUP_SOFTWARE_ADMINS);

        //Creates groups for the category
        Group moderatorGroup = groupManager.createGroup(new StringBuilder(
                    GROUP_SOFTWARE_MODERATORS_PREFIX).append(newCategory.getID())
                                                                                                           .toString());
        Group userGroup = groupManager.createGroup(new StringBuilder(
                    GROUP_SOFTWARE_USERS_PREFIX).append(newCategory.getID())
                                                                                                 .toString());

        moderatorGroup.setDescription(newCategory.getName());
        userGroup.setDescription(newCategory.getName());

        // set permissions.
        PermissionsManager categoryPermissionsManager = newCategory.getPermissionsManager();

        for (int i = 0; i < MODERATOR_PERMS.length; i++) {
            categoryPermissionsManager.addGroupPermission(moderatorGroup,
                PermissionType.ADDITIVE, MODERATOR_PERMS[i]);
        }

        for (int i = 0; i < REGISTERED_PERMS.length; i++) {
            categoryPermissionsManager.addGroupPermission(userGroup,
                PermissionType.ADDITIVE, REGISTERED_PERMS[i]);
        }

        for (int i = 0; i < ADMIN_PERMS.length; i++) {
            categoryPermissionsManager.addGroupPermission(swAdminGroup,
                PermissionType.ADDITIVE, ADMIN_PERMS[i]);
        }

        if (!categoryConfiguration.isPublic()) {
            for (int i = 0; i < SW_BLOCK_PERMS.length; i++) {
                categoryPermissionsManager.addAnonymousUserPermission(PermissionType.NEGATIVE,
                    SW_BLOCK_PERMS[i]);
                categoryPermissionsManager.addRegisteredUserPermission(PermissionType.NEGATIVE,
                    SW_BLOCK_PERMS[i]);
            }
        }
    }

    /**
     * <p>
     * Uses the group manager to get the user group for the specified category.
     * </p>
     *
     * @param groupManager the group manager to get group from
     * @param categoryId the category's id
     * @return the user group of the category
     * @throws GroupNotFoundException if the user group doesn't exist
     */
    private Group getUserGroup(GroupManager groupManager, long categoryId)
        throws GroupNotFoundException {
        return groupManager.getGroup(new StringBuilder(
                GROUP_SOFTWARE_USERS_PREFIX).append(categoryId).toString());
    }

    /**
     * <p>
     * Uses the group manager to get the moderator group for the specified category.
     * </p>
     *
     * @param groupManager the group manager to get group from
     * @param categoryId the category's id
     * @return the moderator group of the category
     * @throws GroupNotFoundException if the moderator group doesn't exist
     */
    private Group getModeratorGroup(GroupManager groupManager, long categoryId)
        throws GroupNotFoundException {
        return groupManager.getGroup(new StringBuilder(
                GROUP_SOFTWARE_MODERATORS_PREFIX).append(categoryId).toString());
    }

    /**
     * <p>
     * Gets the user from user manager by the userId.
     * </p>
     *
     * @param userId the user's id
     * @return the user with the specified id
     * @throws UserNotFoundException if the user with specified userId doesn't exist
     * @throws JiveForumManagementException if the user manager is null
     */
    private User getUserSafe(long userId) throws JiveForumManagementException {
        try {
            UserManager userManager = forumFactory.getUserManager();

            validateNotNull(userManager, "userManager");

            return userManager.getUser(userId);
        } catch (com.jivesoftware.base.UserNotFoundException e) {
            throw new UserNotFoundException("The user with id '" + userId + "' doesn't exist.", e);
        }
    }

    /**
     * <p>
     * Checks whether the obj is null. If the obj is null, <code>JiveForumManagementException</code>
     * is thrown.
     * </p>
     *
     * @param obj the object instance to check
     * @param name the name of the obj
     * @throws JiveForumManagementException if the obj is null
     */
    private void validateNotNull(final Object obj, final String name)
        throws JiveForumManagementException {
        if (obj == null) {
            throw new JiveForumManagementException("The '" + name + "' should not be null.");
        }
    }

    /**
     * <p>
     * Gets the group manager in the rorumFactory. If group manager doesn't exist, <code>
     * JiveForumManagementException</code> is thrown.
     * </p>
     *
     * @return the group manager in the forumFactory
     * @throws JiveForumManagementException if group manager doesn't exist
     */
    private GroupManager getGroupManagerSafe() throws JiveForumManagementException {
        GroupManager groupManager = forumFactory.getGroupManager();
        validateNotNull(groupManager, "groupManager");

        return groupManager;
    }
}
