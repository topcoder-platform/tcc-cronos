/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

/**
 * <p>
 * Unit test for <code>{@link UpdateResourceRequest}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UpdateResourceRequestUnitTests extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link UpdateResourceRequest#UpdateResourceRequest()}.
     * </p>
     *
     * <p>
     * The instance of <code>UpdateResourceRequest</code> should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("UpdateResourceRequest should be created", new UpdateResourceRequest());
    }

    /**
     * <p>
     * Test the getter and setter for resource.
     * </p>
     */
    public void testGetterAndSetterForResource() {
        UpdateResourceRequest request = new UpdateResourceRequest();
        CopiedResource cp = CopiedResource.copyResource(this.createResource());
        request.setResource(cp);
        assertEquals("Resource should be set.", cp, request.getResource());
    }

    /**
     * <p>
     * Test the getter and setter for resource.
     * </p>
     */
    public void testGetterAndSetterForOperator() {
        UpdateResourceRequest request = new UpdateResourceRequest();
        request.setOperator("operator");
        assertEquals("operator should be set.", "operator", request.getOperator());
    }
}
