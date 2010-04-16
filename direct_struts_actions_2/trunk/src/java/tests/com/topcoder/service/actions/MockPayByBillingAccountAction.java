/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.util.Map;

import com.topcoder.service.payment.PaymentType;

/**
 * <p>
 * The mock PayByBillingAccountAction used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPayByBillingAccountAction extends PayByBillingAccountAction {

    /**
     * Overrides the prepare method for unit testing field validations.
     */
    @Override
    public void prepare() {
        super.prepare();

        // if running demo, don't do any additional setup
        if (TestHelper.getTestingMode().equalsIgnoreCase("demo")) {
            return;
        }

        // initialize with valid values
        setContestServiceFacade(new MockContestServiceFacade());
        setPoNumber("test");
        setClientId(1);
        setProjectId(1);
        setType(PaymentType.TCPurchaseOrder);

        // set invalid values, if required
        Map<String, Object> validations = TestHelper.getFieldValues();
        if (validations.size() != 0) {
            for (String key : validations.keySet()) {
                Object value = validations.get(key);

                if (key.equals("poNumber")) {
                    setPoNumber((String) value);
                } else if (key.equals("clientId")) {
                    setClientId(Long.parseLong(value.toString()));
                } else if (key.equals("projectId")) {
                    setProjectId(Long.parseLong(value.toString()));
                } else if (key.equals("type")) {
                    setType((PaymentType) value);
                }
            }
        }
    }

    /**
     * Overrides the execute method of parent to perform the action.
     *
     * @return the action result
     */
    @Override
    public String execute() {
        String result = super.execute();

        // for demo mode, return INPUT if there are field errors so that control
        // will stay on the form and user can see the errors
        if (TestHelper.getTestingMode().equalsIgnoreCase("demo")) {
            return hasFieldErrors() ? INPUT : result;
        }
        return result;
    }
}
