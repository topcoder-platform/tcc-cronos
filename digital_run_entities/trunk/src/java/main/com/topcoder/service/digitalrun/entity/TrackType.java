/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity;

/**
 * <p>
 * The <code>TrackType</code> entity. All the attributes of this entity are defined in its base entity.
 * </p>
 *
 * <p>
 * The following code demonstrates the usage of the entity via a transaction scoped JTA entity manager.
 *
 * <pre>
 * // get the EntityManager
 * Ejb3Configuration cfg = new Ejb3Configuration();
 * EntityManagerFactory emf = cfg.configure(&quot;hibernate.cfg.xml&quot;).buildEntityManagerFactory();
 * manager = emf.createEntityManager();
 *
 * // get the EntityTransaction
 * EntityTransaction et = manager.getTransaction();
 * try {
 *     // begin the transaction
 *     et.begin();
 *
 *     TrackType entity = new TrackType();
 *     entity.setDescription(&quot;description&quot;);
 *
 *     // Persist the entity
 *     manager.persist(entity);
 *
 *     // refresh to get the time stamps to the entity
 *     manager.refresh(entity);
 *
 *     // read
 *     TrackType peristed = manager.find(TrackType.class, entity.getId());
 *
 *     // update the entity
 *     peristed.setDescription(&quot;newdisc&quot;);
 *
 *     manager.merge(peristed);
 *
 *     // delete the entity
 *     manager.remove(peristed);
 * } catch (PersistenceException e) {
 *     // if any errors occurs, rollback the transaction
 *     et.rollback();
 *     throw e;
 * } finally {
 *     // finally close the EntityManager
 *     manager.close();
 * }
 *
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread Safety: It's mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class TrackType extends BaseEntity {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 3325576190810009633L;

    /**
     * Creates the instance. Empty constructor.
     */
    public TrackType() {
        // empty
    }
}
