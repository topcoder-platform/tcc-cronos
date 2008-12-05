1 please update the jboss_home in build-dependencies.xml if you need
2 execute deploy ant task to create an ear and copy it to your jboss AS
3 start up the server using new run.bat
4 now you can run the test

note: about Confluence_Management, please use the Confluence_Management.jar under test_files/lib, 
I correct a bug of original one(a deplicate assetName value for @XmlType annotated on Page class)
and I add a private constructor on ConfluencePageCreationResult make it a normal java bean used in web service(this is allowed by designer already)

