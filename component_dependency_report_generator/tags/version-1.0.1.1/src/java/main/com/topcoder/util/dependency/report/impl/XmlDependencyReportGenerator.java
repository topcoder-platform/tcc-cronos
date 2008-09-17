/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.report.BaseDependencyReportGenerator;
import com.topcoder.util.dependency.report.DependencyReportConfigurationException;
import com.topcoder.util.dependency.report.DependencyReportGenerationException;
import com.topcoder.util.dependency.report.Helper;


/**
 * <p>
 * This dependency report generator writes reports in XML format.
 * </p>
 *
 * <p>
 * It extends <code>BaseDependencyReportGenerator</code>, thus supports generating reports to various destination
 * types, processing indirect dependencies, filtering dependencies by type and category, optional including of
 * dependency type, category and path to the report.
 * </p>
 *
 * <p>
 * This class just overrides <code>writeReport()</code> method of <code>BaseDependencyReportGenerator</code>.
 * It doesn't have additional configuration parameters.
 * </p>
 *
 * <p>
 * Indentation is used in the output XML reports, thus such reports can be easily read by the user without any
 * additional tools.
 * </p>
 *
 * <p>
 *     <strong>Sample API Usage:</strong>
 *     <pre>
 *     //Create an instance with persistence and configuration
 *     XmlDependencyReportGenerator generator = new XmlDependencyReportGenerator(persistence, configuration);
 *
 *     //Or create an instance with pre-loaded dependencies and configuration
 *     generator = new XmlDependencyReportGenerator(List&lt;DependencyEntry&gt;, configuration);
 *
 *     //Generate all reports with indirect dependencies included as string:
 *     String report = generator.generateAll(true);
 *
 *     //Generate all reports without indirect dependencies included as string:
 *     report = generator.generateAll(false);
 *
 *     //Generate all reports with indirect dependencies included to file:
 *     generator.generateAll("FileName", true);
 *
 *     //Generate all reports without indirect dependencies included to file:
 *     generator.generateAll("FileName", false);
 *
 *     //Generate all reports with indirect dependencies included to output stream:
 *     generator.generateAll(OutputStream, true);
 *
 *     //Generate all reports without indirect dependencies included to output stream:
 *     generator.generateAll(OutputStream, false);
 *
 *     //Create a list of component IDs:
 *     List&lt;String&gt; componentIds = ......
 *
 *     //Generate certain reports with indirect dependencies included as string:
 *     String report = generator.generate(componentIds, true);
 *
 *     //Generate certain reports without indirect dependencies included as string:
 *     report = generator.generate(componentIds, false);
 *
 *     //Generate certain reports with indirect dependencies included to file:
 *     generator.generate(componentIds, "FileName", true);
 *
 *     //Generate certain reports without indirect dependencies included to file:
 *     generator.generate(componentIds, "FileName", false);
 *
 *     //Generate certain reports with indirect dependencies included to output stream:
 *     generator.generate(componentIds, OutputStream, true);
 *
 *     //Generate certain reports without indirect dependencies included to output stream:
 *     generator.generate(componentIds, OutputStream, false);
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 *
 * @see BaseDependencyReportGenerator
 */
public class XmlDependencyReportGenerator extends BaseDependencyReportGenerator {

    /**
     * <p>
     * Represents the "components" element.
     * </p>
     */
    private static final String COMPONENTS_ELEMENT = "components";

    /**
     * <p>
     * Represents the "component" element.
     * </p>
     */
    private static final String COMPONENT_ELEMENT = "component";

    /**
     * <p>
     * Represents the "dependency" element.
     * </p>
     */
    private static final String DEPENDENCY_ELEMENT = "dependency";

    /**
     * <p>
     * Represents the "name" attribute.
     * </p>
     */
    private static final String NAME_ATTR = "name";

    /**
     * <p>
     * Represents the "version" attribute.
     * </p>
     */
    private static final String VERSION_ATTR = "version";

