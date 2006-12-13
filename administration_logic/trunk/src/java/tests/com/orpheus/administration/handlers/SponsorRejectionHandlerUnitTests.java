/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import org.w3c.dom.Element;

import com.orpheus.administration.TestHelper;

import junit.framework.TestCase;

/**
 * <p>
 * Test the <code>SponsorRejectionHandler</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SponsorRejectionHandlerUnitTests extends TestCase {
    /**
     * <p>
     * An instance of <code>SponsorRejectionHandler</code> which is tested.
     * </p>
     */
    private SponsorRejectionHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test SponsorRejectionHandler instance and other
     * instances used in tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.prepareTest();
        //      the xml string used for test
        String xml = SponsorApprovalRejectionHandlerUnitTests.XMLSTRING;
        Element element = TestHelper.loadXmlString(xml);
        target = new SponsorRejectionHandler(element);
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
     * Accuracy test. Tests the
     * <code>SponsorRejectionHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to initialize SponsorRejectionHandler.", target);
    }

    /**
     * <p>
     * Failure test. Tests the <code>SponsorRejectionHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if any
     * argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new SponsorRejectionHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the
     * <code>getIsApprovedPropertyValue()</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetIsApprovedPropertyValue_1_accuracy() throws Exception {
        assertEquals("'N' should be returned.", "N", target
                .getIsApprovedPropertyValue());
    }
}
