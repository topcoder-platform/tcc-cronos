package com.topcoder.service.digitalrun.points;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.topcoder.service.digitalrun.entity.DigitalRunPoints;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsReferenceType;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;
import com.topcoder.service.digitalrun.entity.Track;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DemoEJB extends TestCase {

    /**
     * <p>
     * The ear name.
     * </p>
     */
    public static final String EAR_NAME = "dr_points_manager";

    /**
     * Get this test suite.
     *
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(DemoEJB.class);
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
     */
    public void setPoints(DigitalRunPoints points) {
        points.setDescription("description");
        points.setApplicationDate(new Date());
        points.setModificationDate(new Date());
        points.setDigitalRunPointsOperation(getPO());
        points.setDigitalRunPointsReferenceType(getReferenceType());
        points.setDigitalRunPointsStatus(getStatus());
        points.setDigitalRunPointsType(getType());
        Track track = new Track();
        track.setId(2);
        points.setTrack(track);
        points.setUserId(123);
        points.setReferenceId(234);
        points.setAwardDate(new Date());
        points.setCreationDate(new Date());
    }

    /**
     * Test the demo with ejb.
     *
     * @throws Exception
     *             if any exception occurs
     */
    public void testDemoWihtEJB() throws Exception {
        Properties env = new Properties();
        Context context = new InitialContext(env);
        DigitalRunPointsManagerRemote remote = (DigitalRunPointsManagerRemote) context.lookup(EAR_NAME
                + "/DigitalRunPointsManagerBean/remote");

        // I do not test 1.3.1 in demo ejb, because we should create complex database.
        // It's so different to create track in this test.
        // 1.3.2 How to manage DigitalRunPointsType instances

        // create digital run points type
        DigitalRunPointsType type = remote.createDigitalRunPointsType(getType());

        // update digital run points type
        remote.updateDigitalRunPointsType(type);

        // get digital run points type 10
        DigitalRunPointsType type1 = remote.getDigitalRunPointsType(type.getId());

        // get all digital run points types
        List<DigitalRunPointsType> types = remote.getAllDigitalRunPointsTypes();

        // remove digital run points type
        remote.removeDigitalRunPointsType(type.getId());

        // 1.3.3 How to manage DigitalRunPointsReferenceType instances

        // create digital run points reference type
        DigitalRunPointsReferenceType referenceType = remote
                .createDigitalRunPointsReferenceType(getReferenceType());

        // update digital run points reference type
        remote.updateDigitalRunPointsReferenceType(referenceType);

        // get digital run points reference type
        DigitalRunPointsReferenceType referenceType1 = remote.getDigitalRunPointsReferenceType(referenceType
                .getId());

        // get all digital run points reference types
        List<DigitalRunPointsReferenceType> referenceTypes = remote.getAllDigitalRunPointsReferenceTypes();

        // remove digital run points reference type
        remote.removeDigitalRunPointsReferenceType(referenceType.getId());

        // 1.3.4 How to manage DigitalRunPointsStatus instances

        // create digital run points status
        DigitalRunPointsStatus status = remote.createDigitalRunPointsStatus(getStatus());

        // update digital run points status
        remote.updateDigitalRunPointsStatus(status);

        // get digital run points status
        DigitalRunPointsStatus status1 = remote.getDigitalRunPointsStatus(status.getId());

        // get all digital run points statuses
        List<DigitalRunPointsStatus> statuses = remote.getAllDigitalRunPointsStatuses();

        // remove digital run points status
        remote.removeDigitalRunPointsStatus(status.getId());

        // 1.3.5 How to manage DigitalRunPointsOperation instances

        // create digital run points operation
        DigitalRunPointsOperation operation = remote.createDigitalRunPointsOperation(getPO());

        // update digital run points operation
        remote.updateDigitalRunPointsOperation(operation);

        // get digital run points operation
        DigitalRunPointsOperation operation1 = remote.getDigitalRunPointsOperation(operation.getId());

        // get all digital run points operation
        List<DigitalRunPointsOperation> operations = remote.getAllDigitalRunPointsOperations();

        // remove digital run points operation
        remote.removeDigitalRunPointsOperation(operation.getId());
    }
}
