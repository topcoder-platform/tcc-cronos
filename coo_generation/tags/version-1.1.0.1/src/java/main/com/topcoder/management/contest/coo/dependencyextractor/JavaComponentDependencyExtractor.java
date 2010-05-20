/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.dependencyextractor;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
 * This class represents Java Component Dependency Extractor. It implements
 * <code>ComponentDependencyExtractor</code> interface and provide an
 * implementation to extract component dependencies from Java build file
 * </p>
 * <p>
 * It uses DOM XML parser to parse the build file and extract the dependencies.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * //create JavaComponentDependencyExtractor from configuration
 * JavaComponentDependencyExtractor extractor = new JavaComponentDependencyExtractor(configuration);
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
public class JavaComponentDependencyExtractor implements
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
    public JavaComponentDependencyExtractor(ConfigurationObject configuration)
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
        final String signature = "JavaComponentDependencyExtractor#extractDependencies";
        Helper.logEnter(logger, signature);
        Helper.checkNull(logger, "input", input);
        try {
            // map to store properties
            Map<String, String> properties = new HashMap<String, String>();
            // store the result
            List<ComponentDependency> list = new ArrayList<ComponentDependency>();
            // get the document from the input stream
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            // get root element
            Element root = document.getDocumentElement();
            // if only root found,throw exception
            if (root.getChildNodes().getLength() == 1) {
                throw new ComponentDependencyExtractorException("the XML file do not contain any child element.");
            }
            // loop through each child element and process it
            for (int i = 0; i < root.getChildNodes().getLength(); i++) {
                // simply add any property to the map
                Object obj = root.getChildNodes().item(i);
                if (((Node) obj).getNodeType() == Node.ELEMENT_NODE) {
                    Element child = (Element) obj;
                    if (child.getTagName().equals("property")) {
                        properties.put(child.getAttribute("name"), child.getAttribute("value"));
                    }
                    String id = child.getAttribute("id");
                    // process internal compile dependencies
                    if (id.equals("component.tcs-dependencies")) {
                        // category to COMPILE, type to INTERNAL
                        addDependency(list, child, DependencyCategory.COMPILE, DependencyType.INTERNAL, properties);
                    } else if (id.equals("component.3rdParty-dependencies")) {
                        // category to COMPILE, type to EXTERNAL
                        addDependency(list, child, DependencyCategory.COMPILE, DependencyType.EXTERNAL, properties);
                    } else if (id.equals("component.test.3rdParty-dependencies")) {
                        // category to TEST, type to EXTERNAL
                        addDependency(list, child, DependencyCategory.TEST, DependencyType.EXTERNAL, properties);
                    } else if (id.equals("component.test.dependencies")) {
                        // category to TEST, type to INTERNAL
                        addDependency(list, child, DependencyCategory.TEST, DependencyType.INTERNAL, properties);
                    }
                }
            }
            return list;
        } catch (SAXException e) {
            throw Helper.logError(logger, new ComponentDependencyExtractorException("fail to parse the document", e));
        } catch (IOException e) {
            throw Helper.logError(logger, new ComponentDependencyExtractorException("fail to do I/O operation", e));
        } catch (ParserConfigurationException e) {
            throw Helper.logError(logger, new ComponentDependencyExtractorException("fail to parse configuration", e));
        } finally {
            Helper.logExit(logger, signature);
        }
    }

    /**
     * Get the component dependency from the given path and the given category
     * as well as the given type.
     *
     * @param path The path to the dependency.
     * @param category The category of the dependency.
     * @param type The type of the dependency.
     * @return ComponentDependency instance representing the dependency
     *
     */
    private ComponentDependency getComponentDependency(String path,
            DependencyCategory category, DependencyType type) {
        // process path element. create ComponentDependency accordingly
        ComponentDependency dep = new ComponentDependency();
        dep.setCategory(category);
        dep.setType(type);
        Component component = new Component();
        // extract name and version from path.
        extractNameAndVersion(path, component);
        // log dependency information
        Helper.logInfo(logger, MessageFormat.format("Extracted dependency: name[{0}], version[{1}], "
                + "category[{2}], type[{3}].", component.getName(), component.getVersion(), category, type));
        dep.setComponent(component);
        return dep;
    }

    /**
     * <p>
     * extract name and version from path.
     * </p>
     *
     * @param path extracted from file.
     * @param component the component.
     */
    private void extractNameAndVersion(String path, Component component) {
        // parse path to get name and version.
        // regular express match version
        String regex = "([0-9]+)[\\.-]([0-9]+)([\\.-][0-9]+)*(.GA)?";
        String[] strs = path.split("/");

        // now path is something like "mysql-connector-java-5.1.7-bin.jar",
        path = strs[strs.length - 1];

        // now name is something like "mysql-connector-java--bin.jar"
        String name = path.replaceFirst(regex, "");

        // remove file extension
        // now name is something like "mysql-connector-java--bin"
        name = name.substring(0, name.lastIndexOf("."));

        // now we extract the version "5.1.7"
        // string index to iterator
        int idx = 0;
        // the begin index of version in path
        int beginIndex = 0;
        // the end index of version in path
        int endIndex = 0;
        // get begin index of version from the beginning of path
        while (idx < path.length() && idx < name.length()) {
            // if the character is different,get the begin index.
            if (path.charAt(idx) != name.charAt(idx)) {
                beginIndex = idx;
                break;
            }
            idx++;
        }
        idx = 0;
        // get end index of version from the end of path
        while (idx < path.length() && idx < name.length()) {
            // if the character is different,
            if (path.charAt(path.length() - 1 - idx) != name.charAt(name.length()
                    - 1 - idx)) {
                // change it to end index
                endIndex = path.length() - idx;
                break;
            }
            idx++;
        }
        // now we get the version "5.1.7" if version exist in name.
        String version = path.substring(beginIndex, endIndex);

        // we need confirm whether the version is extracted successfully from
        // file name.
        if (!Pattern.compile(regex).matcher(version).matches()) {
            version = path.substring(path.lastIndexOf('-') + 1, path.lastIndexOf('.'));
            if (!Pattern.compile(regex).matcher(version).matches()) {
                version = null;
                // if version doesn't exist in the file name.we need get it from path.
                for (int i = 0; i < strs.length - 1; i++) {
                    if (Pattern.compile(regex).matcher(strs[i]).matches()) {
                        version = strs[i];
                        break;
                    }
                }
            }
        }
        if (name.endsWith("-")) {
            name = name.substring(0, name.length() - 1);
        }
        component.setName(name);
        component.setVersion(version);
    }

    /**
     * add ComponentDependency in list.
     *
     * @param list ComponentDependency to add
     * @param element the Element in xml string.
     * @param category the DependencyCategory.
     * @param type the DependencyType.
     * @param properties property map.
     */
    private void addDependency(List<ComponentDependency> list, Element element,
            DependencyCategory category, DependencyType type,
            Map<String, String> properties) {

        // process internal compile dependencies
        for (int j = 0; j < element.getChildNodes().getLength(); j++) {
            Node node = element.getChildNodes().item(j);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element child = (Element) node;
                if (child.getTagName().equals("pathelement")) {
                    String loc = child.getAttribute("location");
                    String path;
                    if (loc.startsWith("$")) {
                        // remove $.{,} from path
                        loc = loc.replace("$", "").replace("{", "").replace("}", "");
                        path = properties.get(loc);
                    } else {
                        path = loc;
                    }
                    if (path != null && !path.equals(""))
                    {
                        list.add(getComponentDependency(path, category, type));
                    } else {
                        list.add(null);
                    }
                } else {
                    list.add(null);
                }
            }
        }

    }
}
