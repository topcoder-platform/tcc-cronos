/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.buildutility.template.accuracytests;

import com.topcoder.buildutility.template.Template;

import junit.framework.TestCase;


/**
 * Accuracy tests for Template class.
 * @author zjq
 * @version 1.0
 */
public class TemplateAccuracyTests extends TestCase {
    /**
     * A Template instance to test.
     */
    private Template template = null;

    /**
     * Initialize the Template instance.
     */
    protected void setUp() {
        template = new Template(0, "name", "description", "file_name", "test.txt");
    }

    /**
     * <p>Tests whether the Template class could be instantiated with the valid arguments.</p>
     */
    public void testTemplate() {
        template = new Template(1, "name", "description", "file_name", "uri");

        assertNotNull("Unable to instantiate Template.", template);
        assertEquals("id is incorrect", 1, template.getId());
        assertEquals("name is incorrect", "name", template.getName());
        assertEquals("description is incorrect", "description", template.getDescription());
        assertEquals("file name is incorrect", "file_name", template.getFileName());
        assertEquals("uri is incorrect", "uri", template.getUri());
    }

    /**
     * <p>Tests the getId method.</p>
     */
    public void testGetId() {
        assertEquals("id is incorrect", 0, template.getId());
    }

    /**
     * <p>Tests the getName method.</p>
     */
    public void testGetName() {
        assertEquals("name is incorrect", "name", template.getName());
    }

    /**
     * <p>Tests the getDescription method.</p>
     */
    public void testGetDescription() {
        assertEquals("description is incorrect", "description", template.getDescription());
    }

    /**
     * <p>Tests the getFileName method.</p>
     */
    public void testGetFileName() {
        assertEquals("file name is incorrect", "file_name", template.getFileName());
    }

    /**
     * <p>Tests the getUri method.</p>
     */
    public void testGetUri() {
        assertEquals("uri is incorrect", "test.txt", template.getUri());
    }
}