    /**
     * <p>
     * Represents the "language" attribute.
     * </p>
     */
    private static final String LANGUAGE_ATTR = "language";

    /**
     * <p>
     * Represents the "type" attribute.
     * </p>
     */
    private static final String TYPE_ATTR = "type";

    /**
     * <p>
     * Represents the "category" attribute.
     * </p>
     */
    private static final String CATEGORY_ATTR = "category";

    /**
     * <p>
     * Represents the "path" attribute.
     * </p>
     */
    private static final String PATH_ATTR = "path";

    /**
     * <p>
     * Represents the indent.
     * </p>
     */
    private static final Integer INDENT = new Integer(2);

    /**
     * <p>
     * Represents the "indent-number" attribute of <code>TransformerFactory</code>.
     * </p>
     */
    private static final String INDENT_NUMBER = "indent-number";

    /**
     * <p>
     * Represents the value of <code>OutputKeys.INDENT</code> property of <code>Transformer</code>.
     * </p>
     */
    private static final String YES = "yes";

    /**
     * <p>
     * Creates an instance of <code>XmlDependencyReportGenerator</code> with the given dependencies and
     * configuration object.
     * </p>
     *
     * <p>
     * See class doc of <code>BaseDependencyReportGenerator</code> for sample configuration.
     * </p>
     *
     * @param dependencies The list of input dependency entries for this class (cannot be null or empty;
     *        cannot contain null).
     * @param configuration The configuration object for this class (cannot be null).
     *
     * @throws IllegalArgumentException If <code>dependencies</code> is null, empty or contains null element.
     *         Or if <code>configuration</code> is null.
     * @throws DependencyReportConfigurationException If error occurred while reading the configuration
     *         (e.g. Some property has multiple values; Or the value is not type of <code>String</code>;
     *         Or the property is present but has null/empty value; Or the boolean value is neither "true"
     *         nor "false", case sensitive; Or the enum value does not present a valid enum, case insensitive).
     *
     * @see BaseDependencyReportGenerator
     */
    public XmlDependencyReportGenerator(List < DependenciesEntry > dependencies, ConfigurationObject configuration)
        throws DependencyReportConfigurationException {
        super(dependencies, configuration);
    }

    /**
     * <p>
     * Creates an instance of <code>XmlDependencyReportGenerator</code> with the given dependencies persistence and
     * configuration object.
     * </p>
     *
     * <p>
     * See class doc of <code>BaseDependencyReportGenerator</code> for sample configuration.
     * </p>
     *
     * @param persistence The component dependencies persistence to be used (cannot be null).
     * @param configuration The configuration object for this class (cannot be null).
     *
     * @throws IllegalArgumentException If <code>persistence</code> is null. Or if <code>configuration</code> is null.
     * @throws DependencyReportGenerationException If error occurs while accessing the dependencies persistence.
     * @throws DependencyReportConfigurationException If error occurred while reading the configuration
     *         (e.g. Some property has multiple values; Or the value is not type of <code>String</code>;
     *         Or the property is present but has null/empty value; Or the boolean value is neither "true"
     *         nor "false", case sensitive; Or the enum value does not present a valid enum, case insensitive).
     *
     * @see BaseDependencyReportGenerator
     */
    public XmlDependencyReportGenerator(DependenciesEntryPersistence persistence, ConfigurationObject configuration)
        throws DependencyReportGenerationException {
        super(persistence, configuration);
    }

