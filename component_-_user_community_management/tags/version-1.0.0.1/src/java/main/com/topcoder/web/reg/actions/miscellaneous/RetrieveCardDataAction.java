/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.web.common.TCWebException;

/**
 * This is a Struts action that is responsible for retrieving data that is used for rendering TopCoder member card.
 * This action retrieves member card data from persistence. This action generates response directly without using JSP.
 * The injected DataAccessInt instance for this action must represent "topcoder_dw" database.
 * <p>
 * Thread Safety:
 * This class is mutable and not thread safe. But it will be used in thread safe manner by Struts. It's assumed that
 * request scope will be set up for all Struts actions in Spring configuration, thus actions will be accessed from a
 * single thread only.
 *
 * @author saarixx, mumujava
 * @version 1.0
 */
public class RetrieveCardDataAction extends BaseDataAccessUserCommunityManagementAction {
    /**
    * <p>
    * An empty string.
    * </p>
    */
    private static final String EMPTY = "";

    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = 5844783570794262897L;

    /**
     * The ID of the coder whose member card data must be retrieved. This is an input field of the Struts action, it
     * is expected to be injected by Struts using the data from request. This attribute is validated using Struts XML
     * validation, thus it is expected to be positive after insertion. Has a setter. Is used in execute().
     */
    private long coderId;

    /**
     * The stream with XML member card data to be used by Struts for generating HTTP response. Is initialized in
     * execute(). Cannot be null after initialization. Has a getter.
     */
    private InputStream xmlStream;

    /**
     * Creates an instance of RetrieveCardDataAction.
     */
    public RetrieveCardDataAction() {
    }

