<?xml version="1.0"?>
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
    exclude-result-prefixes="xmi uml xsi publish">
    
    <xsl:import href="NamedElementContent.xsl"/>
    <xsl:output method="html"/>
     
    <!-- ======================================================================================== -->
    <!-- Create content for Exit Activity -->
    <!-- ======================================================================================== -->
    <xsl:template name="createExitContent" match="exit[@xsi:type='uml:Activity']" mode="createElementContent">
        <xsl:param name="titlePattern" select="@name"/>
        <xsl:param name="descriptionPattern" select="''"/>
        <xsl:param name="keywordsWithPatterns" select="''"/>
        
        <xsl:apply-templates  select="." mode="NamedElement">
            <xsl:with-param name="titleKey" select="'Heading.Activity'"> </xsl:with-param>
            <xsl:with-param name="titlePattern" select="$titlePattern"> </xsl:with-param>
            <xsl:with-param name="descriptionPattern" select="$descriptionPattern"> </xsl:with-param>
            <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"> </xsl:with-param>
        </xsl:apply-templates>
        <!-- for future steps unique to UML Exit Activity -->
    </xsl:template>
</xsl:stylesheet>
