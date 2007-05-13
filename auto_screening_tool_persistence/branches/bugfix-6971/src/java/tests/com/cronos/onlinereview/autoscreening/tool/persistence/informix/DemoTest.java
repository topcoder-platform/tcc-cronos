/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.persistence.informix;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.tool.ScreeningResponse;
import com.cronos.onlinereview.autoscreening.tool.ScreeningResponseDAO;
import com.cronos.onlinereview.autoscreening.tool.ScreeningStatus;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTaskDAO;
import com.cronos.onlinereview.autoscreening.tool.UpdateFailedException;
import com.cronos.onlinereview.external.impl.DBUserRetrieval;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * This TestCase demonstrates the usage of this component.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends DbTestCase {

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
    }

    /**
     * Demos how to use InformixResponseDAO and InformixTaskDAO.
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testPersistence() throws Exception {
        // Create the ScreeningTaskDAO
        ScreeningTaskDAO dao = new InformixTaskDAO(new DBConnectionFactoryImpl(DB_NAMESPACE),
            new DBUserRetrieval(DB_NAMESPACE));
        // connection name can be specified explicitly
        dao = new InformixTaskDAO(new DBConnectionFactoryImpl(DB_NAMESPACE), "informix_connection",
            new DBUserRetrieval(DB_NAMESPACE));

        // Load all pending tasks.
        ScreeningTask[] result = dao.loadScreeningTasks(null, ScreeningStatus.PENDING);

        // Retrieve the first task and update it.
        ScreeningTask chosenTask = result[0];

        // set the screener id and screening status
        chosenTask.setScreenerId(12);
        chosenTask.setScreeningStatus(ScreeningStatus.SCREENING);

        // Update it in the DAO.
        try {
            dao.updateScreeningTask(chosenTask);
        } catch (UpdateFailedException e) {
            // Find another task to update. because it probably has already been
            // modified by another concurrently running Screening Task.
            // ...
        }

        // Create screening response DAO.
        ScreeningResponseDAO resDao = new InformixResponseDAO(new DBConnectionFactoryImpl(
            DB_NAMESPACE), IDGeneratorFactory.getIDGenerator("screening_result_id_seq"));
        // connection name can be specified explicitly
        resDao = new InformixResponseDAO(new DBConnectionFactoryImpl(DB_NAMESPACE),
            "informix_connection", IDGeneratorFactory.getIDGenerator("screening_result_id_seq"));

        // Create a new Screening Response.
        ScreeningResponse response = new ScreeningResponse();
        response.setScreeningTaskId(chosenTask.getId());
        response.setResponseCodeId(2);
        response.setDetailMessage("detail message");
        response.setCreateDate(new Date());
        response.setCreateUser("operator");
        response.setModificationDate(new Date());
        response.setModificationUser("operator");

        // Store it in the database.
        resDao.createScreeningResponse(response);
    }
}