    /**
     * Executes the Struts action. This method retrieves user data from the database and then constructs XML feed
     * using these data. This XML feed is then returned by Struts as HTTP response content.
     * @throws TCWebException if any error occurred
     * @return SUCCESS to indicate that the operation was successful
     */
    @Override
    public String execute() throws TCWebException {
        final String methodSignature = "RetrieveCardDataAction.execute";
        LoggingWrapperUtility.logEntrance(getLog(), methodSignature, new String[]{"coderId"},
                new Object[]{coderId});
        try {
            //Get user ID:
            long userId = getUserId();
            if (userId != coderId) {
                //Check whether member card is unlocked:
                if (!CardHelper.isUnlocked(coderId)) {
                    throw new TCWebException("User has not unlocked their card.");
                }
            }
            try {
                //Create byte array output stream:
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                TransformerHandler hd = getTransformerHandler(outputStream);
                // Get data access instance to be used:
                DataAccessInt dataAccess = getDataAccess();
                //Create request instance:
                Request profileRequest = new Request();
                //Set content handle to "card_profile_info":
                profileRequest.setContentHandle("card_profile_info");
                //Set coder ID to the request:
                profileRequest.setProperty("cr", String.valueOf(coderId));
                //Perform profile request:
                Map<String, ResultSetContainer> data = dataAccess.getData(profileRequest);
                //Get profile info from the obtained data:
                ResultSetContainer profileRsc = data.get("card_profile_info");
                //Create rating distribution graph request:
                Request distRequest = new Request();
                //Set content handle to "rating_distribution_graph":
                distRequest.setContentHandle("rating_distribution_graph");
                //Perform rating distribution graph request:
                data = dataAccess.getData(distRequest);
                //Get result set from the obtained data:
                ResultSetContainer distRsc = data.get("Rating_Distribution_Graph");
                //Create empty attributes list:
                Attributes emptyAttributes = new AttributesImpl();
                addElements(hd, profileRsc, distRsc, emptyAttributes);
                //Close <algorithmRatingDistribution> element:
                hd.endElement(EMPTY, EMPTY, "algorithmRatingDistribution");
                // Close <memberStats> element:
                hd.endElement(EMPTY, EMPTY, "memberStats");
                // Write the end of XML document:
                hd.endDocument();
                //Get output as byte array:
                byte[] xmlData = outputStream.toByteArray();
                //Create input stream from the byte array
                xmlStream = new ByteArrayInputStream(xmlData);
            } catch (SAXException e) {
                throw new TCWebException("SAXException occurs", e);
            } catch (TransformerConfigurationException e) {
                throw new TCWebException("TransformerConfigurationException occurs", e);
            } catch (Exception e) {
                throw new TCWebException("Failed to access the member profile", e);
            }
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new TCWebException("Invalid coder id", e));
        } catch (TCWebException e) {
            throw LoggingWrapperUtility.logException(getLog(), methodSignature, e);
        }
        LoggingWrapperUtility.logExit(getLog(), methodSignature, new Object[]{xmlStream});
        return SUCCESS;
    }
    /**
     * Adds the elements.
     *
     * @param hd the transformer handler
     * @param profileRsc the profile resultset
     * @param distRsc the result container
     * @param emptyAttributes the attributes of XML element
     * @throws SAXException if any sax error occurs.
     */
    private void addElements(TransformerHandler hd,
        ResultSetContainer profileRsc, ResultSetContainer distRsc,
        Attributes emptyAttributes) throws SAXException {
        //Start <memberStats> XML element:
        hd.startElement(EMPTY, EMPTY, "memberStats", emptyAttributes);
        //Create date format:
        DateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
        //Create decimal format:
        DecimalFormat df = new DecimalFormat("0.00");
        //Get coder handle from the result set:
        String handle = profileRsc.getStringItem(0, "handle");
        //Write handle to XML element:
        addElement(hd, "handle", handle, emptyAttributes);
        //Repeat for other parameters:
        addElement(hd, "photo", profileRsc.getStringItem(0, "image_path"), emptyAttributes);
        addElement(hd, "algorithmRating", profileRsc.getStringItem(0, "algorithm_rating"),
            emptyAttributes);
        addElement(hd, "algorithmRatingMax", profileRsc.getStringItem(0, "highest_rating"),
            emptyAttributes);
        addElement(hd, "rank", profileRsc.getStringItem(0, "rank"), emptyAttributes);
        addDoubleElement(hd, "percentile", profileRsc, emptyAttributes, df);
        addDateElement(profileRsc, "member_since", "memberSince", hd, emptyAttributes, sdf);
        addDateElement(profileRsc, "last_match", "lastMatchDate", hd, emptyAttributes, sdf);
        addElement(hd, "bestDiv1", profileRsc.getStringItem(0, "best_div1"), emptyAttributes);
        addElement(hd, "bestDiv2", profileRsc.getStringItem(0, "best_div2"), emptyAttributes);
        addElement(hd, "competitions", profileRsc.getStringItem(0, "num_competitions"),
            emptyAttributes);
        addElement(hd, "highSchoolRating", profileRsc.getStringItem(0, "high_school_rating"),
            emptyAttributes);
        addElement(hd, "marathonRating", profileRsc.getStringItem(0, "marathon_rating"),
            emptyAttributes);
        addElement(hd, "designRating", profileRsc.getStringItem(0, "design_rating"), emptyAttributes);
        addElement(hd, "developmentRating", profileRsc.getStringItem(0, "development_rating"),
            emptyAttributes);
        addElement(hd, "conceptualizationRating", profileRsc.getStringItem(0,
            "conceptualization_rating"), emptyAttributes);
        addElement(hd, "specificationRating", profileRsc.getStringItem(0, "specification_rating"),
            emptyAttributes);
        addElement(hd, "architectureRating", profileRsc.getStringItem(0, "architecture_rating"),
            emptyAttributes);
        addElement(hd, "assemblyRating", profileRsc.getStringItem(0, "assembly_rating"),
            emptyAttributes);
        addElement(hd, "testSuitesRating", profileRsc.getStringItem(0, "test_suites_rating"),
            emptyAttributes);
        addElement(hd, "testScenariosRating", profileRsc.getStringItem(0, "test_scenarios_rating"),
            emptyAttributes);
        addElement(hd, "uiPrototypeRating", profileRsc.getStringItem(0, "ui_prototype_rating"),
            emptyAttributes);
        addElement(hd, "riaBuildRating", profileRsc.getStringItem(0, "ria_build_rating"),
            emptyAttributes);
        //Start <algorithmRatingDistribution> element:
        hd.startElement(EMPTY, EMPTY, "algorithmRatingDistribution", emptyAttributes);
        for (int i = 0; i < distRsc.getColumnCount(); i++) {
            //Get the next bucket data from the result set:
            String bucket = distRsc.getStringItem(0, i);
            //Add bucket data to XML output:
            addElement(hd, "bucket", bucket, emptyAttributes);
        }
    }
    /**
     * Builds the transformer handler.
     *
     * @param outputStream the output stream
     * @throws TransformerFactoryConfigurationError if any configuration error occurs in the factory
     * @throws TransformerConfigurationException if any configuration error occurs in the transformer
     * @throws SAXException if any sax error occurs.
     * @return the transformer handler
     */
    private TransformerHandler getTransformerHandler(
        ByteArrayOutputStream outputStream)
        throws TransformerConfigurationException, SAXException {
        //Create stream result instance:
        StreamResult streamResult = new StreamResult(outputStream);
        //Create an instance of SAXTransformerFactory:
        SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        // Create a transformer handler:
        TransformerHandler hd = tf.newTransformerHandler();
        // Get serializer associated with the transformer:
        Transformer serializer = hd.getTransformer();
        //Set output encoding to ISO-8859-1:
        serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        //Disable the indentation:
        serializer.setOutputProperty(OutputKeys.INDENT, "no");
        //Set stream result to the transformer:
        hd.setResult(streamResult);
        //Start XML document:
        hd.startDocument();
        return hd;
    }
    /**
     * Adds a double element.
     *
     * @param profileRsc the profile resultset
     * @param itemName the item name
     * @param hd the transformer handler
     * @param emptyAttributes the attributes of XML element
     * @param df the double format
     * @throws SAXException if any sax error occurs.
     */
    private void addDoubleElement(TransformerHandler hd, String itemName,
        ResultSetContainer profileRsc, Attributes emptyAttributes,
        DecimalFormat df) throws SAXException {
        if (profileRsc.getItem(0, itemName).getResultData() == null) {
            addElement(hd, itemName, null, emptyAttributes);
        } else {
            addElement(hd, itemName, df.format(profileRsc.getDoubleItem(0, itemName)),
                emptyAttributes);
        }
    }
    /**
     * Adds a date element.
     *
     * @param profileRsc the profile resultset
     * @param itemName the item name
     * @param elementName the element name
     * @param hd the transformer handler
     * @param emptyAttributes the attributes of XML element
     * @param sdf the date format
     * @throws SAXException if any sax error occurs.
     */
    private void addDateElement(ResultSetContainer profileRsc, String itemName,
        String elementName, TransformerHandler hd, Attributes emptyAttributes, DateFormat sdf) throws SAXException {
        if (profileRsc.getItem(0, itemName).getResultData() == null) {
            addElement(hd, "memberSince", null, emptyAttributes);
        } else {
            addElement(hd, elementName, sdf.format(profileRsc.getItem(0, itemName)
                .getResultData()), emptyAttributes);
        }
}

    /**
     * Add an XML element to the specified transformed handler.
     *
     * @param name the name of XML element
     * @param transformerHandler the transformer handler
     * @param value the text value of XML element
     * @param attributes the attributes of XML element
     * @throws SAXException if any sax error occurs.
     */
    private static void addElement(TransformerHandler transformerHandler, String name, String value,
            Attributes attributes) throws SAXException {
        String temp = value == null ? EMPTY : value;
        // Write opening XML tag:
        transformerHandler.startElement(EMPTY, EMPTY, name, attributes);
        //Write XML element content:
        transformerHandler.characters(temp.toCharArray(), 0, temp.length());
        //Write closing XML tag:
        transformerHandler.endElement(EMPTY, EMPTY, name);
    }

    /**
     * Sets the ID of the coder whose member card data must be retrieved.
     *
     * @param coderId the ID of the coder whose member card data must be retrieved
     */
    public void setCoderId(long coderId) {
        this.coderId = coderId;
    }

    /**
     * Retrieves the stream with XML member card data to be used by Struts for generating HTTP response.
     *
     * @return the stream with XML member card data to be used by Struts for generating HTTP response
     */
    public InputStream getXmlStream() {
        return xmlStream;
    }
}
