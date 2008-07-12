/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.stresstests;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;

import junit.framework.TestCase;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ejb.SessionContext;

import javax.persistence.EntityManager;


/**
 * <p>
 * The base test case class for testing.
 * </p>
 * @author telly
 * @version 1.0
 */
public class BaseTestCase extends TestCase {
    /** Represents the entity manager used for CRUD operations on entity. */
    protected static EntityManager manager;

    /** The SessionContext. */
    protected static SessionContext context = null;
    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    protected static final long NUMBER = 10;

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    protected long current = -1;
    
    /**
     * <p>
     * Starts to count time.
     * </p>
     */
    protected void start() {
        current = System.currentTimeMillis();
    }

    /**
     * <p>
     * Prints test result.
     * </p>
     *
     * @param name the test name
     * @param count the run count
     */
    protected void printResult(String name, long count) {
        System.out.println("The test [" + name + "] run " + count + " times, took time: " +
            (System.currentTimeMillis() - current) + " ms");
    }
    /**
     * <p>
     * Sets the value of a private field in the given class.
     * </p>
     *
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be set
     * @param value the value to set
     */
    protected static void setPrivateField(Object instance, String name, Object value) {
        Field field = null;

        try {
            // get the reflection of the field
            field = instance.getClass().getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * Creates the TrackStatus for testing purpose.
     *
     * @return the entity created
     */
    public TrackStatus createTrackStatus() {
        TrackStatus entity = new TrackStatus();
        entity.setDescription("description");

        return entity;
    }

    /**
     * Creates the TrackType for testing purpose.
     *
     * @return the entity created
     */
    public TrackType createTrackType() {
        TrackType entity = new TrackType();
        entity.setDescription("description");

        return entity;
    }

    /**
     * Creates the Track for testing purpose.
     *
     * @param pointsCalculator the associated points calculator
     * @param trackStatus the associated track status
     * @param trackType the associated track type
     * @param projectType the project type
     *
     * @return the entity created
     */
    public Track createTrack(PointsCalculator pointsCalculator, TrackStatus trackStatus, TrackType trackType,
        ProjectType projectType) {
        Track entity = new Track();
        entity.setPointsCalculator(pointsCalculator);
        entity.setTrackStatus(trackStatus);
        entity.setTrackType(trackType);
        entity.setDescription("description");
        entity.setStartDate(new Date());
        entity.setEndDate(new Date());
        entity.setCreationDate(new Date());
        entity.setModificationDate(new Date());

        Collection<ProjectType> projectTypes = new ArrayList<ProjectType>();
        projectTypes.add(projectType);
        entity.setProjectTypes(projectTypes);

        return entity;
    }

    /**
     * Creates the PointsCalculator for testing purpose.
     *
     * @param id the id
     *
     * @return the entity created
     */
    public PointsCalculator createPointsCalculator(Long id) {
        PointsCalculator entity = new PointsCalculator();
        entity.setClassName("className");
        entity.setDescription("description");

        if (id != null) {
            entity.setId(id);
        }

        return entity;
    }

    /**
     * Creates the ProjectType for testing purpose.
     *
     * @param id the id
     *
     * @return the entity created
     */
    public ProjectType createProjectType(Long id) {
        ProjectType entity = new ProjectType();
        entity.setId(id);
        entity.setCreationUser("creationUser");
        entity.setDescription("description");
        entity.setModificationUser("modificationUser");
        entity.setName("name");

        return entity;
    }
}
