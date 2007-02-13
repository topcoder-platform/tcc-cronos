/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import com.topcoder.timetracker.entry.time.DAOActionException;
import com.topcoder.timetracker.entry.time.DataObject;
import com.topcoder.timetracker.entry.time.RejectReasonDAO;

/**
 * <p>
 * Mock RejectReasonDAO for failure tests.
 * </p>
 *
 * @author GavinWang
 * @version 1.1
 */
final class MockRejectReasonDAO extends RejectReasonDAO {

    /**
     * <p>
     * Create the instance of this class.
     * </p>
     *
     * @param connName
     * @param namespace
     */
    public MockRejectReasonDAO(String connName, String namespace) {
        super(connName, namespace);
    }

    /**
     * <p>
     * .
     * </p>
     * @param statement
     * @param dataObject
     * @param creationUser
     * @param creationDate
     * @throws DAOActionException
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#fillCreatePreparedStatement(java.sql.PreparedStatement, com.topcoder.timetracker.entry.time.DataObject, java.lang.String, java.util.Date)
     */
    protected void fillCreatePreparedStatement(PreparedStatement statement, DataObject dataObject, String creationUser, Date creationDate) throws DAOActionException {
        super.fillCreatePreparedStatement(statement, dataObject, creationUser,
                creationDate);
    }

    /**
     * <p>
     * .
     * </p>
     * @param statement
     * @param dataObject
     * @param modificationUser
     * @param modificationDate
     * @throws DAOActionException
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#fillUpdatePreparedStatement(java.sql.PreparedStatement, com.topcoder.timetracker.entry.time.DataObject, java.lang.String, java.util.Date)
     */
    protected void fillUpdatePreparedStatement(PreparedStatement statement, DataObject dataObject, String modificationUser, Date modificationDate) throws DAOActionException {
        super.fillUpdatePreparedStatement(statement, dataObject, modificationUser,
                modificationDate);
    }

    /**
     * <p>
     * .
     * </p>
     * @return
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#getCreateSqlString()
     */
    protected String getCreateSqlString() {
        return super.getCreateSqlString();
    }

    /**
     * <p>
     * .
     * </p>
     * @return
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#getDeleteSqlString()
     */
    protected String getDeleteSqlString() {
        return super.getDeleteSqlString();
    }

    /**
     * <p>
     * .
     * </p>
     * @return
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#getReadListSqlString()
     */
    protected String getReadListSqlString() {
        return super.getReadListSqlString();
    }

    /**
     * <p>
     * .
     * </p>
     * @return
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#getReadSqlString()
     */
    protected String getReadSqlString() {
        return super.getReadSqlString();
    }

    /**
     * <p>
     * .
     * </p>
     * @return
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#getUpdateSqlString()
     */
    protected String getUpdateSqlString() {
        return super.getUpdateSqlString();
    }

    /**
     * <p>
     * .
     * </p>
     * @param resultSet
     * @return
     * @throws DAOActionException
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#processReadListResultSet(java.sql.ResultSet)
     */
    protected List processReadListResultSet(ResultSet resultSet) throws DAOActionException {
        return super.processReadListResultSet(resultSet);
    }

    /**
     * <p>
     * .
     * </p>
     * @param resultSet
     * @return
     * @throws DAOActionException
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#processReadResultSet(java.sql.ResultSet)
     */
    protected DataObject processReadResultSet(ResultSet resultSet) throws DAOActionException {
        return super.processReadResultSet(resultSet);
    }

    /**
     * <p>
     * .
     * </p>
     * @param dataObject
     * @param creationUser
     * @param creationDate
     * @throws DAOActionException
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#setCreationParametersInDataObject(com.topcoder.timetracker.entry.time.DataObject, java.lang.String, java.util.Date)
     */
    protected void setCreationParametersInDataObject(DataObject dataObject, String creationUser, Date creationDate) throws DAOActionException {
        super.setCreationParametersInDataObject(dataObject, creationUser, creationDate);
    }

    /**
     * <p>
     * .
     * </p>
     * @param dataObject
     * @param modificationUser
     * @param modificationDate
     * @throws DAOActionException
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#setModificationParametersInDataObject(com.topcoder.timetracker.entry.time.DataObject, java.lang.String, java.util.Date)
     */
    protected void setModificationParametersInDataObject(DataObject dataObject, String modificationUser, Date modificationDate) throws DAOActionException {
        super.setModificationParametersInDataObject(dataObject, modificationUser,
                modificationDate);
    }

    /**
     * <p>
     * .
     * </p>
     * @param dataObject
     * @throws DAOActionException
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#verifyCreateDataObject(com.topcoder.timetracker.entry.time.DataObject)
     */
    protected void verifyCreateDataObject(DataObject dataObject) throws DAOActionException {
        super.verifyCreateDataObject(dataObject);
    }

    /**
     * <p>
     * .
     * </p>
     * @param dataObject
     * @throws DAOActionException
     * @see com.topcoder.timetracker.entry.time.RejectReasonDAO#verifyUpdateDataObject(com.topcoder.timetracker.entry.time.DataObject)
     */
    protected void verifyUpdateDataObject(DataObject dataObject) throws DAOActionException {
        super.verifyUpdateDataObject(dataObject);
    }

}
