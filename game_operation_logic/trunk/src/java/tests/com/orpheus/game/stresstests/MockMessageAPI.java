/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import com.topcoder.message.messenger.MessageAPI;

/**
 * <p>
 * Mock implementation of MockMessageAPI, for test purpose.
 * </p>
 *
 * @author catcher
 * @version 1.0
 */
public class MockMessageAPI extends MessageAPI {

    /**
     * the name for test.
     */
    private String name;

    /**
     * the value for test.
     */
    private Object value;

    public void setParameterValue(String name, Object value) throws IllegalArgumentException {
        this.name = name;
        this.value = value;
    }

    /**
     * Get the name.
     *
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Get the value.
     *
     * @return the value
     */
    public Object getValue() {
        return value;
    }

}

