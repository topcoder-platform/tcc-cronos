/**
 * Project.as
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
    
    public class Project extends com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.ProjectData
    {
        /**
         * Constructor, initializes the type class
         */
public function Project() {}
                [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.Competition")]
                   public var competitions:Array;
                   public var createDate:Date;
                   public var modifyDate:Date;
                   public var userId:Number;
           	}
      	 }
