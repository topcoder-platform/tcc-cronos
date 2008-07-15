/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.audit.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;


/**
 * <p>
 * This is a specific interceptor to deal with Contest Remove action audit for any of the
 * <code>remove*()</code> methods in the <code>DigitalRunContestManagerBean</code> class.
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
public class AuditRemoveContestInterceptor extends BaseAuditContestInterceptor {

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public AuditRemoveContestInterceptor() {
        // empty
    }

    /**
     * <p>
     * Audit the removal.
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
        long id = (Long) invocation.getParameters()[0];

        Object result = invocation.proceed();

        getAuditor().logDeletion(id, getAuditorUser(), "Entity with id " + id + " is removed");

        return result;
    }
}
