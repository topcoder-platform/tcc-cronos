/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.accuracytests;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.TestCase;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.PagedResult;
import com.topcoder.web.memberphoto.manager.persistence.JPAMemberPhotoDAO;

/**
 * <p>
 * Accuracy test for the <code>JPAMemberPhotoDAO</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class JPAMemberPhotoDAOAccuracyTest extends TestCase {

    /**
     * Represents the <code>JPAMemberPhotoDAO</code> instance to test.
     */
    private JPAMemberPhotoDAO jPAMemberPhotoDAO = null;

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
        jPAMemberPhotoDAO = new JPAMemberPhotoDAO();
        entityManager = TestHelper.getEntityManager();
        jPAMemberPhotoDAO.setEntityManager(entityManager);
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
        entityManager.close();
        TestHelper.clearDatabase();
    }

    /**
     * <p>
     * Accuracy test for <code>JPAMemberPhotoDAO()</code> constructor.
     * </p>
     */
    public void testCtor1Accuracy() {
        assertNotNull("JPAMemberPhotoDAO creation failed", jPAMemberPhotoDAO);
    }

    /**
     * <p>
     * Accuracy test for <code>JPAMemberPhotoDAO(EntityManager)</code> constructor.
     * </p>
     */
    public void testCtor2Accuracy() {
        jPAMemberPhotoDAO = new JPAMemberPhotoDAO(entityManager);
        assertNotNull("JPAMemberPhotoDAO creation failed", jPAMemberPhotoDAO);
        assertNotNull("JPAMemberPhotoDAO creation failed", jPAMemberPhotoDAO.getEntityManager());
    }

    /**
     * <p>
     * Accuracy test for <code>getMemberPhoto</code> method.
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
        Image actual = jPAMemberPhotoDAO.getMemberPhoto(memberImage.getMemberId());
        assertNotNull("getMemberPhoto failure occured", actual);
        assertEquals("getMemberPhoto failure occured", actual.getFileName(), memberImage.getImage().getFileName());
    }
    /**
     * <p>
     * Accuracy test for {@link JPAMemberPhotoDAO#getMemberPhotos(int, int)} method.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetMemberPhoto2Accuracy() throws Exception {
        Image image = TestHelper.createImage();
        entityManager.getTransaction().begin();
        jPAMemberPhotoDAO.saveMemberPhoto(151, image, "ivern");
        jPAMemberPhotoDAO.saveMemberPhoto(152, "PicForHandle5.jpg", "ivern");
        entityManager.getTransaction().commit();

        PagedResult<MemberImage> pagedResult = jPAMemberPhotoDAO.getMemberPhotos(0, 2);
        assertEquals("getMemberPhotos failed", 2, pagedResult.getTotalRecords());

        List<MemberImage> records = pagedResult.getRecords();
        assertEquals("getMemberPhotos failed", 2, records.size());
    }
    /**
     * <p>
     * Accuracy test for <code>saveMemberPhoto(long memberId, Image image)</code> method.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSaveMemberPhoto1Accuracy() throws Exception {
        Image image = TestHelper.createImage();
        entityManager.getTransaction().begin();

        jPAMemberPhotoDAO.saveMemberPhoto(151, image, "ivern");

        entityManager.getTransaction().commit();

        Image actual = jPAMemberPhotoDAO.getMemberPhoto(151L);
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

        jPAMemberPhotoDAO.saveMemberPhoto(151, "member_photo_151.jpg", "ivern");

        entityManager.getTransaction().commit();

        Image actual = jPAMemberPhotoDAO.getMemberPhoto(151L);
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

        jPAMemberPhotoDAO.removeMemberPhoto(memberImage.getMemberId(), "ivern");
        assertNull("removeMemberPhoto failed", entityManager.find(MemberImage.class, memberImage.getMemberId()));

        // calling it again does nothing
        jPAMemberPhotoDAO.removeMemberPhoto(memberImage.getMemberId(), "ivern");
    }

    /**
     * <p>
     * Accuracy test for <code>getEntityManager</code> method.
     * </p>
     */
    public void testGetEntityManagerAccuracy() {
        jPAMemberPhotoDAO.setEntityManager(null);
        assertEquals("The entityManager is not correct.", null, jPAMemberPhotoDAO.getEntityManager());
    }

    /**
     * <p>
     * Accuracy test for <code>setEntityManager</code> method.
     * </p>
     */
    public void testSetEntityManagerAccuracy() {
        jPAMemberPhotoDAO.setEntityManager(entityManager);
        assertNotNull("The entityManager is not correct.", jPAMemberPhotoDAO.getEntityManager());
    }
}
