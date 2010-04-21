/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.pipeline.CapacityData;
import com.topcoder.service.pipeline.PipelineServiceFacade;

/**
 * <p>
 * Unit test for <code>{@link GetCapacityFullDatesAction}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class GetCapacityFullDatesActionUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>GetCapacityFullDatesAction</code> instance.
     * </p>
     */
    private GetCapacityFullDatesAction getCapacityFullDatesAction;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(GetCapacityFullDatesActionUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        getCapacityFullDatesAction = new GetCapacityFullDatesAction();

        getCapacityFullDatesAction.prepare();
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        getCapacityFullDatesAction = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>GetCapacityFullDatesAction()</code> constructor.
     * </p>
     * <p>
     * The constructor simply do nothing, instance should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("instance should not null", getCapacityFullDatesAction);

        assertTrue("invalid super class.", getCapacityFullDatesAction instanceof BaseDirectStrutsAction);
    }

    /**
     * <p>
     * Tests the <code>getPipelineServiceFacade()</code> method.
     * </p>
     * <p>
     * By default, The pipelineServiceFacade is null.
     * </p>
     */
    public void testGetPipelineServiceFacade_default() {
        assertNull("The pipelineServiceFacade should be null", getCapacityFullDatesAction.getPipelineServiceFacade());
    }

    /**
     * <p>
     * Tests the <code>setPipelineServiceFacade(PipelineServiceFacade)</code> method.
     * </p>
     * <p>
     * If the pipelineServiceFacade is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetPipelineServiceFacade_null() {
        try {
            getCapacityFullDatesAction.setPipelineServiceFacade(null);

            fail("If the pipelineServiceFacade is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>setPipelineServiceFacade(PipelineServiceFacade)</code> method.
     * </p>
     * <p>
     * If the pipelineServiceFacade is not null, should set internally.
     * </p>
     */
    public void testSetPipelineServiceFacade_accuracy() {
        PipelineServiceFacade pipelineServiceFacade = new MockPipelineServiceFacade();
        getCapacityFullDatesAction.setPipelineServiceFacade(pipelineServiceFacade);

        assertSame("The pipelineServiceFacade is not set.", pipelineServiceFacade, getCapacityFullDatesAction
                .getPipelineServiceFacade());
    }

    /**
     * <p>
     * Tests the <code>getContestTypeIdByContestType()</code> method.
     * </p>
     * <p>
     * By default, The contestTypeIdByContestType is null.
     * </p>
     */
    public void testGetContestTypeIdByContestType_default() {
        assertNull("The contestTypeIdByContestType should be null", getCapacityFullDatesAction
                .getContestTypeIdByContestType());
    }

    /**
     * <p>
     * Tests the <code>setContestTypeIdByContestType(Map)</code> method.
     * </p>
     * <p>
     * If the contestTypeIdByContestType is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetContestTypeIdByContestType_null() {
        try {
            getCapacityFullDatesAction.setContestTypeIdByContestType(null);

            fail("If the contestTypeIdByContestType is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>setContestTypeIdByContestType(Map)</code> method.
     * </p>
     * <p>
     * If the contestTypeIdByContestType contains null key, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetContestTypeIdByContestType_containsNullKey() {
        Map<String, Long> contestTypeIdByContestType = new HashMap<String, Long>();
        contestTypeIdByContestType.put(null, 1l);
        try {
            getCapacityFullDatesAction.setContestTypeIdByContestType(contestTypeIdByContestType);

            fail("If the contestTypeIdByContestType contains null key, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>setContestTypeIdByContestType(Map)</code> method.
     * </p>
     * <p>
     * If the contestTypeIdByContestType contains null value, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetContestTypeIdByContestType_containsNullValue() {
        Map<String, Long> contestTypeIdByContestType = new HashMap<String, Long>();
        contestTypeIdByContestType.put("key", null);
        try {
            getCapacityFullDatesAction.setContestTypeIdByContestType(contestTypeIdByContestType);

            fail("If the contestTypeIdByContestType contains null value, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>setContestTypeIdByContestType(ContestTypeIdByContestType)</code> method.
     * </p>
     * <p>
     * If the contestTypeIdByContestType is not null and does not contain null key/value, should set internally.
     * </p>
     */
    public void testSetContestTypeIdByContestType_accuracy() {
        Map<String, Long> contestTypeIdByContestType = new HashMap<String, Long>();
        contestTypeIdByContestType.put("key", 1l);
        getCapacityFullDatesAction.setContestTypeIdByContestType(contestTypeIdByContestType);

        assertNotSame("Shallow copy should be made.", contestTypeIdByContestType, getCapacityFullDatesAction
                .getContestTypeIdByContestType());

        assertEquals("field not set.", 1l, getCapacityFullDatesAction.getContestTypeIdByContestType().get("key")
                .longValue());
    }

    /**
     * <p>
     * Tests the <code>getContestType()</code> method.
     * </p>
     * <p>
     * By default, The contest type should be null.
     * </p>
     */
    public void testGetContestId_default() {
        assertNull("The contestType should be null", getCapacityFullDatesAction.getContestType());
    }

    /**
     * <p>
     * Tests the <code>setContestType(String)</code> method.
     * </p>
     * <p>
     * The contestType should be set internally.
     * </p>
     */
    public void testSetContestType_accuracy() {
        String contestType = "studio";
        getCapacityFullDatesAction.setContestType(contestType);

        assertEquals("field not set.", contestType, getCapacityFullDatesAction.getContestType());
    }

    /**
     * <p>
     * Tests the <code>isStudio()</code> method.
     * </p>
     * <p>
     * By default, false should be return.
     * </p>
     */
    public void testIsStudio_default() {
        assertFalse(getCapacityFullDatesAction.isStudio());
    }

    /**
     * <p>
     * Tests the <code>setStudio(boolean)</code> method.
     * </p>
     * <p>
     * The isStudio should be set internally.
     * </p>
     */
    public void testSetContestId_accuracy() {
        getCapacityFullDatesAction.setStudio(true);

        assertTrue(getCapacityFullDatesAction.isStudio());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * If pipelineServiceFacade is null, should throw IllegalStateException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_ISE1() throws Exception {
        Map<String, Long> contestTypeIdByContestType = new HashMap<String, Long>();
        contestTypeIdByContestType.put("studio", new Long(1));
        getCapacityFullDatesAction.setContestTypeIdByContestType(contestTypeIdByContestType);

        try {
            getCapacityFullDatesAction.executeAction();

            fail("If pipelineServiceFacade is null, should throw IllegalStateException.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * If contestTypeIdByContestType is null, should throw IllegalStateException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_ISE2() throws Exception {
        getCapacityFullDatesAction.setPipelineServiceFacade(new MockPipelineServiceFacade());
        try {
            getCapacityFullDatesAction.executeAction();

            fail("If contestTypeIdByContestType is null, should throw IllegalStateException.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_noContestTypId() throws Exception {
        getCapacityFullDatesAction.setContestType("studio1");
        Map<String, Long> contestTypeIdByContestType = new HashMap<String, Long>();
        contestTypeIdByContestType.put("studio", new Long(1));
        getCapacityFullDatesAction.setContestTypeIdByContestType(contestTypeIdByContestType);
        getCapacityFullDatesAction.setPipelineServiceFacade(new MockPipelineServiceFacade());

        // invoke
        getCapacityFullDatesAction.executeAction();

        // verify the result
        Object result = getCapacityFullDatesAction.getResult();

        assertNull("result should not set", result);
        assertFalse(getCapacityFullDatesAction.getFieldErrors().isEmpty());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @see MockPipelineServiceFacade#getCapacityFullDates(int, boolean)
     */
    @SuppressWarnings("unchecked")
    public void testExecuteAction_accuracy() throws Exception {
        getCapacityFullDatesAction.setContestType("studio");
        Map<String, Long> contestTypeIdByContestType = new HashMap<String, Long>();
        contestTypeIdByContestType.put("studio", new Long(1));
        getCapacityFullDatesAction.setContestTypeIdByContestType(contestTypeIdByContestType);
        getCapacityFullDatesAction.setPipelineServiceFacade(new MockPipelineServiceFacade());

        // invoke
        getCapacityFullDatesAction.executeAction();

        // verify the result
        Object result = getCapacityFullDatesAction.getResult();

        assertTrue("incorrect return type", result instanceof List<?>);

        List<CapacityData> capacityDatas = (List<CapacityData>) result;

        assertEquals("incorrect size.", 5, capacityDatas.size());
    }
}
