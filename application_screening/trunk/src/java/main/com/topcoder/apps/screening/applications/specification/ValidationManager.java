/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ValidationManager.java
 */
package com.topcoder.apps.screening.applications.specification;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.xmi.parser.XMIParser;
import com.topcoder.util.xmi.parser.XMIParserException;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLDiagramTypes;
import com.topcoder.util.xmi.parser.data.UMLElementTypes;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;
import com.topcoder.util.xmi.parser.handler.DefaultXMIHandler;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>
 * This class provides a simple facade to the whole component. It manages a set of validators for the
 * XMI data and a formatter to format the validation output. It also provides several overloaded API methods
 * to perform the actual validation and return the formatted output.
 * </p>
 *
 * <p>
 * The XMI Parser component is used to perform parsing of the XMI data.
 * The class can be configured directly via the constructor parameters or using a Configuration Manager.
 * </p>
 *
 * <p>
 * If static configuration is used, the following properties will be read:
 * <ul>
 * <li>&quot;factory_class&quot; to load the class of a SubmissionValidatorFactory that will be used to create
 *     the validators and the formatter. This property is mandatory.</li>
 * <li>&quot;factory_namespace&quot; to load the namespace for the factory. This property is optional. If
 *     the property is not specified the factory will be created using the default constructor, otherwise
 *     using the constructor with a namespace (String) parameter.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Sample configuration:
 *
 * <pre>
 * &lt;Config&gt;
 * &lt;Property name=&quot;factory_class&quot;&gt;
 * &lt;Value&gt;com.topcoder.apps.screening.applications.specification.impl.
 *                             DefaultSubmissionValidatorFactory&lt;/Value&gt;
 * &lt;/Property&gt;
 * &lt;Property name=&quot;factory_namespace&quot;&gt;
 * &lt;Value&gt;com.topcoder.apps.screening.applications.specification.impl.valid1&lt;/Value&gt;
 * &lt;/Property&gt;
 * &lt;/Config&gt;
 * </pre>
 * </p>
 *
 * <p>
 * Thread-Safety: this class is immutable and therefore thread-safe. Note that this class doesn't keep
 * any private references to an XMIParser or a DefaultXMIHandler instance because these classes
 * are not thread-safe. Instead a new parser instance will be created each time the parse() method is called.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public class ValidationManager {

    /**'factory_class' property name.*/
    private static final String FACTORY_CLASS_PROP = "factory_class";

    /**'factory_namespace' property name.*/
    private static final String FACTORY_NS_PROP = "factory_namespace";

    /**
     * Represents an array of validators. The array and all its contents are initialized in the
     * constructor. The array reference and array contents are not changed afterwards. The list
     * can not be null or empty. Each item in the list is a non-null object extending the SubmissionValidator
     * class. The list contents can be retrieved using getValidators() call.
     * All validators from the list will be used in the validate() method.
     */
    private final SubmissionValidator []validators;

    /**
     * Represents a formatter that will be used to format the validation output produced by validators
     * before returning it to the application.
     * The formatter is assigned in the constructor and not changed afterwards.
     * The reference can never be null.
     */
    private final ValidationOutputFormatter formatter;

    /**
     * <p>
     * Default constructor. Loads the factory configuration from the default namespace
     * (the fully qualified class name).
     * </p>
     *
     * @throws ConfigurationException if any problem happens when loading the configuration
     */
    public ValidationManager() throws ConfigurationException {
        this(ValidationManager.class.getName());
    }

    /**
     * <p>
     * This method will load the factory class.
     * After the factory creation, validtors and formatters will be retrieved.
     * </p>
     *
     * @param namespace the namespace to use
     * @throws ConfigurationException   if any problem happens when loading the configuration
     * @throws IllegalArgumentException if the namespace is null or an empty string
     */
    public ValidationManager(String namespace) throws ConfigurationException {
        if (namespace == null) {
            throw new IllegalArgumentException("Namespace must not be null.");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("Namespace must not be null.");
        }
        try {
            ConfigManager cm = ConfigManager.getInstance();
            String className = cm.getString(namespace, FACTORY_CLASS_PROP);
            if (className == null || className.trim().length() == 0) {
                throw new ConfigurationException("Factory class name is not specified.");
            }

            SubmissionValidatorFactory factory;

            //try to get namespace
            String ns = cm.getString(namespace, FACTORY_NS_PROP);

            Class clazz = Class.forName(className);
            if (ns == null) {
                factory = (SubmissionValidatorFactory) clazz.newInstance();
            } else {

                //if namespace is specified, than call constructor that accepts string
                Constructor constructor = clazz.getDeclaredConstructor(new Class[]{String.class});
                factory = (SubmissionValidatorFactory) constructor.newInstance(new Object[]{ns});
            }

            validators = factory.createValidators();
            formatter = factory.createFormatter();
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("Namespace '" + namespace + "' is unknown.");
        } catch (ConfigurationException e) {
            //re-throw
            throw e;
        } catch (Exception e) {
            throw new ConfigurationException("Exception occurs while creating ValidationOutputFormatter.", e);
        }
    }

    /**
     * <p>
     * This constructor configures the ValidationManager using the provided parameters.
     * </p>
     *
     * @param validators the validators to use
     * @param formatter  the formatters to use
     * @throws IllegalArgumentException if any parameter is null or the array of validators is empty or contains null
     */
    public ValidationManager(SubmissionValidator[] validators, ValidationOutputFormatter formatter) {
        if (validators == null) {
            throw new IllegalArgumentException("Validators array must not be null.");
        }
        if (validators.length == 0) {
            throw new IllegalArgumentException("Validators array must not be empty.");
        }
        for (int i = 0, n = validators.length; i < n; i++) {
            if (validators[i] == null) {
                throw new IllegalArgumentException("Validators array must not contain null.");
            }
        }
        if (formatter == null) {
            throw new IllegalArgumentException("Formatter must not be null.");
        }
        //create array copy
        this.validators = new SubmissionValidator[validators.length];
        System.arraycopy(validators, 0, this.validators, 0, validators.length);
        this.formatter = formatter;
    }

    /**
     * <p>
     * The constructor which configures the ValidationManager using the given factory.
     * </p>
     *
     * @param factory the factory to use
     * @throws IllegalArgumentException if the parameter is null
     * @throws ConfigurationException  if the factory will fail to load its configuration
     */
    public ValidationManager(SubmissionValidatorFactory factory) throws ConfigurationException {
        if (factory == null) {
            throw new IllegalArgumentException("SubmissionValidatorFactory must not be null.");
        }
        validators = factory.createValidators();
        this.formatter = factory.createFormatter();
    }

    /**
     * <p>
     * Returns an array of configured validators.
     * </p>
     *
     * @return the array of validators used by the manager
     */
    public SubmissionValidator[] getValidators() {
        SubmissionValidator []copy = new SubmissionValidator[validators.length];
        System.arraycopy(validators, 0, copy, 0, validators.length);
        return copy;
    }

    /**
     * <p>
     * Returns the currently configured formatter.
     * </p>
     *
     * @return the formatter used by the manager
     */
    public ValidationOutputFormatter getFormatter() {
        return formatter;
    }

    /**
     * <p>
     * This method validates the given submission object and returns the formatted validation outputs.
     * </p>
     *
     * @param submission the submission to validate
     * @return the formatted validation output
     * @throws IllegalArgumentException if submission is null
     * @throws FormatterException       if the formatter failed to format the validation output
     */
    public String[] validate(Submission submission) throws FormatterException {
        if (submission == null) {
            throw new IllegalArgumentException("Submission must not be null.");
        }
        List validationOuputs = new ArrayList();
        for (int i = 0, n = validators.length; i < n; i++) {
            validationOuputs.addAll(Arrays.asList(validators[i].validateSubmission(submission)));
        }
        ValidationOutput []validationOutputArray = (ValidationOutput[]) validationOuputs.
                toArray(new ValidationOutput[validationOuputs.size()]);
        return formatter.format(validationOutputArray);
    }

    /**
     * <p>
     * This method validates the given XMI string and returns the formatted validation outputs.
     * </p>
     *
     * @param xmiString the XMI contents to validate in a String object
     * @return the formatted validation output
     * @throws IllegalArgumentException if xmiString is null or empty
     * @throws XMIParserException       if any problem happened when retrieving or parsing the XMI data
     * @throws FormatterException       if the formatter failed to format the validation output
     */
    public String[] validate(String xmiString) throws XMIParserException, FormatterException {
        return formatter.format(validateRaw(xmiString));
    }

    /**
     * <p>
     * This method validates the given XMI file and returns the formatted validation outputs.
     * </p>
     *
     * @param file the XMI file to validate
     * @return the formatted validation output
     * @throws IllegalArgumentException if file is null
     * @throws XMIParserException       if any problem happened when retrieving or parsing the XMI data
     * @throws FormatterException       if the formatter failed to format the validation output
     */
    public String[] validate(File file) throws XMIParserException, FormatterException {
        return formatter.format(validateRaw(file));
    }

    /**
     * <p>
     * This method validates the given XMI file stream and returns the formatted validation outputs.
     * </p>
     *
     * @param stream the XMI stream to validate
     * @return the formatted validation output
     * @throws IllegalArgumentException if stream is null
     * @throws XMIParserException       if any problem happened when retrieving or parsing the XMI data
     * @throws FormatterException       if the formatter failed to format the validation output
     */
    //was InputStream
    public String[] validate(FileInputStream stream) throws XMIParserException, FormatterException {
        return formatter.format(validateRaw(stream));
    }

    /**
     * <p>
     * This method simply register the handler with the several types:
     * UMLElementTypes.UML_STATE_MACHINE_TOP, UMLElementTypes.UML_STATE_MACHINE_TRANSITIONS,
     * UMLElementTypes.UML_USE_CASE, UMLElementTypes.UML_ACTOR, UMLDiagramTypes.UML_USECASE_DIAGRAM
     * and UMLDiagramTypes.UML_ACTIVITY_DIAGRAM.
     * </p>
     *
     * @param xmiParser parser to which handler will be register
     * @param handler which will be registered to xmiParser
     * @throws XMIParserException exception that could be thrown while registering
     */
    private void prepareParser(XMIParser xmiParser, DefaultXMIHandler handler) throws XMIParserException {
        xmiParser.registerHandler(UMLElementTypes.UML_STATE_MACHINE_TOP, handler);
        xmiParser.registerHandler(UMLElementTypes.UML_STATE_MACHINE_TRANSITIONS, handler);
        xmiParser.registerHandler(UMLElementTypes.UML_USE_CASE, handler);
        xmiParser.registerHandler(UMLElementTypes.UML_ACTOR, handler);
        xmiParser.registerHandler(UMLDiagramTypes.UML_USECASE_DIAGRAM, handler);
        xmiParser.registerHandler(UMLDiagramTypes.UML_ACTIVITY_DIAGRAM, handler);
    }

    /**
     * <p>
     * This method validates the given submission object and returns unformatted validation outputs.
     * </p>
     *
     * @param submission the submission to validate
     * @return the raw (unformatted) validation output
     * @throws IllegalArgumentException if submission is null
     */
    public ValidationOutput[] validateRaw(Submission submission) {
        if (submission == null) {
            throw new IllegalArgumentException("Submission must not be null.");
        }
        List validationOuputs = new ArrayList();
        for (int i = 0, n = validators.length; i < n; i++) {
            validationOuputs.addAll(Arrays.asList(validators[i].validateSubmission(submission)));
        }
        return (ValidationOutput[]) validationOuputs.toArray(new ValidationOutput[validationOuputs.size()]);
    }

    /**
     * <p>
     * This method validates the given XMI string and returns unformatted validation outputs.
     * </p>
     *
     * @param xmiString the validated XMI contents as a string
     * @return the raw (unformatted) validation output
     * @throws IllegalArgumentException if xmiString is null or empty
     * @throws XMIParserException       if any problem happened when retrieving or parsing the XMI data
     */
    public ValidationOutput[] validateRaw(String xmiString) throws XMIParserException {
        if (xmiString == null) {
            throw new IllegalArgumentException("XmiString must not be null.");
        }
        if (xmiString.trim().length() == 0) {
            throw new IllegalArgumentException("XmiString must not be empty.");
        }
        XMIParser xmiParser = new XMIParser();
        DefaultXMIHandler handler = new DefaultXMIHandler();
        prepareParser(xmiParser, handler);
        xmiParser.parse(xmiString);
        return validateRaw(handler);
    }

    /**
     * <p>
     * This method validates the given XMI file and returns unformatted validation outputs.
     * </p>
     *
     * @param file the XMI file to validate
     * @return the raw (unformatted) validation output
     * @throws IllegalArgumentException if file is null
     * @throws XMIParserException       if any problem happened when retrieving or parsing the XMI data
     */
    public ValidationOutput[] validateRaw(File file) throws XMIParserException {
        if (file == null) {
            throw new IllegalArgumentException("File must not be null.");
        }
        XMIParser xmiParser = new XMIParser();
        DefaultXMIHandler handler = new DefaultXMIHandler();
        prepareParser(xmiParser, handler);
        xmiParser.parse(file);
        return validateRaw(handler);
    }

    /**
     * <p>
     * This method validates the given XMI stream and returns unformatted validation outputs.
     * </p>
     *
     * @param stream the XMI stream to validate
     * @return the raw (unformatted) validation output
     * @throws IllegalArgumentException if stream is null
     * @throws XMIParserException       if any problem happened when retrieving or parsing the XMI data
     */
    public ValidationOutput[] validateRaw(FileInputStream stream) throws XMIParserException {
        if (stream == null) {
            throw new IllegalArgumentException("FileInputStream must not be null.");
        }
        XMIParser xmiParser = new XMIParser();
        DefaultXMIHandler handler = new DefaultXMIHandler();
        prepareParser(xmiParser, handler);
        xmiParser.parse(stream);
        return validateRaw(handler);
    }

    /**
     * <p>
     * This method will get use case and activity diagrams from handler, create submission,
     * and call method validateRaw(submission).
     * </p>
     *
     * @param handler DefaultXMIHandler from which diagrams will be get
     * @return the unformatted validation outputs
     */
    private ValidationOutput[] validateRaw(DefaultXMIHandler handler) {
        List useCaseDiagrams = handler.getDiagramsOfType(UMLDiagramTypes.UML_USECASE_DIAGRAM);
        List activityDiagrams = handler.getDiagramsOfType(UMLDiagramTypes.UML_ACTIVITY_DIAGRAM);
        UMLUseCaseDiagram []useCaseDiagramsArray = (UMLUseCaseDiagram[]) useCaseDiagrams.toArray(
                new UMLUseCaseDiagram[useCaseDiagrams.size()]);
        UMLActivityDiagram []activityDiagramsArray = (UMLActivityDiagram[]) activityDiagrams.toArray(
                new UMLActivityDiagram[activityDiagrams.size()]);

        return validateRaw(new Submission(useCaseDiagramsArray, activityDiagramsArray));
    }
}