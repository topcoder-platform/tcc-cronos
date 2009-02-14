package com.topcoder.flex.widgets.widgetcontent.projectwidget.qs
{
	public class Contest
	{
		public var id:String;
		public var name:String;
		public var startDate:String;
		public var endDate:String;
		public var registrants:String;
		public var submissions:String;
		public var forumPosts:String;
		public var forumId:String
		public var status:String;
		
		public var date:Date;


    public function Contest(_id:String,_name:String,_stDt:String,_eDt:String,_regs:String,
          _subs:String,_fPost:String,_fId:String,_sts:String)
    {
      this.id=_id;
      this.name=_name;

      this.registrants=_regs;
      this.submissions=_subs;
      this.forumPosts=_fPost;
      this.forumId=_fId;
      this.status=_sts;

      // BUGR-1156
      // Parse the specified start and end dates
      // Dates are expected to follow this sample format: Thu Oct 23 11:00:00 GMT -0500 2008

      // [0] - Day of week
      // [1] - Month
      // [2] - Day of month
      // [3] - Time
      // [4] - Timezone
      // [5] - Timezone offset
      // [6] - Year
      var startDtData:Array = _stDt.split(" ");
      var endDtData:Array = _eDt.split(" ");

      this.startDate =  startDtData[0]+" "+startDtData[1]+" "+startDtData[2]+" "+startDtData[6]+"\n";
      this.startDate += startDtData[3]+" "+startDtData[4]+" "+startDtData[5];

      this.endDate =  endDtData[0]+" "+endDtData[1]+" "+endDtData[2]+" "+endDtData[6]+"\n";
      this.endDate += endDtData[3]+" "+endDtData[4]+" "+endDtData[5];
    }

	}
}
