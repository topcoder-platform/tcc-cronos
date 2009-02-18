/**
 * GetAllProjectsResultEvent.as
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
    
	public class GetAllProjectsResultEvent extends Event
	{
		/**
		 * The event type value
		 */
		public static var GetAllProjects_RESULT:String="GetAllProjects_result";
		/**
		 * Constructor for the new event type
		 */
		public function GetAllProjectsResultEvent()
		{
			super(GetAllProjects_RESULT,false,false);
		}
        
		private var _headers:Object;
		private var _result:GetAllProjectsResponse;
		public function get result():GetAllProjectsResponse
		{
			return _result;
		}

		public function set result(value:GetAllProjectsResponse):void
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