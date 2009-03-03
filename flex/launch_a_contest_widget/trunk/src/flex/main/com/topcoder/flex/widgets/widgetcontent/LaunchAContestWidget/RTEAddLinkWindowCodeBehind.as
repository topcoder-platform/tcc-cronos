package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget
{
	import com.topcoder.flex.Helper;
	
	import mx.containers.TitleWindow;
	import mx.managers.PopUpManager;
	
	public class RTEAddLinkWindowCodeBehind extends TitleWindow
	{
		private var _onDataEntered:Function;
		
		public function set onDataEntered(f:Function):void {
			_onDataEntered = f;
		}
		
		public function RTEAddLinkWindowCodeBehind()
		{
			super();
		}
		
		protected function addLinkBtnHandler(me:RTEAddLinkWindow):void {
			if(me.linkName.text == "") {
				Helper.showAlertMessage("Please enter link name");
				return;
			} 
			if(me.linkWebAddress.text == "") {
				Helper.showAlertMessage("Please enter link web address");
				return;
			} 
			if(_onDataEntered != null) {
				_onDataEntered.call(TCRichTextEditor, me.linkName.text, me.linkWebAddress.text);
			}
			removePop();
		}
		
		protected function removePop():void {
			PopUpManager.removePopUp(this);
		}
	}
}