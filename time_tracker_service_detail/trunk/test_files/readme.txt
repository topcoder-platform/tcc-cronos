This submission needs:
- DbUnit 2.2
- MockEJB 0.6 beta 2
for the testing

Please change all connection configuration in the configuration files and in the
unit test.

Please include test_files and test_files/mock.jar in the class path.

The dump of informix database is available in test_files/dump, please use DDL from
admin, but please be aware that the DDL may be not error free. There are some mistakes in
the syntax. This is not my responsibility as developer, you can either fix it yourself
(it's easy to fix) or ask PM to fix that.

ejb-jar.xml, ifx-ds.xml, jboss.xml are fixed

CS is updated


