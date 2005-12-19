/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import com.topcoder.buildutility.component.ComponentVersion;

/**
 * <p>
 * This interface defines methods to generate scripts from the input stream which contains the component version
 * information or the component version object.
 * </p>
 * <p>
 * An instance of this interface can be obtained with the
 * {@link BuildScriptGeneratorFactory}<code>.createGenerator</code> methods. This instance may then be used to process
 * XML from a variety of sources and write the scripts output to a variety of sinks.
 * </p>
 * <p>
 * An object of this class may not be used in multiple threads running concurrently. Different generators may be used
 * concurrently by different threads.
 * </p>
 * <p>
 * A Generator may be used multiple times. Parameters and output properties are preserved across generation.
 * </p>
 *
 * @see com.topcoder.buildutility.BuildScriptGeneratorFactory
 * @author dmks, marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public interface BuildScriptGenerator {
    /**
     * <p>
     * Generate the script to the result output stream from source input stream which contains the component version
     * information.
     * </p>
     *
     * @param source source input stream which contains the component version information
     * @param result the output stream stores the generated script
     * @throws GenerationProcessException if failed to generate script
     */
    public void generate(InputStream source, OutputStream result) throws GenerationProcessException;

    /**
     * <p>
     * Generate the script of the passed component version object to the result output stream.
     * </p>
     *
     * @param component the component version object to generate the script for
     * @param result the output stream stores the generated script
     * @throws GenerationProcessException if failed to generate script
     */
    public void generate(ComponentVersion component, OutputStream result) throws GenerationProcessException;

    /**
     * <p>
     * Generate the script of the passed component version object to the result sink.
     * </p>
     *
     * @param component the component version object to generate the script for
     * @param result the output stores the generated script
     * @throws GenerationProcessException if failed to generate script
     */
    public void generate(ComponentVersion component, Result result) throws GenerationProcessException;

    /**
     * <p>
     * Generate the script to the result sink from source which contains the component version information.
     * </p>
     *
     * @param source source which contains the component version information
     * @param result the output stores the generated script
     * @throws GenerationProcessException if failed to generate script
     */
    public void generate(Source source, Result result) throws GenerationProcessException;
}
