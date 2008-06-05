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
    
    <xsl:output method="html" indent="yes"/>
    
    
    <!-- ======================================================================================== -->
    <!-- Create content for fragments -->
    <!-- ======================================================================================== -->
    <xsl:template name="createExecutionOccurrenceContent" match="fragment" mode="createElementContent">
        <xsl:param name="titlePattern" select="@name"/>
        <xsl:param name="descriptionPattern">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'Description.Content'"/>
            </xsl:call-template>
        </xsl:param>
        <xsl:param name="keywordsWithPatterns" select="''"/>
        
		<xsl:choose>
		    <xsl:when test="@xsi:type='uml:BehaviorExecutionSpecification'">
				<xsl:apply-templates select="." mode="NamedElement">
					<xsl:with-param name="titleKey" select="'Heading.ExecutionOccurrence'"/>
					<xsl:with-param name="titlePattern" select="$titlePattern"/>
					<xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
					<xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
				</xsl:apply-templates>
			</xsl:when>
		    <xsl:when test="@xsi:type='uml:OccurrenceSpecification'">
				<xsl:apply-templates select="." mode="NamedElement">
					<xsl:with-param name="titleKey" select="'Heading.OccurrenceSpecification'"/>
					<xsl:with-param name="titlePattern" select="$titlePattern"/>
					<xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
					<xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
				</xsl:apply-templates>
			</xsl:when>
			<xsl:when test="@xsi:type='uml:CombinedFragment'">
				<xsl:apply-templates select="." mode="NamedElement">
					<xsl:with-param name="titleKey" select="'Heading.CombinedFragment'"/>
					<xsl:with-param name="titlePattern" select="$titlePattern"/>
					<xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
					<xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
				</xsl:apply-templates>
			</xsl:when>
	        <xsl:when test="@xsi:type='uml:Stop'">
				<xsl:apply-templates select="." mode="NamedElement">
					<xsl:with-param name="titleKey" select="'Heading.Stop'"/>
					<xsl:with-param name="titlePattern" select="$titlePattern"/>
					<xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
					<xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
				</xsl:apply-templates>
			</xsl:when>
			<xsl:otherwise>
				<!-- This is an error - since it means we have hit a fragments that we do not know 
			how to generate contents for -->
				<xsl:message terminate="no">
					<xsl:text>The current element was not handled by the
						template createExecutionOccurrenceContent.</xsl:text>
					<xsl:value-of select="$newLine"/>
					<xsl:text>Name: </xsl:text>
					<xsl:value-of select="@name"/>
					<xsl:value-of select="$newLine"/>
					<xsl:text>Type: </xsl:text>
					<xsl:value-of select="@xsi:type"/>
					<xsl:value-of select="$newLine"/>
					<xsl:text>ID: </xsl:text>
					<xsl:value-of select="@xmi:id"/>
				</xsl:message>
			</xsl:otherwise>
		</xsl:choose>
    </xsl:template>
    
</xsl:stylesheet>
