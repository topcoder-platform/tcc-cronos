/**
 * DeleteContestResultEvent.as
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
    
    public class DeleteContestResultEvent extends Event
    {
        /**
         * The event type value
         */
        public static var DeleteContest_RESULT:String="DeleteContest_result";
        /**
         * Constructor for the new event type
         */
        public function DeleteContestResultEvent()
        {
            super(DeleteContest_RESULT,false,false);
        }
        
        private var _headers:Object;
        private var _result:DeleteContestResponse;
         public function get result():DeleteContestResponse
        {
            return _result;
        }
        
        public function set result(value:DeleteContestResponse):void
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