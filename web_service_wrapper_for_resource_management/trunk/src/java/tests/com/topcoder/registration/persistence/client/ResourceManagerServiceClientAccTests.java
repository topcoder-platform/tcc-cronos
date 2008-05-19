/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.client;

import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.registration.persistence.BaseTestCase;

/**
 * <p>
 * Accuracy test for <code>{@link ResourceManagerServiceClient}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceManagerServiceClientAccTests extends BaseTestCase {


    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(String)}.
     * </p>
     *
     * <p>
     * Given url string is valid, <code>ResourceManagerServiceClient</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_Accuracy() throws Exception {
        assertNotNull("ResourceManagerServiceClient should be created", new ResourceManagerServiceClient(WSDL));
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(URL)}.
     * </p>
     *
     * <p>
     * Given url is valid, <code>ResourceManagerServiceClient</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_Accuracy() throws Exception {
        assertNotNull("ResourceManagerServiceClient should be created",
            new ResourceManagerServiceClient(new URL(WSDL)));
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(URL, QName)}.
     * </p>
     *
     * <p>
     * Given url and <code>QName</code> are valid, <code>ResourceManagerServiceClient</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor3_Accuracy() throws Exception {
        QName name = DEFAULT_SERVICE_NAME;
        assertNotNull("ResourceManagerServiceClient should be created",
                new ResourceManagerServiceClient(new URL(WSDL), name));
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#getPort()}.
     * </p>
     */
    public void testGetPort_Accuracy() {
        assertNotNull("The port should be created.", CLIENT.getPort());
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_Accuracy() throws Exception {

        CLIENT.updateResource(this.createResource(), "operator");
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_Accuracy() throws Exception {

        CLIENT.updateResources(new Resource[]{
            this.createResource(1L), this.createResource(1L), this.createResource(1L)}, 1L, "operator");
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource_Accuracy() throws Exception {

        CLIENT.removeResource(this.createResource(), "operator");
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#getResource(long)}.
     * </p>
     *
     * <p>
     * Null is returned.
     * </p>
     *
     * @throws Exception to JUnit.
     * @see MockResourceManager
     */
    public void testGetResource_Accuracy_1() throws Exception {
        assertNull("No resource found with id: " + Long.MAX_VALUE, CLIENT.getResource(Long.MAX_VALUE));
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#getResource(long)}.
     * </p>
     *
     * <p>
     * The resource with given id should be returned. All the fields and properties should be retrieved.
     * </p>
     *
     * @throws Exception to JUnit.
     * @see MockResourceManager
     */
    public void testGetResource_Accuracy_2() throws Exception {

        Resource resource = CLIENT.getResource(2L);

        //Ensure all the fields and properties are passed from server to client
        assertEquals("Id should be 2", 2, resource.getId());
        assertEquals("Phase should be 1", 1L, resource.getPhase().longValue());
        assertEquals("Project should be 1", 1L, resource.getProject().longValue());

        assertNotNull("Create Date should be set", resource.getCreationTimestamp());
        assertNotNull("Modify Date should be set", resource.getModificationTimestamp());
        assertEquals("Create user should be 'CreatUser'", "CreatUser", resource.getCreationUser());
        assertEquals("Modify user should be 'ModifyUser'", "ModifyUser", resource.getModificationUser());

        ResourceRole resourceRole = resource.getResourceRole();
        assertNotNull("ResourceRole should be set", resourceRole);
        assertEquals("ResourceRole id should be 2", 2L, resourceRole.getId());
        assertEquals("ResourceRole phase type should be 1", 1L, resourceRole.getPhaseType().longValue());
        assertEquals("ResourceRole name should be 'designer'", "designer", resourceRole.getName());

        Long[] submissions = resource.getSubmissions();
        assertEquals("There should be 2 submissions", 2, submissions.length);
        assertTrue((submissions[0] == 1L && submissions[1] == 2L)
                || (submissions[0] == 2L && submissions[1] == 1L));

        Map properties = resource.getAllProperties();
        assertEquals("There should be 2 properties", 2, properties.size());
        assertEquals("'property1' should have value 'value1'", "value1", properties.get("property1"));
        assertEquals("'property2' should have value 'value2'", "value2", properties.get("property2"));
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_Accuracy() throws Exception {

        CLIENT.updateResourceRole(this.createResourceRole(), "operator");
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole_Accuracy() throws Exception {

        CLIENT.removeResourceRole(this.createResourceRole(), "operator");
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#getAllResourceRoles()}.
     * </p>
     *
     * <p>
     * The resource roles should be returned. All the fields and properties should be retrieved.
     * </p>
     *
     * @throws Exception to JUnit.
     * @see MockResourceManager
     */
    public void testGetAllResourceRoles_Accuracy() throws Exception {
        ResourceRole[] resourceRoles = CLIENT.getAllResourceRoles();
        assertEquals("There should be 2 ResourceRoles", 2, resourceRoles.length);
        for (ResourceRole resourceRole : resourceRoles) {
            assertTrue("ResourceRole id should be either 1 or 2",
                resourceRole.getId() == 1 || resourceRole.getId() == 2);
            if (resourceRole.getId() == 1) {
                assertEquals("Name should be 'developer'", "developer", resourceRole.getName());
            } else {
                assertEquals("Name should be 'designer'", "designer", resourceRole.getName());
            }
            assertEquals("Description should be 'description'", "description", resourceRole.getDescription());
            assertNotNull("Create Date should be set", resourceRole.getCreationTimestamp());
            assertNotNull("Modify Date should be set", resourceRole.getModificationTimestamp());
            assertEquals("Create user should be 'CreatUser'", "CreatUser", resourceRole.getCreationUser());
            assertEquals("Modify user should be 'ModifyUser'", "ModifyUser", resourceRole.getModificationUser());
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_Accuracy() throws Exception {

        CLIENT.updateNotificationType(this.createNotificationType(), "operator");
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType_Accuracy() throws Exception {

        CLIENT.removeNotificationType(this.createNotificationType(), "operator");
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#getAllNotificationTypes()}.
     * </p>
     *
     * <p>
     * The notification types should be returned. All the fields and properties should be retrieved.
     * </p>
     *
     * @throws Exception to JUnit.
     * @see MockResourceManager
     */
    public void testGetAllNotificationTypes_Accuracy() throws Exception {
        NotificationType[] resourceRoles = CLIENT.getAllNotificationTypes();
        assertEquals("There should be 2 NotificationTypes", 2, resourceRoles.length);
        for (NotificationType resourceRole : resourceRoles) {
            assertTrue("NotificationType id should be either 1 or 2",
                resourceRole.getId() == 1 || resourceRole.getId() == 2);
            if (resourceRole.getId() == 1) {
                assertEquals("Name should be 'email'", "email", resourceRole.getName());
            } else {
                assertEquals("Name should be 'message'", "message", resourceRole.getName());
            }
            assertEquals("Description should be 'description'", "description", resourceRole.getDescription());
            assertNotNull("Create Date should be set", resourceRole.getCreationTimestamp());
            assertNotNull("Modify Date should be set", resourceRole.getModificationTimestamp());
            assertEquals("Create user should be 'CreatUser'", "CreatUser", resourceRole.getCreationUser());
            assertEquals("Modify user should be 'ModifyUser'", "ModifyUser", resourceRole.getModificationUser());
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#addNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_Accuracy() throws Exception {
        CLIENT.addNotifications(new long[]{1L, 2L}, 1L, 1L, "operator");
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_Accuracy() throws Exception {
        CLIENT.removeNotifications(new long[]{1L, 2L}, 1L, 1L, "operator");
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#getNotifications(long, long)}.
     * </p>
     *
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetNotifications_Accuracy() throws Exception {
        long[] userIds = CLIENT.getNotifications(1L, 1L);
        assertEquals("There should be 2 user ids retrieved.", 2, userIds.length);
        assertTrue((userIds[0] == 1L && userIds[1] == 2L)
            || (userIds[0] == 2L && userIds[1] == 1L));
    }
}
