/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.io.File;
import java.util.Map;

/**
 * <p>
 * The mock FileUploadAttachContestFileAction used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockFileUploadAttachContestFileAction extends FileUploadAttachContestFileAction {

    /**
     * Overrides the prepare method for unit testing field validations.
     */
    @Override
    public void prepare() {
        super.prepare();

        // initialize with valid values
        setContestServiceFacade(new MockContestServiceFacade());
        MimeTypeRetriever mimeTypeRetriever = new MimeTypeRetriever();
        setContestId(1);
        setContestFile(new File("test_files/test.txt"));
        setContestFileName("test.txt");
        setContestFileContentType(mimeTypeRetriever.getMimeTypeFromFileName(getContestFile().getPath()));
        setContestFileDescription("test file");
        setDocumentTypeId(5);
        setMimeTypeRetriever(mimeTypeRetriever);

        // set invalid values, if required
        Map<String, Object> validations = TestHelper.getFieldValues();
        if (validations.size() != 0) {
            for (String key : validations.keySet()) {
                Object value = validations.get(key);

                if (key.equals("contestId")) {
                    setContestId(Long.parseLong(value.toString()));
                } else if (key.equals("contestFileName")) {
                    setContestFileName((String) value);
                } else if (key.equals("contestFileContentType")) {
                    setContestFileContentType((String) value);
                } else if (key.equals("contestFileDescription")) {
                    setContestFileDescription((String) value);
                } else if (key.equals("documentTypeId")) {
                    setDocumentTypeId(Integer.parseInt(value.toString()));
                } else if (key.equals("mimeTypeRetriever")) {
                    setMimeTypeRetriever((MimeTypeRetriever) value);
                }
            }
        }

    }
}
