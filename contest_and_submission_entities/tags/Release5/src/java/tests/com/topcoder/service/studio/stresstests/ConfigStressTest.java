/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestProperty;

/**
 * <p>
 * Stress test for the class <code>Config</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class ConfigStressTest extends AbstractStressTest {
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
     * Stress test for the class Config using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConfigPersistence() throws Exception {
        ContestConfig[] configs = new ContestConfig[10];
        for (int i = 0; i < configs.length; i++) {
            configs[i] = new ContestConfig();
            configs[i].setContest(createContest(i));
            ContestProperty property = new ContestProperty();
            property.setDescription("description");
            configs[i].setProperty(property);
            configs[i].setValue("value" + i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();
        for (int i = 0; i < configs.length; i++) {
            session.save(configs[i]);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for Config took " + time + "ms");
    }
}
