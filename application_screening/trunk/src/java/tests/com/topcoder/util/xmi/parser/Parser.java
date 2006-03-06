/*
 * @(#)Parser.java
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.util.xmi.parser;

import java.io.File;
import java.io.FileInputStream;


/**
 * <p>
 * Parser is the interface of all the parsers. It provides a collection of
 * overloaded method to parse file, file stream and xmi string.
 * </p>
 *
 * @author TCSDESIGNER
 * @author smell
 * @version 1.0
 */
public interface Parser {
    /**
     * <p>
     * Parses a given file.
     * </p>
     *
     * @param file - the given file
     *
     * @throws ParserException - if the parser met any problems to parser the
     *         given file.
     */
    void parse(File file) throws ParserException;

    /**
     * <p>
     * Parses a given file stream.
     * </p>
     *
     * @param stream - the given file stream
     *
     * @throws ParserException - if the parser met any problems to parser the
     *         given stream.
     */
    void parse(FileInputStream stream) throws ParserException;

    /**
     * <p>
     * Parses a given xmi string.
     * </p>
     *
     * @param xmiStr - the given xmi string
     *
     * @throws ParserException - if the parser met any problems to parser the
     *         given xmi string.
     */
    void parse(String xmiStr) throws ParserException;
}
