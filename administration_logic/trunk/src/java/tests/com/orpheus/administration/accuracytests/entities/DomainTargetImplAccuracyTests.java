/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.entities;

import com.orpheus.administration.entities.DomainTargetImpl;

import junit.framework.TestCase;

/**
 * <p>
 * Test the <code>DomainTargetImpl</code> class.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class DomainTargetImplAccuracyTests extends TestCase {
    /**
     * will hold the identifier for this target.
     */
    private static final Long ID = new Long(34523);

    /**
     * will hold the sequence number of this target among those.
     */
    private static final int SEQUENCE_NUMBER = 121234;

    /**
     * will hold the path and file parts of the URI at which the target.
     */
    private static final String URI_PATH = "uri path";

    /**
     * will hold the plain text identifier of the target.
     */
    private static final String IDENTIFIER_TEXT = "id text";

    /**
     * will hold a hash of the target's identifier.
     */
    private static final String IDENTIFIER_HASH = "id hash";

    /**
     * will hold the unique identifier of a downloadable object constituting an image
     * to be presented to users as the clue for this target.
     */
    private static final long CLUE_IMAGE_ID = 12342;

    /**
     * DomainTargetImpl instance to test.
     */
    private DomainTargetImpl target = null;

    /**
     * <p>
     * setUp() routine. Creates test DomainTargetImpl instance.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        target = new DomainTargetImpl();
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>DomainTargetImpl()</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to get DomainTargetImpl instance.", target);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getClueImageId()</code> for
     * proper behavior. Verify that clue image id got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetClueImageIdAccuracy() throws Exception {
        target.setClueImageId(CLUE_IMAGE_ID);
        assertEquals("clue image id got incorrectly.", CLUE_IMAGE_ID, target
                .getClueImageId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setClueImageId(long)</code> for
     * proper behavior. Verify that clue image id set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetClueImageIdAccuracy() throws Exception {
        target.setClueImageId(CLUE_IMAGE_ID);
        assertEquals("clue image id set incorrectly.", CLUE_IMAGE_ID, target
                .getClueImageId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getId()</code> for
     * proper behavior. Verify that id got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetIdAccuracy() throws Exception {
        target.setId(ID);
        assertEquals("id got incorrectly.", ID, target.getId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setId(long)</code> for
     * proper behavior. Verify that id set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetIdAccuracy() throws Exception {
        target.setId(ID);
        assertEquals("id set incorrectly.", ID, target.getId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getIdentifierHash()</code> for
     * proper behavior. Verify that IdentifierHash got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetIdentifierHashAccuracy() throws Exception {
        target.setIdentifierHash(IDENTIFIER_HASH);
        assertEquals("IdentifierHash got incorrectly.", IDENTIFIER_HASH, target
                .getIdentifierHash());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setIdentifierHash(String)</code> for
     * proper behavior. Verify that IdentifierHash set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetIdentifierHashAccuracy() throws Exception {
        target.setIdentifierHash(IDENTIFIER_HASH);
        assertEquals("IdentifierHash set incorrectly.", IDENTIFIER_HASH, target
                .getIdentifierHash());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getIdentifierText()</code> for
     * proper behavior. Verify that IdentifierText set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetIdentifierTextAccuracy() throws Exception {
        target.setIdentifierText(IDENTIFIER_TEXT);
        assertEquals("IdentifierText got incorrectly.", IDENTIFIER_TEXT, target
                .getIdentifierText());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setIdentifierText(String)</code> for
     * proper behavior. Verify that IdentifierText set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetIdentifierTextAccuracy() throws Exception {
        target.setIdentifierText(IDENTIFIER_TEXT);
        assertEquals("IdentifierText set incorrectly.", IDENTIFIER_TEXT, target
                .getIdentifierText());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getSequenceNumber()</code> for
     * proper behavior. Verify that SequenceNumber set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSequenceNumberAccuracy() throws Exception {
        target.setSequenceNumber(SEQUENCE_NUMBER);
        assertEquals("SequenceNumber got incorrectly.", SEQUENCE_NUMBER, target
                .getSequenceNumber());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setSequenceNumber(int)</code> for
     * proper behavior. Verify that SequenceNumber set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetSequenceNumberAccuracy() throws Exception {
        target.setSequenceNumber(SEQUENCE_NUMBER);
        assertEquals("SequenceNumber set incorrectly.", SEQUENCE_NUMBER, target
                .getSequenceNumber());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getUriPath()</code> for
     * proper behavior. Verify that UriPath set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUriPathAccuracy() throws Exception {
        target.setUriPath(URI_PATH);
        assertEquals("UriPath got incorrectly.", URI_PATH, target.getUriPath());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setUriPath(String)</code> for
     * proper behavior. Verify that UriPath set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetUriPathAccuracy() throws Exception {
        target.setUriPath(URI_PATH);
        assertEquals("UriPath set incorrectly.", URI_PATH, target.getUriPath());
    }
}
