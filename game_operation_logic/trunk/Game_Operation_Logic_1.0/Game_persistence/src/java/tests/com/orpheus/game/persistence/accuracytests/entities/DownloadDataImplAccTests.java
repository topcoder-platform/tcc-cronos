/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests.entities;

import com.orpheus.game.persistence.entities.DownloadDataImpl;
import com.orpheus.game.persistence.accuracytests.AccuracyHelper;
import junit.framework.TestCase;

/**
 * Accuracy test cases for <code>DownloadDataImpl</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class DownloadDataImplAccTests extends TestCase {
    /**
     * .
     * The DownloadDataImpl instance to test
     */
    private DownloadDataImpl dd = null;

    /**
     * Setup for each test cases.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        dd = new DownloadDataImpl("content".getBytes(), "html/text", "html");
    }

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testConstructor() throws Exception {
        assertEquals("Not the expected content.", "content", AccuracyHelper.convertISToString(dd.getContent()));
        assertEquals("Not the expected media type.", "html/text", dd.getMediaType());
        assertEquals("Not the expected suggested name.", "html", dd.getSuggestedName());
    }

    /**
     * <p>
     * Accuracy test of the getId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetId() throws Exception {
        dd = new DownloadDataImpl("testContent".getBytes(), "xml/text", "xml");
        assertEquals("Not the expected content.", "testContent", AccuracyHelper.convertISToString(dd.getContent()));
    }

    /**
     * <p>
     * Accuracy test of the getName() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetName() throws Exception {
        dd = new DownloadDataImpl("testContent".getBytes(), "xml/text", "xml");
        assertEquals("Not the expected media type.", "xml/text", dd.getMediaType());
    }

    /**
     * <p>
     * Accuracy test of the getImageId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetImageId() throws Exception {
        dd = new DownloadDataImpl("testContent".getBytes(), "xml/text", "xml");
        assertEquals("Not the expected suggested name.", "xml", dd.getSuggestedName());
    }
}
