package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.com
{
	public final class DateUtil
	{
		public static function parseFromString(str:String):Date{
			var year:Number = Number(str.substr(0,4));
        		var month:Number = Number(str.substr(5,2)) -1 ;
        		var date:Number = Number(str.substr(8,2));
        		
        		var hh:Number = Number(str.substr(11,2));
        		var mm:Number = Number(str.substr(14,2));
        		var ss:Number = Number(str.substr(17,2));
        		var sss:Number = Number(str.substr(20,3));
        		
        		
        		return new Date(year, month, date, hh,mm ,ss, sss);
		}
		
		public static function toString(date:Date):String{
			var result:String = "";
			result = result + formatString(date.getFullYear(),4 ) + "-";
			result = result + formatString(date.getMonth()+1, 2) + "-";
			result = result + formatString(date.getDate(), 2) + "T";
			result = result + formatString(date.getHours(),2 ) + ":";
			result = result + formatString(date.getMinutes(), 2) + ":";
			result = result + formatString(date.getSeconds(), 2) + ".";
			result = result + formatString(date.getMilliseconds(), 3) + "Z";
			
			return result;
		}
		
		public static function formatString(num:Number, digit:int):String{
			var result:String = num.toString();
			while(result.length<digit){
				result = "0"+result;
			}
			
			return result;
		}
	}
}
