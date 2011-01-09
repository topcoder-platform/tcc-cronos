There is a bug in database schema which make most of unit tests error.
next_block_start field in table id_sequences should be int8 type.
I've update the DDL script.

How to config:
There are four configuration files:
dbfactory.xml
search_bundle_manager.xml
informix_persistence.xml
Logging.xml
To make the component work well, you should add all of them to ConfigManager

I also update search_bundle_manager.xml. Because the 1.0 version use search bundle 1.3, the origin configuration file not work well any more.

The DDL script is in test_files of review management persistence, named schema.sql
Thank you.

For morehappiness, I don't know which 5 testcases fail. So I cannot attach them. But I've run all of your test cases and they work well.
