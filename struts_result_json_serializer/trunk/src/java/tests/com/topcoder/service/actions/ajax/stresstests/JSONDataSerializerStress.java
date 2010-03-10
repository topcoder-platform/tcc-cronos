/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax.stresstests;

import com.topcoder.service.actions.ajax.serializers.JSONDataSerializer;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


/**
 * Stress tests for <code>JSONDataSerializer</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JSONDataSerializerStress extends TestCase {
    /**
     * The data to be serialized.
     */
    private List<DataClass> data;

    /**
     * The expected json string.
     */
    private String token = "{\"name\":\"p1\",\"value\":\"d1\"}";

    /**
     * Set up the environment.
     */
    protected void setUp() {
        data = new ArrayList<DataClass>();

        DataClass p = new DataClass();
        p.setName("p1");
        p.setValue("d1");

        data.add(p);
    }

    /**
     * Stress test for <code>serializeData()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testSerializeDataStress() throws Exception {
        String expected = token;

        for (int i = 0; i < 1000; i++) {
            JSONDataSerializer js = new JSONDataSerializer();
            String result = js.serializeData("action", data);

            assertTrue("The data should be serialized successfully.",
                result.contains(expected));

            // Add another Project in data.
            DataClass p = new DataClass();
            p.setName("p1");
            p.setValue("d1");

            data.add(p);

            // the json result should be added.
            expected += ("," + token);
        }
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
