/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>ResultData</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1ResultDataUnitTest extends TestCase {
    /** Represents the <code>ResultData</code> instance used in tests. */
    private ResultData resultData;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        resultData = new ResultData();
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ResultData()</code>.
     * </p>
     */
    public void testExpenseEntryType_Accuracy() {
        assertNotNull("The instance should be created.", resultData);
    }

    /**
     * <p>
     * Tests accuracy of method <code>getBatchResults()</code>.
     * </p>
     */
    public void testGetBatchResults_Accuracy() {
        assertNull("The batchResults should be got properly.", resultData.getBatchResults());
    }

    /**
     * <p>
     * Tests method <code>setBatchResults(DataObject[])</code> when the batchResults is null. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testSetBatchResults_NullBatchResults() {
        try {
            resultData.setBatchResults(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of method <code>setBatchResults(DataObject[])</code>.
     * </p>
     */
    public void testSetBatchResults_Accuracy() {
        DataObject[] dataObjects = new DataObject[] {new TaskType(), new TimeStatus()};
        resultData.setBatchResults(dataObjects);
        assertEquals("The batchResults should be set properly.", dataObjects, resultData.getBatchResults());
    }

    /**
     * <p>
     * Tests accuracy of method <code>getExceptionList()</code>.
     * </p>
     */
    public void testGetExceptionList_Accuracy() {
        assertNull("The exceptionList should be got properly.", resultData.getExceptionList());
    }

    /**
     * <p>
     * Tests accuracy of method <code>setExceptionList(BatchOperationException[])</code>.
     * </p>
     */
    public void testSetExceptionList_Accuracy() {
        BatchOperationException[] exceptions = new BatchOperationException[] {new BatchOperationException("message")};
        resultData.setExceptionList(exceptions);
        assertEquals("The exceptionList should be set properly.", exceptions, resultData.getExceptionList());
    }

    /**
     * <p>
     * Tests method <code>setProperty(String, String)</code> when key is null. Expect IllegalArgumentException.
     * </p>
     */
    public void testSetProperty_NullKey() {
        try {
            resultData.setProperty(null, "value");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests method <code>setProperty(String, String)</code> when value is null. Expect IllegalArgumentException.
     * </p>
     */
    public void testSetProperty_NullValue() {
        try {
            resultData.setProperty("key", null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setProperty(String, String)</code>.
     * </p>
     */
    public void testSetProperty_Accuracy() {
        String key = "key";
        String value = "value";
        resultData.setProperty(key, value);
        assertEquals("The property should be set properly.", value, resultData.getProperty(key));
    }

    /**
     * <p>
     * Tests method <code>getProperty(String)</code> when value is null. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetProperty_NullKey() {
        try {
            resultData.getProperty(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getProperty(String)</code>.
     * </p>
     */
    public void testGetProperty_Accuracy() {
        String key = "key";
        String value = "value";
        resultData.setProperty(key, value);
        assertEquals("The property should be set properly.", value, resultData.getProperty(key));
        assertNull("The property should be set properly.", resultData.getProperty(value));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getFailedCount()</code>.
     * </p>
     */
    public void testGetFailedCount_Accuracy1() {
        assertEquals("The failed count should be got properly.", 0, resultData.getFailedCount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getFailedCount()</code>.
     * </p>
     */
    public void testGetFailedCount_Accuracy2() {
        BatchOperationException[] exceptions = new BatchOperationException[] {new BatchOperationException("message")};
        resultData.setExceptionList(exceptions);
        assertEquals("The failed count should be got properly.", 1, resultData.getFailedCount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getFailedCount()</code>.
     * </p>
     */
    public void testGetFailedCount_Accuracy3() {
        BatchOperationException[] exceptions = new BatchOperationException[] {null};
        resultData.setExceptionList(exceptions);
        assertEquals("The failed count should be got properly.", 0, resultData.getFailedCount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSuccessCount()</code>.
     * </p>
     */
    public void testGetSuccessCount_Accuracy1() {
        resultData.setOperations(new Integer[] {new Integer(1)});
        assertEquals("The success count should be got properly.", 1, resultData.getSuccessCount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSuccessCount()</code>.
     * </p>
     */
    public void testGetSuccessCount_Accuracy2() {
        BatchOperationException[] exceptions = new BatchOperationException[] {new BatchOperationException("message")};
        resultData.setExceptionList(exceptions);
        resultData.setOperations(new Integer[] {new Integer(1)});
        assertEquals("The success count should be got properly.", 0, resultData.getSuccessCount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSuccessCount()</code>.
     * </p>
     */
    public void testGetSuccessCount_Accuracy3() {
        BatchOperationException[] exceptions = new BatchOperationException[] {null};
        resultData.setExceptionList(exceptions);
        resultData.setOperations(new Integer[] {new Integer(1)});
        assertEquals("The success count should be got properly.", 1, resultData.getSuccessCount());
    }

    /**
     * <p>
     * Tests accuracy of method <code>getOperations()</code>.
     * </p>
     */
    public void testGetOperations_Accuracy() {
        assertNull("The operatioins should be got properly.", resultData.getOperations());
    }

    /**
     * <p>
     * Tests method <code>setOperations(Object[])</code> with null opeations. IllegalArgumentException
     * is expected.
     * </p>
     */
    public void testSetOperations_NullOperations() {
        try {
            resultData.setOperations(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests method <code>setOperations(Object[])</code> with invalid type.
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetOperations_InvalidOperationsType1() {
        try {
            Object[] operations = new Object[2];
            operations[0] = new Object();
            operations[1] = new Integer(1);
            resultData.setOperations(operations);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests method <code>setOperations(Object[])</code> with invalid type.
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetOperations_InvalidOperationsType2() {
        try {
            Object[] operations = new Object[2];
            operations[0] = new TimeStatus();
            operations[1] = new Integer(1);
            resultData.setOperations(operations);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests method <code>setOperations(Object[])</code> with invalid type.
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetOperations_InvalidOperationsType3() {
        try {
            Object[] operations = new Object[2];
            operations[0] = null;
            operations[1] = new Integer(1);
            resultData.setOperations(operations);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of method <code>setOperations(Object[])</code>.
     * </p>
     */
    public void testSetOperations_Accuracy1() {
        Object[] operations = new Object[2];
        operations[0] = new Integer(2);
        operations[1] = new Integer(1);
        resultData.setOperations(operations);
        assertEquals("The operations should be set properly.", operations, resultData.getOperations());
    }

    /**
     * <p>
     * Tests accuracy of method <code>setOperations(Object[])</code>.
     * </p>
     */
    public void testSetOperations_Accuracy2() {
        Object[] operations = new Object[2];
        operations[0] = new TimeStatus();
        operations[1] = new TimeStatus();
        resultData.setOperations(operations);
        assertEquals("The operations should be set properly.", operations, resultData.getOperations());
    }
}
