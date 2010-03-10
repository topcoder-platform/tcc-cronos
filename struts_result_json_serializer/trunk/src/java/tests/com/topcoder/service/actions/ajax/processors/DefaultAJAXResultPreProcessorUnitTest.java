/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax.processors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.UploadedDocument;

/**
 * <p>
 * The unit test for <code>DefaultAJAXResultPreProcessor</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultAJAXResultPreProcessorUnitTest extends TestCase {

    /**
     * <p>
     * The DefaultAJAXResultPreProcessor instace used for test.
     * </p>
     */
    private DefaultAJAXResultPreProcessor instance = new DefaultAJAXResultPreProcessor();

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(DefaultAJAXResultPreProcessorUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for the constructor, make sure it could create the DefaultAJAXResultPreProcessor instance
     * successfully.
     * </p>
     */
    public void testConstructor_Accuracy() {
        assertNotNull("The DefaultAJAXResultPreProcessor instance should be created.",
                new DefaultAJAXResultPreProcessor());
    }

    /**
     * <p>
     * Accuracy test for <code>preProcessData</code>, if data is null, it should be ok.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPreProcessData_Accuracy_7() throws Exception {
        assertEquals("The null data is ok.", null, instance.preProcessData(null));
    }

    /**
     * <p>
     * Accuracy test for <code>preProcessData</code>, if data contains null, it should be ok.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPreProcessData_Accuracy_8() throws Exception {
        List<Object> list = new ArrayList<Object>();
        list.add(null);
        assertEquals("The null data is ok.", list, instance.preProcessData(list));
    }

    /**
     * <p>
     * Accuracy test for <code>preProcessData</code>, if the data is UploadedDocument type, make sure it set
     * the file to null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPreProcessData_Accuracy_1() throws Exception {
        UploadedDocument data = new UploadedDocument();
        data.setFile(new byte[100]);
        assertNotNull("The file should not be null.", data.getFile());
        instance.preProcessData(data);
        assertNull("The file should be null.", data.getFile());
    }

    /**
     * <p>
     * Accuracy test for <code>preProcessData</code>, if the data is a list of UploadedDocument type elements,
     * make sure all element set the file to null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPreProcessData_Accuracy_2() throws Exception {
        UploadedDocument doc1 = new UploadedDocument();
        doc1.setFile(new byte[100]);
        UploadedDocument doc2 = new UploadedDocument();
        doc2.setFile(new byte[100]);
        UploadedDocument doc3 = new UploadedDocument();
        doc3.setFile(new byte[100]);
        List<Object> data = new ArrayList<Object>();
        data.add(doc1);
        data.add(doc2);
        data.add(doc3);
        assertNotNull("The file should not be null.", doc1.getFile());
        assertNotNull("The file should not be null.", doc2.getFile());
        assertNotNull("The file should not be null.", doc3.getFile());
        instance.preProcessData(data);
        assertNull("The file should be null.", doc1.getFile());
        assertNull("The file should be null.", doc2.getFile());
        assertNull("The file should be null.", doc2.getFile());
    }

    /**
     * <p>
     * Accuracy test for <code>preProcessData</code>, if the data is list of UploadedDocument elements and
     * other type elements, make sure all UploadedDocument element set the file to null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPreProcessData_Accuracy_3() throws Exception {
        UploadedDocument doc1 = new UploadedDocument();
        doc1.setFile(new byte[100]);
        UploadedDocument doc2 = new UploadedDocument();
        doc2.setFile(new byte[100]);
        String nodoc = "NOT_UploadedDocument";
        List<Object> data = new ArrayList<Object>();
        data.add(doc1);
        data.add(doc2);
        data.add(nodoc);
        assertNotNull("The file should not be null.", doc1.getFile());
        assertNotNull("The file should not be null.", doc2.getFile());
        instance.preProcessData(data);
        assertNull("The file should be null.", doc1.getFile());
        assertNull("The file should be null.", doc2.getFile());
        assertEquals("Make sure the NOT UploadedDocument element is not changed", "NOT_UploadedDocument",
                data.get(2));
    }

    /**
     * <p>
     * Accuracy test for <code>preProcessData</code>, if the data is list of none UploadedDocument elements,
     * do nothing.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPreProcessData_Accuracy_4() throws Exception {
        String nodoc = "NOT_UploadedDocument";
        List<Object> data = new ArrayList<Object>();
        data.add(nodoc);
        instance.preProcessData(data);
        assertEquals("Make sure the NOT UploadedDocument element is not changed", "NOT_UploadedDocument",
                data.get(0));
    }

    /**
     * <p>
     * Accuracy test for <code>preProcessData</code>, if the data not UploadedDocument and list type, do
     * nothing.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPreProcessData_Accuracy_6() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("key1", "value1");
        instance.preProcessData(data);
        assertEquals("The size should match", 1, data.size());
        assertEquals("Make sure the PreProcessor do nothing", "value1", data.get("key1"));
    }
}
