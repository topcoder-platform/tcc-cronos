/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.accuracytests.searchflters;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.project.searchfilters.CompareOperation;
import com.topcoder.timetracker.project.searchfilters.ValueFilter;
import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * The class <code>ValueFilterAccuracytest</code> contains tests for the class {@link <code>ValueFilter</code>}.
 * @author FireIce
 * @version 1.1
 */
public class ValueFilterAccuracytest extends TestCase {

    /**
     * accuracy test for constructor.
     */
    public void testCtorAccuracy() {
        String fieldName = "test";
        for (Iterator iter = Enum.getEnumList(CompareOperation.class).iterator(); iter.hasNext();) {
            CompareOperation op = (CompareOperation) iter.next();

            // String type
            Object value = "mock";
            ValueFilter valueFilter = new ValueFilter(op, fieldName, value);
            assertSame("opearation field incorrrect", op, valueFilter.getOperation());
            assertEquals("fieldName field incorrect", fieldName, valueFilter.getFieldName());
            assertEquals("value object incorrect", value, valueFilter.getValue());

            // Integer type
            value = new Integer(1);
            valueFilter = new ValueFilter(op, fieldName, value);
            assertSame("opearation field incorrrect", op, valueFilter.getOperation());
            assertEquals("fieldName field incorrect", fieldName, valueFilter.getFieldName());
            assertSame("value object incorrect", value, valueFilter.getValue());

            // Long type
            value = new Long(1);
            valueFilter = new ValueFilter(op, fieldName, value);
            assertSame("opearation field incorrrect", op, valueFilter.getOperation());
            assertEquals("fieldName field incorrect", fieldName, valueFilter.getFieldName());
            assertSame("value object incorrect", value, valueFilter.getValue());

            // Float type
            value = new Float(1);
            valueFilter = new ValueFilter(op, fieldName, value);
            assertSame("opearation field incorrrect", op, valueFilter.getOperation());
            assertEquals("fieldName field incorrect", fieldName, valueFilter.getFieldName());
            assertSame("value object incorrect", value, valueFilter.getValue());

            // Double type
            value = new Double(1);
            valueFilter = new ValueFilter(op, fieldName, value);
            assertSame("opearation field incorrrect", op, valueFilter.getOperation());
            assertEquals("fieldName field incorrect", fieldName, valueFilter.getFieldName());
            assertSame("value object incorrect", value, valueFilter.getValue());

            // Short type
            value = new Short("2");
            valueFilter = new ValueFilter(op, fieldName, value);
            assertSame("opearation field incorrrect", op, valueFilter.getOperation());
            assertEquals("fieldName field incorrect", fieldName, valueFilter.getFieldName());
            assertSame("value object incorrect", value, valueFilter.getValue());

            // Byte type
            value = new Byte((byte) 1);
            valueFilter = new ValueFilter(op, fieldName, value);
            assertSame("opearation field incorrrect", op, valueFilter.getOperation());
            assertEquals("fieldName field incorrect", fieldName, valueFilter.getFieldName());
            assertSame("value object incorrect", value, valueFilter.getValue());

            // Character type
            value = new Character('c');
            valueFilter = new ValueFilter(op, fieldName, value);
            assertSame("opearation field incorrrect", op, valueFilter.getOperation());
            assertEquals("fieldName field incorrect", fieldName, valueFilter.getFieldName());
            assertSame("value object incorrect", value, valueFilter.getValue());

            // Boolean type
            value = Boolean.TRUE;
            valueFilter = new ValueFilter(op, fieldName, value);
            assertSame("opearation field incorrrect", op, valueFilter.getOperation());
            assertEquals("fieldName field incorrect", fieldName, valueFilter.getFieldName());
            assertSame("value object incorrect", value, valueFilter.getValue());

            // BigDecimal type
            value = new BigDecimal(12.324);
            valueFilter = new ValueFilter(op, fieldName, value);
            assertSame("opearation field incorrrect", op, valueFilter.getOperation());
            assertEquals("fieldName field incorrect", fieldName, valueFilter.getFieldName());
            assertSame("value object incorrect", value, valueFilter.getValue());

            // java.util.Date type
            value = new Date();
            valueFilter = new ValueFilter(op, fieldName, value);
            assertSame("opearation field incorrrect", op, valueFilter.getOperation());
            assertEquals("fieldName field incorrect", fieldName, valueFilter.getFieldName());
            assertSame("value object incorrect", value, valueFilter.getValue());
        }
    }

    /**
     * Aggragates all tests in this class.
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ValueFilterAccuracytest.class);
    }
}
