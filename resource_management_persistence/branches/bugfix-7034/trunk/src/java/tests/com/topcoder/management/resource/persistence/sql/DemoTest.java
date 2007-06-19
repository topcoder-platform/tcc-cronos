/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.io.File;
import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * This is the demo test for the usage of the sql persistence class.
 *
 * @author Chenhong
 * @version 1.1
 */
public class DemoTest extends TestCase {
    /**
     * Represents the SqlResourcePersistence instance for test.
     */
    private SqlResourcePersistence persistence = null;

    /**
     * Represents the DBConnectionFactory instance for test.
     */
    private DBConnectionFactory factory = null;

    /**
     * Set up the environment. Create SqlResourcePersistence instance for test.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        DBTestUtil.clearConfigManager();

        File file = new File("test_files/DBConnectionFactory.xml");

        cm.add(file.getAbsolutePath());

        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

        factory = new DBConnectionFactoryImpl(namespace);

        persistence = new SqlResourcePersistence(factory);

        DBTestUtil.clearTables();
        DBTestUtil.setupDatbase();
    }

    /**
     * Tear down the environment. Clear all namespaces in the config manager.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        DBTestUtil.clearConfigManager();
    }

    /**
     * Demonstrate the usage of insert, update, delete operation of Resource instance.
     *
     * @throws Exception
     *             to junit.
     */
    public void testResourceOperationsDemo() throws Exception {
        // Create a role in advance.
        ResourceRole role = DBTestUtil.createResourceRole(5);
        persistence.addResourceRole(role);

        long resourceId = 1;

        // create a resource instance with id equals 1, project id 1 and phase id 1.
        Resource resource = DBTestUtil.createResource(resourceId, 1, 1);

        // Create the resource instance in the database.
        this.persistence.addResource(resource);

        // Load the resource with id equals 1 from database.
        Resource result = this.persistence.loadResource(resourceId);
        assertEquals("id should be 1", 1, result.getId());
        assertEquals("project id should be 1", 1, result.getProject()
                .longValue());
        assertEquals("phase id should be 1", 1, result.getPhase().longValue());

        resource.setPhase(new Long(2)); // Modified the phase
        resource.setProject(new Long(3)); // Modified the project.
        long submissionId = 121;
        resource.setSubmission(new Long(submissionId)); // set a submission.

        resource.setModificationUser("me"); // set the users that modified the record.
        resource.setModificationTimestamp(new Date()); // set the timestamp of the modification

        // The resource has been modified, update the database.
        this.persistence.updateResource(resource);

        // A Resource can also have extended properties.
        resource.setProperty("name", "topcoder1");
        resource.setProperty("age", "25");

        // Save the properties of the resource to database.
        this.persistence.updateResource(resource);

        // Finally, if you want to delete the resource from db.
        this.persistence.deleteResource(resource);
    }

    /**
     * Demonstrate the usage of loading a list of resources.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadResourcesDemo() throws Exception {
        // Create a role in advance.
        ResourceRole role = DBTestUtil.createResourceRole(5);
        persistence.addResourceRole(role);

        // Create 3 resource instances with id 1, 2, 3.
        for (int i = 0; i < 3; i++) {
            long resourceId = i + 1;
            Resource resource = DBTestUtil.createResource(resourceId, 1, 1);
            this.persistence.addResource(resource);
        }

        // Load the 3 resources from db by providing a list of ids..
        Resource[] results = this.persistence.loadResources(new long[] {1, 2,
            3 });
        assertEquals("3 resources are loaded from db", 3, results.length);

        // no resource has id 4, 5 or 6, so it will still load 3 resources from db.
        results = this.persistence
                .loadResources(new long[] {1, 2, 3, 4, 5, 6});
        assertEquals("3 resources are loaded from db", 3, results.length);
    }

    /**
     * Demonstrate the operations on ResourceRole instance.
     *
     * @throws Exception
     *             to junit.
     */
    public void testResourceRoleOperationsDemo() throws Exception {

        // Create a new ResourceRole instance with id 999999.
        long resourceRoleId = 999999;
        ResourceRole role = DBTestUtil.createResourceRole(resourceRoleId);

        // insert the ResourceRole to database.
        persistence.addResourceRole(role);

        // Load the resource role from db.
        ResourceRole result = persistence.loadResourceRole(resourceRoleId);
        assertEquals("id should be 999999", resourceRoleId, result.getId());

        // update the resource role.
        role.setPhaseType(new Long(2));
        role.setName("developer");
        role.setDescription("testing, testing");
        persistence.updateResourceRole(role);

        // Delete the resource role.
        persistence.deleteResourceRole(role);

        // Load a list of resource roles.
        ResourceRole[] roles = persistence.loadResourceRoles(new long[] {1, 2,
            3 });
    }

    /**
     * Demonstate the operations for Notification instances.
     *
     * @throws Exception
     *             to junit.
     */
    public void testNotificationOperationsDemo() throws Exception {
        // Create a notification type in advance.
        NotificationType type = DBTestUtil.createNotificationType(2);
        persistence.addNotificationType(type);

        // Assume there is a Resource instance with id 1, addNotification will add it to the notification list.
        long projectId = 2;
        long notificationTypeId = 2;
        persistence.addNotification(1, projectId, notificationTypeId,
                "operator");

        // Now I want to delete user 1 from the notification list.
        persistence.removeNotification(1, projectId, notificationTypeId,
                "operator");

        // Load a notification from the db to see if a user is in the notification list.
        persistence.loadNotification(1, projectId, notificationTypeId);

        // Load a list of notifications from db.
        persistence.loadNotifications(new long[] {1},
                new long[] {projectId}, new long[] {notificationTypeId});
    }

    /**
     * Demonstrate the operations on NotificationType instance.
     *
     * @throws Exception
     *             to junit.
     */
    public void testNotificationTypeOperationsDemo() throws Exception {
        // Create a new NotificationType with id equals 2
        long notificationTypeId = 2;
        NotificationType type = DBTestUtil
                .createNotificationType(notificationTypeId);

        // Insert the notification type in the db.
        persistence.addNotificationType(type);

        // Load a notification type instance from the db with its id.
        NotificationType result = persistence
                .loadNotificationType(notificationTypeId);
        assertEquals("id should be 2", notificationTypeId, result.getId());

        // Updated the description of the notification type.
        type.setDescription("a new description for the notification type");
        type.setName("a new name");

        type.setModificationUser("opeartor");
        type.setModificationTimestamp(new Date());
        // Update the notification type in the db.
        persistence.updateNotificationType(type);

        // Delete a notification type from db.
        persistence.deleteNotificationType(type);

        // Load a list of notification types from the db.
        persistence.loadNotificationTypes(new long[] {1, 2, 3});
    }
}
