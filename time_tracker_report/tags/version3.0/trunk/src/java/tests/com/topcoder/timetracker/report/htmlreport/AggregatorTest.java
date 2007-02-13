/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.htmlreport;

import com.cronos.timetracker.report.Column;
import junit.framework.TestCase;

import java.math.BigDecimal;


/**
 * This class contains the unit tests for {@link HTMLRenderUtil.Aggregator}.
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class AggregatorTest extends TestCase {

    /**
     * This method tests {@link HTMLRenderUtil.Aggregator#Aggregator(Column)}  and {@link
     * HTMLRenderUtil.Aggregator#getColumn()}for correctness.
     */
    public void testCtorGetColumn() {
        final HTMLRenderUtil.Aggregator aggregator = new HTMLRenderUtil.Aggregator(Column.AMOUNT);
        assertEquals(Column.AMOUNT, aggregator.getColumn());
        assertEquals(new BigDecimal(0), aggregator.getCurrentValue());
    }

    /**
     * This method tests {@link HTMLRenderUtil.Aggregator#Aggregator(Column)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: given column is <tt>null</tt>
     */
    public void testCtorFailNullColumn() {
        try {
            new HTMLRenderUtil.Aggregator(null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link HTMLRenderUtil.Aggregator#add(java.math.BigDecimal)}  and {@link
     * HTMLRenderUtil.Aggregator#getCurrentValue()}for correctness.
     */
    public void testAddGetCurrentValue() {
        final HTMLRenderUtil.Aggregator aggregator = new HTMLRenderUtil.Aggregator(Column.AMOUNT);
        assertEquals(new BigDecimal(0), aggregator.getCurrentValue());
        aggregator.add(new BigDecimal("10"));
        assertEquals(new BigDecimal("10"), aggregator.getCurrentValue());
        aggregator.add(new BigDecimal("10.001"));
        assertEquals(new BigDecimal("20.001"), aggregator.getCurrentValue());
        aggregator.add(new BigDecimal("5.000000001"));
        assertEquals(new BigDecimal("25.001000001"), aggregator.getCurrentValue());
        aggregator.add(new BigDecimal("1"));
        assertEquals(new BigDecimal("26.001000001"), aggregator.getCurrentValue());

    }

}
