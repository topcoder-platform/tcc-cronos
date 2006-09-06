/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.accuracytests;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistence;
import com.topcoder.management.resource.persistence.sql.SqlResourcePersistence;
import com.topcoder.util.config.ConfigManager;

/**
 * Accuracy test fixture for SqlResourcePersistence class.
 * @author Thinfox
 * @version 1.0
 */
public class SqlResourcePersistenceTests extends TestCase {
    /**
     * Represents the default creator name used for tests.
     */
    private static final String TEST_CREATOR = "topcoder";

    /**
     * Represents the default modifier name used for tests.
     */
    private static final String TEST_MODIFIER = "tc";

    /**
     * The default DBConnectionFactory instance used for tests.
     */
    private DBConnectionFactory dbFactory = null;

    /**
     * The default SqlResourcePersistence instance on which to perform tests.
     */
    private SqlResourcePersistence persistence = null;

    /**
     * Create a Resource instance for tests.
     * @param resourceId the id for resource
     * @param projectId the project id
     * @param phaseId the phase id
     * @param submissionId the submission id
     * @param operator name of the creator.
     * @return the Resource instance
     */
    private Resource createResource(long resourceId, long projectId, long phaseId, long submissionId,
        String operator) {
        Resource resource = new Resource(resourceId);

        Date now = new Date();
        resource.setCreationUser(operator);
        resource.setCreationTimestamp(now);
        resource.setModificationUser(operator);
        resource.setModificationTimestamp(now);

        resource.setProject(projectId <= 0 ? null : new Long(projectId));
        resource.setPhase(phaseId <= 0 ? null : new Long(phaseId));
        resource.setSubmission(submissionId <= 0 ? null : new Long(submissionId));
        resource.setPhase(null);
        resource.setSubmission(null);

        ResourceRole role = createResourceRole(resourceId, operator);

        resource.setResourceRole(role);

        return resource;
    }

    /**
     * Create a resource role instance for test.
     * @param id the id of the resource role.
     * @param operator name of the creator.
     * @return the ResourceRole instance.
     */
    private ResourceRole createResourceRole(long id, String operator) {
        ResourceRole role = new ResourceRole(id);
        role.setDescription("resource role");
        role.setName("topcoder");

        role.setPhaseType(new Long(id));

        Date now = new Date();

        role.setCreationUser(operator);
        role.setCreationTimestamp(now);
        role.setModificationUser(operator);
        role.setModificationTimestamp(now);

        return role;
    }

    /**
     * Create a notification type instance for test.
     * @param id the id of the notification type.
     * @param operator name of the creator.
     * @return the NotificationType instance.
     */
    private NotificationType createNotificationType(long id, String operator) {
        NotificationType type = new NotificationType(id);
        type.setName("topcoder");
        type.setDescription("notification type");

        Date now = new Date();

        type.setCreationUser(operator);
        type.setCreationTimestamp(now);
        type.setModificationUser(operator);
        type.setModificationTimestamp(now);

        return type;
    }

    /**
     * Validates the equality of two Resource instances.
     * @param expected the expected resource
     * @param actual the actual resource
     */
    private void validateResource(Resource expected, Resource actual) {
        assertEquals("Incorrect resource id.", expected.getId(), actual.getId());
        assertEquals("Incorrect submission.", expected.getSubmission(), actual.getSubmission());
        assertEquals("Incorrect project.", expected.getProject(), actual.getProject());
        assertEquals("Incorrect phase.", expected.getPhase(), actual.getPhase());

        Set expectedPropertyKeys = expected.getAllProperties().keySet();

        assertEquals("Incorrect count of properties.", expectedPropertyKeys.size(), actual.getAllProperties()
            .size());

        for (Iterator itr = expectedPropertyKeys.iterator(); itr.hasNext();) {
            String key = (String) itr.next();
            assertEquals("Incorrect property.", expected.getProperty(key), actual.getProperty(key));
        }

        validateResourceRole(expected.getResourceRole(), actual.getResourceRole());

        assertEquals("Incorrect creation user.", expected.getCreationUser(), actual.getCreationUser());
        assertEquals("Incorrect modification user.", expected.getModificationUser(), actual
            .getModificationUser());
    }

