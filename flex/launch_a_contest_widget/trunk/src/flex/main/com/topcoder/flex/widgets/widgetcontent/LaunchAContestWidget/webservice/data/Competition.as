/**
 * Competition.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */

package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data{
    import flash.utils.ByteArray;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.soap.types.*;
    import mx.utils.ObjectProxy;
    /**
     * Wrapper class for a operation required type
     */
    
    public class Competition
    {
        /**
         * Constructor, initializes the type class
         */
public function Competition() {}
                
                   public var adminFee:Number;
                   public var competitionId:Number;
                   public var creatorUserId:Number;
                   public var drPoints:Number;
                   public var eligibility:String;
                   public var endTime:String; // TODO: see if we want date here.
                   public var id:Number;

                   [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.CompetitionPrize")]
                   public var prizes:Array;
                   public var project:com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.Project;
                   public var shortSummary:String;
                   public var startTime:String; // TODO: see if we want date here.
                   public var type:String; //com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.CompetionType;
           	}
      	 }
