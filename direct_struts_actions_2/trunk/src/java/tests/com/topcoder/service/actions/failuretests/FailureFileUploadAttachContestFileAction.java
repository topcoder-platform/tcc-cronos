/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import org.springframework.test.context.ContextConfiguration;

import com.topcoder.service.actions.FileUploadAttachContestFileAction;

/**
 * Mock <code>FileUploadAttachContestFileAction</code>.
 * @author moon.river
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class FailureFileUploadAttachContestFileAction extends FileUploadAttachContestFileAction {

    /**
     * Contest file description.
     */
    public static String contestFileDescription;

    /**
     * Contest id.
     */
    public static long contestId;

    /**
     * Document type id.
     */
    public static int documentTypeId;

    /**
     * Gets contest id.
     * @return contest id.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Gets project id.
     * @return project id
     */
    public int getDocumentTypeId() {
        return documentTypeId;
    }

    /**
     * Gets the description.
     * @return description
     */
    public String getContestFileDescription() {
        return contestFileDescription;
    }
    /**
     * Prepares the action.
     */
    public void prepare() {
        super.prepare();
        this.setContestId(this.contestId);
        this.setContestFileDescription(this.contestFileDescription);
        this.setDocumentTypeId(this.documentTypeId);
    }
}
