/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.audit.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;


/**
 * <p>
 * This is a specific interceptor to deal with Contest Create action audit for any of the
 * <code>create*()</code> methods in the <code>DigitalRunContestManagerBean</code> class.
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
public class AuditCreateContestInterceptor extends BaseAuditContestInterceptor {

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public AuditCreateContestInterceptor() {
        // empty
    }

    /**
     * <p>
     * Audit the creation.
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

        Object result = invocation.proceed();

        getAuditor().logCreation(result, getAuditorUser(), "Entity is created.");

        return result;
    }
}
