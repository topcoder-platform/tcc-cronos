/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.accuracytests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.impl.DefaultCOOReportGenerator;
import com.topcoder.management.contest.coo.serializer.PDFCOOReportSerializer;

/**
 * <p>
 * Accuracy test case for {@link PDFCOOReportSerializer} class.
 * </p>
 * 
 * @author myxgyy
 * @version 1.0
 */
public class PDFCOOReportSerializerAccTests extends BaseTestCase {
    /** Result file. */
    private static final String OUTPUT_FILE = BaseTestCase.ACCURACY
            + "output.pdf";

    /**
     * Target.
     */
    private PDFCOOReportSerializer instance;

    /** Report generator. */
    private DefaultCOOReportGenerator generator;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        ConfigurationObject kk = new DefaultConfigurationObject("default");
        ConfigurationObject config = new DefaultConfigurationObject("default");
        config.setPropertyValue("templateFilename", BaseTestCase.ACCURACY
                + "WordTemplate.dot");
        kk.addChild(config);

        // ConfigurationObject config = new
        // DefaultConfigurationObject("default");
        // config.setPropertyValue("templateFilename",
        // "test_files/WordTemplate.dot");
        instance = new PDFCOOReportSerializer(kk);
        generator = new DefaultCOOReportGenerator(BaseTestCase
                .getConfigurationObject(BaseTestCase.ACCURACY + "Config.xml"));
    }

    /**
     * <p>
     * tear down test environment.
     * </p>
     * 
     * @throws Exception
     *             to JUNIT.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test for <code>serializeCOOReportToByteArray()</code> method.
     * </p>
     * <p>
     * Check the result by eye.
     * </p>
     * 
     * @throws Exception
     *             to JUNIT.
     */

    public void testSerializeCOOReport() throws Exception {
        instance.serializeCOOReportToFile(generator.generateCOOReport(1L),
                OUTPUT_FILE);
    }
}
