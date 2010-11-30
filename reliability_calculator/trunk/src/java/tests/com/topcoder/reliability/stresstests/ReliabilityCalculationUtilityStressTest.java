/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.stresstests;

import java.security.Permission;

import com.topcoder.reliability.ReliabilityCalculationUtility;

/**
 * <p>
 * This class contains Stress tests for ReliabilityCalculationUtility.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class ReliabilityCalculationUtilityStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents configuration file switch.
     * </p>
     */
    private static final String CONFIG_FILE_SWITCH = "-c=stresstests/config.properties";

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        System.setSecurityManager(new NoExitSecurityManager());
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        System.setSecurityManager(null);
    }

    /**
     * <p>
     * Tests {@link ReliabilityCalculationUtility#main(String[])} with valid arguments passed and small test.
     * </p>
     * <p>
     * No exception is expected.
     * </p>
     */
    public void testMain_Small() {
        for (int i = 0; i < getRunCount(); i++) {
            ReliabilityCalculationUtility.main(new String[] {CONFIG_FILE_SWITCH});
        }
    }

    /**
     * <p>
     * Tests {@link ReliabilityCalculationUtility#main(String[])} with valid arguments passed and small test.
     * </p>
     * <p>
     * No exception is expected.
     * </p>
     */
    public void testMain_Large() {
        for (int i = 0; i < getRunCount(); i++) {
            ReliabilityCalculationUtility.main(new String[] {CONFIG_FILE_SWITCH});
        }
    }

    /**
     * <p>
     * SecurityManager implementation that prevents System.exit to actually exit the JVM.
     * </p>
     * @author sokol
     * @version 1.0
     */
    public class NoExitSecurityManager extends SecurityManager {

        /**
         * <p>
         * Checks permission.
         * </p>
         * @param perm the permission
         */
        public void checkPermission(Permission perm) {
            // allow anything.
        }

        /**
         * <p>
         * Checks permission.
         * </p>
         * @param perm the permission
         * @param context the context
         */
        public void checkPermission(Permission perm, Object context) {
            // allow anything.
        }

        /**
         * <p>
         * Checks exit. It throws ExitException.
         * </p>
         * @param status the status
         * @throws ExitException is always thrown
         */
        public void checkExit(int status) {
            super.checkExit(status);
            throw new ExitException(status);
        }

        /**
         * <p>
         * This exception is thrown when System.exit is intercepted in NoExitSecurityManager#checkExit() method.
         * </p>
         * @author sokol
         * @version 1.0
         */
        class ExitException extends SecurityException {

            /**
             * <p>
             * The exception status.
             * </p>
             */
            private final int status;

            /**
             * <p>
             * Creates exception with status.
             * </p>
             * @param status the status
             */
            public ExitException(int status) {
                super("ExitException for test only");
                this.status = status;
            }

            /**
             * <p>
             * Returns the exit status.
             * </p>
             * @return the status
             */
            public int getStatus() {
                return status;
            }
        }
    }
}
