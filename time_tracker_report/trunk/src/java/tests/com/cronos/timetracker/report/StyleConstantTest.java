/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report;

import junit.framework.TestCase;

import java.util.List;


/**
 * This class contains the unit tests for {@link StyleConstant}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StyleConstantTest extends TestCase {

    /**
     * This method tests {@link StyleConstant#getName()} for correctness.
     */
    public void testGetName() throws Exception {
        assertEquals("TABLE_STYLE", StyleConstant.TABLE_STYLE.getName());
        assertEquals("TD_STYLE", StyleConstant.TD_STYLE.getName());
        assertEquals("TH_STYLE", StyleConstant.TH_STYLE.getName());
        assertEquals("TR_STYLE", StyleConstant.TR_STYLE.getName());
    }

    /**
     * This method tests {@link StyleConstant#toString()} for correctness.
     */
    public void testToString() {
        assertEquals("TABLE_STYLE", StyleConstant.TABLE_STYLE.toString());
        assertEquals("TD_STYLE", StyleConstant.TD_STYLE.toString());
        assertEquals("TH_STYLE", StyleConstant.TH_STYLE.toString());
        assertEquals("TR_STYLE", StyleConstant.TR_STYLE.toString());
    }

    /**
     * This method tests {@link com.cronos.timetracker.report.StyleConstant#getStyleConstants()}.
     */
    public void testGetStyleConstants() {
        final List styleConstants = StyleConstant.getStyleConstants();
        assertTrue(styleConstants.contains(StyleConstant.TABLE_STYLE));
        assertTrue(styleConstants.contains(StyleConstant.TD_STYLE));
        assertTrue(styleConstants.contains(StyleConstant.TH_STYLE));
        assertTrue(styleConstants.contains(StyleConstant.TR_STYLE));
        assertEquals(4, styleConstants.size());
        try {
            styleConstants.add(new Object());
            fail("should throw");
        } catch (UnsupportedOperationException expected) {
            //expected
        }
    }
}
