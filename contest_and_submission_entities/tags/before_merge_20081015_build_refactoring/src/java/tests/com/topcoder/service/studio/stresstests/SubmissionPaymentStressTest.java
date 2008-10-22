/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.SubmissionPayment;


/**
 * <p>
 * Stress test for the class <code>SubmissionPayment</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class SubmissionPaymentStressTest extends AbstractStressTest {
    /**
     * <p>
     * Sets up the necessary environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tears down the environment.
     * </P>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Stress test for the class SubmissionPayment using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmissionPaymentPersistence() throws Exception {
        SubmissionPayment[] payments = new SubmissionPayment[10];

        for (int i = 0; i < payments.length; i++) {
            payments[i] = new SubmissionPayment();
            payments[i].setPrice(i + 0d);
            payments[i].setSubmission(createSubmission(i));
            PaymentStatus paymentStatus = new PaymentStatus();
            paymentStatus.setPaymentStatusId(i + 0L);
            payments[i].setStatus(paymentStatus);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();

        for (int i = 0; i < payments.length; i++) {
            session.save(payments[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for SubmissionPayment took " + time + "ms");
    }
}
