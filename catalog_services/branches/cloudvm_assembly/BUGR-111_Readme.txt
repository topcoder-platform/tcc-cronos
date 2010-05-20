The AssetDTO is a class that stores information that is communicated between an application and Hibernate Entities.

In AssetDTO, add the following
1. "comments" field (with getters and setters), and make it be retrieved and saved from CompVersion.comments
2. "phase" field (with getters and setters), and make it be retrieved and saved from CompVersion.phase.name
3. "dependencies" field (with getters and setters), that will contain a list of child asset ids. This must be retrieved and saved from CompVersions.dependencies. Notice that this field is currently being added to Catalog Entities (see https://topcoder.com/bugs/browse/BUGR-110)

The developer must also:
- update the class diagram
- create unit tests

Attached you'll find Catalog Services 1.1