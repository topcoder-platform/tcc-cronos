/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * <code>DefaultPhaseTemplate</code> is a default implementation of
 * <code>{@link PhaseTemplate}</code> interface.
 * </p>
 * <p>
 * It manages two underlying variables - persistence(of type
 * <code>{@link PhaseTemplatePersistence}</code>) which is used as the actual
 * template storage logic; and startDateGenerator(of type
 * <code>{@link StartDateGenerator}</code>) which is used as the default
 * project start date generation logic if there's no specific start date
 * provided during the phase generation. By this, we can easily change the
 * persistence and start date generator implementations so that the client is
 * able to swap out for different storage and start date generation strategies
 * without code changes - all we need to do is adding new
 * <code>{@link PhaseTemplatePersistence}</code> and/or
 * <code>{@link StartDateGenerator}</code> implementations.
 * </p>
 * <p>
 * <code>DefaultPhaseTemplate</code> can be created from configuration, please
 * consult the component specification and sample configuration file(docs/Default_Phase_Template.xml)
 * for configuration details. It can also be created programatically.
 * </p>
 * <p>
 * Note that the phase start dates, phase statuses will NOT be included in the template,
 * so these information will NOT be populated to the phases, they should be set by the
 * calling application.
 * </p>
 * <p>
 * This class is thread safe.
 * </p>
 * @author albertwang, flying2hk
 * @version 1.0
 */
public class DefaultPhaseTemplate implements PhaseTemplate {
    /**
     * <p>
     * Represents the key of property "persistence".
     * </p>
     */
    private static final String KEY_PERSISTENCE = "persistence";
    /**
     * <p>
     * Represents the key of property "start_date_generator".
     * </p>
     */
    private static final String KEY_START_DATE_GENERATOR = "start_date_generator";
    /**
     * <p>
     * Represents the key of sub-property "class".
     * </p>
     */
    private static final String KEY_CLASS = "class";
    /**
     * <p>
     * Represents the key of sub-property "namespace".
     * </p>
     */
    private static final String KEY_NAMESPACE = "namespace";
    /**
     * <p>
     * Represents the key of property "workdays".
     * </p>
     */
    private static final String KEY_WORKDAYS = "workdays";
    /**
     * <p>
     * Represents the key of sub-property "object_specification_namespace".
     * </p>
     */
    private static final String KEY_OBJECT_SPEC_NAMESPACE = "object_specification_namespace";
    /**
     * <p>
     * Represents the key of sub-property "object_key".
     * </p>
     */
    private static final String KEY_OBJECT_KEY = "object_key";
    /**
     * <p>
     * Represents the key of sub-property "object_identifier".
     * </p>
     */
    private static final String KEY_OBJECT_IDENTIFIER = "object_identifier";

    /**
     * <p>
     * Represents the underlying template persistence from which the project
     * phases will be generated.
     * </p>
     * <p>
     * It is initialized in the constructor, and can be modified by the
     * setter.It can never be null.
     * </p>
     */
    private PhaseTemplatePersistence persistence;

    /**
     * <p>
     * Represents the generator to generate the project start date.
     * </p>
     * <p>
     * It is initialized in the constructor, and can be modified by the setter.It
     * can never be null.
     * </p>
     */
    private StartDateGenerator startDateGenerator;

    /**
     * <p>
     * Represents the Workdays object which the generated Project will be based on.
     * </p>
     * <p>
     * It is initialized in the constructor, and can be modified by the setter.It can never be null.
     * </p>
     */
    private Workdays workdays;


