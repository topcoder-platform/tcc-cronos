/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.accuracytests;

import com.cronos.timetracker.report.StyleConstant;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;


/**
 * Accuracy test for <code>StyleConstant</code>.
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class AccuracyTestStyleConstant extends TestCase {
    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestStyleConstant.class);
    }

    /**
     * Tests getName().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetName() throws Exception {
        assertTrue("TABLE_STYLE is not valid.", StyleConstant.TABLE_STYLE.getName().equals("TABLE_STYLE"));
        assertTrue("TH_STYLE is not valid.", StyleConstant.TH_STYLE.getName().equals("TH_STYLE"));
        assertTrue("TD_STYLE is not valid.", StyleConstant.TD_STYLE.getName().equals("TD_STYLE"));
        assertTrue("TR_STYLE is not valid.", StyleConstant.TR_STYLE.getName().equals("TR_STYLE"));
    }

    /**
     * Tests toString().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testToString() throws Exception {
        assertTrue("TABLE_STYLE is not valid.", StyleConstant.TABLE_STYLE.toString().equals("TABLE_STYLE"));
        assertTrue("TH_STYLE is not valid.", StyleConstant.TH_STYLE.toString().equals("TH_STYLE"));
        assertTrue("TD_STYLE is not valid.", StyleConstant.TD_STYLE.toString().equals("TD_STYLE"));
        assertTrue("TR_STYLE is not valid.", StyleConstant.TR_STYLE.toString().equals("TR_STYLE"));
    }

    /**
     * Tests getStyleConstants().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetStyleConstants() throws Exception {
        List styles = StyleConstant.getStyleConstants();

        assertTrue("There should be 4 members.", styles.size() == 4);

        assertTrue("TABLE_STYLE is not valid.", styles.contains(StyleConstant.TABLE_STYLE));
        assertTrue("TH_STYLE is not valid.", styles.contains(StyleConstant.TH_STYLE));
        assertTrue("TR_STYLE is not valid.", styles.contains(StyleConstant.TR_STYLE));
        assertTrue("TD_STYLE is not valid.", styles.contains(StyleConstant.TD_STYLE));
    }
}
