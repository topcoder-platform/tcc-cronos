/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.accuracy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.entities.ProjectContestCPConfig;
import com.topcoder.utilities.cp.services.ProjectContestCPConfigService;
import com.topcoder.utilities.cp.services.impl.ProjectContestCPConfigServiceImpl;

/**
 * <p>
 * Unit tests for {@link ProjectContestCPConfigServiceImpl} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectContestCPConfigServiceImplUnitTests extends BaseAccuracyUnitTests {
    /**
     * <p>
     * Represents the <code>ProjectContestCPConfigServiceImpl</code> instance used in tests.
     * </p>
     */
    private ProjectContestCPConfigService instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = (ProjectContestCPConfigService) ACCURAY_APP_CONTEXT.getBean("projectContestCPConfigService");
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectContestCPConfigServiceImpl#ProjectContestCPConfigServiceImpl()}.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull(instance);
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectContestCPConfigServiceImpl#create(ProjectContestCPConfig)}.
     * </p>
     */
    @Test
    public void testCreateAndGet() throws Exception {
        ProjectContestCPConfig config = new ProjectContestCPConfig();
        config.setContestId(4);
        config.setCpRate(1.5);
        config.setTotalOriginalPayment(1000);
        instance.create(config);

        ProjectContestCPConfig newConfig = instance.get(4);
        assertEquals(4, newConfig.getContestId());
        assertTrue(1.5f == newConfig.getCpRate());
        assertTrue(1000f == newConfig.getTotalOriginalPayment());
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectContestCPConfigServiceImpl#update(ProjectContestCPConfig)}.
     * </p>
     */
    @Test
    public void testUpdate() throws Exception {
        ProjectContestCPConfig config = new ProjectContestCPConfig();
        config.setContestId(4);
        config.setCpRate(1.5);
        config.setTotalOriginalPayment(1000);
        instance.create(config);

        config.setCpRate(2.5);
        config.setTotalOriginalPayment(500);
        instance.update(config);

        ProjectContestCPConfig newConfig = instance.get(4);
        assertEquals(4, newConfig.getContestId());
        assertTrue(2.5f == newConfig.getCpRate());
        assertTrue(500f == newConfig.getTotalOriginalPayment());
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectContestCPConfigServiceImpl#delete(long)}.
     * </p>
     */
    @Test
    public void testDelete() throws Exception {
        assertNotNull(instance.get(1));
        instance.delete(1);
        assertNull(instance.get(1));
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectContestCPConfigServiceImpl#get(long)}.
     * </p>
     */
    @Test
    public void testGet() throws Exception {
        assertNull(instance.get(5));
    }
}