    /**
     * <p>
     * Create a <code>DefaultPhaseTemplate</code> from the given configuration
     * namespace.
     * </p>
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if the namespace is null or empty string
     * @throws ConfigurationException if the namespace is unknown or there's any
     *             errors in the configuration namespace
     */
    public DefaultPhaseTemplate(String namespace) throws ConfigurationException {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace can not be null!");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace can not be empty string!");
        }

        try {
            ConfigManager cm = ConfigManager.getInstance();
            // retrieve "persistence" property
            Property property = cm.getPropertyObject(namespace, KEY_PERSISTENCE);
            if (property == null) {
                throw new ConfigurationException("Property " + KEY_PERSISTENCE + " is required!");
            }
            // create persistence from the property
            this.persistence = (PhaseTemplatePersistence) instantiateObjectFromProperty(property);

            // retrieve "start_date_generator" property
            property = cm.getPropertyObject(namespace, KEY_START_DATE_GENERATOR);
            if (property == null) {
                throw new ConfigurationException("Property " + KEY_START_DATE_GENERATOR + " is required!");
            }
            // create startDateGenerator from the property
            this.startDateGenerator = (StartDateGenerator) instantiateObjectFromProperty(property);

            // retrieve "workdays" property
            property = cm.getPropertyObject(namespace, KEY_WORKDAYS);
            if (property == null) {
                throw new ConfigurationException("Property " + KEY_WORKDAYS + " is required!");
            }
            // retrieve "object_specification_namespace", "object_key" and "object_identifier"
            String objectSpecNamespace = property.getValue(KEY_OBJECT_SPEC_NAMESPACE);
            if (objectSpecNamespace == null) {
                throw new ConfigurationException("Property " + KEY_OBJECT_SPEC_NAMESPACE + " is required!");
            }
            String objectKey = property.getValue(KEY_OBJECT_KEY);
            if (objectKey == null) {
                throw new ConfigurationException("Property " + KEY_OBJECT_KEY + " is required!");
            }
            String objectIdentifier = property.getValue(KEY_OBJECT_IDENTIFIER);
            // create Object Factory instance
            ObjectFactory objectFactory = new ObjectFactory(
                    new ConfigManagerSpecificationFactory(objectSpecNamespace));
            // create workdays from Object Factory
            if (objectIdentifier == null) {
                this.workdays = (Workdays) objectFactory.createObject(objectKey);
            } else {
                this.workdays = (Workdays) objectFactory.createObject(objectKey, objectIdentifier);
            }
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException("Object factory error occured while initializing the workdays.", e);
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException("Object factory error occured while initializing the workdays.", e);
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException("Object factory error occured while initializing the workdays.", e);
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("The given namespace is unknown.", e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException("Reflection error occured while loading the configuration.", e);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("Reflection error occured while loading the configuration.", e);
        } catch (InstantiationException e) {
            throw new ConfigurationException("Reflection error occured while loading the configuration.", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Reflection error occured while loading the configuration.", e);
        } catch (InvocationTargetException e) {
            throw new ConfigurationException("Reflection error occured while loading the configuration.", e);
        } catch (ConfigurationException e) {
            throw e;
        }
    }

    /**
     * <p>
     * Create a <code>DefaultPhaseTemplate</code> with the given persistence
     * , startDateGenerator and workdays.
     * </p>
     * @param persistence the PhaseTemplatePersistence instance to store the
     *            templates
     * @param startDateGenerator the StartDateGenerator instance to generate the
     *            project start date
     * @param workdays the Workdays instance used to create the project
     * @throws IllegalArgumentException if persistence or startDateGenerator or workdays is null
     */
    public DefaultPhaseTemplate(PhaseTemplatePersistence persistence,
            StartDateGenerator startDateGenerator, Workdays workdays) {
        this.setPersistence(persistence);
        this.setStartDateGenerator(startDateGenerator);
        this.setWorkdays(workdays);
    }

    /**
     * <p>
     * Return the names of all the templates available to use.
     * </p>
     * @return the names of all the templates available to use
     */
    public String[] getAllTemplateNames() {
        synchronized (this.persistence) {
            return this.persistence.getAllTemplateNames();
        }
    }

    /**
     * <p>
     * Generate a set of project phases and compose a <code>Project</code> object with those
     * phases by applying the template with the given name.The start date of the
     * project will be set to the startDate provided by the caller.
     * </p>
     * @param templateName the template name
     * @param startDate the start date of the project
     * @return a Project with the phases generated from the given template
     * @throws PhaseTemplateException if any error occured during the generation
     *             so that we can not apply the template successfully
     * @throws IllegalArgumentException if templateName or startDate is null, or
     *             templateName is an empty string, or there's no template with
     *             the given name
     */
    public Project applyTemplate(String templateName, Date startDate) throws PhaseTemplateException {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate can not be null!");
        }
        // create a new Project object
        Project project = new Project(startDate, this.workdays);
        // access the persistence to generate the phases
        synchronized (this.persistence) {
            this.persistence.generatePhases(templateName, project);
        }
        // return the project
        return project;
    }

    /**
     * <p>
     * Generate a set of project phases and compose a <code>Project</code> object with those
     * phases by applying the template with the given name.The start date of the
     * project will be generated automatically by the underlying startDateGenerator.
     * </p>
     * @param templateName the template name
     * @return a Project with the phases generated from the given template
     * @throws PhaseTemplateException if any error occured during the generation
     *             so that we can not apply the template successfully
     * @throws IllegalArgumentException if templateName is null or an empty
     *             string, or there's no template with the given name
     */
    public Project applyTemplate(String templateName) throws PhaseTemplateException {
        // generate the default start date using startDateGenerator
        Date startDate = null;
        synchronized (this.startDateGenerator) {
            startDate = this.startDateGenerator.generateStartDate();
        }
        // delegate to applyTemplate(String templateName, Date startDate)
        return this.applyTemplate(templateName, startDate);
    }

    /**
     * <p>
     * Return the persistence layer of this class.
     * </p>
     * @return the PhaseTemplatePersistence of this PhaseTemplate
     */
    public PhaseTemplatePersistence getPersistence() {
        return this.persistence;
    }

    /**
     * <p>
     * Set the <code>{@link PhaseTemplatePersistence}</code> of this <code>PhaseTemplate</code>.
     * </p>
     * @param persistence the PhaseTemplatePersistence instance
     * @throws IllegalArgumentException if the argument is null
     */
    public void setPersistence(PhaseTemplatePersistence persistence) {
        if (persistence == null) {
            throw new IllegalArgumentException("persistence can not be null!");
        }
        synchronized (this) {
            this.persistence = persistence;
        }
    }

    /**
     * <p>
     * Return the StartDateGenerator used to generate the start date in this
     * class.
     * </p>
     * @return the StartDateGenerator used to generate the start date
     */
    public StartDateGenerator getStartDateGenerator() {
        return this.startDateGenerator;
    }

    /**
     * <p>
     * Set the <code>{@link StartDateGenerator}</code> of this <code>PhaseTemplate</code>.
     * </p>
     * @param startDateGenerator the StartDateGenerator instance
     * @throws IllegalArgumentException if the argument is null
     */
    public void setStartDateGenerator(StartDateGenerator startDateGenerator) {
        if (startDateGenerator == null) {
            throw new IllegalArgumentException("startDateGenerator can not be null!");
        }
        synchronized (this) {
            this.startDateGenerator = startDateGenerator;
        }
    }

   /**
    * <p>
    * Return the Workdays used to generate the Project in this class.
    * </p>
    * @return the workdays variable
    */
    public Workdays getWorkdays() {
        return this.workdays;
    }

   /**
    * <p>
    * Set the <code>Workdays</code> used to create projects.
    * </p>
    * @param workdays the Workdays object
    * @throws IllegalArgumentException if workdays is null
    */
    public void setWorkdays(Workdays workdays) {
        if (workdays == null) {
           throw new IllegalArgumentException("workdays can not be null!");
        }
        synchronized (this) {
            this.workdays = workdays;
        }
    }

    /**
     * <p>
     * Instantiate an object from a <code>Property</code> object which contains the class name and
     * configuration namespace.
     * If namespace is not null, it will create the object by invoking constructor with namespace argument.
     * If namespace is null or error occurs while invoking constructor with namespace argument, it will
     * try to create the object by invoking default constructor without argument.
     * </p>
     * <p>
     * It will be used in the constructor with configuration namespace to initialize
     * <code>startDateGenerator</code> and <code>persistence</code>
     * </p>
     * @param property the property from which the object will be created
     * @return the created object
     * @throws SecurityException thrown from relection API
     * @throws NoSuchMethodException thrown from relection API
     * @throws ClassNotFoundException thrown from relection API
     * @throws IllegalArgumentException thrown from relection API
     * @throws InstantiationException thrown from relection API
     * @throws IllegalAccessException thrown from relection API
     * @throws InvocationTargetException thrown from relection API
     * @throws ConfigurationException if "namespace" or "class" property is missing
     */
    private static Object instantiateObjectFromProperty(Property property) throws NoSuchMethodException,
            ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
            ConfigurationException {
        String className = property.getValue(KEY_CLASS);
        if (className == null) {
            throw new ConfigurationException("Property " + KEY_CLASS + " is required!");
        }
        String namespace = property.getValue(KEY_NAMESPACE);
        if (namespace != null) {
            try {
                // namespace is present, use ctor with string argument
                Constructor ctor = Class.forName(className).getConstructor(
                    new Class[] {String.class});
                return ctor.newInstance(new Object[] {namespace});
            } catch (Exception ex) {
                // error occurs, use ctor with no argument
                return Class.forName(className).newInstance();
            }
        } else {
            // namespace is not present, use ctor with no argument
            return Class.forName(className).newInstance();
        }
    }
}