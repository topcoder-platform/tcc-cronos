/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import com.topcoder.management.resource.Resource;

/**
 * <p>
 * Unit test for <code>{@link CopiedResource}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopiedResourceUnitTests extends BaseTestCase {

    /**
     * <p>
     * Test method {@link CopiedResource#syncProperties(Resource)}.
     * </p>
     *
     * <p>
     * Given resource is null, nothing happens.
     * </p>
     */
    public void testSyncProperties_Null() {
        CopiedResource.syncProperties(null);
    }

    /**
     * <p>
     * Test method {@link CopiedResource#syncProperties(Resource)}.
     * </p>
     *
     * <p>
     * Given resource is not type of <code>CopiedResource</code>, nothing happens.
     * </p>
     */
    public void testSyncProperties_NotTypeOfCopiedResource() {
        CopiedResource.syncProperties(this.createResource());
    }

    /**
     * <p>
     * Test method {@link CopiedResource#copyResource(Resource)}.
     * </p>
     *
     * <p>
     * Given resource is null, null should be returned.
     * </p>
     */
    public void testCopyResource_Null() {
        assertNull("Null should be returned", CopiedResource.copyResource(null));
    }

    /**
     * <p>
     * Test methods {@link CopiedResource#syncProperties(Resource)}
     * and {@link CopiedResource#copyResource(Resource)}.
     * </p>
     */
    public void testAccuracy_1() {
        //A resource with all fields/properties set
        Resource resource = this.createResource(1L);

        //Copy resource
        CopiedResource copiedResource = CopiedResource.copyResource(resource);

        assertEquals("Id should be copied", resource.getId(), copiedResource.getId());
        assertEquals("Phase should be copied", resource.getPhase(), copiedResource.getPhase());
        assertEquals("Project should be copied", resource.getProject(), copiedResource.getProject());
        assertEquals("ResourceRole should be copied", resource.getResourceRole(), copiedResource.getResourceRole());

        assertEquals("Submissions should be copied",
                resource.getSubmissions().length, copiedResource.getSubmissions().length);

        assertEquals("Modify date should be copied", resource.getModificationTimestamp(),
                copiedResource.getModificationTimestamp());
        assertEquals("Create date should be copied", resource.getCreationTimestamp(),
                copiedResource.getCreationTimestamp());
        assertEquals("Create user should be copied", resource.getCreationUser(),
                copiedResource.getCreationUser());
        assertEquals("Modify user should be copied", resource.getModificationUser(),
                copiedResource.getModificationUser());

        //Sync properties
        CopiedResource.syncProperties(copiedResource);
        assertEquals("Properties should be synchronized",
               resource.getAllProperties().size(), copiedResource.getAllProperties().size());
        assertEquals("Properties should be synchronized", "value1", copiedResource.getProperty("property1"));
        assertEquals("Properties should be synchronized", "value2", copiedResource.getProperty("property2"));
    }

    /**
     * <p>
     * Test methods {@link CopiedResource#syncProperties(Resource)}
     * and {@link CopiedResource#copyResource(Resource)}.
     * </p>
     */
    public void testAccuracy_2() {
        //An 'empty' resource
        Resource resource = new Resource();

        //Copy resource
        CopiedResource copiedResource = CopiedResource.copyResource(resource);

        assertEquals("Id should be copied", resource.getId(), copiedResource.getId());
        assertEquals("Phase should be copied", resource.getPhase(), copiedResource.getPhase());
        assertEquals("Project should be copied", resource.getProject(), copiedResource.getProject());
        assertEquals("ResourceRole should be copied", resource.getResourceRole(), copiedResource.getResourceRole());

        assertEquals("Submissions should be copied",
                resource.getSubmissions().length, copiedResource.getSubmissions().length);

        assertEquals("Modify date should be copied", resource.getModificationTimestamp(),
                copiedResource.getModificationTimestamp());
        assertEquals("Create date should be copied", resource.getCreationTimestamp(),
                copiedResource.getCreationTimestamp());
        assertEquals("Create user should be copied", resource.getCreationUser(),
                copiedResource.getCreationUser());
        assertEquals("Modify user should be copied", resource.getModificationUser(),
                copiedResource.getModificationUser());

        //Sync properties
        CopiedResource.syncProperties(copiedResource);
        assertEquals("Properties should be synchronized",
                resource.getAllProperties().size(), copiedResource.getAllProperties().size());
    }
}
