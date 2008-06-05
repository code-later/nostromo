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
	xmlns:redirect="http://xml.apache.org/xalan/redirect"
    extension-element-prefixes="redirect"
    exclude-result-prefixes="xmi uml xsi publish">
    
    <xsl:output method="html"/>
    <!-- ======================================================================================== -->
    <!-- Create content for diagrams -->
    <!-- ======================================================================================== -->
    <xsl:template name="createDiagramContent" match="contents" mode="createElementContent">
        <xsl:param name="titlePattern" select="@name"/>
        <xsl:param name="descriptionPattern" select="''"/>
        <xsl:param name="keywordsWithPatterns" select="''"/>
        <xsl:if test="@xsi:type != 'notation:Diagram'">
            <!-- This is an error - since it means we have hit a contents that we do not know 
			how to generate contents for -->
            <xsl:message terminate="no">
                <xsl:text>The current element was not of type
                    'notation:Diagram'. This means the template contentsElement
                    needs to switch on xsi:type and call one for diagrams and
                    one for what ever this element is.</xsl:text>
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
        </xsl:if>
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
        <!-- Create the navigatino bar (to be copied twice into the document) -->
        <xsl:variable name="navBar">
            <xsl:call-template name="outputNavigationBar">
                <xsl:with-param name="docTitle" select="$title"/>
            </xsl:call-template>
        </xsl:variable>
        <!-- Generate the file name needed for output of the navigation list -->
        <xsl:variable name="elementContentFileName">
            <xsl:call-template name="createElementContentFileName"/>
        </xsl:variable>
        <xsl:variable name="elementContentFullPathFileName">
            <xsl:call-template name="prependFullPathToFileName">
                <xsl:with-param name="fileName" select="$elementContentFileName"/>
            </xsl:call-template>
        </xsl:variable>
        <!-- Generate the package's fully qualified name -->
        <xsl:variable name="packageFullyQualifiedName">
            <xsl:call-template name="generateFullyQualifiedName">
                <xsl:with-param name="includeModelName">
                    <xsl:choose>
                        <xsl:when test="$topLevelIsModel">
                            <xsl:value-of select="'no'"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'yes'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
                <xsl:with-param name="includeMyName" select="'no'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:message terminate="no">
            <xsl:value-of select="$newLine"/>
            <xsl:text>Creating diagram content file for: </xsl:text>
            <xsl:value-of select="@name"/>
            <xsl:value-of select="$newLine"/>
            <xsl:text>The value of @Type is: "</xsl:text>
            <xsl:value-of select="@Type"/>
            <xsl:text>"</xsl:text>
            <xsl:value-of select="$newLine"/>
            <xsl:text>The value of @xsi:type is: "</xsl:text>
            <xsl:value-of select="@xsi:type"/>
            <xsl:text>"</xsl:text>
            <xsl:value-of select="$newLine"/>
        </xsl:message>
        <xsl:variable name="diagramTitle">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'Heading.Diagram'"/>
            </xsl:call-template>
        </xsl:variable>
        <redirect:write file="{$elementContentFullPathFileName}">
            <html lang="en">
                <xsl:call-template name="outputHTMLHeader">
                    <xsl:with-param name="titlePattern" select="$title"/>
                    <xsl:with-param name="titleReplaceToken">
                        <xsl:text>#name</xsl:text>
                    </xsl:with-param>
                    <xsl:with-param name="titleReplaceWith" select="@name"/>
                    <xsl:with-param name="descriptionPattern" select="$description"/>
                    <xsl:with-param name="descriptionReplaceToken">
                        <xsl:text>#name</xsl:text>
                    </xsl:with-param>
                    <xsl:with-param name="descriptionReplaceWith" select="@name"/>
                    <xsl:with-param name="keywordsPattern" select="$keywords"/>
                    <xsl:with-param name="keywordsReplaceToken">
                        <xsl:text>#name</xsl:text>
                    </xsl:with-param>
                    <xsl:with-param name="keywordsReplaceWith" select="@name"/>
                </xsl:call-template>
                
               <body>
                    <xsl:copy-of select="$navBar"/>
                    <hr/>
                    
                    <span class="PackageFullyQualifiedName">
                        <xsl:value-of select="$packageFullyQualifiedName"/>
                        <br/>
                    </span>
                    
                    <span class="ElementTitle">
                        <xsl:copy-of select="$diagramTitle"/>
                        <xsl:value-of select="@name"/>
                        <br/>
                    </span>
                    
                    <!-- Documentation -->
                    <span class="ElementDocumentation">
						<xsl:for-each select="ownedComment">
							<xsl:call-template name="replaceXML2HTMLLineBreaks">
								<xsl:with-param name="theText">
									<xsl:value-of select="@body" />
								</xsl:with-param>
							</xsl:call-template>
                       		 <br/>
						</xsl:for-each>
                    </span>

                    
                    <br/>
                    <!-- Generate HTML for the diagram image including hot spots -->
                    <xsl:call-template name="outputDiagramHTML"/>
                    <!-- Output the properties table -->
                    <xsl:call-template name="generateDiagramPropertiesTable"/>
                    <p/>
                    <hr/>
                    <xsl:copy-of select="$navBar"/>
                </body>
            </html>
        </redirect:write>
    </xsl:template>
</xsl:stylesheet>
