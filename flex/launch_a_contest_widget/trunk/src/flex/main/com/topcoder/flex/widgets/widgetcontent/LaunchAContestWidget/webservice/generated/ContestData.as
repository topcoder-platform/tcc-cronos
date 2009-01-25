/**
 * ContestData.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */

package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated
{
	import mx.utils.ObjectProxy;
	import flash.utils.ByteArray;
	import mx.rpc.soap.types.*;
	/**
	 * Wrapper class for a operation required type
	 */
    
	public class ContestData
	{
		/**
		 * Constructor, initializes the type class
		 */
		public function ContestData() {}
            
		public var contestId:Number;
		public var name:String;
		public var projectId:Number;
		public var tcDirectProjectId:Number;
		[ArrayElementType("PrizeData")]
		public var prizes:Array;
		public var launchDateAndTime:Object;
		public var winnerAnnoucementDeadline:Object;
		public var durationInHours:Number;
		[ArrayElementType("UploadedDocument")]
		public var documentationUploads:Array;
		[ArrayElementType("ContestPayload")]
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
		public var submissionCount:Number;
		public var contestTypeId:Number;
		public var contestChannelId:Number;
		public var eligibility:String;
		public var notesOnWinnerSelection:String;
		public var prizeDescription:String;
		public var forumPostCount:Number;
		public var forumId:Number;
		[ArrayElementType("MediumData")]
		public var media:Array;
		public var drPoints:Number;
		public var contestAdministrationFee:Number;
		public var launchImmediately:Boolean;
		public var requiresPreviewImage:Boolean;
		public var requiresPreviewFile:Boolean;
		public var maximumSubmissions:Number;
		public var numberOfRegistrants:Number;
	}
}