    /**
     * Validates the equality of two ResourceRole instances.
     * @param expected the expected ResourceRole
     * @param actual the actual ResourceRole
     */
    private void validateResourceRole(ResourceRole expected, ResourceRole actual) {
        assertEquals("Incorrect resource role id.", expected.getId(), actual.getId());
        assertEquals("Incorrect name.", expected.getName(), actual.getName());
        assertEquals("Incorrect phase type.", expected.getPhaseType(), actual.getPhaseType());
        assertEquals("Incorrect description.", expected.getDescription(), actual.getDescription());

        assertEquals("Incorrect creation user.", expected.getCreationUser(), actual.getCreationUser());
        assertEquals("Incorrect modification user.", expected.getModificationUser(), actual
            .getModificationUser());
    }

    /**
     * Validates the equality of two NotificationType instances.
     * @param expected the expected ResourceRole
     * @param actual the actual ResourceRole
     */
    private void validateNotificationType(NotificationType expected, NotificationType actual) {
        assertEquals("Incorrect notification type id.", expected.getId(), actual.getId());
        assertEquals("Incorrect name.", expected.getName(), actual.getName());
        assertEquals("Incorrect description.", expected.getDescription(), actual.getDescription());

        assertEquals("Incorrect creation user.", expected.getCreationUser(), actual.getCreationUser());
        assertEquals("Incorrect modification user.", expected.getModificationUser(), actual
            .getModificationUser());
    }

    /**
     * Sets up the test environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfiguration();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("accuracy/DBConnectionFactory.xml");

        AccuracyTestHelper.initTables();
        dbFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        persistence = new SqlResourcePersistence(dbFactory);
    }

    /**
     * Cleans up the test environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.clearTables();
        AccuracyTestHelper.clearConfiguration();
    }

    /**
     * Tests whether SqlResourcePersistence implements the ResourcePersistence interface.
     * @throws Exception to JUnit
     */
    public void testInheritance() throws Exception {
        assertTrue("SqlResourcePersistence should implement ResourcePersistence.",
            new SqlResourcePersistence(dbFactory) instanceof ResourcePersistence);
    }

    /**
     * Tests the SqlResourcePersistence(DBConnectionFactory) constructor.
     * @throws Exception to JUnit
     */
    public void testCtor_DBConnectionFactory() throws Exception {
        assertNotNull("Creation failed.", new SqlResourcePersistence(dbFactory));
    }

    /**
     * Tests the SqlResourcePersistence(DBConnectionFactory, String) constructor.
     * @throws Exception to JUnit
     */
    public void testCtor_DBConnectionFactoryString() throws Exception {
        assertNotNull("Unable to create SqlResourcePersistence.", new SqlResourcePersistence(dbFactory,
            "InformixConnection"));
    }

    /**
     * Tests the addResource(Resource) method.
     * @throws Exception to JUnit
     */
    public void testAddResource_WithoutSubmissionAndProperties() throws Exception {
        Resource resource = createResource(1, -1, -1, -1, TEST_CREATOR);

        persistence.addResourceRole(resource.getResourceRole());
        persistence.addResource(resource);

        Resource retrieved = persistence.loadResource(resource.getId());

        validateResource(resource, retrieved);
    }

    /**
     * Tests the addResource(Resource) method.
     * @throws Exception to JUnit
     */
    public void testAddResource_WithSubmissionAndProperties() throws Exception {
        Resource resource = createResource(1, 1, 1, 1, TEST_CREATOR);
        resource.setProperty("name1", "value1");
        resource.setSubmission(new Long(1));

        persistence.addResourceRole(resource.getResourceRole());
        persistence.addResource(resource);

        Resource retrieved = persistence.loadResource(resource.getId());

        validateResource(resource, retrieved);
    }

    /**
     * Tests the addResource(Resource) method.
     * @throws Exception to JUnit
     */
    public void testAddResource_InexistantInfoType() throws Exception {
        Resource resource = createResource(1, 1, 1, 1, TEST_CREATOR);
        resource.setProperty("name", "value");

        persistence.addResourceRole(resource.getResourceRole());
        persistence.addResource(resource);

        Resource retrieved = persistence.loadResource(resource.getId());

        resource = createResource(1, 1, 1, 1, TEST_CREATOR);

        validateResource(resource, retrieved);
    }

