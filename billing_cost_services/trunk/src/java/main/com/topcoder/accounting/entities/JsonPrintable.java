/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities;

/**
 * <p>
 * This interface defines the json printable method.
 * </p>
 * <p>
 * Thread Safety: The implement class is not required to thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public interface JsonPrintable {
    /**
     * <p>
     * The method to return the json string.
     * </p>
     *
     * @return the json string
     */
    public String toJSONString();
}
