$(document).ready(function(){
	//expand function
	$('.filterTitle .expanded').click(function(){
		var filterTitle = $(this).closest('.filterTitle');
		if(!$(this).hasClass('collapsed')){
			filterTitle.addClass('filterTitleCollapsed');
			$(this).addClass('collapsed');
			$('.filterContainer').hide();
		}else{
			filterTitle.removeClass('filterTitleCollapsed');
			$(this).removeClass('collapsed');
			$('.filterContainer').show();
		}
	});
	
	$('.helloUserLinkTarget').attr('target','_blank');
	
	//table zebra
	$('.tableGroupWrapper .normalTableList tbody tr:odd,.groupMembersTableContainer .normalTableList tbody tr:odd,#searchListModal .searchListDiv tbody tr:odd,.groupMembersTable tbody tr:odd').addClass('even');
	
	
/* Modal window */
	var intPreloaderTimmer = 2000;	//timer
	var strTip = "Loading...";		//string for preloader
	var objPreloaderTimmer;			//timer for modal
	var floatOverlayOpacity = 0.6;	//opacity for modal Background
	
	/* position modal */
	modalPosition = function(){
		var wWidth  = window.innerWidth;
		var wHeight = window.innerHeight;

		if (wWidth==undefined) {
			wWidth  = document.documentElement.clientWidth;
			wHeight = document.documentElement.clientHeight;
		}

		var boxLeft = parseInt((wWidth / 2) - ( $("#new-modal").width() / 2 ));
		var boxTop  = parseInt((wHeight / 2) - ( $("#new-modal").height() / 2 ));

		// position modal
		$("#new-modal").css({
			'margin': boxTop + 'px auto 0 ' + boxLeft + 'px'
		});

		$("#modalBackground").css("opacity", floatOverlayOpacity);
		
		if ($("body").height() > $("#modalBackground").height()){
            $("#modalBackground").css("height", $("body").height() + "px");
		}
	}
	
	/* close modal */
	modalClose = function() {
        $('#modalBackground').hide();
		$('.outLay').hide();
    }
	
	/* load modal (string itemID )*/
	modalLoad = function(itemID) {
		modalClose();
        $('#modalBackground').show();
		$(itemID).show();
		modalPosition();
    }
	
	
	/*
	 * Function modalPreloader
	 *
	 * string itemID e.g. #preloaderModal
	 * string strMarginTop e.g. 40px
	 * object callback e.g. function(){}
	 *
	 */
	modalPreloader = function(itemID,strMarginTop,callback){
		if($('#new-modal #preloaderModal').length == 0){
			var preloaderHtml = '';
			preloaderHtml += '<div id="preloaderModal" class="outLay">';
			preloaderHtml += 	'<div class="modalHeaderSmall">';
			preloaderHtml += 	'<div class="modalHeaderSmallRight">';
			preloaderHtml += 	'<div class="modalHeaderSmallCenter">';
			preloaderHtml += 	'</div></div></div>';
			
			preloaderHtml += 	'<div class="modalBody">';
			preloaderHtml += 	'<img src="images/preloader-loading.gif" alt="Loading" />';
			preloaderHtml += 	'<div class="preloaderTips">';
			preloaderHtml += 	strTip;
			preloaderHtml += 	'</div></div>';
			
			preloaderHtml += 	'<div class="modalFooter">';
			preloaderHtml += 	'<div class="modalFooterRight">';
			preloaderHtml += 	'<div class="modalFooterCenter">';
			preloaderHtml += 	'<div class="</div></div></div>">';
			preloaderHtml += '</div>';
			
			$('#new-modal').append(preloaderHtml);
		}
		modalLoad('#preloaderModal');
		
		if(objPreloaderTimmer) clearTimeout(objPreloaderTimmer);
	    objPreloaderTimmer = setTimeout(function(){
			$('#new-modal #preloaderModal').hide();
			modalLoad(itemID);
			if(strMarginTop) $('#new-modal').css({'margin-top':strMarginTop});
            if(callback){
              callback.call(this);
            } 
        },intPreloaderTimmer);
	}
	
	// double click #modalBackground to close modal
	/*$('#modalBackground').live('dblclick', function(){
		modalClose();
		return false;
	});*/
	// close modal
	$('#new-modal .outLay .closeModal').live('click', function(){
		modalClose();
		return false;
	});
	
	// custom method
	$('.triggerModal').live('click',function(){
		modalPreloader($(this).attr('rel'));
		return false;
	});
	
	// custom method
	$('.triggerNoPreloaderModal').live('click',function(){
		modalLoad($(this).attr('rel'));
		return false;
	});
/* end Modal window */


/* init date-pack */
    if($('.date-pick').length > 0){
        Date.firstDayOfWeek = 0;
    	$(".date-pick").datePicker({startDate:'01/01/2001'});
    }

	
	$('.multiSelectBox').css('overflow-x','hidden');
	//Multi Select checkbox function
	$('.multiSelectBox :checkbox').each(function(){
		$(this).click(function(){
			var parentBox = $(this).closest('.multiSelectBox');
			var parentRow = $(this).parent('.multiOptionRow');
			var parentSelectLen = parentBox.find(':checkbox').length;
			
			if($(this).attr('checked')){
				if($(this).siblings('label').text() == 'Select All'){
					parentBox.find(':checkbox').attr('checked',true);
					parentBox.find('.multiOptionRow').addClass('multiOptionRowChecked');
				}else{
					parentRow.addClass('multiOptionRowChecked');
					if(parentBox.find(':checked').length+1 == parentSelectLen){
						parentBox.find(':checkbox:first').attr('checked',true);
						parentBox.find('.multiOptionRow:first').addClass('multiOptionRowChecked');
					}
				}
			}else{
				if($(this).siblings('label').text() == 'Select All'){
					parentBox.find(':checkbox').attr('checked',false);
					parentBox.find('.multiOptionRow').removeClass('multiOptionRowChecked');
				}else{
					parentBox.find(':checkbox:first').attr('checked',false);
					parentBox.find('.multiOptionRow:first').removeClass('multiOptionRowChecked');
					parentRow.removeClass('multiOptionRowChecked');
				}
			}
		});
	});
	
	$('#selectCreateCustomerName').width($('#selectCreateCustomerName').parent().width());
	$(window).resize(function(){
		$('#selectCreateCustomerName').width($('#selectCreateCustomerName').parent().width());
	});
	
	
	$('#fetchUrlButton').live('click',function(){
		$('.fetchUrlDiv').show();
	});
	
	$(window).resize(function(){
		$('.selectCustomer').css('width',$('.compareText').width()+14);
	});
	
	$('.selectCustomer').css('width',$('.compareText').width()+14);
	
	$('#addMemberButton').live('click',function(){
		for(i=0;i<5;i++){
			$('#moreMember').append('<dd><input type="text" /><a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a><div class="clearFixed"></div></dd>');
		}
		$('#moreMember input').css('width',$('.sendGroupInvitationTable table').width()/2-78);
		$('.newAdminText').css('width',$('.groupDetailsTableContainer table').width()/2-78);
		$('.firstColumn .text').css('width',$('.groupMembersTableContainer th:first').width()-68);
	});
	
	var items = 5;
	$('#addGroupMemberButton').live('click',function(){
		for(i=0;i<5;i++){
			items++;
			$('#groupMemberTable').append('<tr><td class="firstColumn"><input type="text" class="text" /><a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a></td><td class="secondColumn"><div class="leftPart"><input type="radio" name="radioGroupMembers'+items+'" checked="checked" value="Group Default" id="gruopRadio'+items+'" /><label for="gruopRadio'+items+'">Group Default</label></div><div class="rightPart"><input type="radio" name="radioGroupMembers'+items+'" value="User Specific" id="userRadio'+items+'" /><label for="userRadio'+items+'">User Specific</label></div></td><td class="thirdColumn"><div class="unit"><input type="checkbox" disabled="disabled" id="readRadio'+items+'" /><label for="readRadio'+items+'">Read</label></div><div class="unit"><input type="checkbox" disabled="disabled" id="writeRadio'+items+'" /><label for="writeRadio'+items+'">Write</label></div><div class="unit"><input type="checkbox" checked="checked" disabled="disabled" id="fullRadio'+items+'" /><label for="fullRadio'+items+'">Full</label></div></td><td class="forthColumn"><a href="javascript:;" class="newButton2 removeButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Remove</span></span></span></a></td></tr>');
		}
		$('#groupMemberTable tbody tr').removeClass('even');
		$('.tableGroupWrapper .normalTableList tbody tr:odd,.groupMembersTableContainer .normalTableList tbody tr:odd,#searchListModal .searchListDiv tbody tr:odd,.groupMembersTable tbody tr:odd').addClass('even');
		$('#moreMember input').css('width',$('.sendGroupInvitationTable table').width()/2-78);
		$('.newAdminText').css('width',$('.groupDetailsTableContainer table').width()/2-78);
		$('.firstColumn .text').css('width',$('.groupMembersTableContainer th:first').width()-68);
		$('.groupMembersTableContainer table tbody td.secondColumn .leftPart').css('margin-left',($('.groupMembersTableContainer table th:eq(1)').width()-266)/2);
	});
	
	$('#groupMemberTable .removeButton').live('click',function(){
		$(this).parent().parent().remove();
		$('#groupMemberTable tbody tr').removeClass('even');
		$('.tableGroupWrapper .normalTableList tbody tr:odd,.groupMembersTableContainer .normalTableList tbody tr:odd,#searchListModal .searchListDiv tbody tr:odd,.groupMembersTable tbody tr:odd').addClass('even');
	});
	
	$('#groupMemberTable input:radio').live('click',function(){
		if($(this).val() == 'Group Default'){
			$(this).parent().parent().parent().find('input:checkbox').attr('disabled',true);	
		}else{
			$(this).parent().parent().parent().find('input:checkbox').attr('disabled',false);
		}
	});
	
	$('#addGroupMemberButtons').live('click',function(){
		for(i=0;i<5;i++){
			items++;
			$('#groupMemberTables').append('<tr><td class="firstColumn"><input type="text" class="text" value="Handle" /><a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a></td><td class="secondColumn"><div class="leftPart"><input type="radio" name="radioGroupMembers'+items+'" checked="checked" value="Group Default" id="gruopRadio'+items+'" /><label for="gruopRadio'+items+'">Group Default</label></div><div class="rightPart"><input type="radio" name="radioGroupMembers'+items+'" value="User Specific" id="userRadio'+items+'" /><label for="userRadio'+items+'">User Specific</label></div></td><td class="thirdColumn"><div class="unit"><input type="checkbox" disabled="disabled" id="readRadio'+items+'" /><label for="readRadio'+items+'">Read</label></div><div class="unit"><input type="checkbox" disabled="disabled" id="writeRadio'+items+'" /><label for="writeRadio'+items+'">Write</label></div><div class="unit"><input type="checkbox" checked="checked" disabled="disabled" id="fullRadio'+items+'" /><label for="fullRadio'+items+'">Full</label></div></td><td class="forthColumn"><a href="javascript:;" class="newButton2 removeButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Remove</span></span></span></a></td></tr>');
		}
		$('#groupMemberTables tbody tr').removeClass('even');
		$('.tableGroupWrapper .normalTableList tbody tr:odd,.groupMembersTableContainer .normalTableList tbody tr:odd,#searchListModal .searchListDiv tbody tr:odd,.groupMembersTable tbody tr:odd').addClass('even');
		$('#moreMember input').css('width',$('.sendGroupInvitationTable table').width()/2-78);
		$('.newAdminText').css('width',$('.groupDetailsTableContainer table').width()/2-78);
		$('.firstColumn .text').css('width',$('.groupMembersTableContainer th:first').width()-68);
		$('.groupMembersTableContainer table tbody td.secondColumn .leftPart').css('margin-left',($('.groupMembersTableContainer table th:eq(1)').width()-266)/2);
	});
	
	$('#groupMemberTables .removeButton').live('click',function(){
		$(this).parent().parent().remove();
		$('#groupMemberTables tbody tr').removeClass('even');
		$('.tableGroupWrapper .normalTableList tbody tr:odd,.groupMembersTableContainer .normalTableList tbody tr:odd,#searchListModal .searchListDiv tbody tr:odd,.groupMembersTable tbody tr:odd').addClass('even');
	});
	
	$('#groupMemberTables input:radio').live('click',function(){
		if($(this).val() == 'Group Default'){
			$(this).parent().parent().parent().find('input:checkbox').attr('disabled',true);	
		}else{
			$(this).parent().parent().parent().find('input:checkbox').attr('disabled',false);
		}
	});
	
	$('#groupMemberTable input:radio').css('position','relative');
	$('#groupMemberTable input:radio').css('top','-2px');
	$('#groupMemberTable input:checkbox').css('position','relative');
	$('#groupMemberTable input:checkbox').css('top','-1px');
	$('.tableControlPage select').css('position','relative');
	$('.tableControlPage select').css('top','3px');
	
	$(window).load(function(){
		$('.topFilterBox .text').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-18));
		$('.topFilterBox select').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-4));
		$('.topFilterBox .specialColumn').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-4));
		$('.topFilterBox .date-pick').css('width',($('.topFilterBox .specialColumn').width()-140)/2);
		$('.topFilterBox .searchText').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-52));
		$('#moreMember input').css('width',$('.sendGroupInvitationTable table').width()/2-82);
		$('.newAdminText').css('width',$('.groupDetailsTableContainer table').width()/2-82);
		$('.firstColumn .text').css('width',$('.groupMembersTableContainer th:first').width()-72);
		$('.groupMembersTableContainer table tbody td.secondColumn .leftPart').css('margin-left',($('.groupMembersTableContainer table th:eq(1)').width()-266)/2);
	});
	
	$(window).resize(function(){
		$('.topFilterBox .text').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-18));
		$('.topFilterBox select').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-4));
		$('.topFilterBox .specialColumn').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-4));
		$('.topFilterBox .date-pick').css('width',($('.topFilterBox .specialColumn').width()-140)/2);
		$('.topFilterBox .searchText').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-52));
		$('#moreMember input').css('width',$('.sendGroupInvitationTable table').width()/2-82);
		$('.newAdminText').css('width',$('.groupDetailsTableContainer table').width()/2-82);
		$('.firstColumn .text').css('width',$('.groupMembersTableContainer th:first').width()-72);
		$('.groupMembersTableContainer table tbody td.secondColumn .leftPart').css('margin-left',($('.groupMembersTableContainer table th:eq(1)').width()-266)/2);
	});
});