/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.util.Calendar;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 * <p>
 * The mock PayByCreditCardAction used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPayByCreditCardAction extends PayByCreditCardAction {

    /**
     * Initializes the mock data.
     */
    private void init() {
        // prepare HTTP request and session
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("123.45.67.89");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("Id", 1);
        ServletActionContext.setRequest(request);

        // initialize with valid values
        setContestServiceFacade(new MockContestServiceFacade());
        setProjectId(1);
        setContestId(0);
        setCardNumber("1234-5678");
        setCardType("Visa");
        setCardExpiryMonth("1");
        setCardExpiryYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR) + 1));
        setCardExpiryMonth("9");
        setFirstName("John");
        setLastName("Doe");
        setZipCode("12345");
        setAddress("123 Nowhere Street");
        setCity("Omaha");
        setState("NE");
        setCountry("US");
        setPhone("(123) 456-7890");
        setEmail("johndoe@aol.com");
        setCsc("123");
    }

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

        // prepare the mock data
        init();

        // set invalid values, if required
        Map<String, Object> validations = TestHelper.getFieldValues();
        if (validations.size() != 0) {
            for (String key : validations.keySet()) {
                Object value = validations.get(key);

                if (key.equals("contestId")) {
                    setContestId(Long.parseLong(value.toString()));
                } else if (key.equals("projectId")) {
                    setProjectId(Long.parseLong(value.toString()));
                } else if (key.equals("cardNumber")) {
                    setCardNumber((String) value);
                } else if (key.equals("cardType")) {
                    setCardType((String) value);
                } else if (key.equals("cardExpiryMonth")) {
                    setCardExpiryMonth((String) value);
                } else if (key.equals("cardExpiryYear")) {
                    setCardExpiryYear((String) value);
                } else if (key.equals("firstName")) {
                    setFirstName((String) value);
                } else if (key.equals("lastName")) {
                    setLastName((String) value);
                } else if (key.equals("zipCode")) {
                    setZipCode((String) value);
                } else if (key.equals("address")) {
                    setAddress((String) value);
                } else if (key.equals("city")) {
                    setCity((String) value);
                } else if (key.equals("state")) {
                    setState((String) value);
                } else if (key.equals("country")) {
                    setCountry((String) value);
                } else if (key.equals("phone")) {
                    setPhone((String) value);
                } else if (key.equals("email")) {
                    setEmail((String) value);
                } else if (key.equals("csc")) {
                    setCsc((String) value);
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
