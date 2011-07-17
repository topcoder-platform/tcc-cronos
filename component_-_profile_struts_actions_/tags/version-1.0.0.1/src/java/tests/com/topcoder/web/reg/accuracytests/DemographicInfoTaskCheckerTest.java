/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.model.DemographicAnswer;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.profile.DemographicInfoTaskChecker;
import com.topcoder.web.reg.actions.profile.ProfileTaskReport;

/**
 * <p>
 * Accuracy tests for the {@link DemographicInfoTaskChecker}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class DemographicInfoTaskCheckerTest {
    /** Represents the instance used to test again. */
    private DemographicInfoTaskChecker testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new DemographicInfoTaskChecker();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link DemographicInfoTaskChecker#DemographicInfoTaskChecker()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testDemographicInfoTaskChecker() throws Exception {
        new DemographicInfoTaskChecker();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link DemographicInfoTaskChecker#getTaskReport()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getTaskReport() throws Exception {
        User user = new User();
        Set<DemographicResponse> demographicResponses = new HashSet<DemographicResponse>();
        DemographicResponse response = new DemographicResponse();
        DemographicAnswer answer = new DemographicAnswer();
        answer.setText("txt");
        answer.setId(2L);
        response.setAnswer(answer);
        DemographicQuestion question = new DemographicQuestion();
        question.setText("txt");
        question.setId(1L);
        response.setQuestion(question);

        demographicResponses.add(response);
        user.setDemographicResponses(demographicResponses);
        testInstance.setTaskName("taskName");
        ProfileTaskReport report = testInstance.getTaskReport(user);
        Assert.assertEquals("taskName", "taskName", report.getTaskName());
        Assert.assertEquals("completed", false, report.getCompleted());
    }
}