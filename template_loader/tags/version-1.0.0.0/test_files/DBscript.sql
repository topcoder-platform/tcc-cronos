-------------------------------------------------------------------------------------
-- Tree 1 - each template hierarchy has only one template, and they use the same name
--                        Start
--                        /    \
--                      Java   .NET
--                      /   \
--               WebServer Weblogic
--                    /
--                  Debug
--                  /   \
--              DebugOn DebugOff
--
insert into temp_hier values(1, "Start", 1);
insert into temp_hier values(2, "Java", 1);
insert into temp_hier values(3, ".NET", 1);
insert into temp_hier values(4, "WebServer", 2);
insert into temp_hier values(5, "Weblogic", 2);
insert into temp_hier values(6, "Debug", 4);
insert into temp_hier values(7, "DebugOn", 6);
insert into temp_hier values(8, "DebugOff", 6);

insert into templates values(1, "Start", "des", "uri", "DEST_FILENAME");
insert into templates values(2, "Java", "des", "uri", "DEST_FILENAME");
insert into templates values(3, ".NET", "des", "uri", "DEST_FILENAME");
insert into templates values(4, "WebServer", "des", "uri", "DEST_FILENAME");
insert into templates values(5, "Weblogic", "des", "uri", "DEST_FILENAME");
insert into templates values(6, "Debug", "des", "uri", "DEST_FILENAME");
insert into templates values(7, "DebugOn", "des", "uri", "DEST_FILENAME");
insert into templates values(8, "DebugOff", "des", "uri", "DEST_FILENAME");

insert into temp_hier_mapping values(1, 1, 1);
insert into temp_hier_mapping values(2, 2, 2);
insert into temp_hier_mapping values(3, 3, 3);
insert into temp_hier_mapping values(4, 4, 4);
insert into temp_hier_mapping values(5, 5, 5);
insert into temp_hier_mapping values(6, 6, 6);
insert into temp_hier_mapping values(7, 7, 7);
insert into temp_hier_mapping values(8, 8, 8);


-------------------------------------------------------------------------------------
-- Tree 2 - a template hierarchy has no children and no template
--
insert into temp_hier values(11, "singleton", 11);


-------------------------------------------------------------------------------------
-- Tree 3 - a template hierarchy has 10 templates
--
insert into temp_hier values(21, "ten_templates", 21);

insert into templates values(10, "template0", "des", "uri", "DEST_FILENAME");
insert into templates values(11, "template1", "des", "uri", "DEST_FILENAME");
insert into templates values(12, "template2", "des", "uri", "DEST_FILENAME");
insert into templates values(13, "template3", "des", "uri", "DEST_FILENAME");
insert into templates values(14, "template4", "des", "uri", "DEST_FILENAME");
insert into templates values(15, "template5", "des", "uri", "DEST_FILENAME");
insert into templates values(16, "template6", "des", "uri", "DEST_FILENAME");
insert into templates values(17, "template7", "des", "uri", "DEST_FILENAME");
insert into templates values(18, "template8", "des", "uri", "DEST_FILENAME");
insert into templates values(19, "template9", "des", "uri", "DEST_FILENAME");

insert into temp_hier_mapping values(2110, 21, 10);
insert into temp_hier_mapping values(2111, 21, 11);
insert into temp_hier_mapping values(2112, 21, 12);
insert into temp_hier_mapping values(2113, 21, 13);
insert into temp_hier_mapping values(2114, 21, 14);
insert into temp_hier_mapping values(2115, 21, 15);
insert into temp_hier_mapping values(2116, 21, 16);
insert into temp_hier_mapping values(2117, 21, 17);
insert into temp_hier_mapping values(2118, 21, 18);
insert into temp_hier_mapping values(2119, 21, 19);

-------------------------------------------------------------------------------------
-- Tree 4 - a normal template hierarchy
--          root has 3 templates
--          level_1_B has 2 templates
--          level_2_B has 1 templates
--          level_2_D has 4 templates
--
--                       root
--                      /    \
--            level_1_A       level_1_B
--            /    \           /    \
--   level_2_A level_2_B  level_2_C level_2_D
--
--
insert into temp_hier values(39, "root", 39);
insert into temp_hier values(38, "level_1_A", 39);
insert into temp_hier values(37, "level_1_B", 39);
insert into temp_hier values(36, "level_2_A", 38);
insert into temp_hier values(35, "level_2_B", 38);
insert into temp_hier values(34, "level_2_C", 37);
insert into temp_hier values(33, "level_2_D", 37);

insert into templates values(111, "root_1", "des", "uri", "DEST_FILENAME");
insert into templates values(112, "root_2", "des", "uri", "DEST_FILENAME");
insert into templates values(113, "root_3", "des", "uri", "DEST_FILENAME");
insert into templates values(221, "level_1_B_1", "des", "uri", "DEST_FILENAME");
insert into templates values(222, "level_1_B_2", "des", "uri", "DEST_FILENAME");
insert into templates values(321, "level_2_B_1", "des", "uri", "DEST_FILENAME");
insert into templates values(341, "level_2_D_1", "des", "uri", "DEST_FILENAME");
insert into templates values(342, "level_2_D_2", "des", "uri", "DEST_FILENAME");
insert into templates values(343, "level_2_D_3", "des", "uri", "DEST_FILENAME");
insert into templates values(344, "level_2_D_4", "des", "uri", "DEST_FILENAME");

insert into temp_hier_mapping values(39111, 39, 111);
insert into temp_hier_mapping values(39112, 39, 112);
insert into temp_hier_mapping values(39113, 39, 113);
insert into temp_hier_mapping values(37221, 37, 221);
insert into temp_hier_mapping values(37222, 37, 222);
insert into temp_hier_mapping values(35321, 35, 321);
insert into temp_hier_mapping values(33341, 33, 341);
insert into temp_hier_mapping values(33342, 33, 342);
insert into temp_hier_mapping values(33343, 33, 343);
insert into temp_hier_mapping values(33344, 33, 344);

-------------------------------------------------------------------------------------
-- Tree 5 - a bad template hierarchy, one template has an empty uri
insert into temp_hier values(99, "bad", 99);
insert into templates values(99, "bad", "des", "", "DEST_FILENAME");
insert into temp_hier_mapping values(9999, 99, 99);