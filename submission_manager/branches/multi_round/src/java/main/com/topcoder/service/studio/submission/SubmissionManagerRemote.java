/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import javax.ejb.Remote;

/**
 * <p>
 * The remote EJB interface that simply extends the <code>{@link SubmissionManager}</code> interface, with no
 * additional facilities.
 * </p>
 * <p>
 * <b>Thread Safety</b>: The container will assume all responsibility for thread-safety.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface SubmissionManagerRemote extends SubmissionManager {
}
