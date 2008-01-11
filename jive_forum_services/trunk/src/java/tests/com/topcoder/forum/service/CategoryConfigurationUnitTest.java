/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.Serializable;


/**
 * <p>
 * Unit tests for <code>CategoryConfiguration</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CategoryConfigurationUnitTest extends TestCase {
    /**
     * <p>The <code>CategoryConfiguration</code> instance for testing.</p>
     */
    private CategoryConfiguration config;

    /**
     * <p>Sets up the environment.</p>
     */
    protected void setUp() {
        config = new CategoryConfiguration();
    }

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(CategoryConfigurationUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>CategoryConfiguration()</code>.
     * </p>
     *
     * <p>
     * As it's a trivial method, we just test whether it can be created successfully.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Unable to create CategoryConfiguration instance.", config);
        assertTrue("The CategoryConfiguration should be instance of Serializable.",
            config instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for <code>getName()</code>.
     * </p>
     *
     * <p>
     * Verify that the initial value is null, and the name can be gotten successfully.
     * </p>
     */
    public void testGetName() {
        assertNull("The name should be initialized as null.", config.getName());

        config.setName("new name");
        assertEquals("The name should match.", "new name", config.getName());
    }

    /**
     * <p>
     * Accuracy test for <code>setName(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the name is assigned to new value.
     * </p>
     */
    public void testSetName() {
        config.setName("name1");
        assertEquals("The name should match.", "name1", config.getName());

        config.setName("name2");
        assertEquals("The name should match.", "name2", config.getName());
    }

    /**
     * <p>
     * Failure test for <code>setName(String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the name is null, <code>IllegalArgumentException</code> is
     * expected.
     * </p>
     */
    public void testSetName_Null() {
        try {
            config.setName(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>setName(String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the name is empty string, <code>IllegalArgumentException</code> is
     * expected.
     * </p>
     */
    public void testSetName_Empty() {
        try {
            config.setName("  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getDescription()</code>.
     * </p>
     *
     * <p>
     * Verify that the initial value is null, and the description can be gotten successfully.
     * </p>
     */
    public void testGetDescription() {
        assertNull("The description should be initialized as null.",
            config.getDescription());

        config.setDescription("new desc");
        assertEquals("The description should match.", "new desc",
            config.getDescription());
    }

    /**
     * <p>
     * Accuracy test for <code>setDescription(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the description is assigned to new value.
     * </p>
     */
    public void testSetDescription() {
        config.setDescription("desc1");
        assertEquals("The description should match.", "desc1",
            config.getDescription());

        config.setDescription("desc2");
        assertEquals("The description should match.", "desc2",
            config.getDescription());
    }

    /**
     * <p>
     * Failure test for <code>setDescription(String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the description is null, <code>IllegalArgumentException</code> is
     * expected.
     * </p>
     */
    public void testSetDescription_Null() {
        try {
            config.setDescription(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>setDescription(String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the description is empty string, <code>IllegalArgumentException</code> is
     * expected.
     * </p>
     */
    public void testSetDescription_Empty() {
        try {
            config.setDescription("  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getRootCategoryId()</code>.
     * </p>
     *
     * <p>
     * Verify that rootCategoryId is initialized with -1, and it can be retrieved successfully.
     * </p>
     */
    public void testGetRootCategoryId() {
        assertEquals("The rootCategoryId's initial value should -1.", -1,
            config.getRootCategoryId());

        config.setRootCategoryId(1000);
        assertEquals("The rootCategoryId should match.", 1000,
            config.getRootCategoryId());
    }

    /**
     * <p>
     * Accuracy test for <code>setRootCategoryId(long)</code>.
     * </p>
     *
     * <p>
     * Verify that the rootCategoryId will be assigned to the new value.
     * </p>
     */
    public void testSetRootCategoryId() {
        config.setRootCategoryId(1);
        assertEquals("The rootCategoryId should match.", 1,
            config.getRootCategoryId());

        config.setRootCategoryId(1000);
        assertEquals("The rootCategoryId should match.", 1000,
            config.getRootCategoryId());
    }

    /**
     * <p>
     * Failure test for <code>setRootCategoryId(long)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the rootCategoryId is non-positive value, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testSetRootCategoryId_Failure() {
        try {
            config.setRootCategoryId(0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getComponentId()</code>.
     * </p>
     *
     * <p>
     * Verify that componentId is initialized with -1, and it can be retrieved successfully.
     * </p>
     */
    public void testGetComponentId() {
        assertEquals("The componentId's initial value should -1.", -1,
            config.getComponentId());

        config.setComponentId(1000);
        assertEquals("The componentId should match.", 1000,
            config.getComponentId());
    }

    /**
     * <p>
     * Accuracy test for <code>setComponentId(long)</code>.
     * </p>
     *
     * <p>
     * Verify that the componentId will be assigned to the new value.
     * </p>
     */
    public void testSetComponentId() {
        config.setComponentId(1);
        assertEquals("The componentId should match.", 1, config.getComponentId());

        config.setComponentId(1000);
        assertEquals("The componentId should match.", 1000,
            config.getComponentId());
    }

    /**
     * <p>
     * Failure test for <code>setComponentId(long)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the componentId is non-positive value, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testSetComponentId_Failure() {
        try {
            config.setComponentId(0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getVersionText()</code>.
     * </p>
     *
     * <p>
     * Verify that the initial value is null, and the versionText can be gotten successfully.
     * </p>
     */
    public void testGetVersionText() {
        assertNull("The versionText should be initialized as null.",
            config.getVersionText());

        config.setVersionText("versionText");
        assertEquals("The versionText should match.", "versionText",
            config.getVersionText());
    }

    /**
     * <p>
     * Accuracy test for <code>setVersionText(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the versionText is assigned to new value.
     * </p>
     */
    public void testSetVersionText() {
        config.setVersionText("versionText1");
        assertEquals("The versionText should match.", "versionText1",
            config.getVersionText());

        config.setVersionText("versionText2");
        assertEquals("The versionText should match.", "versionText2",
            config.getVersionText());
    }

    /**
     * <p>
     * Failure test for <code>setVersionText(String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the versionText is null, <code>IllegalArgumentException</code> is
     * expected.
     * </p>
     */
    public void testSetVersionText_Null() {
        try {
            config.setVersionText(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>setVersionText(String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the versionText is empty string, <code>IllegalArgumentException</code> is
     * expected.
     * </p>
     */
    public void testSetVersionText_Empty() {
        try {
            config.setVersionText("  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getVersionId()</code>.
     * </p>
     *
     * <p>
     * Verify that versionId is initialized with -1, and it can be retrieved successfully.
     * </p>
     */
    public void testGetVersionId() {
        assertEquals("The versionId's initial value should -1.", -1,
            config.getVersionId());

        config.setVersionId(1000);
        assertEquals("The versionId should match.", 1000, config.getVersionId());
    }

    /**
     * <p>
     * Accuracy test for <code>setVersionId(long)</code>.
     * </p>
     *
     * <p>
     * Verify that the versionId will be assigned to the new value.
     * </p>
     */
    public void testSetVersionId() {
        config.setVersionId(1);
        assertEquals("The versionId should match.", 1, config.getVersionId());

        config.setVersionId(1000);
        assertEquals("The versionId should match.", 1000, config.getVersionId());
    }

    /**
     * <p>
     * Failure test for <code>setVersionId(long)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the versionId is non-positive value, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testSetVersionId_Failure() {
        try {
            config.setVersionId(0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>isPublic()</code>.
     * </p>
     *
     * <p>
     * Verify that the isPublic's initial value is true, and it can be retrieved successfully.
     * </p>
     */
    public void testIsPublic() {
        assertTrue("The isPublic's default value is true.", config.isPublic());

        config.setPublic(false);
        assertFalse("The isPublic should match.", config.isPublic());
    }

    /**
     * <p>
     * Accuracy test for <code>setPublic(boolean)</code>.
     * </p>
     *
     * <p>
     * Verify that the isPublic is assigned to the new value.
     * </p>
     */
    public void testSetPublic() {
        config.setPublic(false);
        assertFalse("The isPublic should match.", config.isPublic());

        config.setPublic(true);
        assertTrue("The isPublic should match.", config.isPublic());

        config.setPublic(false);
        assertFalse("The isPublic should match.", config.isPublic());
    }

    /**
     * <p>
     * Accuracy test for <code>getTemplateCategoryId()</code>.
     * </p>
     *
     * <p>
     * Verify that templateCategoryId is initialized with -1, and it can be retrieved successfully.
     * </p>
     */
    public void testGetTemplateCategoryId() {
        assertEquals("The templateCategoryId's initial value should -1.", -1,
            config.getTemplateCategoryId());

        config.setTemplateCategoryId(1000);
        assertEquals("The templateCategoryId should match.", 1000,
            config.getTemplateCategoryId());

        config.setTemplateCategoryId(-1);
        assertEquals("The templateCategoryId can be -1.", -1,
            config.getTemplateCategoryId());
    }

    /**
     * <p>
     * Accuracy test for <code>setTemplateCategoryId()</code>.
     * </p>
     *
     * <p>
     * Verify that the templateCategoryId is assigned to the new value.
     * </p>
     */
    public void testSetTemplateCategoryId() {
        config.setTemplateCategoryId(1000);
        assertEquals("The templateCategoryId should match.", 1000,
            config.getTemplateCategoryId());

        config.setTemplateCategoryId(-1);
        assertEquals("The templateCategoryId can be -1.", -1,
            config.getTemplateCategoryId());
    }

    /**
     * <p>
     * Failure test for <code>setTemplateCategoryId()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the templateCategoryId is not above 0, and neither -1,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testSetTemplateCategoryId_Failure() {
        try {
            config.setTemplateCategoryId(0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>setTemplateCategoryId()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the templateCategoryId is not above 0, and neither -1,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testSetTemplateCategoryId_Failure1() {
        try {
            config.setTemplateCategoryId(-10);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getModeratorUserId()</code>.
     * </p>
     *
     * <p>
     * Verify that moderatorUserId is initialized with -1, and it can be retrieved successfully.
     * </p>
     */
    public void testGetModeratorUserId() {
        assertEquals("The moderatorUserId's initial value should -1.", -1,
            config.getModeratorUserId());

        config.setModeratorUserId(1000);
        assertEquals("The moderatorUserId should match.", 1000,
            config.getModeratorUserId());

        config.setModeratorUserId(-1);
        assertEquals("The moderatorUserId can be -1.", -1,
            config.getModeratorUserId());
    }

    /**
     * <p>
     * Accuracy test for <code>setModeratorUserId()</code>.
     * </p>
     *
     * <p>
     * Verify that the moderatorUserId is assigned to the new value.
     * </p>
     */
    public void testSetModeratorUserId() {
        config.setModeratorUserId(1000);
        assertEquals("The moderatorUserId should match.", 1000,
            config.getModeratorUserId());

        config.setModeratorUserId(-1);
        assertEquals("The moderatorUserId can be -1.", -1,
            config.getModeratorUserId());
    }

    /**
     * <p>
     * Failure test for <code>setModeratorUserId()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the moderatorUserId is not above 0, and neither -1,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testSetModeratorUserId_Failure() {
        try {
            config.setModeratorUserId(0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>setModeratorUserId()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the moderatorUserId is not above 0, and neither -1,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testSetModeratorUserId_Failure1() {
        try {
            config.setModeratorUserId(-10);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getTemplateCategoryType()</code>.
     * </p>
     *
     * <p>
     * Verify that the templateCategoryType's initial value is null, and it can be retrieved successfully.
     * </p>
     */
    public void testGetTemplateCategoryType() {
        assertNull("The templateCategoryType's initial value is null.",
            config.getTemplateCategoryType());

        config.setTemplateCategoryType(CategoryType.APPLICATION);
        assertEquals("The templateCategoryType should match.",
            CategoryType.APPLICATION, config.getTemplateCategoryType());
    }

    /**
     * <p>
     * Accuracy test for <code>setTemplateCategoryType(CategoryType)</code>.
     * </p>
     *
     * <p>
     * Verify that the templateCategoryType is assigned to the new value.
     * </p>
     */
    public void testSetTemplateCategoryType() {
        config.setTemplateCategoryType(CategoryType.APPLICATION);
        assertEquals("The templateCategoryType should match.",
            CategoryType.APPLICATION, config.getTemplateCategoryType());

        config.setTemplateCategoryType(CategoryType.ASSEMBLY_COMPETITION);
        assertEquals("The templateCategoryType should match.",
            CategoryType.ASSEMBLY_COMPETITION, config.getTemplateCategoryType());
    }

    /**
     * <p>
     * Failure test for <code>setTemplateCategoryType(CategoryType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the category type to set is null, <code>IllegalArgumentException</code> is
     * expected.
     * </p>
     */
    public void testSetTemplateCategoryType_Failure() {
        try {
            config.setTemplateCategoryType(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }
}
