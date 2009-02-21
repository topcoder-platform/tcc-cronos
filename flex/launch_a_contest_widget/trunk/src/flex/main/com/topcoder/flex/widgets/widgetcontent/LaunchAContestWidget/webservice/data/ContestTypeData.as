/**
 * ContestTypeData.as
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
    
    public class ContestTypeData
    {
        /**
         * Constructor, initializes the type class
         */
public function ContestTypeData() {}
                
                   public var contestTypeId:Number;
                   public var description:String;
                   public var requirePreviewFile:Boolean;
                   public var requirePreviewImage:Boolean;
                   [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.ContestPayload")]
                   public var config:Array;
           	}
      	 }
