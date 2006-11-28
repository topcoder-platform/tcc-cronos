Dear Reviewer,

Welcome to the Administration Persistence component. I have included my
build.xml file as well as all libraries that this compnent depends on,
so you should just be able to type "ant" and build the component.
The following steps will need to be taken to configure the environmnet.

1. Run the SQL commands in the file "docs/administration_persistence.sql".
These commands contain the tables defined in the requirements spec, along
with some modifications for SQL Server 2005 that were discussed in the
development forum. This file also contains some stored procedures that
are used by the component and the tests. I _HIGHLY_ recommend that you
use SQL Server 2005 (Express) to test because it would probably be a lot of
work to adapt everything to work with a different database.

2. Modify "test_files/database_config.xml" to reflect the local database
configuration on your machine (username, password, etc.)

3. See the Component Specification for instructions on how to deploy the EJB
using JBoss.

Most of the component can actually be tested without deploying the EJB. If
you look at UnitTest.java I have identified the lines that should be
commented out to test the component without deploying the EJB.

This component was very difficult, with many classes, dependencies, stored
procedures, and configuration requirements. In fact, it has been posted for two
weeks now and I still struggled to get it finished! I hope you will take the
difficulty level into account and be a little lenient when scoring! :-)

Sincerely,
T. C. Developer
