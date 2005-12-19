/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;

import java.io.InputStream;

import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;

/**
 * <p>
 * This interface is the factory of {@link BuildScriptGenerator}. It defines methods to create
 * {@link BuildScriptGenerator}s. And all {@link BuildScriptGenerator} instances should be created through factory
 * methods.
 * </p>
 * <p>
 * Usually, a {@link BuildScriptGenerator} instance needs a template or an input stream containing the template to
 * generate a script, so all the factory methods contain parameters which contain the template.
 * </p>
 *
 * @see com.topcoder.buildutility.BuildScriptGenerator
 * @see com.topcoder.buildutility.BuildScriptGeneratorFactoryImpl
 * @author dmks, marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public interface BuildScriptGeneratorFactory {
    /**
     * <p>
     * Create a {@link BuildScriptGenerator} with an input stream containing the template XSLT document.
     * </p>
     *
     * @param source the template XSLT document
     * @return a {@link BuildScriptGenerator} object that may be used to perform a generation in a single thread,
     * this method will never return null
     * @throws GeneratorCreationException if fails to create a {@link BuildScriptGenerator} object
     */
    public BuildScriptGenerator createGenerator(InputStream source) throws GeneratorCreationException;

    /**
     * <p>
     * Create a {@link BuildScriptGenerator} with a <code>Template</code> object which contains the data of XSLT
     * document.
     * </p>
     *
     * @param template a <code>Template</code> object which contains the data of XSLT document
     * @return a {@link BuildScriptGenerator} object that may be used to perform a generation in a single thread,
     * this method will never return null
     * @throws GeneratorCreationException if fails to create a {@link BuildScriptGenerator} object
     */
    public BuildScriptGenerator createGenerator(Template template) throws GeneratorCreationException;

    /**
     * <p>
     * Create a {@link BuildScriptGenerator} object with the template XSLT document which is stored in the template
     * hierarchy.
     * </p>
     * <p>
     * The template will be found according the component version object using the template selection algorithm.
     * </p>
     *
     * @param templateHierarchy hierarchy of the templates for all the component versions
     * @param component the component version object to select the template
     * @return a {@link BuildScriptGenerator} object that may be used to perform a generation in a single thread,
     * this method will never return null.
     * @throws GeneratorCreationException if fails to create the generator.
     */
    public BuildScriptGenerator createGenerator(TemplateHierarchy templateHierarchy, ComponentVersion component)
        throws GeneratorCreationException;
}