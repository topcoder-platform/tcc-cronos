/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.audit.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import com.topcoder.service.digitalrun.entity.BaseEntity;


/**
 * <p>
 * This is a specific interceptor to deal with Contest Update action audit for any of the
 * <code>update*()</code> methods in the <code>DigitalRunContestManagerBean</code> class.
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     It is thread safe to use since it is immutable after initialization.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public class AuditUpdateContestInterceptor extends BaseAuditContestInterceptor {

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public AuditUpdateContestInterceptor() {
        // empty
    }

    /**
     * <p>
     * Audit the update.
     * </p>
     *
     * @param invocation The invocation context.
     *
     * @return The result.
     *
     * @throws Exception if there was a failure during this operation.
     */
    @AroundInvoke
    public Object audit(InvocationContext invocation) throws Exception {
        BaseEntity newEntity = (BaseEntity) invocation.getParameters()[0];

        /**
         * We don't validate null here, just do audit.
         */

        if (newEntity != null) {
            BaseEntity oldEntity = getEntityManager().find(newEntity.getClass(), newEntity.getId());

            /**
             * I have no way but do audit at first before actually updated.
             *
             * Because after actually updating, the oldEntity(which is a managed entity) will be modified
             * and contain the new states came from the newEntity. In other words, the oldEntity will be
             * exactly same as newEntity after merging. Also, the BaseEntity does not have a clone() method
             * , so it is hard to record the old states.
             *
             * This is because, we are using transaction-scoped entity manager, so the entity manager
             * used by interceptor and the entity manager used by EJBean is the same one.
             */

            if (oldEntity != null) {
                getAuditor().logModification(
                        oldEntity,
                        newEntity,
                        getAuditorUser(),
                        "Entity updated");
            }
        }

        return invocation.proceed();
    }
}
