/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.accuracytests;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.service.actions.FileUploadAttachContestFileAction;
import com.topcoder.service.actions.MimeTypeRetriever;
import com.topcoder.service.facade.contest.ContestServiceFacade;

/**
 * Accuracy tests for FileUploadAttachContestFileAction.
 * @author onsky
 * @version 1.0
 */
public class FileUploadAttachContestFileActionTests extends TestCase {
    /**
     * <p>Represents FileUploadAttachContestFileAction instance for testing.</p>
     */
    private FileUploadAttachContestFileAction instance;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new FileUploadAttachContestFileAction();
        ContestServiceFacade facade = new MockContestServiceFacade();
        instance.setContestServiceFacade(facade);
        instance.setContestFileContentType("test");
        instance.setMimeTypeRetriever(new MimeTypeRetriever());
        instance.setContestFile(new File("test_files/accuracy/test.txt"));
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for executeAction.
     */
    public void test_executeAction() {
        try {
        	instance.executeAction();
        	// pass
        } catch (Exception e) {
        	fail("no exception expected");
        }
    }

    /**
     * Accuracy test for setContestFile.
     */
    public void test_setContestFile() {
        File file = new File("test");
        instance.setContestFile(file);
        assertEquals("Incorrect value after set a new one", file, instance.getContestFile());
    }

    /**
     * Accuracy test for setContestId.
     */
    public void test_setContestId() {
        assertEquals("must be 0 by default", 0, instance.getContestId());
        instance.setContestId(2l);
        assertEquals("Incorrect value after set a new one", 2l, instance.getContestId());
    }

    /**
     * Accuracy test for setContestFileName.
     */
    public void test_setContestFileName() {
        assertNull("must be null by default", instance.getContestFileName());
        instance.setContestFileName("test");
        assertEquals("Incorrect value after set a new one", "test", instance.getContestFileName());
    }
}
