/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.ejb.bean;

import com.topcoder.forum.service.CategoryConfiguration;
import com.topcoder.forum.service.CategoryType;
import com.topcoder.forum.service.EntityType;
import com.topcoder.forum.service.JiveForumManagementException;
import com.topcoder.forum.service.JiveForumManager;
import com.topcoder.forum.service.UserRole;
import com.topcoder.forum.service.ejb.JiveForumServiceLocal;
import com.topcoder.forum.service.ejb.JiveForumServiceRemote;
import com.topcoder.forum.service.ejb.ServiceConfigurationException;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * <p>
 * This bean class implements the <code>JiveForumServiceLocal</code> and <code>JiveForumServiceRemote</code>
 * interfaces. It is a stateless session bean with <code>Stateless</code> annotation. It will simply delegate
 * to the <code>JiveForumManager</code> to do the job.  NOTE: the transaction is managed by the container.
 * </p>
 *
 * <p>
 * The bean will read 5 requried parameters from the context to create the <code>JiveForumManager</code>.
 * The parameters are in type<code>java.lang.String</code>. For example:<br/>
 * <pre>
 * &lt;env-entry&gt;
 *     &lt;env-entry-name&gt;AdminUserId&lt;/env-entry-name&gt;
 *     &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *     &lt;env-entry-value&gt;1&lt;/env-entry-value&gt;
 * &lt;/env-entry&gt;
 * &lt;env-entry&gt;
 *     &lt;env-entry-name&gt;ApplicationCategoryId&lt;/env-entry-name&gt;
 *     &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *     &lt;env-entry-value&gt;1&lt;/env-entry-value&gt;
 * &lt;/env-entry&gt;
 * &lt;env-entry&gt;
 *     &lt;env-entry-name&gt;ComponentCategoryId&lt;/env-entry-name&gt;
 *     &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *     &lt;env-entry-value&gt;1&lt;/env-entry-value&gt;
 * &lt;/env-entry&gt;
 * &lt;env-entry&gt;
 *     &lt;env-entry-name&gt;AssemblyCompetitionCategoryId&lt;/env-entry-name&gt;
 *     &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *     &lt;env-entry-value&gt;1&lt;/env-entry-value&gt;
 * &lt;/env-entry&gt;
 * &lt;env-entry&gt;
 *     &lt;env-entry-name&gt;TestingCompetitionCategoryId&lt;/env-entry-name&gt;
 *     &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *     &lt;env-entry-value&gt;1&lt;/env-entry-value&gt;
 * &lt;/env-entry&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * The variables in this class are initialized once in the initialize method after the bean is instantiated
 * by EJB container. They would be never be changed afterwards. So they won't affect the thread-safety of this
 * class when its EJB methods are called. So this class can be used thread-safely in EJB container.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
