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
    
    <!-- Style sheet parameters -->
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
    
    <!-- =================== Style sheet parameters ========================= -->
    <!-- Location for root of output -->
    <xsl:param name="outputDir"/>
    <!-- Location of temp directory -->
    <xsl:param name="tempDir"/>
    <!-- The name of the root html file -->
    <xsl:param name="rootFile" select="&apos;root.html&apos;"/>
    <!-- The name of the overview html file (start page)  -->
    <xsl:param name="overviewFile"/>
    <!-- this is the location of where the FOP userconfig.xml file can be found -->
    <xsl:param name="foContextDir"/>    
    <!-- ============================= Templates ============================== -->
    <!-- Style sheet parameters -->
    <!-- Main template: starts from root element -->
    <xsl:template match="/">
        <xsl:apply-templates select="publish:report/uml:Model|publish:report/packagedElement"/>
    </xsl:template>
    <!-- Template processing model elements 
         Calculates number of classes in each package and stores the maximum
         Calculates number of operations in each class and stores the maximum
         Calculates number of attributes in each class and stores the maximum
  -->
    <xsl:template match="uml:Model|packagedElement">
        <xsl:variable name="title" select="@name"/>
        <xsl:variable name="classCount" select="count (//packagedElement[@xsi:type=&apos;uml:Class&apos;])"/>
        <xsl:variable name="packageCount" select="count (//packagedElement[@xsi:type=&apos;uml:Package&apos;])"/>
        <xsl:variable name="averageClassesPerPackage">
            <xsl:choose>
                <xsl:when test="$packageCount">
                    <xsl:value-of select="$classCount div $packageCount"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="getLocalizedString">
                        <xsl:with-param name="key" select="'MetricsReport.ErrorUnableToMeasure'"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <!--  Calculates number of classes in each package and stores the maximum -->
        <xsl:variable name="packageMaxClassesCount">
            <xsl:for-each select="//packagedElement[@xsi:type=&apos;uml:Package&apos;]">
                <xsl:sort order="descending" select="count (packagedElement[@xsi:type=&apos;uml:Class&apos;])"/>
                <xsl:if test="position()=1">
                    <xsl:value-of select="count (packagedElement[@xsi:type=&apos;uml:Class&apos;])"/>
                </xsl:if>
            </xsl:for-each>
        </xsl:variable>
        <!-- Calculates number of operations in each class and stores the maximum -->
        <xsl:variable name="classMaxOperationsCount">
            <xsl:for-each select="//packagedElement[@xsi:type=&apos;uml:Class&apos;]">
                <xsl:sort order="descending" select="count (ownedOperation)"/>
                <xsl:if test="position()=1">
                    <xsl:value-of select="count (ownedOperation)"/>
                </xsl:if>
            </xsl:for-each>
        </xsl:variable>
        <!-- Calculates number of attributes in each class and stores the maximum -->
        <xsl:variable name="classMaxAttributeCount">
            <xsl:for-each select="//packagedElement[@xsi:type=&apos;uml:Class&apos;]">
                <xsl:sort order="descending" select="count (ownedAttribute)"/>
                <xsl:if test="position()=1">
                    <xsl:value-of select="count (ownedAttribute)"/>
                </xsl:if>
            </xsl:for-each>
        </xsl:variable>
        <!-- determines type of report: Model or Package -->
        <xsl:variable name="reportOnType">
            <xsl:choose>
                <xsl:when test="$topLevelIsModel">
                    <xsl:call-template name="getLocalizedString">
                        <xsl:with-param name="key" select="'MetricsReport.ModelLabel'"/>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="getLocalizedString">
                        <xsl:with-param name="key" select="'MetricsReport.PackageLabel'"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
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
                                <xsl:with-param name="key" select="'MetricsReport.Title'"/>
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
                        <!-- Introduction -->
                        <xsl:variable name="formattedIntroPattern">
                            <xsl:call-template name="getLocalizedString">
                                <xsl:with-param name="key" select="'MetricsReport.Introduction'"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <xsl:variable name="formattedIntro">
                            <xsl:call-template name="replaceSubString">
                                <xsl:with-param name="theString">
                                    <xsl:call-template name="replaceSubString">
                                        <xsl:with-param name="theString">
                                            <xsl:call-template name="replaceSubString">
                                                <xsl:with-param name="theString">
                                                    <xsl:call-template name="replaceSubString">
                                                        <xsl:with-param name="theString" select="$formattedIntroPattern"/>
                                                        <xsl:with-param name="matchSubString" select="&apos;#topLevelName&apos;"/>
                                                        <xsl:with-param name="replaceWith" select="@name"/>
                                                    </xsl:call-template>
                                                </xsl:with-param>
                                                <xsl:with-param name="matchSubString" select="&apos;#reportType&apos;"/>
                                                <xsl:with-param name="replaceWith" select="$reportOnType"/>
                                            </xsl:call-template>
                                        </xsl:with-param>
                                        <xsl:with-param name="matchSubString" select="&apos;#classCount&apos;"/>
                                        <xsl:with-param name="replaceWith" select="$classCount"/>
                                    </xsl:call-template>
                                </xsl:with-param>
                                <xsl:with-param name="matchSubString" select="&apos;#packageCount&apos;"/>
                                <xsl:with-param name="replaceWith" select="$packageCount"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <fo:block space-after="6pt" font-size="12pt" line-height="15pt">
                            <xsl:value-of select="$formattedIntro"/>
                        </fo:block>
                        <!-- Average classes per package -->
                        <xsl:variable name="formattedAveragePattern">
                            <xsl:call-template name="getLocalizedString">
                                <xsl:with-param name="key" select="'MetricsReport.AverageClassesPerPackage'"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <xsl:variable name="formattedAverage">
                            <xsl:call-template name="replaceSubString">
                                <xsl:with-param name="theString" select="$formattedAveragePattern"/>
                                <xsl:with-param name="matchSubString" select="&apos;#average&apos;"/>
                                <xsl:with-param name="replaceWith" select="$averageClassesPerPackage"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <fo:block space-after="6pt" font-size="12pt" line-height="15pt">
                            <xsl:value-of select="$formattedAverage"/>
                        </fo:block>
                        <!-- Package(s) with most classes -->
                        <xsl:variable name="formattedMostClassesPattern">
                            <xsl:call-template name="getLocalizedString">
                                <xsl:with-param name="key" select="'MetricsReport.PackagesMostClasses'"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <xsl:variable name="formattedMostClasses">
                            <xsl:call-template name="replaceSubString">
                                <xsl:with-param name="theString" select="$formattedMostClassesPattern"/>
                                <xsl:with-param name="matchSubString" select="&apos;#maxClassCount&apos;"/>
                                <xsl:with-param name="replaceWith" select="$packageMaxClassesCount"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <fo:block space-after="6pt" font-size="12pt" line-height="15pt">
                            <xsl:value-of select="$formattedMostClasses"/>
                            <!-- Indented list of packages -->
                            <xsl:for-each select="(//packagedElement[@xsi:type=&apos;uml:Package&apos;])[count (packagedElement[@xsi:type=&apos;uml:Class&apos;])=$packageMaxClassesCount]">
                                <xsl:sort select="@publish:qualifiedname"/>
                                <xsl:variable name="iconFullPath">
                                    <xsl:value-of select="@publish:icon"/>
                                    <!--                &lt;xsl:value-of select=&quot;concat(&apos;url(&quot;&apos;, @publish:icon, &apos;&quot;)&apos;)&quot;/&gt;-->
                                    <!--                &lt;xsl:value-of select=&quot;concat(&apos;url(&quot;&apos;, $outputDir, $fileSep, @publish:icon, &apos;)&apos;)&quot;/&gt;-->
                                    <!--                &lt;xsl:value-of select=&quot;concat($outputDir, $fileSep, @publish:icon)&quot;/&gt;-->
                                </xsl:variable>
                                <xsl:message>
                                    <xsl:value-of select="$newLine"/>
                                    <xsl:text>Adding a graphic to the FO XML. The icon attribute is &apos;</xsl:text>
                                    <xsl:value-of select="@publish:icon"/>
                                    <xsl:text>&apos; and the full uri is &apos;</xsl:text>
                                    <xsl:value-of select="$iconFullPath"/>
                                    <xsl:text>&apos;.</xsl:text>
                                </xsl:message>
                                <fo:block font-size="12pt" line-height="15pt" start-indent="0.5cm">
                                    <!--   At the moment images are not rendered properly from our Java - FOP is not able to find them and 
       I cannot figure out how to make it.
 -->
                                    <!--
                &lt;fo:external-graphic width=&quot;10pt&quot; height=&quot;10pt&quot;&gt;
                  &lt;xsl:attribute name=&quot;src&quot;&gt;&lt;xsl:value-of select=&quot;$iconFullPath&quot;/&gt;&lt;/xsl:attribute&gt;
                &lt;/fo:external-graphic&gt;
 -->
                                    <xsl:value-of select="@publish:qualifiedname"/>
                                </fo:block>
                            </xsl:for-each>
                        </fo:block>
                        <!-- Clases with the most operations -->
                        <xsl:variable name="formattedMostOperationsPattern">
                            <xsl:call-template name="getLocalizedString">
                                <xsl:with-param name="key" select="'MetricsReport.ClassesMostOperations'"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <xsl:variable name="formattedMostOperations">
                            <xsl:call-template name="replaceSubString">
                                <xsl:with-param name="theString" select="$formattedMostOperationsPattern"/>
                                <xsl:with-param name="matchSubString" select="&apos;#maxOperationsCount&apos;"/>
                                <xsl:with-param name="replaceWith" select="$classMaxOperationsCount"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <fo:block space-after="6pt" font-size="12pt" line-height="15pt">
                            <xsl:value-of select="$formattedMostOperations"/>
                            <!-- Indented list of classes -->
                            <xsl:for-each select="(//packagedElement[@xsi:type=&apos;uml:Class&apos;])[count(ownedOperation)=$classMaxOperationsCount]">
                                <xsl:sort select="@publish:qualifiedname"/>
                                <fo:block font-size="12pt" line-height="15pt" start-indent="0.5cm">
                                    <!--          At the moment images are not rendered properly from our Java - FOP is not able to find them and 
              I cannot figure out how to make it. -->
                                    <!--
                &lt;fo:external-graphic width=&quot;10pt&quot; height=&quot;10pt&quot;&gt;
                  &lt;xsl:attribute name=&quot;src&quot;&gt;&lt;xsl:value-of select=&quot;@publish:icon&quot;/&gt;&lt;/xsl:attribute&gt;
                &lt;/fo:external-graphic&gt;
              -->
                                    <xsl:value-of select="@publish:qualifiedname"/>
                                </fo:block>
                            </xsl:for-each>
                        </fo:block>
                        <!-- Clases with the most attributes -->
                        <xsl:variable name="formattedMostAttributesPattern">
                            <xsl:call-template name="getLocalizedString">
                                <xsl:with-param name="key" select="'MetricsReport.ClassesMostAttributes'"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <xsl:variable name="formattedMostAttributes">
                            <xsl:call-template name="replaceSubString">
                                <xsl:with-param name="theString" select="$formattedMostAttributesPattern"/>
                                <xsl:with-param name="matchSubString" select="&apos;#maxAttributesCount&apos;"/>
                                <xsl:with-param name="replaceWith" select="$classMaxAttributeCount"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <fo:block space-after="6pt" font-size="12pt" line-height="15pt">
                            <xsl:value-of select="$formattedMostAttributes"/>
                            <!-- Indented list of clases -->
                            <xsl:for-each select="(//packagedElement[@xsi:type=&apos;uml:Class&apos;])[count (ownedAttribute)=$classMaxAttributeCount]">
                                <xsl:sort select="@publish:qualifiedname"/>
                                <fo:block font-size="12pt" line-height="15pt" start-indent="0.5cm">
                                    <!--           At the moment images are not rendered properly from our Java - FOP is not able to find them and 
                I cannot figure out how to make it. -->
                                    <!--
                &lt;fo:external-graphic width=&quot;10pt&quot; height=&quot;10pt&quot;&gt;
                  &lt;xsl:attribute name=&quot;src&quot;&gt;&lt;xsl:value-of select=&quot;@publish:icon&quot;/&gt;&lt;/xsl:attribute&gt;
                &lt;/fo:external-graphic&gt;
                -->
                                    <xsl:value-of select="@publish:qualifiedname"/>
                                </fo:block>
                            </xsl:for-each>
                        </fo:block>
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
