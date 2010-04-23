/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import java.io.File;

import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.actions.FileUploadAttachContestFileAction;
import com.topcoder.service.actions.MimeTypeRetriever;

/**
 * FileUploadAttachContestFileActionTest.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FileUploadAttachContestFileActionTest extends AbstractStressTest {

    /**
     * <p>
     * The mime type retriever.
     * </p>
     */
    private static MimeTypeRetriever mimeTypeRetriever = new MimeTypeRetriever();

    /**
     * Test FileUploadAttachContestFileAction#executeAction().
     *
     * @throws Exception
     *             the exception occurs
     */
    @Test
    public void testExecuteAction() throws Exception {
        String fileName = "test_files/stress_files/aa.txt";
        File file = new File(fileName);
        stress.getStartTime();
        for (int i = 0; i < StressHelper.EXCUTIMES; i++) {
            setRequestParametes();
            ActionProxy proxy = getActionProxy("/fileUpload.action");
            FileUploadAttachContestFileAction cate = (FileUploadAttachContestFileAction) proxy.getAction();
            cate.setContestServiceFacade(contestServiceFacade);
            cate.setMimeTypeRetriever(mimeTypeRetriever);
            cate.setContestFile(file);
            cate.setContestFileName(fileName);
            cate.setContestFileContentType("txt");
            proxy.execute();
            assertEquals(1, cate.getDocumentTypeId());
        }
        stress.getEndTime();

        stress.printResultToFile("FileUploadAttachContestFileAction", "executeAction", "");
    }

    /**
     * Sets the request parametes.
     */
    private void setRequestParametes() {
        request.setParameter("contestFileName", "test_files/stress_files/aa.txt");
        request.setParameter("contestFileContentType", "text/html");
        request.setParameter("contestId", "1");
        request.setParameter("contestFileDescription", "this is file description");
       request.setParameter("documentTypeId", "1");

    }
}
