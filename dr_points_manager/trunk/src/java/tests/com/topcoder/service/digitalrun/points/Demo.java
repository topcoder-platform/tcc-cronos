/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.service.digitalrun.entity.DigitalRunPoints;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsReferenceType;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;
import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.points.dao.implementations.AlwaysTrueValidator;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsDAO;
import com.topcoder.service.digitalrun.points.dao.implementations.MockEntityManager;
import com.topcoder.service.digitalrun.points.dao.implementations.MockSearchStrategy;
import com.topcoder.service.digitalrun.points.dao.implementations.MockSessionContext;
import com.topcoder.service.digitalrun.points.manager.bean.DigitalRunPointsManagerBean;
import com.topcoder.util.datavalidator.ObjectValidator;

/**
 * <p>
 * Demo the usage of this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * A DigitalRunPointsManagerBean instance for testing.
     */
    private DigitalRunPointsManagerBean impl;

    /**
     * A EntityManager instance for testing.
     */
    private MockEntityManager em;

    /**
     * Get this test suite.
     *
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * Get the DigitalRunPointsOperation instance.
     *
     * @return the DigitalRunPointsOperation instance
     */
    public DigitalRunPointsOperation getPO() {
        DigitalRunPointsOperation operation = new DigitalRunPointsOperation();
        operation.setDescription("description");
        operation.setCreationDate(new Date());
        operation.setModificationDate(new Date());
        return operation;
    }

    /**
     * Get the DigitalRunPointsReferenceType instance.
     *
     * @return the DigitalRunPointsReferenceType instance
     */
    public DigitalRunPointsReferenceType getReferenceType() {
        DigitalRunPointsReferenceType referenceType = new DigitalRunPointsReferenceType();
        referenceType.setDescription("description");
        referenceType.setCreationDate(new Date());
        referenceType.setModificationDate(new Date());
        return referenceType;
    }

    /**
     * Get the DigitalRunPointsStatus instance.
     *
     * @return the DigitalRunPointsStatus instance
     */
    public DigitalRunPointsStatus getStatus() {
        DigitalRunPointsStatus status = new DigitalRunPointsStatus();
        status.setDescription("description");
        status.setCreationDate(new Date());
        status.setModificationDate(new Date());
        return status;
    }

    /**
     * Get the DigitalRunPointsType instance.
     *
     * @return the DigitalRunPointsType instance
     */
    public DigitalRunPointsType getType() {
        DigitalRunPointsType type = new DigitalRunPointsType();
        type.setDescription("description");
        type.setCreationDate(new Date());
        type.setModificationDate(new Date());
        return type;
    }

    /**
     * Get the DigitalRunPointsType instance.
     *
     * @param points
     *            the points
     * @throws Exception
     *             to test
     */
    public void setPoints(DigitalRunPoints points) throws Exception {
        points.setDescription("description");
        points.setApplicationDate(new Date());
        points.setModificationDate(new Date());
        points.setDigitalRunPointsOperation(impl.createDigitalRunPointsOperation(getPO()));
        points.setDigitalRunPointsReferenceType(impl.createDigitalRunPointsReferenceType(getReferenceType()));
        points.setDigitalRunPointsStatus(impl.createDigitalRunPointsStatus(getStatus()));
        points.setDigitalRunPointsType(impl.createDigitalRunPointsType(getType()));
        Track track = getTrack();
        TestHelper.persist(em, track);
        points.setTrack(track);
        points.setUserId(123);
        points.setReferenceId(234);
        points.setAwardDate(new Date());
        points.setCreationDate(new Date());
    }

    /**
     * Creates the Track for testing purpose.
     *
     * @return the entity created
     */
    protected Track getTrack() {
        PointsCalculator pointsCalculator = createPointsCalculator();
        TestHelper.persist(em, pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        TestHelper.persist(em, trackStatus);

        TrackType trackType = createTrackType();
        TestHelper.persist(em, trackType);
        Track entity = new Track();
        entity.setPointsCalculator(pointsCalculator);
        entity.setTrackStatus(trackStatus);
        entity.setTrackType(trackType);
        entity.setDescription("description");
        entity.setStartDate(new Date());
        entity.setEndDate(new Date());
        entity.setCreationDate(new Date());
        entity.setModificationDate(new Date());
        return entity;
    }

    /**
     * Creates the TrackStatus for testing purpose.
     *
     * @return the entity created
     */
    protected TrackStatus createTrackStatus() {
        TrackStatus entity = new TrackStatus();
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the TrackType for testing purpose.
     *
     * @return the entity created
     */
    protected TrackType createTrackType() {
        TrackType entity = new TrackType();
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the PointsCalculator for testing purpose.
     *
     * @return the entity created
     */
    protected PointsCalculator createPointsCalculator() {
        PointsCalculator entity = new PointsCalculator();
        entity.setClassName("className");
        entity.setDescription("description");
        return entity;
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             if any error occurs
     */
    @Override
    public void setUp() throws Exception {
        impl = new DigitalRunPointsManagerBean();
        ConfigurationProvider.retrieveConfigurationFromFile();
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "logName", "logger_temp");
        MockSessionContext sc = new MockSessionContext();
        EntityManager manager = null;
        try {
            Ejb3Configuration cfg = new Ejb3Configuration();
            EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
            manager = emf.createEntityManager();
        } catch (Exception e) {
            // ignore
        }
        em = new MockEntityManager(manager);
        sc.setEm(em);
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "sessionContext", sc);
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "unitName", "unit_name");

        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "pointsDAOKey", "PointsDAOImpl");
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "pointsTypeDAOKey", "PointsTypeDAOImpl");
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "pointsOperationDAOKey",
                "PointsOperationDAOImpl");
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "pointsReferenceTypeDAOKey",
                "PointsReferenceTypeDAOImpl");
        TestHelper
                .setPrivateField(DigitalRunPointsManagerBean.class, impl, "pointsStatusDAOKey", "PointsStatusDAOImpl");

        Method method = DigitalRunPointsManagerBean.class.getDeclaredMethod("initialize");
        method.setAccessible(true);
        method.invoke(impl);

        Map<String, ObjectValidator> fields = new HashMap<String, ObjectValidator>();
        fields.put("key_1", new AlwaysTrueValidator());
        Map<String, String> alias = new HashMap<String, String>();
        alias.put("key_1", "value_1");
        SearchBundle searchBundle = new SearchBundle("name", fields, alias, "context", new MockSearchStrategy());

        JPADigitalRunPointsDAO pointsDAO = (JPADigitalRunPointsDAO) TestHelper.getPrivateField(
                DigitalRunPointsManagerBean.class, impl, "pointsDAO");
        pointsDAO.setSearchBundle(searchBundle);
    }

    /**
     * <p>
     * Demo the usage of this component.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testDemo() throws Exception {
        // 1.3.1 Customer scenario that shows how to work with DigitalRunPoints
        // entities

        // -it is necessary for the application to place the configuration
        // in the ConfigurationProvider class so that the bean can create
        // the necessary daos
        // -ConfigurationObject config = ...;
        // ConfigurationProvider.setConfiguration(config);
        // or
        // ConfigurationProvider.retrieveConfigurationFromFile(filename,
        // namespace);

        // lookup the bean
        DigitalRunPointsManagerRemote bean = impl;

        // create a DigitalRunPoints instance and set its fields
        DigitalRunPoints drp = new DigitalRunPoints();
        // ceate a DigitalRunPointsStatus, DigitalRunPointsType,
        // DigitalRunPointsReferenceType and DigitalRunPointsOperation
        // entities and set them on DigitalRunPoints instance then set the
        // rest of DigitalRunPoints fields; among others set amount to 100
        drp.setAmount(100);
        setPoints(drp);

        // create DigitalRunPoints entity into persistence
        drp = bean.createDigitalRunPoints(drp);

        // the drp entity has now an id; the amount is 100
        // modify drp.amount field and update
        drp.setAmount(200);
        bean.updateDigitalRunPoints(drp);

        // get a DigitalRunPoints entity by specifying an id
        long id = drp.getId();
        DigitalRunPoints drp1 = bean.getDigitalRunPoints(id);
        // the DigitalRunPoints created above was returned

        // search for DigitalRunPoints entities using a filter
        EqualToFilter filter = new EqualToFilter("key_1", new Long(1));
        List<DigitalRunPoints> drpList = bean.searchDigitalRunPoints(filter);
        // the list contains the DigitalRunPoints entity created above

        // remove the created DigitalRunPoints from persistence
        bean.removeDigitalRunPoints(id);

        // 1.3.2 How to manage DigitalRunPointsType instances

        // create digital run points type
        DigitalRunPointsType type = bean.createDigitalRunPointsType(getType());

        // update digital run points type
        bean.updateDigitalRunPointsType(type);

        // get digital run points type 10
        DigitalRunPointsType type1 = bean.getDigitalRunPointsType(type.getId());

        // get all digital run points types
        List<DigitalRunPointsType> types = bean.getAllDigitalRunPointsTypes();

        // remove digital run points type
        bean.removeDigitalRunPointsType(type.getId());

        // 1.3.3 How to manage DigitalRunPointsReferenceType instances

        // create digital run points reference type
        DigitalRunPointsReferenceType referenceType = bean.createDigitalRunPointsReferenceType(getReferenceType());

        // update digital run points reference type
        bean.updateDigitalRunPointsReferenceType(referenceType);

        // get digital run points reference type
        DigitalRunPointsReferenceType referenceType1 = bean.getDigitalRunPointsReferenceType(referenceType.getId());

        // get all digital run points reference types
        List<DigitalRunPointsReferenceType> referenceTypes = bean.getAllDigitalRunPointsReferenceTypes();

        // remove digital run points reference type
        bean.removeDigitalRunPointsReferenceType(referenceType.getId());

        // 1.3.4 How to manage DigitalRunPointsStatus instances

        // create digital run points status
        DigitalRunPointsStatus status = bean.createDigitalRunPointsStatus(getStatus());

        // update digital run points status
        bean.updateDigitalRunPointsStatus(status);

        // get digital run points status
        DigitalRunPointsStatus status1 = bean.getDigitalRunPointsStatus(status.getId());

        // get all digital run points statuses
        List<DigitalRunPointsStatus> statuses = bean.getAllDigitalRunPointsStatuses();

        // remove digital run points status
        bean.removeDigitalRunPointsStatus(status.getId());

        // 1.3.5 How to manage DigitalRunPointsOperation instances

        // create digital run points operation
        DigitalRunPointsOperation operation = bean.createDigitalRunPointsOperation(getPO());

        // update digital run points operation
        bean.updateDigitalRunPointsOperation(operation);

        // get digital run points operation
        DigitalRunPointsOperation operation1 = bean.getDigitalRunPointsOperation(operation.getId());

        // get all digital run points operation
        List<DigitalRunPointsOperation> operations = bean.getAllDigitalRunPointsOperations();

        // remove digital run points operation
        bean.removeDigitalRunPointsOperation(operation.getId());
    }

}
