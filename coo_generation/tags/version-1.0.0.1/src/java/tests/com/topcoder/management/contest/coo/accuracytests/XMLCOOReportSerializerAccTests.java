/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.accuracytests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.impl.DefaultCOOReportGenerator;
import com.topcoder.management.contest.coo.serializer.XMLCOOReportSerializer;


/**
 * <p>Accuracy test case for {@link XMLCOOReportSerializer} class.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class XMLCOOReportSerializerAccTests extends BaseTestCase {
    /** Result file. */
    private static final String OUTPUT_FILE = BaseTestCase.ACCURACY
        + "output.xml";

    /** Target. */
    private XMLCOOReportSerializer instance;

    /** Report generator. */
    private DefaultCOOReportGenerator generator;

    /**
     * <p>set up test environment.</p>
     *
     * @throws Exception to JUNIT.
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigurationObject config = new DefaultConfigurationObject("default");
        config.setPropertyValue("templateFilename", BaseTestCase.ACCURACY + "template.xml");
        generator = new DefaultCOOReportGenerator(BaseTestCase
                .getConfigurationObject(BaseTestCase.ACCURACY + "Config.xml"));
        instance = new XMLCOOReportSerializer(config);
    }

    /**
     * <p>tear down test environment.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>Accuracy test case for
     * <code>serializeCOOReportToByteArray()</code> method.</p>
     *  <p>Check the result by eye.</p>
     *
     * @throws Exception to JUNIT.
     */
    public void testSerializeCOOReport() throws Exception {
        instance.serializeCOOReportToFile(generator.generateCOOReport(13), OUTPUT_FILE);
    }
}
