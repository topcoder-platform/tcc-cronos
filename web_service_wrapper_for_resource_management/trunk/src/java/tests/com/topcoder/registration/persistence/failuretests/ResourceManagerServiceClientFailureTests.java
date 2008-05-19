/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.failuretests;

import java.net.URL;

import javax.xml.namespace.QName;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClient;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClientCreationException;

/**
 * Failure test for class ResourceManagerServiceClient.
 *
 * @author extra
 * @version 1.0
 */
public class ResourceManagerServiceClientFailureTests extends BaseTestCase {

    /**
     * Represents instance of Resource for test.
     */
    private Resource resource;

    /**
     * Sets up test environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        super.setUp();
    }

    /**
     * Failure test for constructor ResourceManagerServiceClient(String). With
     * null url string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor1_NullURL() throws Exception {
        try {
            new ResourceManagerServiceClient((String) null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor ResourceManagerServiceClient(String). With
     * empty url string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor1_EmptyURL() throws Exception {
        try {
            new ResourceManagerServiceClient(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor ResourceManagerServiceClient(String). With
     * url string is not a url, ResourceManagerServiceClientCreationException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor1_InvalidURL_1() throws Exception {
        try {
            new ResourceManagerServiceClient("112");
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor ResourceManagerServiceClient(String). With
     * invalid url, ResourceManagerServiceClientCreationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor1_InvalidURL_2() throws Exception {
        try {
            new ResourceManagerServiceClient("http://www.google.com");
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor ResourceManagerServiceClient(URL). With null
     * url, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor2_NullURL() throws Exception {
        try {
            new ResourceManagerServiceClient((URL) null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor ResourceManagerServiceClient(URL). With
     * invalid url, ResourceManagerServiceClientCreationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor2_InvalidURL() throws Exception {
        try {
            new ResourceManagerServiceClient(new URL("http://www.google.com"));
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor ResourceManagerServiceClient(URL, QName).
     * With null url, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor3_NullURL() throws Exception {
        try {
            new ResourceManagerServiceClient(null, DEFAULT_SERVICE_NAME);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor ResourceManagerServiceClient(URL, QName).
     * With null QName, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor3_NullQName() throws Exception {
        try {
            new ResourceManagerServiceClient(new URL(WSDL), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor ResourceManagerServiceClient(URL, QName).
     * With invalid QName, ResourceManagerServiceClientCreationException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor3_InvalidQName() throws Exception {
        try {
            new ResourceManagerServiceClient(new URL(WSDL), new QName("http://www.topcoder.com", "InvalidService"));
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResource(Resource, String). With null
     * Resource, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResource_NullResource() throws Exception {
        try {
            CLIENT.updateResource(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResource(Resource, String). With the
     * resource does not have role set, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResource_RoleNotSet() throws Exception {
        try {
            CLIENT.updateResource(new Resource(2), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResource(Resource, String). With the
     * resource does not have phase set, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResource_PhaseNotSet() throws Exception {
        Resource resource1 = new Resource(1L);
        resource1.setResourceRole(new ResourceRole());
        resource1.getResourceRole().setPhaseType(3L);
        try {
            CLIENT.updateResource(resource1, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResource(Resource, String). With null
     * operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResource_NullOperator() throws Exception {
        try {
            CLIENT.updateResource(resource, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResource(Resource, String). With empty
     * operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResource_EmptyOperator() throws Exception {
        try {
            CLIENT.updateResource(resource, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResource(Resource, String). With the
     * resource does not have id set, IllegalArgumentException expected.
     *
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResource_IdNotSet() throws Exception {
        Resource resource1 = new Resource();
        resource1.setResourceRole(new ResourceRole());
        resource1.getResourceRole().setPhaseType(2L);
        resource1.setPhase(1L);
        try {
            CLIENT.updateResource(resource1, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResource(Resource, String). With Resource
     * is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResource_NullResource() throws Exception {
        try {
            CLIENT.removeResource(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResource(Resource, String). With the
     * resource does not have id set, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResource_IdNotSet() throws Exception {
        try {
            CLIENT.removeResource(new Resource(), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResource(Resource, String). With null
     * operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResource_NullOperator() throws Exception {
        Resource resource1 = new Resource(1L);
        try {
            CLIENT.removeResource(resource1, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResource(Resource, String). With empty
     * operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResource_EmptyOperator() throws Exception {
        Resource resource1 = new Resource(1L);
        try {
            CLIENT.removeResource(resource1, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method getResource(long). With negative id,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetResource_NegativeId() throws Exception {
        try {
            CLIENT.getResource(-1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String).
     * With resources array is null, IllegalArgumentException expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_NullResourcesArray() throws Exception {
        try {
            CLIENT.updateResources(null, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String).
     * With resources array contains null element, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_NullElement() throws Exception {
        try {
            CLIENT.updateResources(new Resource[] {null }, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String).
     * With negative project, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_NegativeProject() throws Exception {
        try {
            CLIENT.updateResources(new Resource[] {}, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String).
     * The given array has a Resource which does not have project set,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_ProjectNotSet() throws Exception {
        Resource resource1 = new Resource(1L);
        resource1.setResourceRole(new ResourceRole());
        resource1.getResourceRole().setPhaseType(2L);
        resource1.setPhase(1L);
        try {
            CLIENT.updateResources(new Resource[] {resource1 }, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String).
     * The given array has a resource which has project not same as given
     * project, IllegalArgumentException expected.
     *
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_ProjectNotSame() throws Exception {
        try {
            CLIENT.updateResources(new Resource[] {resource }, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String).
     * The given array has a Resource which does not have ResourceRole set,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_RoleNotSet() throws Exception {
        Resource resource1 = new Resource(1L);
        resource1.setPhase(1L);
        resource1.setProject(1L);
        try {
            CLIENT.updateResources(new Resource[] {resource1 }, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String).
     * With operator is empty, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_EmptyOperator() throws Exception {
        try {
            CLIENT.updateResources(new Resource[] {resource }, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResourceRole(ResourceRole, String).
     * With null resourceRole, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResourceRole_NullResourceRole() throws Exception {
        try {
            CLIENT.removeResourceRole(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResourceRole(ResourceRole, String).
     * With null operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResourceRole_NullOperator() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        try {
            CLIENT.removeResourceRole(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResourceRole(ResourceRole, String).
     * With empty operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResourceRole_EmptyOperator() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        try {
            CLIENT.removeResourceRole(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateNotificationType(NotificationType, String).
     * With null NotificationType, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateNotificationType_NullNotificationType() throws Exception {
        try {
            CLIENT.updateNotificationType(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateNotificationType(NotificationType, String).
     * With null operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateNotificationType_NullOperator() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            CLIENT.updateNotificationType(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateNotificationType(NotificationType, String).
     * With empty operator, IllegalArgumentException expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateNotificationType_EmptyOperator() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            CLIENT.updateNotificationType(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotificationType(NotificationType, String).
     * With null NotificationType, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotificationType_NullNotificationType() throws Exception {
        try {
            CLIENT.removeNotificationType(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }


    /**
     * Failure test for method removeNotificationType(NotificationType, String).
     * With null operator, IllegalArgumentException expected.
     *
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotificationType_NullOperator() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        try {
            CLIENT.removeNotificationType(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotificationType(NotificationType, String).
     * With empty operator, IllegalArgumentException expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotificationType_EmptyOperator() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        try {
            CLIENT.removeNotificationType(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method addNotifications(long[], long, long, String).
     * With negative project, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddNotifications_NegativeProject() throws Exception {
        try {
            CLIENT.addNotifications(new long[] {1L }, -1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method addNotifications(long[], long, long, String).
     * With negative notification type, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddNotifications_NegativeNotificationType() throws Exception {
        try {
            CLIENT.addNotifications(new long[] {1L }, 1L, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method addNotifications(long[], long, long, String).
     * With null operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddNotifications_NullOperator() throws Exception {
        try {
            CLIENT.addNotifications(new long[] {1L }, 1L, 1L, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method addNotifications(long[], long, long, String).
     * With empty operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddNotifications_EmptyOperator() throws Exception {
        try {
            CLIENT.addNotifications(new long[] {1L }, 1L, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotifications(long[], long, long, String).
     * With negative project, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotifications_NegativeProject() throws Exception {
        try {
            CLIENT.removeNotifications(new long[] {1L }, -1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotifications(long[], long, long, String).
     * With negative notification, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotifications_NegativeNotificationType() throws Exception {
        try {
            CLIENT.removeNotifications(new long[] {1L }, 1L, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotifications(long[], long, long, String).
     * With null operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotifications_NullOperator() throws Exception {
        try {
            CLIENT.removeNotifications(new long[] {1L }, 1L, 1L, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotifications(long[], long, long, String).
     * With empty operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotifications_EmptyOperator() throws Exception {
        try {
            CLIENT.removeNotifications(new long[] {1L }, 1L, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }


    /**
     * Failure test for method getNotifications(long, long).
     * With negative project, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetNotifications_NegativeProject() throws Exception {
        try {
            CLIENT.getNotifications(-1L, 1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method getNotifications(long, long).
     * With negative notification type, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetNotifications_NegativeNotificationType() throws Exception {
        try {
            CLIENT.getNotifications(1L, -1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