    /**
     * Tests the updateResource(Resource) method.
     * @throws Exception to JUnit
     */
    public void testUpdateResource_AddingSubmissionAndProperties() throws Exception {

        Resource resource = createResource(1, -1, -1, -1, TEST_CREATOR);

        persistence.addResourceRole(resource.getResourceRole());
        persistence.addResource(resource);

        resource = createResource(1, 1, 1, 1, TEST_CREATOR);
        resource.setModificationUser(TEST_MODIFIER);
        resource.setProperty("name1", "value1");
        resource.setSubmission(new Long(1));

        persistence.updateResource(resource);
        Resource retrieved = persistence.loadResource(resource.getId());

        validateResource(resource, retrieved);
    }

    /**
     * Tests the updateResource(Resource) method.
     * @throws Exception to JUnit
     */
    public void testUpdateResource_DeletingSubmissionAndProperties() throws Exception {
        Resource resource = createResource(1, 1, 1, 1, TEST_CREATOR);
        resource.setProperty("name1", "value1");
        resource.setSubmission(new Long(1));

        persistence.addResourceRole(resource.getResourceRole());
        persistence.addResource(resource);

        resource = createResource(1, -1, -1, -1, TEST_CREATOR);
        resource.setModificationUser(TEST_MODIFIER);

        persistence.updateResource(resource);
        Resource retrieved = persistence.loadResource(resource.getId());

        validateResource(resource, retrieved);
    }

    /**
     * Tests the updateResource(Resource) method.
     * @throws Exception to JUnit
     */
    public void testUpdateResource_UpdatingSubmissionAndProperties() throws Exception {
        Resource resource = createResource(1, 1, 1, 1, TEST_CREATOR);
        resource.setProperty("name1", "value1");
        resource.setSubmission(new Long(1));

        persistence.addResourceRole(resource.getResourceRole());
        persistence.addResource(resource);

        resource = createResource(1, 2, 2, 2, TEST_CREATOR);
        resource.setSubmission(new Long(2));
        resource.setModificationUser(TEST_MODIFIER);
        resource.setProperty("name1", "value2");

        persistence.updateResource(resource);
        Resource retrieved = persistence.loadResource(resource.getId());

        validateResource(resource, retrieved);
    }

    /**
     * Tests the loadResource(long) method.
     * @throws Exception to JUnit
     */
    public void testLoadResource_WithourSubmissionAndProperties() throws Exception {
        Resource resource = createResource(1, -1, -1, -1, TEST_CREATOR);

        persistence.addResourceRole(resource.getResourceRole());
        persistence.addResource(resource);

        Resource retrieved = persistence.loadResource(resource.getId());

        validateResource(resource, retrieved);
    }

    /**
     * Tests the loadResource(long) method.
     * @throws Exception to JUnit
     */
    public void testLoadResource_WithSubmissionAndProperties() throws Exception {
        Resource resource = createResource(1, 1, 1, 1, TEST_CREATOR);
        resource.setProperty("name1", "value1");
        resource.setSubmission(new Long(1));
        persistence.addResourceRole(resource.getResourceRole());
        persistence.addResource(resource);

        Resource retrieved = persistence.loadResource(resource.getId());

        validateResource(resource, retrieved);
    }

    /**
     * Tests the loadResource(long) method.
     * @throws Exception to JUnit
     */
    public void testLoadResource_Inexistant() throws Exception {
        Resource resource = createResource(1, -1, -1, -1, TEST_CREATOR);

        Resource retrieved = persistence.loadResource(resource.getId());
        assertNull("Should return null if there is no resource with the given id.", retrieved);
    }

