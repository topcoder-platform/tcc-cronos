/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.stresstests;

/**
 * This is the interface used for the thread to execute the specific operations.<p/>
 *
 * @author yuanyeyuanye, DixonD
 * @version 1.1
 */
public interface Action {

    /**
     * Executes the specific method.
     *
     * @throws Exception if any error occurs.
     */
    public void act() throws Exception;
}