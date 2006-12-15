Note:
a) Edit test_files/com/topcoder/naming/jndiutility/JNDIUtils.properties,
	set context.default.url to a valid local directory so that the JNDIUtil can work properly.
	
b) UserProfile is a dependent component of GameOperationLogic, and it depends on IDGenerator and DBConnectionFactory.
	So, IDGenerator and DBConnectionFactory should be configured properly before using this component.
	1) import id_sequences.sql to create a database table for IDGenerator.
	2) test_files/DBConnectionFactory, configure the JDBC driver and db url to property value.

c) Besides the dependency components listed in the CS, there are more components that are needed to make this component work.
	j2ee.jar
	log4j.jar
	SearchBuilder
	FrontController
	jdbc drivers
	xerces.jar - used by test cases
	fscontext.jar and providerutil.jar File System JNDI provider, http://java.sun.com/products/jndi/serviceproviders.html

