<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>TopCoder Cockpit</title>
<!-- Meta Tags -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- External CSS -->
<link rel="stylesheet" href="css/layout.css" media="all" type="text/css" />
<link rel="stylesheet" href="css/layout-groups.css" media="all" type="text/css" />
<link rel="stylesheet" href="css/datepicker.css" media="all" type="text/css"/>

<!-- External javascript -->
<script type="text/javascript" src="scripts/jquery-1.7.min.js"></script>
<script type="text/javascript" src="scripts/date.prev.js"></script>
<script type="text/javascript" src="scripts/date.js"></script>
<script type="text/javascript" src="scripts/jquery.datePicker.js"></script>
<script type="text/javascript" src="scripts/script-groups.js"></script>

</head>


<body id="page" class="dashboardPage">
<div id="wrapper">
	<div id="wrapperInner">
		<div id="container">
			<div id="content">
				<div id="header"> <a href="javascript:;" class="logo">Group Invitation Statuses</a>
					<div id="tabs0">
						<!-- the left tabs -->
						<ul>
							<li> <a href="javascript:;"><span>Dashboard</span></a> </li>
							<li> <a href="javascript:;"><span>Projects</span></a> </li>
							<li> <a href="javascript:;"><span>CoPilots</span></a> </li>
							<li class="on"> <a href="javascript:;"><span>Group Invitation Statuses</span></a> </li>
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
					
				</div>
				<!-- End #header -->
				<div id="wholeContent">
					<div id="wholeArea">
					
						<div id="wholeAreaHeader">
							<h2 class="groupTitle">Group Invitation Statuses</h2>
						</div>
						<!-- End #wholeAreaHeader -->
						
						<!-- .topFilterBox --> 
						<div class="topFilterBox">
							
							<!-- .filterTitle -->
							<div class="filterTitle">
								<div class="filterTitleRight">
									<div class="filterTitleCenter">
										<a href="javascript:;" class="expanded"></a>
										<h4>Filter</h4>
									</div><!-- End .filterTitleCenter -->
								</div><!-- End .filterTitleRight -->
							</div>
							<!-- End .filterTitle -->
							
							<!-- .filterContainer -->
							<div class="filterContainer">
								<dl class="filterUserGroup">
									<dd>
										<div class="leftSide">
											<span class="label">Group Name:</span>
											<input type="text" class="text" />
										</div>
										
										<div class="rightSide">
											<span class="label">Access Rights:</span>
											<div class="secondColumn">
												<input type="checkbox" checked="checked" id="readRadio" />
												<label for="readRadio">Read</label>
												<input type="checkbox" checked="checked" id="writeRadio" />
												<label for="writeRadio">Write</label>
												<input type="checkbox" checked="checked" id="fullRadio" />
												<label for="fullRadio">Full</label>
											</div>
										</div>
										<div class="clearFixed"></div>
									</dd>
									<dd>
										<div class="leftSide">
											<span class="label">Invitation Received Between:</span>
											<div class="secondColumn specialColumn">
												<div class="fLeft">
													<input type="text" name="formData.startDate" readonly="readonly" id="startDateReceived" class="text date-pick dp-applied" />
												</div>
												<div class="fRight">
													<input type="text" name="formData.endDate" readonly="readonly" id="endDateReceived" class="text date-pick dp-applied" />
												</div>
												and
												<div class="clearFixed"></div>
											</div>
										</div>
										
										<div class="rightSide">
											<span class="label">Invitation Accepted Between:</span>
											<div class="secondColumn specialColumn">
												<div class="fLeft">
													<input type="text" name="formData.startDate" readonly="readonly" id="startDateAccepted" class="text date-pick dp-applied" />
												</div>
												<div class="fRight">
													<input type="text" name="formData.endDate" readonly="readonly" id="endDateAccepted" class="text date-pick dp-applied" />
												</div>
												and
												<div class="clearFixed"></div>
											</div>
										</div>
										<div class="clearFixed"></div>
									</dd>
								</dl>
								<div class="filterContainerCommand2">
									<a class="button6" href="<s:url action="viewInvitationStatusAction"/>"><span class="left"><span class="right">FILTER</span></span></a>
									<div class="clearFixed"></div>
								</div>
							</div>
							<!-- End .filterContainer -->
							
						</div>
						<!-- End .topFolderBox -->
						
						<div class="mainContent">
							
							<div class="tableGroupWrapper">
								<table cellpadding="0" cellspacing="0" class="normalTableList">
									<colgroup>
										<col width="20%" />
										<col width="20%" />
										<col width="20%" />
										<col width="20%" />
										<col width="20%" />
									</colgroup>
									<thead>
										<tr>
											<th>Group Name</th>
											<th>Access Rights</th>
											<th>Invitation Received On</th>
											<th>Invitation Accepted On</th>
											<th>Invitation Approved On</th>
										</tr>
									</thead>
									<tbody>
										<tr class="firstRow">
											<td>Group Name Lorem ipsum dolor sit amet, consectetur adipisicing elit</td>
											<td>Full</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
										</tr>
										<tr>
											<td>Group Name</td>
											<td>Full</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
										</tr>
										<tr>
											<td>Group Name</td>
											<td>Full</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
										</tr>
										<tr>
											<td>Group Name</td>
											<td>Full</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
										</tr>
										<tr>
											<td>Group Name</td>
											<td>Full</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
										</tr>
										<tr>
											<td>Group Name</td>
											<td>Full</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
										</tr>
										<tr>
											<td>Group Name</td>
											<td>Full</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
										</tr>
										<tr>
											<td>Group Name</td>
											<td>Full</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
										</tr>
										<tr>
											<td>Group Name</td>
											<td>Full</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
										</tr>
										<tr>
											<td>Group Name</td>
											<td>Full</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
											<td>xx-xx-xxxx xx:xx am</td>
										</tr>
									</tbody>
								</table>
							</div>
							
							<div class="tableControlPage">
								<div class="leftSide">Showing <strong>1</strong> to <strong>10</strong> of <strong>32</strong> entries</div>
								<div class="rightSide">
									<strong class="label">Show:</strong>
									<select>
										<option>10</option>
										<option>25</option>
										<option>50</option>
										<option>All</option>
									</select>
									<span class="label">per Page</span>
									<ul>
										<li><a href="javascript:;" class="prevPage disable">Prev</a></li>
										<li><strong>1</strong></li>
										<li><a href="javascript:;">2</a></li>
										<li><a href="javascript:;" class="nextPage">Next</a></li>
									</ul>
								</div>
								<span class="corner bl"></span>
								<span class="corner br"></span>
							</div>
							<!-- End .tableControlPage -->
							
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
	
</div>
<!-- end modal -->



</body>
<!-- End #page -->
</html>
