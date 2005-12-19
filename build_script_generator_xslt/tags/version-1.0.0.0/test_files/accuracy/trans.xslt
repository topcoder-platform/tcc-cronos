<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- this stylesheet produces a copy of the input xml. It is used to normalize
	the xml before comparision in regression tests with xml results. This to some degree
	will protect the tests against differences implementations of XSLT transformers. -->

	<xsl:output method="xml" version="1.0" indent="no"/>

	<!-- identity transform -->
	<xsl:template match="@*|*|processing-instruction()|comment()">
		<xsl:copy>
			<xsl:apply-templates select="*|@*|text()|processing-instruction()|comment()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="component">
    <xsl:copy>
		<xsl:apply-templates select="*|@*|text()|processing-instruction()|comment()"/>
	</xsl:copy>
    <xsl:comment>Accuracy test for Build Script Generator XSLT.</xsl:comment>
	</xsl:template>


</xsl:stylesheet>

