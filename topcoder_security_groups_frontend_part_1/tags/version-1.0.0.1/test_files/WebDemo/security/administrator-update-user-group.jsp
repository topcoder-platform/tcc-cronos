<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>TopCoder Cockpit</title>
<!-- Meta Tags -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- External CSS -->
<link rel="stylesheet" href="css/layout.css" media="all" type="text/css" />
<link rel="stylesheet" href="css/layout-groups.css" media="all" type="text/css" />

<!-- External javascript -->
<script type="text/javascript" src="scripts/jquery-1.7.min.js"></script>
<script type="text/javascript" src="scripts/script-groups.js"></script>

</head>


<body id="page" class="dashboardPage">
<div id="wrapper">
	<div id="wrapperInner">
		<div id="container">
			<div id="content">
				<jsp:include page="includes/header.jsp">
                    <jsp:param name="tabLoc" value="first" />
                </jsp:include>
				<div id="wholeContent">
					<div id="wholeArea">
					
						<div id="wholeAreaHeader">
							<h2 class="updateGroupTitle">Update Group - &lt;<s:property value="#session[groupSessionKey].name"/>&gt;</h2>
						</div>
						<!-- End #wholeAreaHeader -->
						
						<div class="groupDetailsTable">
							<!-- .filterTitle -->
							<div class="filterTitle">
								<div class="filterTitleRight">
									<div class="filterTitleCenter">
										<h3 class="title">Group Details</h3>
									</div><!-- End .filterTitleCenter -->
								</div><!-- End .filterTitleRight -->
							</div>
							<!-- End .filterTitle -->
							
							<div class="groupDetailsTableContainer">
								<table cellpadding="0" cellspacing="0">
									<colgroup>
										<col width="50%" />
										<col width="50%" />
									</colgroup>
									<tbody>
										<tr class="firstRow">
											<td class="firstColumn">Group Name:</td>
											<td><input type="text" class="text" id="inputCreateGroupName" value="<s:property value="#session[groupSessionKey].name"/>" /></td>
										</tr>
										<tr>
											<td class="firstColumn">Group Authorization Attributes:</td>
											<td>
												<div class="attributesWrapper">
													<s:set name="permission" value="#session[groupSessionKey].defaultPermission.toString()"/>
													<input type="radio" name="radio1" id="readRadio"
													<s:if test="#permission == 'READ'">checked="checked"</s:if>
													/>
													<label for="readRadio">Read</label>
													<input type="radio" name="radio1" id="writeRadio" 
													<s:if test="#permission == 'WRITE'">checked="checked"</s:if>
													/>
													<label for="writeRadio">Write</label>
													<input type="radio" name="radio1" id="fullRadio" 
													<s:if test="#permission == 'FULL'">checked="checked"</s:if>
													/>
													<label for="fullRadio">Full</label>
												</div>
											</td>
										</tr>
										<tr>
											<td class="firstColumn">Customer Name:</td>
											<td>
												<select id="selectCreateCustomerName">
													<s:iterator value="clients">
														<option><s:property value="name"/></option>
													</s:iterator>
												</select>
											</td>
										</tr>
										<tr>
											<td class="firstColumn">Billing Accounts:</td>
											<td>
												<div class="multiSelectBox">
													<div class="multiOptionRow">
														<input type="checkbox" id="multiSelect01CheckBox01" />
														<label for="multiSelect01CheckBox01">Select All</label>
													</div>
													<s:iterator value="accounts" status="status">
														<div class="multiOptionRow <s:if test="#status.first">multiOptionRowChecked</s:if>">
															<input type="checkbox" <s:if test="#status.first">checked="checked"</s:if> id="multiSelect01CheckBox02" />
															<label for="multiSelect01CheckBox1<s:property value="#status.index"/>"><s:property value="name"/></label>
														</div>
													</s:iterator>
												</div>
											</td>
										</tr>
										<tr>
											<td class="firstColumn">Projects:</td>
											<td>
												<div class="multiSelectBox">
													<div class="multiOptionRow">
														<input type="checkbox" id="multiSelect02CheckBox01" />
														<label for="multiSelect02CheckBox01">Select All</label>
													</div>
													<s:iterator value="projects" status="status">
														<div class="multiOptionRow">
														<input type="checkbox" <s:if test="#status.first">checked="checked"</s:if> id="multiSelect02CheckBox0<s:property value="#status.index"/>" />
														<label for="multiSelect02CheckBox1<s:property value="#status.index"/>"><s:property value="name"/></label>
														</div>
													</s:iterator>
												</div>
											</td>
										</tr>
										<tr>
											<td class="firstColumn">Resource Restrictions:</td>
											<td>
												<div class="restrictionsWrapper">
													<input type="checkbox" id="billingCheck" />
													<label for="billingCheck">Billing Accounts</label>
													<input type="checkbox" id="projectsCheck" />
													<label for="projectsCheck">Projects</label>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							
						</div>
						
						<div class="groupMembersTable">
							<!-- .filterTitle -->
							<div class="filterTitle">
								<div class="filterTitleRight">
									<div class="filterTitleCenter">
										<h3 class="title">Group Members</h3>
									</div><!-- End .filterTitleCenter -->
								</div><!-- End .filterTitleRight -->
							</div>
							<!-- End .filterTitle -->
							
							<div class="groupMembersTableContainer">
								<table cellpadding="0" cellspacing="0" id="groupMemberTable">
									<colgroup>
										<col width="28%" />
										<col width="28%" />
										<col width="28%" />
										<col width="16%" />
									</colgroup>
									<thead>
										<tr>
											<th>Member Handle / Email Address</th>
											<th>Group User Authorization Attributes</th>
											<th>Access Level</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="firstColumn">
												<input type="text" class="text" />
												<a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a>
											</td>
											<td class="secondColumn">
												<div class="leftPart">
													<input type="radio" name="radioGroupMembers1" value="Group Default" id="gruopRadio1" />
													<label for="gruopRadio1">Group Default</label>
												</div>
												<div class="rightPart">
													<input type="radio" name="radioGroupMembers1" value="User Specific" checked="checked" id="userRadio1" />
													<label for="userRadio1">User Specific</label>
												</div>
											</td>
											<td class="thirdColumn">
												<div class="unit">
													<input type="radio" name="radio2" id="readRadio" />
													<label for="readRadio1">Read</label>
												</div>
												<div class="unit">
													<input type="radio" name="radio2" id="writeRadio" />
													<label for="writeRadio1">Write</label>
												</div>
												<div class="unit">
													<input type="radio" name="radio2" checked="checked" id="fullRadio" />
													<label for="fullRadio1">Full</label>
												</div>
											</td>
											<td class="forthColumn">
												<a href="javascript:;" class="newButton2 removeButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Remove</span></span></span></a>
											</td>
										</tr>
										<tr>
											<td class="firstColumn">
												<input type="text" class="text" />
												<a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a>
											</td>
											<td class="secondColumn">
												<div class="leftPart">
													<input type="radio" name="radioGroupMembers2" value="Group Default" checked="checked" id="gruopRadio2" />
													<label for="gruopRadio2">Group Default</label>
												</div>
												<div class="rightPart">
													<input type="radio" value="User Specific" name="radioGroupMembers2" id="userRadio2" />
													<label for="userRadio2">User Specific</label>
												</div>
											</td>
											<td class="thirdColumn">
												<div class="unit">
													<input type="radio" name="radio3" disabled="disabled" id="readRadio2" />
													<label for="readRadio2">Read</label>
													<div class="clearFixed"></div>
												</div>
												<div class="unit">
													<input type="radio" name="radio3" disabled="disabled" id="writeRadio2" />
													<label for="writeRadio2">Write</label>
													<div class="clearFixed"></div>
												</div>
												<div class="unit">
													<input type="radio" name="radio3" checked="checked" disabled="disabled" id="fullRadio2" />
													<label for="fullRadio2">Full</label>
													<div class="clearFixed"></div>
												</div>
												<div class="clearFixed"></div>
											</td>
											<td class="forthColumn">
												<a href="javascript:;" class="newButton2 removeButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Remove</span></span></span></a>
											</td>
										</tr>
										<tr>
											<td class="firstColumn">
												<input type="text" class="text" />
												<a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a>
											</td>
											<td class="secondColumn">
												<div class="leftPart">
													<input type="radio" name="radioGroupMembers3" value="Group Default" checked="checked" id="gruopRadio3" />
													<label for="gruopRadio3">Group Default</label>
												</div>
												<div class="rightPart">
													<input type="radio" name="radioGroupMembers3" value="User Specific" id="userRadio3" />
													<label for="userRadio3">User Specific</label>
												</div>
											</td>
											<td class="thirdColumn">
												<div class="unit">
													<input type="radio" name="radio4" disabled="disabled" id="readRadio" />
													<label for="readRadio1">Read</label>
												</div>
												<div class="unit">
													<input type="radio" name="radio4" disabled="disabled" id="writeRadio" />
													<label for="writeRadio1">Write</label>
												</div>
												<div class="unit">
													<input type="radio" name="radio4" disabled="disabled" checked="checked" id="fullRadio" />
													<label for="fullRadio1">Full</label>
												</div>
											</td>
											<td class="forthColumn">
												<a href="javascript:;" class="newButton2 removeButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Remove</span></span></span></a>
											</td>
										</tr>
										<tr>
											<td class="firstColumn">
												<input type="text" class="text" />
												<a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a>
											</td>
											<td class="secondColumn">
												<div class="leftPart">
													<input type="radio" name="radioGroupMembers4" value="Group Default" checked="checked" id="gruopRadio4" />
													<label for="gruopRadio4">Group Default</label>
												</div>
												<div class="rightPart">
													<input type="radio" name="radioGroupMembers4" value="User Specific" id="userRadio4" />
													<label for="userRadio4">User Specific</label>
												</div>
											</td>
											<td class="thirdColumn">
												<div class="unit">
													<input type="radio" name="radio5" disabled="disabled" id="readRadio" />
													<label for="readRadio1">Read</label>
												</div>
												<div class="unit">
													<input type="radio" name="radio5" disabled="disabled" id="writeRadio" />
													<label for="writeRadio1">Write</label>
												</div>
												<div class="unit">
													<input type="radio" name="radio5" disabled="disabled" checked="checked" id="fullRadio" />
													<label for="fullRadio1">Full</label>
												</div>
											</td>
											<td class="forthColumn">
												<a href="javascript:;" class="newButton2 removeButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Remove</span></span></span></a>
											</td>
										</tr>
										<tr>
											<td class="firstColumn">
												<input type="text" class="text" />
												<a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a>
											</td>
											<td class="secondColumn">
												<div class="leftPart">
													<input type="radio" name="radioGroupMembers5" value="Group Default" checked="checked" id="gruopRadio5" />
													<label for="gruopRadio5">Group Default</label>
												</div>
												<div class="rightPart">
													<input type="radio" name="radioGroupMembers5" value="User Specific" id="userRadio5" />
													<label for="userRadio5">User Specific</label>
												</div>
											</td>
											<td class="thirdColumn">
												<div class="unit">
													<input type="radio" name="radio6" disabled="disabled" id="readRadio" />
													<label for="readRadio1">Read</label>
												</div>
												<div class="unit">
													<input type="radio" name="radio6" disabled="disabled" id="writeRadio" />
													<label for="writeRadio1">Write</label>
												</div>
												<div class="unit">
													<input type="radio" name="radio6" disabled="disabled" checked="checked" id="fullRadio" />
													<label for="fullRadio1">Full</label>
												</div>
											</td>
											<td class="forthColumn">
												<a href="javascript:;" class="newButton2 removeButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Remove</span></span></span></a>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="groupMembersTableControl">
								<div class="groupMembersTableControlNotice">Enter the handle if the member is already a registered member. Enter the email address of the user if the user is a new user</div>
								<a href="javascript:;" class="newButton2 addMoreMembersButton" id="addGroupMemberButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Add More Members</span></span></span></a>
							</div>
							
						</div>
						
						<div class="commandOperationBox">
							<a href="javascript:;" class="newButton1 triggerModal" rel="#updateGroupConfirmModal"><span class="btnR"><span class="btnC">Update group details</span></span></a>
							<a href="<s:url action="searchGroup"/>" class="newButton1 newButtonGray"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
						</div>
						
					</div>
				</div>

				<!-- End #mainContent -->
				<div id="footer" class="dashboardFooter">
					<!--Update footer-->
					<div class="socialNetwork">
						<span>Follow us on :</span>
						<a href="http://www.twitter.com/TopCoder" class="twitterIcon" title="Follow us on Twitter"></a>
						<a href="http://www.linkedin.com/company/topcoder" class="linkedInIcon" title="Follow us on LinkedIn"></a>
						<a href="http://www.facebook.com/TopCoderInc" class="facebookIcon" title="Follow us on Facebook"></a>
					</div>
					<!--End socialNetwork-->
					<div class="copyright"> 
						<span>Copyright TopCoder, Inc. 2001-2011</span>
						<a href="http://www.topcoder.com/tc?module=Static" title="Terms of Use">Terms of Use</a>
						<a href="http://www.topcoder.com/tc?module=Static" title="Privacy Policy">Privacy Policy</a>
					</div>
					<!--End copyright-->
				</div>
			
			</div>
			<!-- End #content -->
		</div>
		<!-- End #container -->
	</div>
