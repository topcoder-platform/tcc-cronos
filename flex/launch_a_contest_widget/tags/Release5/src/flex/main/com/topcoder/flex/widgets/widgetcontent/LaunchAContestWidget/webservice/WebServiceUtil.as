package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice
{
	import mx.rpc.soap.SOAPHeader;
	import mx.rpc.soap.WebService;
	
	public class WebServiceUtil
	{
		public static function getWSSEHeader(username:String, password:String):SOAPHeader{
			
			var wsse:Namespace = new Namespace("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
			var wsseSecurity:QName = new QName(wsse.uri, "Security");
			var header:SOAPHeader = new SOAPHeader(wsseSecurity, {"wsse":"Security"});
			header.content = createUserNameToken(username, password);
			return header;
		}
		
		private static function createUserNameToken(username:String, password:String):XML{
			var usernameToken:XML = 
			<wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
			<wsse:UsernameToken wsu:Id="UsernameToken-2870450">
			<wsse:Username>{username}</wsse:Username>
			<wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">{password}</wsse:Password>
			</wsse:UsernameToken>
			</wsse:Security>
			return usernameToken;
		}

	}
}
