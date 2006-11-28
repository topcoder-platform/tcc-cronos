/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import com.orpheus.administration.persistence.Filter;
import com.orpheus.administration.persistence.ParameterHelpers;

/**
 * <p>This filter searches a textual field for a specified substring that is contained in it. The substring can
 * appear anywhere, including the beginning, the end and any other places. It also supports wildcard
 * searching. This class is serializable so that it can be used in the Administration EJB framework. It is meant to
 * parallel the <code>Filter</code> class in the <i>Search Builder</i> component. The parallel is almost exact, but
 * all implementations will ignore the validation and cloning aspects. It also incorporates only the 1.3 version,
 * and all deprecated items have been removed.
 *
 * <p> Four types of value are supported, which are <i>CONTAINS</i>, <i>START_WITH</i>, <i>END_WITH</i> and
 * <i>WITH_CONTENT</i>. All of them are implemented by adding a wildcard in the proper position. </p>
 *
 * <p><strong>Thread Safety</strong>: The class is immutable and therefore thread safe.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class LikeFilter implements Filter {
    /**
     * Special character ssequence denoting a <i>contains</i> search.
     */
    public static final String CONTAIN_TAGS = "SS:";

    /**
     * Special character ssequence denoting a <i>starts with</i> search.
     */
    public static final String START_WITH_TAG = "SW:";

    /**
     * Special character ssequence denoting a <i>ends with</i> search.
     */
    public static final String END_WITH_TAG = "EW:";

    /**
     * Special character ssequence denoting a <i>with content</i> search.
     */
    public static final String WITH_CONTENT = "WC:";

    /**
     * The default escape character.
     */
    private static final char DEFAULTESCAPECHAR = '\\';

    /**
     * The characters that must be escaped.
     */
    private static final char[] INVALID_ESCAPE = {'%', '_', '*'};

    /**
     * <p> Represents escape character to escape the wildcard in SQL (<code>%</code> and <code>_</code>) and LDAP
     * (<code>*</code>). This member is initialzed in constructor and never changed later. It will never be one of
     * the values in {@link #INVALID_ESCAPE INVALID_ESCAPE}. It can be accessed by the getter. Its default value is
     * <code>\</code>.
     */
    private final char escapeCharacter;

    /**
     * The name of the field to filter.
     */
    private final String fieldName;

    /**
     * The value on which to filter.
     */
    private final String value;

    /**
     * Constructs a new <code>LikeFilter</code> with the default escape characer. Equivalent to {@link
     * #LikeFilter(String, String, char) LikeFilter(name, value, '\'}.
     *
     * @param name the name of the field to filter
     * @param value the value on which to filter
     * @throws IllegalArgumentException if either parameter is <code>null</code> or an empty string
     * @throws IllegalArgumentException if <code>value</code> does not begin with one of the character sequences
     *   denoting the type of search (<i>SS:</i>, <i>SW:</i>, <i>EW:</i>, or <i>WC:</i>)
     */
    public LikeFilter(String name, String value) {
        this(name, value, DEFAULTESCAPECHAR);
    }

    /**
     * Constructs a new <code>LikeFilter</code> with the specified field, value, and escape character.
     *
     * @param name the name of the field to filter
     * @param value the value on which to filter
     * @param escapeCharacter character used to escape literal instances of the wildcard character(s)
     * @throws IllegalArgumentException if either string parameter is <code>null</code> or an empty string
     * @throws IllegalArgumentException if <code>value</code> does not begin with one of the character sequences
     *   denoting the type of search (<i>SS:</i>, <i>SW:</i>, <i>EW:</i>, or <i>WC:</i>)
     * @throws IllegalArgumentException if <code>escapeCharacter</code> is <i>%</i>, <i>_</i>, or <i>*</i>
     */
    public LikeFilter(String name, String value, char escapeCharacter) {
        ParameterHelpers.checkString(name, "field name");
        ParameterHelpers.checkString(value, "filter value");

        //check the value should be start with one of the four patterns
        if (!(value.startsWith(CONTAIN_TAGS) || value.startsWith(START_WITH_TAG)
                || value.startsWith(END_WITH_TAG) || value.startsWith(WITH_CONTENT))) {
            throw new IllegalArgumentException("the value should start with 'SS:', 'SW:', 'EW:' or 'WC:'.");
        }

        //the value with out prefix should with length > 0
        if (value.length() == CONTAIN_TAGS.length()) {
            throw new IllegalArgumentException("the value without prefix should not be of zero length.");
        }

        for (int i = 0; i < INVALID_ESCAPE.length; i++) {
            if (INVALID_ESCAPE[i] == escapeCharacter) {
                throw new IllegalArgumentException("the escape character '" + escapeCharacter + "' is invalid.");
            }
        }

        this.fieldName = name;
        this.value = value;
        this.escapeCharacter = escapeCharacter;
    }

    /**
     * Returns the escape character.
     *
     * @return the escape character
     */
    public char getEscapeCharacter() {
        return escapeCharacter;
    }

    /**
     * Returns the name of the field being filtered.
     *
     * @return the name of the field being filtered
     */
    public String getName() {
        return fieldName;
    }

    /**
     * Returns the value to filter on.
     *
     * @return the value to filter on
     */
    public String getValue() {
        return value;
    }
}
