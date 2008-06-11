Short version: Please upgrade to .NET Framework 1.1 Service Pack 1 or greater to receive the full benefits of this component.  This only needs to be done on the server hosting the ASP.NET application.  It can be downloaded from:

http://support.microsoft.com/kb/885055/

----

Longer version:
There is a bug in .NET Framework v1.1 that does not honor the IsPostBack boolean property when Server.Transfer() is called to redirect to another page.  This is frequently encountered in front controller implementations like this component.  You can read about the bug here:

http://support.microsoft.com/kb/817036/

This bug has been fixed in .NET Framework v1.1 service pack 1 and greater:

http://support.microsoft.com/kb/821758/

This component will NOT use Server.Transfer() if it determines you have .NET Framework v1.1.  It will instead use Response.Redirect() which behaves a little differently including having the side effect of changing the client's URL (i.e. they potentially lose some functionality such as bookmarking the correct page).  If you are using .NET Framework v1.1 service pack 1 (or any other version besides 1.1) it will use Server.Transfer() as designed.

By upgrading to .NET v.1.1 service pack 1 you are assuring this component will work fully as designed (and you get the benefits of the other fixes in the service pack).

----

You can determine the installed .NET Framework version by looking at the following registry value:

Key Name: HKEY_LOCAL_MACHINE\Software\Microsoft\NET Framework Setup\NDP\v1.1.4322 
Value: SP  (tells you which service pack is installed)

For future versions of the .NET Framework, you will just need to change the part of the key named v1.1.4322 to be the version you are interested in.  Note that this key will not be present if only v1.0 is installed.