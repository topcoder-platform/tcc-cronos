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
				<div id="header"> <a href="javascript:;" class="logo">Groups</a>
					<div id="tabs0">
						<!-- the left tabs -->
						<ul>
							<li> <a href="javascript:;"><span>Dashboard</span></a> </li>
							<li> <a href="javascript:;"><span>Projects</span></a> </li>
							<li> <a href="javascript:;"><span>CoPilots</span></a> </li>
							<li class="on"> <a href="javascript:;"><span>Groups</span></a> </li>
						</ul>
					</div>
					<!-- End #tabs0 -->
					<div class="helloUser">
						<ul>
							<li> <strong>Hello</strong> <a href="javascript:;" class="coderTextGreen">lunarkid</a> | </li>
							<li class="helloUserLink"> <a href="javascript:;">Logout</a>| </li>
							<li class="helloUserLink"> <a href="javascript:;" class="helloUserLinkTarget" >Help</a> </li>
						</ul>
					</div>
					<!-- End .helloUSer -->
					<div>
						<!-- TC Logo -->
						<a href="http://community.topcoder.com" class="TCLogo"><img src="images/tc-logo.png" alt="TopCoder"></a>
						<p class="lookCP dashBoardLookCP">Looking for Community Portal? <a href="javascript:;" onclick="window.open('http://topcoder.com/home/');"><strong>Go There Now</strong></a></p>
					</div>
					<div id="tabs1">
						<ul>
							<li class="firstTabs"> <a href="administrator-view-user-groups.html"><span class="dashboardSpan">Groups</span></a> </li>
							<li class="on secondTabs"><a href="administrator-send-group-invitation.html"><span class="dashboardSpan">Send Group Invitation</span></a> </li>
							<li class="thirdTabs"> <a href="administrator-view-pending-approval.html"><span class="dashboardSpan">Pending Approvals</span></a> </li>
							<li class="forthTabs"> <a href="administrator-create-new-administrator.html"><span class="dashboardSpan">Create New Administrator User</span></a> </li>
							<li class="fifthTabs"> <a href="administrator-acc-auditing-information.html"><span class="dashboardSpan">Audit Information</span></a> </li>
							<li class="sixthTabs"> <a href="administrator-fetch-registration-url.html"><span class="dashboardSpan">Fetch Registration URL</span></a> </li>
						</ul>
					</div>
				</div>
				<!-- End #header -->
				<div id="wholeContent">
					<div id="wholeArea">
					
						<div id="wholeAreaHeader">
							<h2 class="groupTitle sendEmailTitle">Send Group Invitation</h2>
						</div>
						<!-- End #wholeAreaHeader -->
						
						<div class="sendGroupInvitationTable">
							<table cellpadding="0" cellspacing="0">
								<colgroup>
									<col width="50%" />
									<col width="50%" />
								</colgroup>
								<tbody>
									<tr>
										<td class="firstColumn">
											<div class="unitLabel">Select the group you want to invite the users to (Optional):</div>
											<div class="noteLabel">Note: If you do not select a specific group, the users will be invited so they can register and simply use the application without joining any specific group.</div>
										</td>
										<td class="lastColumn">
											<div class="multiSelectBox">
												<div class="multiOptionRow">
													<input type="checkbox" id="multiSelect01CheckBox01" />
													<label for="multiSelect01CheckBox01">Select All</label>
												</div>
												<div class="multiOptionRow multiOptionRowChecked">
													<input type="checkbox" checked="checked" id="multiSelect01CheckBox02" />
													<label for="multiSelect01CheckBox02">Group Name</label>
												</div>
												<div class="multiOptionRow">
													<input type="checkbox" id="multiSelect01CheckBox03" />
													<label for="multiSelect01CheckBox03">Group Name</label>
												</div>
												<div class="multiOptionRow">
													<input type="checkbox" id="multiSelect01CheckBox04" />
													<label for="multiSelect01CheckBox04">Group Name</label>
												</div>
												<div class="multiOptionRow">
													<input type="checkbox" id="multiSelect01CheckBox05" />
													<label for="multiSelect01CheckBox05">Group Name</label>
												</div>
												<div class="multiOptionRow">
													<input type="checkbox" id="multiSelect01CheckBox06" />
													<label for="multiSelect01CheckBox06">Group Name</label>
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td class="firstColumn">
											<div class="unitLabel">Enter the member handle / email address of the users you want to invite to the groups:</div>
										</td>
										<td class="lastColumn">
											<dl id="moreMember">
												<dd>
													<input type="text" />
													<a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a>
													<div class="clearFixed"></div>
												</dd>
												<dd>
													<input type="text" />
													<a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a>
													<div class="clearFixed"></div>
												</dd>
												<dd>
													<input type="text" />
													<a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a>
													<div class="clearFixed"></div>
												</dd>
												<dd>
													<input type="text" />
													<a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a>
													<div class="clearFixed"></div>
												</dd>
												<dd>
													<input type="text" />
													<a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a>
													<div class="clearFixed"></div>
												</dd>
											</dl>
											<dl>
												<dd class="lastDd">
													<a href="javascript:;" class="newButton2 addMoreMembersButton" id="addMemberButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Add More User</span></span></span></a>
												</dd>
											</dl>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						
						<div class="commandOperationBox">
							<a href="javascript:;" class="newButton1 triggerModal" rel="#deleteGroupConfirmModal"><span class="btnR"><span class="btnC">SEND INVITE</span></span></a>
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
	
	<!-- #deleteGroupConfirmModal -->
	<div id="deleteGroupConfirmModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Send Invite Confirmation
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="images/icon-check.png" alt="correct" /></div>
			<div class="noticeContent noticeContentSendInviteConfirmation">An invite has been sent to join group &lt;Group Name&gt; to members &lt;member handle&gt;, &lt;member handle&gt; and &lt;email address&gt;.</div>
			
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
	
</div>
<!-- end modal -->



</body>
<!-- End #page -->
</html>
