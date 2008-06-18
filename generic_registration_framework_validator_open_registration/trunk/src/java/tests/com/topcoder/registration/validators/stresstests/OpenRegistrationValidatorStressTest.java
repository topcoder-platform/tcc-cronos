/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validators.stresstests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.registration.RegistrationValidator;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.User;
import com.topcoder.registration.validators.OpenRegistrationValidator;

/**
 * <p>
 * Failure test cases for <code>OpenRegistrationValidator</code> class.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class OpenRegistrationValidatorStressTest extends TestCase {

    /**
     * <p>
     * The maximum number of stress runs.
     * </p>
     */
    private static final int MAX_RUN = 500;

    /**
     * <p>
     * Constant holding the number of milliseconds in a day.
     * </p>
     */
    private static final long DAYS = 24 * 60 * 60 * 1000;

    /**
     * <p>
     * Stress test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: Call the method several times and calculate mean time to run.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration() throws Exception {

        long startTime = System.currentTimeMillis();

        RegistrationValidator validator = new OpenRegistrationValidator("stress.Complete");

        for (int i = 0; i < MAX_RUN; ++i) {
            validator.validateUnregistration(createContest(10 * DAYS, 10 * DAYS), new User(), createRole(0, 0));
        }

        double executionTime = (System.currentTimeMillis() - startTime) / (double) MAX_RUN;

        System.out.println("validateUnregistration execution time: " + executionTime + "ms");
    }

    /**
     * <p>
     * Stress test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)} <br>
     * Target: Call the method several times and calculate mean time to run.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration() throws Exception {

        long startTime = System.currentTimeMillis();

        RegistrationValidator validator = new OpenRegistrationValidator("stress.Complete");

        for (int i = 0; i < MAX_RUN; ++i) {
            validator.validateRegistration(createContest(10 * DAYS, 10 * DAYS), new User(), createRole(0, 0));
        }

        double executionTime = (System.currentTimeMillis() - startTime) / (double) MAX_RUN;

        System.out.println("validateRegistration execution time: " + executionTime + "ms");
    }

    /**
     * <p>
     * Creates a <code>Contest</code> that starts at current date - startOffset and ends at current date + end
     * offset.
     *
     * @param startOffset the offset (positive means past) from current date.
     * @param endOffset the offset (positive means future) from current date.
     * @return the <code>Contest</code> with the registration start and end date set.
     */
    private static Contest createContest(long startOffset, long endOffset) {
        Contest contest = new Contest();

        long current = System.currentTimeMillis();

        contest.setRegistrationStart(new Date(current - startOffset));
        contest.setRegistrationEnd(new Date(current + endOffset));

        return contest;
    }

    /**
     * <p>
     * Creates a <code>ContestRole</code> with the given offsets.
     * </p>
     *
     * @param startOffset the role start offset.
     * @param endOffset the role end offset.
     * @return the <code>ContestRole</code> with the given offsets.
     */
    private static ContestRole createRole(long startOffset, long endOffset) {
        ContestRole role = new ContestRole();

        role.setRegistrationStartOffset(startOffset);
        role.setRegistrationEndOffset(endOffset);

        return role;
    }
}
