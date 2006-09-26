/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.accuracytests;

import java.util.Date;

import com.cronos.onlinereview.autoscreening.tool.DAOException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningData;
import com.cronos.onlinereview.autoscreening.tool.ScreeningStatus;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTaskDAO;
import com.cronos.onlinereview.autoscreening.tool.UpdateFailedException;
import com.cronos.onlinereview.autoscreening.tool.accuracytests.DatabaseUtils.DataType;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This implementation of the ScreeningTaskDAO provides an Informix database as the backing
 * datastore.
 * </p>
 * <p>
 * Thread-Safety: Each invocation works on its own connection instance, and the connection is closed
 * after. Multiple concurrent reads and writes are expected on the datastore.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestTaskDAO implements ScreeningTaskDAO {
	/**
	 * Represents the sql statement to load screening tasks from the database.
	 */
	private static final String LOAD_SCREENING_TASKS_SQL = "SELECT "
			+ "project.project_id, project.project_category_id, "
			+ "upload.upload_id, upload.parameter, "
			+ "resource_info.value, "
			+ "screening_status_lu.name, "
			+ "screening_task.screening_task_id, screening_task.screener_id, screening_task.start_timestamp, "
			+ "screening_task.create_user, screening_task.create_date, "
			+ "screening_task.modify_user, screening_task.modify_date " + "FROM screening_task "
			+ "INNER JOIN screening_status_lu "
			+ "ON screening_task.screening_status_id=screening_status_lu.screening_status_id "
			+ "INNER JOIN upload ON screening_task.upload_id=upload.upload_id "
			+ "INNER JOIN project ON upload.project_id=project.project_id "
			+ "INNER JOIN resource_info ON upload.resource_id=resource_info.resource_id "
			+ "WHERE resource_info.resource_info_type_id=1";

	/**
	 * Represents the column types for the result set which is returned by executing the sql
	 * statement to load screening tasks.
	 */
	private static final DataType[] LOAD_SCREENING_TASKS_COLUMN_TYPES = new DataType[] {
			DatabaseUtils.LONG_TYPE, DatabaseUtils.LONG_TYPE, DatabaseUtils.LONG_TYPE,
			DatabaseUtils.STRING_TYPE, DatabaseUtils.STRING_TYPE, DatabaseUtils.STRING_TYPE,
			DatabaseUtils.LONG_TYPE, DatabaseUtils.LONG_TYPE, DatabaseUtils.DATE_TYPE,
			DatabaseUtils.STRING_TYPE, DatabaseUtils.DATE_TYPE, DatabaseUtils.STRING_TYPE,
			DatabaseUtils.DATE_TYPE };

	/**
	 * Represents the sql statement to update screening task onto the database.
	 */
	private static final String UPDATE_SCREENING_TASK_SQL = "UPDATE screening_task "
			+ "SET screening_status_id=?, screener_id=?, start_timestamp=?, "
			+ "create_user=?, create_date=?, modify_user=?, modify_date=? "
			+ "WHERE screening_task_id=?";

	/**
	 * Represents the argument types for the sql statement to update screening task.
	 */
	private static final DataType[] UPDATE_SCREENING_TASK_ARGUMENT_TYPES = new DataType[] {
			DatabaseUtils.LONG_TYPE, DatabaseUtils.LONG_TYPE, DatabaseUtils.DATE_TYPE,
			DatabaseUtils.STRING_TYPE, DatabaseUtils.DATE_TYPE, DatabaseUtils.STRING_TYPE,
			DatabaseUtils.DATE_TYPE, DatabaseUtils.LONG_TYPE };

	/**
	 * <p>
	 * The connection factory on which to retrieve Connections from when accessing the datastore.
	 * </p>
	 */
	private final DBConnectionFactory connectionFactory;

	/**
	 * <p>
	 * The connection name to use when retrieving connections from the connection factory. If not
	 * present, then the default connection is used.
	 * </p>
	 */
	private final String connectionName;

	/**
	 * Constructor that allows for instantiation using the specified connection factory. The default
	 * connection of that factory is used.
	 * 
	 * @param connectionFactory
	 *            the connection factory to retrieve DB connections from.
	 * @throws IllegalArgumentException
	 *             if the connectionFactory or userRetrieval is null.
	 */
	public AccuracyTestTaskDAO(DBConnectionFactory connectionFactory) {
		this(connectionFactory, null);
	}

	/**
	 * Constructor that allows for instantiation using the specified connection factory. The
	 * specified connection name is used.
	 * 
	 * @param connectionFactory
	 *            the connection factory to retrieve DB connections from.
	 * @param connectionName
	 *            the name of the connection to retrieve from the connection factory.
	 * @throws IllegalArgumentException
	 *             if the connectionFactory or userRetrieval is null or if connectionName is an
	 *             empty String.
	 */
	public AccuracyTestTaskDAO(DBConnectionFactory connectionFactory, String connectionName) {
		if (connectionFactory == null) {
			throw new IllegalArgumentException("connectionFactory should not be null.");
		}
		if (connectionName != null && connectionName.trim().length() == 0) {
			throw new IllegalArgumentException("connectionName should not be empty (trimmed).");
		}

		this.connectionFactory = connectionFactory;
		this.connectionName = connectionName;
	}

	/**
	 * <p>
	 * Loads all screening tasks in the datastore that have the provided screenerId as their
	 * screener. If a status is provided, then the tasks will also be constrained to the provided
	 * status.
	 * </p>
	 * <p>
	 * If the screenerId is null, then only the status will be used to constrain the search. If the
	 * status is null, then only the screenerId will be utilized. If both are null, it means all the
	 * tasks should be returned.
	 * </p>
	 * 
	 * @param screenerId
	 *            The screenerId of the tasks to retrieve (may be null to indicate unconstrained by
	 *            screener id)
	 * @param status
	 *            The status of the tasks to retrieve (may be null to indicate unconstrained by
	 *            status)
	 * @return The screening tasks satisfying the specified criteria.
	 * @throws IllegalArgumentException
	 *             if screenerId is not null and its long value is <= 0.
	 * @throws DAOException
	 *             if a problem occurs while accessing the datastore.
	 */
	public ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)
			throws DAOException {
		if (screenerId != null && screenerId.longValue() <= 0) {
			throw new IllegalArgumentException("screenerId should be > 0, if it's not null.");
		}

		// build the additional where condition for the load operation
		String whereCondition = buildWhereConditionForLoad(screenerId, status);

		// load screening tasks from the database
		Object[][] rows = DatabaseUtils.doQuery(connectionFactory, connectionName,
				LOAD_SCREENING_TASKS_SQL + whereCondition, new DataType[0], new Object[0],
				LOAD_SCREENING_TASKS_COLUMN_TYPES);

		// create a new ScreeningTask array
		ScreeningTask[] screeningTasks = new ScreeningTask[rows.length];

		// enumerate each data row
		for (int i = 0; i < rows.length; ++i) {
			// reference the current data row
			Object[] row = rows[i];

			// get the screening task
			ScreeningTask screeningTask = new ScreeningTask();
			loadScreeningTaskSequentially(screeningTask, row, 0);

			// assign it to the array
			screeningTasks[i] = screeningTask;
		}

		return screeningTasks;
	}

	/**
	 * Builds the WHERE condition string for the load operation of screening tasks.
	 * 
	 * @param screenerId
	 *            The screenerId of the tasks to retrieve (may be null to indicate unconstrained by
	 *            screener id)
	 * @param status
	 *            The status of the tasks to retrieve (may be null to indicate unconstrained by
	 *            status)
	 * @return the WHERE condition string
	 */
	private String buildWhereConditionForLoad(Long screenerId, ScreeningStatus status) {

		StringBuffer whereCondition = new StringBuffer();
		if (screenerId != null) {
			whereCondition.append(" AND screening_task.screener_id=" + screenerId);
		}
		if (status != null) {
			whereCondition.append(" AND screening_task.screening_status_id=" + status.getId());
		}
		return whereCondition.toString();
	}

	/**
	 * Loads data items from the data row sequentially and fills them into an instance of
	 * ScreeningData.
	 * 
	 * @param screeningData
	 *            the ScreeningData instance whose fields will be filled
	 * @param row
	 *            the data row
	 * @param startIndex
	 *            the start index to read from
	 * @return the start index of the remaining data items that haven't been read
	 * @throws DAOException
	 *             if a problem occurs while accessing the datastore or setting data to the
	 *             screeningData.
	 */
	private int loadScreeningDataSequentially(ScreeningData screeningData, Object[] row,
			int startIndex) throws DAOException {

		// unwrapLongObject are used blindly although most of them are not null
		long projectId = DatabaseUtils.unwrapLongOjbect((Long) row[startIndex++]);
		long projectCategoryId = DatabaseUtils.unwrapLongOjbect((Long) row[startIndex++]);
		long uploadId = DatabaseUtils.unwrapLongOjbect((Long) row[startIndex++]);
		String fileIdentifier = (String) row[startIndex++];
		String externalUserIDString = (String) row[startIndex++];

		// try to parse the external user id string to long
		long externalUserID;
		try {
			externalUserID = Long.parseLong(externalUserIDString);
		} catch (NumberFormatException e) {
			throw new DAOException("Unable to parse external user id [" + externalUserIDString
					+ "] to long value.", e);
		}

		try {
			screeningData.setProjectId(projectId);
			screeningData.setProjectCategoryId(projectCategoryId);
			screeningData.setUploadId(uploadId);
			screeningData.setFileIdentifier(fileIdentifier);

			// set information about the external user
			screeningData.setSubmitterHandle("handle" + externalUserID);
			screeningData.setSubmitterFirstName("firstname" + externalUserID);
			screeningData.setSubmitterLastName("lastname" + externalUserID);
			screeningData.setSubmitterEmail("email" + externalUserID);
			screeningData
					.setSubmitterAlternativeEmails(new String[] {
							"alternativeemails1_" + externalUserID,
							"alternativeemails2_" + +externalUserID });
		} catch (IllegalArgumentException e) {
			throw new DAOException("Error occurs when setting data to screeningData.", e);
		}
		return startIndex;
	}

	/**
	 * Loads data items from the data row sequentially and fills them into an instance of
	 * ScreeningTask.
	 * 
	 * @param screeningTask
	 *            the ScreeningTask instance whose fields will be filled
	 * @param row
	 *            the data row
	 * @param startIndex
	 *            the start index to read from
	 * @return the start index of the remaining data items that haven't been read
	 * @throws DAOException
	 *             if a problem occurs while accessing the datastore or setting data to the
	 *             screeningTask.
	 */
	private int loadScreeningTaskSequentially(ScreeningTask screeningTask, Object[] row,
			int startIndex) throws DAOException {

		// get screening data
		ScreeningData screeningData = new ScreeningData();
		startIndex = loadScreeningDataSequentially(screeningData, row, startIndex);

		// get screening status
		String statusName = (String) row[startIndex++];
		// statusName could not be null according to the database schema,
		// so getEnumByStringValue will not throw IllegalArgumentException.
		ScreeningStatus screeningStatus = (ScreeningStatus) Enum.getEnumByStringValue(statusName,
				ScreeningStatus.class);
		// screeningStatus maybe null, but we catch this by the later
		// setScreeningStatus setter

		// unwrapLongObject are used blindly although most of them are not null
		long screeningTaskId = DatabaseUtils.unwrapLongOjbect((Long) row[startIndex++]);
		long screenerId = DatabaseUtils.unwrapLongOjbect((Long) row[startIndex++]);
		Date startTimestamp = (Date) row[startIndex++];

		String creationUser = (String) row[startIndex++];
		Date creationDate = (Date) row[startIndex++];
		String modificationUser = (String) row[startIndex++];
		Date modificationDate = (Date) row[startIndex++];

		try {
			// set the information
			screeningTask.setScreeningData(screeningData);
			screeningTask.setScreeningStatus(screeningStatus);
			screeningTask.setId(screeningTaskId);
			screeningTask.setScreenerId(screenerId);
			screeningTask.setStartTimestamp(startTimestamp);
			screeningTask.setCreationUser(creationUser);
			screeningTask.setCreationDate(creationDate);
			screeningTask.setModificationUser(modificationUser);
			screeningTask.setModificationDate(modificationDate);
		} catch (IllegalArgumentException e) {
			throw new DAOException("Error occurs when setting data to screeningTask.", e);
		}
		return startIndex;
	}

	/**
	 * <p>
	 * Updates the provided Screening Task in the datastore.
	 * </p>
	 * 
	 * @param screeningTask
	 *            The screening task to update.
	 * @throws IllegalArgumentException
	 *             if the task is null, or it does not contains screeningStatus instance
	 * @throws UpdateFailedException
	 *             if a problem occurs due to a simultaneous update by a different screener.
	 * @throws DAOException
	 *             if a problem occurs while accessing the datastore, or the screening status of the
	 *             task has not been specified.
	 */
	public void updateScreeningTask(ScreeningTask screeningTask) throws DAOException {
		if (screeningTask == null) {
			throw new IllegalArgumentException("screeningTask should not be null.");
		}

		// check if the screening status of the task has been specified.
		if (screeningTask.getScreeningStatus() == null) {
			throw new DAOException("Screening status of screeningTask has not been specified.");
		}

		// build the where condition for update operation
		String whereCondition = buildWhereConditionForUpdate(screeningTask);

		// build the arguments for the update statement
		// wrapLongValue are used blindly, although most of them are not
		// Long.MIN_VALUE
		Object[] queryArgs = new Object[] {
				// status id
				DatabaseUtils.wrapLongValue(screeningTask.getScreeningStatus().getId()),
				// screener id
				DatabaseUtils.wrapLongValue(screeningTask.getScreenerId()),
				// start time stamp
				screeningTask.getStartTimestamp(),
				// create user, create data, modify user, modify date
				screeningTask.getCreationUser(), screeningTask.getCreationDate(),
				screeningTask.getModificationUser(), screeningTask.getModificationDate(),
				// task id
				DatabaseUtils.wrapLongValue(screeningTask.getId()) };

		// execute the update statement
		int updateCount = DatabaseUtils.doDMLQuery(connectionFactory, connectionName,
				UPDATE_SCREENING_TASK_SQL + whereCondition, UPDATE_SCREENING_TASK_ARGUMENT_TYPES,
				queryArgs);

		// check the updated rows
		if (updateCount != 1) {
			throw new UpdateFailedException(
					"A problem occurs due to a simultaneous update by a different screener.");
		}
	}

	/**
	 * Builds the WHERE condition string for the update of screening task.
	 * 
	 * @param screeningTask
	 *            the screening task to update
	 * @return the WHERE condition string
	 */
	private String buildWhereConditionForUpdate(ScreeningTask screeningTask) {

		// NOTE: only the following updates are allowed:
		// screening status from 'pending' to 'screening';
		// screening status from 'screening' to 'pending';
		// screening status from 'screening' to 'failed';
		// screening status from 'screening' to 'passed'.
		// screening status from 'screening' to 'passed with warning';

		// so if the new status is 'screening', then the old status should be
		// 'pending'; otherwise, the old status should always be 'screening'.
		if (screeningTask.getScreeningStatus() == ScreeningStatus.SCREENING) {
			return " AND screening_status_id=" + ScreeningStatus.PENDING.getId();
		} else {
			return " AND screening_status_id=" + ScreeningStatus.SCREENING.getId();
		}
	}
}
