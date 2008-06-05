<?xml version="1.0"?>
<!--                                                                        -->
<!-- Licensed Materials - Property of IBM                                   -->
<!-- Copyright IBM Corp. 2003, 2004.  All Rights Reserved.                  -->
<!--                                                                        -->
<!-- US Government Users Restricted Rights - Use, duplication or disclosure -->
<!-- restricted by GSA ADP Schedule Contract with IBM Corp.                 -->
<!--                                                                        -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
>
    <!-- Load the string table -->
    <xsl:variable name="stringTable" select="document($stringTableFileName)"/>
    <!-- English string table. It is used by default whenever a different locale translated file
    isn't found. Currently DBCS translations aren't activated by default and the fall back is to 
    use English strings instead. 
     -->
    <xsl:variable name="stringTableEN" select="document('messages.xml')"/>

    <!-- Look up the parameter key in the localized string table (loaded in the variable stringTable 
       based on the passed in parameter stringTableFileName).  If the key cannot be found then the 
       returned string will be !<key>!.
       
       Parameters:
       key - the look up key matched to the key attribute of string elements in the lookup table
   -->
	

<!-- This will not work under XSLTC.
    The error is thrown when "<xsl:for-each select="$stringTable">" is hit, i.e.
    when we try to access another XML file to retrieve localized strings.
-->
    <!-- faster version of the the same template because it is based on keys -->
	<xsl:key name="tableL10NStringsByKey" match="strings/string" use="@key"/>
    <xsl:template name="getLocalizedString">
        <xsl:param name="key"/>
        <xsl:if test="$key">
        	<xsl:choose>
	        	<xsl:when test="count($stringTable) = 0">
			        <xsl:for-each select="$stringTableEN">
			 	        <xsl:variable name="lookupString" select="key('tableL10NStringsByKey', $key)"/>
				        <xsl:choose>
				            <xsl:when test="$lookupString != ''">
				                <xsl:value-of select="$lookupString"/>
				            </xsl:when>
				            <xsl:otherwise>
				                <xsl:element name="a">
				                    <xsl:attribute name="name">MISSING_L10N_KEY</xsl:attribute>
				                    <xsl:text>!!</xsl:text>
				                    <xsl:value-of select="$key"/>
				                    <xsl:text>!!</xsl:text>
				                </xsl:element>
				            </xsl:otherwise>
				        </xsl:choose>
					</xsl:for-each>
	        	</xsl:when>
	        	<xsl:otherwise>
			        <xsl:for-each select="$stringTable">
			 	        <xsl:variable name="lookupString" select="key('tableL10NStringsByKey', $key)"/>
				        <xsl:choose>
				            <xsl:when test="$lookupString != ''">
				                <xsl:value-of select="$lookupString"/>
				            </xsl:when>
				            <xsl:otherwise>
				                <xsl:element name="a">
				                    <xsl:attribute name="name">MISSING_L10N_KEY</xsl:attribute>
				                    <xsl:text>!!</xsl:text>
				                    <xsl:value-of select="$key"/>
				                    <xsl:text>!!</xsl:text>
				                </xsl:element>
				            </xsl:otherwise>
				        </xsl:choose>
					</xsl:for-each>
	        	</xsl:otherwise>
        	</xsl:choose>
		</xsl:if>
     </xsl:template>
</xsl:stylesheet>






