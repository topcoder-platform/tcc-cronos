/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ProjectType class.
 *
 * @author iamajia, pvmagacho
 * @version 1.1
 */
public class ProjectTypeTest extends TestCase {
    /**
     * This projectType is used in test.
     */
    private ProjectType projectType = new ProjectType(1, "test", "des", 1);

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectTypeTest.class);
    }

    /**
     * Accuracy test of <code>ProjectType(long id, String name, long version)</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeAccuracy1() throws Exception {
        ProjectType newProjectType = new ProjectType(1, "test", 1);
        assertEquals("id is incorrect.", 1, newProjectType.getId());
        assertEquals("name is incorrect.", "test", newProjectType.getName());
    }

    /**
     * Failure test of <code>ProjectType(long id, String name, long version)</code> constructor.
     *
     * <p>
     * id = 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure1() throws Exception {
        try {
            new ProjectType(0, "test", 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectType(long id, String name)</code> constructor.
     *
     * <p>
     * id = -1
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure8() throws Exception {
        try {
            new ProjectType(-1, "test", 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectType(long id, String name, long version)</code> constructor.
     *
     * <p>
     * name is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure2() throws Exception {
        try {
            new ProjectType(1, null, 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectType(long id, String name, long version)</code> constructor.
     *
     * <p>
     * name is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure3() throws Exception {
        try {
            new ProjectType(1, "  ", 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectType(long id, String name, long  version)</code> constructor.
     *
     * <p>
     * version = 0.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure10() throws Exception {
        try {
            new ProjectType(1, "test", 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectType(long id, String name, long  version)</code> constructor.
     *
     * <p>
     * version = -1.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure11() throws Exception {
        try {
            new ProjectType(1, "test", -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>ProjectType(long id, String name, String description, long version)</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeAccuracy2() throws Exception {
        ProjectType newProjectType = new ProjectType(1, "test", "", 1);
        assertEquals("id is incorrect.", 1, newProjectType.getId());
        assertEquals("name is incorrect.", "test", newProjectType.getName());
        assertEquals("description is incorrect.", "", newProjectType.getDescription());
    }

    /**
     * Failure test of <code>ProjectType(long id, String name, String description, long version)</code> constructor.
     *
     * <p>
     * id = 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure4() throws Exception {
        try {
            new ProjectType(0, "test", "", 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectType(long id, String name, String description, long version)</code> constructor.
     *
     * <p>
     * id = -1
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure9() throws Exception {
        try {
            new ProjectType(-1, "test", "", 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectType(long id, String name, String description, long version)</code> constructor.
     *
     * <p>
     * name is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure5() throws Exception {
        try {
            new ProjectType(1, null, "", 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectType(long id, String name, String description, long version)</code> constructor.
     *
     * <p>
     * name is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure6() throws Exception {
        try {
            new ProjectType(1, "   ", "", 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectType(long id, String name, String description, long version)</code> constructor.
     *
     * <p>
     * description is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure7() throws Exception {
        try {
            new ProjectType(1, "test", null, 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectType(long id, String name, String description, long version)</code> constructor.
     *
     * <p>
     * version = 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure12() throws Exception {
        try {
            new ProjectType(1, "test", "", 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectType(long id, String name, String description, long version)</code> constructor.
     *
     * <p>
     * version = -1
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectTypeFailure13() throws Exception {
        try {
            new ProjectType(1, "test", "", -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setId(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSetIdAccuracy() throws Exception {
        projectType.setId(2);
        assertEquals("id is incorrect.", 2, projectType.getId());
    }

    /**
     * Failure test of <code>setId(long id)</code> method.
     *
     * <p>
     * id = 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSetIdFailure1() throws Exception {
        try {
            projectType.setId(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setId(long id)</code> method.
     *
     * <p>
     * id = -1
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSetIdFailure2() throws Exception {
        try {
            projectType.setId(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getId()</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetIdAccuracy() throws Exception {
        projectType.setId(2);
        assertEquals("id is incorrect.", 2, projectType.getId());
    }

    /**
     * Accuracy test of <code>setName(String name)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSetNameAccuracy() throws Exception {
        projectType.setName("a");
        assertEquals("name is incorrect.", "a", projectType.getName());
    }

    /**
     * Failure test of <code>setName(String name)</code> method.
     *
     * <p>
     * name is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSetNameFailure1() throws Exception {
        try {
            projectType.setName(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setName(String name)</code> method.
     *
     * <p>
     * name is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSetNameFailure2() throws Exception {
        try {
            projectType.setName("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getName()</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetNameAccuracy() throws Exception {
        projectType.setName("a");
        assertEquals("name is incorrect.", "a", projectType.getName());
    }

    /**
     * Accuracy test of <code>setDescription(String description)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSetDescriptionAccuracy() throws Exception {
        projectType.setDescription("");
        assertEquals("description is incorrect.", "", projectType.getDescription());
    }

    /**
     * Failure test of <code>setDescription(String description)</code> method.
     *
     * <p>
     * description is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSetDescriptionFailure() throws Exception {
        try {
            projectType.setDescription(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getDescription()</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetDescriptionAccuracy() throws Exception {
        projectType.setDescription("");
        assertEquals("description is incorrect.", "", projectType.getDescription());
    }

    /**
     * Accuracy test of <code>setReviewSystemVersion(long reviewSystemVersion)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSetReviewSystemVersionAccuracy() throws Exception {
        projectType.setReviewSystemVersion(2);
        assertEquals("id is incorrect.", 2, projectType.getReviewSystemVersion());
    }

    /**
     * Failure test of <code>setReviewSystemVersion(long reviewSystemVersion)</code> method.
     *
     * <p>
     * id = 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSetReviewSystemVersionFailure1() throws Exception {
        try {
            projectType.setReviewSystemVersion(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setReviewSystemVersion(long reviewSystemVersion)</code> method.
     *
     * <p>
     * id = -1
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSetReviewSystemVersionFailure2() throws Exception {
        try {
            projectType.setReviewSystemVersion(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getReviewSystemVersion()</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewSystemVersionAccuracy() throws Exception {
        projectType.setReviewSystemVersion(2);
        assertEquals("id is incorrect.", 2, projectType.getReviewSystemVersion());
    }
}
