/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax.accuracytests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.service.actions.ajax.serializers.JSONDataSerializer;

/**
 * <p>
 * The accuracy test of the <code>JSONDataSerializer</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JSONDataSerializerAccuracyTest extends TestCase {
    /**
     * <p>
     * Represents the instance of JSONDataSerializer.
     * </p>
     */
    private JSONDataSerializer serializer;

    /**
     * <p>
     * The result template used for test.
     * </p>
     */
    private String resultTemplate = "name = ${action} , result = ${result}";

    /**
     * <p>
     * The error template used for test.
     * </p>
     */
    private String errorTemplate = "name = ${action} , errorMessage = ${error}";

    /**
     * <p>
     * The left bracket around a token.
     * </p>
     */
    private String leftBracket = "{";

    /**
     * <p>
     * The right bracket around a token.
     * </p>
     */
    private String rightBracket = "}";

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        serializer = new JSONDataSerializer();
        if (serializer.getJsonErrorTemplate().indexOf("[") > 0) {
            leftBracket = "[";
            rightBracket = "]";
            resultTemplate = resultTemplate.replace("{", "[");
            resultTemplate = resultTemplate.replace("}", "]");
            errorTemplate = errorTemplate.replace("{", "[");
            errorTemplate = errorTemplate.replace("}", "]");
        }
        serializer.setJsonErrorTemplate(errorTemplate);
        serializer.setJsonResultTemplate(resultTemplate);
    }

    /**
     * <p>
     * Accuracy test of <code>serializeData</code>.
     * </p>
     * <p>
     * Test case : if actionName is null, the ${action} should be replace with a empty string.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Acc_1() throws Exception {
        String result = serializer.serializeData(null, new Object());
        assertEquals("The result is wrong", "name =  , result = {}", result);
    }

    /**
     * <p>
     * Accuracy test of <code>serializeData</code>.
     * </p>
     * <p>
     * Test case : if actionName is not null/empty, the ${action} should be replace with name.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Acc_2() throws Exception {
        String result = serializer.serializeData("acc", new Object());
        assertEquals("The result is wrong", "name = acc , result = {}", result);
    }

    /**
     * <p>
     * Accuracy test of <code>serializeData</code>.
     * </p>
     * <p>
     * Test case : if the data is an exception, the error template should be used.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Acc_3() throws Exception {
        String result = serializer.serializeData("acc", new Exception("some error info"));
        assertEquals("The result is wrong", "name = acc , errorMessage = some error info", result);
    }

    /**
     * <p>
     * Accuracy test of <code>serializeData</code>.
     * </p>
     *
     * <p>
     * Test case : if the data is a list type, it should serialize it correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Acc_5() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("string1");
        list.add("string2");
        String result = serializer.serializeData("acc", list);
        assertEquals("The result is wrong", "name = acc , result = [\"string1\",\"string2\"]", result);
    }

    /**
     * <p>
     * Accuracy test of <code>serializeData</code>.
     * </p>
     *
     * <p>
     * Test case : if the data is a map type, it should serialize it correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Acc_6() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("result_key1", "result_value1");
        map.put("result_key2", "result_value2");
        map.put("result_key3", "result_value3");
        String result = serializer.serializeData("acc", map);
        assertEquals("The result is wrong", "name = acc , result = {\"result_key1\":\"result_value1\","
                + "\"result_key2\":\"result_value2\",\"result_key3\":\"result_value3\"}", result);
    }

    /**
     * <p>
     * Accuracy test of <code>serializeData</code>.
     * </p>
     *
     * <p>
     * Test case : if the data is a string, it should serialize it correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Acc_7() throws Exception {
        String result = serializer.serializeData("acc", "{\"abc\" : 1}");
        assertEquals("The result is wrong", "name = acc , result = {\"abc\":1}", result);
    }

    /**
     * <p>
     * Accuracy test of <code>serializeData</code>.
     * </p>
     *
     * <p>
     * Test case : if the data format is not empty, the token should be used.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Acc_8() throws Exception {
        String template = serializer.getJsonResultTemplate();
        String tokens = "$" + this.leftBracket + "token_1" + this.rightBracket + " " + "$" + this.leftBracket
                + "token_2" + this.rightBracket + " ";
        serializer.setJsonResultTemplate(tokens + template);
        Map<String, String> format = new HashMap<String, String>();
        format.put("token_1", "vaule_1");
        format.put("token_2", "vaule_2");
        serializer.setFormatData(format);
        List<String> list = new ArrayList<String>();
        list.add("string1");
        list.add("string2");
        String result = serializer.serializeData("acc", list);
        assertEquals("The result is wrong.",
                "vaule_1 vaule_2 name = acc , result = [\"string1\",\"string2\"]", result);
    }
}
