/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

/**
 * <p>
 * The enumeration of component programming languages. Current only two languages are supported: Java and .NET(C#). It
 * is used in Component class.
 * </p>
 * <p>
 * Thread Safety: It is enum type so it should be thread safe for each value.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public enum ComponentLanguage {

    /**
     * <p>
     * Represents the Java programming language.
     * </p>
     */
    JAVA,

    /**
     * <p>
     * Represents the .NET (C#) programming language.
     * </p>
     */
    DOT_NET;
}
