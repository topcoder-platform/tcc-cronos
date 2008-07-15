/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.manager.bean;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchStrategy;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.track.ConfigurationProvider;
import com.topcoder.service.digitalrun.track.DigitalRunPointsCalculatorDAO;
import com.topcoder.service.digitalrun.track.DigitalRunProjectTypeDAO;
import com.topcoder.service.digitalrun.track.DigitalRunTrackDAO;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerBeanConfigurationException;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerLocal;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerPersistenceException;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerRemote;
import com.topcoder.service.digitalrun.track.DigitalRunTrackStatusDAO;
import com.topcoder.service.digitalrun.track.DigitalRunTrackTypeDAO;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;
import com.topcoder.service.digitalrun.track.Helper;
import com.topcoder.service.digitalrun.track.dao.implementations.AbstractDAO;

import com.topcoder.util.datavalidator.ObjectValidator;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;


/**
 * <p>
 * This class is a state less session bean that manages track related entities. It implements the
 * <code>DigitalRunTrackManagerLocal</code> and <code>DigitalRunTrackManagerRemote</code> interfaces. All public
 * methods from this class perform logging using LoggingWrapper component. This bean will not access the persistence
 * directly; it will use five DAO to access the persistence. These DAO will be creates in initialize post construct
 * method using Object Factory. The logger will also be initialized in initialize method.
 * </p>
 *
 * <p>
 * Since the container will manage the transactions this class can be considered as thread safe. This bean is also
 * immutable.
 * </p>
 *
 * <p>
 * Here is the configuration for the ejb.xml
 * <pre>
 *   <env-entry>
 *  <env-entry-name>logName</env-entry-name>
 *  <env-entry-type>java.lang.String</env-entry-type>
 *  <env-entry-value>logName</env-entry-value>
 * </env-entry>
 *  <env-entry>
 *  <env-entry-name>unitName</env-entry-name>
 *  <env-entry-type>java.lang.String</env-entry-type>
 *  <env-entry-value>HibernateDRTrackPersistence</env-entry-value>
 * </env-entry>
 * <env-entry>
 *  <env-entry-name>trackDAOKey</env-entry-name>
 *  <env-entry-type>java.lang.String</env-entry-type>
 *  <env-entry-value>trackKey</env-entry-value>
 * </env-entry>
 * <env-entry>
 *  <env-entry-name>trackTypeDAOKey</env-entry-name>
 *  <env-entry-type>java.lang.String</env-entry-type>
 *  <env-entry-value>trackTypeKey</env-entry-value>
 * </env-entry>
 * <env-entry>
 *  <env-entry-name>trackStatusDAOKey</env-entry-name>
 *  <env-entry-type>java.lang.String</env-entry-type>
 *  <env-entry-value>trackStatusKey</env-entry-value>
 * </env-entry>
 * <env-entry>
 *  <env-entry-name>pointsCalculatorDAOKey</env-entry-name>
 *  <env-entry-type>java.lang.String</env-entry-type>
 *  <env-entry-value>pointsCalculator</env-entry-value>
 * </env-entry>
 * <env-entry>
 *  <env-entry-name>projectTypeDAOKey</env-entry-name>
 *  <env-entry-type>java.lang.String</env-entry-type>
 *  <env-entry-value>projectTypeKey</env-entry-value>
 * </env-entry>
 * <persistence-context-ref>
 *  <persistence-context-ref-name>persistence/digitalRunTrackManager</persistence-context-ref-name>
 *  <persistence-unit-name>digital_run_track_manager</persistence-unit-name>
 * </persistence-context-ref>
 * </pre>
 * </p>
 *
 * @author DanLazar, waits
 * @version 1.0
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DigitalRunTrackManagerBean implements DigitalRunTrackManagerLocal, DigitalRunTrackManagerRemote {
    /**
     * <p>
     * The DAO used to manage Track entities. It will be initialized in the initialize method and never changed. It
     * cannot be null after initialization. Used in methods that manage Track entities.
     * </p>
     */
    private DigitalRunTrackDAO trackDAO = null;

    /**
     * <p>
     * The DAO used to manage TrackType entities. It will be initialized in the initialize method and never changed. It
     * cannot be null after initialization. Used in methods that manage TrackType entities.
     * </p>
     */
    private DigitalRunTrackTypeDAO trackTypeDAO = null;

    /**
     * <p>
     * The DAO used to manage TrackStatus entities. It will be initialized in the initialize method and never changed.
     * It cannot be null after initialization. Used in methods that manage TrackStatus entities.
     * </p>
     */
    private DigitalRunTrackStatusDAO trackStatusDAO = null;

    /**
     * <p>
     * The DAO used to manage PointsCalculator entities. It will be initialized in the initialize method and never
     * changed. It cannot be null after initialization. Used in methods that manage PointsCalculator entities.
     * </p>
     */
    private DigitalRunPointsCalculatorDAO pointsCalculatorDAO = null;

    /**
     * <p>
     * The DAO used to manage ProjectType entities. It will be initialized in the initialize method and never changed.
     * It cannot be null after initialization. Used in methods that manage ProjectType entities.
     * </p>
     */
    private DigitalRunProjectTypeDAO projectTypeDAO = null;

    /**
     * <p>
     * Represents the session context of this bean. It is annotated with At Resource so that it will be initialized
     * through injection by the container. It will not be changed after initialization. It cannot be null after
     * initialization. It is used in getEntityManager to obtain an EntityManager instance.
     * </p>
     */
    @Resource
    private SessionContext sessionContext = null;

    /**
     * <p>
     * The persistence unit name. It is annotated with  at Resource so that it will be initialized by the container
     * through injection. It is required that the container initializes this field and after initialization this field
     * cannot be null and empty string and can no longer be modified.
     * </p>
     */
    @Resource(name = "unitName")
    private String unitName;

    /**
     * <p>
     * The log name. It is annotated with at Resource so that it will be initialized by the container through
     * injection. If the container does not initialize this field through injection it means that logging is disabled.
     * If initialized by container then it cannot be null or empty string. After the container initializes the fields,
     * the logName field can no longer be modified. If it is not null or empty string, this field will be used in
     * initialize method to create a Log instance.
     * </p>
     */
    @Resource(name = "logName")
    private String logName = null;

    /**
     * <p>
     * The DAO key used with ObjectFactory to create a DigitalRunTrackDAO instance. It is annotated with at Resource so
     * that it will be initialized by the container through injection. It is required that the container initializes
     * this field and after initialization this field cannot be null and empty string and can no longer be modified.
     * It will be used in initialize method to create a DigitalRunTrackDAO instance with ObjectFactory.
     * </p>
     */
    @Resource(name = "trackDAOKey")
    private String trackDAOKey = null;

    /**
     * <p>
     * The DAO key used with ObjectFactory to create a DigitalRunTrackTypeDAO instance. It is annotated with  at
     * Resource so that it will be initialized by the container through injection. It is required that the container
     * initializes this field and after initialization this field cannot be null and empty string and can no longer be
     * modified. It will be used in initialize method to create a DigitalRunTrackTypeDAO instance with ObjectFactory.
     * </p>
     */
    @Resource(name = "trackTypeDAOKey")
    private String trackTypeDAOKey = null;

    /**
     * <p>
     * The DAO key used with ObjectFactory to create a DigitalRunTrackStatusDAO instance. It is annotated with  at
     * Resource so that it will be initialized by the container through injection. It is required that the container
     * initializes this field and after initialization this field cannot be null and empty string and can no longer be
     * modified. It will be used in initialize method to create a DigitalRunTrackStatusDAO instance with
     * ObjectFactory.
     * </p>
     */
    @Resource(name = "trackStatusDAOKey")
    private String trackStatusDAOKey = null;

    /**
     * <p>
     * The DAO key used with ObjectFactory to create a DigitalRunPointsCalculatorDAO instance. It is annotated with  at
     * Resource so that it will be initialized by the container through injection. It is required that the container
     * initializes this field and after initialization this field cannot be null and empty string and can no longer be
     * modified. It will be used in initialize method to create a DigitalRunPointsCalculatorDAO instance with
     * ObjectFactory.
     * </p>
     */
    @Resource(name = "pointsCalculatorDAOKey")
    private String pointsCalculatorDAOKey = null;

    /**
     * <p>
     * The DAO key used with ObjectFactory to create a DigitalRunProjectTypeDAO instance. It is annotated with  at
     * Resource so that it will be initialized by the container through injection. It is required that the container
     * initializes this field and after initialization this field cannot be null and empty string and can no longer be
     * modified. It will be used in initialize method to create a DigitalRunProjectTypeDAO instance with
     * ObjectFactory.
     * </p>
     */
    @Resource(name = "projectTypeDAOKey")
    private String projectTypeDAOKey = null;

    /**
     * <p>
     * The active status id. It is annotated at Resource with so that it will be initialized by the container through
     * injection. It is required that the container initializes this field and after initialization this field cannot
     * be negative.
     * </p>
     */
    @Resource(name = "activeStatusId")
    private long activeStatusId;

    /**
     * <p>
     * The logger that will be used to perform logging. If logName was injected by container, then this field will be
     * initialized to Log instance in the initialize method, otherwise it will remain set to null meaning that logging
     * is disabled. After the container calls initialize method this field can no longer be modified.It will be used
     * in all public methods of this class to perform logging.
     * </p>
     */
    private Log logger = null;

    /**
     * <p>
     * The searchBundle will be initialized during initialization and can not be null, it will be used in the DAO.
     * </p>
     */
    private SearchBundle searchBundle = null;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public DigitalRunTrackManagerBean() {
    }

    /**
     * <p>
     * This method is called after this bean was constructed by the EJB container. It will initialize the logger field
     * if logName is not null or empty string and it will also initialize the searchBundle field.  It will also
     * validate the two fields annotated with At Resource.
     * </p>
     *
     * @throws DigitalRunTrackManagerBeanConfigurationException if logName or unitName is empty string or if XXXDAOKey
     *         is null or empty string, or if activeStatusID is negative or if null is returned from
     *         ConfigurationProvider.getConfiguration(), or if errors occur when creating the DAOs with ObjectFactory
     *         or if errors occur when getting the Log instance from LogManager
     */
    @PostConstruct
    private void initialize() {
        validateInjections();

        if ((logName != null) && (logName.trim().length() > 0)) {
            this.logger = LogManager.getLog(logName);
        }

        // get the configuration, it should not be null
        ConfigurationObject config = ConfigurationProvider.getConfiguration();
        ObjectFactory of = createOF(getChild(config, "object_factory_child"));

        searchBundle = createSearchBundle(config, of);

        // create the DigitalRunTrackDAO and set the fields
        this.trackDAO = createDAO(DigitalRunTrackDAO.class, this.trackDAOKey, of);

        // create the DigitalRunTrackTypeDAO and set the fields
        this.trackTypeDAO = createDAO(DigitalRunTrackTypeDAO.class, trackTypeDAOKey, of);

        // create the DigitalRunTrackStatusDAO and set the fields
        this.trackStatusDAO = createDAO(DigitalRunTrackStatusDAO.class, trackStatusDAOKey, of);

        // create the DigitalRunPointsCalculatorDAO and set the fields
        this.pointsCalculatorDAO = createDAO(DigitalRunPointsCalculatorDAO.class, pointsCalculatorDAOKey, of);

        // create the DigitalRunProjectTypeDAO and set the fields
        this.projectTypeDAO = createDAO(DigitalRunProjectTypeDAO.class, projectTypeDAOKey, of);
    }

    /**
     * <p>
     * Create ObjectFactory instance with ConfigurationObject.
     * </p>
     *
     * @param co ConfigurationObject to create of
     *
     * @return newly create ObjectFactory
     *
     * @throws DigitalRunTrackManagerBeanConfigurationException if any error occurs
     */
    private ObjectFactory createOF(ConfigurationObject co) {
        try {
            return new ObjectFactory(new ConfigurationObjectSpecificationFactory(co));
        } catch (IllegalReferenceException e) {
            throw new DigitalRunTrackManagerBeanConfigurationException(
                    "Error occurs to create ObjectFactory during initialization.", e);
        } catch (SpecificationConfigurationException e) {
            throw new DigitalRunTrackManagerBeanConfigurationException(
                    "Error occurs to create ObjectFactory during initialization.", e);
        }
    }

    /**
     * <p>
     * Create DAO instance. Create the dao from the ObjectFactory and set the necessary fields.
     * </p>
     *
     * @param <T> the class type of the DAO  to create
     * @param clz class type of the DAO
     * @param key the key in object factory for the DAO
     * @param of the ObjectFactory instance
     *
     * @return DAO instance
     *
     * @throws DigitalRunTrackManagerBeanConfigurationException if any error occurs
     */
    private < T > T createDAO(Class < T > clz, String key, ObjectFactory of) {
        T dao = createObject(clz, key, of);

        if (logger != null) {
            setPrivateField(AbstractDAO.class, dao, "logger", logger);
        }

        setPrivateField(AbstractDAO.class, dao, "sessionContext", sessionContext);
        setPrivateField(AbstractDAO.class, dao, "unitName", unitName);
        setPrivateField(null, dao, "searchBundle", searchBundle);
        setPrivateField(null, dao, "activeStatusId", activeStatusId);

        return dao;
    }

    /**
     * <p>
     * Sets the value of a private field in the given class.
     * </p>
     *
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be set
     * @param value the value to set
     * @param clz the class type to get the field
     */
    @SuppressWarnings("unchecked")
	private void setPrivateField(Class clz, Object instance, String name, Object value) {
        Field field = null;

        try {
            if (clz != null && clz.isAssignableFrom(instance.getClass())) {
                field = clz.getDeclaredField(name);
            } else {
                field = instance.getClass().getDeclaredField(name);
            }
            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Retrieves the child configuration from ConfigurationObject.
     * </p>
     *
     * @param config the configuration to retrieve the configuration child
     * @param childName the name of the children configuration
     *
     * @return ConfigurationObject instance
     * @throws DigitalRunTrackManagerBeanConfigurationException if error occurs
     */
    private ConfigurationObject getChild(ConfigurationObject config, String childName) {
        try {
            ConfigurationObject ofChild = config.getChild(childName);

            if (ofChild == null) {
                throw new DigitalRunTrackManagerBeanConfigurationException(
                        "'" + childName + "' configuration child does not exist.");
            }

            return ofChild;
        } catch (ConfigurationAccessException e) {
            throw new DigitalRunTrackManagerBeanConfigurationException("Fail to get child from configuration.", e);
        }
    }

    /**
     * <p>
     * Create SearchBundle from the configuration.
     * </p>
     *
     * @param config configuration
     * @param of the ObjectFactory
     *
     * @return SearchBundle instance
     *
     * @throws DigitalRunTrackManagerBeanConfigurationException if any error occurs
     */
    private SearchBundle createSearchBundle(ConfigurationObject config, ObjectFactory of) {
        ConfigurationObject sbChild = getChild(config, "search_bundle_child");
        String name = getStringConfig(sbChild, "name");
        String context = getStringConfig(sbChild, "context");

        SearchStrategy searchStrategy = createObject(SearchStrategy.class,
                getStringConfig(sbChild, "search_strategy_key"), of);
        ConfigurationObject fieldsConfig = getChild(config, "fields");

        try {
            // create the fields map
            Map < String, ObjectValidator > fields = new HashMap<String, ObjectValidator>();
            String[] propertyKeys = fieldsConfig.getAllPropertyKeys();
            if (propertyKeys != null) {
                for (String property : propertyKeys) {
                    fields.put(property,
                        createObject(ObjectValidator.class, getStringConfig(fieldsConfig, property), of));
                }
            }

            // create the alias map
            Map < String, String > aliases = new HashMap<String, String>();
            ConfigurationObject aliasConfig = getChild(config, "aliases");
            String[] aliasPropertyKeys = aliasConfig.getAllPropertyKeys();

            if (aliasPropertyKeys != null) {
                for (String property : aliasPropertyKeys) {
                    aliases.put(property, getStringConfig(aliasConfig, property));
                }
            }

            // create searchBundle
            return new SearchBundle(name, fields, aliases, context, searchStrategy);
        } catch (ConfigurationAccessException e) {
            throw new DigitalRunTrackManagerBeanConfigurationException(
                    "Error occurs to create SearchBundle during initialization.", e);
        }
    }

    /**
     * <p>
     * Create object instance with the key from object factory.
     * </p>
     *
     * @param <T> the class type of the object  to create
     * @param clz class type of the object
     * @param key the key in object factory for the object
     * @param of the ObjectFactory instance
     *
     * @return object instance
     *
     * @throws DigitalRunTrackManagerBeanConfigurationException if any error occurs
     */
    @SuppressWarnings("unchecked")
    private < T > T createObject(Class < T > clz, String key, ObjectFactory of) {
        try {
            Object ret = of.createObject(key);

            if (ret == null) {
                throw new DigitalRunTrackManagerBeanConfigurationException(
                        key + " can not be found in objectfactory config.");
            }

            if (!clz.isAssignableFrom(ret.getClass())) {
                throw new DigitalRunTrackManagerBeanConfigurationException(
                        key + " should be of " + clz.getSimpleName() + " type.");
            }

            return (T) ret;
        } catch (InvalidClassSpecificationException e) {
            throw new DigitalRunTrackManagerBeanConfigurationException(
                    "Error occurs during create instance from object factory.", e);
        }
    }

    /**
     * <p>
     * Gets property value from the given ConfigurationObject.
     * </p>
     *
     * @param configurationObject the ConfigurationObject instance to get value from
     * @param key the name of the property whose value to get
     *
     * @return the retrieved config value
     *
     * @throws DigitalRunTrackManagerBeanConfigurationException if any error occurs while retrieving the value from
     *         configurationObject
     */
    private String getStringConfig(ConfigurationObject configurationObject, String key) {
        try {
            String ret = (String) configurationObject.getPropertyValue(key);

            if (ret == null) {
                throw new DigitalRunTrackManagerBeanConfigurationException("The property : '" + key + "' is missing.");
            }

            return ret;
        } catch (ConfigurationAccessException e) {
            throw new DigitalRunTrackManagerBeanConfigurationException(
                    "ConfigurationAccessException occurs while retrieving the string value.", e);
        } catch (ClassCastException cce) {
            throw new DigitalRunTrackManagerBeanConfigurationException(
                    "The value of the property " + key + " should be of String.", cce);
        }
    }

    /**
     * <p>
     * Validates the injections.
     * </p>
     *
     * @throws DigitalRunTrackManagerBeanConfigurationException If any string has not been injected with correct value.
     */
    private void validateInjections() {
        validateInjection(unitName, "unitName");
        validateInjection(trackDAOKey, "trackDAOKey");
        validateInjection(this.pointsCalculatorDAOKey, "pointsCalculatorDAOKey");
        validateInjection(this.projectTypeDAOKey, "projectTypeDAOKey");
        validateInjection(this.trackStatusDAOKey, "trackStatusDAOKey");
        validateInjection(this.trackTypeDAOKey, "trackTypeDAOKey");

        if (sessionContext == null) {
            throw new DigitalRunTrackManagerBeanConfigurationException("The SessionContext does not injected.");
        }

        if (this.activeStatusId < 0) {
            throw new DigitalRunTrackManagerBeanConfigurationException("The activeStatusId can not be negative.");
        }
    }

    /**
     * <p>
     * Checks if the property is been injected with non-null/non-empty value.
     * </p>
     *
     * @param property the property
     * @param name the name of the property
     *
     * @throws DigitalRunTrackManagerBeanConfigurationException If any string has not been injected with correct value.
     */
    private void validateInjection(String property, String name) {
        if (property == null) {
            throw new DigitalRunTrackManagerBeanConfigurationException("The " + name + " is not inject.");
        }

        if (property.trim().length() == 0) {
            throw new DigitalRunTrackManagerBeanConfigurationException("The " + name + " can not be empty string.");
        }
    }

    /**
     * <p>
     * Creates a new Track entity into persistence. Returns the Track instance with id generated.
     * </p>
     *
     * @param track the entity to be created
     *
     * @return the entity with id set
     *
     * @throws IllegalArgumentException if argument is null or if its id is positive
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public Track createTrack(Track track) throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("createTrack(Track)", logger);

        try {
            return this.trackDAO.createTrack(track);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("createTrack(Track)", logger);
        }
    }

    /**
     * <p>
     * Updates the given Track instance into persistence.
     * </p>
     *
     * @param track the entity to be updated
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if a digital run points entity with Track.id does not exist in the persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void updateTrack(Track track) throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("updateTrack(Track)", logger);

        try {
            this.trackDAO.updateTrack(track);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException enfe) {
            sessionContext.setRollbackOnly();
            Helper.logException(enfe, logger);
            throw enfe;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("updateTrack(Track)", logger);
        }
    }

    /**
     * <p>
     * Removes the Track entity identified by the given id from persistence.
     * </p>
     *
     * @param trackId the id of the entity to be removed
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no Track entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrack(long trackId) throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("removeTrack(trackId)", logger);

        try {
            this.trackDAO.removeTrack(trackId);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException enfe) {
            sessionContext.setRollbackOnly();
            Helper.logException(enfe, logger);
            throw enfe;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("removeTrack(trackId)", logger);
        }
    }

    /**
     * <p>
     * Gets the Track entity identified by the given id from persistence.
     * </p>
     *
     * @param trackId the id
     *
     * @return the track entity identified by the id
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no Track entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public Track getTrack(long trackId) throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("getTrack(trackId)", logger);

        try {
            return this.trackDAO.getTrack(trackId);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException enfe) {
            sessionContext.setRollbackOnly();
            Helper.logException(enfe, logger);
            throw enfe;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("getTrack(trackId)", logger);
        }
    }

    /**
     * <p>
     * Gets the active tracks from persistence. An empty list will be returned if there are no active tracks.
     * </p>
     *
     * @return a list containing the active tracks or an empty list if there is no active track
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < Track > getActiveTracks() throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getActiveTracks()", logger);

        try {
            return this.trackDAO.getActiveTracks();
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("getActiveTracks()", logger);
        }
    }

    /**
     * <p>
     * Searches the Track entities that match the given filter. If there is no such entity that matches the given
     * filter an empty list is returned.
     * </p>
     *
     * @param filter the filter
     *
     * @return the matching tracks or an empty list if there are no matching tracks
     *
     * @throws IllegalArgumentException if argument is null
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < Track > searchTracks(Filter filter) throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("searchTracks(filter)", logger);

        try {
            return this.trackDAO.searchTracks(filter);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("searchTracks(filter)", logger);
        }
    }

    /**
     * <p>
     * Adds a project type to the given track.
     * </p>
     *
     * @param projectType the project type to be added to the track
     * @param track the track
     *
     * @throws IllegalArgumentException if any argument is null
     * @throws EntityNotFoundException if the track or project type does not exist in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void addTrackProjectType(Track track, ProjectType projectType)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("addTrackProjectType(track,projectType)", logger);

        try {
            this.trackDAO.addTrackProjectType(track, projectType);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("addTrackProjectType(track,projectType)", logger);
        }
    }

    /**
     * <p>
     * Removes a project type from the given track. Simply delegate to the same method from trackDAO.
     * </p>
     *
     * @param projectType the project type to be removed from the track
     * @param track the track
     *
     * @throws IllegalArgumentException if any argument is null
     * @throws EntityNotFoundException if the track or project type does not exist in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrackProjectType(Track track, ProjectType projectType)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("removeTrackProjectType(track,projectType)", logger);

        try {
            this.trackDAO.removeTrackProjectType(track, projectType);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("removeTrackProjectType(track,projectType)", logger);
        }
    }

    /**
     * <p>
     * Creates a new TrackType entity into persistence. Returns the TrackType instance with id generated.
     * </p>
     *
     * @param trackType the track type
     *
     * @return the track type with id generated
     *
     * @throws IllegalArgumentException if argument is null or if its id is positive
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackType createTrackType(TrackType trackType)
        throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("createTrackType(TrackType)", logger);

        try {
            return this.trackTypeDAO.createTrackType(trackType);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("createTrackType(TrackType)", logger);
        }
    }

    /**
     * <p>
     * Updates the given TrackType instance into persistence.
     * </p>
     *
     * @param trackType the track type to be updated
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if a TrackType entity with TrackType.id does not exist in the persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void updateTrackType(TrackType trackType)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("updateTrackType(trackType)", logger);

        try {
            this.trackTypeDAO.updateTrackType(trackType);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("updateTrackType(trackType)", logger);
        }
    }

    /**
     * <p>
     * Removes the TrackType entity identified by the given id from persistence.
     * </p>
     *
     * @param trackTypeId the id
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no TrackType entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrackType(long trackTypeId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("removeTrackType(trackTypeId)", logger);

        try {
            this.trackTypeDAO.removeTrackType(trackTypeId);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("removeTrackType(trackTypeId)", logger);
        }
    }

    /**
     * <p>
     * Gets the TrackType entity identified by the given id from persistence.
     * </p>
     *
     * @param trackTypeId the id
     *
     * @return the track type identified by the given id
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no TrackType entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackType getTrackType(long trackTypeId)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getTrackType(trackTypeId)", logger);

        try {
            return this.trackTypeDAO.getTrackType(trackTypeId);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("getTrackType(trackTypeId)", logger);
        }
    }

    /**
     * <p>
     * Gets all the TrackType entities from persistence. If there is no TrackType in persistence an empty list is
     * returned.
     * </p>
     *
     * @return a list containing all track types or an empty list if there is no track type
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < TrackType > getAllTrackTypes() throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getAllTrackTypes()", logger);

        try {
            return this.trackTypeDAO.getAllTrackTypes();
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("getAllTrackTypes()", logger);
        }
    }

    /**
     * <p>
     * Creates a new TrackStatus entity into persistence. Returns the TrackStatus instance with id generated.
     * </p>
     *
     * @param trackStatus the track status to persist
     *
     * @return the track status with id generated
     *
     * @throws IllegalArgumentException if argument is null or if its id is positive
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackStatus createTrackStatus(TrackStatus trackStatus)
        throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("createTrackStatus(TrackStatus)", logger);

        try {
            return this.trackStatusDAO.createTrackStatus(trackStatus);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("createTrackStatus(TrackStatus)", logger);
        }
    }

    /**
     * <p>
     * Updates the given TrackStatus instance into persistence.
     * </p>
     *
     * @param trackStatus the track status to be updated
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if a TrackStatus entity with DigitalRunPoints.id does not exist in the
     *         persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void updateTrackStatus(TrackStatus trackStatus)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("updateTrackStatus(TrackStatus)", logger);

        try {
            this.trackStatusDAO.updateTrackStatus(trackStatus);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("updateTrackStatus(TrackStatus)", logger);
        }
    }

    /**
     * <p>
     * Removes the TrackStatus entity identified by the given id from persistence.
     * </p>
     *
     * @param trackStatusId the id
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no TrackStatus entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrackStatus(long trackStatusId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("removeTrackStatus(trackStatusId)", logger);

        try {
            this.trackStatusDAO.removeTrackStatus(trackStatusId);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("removeTrackStatus(trackStatusId)", logger);
        }
    }

    /**
     * <p>
     * Gets the TrackStatus entity identified by the given id from persistence.
     * </p>
     *
     * @param trackStatusId the id
     *
     * @return the track status identified by the id
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no TrackStatus entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackStatus getTrackStatus(long trackStatusId)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getTrackStatus(trackStatusId)", logger);

        try {
            return this.trackStatusDAO.getTrackStatus(trackStatusId);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("getTrackStatus(trackStatusId)", logger);
        }
    }

    /**
     * <p>
     * Gets all TrackStatus entities from persistence. If there is no TrackStatus in persistence an empty list is
     * returned.
     * </p>
     *
     * @return a list containing all the track statuses or an empty list if there is no status
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < TrackStatus > getAllTrackStatuses() throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getAllTrackStatuses()", logger);

        try {
            return this.trackStatusDAO.getAllTrackStatuses();
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("getAllTrackStatuses()", logger);
        }
    }

    /**
     * <p>
     * Creates a new PointsCalculator entity into persistence. Returns the PointsCalculator instance with id generated.
     * </p>
     *
     * @param pointsCalculator the points calculator
     *
     * @return the PointsCalculator instance with id set
     *
     * @throws IllegalArgumentException if argument is null or if its id is positive
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public PointsCalculator createPointsCalculator(PointsCalculator pointsCalculator)
        throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("createPointsCalculator(PointsCalculator)", logger);

        try {
            return this.pointsCalculatorDAO.createPointsCalculator(pointsCalculator);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("createPointsCalculator(PointsCalculator)", logger);
        }
    }

    /**
     * <p>
     * Updates the given PointsCalculator instance into persistence.
     * </p>
     *
     * @param pointsCalculator the points calculator to be updated
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if a PointsCalculator entity with DigitalRunPoints.id does not exist in the
     *         persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void updatePointsCalculator(PointsCalculator pointsCalculator)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("updatePointsCalculator(PointsCalculator)", logger);

        try {
            this.pointsCalculatorDAO.updatePointsCalculator(pointsCalculator);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("updatePointsCalculator(PointsCalculator)", logger);
        }
    }

    /**
     * Removes the PointsCalculator type entity identified by the given id from persistence. Simply delegate to the
     * same method from pointsCalculatorDAO. Logging will be performed as described in 1.3.1 algorithm. If errors
     * occur, before throwing an exception, mark the current transaction for rollback:
     * sessionContext.setRollbackOnly().
     *
     * @param pointsCalculatorId the id
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no PointsCalculator entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removePointsCalculator(long pointsCalculatorId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("removePointsCalculator(pointsCalculatorId)", logger);

        try {
            this.pointsCalculatorDAO.removePointsCalculator(pointsCalculatorId);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("removePointsCalculator(pointsCalculatorId)", logger);
        }
    }

    /**
     * <p>
     * Gets the PointsCalculator entity identified by the given id from persistence.
     * </p>
     *
     * @param pointsCalculatorId the id
     *
     * @return the PointsCalculator instance identified by id
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no PointsCalculator entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public PointsCalculator getPointsCalculator(long pointsCalculatorId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("getPointsCalculator(pointsCalculatorId)", logger);

        try {
            return this.pointsCalculatorDAO.getPointsCalculator(pointsCalculatorId);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("getPointsCalculator(pointsCalculatorId)", logger);
        }
    }

    /**
     * <p>
     * Gets all the PointsCalculator entities from persistence. If there is no PointsCalculator in persistence an empty
     * list is returned.
     * </p>
     *
     * @return a list containing all points calculators or an empty list if there is no points calculator
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < PointsCalculator > getAllPointsCalculators()
        throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getAllPointsCalculators()", logger);

        try {
            return this.pointsCalculatorDAO.getAllPointsCalculators();
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("getAllPointsCalculators()", logger);
        }
    }

    /**
     * <p>
     * Gets the ProjectType entity identified by the given id from the persistence.
     * </p>
     *
     * @param projectTypeId the id
     *
     * @return the project type identified by id
     *
     * @throws IllegalArgumentException id argument is negative
     * @throws EntityNotFoundException if there is no ProjectType with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public ProjectType getProjectType(long projectTypeId)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getProjectType(projectTypeId)", logger);

        try {
            return this.projectTypeDAO.getProjectType(projectTypeId);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("getProjectType(projectTypeId)", logger);
        }
    }

    /**
     * <p>
     * Gets all ProjectTypes entities from persistence. If there is no ProjectType in persistence an empty list is
     * returned.
     * </p>
     *
     * @return a list containing all project types or an empty list if there is no project type
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < ProjectType > getAllProjectTypes() throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getAllProjectTypes()", logger);

        try {
            return this.projectTypeDAO.getAllProjectTypes();
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            Helper.logException(iae, logger);
            throw iae;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            sessionContext.setRollbackOnly();
            Helper.logException(e, logger);
            throw e;
        } finally {
            Helper.logExitInfo("getAllProjectTypes()", logger);
        }
    }
}