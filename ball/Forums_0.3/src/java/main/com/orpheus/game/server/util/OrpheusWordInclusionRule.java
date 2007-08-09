/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.util.web.sitestatistics.impl.rules.LengthRule;
import org.w3c.dom.Element;

/**
 * <p>A custom word inclusion rule which is used to validate the texts which are going to be used as a basis for hunt
 * targets generation. The validation rules ensure that the text is meeting the constraints for puzzle generators used
 * for generating the brainteasers.</p>
 *
 * <p>TODO : This is a short-term solution for the issue which must be addressed in next version of the application.
 * Currently Missing Letter Puzzle component raises an exception if the text does not contain at least two characters
 * from following ranges: <code>0-9, a-z, A-Z</code> and this rule validates the texts against such constraint.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusWordInclusionRule extends LengthRule {

    /**
     * <p>A <code>int</code> providing the minimum number of valid characters to be present in text.</p>
     */
    private static final int MINIMUM_NUM_OF_VALID_CHARS = 2;

    /**
     * <p>Constructs new <code>OrpheusWordInclusionRule</code> instance with specified minimum and maximum length.</p>
     * 
     * @param minLength an <code>int</code> providing the minimum allowed length for the word.
     * @param maxLength an <code>int</code> providing the maximum allowed length for the word.
     * @see   LengthRule#LengthRule(int, int)
     */
    public OrpheusWordInclusionRule(int minLength, int maxLength) {
        super(minLength, maxLength);
    }


    /**
     * <p>Decides whether the given piece of text should be included or not.</p>
     *
     * @param text a <code>String</code> providing the text for validation.
     * @param element an <code>Element</code> representing XML/HTML element containing the validated text.
     * @param caseSensitive a <code>boolean</code> indicating whether checking should be sensitive to the case of the
     *        letters in given text.
     * @return <code>true</code> if specified text is valid and may be included; <code>false</code> otherwise.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public boolean includeText(String text, Element element, boolean caseSensitive) {
        boolean lengthIsValid = super.includeText(text, element, caseSensitive);
        if (!lengthIsValid) {
            return false;
        } else {
            // Check if the word contains enough digit and letter characters to generate the brainteaser
            int cnt = 0;
            for (int i = 0; (i < text.length()) && (cnt < MINIMUM_NUM_OF_VALID_CHARS); i++) {
                if (isValidChar(text.charAt(i))) {
                    cnt++;
                }
            }
            return (cnt >= MINIMUM_NUM_OF_VALID_CHARS);
        }
    }

    /**
     * <p>Checks if specified character is valid for purpose of brainteaser generation.</p>
     *
     * @param ch a <code>char</code> to validate.
     * @return <code>true</code> if specified character is valid for purpose of brainteaser generation; <code>false
     *         </code> otherwise.
     */
    private static boolean isValidChar(char ch) {
        if ((ch >= '0') && (ch <= '9') || (ch >= 'a') && (ch <= 'z') || (ch >= 'A') && (ch <= 'Z')) {
            return true;
        } else {
            return false;
        }
    }
}
