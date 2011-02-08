/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.manager;

import java.util.List;

import javax.persistence.EntityManager;

import com.topcoder.util.log.LogManager;
import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.persistence.JPAMemberPhotoDAO;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link MemberPhotoManagerImpl} class.
 * </p>
 *
 * @author cyberjag, sparemax
 * @version 2.0
 */
public class MemberPhotoManagerImplTest extends TestCase {

    /**
     * Represents the <code>MemberPhotoManagerImpl</code> instance to test.
     */
    private MemberPhotoManagerImpl memberPhotoManagerImpl = null;

    /**
     * Represents the <code>MemberPhotoDAO</code> instance used for testing.
     */
    private JPAMemberPhotoDAO memberPhotoDAO;

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

        entityManager = TestHelper.ENTITY_MANAGER;

        memberPhotoDAO = new JPAMemberPhotoDAO();
        memberPhotoDAO.setEntityManager(entityManager);

        memberPhotoManagerImpl = new MemberPhotoManagerImpl();
        memberPhotoManagerImpl.setLog(LogManager.getLog());
        memberPhotoManagerImpl.setVerboseLogging(true);
        memberPhotoManagerImpl.setMemberPhotoDAO(memberPhotoDAO);

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
        memberPhotoManagerImpl = null;
        entityManager.clear();

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
        return new TestSuite(MemberPhotoManagerImplTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#MemberPhotoManagerImpl()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoManagerImpl() {
        // check for null
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl);
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#MemberPhotoManagerImpl(String)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoManagerImpl1() {
        // check for null
        memberPhotoManagerImpl = new MemberPhotoManagerImpl("TestLog");
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl);
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl.getLog());
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#MemberPhotoManagerImpl(String)} constructor.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String logName : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_MemberPhotoManagerImpl() throws Exception {
        try {
            new MemberPhotoManagerImpl((String) null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#MemberPhotoManagerImpl(String)} constructor.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String logName : Empty string
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_MemberPhotoManagerImpl1() throws Exception {
        try {
            new MemberPhotoManagerImpl("");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#MemberPhotoManagerImpl(String)} constructor.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String logName : Empty string with only spaces
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_MemberPhotoManagerImpl2() throws Exception {
        try {
            new MemberPhotoManagerImpl("   ");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#MemberPhotoManagerImpl(MemberPhotoDAO)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoManagerImpl2() {
        // check for null
        memberPhotoManagerImpl = new MemberPhotoManagerImpl(memberPhotoDAO);
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl);
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl.getMemberPhotoDAO());
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#MemberPhotoManagerImpl(MemberPhotoDAO)} constructor.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      MemberPhotoDAO memberPhotoDAO : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_MemberPhotoManagerImpl3() throws Exception {
        try {
            new MemberPhotoManagerImpl((MemberPhotoDAO) null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#MemberPhotoManagerImpl(String, MemberPhotoDAO)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoManagerImpl3() {
        // check for null
        memberPhotoManagerImpl = new MemberPhotoManagerImpl("TestLog", memberPhotoDAO);
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl);
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl.getLog());
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl.getMemberPhotoDAO());
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#MemberPhotoManagerImpl(String, MemberPhotoDAO)} constructor.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String logName : null value
     *      MemberPhotoDAO memberPhotoDAO : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_MemberPhotoManagerImpl4() throws Exception {
        try {
            new MemberPhotoManagerImpl(null, memberPhotoDAO);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#MemberPhotoManagerImpl(String, MemberPhotoDAO)} constructor.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String logName : Empty string
     *      MemberPhotoDAO memberPhotoDAO : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_MemberPhotoManagerImpl5() throws Exception {
        try {
            new MemberPhotoManagerImpl("", memberPhotoDAO);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#MemberPhotoManagerImpl(String, MemberPhotoDAO)} constructor.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String logName : Empty string with only spaces
     *      MemberPhotoDAO memberPhotoDAO : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_MemberPhotoManagerImpl6() throws Exception {
        try {
            new MemberPhotoManagerImpl("  ", memberPhotoDAO);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#MemberPhotoManagerImpl(String, MemberPhotoDAO)} constructor.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String logName : Valid value
     *      MemberPhotoDAO memberPhotoDAO : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_MemberPhotoManagerImpl7() throws Exception {
        try {
            new MemberPhotoManagerImpl("TestLog", null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#getMemberPhoto(long)} method.
     * </p>
     * <p>
     * Expects a valid instance from persistence.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void test_accuracy_getMemberPhoto_1() throws Exception {
        Image actual = memberPhotoManagerImpl.getMemberPhoto(222444555);

        assertEquals("getMemberPhoto failure occurred", "Handle3.jpg", actual.getFileName());

    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#getMemberPhoto(long)} method.
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
        Image actual = memberPhotoManagerImpl.getMemberPhoto(Long.MAX_VALUE);

        assertNull("getMemberPhoto failure occurred", actual);

    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#getMemberPhoto(long)} method.
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
    public void test_accuracy_getMemberPhoto_NoLogging1() throws Exception {
        memberPhotoManagerImpl.setVerboseLogging(false);

        Image actual = memberPhotoManagerImpl.getMemberPhoto(Long.MAX_VALUE);

        assertNull("getMemberPhoto failure occurred", actual);
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#getMemberPhoto(long)} method.
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
    public void test_accuracy_getMemberPhoto_NoLogging2() throws Exception {
        memberPhotoManagerImpl.setLog(null);

        Image actual = memberPhotoManagerImpl.getMemberPhoto(Long.MAX_VALUE);

        assertNull("getMemberPhoto failure occurred", actual);

    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhoto(long)} method.
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
     * memberPhotoDAO not set
     * </p>
     * <p>
     * Expected {@link IllegalStateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_getMemberPhoto_memberPhotoDAONull() throws Exception {
        memberPhotoManagerImpl.setMemberPhotoDAO(null);

        try {
            memberPhotoManagerImpl.getMemberPhoto(1);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhoto(long)} method.
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
     * memberPhotoDAO not set
     * </p>
     * <p>
     * Expected {@link IllegalStateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_getMemberPhoto_NoLogging() throws Exception {
        memberPhotoManagerImpl.setLog(null);
        memberPhotoManagerImpl.setMemberPhotoDAO(null);

        try {
            memberPhotoManagerImpl.getMemberPhoto(1);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhoto(long)} method.
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
        memberPhotoDAO.setEntityManager(null);

        try {
            memberPhotoManagerImpl.getMemberPhoto(1);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhoto(long)} method.
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
        memberPhotoDAO.setEntityManager(badEntityManager);

        try {
            memberPhotoManagerImpl.getMemberPhoto(1);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhoto(long)} method.
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
        memberPhotoDAO.setEntityManager(closedEntityManager);

        try {
            memberPhotoManagerImpl.getMemberPhoto(1);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhoto(long)} method.
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
            memberPhotoManagerImpl.getMemberPhoto(222444555);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, Image, String)} method.
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
        memberPhotoManagerImpl.saveMemberPhoto(222555666, image, byUser);
        entityManager.getTransaction().commit();

        Image actual = memberPhotoManagerImpl.getMemberPhoto(222555666);
        assertEquals("saveMemberPhoto failed", "NewHandle4.jpg", actual.getFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, Image, String)} method.
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
        memberPhotoManagerImpl.saveMemberPhoto(222222333, image, byUser);
        entityManager.getTransaction().commit();

        Image actual = memberPhotoManagerImpl.getMemberPhoto(222222333);
        assertEquals("saveMemberPhoto failed", "NewHandle4.jpg", actual.getFileName());
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, Image, String)} method.
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
            memberPhotoManagerImpl.saveMemberPhoto(222555666, image, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, Image, String)} method.
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
            memberPhotoManagerImpl.saveMemberPhoto(222555666, image, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, Image, String)} method.
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
            memberPhotoManagerImpl.saveMemberPhoto(222555666, image, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, Image, String)} method.
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
     * memberPhotoDAO is null
     * </p>
     * <p>
     * Expected {@link IllegalStateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_saveMemberPhoto1_memberPhotoDAONull() throws Exception {
        Image image = new Image();
        image.setFileName("NewHandle4.jpg");

        memberPhotoManagerImpl.setMemberPhotoDAO(null);

        try {
            memberPhotoManagerImpl.saveMemberPhoto(222555666, image, byUser);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, Image, String)} method.
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

        memberPhotoDAO.setEntityManager(null);

        try {
            memberPhotoManagerImpl.saveMemberPhoto(222555666, image, byUser);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, Image, String)} method.
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

        memberPhotoDAO.setEntityManager(badEntityManager);
        try {
            memberPhotoManagerImpl.saveMemberPhoto(222555666, image, byUser);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, Image, String)} method.
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

        memberPhotoDAO.setEntityManager(closedEntityManager);
        try {
            memberPhotoManagerImpl.saveMemberPhoto(222555666, image, byUser);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, String, String)} method.
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
        memberPhotoManagerImpl.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
        entityManager.getTransaction().commit();

        Image actual = memberPhotoManagerImpl.getMemberPhoto(222555777);
        assertEquals("saveMemberPhoto failed", "PicForHandle5.jpg", actual.getFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, String, String)} method.
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
        memberPhotoManagerImpl.saveMemberPhoto(222222333, "PicForHandle5.jpg", byUser);
        entityManager.getTransaction().commit();

        Image actual = memberPhotoManagerImpl.getMemberPhoto(222222333);
        assertEquals("saveMemberPhoto failed", "PicForHandle5.jpg", actual.getFileName());
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, String, String)} method.
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
            memberPhotoManagerImpl.saveMemberPhoto(222555777, (String) null, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, String, String)} method.
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
            memberPhotoManagerImpl.saveMemberPhoto(222555777, TestHelper.EMPTY_STRING, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, String, String)} method.
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
            memberPhotoManagerImpl.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, String, String)} method.
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
            memberPhotoManagerImpl.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, String, String)} method.
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
     * memberPhotoDAO is null
     * </p>
     * <p>
     * Expected {@link IllegalStateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_saveMemberPhoto2_memberPhotoDAONull() throws Exception {
        memberPhotoManagerImpl.setMemberPhotoDAO(null);

        try {
            memberPhotoManagerImpl.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, String, String)} method.
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
        memberPhotoDAO.setEntityManager(null);

        try {
            memberPhotoManagerImpl.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, String, String)} method.
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
        memberPhotoDAO.setEntityManager(badEntityManager);
        try {
            memberPhotoManagerImpl.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#saveMemberPhoto(long, String, String)} method.
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
        memberPhotoDAO.setEntityManager(closedEntityManager);
        try {

            memberPhotoManagerImpl.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#removeMemberPhoto(long, String)} method.
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
        memberPhotoManagerImpl.removeMemberPhoto(222333444, byUser);
        entityManager.getTransaction().commit();

        Image actual = memberPhotoManagerImpl.getMemberPhoto(222333444);

        assertNull("removeMemberPhoto failed", actual);
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#removeMemberPhoto(long, String)} method.
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
            memberPhotoManagerImpl.removeMemberPhoto(222333444, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#removeMemberPhoto(long, String)} method.
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
            memberPhotoManagerImpl.removeMemberPhoto(222333444, byUser);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#removeMemberPhoto(long, String)} method.
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
     * memberPhotoDAO is null
     * </p>
     * <p>
     * Expected {@link IllegalStateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_removeMemberPhoto_memberPhotoDAONull() throws Exception {
        memberPhotoManagerImpl.setMemberPhotoDAO(null);

        try {
            memberPhotoManagerImpl.removeMemberPhoto(222333444, byUser);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#removeMemberPhoto(long, String)} method.
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
        memberPhotoDAO.setEntityManager(null);

        try {
            memberPhotoManagerImpl.removeMemberPhoto(222333444, byUser);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#removeMemberPhoto(long, String)} method.
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
        memberPhotoDAO.setEntityManager(badEntityManager);

        try {
            memberPhotoManagerImpl.removeMemberPhoto(222333444, byUser);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#removeMemberPhoto(long, String)} method.
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
        memberPhotoDAO.setEntityManager(closedEntityManager);

        try {
            memberPhotoManagerImpl.removeMemberPhoto(222333444, byUser);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#removeMemberPhoto(long, String)} method.
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
            memberPhotoManagerImpl.removeMemberPhoto(Long.MAX_VALUE, byUser);
            fail("MemberPhotoNotFoundException Expected.");
        } catch (MemberPhotoNotFoundException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#getMemberPhotos(int, int)} method.
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
        memberPhotoManagerImpl.saveMemberPhoto(222555666, image, byUser);
        memberPhotoManagerImpl.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
        memberPhotoManagerImpl.removeMemberPhoto(222333444, byUser);
        entityManager.getTransaction().commit();

        PagedResult<MemberImage> pagedResult = memberPhotoManagerImpl.getMemberPhotos(1, 2);
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
     * Accuracy test for {@link MemberPhotoManagerImpl#getMemberPhotos(int, int)} method.
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
        memberPhotoManagerImpl.saveMemberPhoto(222555666, image, byUser);
        memberPhotoManagerImpl.saveMemberPhoto(222555777, "PicForHandle5.jpg", byUser);
        memberPhotoManagerImpl.removeMemberPhoto(222333444, byUser);
        entityManager.getTransaction().commit();

        PagedResult<MemberImage> pagedResult = memberPhotoManagerImpl.getMemberPhotos(0, 2);
        assertEquals("getMemberPhotos failed", 3, pagedResult.getTotalRecords());

        List<MemberImage> records = pagedResult.getRecords();
        assertEquals("getMemberPhotos failed", 3, records.size());
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhotos(int, int)} method.
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
            memberPhotoManagerImpl.getMemberPhotos(-1, 2);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhotos(int, int)} method.
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
            memberPhotoManagerImpl.getMemberPhotos(1, -1);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhotos(int, int)} method.
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
            memberPhotoManagerImpl.getMemberPhotos(1, 0);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhotos(int, int)} method.
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
     * memberPhotoDAO is null
     * </p>
     * <p>
     * Expected {@link IllegalStateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_getMemberPhotos2_memberPhotoDAONull() throws Exception {
        memberPhotoManagerImpl.setMemberPhotoDAO(null);

        try {
            memberPhotoManagerImpl.getMemberPhotos(1, 2);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhotos(int, int)} method.
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
        memberPhotoDAO.setEntityManager(null);

        try {
            memberPhotoManagerImpl.getMemberPhotos(1, 2);
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhotos(int, int)} method.
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
        memberPhotoDAO.setEntityManager(badEntityManager);
        try {

            memberPhotoManagerImpl.getMemberPhotos(1, 2);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Failure test for {@link MemberPhotoManagerImpl#getMemberPhotos(int, int)} method.
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
        memberPhotoDAO.setEntityManager(closedEntityManager);
        try {
            memberPhotoManagerImpl.getMemberPhotos(1, 2);
            fail("MemberPhotoDAOException Expected.");
        } catch (MemberPhotoDAOException e) {
            // As expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#getMemberPhotoDAO()} and
     * {@link MemberPhotoManagerImpl#setMemberPhotoDAO(MemberPhotoDAO)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     *
     */
    public void test_accuracy_getMemberPhotoDAO() {
        // set the value to test
        memberPhotoManagerImpl.setMemberPhotoDAO(null);
        assertEquals("getMemberPhotoDAO and setMemberPhotoDAO failure occurred", null, memberPhotoManagerImpl
                .getMemberPhotoDAO());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#setMemberPhotoDAO(MemberPhotoDAO)} and
     * {@link MemberPhotoManagerImpl#getMemberPhotoDAO()} method.
     * </p>
     * <p>
     * Sets the value and expects non null value.
     * </p>
     *
     */
    public void test_accuracy_setMemberPhotoDAO() {
        // set the value to test
        memberPhotoManagerImpl.setMemberPhotoDAO(memberPhotoDAO);
        assertNotNull("getMemberPhotoDAO and setMemberPhotoDAO failure occurred", memberPhotoManagerImpl
                .getMemberPhotoDAO());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#isVerboseLogging()} and
     * {@link MemberPhotoManagerImpl#setVerboseLogging(boolean)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is false.
     * </p>
     *
     */
    public void test_accuracy_isVerboseLogging() {
        // set the value to test
        memberPhotoManagerImpl.setVerboseLogging(false);
        assertEquals("isVerboseLogging and setVerboseLogging failure occurred", false, memberPhotoManagerImpl
                .isVerboseLogging());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#setVerboseLogging(boolean)} and
     * {@link MemberPhotoManagerImpl#isVerboseLogging()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is true.
     * </p>
     *
     */
    public void test_accuracy_setVerboseLogging() {
        // set the value to test
        memberPhotoManagerImpl.setVerboseLogging(true);
        assertEquals("isVerboseLogging and setVerboseLogging failure occurred", true, memberPhotoManagerImpl
                .isVerboseLogging());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#getLog()} and {@link MemberPhotoManagerImpl#setLog(Log)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     *
     */
    public void test_accuracy_getLog() {
        // set the value to test
        memberPhotoManagerImpl.setLog(null);
        assertEquals("getLog and setLog failure occurred", null, memberPhotoManagerImpl.getLog());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagerImpl#setLog(Log)} and {@link MemberPhotoManagerImpl#getLog()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects non null value.
     * </p>
     *
     */
    public void test_accuracy_setLog() {
        // set the value to test
        memberPhotoManagerImpl.setLog(LogManager.getLog());
        assertNotNull("getLog and setLog failure occurred", memberPhotoManagerImpl.getLog());
    }
}
