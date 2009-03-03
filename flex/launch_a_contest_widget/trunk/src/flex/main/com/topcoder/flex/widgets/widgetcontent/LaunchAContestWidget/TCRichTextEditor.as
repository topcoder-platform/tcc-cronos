package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget
{
	import flash.events.*;
	
	import mx.containers.*;
	import mx.controls.Button;
	import mx.controls.RichTextEditor;
	import mx.controls.Spacer;
	import mx.controls.TextArea;
	import mx.events.FlexEvent;
	import mx.managers.PopUpManager;

	/**
	 * Customized RichTextEditor
	 * 
	 * @author Margarita
	 * @since BUGR-1279
	 */ 
	public class TCRichTextEditor extends VBox
	{
		private var dx:Number;
		
		private var dy:Number;
		
		private var textArea:TextArea;
		
		private var rte:RichTextEditor;
		
		private var resizeStarted:Boolean;
		
		public function TCRichTextEditor()
		{
			super();
			resizeStarted = false;
			this.addEventListener(FlexEvent.CREATION_COMPLETE, initControls);
		}
		
		private function initControls(e:Event):void {
			rte = new RichTextEditor();
			this.addChild(rte);
			rte.includeInLayout = false;
			rte.visible = false;
			
			textArea = rte.removeChild(rte.textArea) as TextArea;
			textArea.addEventListener(Event.CHANGE, onChange);
			textArea.styleName = "RTETextArea";
			
			var canvas:Canvas = new Canvas();
			canvas.addChild(textArea);
			this.addChild(canvas);
			
			textArea.percentHeight = 100;
			textArea.percentWidth = 100;
			
			var resizeBtn:Button = new Button();
			resizeBtn.styleName = "RTEResizeCorner";
			canvas.percentHeight = 100;
			canvas.percentWidth = 100;
			canvas.addChild(resizeBtn);
			
			resizeBtn.addEventListener(MouseEvent.MOUSE_DOWN, handleDragStart, false, 0, true);
            resizeBtn.addEventListener(Event.ENTER_FRAME, handleDragMove, false, 0, true);
            
			rte.toolbar.removeChild(rte.alignButtons);
			rte.toolbar.removeChild(rte.fontFamilyCombo);
			rte.toolbar.removeChild(rte.fontSizeCombo);
			rte.toolbar.removeChild(rte.linkTextInput);
			rte.toolbar.removeChild(rte.colorPicker);
			rte.toolbar.removeChild(rte._RichTextEditor_VRule1);
			rte.toolbar.removeChild(rte._RichTextEditor_VRule2);					

			var but:Button = new Button();
			but.styleName = "RTELinkButton";
            but.addEventListener(MouseEvent.CLICK, addLink);
            rte.toolbar.addChild(but);
            
			var spacer:Spacer = new Spacer();
			spacer.percentWidth = 100;
			spacer.minWidth = 0;
			var spacer2:Spacer = new Spacer();
			spacer2.percentWidth = 100;
			spacer2.minWidth = 0;
			var c:ControlBar = rte.toolbar.parent as ControlBar;
			c.addChildAt(spacer, 0);
			c.addChild(spacer2);
			c.percentWidth = 100;
			
			try {
				this.addChild(c.parent.removeChild(c));
			} catch (e:Error) {}
			
			this.verticalScrollPolicy = "off";
			this.horizontalScrollPolicy = "off";
		}
		
		private function handleDragStart(event:MouseEvent):void
        {
            resizeStarted = true;
            dx = stage.mouseX;
            dy = stage.mouseY;
            parent.addEventListener(MouseEvent.MOUSE_UP, handleDragStop, false, 0, true);
        }
 
        private function handleDragStop(event:MouseEvent):void
        {
            resizeStarted = false;
        	parent.removeEventListener(MouseEvent.MOUSE_UP, handleDragStop, false);
        }
        
        private function handleDragMove(event:Event):void
        {/*
        	if (stage != null !stage.hasEventListener(MouseEvent.MOUSE_UP)) {
                stage.addEventListener(MouseEvent.MOUSE_UP, handleDragStop, false, 0 ,true);
         	}*/
        	if(resizeStarted) {
        		width += stage.mouseX - dx;
            	height += stage.mouseY - dy;
            	width = Math.max(width, 380);
            	height = Math.max(height, 120);
            	dx = stage.mouseX;
            	dy = stage.mouseY;
         	}
        }
        
        private function onChange(event:Event):void {
        	textArea.validateNow();
			var dh:Number = textArea.textHeight + 60 - textArea.height;
			height += Math.max(0, dh);
		}
		
		private function addLink(event:Event):void {
			var _window:RTEAddLinkWindow = RTEAddLinkWindow(PopUpManager.createPopUp(parent, RTEAddLinkWindow, true));
			_window.linkName.text = rte.selection.text;
			_window.onDataEntered = handleLinkAdd;
			PopUpManager.centerPopUp(_window);
		}
		
		private function handleLinkAdd(linkName:String, linkAddress:String):void {
			if(rte.selection.beginIndex == rte.selection.endIndex) {
				textArea.text += linkName;
				textArea.setSelection(textArea.length - linkName.length, textArea.length);
				textArea.validateNow();
			}	
			rte.linkTextInput.text += linkAddress;
			rte.linkTextInput.dispatchEvent(new FlexEvent(FlexEvent.ENTER, false));
			rte.linkTextInput.dispatchEvent(new FocusEvent(FocusEvent.FOCUS_OUT, false));
			
			var html:String = textArea.htmlText;
            
            var addU:RegExp = /TARGET="_blank">/g;
            html = html.replace(addU, "TARGET=\"_blank\"><U>" );
            
            var closeU:RegExp = /<\/A>/g;
            html = html.replace(closeU, "</U></A>" );
            rte.underlineButton.dispatchEvent(new MouseEvent(MouseEvent.CLICK, false));
            
            var uu:RegExp = /<\/U><\/U>/g;
            html = html.replace(uu, "</U>");
            textArea.htmlText = html;
            textArea.validateNow();
            textArea.setSelection(textArea.length, textArea.length);
			//trace(textArea.htmlText);
		}
		
		private var _htmlText:String = "";

		[Bindable("valueCommit")]
		[CollapseWhiteSpace]
		[NonCommittingChangeEvent("change")]
		[Inspectable(category="General")]
	
		public function get htmlText():String
		{
			return textArea ? textArea.htmlText : "";
		}
	
		public function set htmlText(value:String):void
		{
			textArea.htmlText = value;
			textArea.validateNow();
		}
		
		[Bindable("valueCommit")]
		[CollapseWhiteSpace]
		[NonCommittingChangeEvent("change")]
		[Inspectable(category="General")]
	
		public function get text():String
		{
			return textArea ? textArea.text : "";
		}
	
		public function set text(value:String):void
		{
			textArea.text = value;
			textArea.validateNow();
		}
		
	}
}