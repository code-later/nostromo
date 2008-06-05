<?xml version="1.0" encoding="UTF-8"?>
<!--                                                                        -->
<!-- Licensed Materials - Property of IBM                                   -->
<!-- Copyright IBM Corp. 2003, 2004.  All Rights Reserved.                  -->
<!--                                                                        -->
<!-- US Government Users Restricted Rights - Use, duplication or disclosure -->
<!-- restricted by GSA ADP Schedule Contract with IBM Corp.                 -->
<!--                                                                        -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:xmi="http://www.omg.org/XMI"
    xmlns:uml="http://www.eclipse.org/uml2/1.0.0/UML"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:publish="http://www.ibm.com/Rational/XTools/Publish"
    xmlns:xalan="http://xml.apache.org/xalan"
    exclude-result-prefixes="xmi uml xsi publish xalan">

    <xsl:output method="html"/>
    <!-- The template replaceSubString will replace all occurances of a sub string with a replacement string.
       If there are no occurrences of matchSubString in theString then the result of the template is theString.
       
       Parameters:
         theString - the string which contains the sub strings to be replaced
         matchSubString - the sub string to be replaced
         replaceWith - the new string to replace matchSubString with
  -->
    <xsl:template name="replaceSubString">
        <xsl:param name="theString"/>
        <xsl:param name="matchSubString"/>
        <xsl:param name="replaceWith"/>
        <xsl:choose>
            <xsl:when test="contains($theString,$matchSubString)">
                <xsl:value-of select="substring-before($theString,$matchSubString)"/>
                <xsl:value-of select="$replaceWith"/>
                <xsl:call-template name="replaceSubString">
                    <xsl:with-param name="theString" select="substring-after($theString,$matchSubString)"/>
                    <xsl:with-param name="matchSubString" select="$matchSubString"/>
                    <xsl:with-param name="replaceWith" select="$replaceWith"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$theString"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>