/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity.stresstests;

import com.topcoder.service.digitalrun.entity.idgenerator.DigitalRunEntityIDGenerator;

import junit.framework.TestCase;

import java.util.Properties;
import java.util.Random;


/**
 * The stress test for this component. this component are just some entities, and the entities are
 * very simple, so the stress test for the entities are not provided. We just test the
 * DigitalRunEntityIDGenerator for stress.
 *
 * @author KLW
 * @version 1.0
 */
public class DigitalRunEntitiesStressTests extends TestCase {
    /**
     * The Generator names for test.
     */
    private static final String[] GeneratorNames = {
            "DigitalRunPointsStatus_IDGenerator", "DigitalRunPointsOperation_IDGenerator",
            "DigitalRunPointsReferenceType_IDGenerator", "DigitalRunPointsType_IDGenerator",
            "TrackContestType_IDGenerator", "TrackStatus_IDGenerator", "TrackType_IDGenerator",
            "PointsCalculator_IDGenerator", "TrackContestResultCalculator_IDGenerator", "Track_IDGenerator",
            "TrackContest_IDGenerator", "DigitalRunPoints_IDGenerator"
        };
    
    /**
     * The Random instance for test.
     */
    private static Random r = new Random();

    /**
     * The DigitalRunEntityIDGenerator instance for test.
     */
    private DigitalRunEntityIDGenerator digitalRunEntityIDGenerator = new DigitalRunEntityIDGenerator();

    /**
     * The stress test for the entities.
     * @exception Exception all exception throw to JUnit.
     */
    public void testDigitalRunEntities() throws Exception {
        long start = System.currentTimeMillis();
        Runnable run = new Runnable() {
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        Properties properties = new Properties();
                        String generatorName = GeneratorNames[r.nextInt(GeneratorNames.length)];
                        properties.put("id_generator_name", generatorName);

                        Long id = null;

                        synchronized (digitalRunEntityIDGenerator) {
                            digitalRunEntityIDGenerator.configure(null, properties, null);
                            id = (Long) digitalRunEntityIDGenerator.generate(null, null);
                        }

                        System.out.println(generatorName + " generated id : " + id);
                    }
                }
            };

        ThreadGroup g = new ThreadGroup("test");

        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(g, run);
            thread.start();
        }

        while (g.activeCount() > 0) {
            Thread.sleep(20);
        }

        System.out.println("run generate it for 2000 times, cost : " + (System.currentTimeMillis() - start) +
            " millis.");
    }
}
