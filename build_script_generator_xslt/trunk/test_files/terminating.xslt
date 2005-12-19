<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output indent="yes" method="xml"/>
	<xsl:template match="component">
		<project>
			<xsl:message terminate="yes">foobar</xsl:message>
		</project>
	</xsl:template>
</xsl:stylesheet>
