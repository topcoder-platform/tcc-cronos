/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;

import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;

/**
 * <p>
 * This is the default generator factory implementation in this component. It uses
 * <code>javax.xml.transform.TransformerFactory</code> in Xalan to retrieve a
 * <code>javax.xml.transform.Transformer</code> object to perform the real XSLT transformation. It also uses a
 * TemplateSelectionAlgorithm in the Template Selector Component to select the template in template hierarchy
 * according to a component version object.
 * </p>
 * <p>
 * This class is thread-safe. The instances of this class do not maintain any private state. However the subclasses may
 * choose to add a private state to be used in generator creation. In such a case the subclasses are responsible for
 * addressing the thread safety properly.
 * </p>
 *
 * @see com.topcoder.buildutility.BuildScriptGenerator
 * @author dmks, marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public class BuildScriptGeneratorFactoryImpl implements BuildScriptGeneratorFactory {
    /**
     * <p>
     * Represents the transformer factory used to retrieve <code>Transformer</code> instances for an XSLT document.
     * This field is initialized once, and will never be changed.
     * </p>
     */
    private final TransformerFactory transformerFactory = TransformerFactory.newInstance();


    /**
     * <p>
     * Represents the <code>TemplateSelectionAlgorithm</code> object to select a template in the hierarchy of templates
     * given a component version object. It is initialized once, and will never be changed.
     * </p>
     */
    private final TemplateSelectionAlgorithm templateSelector = new TemplateSelector();

    /**
     * <p>
     * This creates a new <code>BuildScriptGeneratorFactoryImpl</code> instance.
     * </p>
     */
    public BuildScriptGeneratorFactoryImpl() {
    }

    /**
     * <p>
     * Create a {@link BuildScriptGenerator} with an input stream containing the template XSLT document.
     * </p>
     *
     * @param source the template XSLT document
     * @return a {@link BuildScriptGenerator} object that may be used to perform a generation in a single thread,
     * this method will never return null
     * @throws GeneratorCreationException if failed to create a {@link BuildScriptGenerator} object, for example because
     * the inputstream is not a valid XSLT document.
     * @throws NullPointerException if the argument is null
     */
    public BuildScriptGenerator createGenerator(InputStream source) throws GeneratorCreationException {
        // Validate parameter
        if (source == null) {
            throw new NullPointerException("source is null");
        }

        try {
            // Instantiate transformer
            Transformer trans = transformerFactory.newTransformer(new StreamSource(source));
            // Instantiate and return the BuildScriptGenerator
            return new BuildScriptGeneratorImpl(trans);
        } catch (TransformerConfigurationException e) {
            // Wrap exception in GeneratorCreationException
            throw new GeneratorCreationException("Error creating transformer", e);
        }
    }

    /**
     * <p>
     * Create a {@link BuildScriptGenerator} with a template object, which should point to a valid XSLT document. If
     * the template is not a valid XSLT document, a {@link GeneratorCreationException} will be thrown.
     * </p>
     *
     * @param template a <code>Template</code> object which contains the data of XSLT document
     * @return a {@link BuildScriptGenerator} object that may be used to perform a generation in a single thread,
     * this method will never return null
     * @throws GeneratorCreationException if failed to create a {@link BuildScriptGenerator} object, for example because
     * the template is not a valid XSLT document.
     * @throws NullPointerException if the argument is null
     */
    public BuildScriptGenerator createGenerator(Template template) throws GeneratorCreationException {
        // Validate parameter
        if (template == null) {
            throw new NullPointerException("template is null");
        }

        // Use overloaded method createGenerator(InputStream) to instantiate the generator
        try {
            // Get the input stream
            InputStream is = template.getData();
            // Create the generator
            BuildScriptGenerator g = createGenerator(is);
            // Close the input stream
            is.close();
            // Return the generator
            return g;
        } catch (Exception e) {
            // Wrap exceptions in GeneratorCreationException
            throw new GeneratorCreationException("Error getting template data", e);
        }
    }

    /**
     * <p>
     * Create a {@link BuildScriptGenerator} object with the template XSLT document which is stored in the template
     * hierarchy. The template will be found according the component version object using the template selection
     * algorithm.
     * </p>
     *
     * @param templateHierarchy hierarchy of the templates for all the component versions
     * @param component the component version object to select the template
     * @return a {@link BuildScriptGenerator} object that may be used to perform a generation in a single thread,
     * this method will never return null.
     * @throws GeneratorCreationException if failed to create a {@link BuildScriptGenerator} object, for example because
     * the template returned by the template selection algorithm is not a valid XSLT document. This exception will also
     * be thrown, if no templates at all have been found by the template selection algorithm.
     * @throws NullPointerException if any parameter is null
     */
    public BuildScriptGenerator createGenerator(TemplateHierarchy templateHierarchy, ComponentVersion component)
        throws GeneratorCreationException {
        // Validate parameters
        if (templateHierarchy == null) {
            throw new NullPointerException("templateHierarchy is null");
        }
        if (component == null) {
            throw new NullPointerException("component is null");
        }

        // Retrieve the templates
        Template[] templates = templateSelector.selectTemplates(component, templateHierarchy);
        // Verify at least one template is returned, if not throw an exception
        if (templates.length < 1) {
            throw new GeneratorCreationException("No templates found for component in the hierarchy");
        }

        // Use overloaded method(Template) to create the generator using the first returned template
        return createGenerator(templates[0]);
    }
}