    /**
     * Tests the loadResources(long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadResources_WithSubmissionAndProperties() throws Exception {
        final int resourceCount = 3;
        long[] ids = new long[resourceCount];
        Resource[] resources = new Resource[resourceCount];

        for (int i = 0; i < resourceCount; i++) {
            long id = i + 1;
            Resource resource = createResource(id, id, id, id, TEST_CREATOR);
            resource.setProperty("name1", "value1");
            resource.setSubmission(new Long(id));
            persistence.addResourceRole(resource.getResourceRole());
            persistence.addResource(resource);

            ids[i] = id;
            resources[i] = resource;
        }

        Resource[] retrieved = persistence.loadResources(ids);

        assertEquals("Incorrect count of resources.", resourceCount, retrieved.length);
        for (int i = 0; i < 3; i++) {
            validateResource(resources[i], retrieved[i]);
        }
    }

    /**
     * Tests the loadResource(long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadResources_WithoutSubmissionAndProperties() throws Exception {
        final int resourceCount = 3;
        long[] ids = new long[resourceCount];
        Resource[] resources = new Resource[resourceCount];

        for (int i = 0; i < resourceCount; i++) {
            long id = i + 1;
            Resource resource = createResource(id, -1, -1, -1, TEST_CREATOR);
            persistence.addResourceRole(resource.getResourceRole());
            persistence.addResource(resource);

            ids[i] = id;
            resources[i] = resource;
        }

        Resource[] retrieved = persistence.loadResources(ids);

        assertEquals("Incorrect count of resources.", resourceCount, retrieved.length);
        for (int i = 0; i < 3; i++) {
            validateResource(resources[i], retrieved[i]);
        }
    }

    /**
     * Tests the loadResource(long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadResources_Inexistant() throws Exception {
        long[] ids = new long[] {1, 2, 3};

        Resource[] retrieved = persistence.loadResources(ids);

        assertEquals("Incorrect count of resources.", 0, retrieved.length);
    }

    /**
     * Tests the loadResource(long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadResources_None() throws Exception {
        long[] ids = new long[0];
        Resource[] retrieved = persistence.loadResources(ids);
        assertEquals("Incorrect count of resources.", 0, retrieved.length);
    }

    /**
     * Tests the deleteResource(long) method.
     * @throws Exception to JUnit
     */
    public void testDeleteResource_Existant() throws Exception {
        Resource resource = createResource(1, 1, 1, 1, TEST_CREATOR);
        resource.setProperty("name1", "value1");
        resource.setSubmission(new Long(1));

        persistence.addResourceRole(resource.getResourceRole());
        persistence.addResource(resource);
        persistence.deleteResource(resource);

        Resource retrieved = persistence.loadResource(resource.getId());
        assertNull("The resource should be deleted.", retrieved);
    }

    /**
     * Tests the deleteResource(long) method.
     * @throws Exception to JUnit
     */
    public void testDeleteResource_Inexistant() throws Exception {
        Resource resource = createResource(1, -1, -1, -1, TEST_CREATOR);

        persistence.deleteResource(resource);

        Resource retrieved = persistence.loadResource(resource.getId());
        assertNull("The resource should be deleted.", retrieved);
    }

    /**
     * Tests the addResourceRole(ResourceRole) method.
     * @throws Exception to JUnit
     */
    public void testAddResourceRole() throws Exception {
        ResourceRole role = createResourceRole(1, TEST_CREATOR);

        persistence.addResourceRole(role);

        ResourceRole retrieved = persistence.loadResourceRole(role.getId());
        validateResourceRole(role, retrieved);
    }

    /**
     * Tests the updateResourceRole(ResourceRole) method.
     * @throws Exception to JUnit
     */
    public void testUpdateResourceRole() throws Exception {
        ResourceRole role = createResourceRole(1, TEST_CREATOR);

        persistence.addResourceRole(role);

        role.setName("NAME");
        role.setDescription("DESCRIPTION");
        role.setModificationUser(TEST_MODIFIER);

        persistence.updateResourceRole(role);

        ResourceRole retrieved = persistence.loadResourceRole(role.getId());
        validateResourceRole(role, retrieved);
    }

    /**
     * Tests the loadResourceRole(long) method.
     * @throws Exception to JUnit
     */
    public void testLoadResourceRole_Existant() throws Exception {
        ResourceRole role = createResourceRole(1, TEST_CREATOR);

        persistence.addResourceRole(role);

        ResourceRole retrieved = persistence.loadResourceRole(role.getId());
        validateResourceRole(role, retrieved);
    }

    /**
     * Tests the loadResourceRole(long) method.
     * @throws Exception to JUnit
     */
    public void testLoadResourceRole_Inexistant() throws Exception {
        ResourceRole role = createResourceRole(1, TEST_CREATOR);

        ResourceRole retrieved = persistence.loadResourceRole(role.getId());
        assertNull("Should return null if there is no resource role with the given id.", retrieved);
    }

