/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.accuracytests;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for the <code>MemberImage</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class MemberImageAccuracyTest extends TestCase {

    /**
     * Represents the <code>MemberImage</code> instance to test.
     */
    private MemberImage memberImage;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        memberImage = new MemberImage();
    }

    /**
     * <p>
     * Accuracy test for <code>MemberImage</code> constructor.
     * </p>
     */
    public void testCtorAccuracy() {
        assertNotNull("MemberImage creation failed", memberImage);
    }

    /**
     * <p>
     * Accuracy test for <code>getMemberId</code> method.
     * </p>
     */
    public void testGetMemberIdAccuracy() {
        assertEquals("The memberId is not correct.", 0, memberImage.getMemberId());
    }

    /**
     * <p>
     * Accuracy test for <code>setMemberId</code> method.
     * </p>
     */
    public void testSetMemberIdAccuracy() {
        memberImage.setMemberId(1);
        assertEquals("The memberImage is not correct.", 1, memberImage.getMemberId());
    }

    /**
     * <p>
     * Accuracy test for <code>getImage</code> method.
     * </p>
     */
    public void testGetImageAccuracy() {
        assertNull("The image is not correct.", memberImage.getImage());
    }

    /**
     * <p>
     * Accuracy test for <code>setImage</code> method.
     * </p>
     */
    public void testSetImageAccuracy() {
        Image image = new Image();
        memberImage.setImage(image);
        assertEquals("The image is not correct.", image, memberImage.getImage());
    }
}
