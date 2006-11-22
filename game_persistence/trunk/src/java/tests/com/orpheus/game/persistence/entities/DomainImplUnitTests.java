/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.TestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test case for class <code>DomainImpl</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DomainImplUnitTests extends TestCase {
    /** represents the Id constants for testing. */
    public static final Long ID = new Long(1);

    /** represents the domainName constants for testing. */
    public static final String DOMAIN_NAME = "www.topcoder.com";

    /** represents the sponsorId constants for testing. */
    public static final long SPONSOR_ID = 1000L;

    /** respresents the approved constants for testing. */
    public static final Boolean APPROVED = new Boolean(true);

    /** images. */
    private ImageInfo[] images = null;

    /** DomainImpl instance to test against. */
    private Domain domain = null;

    /**
     * create instance.
     */
    protected void setUp() {
        images = new ImageInfo[1];
        images[0] = new ImageInfoImpl(ImageInfoImplUnitTests.ID, ImageInfoImplUnitTests.DOWNLOAD_ID,
                ImageInfoImplUnitTests.DESCRIPTION, ImageInfoImplUnitTests.APPROVED);

        this.domain = new DomainImpl(ID, SPONSOR_ID, DOMAIN_NAME, APPROVED, images);
    }

    /**
     * test the ctor, simply verify the instance.
     */
    public void testCtor() {
        assertNotNull("The DomainImpl can not be instantiate.", domain);
    }

    /**
     * test the ctor, the id can be null, it is an accuracy test case.
     */
    public void testCtor_nullId() {
        try {
            new DomainImpl(null, SPONSOR_ID, DOMAIN_NAME, APPROVED, images);
        } catch (Exception e) {
            fail("The id can be null.");
        }
    }

    /**
     * test the ctor, the id is not positive, iae expected.
     */
    public void testCtor_notPositiveId() {
        try {
            new DomainImpl(new Long(0), SPONSOR_ID, DOMAIN_NAME, APPROVED, images);
            fail("The id should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the sponsorId is not positive, iae expected.
     */
    public void testCtor_notPositiveSponsorId() {
        try {
            new DomainImpl(null, 0, DOMAIN_NAME, APPROVED, images);
            fail("The sponsorId should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the domainName is null, iae expected.
     */
    public void testCtor_nullDomainName() {
        try {
            new DomainImpl(null, SPONSOR_ID, null, APPROVED, images);
            fail("The domainName is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the domainName is empty, iae expected.
     */
    public void testCtor_emptyDomainName() {
        try {
            new DomainImpl(null, SPONSOR_ID, " ", APPROVED, images);
            fail("The domainName is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the imageInfo array is null, iae expected.
     */
    public void testCtor_nullImageArray() {
        try {
            new DomainImpl(null, SPONSOR_ID, DOMAIN_NAME, APPROVED, null);
            fail("The  imageInfo array is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the imageInfo array contains null element, iae expected.
     */
    public void testCtor_nullElementImageArray() {
        try {
            images = new ImageInfo[1];
            images[0] = null;
            new DomainImpl(null, SPONSOR_ID, DOMAIN_NAME, APPROVED, images);
            fail("The  imageInfo array contains null element.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * accuracy test the shallow copy of the imageInfo array.
     */
    public void testCtor_shallowCopy() {
        ImageInfo[] inSideImages = (ImageInfo[]) TestHelper.getPrivateField(DomainImpl.class, this.domain, "images");
        assertFalse("The inside and the outside images are not the same.", this.images == inSideImages);
        assertTrue("The size should be same.", images.length == inSideImages.length);
        assertEquals("Shallow copy.", images[0], inSideImages[0]);
    }

    /**
     * simply verify the getImages method. verify the shallow copy.
     */
    public void testGetImages() {
        ImageInfo[] inSideImages = (ImageInfo[]) TestHelper.getPrivateField(DomainImpl.class, this.domain, "images");
        ImageInfo[] outSideImages = this.domain.getImages();
        assertFalse("The inside and the outside images are not the same.", outSideImages == inSideImages);
        assertTrue("The size should be same.", outSideImages.length == inSideImages.length);
        assertEquals("Shallow copy.", outSideImages[0], inSideImages[0]);
    }

    /**
     * simply verify the getId method.
     */
    public void testGetId() {
        assertEquals("The id to set is the not one get.", ID, this.domain.getId());
    }

    /**
     * simply verify the isApproved method.
     */
    public void testIsApproved() {
        assertEquals("The approve to set is the not one get.", APPROVED, this.domain.isApproved());
    }

    /**
     * simply verify the getDomainName method.
     */
    public void testGetName() {
        assertEquals("The domainName to set is the not one get.", DOMAIN_NAME, this.domain.getDomainName());
    }

    /**
     * simply verify the getSponsorId method.
     */
    public void testGetImageId() {
        assertEquals("The sponsorId to set is the not one get.", SPONSOR_ID, this.domain.getSponsorId());
    }
}
