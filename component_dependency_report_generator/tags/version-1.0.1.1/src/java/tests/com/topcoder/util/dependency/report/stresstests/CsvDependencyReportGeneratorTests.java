/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.stresstests;

import com.topcoder.util.dependency.report.DependencyReportGenerator;
import com.topcoder.util.dependency.report.impl.CsvDependencyReportGenerator;

/**
 * This is a stress test suite for CsvDependencyReportGenerator.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CsvDependencyReportGeneratorTests extends BaseStressTests {
    /**
     * Simply return CsvDependencyReportGenerator.class.
     * 
     * @return CsvDependencyReportGenerator.class
     */
    @Override
    protected Class<? extends DependencyReportGenerator> getGeneratorClass() {
        return CsvDependencyReportGenerator.class;
    }
}
