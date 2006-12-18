/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import java.io.StringReader;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.topcoder.util.config.ConfigManager;

/**
 * Defines helper methods used in accuracy tests.
 * 
 * @author Zulander
 * @version 1.0
 */
final class AccuracyTestHelper {

    /**
     * A DocumentBuilderFactory used to generate XML parsers in XML2Element
     */
    private final static DocumentBuilderFactory dbFactory;

    static {
        dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
    }

	/**
	 * Emtpy private constructor to make this class can not be instanced.
	 */
	private AccuracyTestHelper() {
	}
	
	/**
	 * Parses a string represents XML elements into a DOM Element.
	 * @param content string represents XML elements.
	 * @throws Exception exception thrown to JUnit.
	 */
	static Element XML2Element(String content) throws Exception {
		// Create a local parser for thread-safety
		DocumentBuilder parser = dbFactory.newDocumentBuilder();
		StringReader reader = new StringReader(content);
		InputSource inputSource = new InputSource(reader);
		Document doc = parser.parse(inputSource);

		return doc.getDocumentElement();
	}

	/**
	 * <p>
	 * Clear the configs.
	 * </p>
	 * 
	 * @throws Exception
	 *             unexpected exception.
	 */
	static void clearConfig() throws Exception {
		ConfigManager configManager = ConfigManager.getInstance();

		for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
			configManager.removeNamespace((String) iter.next());
		}
	}
}
