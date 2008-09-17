/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.stresstests;

import com.topcoder.util.dependency.report.DependencyReportGenerator;
import com.topcoder.util.dependency.report.impl.HtmlDependencyReportGenerator;

/**
 * This is a stress test suite for HtmlDependencyReportGenerator.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HtmlDependencyReportGeneratorTests extends BaseStressTests {
    /**
     * Simply return HtmlDependencyReportGenerator.class.
     * 
     * @return HtmlDependencyReportGenerator.class
     */
    @Override
    protected Class<? extends DependencyReportGenerator> getGeneratorClass() {
        return HtmlDependencyReportGenerator.class;
    }
}
