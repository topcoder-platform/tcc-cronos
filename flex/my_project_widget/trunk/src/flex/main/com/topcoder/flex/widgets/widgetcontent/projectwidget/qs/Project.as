package com.topcoder.flex.widgets.widgetcontent.projectwidget.qs
{
	import mx.collections.ArrayCollection;

	public class Project
	{
		public var id:String;
		public var name:String;
		public var description:String;
		public var status:String;
		public var contests:ArrayCollection;
		public var date:Date=null;
		public function Project(_id:String,_name:String,_descr:String,_status:String,_contest:ArrayCollection=null)
		{
			id=_id;
			this.name=_name;
			this.description=_descr;
			this.status=_status;
			this.contests=_contest;
			for(var i:int=0;i<contests.length;i++)
			{
				var c:Contest=contests.getItemAt(i) as Contest;
				if(date==null || date < c.date)
				{
					date=c.date;
				}
			}
		}

	}
}
