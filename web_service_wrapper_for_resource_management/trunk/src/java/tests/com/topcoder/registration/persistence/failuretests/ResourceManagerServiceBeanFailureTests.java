/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.failuretests;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.lang.reflect.Field;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.registration.persistence.BaseTestCase;
import com.topcoder.registration.persistence.ResourceManagementException;
import com.topcoder.registration.persistence.ejbservice.ResourceManagerBeanInitializationException;
import com.topcoder.registration.persistence.ejbservice.ResourceManagerServiceBean;

/**
 * Unit test for ResourceManagerServiceBean class.
 *
 * @author extra
 * @version 1.0
 */
public class ResourceManagerServiceBeanFailureTests extends BaseTestCase {

    /**
     * Represents mock instance of ResourceManager for test.
     */
    private static final ResourceManager MANAGER = createNiceMock(ResourceManager.class);

    /**
     * Represents instance of ExtendedResourceManagerServiceBean for test.
     */
    private ExtendedResourceManagerServiceBean bean;

    /**
     * Represents instance of Resource for test.
     */
    private Resource resource;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnt.
     */
    protected void setUp() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        this.setField("resourceManager", MANAGER);
        this.setField("cacheResourceRoles", Boolean.TRUE);
        this.setField("cacheNotificationTypes", Boolean.TRUE);
        this.setField("file", "ServerSideConfig.properties");
        this.setField("namespace", "ResourceManagerServiceBean");
        this.setField("resourceManagerKey", "resourceManagerKey");
        resource = createResource(1);
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        this.bean = null;
    }

    /**
     * Failure test for method initialize(). With empty file,
     * ResourceManagerBeanInitializationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_EmptyFile() throws Exception {
        this.setField("file", " ");
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            // expected
        }
    }

    /**
     * Failure test for method initialize(). With empty namespace,
     * ResourceManagerBeanInitializationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_EmptyNamespace() throws Exception {
        this.setField("namespace", " ");
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            // expected
        }
    }

    /**
     * Failure test for method initialize(). With empty resourceManagerKey,
     * ResourceManagerBeanInitializationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_EmptyResourceManagerKey() throws Exception {
        this.setField("resourceManagerKey", " ");
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            // expected
        }
    }

    /**
     * Failure test for method initialize(). With null namespace,
     * ResourceManagerBeanInitializationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_NullNamespace() throws Exception {
        this.setField("namespace", null);
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            // expected
        }
    }

    /**
     * Failure test for method initialize(). With null resourceManagerKey,
     * ResourceManagerBeanInitializationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_NullResourceManagerKey() throws Exception {
        this.setField("resourceManagerKey", null);
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            // expected
        }
    }

    /**
     * Failure test for method initialize(). With not exist file,
     * ResourceManagerBeanInitializationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_FileNotExist() throws Exception {
        this.setField("file", "error.properties");
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            // expected
        }
    }

    /**
     * Failure test for method initialize(). The object created by ObjectFactory
     * is not type of ResourceManager,
     * ResourceManagerBeanInitializationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_StringKey() throws Exception {
        this.setField("resourceManagerKey", "stringKey");
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResource(Resource, String). The
     * resourceManager filed has not been instantiate, IllegalStateException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResource_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.updateResource(this.createResource(), "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResource(Resource, String). With Resource
     * is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResource_NullResource() throws Exception {
        try {
            this.bean.updateResource(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResource(Resource, String). With Resource
     * does not have role set, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResource_RoleNotSet() throws Exception {
        try {
            this.bean.updateResource(new Resource(1L), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResource(Resource, String).
     *
     * With resource's role has phase type, but the resource does not have a
     * phase, IllegalArgumentException expected.
     *
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResource_PhaseNotSet() throws Exception {
        Resource resource1 = new Resource(1L);
        resource1.setResourceRole(new ResourceRole());
        resource1.getResourceRole().setPhaseType(2L);
        try {
            this.bean.updateResource(resource1, "operator");
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
            this.bean.updateResource(resource, null);
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
            this.bean.updateResource(resource, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResource(Resource, String). With Resource
     * does not have id set, IllegalArgumentException expected.
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
            this.bean.updateResource(resource1, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResource(Resource, String). Error occurs
     * while updating resource, ResourceManagementException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResource_Error() throws Exception {
        Resource resource1 = this.createResource();
        reset(MANAGER);
        MANAGER.updateResource(resource1, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.updateResource(resource1, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResource(Resource, String). The
     * resourceManager filed has not been instantiate, IllegalStateException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResource_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.removeResource(this.createResource(), "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResource(Resource, String). With null
     * resource, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResource_NullResource() throws Exception {
        try {
            this.bean.removeResource(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResource(Resource, String). With Resource
     * does not have id set, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResource_IdNotSet() throws Exception {
        try {
            this.bean.removeResource(new Resource(), "operator");
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
        try {
            this.bean.removeResource(resource, null);
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
            this.bean.removeResource(resource1, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResource(Resource, String). Error occurs
     * while removing resource, ResourceManagementException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResource_Error() throws Exception {
        Resource resource1 = this.createResource();
        reset(MANAGER);
        MANAGER.removeResource(resource1, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.removeResource(resource1, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method getResource(long). The resourceManager filed has
     * not been instantiate, IllegalStateException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetResource_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.getResource(1L);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
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
            this.bean.getResource(-1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method getResource(long). Error occurs while retrieving
     * resource, ResourceManagementException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetResource_Error() throws Exception {
        reset(MANAGER);
        MANAGER.getResource(1L);
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.getResource(1L);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String). The
     * resourceManager filed has not been instantiate, IllegalStateException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.updateResources(new Resource[] {this.createResource(1L) }, 1L, "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String). With
     * null resources, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_NullResources() throws Exception {
        try {
            this.bean.updateResources(null, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String). With
     * resources contains null element, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_NullElement() throws Exception {
        try {
            this.bean.updateResources(new Resource[] {null }, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String). With
     * negative project, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_NegativeProject() throws Exception {
        try {
            this.bean.updateResources(new Resource[] {}, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String). The
     * given array has a Resource which does not have project set,
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
            this.bean.updateResources(new Resource[] {resource1 }, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String). The
     * given array has a Resource which does not have ResourceRole set,
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
            this.bean.updateResources(new Resource[] {resource1 }, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String). With
     * null operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_NullOperator() throws Exception {
        try {
            this.bean.updateResources(new Resource[] {resource }, 1L, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String). With
     * empty operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_EmptyOperator() throws Exception {
        try {
            this.bean.updateResources(new Resource[] {resource }, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResources(Resource[], long, String). Error
     * occurs while updating resources, ResourceManagementException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResources_Error() throws Exception {
        Resource resource1 = this.createResource(1L);
        Resource[] resources = new Resource[] {resource1 };
        reset(MANAGER);
        MANAGER.updateResources(resources, 1L, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.updateResources(resources, 1L, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResourceRole(ResourceRole, String). The
     * resourceManager filed has not been instantiate, IllegalStateException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResourceRole_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.updateResourceRole(this.createResourceRole(), "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResourceRole(ResourceRole, String). With
     * null resourceRole, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResourceRole_NullResourceRole() throws Exception {
        try {
            this.bean.updateResourceRole(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResourceRole(ResourceRole, String). With
     * resourceRole does not have name set, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResourceRole_NameNotSet() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setDescription("description");
        try {
            this.bean.updateResourceRole(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResourceRole(ResourceRole, String). With
     * resourceRole does not have description set, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResourceRole_DescriptionNotSet() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setName("name");
        try {
            this.bean.updateResourceRole(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResourceRole(ResourceRole, String). With
     * resourceRole does not have id set, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResourceRole_IdNotSet() throws Exception {
        ResourceRole resourceRole = new ResourceRole();
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            this.bean.updateResourceRole(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResourceRole(ResourceRole, String). With
     * null operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResourceRole_NullOperator() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            this.bean.updateResourceRole(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResourceRole(ResourceRole, String). With
     * empty operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResourceRole_EmptyOperator() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            this.bean.updateResourceRole(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateResourceRole(ResourceRole, String). Error
     * occurs while updating ResourceRole, ResourceManagementException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateResourceRole_Error() throws Exception {
        ResourceRole resourceRole = this.createResourceRole();
        reset(MANAGER);
        MANAGER.updateResourceRole(resourceRole, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.updateResourceRole(resourceRole, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResourceRole(ResourceRole, String). The
     * resourceManager filed has not been instantiate, IllegalStateException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResourceRole_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.removeResourceRole(this.createResourceRole(), "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResourceRole(ResourceRole, String). With
     * null resourceRole, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResourceRole_NullResourceRole() throws Exception {
        try {
            this.bean.removeResourceRole(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResourceRole(ResourceRole, String). With
     * resourceRole does not have id set, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResourceRole_IdNotSet() throws Exception {
        try {
            this.bean.removeResourceRole(new ResourceRole(), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResourceRole(ResourceRole, String). With
     * null operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResourceRole_NullOperator() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        try {
            this.bean.removeResourceRole(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResourceRole(ResourceRole, String). With
     * empty operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResourceRole_EmptyOperator() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        try {
            this.bean.removeResourceRole(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeResourceRole(ResourceRole, String). Error
     * occurs while removing ResourceRole, ResourceManagementException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveResourceRole_Error() throws Exception {
        ResourceRole resourceRole = this.createResourceRole();
        reset(MANAGER);
        MANAGER.removeResourceRole(resourceRole, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.removeResourceRole(resourceRole, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method getAllResourceRoles(). The resourceManager filed
     * has not been instantiate, IllegalStateException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetAllResourceRoles_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.getAllResourceRoles();
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Failure test for method getAllResourceRoles(). Error occurs while
     * retrieving ResourceRole, ResourceManagementException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetAllResourceRoles_Error() throws Exception {
        reset(MANAGER);
        expect(MANAGER.getAllResourceRoles()).andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.getAllResourceRoles();
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateNotificationType(NotificationType, String).
     * The resourceManager filed has not been instantiate, IllegalStateException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateNotificationType_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.updateNotificationType(this.createNotificationType(), "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateNotificationType(NotificationType, String).
     * With null notificationType, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateNotificationType_NullNotificationType() throws Exception {
        try {
            this.bean.updateNotificationType(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateNotificationType(NotificationType, String).
     * With notificationType does not have name set, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateNotificationType_NameNotSet() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        resourceRole.setDescription("description");
        try {
            this.bean.updateNotificationType(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateNotificationType(NotificationType, String).
     * With notificationType does not have description set,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateNotificationType_DescriptionNotSet() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        resourceRole.setName("name");
        try {
            this.bean.updateNotificationType(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateNotificationType(NotificationType, String).
     * With NotificationType does not have id set, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateNotificationType_IdNotSet() throws Exception {
        NotificationType resourceRole = new NotificationType();
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            this.bean.updateNotificationType(resourceRole, "operator");
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
            this.bean.updateNotificationType(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateNotificationType(NotificationType, String).
     * With empty operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateNotificationType_EmptyOperator() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            this.bean.updateNotificationType(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateNotificationType(NotificationType, String).
     * Error occurs while updating NotificationType, ResourceManagementException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateNotificationType_Error() throws Exception {
        NotificationType resourceRole = this.createNotificationType();
        reset(MANAGER);
        MANAGER.updateNotificationType(resourceRole, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.updateNotificationType(resourceRole, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotificationType(NotificationType, String).
     * The resourceManager filed has not been instantiate, IllegalStateException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotificationType_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.removeNotificationType(this.createNotificationType(), "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotificationType(NotificationType, String).
     * With null notificationType, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotificationType_NullNotificationType() throws Exception {
        try {
            this.bean.removeNotificationType(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotificationType(NotificationType, String).
     * With notificationType does not have id set, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotificationType_IdNotSet() throws Exception {
        try {
            this.bean.removeNotificationType(new NotificationType(), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotificationType(NotificationType, String).
     * With null operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotificationType_NullOperator() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        try {
            this.bean.removeNotificationType(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotificationType(NotificationType, String).
     * With empty operator, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotificationType_EmptyOperator() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        try {
            this.bean.removeNotificationType(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotificationType(NotificationType, String).
     * Error occurs while removing NotificationType, ResourceManagementException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotificationType_Error() throws Exception {
        NotificationType resourceRole = this.createNotificationType();
        reset(MANAGER);
        MANAGER.removeNotificationType(resourceRole, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.removeNotificationType(resourceRole, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method getAllNotificationTypes(). The resourceManager
     * filed has not been instantiate, IllegalStateException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetAllNotificationTypes_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.getAllNotificationTypes();
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Failure test for method getAllNotificationTypes(). Error occurs while
     * retrieving NotificationType, ResourceManagementException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetAllNotificationTypes_Error() throws Exception {
        reset(MANAGER);
        expect(MANAGER.getAllNotificationTypes()).andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.getAllNotificationTypes();
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method addNotifications(long[], long, long, String). The
     * resourceManager filed has not been instantiate, IllegalStateException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddNotifications_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.addNotifications(new long[] {1L }, 1L, 1L, "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Failure test for method addNotifications(long[], long, long, String).
     * With null array of user ids, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddNotifications_NullUserIds() throws Exception {
        try {
            this.bean.addNotifications(null, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method addNotifications(long[], long, long, String).
     * With empty array of user ids, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddNotifications_EmptyUserIds() throws Exception {
        try {
            this.bean.addNotifications(new long[0], 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method addNotifications(long[], long, long, String).
     * With array contains negative user id, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddNotifications_NegativeUserId() throws Exception {
        try {
            this.bean.addNotifications(new long[] {-1L }, 1L, 1L, "operator");
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
            this.bean.addNotifications(new long[] {1L }, -1L, 1L, "operator");
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
            this.bean.addNotifications(new long[] {1L }, 1L, -1L, "operator");
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
            this.bean.addNotifications(new long[] {1L }, 1L, 1L, null);
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
            this.bean.addNotifications(new long[] {1L }, 1L, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method addNotifications(long[], long, long, String).
     * Error occurs while adding notifications, ResourceManagementException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddNotifications_Error() throws Exception {
        long[] userIds = new long[] {1L };
        reset(MANAGER);
        MANAGER.addNotifications(userIds, 1L, 1L, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.addNotifications(userIds, 1L, 1L, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotifications(long[], long, long, String).
     * The resourceManager filed has not been instantiate, IllegalStateException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotifications_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.removeNotifications(new long[] {1L }, 1L, 1L, "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotifications(long[], long, long, String).
     * With null array of user ids, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotifications_NullUserIds() throws Exception {
        try {
            this.bean.removeNotifications(null, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     *
     * Failure test for method removeNotifications(long[], long, long, String).
     * With empty array of user ids, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotifications_EmptyUserIds() throws Exception {
        try {
            this.bean.removeNotifications(new long[0], 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotifications(long[], long, long, String).
     * With array contains negative user id, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotifications_NegativeUserId() throws Exception {
        try {
            this.bean.removeNotifications(new long[] {-1L }, 1L, 1L, "operator");
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
            this.bean.removeNotifications(new long[] {1L }, -1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotifications(long[], long, long, String).
     * With negative notification type, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotifications_NegativeNotificationType() throws Exception {
        try {
            this.bean.removeNotifications(new long[] {1L }, 1L, -1L, "operator");
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
            this.bean.removeNotifications(new long[] {1L }, 1L, 1L, null);
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
            this.bean.removeNotifications(new long[] {1L }, 1L, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method removeNotifications(long[], long, long, String).
     * Error occurs while removing notifications, ResourceManagementException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveNotifications_Error() throws Exception {
        long[] userIds = new long[] {1L };
        reset(MANAGER);
        MANAGER.removeNotifications(userIds, 1L, 1L, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.removeNotifications(userIds, 1L, 1L, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method getNotifications(long, long). The resourceManager
     * filed has not been instantiate, IllegalStateException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetNotifications_ISE() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.getNotifications(1L, 1L);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Failure test for method getNotifications(long, long). Error occurs while
     * retrieving notifications, ResourceManagementException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetNotifications_Error() throws Exception {
        reset(MANAGER);
        MANAGER.getNotifications(1L, 1L);
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.getNotifications(1L, 1L);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            // expected
        }
    }

    /**
     * Failure test for method getNotifications(long, long). With negative
     * project, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetNotifications_NegativeProject() throws Exception {
        try {
            this.bean.getNotifications(-1L, 1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method getNotifications(long, long). With notification
     * type is negative, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetNotifications_NegativeNotificationType() throws Exception {
        try {
            this.bean.getNotifications(1L, -1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Set the value into private field.
     *
     * @param fieldName
     *            The name of field.
     * @param fieldValue
     *            The field value to be set.
     *
     * @throws Exception
     *             to JUnit.
     */
    private void setField(String fieldName, Object fieldValue) throws Exception {
        Field field = ResourceManagerServiceBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this.bean, fieldValue);
    }

    /**
     * This class extends ResourceManagerServiceBean for test.
     *
     * @author extra
     * @version 1.0
     */
    private static final class ExtendedResourceManagerServiceBean extends ResourceManagerServiceBean {
        /**
         *
         * Simply delegates to super.initialize().
         *
         */
        public void initialize() {
            super.initialize();
        }
    }
}
