When getting the list of all the versions for a component, each version is stored in the index given by comp_versions.version.

Since versions normally start from 1, element 0 of the list is null, which brings problems in catalog services when iterating through the list.
Also, it's not mandatory to start from 1 and to have consecutive numbers (even if it's the most common situation).

This must be fixed so that the returned list contains one version in each element on the list, and doesn't contain nulls.
This can be done by changing the mapping (but you can't change the public interface), or by making the getters clean the nulls.

You must provide at least one test case validating the bug fix. The test must fail using the previous version of catalog entities, but pass using your fix. 