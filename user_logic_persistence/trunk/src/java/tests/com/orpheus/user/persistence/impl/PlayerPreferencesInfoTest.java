/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.Serializable;

/**
 * <p>
 * Tests the PlayerPreferencesInfo class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class PlayerPreferencesInfoTest extends TestCase {

    /**
     * <p>
     * The PlayerPreferencesInfo instance to test.
     * </p>
     */
    private PlayerPreferencesInfo testedInstance = null;

    /**
     * <p>
     * Creates the test PlayerPreferencesInfo instance.
     * </p>
     */
    protected void setUp() {
        testedInstance = new PlayerPreferencesInfo(1234);
    }

    /**
     * <p>
     * Tests the PlayerPreferencesInfo(long id) constructor with a valid positive
     * argument. The newly created instance should not be null. The return value
     * of the getId() method should be equal to the constructor argument.
     * </p>
     */
    public void testCtorWithValidPositiveArg() {
        long id = 85837850;
        PlayerPreferencesInfo info = new PlayerPreferencesInfo(id);
        assertNotNull("The PlayerPreferencesInfo instance should not be null", info);
        assertEquals("The ID is incorrect", id, info.getId());
    }

    /**
     * <p>
     * Tests the PlayerPreferencesInfo(long id) constructor with a valid positive
     * argument. The newly created instance should not be null. The return value
     * of the getId() method should be equal to the constructor argument.
     * </p>
     */
    public void testCtorWithValidNegativeArg() {
        long id = -85837850;
        PlayerPreferencesInfo info = new PlayerPreferencesInfo(id);
        assertNotNull("The PlayerPreferencesInfo instance should not be null", info);
        assertEquals("The ID is incorrect", id, info.getId());
    }

    /**
     * <p>
     * Tests the setId(long id) method with a valid positive argument. The
     * return value of the getId() method should be equal to the method
     * argument.
     * </p>
     */
    public void testSetIdWithValidPositiveArg() {
        long id = 967183;
        testedInstance.setId(id);
        assertEquals("The ID is incorrect", id, testedInstance.getId());
    }

    /**
     * <p>
     * Tests the setId(long id) method with a valid negative argument. The
     * return value of the getId() method should be equal to the method
     * argument.
     * </p>
     */
    public void testSetIdWithValidNegativeArg() {
        long id = -967183;
        testedInstance.setId(id);
        assertEquals("The ID is incorrect", id, testedInstance.getId());
    }

    /**
     * <p>
     * Tests the setSoundOption(int option) method with a valid positive argument. The
     * return value of the getSoundOption() method should be equal to the method
     * argument.
     * </p>
     */
    public void testSetSoundOptionWithValidPositiveArg() {
        int option = 967183;
        testedInstance.setSoundOption(option);
        assertEquals("The sound option is incorrect", option, testedInstance.getSoundOption());
    }

    /**
     * <p>
     * Tests the setSoundOption(int option) method with a valid negative argument. The
     * return value of the getSoundOption() method should be equal to the method
     * argument.
     * </p>
     */
    public void testSetSoundOptionWithValidNegativeArg() {
        int option = -967183;
        testedInstance.setSoundOption(option);
        assertEquals("The sound option is incorrect", option, testedInstance.getSoundOption());
    }

    /**
     * <p>
     * Tests the setGeneralNotificationsOptIn(boolean option) method with a valid positive argument. The
     * return value of the getGeneralNotificationsOptIn() method should be equal to the method
     * argument.
     * </p>
     */
    public void testSetGeneralNotificationOptIn() {
        boolean[] values = new boolean[] {true, false};
        for (int i = 0; i < values.length; i++) {
            testedInstance.setGeneralNotificationsOptIn(values[i]);
            assertEquals("The sound option is incorrect", values[i], testedInstance.getGeneralNotificationsOptIn());
        }
    }

    /**
     * <p>
     * Tests that PlayerPreferencesInfo implements the Serializable interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("PlayerPreferencesInfo should implement the Serializable interface",
                   testedInstance instanceof Serializable);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(PlayerPreferencesInfoTest.class);
    }

}
