/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */

package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data{
    import flash.utils.ByteArray;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.soap.types.*;
    import mx.utils.ObjectProxy;
    /**
     * A DTO class for contest data.
     *
     * Version 1.1 (Studio Multi-Rounds Assembly - Launch Contest) Change Notes:
     *    - Added multiRound, milestonePrizeData and multiRoundData attributes
     *
     * @author pulky
     * @version 1.1
     * @since 1.0
     */
    public class ContestData {

        /**
         * Default empty constructor
         */
        public function ContestData() {
        }

        public var contestId:Number;
        public var name:String;
        public var projectId:Number;
        public var tcDirectProjectId:Number;
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.PrizeData")]
        public var prizes:Array;
        public var launchDateAndTime:String; // TODO: check if we want Date here.
        public var winnerAnnoucementDeadline:String; // TODO: check if we want Date here.
        public var durationInHours:Number;
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.UploadedDocument")]
        public var documentationUploads:Array;
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.ContestPayload")]
        public var contestPayloads:Array;
        public var shortSummary:String;
        public var contestDescriptionAndRequirements:String;
        public var requiredOrRestrictedColors:String;
        public var requiredOrRestrictedFonts:String;
        public var sizeRequirements:String;
        public var otherRequirementsOrRestrictions:String;
        public var creatorUserId:Number;
        public var finalFileFormat:String;
        public var otherFileFormats:String;
        public var statusId:Number;
        public var detailedStatusId:Number;
        public var submissionCount:Number;
        public var contestTypeId:Number;
        public var contestChannelId:Number;
        public var eligibility:String;
        public var notesOnWinnerSelection:String;
        public var prizeDescription:String;
        public var forumPostCount:Number;
        public var forumId:Number;
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.MediumData")]
        public var media:Array;
        public var drPoints:Number;
        public var contestAdministrationFee:Number;
        public var launchImmediately:Boolean;
        public var requiresPreviewImage:Boolean;
        public var requiresPreviewFile:Boolean;
        public var maximumSubmissions:Number;
        public var numberOfRegistrants:Number;
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.ContestPaymentData")]
        public var payments:Array; // BUGR-1363
        public var tcDirectProjectName:String;
        public var billingProject:Number;

        /**
         * The contest multi round flag
         *
         * @since 1.1
         */
        public var multiRound:Boolean;

        /**
         * The contest milestone prize data
         *
         * @since 1.1
         */
        public var milestonePrizeData:MilestonePrizeData;

        /**
         * The contest multi round information data
         *
         * @since 1.1
         */
        public var multiRoundData:ContestMultiRoundInformationData;
    }
}
