/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.util.Map;

import junit.framework.TestCase;

/**
 * Unit tests for the class: Resource.
 *
 * @author kinfkong
 * @version 1.0
 */
public class ResourceTest extends TestCase {

    /**
     * Tests constructor: Resource().
     *
     * Checks if the fields are properly set.
     */
    public void testResource() {
        Resource resource = new Resource();
        // the instance can be created
        assertNotNull("The instance cannot be created.", resource);
        // check the fields
        assertEquals("The id is not set properly.", Resource.UNSET_ID, resource.getId());
        assertEquals("The resourceRole is not set properly.", null, resource.getResourceRole());
    }

    /**
     * Tests constructor Resource(long).
     *
     * Tests with non-positive id value, IllegalArgumentException should be thrown.
     */
    public void testResourceLong_NonPositvieId() {
        try {
            new Resource(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor Resource(long).
     *
     * Checks if the fields are properly set.
     */
    public void testResourceLong_accuracy() {
        Resource resource = new Resource(10);
        // the instance can be created
        assertNotNull("The instance cannot be created.", resource);
        // check the fields
        assertEquals("The id is not set properly.", 10, resource.getId());
        assertEquals("The resourceRole is not set properly.", null, resource.getResourceRole());
    }

    /**
     * Tests constructor Resource(long, ResourceRole).
     *
     * With non-positive id value, IllegalArgumentException should be thrown.
     */
    public void testResourceLongResourceRole_NonPositiveId() {
        try {
            new Resource(-1, new ResourceRole());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor Resource(long, ResourceRole).
     *
     * With null resouceRole, IllegalArgumentException should be thrown.
     */
    public void testResourceLongResourceRole_NullResourceRole() {
        try {
            new Resource(1, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor Resource(long, ResourceRole).
     *
     * Checks if the fields are properly set.
     */
    public void testResourceLongResourceRole_accuracy() {
        ResourceRole resourceRole = new ResourceRole();
        Resource resource = new Resource(10, resourceRole);
        // the instance can be created
        assertNotNull("The instance cannot be created.", resource);
        // check the fields
        assertEquals("The id is not set properly.", 10, resource.getId());
        assertEquals("The resourceRole is not set properly.", resourceRole, resource.getResourceRole());
    }

    /**
     * Tests method: setId(long).
     *
     * With non-positive id, IllegalArgumentException should be thrown.
     */
    public void testSetId_NonPositive() {
        Resource resource = new Resource();
        try {
            resource.setId(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: setId(long).
     *
     * Reset an already set id, IdAlreadySetException should be thrown.
     */
    public void testSetId_ResetId() {
        Resource resource = new Resource(1);
        try {
            resource.setId(31);
            fail("IdAlreadySetException should be thrown.");
        } catch (IdAlreadySetException e) {
            // success
        }
    }

    /**
     * Tests method: setId(long).
     *
     * Checks if the id is properly set.
     */
    public void testSetId_accuracy() {
        Resource resource = new Resource();
        resource.setId(1);
        assertEquals("The id is not properly set.", 1, resource.getId());
    }

    /**
     * Tests method: getId().
     *
     * Checks if the id is properly returned.
     */
    public void testGetId() {
        Resource resource = new Resource(2);
        assertEquals("The id is not properly returned.", 2, resource.getId());
    }

    /**
     * Tests method: setResourceRole(ResourceRole).
     *
     * Sets with null.
     */
    public void testSetResourceRole_Null() {
        Resource resource = new Resource();
        resource.setResourceRole(null);
        assertEquals("The method does not work properly.", null, resource.getResourceRole());
    }

    /**
     * Tests method: setResourceRole(ResourceRole).
     *
     * Sets with non-null value.
     */
    public void testSetResourceRole_NotNull() {
        Resource resource = new Resource();
        ResourceRole rr = new ResourceRole();
        resource.setResourceRole(rr);
        assertEquals("The method does not work properly.", rr, resource.getResourceRole());
    }

    /**
     * Tests method: getResourceRole(ResourceRole).
     *
     * Sets with null.
     */
    public void testGetResourceRole_Null() {
        Resource resource = new Resource();
        resource.setResourceRole(null);
        assertEquals("The method does not work properly.", null, resource.getResourceRole());
    }

    /**
     * Tests method: getResourceRole(ResourceRole).
     *
     * Sets with non-null value.
     */
    public void testGetResourceRole_NotNull() {
        Resource resource = new Resource();
        ResourceRole rr = new ResourceRole();
        resource.setResourceRole(rr);
        assertEquals("The method does not work properly.", rr, resource.getResourceRole());
    }

    /**
     * Tests method setProject(Long).
     *
     * Sets it with null.
     */
    public void testSetProject_Null() {
        Resource resource = new Resource();
        resource.setProject(null);
        assertEquals("The method does not work properly.", null, resource.getProject());
    }

    /**
     * Tests method setProject(Long).
     *
     * Sets it with non-positive value, IllegalArgumentException should be thrown.
     */
    public void testSetProject_NonPositive() {
        Resource resource = new Resource();
        try {
            resource.setProject(new Long(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method setProject(Long).
     *
     * Sets it with a normal value.
     */
    public void testSetProject_Normal() {
        Resource resource = new Resource();
        resource.setProject(new Long(3));
        assertEquals("The method does not work properly.", new Long(3), resource.getProject());
    }

    /**
     * Tests method getProject(Long).
     *
     * Sets it with a normal value.
     */
    public void testGetProject_Normal() {
        Resource resource = new Resource();
        resource.setProject(new Long(3));
        assertEquals("The method does not work properly.", new Long(3), resource.getProject());
    }

    /**
     * Tests method getProject(Long).
     *
     * Sets it with null.
     */
    public void testGetProject_Null() {
        Resource resource = new Resource();
        resource.setProject(null);
        assertEquals("The method does not work properly.", null, resource.getProject());
    }

    /**
     * Tests method setPhase(Long).
     *
     * Sets it with null.
     */
    public void testSetPhase_Null() {
        Resource resource = new Resource();
        resource.setPhase(null);
        assertEquals("The method does not work properly.", null, resource.getPhase());
    }

    /**
     * Tests method setPhase(Long).
     *
     * Sets it with non-positive value, IllegalArgumentException should be thrown.
     */
    public void testSetPhase_NonPositive() {
        Resource resource = new Resource();
        try {
            resource.setPhase(new Long(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method setPhase(Long).
     *
     * Sets it with a normal value.
     */
    public void testSetPhase_Normal() {
        Resource resource = new Resource();
        resource.setPhase(new Long(3));
        assertEquals("The method does not work properly.", new Long(3), resource.getPhase());
    }

    /**
     * Tests method getPhase(Long).
     *
     * Sets it with a normal value.
     */
    public void testGetPhase_Normal() {
        Resource resource = new Resource();
        resource.setPhase(new Long(3));
        assertEquals("The method does not work properly.", new Long(3), resource.getPhase());
    }

    /**
     * Tests method getPhase(Long).
     *
     * Sets it with null.
     */
    public void testGetPhase_Null() {
        Resource resource = new Resource();
        resource.setPhase(null);
        assertEquals("The method does not work properly.", null, resource.getPhase());
    }

    /**
     * Tests method setSubmission(Long).
     *
     * Sets it with null.
     */
    public void testSetSubmission_Null() {
        Resource resource = new Resource();
        resource.setSubmission(null);
        assertEquals("The method does not work properly.", null, resource.getSubmission());
    }

    /**
     * Tests method setSubmission(Long).
     *
     * Sets it with non-positive value, IllegalArgumentException should be thrown.
     */
    public void testSetSubmission_NonPositive() {
        Resource resource = new Resource();
        try {
            resource.setSubmission(new Long(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method setSubmission(Long).
     *
     * Sets it with a normal value.
     */
    public void testSetSubmission_Normal() {
        Resource resource = new Resource();
        resource.setSubmission(new Long(3));
        assertEquals("The method does not work properly.", new Long(3), resource.getSubmission());
    }

    /**
     * Tests method getSubmission(Long).
     *
     * Sets it with a normal value.
     */
    public void testGetSubmission_Normal() {
        Resource resource = new Resource();
        resource.setSubmission(new Long(3));
        assertEquals("The method does not work properly.", new Long(3), resource.getSubmission());
    }

    /**
     * Tests method getSubmission(Long).
     *
     * Sets it with null.
     */
    public void testGetSubmission_Null() {
        Resource resource = new Resource();
        resource.setSubmission(null);
        assertEquals("The method does not work properly.", null, resource.getSubmission());
    }

    /**
     * Tests method: setProperty(String, Object).
     *
     * With null name, IllegalArgumentException should be thrown.
     */
    public void testSetProperty_NullName() {
        Resource resource = new Resource();
        try {
            resource.setProperty(null, "value 1");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: setProperty(String, Object).
     *
     * With null value, IllegalArgumentException should be thrown.
     */
    public void testSetProperty_NullValue() {
        Resource resource = new Resource();
        try {
            resource.setProperty("name 1", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: setProperty(String, object).
     *
     * Checks if the property is properly set.
     */
    public void testSetProperty_Accuracy() {
        Resource resource = new Resource();
        resource.setProperty("name 1", "value 1");
        assertEquals("The property is not set properly.", "value 1", resource.getProperty("name 1"));
    }

    /**
     * Tests method: hasProperty(String).
     *
     * Tests it with null name, IllegalArgumentException should be thrown.
     */
    public void testHasProperty_NullName() {
        Resource resource = new Resource();
        try {
            resource.hasProperty(null);
            fail("The parameter name cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: hasProperty(String).
     *
     * Tests it with a name that does not contain in the properties, false is expected.
     */
    public void testHasProperty_NotExist() {
        Resource resource = new Resource();
        resource.setProperty("name 1", "value 1");
        resource.setProperty("name 2", "value 2");
        assertFalse("The property should not exist.", resource.hasProperty("NotExist"));
    }

    /**
     * Tests method: hasProperty(String).
     *
     * Tests it with a name that contains in the properties, true is expected.
     */
    public void testHasProperty_Exist() {
        Resource resource = new Resource();
        resource.setProperty("name 1", "value 1");
        resource.setProperty("name 2", "value 2");
        assertTrue("The property should exist.", resource.hasProperty("name 1"));
    }

    /**
     * Tests method: getProperty(String).
     *
     * With a name that does not exist in the properties, null should be returned.
     */
    public void testGetProperty_NotExist() {
        Resource resource = new Resource();
        resource.setProperty("name 1", "value 1");
        resource.setProperty("name 2", "value 2");
        assertNull("The property should not exist, null should be returned.",
                resource.getProperty("name 3"));
    }

    /**
     * Tests method: getProperty(String).
     *
     * With a name that exists in the properties, null should be returned.
     */
    public void testGetProperty_Exist() {
        Resource resource = new Resource();
        resource.setProperty("name 1", "value 1");
        resource.setProperty("name 2", "value 2");
        assertEquals("The property should exist, the value should be returned.", "value 1",
                resource.getProperty("name 1"));
    }


    /**
     * Tests method: getAllProperties().
     *
     * With no properties, empty should be expected.
     */
    public void testGetAllProperties_empty() {
        Resource resource = new Resource();
        Map map = resource.getAllProperties();
        assertEquals("The map should be empty.", 0, map.size());
    }

    /**
     * Tests method: getAllProperties().
     *
     * With three properties, check if the returned map is correct.
     */
    public void testGetAllProperties() {
        Resource resource = new Resource();
        resource.setProperty("name 1", "value 1");
        resource.setProperty("name 2", "value 2");
        resource.setProperty("name 3", "value 3");
        Map map = resource.getAllProperties();
        assertEquals("The map should contain 3 elements.", 3, map.size());
        assertEquals("The value is incorrect.", "value 1", map.get("name 1"));
        assertEquals("The value is incorrect.", "value 2", map.get("name 2"));
        assertEquals("The value is incorrect.", "value 3", map.get("name 3"));
    }

    /**
     * Tests method: getAllProperties().
     *
     * Change the data in the returned map, check if the properties underlying the class changed.
     */
    public void testGetAllProperties_copy() {
        Resource resource = new Resource();
        resource.setProperty("name 1", "value 1");
        resource.setProperty("name 2", "value 2");
        resource.setProperty("name 3", "value 3");
        Map map = resource.getAllProperties();
        // change the map
        map.put("name 1", "new value 1");
        // the Adas still not changed
        map = resource.getAllProperties();
        assertEquals("The map should contain 3 elements.", 3, map.size());
        assertEquals("The value is incorrect.", "value 1", map.get("name 1"));
        assertEquals("The value is incorrect.", "value 2", map.get("name 2"));
        assertEquals("The value is incorrect.", "value 3", map.get("name 3"));
    }

}
