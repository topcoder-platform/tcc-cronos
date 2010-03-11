/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax.stresstests;

import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.struts2.ServletActionContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.service.actions.AbstractAction;
import com.topcoder.service.actions.AggregateDataModel;
import com.topcoder.service.actions.ajax.CustomFormatAJAXResult;
import com.topcoder.service.actions.ajax.serializers.JSONDataSerializer;


/**
 * Stress tests for <code>CustomFormatAJAXResult</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CustomFormatAJAXResultStress extends TestCase {
    /**
     * Stress test for <code>execute()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testExecuteStress() throws Exception {
        for (int i = 0; i < 1000; i++) {
            CustomFormatAJAXResult result = new CustomFormatAJAXResult();
            ((JSONDataSerializer) result.getDataSerializer())
                    .setJsonResultTemplate("{\"result\":{\"name\":\"$[action]\",\"return\":$[result]}}");
            ActionContext.setContext(new ActionContext(new HashMap()));

            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletActionContext.setResponse(response);

            MockHttpServletRequest request = new MockHttpServletRequest();
            ServletActionContext.setRequest(request);

            MockActionInvocation invocation = new MockActionInvocation();

            AbstractAction action = new MockAction();

            AggregateDataModel dataModel = new AggregateDataModel();
            DataClass p = new DataClass();
            p.setValue("d1");
            p.setName("p1");

            dataModel.setAction("action1");
            dataModel.setData("return", p);

            action.setModel(dataModel);

            invocation.setAction(action);

            result.execute(invocation);

            assertEquals("{\"result\":{\"name\":\"action1\",\"return\":" +
                "{\"name\":\"p1\",\"value\":\"d1\"}}}",
                response.getContentAsString());
        }
    }

    /**
     * Mock Action.
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class MockAction extends AbstractAction {
    }

    /**
     * Mock Data Class.
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    public class DataClass
    {
        /**
         * Mock property.
         */
        private String name;

        /**
         * Mock property.
         */
        private String value;

        /**
         * Mock property.
         * @return mock getter.
         */
        public String getName() {
            return name;
        }

        /**
         * Mock property.
         * @param name mock property name.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Mock property.
         * @return mock getter.
         */
        public String getValue() {
            return value;
        }

        /**
         * Mock property.
         * @param value mock property value.
         */
        public void setValue(String value) {
            this.value = value;
        }
    }
}