    /**
     * <p>
     * Writes the data from the provided entries to the output stream with use of the XML format.
     * </p>
     *
     * @param entries The list of dependency entries that hold data of the generated report
     *        (cannot be null; cannot contain null).
     * @param os The output stream for the generated report (cannot be null).
     *
     * @throws IllegalArgumentException If <code>entries</code> is null or contains null.
     *         Or <code>os</code> is null.
     * @throws DependencyReportGenerationException If error occurred while writing the report.
     */
    @Override
    protected void writeReport(List < DependenciesEntry > entries, OutputStream os)
        throws DependencyReportGenerationException {
        Collection < DependenciesEntry > distinctEntries =
            Helper.checkCollection(entries, "The list of dependency entries", true);
        Helper.checkNull(os, "OutputStream should not be null");

        //Create W3C DOM
        Document doc = createDocument(distinctEntries);

        try {
            TransformerFactory transfac = TransformerFactory.newInstance();

            //Set "indent-number", this is important for indent under JDK5
            //See http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6296446
            transfac.setAttribute(INDENT_NUMBER, INDENT);

            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, YES);

            StringWriter sw = new StringWriter();

            //Transform DOM to String
            trans.transform(new DOMSource(doc), new StreamResult(sw));

            String xmlString = sw.toString();

            //Write to output stream
            Writer writer = new OutputStreamWriter(os);
            writer.write(xmlString, 0, xmlString.length());
            writer.flush();
        } catch (TransformerFactoryConfigurationError e) {
            throw new DependencyReportGenerationException("Error occurs while instantiating TransformerFactory.", e);
        } catch (TransformerException e) {
            throw new DependencyReportGenerationException("Error occurs while transforming DOM.", e);
        } catch (IOException e) {
            throw new DependencyReportGenerationException("Error occurs while writing XML report to output stream.", e);
        }
    }

    /**
     * <p>
     * Creates a W3C dom with given entries.
     * </p>
     *
     * @param distinctEntries The collection of distinct dependencies entries.
     *
     * @return The created W3C dom with given entries.
     *
     * @throws DependencyReportGenerationException If any error occurs while creating W3C dom.
     */
    private Document createDocument(Collection < DependenciesEntry > distinctEntries)
        throws DependencyReportGenerationException {
        try {
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            //Create root element "components"
            Element root = doc.createElement(COMPONENTS_ELEMENT);

            doc.appendChild(root);

            for (DependenciesEntry entry : distinctEntries) {
                //Create element "component"
                Element component = doc.createElement(COMPONENT_ELEMENT);

                //Set the "name", "version", "language" attributes of "component" element
                component.setAttribute(NAME_ATTR, entry.getTargetComponent().getName());
                component.setAttribute(VERSION_ATTR, entry.getTargetComponent().getVersion());
                component.setAttribute(LANGUAGE_ATTR, entry.getTargetComponent().getLanguage().name().toLowerCase());

                for (ComponentDependency dependency : entry.getDependencies()) {
                    //Create element "dependency"
                    Element dependencyElement = doc.createElement(DEPENDENCY_ELEMENT);

                    //Set "type" attribute of "dependency" element
                    if (this.isDependencyTypeIncluded()) {
                        dependencyElement.setAttribute(TYPE_ATTR, dependency.getType().name().toLowerCase());
                    }

                    //Set "category" attribute of "dependency" element
                    if (this.isDependencyCategoryIncluded()) {
                        dependencyElement.setAttribute(CATEGORY_ATTR, dependency.getCategory().name().toLowerCase());
                    }

                    //Set "name" attribute of "dependency" element
                    dependencyElement.setAttribute(NAME_ATTR, dependency.getName());

                    //Set "version" attribute of "dependency" element
                    dependencyElement.setAttribute(VERSION_ATTR, dependency.getVersion());

                    //Set "path" attribute of "dependency" element
                    if (this.isDependencyPathIncluded()) {
                        dependencyElement.setAttribute(PATH_ATTR, dependency.getPath());
                    }

                    //Append "dependency" element as child of "component" element
                    component.appendChild(dependencyElement);
                }

                //Append "component" element as child of root element
                root.appendChild(component);
            }
            return doc;
        } catch (FactoryConfigurationError e) {
            throw new DependencyReportGenerationException(
                "Error occurs while instantiating DocumentBuilderFactory.", e);
        } catch (ParserConfigurationException e) {
            throw new DependencyReportGenerationException("Error occurs while instantiating DocumentBuilder.", e);
        }
    }
}
