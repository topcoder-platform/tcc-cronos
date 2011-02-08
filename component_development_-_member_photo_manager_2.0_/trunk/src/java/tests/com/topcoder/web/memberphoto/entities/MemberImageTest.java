/*
 * Copyright (C) 2008, 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.entities;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.web.memberphoto.manager.TestHelper;

/**
 * <p>
 * Tests the functionality of {@link MemberImage} class.
 * </p>
 *
 * @author cyberjag, sparemax
 * @version 2.0
 */
public class MemberImageTest extends TestCase {

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
    @Override
    protected void setUp() throws Exception {
        memberImage = new MemberImage();
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
        memberImage = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(MemberImageTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link MemberImage#MemberImage()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberImage() {
        // check for null
        assertNotNull("MemberImage creation failed", memberImage);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     *
     * @since 2.0
     */
    public void test_getId() {
        long value = 1;
        memberImage.setId(value);

        assertEquals("'getId' should be correct.", value, memberImage.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setId(long id)</code>.<br>
     * The value should be properly set.
     * </p>
     *
     * @since 2.0
     */
    public void test_setId() {
        long value = 1;
        memberImage.setId(value);

        assertEquals("'setId' should be correct.", value, TestHelper.getField(memberImage, "id"));
    }

    /**
     * <p>
     * Accuracy test for {@link MemberImage#getMemberId()} and {@link MemberImage#setMemberId(long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is 0.
     * </p>
     *
     */
    public void test_accuracy_getMemberId() {
        // set the value to test
        memberImage.setMemberId(0);
        assertEquals("getMemberId and setMemberId failure occurred", 0, memberImage.getMemberId());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberImage#setMemberId(long)} and {@link MemberImage#getMemberId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is 1.
     * </p>
     *
     */
    public void test_accuracy_setMemberId() {
        // set the value to test
        memberImage.setMemberId(1);
        assertEquals("getMemberId and setMemberId failure occurred", 1, memberImage.getMemberId());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberImage#getImage()} and {@link MemberImage#setImage(Image)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     *
     */
    public void test_accuracy_getImage() {
        // set the value to test
        memberImage.setImage(null);
        assertEquals("getImage and setImage failure occurred", null, memberImage.getImage());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberImage#setImage(Image)} and {@link MemberImage#getImage()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setImage() {
        // set the value to test
        Image image = new Image();
        image.setId(1L);
        memberImage.setImage(image);
        assertEquals("getImage and setImage failure occurred", image.getId(), memberImage.getImage().getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>isRemoved()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     *
     * @since 2.0
     */
    public void test_isRemoved() {
        boolean value = true;
        memberImage.setRemoved(value);

        assertEquals("'isRemoved' should be correct.", value, memberImage.isRemoved());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setRemoved(boolean removed)</code>.<br>
     * The value should be properly set.
     * </p>
     *
     * @since 2.0
     */
    public void test_setRemoved() {
        boolean value = true;
        memberImage.setRemoved(value);

        assertEquals("'setRemoved' should be correct.", value, TestHelper.getField(memberImage, "removed"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCreatedBy()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     *
     * @since 2.0
     */
    public void test_getCreatedBy() {
        String value = "new_value";
        memberImage.setCreatedBy(value);

        assertEquals("'getCreatedBy' should be correct.", value, memberImage.getCreatedBy());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCreatedBy(String createdBy)</code>.<br>
     * The value should be properly set.
     * </p>
     *
     * @since 2.0
     */
    public void test_setCreatedBy() {
        String value = "new_value";
        memberImage.setCreatedBy(value);

        assertEquals("'setCreatedBy' should be correct.", value, TestHelper.getField(memberImage, "createdBy"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCreatedDate()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     *
     * @since 2.0
     */
    public void test_getCreatedDate() {
        Date value = new Date();
        memberImage.setCreatedDate(value);

        assertSame("'getCreatedDate' should be correct.", value, memberImage.getCreatedDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCreatedDate(Date createdDate)</code>.<br>
     * The value should be properly set.
     * </p>
     *
     * @since 2.0
     */
    public void test_setCreatedDate() {
        Date value = new Date();
        memberImage.setCreatedDate(value);

        assertSame("'setCreatedDate' should be correct.", value, TestHelper.getField(memberImage, "createdDate"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUpdatedBy()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     *
     * @since 2.0
     */
    public void test_getUpdatedBy() {
        String value = "new_value";
        memberImage.setUpdatedBy(value);

        assertEquals("'getUpdatedBy' should be correct.", value, memberImage.getUpdatedBy());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUpdatedBy(String updatedBy)</code>.<br>
     * The value should be properly set.
     * </p>
     *
     * @since 2.0
     */
    public void test_setUpdatedBy() {
        String value = "new_value";
        memberImage.setUpdatedBy(value);

        assertEquals("'setUpdatedBy' should be correct.", value, TestHelper.getField(memberImage, "updatedBy"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUpdatedDate()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     *
     * @since 2.0
     */
    public void test_getUpdatedDate() {
        Date value = new Date();
        memberImage.setUpdatedDate(value);

        assertSame("'getUpdatedDate' should be correct.", value, memberImage.getUpdatedDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUpdatedDate(Date updatedDate)</code>.<br>
     * The value should be properly set.
     * </p>
     *
     * @since 2.0
     */
    public void test_setUpdatedDate() {
        Date value = new Date();
        memberImage.setUpdatedDate(value);

        assertSame("'setUpdatedDate' should be correct.", value, TestHelper.getField(memberImage, "updatedDate"));
    }
}
