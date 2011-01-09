/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Project class.
 *
 * @author iamajia
 * @version 1.0
 */
public class ProjectTest extends TestCase {
    /**
     * This projectStatus is used in the test.
     */
    private ProjectStatus projectStatus = new ProjectStatus(1, "projectStatus");

    /**
     * This projectCategory is used in the test.
     */
    private ProjectCategory projectCategory = new ProjectCategory(1, "category1", new ProjectType(2,
        "projectType1", 1));

    /**
     * This project is used in the test.
     */
    private Project project = new Project(projectCategory, projectStatus);

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectTest.class);
    }

    /**
     * Accuracy test of
     * <code>Project(ProjectCategory projectCategory, ProjectStatus projectStatus)</code>
     * constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectAccuracy1() throws Exception {
        Project newProject = new Project(projectCategory, projectStatus);
        assertEquals("id is incorrect.", 0, newProject.getId());
        assertEquals("projectCategory is incorrect.", projectCategory, newProject.getProjectCategory());
        assertEquals("projectStatus is incorrect.", projectStatus, newProject.getProjectStatus());
        assertTrue("Properties is incorrect.", newProject.getAllProperties().isEmpty());
    }

    /**
     * Failure test of
     * <code>Project(ProjectCategory projectCategory, ProjectStatus projectStatus)</code>
     * constructor.
     * <p>
     * projectCategory is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectFailure1() throws Exception {
        try {
            new Project(null, projectStatus);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>Project(ProjectCategory projectCategory, ProjectStatus projectStatus)</code>
     * constructor.
     * <p>
     * projectStatus is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectFailure2() throws Exception {
        try {
            new Project(projectCategory, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of
     * <code>Project(long projectId, ProjectCategory projectCategory, ProjectStatus projectStatus)</code>
     * constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectAccuracy2() throws Exception {
        Project newProject = new Project(0, projectCategory, projectStatus);
        assertEquals("id is incorrect.", 0, newProject.getId());
        assertEquals("projectCategory is incorrect.", projectCategory, newProject.getProjectCategory());
        assertEquals("projectStatus is incorrect.", projectStatus, newProject.getProjectStatus());
        assertTrue("Properties is incorrect.", newProject.getAllProperties().isEmpty());

    }

    /**
     * Failure test of
     * <code>Project(long projectId, ProjectCategory projectCategory, ProjectStatus projectStatus)</code>
     * constructor.
     * <p>
     * projectId < 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectFailure3() throws Exception {
        try {
            new Project(-1, projectCategory, projectStatus);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>Project(long projectId, ProjectCategory projectCategory, ProjectStatus projectStatus)</code>
     * constructor.
     * <p>
     * projectCategory is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectFailure4() throws Exception {
        try {
            new Project(0, null, projectStatus);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>Project(long projectId, ProjectCategory projectCategory, ProjectStatus projectStatus)</code>
     * constructor.
     * <p>
     * projectStatus is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectFailure5() throws Exception {
        try {
            new Project(0, projectCategory, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of
     * <code>Project(long projectId, ProjectCategory projectCategory,
     * ProjectStatus projectStatus, Map properties)</code>
     * constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectAccuracy3() throws Exception {
        Map properties = new HashMap();
        properties.put("key1", "");
        properties.put("key2", new Object());
        Project newProject = new Project(0, projectCategory, projectStatus, new HashMap(properties));
        assertEquals("id is incorrect.", 0, newProject.getId());
        assertEquals("projectCategory is incorrect.", projectCategory, newProject.getProjectCategory());
        assertEquals("projectStatus is incorrect.", projectStatus, newProject.getProjectStatus());
        assertEquals("Properties is incorrect.", properties, newProject.getAllProperties());
    }

    /**
     * Failure test of
     * <code>Project(long projectId, ProjectCategory projectCategory,
     * ProjectStatus projectStatus, Map properties)</code>
     * constructor.
     * <p>
     * projectId < 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectFailure6() throws Exception {
        try {
            new Project(-1, projectCategory, projectStatus, new HashMap());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>Project(long projectId, ProjectCategory projectCategory,
     * ProjectStatus projectStatus, Map properties)</code>
     * constructor.
     * <p>
     * projectCategory is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectFailure7() throws Exception {
        try {
            new Project(0, null, projectStatus, new HashMap());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>Project(long projectId, ProjectCategory projectCategory,
     * ProjectStatus projectStatus, Map properties)</code>
     * constructor.
     * <p>
     * projectStatus is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectFailure8() throws Exception {
        try {
            new Project(0, projectCategory, null, new HashMap());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>Project(long projectId, ProjectCategory projectCategory,
     * ProjectStatus projectStatus, Map properties)</code>
     * constructor.
     * <p>
     * properties contain a key that is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectFailure9() throws Exception {
        Map properties = new HashMap();
        properties.put(null, new Object());
        try {
            new Project(0, projectCategory, projectStatus, properties);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>Project(long projectId, ProjectCategory projectCategory,
     * ProjectStatus projectStatus, Map properties)</code>
     * constructor.
     * <p>
     * properties contain a key that is Long.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectFailure10() throws Exception {
        Map properties = new HashMap();
        properties.put(new Long(1), new Object());
        try {
            new Project(0, projectCategory, projectStatus, properties);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>Project(long projectId, ProjectCategory projectCategory,
     * ProjectStatus projectStatus, Map properties)</code>
     * constructor.
     * <p>
     * properties contain a key that is empty string.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectFailure11() throws Exception {
        Map properties = new HashMap();
        properties.put("   ", new Object());
        try {
            new Project(0, projectCategory, projectStatus, properties);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>Project(long projectId, ProjectCategory projectCategory,
     * ProjectStatus projectStatus, Map properties)</code>
     * constructor.
     * <p>
     * properties contain a value that is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectFailure12() throws Exception {
        Map properties = new HashMap();
        properties.put("test", null);
        try {
            new Project(0, projectCategory, projectStatus, properties);
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
        project.setId(2);
        assertEquals("id is incorrect.", 2, project.getId());
    }

    /**
     * Failure test of <code>setId(long id)</code> method.
     * <p>
     * id <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetIdFailure() throws Exception {
        try {
            project.setId(0);
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
        project.setId(2);
        assertEquals("id is incorrect.", 2, project.getId());
    }

    /**
     * Accuracy test of
     * <code>setProjectStatus(ProjectStatus projectStatus)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetProjectStatusAccuracy() throws Exception {
        ProjectStatus newProjectStatus = new ProjectStatus(1, "status");
        project.setProjectStatus(newProjectStatus);
        assertEquals("projectStatus is incorrect.", newProjectStatus, project.getProjectStatus());
    }

    /**
     * Failure test of
     * <code>setProjectStatus(ProjectStatus projectStatus)</code> method.
     * <p>
     * projectStatus is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetProjectStatusFailure() throws Exception {
        try {
            project.setProjectStatus(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getProjectStatus()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetProjectStatusAccuracy() throws Exception {
        ProjectStatus newProjectStatus = new ProjectStatus(1, "status");
        project.setProjectStatus(newProjectStatus);
        assertEquals("projectStatus is incorrect.", newProjectStatus, project.getProjectStatus());
    }

    /**
     * Accuracy test of
     * <code>setProjectCategory(ProjectCategory projectCategory)</code>
     * method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetProjectCategoryAccuracy() throws Exception {
        ProjectCategory newProjectCategory = new ProjectCategory(4, "category1", new ProjectType(5, "type1", 1));
        project.setProjectCategory(newProjectCategory);
        assertEquals("projectCategory is incorrect.", newProjectCategory, project.getProjectCategory());
    }

    /**
     * Failure test of
     * <code>setProjectCategory(ProjectCategory projectCategory)</code>
     * method.
     * <p>
     * projectCategory is incorrect.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetProjectCategoryFailure() throws Exception {
        try {
            project.setProjectCategory(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getProjectCategory()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetProjectCategoryAccuracy() throws Exception {
        ProjectCategory newProjectCategory = new ProjectCategory(4, "category1", new ProjectType(5, "type1", 1));
        project.setProjectCategory(newProjectCategory);
        assertEquals("projectCategory is incorrect.", newProjectCategory, project.getProjectCategory());
    }

    /**
     * Accuracy test of <code>setProperty(String name, Object value)</code>
     * method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPropertyAccuracy1() throws Exception {
        project.setProperty("name", "value");
        assertEquals("property is incorrect.", "value", project.getProperty("name"));
    }

    /**
     * Accuracy test of <code>setProperty(String name, Object value)</code>
     * method.
     * <p>
     * value is null. so property should be removed.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPropertyAccuracy2() throws Exception {
        project.setProperty("name", "value");
        project.setProperty("name", null);
        assertNull("property is incorrect.", project.getProperty("name"));
    }

    /**
     * Failure test of <code>setProperty(String name, Object value)</code>
     * method.
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
    public void testSetPropertyFailure1() throws Exception {
        try {
            project.setProperty(null, "value");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setProperty(String name, Object value)</code>
     * method.
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
    public void testSetPropertyFailure2() throws Exception {
        try {
            project.setProperty("  ", "value");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getProperty(String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetPropertyAccuracy1() throws Exception {
        project.setProperty("name", "value");
        assertEquals("property is incorrect.", "value", project.getProperty("name"));
    }

    /**
     * Accuracy test of <code>getProperty(String name)</code> method.
     * <p>
     * property is not exist.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetPropertyAccuracy2() throws Exception {
        assertNull("property is incorrect.", project.getProperty("name"));
    }

    /**
     * Failure test of <code>getProperty(String name)</code> method.
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
    public void testGetPropertyFailure1() throws Exception {
        try {
            project.getProperty(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProperty(String name)</code> method.
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
    public void testGetPropertyFailure2() throws Exception {
        try {
            project.getProperty("   ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getAllProperties()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetAllPropertiesAccuracy() throws Exception {
        Map properties = new HashMap();
        properties.put("key1", "");
        properties.put("key2", new Object());
        Project newProject = new Project(0, projectCategory, projectStatus, new HashMap(properties));
        assertEquals("Properties is incorrect.", properties, newProject.getAllProperties());
    }
}
