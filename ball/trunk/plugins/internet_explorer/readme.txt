Installer generation
--------------------------
The  NAntContrib is required to create the MSI package.

Task to execute: nant installer. Installer will be generated to build\dist\installer directory.

The license agreement that need to be shown during installation is placed in docs/license.rtf  (it need to be in rtf format!).


Configuration
----------------
The configuration file: conf/config.xml. The following properties need to be set to actual values:

Namespace: Orpheus.Plugin.Toolbar
Property: context  - the address of the game server.

Namespace: Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers
property: polling_url  - the url of polling action
property: test_object_url  - the url of test object action
