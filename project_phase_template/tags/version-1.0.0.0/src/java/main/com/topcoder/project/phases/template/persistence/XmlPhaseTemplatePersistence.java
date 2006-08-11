/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.persistence;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.PersistenceException;
import com.topcoder.project.phases.template.PhaseGenerationException;
import com.topcoder.project.phases.template.PhaseTemplatePersistence;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * <code>XmlPhaseTemplatePersistence</code> is an XML based persistence
 * implementation of <code>{@link PhaseTemplatePersistence}</code>.
 * </p>
 * <p>
 * Phase templates are stored in XML files, each XML file defines one template,
 * the XML schema is defined in docs/xml_phase_templte.xsd, and also a sample
 * template is provided in docs directory. At the instantiation time, the template
 * XML files will be validated against the schema, the schema file should be named as
 * "xml_phase_template.xsd" and placed in the root path of CLASSPATH(the first choice
 * is to include it in root path of the package JAR), or placed in current file path.
 * </p>
 * <p>
 * Each template is assigned a template name.
 * Inside the <code>XmlPhaseTemplatePersistence</code>, templates are stored in a
 * <code>Map</code> with the template name as the key, and with <code>org.w3c.dom.Document</code>
 * objects parsed from the XML document as the value.
 * </p>
 * <p>
 * The persistence can be created from configurations with the configured XML
 * template file paths, instance can also be created programatically.
 * Please consult the component specification and sample configuration file
 * (docs/Xml_Phase_Template_Persistence.xml) for configuration details.
 * </p>
 * <p>
 * Note that the phase start dates, phase statuses will NOT be included in the template,
 * so these information will NOT be populated to the phases, they should be set by the
 * calling application.
 * </p>
 * <p>
 * This class is thread safe, as it is immutable and the templates are readonly.
 * </p>
 * @author albertwang, flying2hk
 * @version 1.0
 */
public class XmlPhaseTemplatePersistence implements PhaseTemplatePersistence {
    /**
     * <p>
     * Represents the property key of "template_files".
     * </p>
     */
    private static final String KEY_TEMPLATE_FILES = "template_files";
    /**
     * <p>
     * Represents the tag name "PhaseType".
     * </p>
     */
    private static final String TAG_PHASE_TYPE = "PhaseType";
    /**
     * <p>
     * Represents the tag name "Dependency".
     * </p>
     */
    private static final String TAG_DEPENDENCY = "Dependency";
    /**
     * <p>
     * Represents the tag name "Phase".
     * </p>
     */
    private static final String TAG_PHASE = "Phase";
    /**
     * <p>
     * Represents the attribute name "typeName".
     * </p>
     */
    private static final String ATTRIBUTE_TYPE_NAME = "typeName";
    /**
     * <p>
     * Represents the attribute name "typeId".
     * </p>
     */
    private static final String ATTRIBUTE_TYPE_ID = "typeId";
    /**
     * <p>
     * Represents the attribute name "id".
     * </p>
     */
    private static final String ATTRIBUTE_ID = "id";
    /**
     * <p>
     * Represents the attribute name "type".
     * </p>
     */
    private static final String ATTRIBUTE_TYPE = "type";
    /**
     * <p>
     * Represents the attribute name "length".
     * </p>
     */
    private static final String ATTRIBUTE_LENGTH = "length";
    /**
     * <p>
     * Represents the attribute name "isDependencyStart".
     * </p>
     */
    private static final String ATTRIBUTE_IS_DEPENDENCY_START = "isDependencyStart";
    /**
     * <p>
     * Represents the attribute name "isDependentStart".
     * </p>
     */
    private static final String ATTRIBUTE_IS_DEPENDENT_START = "isDependentStart";
    /**
     * <p>
     * Represents the attribute name "lagTime".
     * </p>
     */
    private static final String ATTRIBUTE_LAG_TIME = "lagTime";
    /**
     * <p>
     * Represents the attribute name "name".
     * </p>
     */
    private static final String ATTRIBUTE_NAME = "name";
    /**
     * <p>
     * XML Schema file(XSD schema) for the validation of persistence file.
     * This implementation will try to find the schema file in the classpath,
     * if it is not found in classpath, then try to find in the relative file path.
     * </p>
     * <p>
     * The schema file should be placed in the root path of CLASSPATH(the first choice
     * is to include it in root path of the package JAR), or placed in current file path.
     * </p>
     */
    private static final String SCHEMA_FILE = "xml_phase_template.xsd";

