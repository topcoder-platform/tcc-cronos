/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax.stresstests;

import com.topcoder.service.actions.ajax.processors.DefaultAJAXResultPreProcessor;
import com.topcoder.service.studio.UploadedDocument;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


/**
 * Stress tests for <code>DefaultAJAXResultPreProcessor</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultAJAXResultPreProcessorStress extends TestCase {
    /**
     * Stress test for <code>preProcessData()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testPreProcessDataStress_UploadedDocument()
        throws Exception {
        UploadedDocument doc = new UploadedDocument();
        doc.setFile(new byte[] { 1, 2, 3, 4 });

        for (int i = 0; i < 10000; i++) {
            DefaultAJAXResultPreProcessor processor = new DefaultAJAXResultPreProcessor();
            processor.preProcessData(doc);
        }

        assertNull("The file should be set to null.", doc.getFile());
    }

    /**
     * Stress test for <code>preProcessData()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testPreProcessDataStress_List() throws Exception {
        List<Object> list = new ArrayList<Object>();

        // Add 10000 UploadedDocument to process
        for (int i = 0; i < 10000; i++) {
            UploadedDocument doc = new UploadedDocument();
            doc.setFile(new byte[] { 1, 2, 3, 4 });
            list.add(doc);
        }

        // Add some misc data
        for (int i = 0; i < 10000; i++) {
            list.add(null);
        }

        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }

        for (int i = 0; i < 10000; i++) {
            DefaultAJAXResultPreProcessor processor = new DefaultAJAXResultPreProcessor();
            processor.preProcessData(list);
        }

        // Verify UploadedDocument is processed correctly.
        for (int i = 0; i < 10000; i++) {
            assertNull("The file should be set to null.",
                ((UploadedDocument) list.get(i)).getFile());
        }
    }
}
