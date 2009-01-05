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


		public function Contest(_id:String,_name:String,_stDt:String,_eDt:String,_regs:String,
					_subs:String,_fPost:String,_fId:String,_sts:String)
		{
			this.id=_id;
			this.name=_name;
			this.startDate=_stDt;
			this.endDate=_eDt;
			this.registrants=_regs;
			this.submissions=_subs;
			this.forumPosts=_fPost;
			this.forumId=_fId;
			this.status=_sts;
		}

	}
}