    /**
     * <p>
     * Represents the mapping from template names to the templates.
     * </p>
     * <p>
     * The keys are strings, the values are <code>org.w3c.dom.Document</code> objects parsed
     * from the XML based template files.
     * </p>
     * <p>
     * It is initialized in place, and can not be modified afterwards,
     * it can never be null, can be empty Map if there's no template defined.
     * </p>
     */
    private final Map templates = new HashMap();

    /**
     * <p>
     * Create an <code>XmlPhaseTemplatePersistence</code> from the given
     * configuration namespace.
     * </p>
     * <p>
     * The templates will be loaded from the configured persistence files,
     * note that the persistence XML files will be validated against the
     * XSD schema.
     * </p>
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if the namespace is null or empty string
     * @throws ConfigurationException if any error occurs(e.g. the required
     *             configuration properties is missing, the configured xml file
     *             is malformed, etc) so that the persistence can not be
     *             initialized successfully
     */
    public XmlPhaseTemplatePersistence(String namespace) throws ConfigurationException {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace can not be null!");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace can not be empty string!");
        }

        try {
            // retrieve template_files from configuration
            ConfigManager cm = ConfigManager.getInstance();
            String[] templateFiles = cm.getStringArray(namespace, KEY_TEMPLATE_FILES);

            // initialize the templates
            this.initialzeTemplates(templateFiles);
        } catch (PersistenceException e) {
            throw new ConfigurationException("Persistence error occured while initializing the persistence.", e);
        } catch (ParserConfigurationException e) {
            throw new ConfigurationException("Error occured while parsing the XML persistence file.", e);
        } catch (SAXException e) {
            throw new ConfigurationException("Error occured while parsing the XML persistence file.", e);
        } catch (IOException e) {
            throw new ConfigurationException("Error occured while accessing the XML persistence file.", e);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("Error occured while initializing the persistence.", e);
        }
    }

    /**
     * <p>
     * Create an XmlPhaseTemplatePersistence with the given xml template file
     * names.
     * </p>
     * <p>
     * The templates will be loaded from the given persistence files,
     * note that the persistence XML files will be validated against the
     * XSD schema.
     * </p>
     * @param fileNames the XML phase template file names
     * @throws IllegalArgumentException if the fileNames is null, or any of the
     *             items is null or empty string, or schema validation can not be performed
     * @throws PersistenceException if any error occurs(e.g. any of the file is
     *             malformed XML document) while parsing the files
     */
    public XmlPhaseTemplatePersistence(String[] fileNames) throws PersistenceException {
        try {
            // init templates
            this.initialzeTemplates(fileNames);
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (Exception ex) {
            // any exception is thrown, wrap to PersistenceException
            throw new PersistenceException("Error occurs while accessing the persistence.", ex);
        }
    }

    /**
     * <p>
     * Generates an array of <code>Phase</code>s from the template with the given name and
     * add them into the given <code>Project</code> object.The dependency hierarchy will be
     * populated too.
     * </p>
     * @param templateName the template name
     * @param project the project which the phases will be added to
     * @throws IllegalArgumentException if the templateName is null or empty
     *             string, or the project is null, or the there's no template
     *             with the given templateName
     * @throws PhaseGenerationException if any error occured in the phase
     *             generation(e.g. cyclic dependency, etc.) so that the
     *             generation process can not complete successfully
     * @throws PersistenceException if error occurs while accessing the
     *             persistence layer
     */
    public void generatePhases(String templateName, Project project)
        throws PhaseGenerationException, PersistenceException {
        if (templateName == null) {
            throw new IllegalArgumentException("templateName can not be null!");
        }
        if (project == null) {
            throw new IllegalArgumentException("project can not be null!");
        }
        if (templateName.trim().length() == 0) {
            throw new IllegalArgumentException("templateName can not be empty string!");
        }
        // verify if the template exsits
        if (!this.templates.containsKey(templateName)) {
            throw new IllegalArgumentException("There's no such template with name " + templateName);
        }
        try {
            // retrieve the DOM Document representing the template
            Document doc = (Document) this.templates.get(templateName);

            // generating phase types
            Map phaseTypes = new HashMap();
            NodeList list = doc.getDocumentElement().getElementsByTagName(TAG_PHASE_TYPE);
            for (int i = 0; i < list.getLength(); i++) {
                Element typeElement = (Element) list.item(i);
                // retrieve typeId and typeName, create the PhaseType with them
                long typeId = Long.parseLong(typeElement.getAttribute(ATTRIBUTE_TYPE_ID));
                String typeName = typeElement.getAttribute(ATTRIBUTE_TYPE_NAME);
                PhaseType phaseType = new PhaseType(typeId, typeName);
                // cache the phaseType
                phaseTypes.put(typeElement.getAttribute(ATTRIBUTE_ID), phaseType);
            }

            // 1st pass to scan the "Phase" elements, in this pass, all phase related information
            // except dependency information will be populated
            Map phases = new HashMap();
            list = doc.getElementsByTagName(TAG_PHASE);
            for (int i = 0; i < list.getLength(); i++) {
                Element phaseElement = (Element) list.item(i);
                // retrieve length and create the Phase with project and length
                long length = Long.parseLong(phaseElement.getAttribute(ATTRIBUTE_LENGTH));
                Phase phase = new Phase(project, length);
                // retrieve the cached phase type and set it to the phase
                PhaseType type = (PhaseType) phaseTypes.get(phaseElement.getAttribute(ATTRIBUTE_TYPE));
		if (type == null) {
                    throw new PhaseGenerationException("The requested phase type does not exist.");
		}
                phase.setPhaseType(type);
                // cache the phase
                phases.put(phaseElement.getAttribute(ATTRIBUTE_ID), phase);
            }

            // 2nd pass to scan the "Phase" elements, in this pass, dependency information will
            // be populated
            for (int i = 0, length = list.getLength(); i < length; i++) {
                Element phaseElement = (Element) list.item(i);
                // "Dependency" elements
                NodeList dependencyElements = phaseElement.getElementsByTagName(TAG_DEPENDENCY);
                // Current Phase
                Phase phase = (Phase) phases.get(phaseElement.getAttribute(ATTRIBUTE_ID));
                // for each "Dependency"
                for (int j = 0; j < dependencyElements.getLength(); j++) {
                    // retrieve the cached phase as the dependency
                    Element dependencyElement = (Element) dependencyElements.item(j);
                    Phase dependencyPhase = (Phase) phases.get(dependencyElement.getAttribute(ATTRIBUTE_ID));
                    // isDependencyStart flag, optional attribute, default to false if missing.
                    boolean isDependencyStart = false;
                    // isDependentStart flag, optional attribute, default to true if missing
                    boolean isDependentStart = true;
                    // lagTime between the dependent and the dependency, optional attribute, default to 0 if missing
                    int lagTime = 0;
                    // temp variable to cache attribute value
                    String tmp = dependencyElement.getAttribute(ATTRIBUTE_IS_DEPENDENCY_START);
                    // retrieve isDependencyStart
                    if (tmp.trim().length() != 0) {
                        isDependencyStart = parseBoolean(tmp);
                    }
                    // retrieve isDependentStart
                    tmp = dependencyElement.getAttribute(ATTRIBUTE_IS_DEPENDENT_START);
                    if (tmp.trim().length() != 0) {
                        isDependentStart = parseBoolean(tmp);
                    }
                    // retrieve lagTime
                    tmp = dependencyElement.getAttribute(ATTRIBUTE_LAG_TIME);
                    if (tmp.trim().length() != 0) {
                        lagTime = Integer.parseInt(tmp);
                    }
                    // create Dependency object with the retrieved information
                    Dependency dependency = new Dependency(dependencyPhase, phase, isDependencyStart,
                            isDependentStart, lagTime);
                    // add the dependency to Current Phase
                    phase.addDependency(dependency);
                }
            }
            // add the generated phases to the project
            for (Iterator itr = phases.values().iterator(); itr.hasNext();) {
                Phase phase = (Phase) itr.next();
                // method Project.addPhase(Phase phase) doesn't detect the cyclic dependency,
                // so here we need call method calcStartDate() to detect the cyclic dependency
                phase.calcStartDate();
                // add to the project
                project.addPhase(phase);
            }
        } catch (Exception ex) {
            // if any exception occurs, wrap it to a PhaseGenerationException
            throw new PhaseGenerationException("Error occurs while generating the phases.", ex);
        }
    }

    /**
     * <p>
     * Return the names of all templates defined in the persistence layer.
     * </p>
     * @return the names of all templates defined in the persistence layer.
     */
    public String[] getAllTemplateNames() {
        String[] names = new String[this.templates.size()];
        this.templates.keySet().toArray(names);
        return names;
    }

    /**
     * <p>
     * Initialize the <code>templates</code> map with the templateFiles array.
     * It will pase DOM Documents from the files and put them in the templates map.
     * </p>
     * @param templateFiles the template files
     * @throws ParserConfigurationException thrown from SAX API
     * @throws SAXException thrown from SAX API
     * @throws IOException if any I/O error occurs
     * @throws PersistenceException if the template name is empty string
     * @throws IllegalArgumentException if the parser is not JAXP 1.2 compatible.
     */
    private void initialzeTemplates(String[] templateFiles) throws ParserConfigurationException,
            SAXException, IOException, PersistenceException {
        if (templateFiles == null) {
            throw new IllegalArgumentException("templateFiles can not be null!");
        }
        if (templateFiles.length < 1) {
            throw new IllegalArgumentException("There should be at least one template in templateFiles!");
        }
        // check the templateFile for null or empty string
        for (int i = 0; i < templateFiles.length; i++) {
            if (templateFiles[i] == null) {
                throw new IllegalArgumentException("Element in the templateFiles can not be null!");
            }
            if (templateFiles[i].trim().length() == 0) {
                throw new IllegalArgumentException("Element in the templateFiles can not be empty!");
            }
        }
        // for each template file, try to parse the xml file to Document object and cache it to the map
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // set it to validating
        factory.setValidating(true);

        // set the XSD schema
        // If the parser is not JAXP 1.2 compatible, these may throw IllegalArgumentException.
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
            "http://www.w3.org/2001/XMLSchema");
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", getSchemaFile());

        DocumentBuilder builder = factory.newDocumentBuilder();
        // create and set the error handler
        ErrorHandler errorHandler = new ErrorHandler() {
            /**
             * <p>Just rethrow the SAXParseException.</p>
             * @param exception the SAXParseException that describes the parsing error that occured.
             * @throws SAXException when error happens in parsing.
             */
            public void error(SAXParseException exception) throws SAXException {
                throw exception;
            }
            /**
             * <p>Just rethrow the SAXParseException.</p>
             * @param exception the SAXParseException that describes the parsing error that occured.
             * @throws SAXException when fatal error happens in parsing.
             */
            public void fatalError(SAXParseException exception) throws SAXException {
                throw exception;
            }
            /**
             * <p>Just rethrow the SAXParseException.</p>
             * @param exception the SAXParseException that describes the parsing error that occured.
             * @throws SAXException when warning happens in parsing.
             */
            public void warning(SAXParseException exception) throws SAXException {
                throw exception;
            }
        };

        builder.setErrorHandler(errorHandler);
        for (int i = 0; i < templateFiles.length; i++) {
            Document doc = builder.parse(new File(templateFiles[i]));
            String name = doc.getDocumentElement().getAttribute(ATTRIBUTE_NAME);
            // the template name should be non-empty
            if (name.trim().length() == 0) {
                throw new PersistenceException("The name of a template is empty!");
            }
            this.templates.put(name, doc);
        }
    }

    /**
     * <p>
     * Gets the schema file from the CLASSPATH, if it is not found in classpath,
     * then try to find in the relative file path.
     * </p>
     * @return a File instance of schema file.
     */
    private static File getSchemaFile() {
        // get the schema file from classpath
        URL schemaURL = XmlPhaseTemplatePersistence.class.getClassLoader().getResource(SCHEMA_FILE);

        // when schema file is not found in classpath, try to find the input schema file in the relative file path.
        if (schemaURL == null) {
            return new File(XmlPhaseTemplatePersistence.SCHEMA_FILE);
        } else {
            File schemaFile = new File(schemaURL.getFile());
            return schemaFile;
        }
    }

    /**
     * <p>
     * Parse a string value to boolean value.
     * </p>
     * <p>
     * If string value equals to "true"(case-sensitive), then <code>true</code> will be returned;
     * If string value equals to "false"(case-sensitive), then <code>false</code> will be returned.
     * Otherwise, <code>IllegalArgumentException</code> will be thrown.
     * </p>
     * @param value the string value
     * @return parsed boolean value
     * @throws IllegalArgumentException if the value can not be parsed to boolean value
     */
    private static boolean parseBoolean(String value) {
        if (value.equals("true")) {
            return true;
        } else if (value.equals("false")) {
            return false;
        } else {
            throw new IllegalArgumentException("value is not either true or false.");
        }
    }
}
