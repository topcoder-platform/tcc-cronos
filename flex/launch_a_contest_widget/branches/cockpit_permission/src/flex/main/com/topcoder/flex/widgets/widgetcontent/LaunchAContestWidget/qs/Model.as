package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.qs
{
	public class Model
	{
		[Bindable]
		public var cardNum:String;
		
		[Bindable]
		public var cardType:String;
		
		[Bindable]
		public var cardExpMonth:String;
		
		[Bindable]
		public var cardExpYear:String;
		
		[Bindable]
		public var cardExpDateStr:String;
		
		[Bindable]
		public var firstName:String;
		
		[Bindable]
		public var lastName:String;
		
		[Bindable]
		public var cardname:String;
		
		[Bindable]
		public var address:String;
		
		[Bindable]
		public var city:String;
		
		[Bindable]
		public var state:String;
		
		[Bindable]
		public var code:String;
		
		[Bindable]
		public var country:String;
		
		[Bindable]
		public var phone:String;
		
		[Bindable]
		public var email:String;
		
		[Bindable]
		public var purchaseOrder:Object;
		
		// BUGR-1398
		[Bindable]
		public var csc:String;
		
		public function Model()
		{
		}
		
		private static var _instance:Model;
		public static function get instance():Model {
			if (!_instance) {
				_instance = new Model();
			}
			return _instance;
		}

	}
}
