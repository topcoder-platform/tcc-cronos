/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import com.orpheus.administration.TestHelper;
import com.orpheus.game.persistence.Domain;
import com.topcoder.user.profile.UserProfile;

import junit.framework.TestCase;

/**
 * <p>
 * Test the <code>SponsorDomain</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SponsorDomainUnitTests extends TestCase {
    /**
     * holds the UserProfile instance representing the sponsor.
     *
     */
    private UserProfile sponsor;

    /**
     * holds the domains associated with the sponsor.
     *
     */
    private final Domain[] domains = new Domain[] {new MockDomain(),
        new MockDomain()};

    /**
     * SponsorDomain instance to test.
     */
    private SponsorDomain target = null;

    /**
     * <p>
     * setUp() routine. Creates test SponsorDomain instance.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.prepareTest();
        sponsor = new UserProfile();
        target = new SponsorDomain(sponsor, domains);
    }

    /**
     * <p>
     * Clean up all namespace of ConfigManager.
     * </p>
     *
     * @throws Exception
     *             the exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearTestEnvironment();
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>SponsorDomain(UserProfile, Domain[])</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to get SponsorDomain instance.", target);
        assertEquals("sponsor set incorrectly.", sponsor, target.getSponsor());
        assertEquals("domains set incorrectly.", domains, target.getDomains());
    }

    /**
     * <p>
     * Failure test. Tests the <code>SponsorDomain(UserProfile, Domain[])</code> for
     * proper behavior. Verify that IllegalArgumentException should be thrown if sponsor is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_failure() throws Exception {
        try {
            new SponsorDomain(null, domains);
            fail("IllegalArgumentException should be thrown if sponsor is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getDomains()</code> for
     * proper behavior. Verify that Domains got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetDomains_1_accuracy() throws Exception {
        assertEquals("Domains got incorrectly.", domains, target.getDomains());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getSponsor()</code> for
     * proper behavior. Verify that Sponsor got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSponsor_1_accuracy() throws Exception {
        assertEquals("Sponsor got incorrectly.", sponsor, target.getSponsor());
    }
}
