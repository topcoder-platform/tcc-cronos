This component needs to be updated in order to provide dependencies between components (actually, between their versions).

For that purpose, a table will be added (schema attached), containing as FKs the id of the version and the id of the child version.

The developer must:
- Add in CompVersion entity a "dependencies" collection (you can choose the more appropriate collection type) containing the children CompVersion's. Please make sure that it's possible to iterate through that list and get all the ids without loading the whole CompVersion.
- Update the hibernate mapping for the new collection
- Create unit tests for the new addition
- Update the class diagram, data model and DDL, as well as any other document that needs to be changed to reflect the addition.

Attached to this bug you'll find Catalog Entities 1.1, to be used as the base. 