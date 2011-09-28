package com.topcoder.accounting.mock;

import org.apache.axis2.context.ServiceContext;
import org.apache.axis2.description.AxisService;

public class ServiceContextMock extends ServiceContext {
    private AxisService axisService;

    public org.apache.axis2.description.AxisService getAxisService() {
        return this.axisService;
    }

    /**
     * <p>
     * Setter method for the axisService.
     * </p>
     * @param axisService the axisService to set
     */
    public void setAxisService(AxisService axisService) {
        this.axisService = axisService;
    }
}
