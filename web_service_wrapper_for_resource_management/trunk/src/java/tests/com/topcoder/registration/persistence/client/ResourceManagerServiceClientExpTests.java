/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.registration.persistence.BaseTestCase;
import com.topcoder.registration.persistence.MockResourceManager;
import com.topcoder.registration.persistence.ResourceManagementException;

/**
 * <p>
 * Failure test for <code>{@link ResourceManagerServiceClient}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceManagerServiceClientExpTests extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(String)}.
     * </p>
     *
     * <p>
     * Given url string is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_NullURLString() throws Exception {
        try {
            new ResourceManagerServiceClient((String) null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(String)}.
     * </p>
     *
     * <p>
     * Given url string is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_EmptyURLString() throws Exception {
        try {
            new ResourceManagerServiceClient(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(String)}.
     * </p>
     *
     * <p>
     * Given url string is invalid, <code>ResourceManagerServiceClientCreationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_InvalidURLString_1() throws Exception {
        try {
            new ResourceManagerServiceClient("web.2.0");
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(String)}.
     * </p>
     *
     * <p>
     * Given url string is invalid, <code>ResourceManagerServiceClientCreationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_InvalidURLString_2() throws Exception {
        try {
            new ResourceManagerServiceClient("http://www.google.com");
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(String)}.
     * </p>
     *
     * <p>
     * Given url string is invalid, <code>ResourceManagerServiceClientCreationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_InvalidURLString_3() throws Exception {
        try {
            new ResourceManagerServiceClient("http://www.google.com/");
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(String)}.
     * </p>
     *
     * <p>
     * Given url string is invalid, <code>ResourceManagerServiceClientCreationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_InvalidURLString_4() throws Exception {
        try {
            new ResourceManagerServiceClient("http://www.google.com/web?WSDL");
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(String)}.
     * </p>
     *
     * <p>
     * Given url string is invalid, <code>ResourceManagerServiceClientCreationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_InvalidURLString_5() throws Exception {
        try {
            new ResourceManagerServiceClient("http://localhost:10002101/web?WSDL");
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(URL)}.
     * </p>
     *
     * <p>
     * Given url is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_NullURL() throws Exception {
        try {
            new ResourceManagerServiceClient((URL) null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(URL)}.
     * </p>
     *
     * <p>
     * Given url is invalid, <code>ResourceManagerServiceClientCreationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_InvalidURL() throws Exception {
        try {
            new ResourceManagerServiceClient(new URL("http://www.google.com/service?WSDL"));
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(URL, QName)}.
     * </p>
     *
     * <p>
     * Given url is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor3_NullURL() throws Exception {
        try {
            new ResourceManagerServiceClient(null, DEFAULT_SERVICE_NAME);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(URL, QName)}.
     * </p>
     *
     * <p>
     * Given <code>QName</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor3_NullQName() throws Exception {
        try {
            new ResourceManagerServiceClient(new URL(WSDL), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerServiceClient#ResourceManagerServiceClient(URL, QName)}.
     * </p>
     *
     * <p>
     * Given <code>QName</code> is invalid, <code>ResourceManagerServiceClientCreationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor3_InvalidQName() throws Exception {
        try {
            new ResourceManagerServiceClient(new URL(WSDL),
                new QName("http://www.topcoder.com/Fuck", "FuckService"));
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResource(Resource, String)}.
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
            CLIENT.updateResource(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResource(Resource, String)}.
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
            CLIENT.updateResource(new Resource(1L), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResource(Resource, String)}.
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
            CLIENT.updateResource(resource, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResource(Resource, String)}.
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
            CLIENT.updateResource(resource, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResource(Resource, String)}.
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
            CLIENT.updateResource(resource, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResource(Resource, String)}.
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
            CLIENT.updateResource(resource, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Web service throws error,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_ResourceManagementException() throws Exception {
        try {
            CLIENT.updateResource(this.createResource(), MockResourceManager.FAIL_OPERATOR);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeResource(Resource, String)}.
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
            CLIENT.removeResource(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeResource(Resource, String)}.
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
            CLIENT.removeResource(new Resource(), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeResource(Resource, String)}.
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
            CLIENT.removeResource(resource, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeResource(Resource, String)}.
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
            CLIENT.removeResource(resource, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeResource(Resource, String)}.
     * </p>
     *
     * <p>
     * Web service throws error,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource_ResourceManagementException() throws Exception {
        try {
            CLIENT.removeResource(this.createResource(), MockResourceManager.FAIL_OPERATOR);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#getResource(long)}.
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
            CLIENT.getResource(-1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#getResource(long)}.
     * </p>
     *
     * <p>
     * Web service throws error,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetResource_ResourceManagementException() throws Exception {
        try {
            CLIENT.getResource(MockResourceManager.INVALID_RESOURCE_ID);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResources(Resource[], long, String)}.
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
            CLIENT.updateResources(null, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResources(Resource[], long, String)}.
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
            CLIENT.updateResources(new Resource[]{null}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResources(Resource[], long, String)}.
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
            CLIENT.updateResources(new Resource[]{}, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResources(Resource[], long, String)}.
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
            CLIENT.updateResources(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResources(Resource[], long, String)}.
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
            CLIENT.updateResources(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResources(Resource[], long, String)}.
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
            CLIENT.updateResources(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResources(Resource[], long, String)}.
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
            CLIENT.updateResources(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResources(Resource[], long, String)}.
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
            CLIENT.updateResources(new Resource[]{resource}, 1L, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResources(Resource[], long, String)}.
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
            CLIENT.updateResources(new Resource[]{resource}, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResources(Resource[], long, String)}.
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
            CLIENT.updateResources(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResources(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Web service throws error,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_ResourceManagementException() throws Exception {
        try {
            CLIENT.updateResources(new Resource[]{this.createResource(1L)}, 1L, MockResourceManager.FAIL_OPERATOR);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResourceRole(ResourceRole, String)}.
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
            CLIENT.updateResourceRole(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResourceRole(ResourceRole, String)}.
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
            CLIENT.updateResourceRole(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResourceRole(ResourceRole, String)}.
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
            CLIENT.updateResourceRole(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResourceRole(ResourceRole, String)}.
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
            CLIENT.updateResourceRole(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResourceRole(ResourceRole, String)}.
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
            CLIENT.updateResourceRole(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResourceRole(ResourceRole, String)}.
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
            CLIENT.updateResourceRole(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Web service throws error,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_ResourceManagementException() throws Exception {
        try {
            CLIENT.updateResourceRole(this.createResourceRole(), MockResourceManager.FAIL_OPERATOR);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeResourceRole(ResourceRole, String)}.
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
            CLIENT.removeResourceRole(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeResourceRole(ResourceRole, String)}.
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
            CLIENT.removeResourceRole(new ResourceRole(), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeResourceRole(ResourceRole, String)}.
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
            CLIENT.removeResourceRole(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeResourceRole(ResourceRole, String)}.
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
            CLIENT.removeResourceRole(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeResourceRole(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Web service throws error,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole_ResourceManagementException() throws Exception {
        try {
            CLIENT.removeResourceRole(this.createResourceRole(), MockResourceManager.FAIL_OPERATOR);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateNotificationType(NotificationType, String)}.
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
            CLIENT.updateNotificationType(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateNotificationType(NotificationType, String)}.
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
            CLIENT.updateNotificationType(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateNotificationType(NotificationType, String)}.
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
            CLIENT.updateNotificationType(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateNotificationType(NotificationType, String)}.
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
            CLIENT.updateNotificationType(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateNotificationType(NotificationType, String)}.
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
            CLIENT.updateNotificationType(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateNotificationType(NotificationType, String)}.
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
            CLIENT.updateNotificationType(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#updateNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Web service throws error,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_ResourceManagementException() throws Exception {
        try {
            CLIENT.updateNotificationType(this.createNotificationType(), MockResourceManager.FAIL_OPERATOR);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotificationType(NotificationType, String)}.
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
            CLIENT.removeNotificationType(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotificationType(NotificationType, String)}.
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
            CLIENT.removeNotificationType(new NotificationType(), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotificationType(NotificationType, String)}.
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
            CLIENT.removeNotificationType(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotificationType(NotificationType, String)}.
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
            CLIENT.removeNotificationType(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotificationType(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Web service throws error,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType_ResourceManagementException() throws Exception {
        try {
            CLIENT.removeNotificationType(this.createNotificationType(), MockResourceManager.FAIL_OPERATOR);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#addNotifications(long[], long, long, String)}.
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
            CLIENT.addNotifications(null, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#addNotifications(long[], long, long, String)}.
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
            CLIENT.addNotifications(new long[0], 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#addNotifications(long[], long, long, String)}.
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
            CLIENT.addNotifications(new long[]{-1L}, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#addNotifications(long[], long, long, String)}.
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
            CLIENT.addNotifications(new long[]{1L}, -1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#addNotifications(long[], long, long, String)}.
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
            CLIENT.addNotifications(new long[]{1L}, 1L, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#addNotifications(long[], long, long, String)}.
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
            CLIENT.addNotifications(new long[]{1L}, 1L, 1L, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#addNotifications(long[], long, long, String)}.
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
            CLIENT.addNotifications(new long[]{1L}, 1L, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#addNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Web service throws error,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_ResourceManagementException() throws Exception {
        try {
            CLIENT.addNotifications(new long[]{1L}, 1L, 1L, MockResourceManager.FAIL_OPERATOR);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotifications(long[], long, long, String)}.
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
            CLIENT.removeNotifications(null, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotifications(long[], long, long, String)}.
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
            CLIENT.removeNotifications(new long[0], 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotifications(long[], long, long, String)}.
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
            CLIENT.removeNotifications(new long[]{-1L}, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotifications(long[], long, long, String)}.
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
            CLIENT.removeNotifications(new long[]{1L}, -1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotifications(long[], long, long, String)}.
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
            CLIENT.removeNotifications(new long[]{1L}, 1L, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotifications(long[], long, long, String)}.
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
            CLIENT.removeNotifications(new long[]{1L}, 1L, 1L, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotifications(long[], long, long, String)}.
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
            CLIENT.removeNotifications(new long[]{1L}, 1L, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#removeNotifications(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Web service throws error,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_ResourceManagementException() throws Exception {
        try {
            CLIENT.removeNotifications(new long[]{1L}, 1L, 1L, MockResourceManager.FAIL_OPERATOR);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#getNotifications(long, long)}.
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
            CLIENT.getNotifications(-1L, 1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#getNotifications(long, long)}.
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
            CLIENT.getNotifications(1L, -1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerServiceClient#getNotifications(long, long)}.
     * </p>
     *
     * <p>
     * Web service throws error,
     * <code>ResourceManagementException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetNotifications_ResourceManagementException() throws Exception {
        try {
            CLIENT.getNotifications(1L, MockResourceManager.INVALID_NOTIFICATION_TYPE);
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }
}
