/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.io.IOException;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.NamespaceConflictException;
import com.topcoder.configuration.persistence.UnrecognizedFileTypeException;
import com.topcoder.configuration.persistence.UnrecognizedNamespaceException;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.DocumentGeneratorConfigurationException;
import com.topcoder.util.file.DocumentGeneratorFactory;
import com.topcoder.web.reg.actions.registration.EmailSendingAction;

/**
 * This is a base class for all accuracy test cases. It loads configuration settings, database connection
 * and clean up for the test cases.
 *
 * @author Beijing2008
 * @since 1.0
 */
public class BaseAccuracyTest extends TestCase {
    static void setDocumentGenerator(EmailSendingAction action) throws DocumentGeneratorConfigurationException, ConfigurationAccessException, UnrecognizedNamespaceException, ConfigurationParserException, NamespaceConflictException, UnrecognizedFileTypeException, IOException {
        DocumentGenerator generator = DocumentGeneratorFactory
        .getDocumentGenerator(new ConfigurationFileManager("test_files/accuracy/DocumentManager.xml")
        .getConfiguration("com.topcoder.util.file").getChild("com.topcoder.util.file"));
        action.setEmailSender("beijing2008@topcoder.com");
        action.setEmailBodyTemplateName("test_files/accuracy/MailBodyTemplate.txt");
        action.setEmailSubject("This is the notification from topcoder");
        action.setEmailBodyTemplateSourceId("file");
        action.setDocumentGenerator(generator);
        
    }
}
