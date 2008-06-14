/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.stresstests;

import com.topcoder.util.dependency.report.DependencyReportGenerator;
import com.topcoder.util.dependency.report.impl.XmlDependencyReportGenerator;

/**
 * This is a stress test suite for XmlDependencyReportGenerator.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class XmlDependencyReportGeneratorTests extends BaseStressTests {
    /**
     * Simply return XmlDependencyReportGenerator.class.
     * 
     * @return XmlDependencyReportGenerator.class
     */
    @Override
    protected Class<? extends DependencyReportGenerator> getGeneratorClass() {
        return XmlDependencyReportGenerator.class;
    }
}
