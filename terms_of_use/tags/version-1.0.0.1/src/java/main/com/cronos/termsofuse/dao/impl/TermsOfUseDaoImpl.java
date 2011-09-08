/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.termsofuse.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.cronos.termsofuse.dao.EntityNotFoundException;
import com.cronos.termsofuse.dao.TermsOfUseDao;
import com.cronos.termsofuse.dao.TermsOfUseDaoConfigurationException;
import com.cronos.termsofuse.dao.TermsOfUsePersistenceException;
import com.cronos.termsofuse.model.TermsOfUse;
import com.cronos.termsofuse.model.TermsOfUseType;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is the default implementation of TermsOfUseDao. It utilizes the DB Connection Factory to get access to
 * the database. The configuration is done by the Configuration API.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;CMConfig&gt;
 *     &lt;Config name="termsOfUseDao"&gt;
 *      &lt;Property name="dbConnectionFactoryConfig"&gt;
 *        &lt;Property name="com.topcoder.db.connectionfactory.DBConnectionFactoryImpl"&gt;
 *          &lt;Property name="connections"&gt;
 *                 &lt;Property name="default"&gt;
 *                     &lt;Value&gt;InformixJDBCConnection&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *              &lt;Property name="InformixJDBCConnection"&gt;
 *                  &lt;Property name="producer"&gt;
 *                      &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *                  &lt;/Property&gt;
 *                  &lt;Property name="parameters"&gt;
 *                      &lt;Property name="jdbc_driver"&gt;
 *                      &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name="jdbc_url"&gt;
 *                              &lt;Value&gt;
 *                                  jdbc:informix-sqli://localhost:1526/common_oltp:informixserver=ol_topcoder
 *                              &lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name="user"&gt;
 *                          &lt;Value&gt;informix&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name="password"&gt;
 *                          &lt;Value&gt;123456&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                  &lt;/Property&gt;
 *              &lt;/Property&gt;
 *          &lt;/Property&gt;
 *        &lt;/Property&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="loggerName"&gt;
 *          &lt;Value&gt;loggerName&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="idGeneratorName"&gt;
 *          &lt;Value&gt;idGenerator&lt;/Value&gt;
 *      &lt;/Property&gt;
 *     &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Create the configuration object
 * ConfigurationObject configurationObject = TestsHelper.getConfig(TestsHelper.CONFIG_TERMS);
 * // Instantiate the dao implementation from configuration defined above
 * TermsOfUseDao termsOfUseDao = new TermsOfUseDaoImpl(configurationObject);
 *
 * // Create simple TermsOfUse to persist
 * TermsOfUse terms = new TermsOfUse();
 *
 * terms.setTermsOfUseTypeId(3);
 * terms.setTitle(&quot;t5&quot;);
 * terms.setElectronicallySignable(true);
 * terms.setUrl(&quot;url5&quot;);
 * terms.setMemberAgreeable(false);
 *
 * // Persist the TermsOfUse
 * terms = termsOfUseDao.createTermsOfUse(terms, &quot;&quot;);
 *
 * // Set terms of use text
 * termsOfUseDao.setTermsOfUseText(terms.getTermsOfUseId(), &quot;text5&quot;);
 *
 * // Get terms of use text. This will return &quot;text5&quot;.
 * String termsOfUseText = termsOfUseDao.getTermsOfUseText(terms.getTermsOfUseId());
 *
 * // Update some information for TermsOfUse
 * terms.setMemberAgreeable(true);
 *
 * // And update the TermsOfUse
 * terms = termsOfUseDao.updateTermsOfUse(terms);
 *
 * // Retrieve some terms of use. The third row will be returned
 * terms = termsOfUseDao.getTermsOfUse(3);
 *
 * // Delete terms of use
 * termsOfUseDao.deleteTermsOfUse(5);
 *
 * // Retrieve all terms of use. All rows will be returned
 * List&lt;TermsOfUse&gt; allTerms = termsOfUseDao.getAllTermsOfUse();
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The class is immutable and thread safe.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public class TermsOfUseDaoImpl extends BaseTermsOfUseDao implements TermsOfUseDao {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = TermsOfUseDaoImpl.class.getName();

    /**
     * <p>
     * Represents the property key 'idGeneratorName'.
     * </p>
     */
    private static final String KEY_ID_GENERATOR_NAME = "idGeneratorName";

    /**
     * <p>
     * Represents the SQL string to insert a terms of use entity.
     * </p>
     */
    private static final String INSERT_TERMS = "INSERT INTO terms_of_use (terms_of_use_id, terms_text,"
        + " terms_of_use_type_id, title, electronically_signable, url, member_agreeable) VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL string to update the terms of use entity.
     * </p>
     */
    private static final String UPDATE_TERMS = "UPDATE terms_of_use SET terms_of_use_type_id = ?, title = ?,"
        + " electronically_signable = ?, url = ?, member_agreeable=? WHERE terms_of_use_id = ?";


    /**
     * <p>
     * Represents the SQL string to query a terms of use entity.
     * </p>
     */
    private static final String QUERY_TERMS = "SELECT terms_of_use_type_id, title, electronically_signable, url,"
        + " member_agreeable FROM terms_of_use WHERE terms_of_use_id=?";

    /**
     * <p>
     * Represents the SQL string to delete a terms of use entity.
     * </p>
     */
    private static final String DELETE_TERMS = "DELETE FROM terms_of_use WHERE terms_of_use_id=?";


    /**
     * <p>
     * Represents the SQL string to query terms of use entities by the terms of use type id.
     * </p>
     */
    private static final String QUERY_TERMS_BY_TYPE_ID = "SELECT terms_of_use_id, title, electronically_signable,"
        + " url, member_agreeable FROM terms_of_use WHERE terms_of_use_type_id=?";


    /**
     * <p>
     * Represents the SQL string to query all terms of use entities.
     * </p>
     */
    private static final String QUERY_ALL_TERMS = "SELECT terms_of_use_id, terms_of_use_type_id, title,"
        + " electronically_signable, url, member_agreeable FROM terms_of_use";

    /**
     * <p>
     * Represents the SQL string to query the terms of use type.
     * </p>
     */
    private static final String QUERY_TERMS_TYPE = "SELECT terms_of_use_type_desc FROM terms_of_use_type"
        + " WHERE terms_of_use_type_id=?";

    /**
     * <p>
     * Represents the SQL string to update the terms of use type.
     * </p>
     */
    private static final String UPDATE_TERMS_TYPE = "UPDATE terms_of_use_type SET terms_of_use_type_desc = ?"
        + " WHERE terms_of_use_type_id = ?";


    /**
     * <p>
     * Represents the SQL string to query the terms text.
     * </p>
     */
    private static final String QUERY_TERMS_TEXT = "SELECT terms_text FROM terms_of_use WHERE terms_of_use_id=?";


    /**
     * <p>
     * Represents the SQL string to update the terms text.
     * </p>
     */
    private static final String UPDATE_TERMS_TEXT = "UPDATE terms_of_use SET terms_text=? WHERE terms_of_use_id=?";

    /**
     * <p>
     * The id generator, used to generate ids for the entities.
     * </p>
     *
     * <p>
     * It is initialized in the constructor and never changed afterwards. It is used in the dao operations whenever
     * generation of id is necessary. It can not be null.
     * </p>
     */
    private final IDGenerator idGenerator;

    /**
     * This is the constructor with the ConfigurationObject parameter, which simply delegates the instance
     * initialization to the base class and additionally initializes id generator.
     *
     * @param configurationObject
     *            the configuration object containing the configuration.
     *
     * @throws IllegalArgumentException
     *             if the configurationObject is null.
     * @throws TermsOfUseDaoConfigurationException
     *             if any exception occurs while initializing the instance.
     */
    public TermsOfUseDaoImpl(ConfigurationObject configurationObject) {
        super(configurationObject);

        try {
            idGenerator = IDGeneratorFactory.getIDGenerator(Helper.getRequiredProperty(configurationObject,
                KEY_ID_GENERATOR_NAME));
        } catch (IDGenerationException e) {
            throw new TermsOfUseDaoConfigurationException("Failed to retrieve an id generator.", e);
        }
    }

    /**
     * Creates terms of use entity with the terms text.
     *
     * @param termsOfUse
     *            a TermsOfUse containing required information for creation.
     * @param termsText
     *            the terms text to create.
     *
     * @return a TermsOfUse with created id attribute.
     *
     * @throws IllegalArgumentException
     *             if termsOfUse is null.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUse createTermsOfUse(TermsOfUse termsOfUse, String termsText) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".createTermsOfUse(TermsOfUse termsOfUse, String termsText)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUse", "termsText"},
            new Object[] {termsOfUse, termsText});

        try {
            Helper.checkNull(termsOfUse, "termsOfUse");

            long termsOfUseId;
            try {
                termsOfUseId = idGenerator.getNextID();
            } catch (IDGenerationException e) {
                throw new TermsOfUsePersistenceException("Failed to generate a new ID.", e);
            }
            termsOfUse.setTermsOfUseId(termsOfUseId);

            Helper.executeUpdate(getDBConnectionFactory(), INSERT_TERMS,
                new Object[] {termsOfUseId, (termsText == null) ? null : termsText.getBytes(),
                    termsOfUse.getTermsOfUseTypeId(), termsOfUse.getTitle(), termsOfUse.isElectronicallySignable(),
                    termsOfUse.getUrl(), termsOfUse.isMemberAgreeable()});

            // Log method exit
            Helper.logExit(log, signature, new Object[] {termsOfUse});
            return termsOfUse;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Updates terms of use entity.
     *
     * @param termsOfUse
     *            a TermsOfUse containing required information for update.
     *
     * @return a TermsOfUse with updated id attribute.
     *
     * @throws IllegalArgumentException
     *             if termsOfUse is null.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUse updateTermsOfUse(TermsOfUse termsOfUse) throws TermsOfUsePersistenceException,
        EntityNotFoundException {
        String signature = CLASS_NAME + ".updateTermsOfUse(TermsOfUse termsOfUse)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUse"},
            new Object[] {termsOfUse});

        try {
            Helper.checkNull(termsOfUse, "termsOfUse");

            Helper.executeUpdate(getDBConnectionFactory(), UPDATE_TERMS,
                new Object[] {termsOfUse.getTermsOfUseTypeId(), termsOfUse.getTitle(),
                    termsOfUse.isElectronicallySignable(), termsOfUse.getUrl(), termsOfUse.isMemberAgreeable(),
                    termsOfUse.getTermsOfUseId()},
                Long.toString(termsOfUse.getTermsOfUseId()));

            // Log method exit
            Helper.logExit(log, signature, new Object[] {termsOfUse});
            return termsOfUse;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Retrieves a terms of use entity from the database.
     *
     * @param termsOfUseId
     *            a long containing the terms of use id to retrieve.
     *
     * @return a TermsOfUse with the requested terms of use or null if not found.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUse getTermsOfUse(long termsOfUseId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getTermsOfUse(long termsOfUseId)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseId"},
            new Object[] {termsOfUseId});

        try {
            Connection conn = Helper.createConnection(getDBConnectionFactory());
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement(QUERY_TERMS);
                ps.setLong(1, termsOfUseId);

                ResultSet rs = ps.executeQuery();
                TermsOfUse terms = null;
                if (rs.next()) {
                    terms = Helper.getTermsOfUse(rs, termsOfUseId, null);
                }

                // Log method exit
                Helper.logExit(log, signature, new Object[] {terms});
                return terms;
            } catch (SQLException e) {
                throw new TermsOfUsePersistenceException("A database access error has occurred.", e);
            } finally {
                // Close the prepared statement
                // (The result set will be closed automatically)
                Helper.closeStatement(ps);
                // Close the connection
                Helper.closeConnection(conn);
            }
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Deletes a terms of use entity from the database.
     *
     * @param termsOfUseId
     *            a long containing the terms of use id to delete.
     *
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public void deleteTermsOfUse(long termsOfUseId) throws EntityNotFoundException, TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".deleteTermsOfUse(long termsOfUseId)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseId"},
            new Object[] {termsOfUseId});

        try {
            Helper.executeUpdate(getDBConnectionFactory(), DELETE_TERMS,
                new Object[] {termsOfUseId},
                Long.toString(termsOfUseId));

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Retrieves a terms of use entities by the terms of use type id from the database.
     *
     * @param termsOfUseTypeId
     *            an int containing the terms of use type id to retrieve.
     *
     * @return a list of TermsOfUse entities with the requested terms of use or empty list if not found.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public List<TermsOfUse> getTermsOfUseByTypeId(int termsOfUseTypeId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getTermsOfUseByTypeId(int termsOfUseTypeId)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseTypeId"},
            new Object[] {termsOfUseTypeId});

        // Delegate to Helper.getTermsOfUse
        return Helper.getTermsOfUse(signature, log, getDBConnectionFactory(), QUERY_TERMS_BY_TYPE_ID, null,
            termsOfUseTypeId);
    }

    /**
     * Retrieves all terms of use entities from the database.
     *
     * @return a list of all TermsOfUse entities.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public List<TermsOfUse> getAllTermsOfUse() throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getAllTermsOfUse()";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature, null, null);

        // Delegate to Helper.getTermsOfUse
        return Helper.getTermsOfUse(signature, log, getDBConnectionFactory(), QUERY_ALL_TERMS, null, null);
    }

    /**
     * Gets terms of use type by id.
     *
     * @param termsOfUseTypeId
     *            terms of use type id.
     *
     * @return terms of use type.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUseType getTermsOfUseType(int termsOfUseTypeId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getTermsOfUseType(int termsOfUseTypeId)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseTypeId"},
            new Object[] {termsOfUseTypeId});

        try {
            Connection conn = Helper.createConnection(getDBConnectionFactory());
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement(QUERY_TERMS_TYPE);
                ps.setLong(1, termsOfUseTypeId);

                ResultSet rs = ps.executeQuery();
                TermsOfUseType termsType = null;
                if (rs.next()) {
                    termsType = new TermsOfUseType();
                    termsType.setTermsOfUseTypeId(termsOfUseTypeId);
                    termsType.setDescription(rs.getString("terms_of_use_type_desc"));
                }

                // Log method exit
                Helper.logExit(log, signature, new Object[] {termsType});
                return termsType;
            } catch (SQLException e) {
                throw new TermsOfUsePersistenceException("A database access error has occurred.", e);
            } finally {
                // Close the prepared statement
                // (The result set will be closed automatically)
                Helper.closeStatement(ps);
                // Close the connection
                Helper.closeConnection(conn);
            }
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Update terms of use type.
     *
     * @param termsType
     *            the terms of use type to be updated.
     *
     * @return updated terms of use type.
     *
     * @throws IllegalArgumentException
     *             if termsType is null.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUseType updateTermsOfUseType(TermsOfUseType termsType) throws TermsOfUsePersistenceException,
        EntityNotFoundException {
        String signature = CLASS_NAME + ".updateTermsOfUseType(TermsOfUseType termsType)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsType"},
            new Object[] {termsType});

        try {
            Helper.checkNull(termsType, "termsType");

            Helper.executeUpdate(getDBConnectionFactory(), UPDATE_TERMS_TYPE,
                new Object[] {termsType.getDescription(), termsType.getTermsOfUseTypeId()},
                Long.toString(termsType.getTermsOfUseTypeId()));

            // Log method exit
            Helper.logExit(log, signature, new Object[] {termsType});
            return termsType;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Gets terms of use text by terms of use id.
     *
     * @param termsOfUseId
     *            terms of use id.
     *
     * @return text of terms of use.
     *
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public String getTermsOfUseText(long termsOfUseId) throws TermsOfUsePersistenceException, EntityNotFoundException {
        String signature = CLASS_NAME + ".getTermsOfUseText(long termsOfUseId)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseId"},
            new Object[] {termsOfUseId});

        try {
            Connection conn = Helper.createConnection(getDBConnectionFactory());
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement(QUERY_TERMS_TEXT);
                ps.setLong(1, termsOfUseId);

                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new EntityNotFoundException("The entity was not found for id  (" + termsOfUseId + ").");
                }

                byte[] termsTextBytes = rs.getBytes(1);
                String termsText = (termsTextBytes == null) ? null : new String(termsTextBytes);

                // Log method exit
                Helper.logExit(log, signature, new Object[] {termsText});
                return termsText;
            } catch (SQLException e) {
                throw new TermsOfUsePersistenceException("A database access error has occurred.", e);
            } finally {
                // Close the prepared statement
                // (The result set will be closed automatically)
                Helper.closeStatement(ps);
                // Close the connection
                Helper.closeConnection(conn);
            }
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Sets terms of use text.
     *
     * @param termsOfUseId
     *            terms of use id.
     * @param text
     *            text of terms of use.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     */
    public void setTermsOfUseText(long termsOfUseId, String text) throws TermsOfUsePersistenceException,
        EntityNotFoundException {
        String signature = CLASS_NAME + ".setTermsOfUseText(long termsOfUseId, String text)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseId", "text"},
            new Object[] {termsOfUseId, text});

        try {
            Helper.executeUpdate(getDBConnectionFactory(), UPDATE_TERMS_TEXT,
                new Object[] {(text == null) ? null : text.getBytes(), termsOfUseId},
                Long.toString(termsOfUseId));

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }
}
