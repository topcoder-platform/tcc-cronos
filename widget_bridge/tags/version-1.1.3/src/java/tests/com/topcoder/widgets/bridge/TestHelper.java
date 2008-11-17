/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.widgets.bridge;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.mock.web.MockServletConfig;

import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONObject;
import com.topcoder.json.object.io.JSONDecoder;
import com.topcoder.json.object.io.JSONDecodingException;
import com.topcoder.json.object.io.StandardJSONDecoder;
import com.topcoder.service.project.Project;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestPayload;
import com.topcoder.service.studio.PrizeData;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.UploadedDocument;

/**
 * <p>
 * Helper class for use with Unit test classes.
 * </p>
 * 
 * @author pinoydream
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {
    /**
     * <p>
     * Instantiate a simple json decoder.
     * </p>
     */
    private static final JSONDecoder JSON_DECODER = new StandardJSONDecoder();

    /**
     * <p>
     * Date Formatter for the date strings received from request.
     * </p>
     */
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * <p>
     * Private constructor prevents this class from being instantiated.
     * </p>
     */
    private TestHelper() {
        // do nothing
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'ajaxBridgeConfigFile' with an invalid file.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithInvalidAjaxBridgeParam(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("ajaxBridgeConfigFile", "invalid_file");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'ajaxBridgeConfigFile' with an invalid file.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithInvalid1AjaxBridgeParam(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("ajaxBridgeConfigFile", "ConfigurationFileManager_invalid1.properties");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'ajaxBridgeConfigFile' with an invalid file.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithInvalid2AjaxBridgeParam(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("ajaxBridgeConfigFile", "ConfigurationFileManager_invalid2.properties");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'ajaxBridgeConfigFile' with an empty value.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithEmptyAjaxBridgeParam(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("ajaxBridgeConfigFile", "");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'ajaxBridgeConfigFile' with a null value.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithNullAjaxBridgeParam(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("objectFactoryNamespace", "com.topcoder.widgets.objectfactory");
        servletConfig.addInitParameter("jsonEncoderKey", "jsonEncoder");
        servletConfig.addInitParameter("jsonDecoderKey", "jsonDecoder");
        servletConfig.addInitParameter("projectServiceKey", "projectService");
        servletConfig.addInitParameter("prerequisiteServiceKey", "prerequisiteService");
        servletConfig.addInitParameter("studioServiceKey", "studioService");
        servletConfig.addInitParameter("loggerName", "com.topcoder.widget.AjaxBridgeServlet");
        servletConfig.addInitParameter("fileUploadNamespace", "com.topcoder.servlet.request");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'objectFactoryNamespace' with an invalid file.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithInvalidObjectNS(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("objectFactoryNamespace", "invalid_ns");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'jsonEncoderKey' with an invalid file.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithInvalidEncoderKey(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("jsonEncoderKey", "invalid_encode_key");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'jsonDecoderKey' with an invalid file.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithInvalidDecoderKey(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("jsonDecoderKey", "invalid_decode_key");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'projectServiceKey' with an invalid file.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithInvalidProjectKey(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("projectServiceKey", "invalid_proj_key");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'projectServiceKey' with an empty value.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithEmptyProjectKey(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("projectServiceKey", "");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'prerequisiteServiceKey' with an invalid file.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithInvalidPrerequisiteKey(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("prerequisiteServiceKey", "invalid_prerequisite_key");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'studioServiceKey' with an invalid file.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithInvalidStudioKey(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("studioServiceKey", "invalid_studio_key");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameter 'fileUploadNamespace' with an invalid file.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithInvalidUploadKey(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("fileUploadNamespace", "invalid_ns_key");
    }

    /**
     * <p>
     * Sets the Servlet Config's init parameters with valid values.
     * </p>
     * 
     * @param servletConfig the servlet config to add the init parameter
     */
    public static void initSCWithValidValues(MockServletConfig servletConfig) {
        servletConfig.addInitParameter("ajaxBridgeConfigFile",
            "com/topcoder/configuration/persistence/ConfigurationFileManager.properties");
        servletConfig.addInitParameter("objectFactoryNamespace", "com.topcoder.widgets.objectfactory");
        servletConfig.addInitParameter("jsonEncoderKey", "jsonEncoder");
        servletConfig.addInitParameter("jsonDecoderKey", "jsonDecoder");
        servletConfig.addInitParameter("projectServiceKey", "projectService");
        servletConfig.addInitParameter("prerequisiteServiceKey", "prerequisiteService");
        servletConfig.addInitParameter("studioServiceKey", "studioService");
        servletConfig.addInitParameter("loggerName", "com.topcoder.widget.AjaxBridgeServlet");
        servletConfig.addInitParameter("fileUploadNamespace", "com.topcoder.servlet.request");
    }

    /**
     * <p>
     * Decodes a json response string and converts it to a simple JSONObject.
     * </p>
     * 
     * @param jsonResponse string that contains a json encoded strings
     * @throws Exception when there's a problem encoding the response string
     * @return JSONObject of the json string
     */
    public static JSONObject decodeResponseString(String jsonResponse) throws Exception {
        return JSON_DECODER.decodeObject(jsonResponse);
    }

    /**
     * <p>
     * Gets a valid JSON String equivalent of a Project.
     * </p>
     * 
     * @return json string of a valid Project
     */
    public static String getProjectJSONStringValid() {
        return "{\"projectID\" : 1,\"name\" : \"Project Name\",\"description\" : \"Proj Desc\"}";
    }

    /**
     * <p>
     * Gets a JSON String equivalent of a Project that will cause a project service exception when created.
     * </p>
     * 
     * @return json string of a valid Project
     */
    public static String getProjectJSONStringError() {
        return "{\"projectID\" : 50,\"name\" : \"Project Name\",\"description\" : \"Proj Desc\"}";
    }

    /**
     * <p>
     * Gets a valid JSON String equivalent of a Uploaded Document.
     * </p>
     * 
     * @return json string of a valid Uploaded Document
     */
    public static String getUploadDocJSONStringValid() {
        return "{\"fileName\" : \"JSON file name\",\"path\":\"filePath\",\"documentID\" : 1,\"contestID\" : 77777,"
            + " \"description\" : \"JSON descrip\"}";
    }

    /**
     * <p>
     * Gets a JSON String equivalent of a Uploaded Document that will cause a studio service exception when created.
     * </p>
     * 
     * @return json string of a valid Uploaded Document
     */
    public static String getUploadDocJSONStringError() {
        return "{\"fileName\" : \"JSON file name\",\"documentID\" : 50,\"contestID\" : 77777,"
            + " \"description\" : \"JSON descrip\"}";
    }

    /**
     * <p>
     * Gets a valid JSON String equivalent of a Submission.
     * </p>
     * 
     * @return json string of a valid Uploaded Document
     */
    public static String getSubmissionJSONStringValid() {
        return "{ \"submissionID\" : 1, \"submitterID\" : 321, \"submissionTimeStamp\" : \"2008-03-04 09:00\" ,"
            + " \"submissionContent\" : \"content\" , \"contestID\" : 99,"
            + " \"passedScreening\" : true,\"passedReview\" : true, \"placement\" "
            + ": 3, \"paidFor\" : true, \"price\" : 88, \"markedForPurchase\" : true, \"removed\" : true}";
    }

    /**
     * <p>
     * Gets a JSON String equivalent of a Submission that will cause a studio service exception when created.
     * </p>
     * 
     * @return json string of a valid Uploaded Document
     */
    public static String getSubmissionJSONStringError() {
        return "{ \"submissionID\" : 50, \"submitterID\" : 321, \"submissionTimeStamp\" : \"2008-03-04 09:00\" ,"
            + " \"submissionContent\" : \"content\" , \"contestID\" : 9999, \"passedScreening\" : true, \"placement\" "
            + ": 3, \"paidFor\" : true, \"price\" : 4443332, \"markedForPurchase\" : true, \"removed\" : true}";
    }

    /**
     * <p>
     * Gets a valid JSON String parameters.
     * </p>
     * 
     * @return json string of valid parameters
     */
    public static String getParametersJSONStringValid() {
        return "[\"param1\",\"param2\",\"param3\"]";
    }

    /**
     * <p>
     * Gets an invalid JSON String parameters.
     * </p>
     * 
     * @return json string of invalid parameters
     */
    public static String getParametersJSONStringError() {
        return "{ 1234,321,3123}";
    }

    /**
     * <p>
     * Gets a valid JSON String equivalent of a Contest.
     * </p>
     * 
     * @return json string of a valid Contest
     */
    public static String getContestJSONStringValid() {
        return "{\"contestID\" : 1, \"projectID\" : 1, \"name\" : \"contest name\", \"shortSummary\" : "
            + "\"short summary\",\"prizes\" : [{\"place\" : 1,\"amount\" : 20000},{\"place\" : 2,\"amount\" : 10000}],"
            + " \"launchDateAndTime\" : \"2008-03-07 01:00\",\"durationInHours\" : 3, \"winnerAnnouncementDeadline\" : "
            + "\"2008-03-20 01:00\", \"contestTypeID\" : 1,\"finalFileFormatList\" : [\"format 1\",\"format2\","
            + "\"format3\""
            + "],\"finalFileFormatOther\" : \"zip\", \"documentationUploads\" : [{\"fileName\" : \"JSON file name1\","
            + "\"path\":\"filePath\",\"documentID\" : 321321,\"contestID\" : 77777, \"description\" : \"JSON descrip1\"},{\"fileName\" :"
            + " \"JSON"
            + " file name2\",\"path\":\"filePath\",\"documentID\" : 321321,\"contestID\" : 77777, \"description\" : \"JSON descrip2\"}], "
            + "\"statusID\" : 3221123, \"contestPayloads\" : [{\"name\" : \"JSON Payload Name1\",\"value\" : "
            + " \"JSON Payload Value1\",\"description\" : \"JSON Payload Desc1\",\"required\" : true, \"contestTypeID\""
            + " : " + "4444},{\"name\" : \"JSON Payload Name2\",\"value\" : \"JSON Payload Value2\",\"description\" : "
            + "\"JSON Payload Desc2\",\"required\" : true,\"contestTypeID\" : 4444}], \"contestCategoryID\" : 2,"
            + " \"contestDescriptionAndRequirements\" : \"This is a long desc\", \"requiredOrRestrictedColors\" :"
            + " \"red\", \"requiredOrRestrictedFonts\" : \"arial\", \"sizeRequirements\" : \"11 pt.\", "
            + "\"otherRequirementsOrRestrictions\" : \"other reqs\", \"tcDirectProjectID\" : 1000, \"creatorUserID\""
            + " : 7000}";
    }

    /**
     * <p>
     * Gets a JSON String equivalent of a Contest that will cause a studio service exception when created.
     * </p>
     * 
     * @return json string of a valid Contest
     */
    public static String getContestJSONStringError() {
        return "{\"contestID\" : 50, \"projectID\" : 50, \"name\" : \"contest name\", \"shortSummary\" : "
            + "\"short summary\",\"prizes\" : [{\"place\" : 1,\"amount\" : 20000},{\"place\" : 2,\"amount\" : 10000}],"
            + " \"launchDateAndTime\" : \"2008-03-07 01:00\",\"durationInHours\" : 3, \"winnerAnnouncementDeadline\" : "
            + "\"2008-03-20 01:00\", \"contestTypeID\" : 1,\"finalFileFormatList\" : [\"format 1\",\"format2\","
            + "\"format3\""
            + "],\"finalFileFormatOther\" : \"zip\", \"documentationUploads\" : [{\"fileName\" : \"JSON file name1\","
            + "\"documentID\" : 321321,\"contestID\" : 77777, \"description\" : \"JSON descrip1\"},{\"fileName\" :"
            + " \"JSON"
            + " file name2\",\"documentID\" : 321321,\"contestID\" : 77777, \"description\" : \"JSON descrip2\"}], "
            + "\"statusID\" : 3221123, \"contestPayloads\" : [{\"name\" : \"JSON Payload Name1\",\"value\" : "
            + " \"JSON Payload Value1\",\"description\" : \"JSON Payload Desc1\",\"required\" : true, "
            + "\"contestTypeID\" : "
            + "4444},{\"name\" : \"JSON Payload Name2\",\"value\" : \"JSON Payload Value2\",\"description\" : "
            + "\"JSON Payload Desc2\",\"required\" : true,\"contestTypeID\" : 4444}], \"contestCategoryID\" : 2,"
            + " \"contestDescriptionAndRequirements\" : \"This is a long desc\", \"requiredOrRestrictedColors\" :"
            + " \"red\", \"requiredOrRestrictedFonts\" : \"arial\", \"sizeRequirements\" : \"11 pt.\", "
            + "\"otherRequirementsOrRestrictions\" : \"other reqs\", \"tcDirectProjectID\" : 1000, "
            + "\"creatorUserID\" : 7000}";
    }

    /**
     * <p>
     * Gets a valid JSON String equivalent of a PrerequisiteDocument.
     * </p>
     * 
     * @return json string of a valid PrerequisiteDocument
     */
    public static String getPrerequisiteDocumentJSONStringValid() {
        return "{ \"documentID\": 1,\"version\": 1,\"versionDate\":\"2008-05-24 09:00\",\"name\":\"document name\","
            + "\"content\":\"document content\" }";
    }

    /**
     * <p>
     * Gets a invalid JSON String of a PrerequisiteDocument.
     * </p>
     * 
     * @return json string of a invalid PrerequisiteDocument
     */
    public static String getPrerequisiteDocumentJSONStringError() {
        return "{ \"documentID\": 1,\"version\": 1,\"versionDate\":\"2008-05-24 09:00\",\"name\":\"document name\","
            + "\"content\":1 }";
    }

    /**
     * <p>
     * Convenience method in getting a Project object from a JSONObject.
     * </p>
     * 
     * @param jsonProj the JSONObject where the values will be coming from
     * @return the project object created from this json object
     */
    public static ProjectData getProjectFromJSON(JSONObject jsonProj) {
        // initialize the project using the JSON object
        ProjectData project = new Project();
        project.setProjectId(jsonProj.getLong("projectID"));
        project.setName(jsonProj.getString("name"));
        project.setDescription(jsonProj.getString("description"));

        return project;
    }

    /**
     * <p>
     * Convenience method in getting a Contest object from a JSONObject.
     * </p>
     * 
     * @param jsonContest the JSONObject where the values will be coming from
     * @return the project object created from this json object
     * @throws ParseException when an error occurs while parsing the date
     * @throws JSONDecodingException when an error occurs while decoding a json string
     */
    public static ContestData getContestFromJSON(JSONObject jsonContest) throws ParseException, JSONDecodingException {
        // initialize the contest using the JSON object
        ContestData contest = new ContestData();
        // set simple types
        contest.setContestId(jsonContest.getLong("contestID"));
        contest.setProjectId(jsonContest.getLong("projectID"));
        contest.setName(jsonContest.getString("name"));
        contest.setShortSummary(jsonContest.getString("shortSummary"));
        contest.setDurationInHours(jsonContest.getInt("durationInHours"));
        contest.setOtherFileFormats(jsonContest.getString("finalFileFormatOther"));
        // It needs to be fixed to add a contest status id in ContestData class
        // contest.setStatusId(jsonContest.getLong("statusID"));
        contest.setContestTypeId(jsonContest.getLong("contestTypeID"));
        contest.setContestDescriptionAndRequirements(jsonContest.getString("contestDescriptionAndRequirements"));
        contest.setRequiredOrRestrictedColors(jsonContest.getString("requiredOrRestrictedColors"));
        contest.setRequiredOrRestrictedFonts(jsonContest.getString("requiredOrRestrictedFonts"));
        contest.setSizeRequirements(jsonContest.getString("sizeRequirements"));
        contest.setOtherRequirementsOrRestrictions(jsonContest.getString("otherRequirementsOrRestrictions"));
        contest.setTcDirectProjectId(jsonContest.getLong("tcDirectProjectID"));
        contest.setCreatorUserId(jsonContest.getLong("creatorUserID"));
        contest.setLaunchDateAndTime(getXMLGregorianCalendar(jsonContest.getString("launchDateAndTime")));
        contest.setWinnerAnnoucementDeadline(getXMLGregorianCalendar(jsonContest
            .getString("winnerAnnouncementDeadline")));
        contest.setSubmissionCount(jsonContest.getLong("submissionCount"));
        // no contestTypeID available in Contest.java but architect specified to add this
        // jsonContest.getLong("contestTypeID");

        // set up ARRAYS
        // Prizes[]
        // get the JSONArray of prizes
        JSONArray jsonPrizes = jsonContest.getArray("prizes");
        // check if it is null
        if (jsonPrizes != null) {
            // we get the contents as Array of Objects
            Object[] prizes = jsonPrizes.getObjects();
            // we initialize a list for the prizes which will be converted later into a Set
            List<PrizeData> listOfPrizes = new ArrayList<PrizeData>();
            if (prizes != null) {
                for (int i = 0; i < prizes.length; i++) {
                    // get the Prize object from the JSONObject
                    listOfPrizes.add(getPrizeFromJSON((JSONObject) prizes[i]));
                }
            }
            // set the Set of prizes
            contest.setPrizes(listOfPrizes);
        }

        // final file format list
        // get the JSONArray of Final File Format List
        JSONArray jsonFileFormat = jsonContest.getArray("finalFileFormatList");
        // check if it is null
        if (jsonFileFormat != null) {
            // we get the contents as Array of Objects
            Object[] fileformat = jsonFileFormat.getObjects();
            // we initialize a list for the file formats which will be converted later into a Set
            List<String> fileFormats = new ArrayList<String>();
            if (fileformat != null) {
                for (int i = 0; i < fileformat.length; i++) {
                    // get the Uploaded Document object from the JSONObject
                    fileFormats.add((String) fileformat[i]);
                }
            }
            // set the Set of uploaded documents
            contest.setFinalFileFormat(joinStrings(fileFormats));
        }

        // document uploads
        // get the JSONArray of Uploaded Document
        JSONArray jsonUpDoc = jsonContest.getArray("documentationUploads");
        // check if it is null
        if (jsonUpDoc != null) {
            // we get the contents as Array of Objects
            Object[] updocs = jsonUpDoc.getObjects();
            // we initialize a list for the uploaded documents which will be converted later into a Set
            List<UploadedDocument> listOfUpDocs = new ArrayList<UploadedDocument>();
            if (updocs != null) {
                for (int i = 0; i < updocs.length; i++) {
                    // get the Uploaded Document object from the JSONObject
                    listOfUpDocs.add(getDocumentUploadsFromJSON((JSONObject) updocs[i]));
                }
            }
            // set the Set of uploaded documents
            contest.setDocumentationUploads(listOfUpDocs);
        }

        // contest payloads
        // get the JSONArray of Contest Payloads
        JSONArray jsonPayloads = jsonContest.getArray("contestPayloads");
        // check if it is null
        if (jsonPayloads != null) {
            // we get the contents as Array of Objects
            Object[] payloads = jsonPayloads.getObjects();
            // we initialize a list for the payloads which will be converted later into a Set
            List<ContestPayload> listOfPayloads = new ArrayList<ContestPayload>();
            if (payloads != null) {
                for (int i = 0; i < payloads.length; i++) {
                    // get the contest payload object from the JSONObject
                    listOfPayloads.add(getContestPayloadFromJSON((JSONObject) payloads[i]));
                }
            }
            // set the Set of contest payload
            contest.setContestPayloads(listOfPayloads);
        }

        return contest;
    }

    /**
     * <p>
     * Convenience method in getting a Submission object from a JSONObject.
     * </p>
     * 
     * @param jsonSubmission the JSONObject where the values will be coming from
     * @return the Submission object created from this json object
     * @throws ParseException when an exception occurs while parsing the date
     */
    public static SubmissionData getSubmissionFromJSON(JSONObject jsonSubmission) throws ParseException {
        // initialize the Submission using the JSON object
        // initialize the Submission using the JSON object
        SubmissionData submission = new SubmissionData();
        submission.setSubmissionId(jsonSubmission.getLong("submissionID"));
        submission.setSubmitterId(jsonSubmission.getLong("submitterID"));
        submission.setSubmittedDate(getXMLGregorianCalendar(jsonSubmission.getString("submissionTimeStamp")));
        submission.setSubmissionContent(jsonSubmission.getString("submissionContent"));
        submission.setContestId(jsonSubmission.getLong("contestID"));
        submission.setPassedScreening(new Boolean(jsonSubmission.getBoolean("passedScreening")));
        submission.setPlacement(jsonSubmission.getInt("placement"));
        submission.setPaidFor(jsonSubmission.getBoolean("paidFor"));
        submission.setPrice(jsonSubmission.getDouble("price"));
        submission.setMarkedForPurchase(jsonSubmission.getBoolean("markedForPurchase"));
        submission.setPassedScreening(jsonSubmission.getBoolean("passedReview"));

        return submission;
    }

    /**
     * <p>
     * Convenience method in getting a Prize object from a JSONObject.
     * </p>
     * 
     * @param jsonPrize the JSONObject where the values will be coming from
     * @return the prize object created from this json object
     */
    private static PrizeData getPrizeFromJSON(JSONObject jsonPrize) {
        PrizeData prize = new PrizeData();
        prize.setPlace(jsonPrize.getInt("place"));
        prize.setAmount(jsonPrize.getDouble("amount"));

        return prize;
    }

    /**
     * <p>
     * Convenience method in getting a UploadedDocument object from a JSONObject.
     * </p>
     * 
     * @param jsonUpDoc the JSONObject where the values will be coming from
     * @return the uploaded document object created from this json object
     */
    private static UploadedDocument getDocumentUploadsFromJSON(JSONObject jsonUpDoc) {
        UploadedDocument upDoc = new UploadedDocument();
        upDoc.setContestId(jsonUpDoc.getLong("contestID"));
        upDoc.setDescription(jsonUpDoc.getString("description"));
        upDoc.setDocumentId(jsonUpDoc.getLong("documentID"));
        upDoc.setFileName(jsonUpDoc.getString("fileName"));
        upDoc.setPath(jsonUpDoc.getString("path"));

        return upDoc;
    }

    /**
     * <p>
     * Convenience method in getting a Contest Payload object from a JSONObject.
     * </p>
     * 
     * @param jsonPayload the JSONObject where the values will be coming from
     * @return the contest payload object created from this json object
     */
    private static ContestPayload getContestPayloadFromJSON(JSONObject jsonPayload) {
        ContestPayload payload = new ContestPayload();
        payload.setName(jsonPayload.getString("name"));
        payload.setValue(jsonPayload.getString("value"));
        payload.setRequired(jsonPayload.getBoolean("required"));

        return payload;
    }

    /**
     * <p>
     * Reads the contents of an input stream into a byte[].
     * </p>
     * 
     * @param inputStream where the data will come from
     * @return byte array equivalent of the contents from the input stream
     * @throws IOException where there's a problem reading the input stream
     */
    public static byte[] readContent(InputStream inputStream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        while ((len = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }

        inputStream.close();
        output.close();

        return output.toByteArray();

    }

    /**
     * <p>
     * Join strings together as comma deliminated string.
     * </p>
     * 
     * @param strings string list to be joined
     * @return concatenated string
     */
    private static String joinStrings(List<String> strings) {
        if (strings == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string).append(",");
        }

        // remove last ,
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    /**
     * <p>
     * Converts date string into XMLGregorianCalendar instance. Returns null if parameter is null or empty.
     * </p>
     * 
     * @param dateString Date string to convert
     * @return converted calendar instance
     * @throws ParseException if any parse error
     */
    public static XMLGregorianCalendar getXMLGregorianCalendar(String dateString) throws ParseException {
        if (dateString == null || dateString.trim().length() == 0) {
            return null;
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(DATE_FORMATTER.parse(dateString));
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            // can't create calendar, return null
            return null;
        }
    }

    /**
     * <p>
     * Converts XMLGregorianCalendar date into standard java Date object. Returns empty string if argument is null.
     * </p>
     * 
     * @param calendar calendar instance to convert
     * @return converted date string
     */
    public static String getDateString(XMLGregorianCalendar calendar) {
        return calendar == null ? "" : DATE_FORMATTER.format(calendar.toGregorianCalendar().getTime());
    }
}
