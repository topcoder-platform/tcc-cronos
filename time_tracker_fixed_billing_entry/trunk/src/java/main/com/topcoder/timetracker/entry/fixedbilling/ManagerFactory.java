/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;


/**
 * <p>
 * This is a class that acts as a factory for the managers, and may be used to easily access the manager class for
 * various purposes. It uses lazy instantiation to create the managers. Instantiation is done through the Object
 * Factory.
 * </p>
 *
 * <p>
 * &lt;?xml version=&quot;1.0&quot;?&gt;<br>
 * &lt;CMConfig&gt;<br>
 * &lt;Config name=&quot;com.topcoder.timetracker.entry.fixedbilling&quot;&gt;<br>
 * &lt;Property name=&quot;FixedBillingEntryManager&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManagerImpl&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;params&quot;&gt;<br>
 * &lt;Property name=&quot;param1&quot;&gt;<br>
 * &lt;Property name=&quot;name&quot;&gt;<br>
 * &lt;Value&gt;com/topcoder/timetracker/entry/fixedbilling/FixedBillingEntryDAO&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;param2&quot;&gt;<br>
 * &lt;Property name=&quot;name&quot;&gt;<br>
 * &lt;Value&gt;com/topcoder/timetracker/entry/fixedbilling/FixedBillingEntryRejectReasonDAO&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;param3&quot;&gt;<br>
 * &lt;Property name=&quot;name&quot;&gt;<br>
 * &lt;Value&gt;com/topcoder/timetracker/rejectreason/ejb/RejectReasonManager&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;FixedBillingStatusManager&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusManagerImpl&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;params&quot;&gt;<br>
 * &lt;Property name=&quot;param1&quot;&gt;<br>
 * &lt;Property name=&quot;name&quot;&gt;<br>
 * &lt;Value&gt;com/topcoder/timetracker/entry/fixedbilling/FixedBillingStatusDAO&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- Object Factory definition for FixedBillingEntryDAO --&gt;<br>
 * &lt;Property name=&quot;com/topcoder/timetracker/entry/fixedbilling/FixedBillingEntryDAO&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryDAO&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;params&quot;&gt;<br>
 * &lt;!-- ConnectionFactory parameter for the DAO. --&gt;<br>
 * &lt;Property name=&quot;param1&quot;&gt;<br>
 * &lt;Property name=&quot;name&quot;&gt;<br>
 * &lt;Value&gt;com/topcoder/db/connectionfactory/DBConnectionFactoryImpl/FixBillEntry&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- Connection name parameter for the DAO (we use null). --&gt;<br>
 * &lt;Property name=&quot;param2&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;String&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;value&quot;&gt;<br>
 * &lt;Value&gt;Informix_Connection_Producer&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- Id Generator parameter for the DAO. --&gt;<br>
 * &lt;Property name=&quot;param3&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;String&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;value&quot;&gt;<br>
 * &lt;Value&gt;com.topcoder.timetracker.entry.fixedbilling&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- Search Strategy Namespace parameter for the DAO. --&gt;<br>
 * &lt;Property name=&quot;param4&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;String&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;value&quot;&gt;<br>
 * &lt;Value&gt;DBSearchStrategy&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- AuditManager parameter for the DAO. --&gt;<br>
 * &lt;Property name=&quot;param5&quot;&gt;<br>
 * &lt;Property name=&quot;name&quot;&gt;<br>
 * &lt;Value&gt;com/topcoder/timetracker/audit/MockAuditManager&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- FixedBillingStatusDAO parameter for the DAO. --&gt;<br>
 * &lt;Property name=&quot;param6&quot;&gt;<br>
 * &lt;Property name=&quot;name&quot;&gt;<br>
 * &lt;Value&gt;com/topcoder/timetracker/entry/fixedbilling/FixedBillingStatusDAO&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;com/topcoder/db/connectionfactory/DBConnectionFactoryImpl/FixBillEntry&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;params&quot;&gt;<br>
 * &lt;!-- ConnectionFactory parameter for the DAO. --&gt;<br>
 * &lt;Property name=&quot;param1&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;String&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;value&quot;&gt;<br>
 * &lt;Value&gt;com.topcoder.db.connectionfactory&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;com/topcoder/timetracker/rejectreason/ejb/RejectReasonManager&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;params&quot;&gt;<br>
 * &lt;!-- ConnectionFactory parameter for the DAO. --&gt;<br>
 * &lt;Property name=&quot;param1&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;String&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;value&quot;&gt;<br>
 * &lt;Value&gt;com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- Object Factory definition for FixedBillingStatusDAO --&gt;<br>
 * &lt;Property name=&quot;com/topcoder/timetracker/entry/fixedbilling/FixedBillingStatusDAO&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingStatusDAO&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;params&quot;&gt;<br>
 * &lt;!-- ConnectionFactory parameter for the DAO. --&gt;<br>
 * &lt;Property name=&quot;param1&quot;&gt;<br>
 * &lt;Property name=&quot;name&quot;&gt;<br>
 * &lt;Value&gt;com/topcoder/db/connectionfactory/DBConnectionFactoryImpl/FixBillEntry&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- Connection name parameter for the DAO (we use null). --&gt;<br>
 * &lt;Property name=&quot;param2&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;String&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;value&quot;&gt;<br>
 * &lt;Value&gt;Informix_Connection_Producer&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- Id Generator parameter for the DAO. --&gt;<br>
 * &lt;Property name=&quot;param3&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;String&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;value&quot;&gt;<br>
 * &lt;Value&gt;com.topcoder.timetracker.entry.fixedbilling&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- Search Strategy Namespace parameter for the DAO. --&gt;<br>
 * &lt;Property name=&quot;param4&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;String&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;value&quot;&gt;<br>
 * &lt;Value&gt;DBSearchStrategy&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- Object Factory definition for FixedBillingEntryRejectReasonDAO --&gt;<br>
 * &lt;Property name=&quot;com/topcoder/timetracker/entry/fixedbilling/FixedBillingEntryRejectReasonDAO&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryRejectReasonDAO&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;params&quot;&gt;<br>
 * &lt;!-- ConnectionFactory parameter for the DAO. --&gt;<br>
 * &lt;Property name=&quot;param1&quot;&gt;<br>
 * &lt;Property name=&quot;name&quot;&gt;<br>
 * &lt;Value&gt;com/topcoder/db/connectionfactory/DBConnectionFactoryImpl/FixBillEntry&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- Connection name parameter for the DAO (we use null). --&gt;<br>
 * &lt;Property name=&quot;param2&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;String&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;value&quot;&gt;<br>
 * &lt;Value&gt;Informix_Connection_Producer&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;param3&quot;&gt;<br>
 * &lt;Property name=&quot;name&quot;&gt;<br>
 * &lt;Value&gt;com/topcoder/timetracker/audit/MockAuditManager&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;com/topcoder/timetracker/audit/MockAuditManager&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;com.topcoder.timetracker.audit.MockAuditManager&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;params&quot;&gt;<br>
 * &lt;Property name=&quot;param1&quot;&gt;<br>
 * &lt;Property name=&quot;type&quot;&gt;<br>
 * &lt;Value&gt;boolean&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;value&quot;&gt;<br>
 * &lt;Value&gt;false&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Config&gt;<br>
 * &lt;/CMConfig&gt;<br>
 * </p>
 *
 * <p>
 * Thread Safety: The methods should be synchronized to ensure only a single instance of each manager is instantiated.
 * The managers themselves are thread-safe since it is required by the manager interfaces.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class ManagerFactory {
    /**
     * <p>
     * This is the namespace to use when creating a ConfigManagerSpecificationFactory. It is also the namespace that
     * should be used when writing a configuration file for the managers.
     * </p>
     */
    public static final String NAMESPACE = "com.topcoder.timetracker.entry.fixedbilling";

    /**
     * <p>
     * This is the FixedBillingEntryManager  that will be used.  It is null until it has been accessed.  It will be
     * initialized by a call to ObjectFactory.createObject.
     * </p>
     */
    private static FixedBillingEntryManager fixedBillingEntryManager;

    /**
     * <p>
     * This is the FixedBillingStatusManager  that will be used.  It is null until it has been accessed.  It will be
     * initialized by a call to ObjectFactory.createObject.
     * </p>
     */
    private static FixedBillingStatusManager fixedBillingStatusManager;

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private ManagerFactory() {
    }

    /**
     * <p>
     * Retrieves an instance of FixedBillingEntryManager.  If it has not yet been initialized, then an instance will be
     * created.
     * </p>
     *
     * <p>
     * Thread Safety: Synchronization should be used to ensure only a single instance is created.
     * </p>
     *
     * @return An instance of FixedBillingEntryManager to use.
     *
     * @throws ConfigurationException if any error occurs.
     */
    public static synchronized FixedBillingEntryManager getFixedBillingEntryManager()
        throws ConfigurationException {
        if (fixedBillingEntryManager == null) {
            try {
                ObjectFactory objectfactory = new ObjectFactory(new ConfigManagerSpecificationFactory(NAMESPACE),
                        ObjectFactory.BOTH);

                fixedBillingEntryManager = (FixedBillingEntryManager) objectfactory.createObject(
                        "FixedBillingEntryManager");
            } catch (IllegalReferenceException ire) {
                throw new ConfigurationException("Unable to create the instance.", ire);
            } catch (InvalidClassSpecificationException icse) {
                throw new ConfigurationException("Unable to create the instance.", icse);
            } catch (SpecificationConfigurationException sce) {
                throw new ConfigurationException("Unable to create the instance.", sce);
            } catch (ClassCastException cce) {
                throw new ConfigurationException("Unable to create the instance.", cce);
            }
        }

        return fixedBillingEntryManager;
    }

    /**
     * <p>
     * Retrieves an instance of FixedBillingStatusManager.  If it has not yet been initialized, then an instance will
     * be created.
     * </p>
     *
     * <p>
     * Thread Safety: Synchronization should be used to ensure only a single instance is created.
     * </p>
     *
     * @return An instance of FixedBillingStatusManager to use.
     *
     * @throws ConfigurationException if any error occurs.
     */
    public static synchronized FixedBillingStatusManager getFixedBililngStatusManager()
        throws ConfigurationException {
        if (fixedBillingStatusManager == null) {
            try {
                ObjectFactory objectfactory = new ObjectFactory(new ConfigManagerSpecificationFactory(NAMESPACE),
                        ObjectFactory.BOTH);

                fixedBillingStatusManager = (FixedBillingStatusManager) objectfactory.createObject(
                        "FixedBillingStatusManager");
            } catch (IllegalReferenceException ire) {
                throw new ConfigurationException("Unable to create the instance.", ire);
            } catch (InvalidClassSpecificationException icse) {
                throw new ConfigurationException("Unable to create the instance.", icse);
            } catch (SpecificationConfigurationException sce) {
                throw new ConfigurationException("Unable to create the instance.", sce);
            } catch (ClassCastException cce) {
                throw new ConfigurationException("Unable to create the instance.", cce);
            }
        }

        return fixedBillingStatusManager;
    }
}
