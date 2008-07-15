/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.service.digitalrun.entity.BaseEntity;

import com.topcoder.util.log.LogManager;

import junit.framework.TestCase;

import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Tests functionality and error cases of the <code>Helper</code> class.
 * </p>
 * @author waits
 * @version 1.0
 */
public class HelperTests extends TestCase {
    /** Ids for testing. */
    private List < Long > ids = new ArrayList<Long>();

    /**
     * <p>
     * Accuracy test for the private constructor.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testCtorAccuracy() throws Exception {
        Constructor constructor = Helper.class.getDeclaredConstructor(new Class[0]);
        constructor.setAccessible(true);

        Object obj = constructor.newInstance(new Object[0]);
        assertTrue("The constructor can be accessed properly through reflect.", obj instanceof Helper);
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkNull(Object arg, String name, Log logger)</code> with the object is not
     * null, expects no error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCheckNullAccuracy() throws Exception {
        Helper.checkNull("arg", "name", null);
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkNull(Object arg, String name, Log logger)</code> with the object is not
     * null, expects no error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCheckNullAccuracy2() throws Exception {
        Helper.checkNull("arg", "name", LogManager.getLog());
    }

    /**
     * <p>
     * Failure test for the method <code>checkNull(Object arg, String name, Log logger)</code> with arg is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCheckNullWithNull() throws Exception {
        try {
            Helper.checkNull(null, "name", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>checkList</code> method with the array is not null or contains null values, expects
     * no error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCheckListAccuracy() throws Exception {
        ids.add(1L);
        Helper.checkList(ids, "name");
    }

    /**
     * <p>
     * Failure test for the <code>checkList</code> method with the array is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testcheckList_emptyList() throws Exception {
        try {
            Helper.checkList(ids, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>checkList</code> method with the array contains null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testcheckListWithContainNull() throws Exception {
        try {
            ids.add(null);
            Helper.checkList(ids, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>checkLongList</code> method with the array is not null or contains null values,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testcheckLongListAccuracy() throws Exception {
        ids.add(1L);
        Helper.checkLongList(ids, "name");
    }

    /**
     * <p>
     * Failure test for the <code>checkLongList</code> method with the array is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testcheckLongList_emptyList() throws Exception {
        try {
            Helper.checkLongList(ids, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>checkLongList</code> method with the array contains null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testcheckLongListWithContainNull() throws Exception {
        try {
            ids.add(null);
            Helper.checkLongList(ids, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>checkLongList</code> method with the array contains negative,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testcheckLongListWithContainNegative()
        throws Exception {
        try {
            ids.add(-1L);
            Helper.checkLongList(ids, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>checkNegative</code> method with valid value.
     * </p>
     */
    public void testCheckNegative() {
        Helper.checkNegative(1, getName(), null);
    }

    /**
     * <p>
     * Failure test for the <code>checkNegative</code> method with valid value.
     * </p>
     *
     */
    public void testCheckNegative2() {
        Helper.checkNegative(1, getName(), LogManager.getLog());
    }

    /**
     * <p>
     * Failure test for the <code>checkNegative</code> method with invalid value.
     * </p>
     */
    public void testCheckNegative_negative() {
        try {
            Helper.checkNegative(-1, getName(), LogManager.getLog());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>checkEntityPositive</code> method with valid value.
     * </p>
     */
    public void testcheckEntityPositive() {
        BaseEntity baseEntity = new BaseEntity() {
            };

        Helper.checkEntityPositive(baseEntity, getName(), null);
    }

    /**
     * <p>
     * Failure test for the <code>checkEntityPositive</code> method with valid value.
     * </p>
     */
    public void testcheckEntityPositive2() {
        BaseEntity baseEntity = new BaseEntity() {
            };

        Helper.checkEntityPositive(baseEntity, getName(), LogManager.getLog());
    }

    /**
     * <p>
     * Failure test for the <code>checkEntityPositive</code> method with invalid value.
     * </p>
     */
    public void testcheckEntityPositive_positiveId() {
        BaseEntity baseEntity = new BaseEntity() {
            };

        baseEntity.setId(1);

        try {
            Helper.checkEntityPositive(baseEntity, getName(), LogManager.getLog());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>checkEntityPositive</code> method with invalid value.
     * </p>
     */
    public void testcheckEntityPositive_null() {
        try {
            Helper.checkEntityPositive(null, "name", LogManager.getLog());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>logEntranceInfo</code> method, expects no error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLogEntranceInfoAccuracy1() throws Exception {
        Helper.logEntranceInfo("method", LogManager.getLog());
    }

    /**
     * <p>
     * Accuracy test for the <code>logEntranceInfo</code> method with the logger is null, expects no error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLogEntranceInfoAccuracy2() throws Exception {
        Helper.logEntranceInfo("method", null);
    }

    /**
     * <p>
     * Accuracy test for the <code>logExitInfo</code> method, expects no error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLogExitInfoAccuracy1() throws Exception {
        Helper.logExitInfo("method", LogManager.getLog());
    }

    /**
     * <p>
     * Accuracy test for the <code>logExitInfo</code> method with the logger is null, expects no error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLogExitInfoAccuracy2() throws Exception {
        Helper.logExitInfo("method", null);
    }

    /**
     * <p>
     * Accuracy test for the <code>logException</code> method, expects no error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLogExceptionAccuracy1() throws Exception {
        Helper.logException(new IllegalArgumentException("test"), LogManager.getLog());
    }

    /**
     * <p>
     * Accuracy test for the <code>logException</code> method with the logger is null, expects no error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLogExceptionAccuracy2() throws Exception {
        Helper.logException(new IllegalArgumentException("test"), null);
    }

    /**
     * <p>
     * Accuracy test for the <code>logError</code> method, expects no error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLogInfoAccuracy1() throws Exception {
        Helper.logError("info", LogManager.getLog());
    }

    /**
     * <p>
     * Accuracy test for the <code>logError</code> method with the logger is null, expects no error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLogInfoAccuracy2() throws Exception {
        Helper.logError("info", null);
    }
}
