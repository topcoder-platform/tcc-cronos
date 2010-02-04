/**
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.contest.coo.stresstests;

/**
 * <p>
 * This is the interface used for the thread to execute the specific operations.
 * </p>
 *
 * @author yuanyeyuanye
 * @version 1.0
 */
public interface Action {

    /**
     * Execute the specific method.
     *
     * @throws Exception If exception occurs.
     */
    public void act() throws Exception;
}
