/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.submission.PaymentStatus;


/**
 * <p>
 * Stress test for the class <code>PaymentStatus</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class PaymentStatusStressTest extends AbstractStressTest {
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
     * Stress test for the class PaymentStatus using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPaymentStatusPersistence() throws Exception {
        PaymentStatus[] paymentStatuses = new PaymentStatus[10];

        for (int i = 0; i < paymentStatuses.length; i++) {
            paymentStatuses[i] = new PaymentStatus();
            paymentStatuses[i].setPaymentStatusId(i + 0L);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();

        for (int i = 0; i < paymentStatuses.length; i++) {
            session.save(paymentStatuses[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for PaymentStatus took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", paymentStatuses[0],
            session.get(PaymentStatus.class,
                paymentStatuses[0].getPaymentStatusId()));
    }
}
