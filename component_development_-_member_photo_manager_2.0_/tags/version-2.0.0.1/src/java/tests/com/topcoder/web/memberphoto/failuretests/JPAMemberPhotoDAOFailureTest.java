/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.failuretests;

import javax.persistence.EntityManager;

import junit.framework.TestCase;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.manager.MemberPhotoNotFoundException;
import com.topcoder.web.memberphoto.manager.persistence.JPAMemberPhotoDAO;

/**
 * This is the failure test cases for <code>JPAMemberPhotoDAO</code> class.
 * 
 * @author Xuchen, mumujava
 * @version 2.0
 */
public class JPAMemberPhotoDAOFailureTest extends TestCase {
    
    /**
     * The JPAMemberPhotoDAO instance used for testing.
     */
    private JPAMemberPhotoDAO dao = null;
    
    /**
     * The Image instance used for testing.
     */
    private Image image = null;
    
    /**
     * The EntityManager instance used for testing.
     */
    private EntityManager entityManager = null;
    
    /**
     * Set up the testing environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        dao = new JPAMemberPhotoDAO();
        image = new Image();
        image.setFileName("filename");
        image.setId(1);
        entityManager = FailureTestHelper.getEntityManager();
    }

    /**
     * Test getMemberPhoto method when the entity manager is not set.
     * It should throw IllegalStateException.
     * @throws Exception to JUnit.
     */
    public void testGetMemberPhoto_NoManager() throws Exception {
        try {
            dao.getMemberPhoto(1);
            fail("It should throw IllegalStateException.");
        } catch (IllegalStateException e) {
            // expected.
        }        
    }
    
    /**
     * Test saveMemberPhoto method when the entity manager is not set.
     * It should throw IllegalStateException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_NoManager() throws Exception {
        try {
            dao.saveMemberPhoto(1, image, "test");
            fail("It should throw IllegalStateException.");
        } catch (IllegalStateException e) {
            // expected.
        }        
    }

    /**
     * Test saveMemberPhoto method with invalid argument.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_Null() throws Exception {
        dao.setEntityManager(entityManager);
        try {
            dao.saveMemberPhoto(1, (Image) null, "test");
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    /**
     * Test saveMemberPhoto method with invalid byUser.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_NullbyUser() throws Exception {
        dao.setEntityManager(entityManager);
        try {
            dao.saveMemberPhoto(1, image, null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    /**
     * Test saveMemberPhoto method with invalid byUser.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_EmptybyUser() throws Exception {
        dao.setEntityManager(entityManager);
        try {
            dao.saveMemberPhoto(1, image, "  ");
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }

    /**
     * Test saveMemberPhoto method when the entity manager is not set.
     * It should throw IllegalStateException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_NoManager2() throws Exception {
        try {
            dao.saveMemberPhoto(1, "filename", "test");
            fail("It should throw IllegalStateException.");
        } catch (IllegalStateException e) {
            // expected.
        }        
    }

    /**
     * Test saveMemberPhoto method with invalid argument.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_Null2() throws Exception {
        dao.setEntityManager(entityManager);
        try {
            dao.saveMemberPhoto(1, (String) null, "test");
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    
    /**
     * Test saveMemberPhoto method with invalid argument.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_Empty() throws Exception {
        dao.setEntityManager(entityManager);
        try {
            dao.saveMemberPhoto(1, "  ", "test");
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    
    /**
     * Test saveMemberPhoto method with invalid byUser.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto2_NullbyUser() throws Exception {
        dao.setEntityManager(entityManager);
        try {
            dao.saveMemberPhoto(1, image, null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    /**
     * Test saveMemberPhoto method with invalid byUser.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto2_EmptybyUser() throws Exception {
        dao.setEntityManager(entityManager);
        try {
            dao.saveMemberPhoto(1, image, "  ");
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }

    
    /**
     * Test removeMemberPhoto method when the entity manager is not set.
     * It should throw IllegalStateException.
     * @throws Exception to JUnit.
     */
    public void testRemoveMemberPhoto_NoManager() throws Exception {
        try {
            dao.removeMemberPhoto(1, "test");
            fail("It should throw IllegalStateException.");
        } catch (IllegalStateException e) {
            // expected.
        }        
    }    
    
    /**
     * Test removeMemberPhoto method with invalid byUser.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testremoveMemberPhoto2_NullbyUser() throws Exception {
        dao.setEntityManager(entityManager);
        try {
            dao.removeMemberPhoto(1,  null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    /**
     * Test removeMemberPhoto method with invalid byUser.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testremoveMemberPhoto2_EmptybyUser() throws Exception {
        dao.setEntityManager(entityManager);
        try {
            dao.removeMemberPhoto(1,  "  ");
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }

    /**
     * Test removeMemberPhoto method.
     * It should throw MemberPhotoDAOException.
     * @throws Exception to JUnit.
     */
    public void testremoveMemberPhotoEntityNotFOund() throws Exception {
        dao.setEntityManager(entityManager);
        try {
            dao.removeMemberPhoto(1, "test");
            fail("It should throw MemberPhotoNotFoundException.");
        } catch (MemberPhotoNotFoundException e) {
            // expected.
        }        
    }
    
    /**
     * Test getMemberPhotos method with invalid argument.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testgetMemberPhotos1() throws Exception {
        dao.setEntityManager(entityManager);
        try {
            dao.getMemberPhotos(-1,  1);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    /**
     * Test getMemberPhotos method with invalid argument.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testgetMemberPhotos2() throws Exception {
        dao.setEntityManager(entityManager);
        try {
            dao.getMemberPhotos(1,  0);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
}
