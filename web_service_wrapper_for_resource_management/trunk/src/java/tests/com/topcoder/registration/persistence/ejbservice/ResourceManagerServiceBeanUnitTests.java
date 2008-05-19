/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.ejbservice;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.lang.reflect.Field;

import org.easymock.IAnswer;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.registration.persistence.BaseTestCase;
import com.topcoder.registration.persistence.ResourceManagementException;

/**
 * <p>
 * Unit test for <code>{@link ResourceManagerServiceBean}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceManagerServiceBeanUnitTests extends BaseTestCase {

    /**
     * <p>
     * The mock of <code>ResourceManager</code>.
     * </p>
     */
    private static final ResourceManager MANAGER = createNiceMock(ResourceManager.class);

    /**
     * <p>
     * The <code>ResourceManagerServiceBean</code> to be tested.
     * </p>
     */
    private ExtendedResourceManagerServiceBean bean;

    /**
     * <p>
     * Set up the test case.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        this.setField("resourceManager", MANAGER);
        this.setField("cacheResourceRoles", Boolean.TRUE);
        this.setField("cacheNotificationTypes", Boolean.TRUE);
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     */
    @Override
    protected void tearDown() {
        this.bean = null;
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceBean#ResourceManagerServiceBean()}.
     * </p>
     *
     * <p>
     * The instance of <code>ResourceManagerServiceBean</code>
     * should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("The bean should be created.", this.bean);
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#initialize()}.
     * </p>
     *
     * <p>
     * The file is empty, <code>ResourceManagerBeanInitializationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_EmptyFile() throws Exception {
        this.setField("file", " ");
        this.setField("namespace", "ResourceManagerServiceBean");
        this.setField("resourceManagerKey", "resourceManagerKey");
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#initialize()}.
     * </p>
     *
     * <p>
     * The namespace is empty, <code>ResourceManagerBeanInitializationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_EmptyNamespace() throws Exception {
        this.setField("file", "ServerSideConfig.properties");
        this.setField("namespace", " ");
        this.setField("resourceManagerKey", "resourceManagerKey");
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#initialize()}.
     * </p>
     *
     * <p>
     * The resourceManagerKey is empty, <code>ResourceManagerBeanInitializationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_EmptyResourceManagerKey() throws Exception {
        this.setField("file", "ServerSideConfig.properties");
        this.setField("namespace", "ResourceManagerServiceBean");
        this.setField("resourceManagerKey", " ");
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#initialize()}.
     * </p>
     *
     * <p>
     * The namespace is null, <code>ResourceManagerBeanInitializationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_NullNamespace() throws Exception {
        this.setField("file", "ServerSideConfig.properties");
        this.setField("namespace", null);
        this.setField("resourceManagerKey", "resourceManagerKey");
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#initialize()}.
     * </p>
     *
     * <p>
     * The resourceManagerKey is empty, <code>ResourceManagerBeanInitializationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_NullResourceManagerKey() throws Exception {
        this.setField("file", "ServerSideConfig.properties");
        this.setField("namespace", "ResourceManagerServiceBean");
        this.setField("resourceManagerKey", null);
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#initialize()}.
     * </p>
     *
     * <p>
     * The file does not exist, <code>ResourceManagerBeanInitializationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_FileNotExist() throws Exception {
        this.setField("file", "NoSuchFile.properties");
        this.setField("namespace", "ResourceManagerServiceBean");
        this.setField("resourceManagerKey", "resourceManagerKey");
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#initialize()}.
     * </p>
     *
     * <p>
     * The file type is not recognized,
     * <code>ResourceManagerBeanInitializationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_UnrecognizedFileType() throws Exception {
        this.setField("file", "ServerSideConfig.Console");
        this.setField("namespace", "ResourceManagerServiceBean");
        this.setField("resourceManagerKey", "resourceManagerKey");
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#initialize()}.
     * </p>
     *
     * <p>
     * The object created by <code>ObjectFactory</code> is not type of <code>ResourceManager</code>,
     * <code>ResourceManagerBeanInitializationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_ClassCastException() throws Exception {
        this.setField("file", "ServerSideConfig.properties");
        this.setField("namespace", "ResourceManagerServiceBean");
        this.setField("resourceManagerKey", "stringKey");
        try {
            this.bean.initialize();
            fail("ResourceManagerBeanInitializationException expected");
        } catch (ResourceManagerBeanInitializationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#initialize()}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed should be instantiated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Accuracy() throws Exception {
        this.setField("file", "ServerSideConfig.properties");
        this.setField("namespace", "ResourceManagerServiceBean");
        this.setField("resourceManagerKey", "resourceManagerKey");

        this.bean.initialize();
        assertNotNull("The resourceManager should be created.", this.getField("resourceManager"));
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.updateResource(this.createResource(), "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>Resource</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_NullResource() throws Exception {
        try {
            this.bean.updateResource(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>Resource</code> does not have role set, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_RoleNotSet() throws Exception {
        try {
            this.bean.updateResource(new Resource(1L), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Given resource's role has phase type, but the resource does not have a phase,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_PhaseNotSet() throws Exception {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        try {
            this.bean.updateResource(resource, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Given operator is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_NullOperator() throws Exception {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        try {
            this.bean.updateResource(resource, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_EmptyOperator() throws Exception {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole(1L));
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        try {
            this.bean.updateResource(resource, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>Resource</code> does not have id set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_IdNotSet() throws Exception {
        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        try {
            this.bean.updateResource(resource, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Error occurs while updating resource,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_ResourceManagementException() throws Exception {
        Resource resource = this.createResource();
        reset(MANAGER);
        MANAGER.updateResource(resource, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.updateResource(resource, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * The resource should be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testUpdateResource_Accuracy() throws Exception {
        Resource resource = this.createResource();
        reset(MANAGER);

        MANAGER.updateResource(resource, "operator");
        expectLastCall().andAnswer(new IAnswer() {
            /**
             * <p>
             * Set the modification user.
             * </p>
             *
             * @return null always.
             */
            public Object answer() {
                Object[] args = getCurrentArguments();
                ((Resource) args[0]).setModificationUser("ME");
                return null;
            }
        });

        replay(MANAGER);

        this.bean.updateResource(resource, "operator");

        assertEquals("Resource should be updated", "ME", resource.getModificationUser());
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResource(Resource, String)}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.removeResource(this.createResource(), "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>Resource</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource_NullResource() throws Exception {
        try {
            this.bean.removeResource(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>Resource</code> does not have id set, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource_IdNotSet() throws Exception {
        try {
            this.bean.removeResource(new Resource(), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Given operator is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource_NullOperator() throws Exception {
        Resource resource = new Resource(1L);
        try {
            this.bean.removeResource(resource, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource_EmptyOperator() throws Exception {
        Resource resource = new Resource(1L);
        try {
            this.bean.removeResource(resource, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Error occurs while removing resource,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource_ResourceManagementException() throws Exception {
        Resource resource = this.createResource();
        reset(MANAGER);
        MANAGER.removeResource(resource, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.removeResource(resource, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResource(Resource, String)}.
     * </p>
     *
     * <p>
     * The resource should be removed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testRemoveResource_Accuracy() throws Exception {
        Resource resource = this.createResource();
        reset(MANAGER);

        MANAGER.removeResource(resource, "operator");
        expectLastCall().andAnswer(new IAnswer() {
            /**
             * <p>
             * Set the modification user.
             * </p>
             *
             * @return null always.
             */
            public Object answer() {
                Object[] args = getCurrentArguments();
                ((Resource) args[0]).setModificationUser("ME");
                return null;
            }
        });

        replay(MANAGER);

        this.bean.removeResource(resource, "operator");

        assertEquals("Resource should be removed", "ME", resource.getModificationUser());
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getResource(long)}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetResource_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.getResource(1L);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getResource(long)}.
     * </p>
     *
     * <p>
     * Given id is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetResource_NegativeId() throws Exception {
        try {
            this.bean.getResource(-1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getResource(long)}.
     * </p>
     *
     * <p>
     * Error occurs while retrieving resource,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetResource_ResourceManagementException() throws Exception {
        reset(MANAGER);
        MANAGER.getResource(1L);
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.getResource(1L);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getResource(long)}.
     * </p>
     *
     * <p>
     * Resource with given id is retrieved.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetResource_Accuracy_1() throws Exception {
        reset(MANAGER);
        expect(MANAGER.getResource(1L)).andStubReturn(new Resource(1L));
        replay(MANAGER);

        Resource resource = this.bean.getResource(1L);
        assertEquals("Resource with given id should be retrieved", 1L, resource.getId());
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getResource(long)}.
     * </p>
     *
     * <p>
     * Resource with given id is not found, null is returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetResource_Accuracy_2() throws Exception {
        reset(MANAGER);
        expect(MANAGER.getResource(Long.MAX_VALUE)).andStubReturn(null);
        replay(MANAGER);

        Resource resource = this.bean.getResource(Long.MAX_VALUE);
        assertNull("Resource with given id is not found, null should be returned", resource);
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.updateResources(new Resource[]{this.createResource(1L)}, 1L, "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Given resources array is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_NullResourcesArray() throws Exception {
        try {
            this.bean.updateResources(null, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Given resources array contains null element,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_ResourcesArrayContainsNullElement() throws Exception {
        try {
            this.bean.updateResources(new Resource[]{null}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Given project is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_NegativeProject() throws Exception {
        try {
            this.bean.updateResources(new Resource[]{}, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * The given array has a <code>Resource</code> which does not have project set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_ProjectNotSet() throws Exception {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        try {
            this.bean.updateResources(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * The given array has a <code>Resource</code> which has project not same as given project,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_ProjectNotSame() throws Exception {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        resource.setProject(2L);
        try {
            this.bean.updateResources(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * The given array has a <code>Resource</code> which does not have <code>ResourceRole</code> set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_RoleNotSet() throws Exception {
        Resource resource = new Resource(1L);
        resource.setPhase(1L);
        resource.setProject(1L);
        try {
            this.bean.updateResources(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * The given array has a <code>Resource</code>'s role has phase type, but the resource does not have a phase,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_PhaseNotSet() throws Exception {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setProject(1L);
        try {
            this.bean.updateResources(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Given operator is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_NullOperator() throws Exception {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        resource.setProject(1L);
        try {
            this.bean.updateResources(new Resource[]{resource}, 1L, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_EmptyOperator() throws Exception {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        resource.setProject(1L);
        try {
            this.bean.updateResources(new Resource[]{resource}, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * The given array has a <code>Resource</code> which does not have id set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_IdNotSet() throws Exception {
        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        resource.setProject(1L);
        try {
            this.bean.updateResources(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Error occurs while updating resources,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_ResourceManagementException() throws Exception {
        Resource resource = this.createResource(1L);
        Resource[] resources = new Resource[]{resource};
        reset(MANAGER);
        MANAGER.updateResources(resources, 1L, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.updateResources(resources, 1L, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * The resources should be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testUpdateResources_Accuracy() throws Exception {
        Resource resource1 = this.createResource(1L);
        Resource resource2 = this.createResource(1L);
        Resource[] resources = new Resource[]{resource1, resource2};
        reset(MANAGER);

        MANAGER.updateResources(resources, 1L, "operator");
        expectLastCall().andAnswer(new IAnswer() {
            /**
             * <p>
             * Set the modification user.
             * </p>
             *
             * @return null always.
             */
            public Object answer() {
                Object[] args = getCurrentArguments();
                for (Resource resource : ((Resource[]) args[0])) {
                    resource.setModificationUser("ME");
                }
                return null;
            }
        });

        replay(MANAGER);

        this.bean.updateResources(resources, 1L, "operator");

        for (Resource res : resources) {
            assertEquals("Resource should be updated", "ME", res.getModificationUser());
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.updateResourceRole(this.createResourceRole(), "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_NullResourceRole() throws Exception {
        try {
            this.bean.updateResourceRole(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> does not have name set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_NameNotSet() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setDescription("description");
        try {
            this.bean.updateResourceRole(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> does not have description set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_DescriptionNotSet() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setName("name");
        try {
            this.bean.updateResourceRole(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> does not have id set, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_IdNotSet() throws Exception {
        ResourceRole resourceRole = new ResourceRole();
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            this.bean.updateResourceRole(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given operator is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_NullOperator() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            this.bean.updateResourceRole(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_EmptyOperator() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            this.bean.updateResourceRole(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Error occurs while updating <code>ResourceRole</code>,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_ResourceManagementException() throws Exception {
        ResourceRole resourceRole = this.createResourceRole();
        reset(MANAGER);
        MANAGER.updateResourceRole(resourceRole, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.updateResourceRole(resourceRole, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * The <code>ResourceRole</code> should be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testUpdateResourceRole_Accuracy() throws Exception {
        ResourceRole resourceRole = this.createResourceRole();
        reset(MANAGER);

        MANAGER.updateResourceRole(resourceRole, "operator");
        expectLastCall().andAnswer(new IAnswer() {
            /**
             * <p>
             * Set the modification user.
             * </p>
             *
             * @return null always.
             */
            public Object answer() {
                Object[] args = getCurrentArguments();
                ((ResourceRole) args[0]).setModificationUser("ME");
                return null;
            }
        });

        replay(MANAGER);

        this.bean.updateResourceRole(resourceRole, "operator");

        assertEquals("ResourceRole should be updated", "ME", resourceRole.getModificationUser());
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.removeResourceRole(this.createResourceRole(), "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole_NullResourceRole() throws Exception {
        try {
            this.bean.removeResourceRole(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> does not have id set, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole_IdNotSet() throws Exception {
        try {
            this.bean.removeResourceRole(new ResourceRole(), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given operator is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole_NullOperator() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        try {
            this.bean.removeResourceRole(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole_EmptyOperator() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1L);
        try {
            this.bean.removeResourceRole(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Error occurs while removing <code>ResourceRole</code>,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole_ResourceManagementException() throws Exception {
        ResourceRole resourceRole = this.createResourceRole();
        reset(MANAGER);
        MANAGER.removeResourceRole(resourceRole, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.removeResourceRole(resourceRole, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * The <code>ResourceRole</code> should be removed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testRemoveResourceRole_Accuracy() throws Exception {
        ResourceRole resourceRole = this.createResourceRole();
        reset(MANAGER);

        MANAGER.removeResourceRole(resourceRole, "operator");
        expectLastCall().andAnswer(new IAnswer() {
            /**
             * <p>
             * Set the modification user.
             * </p>
             *
             * @return null always.
             */
            public Object answer() {
                Object[] args = getCurrentArguments();
                ((ResourceRole) args[0]).setModificationUser("ME");
                return null;
            }
        });

        replay(MANAGER);

        this.bean.removeResourceRole(resourceRole, "operator");

        assertEquals("ResourceRole should be removed", "ME", resourceRole.getModificationUser());
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getAllResourceRoles()}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllResourceRoles_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.getAllResourceRoles();
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getAllResourceRoles()}.
     * </p>
     *
     * <p>
     * Error occurs while retrieving <code>ResourceRole</code>,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllResourceRoles_ResourceManagementException() throws Exception {
        reset(MANAGER);
        expect(MANAGER.getAllResourceRoles()).andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.getAllResourceRoles();
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getAllResourceRoles()}.
     * </p>
     *
     * <p>
     * Cache is not enabled, should always call {@link ResourceManager#getAllResourceRoles()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllResourceRoles_Accuracy_CacheNotEnabled() throws Exception {
        this.setField("cacheResourceRoles", Boolean.FALSE);

        //First call.
        reset(MANAGER);
        expect(MANAGER.getAllResourceRoles()).andStubReturn(new ResourceRole[]{this.createResourceRole()});
        replay(MANAGER);

        assertEquals("The size of array should be 1.", 1, this.bean.getAllResourceRoles().length);

        //Second call. Different values returned.
        reset(MANAGER);
        expect(MANAGER.getAllResourceRoles()).andStubReturn(new ResourceRole[0]);
        replay(MANAGER);

        assertEquals("An empty array should be returned.", 0, this.bean.getAllResourceRoles().length);
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getAllResourceRoles()}.
     * </p>
     *
     * <p>
     * Cache is enabled, the second call will return the same values as the first call.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllResourceRoles_Accuracy_CacheEnabled() throws Exception {
        this.setField("cacheResourceRoles", Boolean.TRUE);

        //First call.
        reset(MANAGER);
        expect(MANAGER.getAllResourceRoles()).andStubReturn(new ResourceRole[]{this.createResourceRole()});
        replay(MANAGER);

        assertEquals("The size of array should be 1.", 1, this.bean.getAllResourceRoles().length);

        //Second call. Same values returned despite the what the ResourceManager will return since
        //values are cached.
        reset(MANAGER);
        expect(MANAGER.getAllResourceRoles()).andStubReturn(new ResourceRole[0]);
        replay(MANAGER);

        assertEquals("The size of array should be 1.", 1, this.bean.getAllResourceRoles().length);
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.updateNotificationType(this.createNotificationType(), "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_NullNotificationType() throws Exception {
        try {
            this.bean.updateNotificationType(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> does not have name set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_NameNotSet() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        resourceRole.setDescription("description");
        try {
            this.bean.updateNotificationType(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> does not have description set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_DescriptionNotSet() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        resourceRole.setName("name");
        try {
            this.bean.updateNotificationType(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> does not have id set, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_IdNotSet() throws Exception {
        NotificationType resourceRole = new NotificationType();
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            this.bean.updateNotificationType(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given operator is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_NullOperator() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            this.bean.updateNotificationType(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_EmptyOperator() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            this.bean.updateNotificationType(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Error occurs while updating <code>NotificationType</code>,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_ResourceManagementException() throws Exception {
        NotificationType resourceRole = this.createNotificationType();
        reset(MANAGER);
        MANAGER.updateNotificationType(resourceRole, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.updateNotificationType(resourceRole, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * The <code>NotificationType</code> should be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testUpdateNotificationType_Accuracy() throws Exception {
        NotificationType resourceRole = this.createNotificationType();
        reset(MANAGER);

        MANAGER.updateNotificationType(resourceRole, "operator");
        expectLastCall().andAnswer(new IAnswer() {
            /**
             * <p>
             * Set the modification user.
             * </p>
             *
             * @return null always.
             */
            public Object answer() {
                Object[] args = getCurrentArguments();
                ((NotificationType) args[0]).setModificationUser("ME");
                return null;
            }
        });

        replay(MANAGER);

        this.bean.updateNotificationType(resourceRole, "operator");

        assertEquals("NotificationType should be updated", "ME", resourceRole.getModificationUser());
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.removeNotificationType(this.createNotificationType(), "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType_NullNotificationType() throws Exception {
        try {
            this.bean.removeNotificationType(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> does not have id set, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType_IdNotSet() throws Exception {
        try {
            this.bean.removeNotificationType(new NotificationType(), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given operator is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType_NullOperator() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        try {
            this.bean.removeNotificationType(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType_EmptyOperator() throws Exception {
        NotificationType resourceRole = new NotificationType(1L);
        try {
            this.bean.removeNotificationType(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Error occurs while removing <code>NotificationType</code>,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType_ResourceManagementException() throws Exception {
        NotificationType resourceRole = this.createNotificationType();
        reset(MANAGER);
        MANAGER.removeNotificationType(resourceRole, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.removeNotificationType(resourceRole, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * The <code>NotificationType</code> should be removed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testRemoveNotificationType_Accuracy() throws Exception {
        NotificationType resourceRole = this.createNotificationType();
        reset(MANAGER);

        MANAGER.removeNotificationType(resourceRole, "operator");
        expectLastCall().andAnswer(new IAnswer() {
            /**
             * <p>
             * Set the modification user.
             * </p>
             *
             * @return null always.
             */
            public Object answer() {
                Object[] args = getCurrentArguments();
                ((NotificationType) args[0]).setModificationUser("ME");
                return null;
            }
        });

        replay(MANAGER);

        this.bean.removeNotificationType(resourceRole, "operator");

        assertEquals("NotificationType should be removed", "ME", resourceRole.getModificationUser());
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getAllNotificationTypes()}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllNotificationTypes_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.getAllNotificationTypes();
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getAllNotificationTypes()}.
     * </p>
     *
     * <p>
     * Error occurs while retrieving <code>NotificationType</code>,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllNotificationTypes_ResourceManagementException() throws Exception {
        reset(MANAGER);
        expect(MANAGER.getAllNotificationTypes()).andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.getAllNotificationTypes();
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getAllNotificationTypes()}.
     * </p>
     *
     * <p>
     * Cache is not enabled, should always call {@link ResourceManager#getAllNotificationTypes()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllNotificationTypes_Accuracy_CacheNotEnabled() throws Exception {
        this.setField("cacheNotificationTypes", Boolean.FALSE);

        //First call.
        reset(MANAGER);
        expect(MANAGER.getAllNotificationTypes()).andStubReturn(new NotificationType[]{this.createNotificationType()});
        replay(MANAGER);

        assertEquals("The size of array should be 1.", 1, this.bean.getAllNotificationTypes().length);

        //Second call. Different values returned.
        reset(MANAGER);
        expect(MANAGER.getAllNotificationTypes()).andStubReturn(new NotificationType[0]);
        replay(MANAGER);

        assertEquals("An empty array should be returned.", 0, this.bean.getAllNotificationTypes().length);
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getAllNotificationTypes()}.
     * </p>
     *
     * <p>
     * Cache is enabled, the second call will return the same values as the first call.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllNotificationTypes_Accuracy_CacheEnabled() throws Exception {
        this.setField("cacheNotificationTypes", Boolean.TRUE);

        //First call.
        reset(MANAGER);
        expect(MANAGER.getAllNotificationTypes()).andStubReturn(new NotificationType[]{this.createNotificationType()});
        replay(MANAGER);

        assertEquals("The size of array should be 1.", 1, this.bean.getAllNotificationTypes().length);

        //Second call. Same values returned despite the what the ResourceManager will return since
        //values are cached.
        reset(MANAGER);
        expect(MANAGER.getAllNotificationTypes()).andStubReturn(new NotificationType[0]);
        replay(MANAGER);

        assertEquals("The size of array should be 1.", 1, this.bean.getAllNotificationTypes().length);
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#addNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.addNotifications(new long[]{1L}, 1L, 1L, "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#addNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given array of user ids is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_NullUserIdsArray() throws Exception {
        try {
            this.bean.addNotifications(null, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#addNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given array of user ids is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_EmptyUserIdsArray() throws Exception {
        try {
            this.bean.addNotifications(new long[0], 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#addNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given array contains an user id which is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_NegativeUserId() throws Exception {
        try {
            this.bean.addNotifications(new long[]{-1L}, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#addNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given project is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_NegativeProject() throws Exception {
        try {
            this.bean.addNotifications(new long[]{1L}, -1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#addNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given notification type is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_NegativeNotificationType() throws Exception {
        try {
            this.bean.addNotifications(new long[]{1L}, 1L, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#addNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given operator is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_NullOperator() throws Exception {
        try {
            this.bean.addNotifications(new long[]{1L}, 1L, 1L, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#addNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_EmptyOperator() throws Exception {
        try {
            this.bean.addNotifications(new long[]{1L}, 1L, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#addNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Error occurs while adding notifications,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_ResourceManagementException() throws Exception {
        long[] userIds = new long[]{1L};
        reset(MANAGER);
        MANAGER.addNotifications(userIds, 1L, 1L, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.addNotifications(userIds, 1L, 1L, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#addNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Adding notifications is successful,
     * <code>ResourceManagementException</code> not expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_Accuracy() throws Exception {
        long[] userIds = new long[]{1L};
        reset(MANAGER);
        MANAGER.addNotifications(userIds, 1L, 1L, "operator");
        replay(MANAGER);
        try {
            this.bean.addNotifications(userIds, 1L, 1L, "operator");
        } catch (ResourceManagementException e) {
            fail("ResourceManagementException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.removeNotifications(new long[]{1L}, 1L, 1L, "operator");
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given array of user ids is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_NullUserIdsArray() throws Exception {
        try {
            this.bean.removeNotifications(null, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given array of user ids is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_EmptyUserIdsArray() throws Exception {
        try {
            this.bean.removeNotifications(new long[0], 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given array contains an user id which is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_NegativeUserId() throws Exception {
        try {
            this.bean.removeNotifications(new long[]{-1L}, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given project is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_NegativeProject() throws Exception {
        try {
            this.bean.removeNotifications(new long[]{1L}, -1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given notification type is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_NegativeNotificationType() throws Exception {
        try {
            this.bean.removeNotifications(new long[]{1L}, 1L, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given operator is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_NullOperator() throws Exception {
        try {
            this.bean.removeNotifications(new long[]{1L}, 1L, 1L, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_EmptyOperator() throws Exception {
        try {
            this.bean.removeNotifications(new long[]{1L}, 1L, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Error occurs while removing notifications,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_ResourceManagementException() throws Exception {
        long[] userIds = new long[]{1L};
        reset(MANAGER);
        MANAGER.removeNotifications(userIds, 1L, 1L, "operator");
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.removeNotifications(userIds, 1L, 1L, "operator");
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#removeNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Removing notifications is successful,
     * <code>ResourceManagementException</code> not expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_Accuracy() throws Exception {
        long[] userIds = new long[]{1L};
        reset(MANAGER);
        MANAGER.removeNotifications(userIds, 1L, 1L, "operator");
        replay(MANAGER);
        try {
            this.bean.removeNotifications(userIds, 1L, 1L, "operator");
        } catch (ResourceManagementException e) {
            fail("ResourceManagementException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getNotifications(long, long)}.
     * </p>
     *
     * <p>
     * The <code>resourceManager</code> filed has not been instantiate,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetNotifications_ResourceManagerNotInitialized() throws Exception {
        this.bean = new ExtendedResourceManagerServiceBean();
        try {
            this.bean.getNotifications(1L, 1L);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getNotifications(long, long)}.
     * </p>
     *
     * <p>
     * Error occurs while retrieving notifications,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetNotifications_ResourceManagementException() throws Exception {
        reset(MANAGER);
        MANAGER.getNotifications(1L, 1L);
        expectLastCall().andStubThrow(new ResourcePersistenceException("mock exception"));
        replay(MANAGER);
        try {
            this.bean.getNotifications(1L, 1L);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getNotifications(long, long)}.
     * </p>
     *
     * <p>
     * Given project is negative, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetNotifications_NegativeProject() throws Exception {
        try {
            this.bean.getNotifications(-1L, 1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getNotifications(long, long)}.
     * </p>
     *
     * <p>
     * Given notification type is negative, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetNotifications_NegativeNotificationType() throws Exception {
        try {
            this.bean.getNotifications(1L, -1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceBean#getNotifications(long, long)}.
     * </p>
     *
     * <p>
     * Notification user ids are successfully retrieved.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetNotifications_Accuracy() throws Exception {
        long[] userIds = new long[]{1, 2};
        reset(MANAGER);
        expect(MANAGER.getNotifications(1L, 1L)).andStubReturn(userIds);
        replay(MANAGER);

        long[] result = this.bean.getNotifications(1L, 1L);
        assertEquals("Size of user ids should be 2", 2, result.length);
    }

    /**
     * <p>
     * Get the value from private field.
     * </p>
     *
     * @param fieldName The name of field.
     * @return the value of the field.
     *
     * @throws Exception to JUnit.
     */
    private Object getField(String fieldName) throws Exception {
        Field field = ResourceManagerServiceBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(this.bean);
    }

    /**
     * <p>
     * Set the value into private field.
     * </p>
     *
     * @param fieldName The name of field.
     * @param fieldValue The field value to be set.
     *
     * @throws Exception to JUnit.
     */
    private void setField(String fieldName, Object fieldValue) throws Exception {
        Field field = ResourceManagerServiceBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this.bean, fieldValue);
    }

    /**
     * <p>
     * This class extends <code>ResourceManagerServiceBean</code>.
     * It is used to expose the <code>initialize()</code> method.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private static final class ExtendedResourceManagerServiceBean extends ResourceManagerServiceBean {

        /**
         * <p>
         * Simply delegates to <code>super.initialize()</code>.
         * </p>
         */
        public void initialize() {
            super.initialize();
        }
    }
}