    /**
     * Tests the loadResourceRoles(long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadResourceRoles_Existant() throws Exception {
        ResourceRole role = createResourceRole(1, TEST_CREATOR);

        persistence.addResourceRole(role);

        ResourceRole[] retrieved = persistence.loadResourceRoles(new long[] {role.getId()});
        assertEquals("Incorrect count of resource roles", 1, retrieved.length);
        validateResourceRole(role, retrieved[0]);
    }

    /**
     * Tests the loadResourceRoles(long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadResourceRoles_Inexistant() throws Exception {
        long[] ids = new long[] {1, 2, 3};
        ResourceRole[] retrieved = persistence.loadResourceRoles(ids);
        assertEquals("Incorrect count of resource roles.", 0, retrieved.length);
    }

    /**
     * Tests the loadResourceRoles(long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadResourceRoles_None() throws Exception {
        long[] ids = new long[0];
        ResourceRole[] retrieved = persistence.loadResourceRoles(ids);
        assertEquals("Incorrect count of resource roles.", 0, retrieved.length);
    }

    /**
     * Tests the deleteResourceRole(long) method.
     * @throws Exception to JUnit
     */
    public void testDeleteResourceRole_Existant() throws Exception {
        ResourceRole role = createResourceRole(1, TEST_CREATOR);

        persistence.addResourceRole(role);
        persistence.deleteResourceRole(role);

        ResourceRole retrieved = persistence.loadResourceRole(role.getId());
        assertNull("The resource role should be deleted.", retrieved);
    }

    /**
     * Tests the deleteResourceRole(long) method.
     * @throws Exception to JUnit
     */
    public void testDeleteResourceRole_Inexistant() throws Exception {
        ResourceRole role = createResourceRole(1, TEST_CREATOR);

        persistence.deleteResourceRole(role);

        ResourceRole retrieved = persistence.loadResourceRole(role.getId());
        assertNull("The resource role should be deleted.", retrieved);
    }

    /**
     * Tests the addNotificationType(NotificationType) method.
     * @throws Exception to JUnit
     */
    public void testAddNotificationType() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.addNotificationType(type);

