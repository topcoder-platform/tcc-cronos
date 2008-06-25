/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validators;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.registration.RegistrationValidator;
import com.topcoder.registration.ValidationResult;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.User;

/**
 * <p>
 * The Demo suite for this component.
 * </p>
 *
 * @author AleaActaEst, romanoTC
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * <p>
     * Constant holding the number of milliseconds in a minute.
     * </p>
     */
    private static final long MINUTES = 60 * 1000;

    /**
     * <p>
     * Constant holding the number of milliseconds in an hour.
     * </p>
     */
    private static final long HOURS = 60 * MINUTES;

    /**
     * <p>
     * Constant holding the number of milliseconds in a day.
     * </p>
     */
    private static final long DAYS = 24 * HOURS;

    /**
     * <p>
     * Demonstrates the validate registration usage. Uses a custom resource bundle file.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDemo_ValidateRegistration() throws Exception {
        System.out.println("---- testDemo_ValidateRegistration ----");

        // current time
        long current = System.currentTimeMillis();

        // A dummy user
        User user = new User();

        // We can create a default validator
        String bundleName = "Demo";

        // We can create a specific validator based on some specific bundle
        RegistrationValidator validator = new OpenRegistrationValidator(bundleName);

        // Let's call today's date D and assume an offset of 1 hour each way.

        ContestRole reviewerRole = new ContestRole();
        reviewerRole.setRegistrationStartOffset(HOURS);
        reviewerRole.setRegistrationEndOffset(HOURS);

        Contest testContest = new Contest();

        // Here is how we would run the code assuming some "reviewer" role has been
        // passed and assuming that the contest start date is D-1 and end is D+5
        // Note that the user is not relevant to this validation.

        testContest.setRegistrationStart(new Date(current - DAYS));
        testContest.setRegistrationEnd(new Date(current + 5 * DAYS));

        ValidationResult result = validator.validateRegistration(testContest, user, reviewerRole);

        // this will be successful since registration is currently open. We would
        // expect to get the following message in result.getDescription():
        // "Registration is validated by OpenRegistrationValidator."

        System.out.println(result.getDescription());

        // Here is how we would run the code assuming some "reviewer" role has been
        // passed and assuming that the contest start date is D-5 and end is D-1
        // Note that the user is not relevant to this validation.

        testContest.setRegistrationStart(new Date(current - 4 * DAYS));
        testContest.setRegistrationEnd(new Date(current - DAYS));

        result = validator.validateRegistration(testContest, user, reviewerRole);

        // this will fail since registration is currently closed. We would
        // expect to get the following message in result.getDescription():
        // "Registration validation failed because registration is closed for the contest and role submitted."

        System.out.println(result.getDescription());

        // Here is how we would run the code assuming some "reviewer" role has been
        // passed and assuming that the contest start date is D+1 and end is D+5
        // Note that the user is not relevant to this validation.

        testContest.setRegistrationStart(new Date(current + DAYS));
        testContest.setRegistrationEnd(new Date(current - 5 * DAYS));

        result = validator.validateRegistration(testContest, user, reviewerRole);

        // this will fail since registration is currently not open. We would
        // expect to get the following message in result.getDescription():
        // "Registration validation failed because registration is not open yet for the contest and role submitted.

        System.out.println(result.getDescription());

        // Here is how we would run the code assuming some "reviewer" role has been
        // passed and assuming that the contest start date is D+1h and end is D+5
        // Note that the user is not relevant to this validation.

        testContest.setRegistrationStart(new Date(current + HOURS));
        testContest.setRegistrationEnd(new Date(current - 5 * DAYS));

        result = validator.validateRegistration(testContest, user, reviewerRole);

        // this will fail since registration is currently not open (due to offset). We would
        // expect to get the following message in result.getDescription():
        // "Registration validation failed because registration is not open yet for the contest and role submitted.

        System.out.println(result.getDescription());

        // Here is how we would run the code assuming some "reviewer" role has been
        // passed and assuming that the contest start date is D-1 and end is D-50min
        // Note that the user is not relevant to this validation.

        testContest.setRegistrationStart(new Date(current - DAYS));
        testContest.setRegistrationEnd(new Date(current - 50 * MINUTES));

        result = validator.validateRegistration(testContest, user, reviewerRole);

        // this will be successful since registration is currently open (due to offset). We would
        // expect to get the following message in result.getDescription():
        // "Registration is validated by OpenRegistrationValidator."

        System.out.println(result.getDescription());
    }

    /**
     * <p>
     * Demonstrates the validate unregistration usage. Uses the default resource bundle file.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDemo_ValidateUnRegistration() throws Exception {
        System.out.println("---- testDemo_ValidateUnRegistration ----");

        // current time
        long current = System.currentTimeMillis();

        // A dummy user
        User user = new User();

        // We can create a default validator
        RegistrationValidator validator = new OpenRegistrationValidator();

        // Let's call today's date D and assume an offset of 1 hour each way.

        ContestRole reviewerRole = new ContestRole();
        reviewerRole.setRegistrationStartOffset(HOURS);
        reviewerRole.setRegistrationEndOffset(HOURS);

        Contest testContest = new Contest();

        // Here is how we would run the code assuming some "reviewer" role has been
        // passed and assuming that the contest start date is D-1 and end is D+5
        // Note that the user is not relevant to this validation.

        testContest.setRegistrationStart(new Date(current - DAYS));
        testContest.setRegistrationEnd(new Date(current + 5 * DAYS));

        ValidationResult result = validator.validateUnregistration(testContest, user, reviewerRole);

        // this will be successful since registration is currently open. We would
        // expect to get the following message in result.getDescription():
        // "Unregistration successful"

        System.out.println(result.getDescription());

        // Here is how we would run the code assuming some "reviewer" role has been
        // passed and assuming that the contest start date is D-5 and end is D-1
        // Note that the user is not relevant to this validation.

        testContest.setRegistrationStart(new Date(current - 4 * DAYS));
        testContest.setRegistrationEnd(new Date(current - DAYS));

        result = validator.validateUnregistration(testContest, user, reviewerRole);

        // this will fail since registration is currently closed. We would
        // expect to get the following message in result.getDescription():
        // "Unregistration too late"

        System.out.println(result.getDescription());

        // Here is how we would run the code assuming some "reviewer" role has been
        // passed and assuming that the contest start date is D+1 and end is D+5
        // Note that the user is not relevant to this validation.

        testContest.setRegistrationStart(new Date(current + DAYS));
        testContest.setRegistrationEnd(new Date(current - 5 * DAYS));

        result = validator.validateUnregistration(testContest, user, reviewerRole);

        // this will fail since registration is currently not open. We would
        // expect to get the following message in result.getDescription():
        // "Unregistration too early"

        System.out.println(result.getDescription());

        // Here is how we would run the code assuming some "reviewer" role has been
        // passed and assuming that the contest start date is D+1h and end is D+5
        // Note that the user is not relevant to this validation.

        testContest.setRegistrationStart(new Date(current + HOURS));
        testContest.setRegistrationEnd(new Date(current - 5 * DAYS));

        result = validator.validateUnregistration(testContest, user, reviewerRole);

        // this will fail since registration is currently not open (due to offset). We would
        // expect to get the following message in result.getDescription():
        // "Unregistration too early"

        System.out.println(result.getDescription());

        // Here is how we would run the code assuming some "reviewer" role has been
        // passed and assuming that the contest start date is D-1 and end is D-50min
        // Note that the user is not relevant to this validation.

        testContest.setRegistrationStart(new Date(current - DAYS));
        testContest.setRegistrationEnd(new Date(current - 50 * MINUTES));

        result = validator.validateUnregistration(testContest, user, reviewerRole);

        // this will be successful since registration is currently open (due to offset). We would
        // expect to get the following message in result.getDescription():
        // "Unregistration successful"

        System.out.println(result.getDescription());
    }
}
