package com.topcoder.service.digitalrun.points.accuracytests;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.service.digitalrun.entity.DigitalRunPoints;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsReferenceType;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;
import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.points.TestHelper;

class AccuracyTestHelper {


    /**
     * Get the DigitalRunPointsOperation instance.
     *
     * @return the DigitalRunPointsOperation instance
     */
    public static DigitalRunPointsOperation getPO() {
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
    public static DigitalRunPointsReferenceType getReferenceType() {
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
    public static DigitalRunPointsStatus getStatus() {
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
    public static DigitalRunPointsType getType() {
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
    public static void setPoints(DigitalRunPoints points) throws Exception {
        points.setDescription("description");
        points.setApplicationDate(new Date());
        points.setModificationDate(new Date());
        points.setDigitalRunPointsOperation((DigitalRunPointsOperation)TestHelper.persist(null, getPO()));
        points.setDigitalRunPointsReferenceType((DigitalRunPointsReferenceType)TestHelper.persist(null, getReferenceType()));
        points.setDigitalRunPointsStatus((DigitalRunPointsStatus)TestHelper.persist(null, getStatus()));
        points.setDigitalRunPointsType((DigitalRunPointsType)TestHelper.persist(null, getType()));
        points.setTrack((Track)TestHelper.persist(null, getTrack()));
        points.setUserId(123);
        points.setAmount(100d);
        points.setReferenceId(234);
        points.setAwardDate(new Date());
        points.setCreationDate(new Date());
    }

    /**
     * Creates the Track for testing purpose.
     *
     * @return the entity created
     */
    public static  Track getTrack() {
        PointsCalculator pointsCalculator = createPointsCalculator();
        TestHelper.persist(null, pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        TestHelper.persist(null, trackStatus);

        TrackType trackType = createTrackType();
        TestHelper.persist(null, trackType);
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
    public static TrackStatus createTrackStatus() {
        TrackStatus entity = new TrackStatus();
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the TrackType for testing purpose.
     *
     * @return the entity created
     */
    public static  TrackType createTrackType() {
        TrackType entity = new TrackType();
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the PointsCalculator for testing purpose.
     *
     * @return the entity created
     */
    public static  PointsCalculator createPointsCalculator() {
        PointsCalculator entity = new PointsCalculator();
        entity.setClassName("className");
        entity.setDescription("description");
        return entity;
    }

    /**
     * Clear database.
     */
    public static void clearDatabase() {
        EntityManager em = TestHelper.getEntityManager();
        em.getTransaction().begin();
        Query res = null;
        res = em.createNativeQuery("delete from dr_points");
        res.executeUpdate();
        res = em.createNativeQuery("delete from dr_points_status_lu");
        res.executeUpdate();
        res = em.createNativeQuery("delete from dr_points_reference_type_lu");
        res.executeUpdate();
        res = em.createNativeQuery("delete from dr_points_operation_lu");
        res.executeUpdate();
        res = em.createNativeQuery("delete from dr_points_type_lu");
        res.executeUpdate();
        em.getTransaction().commit();
    }
}
