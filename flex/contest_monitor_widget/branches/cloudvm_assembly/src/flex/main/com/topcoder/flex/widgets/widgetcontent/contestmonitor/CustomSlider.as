package com.topcoder.flex.widgets.widgetcontent.contestmonitor
{
	import mx.controls.HSlider;
	
	public class CustomSlider extends HSlider
	{
		public function CustomSlider()
		{
		}
		
		public function update():void {
			updateDisplayList(unscaledWidth, unscaledHeight);
		}
	}
}