/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import javax.ejb.Local;

/**
 * <p>
 * The local EJB interface that simply extends the <code>{@link SubmissionManager}</code> interface, with no
 * additional facilities.
 * </p>
 * <p>
 * <b>Thread Safety</b>: The container will assume all responsibility for thread-safety.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@Local
public interface SubmissionManagerLocal extends SubmissionManager {
}
