/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import javax.ejb.Remote;

/**
 * <p>
 * This EJB remote interface defines contract to manage the digital run contest data in persistence.
 * </p>
 *
 * <p>
 * It inherits all the method of the <code>DigitalRunContestManager</code> interface.
 * </p>
 *
 * <p>
 * It is marked with "@Remote" representing it's an remote interface.
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     Implementations of it need to be stateless session bean, and should be thread-safe when used in
 *     EJB container.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface DigitalRunContestManagerRemote extends DigitalRunContestManager {
}

