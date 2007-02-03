/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * UnitTest for ImplementationHelperUnit class.
 *
 * @author mittu
 * @version 1.0
 */
public class ImplementationHelperUnitTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ImplementationHelperUnitTest.class);
    }

    /**
     * Accuracy test of <code>checkObjectValid(Object obj, String name)</code>.
     */
    public void testCheckObjectValid() {
        try {
            ImplementationHelper.checkObjectValid(null, "junit");
            fail("failed to throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>checkStringValid(String str, String name)</code>.
     */
    public void testCheckStringValid() {
        try {
            ImplementationHelper.checkStringValid("  ", "junit");
            fail("failed to throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getElement(Element element, String key)</code>.
     */
    public void testGetElement() {
	Element documentElement = TestHelper.getDomElement("GameDetailHandler.xml", false);
	NodeList nodes = documentElement.getElementsByTagName("handler");

        assertEquals("failed to get key value", ImplementationHelper.getElement(
                (Element) documentElement, "game_id_param_key"), "gameId");

        // key not present in the xml.
        try {
            ImplementationHelper.getElement(TestHelper.getDomElement("GameDetailHandler2.xml", true),
                    "game_id_param_key");
            fail("failed to throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
