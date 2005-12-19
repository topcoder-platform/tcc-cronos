/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.component.ExternalComponentVersion;
import com.topcoder.buildutility.component.TechnologyType;

/**
 * <p>
 * This class is the default implementation of the {@link BuildScriptGenerator} interface. It uses
 * the <code>javax.xml.transform.Transformer</code> class to tranform the component version information to a build
 * script.
 * </p>
 * <p>
 * An instance of this interface can be obtained with the
 * {@link BuildScriptGeneratorFactoryImpl}<code>.createGenerator</code> methods. This instance may then be used to
 * process XML from a variety of sources and write the scripts output to a variety of sinks. If the
 * {@link #generate(InputStream, OutputStream)} or {@link #generate(Source, Result)} methods are used, the xml
 * passed should follow the following DTD:
 * <pre>
 * &lt;!<b>ELEMENT</b> <i>component</i> (<i>dependencies</i>, <i>external_dependencies</i>, <i>technology_types</i>)&gt;
 * &lt;!<b>ATTLIST</b> <i>component</i>
 *          <i>id</i>                  <b>CDATA</b>   <b>#REQUIRED</b>
 *          <i>name</i>                <b>CDATA</b>   <b>#REQUIRED</b>
 *          <i>description</i>         <b>CDATA</b>   <b>#IMPLIED</b>
 *          <i>version</i>             <b>CDATA</b>   <b>#REQUIRED</b>
 *          <i>package</i>             <b>CDATA</b>   <b>#IMPLIED</b>&gt;
 * &lt;!<b>ELEMENT</b> <i>dependencies</i> (<i>dependent_component</i>*)&gt;
 * &lt;!<b>ELEMENT</b> <i>dependent_component</i> <b>EMPTY</b>&gt;
 * &lt;!<b>ATTLIST</b> <i>dependent_component</i>
 *          <i>id</i>                  <b>CDATA</b>   <b>#REQUIRED</b>
 *          <i>name</i>                <b>CDATA</b>   <b>#REQUIRED</b>
 *          <i>description</i>         <b>CDATA</b>   <b>#IMPLIED</b>
 *          <i>version</i>             <b>CDATA</b>   <b>#REQUIRED</b>
 *          <i>package</i>             <b>CDATA</b>   <b>#IMPLIED</b>&gt;
 * &lt;!<b>ELEMENT</b> <i>external_dependencies</i> (<i>external_dependency</i>*)&gt;
 * &lt;!<b>ELEMENT</b> <i>external_dependency</i> <b>EMPTY</b>&gt;
 * &lt;!<b>ATTLIST</b> <i>external_dependency</i>
 *          <i>id</i>                  <b>CDATA</b>   <b>#REQUIRED</b>
 *          <i>name</i>                <b>CDATA</b>   <b>#REQUIRED</b>
 *          <i>version</i>             <b>CDATA</b>   <b>#REQUIRED</b>
 *          <i>description</i>         <b>CDATA</b>   <b>#IMPLIED</b>
 *          <i>filename</i>            <b>CDATA</b>   <b>#REQUIRED</b>&gt;
 * &lt;!<b>ELEMENT</b> <i>technology_types</i> (<i>technology_type</i>*)&gt;
 * &lt;!<b>ELEMENT</b> <i>technology_type</i> <b>EMPTY</b>&gt;
 * &lt;!<b>ATTLIST</b> <i>technology_type</i>
 *          <i>id</i>                  <b>CDATA</b>   <b>#REQUIRED</b>
 *          <i>name</i>                <b>CDATA</b>   <b>#REQUIRED</b>
 *          <i>description</i>         <b>CDATA</b>   <b>#IMPLIED</b>
 *          <i>deprecated_status</i>   (<i>true</i>|<i>false</i>) <b>#REQUIRED</b>&gt;
 * </pre>
 * </p>
 * <p>
 * An object of this class may not be used in multiple threads running concurrently. Different generators may be used
 * concurrently by different threads. A Generator may be used multiple times. Parameters and output properties are
 * preserved across generation.
 * </p>
 *
 * @author dmks, marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public class BuildScriptGeneratorImpl implements BuildScriptGenerator {
    /** The name of the XML tag representing the entire component. */
    private static final String COMPONENT_TAG = "component";
    /** The name of the XML tag representing the component dependencies. */
    private static final String DEPENDENCIES_TAG = "dependencies";
    /** The name of the XML tag representing a dependent component. */
    private static final String DEPENDENT_COMPONENT_TAG = "dependent_component";
    /** The name of the XML tag representing the external dependencies. */
    private static final String EXTERNAL_DEPENDENCIES_TAG = "external_dependencies";
    /** The name of the XML tag representing an external dependency. */
    private static final String EXTERNAL_DEPENDENCY_TAG = "external_dependency";
    /** The name of the XML tag representing the technology types of the component. */
    private static final String TECHNOLOGY_TYPES_TAG = "technology_types";
    /** The name of the XML tag representing a technology type. */
    private static final String TECHNOLOGY_TYPE_TAG = "technology_type";

    /** The name of the XML attribute representing a dependency or technology type id. */
    private static final String ID_ATTR = "id";
    /** The name of the XML attribute representing the name of a dependency or technology. */
    private static final String NAME_ATTR = "name";
    /** The name of the XML attribute representing the description of a dependency or technology. */
    private static final String DESCRIPTION_ATTR = "description";
    /** The name of the XML attribute representing the version of a dependency. */
    private static final String VERSION_ATTR = "version";
    /** The name of the XML attribute representing the filename of a dependency. */
    private static final String FILENAME_ATTR = "filename";
    /** The name of the XML attribute representing the deprecated status of a technology. */
    private static final String DEPRECATED_STATUS_ATTR = "deprecated_status";

    /**
     * <p>
     * Represents the <code>Transformer</code> instance used to perform the real transformation from the source to
     * the result script.
     * </p>
     * <p>
     * It is initialized in the constructor and never changed later, it will never be null.
     * </p>
     */
    private final Transformer transformer;

    /**
     * <p>
     * Construct this object with a <code>Transformer</code> instance.
     * </p>
     * <p>
     * This constructor is package private and will be used by the {@link BuildScriptGeneratorFactoryImpl} class.
     * Because of this, the parameter will not be checked for null values, although null should never be passed in.
     * </p>
     *
     * @param transformer a <code>Transformer</code> instance that will be used to perform real transformation
     * @throws NullPointerException if transformer is null
     */
    BuildScriptGeneratorImpl(Transformer transformer) {
        // Validate parameter
        if (transformer == null) {
            throw new NullPointerException("transformer is null");
        }

        // Initialize fields
        this.transformer = transformer;
    }

    /**
     * <p>
     * Generate the script to the result ouput stream from source input stream. The input stream should contain an XML
     * file followin the DTD described {@linkplain BuildScriptGeneratorImpl here}.
     * </p>
     *
     * @param source source input stream which contains the component version information
     * @param result the output stream stores the generated script
     * @throws GenerationProcessException if failed to generate the script, for example because the input stream does
     * not return a valid XML file
     * @throws NullPointerException if any parameter is null
     */
    public void generate(InputStream source, OutputStream result) throws GenerationProcessException {
        // Validate parameters
        if (source == null) {
            throw new NullPointerException("source is null");
        }
        if (result == null) {
            throw new NullPointerException("result is null");
        }

        // Use overloaded method generate(Source, Result) to generate the build script
        generate(new StreamSource(source), new StreamResult(result));
    }

    /**
     * <p>
     * Generate the script of the passed component version object to the result ouput stream.
     * </p>
     *
     * @param component the component version object to generate the script for
     * @param result the output stream stores the generated script
     * @throws GenerationProcessException if failed to generate the script
     * @throws NullPointerException if any parameter is null
     */
    public void generate(ComponentVersion component, OutputStream result) throws GenerationProcessException {
        // Validate parameters, no need to validate component as it is already validated in the called generate method
        if (result == null) {
            throw new NullPointerException("result is null");
        }

        // Use overloaded method generate(ComponentVersion, Result) to generate the build script
        generate(component, new StreamResult(result));
    }

    /**
     * <p>
     * Generate the script of the passed component version object to the result sink.
     * </p>
     *
     * @param component the component version object to generate the script for
     * @param result the output stores the generated script
     * @throws GenerationProcessException if failed to generate the script
     * @throws NullPointerException if any parameter is null
     */
    public void generate(ComponentVersion component, Result result) throws GenerationProcessException {
        // Validate parameters
        if (component == null) {
            throw new NullPointerException("component is null");
        }
        if (result == null) {
            throw new NullPointerException("result is null");
        }

        // Convert component to DOM Node
        Node node = transform(component);
        // Use overloaded method to generate the build script
        generate(new DOMSource(node), result);
    }

    /**
     * <p>
     * Generate the script to the result sink from source which contains the component version information.
     * </p>
     *
     * @param source source which contains the component version information
     * @param result the output stores the generated script
     * @throws GenerationProcessException if failed to generate the script
     * @throws NullPointerException if any parameter is null
     */
    public void generate(Source source, Result result) throws GenerationProcessException {
        // Validate parameters
        if (source == null) {
            throw new NullPointerException("source is null");
        }
        if (result == null) {
            throw new NullPointerException("result is null");
        }

        // Transform using the transformer
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            // Wrap exceptions in GenerationProcessException
            throw new GenerationProcessException("Error generating build script", e);
        }
    }

    /**
     * <p>
     * Transform a component version object to a DOM Node. The DTD of the generated component version xml document is
     * described {@linkplain BuildScriptGeneratorImpl here}.
     * </p>
     *
     * @param component the component version the transform
     * @return the corresponding xml node of the specified component version
     * @throws GenerationProcessException if transforming the component failed
     * @throws NullPointerException if component is null
     */
    protected Node transform(ComponentVersion component) throws GenerationProcessException {
        // Validate parameter
        if (component == null) {
            throw new NullPointerException("component is null");
        }

        // Transform the component
        try {
            // Create a new DOM document
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            // Create a component tag in the document
            Element compElem = doc.createElement(COMPONENT_TAG);
            // Add the tag to the document
            doc.appendChild(compElem);

            // Fill the attributes of the component element
            addComponentAttributes(compElem, component);

            // Add dependency components, first create the dependencies tag
            Element dependenciesElem = doc.createElement(DEPENDENCIES_TAG);
            // Add the tag to the components tag
            compElem.appendChild(dependenciesElem);
            // Iterate over all component dependencies (both direct and indirect dependencies)
            ComponentVersion[] dependComp = component.getAllTopcoderDependencies();
            for (int i = 0; i < dependComp.length; i++) {
                // Create an element for the dependency
                Element dependencyElem = doc.createElement(DEPENDENT_COMPONENT_TAG);
                // Add the tag to the dependencies tag
                dependenciesElem.appendChild(dependencyElem);
                // Fill the attributes of this dependency tag
                addComponentAttributes(dependencyElem, dependComp[i]);
            }

            // Add external dependencies, create the external_dependencies tag
            Element extDependencyElem = doc.createElement(EXTERNAL_DEPENDENCIES_TAG);
            // Append the tag to the component tag
            compElem.appendChild(extDependencyElem);
            // Fill in the external dependencies
            addExternalDependencies(doc, extDependencyElem, component.getExternalComponentDependencies());

            // Add technology types, create the technology_types tag
            Element techTypesElem = doc.createElement(TECHNOLOGY_TYPES_TAG);
            // Append the tag to the component tag
            compElem.appendChild(techTypesElem);
            // Fill in the technology types
            addTechnologyTypes(doc, techTypesElem, component.getTechnologyTypes());

            // Return the document
            return doc.getDocumentElement();
        } catch (ParserConfigurationException e) {
            // Wrap exception in GenerationProcessException
            throw new GenerationProcessException("Error converting component to xml", e);
        }
    }

    /**
     * This method adds all the attributes of the specific ComponentVersion object to the DOM Element.
     *
     * @param compElem DOM element to write the attributes to
     * @param component the ComponentVersion to get the attributes from
     */
    private void addComponentAttributes(Element compElem, ComponentVersion component) {
        // Set the standard attributes
        compElem.setAttribute(ID_ATTR, component.getId().toString());
        compElem.setAttribute(NAME_ATTR, component.getName());
        setAttributeIfNotNull(compElem, DESCRIPTION_ATTR, component.getDescription());
        compElem.setAttribute(VERSION_ATTR, component.getVersion());

        // Get the remaining component attributes
        String attrs[] = component.getAttributeNames();
        // Iterate over the attribute names
        for (int i = 0; i < attrs.length; i++) {
            // Set the attributes
            compElem.setAttribute(attrs[i], component.getAttribute(attrs[i]));
        }
    }

    /**
     * This method adds the given array of external dependencies to the provided DOM Element.
     *
     * @param doc the Document to be used to create new Element's
     * @param elem the Element to wich to add the newly created Element's
     * @param extDeps the ExternalComponentVersion's to convert
     */
    private void addExternalDependencies(Document doc, Element elem, ExternalComponentVersion[] extDeps) {
        // Iterate over the external dependencies
        for (int i = 0; i < extDeps.length; i++) {
            // Create the new Element
            Element depElem = doc.createElement(EXTERNAL_DEPENDENCY_TAG);
            // Add the element to the parent element
            elem.appendChild(depElem);
            // Set the attributes of the newly created element
            depElem.setAttribute(ID_ATTR, extDeps[i].getId().toString());
            depElem.setAttribute(NAME_ATTR, extDeps[i].getName());
            setAttributeIfNotNull(depElem, DESCRIPTION_ATTR, extDeps[i].getDescription());
            depElem.setAttribute(FILENAME_ATTR, extDeps[i].getFilename());
            depElem.setAttribute(VERSION_ATTR, extDeps[i].getVersion());
        }
    }

    /**
     * This method adds the given array of technology types to the provided DOM Element.
     *
     * @param doc the Document to be used to create new Element's
     * @param elem the Element to wich to add the newly created Element's
     * @param techTypes the TechnologyType's to convert
     */
    private void addTechnologyTypes(Document doc, Element elem, TechnologyType[] techTypes) {
        // Iterate over all TechnologyType's
        for (int i = 0; i < techTypes.length; i++) {
            // Create a new Element
            Element techElem = doc.createElement(TECHNOLOGY_TYPE_TAG);
            // Append the Element to the provided element
            elem.appendChild(techElem);
            // Set the attributes of the newly created TechnologyType element
            techElem.setAttribute(ID_ATTR, techTypes[i].getId().toString());
            techElem.setAttribute(NAME_ATTR, techTypes[i].getName());
            techElem.setAttribute(DEPRECATED_STATUS_ATTR, (techTypes[i].isDeprecated() ? "true" : "false"));
            setAttributeIfNotNull(techElem, DESCRIPTION_ATTR, techTypes[i].getDescription());
        }
    }

    /**
     * Only set the attribute value of an element if the value is not null.
     *
     * @param elem the Element of which to set the name
     * @param attrName the name of the attribute to set
     * @param value the new value of the attribute, if this is null nothing will be done
     */
    private void setAttributeIfNotNull(Element elem, String attrName, String value) {
        // Check if value is null
        if (value != null) {
            // If not, set the attribute
            elem.setAttribute(attrName, value);
        }
    }
}