/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.topcoder.util.config.ConfigManager;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Defines helper methods used in accuracy tests.
 * 
 * @author Zulander
 * @version 1.0
 */
final class AccuracyTestHelper {

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
		DOMParser parser = new DOMParser();
		StringReader reader = new StringReader(content);
		InputSource inputSource = new InputSource(reader);

		parser.parse(inputSource);
		return parser.getDocument().getDocumentElement();
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
