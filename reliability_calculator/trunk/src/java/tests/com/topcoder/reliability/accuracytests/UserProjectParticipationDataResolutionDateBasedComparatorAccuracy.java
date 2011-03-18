/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectParticipationDataComparator;
import com.topcoder.reliability.impl.comparators.UserProjectParticipationDataResolutionDateBasedComparator;


/**
 * Accuracy test for <code>UserProjectParticipationDataResolutionDateBasedComparator</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserProjectParticipationDataResolutionDateBasedComparatorAccuracy
    extends TestCase {
    /**
     * The <code>UserProjectParticipationDataResolutionDateBasedComparator</code> instance for testing.
     */
    private UserProjectParticipationDataResolutionDateBasedComparator instance = new UserProjectParticipationDataResolutionDateBasedComparator();

    /**
     * Accuracy test for constructor.
     */
    public void testCtor() {
        assertNotNull("The result should match.", instance);
        assertTrue("The result should match.",
            instance instanceof UserProjectParticipationDataComparator);
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testcompare() throws Exception {
        UserProjectParticipationData data1 = new UserProjectParticipationData();
        UserProjectParticipationData data2 = new UserProjectParticipationData();

        assertEquals("should be equal", 0, instance.compare(data1, data2));
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testcompare1() throws Exception {
        UserProjectParticipationData data1 = new UserProjectParticipationData();
        UserProjectParticipationData data2 = new UserProjectParticipationData();
        data2.setResolutionDate(new Date());

        assertEquals("should be equal", -1, instance.compare(data1, data2));
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testcompare2() throws Exception {
        UserProjectParticipationData data1 = new UserProjectParticipationData();
        UserProjectParticipationData data2 = new UserProjectParticipationData();
        data1.setResolutionDate(new Date());

        assertEquals("should be equal", 1, instance.compare(data1, data2));
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testcompare3() throws Exception {
        UserProjectParticipationData data1 = new UserProjectParticipationData();
        UserProjectParticipationData data2 = new UserProjectParticipationData();
        data1.setResolutionDate(new Date());

        Thread.sleep(1000);
        data2.setResolutionDate(new Date());

        assertEquals("should be equal", -1, instance.compare(data1, data2));
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testcompare4() throws Exception {
        UserProjectParticipationData data1 = new UserProjectParticipationData();
        UserProjectParticipationData data2 = new UserProjectParticipationData();
        data2.setResolutionDate(new Date());

        Thread.sleep(1000);
        data1.setResolutionDate(new Date());

        assertEquals("should be equal", 1, instance.compare(data1, data2));
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testcompare5() throws Exception {
        UserProjectParticipationData data1 = new UserProjectParticipationData();
        UserProjectParticipationData data2 = new UserProjectParticipationData();
        data1.setProjectId(0);
        data2.setProjectId(1);

        Date d = new Date();
        data2.setResolutionDate(d);
        data1.setResolutionDate(d);

        assertEquals("should be equal", -1, instance.compare(data1, data2));
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testcompare6() throws Exception {
        UserProjectParticipationData data1 = new UserProjectParticipationData();
        UserProjectParticipationData data2 = new UserProjectParticipationData();
        data1.setProjectId(1);
        data2.setProjectId(0);

        Date d = new Date();
        data2.setResolutionDate(d);
        data1.setResolutionDate(d);

        assertEquals("should be equal", 1, instance.compare(data1, data2));
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testcompare7() throws Exception {
        UserProjectParticipationData data1 = new UserProjectParticipationData();
        UserProjectParticipationData data2 = new UserProjectParticipationData();
        data1.setProjectId(1);
        data2.setProjectId(1);

        Date d = new Date();
        data2.setResolutionDate(d);
        data1.setResolutionDate(d);

        assertEquals("should be equal", 0, instance.compare(data1, data2));
    }
}
