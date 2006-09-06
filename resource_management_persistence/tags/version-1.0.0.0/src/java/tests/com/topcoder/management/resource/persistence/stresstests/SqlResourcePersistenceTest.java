/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.stresstests;

import java.util.Date;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.sql.SqlResourcePersistence;


/**
 * Stress tests for the class: SqlResourcePersistence.
 *
 * @author kinfong
 * @version 1.0
 */
public class SqlResourcePersistenceTest extends DbStressTest {

    /**
     * An instance of sql resource persistence for tests.
     */
    private SqlResourcePersistence persistence = null;

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        persistence = new SqlResourcePersistence(getConnectionFactory());
    }

    /**
     * Returns the resource with the specific id.
     *
     * @param id the id
     * @return the resource with the specific id
     */
    private Resource getResource(long id) {
        Resource resource = new Resource(id);
        resource.setResourceRole(getResourceRole(1));
        Date current = new Date();
        resource.setCreationTimestamp(current);
        resource.setCreationUser("STRESS_REVIEWER");
        resource.setModificationTimestamp(current);
        resource.setModificationUser("STRESS_REVIEWER");
        return resource;
    }

    /**
     * Returns the resource role with the specific id.
     *
     * @param id the id
     * @return the resource role with the specific id
     */
    private ResourceRole getResourceRole(long id) {
        ResourceRole resourceRole = new ResourceRole(id);
        Date current = new Date();
        resourceRole.setCreationTimestamp(current);
        resourceRole.setCreationUser("STRESS_REVIEWER");
        resourceRole.setModificationTimestamp(current);
        resourceRole.setModificationUser("STRESS_REVIEWER");
        resourceRole.setName("name");
        resourceRole.setDescription("For test");
        return resourceRole;
    }


    /**
     * Returns the notification type with the specific id.
     *
     * @param id the id
     * @return the notification type with the specific id
     */
    private NotificationType getNotificationType(long id) {
        NotificationType notificationType = new NotificationType(id);
        Date current = new Date();
        notificationType.setCreationTimestamp(current);
        notificationType.setCreationUser("STRESS_REVIEWER");
        notificationType.setModificationTimestamp(current);
        notificationType.setModificationUser("STRESS_REVIEWER");
        notificationType.setName("name");
        notificationType.setDescription("For test");
        return notificationType;
    }
    /**
     * Stress tests for the method: addResource(Resource).
     *
     * @throws Exception to JUnit
     */
    public void testAddResource() throws Exception {

        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            persistence.addResource(getResource(100 + i));

            String sql = "SELECT * FROM resource WHERE resource_id = ?";
            assertTrue("The record should exist.", existsRecord(sql, new Object[] {new Long(i + 100)}));
        }

        Date finish = new Date();

        outputStressInfo(start, finish, "addResource(Resource)");

    }



    /**
     * Stress tests for the method: deleteResource(Resource).
     *
     * @throws Exception to JUnit
     */
    public void testDeleteResource() throws Exception {
        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            // add it
            persistence.addResource(getResource(100 + i));

            // delete it
            persistence.deleteResource(getResource(100 + i));

            String sql = "SELECT * FROM resource WHERE resource_id = ?";
            assertFalse("The record should not exist.", existsRecord(sql, new Object[] {new Long(i + 100)}));
        }

        Date finish = new Date();

        outputStressInfo(start, finish, "deleteResource(Resource)");
    }

    /**
     * Stress tests for the method: updateResource(Resource).
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResource() throws Exception {
        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            Resource resource = getResource(100 + i);

            // add it
            persistence.addResource(resource);

            // change it
            resource.setModificationUser("Lalala");

            // update it
            persistence.updateResource(resource);

            String sql = "SELECT * FROM resource WHERE modify_user = ?";
            assertTrue("The record should exist.", existsRecord(sql, new Object[] {"Lalala"}));
        }

        Date finish = new Date();

        outputStressInfo(start, finish, "updateResource(Resource)");

    }

    /**
     * Stress tests for the method: loadResource(long).
     *
     * @throws Exception to JUnit
     */
    public void testLoadResource() throws Exception {

        Date start = new Date();

        Resource resource = null;
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            resource = persistence.loadResource(1);
        }

        assertEquals("The returned result is not correct.", 1, resource.getId());

        Date finish = new Date();

        // output the info
        outputStressInfo(start, finish, "loadResource(long)");
    }

    /**
     * Stress tests for the method: addNotification(long, long, long, String).
     *
     * @throws Exception to JUnit
     */
    public void testAddNotification() throws Exception {
        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            persistence.addNotification(200 + i, 1, 3, "reviewer");

            String sql = "SELECT * FROM notification WHERE external_ref_id = ? ";

            assertTrue("The record should exist.", existsRecord(sql, new Object[] {new Long(i + 200)}));
        }

        Date finish = new Date();

        outputStressInfo(start, finish, "addNotification(long, long, long, String)");
    }

    /**
     * Stress tests for the mehtod: removeNotification(long, long, long, String).
     *
     * @throws Exception to JUnit
     */
    public void testRemoveNotification() throws Exception {
        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // add it
            persistence.addNotification(200 + i, 1, 3, "reviewer");

            // delete it
            persistence.removeNotification(200 + i, 1, 3, "reviewer");

            String sql = "SELECT * FROM notification WHERE external_ref_id = ? ";

            assertFalse("The record should not exist.", existsRecord(sql, new Object[] {new Long(i + 200)}));
        }

        Date finish = new Date();

        outputStressInfo(start, finish, "removeNotification(long, long, long, String)");
    }

    /**
     * Stress tests for the method: loadNotification(long, long, long).
     *
     * @throws Exception to JUnit
     */
    public void testLoadNotification() throws Exception {
        Date start = new Date();

        Notification notification = null;
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            notification = persistence.loadNotification(1, 1, 1);
        }

        assertEquals("The returned result is not correct.", 1, notification.getProject());

        Date finish = new Date();

        // output the info
        outputStressInfo(start, finish, "loadNotification(long)");
    }

    /**
     * Stress tests for the method: addNotificationType(NotificationType).
     *
     * @throws Exception to JUnit
     */
    public void testAddNotificationType() throws Exception {
        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            persistence.addNotificationType(getNotificationType(i + 100));

            String sql = "SELECT * FROM notification_type_lu WHERE notification_type_id = ? ";

            assertTrue("The record should exist.", existsRecord(sql, new Object[] {new Long(i + 100)}));
        }

        Date finish = new Date();

        outputStressInfo(start, finish, "addNotificationType(NotificationType)");
    }

    /**
     * Stress tests for the method: deleteNotificationType(NotificationType).
     *
     * @throws Exception to JUnit
     */
    public void testDeleteNotificationType() throws Exception {
        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // add it
            persistence.addNotificationType(getNotificationType(i + 100));

            // delete it
            persistence.deleteNotificationType(getNotificationType(i + 100));

            String sql = "SELECT * FROM notification_type_lu WHERE notification_type_id = ? ";

            assertFalse("The record should not exist.", existsRecord(sql, new Object[] {new Long(i + 100)}));
        }

        Date finish = new Date();

        outputStressInfo(start, finish, "deleteNotificationType(NotificationType)");
    }

    /**
     * Stress tests for the method: updateNotificationType(NotificationType).
     *
     * @throws Exception to JUnit
     */
    public void testUpdateNotificationType() throws Exception {

        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            NotificationType type = getNotificationType(i + 100);

            // add it
            persistence.addNotificationType(type);

            // change it
            type.setModificationUser("Lalala");

            // update it
            persistence.updateNotificationType(type);

            String sql = "SELECT * FROM notification_type_lu WHERE modify_user = ? ";

            assertTrue("The record should exist.", existsRecord(sql, new Object[] {"Lalala"}));
        }

        Date finish = new Date();

        outputStressInfo(start, finish, "updateNotificationType(NotificationType)");

    }

    /**
     * Stress tests for the method: loadNotificationType(long).
     *
     * @throws Exception to JUnit
     */
    public void testLoadNotificationType() throws Exception {
        Date start = new Date();

        NotificationType notificationType = null;
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            notificationType = persistence.loadNotificationType(1);
        }

        assertEquals("The returned result is not correct.", 1, notificationType.getId());

        Date finish = new Date();

        // output the info
        outputStressInfo(start, finish, "loadNotificationType(long)");
    }

    /**
     * Stress tests for the method: addResourceRole(ResourceRole).
     *
     * @throws Exception to JUnit
     */
    public void testAddResourceRole() throws Exception {
        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            persistence.addResourceRole(getResourceRole(i + 100));

            String sql = "SELECT * FROM resource_role_lu WHERE resource_role_id = ? ";

            assertTrue("The record should exist.", existsRecord(sql, new Object[] {new Long(i + 100)}));
        }

        Date finish = new Date();

        outputStressInfo(start, finish, "addResourceRole(ResourceRole)");
    }

    /**
     * Stress tests for the method: deleteResourceRole(ResourceRole).
     *
     * @throws Exception to JUnit
     */
    public void testDeleteResourceRole() throws Exception {
        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            // add it
            persistence.addResourceRole(getResourceRole(i + 100));

            // remove it
            persistence.deleteResourceRole(getResourceRole(i + 100));

            String sql = "SELECT * FROM resource_role_lu WHERE resource_role_id = ? ";

            assertFalse("The record should not exist.", existsRecord(sql, new Object[] {new Long(i + 100)}));
        }

        Date finish = new Date();

        outputStressInfo(start, finish, "deleteResourceRole(ResourceRole)");
    }

    /**
     * Stress tests for the method: updateResourceRole(ResourceRole).
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResourceRole() throws Exception {
        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            // get the resource role
            ResourceRole resourceRole = getResourceRole(i + 100);

            // add it
            persistence.addResourceRole(getResourceRole(i + 100));

            // change it
            resourceRole.setModificationUser("Lalala");

            // update it
            persistence.updateResourceRole(resourceRole);

            String sql = "SELECT * FROM resource_role_lu WHERE modify_user = ? ";

            assertTrue("The record should exist.", existsRecord(sql, new Object[] {"Lalala"}));
        }

        Date finish = new Date();

        outputStressInfo(start, finish, "updateResourceRole(ResourceRole)");
    }

    /**
     * Stress tests for the method: loadResourceRole(long).
     *
     * @throws Exception to JUnit
     */
    public void testLoadResourceRole() throws Exception {
        Date start = new Date();

        ResourceRole role = null;
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            role = persistence.loadResourceRole(1);
        }

        assertEquals("The returned result is not correct.", 1, role.getId());

        Date finish = new Date();

        // output the info
        outputStressInfo(start, finish, "loadResourceRole(long)");
    }

    /**
     * Stress tests for the method: loadResources(long[]).
     *
     * @throws Exception to JUnit
     */
    public void testLoadResources() throws Exception {
        // ids
        long[] ids = new long[] {1, 2, 3};

        // result
        Resource[] result = null;

        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            result = persistence.loadResources(ids);
        }

        Date finish = new Date();

        // check the answer
        // assertEquals("The length of the ids are not the same.", ids.length, result.length);
        for (int i = 0; i < result.length; i++) {
            int j = 0;
            for (j = 0; j < ids.length; j++) {
                if (result[i].getId() == ids[j]) {
                    break;
                }
            }
            if (ids.length == j) {
                fail("The answer is not correct.");
            }
        }

        // output stress info
        outputStressInfo(start, finish, "loadResources");
    }

    /**
     * Stress tests for the method: loadNotificationTypes(long[]).
     *
     * @throws Exception to JUnit
     */
    public void testLoadNotificationTypes() throws Exception {
        // ids
        long[] ids = new long[] {1, 2, 3};

        // result
        NotificationType[] result = null;

        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            result = persistence.loadNotificationTypes(ids);
        }

        Date finish = new Date();

        // check the answer
        // assertEquals("The length of the ids are not the same.", ids.length, result.length);
        for (int i = 0; i < result.length; i++) {
            int j = 0;
            for (j = 0; j < ids.length; j++) {
                if (result[i].getId() == ids[j]) {
                    break;
                }
            }
            if (ids.length == j) {
                fail("The answer is not correct.");
            }
        }

        // output stress info
        outputStressInfo(start, finish, "loadNotificationTypes");
    }

    /**
     * Stress tests for the method: loadResourceRoles(long[]).
     *
     * @throws Exception to JUnit
     */
    public void testLoadResourceRoles() throws Exception {
        // ids
        long[] ids = new long[] {1, 2, 3};

        // result
        ResourceRole[] result = null;

        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            result = persistence.loadResourceRoles(ids);
        }

        Date finish = new Date();

        // check the answer
        // assertEquals("The length of the ids are not the same.", ids.length, result.length);
        for (int i = 0; i < result.length; i++) {
            int j = 0;
            for (j = 0; j < ids.length; j++) {
                if (result[i].getId() == ids[j]) {
                    break;
                }
            }
            if (ids.length == j) {
                fail("The answer is not correct.");
            }
        }

        // output stress info
        outputStressInfo(start, finish, "loadResourceRoles");
    }

    /**
     * Stress tests for the method: loadNotifications(long[], long[], long[]).
     *
     * @throws Exception to JUnit
     */
    public void testLoadNotifications() throws Exception {
        // ids
        long[] id1s = new long[] {1, 2, 3};
        long[] id2s = new long[] {1, 2, 3};
        long[] id3s = new long[] {1, 2, 3};

        // result
        Notification[] result = null;

        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            result = persistence.loadNotifications(id1s, id2s, id3s);
        }

        Date finish = new Date();

        // check the answer
        assertEquals("The answer is not corrected.", 1, result[0].getProject());

        // output stress info
        outputStressInfo(start, finish, "loadNotifications");
    }

}
