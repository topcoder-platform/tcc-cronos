/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.accuracytests;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

import com.topcoder.registration.persistence.client.ResourceManagerServiceClient;

import junit.framework.TestCase;

import java.util.Date;
import java.util.Map;


/**
 * Accuracy test case for the ResourceManagerServiceClient class.
 * @author waits
 * @version 1.0
 */
public class ResourceManagerServiceClientAccTests extends TestCase {
    /** The wsdl url. */
    private static final String WSDL = "http://127.0.0.1:8080/ResourceManagerServiceBeanService/ResourceManagerServiceBean?wsdl";

    /** ResourceManagerServiceClient instance to test against. */
    private ResourceManagerServiceClient client = null;

    /**
     * Create instances.
     *
     * @throws Exception into JUnit
     */
    protected void setUp() throws Exception {
        client = new ResourceManagerServiceClient(WSDL);
    }

    /**
     * <p>
     * Test the updateResource method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource() throws Exception {
        client.updateResource(createResource(), "wiah");
    }

    /**
     * <p>
     * Test the updateResources method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources() throws Exception {
        client.updateResources(new Resource[] { createResource(1L), createResource(1L) }, 1L, "wasi");
    }

    /**
     * <p>
     * Test the removeResource method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource() throws Exception {
        client.removeResource(createResource(), "wasi");
    }

    /**
     * <p>
     * Test the getResource method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testGetResource() throws Exception {
        Resource resource = client.getResource(2L);

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
        assertTrue(((submissions[0] == 1L) && (submissions[1] == 2L)) ||
            ((submissions[0] == 2L) && (submissions[1] == 1L)));

        Map properties = resource.getAllProperties();
        assertEquals("There should be 2 properties", 2, properties.size());
        assertEquals("'property1' should have value 'value1'", "value1", properties.get("property1"));
        assertEquals("'property2' should have value 'value2'", "value2", properties.get("property2"));
    }

    /**
     * <p>
     * Test  the updateResourceRole method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole() throws Exception {
        client.updateResourceRole(createResourceRole(), "wasi");
    }

    /**
     * <p>
     * Test the removeResourceRole method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole() throws Exception {
        client.removeResourceRole(createResourceRole(), "wasi");
    }

    /**
     * <p>
     * Test the getAllResourceRoles() method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllResourceRoles() throws Exception {
        ResourceRole[] resourceRoles = client.getAllResourceRoles();
        assertEquals("There should be 2 ResourceRoles", 2, resourceRoles.length);

        for (ResourceRole resourceRole : resourceRoles) {
            assertTrue("ResourceRole id should be either 1 or 2",
                (resourceRole.getId() == 1) || (resourceRole.getId() == 2));

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
     * Test the updateNotificationType method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType() throws Exception {
        client.updateNotificationType(createNotificationType(), "wasi");
    }

    /**
     * <p>
     * Test the removeNotificationType method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType() throws Exception {
        client.removeNotificationType(createNotificationType(), "wasi");
    }

    /**
     * <p>
     * Test the getAllNotificationTypes() method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllNotificationTypes() throws Exception {
        NotificationType[] resourceRoles = client.getAllNotificationTypes();
        assertEquals("There should be 2 NotificationTypes", 2, resourceRoles.length);

        for (NotificationType resourceRole : resourceRoles) {
            assertTrue("NotificationType id should be either 1 or 2",
                (resourceRole.getId() == 1) || (resourceRole.getId() == 2));

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
     * Test the add/removeNotifications method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications() throws Exception {
        client.addNotifications(new long[] { 1L, 2L }, 1L, 1L, "wasi");
        client.removeNotifications(new long[] { 1L, 2L }, 1L, 1L, "wasi");
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceclient#getNotifications(long, long)}.
     * </p>
     * 
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetNotifications() throws Exception {
        long[] userIds = client.getNotifications(1L, 1L);
        assertEquals("There should be 2 user ids retrieved.", 2, userIds.length);
        assertTrue(((userIds[0] == 1L) && (userIds[1] == 2L)) || ((userIds[0] == 2L) && (userIds[1] == 1L)));
    }

    /**
     * <p>
     * Create a new instance of <code>NotificationType</code>.
     * </p>
     *
     * @return a new instance of <code>NotificationType</code>.
     */
    protected static NotificationType createNotificationType() {
        NotificationType notificationType = new NotificationType(1L);
        notificationType.setName("name");
        notificationType.setDescription("description");
        notificationType.setCreationUser("user");
        notificationType.setModificationUser("user");
        notificationType.setCreationTimestamp(new Date());
        notificationType.setModificationTimestamp(new Date());

        return notificationType;
    }

    /**
     * <p>
     * Create a new instance of <code>Resource</code>.
     * </p>
     *
     * @param project The project id. If not given then the project will be set as 1.
     *
     * @return a new instance of <code>Resource</code>.
     */
    protected static Resource createResource(long...project) {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole(1L));
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        resource.setProject(1L);
        resource.setProperty("name", "document");
        resource.setProperty("length", "10");
        resource.setSubmissions(new Long[] { 1L });
        resource.setCreationUser("ivern");
        resource.setModificationUser("ivern");
        resource.setCreationTimestamp(new Date());
        resource.setModificationTimestamp(new Date());

        if ((project != null) && (project.length > 0)) {
            resource.setProject(project[0]);
        }

        return resource;
    }

    /**
     * <p>
     * Create a new instance of <code>ResourceRole</code>.
     * </p>
     *
     * @return a new instance of <code>ResourceRole</code>.
     */
    protected static ResourceRole createResourceRole() {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setPhaseType(1L);
        resourceRole.setName("admin");
        resourceRole.setDescription("group admin");
        resourceRole.setCreationUser("user");
        resourceRole.setModificationUser("user");
        resourceRole.setCreationTimestamp(new Date());
        resourceRole.setModificationTimestamp(new Date());

        return resourceRole;
    }
}
