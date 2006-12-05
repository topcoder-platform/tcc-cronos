/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import java.util.Arrays;
import java.util.Date;

import junit.framework.TestCase;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.ImageInfo;

/**
 * <p>
 * Test the <code>HostingSlotImpl</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HostingSlotImplUnitTests extends TestCase {
    /**
     * will hold the unique identifier of this slot.
     *
     */
    private final Long id = new Long(1);

    /**
     * will hold a Domain object represented the domain assigned to this hosting slot.
     *
     */
    private final Domain domain = new Domain() {

        public String getDomainName() {

            return null;
        }

        public Long getId() {

            return null;
        }

        public ImageInfo[] getImages() {

            return null;
        }

        public long getSponsorId() {

            return 0;
        }

        public Boolean isApproved() {

            return null;
        }
    };

    /**
     * will hold the ID of the image information associated with this hosting slot.
     *
     */
    private final long imageId = 2;

    /**
     * will hold the unique IDs of the brain teasers in this slot?s brain teaser series.
     *
     */
    private final long[] brainTeaserIds = new long[] {3, 4};

    /**
     * will hold the ID of the puzzle assigned to this slot.
     *
     */
    private final Long puzzleId = new Long(5);

    /**
     * will hold the sequence number of this slot within its block.
     *
     */
    private final int sequenceNumber = 6;

    /**
     * will hold an array of DomainTarget objects representing the "minihunt targets" for this hosting slot.
     *
     */
    private final DomainTarget[] domainTargets = new DomainTarget[] {
        new DomainTargetImpl(), new DomainTargetImpl()};

    /**
     * will hold the amount of the winning bid in the auction for this slot.
     *
     */
    private final int winningBid = 7;

    /**
     * will hold a Date representing the date and time at which this hosting slot began hosting.
     *
     */
    private final Date hostingStart = new Date(
            System.currentTimeMillis() - 100000);

    /**
     * will hold Date representing the date and time at which this hosting slot stopped hosting.
     *
     */
    private final Date hostingEnd = new Date(
            System.currentTimeMillis() - 200000);

    /**
     * HostingSlotImpl instance to test.
     */
    private HostingSlotImpl target = null;

    /**
     * <p>
     * setUp() routine. Creates test HostingSlotImpl instance.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        target = new HostingSlotImpl();
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>HostingSlotImpl()</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to get HostingSlotImpl instance.", target);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getDomain()</code> for
     * proper behavior. Verify that Domain got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetDomain_1_accuracy() throws Exception {
        target.setDomain(domain);
        assertEquals("Domain got incorrectly.", domain, target.getDomain());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setDomain(Domain)</code> for
     * proper behavior. Verify that Domain set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetDomain_1_accuracy() throws Exception {
        target.setDomain(domain);
        assertEquals("Domain set incorrectly.", domain, target.getDomain());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getBrainTeaserIds()</code> for
     * proper behavior. Verify that BrainTeaserIds got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetBrainTeaserIds_1_accuracy() throws Exception {
        target.setBrainTeaserIds(brainTeaserIds);
        assertTrue("BrainTeaserIds got incorrectly.", Arrays.equals(
                brainTeaserIds, target.getBrainTeaserIds()));
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setBrainTeaserIds(long[])</code> for
     * proper behavior. Verify that BrainTeaserIds set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetBrainTeaserIds_1_accuracy() throws Exception {
        target.setBrainTeaserIds(brainTeaserIds);
        assertTrue("BrainTeaserIds set incorrectly.", Arrays.equals(
                brainTeaserIds, target.getBrainTeaserIds()));
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getDomainTargets()</code> for
     * proper behavior. Verify that DomainTargets got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetDomainTargets_1_accuracy() throws Exception {
        target.setDomainTargets(domainTargets);
        assertTrue("DomainTargets got incorrectly.", Arrays.equals(
                domainTargets, target.getDomainTargets()));
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setDomainTargets(DomainTarget[])</code> for
     * proper behavior. Verify that DomainTargets set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetDomainTargets_1_accuracy() throws Exception {
        target.setDomainTargets(domainTargets);
        assertTrue("DomainTargets set incorrectly.", Arrays.equals(
                domainTargets, target.getDomainTargets()));
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getHostingEnd()</code> for
     * proper behavior. Verify that HostingEnd got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetHostingEnd_1_accuracy() throws Exception {
        target.setHostingEnd(hostingEnd);
        assertEquals("HostingEnd got incorrectly.", hostingEnd, target
                .getHostingEnd());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setHostingEnd(Date)</code> for
     * proper behavior. Verify that HostingEnd set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetHostingEnd_1_accuracy() throws Exception {
        target.setHostingEnd(hostingEnd);
        assertEquals("HostingEnd set incorrectly.", hostingEnd, target
                .getHostingEnd());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getHostingStart()</code> for
     * proper behavior. Verify that HostingStart got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetHostingStart_1_accuracy() throws Exception {
        target.setHostingStart(hostingStart);
        assertEquals("HostingStart got incorrectly.", hostingStart, target
                .getHostingStart());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setHostingStart(Date)</code> for
     * proper behavior. Verify that HostingStart set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetHostingStart_1_accuracy() throws Exception {
        target.setHostingStart(hostingStart);
        assertEquals("HostingStart set incorrectly.", hostingStart, target
                .getHostingStart());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getId()</code> for
     * proper behavior. Verify that Id got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetId_1_accuracy() throws Exception {
        target.setId(id);
        assertEquals("Id got incorrectly.", id, target.getId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setId(Long)</code> for
     * proper behavior. Verify that Id set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetId_1_accuracy() throws Exception {
        target.setId(id);
        assertEquals("Id set incorrectly.", id, target.getId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getImageId()</code> for
     * proper behavior. Verify that ImageId got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetImageId_1_accuracy() throws Exception {
        target.setImageId(imageId);
        assertEquals("ImageId got incorrectly.", imageId, target.getImageId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setImageId(long)</code> for
     * proper behavior. Verify that ImageId set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetImageId_1_accuracy() throws Exception {
        target.setImageId(imageId);
        assertEquals("ImageId set incorrectly.", imageId, target.getImageId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getPuzzleId()</code> for
     * proper behavior. Verify that PuzzleId got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetPuzzleId_1_accuracy() throws Exception {
        target.setPuzzleId(puzzleId);
        assertEquals("PuzzleId got incorrectly.", puzzleId, target
                .getPuzzleId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setPuzzleId(Long)</code> for
     * proper behavior. Verify that PuzzleId set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetPuzzleId_1_accuracy() throws Exception {
        target.setPuzzleId(puzzleId);
        assertEquals("PuzzleId set incorrectly.", puzzleId, target
                .getPuzzleId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getSequenceNumber()</code> for
     * proper behavior. Verify that SequenceNumber got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSequenceNumber_1_accuracy() throws Exception {
        target.setSequenceNumber(sequenceNumber);
        assertEquals("SequenceNumber got incorrectly.", sequenceNumber, target
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
    public void testSetSequenceNumber_1_accuracy() throws Exception {
        target.setSequenceNumber(sequenceNumber);
        assertEquals("SequenceNumber set incorrectly.", sequenceNumber, target
                .getSequenceNumber());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getWinningBid()</code> for
     * proper behavior. Verify that WinningBid got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetWinningBid_1_accuracy() throws Exception {
        target.setWinningBid(winningBid);
        assertEquals("WinningBid got incorrectly.", winningBid, target
                .getWinningBid());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setWinningBid(int)</code> for
     * proper behavior. Verify that WinningBid set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetWinningBid_1_accuracy() throws Exception {
        target.setWinningBid(winningBid);
        assertEquals("WinningBid set incorrectly.", winningBid, target
                .getWinningBid());
    }
}
