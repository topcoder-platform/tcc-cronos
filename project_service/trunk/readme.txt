The following fixes were done after version 1.0.0

- In ProjectService, I removed @RolesAllowed annotations so that the
annotations are used from the bean class.
- In the bean, I changed the @RolesAllowed to allow the admin as well.
- in getProjectsForUser, I removed the code checking for admin role
since this is already achieved by the annotation in that method.
- in getAllProjects and getProjectById, I changed the way it checks for
the admin role.  I use sessionContext.isCallerInRole instead of getting
the principal, the its profile, then its roles, and checking if the
admin is in the set.
- in updateProject, the comment says that admin can update any project,
however I think that the code was not checking for an admin.  In order
to fix it, I called method getProjectById which already does the
authorization checking.