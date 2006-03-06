/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultSubmissionValidatorFactory.java
 */
package com.topcoder.apps.screening.applications.specification.impl;

import com.topcoder.apps.screening.applications.specification.ConfigurationException;
import com.topcoder.apps.screening.applications.specification.SubmissionValidator;
import com.topcoder.apps.screening.applications.specification.SubmissionValidatorFactory;
import com.topcoder.apps.screening.applications.specification.ValidationOutputFormatter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import java.lang.reflect.Constructor;

/**
 * <p>
 * This class provides the default factory implementation. This implementation loads static component configuration
 * using the Configuration Manager and instantiates validators and formatters from this configuration via
 * reflection API.
 * </p>
 *
 * <p>
 * The following configuration properties are used by the factory:
 * <ul>
 * <li>&quot;validators&quot; - a multi-value property containing String names of validators to create.
 *     This property is mandatory (at least one validator should be configured)</li>
 * <li>&lt;validator_name&gt; + &quot;_class&quot; - a String property containing a class name for the
 *     validator &lt;validator_name&gt;. This property is mandatory (each &lt;validator_name&gt; mentioned
 *     in the &quot;validators&quot; should have this property)</li>
 * <li>&lt;validator_name&gt; + &quot;_namespace&quot; - a String property containing a ConfigManager namespace
 *      for the validator &lt;validator_name&gt;. This property is optional. If absent, then the validator will
 *      be created using the default constructor. If present, the validator will be created using the constructor
 *      with namespace:String parameter</li>
 * <li>&quot;formatter_class&quot; - a String property containing a class name for the formatter.
 *     This property is mandatory</li>
 * <li>&quot;formatter_namespace&quot; - a String property containing a namespace for the formatter. This
 *     property is optional. If absent, the formatter will be created via the default constructor. If present,
 *     the formatter will be created via the constructor with namespace:String parameter</li>
 * </ul>
 * </p>
 *
 * <p>
 * Sample configuration:
 *
 * <pre>
 * &lt;Config&gt;
 * &lt;Property name=&quot;formatter_class&quot;&gt;
 * &lt;Value&gt;com.topcoder.apps.screening.applications.specification.impl.
 *              formatters.XMLValidationOutputFormatter&lt;/Value&gt;
 * &lt;/Property&gt;
 * &lt;Property name=&quot;validators&quot;&gt;
 * &lt;Value&gt;ucd_validator&lt;/Value&gt;
 * &lt;Value&gt;ucd_naming_validator&lt;/Value&gt;
 * &lt;/Property&gt;
 * &lt;Property name=&quot;ucd_validator_class&quot;&gt;
 * &lt;Value&gt;com.topcoder.apps.screening.applications.specification.impl.
 *              validators.DefaultUseCaseDiagramValidator&lt;/Value&gt;
 * &lt;/Property&gt;
 * &lt;Property name=&quot;ucd_naming_validator_class&quot;&gt;
 * &lt;Value&gt;com.topcoder.apps.screening.applications.specification.impl.
 *              validators.DefaultUseCaseDiagramNamingValidator&lt;/Value&gt;
 * &lt;/Property&gt;
 * &lt;/Config&gt;
 * </pre>
 * </p>
 *
 * <p>
 * Thread-Safety: This class is immutable and therefore thread-safe.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultSubmissionValidatorFactory implements SubmissionValidatorFactory {

    /**'formatter_class' property name.*/
    private static final String FORMATTER_CLASS_PROP = "formatter_class";

    /**'formatter_namespace' property name.*/
    private static final String FORMATTER_NS_PROP = "formatter_namespace";

    /**'validators' property name.*/
    private static final String VALIDATORS_PROP = "validators";

    /**Validator class name property suffix.*/
    private static final String VALIDATORS_CLASS_SUFFIX = "_class";

    /**Validator namespace property suffix.*/
    private static final String VALIDATORS_NS_SUFFIX = "_namespace";

    /**
     * Represents a namespace to use for loading of the configuration (validators and formatters).
     * The value is assigned in the constructor and not changed afterwards.
     * It cannot be null or an empty string. No additional validation of the namespace name (for example,
     * whether the namespace actually exists) is made before the createValidators() or createFormatter() calls.
     */
    private final String namespace;

    /**
     * <p>
     * Default constructor. Sets namespace to the fully classified class name.
     * Note, that no other actions are performed, and existence of namespace is not checked.
     * </p>
     */
    public DefaultSubmissionValidatorFactory() {
        this.namespace = DefaultSubmissionValidatorFactory.class.getName();
    }

    /**
     * <p>
     * Sets namespace to the given parameter.
     * Note, that no other actions are performed, and existence of namespace is not checked.
     * </p>
     *
     * @param namespace the namespace to use
     * @throws IllegalArgumentException if the namespace is null or an empty string
     */
    public DefaultSubmissionValidatorFactory(String namespace) {
        if (namespace == null) {
            throw new IllegalArgumentException("Namespace cannot be null.");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("Namespace cannot be empty.");
        }
        this.namespace = namespace;
    }

    /**
     * <p>
     * This method will create the set of validators based on the information from the given namespace.
     * </p>
     *
     * @return the set of validators to use (as a static array)
     * @throws ConfigurationException if the factory configuration can't be retrieved or the configuration is wrong
     */
    public SubmissionValidator[] createValidators() throws ConfigurationException {
        ConfigManager cm = ConfigManager.getInstance();

        //get validators' names
        String []validatorNames;
        try {
            validatorNames = cm.getStringArray(namespace, VALIDATORS_PROP);
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("Namespace '" + namespace + "' is unknown.");
        }
        if (validatorNames == null || validatorNames.length == 0) {
            throw new ConfigurationException("Configuration file does not contain any validator.");
        }

        //get class names
        SubmissionValidator []validators = new SubmissionValidator[validatorNames.length];
        for (int i = 0, n = validatorNames.length; i < n; i++) {
            validators[i] = getValidator(cm, validatorNames[i]);
        }

        return validators;
    }

    /**
     * <p>
     * This method creates SubmissionValidator.
     * </p>
     *
     * @param cm ConfigManager that is used for getting class name and namespace
     * @param validatorName validator name (used for getting class name and namspace)
     * @return SubmissionValidator instance
     * @throws ConfigurationException if the factory configuration can't be retrieved or the configuration is wrong
     */
    private SubmissionValidator getValidator(ConfigManager cm, String validatorName) throws ConfigurationException {
        try {
            //try to get class name
            String className = cm.getString(namespace, validatorName + VALIDATORS_CLASS_SUFFIX);
            if (className == null || className.trim().length() == 0) {
                throw new ConfigurationException("Class name for validator '" + validatorName
                        + "' is not specified.");
            }

            //try to get namespace
            String ns = cm.getString(namespace, validatorName + VALIDATORS_NS_SUFFIX);

            Class clazz = Class.forName(className);
            //if namespace is null, than use empty constructor
            if (ns == null) {
                return (SubmissionValidator) clazz.newInstance();
            }

            //if namespace is specified, than call constructor that accepts string
            Constructor constructor = clazz.getDeclaredConstructor(new Class[]{String.class});
            return (SubmissionValidator) constructor.newInstance(new Object[]{ns});
        } catch (ConfigurationException e) {
            //re-throw
            throw e;
        } catch (Exception e) {
            throw new ConfigurationException("Exception occurs while creating SubmissionValidator.", e);
        }
    }

    /**
     * <p>
     * This method will create the formatter based on the information from the given namespace.
     * </p>
     *
     * @return the formatter to use
     * @throws ConfigurationException if the factory configuration can't be retrieved or the configuration is wrong
     */
    public ValidationOutputFormatter createFormatter() throws ConfigurationException {
        try {
            ConfigManager cm = ConfigManager.getInstance();
            String className = cm.getString(namespace, FORMATTER_CLASS_PROP);
            if (className == null || className.trim().length() == 0) {
                throw new ConfigurationException("Class name for formatter is not specified.");
            }

            //try to get namespace
            String ns = cm.getString(namespace, FORMATTER_NS_PROP);

            Class clazz = Class.forName(className);
            if (ns == null) {
                return (ValidationOutputFormatter) clazz.newInstance();
            }

            //if namespace is specified, than call constructor that accepts string
            Constructor constructor = clazz.getDeclaredConstructor(new Class[]{String.class});
            return (ValidationOutputFormatter) constructor.newInstance(new Object[]{ns});
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("Namespace '" + namespace + "' is unknown.");
        } catch (ConfigurationException e) {
            //re-throw
            throw e;
        } catch (Exception e) {
            throw new ConfigurationException("Exception occurs while creating ValidationOutputFormatter.", e);
        }
    }
}