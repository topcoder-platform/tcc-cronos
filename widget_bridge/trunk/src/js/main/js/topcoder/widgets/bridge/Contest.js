/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

/**
 * <p>This Java Script object represents the Contest data.
 *
 * Thread safety:  Mutable and not thread-safe.</p>
 *
 * @author Standlove, pinoydream
 * @version 1.0
 */

/**
 * <p>Construct the js object with the json object.
 * 
 * Set json's properties to the corresponding properties of this js object.</p>
 *
 * <p>If this constructor is called without a parameter, it serves as an empty default constructor.</p>
 */
js.topcoder.widgets.bridge.Contest = function (/* JSON Object */ json) {
	/**
	 * This is to make the object available to private methods.
	 */
	var that = this;

	/**
	 * <p>Represents the contest's ID</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or  -1</p>
	 */
	var contestID /* long */ = -1;

	/**
	 * <p>Represents the project's ID</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values:  must be greater or equal than 0 or -1 (not set)</p>
	 */
    var projectID /* long */ = -1;

	/**
	 * <p>Represents the contest's name.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var name/* String */ = null;

	/**
	 * <p>Represents the short summary of the contest.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var shortSummary /* String */ = null;

	/**
	 * <p>Represents the contest prizes.</p>
	 * <p>Initial Value: empty array; it's final , it's empty at beginning</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values:  it's not null, can be empty, can't contain null values</p>
	 */
    var prizes /* Prize[] */ = new Array();

	/**
	 * <p>Represents the launch date and time of the contest.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var launchDateAndTime /* Date */ = null;

	/**
	 * <p>Represents the duration in hours of the contest.</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values:  must be greater or equal than 0 or -1 (not set)</p>
	 */
    var durationInHours /* int */ = -1;

	/**
	 * <p>Represents the winner announcment deadline.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var winnerAnnouncementDeadline /* Date */ = null;

	/**
	 * <p>Represents the contest type ID.</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values:  must be greater or equal than 0 or -1 (not set)</p>
	 */
	var contestTypeID /* long */ = -1;

	/**
	 * <p>Represents the contest Channel ID.</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values:  must be greater or equal than 0 or -1 (not set)</p>
	 */
	var contestChannelID /* long */ = -1;
	
	/**
	 * <p>Represents the final file format list.</p>
	 * <p>Initial Value: empty array; it's final , it's empty at beginning</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values:  it's not null, can be empty, can't contain null values</p>
	 */
    var finalFileFormatList /* String[] */ = new Array();

	/**
	 * <p>Represents the final file format other.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var finalFileFormatOther /* String */ = null;

	/**
	 * <p>Represents the the documentationUpload set of contest.</p>
	 * <p>Initial Value: empty array; it's final , it's empty at beginning</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values:  it's not null, can be empty, can't contain null values</p>
	 */
    var documentationUploads /* UploadedDocument[] */ = new Array();

	/**
	 * <p>Represents the status' ID</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values:  must be greater or equal than 0 or -1 (not set)</p>
	 */
    var statusID /* long */ = -1;

	/**
	 * <p>Represents the contest payload set.</p>
	 * <p>Initial Value: empty array; it's final , it's empty at beginning</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values:  it's not null, can be empty, can't contain null values</p>
	 */
    var contestPayloads /* ContestPayload[] */ = new Array();

	/**
	 * <p>Represents the contest Description And Requirements It is added in the CID because it exists in the WSDL.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var contestDescriptionAndRequirements /* String */ = null;

	/**
	 * <p>Represents the required Or Restricted Colors.  It is added in the CID because it exists in the WSDL.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var requiredOrRestrictedColors /* String */ = null;

	/**
	 * <p>Represents the required Or Restricted Fonts.  It is added in the CID because it exists in the WSDL.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var requiredOrRestrictedFonts /* String */ = null;

	/**
	 * <p>Represents the size Requirements.  It is added in the CID because it exists in the WSDL.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var sizeRequirements /* String */ = null;

	/**
	 * <p>Represents the other Requirements Or Restrictions.  It is added in the CID because it exists in the WSDL.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var otherRequirementsOrRestrictions /* String */ = null;

	/**
	 * <p>Represents the Eligibility .</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var eligibility /* String */ = null;

	/**
	 * <p>Represents the Notes on Winner Selection.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var notesOnWinnerSelection /* String */ = null;

	/**
	 * <p>Represents the Prize Description.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var prizeDescription /* String */ = null;
    
	/**
	 * <p>Represents the tc Direct Project Id.  It is added in the CID because it exists in the WSDL.</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or  -1</p>
	 */
    var tcDirectProjectID /* long */ = -1;

	/**
	 * <p>Represents the creator User Id.  It is added in the CID because it exists in the WSDL.</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or  -1</p>
	 */
    var creatorUserID /* long */ = -1;

	/**
	 * <p>Represents the number of registrants.  It is added in the CID because it exists in the WSDL.</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or  -1</p>
	 */
    var numberOfRegistrants /* long */ = -1;

	/**
	 * <p>Represents the opinion requires preview file.  It is added in the CID because it exists in the WSDL.</p>
	 * <p>Initial Value: false, means not required</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be true or false.</p>
	 */
    var requiresPreviewFile /* bool */ = false;

	/**
	 * <p>Represents the opinion requires preview image.  It is added in the CID because it exists in the WSDL.</p>
	 * <p>Initial Value: false, means not required</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be true or false.</p>
	 */
    var requiresPreviewImage /* bool */ = false;

	/**
	 * <p>Represents the maximum submissions. It is added in the CID because it exists in the WSDL.</p>
	 * <p>Initial Value: -1, means not set.</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be equal to or greater than -1.</p>
	 */
    var maximumSubmissions /* long */ = -1;

	/**
	 * <p>Represents the the media of contest.</p>
	 * <p>Initial Value: empty array; it's final , it's empty at beginning</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values:  it's not null, can be empty, can't contain null values</p>
	 */
    var media /* Medium[] */ = new Array();
    
    /**
     * Abbreviation for Helper class.
     */
    var Helper = js.topcoder.widgets.bridge.Helper;

	/**
	 * <p>Construct the js object with the json object.
	 * Set json's properties to the corresponding properties of this js object.
	 *
	 * Note: we can't throw IllegalArgumentException here since we will
	 * support an empty Constructor for this class.
	 *
	 * Note: Setter Methods can't actually be used here since it doesn't
	 * retain the values of the parameters after this object has been created.
	 * The Getter methods won't give the expected result and will return 'undefined' or 'null'.
	 * This is an issue with the use of 'this'. 
	 */
	if (json != null) {
		// sets the contestID
		if (typeof(json.contestID) != "undefined" && typeof(json.contestID) == "number") {
			that.contestID /* long */ = json.contestID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestID",
			"json.contestID does not exists");
		}
		// sets the projectID
		if (typeof(json.projectID) != "undefined" && typeof(json.projectID) == "number") {
	    	that.projectID /* long */ = json.projectID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.projectID",
			"json.projectID does not exists");
		}
		// sets the name
		if (typeof(json.name) != "undefined" && typeof(json.name) == "string") {
		    that.name/* String */ = json.name;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.name","json.name does not exists");
		}
		// sets the shortSummary
		if (typeof(json.shortSummary) != "undefined" && typeof(json.shortSummary) == "string") {
		    that.shortSummary /* String */ = json.shortSummary;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.shortSummary",
			"json.shortSummary does not exists");
		}

		// sets the prizes
		// since this has already been evaluated, we convert the json object to a json string
		if (typeof(json.prizes) != "undefined" && typeof(json.prizes) == "object") {
			var tempPrizes = json.prizes;
			var prizeArray = new Array();
			for (var x = 0; x < tempPrizes.length; x++) {
				prizeArray[x] =
					"{\"place\" : "+tempPrizes[x].place+",\"amount\" : "+tempPrizes[x].amount+"}";
			}
		    that.prizes /* Prize[] */ = prizeArray;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.prizes","json.prizes does not exists");
		}

		// sets the launchDateAndTime
		if (typeof(json.launchDateAndTime) != "undefined" && typeof(json.launchDateAndTime) == "string") {
		    that.launchDateAndTime /* Date */ = json.launchDateAndTime;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.launchDateAndTime",
			"json.launchDateAndTime does not exists");
		}

		// sets the durationInHours
		if (typeof(json.durationInHours) != "undefined" && typeof(json.durationInHours) == "number") {
		    that.durationInHours /* int */ = json.durationInHours;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.durationInHours",
			"json.durationInHours does not exists");
		}

		// sets the winnerAnnouncementDeadline
		if (typeof(json.winnerAnnouncementDeadline) != "undefined"
			&& typeof(json.winnerAnnouncementDeadline) == "string") {
		    that.winnerAnnouncementDeadline /* Date */ = json.winnerAnnouncementDeadline;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.winnerAnnouncementDeadline",
			"json.winnerAnnouncementDeadline does not exists");
		}

	    // set the contest type ID
		if (typeof(json.contestTypeID) != "undefined" && typeof(json.contestTypeID) == "number") {
		    that.contestTypeID = json.contestTypeID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestTypeID",
			"json.contestTypeID does not exists");
		}

	    // set the contest channel ID
		if (typeof(json.contestChannelID) != "undefined" && typeof(json.contestChannelID) == "number") {
		    that.contestChannelID = json.contestChannelID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestChannelID",
			"json.contestChannelID does not exists");
		}
		
		// sets the finalFileFormatList
		// since this has already been evaluated, we convert the json object to a json string
		if (typeof(json.finalFileFormatList) != "undefined" && typeof(json.finalFileFormatList) == "object") {
			var tempFormat = json.finalFileFormatList;
			var formatArray = new Array();
			for (var x = 0; x < tempFormat.length; x++) {
				formatArray[x] = "\""+tempFormat[x]+"\"";
			}
		    that.finalFileFormatList /* String[] */ = formatArray;
	    } else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.finalFileFormatList",
			"json.finalFileFormatList does not exists");
		}

		// sets the finalFileFormatOther
		if (typeof(json.finalFileFormatOther) != "undefined" && typeof(json.finalFileFormatOther) == "string") {
		    that.finalFileFormatOther /* String */ = json.finalFileFormatOther;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.finalFileFormatOther",
			"json.finalFileFormatOther does not exists");
		}
	    
		// sets the documentationUploads
		// since this has already been evaluated, we convert the json object to a json string
		if (typeof(json.documentationUploads) != "undefined" && typeof(json.documentationUploads) == "object") {
			var tempUpDoc = json.documentationUploads;
			var upDocArray = new Array();
			for (var x = 0; x < tempUpDoc.length; x++) {
				upDocArray[x] = 
					"{\"fileName\" : \"" + tempUpDoc[x].fileName + "\",\"path\" : \""+ tempUpDoc[x].path + "\",\"documentID\" : " + tempUpDoc[x].documentID
					+ ",\"contestID\" : " + tempUpDoc[x].contestID + ", \"description\" : \""
					+ tempUpDoc[x].description+"\"}";
			}
		    that.documentationUploads /* UploadedDocument[] */ = upDocArray;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.documentationUploads",
			"json.documentationUploads does not exists");
		}

		// sets the statusID
		if (typeof(json.statusID) != "undefined" && typeof(json.statusID) == "number") {
		    that.statusID /* long */ = json.statusID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.statusID",
			"json.statusID does not exists");
		}

		// sets the contestPayloads
		// since this has already been evaluated, we convert the json object to a json string
		if (typeof(json.contestPayloads) != "undefined" && typeof(json.contestPayloads) == "object") {
			var tempPayload = json.contestPayloads;
			var payloadArray = new Array();
			for (var x = 0; x < tempPayload.length; x++) {
				payloadArray[x] = 
					"{\"name\" : \"" + tempPayload[x].name + "\",\"value\" : \"" + tempPayload[x].value
					+ "\",\"description\" : \"" + tempPayload[x].description + "\",\"required\" : "
					+ tempPayload[x].required + ", \"contestTypeID\" : " + tempPayload[x].contestTypeID + "}";
			}
		    that.contestPayloads /* ContestPayload[] */ = payloadArray;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestPayloads",
			"json.contestPayloads does not exists");
		}

		// sets the media
		// since this has already been evaluated, we convert the json object to a json string
		if (typeof(json.media) != "undefined" && typeof(json.media) == "object") {
			var tempMedia = json.media;
			var mediumArray = new Array();
			for (var x = 0; x < tempMedia.length; x++) {
				mediumArray[x] = 
					"{\"description\" : \"" + tempMedia[x].description + "\", \"mediumId\" : " + tempMedia[x].mediumId + "}";
			}
		    that.media /* Medium[] */ = mediumArray;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.media",
			"json.media does not exists");
		}
		
		// sets the contestDescriptionAndRequirements
		if (typeof(json.contestDescriptionAndRequirements) != "undefined"
		    && typeof(json.contestDescriptionAndRequirements) == "string") {
		    that.contestDescriptionAndRequirements /* String */ = json.contestDescriptionAndRequirements;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestDescriptionAndRequirements",
			"json.contestDescriptionAndRequirements does not exists");
		}

		// sets the requiredOrRestrictedColors
		if (typeof(json.requiredOrRestrictedColors) != "undefined"
		    && typeof(json.requiredOrRestrictedColors) == "string") {
		    that.requiredOrRestrictedColors /* String */ = json.requiredOrRestrictedColors;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.requiredOrRestrictedColors",
			"json.requiredOrRestrictedColors does not exists");
		}

		// sets the requiredOrRestrictedFonts
		if (typeof(json.requiredOrRestrictedFonts) != "undefined"
		    && typeof(json.requiredOrRestrictedFonts) == "string") {
		    that.requiredOrRestrictedFonts /* String */ = json.requiredOrRestrictedFonts;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.requiredOrRestrictedFonts",
			"json.requiredOrRestrictedFonts does not exists");
		}

		// sets the sizeRequirements
		if (typeof(json.sizeRequirements) != "undefined" && typeof(json.sizeRequirements) == "string") {
		    that.sizeRequirements /* String */ = json.sizeRequirements;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.sizeRequirements",
			"json.sizeRequirements does not exists");
		}

		// sets the otherRequirementsOrRestrictions
		if (typeof(json.otherRequirementsOrRestrictions) != "undefined"
		    && typeof(json.otherRequirementsOrRestrictions) == "string") {
		    that.otherRequirementsOrRestrictions /* String */ = json.otherRequirementsOrRestrictions;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.otherRequirementsOrRestrictions",
			"json.otherRequirementsOrRestrictions does not exists");
		}

		// sets the tcDirectProjectID
		if (typeof(json.tcDirectProjectID) != "undefined" && typeof(json.tcDirectProjectID) == "number") {
		    that.tcDirectProjectID /* long */ = json.tcDirectProjectID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.tcDirectProjectID",
			"json.tcDirectProjectID does not exists");
		}
		
		// sets the tcDirectProjectID
		if (typeof(json.submissionCount) != "undefined" && typeof(json.submissionCount) == "number") {
		    that.submissionCount /* long */ = json.submissionCount;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.submissionCount",
			"json.submissionCount does not exists");
		}		

		// sets the creatorUserID
		if (typeof(json.creatorUserID) != "undefined" && typeof(json.creatorUserID) == "number") {
		    that.creatorUserID /* long */ = json.creatorUserID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.creatorUserID",
			"json.creatorUserID does not exists");
		}

		// sets the numberOfRegistrants
		if (typeof(json.numberOfRegistrants) != "undefined" && typeof(json.numberOfRegistrants) == "number") {
		    that.numberOfRegistrants /* long */ = json.numberOfRegistrants;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.numberOfRegistrants",
			"json.submissionCount does not exists");
		}
		
		// sets the maximumSubmissions
		if (typeof(json.maximumSubmissions) != "undefined" && typeof(json.maximumSubmissions) == "number") {
		    that.maximumSubmissions /* long */ = json.maximumSubmissions;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.maximumSubmissions",
			"json.maximumSubmissions does not exists");
		}

		// sets the requiresPreviewImage
		if (typeof(json.requiresPreviewImage) != "undefined" ) {
		    that.requiresPreviewImage /* bool */ = json.requiresPreviewImage;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.requiresPreviewImage",
			"json.requiresPreviewImage does not exists");
		}
		
		// sets the requiresPreviewFile
		if (typeof(json.requiresPreviewFile) != "undefined" ) {
		    that.requiresPreviewFile /* bool */ = json.requiresPreviewFile;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.requiresPreviewFile",
			"json.requiresPreviewFile does not exists");
		}

		// sets the Eligibility
		if (typeof(json.eligibility) != "undefined" ) {
		    that.eligibility /* String */ = json.eligibility;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.eligibility",
			"json.eligibility does not exists");
		}

		// sets the notesOnWinnerSelection.
		if (typeof(json.notesOnWinnerSelection) != "undefined" ) {
		    that.notesOnWinnerSelection /* String */ = json.notesOnWinnerSelection;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.notesOnWinnerSelection",
			"json.notesOnWinnerSelection does not exists");
		}

		// sets the Prize Description.
		if (typeof(json.prizeDescription) != "undefined" ) {
		    that.prizeDescription /* String */ = json.prizeDescription;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.prizeDescription",
			"json.prizeDescription does not exists");
		}
	}



    /**
     * <p>Returns the contestDescriptionAndRequirements.</p>
     */
    this.getContestDescriptionAndRequirements = function /* String */ () {
        return that.contestDescriptionAndRequirements;
    }

    /**
     * <p>Sets the contestDescriptionAndRequirements.</p>
     */
    this.setContestDescriptionAndRequirements = function /* void */ (/* String */ contestDescriptionAndRequirements) {
        that.contestDescriptionAndRequirements = contestDescriptionAndRequirements;
    }

    /**
     * <p>Returns the contestID.</p>
     */
    this.getContestID = function /* long */ () {
        return that.contestID;
    }

    /**
     * <p>Sets the contestID.</p>
     */
    this.setContestID = function /* void */ (/* long */ contestID) {
        that.contestID = contestID;
    }

    /**
     * <p>Returns the contestPayloads.</p>
     */
    this.getContestPayloads = function /* ContestPayload[] */ () {
        return that.contestPayloads;
    }

    /**
     * <p>Sets the contestPayloads.</p>
     */
    this.setContestPayloads = function /* void */ (/* ContestPayload[] */ contestPayloads) {
        that.contestPayloads = contestPayloads;
    }

    /**
     * <p>Returns the creatorUserID.</p>
     */
    this.getCreatorUserID = function /* long */ () {
        return that.creatorUserID;
    }

    /**
     * <p>Sets the creatorUserID.</p>
     */
    this.setCreatorUserID = function /* void */ (/* long */ creatorUserID) {
        that.creatorUserID = creatorUserID;
    }

    /**
     * <p>Returns the documentationUploads.</p>
     */
    this.getDocumentationUploads = function /* UploadedDocument[] */ () {
        return that.documentationUploads;
    }

    /**
     * <p>Sets the documentationUploads.</p>
     */
    this.setDocumentationUploads = function /* void */ (/* UploadedDocument[] */ documentationUploads) {
        that.documentationUploads = documentationUploads;
    }

    /**
     * <p>Returns the media.</p>
     */
    this.getMedia = function /* Medium[] */ () {
        return that.media;
    }

    /**
     * <p>Sets the media.</p>
     */
    this.setMedia = function /* void */ (/* Medium[] */ media) {
        that.media = media;
    }
    
    /**
     * <p>Returns the durationInHours.</p>
     */
    this.getDurationInHours = function /* int */ () {
        return that.durationInHours;
    }

    /**
     * <p>Sets the durationInHours.</p>
     */
    this.setDurationInHours = function /* void */ (/* int */ durationInHours) {
        that.durationInHours = durationInHours;
    }

    /**
     * <p>Returns the finalFileFormatList.</p>
     */
    this.getFinalFileFormatList = function /* String[] */ () {
        return that.finalFileFormatList;
    }

    /**
     * <p>Sets the finalFileFormatList.</p>
     */
    this.setFinalFileFormatList = function /* void */ (/* String[] */ finalFileFormatList) {
        that.finalFileFormatList = finalFileFormatList;
    }

    /**
     * <p>Returns the finalFileFormatOther.</p>
     */
    this.getFinalFileFormatOther = function /* String */ () {
        return that.finalFileFormatOther;
    }

    /**
     * <p>Sets the eligibility.</p>
     */
    this.setEligibility = function /* void */ (/* String */ eligibility ) {
        that.eligibility = eligibility;
    }

    /**
     * <p>Returns the eligibility.</p>
     */
    this.getEligibility = function /* String */ () {
        return that.eligibility;
    }

    /**
     * <p>Sets the Prize Description.</p>
     */
    this.setPrizeDescription = function /* void */ (/* String */ prizeDescription ) {
        that.prizeDescription = prizeDescription;
    }

    /**
     * <p>Returns the Prize Description .</p>
     */
    this.getPrizeDescription = function /* String */ () {
        return that.prizeDescription;
    }
    
    /**
     * <p>Sets the Notes on Winner Selection.</p>
     */
    this.setNotesOnWinnerSelection = function /* void */ (/* String */ notesOnWinnerSelection ) {
        that.notesOnWinnerSelection = notesOnWinnerSelection;
    }

    /**
     * <p>Returns the Notes on Winner Selection.</p>
     */
    this.getNotesOnWinnerSelection = function /* String */ () {
        return that.notesOnWinnerSelection;
    }

    
    /**
     * <p>Sets the finalFileFormatOther.</p>
     */
    this.setFinalFileFormatOther = function /* void */ (/* String */ finalFileFormatOther) {
        that.finalFileFormatOther = finalFileFormatOther;
    }

    /**
     * <p>Returns the launchDateAndTime.</p>
     */
    this.getLaunchDateAndTime = function /* Date */ () {
        return that.launchDateAndTime;
    }

    /**
     * <p>Sets the launchDateAndTime.</p>
     */
    this.setLaunchDateAndTime = function /* void */ (/* Date */ launchDateAndTime) {
        that.launchDateAndTime = launchDateAndTime;
    }

    /**
     * <p>Returns the name.</p>
     */
    this.getName = function /* String */ () {
        return that.name;
    }

    /**
     * <p>Sets the name.</p>
     */
    this.setName = function /* void */ (/* String */ name) {
        that.name = name;
    }

    /**
     * <p>Returns the otherRequirementsOrRestrictions.</p>
     */
    this.getOtherRequirementsOrRestrictions = function /* String */ () {
        return that.otherRequirementsOrRestrictions;
    }

    /**
     * <p>Sets the otherRequirementsOrRestrictions.</p>
     */
    this.setOtherRequirementsOrRestrictions = function /* void */ (/* String */ otherRequirementsOrRestrictions) {
        that.otherRequirementsOrRestrictions = otherRequirementsOrRestrictions;
    }

    /**
     * <p>Returns the prizes.</p>
     */
    this.getPrizes = function /* Prize[] */ () {
        return that.prizes;
    }

    /**
     * <p>Sets the prizes.</p>
     */
    this.setPrizes = function /* void */ (/* Prize[] */ prizes) {
        that.prizes = prizes;
    }

    /**
     * <p>Returns the projectID.</p>
     */
    this.getProjectID = function /* long */ () {
        return that.projectID;
    }

    /**
     * <p>Sets the projectID.</p>
     */
    this.setProjectID = function /* void */ (/* long */ projectID) {
        that.projectID = projectID;
    }

    /**
     * <p>Returns the requiredOrRestrictedColors.</p>
     */
    this.getRequiredOrRestrictedColors = function /* String */ () {
        return that.requiredOrRestrictedColors;
    }

    /**
     * <p>Sets the requiredOrRestrictedColors.</p>
     */
    this.setRequiredOrRestrictedColors = function /* void */ (/* String */ requiredOrRestrictedColors) {
        that.requiredOrRestrictedColors = requiredOrRestrictedColors;
    }

    /**
     * <p>Returns the requiredOrRestrictedFonts.</p>
     */
    this.getRequiredOrRestrictedFonts = function /* String */ () {
        return that.requiredOrRestrictedFonts;
    }

    /**
     * <p>Sets the requiredOrRestrictedFonts.</p>
     */
    this.setRequiredOrRestrictedFonts = function /* void */ (/* String */ requiredOrRestrictedFonts) {
        that.requiredOrRestrictedFonts = requiredOrRestrictedFonts;
    }

    /**
     * <p>Returns the shortSummary.</p>
     */
    this.getShortSummary = function /* String */ () {
        return that.shortSummary;
    }

    /**
     * <p>Sets the shortSummary.</p>
     */
    this.setShortSummary = function /* void */ (/* String */ shortSummary) {
        that.shortSummary = shortSummary;
    }

    /**
     * <p>Returns the sizeRequirements.</p>
     */
    this.getSizeRequirements = function /* String */ () {
        return that.sizeRequirements;
    }

    /**
     * <p>Sets the sizeRequirements.</p>
     */
    this.setSizeRequirements = function /* void */ (/* String */ sizeRequirements) {
        that.sizeRequirements = sizeRequirements;
    }

    /**
     * <p>Returns the statusID.</p>
     */
    this.getStatusID = function /* long */ () {
        return that.statusID;
    }

    /**
     * <p>Sets the statusID.</p>
     */
    this.setStatusID = function /* void */ (/* long */ statusID) {
        that.statusID = statusID;
    }

    /**
     * <p>Returns the tcDirectProjectID.</p>
     */
    this.getTcDirectProjectID = function /* long */ () {
        return that.tcDirectProjectID;
    }

    /**
     * <p>Sets the tcDirectProjectID.</p>
     */
    this.setTcDirectProjectID = function /* void */ (/* long */ tcDirectProjectID) {
        that.tcDirectProjectID = tcDirectProjectID;
    }

    /**
     * <p>Returns the winnerAnnouncementDeadline.</p>
     */
    this.getWinnerAnnouncementDeadline = function /* Date */ () {
        return that.winnerAnnouncementDeadline;
    }

    /**
     * <p>Sets the winnerAnnouncementDeadline.</p>
     */
    this.setWinnerAnnouncementDeadline = function /* void */ (/* Date */ winnerAnnouncementDeadline) {
        that.winnerAnnouncementDeadline = winnerAnnouncementDeadline;
    }    

    /**
     * <p>Returns the contestTypeID.</p>
     */
    this.getContestTypeID = function /* long */ () {
        return that.contestTypeID;
    }

    /**
     * <p>Sets the contestTypeID.</p>
     */
    this.setContestTypeID = function /* void */ (/* long */ contestTypeID) {
        that.contestTypeID = contestTypeID;
    }    

    /**
     * <p>Returns the contestChannelID.</p>
     */
    this.getContestChannelID = function /* long */ () {
        return that.contestChannelID;
    }

    /**
     * <p>Sets the maximumSubmissions.</p>
     */
    this.setMaximumSubmissions = function /* void */ (/* long */ maximumSubmissions) {
        that.maximumSubmissions = maximumSubmissions;
    }    

    /**
     * <p>Returns the maximumSubmissions.</p>
     */
    this.getMaximumSubmissions = function /* long */ () {
        return that.maximumSubmissions;
    }

    /**
     * <p>Sets the requiresPreviewFile.</p>
     */
    this.setRequiresPreviewFile = function /* void */ (/* bool */ requiresPreviewFile) {
        that.requiresPreviewFile = requiresPreviewFile;
    }    

    /**
     * <p>Returns the requiresPreviewFile.</p>
     */
    this.getRequiresPreviewFile = function /* bool */ () {
        return that.requiresPreviewFile;
    }

    /**
     * <p>Sets the requiresPreviewImage.</p>
     */
    this.setRequiresPreviewImage = function /* void */ (/* bool */ requiresPreviewImage) {
        that.requiresPreviewImage = requiresPreviewImage;
    }    

    /**
     * <p>Returns the requiresPreviewImage.</p>
     */
    this.getRequiresPreviewImage = function /* bool */ () {
        return that.requiresPreviewImage;
    }
    
    /**
     * <p>Sets the contestTypeID.</p>
     */
    this.setContestChannelID = function /* void */ (/* long */ contestChannelID) {
        that.contestChannelID = contestChannelID;
    }    
    
	/**
	 * <p>Convert this JavaScript object to a JSON string and return.
	 *
	 * It will generate json string in the following format: (The $xxx should be replaced by corresponding variables.)</p>
	 *
	 * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.  Properties with null
	 * value should also be added to the generated json string with null value. 
	 *
	 * {"contestID" : $contestID,"projectID" : $projectID,"name" : "$name","shortSummary" : "$shortSummary",
	 * "prizes" : [ json-string of each prize js object delimited by comma ],"launchDateAndTime" : "$launchDateAndTime", 
	 * //  string representation of the date value"durationInHours" : $durationInHours,"winnerAnnouncementDeadline" : "$winnerAnnouncementDeadline" 
	 * //  string representation of the date value"winnerAnnouncementDeadline","finalFileFormatList" : 
	 * [ values of $finalFileFormatList delimited by comma ], "finalFileFormatOther" : "$finalFileFormatOther","documentationUpload" : 
	 * [json-string of each UploadedDocument js object delimited by comma],"statusID" : $statusID,"contestPayloads" : 
	 * [json-string of each ContestPayload js object delimited by comma],
	 * "contestDescriptionAndRequirements" : "$contestDescriptionAndRequirements","requiredOrRestrictedColors" : "$requiredOrRestrictedColors",
	 * "requiredOrRestrictedFonts" : "$requiredOrRestrictedFonts","sizeRequirements" : "$sizeRequirements","otherRequirementsOrRestrictions" : 
	 * "$otherRequirementsOrRestrictions","tcDirectProjectID" : $tcDirectProjectID,"creatorUserID" : $creatorUserID}
	 */
	this.toJSON = function /* String */ () {
		if(typeof(that.getEligibility()) == "undefined"){
			that.setEligibility('n/a'); 
		}
		if(typeof(that.getNotesOnWinnerSelection()) == "undefined"){
			that.setNotesOnWinnerSelection('n/a'); 
		}
		if(typeof(that.getPrizeDescription()) == "undefined"){
			that.setPrizeDescription('n/a'); 
		}
		
		// setup Prizes json string
		var locPrizes = "[";
		var tempPrizes = that.getPrizes();
		for (var x = 0; x < tempPrizes.length;  x++) {
			if (x == 0) {
				locPrizes += tempPrizes[x];
			} else {
				locPrizes += (", " + tempPrizes[x]);
			}
		}
		locPrizes += "]";

		// setup final file format list string		
		var locFileFomat = "[";
		var tempFinalFormat = that.getFinalFileFormatList();
		for (var x = 0; x < tempFinalFormat.length;  x++) {
			if (x == 0) {
				locFileFomat += tempFinalFormat[x];
			} else {
				locFileFomat += (", " + tempFinalFormat[x]);
			}
		}
		locFileFomat += "]";

		// setup Uploaded Document json string
		var locUpDoc = "[";
		var tempUpDoc = that.getDocumentationUploads();
		for (var x = 0; x < tempUpDoc.length;  x++) {
			if (x == 0) {
				locUpDoc += tempUpDoc[x];
			} else {
				locUpDoc += ("," + tempUpDoc[x]);
			}
		}
		locUpDoc += "]";

		// setup Contest Payload json string
		var locContPay = "[";
		var tempContPay = that.getContestPayloads();
		for (var x = 0; x < tempContPay.length;  x++) {
			if (x == 0) {
				locContPay += tempContPay[x];
			} else {
				locContPay += ("," + tempContPay[x]);
			}
		}
		locContPay += "]";

		// setup Contest medium json string
		var locMed = "[";
		var tempMed = that.getMedia();
		if ( typeof(tempMed) == "undefined" )
		{
			tempMed = new Array();
		}
		for (var x = 0; x < tempMed.length;  x++) {
			if (x == 0) {
				locMed += tempMed[x];
			} else {
				locMed += ("," + tempMed[x]);
			}
		}
		locMed += "]";
		
		return	"{"
		        + "\"contestID\" : " + that.getContestID() + ", "
		        + "\"projectID\" : " + that.getProjectID() + ", "
		        + "\"name\" : \"" + Helper.escapeJSONValue(that.getName()) + "\", "
		        + "\"shortSummary\" : \"" + Helper.escapeJSONValue(that.getShortSummary()) + "\", "
		        + "\"prizes\" : " + locPrizes + ", "
		        + "\"media\" : " + locMed + ", "
		        + "\"launchDateAndTime\" : \"" + that.getLaunchDateAndTime() + "\", "
		        + "\"durationInHours\" : " + that.getDurationInHours() + ", "
		        + "\"winnerAnnouncementDeadline\" : \"" + that.getWinnerAnnouncementDeadline() + "\", "
		        + "\"contestTypeID\" : " + that.getContestTypeID() + ", "
		        + "\"contestChannelID\" : " + that.getContestChannelID() + ", "
		        + "\"finalFileFormatList\" : " + locFileFomat + ", "
		        + "\"finalFileFormatOther\" : \"" + Helper.escapeJSONValue(that.getFinalFileFormatOther()) + "\", "
		        + "\"documentationUploads\" : " + locUpDoc + ", "
		        + "\"statusID\" : " + that.getStatusID() + ", "
		        + "\"contestPayloads\" : " + locContPay + ", "
		        + "\"contestDescriptionAndRequirements\" : \"" + Helper.escapeJSONValue(that.getContestDescriptionAndRequirements()) + "\", "
		        + "\"requiredOrRestrictedColors\" : \"" + Helper.escapeJSONValue(that.getRequiredOrRestrictedColors()) + "\", "
		        + "\"requiredOrRestrictedFonts\" : \"" + Helper.escapeJSONValue(that.getRequiredOrRestrictedFonts()) + "\", "
		        + "\"sizeRequirements\" : \"" + Helper.escapeJSONValue(that.getSizeRequirements()) + "\", "
		        + "\"otherRequirementsOrRestrictions\" : \"" + Helper.escapeJSONValue(that.getOtherRequirementsOrRestrictions()) + "\", "
		        + "\"tcDirectProjectID\" : " + that.getTcDirectProjectID() + ", "
		        + "\"submissionCount\" : " + that.getSubmissionCount() + ", "
		        + "\"numberOfRegistrants\" : " + that.getNumberOfRegistrants () + ", "
		        + "\"maximumSubmissions\" : " + that.getMaximumSubmissions () + ", "
		        + "\"requiresPreviewImage\" : " + that.getRequiresPreviewImage () + ", "
		        + "\"requiresPreviewFile\" : " + that.getRequiresPreviewFile () + ", "
		        + "\"eligibility\" : \"" + that.getEligibility() + "\", "
		        + "\"notesOnWinnerSelection\" : \"" + that.getNotesOnWinnerSelection () + "\", "
		        + "\"prizeDescription\" : \"" + that.getPrizeDescription() + "\", "
		        + "\"creatorUserID\" : " + that.getCreatorUserID()
		        + "}";
	}


    /**
     * <p>Returns formatted winnerAnnouncementDeadline.</p>
     */
    this.getWinnerAnnouncementDeadlineAsDate = function /* String */ (){
	    var results = that.winnerAnnouncementDeadline.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}) *$/);    
      var dt = new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]),parseInt(results[4]),parseInt(results[5]),0);
      return dt.toString();
	}    

    /**
     * <p>Returns formatted launchDateAndTime.</p>
     */
    this.getLaunchDateAndTimeAsDate = function /* String */ (){
	    var results = that.launchDateAndTime.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}) *$/);    
      var dt = new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]),parseInt(results[4]),parseInt(results[5]),0);
      return dt.toString();
	}
	
	/**
	 * <p>Represents the submissions count</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or  -1</p>
	 */
	var submissionCount /* long */ = -1;

  /**
   * <p>Returns the submissionCount .</p>
   */
  this.getSubmissionCount  = function /* long */ () {
      return that.submissionCount ;
  }

  /**
   * <p>Sets the submissionCount .</p>
   */
  this.setSubmissionCount  = function /* void */ (/* long */ submissionCount ) {
      that.submissionCount  = submissionCount ;
  }	    
  

  /**
   * <p>Returns the numberOfRegistrants .</p>
   */
  this.getNumberOfRegistrants = function /* long */ () {
      return that.numberOfRegistrants;
  }

  /**
   * <p>Sets the numberOfRegistrants .</p>
   */
  this.setNumberOfRegistrants = function /* void */ (/* long */ numberOfRegistrants ) {
      that.numberOfRegistrants  = numberOfRegistrants ;
  }	    
} // end