public class JiveForumServiceBean implements JiveForumServiceLocal,
    JiveForumServiceRemote {
    /**
     * <p>The env entry name for AdminUserId parameter.</p>
     */
    private static final String ENV_ADMIN_USER_ID = "AdminUserId";

    /**
     * <p>The env entry name for ApplicationCategoryId parameter.</p>
     */
    private static final String ENV_APP_ID = "ApplicationCategoryId";

    /**
     * <p>The env entry name for ComponentCategoryId parameter.</p>
     */
    private static final String ENV_COMP_ID = "ComponentCategoryId";

    /**
     * <p>The env entry name for AssemblyCompetitionCategoryId parameter.</p>
     */
    private static final String ENV_ASM_ID = "AssemblyCompetitionCategoryId";

    /**
     * <p>The env entry name for TestingCompetitionCategoryId parameter.</p>
     */
    private static final String ENV_TEST_ID = "TestingCompetitionCategoryId";

    /**
     * <p>
     * This field represents the <code>JiveForumManager</code> instance to manage the Jive Forum.
     * It will be initialized in the initialize method and never changed afterwards. It's non-null
     * after initialized.
     * </p>
     */
    private JiveForumManager manager;

    /**
     * <p>
     * This field represents the <code>SessionContext</code> injected by the EJB container automatically.
     * It is marked with <code>Resource</code> annotation. It's non-null after injected when this bean
     * is instantiated. And its reference is not changed afterwards. It is used in the initialize method
     * to lookup JNDI resources.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>Creates an instance, it does nothing.</p>
     */
    public JiveForumServiceBean() {
    }

    /**
     * <p>
     * Creates a watch for a user in a category, forum or thread. Given a user id and either a category,
     * forum or thread id, if the user was not previously watching it, add the watch.
     * </p>
     *
     * <p>
     * This method should be marked with <code>TransactionAttribute(TransactionAttributeType.REQUIRED)</code>
     * to indicate the transaction is required.
     * </p>
     *
     * @param userId the user id
     * @param entityId the entity id
     * @param entityType the entity type
     *
     * @throws IllegalArgumentException if the userId or entityId isn't above 0, or if entityType is null
     * @throws UserNotFoundException if there is no user with the given userId in jive forum
     * @throws ForumEntityNotFoundException if there is no entity for the given entityId in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIs
    */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void watch(long userId, long entityId, EntityType entityType)
        throws JiveForumManagementException {
        manager.watch(userId, entityId, entityType);
    }

    /**
     * <p>
     * Gets whether a user is watching a category, forum or thread. Given a user id and either a category,
     * forum or thread id, return whether the user is already watching it.
     * </p>
     *
     * <p>
     * This method should be marked with <code>TransactionAttribute(TransactionAttributeType.REQUIRED)</code>
     * to indicate the transaction is required.
     * </p>
     *
     * @param userId the user id
     * @param entityId the entity id
     * @param entityType the entity type
     * @return true if the forum entity is watched, false otherwise
     *
     * @throws IllegalArgumentException if the userId or entityId isn't above 0, or if entityType is null
     * @throws UserNotFoundException if there is no user with the given userId in jive forum
     * @throws ForumEntityNotFoundException if there is no entity for the given entityId in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIs
    */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean isWatched(long userId, long entityId, EntityType entityType)
        throws JiveForumManagementException {
        return manager.isWatched(userId, entityId, entityType);
    }

    /**
     * <p>
     * Sets User Role in Category. Given a user id and a category id, one of these roles can be assigned:<br/>
     * No access: the user must not belong to moderators or users groups<br/>
     * Contributor: the user must belong just to users group<br/>
     * Moderator: the user must belong just to moderators group
     * </p>
     *
     * <p>
     * This method should be marked with <code>TransactionAttribute(TransactionAttributeType.REQUIRED)</code>
     * to indicate the transaction is required.
     * </p>
     *
     * @param userId the user id
     * @param categoryId the category id
     * @param role the role
     *
     * @throws IllegalArgumentException if the userId or categoryId isn't above 0, or if the role is null
     * @throws UserNotFoundException if there is no user with the given userId in the jive forum
     * @throws ForumEntityNotFoundException if there is no category for the given categoryId in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIS
    */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void setUserRole(long userId, long categoryId, UserRole role)
        throws JiveForumManagementException {
        manager.setUserRole(userId, categoryId, role);
    }

    /**
     * <p>
     * Gets User Role for a Category. Given a user id and a category id, return whether the
     * user has no access, is contributor or moderator, depending on the groups he belong.
     * </p>
     *
     * <p>
     * This method should be marked with <code>TransactionAttribute(TransactionAttributeType.REQUIRED)</code>
     * to indicate the transaction is required.
     * </p>
     *
     * @param userId the user id.
     * @param categoryId the category id.
     * @return the user role.
     * @throws IllegalArgumentException if the userId or categoryId isn't above 0
     * @throws UserNotFoundException if there is no user with the given userId in jive forum
     * @throws ForumEntityNotFoundException if there is no category for the given categoryId in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIS
    */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UserRole getUserRole(long userId, long categoryId)
        throws JiveForumManagementException {
        return manager.getUserRole(userId, categoryId);
    }

    /**
     * <p>
     * Creates a new category according to the category configuration. It creates a new category, and if
     * a template category is specified, it will also copy all the contents. It will create the category
     * and set its properties and then create groups and set permissions. User should provide either template
     * category id or template category type if a template category is necessary.
     * </p>
     *
     * <p>
     * This method should be marked with <code>TransactionAttribute(TransactionAttributeType.REQUIRED)</code>
     * to indicate the transaction is required.
     * </p>
     *
     * @param categoryConfiguration category configuration.
     * @return the newly created category id.
     * @throws IllegalArgumentException
     *         if categoryConfiguration is null, or its name/description/versionText is null/empty string, or
     *         if its componentId/versionId/rootCategoryId is isn't above 0
     * @throws UserNotFoundException if there is no user with given moderationUserId in jive forum
     * @throws ForumEntityNotFoundException
     *         if there is no category for the given template category id / root category id in jive forum
     * @throws JiveForumManagementException if any other error occurs when calling jive forum APIs
    */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long createCategory(CategoryConfiguration categoryConfiguration)
        throws JiveForumManagementException {
        return manager.createCategory(categoryConfiguration);
    }

    /**
     * <p>
     * This method is called after this bean is constructed by the EJB container. This method should be
     * marked with <code>PostConstruct</code> annotation. This method will load the configured values to
     * create the <code>JiveForumManager</code> object.
     * </p>
     *
     * @throws ServiceConfigurationException
     *         if any required parameter is missing, or any configured value is invalid,
     *         and it is also used to wrap the exceptions from JiveForumManager
     */
    @PostConstruct
    public void initialize() {
        try {
            long adminUserId = Long.parseLong((String) sessionContext.lookup(
                        ENV_ADMIN_USER_ID));

            Map<CategoryType, Long> categoryTemplateIds = new HashMap<CategoryType, Long>();
            categoryTemplateIds.put(CategoryType.APPLICATION,
                new Long((String) sessionContext.lookup(ENV_APP_ID)));
            categoryTemplateIds.put(CategoryType.ASSEMBLY_COMPETITION,
                new Long((String) sessionContext.lookup(ENV_ASM_ID)));
            categoryTemplateIds.put(CategoryType.COMPONENT,
                new Long((String) sessionContext.lookup(ENV_COMP_ID)));
            categoryTemplateIds.put(CategoryType.TESTING_COMPETITION,
                new Long((String) sessionContext.lookup(ENV_TEST_ID)));

            manager = new JiveForumManager(adminUserId, categoryTemplateIds);
        } catch (NumberFormatException e) {
            throw new ServiceConfigurationException("All configuration items should be valid long value.",
                e);
        } catch (ClassCastException e) {
            throw new ServiceConfigurationException("The configuration items should be string value.",
                e);
        } catch (IllegalArgumentException e) {
            throw new ServiceConfigurationException("The ids should be valid for the jive forum.",
                e);
        } catch (JiveForumManagementException e) {
            throw new ServiceConfigurationException("Unable to create JiveForumManager instance.",
                e);
        }
    }
}
