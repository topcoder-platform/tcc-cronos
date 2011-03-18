/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import java.lang.reflect.Field;
import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.reliability.impl.ResolutionDateDetector;
import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.detectors.PhaseEndTimeBasedResolutionDateDetector;


/**
 * Accuracy test for <code>PhaseEndTimeBasedResolutionDateDetector</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PhaseEndTimeBasedResolutionDateDetectorAccuracy extends TestCase {
    /**
     * The <code>PhaseEndTimeBasedResolutionDateDetector</code> instance for testing.
     */
    private PhaseEndTimeBasedResolutionDateDetector instance = new PhaseEndTimeBasedResolutionDateDetector();

    /**
     * The instance for testing.
     */
    private ConfigurationObject config;

    /**
     * Sets up environment.
     *
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        config = new DefaultConfigurationObject("myconfig");
        config.setPropertyValue("loggerName", "mylog");

        instance.configure(config);
    }

    /**
     * Accuracy test for constructor.
     */
    public void testCtor() {
        assertNotNull("The result should match.", instance);
        assertTrue("The result should match.",
            instance instanceof ResolutionDateDetector);
    }

    /**
     * Accuracy test for <code>configure()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testConfigure() throws Exception {
        ConfigurationObject configObj = new DefaultConfigurationObject(
                "myconfig");

        instance = new PhaseEndTimeBasedResolutionDateDetector();
        instance.configure(configObj);

        Field field = instance.getClass().getDeclaredField("log");
        field.setAccessible(true);

        assertNull("The log isn't set.", field.get(instance));

        configObj.setPropertyValue("loggerName", "mylog");
        instance = new PhaseEndTimeBasedResolutionDateDetector();
        instance.configure(configObj);
        assertNotNull("the log should be set.", field.get(instance));
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testDetect_InSubmission() throws Exception {
        UserProjectParticipationData data = new UserProjectParticipationData();
        data.setResolutionDate(new Date());

        instance.detect(data);
        assertNull("The resolutionDate cannot be set.", data.getResolutionDate());
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testDetect_NoSubmission() throws Exception {
        UserProjectParticipationData data = new UserProjectParticipationData();
        data.setResolutionDate(new Date());
        data.setSubmissionPhaseStatusId(3);

        Date d = new Date();
        data.setSubmissionPhaseEnd(d);

        instance.detect(data);
        assertEquals("The resolutionDate should be submission phase end.", d,
            data.getResolutionDate());
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testDetect_FailedScreen() throws Exception {
        UserProjectParticipationData data = new UserProjectParticipationData();
        data.setResolutionDate(new Date());
        data.setSubmissionPhaseStatusId(3);
        data.setSubmissionStatusId(2L);

        Date d = new Date();
        data.setScreeningPhaseEnd(d);

        instance.detect(data);
        assertEquals("The resolutionDate should be submission phase end.", d,
            data.getResolutionDate());
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testDetect_FailedReview() throws Exception {
        UserProjectParticipationData data = new UserProjectParticipationData();
        data.setResolutionDate(new Date());
        data.setSubmissionPhaseStatusId(3);
        data.setSubmissionStatusId(3L);

        Date d = new Date();
        data.setAppealsResponsePhaseEnd(d);

        instance.detect(data);
        assertEquals("The resolutionDate should be appeal response phase end.",
            d, data.getResolutionDate());
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testDetect_PassReview() throws Exception {
        UserProjectParticipationData data = new UserProjectParticipationData();
        data.setResolutionDate(new Date());
        data.setSubmissionPhaseStatusId(3);
        data.setSubmissionStatusId(1L);
        data.setPassedReview(true);

        Date d = new Date();
        data.setAppealsResponsePhaseEnd(d);

        instance.detect(data);
        assertEquals("The resolutionDate should be appeal response phase end.",
            d, data.getResolutionDate());
    }

    /**
     * Accuracy test for <code>detect()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testDetect_Other() throws Exception {
        UserProjectParticipationData data = new UserProjectParticipationData();
        data.setResolutionDate(new Date());
        data.setSubmissionPhaseStatusId(3);
        data.setSubmissionStatusId(1L);

        Date d = new Date();
        data.setAppealsResponsePhaseEnd(d);

        instance.detect(data);
        assertNull("The resolutionDate should be null", data.getResolutionDate());
    }
}