        NotificationType retrieved = persistence.loadNotificationType(type.getId());
        validateNotificationType(type, retrieved);
    }

    /**
     * Tests the updateNotificationType(NotificationType) method.
     * @throws Exception to JUnit
     */
    public void testUpdateNotificationType() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.addNotificationType(type);

        type.setName("NAME");
        type.setDescription("DESCRIPTION");
        type.setModificationUser(TEST_MODIFIER);

        persistence.updateNotificationType(type);

        NotificationType retrieved = persistence.loadNotificationType(type.getId());
        validateNotificationType(type, retrieved);
    }

    /**
     * Tests the loadNotificationType(long) method.
     * @throws Exception to JUnit
     */
    public void testLoadNotificationType_Existant() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.addNotificationType(type);

        NotificationType retrieved = persistence.loadNotificationType(type.getId());
        validateNotificationType(type, retrieved);
    }

    /**
     * Tests the loadNotificationType(long) method.
     * @throws Exception to JUnit
     */
    public void testLoadNotificationType_Inexistant() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        NotificationType retrieved = persistence.loadNotificationType(type.getId());
        assertNull("Should return null if there is no notification type with the given id.", retrieved);
    }

    /**
     * Tests the loadNotificationTypes(long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadNotificationTypes_Existant() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.addNotificationType(type);

        NotificationType[] retrieved = persistence.loadNotificationTypes(new long[] {type.getId()});
        assertEquals("Incorrect count of notification types", 1, retrieved.length);
        validateNotificationType(type, retrieved[0]);
    }

    /**
     * Tests the loadNotificationTypes(long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadNotificationTypes_Inexistant() throws Exception {
        long[] ids = new long[] {1, 2, 3};
        NotificationType[] retrieved = persistence.loadNotificationTypes(ids);
        assertEquals("Incorrect count of notification types.", 0, retrieved.length);
    }

    /**
     * Tests the loadNotificationTypes(long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadNotificationTypes_None() throws Exception {
        long[] ids = new long[0];
        NotificationType[] retrieved = persistence.loadNotificationTypes(ids);
        assertEquals("Incorrect count of notification types.", 0, retrieved.length);
    }

    /**
     * Tests the deleteNotificationType(long) method.
     * @throws Exception to JUnit
     */
    public void testDeleteNotificationType_Existant() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.addNotificationType(type);
        persistence.deleteNotificationType(type);

        NotificationType retrieved = persistence.loadNotificationType(type.getId());
        assertNull("The notification type should be deleted.", retrieved);
    }

    /**
     * Tests the deleteNotificationType(long) method.
     * @throws Exception to JUnit
     */
    public void testDeleteNotificationType_Inexistant() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.deleteNotificationType(type);

        NotificationType retrieved = persistence.loadNotificationType(type.getId());
        assertNull("The notification type should be deleted.", retrieved);
    }

    /**
     * Tests the addNotification(long, long, long, String) method.
     * @throws Exception to JUnit
     */
    public void testAddNotification() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.addNotificationType(type);
        persistence.addNotification(1, 1, type.getId(), TEST_CREATOR);

        Notification retrieved = persistence.loadNotification(1, 1, type.getId());
        validateNotificationType(type, retrieved.getNotificationType());
        assertEquals("Incorrect creation user.", type.getCreationUser(), retrieved.getCreationUser());
        assertEquals("Incorrect modification user.", type.getModificationUser(), retrieved
            .getModificationUser());
    }

    /**
     * Tests the loadNotification(long, long, long) method.
     * @throws Exception to JUnit
     */
    public void testLoadNotification_Existant() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.addNotificationType(type);
        persistence.addNotification(1, 1, type.getId(), TEST_CREATOR);

        Notification retrieved = persistence.loadNotification(1, 1, type.getId());
        validateNotificationType(type, retrieved.getNotificationType());
        assertEquals("Incorrect creation user.", type.getCreationUser(), retrieved.getCreationUser());
        assertEquals("Incorrect modification user.", type.getModificationUser(), retrieved
            .getModificationUser());
    }

    /**
     * Tests the loadNotification(long, long, long) method.
     * @throws Exception to JUnit
     */
    public void testLoadNotification_Inexistant() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.addNotificationType(type);

        Notification retrieved = persistence.loadNotification(1, 1, type.getId());
        assertNull("Should return null if there there is no notification with given user, project and type.",
            retrieved);
    }

    /**
     * Tests the loadNotifications(long[], long[], long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadNotifications_Existant() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.addNotificationType(type);
        persistence.addNotification(1, 1, type.getId(), TEST_CREATOR);

        Notification[] retrieved = persistence.loadNotifications(new long[] {1}, new long[] {1},
            new long[] {type.getId()});

        assertEquals("Incorrect count of notifications.", 1, retrieved.length);
        validateNotificationType(type, retrieved[0].getNotificationType());
        assertEquals("Incorrect creation user.", type.getCreationUser(), retrieved[0].getCreationUser());
        assertEquals("Incorrect modification user.", type.getModificationUser(), retrieved[0]
            .getModificationUser());
    }

    /**
     * Tests the loadNotification(long[], long[], long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadNotifications_Inexistant() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.addNotificationType(type);

        Notification[] retrieved = persistence.loadNotifications(new long[] {1}, new long[] {1},
            new long[] {type.getId()});
        assertEquals("Incorrect count of notifications.", 0, retrieved.length);
    }

    /**
     * Tests the loadNotification(long[], long[], long[]) method.
     * @throws Exception to JUnit
     */
    public void testLoadNotifications_None() throws Exception {
        Notification[] retrieved = persistence.loadNotifications(new long[0], new long[0], new long[0]);
        assertEquals("Incorrect count of notifications.", 0, retrieved.length);
    }

    /**
     * Tests the removeNotification(long, long, long, String) method.
     * @throws Exception to JUnit
     */
    public void testRemoveNotification_Existant() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.addNotificationType(type);
        persistence.addNotification(1, 1, type.getId(), TEST_CREATOR);
        persistence.removeNotification(1, 1, type.getId(), TEST_CREATOR);

        Notification retrieved = persistence.loadNotification(1, 1, type.getId());
        assertNull("The notification should be deleted.", retrieved);
    }

    /**
     * Tests the removeNotification(long, long, long, String) method.
     * @throws Exception to JUnit
     */
    public void testRemoveNotification_Inexistant() throws Exception {
        NotificationType type = createNotificationType(1, TEST_CREATOR);

        persistence.addNotificationType(type);
        persistence.removeNotification(1, 1, type.getId(), TEST_CREATOR);

        Notification retrieved = persistence.loadNotification(1, 1, type.getId());
        assertNull("The notification should be deleted.", retrieved);
    }
}