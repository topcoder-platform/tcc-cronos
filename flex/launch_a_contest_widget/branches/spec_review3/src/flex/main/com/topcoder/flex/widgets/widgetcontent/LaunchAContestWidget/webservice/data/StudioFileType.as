/**
 * StudioFileType.as
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
    
    public class StudioFileType
    {
        /**
         * Constructor, initializes the type class
         */
public function StudioFileType() {}
                
                   public var description:String;
                   public var extension:String;
                   public var imageFile:Boolean;
                   
                   [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.MimeType")]
                   public var mimeTypes:Array;
                   public var sort:Number;
                   public var studioFileType:Number;
           	}
      	 }
