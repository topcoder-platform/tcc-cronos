/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.failuretests;

import junit.framework.TestCase;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.manager.MemberPhotoDAO;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagerImpl;
import com.topcoder.web.memberphoto.manager.persistence.JPAMemberPhotoDAO;

/**
 * This is the failure test cases for <code>MemberPhotoManagerImpl</code> class.
 * 
 * @author Xuchen, mumujava
 * @version 2.0
 */
public class MemberPhotoManagerImplFailureTest extends TestCase {
    
    /**
     * The MemberPhotoManagerImpl instance used for testing.
     */
    private MemberPhotoManagerImpl manager = null;
    
    /**
     * The JPAMemberPhotoDAO instance used for testing.
     */
    private JPAMemberPhotoDAO dao = null;
    
    /**
     * The Image instance used for testing.
     */
    private Image image = null;
    
    /**
     * Set up the testing environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        dao = new JPAMemberPhotoDAO(FailureTestHelper.getEntityManager());
        manager = new MemberPhotoManagerImpl();
        image = new Image();
        image.setFileName("filename");
        image.setId(1);
    }

    /**
     * Test constructor with null argument.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testConstructor_Null() throws Exception {
        try {
            new MemberPhotoManagerImpl((String) null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }
    
    /**
     * Test constructor with empty string argument.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testConstructor_Empty() throws Exception {
        try {
            new MemberPhotoManagerImpl("   ");
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }
    
    /**
     * Test constructor with null argument.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testConstructor_Null2() throws Exception {
        try {
            new MemberPhotoManagerImpl((MemberPhotoDAO) null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }
    
    /**
     * Test constructor with null argument.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testConstructor_Null3() throws Exception {
        try {
            new MemberPhotoManagerImpl((String) null, dao);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }
    
    /**
     * Test constructor with empty string argument.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testConstructor_Empty2() throws Exception {
        try {
            new MemberPhotoManagerImpl("   ", dao);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }
    
    /**
     * Test constructor with null argument.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testConstructor_Null4() throws Exception {
        try {
            new MemberPhotoManagerImpl("logname", (MemberPhotoDAO) null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }
    
    /**
     * Test getMemberPhoto method when the dao is not set.
     * It should throw IllegalStateException.
     * @throws Exception to JUnit.
     */
    public void testGetMemberPhoto_NoDao() throws Exception {
        try {
            manager.getMemberPhoto(1);
            fail("It should throw IllegalStateException.");
        } catch (IllegalStateException e) {
            // expected.
        }        
    }
    
    
    /**
     * Test saveMemberPhoto method when the dao is not set.
     * It should throw IllegalStateException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_NoDao() throws Exception {
        try {
            manager.saveMemberPhoto(1, image, "test");
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
        manager.setMemberPhotoDAO(dao);
        
        try {
            manager.saveMemberPhoto(1, (Image) null, "test");
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    
    /**
     * Test saveMemberPhoto method with null byUser.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_5() throws Exception {
        manager.setMemberPhotoDAO(dao);
        try {
            manager.saveMemberPhoto(1, image, null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    /**
     * Test saveMemberPhoto method with empty byUser.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_6() throws Exception {
        manager.setMemberPhotoDAO(dao);
        try {
            manager.saveMemberPhoto(1, image, "  ");
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    
    /**
     * Test saveMemberPhoto method when the dao is not set.
     * It should throw IllegalStateException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_NoManager2() throws Exception {
        try {
            manager.saveMemberPhoto(1, "filename", "test");
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
        manager.setMemberPhotoDAO(dao);
        try {
            manager.saveMemberPhoto(1, (String) null, "test");
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    

    /**
     * Test saveMemberPhoto method with null byUser.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_3() throws Exception {
        manager.setMemberPhotoDAO(dao);
        try {
            manager.saveMemberPhoto(1, "filename", null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    /**
     * Test saveMemberPhoto method with empty byUser.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testSaveMemberPhoto_4() throws Exception {
        manager.setMemberPhotoDAO(dao);
        try {
            manager.saveMemberPhoto(1, "filename", "  ");
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    
    /**
     * Test removeMemberPhoto method when the dao is not set.
     * It should throw IllegalStateException.
     * @throws Exception to JUnit.
     */
    public void testRemoveMemberPhoto_NoDao() throws Exception {
        try {
            manager.removeMemberPhoto(1, "test");
            fail("It should throw IllegalStateException.");
        } catch (IllegalStateException e) {
            // expected.
        }        
    }
    /**
     * Test removeMemberPhoto method with null byUser.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testRemoveMemberPhoto_2() throws Exception {
        manager.setMemberPhotoDAO(dao);
        try {
            manager.removeMemberPhoto(1, null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    /**
     * Test removeMemberPhoto method with empty byUser.
     * It should throw IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    public void testRemoveMemberPhoto_3() throws Exception {
        manager.setMemberPhotoDAO(dao);
        try {
            manager.removeMemberPhoto(1, " ");
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
    public void testgetMemberPhotos1() throws Exception {
        manager.setMemberPhotoDAO(dao);
        try {
        	manager.getMemberPhotos(-1,  1);
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
        manager.setMemberPhotoDAO(dao);
        try {
        	manager.getMemberPhotos(1,  0);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }        
    }
    
    /**
     * Test getMemberPhotos method when the dao is not set.
     * It should throw IllegalStateException.
     * @throws Exception to JUnit.
     */
    public void testgetMemberPhotos3() throws Exception {
        try {
        	manager.getMemberPhotos(1,  1);
            fail("It should throw IllegalStateException.");
        } catch (IllegalStateException e) {
            // expected.
        }        
    }
}
