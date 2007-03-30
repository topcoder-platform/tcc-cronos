/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import java.io.File;
import java.util.Iterator;

import com.cronos.im.messenger.XMLMessage;
import com.topcoder.util.config.ConfigManager;

/**
 * Helper class for failure test cases.
 *
 * @author mittu
 * @version 1.0
 */
public class FailureTestHelper {

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private FailureTestHelper() {

    }

    /**
     * <p>
     * Loads the given configuration file.
     * </p>
     *
     * @param fileName
     *            The file to be loaded
     *
     * @throws Exception
     *             to junit.
     */
    static void loadConfigFile(String fileName) throws Exception {
        // releaseConfigFiles();
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add("failure_tests" + File.separator + fileName);
    }

    /**
     * <p>
     * Releases all the configurations.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    static void releaseConfigFiles() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();
        for (Iterator iterator = configManager.getAllNamespaces(); iterator.hasNext();) {
            configManager.removeNamespace((String) iterator.next());
        }
    }

    /**
     * <p>
     * Returns an <code>XMLMessage</code> with sender as null.
     * </p>
     * @param message The XML Message
     * @return XMLMessage.
     */
    static XMLMessage getNullSenderXmlMessage(XMLMessage message) {
        message.addRecipient("recipient 1");
        message.setChatSessionId(1);
        return message;
    }

    /**
     * <p>
     * Returns an <code>XMLMessage</code> with no recipients added.
     * </p>
     * @param message The XML Message
     * @return XMLMessage.
     */
    static XMLMessage getNoRecipientsXmlMessage(XMLMessage message) {
        message.setSender("sender");
        message.setChatSessionId(1);
        return message;
    }

    /**
     * <p>
     * Returns an <code>XMLMessage</code> with no chat session id.
     * </p>
     * @param message The XML Message
     * @return XMLMessage.
     */
    static XMLMessage getNoChatSessionId(XMLMessage message) {
        message.setSender("sender");
        message.addRecipient("recipient 1");
        return message;
    }

    /**
     * <p>
     * Returns an <code>XMLMessage</code> with all required attributes set.
     * </p>
     * @param message The XMLMessage.
     * @return XMLMessage.
     */
    static XMLMessage getAllReqdSet(XMLMessage message) {
        message.setSender("sender");
        message.addRecipient("recipient 1");
        message.setChatSessionId(1);
        return message;
    }
}
