/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.ejb.mock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.topcoder.forum.service.CategoryConfiguration;
import com.topcoder.forum.service.EntityType;
import com.topcoder.forum.service.UserRole;
import com.topcoder.forum.service.ejb.JiveForumServiceLocal;
import com.topcoder.forum.service.ejb.JiveForumServiceRemote;
import com.topcoder.util.errorhandling.ExceptionUtils;


/**
 * <p>
 * This is a mock <code>JiveForumService</code> implementation that will keep an in-memory storage of the
 * watches, user roles, and categories created. This will be used for testing the services without having to set up a
 * full <strong>Jive</strong> installation. For instance, when a user calls <code>setUserRole</code>, they should then
 * be able to call <code>getUserRole</code> to retrieve the role just added. No interactions with the <strong>Jive
 * API</strong> are necessary.
 * </p>
 *
 * <p>
 * It is a stateful <strong>EJB</strong>, it implements <code>JiveForumServiceLocal</code> and
 * <code>JiveForumServiceRemote</code> interfaces.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * This class is thread safe because EJB container will properly handle thread safety issues.
 * </p>
 *
 * <p>Examples of usage of <code>MockJiveForumService</code> :
 *    <pre>
 *      // create mock service in programmatic way
 *      MockJiveForumService service = new MockJiveForumService();
 *
 *      // get service bean from EJB container
 *      InitialContext ctx = new InitialContext();
 *      JiveForumServiceRemote service = (JiveForumServiceRemote) ctx.lookup("JiveForumService/remote");
 *
 *      // add a watch, note that the watch is just stored in-memory
 *      long userId = 123;
 *      long entityId = 456;
 *      service.watch(userId, entityId, EntityType.FORUM);
 *
 *      // test if user is watch a entity, true should be returned
 *      boolean watching = service.isWatched(userId, entityId, EntityType.FORUM);
 *
 *      // false should be returned
 *      watching = service.isWatched(userId, entityId, EntityType.FORUM_THREAD);
 *
 *      // false should be returned
 *      watching = service.isWatched(userId, 789, EntityType.FORUM);
 *
 *      long categoryId = 3;
 *      service.setUserRole(userId, categoryId, UserRole.NO_ACCESS);
 *
 *      // get user role
 *      // UserRole.NO_ACCESS should be returned
 *      UserRole role = service.getUserRole(userId, categoryId);
 *
 *      //create category configuration
 *      CategoryConfiguration categoryConfig = create category configuration;
 *      long newCatId = service.createCategory(categoryConfig);
 *    </pre>
 * </p>
 *
 * <p>Examples of EJB configuration file :
 *   <pre>
 *     &lt;enterprise-beans&gt;
 *       &lt;session&gt;
 *         &lt;ejb-name&gt;JiveForumService&lt;/ejb-name&gt;
 *         &lt;remote&gt;
 *           com.topcoder.forum.service.ejb.JiveForumServiceRemote
 *         &lt;/remote&gt;
 *         &lt;local&gt;
 *           com.topcoder.forum.service.ejb.JiveForumServiceLocal
 *         &lt;/local&gt;
 *         &lt;ejb-class&gt;
 *           com.topcoder.forum.service.ejb.mock.MockJiveForumService
 *         &lt;/ejb-class&gt;
 *         &lt;session-type&gt;Stateful&lt;/session-type&gt;
 *         &lt;transaction-type&gt;Container&lt;/transaction-type&gt;
 *       &lt;/session&gt;
 *     &lt;/enterprise-beans&gt;
 *   </pre>
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
@Stateful
public class MockJiveForumService implements JiveForumServiceLocal, JiveForumServiceRemote {
    /**
     * <p>
     * This variable stores all the watches.<br/>
     * The keys are non-null user ids.<br/>
     * The values are mappings from entity types to watched entity ids.<br/>
     * Value maps are not null, not empty; keys of the value maps are not null, values of the value maps
     * are not null not empty sets of watched non-null entity ids.<br/>
     * Its reference is not changed but the content can be changed.<br/>
     * It is used in the watch and <code>isWatched</code> methods.
     * </p>
     */
    private final Map<Long, Map<EntityType, Set<Long>>> watches = new HashMap<Long, Map<EntityType, Set<Long>>>();

    /**
     * <p>
     * This variable stores all the user roles.<br/>
     * The keys are non-null user ids.<br/>
     * The values are mappings from category ids to user roles.<br/>
     * Value maps are not null, not empty; keys and values of the value maps are not null.<br/>
     * Its reference is not changed but the content can be changed.<br/>
     * It is used in the <code>getUserRole</code> and <code>setUserRole</code> methods.
     * </p>
     */
    private final Map<Long, Map<Long, UserRole>> userRoles = new HashMap<Long, Map<Long, UserRole>>();

    /**
     * <p>
     * This variable stores the next category id.<br/>
     * It is initialized to 1 and incremented by 1 whenever a category is created.<br/>
     * It is incremented in the <code>createCategory</code> method.
     * </p>
     */
    private long nextCategoryId = 1;

    /**
     * <p>Default empty constructor.</p>
     */
    public MockJiveForumService() {
        //do nothing
    }

    /**
     * <p>
     * Create a watch for a user in a category, forum or thread. Given a user id and either a category, forum or
     * thread id, if the user was not previously watching it, add the watch.<br/>
     * This method only stores the watch in the <code>watches</code> variable.<br/>
     * This method has been annotated with <strong>TransactionAttribute(TransactionAttributeType.REQUIRED)</strong>.
     * </p>
     *
     * @param userId the user id
     * @param entityId the entity id
     * @param entityType the entity type
     *
     * @throws IllegalArgumentException if the 'userId' is less than or equal to 0, or 'entityId' is less then or equal
     *         to 0, or 'entityType' is null.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void watch(long userId, long entityId, EntityType entityType) {
        //check parameter 'userId' positive
        checkPositive(userId, "userId");

        //check parameter 'entityId' positive
        checkPositive(entityId, "entityId");

        //check parameter 'entityType' not null
        ExceptionUtils.checkNull(entityType, null, null, "The parameter 'entityType' can not be null.");

        Map<EntityType, Set<Long>> watch = watches.get(userId);

        if (watch == null) {
            //create an empty 'watch' map if it is null
            watch = new HashMap<EntityType, Set<Long>>();
            //put the created empty 'watch' map to 'watches' map with 'userId' as key
            watches.put(userId, watch);
        }

        Set<Long> entityIds = watch.get(entityType);

        if (entityIds == null) {
            //create an empty 'entityIds' set if it is null
            entityIds = new HashSet<Long>();
            //put the created empty 'entityIds' to 'watch' map with 'entityType' as key
            watch.put(entityType, entityIds);
        }

        //add 'eneityId' to watch list if it does not exist before
        entityIds.add(entityId);
    }

    /**
     * <p>
     * Get whether a user is watching a category, forum or thread.<br/>
     * Given a user id and either a category, forum or thread id, return whether the user is already
     * watching it.<br/>
     * Note that the watches are only stored in the <code>watches</code> variable.<br/>
     * This method has been annotated with <strong>TransactionAttribute(TransactionAttributeType.REQUIRED)</strong>.
     * </p>
     *
     * @param userId the user id
     * @param entityId the entity id
     * @param entityType the entity type
     *
     * @return whether the entity is watched by the user
     *
     * @throws IllegalArgumentException if the 'userId' is less than or equal to 0, or 'entityId' is less then or equal
     *         to 0, or 'entityType' is null.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean isWatched(long userId, long entityId, EntityType entityType) {
        //check parameter 'userId' positive
        checkPositive(userId, "userId");

        //check parameter 'entityId' positive
        checkPositive(entityId, "entityId");

        //check parameter 'entityType' not null
        ExceptionUtils.checkNull(entityType, null, null, "The parameter 'entityType' can not be null.");

        Map<EntityType, Set<Long>> watch = watches.get(userId);
        if (watch == null) {
            //return false if the 'watch' map has not been created
            return false;
        }

        Set<Long> entityIds = watch.get(entityType);
        if (entityIds == null) {
            //return false if the 'entityIds' set has not been created
            return false;
        }

        //return whether the entity is watched by the user
        return entityIds.contains(entityId);
    }

    /**
     * <p>
     * Set User Role in Category.<br/>
     * Given a user id and a category id, one of these roles can be assigned.<br/>
     * If the role of the specified category for a user exists, it will be replaced by the new one.<br/>
     * No access: the user must not belong to moderators or users groups.<br/>
     * Contributor: the user must belong just to users group.<br/>
     * Moderator: the user must belong just to moderators group.<br/>
     * Note that the user roles are stored in the <code>userRoles</code> variable.<br/>
     * This method should be annotated with <strong>TransactionAttribute(TransactionAttributeType.REQUIRED)</strong>.
     * </p>
     *
     * @param userId the user id
     * @param categoryId the category id
     * @param role the role to set
     *
     * @throws IllegalArgumentException if the 'userId' is less than or equal to 0 or 'categoryId' is less than or
     *         equal to 0, or the 'role' is null.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void setUserRole(long userId, long categoryId, UserRole role) {
        //check parameter 'userId' positive
        checkPositive(userId, "userId");

        //check parameter 'categoryId' positive
        checkPositive(categoryId, "categoryId");

        //check parameter 'role' not null
        ExceptionUtils.checkNull(role, null, null, "The parameter 'role' can not be null.");

        Map<Long, UserRole> userRole = userRoles.get(userId);

        if (userRole == null) {
            //create an empty 'userRole' map if it is null
            userRole = new HashMap<Long, UserRole>();
            //put the created empty 'userRole' map to 'userRoles' with 'userId' as key
            userRoles.put(userId, userRole);
        }

        userRole.put(categoryId, role);
    }

    /**
     * <p>
     * Get User Role for a Category.<br/>
     * Given a user id and a category id, return whether the user has no access, is contributor or
     * moderator, depending on the groups he belong.<br/>
     * <strong>UserRole.NO_ACCESS</strong> is returned if no user role is present.<br/>
     * Note that the user roles are stored in the <code>userRoles</code> variable.<br/>
     * This method has been annotated with <strong>TransactionAttribute(TransactionAttributeType.REQUIRED)</strong>.
     * </p>
     *
     * @param userId the user id
     * @param categoryId the category id
     *
     * @return role of the user on the category
     *
     * @throws IllegalArgumentException if the 'userId' is less than or equal to 0 or 'categoryId' is less than or
     *         equal to 0.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UserRole getUserRole(long userId, long categoryId) {
        //check parameter 'userId' positive
        checkPositive(userId, "userId");

        //check parameter 'categoryId' positive
        checkPositive(categoryId, "categoryId");

        Map<Long, UserRole> userRole = userRoles.get(userId);

        if (userRole == null) {
            //return NO_ACCESS if no user role is present
            return UserRole.NO_ACCESS;
        }

        UserRole role = userRole.get(categoryId);

        if (role == null) {
            //return NO_ACCESS if no user role is present
            return UserRole.NO_ACCESS;
        } else {
            return role;
        }
    }

    /**
     * <p>
     * Create Software Forums category from the given category configuration.<br/>
     * This method creates category logically, simply consumes the next category id, and if a Moderator
     * User Id is specified, then a watch for the user is created for the category, its user role is also created for
     * the category.<br/>
     * This method has been annotated with <strong>TransactionAttribute(TransactionAttributeType.REQUIRED)</strong>.
     * </p>
     *
     * @param categoryConfiguration the category configuration to create
     *
     * @return the id of the created category
     *
     * @throws IllegalArgumentException if categoryConfiguration is null, or its name/description/versionText is
     *         null/empty string, or its componentId/versionId/rootCategoryId is less than or equal to 0.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long createCategory(CategoryConfiguration categoryConfiguration) {
        //check parameter 'categoryConfiguration' is not null
        ExceptionUtils.checkNull(categoryConfiguration, null, null,
            "The parameter 'categoryConfiguration' should not be null.");

        //check 'categoryConfiguration.name' is not null/empty string
        ExceptionUtils.checkNullOrEmpty(categoryConfiguration.getName(), null, null,
            "The parameter 'categoryConfiguration.name' should not be null/empty.");

        //check 'categoryConfiguration.description' is not null/empty string
        ExceptionUtils.checkNullOrEmpty(categoryConfiguration.getDescription(), null, null,
            "The parameter 'categoryConfiguration.description' should not be null/empty.");

        //check 'categoryConfiguration.versionText' is not null/empty string
        ExceptionUtils.checkNullOrEmpty(categoryConfiguration.getVersionText(), null, null,
            "The parameter 'categoryConfiguration.versionText' should not be null/empty.");

        //check 'categoryConfiguration.componentId' is positive
        checkPositive(categoryConfiguration.getComponentId(), "categoryConfiguration.componentId");

        //check 'categoryConfiguration.versionId' is positive
        checkPositive(categoryConfiguration.getVersionId(), "categoryConfiguration.versionId");

        //check 'categoryConfiguration.rootCategoryId' is positive
        checkPositive(categoryConfiguration.getRootCategoryId(), "categoryConfiguration.rootCategoryId");

        long categoryId = nextCategoryId++;

        // if moderator user id is specified
        long moderatorUserId = categoryConfiguration.getModeratorUserId();
        if (moderatorUserId > 0) {
            // create a watch
            watch(moderatorUserId, categoryId, EntityType.FORUM_CATEGORY);
            // create role
            setUserRole(moderatorUserId, categoryId, UserRole.MODERATOR);
        }

        return categoryId;
    }

    /**
     * <p>Check the number is positive or not.</p>
     *
     * @param number the number value
     * @param paramName the parameter name.
     *
     * @throws IllegalArgumentException if parameter is less than or equal to 0.
     */
    private void checkPositive(long number, String paramName) {
        if (number <= 0) {
            throw new IllegalArgumentException("The parameter '" + paramName
                + "' should not be less than or equal to 0.");
        }
    }
}
