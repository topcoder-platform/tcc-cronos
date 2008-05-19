/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

/**
 * <p>
 * Unit test for <code>{@link UpdateResourcesRequest}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UpdateResourcesRequestUnitTests extends BaseTestCase {
    /**
     * <p>
     * Test constructor {@link UpdateResourcesRequest#UpdateResourcesRequest()}.
     * </p>
     *
     * <p>
     * The instance of <code>UpdateResourcesRequest</code> should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("UpdateResourcesRequest should be created", new UpdateResourcesRequest());
    }

    /**
     * <p>
     * Test the getter and setter for resources.
     * </p>
     */
    public void testGetterAndSetterForResources() {
        UpdateResourcesRequest request = new UpdateResourcesRequest();
        CopiedResource cp = CopiedResource.copyResource(this.createResource());
        CopiedResource[] cps = new CopiedResource[]{cp};
        request.setResources(cps);
        assertEquals("Resources should be set.", cps, request.getResources());
    }

    /**
     * <p>
     * Test the getter and setter for resource.
     * </p>
     */
    public void testGetterAndSetterForOperator() {
        UpdateResourcesRequest request = new UpdateResourcesRequest();
        request.setOperator("operator");
        assertEquals("operator should be set.", "operator", request.getOperator());
    }

    /**
     * <p>
     * Test the getter and setter for project.
     * </p>
     */
    public void testGetterAndSetterForProject() {
        UpdateResourcesRequest request = new UpdateResourcesRequest();
        request.setProject(1L);
        assertEquals("project should be set.", 1L, request.getProject());
    }
}
