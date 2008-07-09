/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging.persistence.failuretests;

import com.topcoder.messaging.MessageBoardConfigurationException;
import com.topcoder.messaging.persistence.InformixMessageBoardPersistence;

import junit.framework.TestCase;

/**
 * The failure test for the class {@link InformixMessageBoardPersistence}.
 * 
 * @author superZZ
 * @version 2.0
 */
public class InformixMessageBoardPersistenceTests extends TestCase {
    /**
     * Tests constructor.
     * 
     * Empty connection name is configured.
     * 
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testConstructor_EmptyConnectionName() throws Exception {
        try {
            new InformixMessageBoardPersistence("failure/config.properties",
                    "InformixMessageBoardPersistence_EmptyConnectionName");
            fail("MessageBoardConfigurationException is expected.");
        } catch (MessageBoardConfigurationException e) {
        }
    }

    /**
     * Tests constructor.
     * 
     * Empty connection name is configured.
     * 
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testConstructor_EmptyIdGeneratorName() throws Exception {
        try {
            new InformixMessageBoardPersistence("failure/config.properties",
                    "InformixMessageBoardPersistence_Empty_IDGenerator");
            fail("MessageBoardConfigurationException is expected.");
        } catch (MessageBoardConfigurationException e) {
        }
    }
}
