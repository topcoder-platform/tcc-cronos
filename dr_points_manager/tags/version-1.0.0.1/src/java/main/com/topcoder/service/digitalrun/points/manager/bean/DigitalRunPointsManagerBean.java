/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.manager.bean;

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

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchStrategy;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.digitalrun.entity.DigitalRunPoints;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsReferenceType;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;
import com.topcoder.service.digitalrun.points.ConfigurationProvider;
import com.topcoder.service.digitalrun.points.DigitalRunPointsDAO;
import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerBeanConfigurationException;
import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerLocal;
import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerPersistenceException;
import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerRemote;
import com.topcoder.service.digitalrun.points.DigitalRunPointsOperationDAO;
import com.topcoder.service.digitalrun.points.DigitalRunPointsReferenceTypeDAO;
import com.topcoder.service.digitalrun.points.DigitalRunPointsStatusDAO;
import com.topcoder.service.digitalrun.points.DigitalRunPointsTypeDAO;
import com.topcoder.service.digitalrun.points.EntityNotFoundException;
import com.topcoder.service.digitalrun.points.Helper;
import com.topcoder.service.digitalrun.points.dao.implementations.AbstractDAO;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsDAO;
import com.topcoder.util.datavalidator.ObjectValidator;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This class is a stateless session bean that manages digital run points entities.<br>
 * It implements the DigitalRunPointsManagerLocal and DigitalRunPointsManagerRemote interfaces. All
 * public methods from this class perform logging using LoggingWrapper component. This bean will not
 * access the persistence directly; it will use five daos to access the persistence. These daos will
 * be creates in initialize post construct method using Object Factory. The logger will also be
 * initialized in initialize method. <br>
 * This class will be annotated with:<br>
 * <br>
 * Stateless <br>
 * TransactionManagement(TransactionManagementType.CONTAINER) <br>
 * TransactionAttribute(TransactionAttributeType.REQUIRED)
 * </p>
 * <p>
 * <strong>Simple Config</strong>
 *
 * <pre>
 * &lt;CMConfig&gt;
 *  &lt;!-- Configuration for ObjectFactory --&gt;
 *  &lt;Config name=&quot;object_factory_child&quot;&gt;
 *  &lt;Property name=&quot;PointsDAOImpl&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsDAO&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;PointsTypeDAOImpl&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsTypeDAO&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;PointsOperationDAOImpl&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.service.digitalrun.points.dao.implementations.
 *  JPADigitalRunPointsOperationDAO&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;PointsReferenceTypeDAOImpl&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.service.digitalrun.points.dao.implementations.
 *  JPADigitalRunPointsReferenceTypeDAO&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;PointsStatusDAOImpl&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsStatusDAO&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;SearchStrategyImpl&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.service.digitalrun.points.dao.implementations.MockSearchStrategy&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;ValidatorKey_1&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.service.digitalrun.points.dao.implementations.AlwaysTrueValidator&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Config&gt;
 *
 *  &lt;!-- Configuration for SearchBundle --&gt;
 *  &lt;Config name=&quot;search_bundle_child&quot;&gt;
 *  &lt;Property name=&quot;name&quot;&gt;
 *  &lt;Value&gt;Search_Name&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;context&quot;&gt;
 *  &lt;Value&gt;Search_Context&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;search_strategy_key&quot;&gt;
 *  &lt;Value&gt;SearchStrategyImpl&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;fields&quot;&gt;
 *  &lt;Property name=&quot;field_1&quot;&gt;
 *  &lt;Value&gt;ValidatorKey_1&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;field_2&quot;&gt;
 *  &lt;Value&gt;ValidatorKey_1&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;aliases&quot;&gt;
 *  &lt;Property name=&quot;alia_1&quot;&gt;
 *  &lt;Value&gt;Name_1&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;alia_2&quot;&gt;
 *  &lt;Value&gt;Name_2&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Config&gt;
 *
 *  &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 * <p>
 * <strong>Simple Code</strong>
 *
 * <pre>
 * // lookup the bean
 *  DigitalRunPointsManagerRemote bean = ...;
 *
 *  // create a DigitalRunPoints instance and set its fields
 *  DigitalRunPoints drp = new DigitalRunPoints();
 *  // ceate a DigitalRunPointsStatus, DigitalRunPointsType,
 *  // DigitalRunPointsReferenceType and DigitalRunPointsOperation
 *  // entities and set them on DigitalRunPoints instance then set the
 *  // rest of DigitalRunPoints fields; among others set amount to 100
 *  drp.setAmount(100);
 *  setPoints(drp);
 *
 *  // create DigitalRunPoints entity into persistence
 *  drp = bean.createDigitalRunPoints(drp);
 *
 *  // the drp entity has now an id; the amount is 100
 *  // modify drp.amount field and update
 *  drp.setAmount(200);
 *  bean.updateDigitalRunPoints(drp);
 *
 *  // get a DigitalRunPoints entity by specifying an id
 *  long id = drp.getId();
 *  DigitalRunPoints drp1 = bean.getDigitalRunPoints(id);
 *  // the DigitalRunPoints created above was returned
 *
 *  // search for DigitalRunPoints entities using a filter
 *  EqualToFilter filter = new EqualToFilter(&quot;key_1&quot;, new Long(1));
 *  List&lt;DigitalRunPoints&gt; drpList = bean.searchDigitalRunPoints(filter);
 *  // the list contains the DigitalRunPoints entity created above
 *
 *  // remove the created DigitalRunPoints from persistence
 *  bean.removeDigitalRunPoints(id);
 *
 *  // 1.3.2 How to manage DigitalRunPointsType instances
 *
 *  // create digital run points type
 *  DigitalRunPointsType type = bean.createDigitalRunPointsType(getType());
 *
 *  // update digital run points type
 *  bean.updateDigitalRunPointsType(type);
 *
 *  // get digital run points type 10
 *  DigitalRunPointsType type1 = bean.getDigitalRunPointsType(type.getId());
 *
 *  // get all digital run points types
 *  List&lt;DigitalRunPointsType&gt; types = bean.getAllDigitalRunPointsTypes();
 *
 *  // remove digital run points type
 *  bean.removeDigitalRunPointsType(type.getId());
 *
 *  // 1.3.3 How to manage DigitalRunPointsReferenceType instances
 *
 *  // create digital run points reference type
 *  DigitalRunPointsReferenceType referenceType = bean
 *  .createDigitalRunPointsReferenceType(getReferenceType());
 *
 *  // update digital run points reference type
 *  bean.updateDigitalRunPointsReferenceType(referenceType);
 *
 *  // get digital run points reference type
 *  DigitalRunPointsReferenceType referenceType1 = bean.getDigitalRunPointsReferenceType(referenceType
 *  .getId());
 *
 *  // get all digital run points reference types
 *  List&lt;DigitalRunPointsReferenceType&gt; referenceTypes = bean.getAllDigitalRunPointsReferenceTypes();
 *
 *  // remove digital run points reference type
 *  bean.removeDigitalRunPointsReferenceType(referenceType.getId());
 *
 *  // 1.3.4 How to manage DigitalRunPointsStatus instances
 *
 *  // create digital run points status
 *  DigitalRunPointsStatus status = bean.createDigitalRunPointsStatus(getStatus());
 *
 *  // update digital run points status
 *  bean.updateDigitalRunPointsStatus(status);
 *
 *  // get digital run points status
 *  DigitalRunPointsStatus status1 = bean.getDigitalRunPointsStatus(status.getId());
 *
 *  // get all digital run points statuses
 *  List&lt;DigitalRunPointsStatus&gt; statuses = bean.getAllDigitalRunPointsStatuses();
 *
 *  // remove digital run points status
 *  bean.removeDigitalRunPointsStatus(status.getId());
 *
 *  // 1.3.5 How to manage DigitalRunPointsOperation instances
 *
 *  // create digital run points operation
 *  DigitalRunPointsOperation operation = bean.createDigitalRunPointsOperation(getPO());
 *
 *  // update digital run points operation
 *  bean.updateDigitalRunPointsOperation(operation);
 *
 *  // get digital run points operation
 *  DigitalRunPointsOperation operation1 = bean.getDigitalRunPointsOperation(operation.getId());
 *
 *  // get all digital run points operation
 *  List&lt;DigitalRunPointsOperation&gt; operations = bean.getAllDigitalRunPointsOperations();
 *
 *  // remove digital run points operation
 *  bean.removeDigitalRunPointsOperation(operation.getId());
 * </pre>
 *
 * </p>
 * <p>
 * Thread Safety: Since the container will manage the transactions this class can be considered as
 * thread safe.<br>
 * This bean is also immutable.
 * </p>
 *
 * @author DanLazar, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DigitalRunPointsManagerBean implements DigitalRunPointsManagerLocal,
        DigitalRunPointsManagerRemote {
    /**
     * Present the class name.
     */
    private static final String CLASS_NAME = DigitalRunPointsManagerBean.class.getName();

    /**
     * The dao used to manage DigitalRunPoints entities. It will be initialized in the initialize
     * method and never changed. It cannot be null after initialization. Used in methods that manage
     * DigitalRunPoints entities.
     */
    private DigitalRunPointsDAO pointsDAO = null;
    /**
     * The dao used to manage DigitalRunPointsType entities. It will be initialized in the
     * initialize method and never changed. It cannot be null after initialization. Used in methods
     * that manage DigitalRunPointsType entities.
     */
    private DigitalRunPointsTypeDAO pointsTypeDAO = null;
    /**
     * The dao used to manage DigitalRunPointsOperation entities. It will be initialized in the
     * initialize method and never changed. It cannot be null after initialization. Used in methods
     * that manage DigitalRunPointsOperation entities.
     */
    private DigitalRunPointsOperationDAO pointsOperationDAO = null;
    /**
     * The dao used to manage DigitalRunPointsReferenceType entities. It will be initialized in the
     * initialize method and never changed. It cannot be null after initialization. Used in methods
     * that manage DigitalRunPointsReferenceType entities.
     */
    private DigitalRunPointsReferenceTypeDAO pointsReferenceTypeDAO = null;
    /**
     * The dao used to manage DigitalRunPointsStatus entities. It will be initialized in the
     * initialize method and never changed. It cannot be null after initialization. Used in methods
     * that manage DigitalRunPointsStatus entities.
     */
    private DigitalRunPointsStatusDAO pointsStatusDAO = null;
    /**
     * Represents the session context of this bean. It is annotated with Resource so that it will be
     * initialized through injection by the container. It will not be changed after initialization.
     * It cannot be null after initialization. It is used in getEntityManager to obtain an
     * EntityManager instance.
     */
    @Resource
    private final SessionContext sessionContext = null;
    /**
     * The unit name. It is annotated with Resource so that it will be initialized by the container
     * through injection. It is required that the container initializes this field and after
     * initialization this field cannot be null and empty string and can no longer be modified.
     */
    @Resource(name = "unitName")
    private String unitName;
    /**
     * The log name. It is annotated with Resource so that it will be initialized by the container
     * through injection. If the container does not initialize this field through injection it means
     * that logging is disabled. If initialized by container then it cannot be null or empty string.
     * After the container initializes the fields annotated with Resource, the logName field can no
     * longer be modified. If it is not null or empty string, this field will be used in initialize
     * method to create a Log instance.
     */
    @Resource(name = "logName")
    private final String logName = null;
    /**
     * The dao key used with ObjectFactory to create a DigitalRunPointsDAO instance. It is annotated
     * with Resource so that it will be initialized by the container through injection. It is
     * required that the container initializes this field and after initialization this field cannot
     * be null and empty string and can no longer be modified. It will be used in initialize method
     * to create a DigitalRunPointsDAO instance with ObjectFactory.
     */
    @Resource(name = "pointsDAOKey")
    private final String pointsDAOKey = null;
    /**
     * The dao key used with ObjectFactory to create a DigitalRunPointsTypeDAO instance. It is
     * annotated with Resource so that it will be initialized by the container through injection. It
     * is required that the container initializes this field and after initialization this field
     * cannot be null and empty string and can no longer be modified. It will be used in initialize
     * method to create a DigitalRunPointsTypeDAO instance with ObjectFactory.
     */
    @Resource(name = "pointsTypeDAOKey")
    private final String pointsTypeDAOKey = null;
    /**
     * The dao key used with ObjectFactory to create a DigitalRunPointsOperationDAO instance. It is
     * annotated with Resource so that it will be initialized by the container through injection. It
     * is required that the container initializes this field and after initialization this field
     * cannot be null and empty string and can no longer be modified. It will be used in initialize
     * method to create a DigitalRunPointsOperationDAO instance with ObjectFactory.
     */
    @Resource(name = "pointsOperationDAOKey")
    private final String pointsOperationDAOKey = null;
    /**
     * The dao key used with ObjectFactory to create a DigitalRunPointsReferenceTypeDAO instance. It
     * is annotated with Resource so that it will be initialized by the container through injection.
     * It is required that the container initializes this field and after initialization this field
     * cannot be null and empty string and can no longer be modified. It will be used in initialize
     * method to create a DigitalRunPointsReferenceTypeDAO instance with ObjectFactory.
     */
    @Resource(name = "pointsReferenceTypeDAOKey")
    private final String pointsReferenceTypeDAOKey = null;
    /**
     * The dao key used with ObjectFactory to create a DigitalRunPointsStatusDAO instance. It is
     * annotated with Resource so that it will be initialized by the container through injection. It
     * is required that the container initializes this field and after initialization this field
     * cannot be null and empty string and can no longer be modified. It will be used in initialize
     * method to create a DigitalRunPointsStatusDAO instance with ObjectFactory.
     */
    @Resource(name = "pointsStatusDAOKey")
    private final String pointsStatusDAOKey = null;
    /**
     * The logger that will be used to perform logging. If logName was injected by container, then
     * this field will be initialized to Log instance in the initialize method, otherwise it will
     * remain set to null meaning that logging is disabled. After the container calls initialize
     * method this field can no longer be modified.It will be used in all public methods of this
     * class to perform logging.
     */
    private Log logger = null;

    /**
     * Empty constructor.
     */
    public DigitalRunPointsManagerBean() {
        // Empty
    }

    /**
     * This method is called after this bean was constructed by the EJB container. This method is
     * marked with PostConstruct annotation. It will initialize the logger field if logName is not
     * null or empty string and it will also initialize the searchBundle field. It will also
     * validate the two fields annotated with Resource.
     *
     * @throws DigitalRunPointsManagerBeanConfigurationException
     *             if logName is empty string or if ...DAOKey is null or empty string, or if null is
     *             returned from ConfigurationProvider.getConfiguration(), or if errors occur when
     *             creating the daos with ObjectFactory or if errors occur when getting the Log
     *             instance from LogManager
     */
    @PostConstruct
    private void initialize() {
        checkConfigedString(this.pointsDAOKey, "pointsDAOKey");
        checkConfigedString(this.pointsTypeDAOKey, "pointsTypeDAOKey");
        checkConfigedString(this.pointsOperationDAOKey, "pointsOperationDAOKey");
        checkConfigedString(this.pointsReferenceTypeDAOKey, "pointsReferenceTypeDAOKey");
        checkConfigedString(this.pointsStatusDAOKey, "pointsStatusDAOKey");

        checkConfigedString(this.unitName, "unitName");
        if (this.sessionContext == null) {
            throw new DigitalRunPointsManagerBeanConfigurationException("sessionContext should not be null.");
        }

        // if logName is not null or empty string initialize the logger field
        if (logName != null && logName.trim().length() != 0) {
            this.logger = LogManager.getLog(logName);
        }

        try {
            // get configuration, necessary to create the dao instances, from ConfigurationProvider
            // class
            ConfigurationObject config = ConfigurationProvider.getConfiguration();
            checkConfigurationObject(config, "Configuration");

            // get the child necessary for Object Factory
            ConfigurationObject ofChild = config.getChild("object_factory_child");
            checkConfigurationObject(ofChild, "Configuration of ObjectFactory");

            // create a ConfigurationObjectSpecificationFactory instance
            ConfigurationObjectSpecificationFactory specification = new ConfigurationObjectSpecificationFactory(
                    ofChild);
            // create an ObjectFactory instance
            ObjectFactory of = new ObjectFactory(specification);

            // get the child that contains configuration for SearchBundle
            ConfigurationObject sbChild = config.getChild("search_bundle_child");
            checkConfigurationObject(sbChild, "Configuration of SearchBundle");
            // get SearchBundle name, context and strategy objectFactory key
            String sbName = (String) sbChild.getPropertyValue("name");
            String sbContext = (String) sbChild.getPropertyValue("context");
            String sbStrategyKey = (String) sbChild.getPropertyValue("search_strategy_key");
            // create searchStrategy
            SearchStrategy searchStrategy = (SearchStrategy) of.createObject(sbStrategyKey);

            // create the fields map
            ConfigurationObject sbFieldsChild = sbChild.getChild("fields");
            checkConfigurationObject(sbFieldsChild, "Configuration of Fields");
            String[] fieldKeys = sbFieldsChild.getAllPropertyKeys();
            Map<String, ObjectValidator> fields = new HashMap<String, ObjectValidator>();
            for (String fieldKey : fieldKeys) {
                String validatorKey = (String) sbFieldsChild.getPropertyValue(fieldKey);
                ObjectValidator ov = (ObjectValidator) of.createObject(validatorKey);
                fields.put(fieldKey, ov);
            }

            // create the alias map
            ConfigurationObject sbAliasChild = sbChild.getChild("aliases");
            checkConfigurationObject(sbAliasChild, "Configuration of Aliases");
            String[] aliaKeys = sbAliasChild.getAllPropertyKeys();
            Map<String, String> alias = new HashMap<String, String>();
            for (String aliaKey : aliaKeys) {
                alias.put(aliaKey, (String) sbAliasChild.getPropertyValue(aliaKey));
            }

            // create the SearchBundle instance
            SearchBundle searchBundle = new SearchBundle(sbName, fields, alias, sbContext, searchStrategy);

            // create a DigitalRunPointsDAO instance and set the field
            this.pointsDAO = (DigitalRunPointsDAO) of.createObject(this.pointsDAOKey);
            // set logger (if provided), sessionContext and unitName if the instance has the setters
            if (pointsDAO instanceof AbstractDAO) {
                ((AbstractDAO) pointsDAO).setLogger(logger);
                ((AbstractDAO) pointsDAO).setSessionContext(sessionContext);
                ((AbstractDAO) pointsDAO).setUnitName(unitName);
            }

            if (pointsDAO instanceof JPADigitalRunPointsDAO) {
                ((JPADigitalRunPointsDAO) pointsDAO).setSearchBundle(searchBundle);
            }

            // create a DigitalRunPointsTypeDAO instance and set the field
            this.pointsTypeDAO = (DigitalRunPointsTypeDAO) of.createObject(this.pointsTypeDAOKey);
            // set logger (if provided), sessionContext and unitName if the instance has the setters
            if (pointsTypeDAO instanceof AbstractDAO) {
                ((AbstractDAO) pointsTypeDAO).setLogger(logger);
                ((AbstractDAO) pointsTypeDAO).setSessionContext(sessionContext);
                ((AbstractDAO) pointsTypeDAO).setUnitName(unitName);
            }

            // create a DigitalRunPointsOperationDAO instance and set the field
            this.pointsOperationDAO = (DigitalRunPointsOperationDAO) of
                    .createObject(this.pointsOperationDAOKey);
            // set logger (if provided), sessionContext and unitName if the instance has the setters
            if (pointsOperationDAO instanceof AbstractDAO) {
                ((AbstractDAO) pointsOperationDAO).setLogger(logger);
                ((AbstractDAO) pointsOperationDAO).setSessionContext(sessionContext);
                ((AbstractDAO) pointsOperationDAO).setUnitName(unitName);
            }

            // create a DigitalRunPointsReferenceTypeDAO instance and set the field
            this.pointsReferenceTypeDAO = (DigitalRunPointsReferenceTypeDAO) of
                    .createObject(this.pointsReferenceTypeDAOKey);
            // set logger (if provided), sessionContext and unitName if the instance has the setters
            if (pointsReferenceTypeDAO instanceof AbstractDAO) {
                ((AbstractDAO) pointsReferenceTypeDAO).setLogger(logger);
                ((AbstractDAO) pointsReferenceTypeDAO).setSessionContext(sessionContext);
                ((AbstractDAO) pointsReferenceTypeDAO).setUnitName(unitName);
            }

            // create a DigitalRunPointsStatusDAO instance and set the field
            this.pointsStatusDAO = (DigitalRunPointsStatusDAO) of.createObject(this.pointsStatusDAOKey);
            // set logger (if provided), sessionContext and unitName if the instance has the setters
            if (pointsStatusDAO instanceof AbstractDAO) {
                ((AbstractDAO) pointsStatusDAO).setLogger(logger);
                ((AbstractDAO) pointsStatusDAO).setSessionContext(sessionContext);
                ((AbstractDAO) pointsStatusDAO).setUnitName(unitName);
            }

        } catch (ClassCastException e) {
            throw new DigitalRunPointsManagerBeanConfigurationException("ClassCastException occurs", e);
        } catch (ConfigurationAccessException e) {
            throw new DigitalRunPointsManagerBeanConfigurationException(
                    "ConfigurationAccessException occurs while getting child", e);
        } catch (IllegalReferenceException e) {
            throw new DigitalRunPointsManagerBeanConfigurationException(
                    "IllegalReferenceException occurs while creating ConfigurationObjectSpecificationFactory",
                    e);
        } catch (SpecificationConfigurationException e) {
            throw new DigitalRunPointsManagerBeanConfigurationException(
                    "SpecificationConfigurationException occurs while creating ConfigurationObjectSpecificationFactory",
                    e);
        } catch (InvalidClassSpecificationException e) {
            throw new DigitalRunPointsManagerBeanConfigurationException(
                    "InvalidClassSpecificationException occurs while creating object", e);
        }
    }

    /**
     * Creates a new DigitalRunPoints entity into persistence. Returns the DigitalRunPoints instance
     * with id generated.<br>
     *
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param points
     *            the entity to be created
     * @return the entity with id set
     */
    public DigitalRunPoints createDigitalRunPoints(DigitalRunPoints points)
        throws DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".createDigitalRunPoints()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        DigitalRunPoints res = null;
        try {
            res = this.pointsDAO.createDigitalRunPoints(points);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;

    }

    /**
     * Updates the given DigitalRunPoints instance into persistence.<br>
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws EntityNotFoundException
     *             if a digital run points entity with DigitalRunPoints.id does not exist in the
     *             persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param points
     *            the entity to be updated
     */
    public void updateDigitalRunPoints(DigitalRunPoints points) throws EntityNotFoundException,
        DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".updateDigitalRunPoints()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        try {
            this.pointsDAO.updateDigitalRunPoints(points);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
    }

    /**
     * Removes the digital run points entity identified by the given id from persistence.<br>
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points entity with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsId
     *            the id of the entity to be removed
     */
    public void removeDigitalRunPoints(long pointsId) throws EntityNotFoundException,
        DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".removeDigitalRunPoints()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        try {
            this.pointsDAO.removeDigitalRunPoints(pointsId);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
    }

    /**
     * Gets the digital run points entity identified by the given id from persistence.<br>
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points entity with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param id
     *            the id
     * @return the points entity identified by the id
     */
    public DigitalRunPoints getDigitalRunPoints(long id) throws EntityNotFoundException,
        DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".getDigitalRunPoints()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        DigitalRunPoints res = null;
        try {
            res = this.pointsDAO.getDigitalRunPoints(id);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;
    }

    /**
     * Searches the digital run points entities that match the given filter. If there is no such
     * entity that matches the given filter an empty list is returned.<br>
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param filter
     *            the filter
     * @return the matching entities or an empty list if no matching entities were found
     */
    public List<DigitalRunPoints> searchDigitalRunPoints(Filter filter)
        throws DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".searchDigitalRunPoints()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        List<DigitalRunPoints> res = null;
        try {
            res = this.pointsDAO.searchDigitalRunPoints(filter);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;
    }

    /**
     * Creates a new DigitalRunPointsType entity into persistence. Returns the DigitalRunPointsType
     * instance with id generated.<br>
     *
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsType
     *            the entity to be created
     * @return the entity with the id set
     */
    public DigitalRunPointsType createDigitalRunPointsType(DigitalRunPointsType pointsType)
        throws DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".createDigitalRunPointsType()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        DigitalRunPointsType res = null;
        try {
            res = this.pointsTypeDAO.createDigitalRunPointsType(pointsType);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;
    }

    /**
     * Updates the given DigitalRunPointsType instance into persistence. <br>
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws EntityNotFoundException
     *             if a digital run points type entity with DigitalRunPoints.id does not exist in
     *             the persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsType
     *            the entity to be updated
     */
    public void updateDigitalRunPointsType(DigitalRunPointsType pointsType) throws EntityNotFoundException,
        DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".updateDigitalRunPointsType()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        try {
            this.pointsTypeDAO.updateDigitalRunPointsType(pointsType);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
    }

    /**
     * Removes the digital run points type entity identified by the given id from persistence.<br>
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points type entity with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsTypeId
     *            the id of the entity to be removed
     */
    public void removeDigitalRunPointsType(long pointsTypeId) throws EntityNotFoundException,
        DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".removeDigitalRunPointsType()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        try {
            this.pointsTypeDAO.removeDigitalRunPointsType(pointsTypeId);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
    }

    /**
     * Gets the digital run points type entity identified by the given id from persistence.<br>
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points type entity with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param id
     *            the id
     * @return the points type entity identified by the id
     */
    public DigitalRunPointsType getDigitalRunPointsType(long id) throws EntityNotFoundException,
        DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".getDigitalRunPointsType()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        DigitalRunPointsType res = null;
        try {
            res = this.pointsTypeDAO.getDigitalRunPointsType(id);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;
    }

    /**
     * Gets all the digital run points types from persistence. If there is no digital run points
     * type in persistence an empty list is returned.<br>
     *
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @return a list containing all types or an empty list if there is no type
     */
    public List<DigitalRunPointsType> getAllDigitalRunPointsTypes()
        throws DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".getAllDigitalRunPointsTypes()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        List<DigitalRunPointsType> res = null;
        try {
            res = this.pointsTypeDAO.getAllDigitalRunPointsTypes();
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;
    }

    /**
     * Creates a new DigitalRunPointsStatus entity into persistence. Returns the
     * DigitalRunPointsStatus instance with id generated.<br>
     *
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsStatus
     *            the entity to be created
     * @return the entity with the id set
     */
    public DigitalRunPointsStatus createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)
        throws DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".createDigitalRunPointsStatus()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        DigitalRunPointsStatus res = null;
        try {
            res = this.pointsStatusDAO.createDigitalRunPointsStatus(pointsStatus);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;
    }

    /**
     * Updates the given DigitalRunPointsStatus instance into persistence. <br>
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws EntityNotFoundException
     *             if a digital run points status entity with DigitalRunPoints.id does not exist in
     *             the persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsStatus
     *            the entity to be updated
     */
    public void updateDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)
        throws EntityNotFoundException, DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".updateDigitalRunPointsStatus()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        try {
            this.pointsStatusDAO.updateDigitalRunPointsStatus(pointsStatus);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
    }

    /**
     * Removes the digital run points status entity identified by the given id from persistence.<br>
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points status entity with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsStatusId
     *            the id of the entity to be removed
     */
    public void removeDigitalRunPointsStatus(long pointsStatusId) throws EntityNotFoundException,
        DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".removeDigitalRunPointsStatus()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        try {
            this.pointsStatusDAO.removeDigitalRunPointsStatus(pointsStatusId);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
    }

    /**
     * Gets the digital run points status entity identified by the given id from persistence.<br>
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points status entity with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param id
     *            the id
     * @return the points status entity identified by the id
     */
    public DigitalRunPointsStatus getDigitalRunPointsStatus(long id) throws EntityNotFoundException,
        DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".getDigitalRunPointsStatus()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        DigitalRunPointsStatus res = null;
        try {
            res = this.pointsStatusDAO.getDigitalRunPointsStatus(id);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;
    }

    /**
     * Gets all the digital run points statuses from persistence. If there is no digital run points
     * status in persistence an empty list is returned.<br>
     *
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @return a list containing all statuses or an empty list if there is no status
     */
    public List<DigitalRunPointsStatus> getAllDigitalRunPointsStatuses()
        throws DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".getAllDigitalRunPointsStatuses()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        List<DigitalRunPointsStatus> res = null;
        try {
            res = this.pointsStatusDAO.getAllDigitalRunPointsStatuses();
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;
    }

    /**
     * Creates a new DigitalRunPointsOperation entity into persistence. Returns the
     * DigitalRunPointsOperation instance with id generated.<br>
     *
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsOperation
     *            the entity to be created
     * @return the entity with the id set
     */
    public DigitalRunPointsOperation createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)
        throws DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".createDigitalRunPointsOperation()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        DigitalRunPointsOperation res = null;
        try {
            res = this.pointsOperationDAO.createDigitalRunPointsOperation(pointsOperation);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;

    }

    /**
     * Updates the given DigitalRunPointsOperation instance into persistence.<br>
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws EntityNotFoundException
     *             if a digital run points operation entity with DigitalRunPoints.id does not exist
     *             in the persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsOperation
     *            the entity to be updated
     */
    public void updateDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)
        throws EntityNotFoundException, DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".updateDigitalRunPointsOperation()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        try {
            this.pointsOperationDAO.updateDigitalRunPointsOperation(pointsOperation);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
    }

    /**
     * Removes the digital run points operation entity identified by the given id from persistence.<br>
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points operation entity with the given id in
     *             persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsOperationId
     *            the id of the entity to be removed
     */
    public void removeDigitalRunPointsOperation(long pointsOperationId) throws EntityNotFoundException,
        DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".removeDigitalRunPointsOperation()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        try {
            this.pointsOperationDAO.removeDigitalRunPointsOperation(pointsOperationId);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
    }

    /**
     * Gets the digital run points operation entity identified by the given id from persistence.<br>
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points operation entity with the given id in
     *             persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param id
     *            the id
     * @return the points operation entity identified by the id
     */
    public DigitalRunPointsOperation getDigitalRunPointsOperation(long id) throws EntityNotFoundException,
        DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".getDigitalRunPointsOperation()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        DigitalRunPointsOperation res = null;
        try {
            res = this.pointsOperationDAO.getDigitalRunPointsOperation(id);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;
    }

    /**
     * Gets all the digital run points operations from persistence. If there is no digital run
     * points operation in persistence an empty list is returned.<br>
     *
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @return a list containing all operations or an empty list if there is no operation
     */
    public List<DigitalRunPointsOperation> getAllDigitalRunPointsOperations()
        throws DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".getAllDigitalRunPointsOperations()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        List<DigitalRunPointsOperation> res = null;
        try {
            res = this.pointsOperationDAO.getAllDigitalRunPointsOperations();
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;
    }

    /**
     * Creates a new DigitalRunPointsReferenceType entity into persistence. Returns the
     * DigitalRunPointsReference instance with id generated.<br>
     *
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsReferenceType
     *            the entity to be created
     * @return the entity with the id set
     */
    public DigitalRunPointsReferenceType createDigitalRunPointsReferenceType(
            DigitalRunPointsReferenceType pointsReferenceType)
        throws DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".createDigitalRunPoints()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        DigitalRunPointsReferenceType res = null;
        try {
            res = this.pointsReferenceTypeDAO.createDigitalRunPointsReferenceType(pointsReferenceType);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;
    }

    /**
     * Updates the given DigitalRunPointsReference instance into persistence.<br>
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws EntityNotFoundException
     *             if a digital run points reference type entity with DigitalRunPoints.id does not
     *             exist in the persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsReferenceType
     *            the entity to be updated
     */
    public void updateDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)
        throws EntityNotFoundException, DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".updateDigitalRunPointsReferenceType()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        try {
            this.pointsReferenceTypeDAO.updateDigitalRunPointsReferenceType(pointsReferenceType);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
    }

    /**
     * Removes the digital run points reference type entity identified by the given id from
     * persistence. <br>
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points reference type entity with the given id in
     *             persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsReferenceTypeId
     *            the id of the entity to be removed
     */
    public void removeDigitalRunPointsReferenceType(long pointsReferenceTypeId)
        throws EntityNotFoundException, DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".removeDigitalRunPointsReferenceType()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        try {
            this.pointsReferenceTypeDAO.removeDigitalRunPointsReferenceType(pointsReferenceTypeId);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
    }

    /**
     * Gets the digital run points reference type entity identified by the given id from
     * persistence.<br>
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points reference type entity with the given id in
     *             persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param id
     *            the id
     * @return the points reference type entity identified by the id
     */
    public DigitalRunPointsReferenceType getDigitalRunPointsReferenceType(long id)
        throws EntityNotFoundException, DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".getDigitalRunPointsReferenceType()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        DigitalRunPointsReferenceType res = null;
        try {
            res = this.pointsReferenceTypeDAO.getDigitalRunPointsReferenceType(id);
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;
    }

    /**
     * Gets all the digital run points reference types from persistence. If there is no digital run
     * points reference type in persistence an empty list is returned.<br>
     *
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @return a list containing all reference types or an empty list if there is no reference type
     */
    public List<DigitalRunPointsReferenceType> getAllDigitalRunPointsReferenceTypes()
        throws DigitalRunPointsManagerPersistenceException {
        String methodName = CLASS_NAME + ".createDigitalRunPoints()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        List<DigitalRunPointsReferenceType> res = null;
        try {
            res = this.pointsReferenceTypeDAO.getAllDigitalRunPointsReferenceTypes();
        } catch (DigitalRunPointsManagerPersistenceException e) {
            Helper.logException(logger, e, methodName);
            sessionContext.setRollbackOnly();
            throw e;
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return res;

    }

    /**
     * Check the config string. If the string is null or empty, throw
     * DigitalRunPointsManagerBeanConfigurationException.
     *
     * @param key
     *            the config string key
     * @param keyName
     *            the config string name
     * @throws DigitalRunPointsManagerBeanConfigurationException
     *             the configuration exception
     */
    private void checkConfigedString(String key, String keyName) {
        if (key == null || key.trim().length() == 0) {
            throw new DigitalRunPointsManagerBeanConfigurationException(keyName
                    + " is required. The field should not be null and emtpy.");
        }
    }

    /**
     * Check the configuration object. If the object is null, throw
     * DigitalRunPointsManagerBeanConfigurationException.
     *
     * @param co
     *            the configuration object
     * @param coName
     *            the configuration object name
     * @throws DigitalRunPointsManagerBeanConfigurationException
     *             the configuration exception
     */
    private void checkConfigurationObject(ConfigurationObject co, String coName) {
        if (co == null) {
            throw new DigitalRunPointsManagerBeanConfigurationException(coName
                    + " is required. This configuration should not be null.");
        }
    }
}
