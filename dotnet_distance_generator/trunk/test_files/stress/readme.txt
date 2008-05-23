These XML files are for use with FlatFileMemberDataAccess.  The implementation is provided
to help you test your development code without needing to write your own data access layer.

If you instantiate the class with the path to these files (include trailing slash),
the MemberDataAccess (MDA) will read any properly formatted files.

The proper format is:

<map>
 one or more {
 <coder>
  <coder_id>positive long</coder_id>
  <handle>string</coder_id>
  <image>string</image>
  <distance>double</distance>
  <overlap>int</overlap>
  <country>string</country>
  zero or more of { algorithm_rating, highschool_rating, assembly_rating,
                    marathon_rating, design_rating, development_rating,
                    studio_rating } containing a positive integer      
 </coder>
 }
</map>

There are 5 valid samples provided.

The MDA will return the first element in the file named <coder_id>.xml for calls of
GetMember(coder_id).

The MDA will return the 2..N elements in the file named <coder_id>.xml for calls of
GetRelatedMembers(coder_id, *);

Note that map.coder.distance is a double in these XML.  It will be multiplied by 1000
and converted to integer when loaded as Member.GeographicalDistance.
