/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

/**
 * <p>
 * Unit test for <code>{@link GetResourceResponse}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetResourceResponseUnitTests extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link GetResourceResponse#GetResourceResponse()}.
     * </p>
     *
     * <p>
     * The instance of <code>GetResourceResponse</code> should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("GetResourceResponse should be created", new GetResourceResponse());
    }

    /**
     * <p>
     * Test the getter and setter.
     * </p>
     */
    public void testGetterAndSetter() {
        GetResourceResponse response = new GetResourceResponse();
        CopiedResource cp = CopiedResource.copyResource(this.createResource());
        response.setResource(cp);
        assertEquals("Resource should be set.", cp, response.getResource());
    }
}
