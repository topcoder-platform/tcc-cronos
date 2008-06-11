/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.persistence;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentDependencyConfigurationException;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.DependenciesEntryPersistenceException;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.dependency.Utils;

/**
 * <p>
 * This is a default implementation of DependenciesEntryPersistence. It loads (saves) dependency entries from (to) XML
 * files. Indentation is used while formatting the XML file, so it can be easily read by the user.
 * </p>
 * <p>
 * Thread Safety: This class is immutable and thread safe when multiple instances of it don't use the same file
 * simultaneously.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultXmlDependenciesEntryPersistence implements DependenciesEntryPersistence {
    /**
     * <p>
     * Constant for components tag name.
     * </p>
     */
    private static final String TAG_COMPONENTS = "components";

    /**
     * <p>
     * Constant for component tag name.
     * </p>
     */
    private static final String TAG_COMPONENT = "component";

    /**
     * <p>
     * Constant for dependency tag name.
     * </p>
     */
    private static final String TAG_DEPENDENCY = "dependency";

    /**
     * <p>
     * Constant for attribute name.
     * </p>
     */
    private static final String ATTRIBUTE_NAME = "name";

    /**
     * <p>
     * Constant for attribute version.
     * </p>
     */
    private static final String ATTRIBUTE_VERSION = "version";

    /**
     * <p>
     * Constant for attribute language.
     * </p>
     */
    private static final String ATTRIBUTE_LANGUAGE = "language";

    /**
     * <p>
     * Constant for attribute type.
     * </p>
     */
    private static final String ATTRIBUTE_TYPE = "type";

    /**
     * <p>
     * Constant for attribute category.
     * </p>
     */
    private static final String ATTRIBUTE_CATEGORY = "category";

    /**
     * <p>
     * Constant for attribute path.
     * </p>
     */
    private static final String ATTRIBUTE_PATH = "path";

    /**
     * <p>
     * Constant for language value : java.
     * </p>
     */
    private static final String LANGUAGE_JAVA = "java";

    /**
     * <p>
     * Constant for language value: dot_net.
     * </p>
     */
    private static final String LANGUAGE_DOT_NET = "dot_net";

    /**
     * <p>
     * Constant for type value: internal.
     * </p>
     */
    private static final String TYPE_INTERNAL = "internal";

    /**
     * <p>
     * Constant for type value: external.
     * </p>
     */
    private static final String TYPE_EXTERNAL = "external";

    /**
     * <p>
     * Constant for category value: compile.
     * </p>
     */
    private static final String CATEGORY_COMPILE = "compile";

    /**
     * <p>
     * Constant for category value: test.
     * </p>
     */
    private static final String CATEGORY_TEST = "test";

    /**
     * <p>
     * Configuration key : The name of the file where the list of dependency entries is (can be) stored. The value for
     * this key is String and not empty and required.
     * </p>
     */
    private static final String KEY_FILE_NAME = "file_name";

    /**
     * <p>
     * The path of the file that is used for storing the list of dependency entries in XML format. Is initialized in the
     * constructor and never changed after that. Cannot be null or empty. Is used in load() and save().
     * </p>
     */
    private final String filePath;

    /**
     * <p>
     * Creates an instance of DefaultXmlDependenciesEntryPersistence with the given file path.
     * </p>
     *
     * @param filePath the persistence file path(cannot be null or empty)
     *
     * @throws IllegalArgumentException if filePath is null or empty
     */
    public DefaultXmlDependenciesEntryPersistence(String filePath) {
        Utils.checkStringNullOrEmpty(filePath, "filePath");
        this.filePath = filePath;
    }

    /**
     * <p>
     * Creates an instance of DefaultXmlDependenciesEntryPersistence from the given configuration object.
     * </p>
     *
     * @param configuration the configuration object for this class (cannot be null)
     *
     * @throws IllegalArgumentException if configuration is null
     * @throws ComponentDependencyConfigurationException if error occurred while reading the configuration
     */
    public DefaultXmlDependenciesEntryPersistence(ConfigurationObject configuration)
        throws ComponentDependencyConfigurationException {
        Utils.checkNull(configuration, "configuration");
        this.filePath = Utils.getRequiredStringValue(configuration, KEY_FILE_NAME);
    }

    /**
     * <p>
     * Loads the list of dependency entries from the XML file with the given name.
     * </p>
     *
     * @return the loaded list of entries (cannot be null; cannot contain null)
     *
     * @throws DependenciesEntryPersistenceException if file doesn't exist, has invalid format or I/O error occurred
     */
    public List<DependenciesEntry> load() throws DependenciesEntryPersistenceException {
        // see CS 1.3.4
        File file = new File(filePath);
        if (!file.isFile()) {
            throw new DependenciesEntryPersistenceException("file <" + filePath + "> is not an existing file.");
        }

        try {
            Document doc = newDocumentBuilder().parse(file);
            Element root = doc.getDocumentElement();
            if (!TAG_COMPONENTS.equals(root.getTagName())) {
                throw new DependenciesEntryPersistenceException("The document should start with 'components' element.");
            }

            List<DependenciesEntry> result = new ArrayList<DependenciesEntry>();

            // see http://forums.topcoder.com/?module=Thread&threadID=615251&start=0
            NodeList componentList = root.getChildNodes();
            for (int i = 0; i < componentList.getLength(); i++) {
                Element componentElement = getElement(componentList.item(i), TAG_COMPONENT);
                if (componentElement == null) {
                    continue;
                }
                // read component
                String componentName = getNotEmptyAttributeValue(componentElement, ATTRIBUTE_NAME);
                String componentVersion = componentElement.getAttribute(ATTRIBUTE_VERSION);
                ComponentLanguage language = getLanguage(componentElement.getAttribute(ATTRIBUTE_LANGUAGE));
                Component component = new Component(componentName, componentVersion, language);

                // get dependencies
                List<ComponentDependency> dependencies = new ArrayList<ComponentDependency>();
                NodeList dependencyList = componentElement.getChildNodes();
                for (int j = 0; j < dependencyList.getLength(); j++) {
                    Element dependencyElement = getElement(dependencyList.item(j), TAG_DEPENDENCY);
                    if (dependencyElement == null) {
                        continue;
                    }
                    String name = getNotEmptyAttributeValue(dependencyElement, ATTRIBUTE_NAME);
                    String version = dependencyElement.getAttribute(ATTRIBUTE_VERSION);
                    String path = getNotEmptyAttributeValue(dependencyElement, ATTRIBUTE_PATH);
                    DependencyType type = getType(dependencyElement.getAttribute(ATTRIBUTE_TYPE));
                    DependencyCategory category = getCategory(dependencyElement.getAttribute(ATTRIBUTE_CATEGORY));
                    dependencies.add(new ComponentDependency(name, version, language, category, type, path));
                }

                // create a new entry
                DependenciesEntry entry = new DependenciesEntry(component, dependencies);
                result.add(entry);
            }

            return result;
        } catch (SAXException e) {
            throw new DependenciesEntryPersistenceException("SAXException occurs while parsing document<" + filePath
                + "> : " + e.getMessage(), e);
        } catch (IOException e) {
            throw new DependenciesEntryPersistenceException("IOException occurs while parsing document<" + filePath
                + "> : " + e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Converts to <code>Element</code> from passed <code>Node</code>.
     * </p>
     *
     * @param node to be converted
     * @param tagName tag name which is supposed to be
     * @return <code>Element</code> node. It could be null if it is an empty <code>TextNode</code>.
     *
     * @throws DependenciesEntryPersistenceException if it is not an element or empty value text node
     */
    private Element getElement(Node node, String tagName) throws DependenciesEntryPersistenceException {
        // see http://forums.topcoder.com/?module=Thread&threadID=615251&start=0
        // it could be empty text node and we will return null
        if (Node.TEXT_NODE == node.getNodeType() && Utils.isStringNullOrEmpty(node.getNodeValue())) {
            return null;
        }
        if (!(node instanceof Element)) {
            throw new DependenciesEntryPersistenceException("node should be Element.");
        }
        Element element = (Element) node;
        if (!tagName.equals(element.getTagName())) {
            throw new DependenciesEntryPersistenceException("node should be '" + tagName + "' Element.");
        }

        return element;
    }

    /**
     * <p>
     * Gets attribute value which should not be empty.
     * </p>
     *
     * @param element which it gets attribute from
     * @param attributeName attribute name
     * @return attribute value
     *
     * @throws DependenciesEntryPersistenceException if the value is empty
     */
    private String getNotEmptyAttributeValue(Element element, String attributeName)
        throws DependenciesEntryPersistenceException {
        String value = element.getAttribute(attributeName);
        if (Utils.isStringNullOrEmpty(value)) {
            throw new DependenciesEntryPersistenceException(attributeName + " attribute should not be empty.");
        }
        return value;
    }

    /**
     * <p>
     * Gets language value from language string.
     * </p>
     *
     * @param languageStr language string
     * @return language value
     *
     * @throws DependenciesEntryPersistenceException if no corresponding value could be found
     */
    private ComponentLanguage getLanguage(String languageStr) throws DependenciesEntryPersistenceException {
        if (LANGUAGE_JAVA.equals(languageStr)) {
            return ComponentLanguage.JAVA;
        } else if (LANGUAGE_DOT_NET.equals(languageStr)) {
            return ComponentLanguage.DOT_NET;
        } else {
            throw new DependenciesEntryPersistenceException("language string<" + languageStr + "> is invalid.");
        }
    }

    /**
     * <p>
     * Gets type value from type string.
     * </p>
     *
     * @param typeStr type string
     * @return type value
     *
     * @throws DependenciesEntryPersistenceException if no corresponding value could be found
     */
    private DependencyType getType(String typeStr) throws DependenciesEntryPersistenceException {
        if (TYPE_INTERNAL.equals(typeStr)) {
            return DependencyType.INTERNAL;
        } else if (TYPE_EXTERNAL.equals(typeStr)) {
            return DependencyType.EXTERNAL;
        } else {
            throw new DependenciesEntryPersistenceException("type string<" + typeStr + "> is invalid.");
        }
    }

    /**
     * <p>
     * Gets category value from category string.
     * </p>
     *
     * @param categoryStr category string
     * @return category value
     *
     * @throws DependenciesEntryPersistenceException if no corresponding value could be found
     */
    private DependencyCategory getCategory(String categoryStr) throws DependenciesEntryPersistenceException {
        if (CATEGORY_COMPILE.equals(categoryStr)) {
            return DependencyCategory.COMPILE;
        } else if (CATEGORY_TEST.equals(categoryStr)) {
            return DependencyCategory.TEST;
        } else {
            throw new DependenciesEntryPersistenceException("category string<" + categoryStr + "> is invalid.");
        }
    }

    /**
     * <p>
     * Saves the provided list of dependency entries to the XML file with the given name.
     * </p>
     *
     * @param entries the list of entries to be saved (cannot be null; cannot contain null)
     *
     * @throws IllegalArgumentException if entries is null or contains null
     * @throws DependenciesEntryPersistenceException if I/O error occurred
     */
    public void save(List<DependenciesEntry> entries) throws DependenciesEntryPersistenceException {
        // some detail is in section 1.3.3 of CS
        Utils.checkCollection(entries, "entries", true);

        try {
            DocumentBuilder docBuilder = newDocumentBuilder();
            // create document
            Document doc = docBuilder.newDocument();
            // root element
            Element root = doc.createElement(TAG_COMPONENTS);
            doc.appendChild(root);
            // add all components
            for (DependenciesEntry entry : entries) {
                Element component = doc.createElement(TAG_COMPONENT);
                component.setAttribute(ATTRIBUTE_NAME, entry.getTargetComponent().getName());
                component.setAttribute(ATTRIBUTE_VERSION, entry.getTargetComponent().getVersion());
                component.setAttribute(ATTRIBUTE_LANGUAGE,
                    entry.getTargetComponent().getLanguage() == ComponentLanguage.JAVA ? LANGUAGE_JAVA
                        : LANGUAGE_DOT_NET);

                // add all dependencies
                for (ComponentDependency dependency : entry.getDependencies()) {
                    Element dependencyElement = doc.createElement(TAG_DEPENDENCY);
                    dependencyElement.setAttribute(ATTRIBUTE_NAME, dependency.getName());
                    dependencyElement.setAttribute(ATTRIBUTE_VERSION, dependency.getVersion());
                    dependencyElement.setAttribute(ATTRIBUTE_TYPE,
                        dependency.getType() == DependencyType.INTERNAL ? TYPE_INTERNAL : TYPE_EXTERNAL);
                    dependencyElement.setAttribute(ATTRIBUTE_CATEGORY,
                        dependency.getCategory() == DependencyCategory.COMPILE ? CATEGORY_COMPILE : CATEGORY_TEST);
                    dependencyElement.setAttribute(ATTRIBUTE_PATH, dependency.getPath());
                    // add dependency
                    component.appendChild(dependencyElement);
                }

                // add component
                root.appendChild(component);
            }
            // rendering
            TransformerFactory tfFactory = TransformerFactory.newInstance();
            Transformer transformer = tfFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            OutputStream os = new BufferedOutputStream(new FileOutputStream(filePath));
            try {
                StreamResult result = new StreamResult(os);
                DOMSource source = new DOMSource(doc);
                transformer.transform(source, result);
            } finally {
                Utils.closeOutputStreamSafely(os);
            }
        } catch (TransformerConfigurationException e) {
            throw new DependenciesEntryPersistenceException(
                "TransformerConfigurationException occurs while creating new transformer : " + e.getMessage(), e);
        } catch (TransformerException e) {
            throw new DependenciesEntryPersistenceException(
                "TransformerException occurs while transforming the document : " + e.getMessage(), e);
        } catch (FileNotFoundException e) {
            throw new DependenciesEntryPersistenceException(
                "FileNotFoundException occurs while creating a file output stream : " + e.getMessage(), e);
        }

    }

    /**
     * <p>
     * Creates a new document builder. There is no grantee for thread safety for returned new document factory or
     * document builder so for each call, it is better to get a new document builder.
     * </p>
     *
     * @return document builder
     *
     * @throws DependenciesEntryPersistenceException if any error occurs while creating new document builder
     */
    private DocumentBuilder newDocumentBuilder() throws DependenciesEntryPersistenceException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            return dbFactory.newDocumentBuilder();
        } catch (FactoryConfigurationError e) {
            throw new DependenciesEntryPersistenceException(
                "FactoryConfigurationError occurs while creating new document factory : " + e.getMessage(), e);
        } catch (ParserConfigurationException e) {
            throw new DependenciesEntryPersistenceException(
                "ParserConfigurationException occurs while creating new document builder : " + e.getMessage(), e);
        }
    }
}
