/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.DomainTarget;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test for class <code>DomainTargetImpl</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DomainTargetImplUnitTests extends TestCase {
    /** represents the Id constants for testing. */
    public static final Long ID = new Long(1);
    /** represents the sequenceNumber constants for testing. */
    public static final int SEQUENCE_NUMBER = 1000;
    /** represents the clueImageId constants for testing. */
    public static final long CULE_IMAGE_ID = 2l;

    /** represents the uriPath constants for testing. */
    public static final String URI_PATH = "/tc";

    /** represents the identifierText constants for testing. */
    public static final String IDENTIFIER_TEXT = "topcoder";

    /** represents the identifierHash constants for testing. */
    public static final String IDENTIFIER_HASH = "topxxcxxoder";

  

    /** DomainTargetImpl instance to test against. */
    private DomainTarget domainTarget = null;

    /**
     * create instance.
     */
    protected void setUp() {
        this.domainTarget = new DomainTargetImpl(ID, SEQUENCE_NUMBER, URI_PATH, IDENTIFIER_TEXT, IDENTIFIER_HASH,
                CULE_IMAGE_ID);
    }

    /**
     * test the ctor, simply verify the instance.
     */
    public void testCtor() {
        assertNotNull("The DomainTargetImpl can not be instantiate.", domainTarget);
    }

    /**
     * test the ctor, the id can be null, it is an accuracy test case.
     */
    public void testCtor_nullId() {
        try {
            new DomainTargetImpl(null, SEQUENCE_NUMBER, URI_PATH, IDENTIFIER_TEXT, IDENTIFIER_HASH, CULE_IMAGE_ID);
        } catch (Exception e) {
            fail("The id can be null.");
        }
    }

    /**
     * test the ctor, the id is not positive, iae expected.
     */
    public void testCtor_notPositiveId() {
        try {
            new DomainTargetImpl(new Long(0), SEQUENCE_NUMBER, URI_PATH, IDENTIFIER_TEXT, IDENTIFIER_HASH, CULE_IMAGE_ID);
            fail("The id should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the SequenceNumber is negative, iae expected.
     */
    public void testCtor_negativeSequenceNumber() {
        try {
            new DomainTargetImpl(null, -1, URI_PATH, IDENTIFIER_TEXT, IDENTIFIER_HASH, CULE_IMAGE_ID);
            fail("The SequenceNumber is negative.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the culeImageId is not positive, iae expected.
     */
    public void testCtor_notPositiveCuleImageId() {
        try {
            new DomainTargetImpl(ID, SEQUENCE_NUMBER, URI_PATH, IDENTIFIER_TEXT, IDENTIFIER_HASH, 0);
            fail("The culeImageId should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the uriPath is null, iae expected.
     */
    public void testCtor_nullUriPath() {
        try {
            new DomainTargetImpl(ID, SEQUENCE_NUMBER, null, IDENTIFIER_TEXT, IDENTIFIER_HASH, CULE_IMAGE_ID);
            fail("The uriPath is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the uriPath is empty, iae expected.
     */
    public void testCtor_emptyUriPath() {
        try {
            new DomainTargetImpl(ID, SEQUENCE_NUMBER, " ", IDENTIFIER_TEXT, IDENTIFIER_HASH, CULE_IMAGE_ID);
            fail("The uriPath is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the identifierHash is null, iae expected.
     */
    public void testCtor_nullIdentifierHash() {
        try {
            new DomainTargetImpl(ID, SEQUENCE_NUMBER, URI_PATH, IDENTIFIER_TEXT, null, CULE_IMAGE_ID);
            fail("The identifierHash is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the identifierHash is empty, iae expected.
     */
    public void testCtor_emptyIdentifierHash() {
        try {
            new DomainTargetImpl(ID, SEQUENCE_NUMBER, URI_PATH, IDENTIFIER_TEXT, " ", CULE_IMAGE_ID);
            fail("The identifierHash is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * simply verify the getId method.
     */
    public void testGetId() {
        assertEquals("The id to set is the not one get.", ID, this.domainTarget.getId());
    }

    /**
     * simply verify the getClueImageId method.
     */
    public void testGetClueImageId() {
        assertEquals("The CULE_IMAGE_ID to set is the not one get.", CULE_IMAGE_ID, domainTarget.getClueImageId());
    }

    /**
     * simply verify the getSequenceNumber method.
     */
    public void testGetSequenceNumbe() {
        assertEquals("The sequenceNumber to set is the not one get.", SEQUENCE_NUMBER,
            this.domainTarget.getSequenceNumber());
    }

    /**
     * simply verify the getUriPath method.
     */
    public void testGetUriPath() {
        assertEquals("The UriPath to set is the not one get.", URI_PATH, this.domainTarget.getUriPath());
    }

    /**
     * simply verify the getIdentifierText method.
     */
    public void testGetIdentifierText() {
        assertEquals("The identifierText to set is the not one get.", IDENTIFIER_TEXT,
            this.domainTarget.getIdentifierText());
    }

    /**
     * simply verify the getIdentifierHash method.
     */
    public void testGetIdentifierHash() {
        assertEquals("The identifierHash to set is the not one get.", IDENTIFIER_HASH,
            this.domainTarget.getIdentifierHash());
    }
}
