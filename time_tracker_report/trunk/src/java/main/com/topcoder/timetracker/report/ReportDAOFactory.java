/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import com.topcoder.timetracker.report.informix.InformixReportDAO;

import com.topcoder.util.config.Property;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;

import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;

/**
 * <p>
 * The scope of this class is to return the correct ReportDAO from the type. The map is filled with
 * InformixReportDAO and others ReportDAO defined in configuration file using Object Factory
 * Component.
 * </p>
 *
 * <p>
 * <strong>Configuration:</strong> All ReportDaos are stored in an internal map which maps the
 * types of the DAOs to their DAO objects. DAOs are defined as sub-properties of property
 * <b>"reportDaos"</b>. An example is described below:
 * </p>
 *
 * <p>
 * &lt;Property name="reportDaos"><br>
 *      &lt;Property name="type1"><br>
 *          &lt;Value>InformixReportDAOKey&lt;/Value><br>
 *      &lt;/Property><br>
 *      &lt;Property name="type2"><br>
 *           &lt;Value>OtherReportDAOKey2&lt;/Value><br>
 *      &lt;/Property><br>
 * &lt;/Property><br>
 * </p>
 *
 * <p>
 * The above configuration defines two DAOs with type <code>"type1"</code> and <code>"type2"</code>.
 * <code>InformixReportDAOKey</code> and <code>OtherReportDAOKey2</code> are keys for Object Factory to
 * create the actual DAO objects. The namespace of Object Factory is defined in property
 * <code>"objectFactoryNameSpace"</code>:
 * </p>
 *
 * <p>
 * &lt;Property name="objectFactoryNameSpace"><br>
 *      &lt;Value>AllDao_ObjectFactoryNS&lt;/Value><br>
 * &lt;/Property><br>
 * </p>
 *
 * <p>
 * <code>InformixReportDAOKey</code> and <code>OtherReportDAOKey2</code> are defined in namespace
 * <code>AllDao_ObjectFactoryNS</code>.
 * </p>
 *
 * <p>
 * There is one more optional property for ReportDAOFactory: <code>"informixReportDaoNamespace"</code>.
 * This property tells ReportDAOFactory to create an InformixReportDAO using the provided namespace. If it
 * is missing, default namespace will be used. This means that ReportDAOFactory always creates an object of
 * InformixReportDAO besides all the DAOs defined by <code>"reportDaos"</code>. <code>"informixReportDaoNamespace"
 * </code> and <code>"reportDaos"</code> are <i>optional</i>, but <code>"objectFactoryNameSpace"</code> is
 * <i>required</i>.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> this class is thread safe because doesn't have public methods
 * for change its variables.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public class ReportDAOFactory {

    /**
     * Represents the default namespace when the void constructor is called.
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.timetracker.report.ReportDaoFactory";

    /**
     * Represents the type of InformixReportDAO.
     */
    public static final String INFORMIX = "informix";

    /**
     * Represents the property for namespace for Object Factory Component.
     */
    private static final String OBJECT_FACTORY_NAMESPACE_PROPERTY = "objectFactoryNameSpace";

    /**
     * Represents the property that contains the sub-properties for instantiate the ReportDAOs using
     * Object Factory.
     */
    private static final String REPORT_DAOS_PROPERTY = "reportDaos";

    /**
     * Represents the property for read the namespace of InformixReportDAO.
     */
    private static final String INFORMIX_REPORT_DAO_NAMESPACE_PROPERTY = "informixReportDaoNamespace";

    /**
     * Represents the map of type ReportDAO. The keys are String that define the values that are
     * ReportDAO implementations. An example of type is &quot;informix&quot; (the INFORMIX
     * constant). It is initialized in constructor and doesn't change. It cannot be null and can't be
     * void because at minimum it contains InformixReportDAO.
     */
    private final Map reportDAOs;

    /**
     * <p>
     * Constructs a new <code>ReportDAOFactory</code> and load the reportDaos map using the
     * default namespace.
     * </p>
     *
     * @throws ReportConfigException if some errors occur when config file is read.
     */
    public ReportDAOFactory() throws ReportConfigException {
        this.reportDAOs = this.createReportDAOMap(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Constructs a new <code>ReportDAOFactory</code> and load the reportDaos map using the given
     * namespace.
     * </p>
     *
     * @param namespace the namespace for load the configuration.
     *
     * @throws IllegalArgumentException if <code>namespace</code> is null or empty.
     * @throws ReportConfigException if some errors occur when config file is read.
     */
    public ReportDAOFactory(String namespace) throws ReportConfigException {
        Helper.checkString(namespace, "namespace");
        this.reportDAOs = this.createReportDAOMap(namespace);
    }

    /**
     * <p>
     * Returns the ReportDAO from the type. If the map does not contain any DAO class associated to
     * <code>type</code>, null will be returned.
     * </p>
     *
     * @param type the type of ReportDAO.
     *
     * @return the ReportDAO from the type.
     *
     * @throws IllegalArgumentException if <code>type</code> is null, or empty string.
     */
    public ReportDAO getReportDAO(String type) {
        Helper.checkString(type, "type");
        return (ReportDAO) this.reportDAOs.get(type);
    }

    /**
     * <p>
     * Constructs a map of ReportDAOs from the configuration file using the given namespace.
     * <code>ConfigManager</code> is used to retrieve the necessary information from the
     * configuration file. Please consult the config guide-lines for more information about the
     * configuration.
     * </p>
     *
     * <p>
     * This method will be called by the constructors. The map will always contain at least 1
     * key-value pair with the value of type InformixReportDAO.
     * </p>
     *
     * @param namespace the namespace for load the configuration. <code>namespace</code> is never
     *        null nor empty string.
     *
     * @return map of ReportDaos.
     *
     * @throws ReportConfigException if some errors occur when config file is read.
     */
    private Map createReportDAOMap(String namespace) throws ReportConfigException {
        ObjectFactory objFactory = Helper.getObjectFactory(namespace, OBJECT_FACTORY_NAMESPACE_PROPERTY);

        Map daoMap = new HashMap();

        try {
            Property reportDAOsProperty =
                    ConfigManager.getInstance().getPropertyObject(namespace, REPORT_DAOS_PROPERTY);
            if (reportDAOsProperty != null) {
                Enumeration enumProperty = reportDAOsProperty.propertyNames();
                while (enumProperty.hasMoreElements()) {
                    String propertyName = (String) enumProperty.nextElement(); // this is the type
                    String key = (String) reportDAOsProperty.getProperty(propertyName).getValue().trim();

                    Object obj = objFactory.createObject(key);
                    if (!(obj instanceof ReportDAO)) {
                        throw new ReportConfigException("The object created using key "
                                + key
                                + " is not a valid instance of type ReportDAO");
                    }

                    daoMap.put(propertyName, (ReportDAO) obj);
                }
            }
        } catch (UnknownNamespaceException e) {
            throw new ReportConfigException("An error occurred while creating the map.", e);
        } catch (InvalidClassSpecificationException e) {
            throw new ReportConfigException("An error occurred while creating the map.", e);
        }

        // get InformixDao
        String informixReportDaoNS =
                Helper.getPropertyValue(namespace, INFORMIX_REPORT_DAO_NAMESPACE_PROPERTY, false);
        InformixReportDAO informixReportDao =
                (informixReportDaoNS == null) ? new InformixReportDAO() : new InformixReportDAO(
                        informixReportDaoNS);
        daoMap.put(INFORMIX, informixReportDao);

        return daoMap;
    }
}
