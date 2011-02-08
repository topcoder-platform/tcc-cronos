/*
 * Copyright (C) 2008, 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.manager.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.MemberPhotoDAOException;
import com.topcoder.web.memberphoto.manager.MemberPhotoNotFoundException;
import com.topcoder.web.memberphoto.manager.PagedResult;
import com.topcoder.web.memberphoto.manager.TestHelper;

/**
 * <p>
 * Tests the functionality of {@link JPAMemberPhotoDAO} class.
 * </p>
 *
 * @author cyberjag, sparemax
 * @version 2.0
 */
public class JPAMemberPhotoDAOTest extends TestCase {

    /**
     * Represents the <code>JPAMemberPhotoDAO</code> instance to test.
     */
    private JPAMemberPhotoDAO jPAMemberPhotoDAO;

    /**
     * Represents the entity manager.
     */
    private EntityManager entityManager;

    /**
     * Represents the failure entity manager.
     */
    private EntityManager badEntityManager;

    /**
     * Represents the failure entity manager.
     *
     * @since 2.0
     */
    private EntityManager closedEntityManager;

    /**
     * Represents the username of the user that performs the operation.
     *
     * @since 2.0
     */
    private String byUser = "testUser";

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void setUp() throws Exception {
        TestHelper.clearDB();
        TestHelper.loadDB();

        jPAMemberPhotoDAO = new JPAMemberPhotoDAO();
        entityManager = TestHelper.ENTITY_MANAGER;
        jPAMemberPhotoDAO.setEntityManager(entityManager);

        badEntityManager = TestHelper.ENTITY_MANAGER_BAD;
        closedEntityManager = TestHelper.ENTITY_MANAGER_CLOSED;
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void tearDown() throws Exception {
        jPAMemberPhotoDAO = null;

        TestHelper.clearDB();
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(JPAMemberPhotoDAOTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#JPAMemberPhotoDAO()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_JPAMemberPhotoDAO() {
        // check for null
        assertNotNull("JPAMemberPhotoDAO creation failed", jPAMemberPhotoDAO);
    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#JPAMemberPhotoDAO(EntityManager)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_JPAMemberPhotoDAO1() {
        // check for null
        jPAMemberPhotoDAO = new JPAMemberPhotoDAO(entityManager);
        assertNotNull("JPAMemberPhotoDAO creation failed", jPAMemberPhotoDAO);
        assertNotNull("JPAMemberPhotoDAO creation failed", jPAMemberPhotoDAO.getEntityManager());
    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#getMemberPhoto(long)} method.
     * </p>
     * <p>
     * Expects a valid instance from persistence.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void test_accuracy_getMemberPhoto_1() throws Exception {
        Image actual = jPAMemberPhotoDAO.getMemberPhoto(222444555);

        assertEquals("getMemberPhoto failure occurred", "Handle3.jpg", actual.getFileName());

    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#getMemberPhoto(long)} method.
     * </p>
     * <p>
     * Expects a null from persistence.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     *
     * @since 2.0
     */
    public void test_accuracy_getMemberPhoto_2() throws Exception {
        Image actual = jPAMemberPhotoDAO.getMemberPhoto(Long.MAX_VALUE);

        assertNull("getMemberPhoto failure occurred", actual);

    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#getMemberPhoto(long)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     * </pre>
     *
     * <p>
     * entityManager not set
     * </p>
     * <p>
     * Expected {@link IllegalStateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_getMemberPhoto_1() throws Exception {
        jPAMemberPhotoDAO.setEntityManager(null);

        try {
            jPAMemberPhotoDAO.getMemberPhoto(1);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#getMemberPhoto(long)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     * </pre>
     *
     * <p>
     * Error from the persistence layer
     * </p>
     * <p>
     * Expected {@link MemberPhotoDAOException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_getMemberPhoto_2() throws Exception {
        jPAMemberPhotoDAO.setEntityManager(badEntityManager);

        try {
            jPAMemberPhotoDAO.getMemberPhoto(1);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#getMemberPhoto(long)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     * </pre>
     *
     * <p>
     * entityManager has been closed
     * </p>
     * <p>
     * Expected {@link MemberPhotoDAOException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_getMemberPhoto_3() throws Exception {
        jPAMemberPhotoDAO.setEntityManager(closedEntityManager);

        try {
            jPAMemberPhotoDAO.getMemberPhoto(1);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#getMemberPhoto(long)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     * </pre>
     *
     * <p>
     * More than one result found.
     * </p>
     * <p>
     * Expected {@link MemberPhotoDAOException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_getMemberPhoto_4() throws Exception {
        TestHelper.clearDB();
        TestHelper.executeSQL(TestHelper.TEST_FILES + "DBDataInvalid.sql");

        try {
            jPAMemberPhotoDAO.getMemberPhoto(222444555);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, Image, String)} method.
     * </p>
     * <p>
     * Validates the persistence to check the member photo is persisted.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void test_accuracy_saveMemberPhoto1_1() throws Exception {
        Image image = new Image();
        image.setFileName("NewHandle4.jpg");
        entityManager.getTransaction().begin();
        jPAMemberPhotoDAO.saveMemberPhoto(222555666, image, byUser);
        entityManager.getTransaction().commit();

        Image actual = jPAMemberPhotoDAO.getMemberPhoto(222555666);
        assertEquals("saveMemberPhoto failed", "NewHandle4.jpg", actual.getFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, Image, String)} method.
     * </p>
     * <p>
     * Validates the persistence to check the member photo is persisted.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     *
     * @since 2.0
     */
    public void test_accuracy_saveMemberPhoto1_2() throws Exception {
        Image image = new Image();
        image.setFileName("NewHandle4.jpg");
        entityManager.getTransaction().begin();
        jPAMemberPhotoDAO.saveMemberPhoto(222222333, image, byUser);
        entityManager.getTransaction().commit();

        Image actual = jPAMemberPhotoDAO.getMemberPhoto(222222333);
        assertEquals("saveMemberPhoto failed", "NewHandle4.jpg", actual.getFileName());
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, Image, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid value
     *      Image image : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_saveMemberPhoto1_imageNull() throws Exception {
        Image image = null;

        try {
            jPAMemberPhotoDAO.saveMemberPhoto(222555666, image, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, Image, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid value
     *      Image image : Valid value
     *      String byUser : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_saveMemberPhoto1_byUserNull() throws Exception {
        Image image = new Image();
        image.setFileName("NewHandle4.jpg");

        byUser = null;

        try {
            jPAMemberPhotoDAO.saveMemberPhoto(222555666, image, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, Image, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid value
     *      Image image : Valid value
     *      String byUser : empty string
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_saveMemberPhoto1_byUserEmpty() throws Exception {
        Image image = new Image();
        image.setFileName("NewHandle4.jpg");

        byUser = TestHelper.EMPTY_STRING;

        try {
            jPAMemberPhotoDAO.saveMemberPhoto(222555666, image, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, Image, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     *      Image image : Valid Value
     *      String byUser : Valid value
     * </pre>
     *
     * <p>
     * entityManager is null
     * </p>
     * <p>
     * Expected {@link IllegalStateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_saveMemberPhoto1_1() throws Exception {
        Image image = new Image();
        image.setFileName("NewHandle4.jpg");

        jPAMemberPhotoDAO.setEntityManager(null);

        try {
            jPAMemberPhotoDAO.saveMemberPhoto(222555666, image, byUser);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, Image, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     *      Image image : Valid Value
     *      String byUser : Valid value
     * </pre>
     *
     * <p>
     * Error from the persistence layer
     * </p>
     * <p>
     * Expected {@link MemberPhotoDAOException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_saveMemberPhoto1_2() throws Exception {
        Image image = new Image();
        image.setFileName("NewHandle4.jpg");

        jPAMemberPhotoDAO.setEntityManager(badEntityManager);
        try {
            jPAMemberPhotoDAO.saveMemberPhoto(222555666, image, byUser);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, Image, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     *      Image image : Valid Value
     *      String byUser : Valid value
     * </pre>
     *
     * <p>
     * entityManager has been closed
     * </p>
     * <p>
     * Expected {@link MemberPhotoDAOException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_saveMemberPhoto1_3() throws Exception {
        Image image = new Image();
        image.setFileName("NewHandle4.jpg");

        jPAMemberPhotoDAO.setEntityManager(closedEntityManager);
        try {
            jPAMemberPhotoDAO.saveMemberPhoto(222555666, image, byUser);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, String, String)} method.
     * </p>
     * <p>
     * Validates the persistence to check the member photo is persisted.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void test_accuracy_saveMemberPhoto2_1() throws Exception {
        entityManager.getTransaction().begin();
        jPAMemberPhotoDAO.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
        entityManager.getTransaction().commit();

        Image actual = jPAMemberPhotoDAO.getMemberPhoto(222555777);
        assertEquals("saveMemberPhoto failed", "PicForHandle5.jpg", actual.getFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, String, String)} method.
     * </p>
     * <p>
     * Validates the persistence to check the member photo is persisted.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     *
     * @since 2.0
     */
    public void test_accuracy_saveMemberPhoto2_2() throws Exception {
        entityManager.getTransaction().begin();
        jPAMemberPhotoDAO.saveMemberPhoto(222222333, "PicForHandle5.jpg", byUser);
        entityManager.getTransaction().commit();

        Image actual = jPAMemberPhotoDAO.getMemberPhoto(222222333);
        assertEquals("saveMemberPhoto failed", "PicForHandle5.jpg", actual.getFileName());
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, String, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid value
     *      String fileName : null value
     *      String byUser : Valid Value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_saveMemberPhoto2_fileNameNull() throws Exception {
        try {
            jPAMemberPhotoDAO.saveMemberPhoto(222555777, (String) null, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, String, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid value
     *      String fileName : Empty string
     *      String byUser : Valid Value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_saveMemberPhoto2_fileNameEmpty() throws Exception {
        try {
            jPAMemberPhotoDAO.saveMemberPhoto(222555777, TestHelper.EMPTY_STRING, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, String, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid value
     *      String fileName : Valid value
     *      String byUser : null Value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_saveMemberPhoto2_byUserNull() throws Exception {
        byUser = null;

        try {
            jPAMemberPhotoDAO.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, String, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid value
     *      String fileName : Valid string
     *      String byUser : Empty Value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_saveMemberPhoto2_byUserEmpty() throws Exception {
        byUser = TestHelper.EMPTY_STRING;

        try {
            jPAMemberPhotoDAO.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, String, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     *      String fileName : Valid Value
     *      String byUser : Valid Value
     * </pre>
     *
     * <p>
     * entityManager is null
     * </p>
     * <p>
     * Expected {@link IllegalStateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_saveMemberPhoto2_1() throws Exception {
        jPAMemberPhotoDAO.setEntityManager(null);

        try {
            jPAMemberPhotoDAO.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, String, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     *      String fileName : Valid Value
     *      String byUser : Valid Value
     * </pre>
     *
     * <p>
     * Exception from the persistence layer.
     * </p>
     * <p>
     * Expected {@link MemberPhotoDAOException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_saveMemberPhoto2_2() throws Exception {
        jPAMemberPhotoDAO.setEntityManager(badEntityManager);
        try {
            jPAMemberPhotoDAO.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#saveMemberPhoto(long, String, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     *      String fileName : Valid Value
     *      String byUser : Valid Value
     * </pre>
     *
     * <p>
     * entityManager has been closed
     * </p>
     * <p>
     * Expected {@link MemberPhotoDAOException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_saveMemberPhoto2_3() throws Exception {
        jPAMemberPhotoDAO.setEntityManager(closedEntityManager);
        try {

            jPAMemberPhotoDAO.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#removeMemberPhoto(long, String)} method.
     * </p>
     * <p>
     * Verifies in the persistence whether the member image is removed.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void test_accuracy_removeMemberPhoto() throws Exception {
        entityManager.getTransaction().begin();
        jPAMemberPhotoDAO.removeMemberPhoto(222333444, byUser);
        entityManager.getTransaction().commit();

        Image actual = jPAMemberPhotoDAO.getMemberPhoto(222333444);

        assertNull("removeMemberPhoto failed", actual);
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#removeMemberPhoto(long, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     *      String byUser : null Value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_removeMemberPhoto_byUserNull() throws Exception {
        byUser = null;

        try {
            jPAMemberPhotoDAO.removeMemberPhoto(222333444, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#removeMemberPhoto(long, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     *      String byUser : empty string
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_removeMemberPhoto_byUserEmpty() throws Exception {
        byUser = TestHelper.EMPTY_STRING;

        try {
            jPAMemberPhotoDAO.removeMemberPhoto(222333444, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#removeMemberPhoto(long, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     *      String byUser : Valid Value
     * </pre>
     *
     * <p>
     * entityManager is null
     * </p>
     * <p>
     * Expected {@link IllegalStateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_removeMemberPhoto_1() throws Exception {
        jPAMemberPhotoDAO.setEntityManager(null);

        try {
            jPAMemberPhotoDAO.removeMemberPhoto(222333444, byUser);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#removeMemberPhoto(long, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     *      String byUser : Valid Value
     * </pre>
     *
     * <p>
     * Persistence error occurred
     * </p>
     * <p>
     * Expected {@link MemberPhotoDAOException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_removeMemberPhoto_2() throws Exception {
        jPAMemberPhotoDAO.setEntityManager(badEntityManager);

        try {
            jPAMemberPhotoDAO.removeMemberPhoto(222333444, byUser);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#removeMemberPhoto(long, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     *      String byUser : Valid Value
     * </pre>
     *
     * <p>
     * entityManager has been closed
     * </p>
     * <p>
     * Expected {@link MemberPhotoDAOException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_removeMemberPhoto_3() throws Exception {
        jPAMemberPhotoDAO.setEntityManager(closedEntityManager);

        try {
            jPAMemberPhotoDAO.removeMemberPhoto(222333444, byUser);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#removeMemberPhoto(long, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long memberId : Valid Value
     *      String byUser : Valid Value
     * </pre>
     *
     * <p>
     * an member photo does not exists or is removed
     * </p>
     * <p>
     * Expected {@link MemberPhotoNotFoundException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_removeMemberPhoto_4() throws Exception {
        try {
            jPAMemberPhotoDAO.removeMemberPhoto(Long.MAX_VALUE, byUser);
            fail("MemberPhotoNotFoundException Expected.");
        } catch (MemberPhotoNotFoundException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#getMemberPhotos(int, int)} method.
     * </p>
     * <p>
     * Validates the persistence to check the member photo is persisted.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void test_accuracy_getMemberPhotos2_1() throws Exception {
        Image image = new Image();
        image.setFileName("NewHandle4.jpg");
        entityManager.getTransaction().begin();
        jPAMemberPhotoDAO.saveMemberPhoto(222555666, image, byUser);
        jPAMemberPhotoDAO.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
        jPAMemberPhotoDAO.removeMemberPhoto(222333444, byUser);
        entityManager.getTransaction().commit();

        PagedResult<MemberImage> pagedResult = jPAMemberPhotoDAO.getMemberPhotos(1, 2);
        assertEquals("getMemberPhotos failed", 3, pagedResult.getTotalRecords());

        List<MemberImage> records = pagedResult.getRecords();
        assertEquals("getMemberPhotos failed", 2, records.size());
        MemberImage memberImage = records.get(0);
        assertEquals("getMemberPhotos failed", 222555777, memberImage.getMemberId());
        assertFalse("getMemberPhotos failed", memberImage.isRemoved());
        assertEquals("getMemberPhotos failed", byUser, memberImage.getUpdatedBy());
        assertEquals("getMemberPhotos failed", "PicForHandle5.jpg", memberImage.getImage().getFileName());
        memberImage = records.get(1);
        assertEquals("getMemberPhotos failed", 222555666, memberImage.getMemberId());
        assertFalse("getMemberPhotos failed", memberImage.isRemoved());
        assertEquals("getMemberPhotos failed", byUser, memberImage.getUpdatedBy());
        assertEquals("getMemberPhotos failed", "NewHandle4.jpg", memberImage.getImage().getFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#getMemberPhotos(int, int)} method.
     * </p>
     * <p>
     * Validates the persistence to check the member photo is persisted.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     *
     * @since 2.0
     */
    public void test_accuracy_getMemberPhotos2_2() throws Exception {
        Image image = new Image();
        image.setFileName("NewHandle4.jpg");
        entityManager.getTransaction().begin();
        jPAMemberPhotoDAO.saveMemberPhoto(222555666, image, byUser);
        jPAMemberPhotoDAO.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
        jPAMemberPhotoDAO.removeMemberPhoto(222333444, byUser);
        entityManager.getTransaction().commit();

        PagedResult<MemberImage> pagedResult = jPAMemberPhotoDAO.getMemberPhotos(0, 2);
        assertEquals("getMemberPhotos failed", 3, pagedResult.getTotalRecords());

        List<MemberImage> records = pagedResult.getRecords();
        assertEquals("getMemberPhotos failed", 3, records.size());
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#getMemberPhotos(int, int)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      int pageNo : negative value
     *      int pageSize : Valid Value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_getMemberPhotos2_pageNoNegative() throws Exception {
        try {
            jPAMemberPhotoDAO.getMemberPhotos(-1, 2);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#getMemberPhotos(int, int)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      int pageNo : Valid value
     *      int pageSize : negative Value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_getMemberPhotos2_pageSizeNegative() throws Exception {
        try {
            jPAMemberPhotoDAO.getMemberPhotos(1, -1);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#getMemberPhotos(int, int)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      int pageNo : Valid value
     *      int pageSize : 0 Value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_getMemberPhotos2_pageSizeZero() throws Exception {
        try {
            jPAMemberPhotoDAO.getMemberPhotos(1, 0);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#getMemberPhotos(int, int)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      int pageNo : Valid value
     *      int pageSize : Valid Value
     * </pre>
     *
     * <p>
     * entityManager is null
     * </p>
     * <p>
     * Expected {@link IllegalStateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_getMemberPhotos2_1() throws Exception {
        jPAMemberPhotoDAO.setEntityManager(null);

        try {
            jPAMemberPhotoDAO.getMemberPhotos(1, 2);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#getMemberPhotos(int, int)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      int pageNo : Valid value
     *      int pageSize : Valid Value
     * </pre>
     *
     * <p>
     * Exception from the persistence layer.
     * </p>
     * <p>
     * Expected {@link MemberPhotoDAOException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_getMemberPhotos2_2() throws Exception {
        jPAMemberPhotoDAO.setEntityManager(badEntityManager);
        try {

            jPAMemberPhotoDAO.getMemberPhotos(1, 2);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link JPAMemberPhotoDAO#getMemberPhotos(int, int)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      int pageNo : Valid value
     *      int pageSize : Valid Value
     * </pre>
     *
     * <p>
     * entityManager has been closed
     * </p>
     * <p>
     * Expected {@link MemberPhotoDAOException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     *
     * @since 2.0
     */
    public void test_failure_getMemberPhotos2_3() throws Exception {
        jPAMemberPhotoDAO.setEntityManager(closedEntityManager);
        try {
            jPAMemberPhotoDAO.getMemberPhotos(1, 2);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#getEntityManager()} and
     * {@link JPAMemberPhotoDAO#setEntityManager(EntityManager)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     *
     */
    public void test_accuracy_getEntityManager() {
        // set the value to test
        jPAMemberPhotoDAO.setEntityManager(null);
        assertEquals("getEntityManager and setEntityManager failure occurred", null, jPAMemberPhotoDAO
                .getEntityManager());
    }

    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#setEntityManager(EntityManager)} and
     * {@link JPAMemberPhotoDAO#getEntityManager()} method.
     * </p>
     * <p>
     * Sets the value and expects non null.
     * </p>
     *
     */
    public void test_accuracy_setEntityManager() {
        // set the value to test
        jPAMemberPhotoDAO.setEntityManager(entityManager);
        assertNotNull("getEntityManager and setEntityManager failure occurred", jPAMemberPhotoDAO
                .getEntityManager());
    }
}
