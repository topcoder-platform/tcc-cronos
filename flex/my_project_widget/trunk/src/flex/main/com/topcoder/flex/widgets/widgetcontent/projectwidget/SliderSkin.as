package com.topcoder.flex.widgets.widgetcontent.projectwidget
{

import flash.events.*;

import mx.managers.*;
import mx.skins.Border;

/**
 * The skin for the highlighted state of the track of a Slider. Modified to work with
 * flexlib.controls.VSlider and HSlider.
 */
public class SliderSkin extends Border
{

	public function SliderSkin()
	{
	}

	/**
	 *  The preferred width of this object.
	 */
	override public function get measuredWidth():Number
	{
		return 1;
	}

	/**
	 *  The preferred height of this object.
	 */
	override public function get measuredHeight():Number
	{
		return 2;
	}

	override protected function updateDisplayList(w:Number, h:Number):void
	{
		
		super.updateDisplayList(w, h);

		var themeColor:int = 0xFF0000;
		
		graphics.clear();
		
		
		// Highlight
		drawRoundRect(
			0, h/2, w, 1, 0,
			themeColor, 0.7);
		drawRoundRect(
			0, h/2 - 1, w, 1, 0,
			themeColor, 1);
		drawRoundRect(
			0, h/2 - 2, w, 1, 0,
			themeColor, 0.4);
	}
	
	
}

}