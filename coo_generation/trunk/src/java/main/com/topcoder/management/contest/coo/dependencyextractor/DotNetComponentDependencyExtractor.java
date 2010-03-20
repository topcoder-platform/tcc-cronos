/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.dependencyextractor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.Component;
import com.topcoder.management.contest.coo.ComponentDependency;
import com.topcoder.management.contest.coo.ComponentDependencyExtractor;
import com.topcoder.management.contest.coo.ComponentDependencyExtractorException;
import com.topcoder.management.contest.coo.DependencyCategory;
import com.topcoder.management.contest.coo.DependencyType;
import com.topcoder.management.contest.coo.Helper;
import com.topcoder.management.contest.coo.impl.ConfigurationException;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class represents .NET Component Dependency Extractor. It implements
 * <code>ComponentDependencyExtractor</code> interface and provide an
 * implementation to extract component dependencies from .NET build file
 * </p>
 * <p>
 * It uses DOM XML parser to parse the build file and extract the dependencies.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * //create DotNetComponentDependencyExtractor from configuration
 * DotNetComponentDependencyExtractor extractor = new DotNetComponentDependencyExtractor(configuration);
 *
 * //retrieve component dependency from some input stream.
 * List&lt;ComponentDependency&gt; dependency = extractor.extractDependencies(input);
 *
 * </pre>
 *
 * </p>
 * <p>
 * <strong>Thread safe:</strong> This class is thread safe since it is
 * stateless.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public class DotNetComponentDependencyExtractor implements
        ComponentDependencyExtractor {
    /**
     * <p>
     * Represents the logger to be used.
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed. Can be null to indicate
     * no logging.
     * </p>
     */
    private final Log logger;

    /**
     * <p>
     * Constructor. Initialize instance variables.
     * </p>
     *
     * @param configuration The configuration object. must not be null.
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ConfigurationException if any error in configuration/missing
     *             value/etc
     *
     */
    public DotNetComponentDependencyExtractor(ConfigurationObject configuration)
        throws ConfigurationException {
        Helper.checkNull("configuration", configuration);
        // get logger instance
        this.logger = Helper.getLogger(configuration);
    }

    /**
     * <p>
     * Extract the dependencies from the given build file (represented as input
     * stream).
     * </p>
     *
     * @param input The input stream of the build file. must not be null.
     * @return list of dependencies extracted from the build file
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ComponentDependencyExtractorException if there is error in
     *             performing this method.
     *
     */
    public List<ComponentDependency> extractDependencies(InputStream input)
        throws ComponentDependencyExtractorException {
        // signature for logging.
        final String signature = "DotNetComponentDependencyExtractor#extractDependencies";
        Helper.logEnter(logger, signature);
        // do check
        Helper.checkNull(logger, "inputStream", input);
        // store the result
        List<ComponentDependency> list;
        try {
            list = new ArrayList<ComponentDependency>();

            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // get the document from the input stream
            Document document = parser.parse(input);

            // get root element
            Element root = document.getDocumentElement();

            // if only root found,throw exception
            if (root.getChildNodes().getLength() == 1) {
                throw new ComponentDependencyExtractorException("the XML file do not contain any child element.");
            }

            // loop through each child element and process it
            for (int i = 0; i < root.getChildNodes().getLength(); i++) {
                // For each child:Element from root children do
                Object obj = root.getChildNodes().item(i);
                if (((Node) obj).getNodeType() == Node.ELEMENT_NODE) {
                    Element child = (Element) obj;
                    // get conditional element "When" under "Choose" tag.
                    if (child.getTagName().equals("Choose")) {
                        extractChoose(child, list);
                    } else if (child.getTagName().equals("ItemGroup")) {
                        // process ItemGroup element.
                        extractItemGroup(child, list);
                    } else if (child.getTagName().equals("Reference")) {
                        // process direct Reference element.
                        NodeList node = child.getChildNodes();
                        Element child3 = (Element) node;
                        if (child3.getTagName().equals("HintPath")) {
                            DependencyCategory category = DependencyCategory.COMPILE;
                            list.add(this.getComponentDependency(child3.getTextContent(), category));
                        }
                    }
                }
            }
            return list;
        } catch (DOMException e) {
            throw Helper.logError(logger, new ComponentDependencyExtractorException("fail to parse the"
                    + " document object in XML dependency file.", e));
        } catch (IOException e) {
            throw Helper.logError(logger, new ComponentDependencyExtractorException("fail to do some "
                    + "I/O operation", e));
        } catch (ParserConfigurationException e) {
            throw Helper.logError(logger, new ComponentDependencyExtractorException("fail to parse config.", e));
        } catch (SAXException e) {
            throw Helper.logError(logger, new ComponentDependencyExtractorException("fail to parse input document,"
                    + "SAXException occur", e));
        } finally {
            Helper.logExit(logger, signature);
        }

    }

    /**
     * <p>
     * dealing with the "Choose" element and extract the "ComponentSources" and
     * "ComponentTests" elements underneath.
     * </p>
     *
     * @param child of node list.
     * @param list component dependency list
     * @throws ComponentDependencyExtractorException if fail to find "When"
     *             element under "Choose" element.
     */
    private void extractChoose(Element child, List<ComponentDependency> list)
        throws ComponentDependencyExtractorException {
        NodeList nd = child.getElementsByTagName("When");
        // if fail to find "When" element under "Choose".
        if (nd.getLength() == 0) {
            throw new ComponentDependencyExtractorException("no \"When\" element found under \"Choose\" element.");
        }
        for (int idx = 0; idx < nd.getLength(); idx++) {
            child = (Element) nd.item(idx);
            // process the conditional element
            if (child.getTagName().equals("When")) {
                String condition = child.getAttribute("Condition");
                condition = condition.substring(condition.indexOf("'")).replace('\'', ' ').trim();
                // Parse the Reference elements and add to
                // dependency list
                if (condition.equals("ComponentSources")) {
                    // this is for compile dependency category.
                    extractReference(child, list, DependencyCategory.COMPILE);
                } else if (condition.equals("ComponentTests")) {
                    // this is for test dependency category.
                    extractReference(child, list, DependencyCategory.TEST);
                }
            }
        }
    }

    /**
     * <p>
     * extract the "ItemGroup" element and add the "Reference" elements
     * underneath and add to dependency list.
     * </p>
     *
     * @param child of node list.
     * @param list component dependency list
     */
    private void extractItemGroup(Element child,
            List<ComponentDependency> list) {
        // process ItemGroup element.
        NodeList node = child.getChildNodes();
        for (int j = 0; j < node.getLength(); j++) {
            if ((node.item(j)).getNodeType() == Node.ELEMENT_NODE) {
                Element child2 = (Element) node.item(j);
                // add the "Reference" elements underneath
                if (child2.getTagName().equals("Reference")) {
                    NodeList node2 = child2.getChildNodes();
                    for (int k = 0; k < node2.getLength(); k++) {
                        if ((node2.item(k)).getNodeType() == Node.ELEMENT_NODE) {
                            // extract "HintPath" node
                            extractHintPath(node2.item(k), list, DependencyCategory.COMPILE);
                        }
                    }
                }
            }
        }
    }

    /**
     * <p>
     * extract the "Reference" element and add it to component dependency list.
     * </p>
     *
     * @param child of node list
     * @param list component dependency list
     * @param category dependency category
     */
    private void extractReference(Element child,
            List<ComponentDependency> list, DependencyCategory category) {
        NodeList node = child.getElementsByTagName("Reference");
        for (int j = 0; j < node.getLength(); j++) {
            if ((node.item(j)).getNodeType() == Node.ELEMENT_NODE) {
                Element child2 = (Element) node.item(j);
                if (child2.getTagName().equals("Reference")) {
                    NodeList node2 = child2.getChildNodes();
                    for (int k = 0; k < node2.getLength(); k++) {
                        if ((node2.item(k)).getNodeType() == Node.ELEMENT_NODE) {
                            // extract "HintPath" node
                            extractHintPath(node2.item(k), list, category);
                        }
                    }
                }
            }
        }
    }

    /**
     * <p>
     * extract the "HintPath" element and add it to component dependency list.
     * </p>
     *
     * @param node the node
     * @param list component dependency list
     * @param category dependency category
     */
    private void extractHintPath(Node node, List<ComponentDependency> list,
            DependencyCategory category) {
        Element element = (Element) node;
        if (element.getTagName().equals("HintPath")) {
            list.add(this.getComponentDependency(element.getTextContent(), category));
        }
    }

    /**
     * <p>
     * Get the component dependency from the given path and the given category.
     * </p>
     *
     * @param path The path to the dependency.
     * @param category The category of the dependency.
     * @return ComponentDependency instance representing the dependency
     *
     */
    private ComponentDependency getComponentDependency(String path,
            DependencyCategory category) {
        // create ComponentDependency
        ComponentDependency dep = new ComponentDependency();

        // set category
        dep.setCategory(category);

        // set dependency type
        if (path.toLowerCase().contains("topcoder")) {
            dep.setType(DependencyType.INTERNAL);
        } else {
            dep.setType(DependencyType.EXTERNAL);
        }

        // regular express match version
        String regex = "([0-9]+)[\\.-]([0-9]+)([\\.-][0-9]+)*";
        int idx = path.lastIndexOf(File.separator) + 1;
        String name;
        String version = null;
        if (idx != 0) {
            // path like "$(ext_bin)\log4net\1.2.9\log4net.dll"
            // get the filename from path (exclude extension)
            name = path.substring(idx).replace(".dll", "");
            path = path.substring(0, idx - 1);
            // get version from path
            int idx2 = path.lastIndexOf(File.separator) + 1;
            if (idx2 != 0) {
                version = path.substring(idx2);
            }
            if (!Pattern.compile(regex).matcher(version).matches()) {
                version = null;
            }
        } else {
            // dealing with path like "$(NUnit)"
            name = path.replace("$", "").replace("(", "").replace(")", "");
        }

        Component component = new Component();
        component.setName(name);
        component.setVersion(version);
        dep.setComponent(component);

        // log extracted dependency from dependency file
        Helper.logInfo(logger, MessageFormat.format("Extracted dependency: name[{0}],version[{1}],"
                + " category[{2}], type[{3}].", name, version, category, dep.getType()));
        return dep;
    }

}
