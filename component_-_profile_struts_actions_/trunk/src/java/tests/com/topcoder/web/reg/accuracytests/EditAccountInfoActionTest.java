/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.reg.actions.profile.EditAccountInfoAction;
import com.topcoder.web.reg.actions.profile.ViewAccountInfoAction;

/**
 * <p>
 * Accuracy tests for the {@link EditAccountInfoAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class EditAccountInfoActionTest {
    /** Represents the canChangeHandle used to test again. */
    private boolean canChangeHandleValue;

    /** Represents the coderTypeDAO used to test again. */
    private CoderTypeDAO coderTypeDAOValue;

    /** Represents the coderTypes used to test again. */
    private List coderTypesValue;

    /** Represents the instance used to test again. */
    private EditAccountInfoAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new EditAccountInfoAction();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link EditAccountInfoAction#EditAccountInfoAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testEditAccountInfoAction() throws Exception {
        new EditAccountInfoAction();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link EditAccountInfoAction#getCanChangeHandle()}
     * </p>
     * <p>
     * The value of <code>canChangeHandle</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getCanChangeHandle() throws Exception {
        assertTrue("Old value", !testInstance.getCanChangeHandle());
    }

    /**
     * <p>
     * Accuracy test {@link EditAccountInfoAction#setCanChangeHandle(boolean)}.
     * </p>
     * <p>
     * The value of <code>canChangeHandle</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCanChangeHandle() throws Exception {
        testInstance.setCanChangeHandle(canChangeHandleValue);
        assertEquals("New value", canChangeHandleValue, testInstance.getCanChangeHandle());
    }

    /**
     * <p>
     * Accuracy test for {@link EditAccountInfoAction#getCoderTypeDAO()}
     * </p>
     * <p>
     * The value of <code>coderTypeDAO</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getCoderTypeDAO() throws Exception {
        assertNull("Old value", testInstance.getCoderTypeDAO());
    }

    /**
     * <p>
     * Accuracy test {@link EditAccountInfoAction#setCoderTypeDAO(CoderTypeDAO)}.
     * </p>
     * <p>
     * The value of <code>coderTypeDAO</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCoderTypeDAO() throws Exception {
        testInstance.setCoderTypeDAO(coderTypeDAOValue);
        assertEquals("New value", coderTypeDAOValue, testInstance.getCoderTypeDAO());
    }

    /**
     * <p>
     * Accuracy test for {@link EditAccountInfoAction#getCoderTypes()}
     * </p>
     * <p>
     * The value of <code>coderTypes</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getCoderTypes() throws Exception {
        assertNull("Old value", testInstance.getCoderTypes());
    }

    /**
     * <p>
     * Accuracy test {@link EditAccountInfoAction#setCoderTypes(List)}.
     * </p>
     * <p>
     * The value of <code>coderTypes</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCoderTypes() throws Exception {
        testInstance.setCoderTypes(coderTypesValue);
        assertEquals("New value", coderTypesValue, testInstance.getCoderTypes());
    }

}