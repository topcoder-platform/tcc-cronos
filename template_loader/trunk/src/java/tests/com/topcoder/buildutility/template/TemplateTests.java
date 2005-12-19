/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0 (Unit Test)
 */
package com.topcoder.buildutility.template;

import junit.framework.TestCase;
import java.io.InputStream;
import java.io.FileNotFoundException;

/**
 * <p>Unit test cases for Template.</p>
 *
 * @author oldbig
 *
 * @version 1.0
 */
public class TemplateTests extends TestCase {

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
     * <p>Tests the constructor of Template.</p>
     * <p>name is null, NullPointerException should be thrown.</p>
     */
    public void testTemplate1() {
        try {
            // name is null, NullPointerException should be thrown
            new Template(0, null, "foo", "foo", "foo");
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor of Template.</p>
     * <p>uri is null, NullPointerException should be thrown.</p>
     */
    public void testTemplate2() {
        try {
            // uri is null, NullPointerException should be thrown
            new Template(0, "foo", "foo", "foo", null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor of Template.</p>
     * <p>fileName is null, NullPointerException should be thrown.</p>
     */
    public void testTemplate3() {
        try {
            // fileName is null, NullPointerException should be thrown
            new Template(0, "foo", "foo", null, "foo");
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor of Template.</p>
     * <p>name is empty, IllegalArgumentException should be thrown.</p>
     */
    public void testTemplate4() {
        try {
            // name is empty, IllegalArgumentException should be thrown
            new Template(0, " ", "foo", "foo", "foo");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor of Template.</p>
     * <p>uri is empty, IllegalArgumentException should be thrown.</p>
     */
    public void testTemplate5() {
        try {
            // uri is empty, IllegalArgumentException should be thrown
            new Template(0, "foo", "foo", "foo", " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor of Template.</p>
     * <p>fileName is empty, IllegalArgumentException should be thrown.</p>
     */
    public void testTemplate6() {
        try {
            // fileName is empty, IllegalArgumentException should be thrown
            new Template(0, "foo", "foo", " ", "foo");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor of Template.</p>
     * <p>description is empty, IllegalArgumentException should be thrown.</p>
     */
    public void testTemplate7() {
        try {
            // description is empty, IllegalArgumentException should be thrown
            new Template(0, "foo", " ", "foo", "foo");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests whether the Template class could be instantiated with the valid arguments.</p>
     */
    public void testTemplate8() {

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

    /**
     * <p>Tests the setFileServerUri method.</p>
     * <p>Argument is null, NullPointerException should be thrown.</p>
     */
    public void testSetFileServerUri1() {
        try {
            // argument is null, NullPointerException should be thrown
            template.setFileServerUri(null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the setFileServerUri method.</p>
     * <p>Argument is empty, IllegalArgumentException should be thrown.</p>
     */
    public void testSetFileServerUri2() {
        try {
            // argument is empty, IllegalArgumentException should be thrown
            template.setFileServerUri(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the setFileServerUri method.</p>
     * <p>Validates whether the file server uri was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testSetFileServerUri3() throws Exception {

        // set the file server uri
        template.setFileServerUri("test_files/");

        // if the file server uri was set correctly, no exception should be thrown
        InputStream input = template.getData();
        if (input != null) {
            input.close();
        }
    }

    /**
     * <p>Tests the getData method.</p>
     * <p>the file server uri is not set, IllegalStateException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testGetData1() throws Exception {
        try {
            // the file server uri is not set, IllegalStateException should be thrown
            template.getData();
            fail("IllegalStateException should be thrown");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>Tests the getData method.</p>
     * <p>the specified file dost not exist, FileNotFoundException should be thrown.</p>
     */
    public void testGetData2() {
        template.setFileServerUri("no_this_path");
        try {
            // the specified file dost not exist, FileNotFoundException should be thrown
            template.getData();
            fail("FileNotFoundException should be thrown");
        } catch (FileNotFoundException e) {
            // success
        }
    }

    /**
     * <p>Tests the getData method.</p>
     * <p>the InputStream could be properly retrieved.</p>
     *
     * @throws Exception to JUnit
     */
    public void testGetData3() throws Exception {

        // set the file server uri
        template.setFileServerUri("test_files/");

        InputStream input = null;
        try {
            // get the input stream
            input = template.getData();

            // read all bytes from the input stream
            byte[] buffer = new byte[10];
            int size = input.read(buffer);

            // check whether the InputStream was properly retrieved
            assertEquals("the InputStream is not properly retrieved", "test", new String(buffer, 0, size));

        } finally {
            // finally, we should close the stream if it was open
            if (input != null) {
                input.close();
            }
        }

    }

}
