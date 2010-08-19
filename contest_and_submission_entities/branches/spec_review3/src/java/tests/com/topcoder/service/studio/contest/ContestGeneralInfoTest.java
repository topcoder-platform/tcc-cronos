/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ContestGeneralInfo} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since 1.2
 */
public class ContestGeneralInfoTest extends TestCase {

    /**
     * Represents the <code>ContestGeneralInfo</code> instance to test.
     */
    private ContestGeneralInfo contestGeneralInfo = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestGeneralInfo = new ContestGeneralInfo();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestGeneralInfo = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestGeneralInfoTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#ContestGeneralInfo()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_PrizeType() {
        // check for null
        assertNotNull("ContestGeneralInfo creation failed", contestGeneralInfo);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#getContestGeneralInfoId()} and
     * {@link ContestGeneralInfo#setContestGeneralInfoId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getContestGeneralInfoId() {
        // set the value to test
        contestGeneralInfo.setContestGeneralInfoId(new Long(1));
        assertEquals("getContestGeneralInfoId and setContestGeneralInfoId failure occured", new Long(1),
                contestGeneralInfo.getContestGeneralInfoId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#setContestGeneralInfoId(Long)} and
     * {@link ContestGeneralInfo#getContestGeneralInfoId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContestGeneralInfoId() {
        // set the value to test
        contestGeneralInfo.setContestGeneralInfoId(1L);
        assertEquals("getContestGeneralInfoId and setContestGeneralInfoId failure occured", 1L,
                (long) contestGeneralInfo.getContestGeneralInfoId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#getGoals()} and {@link ContestGeneralInfo#setGoals(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getGoals() {
        // set the value to test
        contestGeneralInfo.setGoals(null);
        assertEquals("getGoals and setGoals failure occured", null, contestGeneralInfo.getGoals());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#setGoals(String)} and {@link ContestGeneralInfo#getGoals()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setGoals() {
        // set the value to test
        contestGeneralInfo.setGoals("test");
        assertEquals("getGoals and setGoals failure occured", "test", contestGeneralInfo.getGoals());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#getTargetAudience()} and
     * {@link ContestGeneralInfo#setTargetAudience(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getTargetAudience() {
        // set the value to test
        contestGeneralInfo.setTargetAudience(null);
        assertEquals("getTargetAudience and setTargetAudience failure occured", null, contestGeneralInfo
                .getTargetAudience());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#setTargetAudience(String)} and
     * {@link ContestGeneralInfo#getTargetAudience()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setTargetAudience() {
        // set the value to test
        contestGeneralInfo.setTargetAudience("test");
        assertEquals("getTargetAudience and setTargetAudience failure occured", "test", contestGeneralInfo
                .getTargetAudience());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#setBrandingGuidelines(String)} and
     * {@link ContestGeneralInfo#getBrandingGuidelines()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setBrandingGuidelines() {
        // set the value to test
        contestGeneralInfo.setBrandingGuidelines("test");
        assertEquals("getBrandingGuidelines and setBrandingGuidelines failure occured", "test", contestGeneralInfo
                .getBrandingGuidelines());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#getBrandingGuidelines()} and
     * {@link ContestGeneralInfo#setBrandingGuidelines(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getBrandingGuidelines() {
        // set the value to test
        contestGeneralInfo.setBrandingGuidelines(null);
        assertEquals("getBrandingGuidelines and setBrandingGuidelines failure occured", null, contestGeneralInfo
                .getBrandingGuidelines());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#getDislikedDesignsWebsites()} and
     * {@link ContestGeneralInfo#setDislikedDesignsWebsites(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDislikedDesignsWebsites() {
        // set the value to test
        contestGeneralInfo.setDislikedDesignsWebsites(null);
        assertEquals("getDislikedDesignsWebsites and setDislikedDesignsWebsites failure occured", null,
                contestGeneralInfo.getDislikedDesignsWebsites());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#setDislikedDesignsWebsites(String)} and
     * {@link ContestGeneralInfo#getDislikedDesignsWebsites()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDislikedDesignsWebsites() {
        // set the value to test
        contestGeneralInfo.setDislikedDesignsWebsites("test");
        assertEquals("getDislikedDesignsWebsites and setDislikedDesignsWebsites failure occured", "test",
                contestGeneralInfo.getDislikedDesignsWebsites());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#getOtherInstructions()} and
     * {@link ContestGeneralInfo#setOtherInstructions(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getOtherInstructions() {
        // set the value to test
        contestGeneralInfo.setOtherInstructions(null);
        assertEquals("getOtherInstructions and setOtherInstructions failure occured", null, contestGeneralInfo
                .getOtherInstructions());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#setOtherInstructions(String)} and
     * {@link ContestGeneralInfo#getOtherInstructions()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setOtherInstructions() {
        // set the value to test
        contestGeneralInfo.setOtherInstructions("test");
        assertEquals("getOtherInstructions and setOtherInstructions failure occured", "test", contestGeneralInfo
                .getOtherInstructions());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#getWinningCriteria()} and
     * {@link ContestGeneralInfo#setWinningCriteria(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getWinningCriteria() {
        // set the value to test
        contestGeneralInfo.setWinningCriteria(null);
        assertEquals("getWinningCriteria and setWinningCriteria failure occured", null, contestGeneralInfo
                .getWinningCriteria());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#setWinningCriteria(String)} and
     * {@link ContestGeneralInfo#getWinningCriteria()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setWinningCriteria() {
        // set the value to test
        contestGeneralInfo.setWinningCriteria("test");
        assertEquals("getWinningCriteria and setWinningCriteria failure occured", "test", contestGeneralInfo
                .getWinningCriteria());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestGeneralInfo resource = new ContestGeneralInfo();
        resource.setContestGeneralInfoId(1L);
        contestGeneralInfo.setContestGeneralInfoId(1L);
        assertTrue("equals", resource.equals(contestGeneralInfo));
        assertTrue("hashCode", resource.hashCode() == contestGeneralInfo.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        ContestGeneralInfo resource = new ContestGeneralInfo();
        resource.setContestGeneralInfoId(2L);
        contestGeneralInfo.setContestGeneralInfoId(1L);
        assertFalse("failed equals", resource.equals(contestGeneralInfo));
        assertFalse("failed hashCode", resource.hashCode() == contestGeneralInfo.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestGeneralInfo#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object specifications = new Object();
        contestGeneralInfo.setContestGeneralInfoId(1L);
        assertFalse("failed equals", contestGeneralInfo.equals(specifications));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestGeneralInfo}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            ContestGeneralInfo entity = new ContestGeneralInfo();
            entity.setGoals("test_goals");
            entity.setTargetAudience("test_audience");
            entity.setBrandingGuidelines("test_guidelines");
            entity.setDislikedDesignsWebsites("test_websites");
            entity.setOtherInstructions("test_instructions");
            entity.setWinningCriteria("test_criteria");

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            ContestGeneralInfo persisted = (ContestGeneralInfo) HibernateUtil.getManager().find(
                    ContestGeneralInfo.class, entity.getContestGeneralInfoId());
            assertEquals("Failed to persist - goals mismatch", entity.getGoals(), persisted.getGoals());
            assertEquals("Failed to persist - audience mismatch", entity.getTargetAudience(), persisted
                    .getTargetAudience());
            assertEquals("Failed to persist - guideline mismatch", entity.getBrandingGuidelines(), persisted
                    .getBrandingGuidelines());
            assertEquals("Failed to persist - websites mismatch", entity.getDislikedDesignsWebsites(), persisted
                    .getDislikedDesignsWebsites());
            assertEquals("Failed to persist - other instruction mismatch", entity.getOtherInstructions(), persisted
                    .getOtherInstructions());
            assertEquals("Failed to persist - criteria mismatch", entity.getWinningCriteria(), persisted
                    .getWinningCriteria());

            // update the entity
            entity.setGoals("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestGeneralInfo) HibernateUtil.getManager().find(ContestGeneralInfo.class,
                    entity.getContestGeneralInfoId());
            assertEquals("Failed to persist - goals mismatch", entity.getGoals(), persisted.getGoals());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
