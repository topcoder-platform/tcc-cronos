The current version of a component is incorrectly mapped, since it's trying to match comp_catalog.current_version with comp_versions.comp_version_id.
Instead, it should be matching comp_catalog.current_version with comp_versions.version AND comp_catalog.component_id with comp_versions.component_id.
This should be fixed from the mapping if possible.
Otherwise, you can do a workaround by making the getters/setters find the current version from the list of versions.

For example, in that case:
comp_catalog.component_id = 100
comp_catalog.current_version = 1

comp_versions.comp_version_id = 200
comp_versions.component_id = 100
comp_versions.version =1

The current version doesn't associate the component with its version, since it's trying to findcomp_catalog.current_version (=1) with comp_versions.comp_version_id (=200).

This must be fixed so that the component and the version are matched, since comp_catalog.component_id=comp_versions.component_id (= 100) and comp_catalog.current_version=comp_versions.version (=1)

You must provide at least one test case validating the bug fix. The test must fail using the previous version of catalog entities, but pass using your fix.
