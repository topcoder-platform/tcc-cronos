Expected changes in other components:
1) SecurityGroupsActionException will be added in Part 1 component. It inherits from java.lang.Exception and is used to indicate Struts action execution `error.
2) SecurityGroupsActionConfigurationException will be added in Part 1 component. It inherits from java.lang.RuntimeException and is used to indicate Struts action configuration error.
3) SecurityGroupsActionValidationException will be added in Part 1 component. It inherits from java.lang.Exception and is used to indicate Struts action input validation error.
4) In backend, "rejectReason:String" field will be added to GroupInvitation DTO. It will specify reject reason in case of reject.
5) In backend, InvitationSearchCriteria.masterUserId:Long should be added (as already was specified in frontend architecture).
6) In backed, InvitationSearchCriteria.ownedUserId:Long should be added (as already was specified in frontend architecture).
7) In backed, InvitationSearchCriteria.inviteeHandle and InvitationSearchCriteria.inviteeEmail should be added in order to fullfill frontend needs (administrator-view-pending-approval.html "Invite Handle" and "Invitee Email Address" search criteria).