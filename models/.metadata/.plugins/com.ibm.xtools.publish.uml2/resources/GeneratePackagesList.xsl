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
    extension-element-prefixes="redirect" exclude-result-prefixes="xmi uml xsi publish">
    <xsl:output method="html" />
    <!-- ======================================================================================== -->
    <!-- Generate the package list html file for the top left navigation window and the over 
       view summary content page for the top level model or Package 
       
       In the split package mode (one XML per package) navigation frame html should be processed as a separate step
       
     -->
    <!-- ======================================================================================== -->
    <xsl:template match="packagedElement[@xsi:type='uml:Package'] | uml:Model | uml:Profile" mode="createPackageLists">
        <xsl:param name="titlePattern" select="@name"/>
        <xsl:param name="descriptionPattern" select="&apos;&apos;"/>
        <xsl:param name="keywordsWithPatterns" select="&apos;&apos;"/>
        <!-- Perform pattern replacement for any &apos;#name&apos; with the attribute &apos;name&apos; -->
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
        <!--Generate the file names needed for referencing -->
        <xsl:variable name="allElementsFileName">
            <xsl:call-template name="createAllElementsFileName"/>
        </xsl:variable>
        <xsl:variable name="packageListFileName">
            <xsl:call-template name="createPackageListFileName"/>
        </xsl:variable>
        <xsl:variable name="topSummaryFileName">
            <xsl:call-template name="createTopSummaryFileName"/>
        </xsl:variable>
        <!-- Generate the file name needed for output -->
        <xsl:variable name="packageListFullPathName">
            <xsl:call-template name="prependFullPathToFileName">
                <xsl:with-param name="fileName" select="$packageListFileName"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="topSummaryFullPathName">
            <xsl:call-template name="prependFullPathToFileName">
                <xsl:with-param name="fileName" select="$topSummaryFileName"/>
            </xsl:call-template>
        </xsl:variable>
        <!-- Localized strings -->
        <xsl:variable name="allElementsHeading">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="&apos;Heading.AllElements&apos;"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="packagesHeading">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="&apos;Heading.Packages&apos;"/>
            </xsl:call-template>
        </xsl:variable>
        <!-- Create the navigation bar (to be copied twice into the document) -->
        <xsl:variable name="navBar">
            <xsl:call-template name="outputNavigationBar">
                <xsl:with-param name="appearsOn" select="&apos;top&apos;"/>
                <xsl:with-param name="docTitle" select="$title"/>
            </xsl:call-template>
        </xsl:variable>
        <!-- Open a file for the master package list (and fill it with an href pointing to the package list of 
         each owned package - in the for-each below) -->
        <!-- Top left hand frame -->
        <!-- xxx-navigation-frame.html -->
        <redirect:write file="{$packageListFullPathName}"/>
        <redirect:write file="{$packageListFullPathName}" append="true">
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
                    <span class="Title">
                        <xsl:value-of select="$title"/>
                    </span>
                    <table class="PackageList">
                        <tr>
                            <td nowrap="">
                                <a target="packageFrame">
                                    <xsl:attribute name="href">
                                        <xsl:value-of select="$allElementsFileName"/>
                                    </xsl:attribute>
                                    <xsl:copy-of select="$allElementsHeading"/>
                                </a>
                                <p/>
                                <span class="ClassesTableHeading">
                                    <xsl:copy-of select="$packagesHeading"/>
                                </span>
                                <br/>
                                <!-- Create the package content file and fill it with header information 
                                    xxx-top-summary.html
                                -->
                                <redirect:write file="{$topSummaryFullPathName}"/>
                                <redirect:write file="{$topSummaryFullPathName}" append="true">
                                    <html lang="en">
                                        <xsl:call-template name="outputHTMLHeader">
                                            <xsl:with-param name="titlePattern" select="$title"/>
                                            <xsl:with-param name="descriptionPattern" select="$description"/>
                                            <xsl:with-param name="keywordsPattern" select="$keywords"/>
                                        </xsl:call-template>
                                        <body>
                                            <xsl:copy-of select="$navBar"/>
                                            <hr/>
                                            <span class="ContentPageTitle">
                                                <xsl:value-of select="$title"/>
                                            </span>
                                            <p/>
                                            <xsl:message>
                                                <xsl:value-of select="$newLine"/>
                                                <xsl:text>Checking for the default diagram. Value of expression
                                                    count(contents[@xsi:type=&apos;notation:Diagram&apos; and
                                                    @isDefault='true']) is </xsl:text>
                                                <xsl:value-of select="count(contents[@xsi:type=&apos;notation:Diagram&apos; and @name=&apos;Main&apos;])"/>
                                                <xsl:text>.</xsl:text>
                                            </xsl:message>
                                            <xsl:if test="count(contents[@xsi:type=&apos;notation:Diagram&apos; and @isDefault='true']) &gt; 0">
                                                <xsl:for-each select="contents[@xsi:type=&apos;notation:Diagram&apos; and @isDefault='true']">
                                                    <xsl:message terminate="no">
                                                        <xsl:value-of select="$newLine"/>
                                                        <xsl:text>Processing the top level main diagram.</xsl:text>
                                                        <xsl:value-of select="$newLine"/>
                                                    </xsl:message>
                                                    <p/>
                                                    <!-- Generate HTML for the main diagram image including hot spots -->
                                                    <xsl:call-template name="outputDiagramHTML"/>
                                                </xsl:for-each>
                                                <p/>
                                            </xsl:if>
                                            <!-- Create a &apos;Packages&apos; table for the content HTML -->
                                            <!-- Begin a table for the packages -->
                                            <xsl:variable name="allPackages" select="descendant::packagedElement[@xsi:type=&apos;uml:Package&apos;]"/>
                                            <table class="PackagesTable" border="1">
                                                <xsl:if test="count ($allPackages) &gt; 0">
                                                    <tr>
                                                        <td class="PackagesTitle" colspan="2">
                                                            <xsl:value-of select="$packagesHeading"/>
                                                        </td>
                                                    </tr>
                                                </xsl:if>
                                                <!-- Loop for each nested package creating an entry in the top element&apos;s summary 
									                table and a navigation list file for that package -->
                                                <xsl:for-each select="$allPackages">
                                                    <xsl:sort select="@publish:qualifiedname"/>
                                                    <xsl:variable name="packageListFileName">
                                                        <xsl:call-template name="createPackageNavigationListFileName"/>
                                                    </xsl:variable>
                                                    <xsl:variable name="packageContentFileName">
                                                        <xsl:call-template name="createElementContentFileName"/>
                                                    </xsl:variable>
                                                    <xsl:variable name="iconImageFileName">
                                                        <xsl:call-template name="createElementIconFileName"/>
                                                    </xsl:variable>
                                                    <!-- Create a refernece to each package&apos;s navigation list HTML file -->
                                                    <redirect:write file="{$packageListFullPathName}" append="true">
                                                        <!-- Create a reference to each element&apos;s content HTML file -->
                                                        <xsl:call-template name="outputImageAndTextLinkHTML">
                                                            <xsl:with-param name="link" select="$packageListFileName"/>
                                                            <xsl:with-param name="image">
	                                                            <xsl:if test="@publish:icon != &apos;&apos;">
		                                                            <xsl:value-of select="$iconImageFileName"/>
	                                                            </xsl:if>
                                                            </xsl:with-param>
                                                            <xsl:with-param name="text">
                                                            <xsl:call-template name="generateFullyQualifiedName"/>
                                                            </xsl:with-param>
                                                            <xsl:with-param name="target" select="&apos;packageFrame&apos;"/>
                                                        </xsl:call-template>
                                                        <br/>
                                                    </redirect:write>
                                                    <!-- Add an entry in the package table for the top element&apos;s summary HTML table -->
                                                    <redirect:write file="{$topSummaryFullPathName}" append="true">
                                                        <tr>
                                                            <td class="ClassTableEntryLink">
                                                            <xsl:call-template name="outputImageAndTextLinkHTML">
                                                            <xsl:with-param name="link" select="$packageContentFileName"/>
                                                            <xsl:with-param name="image">
	                                                            <xsl:if test="@publish:icon != &apos;&apos;">
		                                                            <xsl:value-of select="$iconImageFileName"/>
	                                                            </xsl:if>
                                                            </xsl:with-param>
                                                            <xsl:with-param name="text">
                                                            <xsl:call-template name="generateFullyQualifiedName"/>
                                                            </xsl:with-param>
                                                            </xsl:call-template>
                                                            </td>
                                                        </tr>
                                                    </redirect:write>
                                                    <!-- Create the package&apos;s navigaion HTML file referenced above 
                                                    
                                                        Generate xxx-package-frame.html contents
                                                    -->
                                                    <xsl:call-template name="SplitMode_createAPackageElementList">
                                                        <xsl:with-param name="masterDocTitle" select="$title"/>
                                                        <xsl:with-param name="titlePattern">
                                                            <xsl:text>#name (</xsl:text>
                                                            <xsl:value-of select="$title"/>
                                                            <xsl:text>)</xsl:text>
                                                        </xsl:with-param>
                                                    </xsl:call-template>
                                                </xsl:for-each>
                                                <!-- Close the &apos;Packages&apos; table -->
                                            </table>
                                            <p/>
                                            <!-- Output the properties table -->
                                            <xsl:apply-templates select="publish:properties"/>
                                            <hr/>
                                            <xsl:copy-of select="$navBar"/>
                                        </body>
                                    </html>
                                </redirect:write>
                            </td>
                        </tr>
                    </table>
                </body>
            </html>
        </redirect:write>
    </xsl:template>
    
    <!-- 1. package summary file html file: xxx-package-frame.html (bottom left frame) -->
    <!-- this should apply to all xml files generated for each package -->
    <xsl:template name="createPackageSummaryFrame" match="packagedElement[@xsi:type='uml:Package'] | 
        uml:Model | uml:Profile" mode="SplitMode_PackageSummaryFrame">
        
        <xsl:param name="titlePattern" select="@name"/>
        <xsl:param name="descriptionPattern" select="&apos;&apos;"/>
        <xsl:param name="keywordsWithPatterns" select="&apos;&apos;"/>
        
        <!-- Perform pattern replacement for any &apos;#name&apos; with the attribute &apos;name&apos; -->
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
        <xsl:call-template name="SplitMode_createAPackageElementList">
            <xsl:with-param name="masterDocTitle" select="$title"/>
            <xsl:with-param name="titlePattern">
                <xsl:text>#name (</xsl:text>
                <xsl:value-of select="$title"/>
                <xsl:text>)</xsl:text>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    
    <!-- 3. Create initial top left frame contents (xxx-navigation-frame.html) TLF -->
    <xsl:template name="createPackagesNavigationFrame" match="packagedElement[@xsi:type='uml:Package'] | uml:Model | uml:Profile" mode="SplitMode_PackagesNavigationFrame">
        <!-- parameters -->
        <xsl:param name="titlePattern" select="@name"/>
        <xsl:param name="descriptionPattern" select="&apos;&apos;"/>
        <xsl:param name="keywordsWithPatterns" select="&apos;&apos;"/>
        
        <!-- variables -->
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
        <xsl:variable name="packagesHeading">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="&apos;Heading.Packages&apos;"/>
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
        <xsl:variable name="allElementsHeading">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="&apos;Heading.AllElements&apos;"/>
            </xsl:call-template>
        </xsl:variable>
        <!--Generate the file names needed for referencing -->
        <xsl:variable name="allElementsFileName">
            <xsl:call-template name="createAllElementsFileName"/>
        </xsl:variable>
        <xsl:variable name="packageListFileName">
            <xsl:call-template name="createPackageListFileName"/>
        </xsl:variable>
        <!-- Generate the file name needed for output -->
        <xsl:variable name="packageListFullPathName">
            <xsl:call-template name="prependFullPathToFileName">
                <xsl:with-param name="fileName" select="$packageListFileName"/>
            </xsl:call-template>
        </xsl:variable>
        <!-- xxx-navigation-frame.html -->
        <redirect:write file="{$packageListFullPathName}"/>
        <redirect:write file="{$packageListFullPathName}" append="true">
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
                    <span class="Title">
                        <xsl:value-of select="$title"/>
                    </span>
                    <table class="PackageList">
                        <tr>
                            <td nowrap="">
                                <a target="packageFrame">
                                    <xsl:attribute name="href">
                                        <xsl:value-of select="$allElementsFileName"/>
                                    </xsl:attribute>
                                    <xsl:copy-of select="$allElementsHeading"/>
                                </a>
                                <p/>
                                <span class="ClassesTableHeading">
                                    <xsl:copy-of select="$packagesHeading"/>
                                </span>
                                <br/>
                                
                                <!-- use packages.xml file for the top level element -->
                                <xsl:variable name="packagesFilePath">
                                    <xsl:call-template name="constructAuxilliaryXmlFilepath">
                                        <xsl:with-param name="guid" select="@xmi:id"/>
                                        <xsl:with-param name="suffix" select="'packages.xml'"/>
                                    </xsl:call-template>
                                </xsl:variable>
                                <xsl:variable name="allPackages" select="document($packagesFilePath)/publish:packages"/>
                                <xsl:for-each select="$allPackages/publish:package">
                                    <xsl:sort select="@publish:qualifiedname"/>
                                    <xsl:call-template name="outputImageAndTextLinkHTML">
                                        <xsl:with-param name="link">
                                            <xsl:call-template name="createPackageNavigationListFileName"/>
                                        </xsl:with-param>
                                        <xsl:with-param name="image">
                                        <xsl:if test="@publish:icon != &apos;&apos;">
                                            <xsl:call-template name="createElementIconFileName"/>
                                        </xsl:if>
                                        </xsl:with-param>
                                        <xsl:with-param name="text">
                                            <xsl:call-template name="generateFullyQualifiedName"/>
                                        </xsl:with-param>
                                        <xsl:with-param name="target" select="&apos;packageFrame&apos;"/>
                                    </xsl:call-template>
                                    <br/>
                                </xsl:for-each>
                            </td>
                        </tr>
                    </table>
                </body>
            </html>
        </redirect:write>
    </xsl:template>
    
    <!-- 4. create contents for each element in the package -->
    <xsl:template name="createElementContentsForPackage"  match="packagedElement[@xsi:type='uml:Package'] | uml:Model | uml:Profile" mode="SplitMode_ElementContentsForPackage">
        <xsl:param name="titlePattern" select="@name"/>
        
        <!-- Perform pattern replacement for any '#name' with the attribute 'name' -->
        <xsl:variable name="masterDocTitle">
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
        
        <!-- Loop for each contained element creating an entry in the list -->
        <xsl:for-each select="$globalTopLevelAllSupportedElementTypes">
			<!-- Create the content HTML for the element -->
			<xsl:apply-templates
				mode="createElementContent" select=".">
				<xsl:with-param name="titlePattern" select="$masterDocTitle"/>
			</xsl:apply-templates>
        </xsl:for-each>
    </xsl:template>
    
</xsl:stylesheet>
