<?xml version="1.0"?>
<!--                                                                        -->
<!-- Licensed Materials - Property of IBM                                   -->
<!-- Copyright IBM Corp. 2003, 2004.  All Rights Reserved.                  -->
<!--                                                                        -->
<!-- US Government Users Restricted Rights - Use, duplication or disclosure -->
<!-- restricted by GSA ADP Schedule Contract with IBM Corp.                 -->
<!--                                                                        -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" 
    xmlns:xmi="http://www.omg.org/XMI"
    xmlns:uml="http://www.eclipse.org/uml2/1.0.0/UML" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:publish="http://www.ibm.com/Rational/XTools/Publish"
    xmlns:redirect="http://xml.apache.org/xalan/redirect"
    extension-element-prefixes="redirect" 
    xmlns:xalan="http://xml.apache.org/xslt"
    xmlns:java="http://xml.apache.org/xalan/java" 
    exclude-result-prefixes="xmi uml xsi publish xalan java">
    
    <!-- ====================== Imports =================================== -->
    <xsl:import href="../utilities/StringUtility.xsl"/>
    <xsl:import href="GlobalVariables.xsl"/>
    <xsl:import href="ReportUtility.xsl"/>
    <xsl:output method="xml"/>
    
    <!-- The file name for localization lookup, default  is English
         For different locales users must work with the contents of the messages.xml file
     -->
    <xsl:param name="stringTableFileName" select="'messages.xml'"/> 
    
    <!-- Localization support -->
    <xsl:include href="l10n/Localize.xsl" />
    
    <!-- Style sheet parameters -->
    <xsl:param name="outputDir"/>
    <!-- Location for root of output -->
    <xsl:param name="imagesDir"/>
    <!-- Location of images directory -->
    <xsl:param name="tempDir"/>
    <!-- Location of temp directory -->
    <xsl:param name="rootFile" select="&apos;root.html&apos;"/>
    <!-- The name of the root html file -->
    <xsl:param name="overviewFile"/>
    <!-- The name of the overview html -->
    <xsl:param name="foContextDir"/>
    <!-- this is the location of where the FOP userconfig.xml file can be found -->    
    

    <!-- ============= Templates ========================= -->
    <xsl:template match="/">
        <xsl:apply-templates select="publish:report/uml:Model|publish:report/packagedElement"/>
    </xsl:template>
    <!-- This is the template for the diagrams
         All the diagrams are &apos;contents&apos; with attribute xsi:type as notation:Diagram.  -->
    <xsl:template match="contents">
        <xsl:variable name="imgPathName">
            <xsl:value-of select="$outputDir"/>
            <xsl:value-of select="$fileSep"/>
            <xsl:value-of select="$imagesDir"/>
            <xsl:value-of select="@xmi:id"/>.<xsl:value-of select="@publish:imageType"/>
        </xsl:variable>
        <fo:block space-after="6pt" font-size="12pt" line-height="15pt">
            <xsl:value-of select="@publish:qualifiedname"/>
            <xsl:text> -- </xsl:text>
            <xsl:value-of select="@Type"/>
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'ModelDiagramReport.Diagram'"/>
            </xsl:call-template>
        </fo:block>
        <fo:block>
            <fo:external-graphic>
                <xsl:attribute name="src">
                    <xsl:value-of select="$imgPathName"/>
                </xsl:attribute>
            </fo:external-graphic>
        </fo:block>
    </xsl:template>
    <!-- This is the model/package level template -->
    <xsl:template match="uml:Model|packagedElement">
        <xsl:variable name="title" select="@name"/>
        <xsl:variable name="foName">
            <xsl:value-of select="$rootFile"/>
            <xsl:text>.fo</xsl:text>
        </xsl:variable>
        <xsl:variable name="foFullPathName">
            <xsl:value-of select="concat($outputDir, $fileSep, $tempDir, $foName)"/>
        </xsl:variable>
        <xsl:variable name="reportName">
            <xsl:value-of select="$rootFile"/>
        </xsl:variable>
        <xsl:variable name="reportFullPathName">
            <xsl:value-of select="concat($outputDir, $fileSep, $reportName)"/>
        </xsl:variable>
        <redirect:write file="{$foFullPathName}">
            <!-- Create the basic FO tags -->
            <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
                <fo:layout-master-set>
                    <xsl:call-template name="generateLetterMasterSet">
                        <xsl:with-param name="name" select="&apos;main&apos;"/>
                    </xsl:call-template>
                </fo:layout-master-set>
                <fo:page-sequence master-reference="main">
                    <fo:flow flow-name="xsl-region-body">
                        <!-- Title -->
                        <xsl:variable name="formattedTitlePattern">
                            <xsl:call-template name="getLocalizedString">
                                <xsl:with-param name="key" select="'ModelDiagramReport.Title'"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <xsl:variable name="formattedTitle">
                            <xsl:call-template name="replaceSubString">
                                <xsl:with-param name="theString" select="$formattedTitlePattern"/>
                                <xsl:with-param name="matchSubString" select="&apos;#topLevelName&apos;"/>
                                <xsl:with-param name="replaceWith" select="@publish:qualifiedname"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <fo:block text-align="center" font-weight="bold" font-size="16pt" line-height="36pt">
                            <xsl:value-of select="$formattedTitle"/>
                        </fo:block>
                        <xsl:apply-templates select="//contents">
                            <xsl:sort select="@publish:qualifiedname"/>
                        </xsl:apply-templates>
                    </fo:flow>
                </fo:page-sequence>
            </fo:root>
        </redirect:write>
        
        <!-- Call the XTools extension FOToPDF.createPDF to do the creation of the PDF file -->
        <xsl:value-of select="java:com.ibm.ccl.erf.core.internal.XSLT.XSLTExtension.createPDF($foFullPathName, $reportFullPathName, $foContextDir)"/>
        <!-- Clean up generated files -->
        <xsl:variable name="tempDirPath">
            <xsl:value-of select="concat($outputDir, $fileSep, $tempDir)"/>
        </xsl:variable>
        <xsl:value-of select="java:com.ibm.ccl.erf.core.internal.XSLT.XSLTExtension.cleanUpTemporaryDir($tempDirPath)"/>
    </xsl:template>
</xsl:stylesheet>
