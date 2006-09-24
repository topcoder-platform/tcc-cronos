/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import com.cronos.onlinereview.autoscreening.tool.DAOException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningResponse;
import com.cronos.onlinereview.autoscreening.tool.ScreeningResponseDAO;
import com.cronos.onlinereview.autoscreening.tool.failuretests.DatabaseUtils.DataType;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;

/**
 * <p>
 * This implementation of the ResponseTaskDAO provides an Informix database as
 * the backing datastore.
 * </p>
 * <p>
 * Thread-Safety: Each invocation works on its own connection instance, and the
 * connection is closed after. Unlike InformixTaskDAO, the threading issues here
 * are less, because this is only involved in creating new instances.
 * </p>
 * 
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class MockResponseDAO implements ScreeningResponseDAO {

	/**
	 * Represents the sql statement to create screening response onto the
	 * database.
	 */
	private static final String CREATE_SCREENING_RESPONSE_SQL = "INSERT INTO screening_result "
			+ "(screening_result_id, screening_task_id, screening_response_id, dynamic_response_text, "
			+ "create_user, create_date, modify_user, modify_date) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	/**
	 * Represents the argument types for the sql statement to create screening
	 * response.
	 */
	private static final DataType[] CREATE_SCREENING_RESPONSE_ARGUMENT_TYPES = new DataType[] {
			DatabaseUtils.LONG_TYPE, DatabaseUtils.LONG_TYPE,
			DatabaseUtils.LONG_TYPE, DatabaseUtils.STRING_TYPE,
			DatabaseUtils.STRING_TYPE, DatabaseUtils.DATE_TYPE,
			DatabaseUtils.STRING_TYPE, DatabaseUtils.DATE_TYPE };

	/**
	 * <p>
	 * The connection factory on which to retrieve Connections from when
	 * accessing the datastore.
	 * </p>
	 */
	private final DBConnectionFactory connectionFactory;

	/**
	 * <p>
	 * The connection name to use when retrieving connections from the
	 * connection factory. If not present, then the default connection is used.
	 * </p>
	 */
	private final String connectionName;

	/**
	 * <p>
	 * This is the id generator that is used to provide ids to the created
	 * ScreeningResponses.
	 * </p>
	 */
	private final IDGenerator idGenerator;

	/**
	 * Constructor that allows for instantiation using the specified connection
	 * factory. The default connection of that factory is used.
	 * 
	 * @param connectionFactory
	 *            the connection factory to retrieve DB connections from.
	 * @param idGenerator
	 *            The id generator to use.
	 * @throws IllegalArgumentException
	 *             if the connectionFactory or idGenerator is null.
	 */
	public MockResponseDAO(DBConnectionFactory connectionFactory,
			IDGenerator idGenerator) {
		this(connectionFactory, null, idGenerator);
	}

	/**
	 * Constructor that allows for instantiation using the specified connection
	 * factory. The specified connection name is used.
	 * 
	 * @param connectionFactory
	 *            the connection factory to retrieve DB connections from.
	 * @param connectionName
	 *            the name of the connection to retrieve from the connection
	 *            factory.
	 * @param idGenerator
	 *            The id generator to use.
	 * @throws IllegalArgumentException
	 *             if the connectionFactory or idGenerator is null or if
	 *             connectionName is an empty String.
	 */
	public MockResponseDAO(DBConnectionFactory connectionFactory,
			String connectionName, IDGenerator idGenerator) {
		if (connectionFactory == null) {
			throw new IllegalArgumentException(
					"connectionFactory should not be null.");
		}
		if (connectionName != null && connectionName.trim().length() == 0) {
			throw new IllegalArgumentException(
					"connectionName should not be empty (trimmed).");
		}
		if (idGenerator == null) {
			throw new IllegalArgumentException(
					"idGenerator should not be null.");
		}

		this.connectionFactory = connectionFactory;
		this.connectionName = connectionName;
		this.idGenerator = idGenerator;
	}

	/**
	 * <p>
	 * Records a screening response within the data store.
	 * </p>
	 * <p>
	 * If the id of screeningResponse is not set (i.e. Long.MIN_VALUE), it will
	 * be set using the id generator; otherwise, the id that screeningResponse
	 * already has will be used.
	 * </p>
	 * 
	 * @param screeningResponse
	 *            the screening response to create within the data store.
	 * @throws IllegalArgumentException
	 *             if the screeningResponse is null.
	 * @throws DAOException
	 *             if a problem occurs while accessing the datastore.
	 */
	public void createScreeningResponse(ScreeningResponse screeningResponse)
			throws DAOException {
		if (screeningResponse == null) {
			throw new IllegalArgumentException(
					"screeningResponse should not be null.");
		}

		// get the new Id for the screening response if the old one is not set
		long id = screeningResponse.getId() == Long.MIN_VALUE ? getNextID()
				: screeningResponse.getId();

		// build the arguments for the insert statement
		// NOTE: wrapLongValue are used blindly except for id
		Object[] queryArgs = new Object[] {
				// response id
				new Long(id),
				// task id
				DatabaseUtils.wrapLongValue(screeningResponse
						.getScreeningTaskId()),
				// response code id
				DatabaseUtils.wrapLongValue(screeningResponse
						.getResponseCodeId()),
				// detail message
				screeningResponse.getDetailMessage(),
				// create user, create date, modify user, modify date
				screeningResponse.getCreateUser(),
				screeningResponse.getCreateDate(),
				screeningResponse.getModificationUser(),
				screeningResponse.getModificationDate() };

		// execute the insert statement
		DatabaseUtils.doDMLQuery(connectionFactory, connectionName,
				CREATE_SCREENING_RESPONSE_SQL,
				CREATE_SCREENING_RESPONSE_ARGUMENT_TYPES, queryArgs);

		// set the id only if the insert operation was successful.
		screeningResponse.setId(id);
	}

	/**
	 * Generates next id for a screening response using idGenerator.
	 * 
	 * @return a long that represents the next id generated
	 * @throws DAOException
	 *             if any error occurs during the generation
	 */
	private long getNextID() throws DAOException {

		try {
			// generate next id for the screening response.
			return idGenerator.getNextID();
		} catch (IDGenerationException e) {
			throw new DAOException(
					"Unable to generate id for the screening response.", e);
		}
	}
}
