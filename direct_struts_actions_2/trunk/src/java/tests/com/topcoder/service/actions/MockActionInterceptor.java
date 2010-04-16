/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.UploadedDocument;

/**
 * <p>
 * The mock action interceptor used in the demo.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockActionInterceptor extends AbstractInterceptor {

    /**
     * Intercepts the action and populates with proper data, and prepares required mock data.
     *
     * @param actionInvocation the action invocation instance to intercept
     *
     * @return the action result after executing action
     *
     * @throws Exception if some error occurs during method execution
     */
    @SuppressWarnings("unchecked")
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

        // clear existing mock data
        TestHelper.cleanupEnvironment();

        // prepare mock data so it will be present for the actions
        prepareMockData();

        Object obj = actionInvocation.getAction();

        if (obj instanceof FileUploadAttachContestFileAction) {
            FileUploadAttachContestFileAction action = (FileUploadAttachContestFileAction) obj;

            // get contest file name (using real path)
            Map<String, Object> parameters = (Map<String, Object>) actionInvocation.getStack().getContext().get(
                "parameters");
            String fileName = ((String[]) parameters.get("contestFileName"))[0].toString();
            String realPath = ServletActionContext.getServletContext().getRealPath(fileName);
            action.setContestFileName(realPath);
            action.setContestFile(new File(action.getContestFileName()));

            // set other parameters for action
            MimeTypeRetriever mimeTypeRetriever = new MimeTypeRetriever();
            String contestFileContentType = mimeTypeRetriever.getMimeTypeFromFileName(action.getContestFileName());
            action.setContestFileContentType(contestFileContentType);
            action.setContestFileDescription(((String[]) parameters.get("contestFileDescription"))[0].toString());
        }

        return actionInvocation.invoke();
    }

    /**
     * Prepares mock data for actions.
     */
    private static void prepareMockData() {
        // prepare new mock data
        ContestData contestData = new ContestData();
        List<UploadedDocument> docUploads = new ArrayList<UploadedDocument>();

        UploadedDocument doc = new UploadedDocument();
        doc.setDocumentId(1);
        doc.setContestId(1);
        loadFileToDocument(doc, "test.txt");
        docUploads.add(doc);

        doc = new UploadedDocument();
        doc.setDocumentId(2);
        doc.setContestId(1);
        loadFileToDocument(doc, "test.pdf");
        docUploads.add(doc);

        contestData.setDocumentationUploads(docUploads);

        StudioCompetition comp = new StudioCompetition(contestData);
        comp.setId(1);
        MockContestServiceFacade.addStudioCompetition(comp);

        SoftwareCompetition comp2 = new SoftwareCompetition();
        comp2.setId(1);
        MockContestServiceFacade.addSoftwareCompetition(comp2);

        doc = new UploadedDocument();
        doc.setDocumentId(1);
        MockContestServiceFacade.addDocument(doc);

        doc = new UploadedDocument();
        doc.setDocumentId(2);
        MockContestServiceFacade.addDocument(doc);
    }

    /**
     * Loads the file to the document.
     *
     * @param doc the document
     * @param fileName the name of the file
     */
    private static void loadFileToDocument(UploadedDocument doc, String fileName) {
        doc.setFileName(fileName);
        String realPath = ServletActionContext.getServletContext().getRealPath(doc.getFileName());
        doc.setPath(realPath.substring(0, realPath.lastIndexOf(System.getProperty("file.separator")) + 1));
        doc.setMimeTypeId(new MimeTypeRetriever().getMimeTypeIdFromFileName(realPath));
    }
}
