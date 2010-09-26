package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice
{
	public class UserNameToken
	{
		
		private var _username:String;
		private var _password:String;
		public function UserNameToken(username:String, password:String)
		{
			this._username = username;
			this._password = password;
		}
		
		public function toXML():XML{
			var usernameToken:XML = 
			<wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
			<wsse:UsernameToken wsu:Id="UsernameToken-2870450">
			<wsse:Username>{this._username}</wsse:Username>
			<wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">{this._password}</wsse:Password>
			</wsse:UsernameToken>
			</wsse:Security>
			return usernameToken;
		}

	}
}
