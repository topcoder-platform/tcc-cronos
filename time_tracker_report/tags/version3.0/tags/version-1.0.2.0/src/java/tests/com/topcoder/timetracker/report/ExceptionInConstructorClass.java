/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

/**
 * This is a class for testing correct failure in case of constructor throwing an exception.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ExceptionInConstructorClass {

    /**
     * constructor as Testcase scenario.
     *
     * @throws NullPointerException always, as failure scenario.
     */
    public ExceptionInConstructorClass() {
        throw new NullPointerException("This class throws Exception upon ConstructorInvocation");
    }
}