</div>
<!-- End #wrapper -->

<!-- modal -->
<div id="modalBackground"></div>
<div id="new-modal">

	<!-- #deleteGroupModal -->
	<div id="deleteGroupModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Delete Group
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="images/icon-question.png" alt="question" /></div>
			<div class="noticeContent">Are you sure you want to delete group &lt;Group Name&gt; ?</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 triggerNoPreloaderModal" rel="#deleteGroupConfirmModal"><span class="btnR"><span class="btnC">YES</span></span></a>
    			<a href="javascript:;" class="newButton1 newButtonGray closeModal"><span class="btnR"><span class="btnC">NO</span></span></a>
			</div>
		</div>
		<!-- end .modalBody -->
		
		<div class="modalFooter">
			<div class="modalFooterRight">
				<div class="modalFooterCenter"></div>
			</div>
		</div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #deleteGroupModal -->
	
	<!-- #deleteGroupConfirmModal -->
	<div id="deleteGroupConfirmModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Delete Group
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="images/icon-question.png" alt="question" /></div>
			<div class="noticeContent">&lt;Group Name&gt; has been deleted.</div>
			
			<div class="modalCommandBox">
    			<a href="<s:url action="searchGroup"/>" class="newButton1"><span class="btnR"><span class="btnC">OK</span></span></a>
			</div>
		</div>
		<!-- end .modalBody -->
		
		<div class="modalFooter">
			<div class="modalFooterRight">
				<div class="modalFooterCenter"></div>
			</div>
		</div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #deleteGroupConfirmModal -->
	
	<!-- #searchModal -->
	<div id="searchModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Search User Handle
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="searchInputDiv">
				<label>Handle Name:</label>
				<input type="text" class="text" value="Name" />
			</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 searchButton triggerNoPreloaderModal" rel="#searchListModal"><span class="btnR"><span class="btnC">SEARCH</span></span></a>
				<a href="javascript:;" class="newButton1 newButtonGray closeModal"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
			</div>
		</div>
		<!-- end .modalBody -->
		
		<div class="modalFooter">
			<div class="modalFooterRight">
				<div class="modalFooterCenter"></div>
			</div>
		</div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #searchModal -->
	
	<!-- #searchListModal -->
	<div id="searchListModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Search User Handle
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="searchListDiv">
				<p>10 User Handles Found:</p>
				<table border="0" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="30" />
						<col width="316" />
					</colgroup>
					<tbody>
						<tr>
							<td class="firstColumn"><input type="checkbox" checked="checked" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 searchButton closeModal"><span class="btnR"><span class="btnC">ADD USER TO GROUP</span></span></a>
				<a href="javascript:;" class="newButton1 searchButton triggerNoPreloaderModal" rel="#searchModal"><span class="btnR"><span class="btnC">SEARCH AGAIN</span></span></a>
				<a href="javascript:;" class="newButton1 newButtonGray closeModal"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
			</div>
		</div>
		<!-- end .modalBody -->
		
		<div class="modalFooter">
			<div class="modalFooterRight">
				<div class="modalFooterCenter"></div>
			</div>
		</div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #searchListModal -->
	
	<!-- #updateGroupConfirmModal -->
	<div id="updateGroupConfirmModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Group Details Updated
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="images/icon-check.png" alt="question" /></div>
			<div class="noticeContent">&lt;Group Name&gt; details have been succesfully updated.</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span class="btnC">OK</span></span></a>
			</div>
		</div>
		<!-- end .modalBody -->
		
		<div class="modalFooter">
			<div class="modalFooterRight">
				<div class="modalFooterCenter"></div>
			</div>
		</div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #updateGroupConfirmModal -->
	
</div>
<!-- end modal -->

</body>
<!-- End #page -->
</html>

<s:debug></s:debug>
