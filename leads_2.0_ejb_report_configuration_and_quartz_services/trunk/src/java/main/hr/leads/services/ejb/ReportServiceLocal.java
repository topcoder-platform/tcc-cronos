/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.ejb;

import hr.leads.services.ReportService;

import javax.ejb.Local;

/**
 * <p>
 * This interface represents the local interface for ReportService session bean.
 * </p>
 * <p>
 * It extends ReportService interface and provides no additional methods.
 * </p>
 * <p>
 * <b> Thread Safety: </b> Implementations of this interface must be thread safe
 * when their configurable properties do not change after initialization.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 */
@Local
public interface ReportServiceLocal extends ReportService {
}

