/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.accuracytests;

import javax.persistence.EntityManager;

import com.topcoder.util.log.LogManager;
import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagerImpl;
import com.topcoder.web.memberphoto.manager.persistence.JPAMemberPhotoDAO;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for the <code>MemberPhotoManagerImpl</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class MemberPhotoManagerImplAccuracyTest extends TestCase {

    /**
     * Represents the <code>MemberPhotoManagerImpl</code> instance to test.
     */
    private MemberPhotoManagerImpl memberPhotoManagerImpl;

    /**
     * Represents the <code>MemberPhotoDAO</code> instance used for testing.
     */
    private JPAMemberPhotoDAO memberPhotoDAO;

    /**
     * Represents the entity manager.
     */
    private EntityManager entityManager;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        TestHelper.clearDatabase();
        memberPhotoDAO = new JPAMemberPhotoDAO();
        entityManager = TestHelper.getEntityManager();
        memberPhotoDAO.setEntityManager(entityManager);

        memberPhotoManagerImpl = new MemberPhotoManagerImpl();
        memberPhotoManagerImpl.setLog(LogManager.getLog());
        memberPhotoManagerImpl.setVerboseLogging(true);
        memberPhotoManagerImpl.setMemberPhotoDAO(memberPhotoDAO);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        entityManager.clear();
        TestHelper.clearDatabase();
    }

    /**
     * <p>
     * Accuracy test for the <code>MemberPhotoManagerImpl()</code> constructor.
     * </p>
     */
    public void testCtor1Accuracy() {
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl);
    }

    /**
     * <p>
     * Accuracy test for <code>MemberPhotoManagerImpl(String)</code> constructor.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        memberPhotoManagerImpl = new MemberPhotoManagerImpl("TestLog");
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl);
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl.getLog());
    }

    /**
     * <p>
     * Accuracy test for <code>MemberPhotoManagerImpl(MemberPhotoDAO)</code> constructor.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor3Accuracy() throws Exception {
        memberPhotoManagerImpl = new MemberPhotoManagerImpl(memberPhotoDAO);
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl);
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl.getMemberPhotoDAO());
    }

    /**
     * <p>
     * Accuracy test for <code>MemberPhotoManagerImpl(String, MemberPhotoDAO)</code> constructor.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor4Accuracy() throws Exception {
        memberPhotoManagerImpl = new MemberPhotoManagerImpl("TestLog", memberPhotoDAO);
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl);
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl.getLog());
        assertNotNull("MemberPhotoManagerImpl creation failed", memberPhotoManagerImpl.getMemberPhotoDAO());
    }

    /**
     * <p>
     * Accuracy test for <code>getMemberPhoto(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetMemberPhotoAccuracy() throws Exception {
        Image image = TestHelper.createImage();
        MemberImage memberImage = TestHelper.createMemberImage(image);
        entityManager.getTransaction().begin();
        entityManager.persist(image);
        entityManager.persist(memberImage);
        entityManager.getTransaction().commit();
        Image actual = memberPhotoManagerImpl.getMemberPhoto(memberImage.getMemberId());
        assertNotNull("getMemberPhoto failure occured", actual);
        assertEquals("getMemberPhoto failure occured", actual.getFileName(), memberImage.getImage().getFileName());
    }

    /**
     * <p>
     * Accuracy test for <code>saveMemberPhoto(long, Image)</code> method.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSaveMemberPhoto1Accuracy() throws Exception {
        Image image = TestHelper.createImage();
        entityManager.getTransaction().begin();

        memberPhotoManagerImpl.saveMemberPhoto(151L, image, "ivern");

        entityManager.getTransaction().commit();

        Image actual = memberPhotoManagerImpl.getMemberPhoto(151L);

        assertEquals("saveMemberPhoto failed", actual.getFileName(), actual
                .getFileName());
    }

    /**
     * <p>
     * Accuracy test for <code>saveMemberPhoto(long, String)</code> method.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSaveMemberPhoto2Accuracy() throws Exception {
        entityManager.getTransaction().begin();

        memberPhotoManagerImpl.saveMemberPhoto(151, "member_photo_151.jpg", "ivern");
        entityManager.getTransaction().commit();

        Image actual =  memberPhotoManagerImpl.getMemberPhoto(151L);
        assertEquals("saveMemberPhoto failed", actual.getFileName(), actual
                .getFileName());
    }

    /**
     * <p>
     * Accuracy test for <code>removeMemberPhoto(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveMemberPhotoAccuracy() throws Exception {
        Image image = TestHelper.createImage();
        MemberImage memberImage = TestHelper.createMemberImage(image);
        entityManager.getTransaction().begin();
        entityManager.persist(image);
        entityManager.persist(memberImage);
        entityManager.getTransaction().commit();

        memberPhotoManagerImpl.removeMemberPhoto(memberImage.getMemberId(), "ivern");
        assertNull("removeMemberPhoto failed", entityManager.find(MemberImage.class, memberImage.getMemberId()));

        // calling it again does nothing
        memberPhotoManagerImpl.removeMemberPhoto(memberImage.getMemberId(), "ivern");
    }

    /**
     * <p>
     * Accuracy test for <code>getMemberPhotoDAO</code> method.
     * </p>
     */
    public void testGetMemberPhotoDAOAccuracy() {
        memberPhotoManagerImpl.setMemberPhotoDAO(null);
        assertNull("The memberPhotoDAO is not correct.", memberPhotoManagerImpl.getMemberPhotoDAO());
    }

    /**
     * <p>
     * Accuracy test for <code>setMemberPhotoDAO</code> method.
     * </p>
     */
    public void testSetMemberPhotoDAOAccuracy() {
        memberPhotoManagerImpl.setMemberPhotoDAO(memberPhotoDAO);
        assertNotNull("The memberPhotoDAO is not correct.", memberPhotoManagerImpl.getMemberPhotoDAO());
    }

    /**
     * <p>
     * Accuracy test for <code>isVerboseLogging</code> method.
     * </p>
     */
    public void testIsVerboseLoggingAccuracy() {
        memberPhotoManagerImpl.setVerboseLogging(false);
        assertFalse("The verboseLogging is not correct.", memberPhotoManagerImpl.isVerboseLogging());
    }

    /**
     * <p>
     * Accuracy test for <code>setVerboseLogging(boolean)</code> method.
     * </p>
     */
    public void testSetVerboseLoggingAccuracy() {
        memberPhotoManagerImpl.setVerboseLogging(true);
        assertTrue("The verboseLogging is not correct.", memberPhotoManagerImpl.isVerboseLogging());
    }

    /**
     * <p>
     * Accuracy test for <code>getLog</code> method.
     * </p>
     */
    public void testGetLogAccuracy() {
        memberPhotoManagerImpl.setLog(null);
        assertNull("The log is not correct.", memberPhotoManagerImpl.getLog());
    }

    /**
     * <p>
     * Accuracy test for <code>setLog</code> method.
     * </p>
     */
    public void testSetLogAccuracy() {
        memberPhotoManagerImpl.setLog(LogManager.getLog());
        assertNotNull("The log is not correct.", memberPhotoManagerImpl.getLog());
    }
}
