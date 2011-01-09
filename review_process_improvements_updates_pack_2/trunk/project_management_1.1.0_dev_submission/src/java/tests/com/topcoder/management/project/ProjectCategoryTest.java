/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ProjectCategory class.
 *
 * @author iamajia
 * @version 1.0
 */
public class ProjectCategoryTest extends TestCase {
    /**
     * This projectType is used in the test.
     */
    private ProjectType projectType = new ProjectType(2, "type1", "type_des", 1);
    /**
     * This projectCategory is used in the test.
     */
    private ProjectCategory projectCategory = new ProjectCategory(1, "test", "des", projectType);

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectCategoryTest.class);
    }

    /**
     * Accuracy test of <code>ProjectCategory(long id, String name, ProjectType projectType)</code>
     * constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryAccuracy1() throws Exception {
        ProjectCategory newProjectCategory = new ProjectCategory(1, "test", projectType);
        assertEquals("id is incorrect.", 1, newProjectCategory.getId());
        assertEquals("name is incorrect.", "test", newProjectCategory.getName());
        assertEquals("projectType is incorrect.", projectType, newProjectCategory.getProjectType());
    }

    /**
     * Failure test of <code>ProjectCategory(long id, String name, ProjectType projectType)</code>
     * constructor.
     * <p>
     * id = 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryFailure1() throws Exception {
        try {
            new ProjectCategory(0, "test", projectType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>ProjectCategory(long id, String name, ProjectType projectType)</code>
     * constructor.
     * <p>
     * id = -1
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryFailure10() throws Exception {
        try {
            new ProjectCategory(-1, "test", projectType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>ProjectCategory(long id, String name, ProjectType projectType)</code>
     * constructor.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryFailure2() throws Exception {
        try {
            new ProjectCategory(1, null, projectType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectCategory(long id, String name, ProjectType projectType)</code>
     * constructor.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryFailure3() throws Exception {
        try {
            new ProjectCategory(1, "  ", projectType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectCategory(long id, String name, ProjectType projectType)</code>
     * constructor.
     * <p>
     * projectType is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryFailure4() throws Exception {
        try {
            new ProjectCategory(1, "test", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of
     * <code>ProjectCategory(long id, String name, String description, ProjectType projectType)</code>
     * constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryAccuracy2() throws Exception {
        ProjectCategory newProjectCategory = new ProjectCategory(1, "test", "", projectType);
        assertEquals("id is incorrect.", 1, newProjectCategory.getId());
        assertEquals("name is incorrect.", "test", newProjectCategory.getName());
        assertEquals("description is incorrect.", "", newProjectCategory.getDescription());
        assertEquals("projectType is incorrect.", projectType, newProjectCategory.getProjectType());
    }

    /**
     * Failure test of
     * <code>ProjectCategory(long id, String name, String description, ProjectType projectType)</code>
     * constructor.
     * <p>
     * id = 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryFailure5() throws Exception {
        try {
            new ProjectCategory(0, "test", "", projectType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>ProjectCategory(long id, String name, String description, ProjectType projectType)</code>
     * constructor.
     * <p>
     * id = -1
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryFailure11() throws Exception {
        try {
            new ProjectCategory(-1, "test", "", projectType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>ProjectCategory(long id, String name, String description, ProjectType projectType)</code>
     * constructor.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryFailure6() throws Exception {
        try {
            new ProjectCategory(1, null, "", projectType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>ProjectCategory(long id, String name, String description, ProjectType projectType)</code>
     * constructor.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryFailure7() throws Exception {
        try {
            new ProjectCategory(1, "   ", "", projectType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>ProjectCategory(long id, String name, String description, ProjectType projectType)</code>
     * constructor.
     * <p>
     * description is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryFailure8() throws Exception {
        try {
            new ProjectCategory(1, "test", null, projectType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>ProjectCategory(long id, String name, String description, ProjectType projectType)</code>
     * constructor.
     * <p>
     * projectType is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectCategoryFailure9() throws Exception {
        try {
            new ProjectCategory(1, "test", "", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setId(long id)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetIdAccuracy() throws Exception {
        projectCategory.setId(2);
        assertEquals("id is incorrect.", 2, projectCategory.getId());
    }

    /**
     * Failure test of <code>setId(long id)</code> method.
     * <p>
     * id = 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetIdFailure1() throws Exception {
        try {
            projectCategory.setId(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setId(long id)</code> method.
     * <p>
     * id = -1
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetIdFailure2() throws Exception {
        try {
            projectCategory.setId(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Accuracy test of <code>getId()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetIdAccuracy() throws Exception {
        projectCategory.setId(2);
        assertEquals("id is incorrect.", 2, projectCategory.getId());
    }

    /**
     * Accuracy test of <code>setName(String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameAccuracy() throws Exception {
        projectCategory.setName("a");
        assertEquals("name is incorrect.", "a", projectCategory.getName());
    }

    /**
     * Failure test of <code>setName(String name)</code> method.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameFailure1() throws Exception {
        try {
            projectCategory.setName(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setName(String name)</code> method.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameFailure2() throws Exception {
        try {
            projectCategory.setName("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getName()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetNameAccuracy() throws Exception {
        projectCategory.setName("a");
        assertEquals("name is incorrect.", "a", projectCategory.getName());
    }

    /**
     * Accuracy test of <code>setDescription(String description)</code>
     * method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetDescriptionAccuracy() throws Exception {
        projectCategory.setDescription("");
        assertEquals("description is incorrect.", "", projectCategory.getDescription());
    }

    /**
     * Failure test of <code>setDescription(String description)</code> method.
     * <p>
     * description is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetDescriptionFailure() throws Exception {
        try {
            projectCategory.setDescription(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getDescription()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetDescriptionAccuracy() throws Exception {
        projectCategory.setDescription("");
        assertEquals("description is incorrect.", "", projectCategory.getDescription());
    }

    /**
     * Accuracy test of <code>setProjectType(ProjectType projectType)</code>
     * method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetProjectTypeAccuracy() throws Exception {
        ProjectType newProjectType = new ProjectType(3, "test", "des", 1);
        projectCategory.setProjectType(newProjectType);
        assertEquals("projectType is incorrect.", newProjectType, projectCategory.getProjectType());
    }

    /**
     * Failure test of <code>setProjectType(ProjectType projectType)</code>
     * method.
     * <p>
     * projectType is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetProjectTypeFailure() throws Exception {
        try {
            projectCategory.setProjectType(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getProjectType()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetProjectTypeAccuracy() throws Exception {
        ProjectType newProjectType = new ProjectType(3, "test", "des", 1);
        projectCategory.setProjectType(newProjectType);
        assertEquals("projectType is incorrect.", newProjectType, projectCategory.getProjectType());
    }
}
