/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.ejb.Ejb3Configuration;

/**
 * <p>
 * Demo tests showing the usage of this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * Represents the entity manager used for CRUD operations on entity.
     */
    private static EntityManager manager;

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * Demo showing the usage of java beans.
     */
    public void testDemo1() {
        // create the DigitalRunPointsType instance
        DigitalRunPointsType digitalRunPointsType = new DigitalRunPointsType();
        digitalRunPointsType.setDescription("points type");

        // create the DigitalRunPointsStatus instance
        DigitalRunPointsStatus digitalRunPointsStatus = new DigitalRunPointsStatus();
        digitalRunPointsStatus.setDescription("points statuses");

        // create the DigitalRunPointsReferenceType instance
        DigitalRunPointsReferenceType digitalRunPointsReferenceType = new DigitalRunPointsReferenceType();
        digitalRunPointsReferenceType.setDescription("points reference type");

        // create the DigitalRunPointsOperation instance
        DigitalRunPointsOperation digitalRunPointsOperation = new DigitalRunPointsOperation();
        digitalRunPointsOperation.setDescription("points operation");

        // create the DigitalRunPoints instance
        DigitalRunPoints digitalRunPoints = new DigitalRunPoints();

        // create track
        Track track = new Track();
        track.setDescription("description");
        track.setStartDate(new Date());
        track.setEndDate(new Date());
        track.setCreationDate(new Date());
        track.setModificationDate(new Date());

        // set track
        // assume that tracks entities were created
        digitalRunPoints.setTrack(track);

        // set digital run points status
        digitalRunPoints.setDigitalRunPointsStatus(digitalRunPointsStatus);

        // set digital run points type
        digitalRunPoints.setDigitalRunPointsType(digitalRunPointsType);

        // set digital run points reference type
        digitalRunPoints.setDigitalRunPointsReferenceType(digitalRunPointsReferenceType);

        // set digital run points operation
        digitalRunPoints.setDigitalRunPointsOperation(digitalRunPointsOperation);

        Date applicationDate = new Date();
        Date awardDate = new Date();

        // for the following userId, amount, applicationDate,
        // awardDate and referenceId
        // we assume that they were retrieved somewhere.
        // set user id
        digitalRunPoints.setUserId(101);

        // set amount
        digitalRunPoints.setAmount(101.20);

        // set application date
        digitalRunPoints.setApplicationDate(applicationDate);

        // set award date
        digitalRunPoints.setAwardDate(awardDate);

        // set reference id
        digitalRunPoints.setReferenceId(40);

        // set potential
        digitalRunPoints.setPotential(true);

        // the usage of other entites are similiar
    }

    /**
     * Demo showing the usage of classes as entities via a transaction-scoped JTA EntityManager.
     */
    public void testDemo2() {
        // get the EntityManager
        Ejb3Configuration cfg = new Ejb3Configuration();
        EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
        manager = emf.createEntityManager();

        // get the EntityTransaction
        EntityTransaction et = manager.getTransaction();
        try {
            // begin the transaction
            et.begin();

            TrackType entity = new TrackType();
            entity.setDescription("description");
            entity.setCreationDate(new Date());
            entity.setModificationDate(new Date());

            // Persist the entity
            manager.persist(entity);

            // read
            TrackType peristed = manager.find(TrackType.class, entity.getId());

            // update the entity
            peristed.setDescription("newdisc");

            manager.merge(peristed);

            // delete the entity
            manager.remove(peristed);
        } catch (PersistenceException e) {
            // if any errors occurs, rollback the transaction
            et.rollback();
            throw e;
        } finally {
            // finally close the EntityManager
            manager.close();
        }

        // NOTE: Manipulations on the other entities are quite
        // similar, so they are not shown.

    }
}
