/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.calculators.BaseUserReliabilityCalculator;
import com.topcoder.reliability.impl.calculators.WeightedUserReliabilityCalculator;


/**
 * Accuracy test for <code>WeightedUserReliabilityCalculator</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class WeightedUserReliabilityCalculatorAccuracy extends TestCase {
    /**
     * The <code>WeightedUserReliabilityCalculator</code> instance for testing.
     */
    private WeightedUserReliabilityCalculator instance = new WeightedUserReliabilityCalculator();

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

        String[] weights = new String[] { "1", "1", "1", "1" };

        config.setPropertyValues("weights", weights);

        instance.configure(config);
    }

    /**
     * Accuracy test for constructor.
     */
    public void testCtor() {
        assertNotNull("The result should match.", instance);
        assertTrue("The result should match.",
            instance instanceof BaseUserReliabilityCalculator);
    }

    /**
     * Accuracy test for <code>configure()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testConfigure() throws Exception {
        ConfigurationObject configObj = new DefaultConfigurationObject(
                "myconfig");
        Object[] weights = new Object[4];
        weights[0] = "1";
        weights[1] = "1";
        weights[2] = "1";
        weights[3] = "1";

        configObj.setPropertyValues("weights", weights);

        instance = new WeightedUserReliabilityCalculator();
        instance.configure(configObj);

        Field field = BaseUserReliabilityCalculator.class.getDeclaredField(
                "log");
        field.setAccessible(true);

        Field lenField = instance.getClass().getDeclaredField("weights");
        lenField.setAccessible(true);

        assertNull("The log isn't set.", field.get(instance));
        assertNotNull("The length should match.", lenField.get(instance));

        double[] result = (double[]) lenField.get(instance);
        assertEquals("the length should match.", 4, result.length);

        configObj.setPropertyValue("loggerName", "mylog");
        instance = new WeightedUserReliabilityCalculator();
        instance.configure(configObj);

        assertNotNull("the log should be set.", field.get(instance));
    }

    /**
     * Accuracy test for <code>calculate()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testCalculate() throws Exception {
        List<UserProjectParticipationData> list = new ArrayList<UserProjectParticipationData>();

        UserProjectParticipationData data1 = new UserProjectParticipationData();
        data1.setUserRegistrationDate(new Date());
        data1.setResolutionDate(new Date());
        data1.setPassedReview(false);

        UserProjectParticipationData data2 = new UserProjectParticipationData();
        data2.setUserRegistrationDate(new Date());
        data2.setResolutionDate(new Date());
        data2.setPassedReview(false);

        list.add(data1);
        list.add(data2);

        assertTrue("The result should be empty.",
            instance.calculate(list).isEmpty());
    }

    /**
     * Accuracy test for <code>calculate()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testCalculate2() throws Exception {
        List<UserProjectParticipationData> list = new ArrayList<UserProjectParticipationData>();

        UserProjectParticipationData data1 = new UserProjectParticipationData();
        data1.setUserRegistrationDate(new Date());
        data1.setResolutionDate(new Date());
        data1.setPassedReview(false);

        UserProjectParticipationData data2 = new UserProjectParticipationData();
        data2.setUserRegistrationDate(new Date());
        data2.setResolutionDate(new Date());
        data2.setPassedReview(false);

        list.add(data1);
        list.add(data2);

        Date date = new Date();
        UserProjectParticipationData data3 = new UserProjectParticipationData();
        data3.setUserRegistrationDate(new Date());
        data3.setResolutionDate(date);
        data3.setPassedReview(true);
        data3.setProjectId(1);
        data3.setUserId(2);

        list.add(data3);

        List<UserProjectReliabilityData> result = instance.calculate(list);
        assertEquals("The result only contains 1 project.", 1, result.size());
        assertEquals("The project id should match.", 1,
            result.get(0).getProjectId());
        assertEquals("The project id should match.", 2,
            result.get(0).getUserId());
        assertEquals("The project id should match.", true,
            result.get(0).isReliable());
        assertEquals("The date should match.", date,
            result.get(0).getResolutionDate());
        assertNull("The value should match.",
            result.get(0).getReliabilityBeforeResolution());
        assertNull("The value should match.",
            result.get(0).getReliabilityOnRegistration());
        assertEquals("The value should match.", 1.0,
            result.get(0).getReliabilityAfterResolution());
    }

    /**
     * Accuracy test for <code>calculate()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testCalculate3() throws Exception {
        List<UserProjectParticipationData> list = new ArrayList<UserProjectParticipationData>();

        UserProjectParticipationData data1 = new UserProjectParticipationData();
        data1.setUserRegistrationDate(new Date());
        data1.setResolutionDate(new Date());
        data1.setPassedReview(false);

        UserProjectParticipationData data2 = new UserProjectParticipationData();
        data2.setUserRegistrationDate(new Date());
        data2.setResolutionDate(new Date());
        data2.setPassedReview(false);

        list.add(data1);
        list.add(data2);

        Date date3 = new Date();
        long datetime = date3.getTime();

        UserProjectParticipationData data3 = new UserProjectParticipationData();
        data3.setUserRegistrationDate(new Date(datetime - 1500));
        data3.setResolutionDate(date3);
        data3.setPassedReview(true);
        data3.setProjectId(1);
        data3.setUserId(2);
        list.add(data3);

        UserProjectParticipationData data4 = new UserProjectParticipationData();
        data4.setUserRegistrationDate(new Date(datetime - 1000));
        data4.setResolutionDate(new Date(datetime + 1000));
        data4.setPassedReview(true);
        data4.setProjectId(2);
        data4.setUserId(2);
        list.add(data4);

        UserProjectParticipationData data5 = new UserProjectParticipationData();
        data5.setUserRegistrationDate(new Date(datetime + 500));
        data5.setResolutionDate(new Date(datetime + 2000));
        data5.setPassedReview(false);
        data5.setProjectId(3);
        data5.setUserId(2);
        list.add(data5);

        UserProjectParticipationData data6 = new UserProjectParticipationData();
        data6.setUserRegistrationDate(new Date(datetime + 3000));
        data6.setResolutionDate(new Date(datetime + 4000));
        data6.setPassedReview(true);
        data6.setProjectId(4);
        data6.setUserId(2);
        list.add(data6);

        UserProjectParticipationData data7 = new UserProjectParticipationData();
        data7.setUserRegistrationDate(new Date(datetime + 3500));
        data7.setResolutionDate(new Date(datetime + 3800));
        data7.setPassedReview(true);
        data7.setProjectId(5);
        data7.setUserId(2);
        list.add(data7);

        List<UserProjectReliabilityData> result = instance.calculate(list);
        assertEquals("The result only contains 5 project.", 5, result.size());
        assertNull("the value should match.",
            result.get(0).getReliabilityBeforeResolution());
        assertNull("the value should match.",
            result.get(0).getReliabilityOnRegistration());
        assertEquals("the value should match.", 1.0,
            result.get(0).getReliabilityAfterResolution());

        assertEquals("The reliability should match.", 1.0,
            result.get(1).getReliabilityBeforeResolution());
        assertEquals("The reliability should match.", 1.0,
            result.get(2).getReliabilityBeforeResolution());
        assertEquals("The reliability should match.", 2.0 / 3,
            result.get(3).getReliabilityBeforeResolution());
        assertEquals("The reliability should match.", 0.75,
            result.get(4).getReliabilityBeforeResolution());

        assertNull("The reliability should match.",
            result.get(1).getReliabilityOnRegistration());
        assertEquals("The reliability should match.", 1.0,
            result.get(2).getReliabilityOnRegistration());
        assertEquals("The reliability should match.", 2.0 / 3,
            result.get(3).getReliabilityOnRegistration());
        assertEquals("The reliability should match.", 2.0 / 3,
            result.get(4).getReliabilityOnRegistration());
    }

    /**
     * Accuracy test for <code>calculate()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testCalculate4() throws Exception {
        config = new DefaultConfigurationObject("myconfig");
        config.setPropertyValue("loggerName", "mylog");

        String[] weights = new String[] { "0.5", "1", "1.5", "2.0" };
        config.setPropertyValues("weights", weights);

        instance = new WeightedUserReliabilityCalculator();
        instance.configure(config);

        List<UserProjectParticipationData> list = new ArrayList<UserProjectParticipationData>();

        UserProjectParticipationData data1 = new UserProjectParticipationData();
        data1.setUserRegistrationDate(new Date());
        data1.setResolutionDate(new Date());
        data1.setPassedReview(false);

        UserProjectParticipationData data2 = new UserProjectParticipationData();
        data2.setUserRegistrationDate(new Date());
        data2.setResolutionDate(new Date());
        data2.setPassedReview(false);

        list.add(data1);
        list.add(data2);

        Date date3 = new Date();
        long datetime = date3.getTime();

        UserProjectParticipationData data3 = new UserProjectParticipationData();
        data3.setUserRegistrationDate(new Date(datetime - 1500));
        data3.setResolutionDate(date3);
        data3.setPassedReview(true);
        data3.setProjectId(1);
        data3.setUserId(2);
        list.add(data3);

        UserProjectParticipationData data4 = new UserProjectParticipationData();
        data4.setUserRegistrationDate(new Date(datetime - 1000));
        data4.setResolutionDate(new Date(datetime + 1000));
        data4.setPassedReview(true);
        data4.setProjectId(2);
        data4.setUserId(2);
        list.add(data4);

        UserProjectParticipationData data5 = new UserProjectParticipationData();
        data5.setUserRegistrationDate(new Date(datetime + 500));
        data5.setResolutionDate(new Date(datetime + 2000));
        data5.setPassedReview(false);
        data5.setProjectId(3);
        data5.setUserId(2);
        list.add(data5);

        UserProjectParticipationData data6 = new UserProjectParticipationData();
        data6.setUserRegistrationDate(new Date(datetime + 3000));
        data6.setResolutionDate(new Date(datetime + 4000));
        data6.setPassedReview(false);
        data6.setProjectId(4);
        data6.setUserId(2);
        list.add(data6);

        UserProjectParticipationData data7 = new UserProjectParticipationData();
        data7.setUserRegistrationDate(new Date(datetime + 3500));
        data7.setResolutionDate(new Date(datetime + 3800));
        data7.setPassedReview(true);
        data7.setProjectId(5);
        data7.setUserId(2);
        list.add(data7);

        List<UserProjectReliabilityData> result = instance.calculate(list);
        assertEquals("The result only contains 5 project.", 5, result.size());
        assertNull("the value should match.",
            result.get(0).getReliabilityBeforeResolution());
        assertNull("the value should match.",
            result.get(0).getReliabilityOnRegistration());
        assertEquals("the value should match.", 1.0,
            result.get(0).getReliabilityAfterResolution());

        assertEquals("The reliability should match.", 1.0,
            result.get(1).getReliabilityBeforeResolution());
        assertEquals("The reliability should match.", 1.0,
            result.get(2).getReliabilityBeforeResolution());
        assertEquals("The reliability should match.", 2.5 / 4.5,
            result.get(3).getReliabilityBeforeResolution());
        assertEquals("The reliability should match.", 1.5 / 5.0,
            result.get(4).getReliabilityBeforeResolution());
        assertEquals("The reliability should match.", 2.5 / 5.0,
            result.get(4).getReliabilityAfterResolution());

        assertNull("The reliability should match.",
            result.get(1).getReliabilityOnRegistration());
        assertEquals("The reliability should match.", 1.0,
            result.get(2).getReliabilityOnRegistration());
        assertEquals("The reliability should match.", 2.5 / 4.5,
            result.get(3).getReliabilityOnRegistration());
        assertEquals("The reliability should match.", 2.5 / 4.5,
            result.get(4).getReliabilityOnRegistration());
    }
}
