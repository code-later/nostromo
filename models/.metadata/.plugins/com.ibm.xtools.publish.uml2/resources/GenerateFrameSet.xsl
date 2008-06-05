<?xml version="1.0"?>
<!--                                                                        -->
<!-- Licensed Materials - Property of IBM                                   -->
<!-- Copyright IBM Corp. 2003, 2004.  All Rights Reserved.                  -->
<!--                                                                        -->
<!-- US Government Users Restricted Rights - Use, duplication or disclosure -->
<!-- restricted by GSA ADP Schedule Contract with IBM Corp.                 -->
<!--                                                                        -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:uml="http://www.eclipse.org/uml2/1.0.0/UML"
	xmlns:redirect="http://xml.apache.org/xalan/redirect"
    extension-element-prefixes="redirect"
    exclude-result-prefixes="uml">
    <xsl:output method="html"/>
    <!-- ======================================================================================== -->
    <!-- Generate the top level html file which sets up the frames -->
    <!-- ======================================================================================== -->
    <xsl:template match="uml:Model| packagedElement | uml:Profile" mode="createBaseFrame">
        <xsl:param name="titlePattern" select="@name"/>
        <xsl:param name="descriptionPattern" select="''"/>
        <xsl:param name="keywordsWithPatterns" select="''"/>
        <!-- Perform pattern replacement for any '#name' with the attribute 'name' -->
        <xsl:variable name="title">
            <xsl:call-template name="replaceSubString">
                <xsl:with-param name="theString">
                    <xsl:value-of select="$titlePattern"/>
                </xsl:with-param>
                <xsl:with-param name="matchSubString">
                    <xsl:text>#name</xsl:text>
                </xsl:with-param>
                <xsl:with-param name="replaceWith">
                    <xsl:value-of select="@name"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="description">
            <xsl:call-template name="replaceSubString">
                <xsl:with-param name="theString">
                    <xsl:value-of select="$descriptionPattern"/>
                </xsl:with-param>
                <xsl:with-param name="matchSubString">
                    <xsl:text>#name</xsl:text>
                </xsl:with-param>
                <xsl:with-param name="replaceWith">
                    <xsl:value-of select="@name"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="keywords">
            <xsl:call-template name="replaceSubString">
                <xsl:with-param name="theString">
                    <xsl:value-of select="$keywordsWithPatterns"/>
                </xsl:with-param>
                <xsl:with-param name="matchSubString">
                    <xsl:text>#name</xsl:text>
                </xsl:with-param>
                <xsl:with-param name="replaceWith">
                    <xsl:value-of select="@name"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="baseFrameFileName">
            <xsl:call-template name="createBaseFrameFileName"/>
        </xsl:variable>
        <!-- Generate the file name needed for output -->
        <xsl:variable name="baseFrameFullFileName">
            <xsl:call-template name="prependFullPathToFileName">
                <xsl:with-param name="fileName" select="$baseFrameFileName"/>
            </xsl:call-template>
        </xsl:variable>
        
        <!-- Create variables for all the file names to be referenced -->
        <xsl:variable name="packageListFileName">
            <xsl:call-template name="createPackageListFileName"/>
        </xsl:variable>
        <xsl:variable name="dojoNavFileName">
            <xsl:call-template name="createDojoNavFileName"/>
        </xsl:variable>
        <xsl:variable name="allElementsFileName">
            <xsl:call-template name="createAllElementsFileName"/>
        </xsl:variable>
        <xsl:variable name="topSummaryFileName">
            <xsl:call-template name="createTopSummaryFileName"/>
        </xsl:variable>
        <!-- Open and write the base frame HTML -->
        <redirect:write file="{$baseFrameFullFileName}">
            <html lang="en">
                <xsl:call-template name="outputHTMLHeader">
                    <xsl:with-param name="titlePattern" select="$title"/>
                    <xsl:with-param name="descriptionPattern" select="$description"/>
                    <xsl:with-param name="keywordsPattern" select="$keywords"/>
                </xsl:call-template>
                
                <xsl:choose>
                    <xsl:when test="$navigation_layout = 'NAVI_DOJO'">
		                <frameset cols="25%,75%">
		                    <frame name="packageListFrame">
		                        <xsl:attribute name="src">
		                            <xsl:value-of select="$packageListFileName"/>
		                        </xsl:attribute>
		                        <xsl:attribute name="title">
		                            <xsl:value-of select="$topLeftFrameTitle"/>
		                        </xsl:attribute>
		                    </frame>
		                    <frame name="elementFrame">
		                        <xsl:attribute name="src">
		                            <xsl:value-of select="$topSummaryFileName"/>
		                        </xsl:attribute>
		                        <xsl:attribute name="title">
		                            <xsl:value-of select="$rightFrameTitle"/>
		                        </xsl:attribute>
		                    </frame>
		                </frameset>
	                	
	                </xsl:when>
	                <xsl:otherwise>
		                <frameset cols="22%,78%">
		                    <frameset rows="30%,70%">
		                        <frame name="packageListFrame">
		                            <xsl:attribute name="src">
		                                <xsl:value-of select="$packageListFileName"/>
		                            </xsl:attribute>
		                            <xsl:attribute name="title">
		                                <xsl:value-of select="$topLeftFrameTitle"/>
		                            </xsl:attribute>
		                        </frame>
		                        <frame name="packageFrame">
		                            <xsl:attribute name="src">
		                                <xsl:value-of select="$allElementsFileName"/>
		                            </xsl:attribute>
		                            <xsl:attribute name="title">
		                                <xsl:value-of select="$bottomLeftFrameTitle"/>
		                            </xsl:attribute>
		                        </frame>
		                    </frameset>
		                    <frame name="elementFrame">
		                        <xsl:attribute name="src">
		                            <xsl:value-of select="$topSummaryFileName"/>
		                        </xsl:attribute>
		                        <xsl:attribute name="title">
		                            <xsl:value-of select="$rightFrameTitle"/>
		                        </xsl:attribute>
		                    </frame>
		                </frameset>
	                </xsl:otherwise>
                </xsl:choose>
                
                <noframes>
                    <h2>Frame Support Expected</h2>
                    <p>This document has been designed to use the HTML Frames
                        feature. If you see this message it means your browser
                        does not support frames; <br/>link to <a>
                            <xsl:attribute name="href">
                                <xsl:value-of select="$topSummaryFileName"/>
                            </xsl:attribute> Non-frame version instead.</a>
                    </p>
                </noframes>
            </html>
        </redirect:write>
    </xsl:template>
</xsl:stylesheet>
