<?xml version="1.0"?>
<!--                                                                        -->
<!-- Licensed Materials - Property of IBM                                   -->
<!-- Copyright IBM Corp. 2004.  All Rights Reserved.                        -->
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
    <xsl:output method="html"/>
    <!-- The template outputNavigaionBar will place a navigation bar in the output stream.  The 
	     links will be formed by passing the appropriate element to the methods in 
	     "FileNameGernation.xsl".  The default parameters are designed to give some kind of default; 
	     however to make it really useful some of the parameters should probably be passed in 
	     (e.g. prevElement, nextElement, appearsOn and location).
	  
       Parameters:
         currentElement  - The element who's content page the navigation bar appears.  Default is 
                           the current context which is usually sufficient.
         docTitle        - The title to appear to the right of the navigaion links.  The default 
                           is an empty string which means not title will appear on the right of 
                           the navbar.
         overviewLink    - The link text for the over view page (start page that link to this 
                           model's documentation).
         topElement      - The root element used to link to the top page.  By default this 
                           will be the root package or model node in the soiurce XML this is 
                           usually correct.
         owningPackage   - The owning package for the element who's content page is being 
                           generated.  By default it will look for the anccestor of the context 
                           node that has a type uml:Package which is usually correct.
         appearsOn       - This flag indicates the type of page that the navigation bar is to 
                           appear on.  This can be "content", "package" or "top" the default 
                           is "content".  This governs which navigaion links will he present and 
                           what lable will be marked highlighed:
                             content  - Both the package and top links will be active and the 
                                        content link will be highlighted.
                             package  - The top link will be active, the content link will 
                                        not and the package link will be highlighted.
                             top      - The top link will be highlighted and niether the 
                                        package and content link will be.
                                        
        
    <xsl:variable name="navBar">
      <xsl:call-template name="outputNavigationBar">
        <xsl:with-param name="docTitle" select="$masterDocTitle"/>
        <xsl:with-param name="bookMarkLinks" select="xalan:nodeset($bookmarks)"/>
      </xsl:call-template>
    </xsl:variable>
	-->
    <xsl:template name="outputNavigationBar">
        <xsl:param name="currentElement" select="."/>
        <xsl:param name="docTitle" select="''"/>
        <xsl:param name="overviewLink" select="concat('../', $overviewFile)"/>
        <xsl:param name="owningPackage" select="ancestor::packagedElement[@xsi:type='uml:Package'][1]"/>
        <xsl:param name="appearsOn" select="'content'"/>
        <xsl:param name="bookMarkLinks" select="."/>
        
        <!-- Trace messages -->
        <xsl:message terminate="no">
            <xsl:value-of select="$newLine"/>
            <xsl:text>========== Generating navigation bar for element named: "</xsl:text>
            <xsl:value-of select="@name"/>
            <xsl:text>"</xsl:text>
            <xsl:value-of select="$newLine"/>
            <!-- <xsl:text> Top element name: "</xsl:text>
            <xsl:value-of select="$topElement/@name"/>
            <xsl:text>"</xsl:text> -->
            <xsl:value-of select="$newLine"/>
            <xsl:text> Parent name: "</xsl:text>
            <xsl:value-of select="../@name"/>
            <xsl:text>"</xsl:text>
            <xsl:value-of select="$newLine"/>
            
            <xsl:text> Owning package name: "</xsl:text>
            <xsl:value-of select="$owningPackage/@name"/>
            <xsl:text>"</xsl:text>
            <xsl:value-of select="$newLine"/>
            <xsl:text> Bookmark link elements "</xsl:text>
            <xsl:value-of select="$bookMarkLinks"/>
            <xsl:text>"</xsl:text>
            <xsl:value-of select="$newLine"/>
            <xsl:text> Bookmark link element count is "</xsl:text>
            <xsl:value-of select="count ($bookMarkLinks/publish:navbookmarks/*/publish:bookmarklink)"/>
            <xsl:text>"</xsl:text>
            <xsl:value-of select="$newLine"/>
        </xsl:message>
        <!-- Generate file name links form passed in navigation elements -->
        <xsl:variable name="topLink">
            <xsl:call-template name="createTopSummaryFileName"/>
        </xsl:variable>
        <!-- Element is owned by top level package use the over view summary link -->
        <xsl:variable name="owningPackageLink">
        	<xsl:choose>
        		<xsl:when test = "count($owningPackage) > 0">
		        	<xsl:call-template name="createElementContentFileName">
		        		<xsl:with-param name="element" select="$owningPackage"/>
		        	</xsl:call-template>
	        	</xsl:when>
	        	<xsl:otherwise>
	        		<xsl:value-of select="$topLink"/>
	        	</xsl:otherwise>
			</xsl:choose>        	
        </xsl:variable>
        
        <xsl:message>
        	<xsl:text> PACKAGE VALUE: </xsl:text>
        	<xsl:value-of select="$owningPackageLink"></xsl:value-of>
        </xsl:message>
        
        <!-- Load customer visible strings from the string table -->
        <xsl:variable name="overviewLinkText">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'NavigationBar.OverviewLinkText'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="topLinkText">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'NavigationBar.TopLinkText'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="packageLinkText">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'NavigationBar.PackageLinkText'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="elementLinkText">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'NavigationBar.ElementLinkText'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="summaryTitleText">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'NavigationBar.SummaryTitle'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="detailTitleText">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'NavigationBar.DetailTitle'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="LinkText">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'NavigationBar.'"/>
            </xsl:call-template>
        </xsl:variable>
        <table class="NavigationBar">
            <tr>
                <td>
                    <table class="NavBarLeft">
                        <tr>
                            <td class="NavBarMainLink">
                                <a target="_top">
                                    <xsl:attribute name="href">
                                        <xsl:value-of select="$overviewLink"/>
                                    </xsl:attribute>
                                    <xsl:copy-of select="$overviewLinkText"/>
                                </a>
                            </td>
                            <xsl:choose>
                                <xsl:when test="$appearsOn='top'">
                                    <td class="NavBarMainHighlight">
                                        <xsl:copy-of select="$topLinkText"/>
                                    </td>
                                    <td class="NavBarMainPrint">
                                        <xsl:value-of select="$packageLinkText"/>
                                    </td>
                                    <td class="NavBarMainPrint">
                                        <xsl:copy-of select="$elementLinkText"/>
                                    </td>
                                </xsl:when>
                                <xsl:otherwise>
                                    <td class="NavBarMainLink">
                                        <a>
                                        <xsl:attribute name="href">
                                        <xsl:value-of select="$topLink"/>
                                        </xsl:attribute>
                                        <xsl:copy-of select="$topLinkText"/>
                                        </a>
                                    </td>
                                    <xsl:choose>
                                        <xsl:when test="$appearsOn='package'">
                                        	<td class="NavBarMainHighlight">
                                        		<xsl:value-of select="$packageLinkText"/>
                                        	</td>
                                        	<td class="NavBarMainPrint">
                                        		<xsl:copy-of select="$elementLinkText"/>
                                        	</td>
                                        </xsl:when>
                                        <xsl:otherwise>
                                        	<td class="NavBarMainLink">
                                        	<a>
	                                        	<xsl:attribute name="href">
	                                        		<xsl:value-of select="$owningPackageLink"/>
	                                        	</xsl:attribute>
	                                        	<xsl:copy-of select="$packageLinkText"/>
                                        	</a>
                                        </td>
                                        <xsl:choose>
	                                        <xsl:when test="$appearsOn='content'">
		                                        <td class="NavBarMainHighlight">
			                                        <xsl:value-of select="$elementLinkText"/>
		                                        </td>
	                                        </xsl:when>
                                        </xsl:choose>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:otherwise>
                            </xsl:choose>
                        </tr>
                    </table>
                </td>
                <td class="NavBarRight">
                    <xsl:if test="not ($docTitle = '')">
                        <!-- There is a title add a column for it -->
                        <xsl:value-of select="$docTitle"/>
                    </xsl:if>
                </td>
            </tr>
        </table>
        <table>
            <tr class="NavBarBookMarks">
                <td class="BookMarkSections">
                    <xsl:choose>
                        <!-- Create the summary list bookmarks -->
                        <xsl:when test="count ($bookMarkLinks/publish:navbookmarks/publish:summary/publish:bookmarklink) &gt; 0">
                            <span class="BookMarkElements">
                                <xsl:copy-of select="$summaryTitleText"/>
                            </span>
                            <xsl:for-each select="$bookMarkLinks/publish:navbookmarks/publish:summary/publish:bookmarklink">
                                <span class="BookMarkElements">
                                    <xsl:choose>
                                        <xsl:when test="string-length(@link) &gt; 0">
                                        <!-- Create the bookmark link -->
                                        <a>
                                        <xsl:attribute name="href">
                                        <xsl:value-of select="@link"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="title">
                                        <xsl:value-of select="@title"/>
                                        </xsl:attribute>
                                        <xsl:value-of select="@name"/>
                                        </a>
                                        </xsl:when>
                                        <xsl:otherwise>
                                        <!-- No link ust put the name there -->
                                        <xsl:value-of select="@name"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </span>
                            </xsl:for-each>
                        </xsl:when>
                        <xsl:otherwise/>
                    </xsl:choose>
                </td>
                <td class="BookMarkSections">
                    <xsl:choose>
                        <xsl:when test="count ($bookMarkLinks/publish:navbookmarks/publish:detail/publish:bookmarklink) &gt; 0">
                            <span class="BookMarkElements">
                                <xsl:copy-of select="$detailTitleText"/>
                            </span>
                            <xsl:for-each select="$bookMarkLinks/publish:navbookmarks/publish:detail/publish:bookmarklink">
                                <span class="BookMarkElements">
                                    <xsl:choose>
                                        <xsl:when test="string-length(@link) &gt; 0">
                                        <a>
                                        <xsl:attribute name="href">
                                        <xsl:value-of select="@link"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="title">
                                        <xsl:value-of select="@title"/>
                                        </xsl:attribute>
                                        <xsl:value-of select="@name"/>
                                        </a>
                                        </xsl:when>
                                        <xsl:otherwise>
                                        <xsl:value-of select="@name"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </span>
                            </xsl:for-each>
                        </xsl:when>
                        <xsl:otherwise/>
                    </xsl:choose>
                </td>
            </tr>
        </table>
    </xsl:template>
</xsl:stylesheet>
