/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.util.Map;

/**
 * <p>
 * The mock GetDocumentFileContestAction used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockGetDocumentFileContestAction extends GetDocumentFileContestAction {

    /**
     * Overrides the prepare method for unit testing field validations.
     */
    @Override
    public void prepare() {
        super.prepare();

        // initialize with valid values
        setContestServiceFacade(new MockContestServiceFacade());
        setMimeTypeRetriever(new MimeTypeRetriever());
        setDocumentId(1);
        setContestId(1);

        // set invalid values, if required
        Map<String, Object> validations = TestHelper.getFieldValues();
        if (validations.size() != 0) {
            for (String key : validations.keySet()) {
                Object value = validations.get(key);

                if (key.equals("documentId")) {
                    setDocumentId(Long.parseLong(value.toString()));
                }
            }
        }

    }

}
