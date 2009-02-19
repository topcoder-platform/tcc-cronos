/**
 * GetSimpleProjectContestDataByPIDResultEvent.as
 * This file was auto-generated from WSDL
 * Any change made to this file will be overwritten when the code is re-generated.
*/
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated
{
    import mx.utils.ObjectProxy;
    import flash.events.Event;
    import flash.utils.ByteArray;
    import mx.rpc.soap.types.*;
    /**
     * Typed event handler for the result of the operation
     */
    
    public class GetSimpleProjectContestDataByPIDResultEvent extends Event
    {
        /**
         * The event type value
         */
        public static var GetSimpleProjectContestDataByPID_RESULT:String="GetSimpleProjectContestDataByPID_result";
        /**
         * Constructor for the new event type
         */
        public function GetSimpleProjectContestDataByPIDResultEvent()
        {
            super(GetSimpleProjectContestDataByPID_RESULT,false,false);
        }
        
        private var _headers:Object;
        private var _result:GetSimpleProjectContestDataByPIDResponse;
         public function get result():GetSimpleProjectContestDataByPIDResponse
        {
            return _result;
        }
        
        public function set result(value:GetSimpleProjectContestDataByPIDResponse):void
        {
            _result = value;
        }

        public function get headers():Object
	    {
            return _headers;
	    }
			
	    public function set headers(value:Object):void
	    {
            _headers = value;
	    }
    }
}