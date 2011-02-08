/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.accuracytests;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.Assert;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;

/**
 * <p>
 * Helper used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public final class TestHelper {

    /**
     * The clear table sql.
     */
    private static final String[] CLEAR_SQLS = new String[] {"delete from member_image", "delete from image"};

    /**
     * Represents the entity manager used for CRUD operations on entity.
     */
    private static EntityManager manager;

    /**
     * No instances allowed.
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Gets the entity manager.
     * </p>
     *
     * @return the entity manager
     */
    public static EntityManager getEntityManager() {
        if (manager == null || !manager.isOpen()) {
            manager = Persistence.createEntityManagerFactory("memberPhotoManager").createEntityManager();
        }
        return manager;
    }

    /**
     * Creates the MemberImage for testing.
     *
     * @param image
     *            the associated image
     * @return the member image
     */
    public static MemberImage createMemberImage(Image image) {
        MemberImage memberImage = new MemberImage();
        memberImage.setMemberId(201);
        memberImage.setImage(image);
        memberImage.setCreatedBy("ivern");
        memberImage.setCreatedDate(new Date());
        memberImage.setUpdatedBy("ivern2");
        memberImage.setUpdatedDate(new Date());
        return memberImage;
    }

    /**
     * Creates the image for testing.
     *
     * @return the image
     */
    public static Image createImage() {
        Image image = new Image();
        image.setFileName("member_photo.jpg");

        return image;
    }

    /**
     * Utility to assert the two member images.
     *
     * @param image1
     *            the image 1
     * @param image2
     *            the image 2
     */
    public static void assertMemberImage(MemberImage image1, MemberImage image2) {
        Assert.assertEquals("Property mismatch #getMemberId", image1.getMemberId(), image2.getMemberId());
        Assert.assertEquals("Property mismatch #Image", image1.getImage().getFileName(), image2.getImage()
                .getFileName());
    }

    /**
     * Clears the database tables.
     */
    public static void clearDatabase() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        for (String sql : CLEAR_SQLS) {
            em.createNativeQuery(sql).executeUpdate();
        }
        em.getTransaction().commit();
    }
}
