/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for <code>MimeTypeRetriever</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MimeTypeRetrieverUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private MimeTypeRetriever instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new MimeTypeRetriever();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type text, so text/plain should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy1() throws Exception {
        assertEquals("mime type is wrong", "text/plain", instance.getMimeTypeFromFileName("test_files/test.txt"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type pdf, so application/pdf should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy2() throws Exception {
        assertEquals("mime type is wrong", "application/pdf", instance.getMimeTypeFromFileName("test_files/test.pdf"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type doc, so application/doc should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy3() throws Exception {
        assertEquals("mime type is wrong", "application/msword", instance
            .getMimeTypeFromFileName("test_files/test.doc"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type rtf, so application/rtf should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy4() throws Exception {
        assertEquals("mime type is wrong", "application/rtf", instance.getMimeTypeFromFileName("test_files/test.rtf"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type html, so text/html should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy5() throws Exception {
        assertEquals("mime type is wrong", "text/html", instance.getMimeTypeFromFileName("test_files/test.html"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type htm, so text/htm should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy6() throws Exception {
        assertEquals("mime type is wrong", "text/html", instance.getMimeTypeFromFileName("test_files/test.htm"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type jpg, so image/jpeg should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy7() throws Exception {
        assertEquals("mime type is wrong", "image/jpeg", instance.getMimeTypeFromFileName("test_files/test.jpg"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type jpeg, so image/jpeg should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy8() throws Exception {
        assertEquals("mime type is wrong", "image/jpeg", instance.getMimeTypeFromFileName("test_files/test.jpeg"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type gif, so image/gif should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy9() throws Exception {
        assertEquals("mime type is wrong", "image/gif", instance.getMimeTypeFromFileName("test_files/test.gif"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type png, so image/png should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy10() throws Exception {
        assertEquals("mime type is wrong", "image/png", instance.getMimeTypeFromFileName("test_files/test.png"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type bmp, so image/bmp should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy11() throws Exception {
        assertEquals("mime type is wrong", "image/bmp", instance.getMimeTypeFromFileName("test_files/test.bmp"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type xls, so application/vnd.ms-excel should be
     * returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy12() throws Exception {
        assertEquals("mime type is wrong", "application/vnd.ms-excel", instance
            .getMimeTypeFromFileName("test_files/test.xls"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type zip, so application/zip should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy13() throws Exception {
        assertEquals("mime type is wrong", "application/zip", instance.getMimeTypeFromFileName("test_files/test.zip"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type rar, so application/x-zip-compressed should
     * be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy14() throws Exception {
        assertEquals("mime type is wrong", "application/x-zip-compressed", instance
            .getMimeTypeFromFileName("test_files/test.rar"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type mpg, so audio/mpeg should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy15() throws Exception {
        assertEquals("mime type is wrong", "audio/mpeg", instance.getMimeTypeFromFileName("test_files/test.mpg"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type mp3, so audio/mp3 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy16() throws Exception {
        assertEquals("mime type is wrong", "audio/mp3", instance.getMimeTypeFromFileName("test_files/test.mp3"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type jar, so application/java-archive should be
     * returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy17() throws Exception {
        assertEquals("mime type is wrong", "application/java-archive", instance
            .getMimeTypeFromFileName("test_files/test.jar"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type ppt, so application/vnd.ms-powerpoint
     * should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy18() throws Exception {
        assertEquals("mime type is wrong", "application/vnd.ms-powerpoint", instance
            .getMimeTypeFromFileName("test_files/test.ppt"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type pps, so application/vnd.ms-powerpoint
     * should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy19() throws Exception {
        assertEquals("mime type is wrong", "application/vnd.ms-powerpoint", instance
            .getMimeTypeFromFileName("test_files/test.pps"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName. The file is type properties, so application/octet-stream
     * should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Accuracy20() throws Exception {
        assertEquals("mime type is wrong", "application/octet-stream", instance
            .getMimeTypeFromFileName("test_files/struts.properties"));
    }

    /**
     * Failure test for getMimeTypeFromFileName. The filename is null, so IllegalArgumentException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Failure1() throws Exception {
        try {
            instance.getMimeTypeFromFileName(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for getMimeTypeFromFileName. The filename is empty, so IllegalArgumentException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromFileName_Failure2() throws Exception {
        try {
            instance.getMimeTypeFromFileName(" ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type text, so 3 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy1() throws Exception {
        assertEquals("mime type is wrong", 3, instance.getMimeTypeIdFromFileName("test_files/test.txt"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type pdf, so 4 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy2() throws Exception {
        assertEquals("mime type is wrong", 4, instance.getMimeTypeIdFromFileName("test_files/test.pdf"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type doc, so 1 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy3() throws Exception {
        assertEquals("mime type is wrong", 1, instance.getMimeTypeIdFromFileName("test_files/test.doc"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type rtf, so 2 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy4() throws Exception {
        assertEquals("mime type is wrong", 2, instance.getMimeTypeIdFromFileName("test_files/test.rtf"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type html, so 6 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy5() throws Exception {
        assertEquals("mime type is wrong", 6, instance.getMimeTypeIdFromFileName("test_files/test.html"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type htm, so 6 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy6() throws Exception {
        assertEquals("mime type is wrong", 6, instance.getMimeTypeIdFromFileName("test_files/test.htm"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type jpg, so 7 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy7() throws Exception {
        assertEquals("mime type is wrong", 7, instance.getMimeTypeIdFromFileName("test_files/test.jpg"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type jpeg, so 7 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy8() throws Exception {
        assertEquals("mime type is wrong", 7, instance.getMimeTypeIdFromFileName("test_files/test.jpeg"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type gif, so 8 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy9() throws Exception {
        assertEquals("mime type is wrong", 8, instance.getMimeTypeIdFromFileName("test_files/test.gif"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type png, so 9 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy10() throws Exception {
        assertEquals("mime type is wrong", 9, instance.getMimeTypeIdFromFileName("test_files/test.png"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type bmp, so 10 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy11() throws Exception {
        assertEquals("mime type is wrong", 10, instance.getMimeTypeIdFromFileName("test_files/test.bmp"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type xls, so 11 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy12() throws Exception {
        assertEquals("mime type is wrong", 11, instance.getMimeTypeIdFromFileName("test_files/test.xls"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type zip, so 12 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy13() throws Exception {
        assertEquals("mime type is wrong", 12, instance.getMimeTypeIdFromFileName("test_files/test.zip"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type rar, so 13 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy14() throws Exception {
        assertEquals("mime type is wrong", 13, instance.getMimeTypeIdFromFileName("test_files/test.rar"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type mpg, so 14 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy15() throws Exception {
        assertEquals("mime type is wrong", 14, instance.getMimeTypeIdFromFileName("test_files/test.mpg"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type mp3, so 15 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy16() throws Exception {
        assertEquals("mime type is wrong", 15, instance.getMimeTypeIdFromFileName("test_files/test.mp3"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type jar, so 16 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy17() throws Exception {
        assertEquals("mime type is wrong", 16, instance.getMimeTypeIdFromFileName("test_files/test.jar"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type ppt, so 17 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy18() throws Exception {
        assertEquals("mime type is wrong", 17, instance.getMimeTypeIdFromFileName("test_files/test.ppt"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type pps, so 17 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy19() throws Exception {
        assertEquals("mime type is wrong", 17, instance.getMimeTypeIdFromFileName("test_files/test.pps"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName. The file is type properties, so -1 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Accuracy20() throws Exception {
        assertEquals("mime type is wrong", -1, instance.getMimeTypeIdFromFileName("test_files/struts.properties"));
    }

    /**
     * Failure test for getMimeTypeIdFromFileName. The filename is null, so IllegalArgumentException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Failure1() throws Exception {
        try {
            instance.getMimeTypeIdFromFileName(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for getMimeTypeIdFromFileName. The filename is empty, so IllegalArgumentException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromFileName_Failure2() throws Exception {
        try {
            instance.getMimeTypeIdFromFileName(" ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is text/plain, so 3 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy1() throws Exception {
        assertEquals("mime type is wrong", 3, instance.getMimeTypeIdFromMimeType("text/plain"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is application/pdf, so 4 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy2() throws Exception {
        assertEquals("mime type is wrong", 4, instance.getMimeTypeIdFromMimeType("application/pdf"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is application/msword, so 1 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy3() throws Exception {
        assertEquals("mime type is wrong", 1, instance.getMimeTypeIdFromMimeType("application/msword"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is application/rtf, so 2 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy4() throws Exception {
        assertEquals("mime type is wrong", 2, instance.getMimeTypeIdFromMimeType("application/rtf"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is text/html, so 6 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy5() throws Exception {
        assertEquals("mime type is wrong", 6, instance.getMimeTypeIdFromMimeType("text/html"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is image/jpeg, so 7 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy6() throws Exception {
        assertEquals("mime type is wrong", 7, instance.getMimeTypeIdFromMimeType("image/jpeg"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is image/gif, so 8 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy7() throws Exception {
        assertEquals("mime type is wrong", 8, instance.getMimeTypeIdFromMimeType("image/gif"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is image/png, so 9 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy8() throws Exception {
        assertEquals("mime type is wrong", 9, instance.getMimeTypeIdFromMimeType("image/png"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is image/bmp, so 10 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy9() throws Exception {
        assertEquals("mime type is wrong", 10, instance.getMimeTypeIdFromMimeType("image/bmp"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is application/vnd.ms-excel, so 11 should be
     * returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy10() throws Exception {
        assertEquals("mime type is wrong", 11, instance.getMimeTypeIdFromMimeType("application/vnd.ms-excel"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is application/zip, so 12 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy11() throws Exception {
        assertEquals("mime type is wrong", 12, instance.getMimeTypeIdFromMimeType("application/zip"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is application/x-zip-compressed, so 13 should be
     * returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy12() throws Exception {
        assertEquals("mime type is wrong", 13, instance.getMimeTypeIdFromMimeType("application/x-zip-compressed"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is audio/mpeg, so 14 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy13() throws Exception {
        assertEquals("mime type is wrong", 14, instance.getMimeTypeIdFromMimeType("audio/mpeg"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is audio/mp3, so 15 should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy14() throws Exception {
        assertEquals("mime type is wrong", 15, instance.getMimeTypeIdFromMimeType("audio/mp3"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is application/java-archive, so 16 should be
     * returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy15() throws Exception {
        assertEquals("mime type is wrong", 16, instance.getMimeTypeIdFromMimeType("application/java-archive"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is application/vnd.ms-powerpoint, so 17 should be
     * returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy16() throws Exception {
        assertEquals("mime type is wrong", 17, instance.getMimeTypeIdFromMimeType("application/vnd.ms-powerpoint"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType. The type is application/octet-stream, so -1 should be
     * returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Accuracy17() throws Exception {
        assertEquals("mime type is wrong", -1, instance.getMimeTypeIdFromMimeType("application/octet-stream"));
    }

    /**
     * Failure test for getMimeTypeIdFromMimeType. The filename is null, so IllegalArgumentException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Failure1() throws Exception {
        try {
            instance.getMimeTypeIdFromMimeType(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for getMimeTypeIdFromMimeType. The filename is empty, so IllegalArgumentException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeIdFromMimeType_Failure2() throws Exception {
        try {
            instance.getMimeTypeIdFromMimeType(" ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 3 is passed, so text/plain should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy1() throws Exception {
        assertEquals("mime type is wrong", "text/plain", instance.getMimeTypeFromMimeTypeId(3));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 4 is passed, so application/pdf should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy2() throws Exception {
        assertEquals("mime type is wrong", "application/pdf", instance.getMimeTypeFromMimeTypeId(4));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 1 is passed, so application/msword should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy3() throws Exception {
        assertEquals("mime type is wrong", "application/msword", instance.getMimeTypeFromMimeTypeId(1));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 2 is passed, so application/rtf should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy4() throws Exception {
        assertEquals("mime type is wrong", "application/rtf", instance.getMimeTypeFromMimeTypeId(2));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 6 is passed, so text/html should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy5() throws Exception {
        assertEquals("mime type is wrong", "text/html", instance.getMimeTypeFromMimeTypeId(6));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 7 is passed, so image/jpeg should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy6() throws Exception {
        assertEquals("mime type is wrong", "image/jpeg", instance.getMimeTypeFromMimeTypeId(7));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 8 is passed, so image/gif should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy7() throws Exception {
        assertEquals("mime type is wrong", "image/gif", instance.getMimeTypeFromMimeTypeId(8));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 9 is passed, so image/png should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy8() throws Exception {
        assertEquals("mime type is wrong", "image/png", instance.getMimeTypeFromMimeTypeId(9));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 10 is passed, so image/bmp should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy9() throws Exception {
        assertEquals("mime type is wrong", "image/bmp", instance.getMimeTypeFromMimeTypeId(10));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 11 is passed, so application/vnd.ms-excel should be
     * returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy10() throws Exception {
        assertEquals("mime type is wrong", "application/vnd.ms-excel", instance.getMimeTypeFromMimeTypeId(11));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 12 is passed, so application/zip should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy11() throws Exception {
        assertEquals("mime type is wrong", "application/zip", instance.getMimeTypeFromMimeTypeId(12));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 13 is passed, so application/x-zip-compressed should be
     * returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy12() throws Exception {
        assertEquals("mime type is wrong", "application/x-zip-compressed", instance.getMimeTypeFromMimeTypeId(13));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 14 is passed, so audio/mpeg should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy13() throws Exception {
        assertEquals("mime type is wrong", "audio/mpeg", instance.getMimeTypeFromMimeTypeId(14));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 15 is passed, so should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy14() throws Exception {
        assertEquals("mime type is wrong", "audio/mp3", instance.getMimeTypeFromMimeTypeId(15));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 16 is passed, so application/java-archive should be
     * returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy15() throws Exception {
        assertEquals("mime type is wrong", "application/java-archive", instance.getMimeTypeFromMimeTypeId(16));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 17 is passed, so application/vnd.ms-powerpoint should be
     * returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy16() throws Exception {
        assertEquals("mime type is wrong", "application/vnd.ms-powerpoint", instance.getMimeTypeFromMimeTypeId(17));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId. 592 is passed, so null should be
     * returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Accuracy17() throws Exception {
        assertEquals("mime type is wrong", null, instance.getMimeTypeFromMimeTypeId(592));
    }

    /**
     * Failure test for getMimeTypeFromMimeTypeId. The argument is less than 1, so IllegalArgumentException
     * is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMimeTypeFromMimeTypeId_Failure1() throws Exception {
        try {
            instance.getMimeTypeFromMimeTypeId(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

}
