/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.report.BasicColumnDecorator;
import com.topcoder.timetracker.report.Column;
import com.topcoder.timetracker.report.ColumnDecorator;
import com.topcoder.timetracker.report.EqualityFilter;
import com.topcoder.timetracker.report.FilterCategory;
import com.topcoder.timetracker.report.RangeFilter;
import com.topcoder.timetracker.report.ReportCategory;
import com.topcoder.timetracker.report.ReportConfiguration;
import com.topcoder.timetracker.report.ReportType;
import com.topcoder.timetracker.report.StyleConstant;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Accuracy test for <code>ReportConfiguration</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestReportConfiguration extends TestCase {
    /** The namespace used for test. */
    private static final String NAMESPACE = "TEST NAMESPACE";

    /** The value of table style used for test. */
    private static final String TABLE_STYLE = "TEST TABLE STYLE";

    /** The value of tr style used for test. */
    private static final String TR_STYLE = "TEST TR STYLE";

    /** The category used for test. */
    private static final ReportCategory CATEGORY = ReportCategory.TIME;

    /** The type used for test. */
    private static final ReportType TYPE = ReportType.CLIENT;

    /** The instance of EqualityFilter used for test. */
    private static final EqualityFilter EQUALITY_FILTER = new EqualityFilter(Column.CLIENT, FilterCategory.CLIENT);

    /** The instance of RangeFilter used for test. */
    private static final RangeFilter RANGE_FILTER = new RangeFilter(Column.DATE, FilterCategory.DATE);

    /** The implement of ColumnDecorator used for test. */
    private static final ColumnDecorator COLUMN_DECORATOR_1 = new BasicColumnDecorator("TEST NAME 1", "TEST LABEL 1");

    /** The implement of ColumnDecorator used for test. */
    private static final ColumnDecorator COLUMN_DECORATOR_2 = new BasicColumnDecorator("TEST NAME 2", "TEST LABEL 2");

    /** The list of the filters used for test. */
    private List filters;

    /** The list of the column decorators used for test. */
    private List columnDecorators;

    /** The Map of the styles used for test. */
    private Map styles;

    /** The instance of ReportConfiguration used for test. */
    private ReportConfiguration config = null;

    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestReportConfiguration.class);
    }

    /**
     * See javadoc for junit.framework.TestCase#setUp().
     *
     * @throws Exception exception that occurs during the setup process.
     */
    protected void setUp() throws Exception {
        filters = new ArrayList();
        filters.add(EQUALITY_FILTER);
        filters.add(RANGE_FILTER);

        columnDecorators = new ArrayList();
        columnDecorators.add(COLUMN_DECORATOR_1);
        columnDecorators.add(COLUMN_DECORATOR_2);

        styles = new HashMap();
        styles.put(StyleConstant.TABLE_STYLE, TABLE_STYLE);
        styles.put(StyleConstant.TR_STYLE, TR_STYLE);

        config = new ReportConfiguration(CATEGORY, TYPE, NAMESPACE);
    }

    /**
     * See javadoc for junit.framework.TestCase#tearDown().
     *
     * @throws Exception exception that occurs during the tear down process.
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests Creating ReportConfiguration with legal parameters.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testConstructor() throws Exception {
        assertTrue("The category of the config is not valid.", config.getCategory().equals(CATEGORY));
        assertTrue("The namespace of the config is not valid.", config.getNamespace().equals(NAMESPACE));
        assertTrue("The type of the config is not valid.", config.getType().equals(TYPE));
    }

    /**
     * Tests setFilters() and getFilters().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetAndGetFilters() throws Exception {
        config.setFilters(filters);

        List filtersList = config.getFilters();

        assertTrue("The filters list is not valid.", filtersList.size() == 2);
        assertTrue("The first filter is not valid.", filtersList.get(0).equals(EQUALITY_FILTER));
        assertTrue("The second filter is not valid.", filtersList.get(1).equals(RANGE_FILTER));
    }

    /**
     * Tests setColumnDecorators() and getColumnDecorators().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetAndGetColumnDecorators() throws Exception {
        config.setColumnDecorators(columnDecorators);

        List columnDecoratorsList = config.getColumnDecorators();

        assertTrue("The columns list is not valid.", columnDecoratorsList.size() == 2);
        assertTrue("The first decorator is not valid.", columnDecoratorsList.get(0).equals(COLUMN_DECORATOR_1));
        assertTrue("The second decorator is not valid.", columnDecoratorsList.get(1).equals(COLUMN_DECORATOR_2));
    }

    /**
     * Tests setStyles() and getStyles().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetAndGetStyles() throws Exception {
        config.setStyles(styles);

        Map stylesMap = config.getStyles();

        assertTrue("The styles map is not valid.", stylesMap.size() == 2);
        assertTrue("The table style is not valid.", stylesMap.get(StyleConstant.TABLE_STYLE).equals(TABLE_STYLE));
        assertTrue("The tr style is not valid.", stylesMap.get(StyleConstant.TR_STYLE).equals(TR_STYLE));
    }

    /**
     * Tests getCategory.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetCategory() throws Exception {
        assertTrue("The category of config is not valid.", config.getCategory().equals(CATEGORY));
    }

    /**
     * Tests getType.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetType() throws Exception {
        assertTrue("The type of the config is not valid.", config.getType().equals(TYPE));
    }

    /**
     * Tests getNamespace.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetNamespace() throws Exception {
        assertTrue("The namespace of config is not valid.", config.getNamespace().equals(NAMESPACE));
    }
}
