<?xml version="1.0"?>
<!--                                                                        -->
<!-- Licensed Materials - Property of IBM                                   -->
<!-- Copyright IBM Corp. 2003, 2004.  All Rights Reserved.                  -->
<!--                                                                        -->
<!-- US Government Users Restricted Rights - Use, duplication or disclosure -->
<!-- restricted by GSA ADP Schedule Contract with IBM Corp.                 -->
<!--                                                                        -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:xmi="http://www.omg.org/XMI" xmlns:uml="http://www.eclipse.org/uml2/1.0.0/UML"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:publish="http://www.ibm.com/Rational/XTools/Publish"
    xmlns:xalan="http://xml.apache.org/xalan" exclude-result-prefixes="xmi uml xsi publish xalan">
    <!-- =========================== Imports ============================================== -->
    <xsl:import href="StringUtility.xsl"/>
    <xsl:output method="html"/>
    <!-- The template outputHTMLHeader will create a header element for HTML.  This will include a 
  	   title element plus description, keyword and generator meta tags.  For the title, description 
  	   and keywords strings the template will use the replaceSubString template to replace a token 
  	   with a replace string in a specified pattern before using the string in the header.
       
       Parameters:
         titlePattern            - The pattern to use as the title (e.g. "Contents for #name")
         titleReplaceToken       - The token to replace in the title pattern (e.g. "#name").
         titleReplaceWith        - The string to replace any titleReplaceTokens found (e.g. 
                                   select="@name").
         descriptionPattern      - The pattern to use as the description meta tag (e.g. "This is 
                                   the informaiton for the element #name").
         descriptionReplaceToken - The token to replace in the description pattern (e.g. "#name").
         descriptionReplaceWith  - The string to replace any descriptionReplaceTokens found (e.g. 
                                   select="@name")
         keywordsPattern         - The pattern to use as the keyword meta tag (e.g. "#name")
         keywordsReplaceToken    - The token to replace in the keyword pattern (e.g. "#name")
         keywordsReplaceWith     - The string to replace any keywordReplaceTokens found (e.g. 
                                   select="@name")
         generator               - The string to place in the "generator" meta tag.
  -->
    <xsl:template name="outputHTMLHeader">
        <xsl:param name="titlePattern" select="@name"/>
        <xsl:param name="titleReplaceToken">
            <xsl:text>#name</xsl:text>
        </xsl:param>
        <xsl:param name="titleReplaceWith" select="@name"/>
        <xsl:param name="descriptionPattern" select="''"/>
        <xsl:param name="descriptionReplaceToken">
            <xsl:text>#name</xsl:text>
        </xsl:param>
        <xsl:param name="descriptionReplaceWith" select="@name"/>
        <xsl:param name="keywordsPattern" select="''"/>
        <xsl:param name="keywordsReplaceToken">
            <xsl:text>#name</xsl:text>
        </xsl:param>
        <xsl:param name="keywordsReplaceWith" select="@name"/>
        <xsl:param name="csslink" select="WebPublish.css"/>
        <xsl:variable name="title">
            <xsl:call-template name="replaceSubString">
                <xsl:with-param name="theString">
                    <xsl:value-of select="$titlePattern"/>
                </xsl:with-param>
                <xsl:with-param name="matchSubString">
                    <xsl:value-of select="$titleReplaceToken"/>
                </xsl:with-param>
                <xsl:with-param name="replaceWith">
                    <xsl:value-of select="$titleReplaceWith"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="description">
            <xsl:call-template name="replaceSubString">
                <xsl:with-param name="theString">
                    <xsl:value-of select="$descriptionPattern"/>
                </xsl:with-param>
                <xsl:with-param name="matchSubString">
                    <xsl:value-of select="$descriptionReplaceToken"/>
                </xsl:with-param>
                <xsl:with-param name="replaceWith">
                    <xsl:value-of select="$descriptionReplaceWith"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="keywords">
            <xsl:call-template name="replaceSubString">
                <xsl:with-param name="theString">
                    <xsl:value-of select="$keywordsPattern"/>
                </xsl:with-param>
                <xsl:with-param name="matchSubString">
                    <xsl:value-of select="$keywordsReplaceToken"/>
                </xsl:with-param>
                <xsl:with-param name="replaceWith">
                    <xsl:value-of select="$keywordsReplaceWith"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <head>
            <title>
                <xsl:value-of select="$title"/>
            </title>
            <xsl:if test="$globalGeneratorName != ''">
                <meta>
                    <xsl:attribute name="name">generator</xsl:attribute>
                    <xsl:attribute name="content">
                        <xsl:value-of select="$globalGeneratorName"/>
                    </xsl:attribute>
                </meta>
            </xsl:if>
            <xsl:if test="$description != ''">
                <meta>
                    <xsl:attribute name="name">description</xsl:attribute>
                    <xsl:attribute name="content">
                        <xsl:value-of select="$description"/>
                    </xsl:attribute>
                </meta>
            </xsl:if>
            <xsl:if test="$keywords != ''">
                <meta>
                    <xsl:attribute name="keywords">
                        <xsl:value-of select="$keywords"/>
                    </xsl:attribute>
                </meta>
            </xsl:if>
            <link rel="stylesheet" type="text/css" href="WebPublish.css"/>
        </head>
    </xsl:template>
    
    
    <!-- Generate the HTML for combined image and text. 
    
       Parameters:
         image  - The image to display before the text.
         text   - The text to display next to the image.
         target - The optional value for the target attribute.
  -->
    <xsl:template name="outputImageAndTextHTML">
        <xsl:param name="image" select="''"/>
        <xsl:param name="text" select="@name"/>
        <xsl:variable name="label">
            <xsl:if test="$text != ''">
                <xsl:value-of select="$text"/>
            </xsl:if>
            <xsl:if test="$text = ''">
                <xsl:text>Unnamed</xsl:text>
            </xsl:if>
        </xsl:variable>
        <!-- Create image -->
        <xsl:if test="$image != ''">
            <img border="0">
                <xsl:attribute name="src">
                    <xsl:value-of select="$image"/>
                </xsl:attribute>
                <xsl:attribute name="alt">
                    <xsl:value-of select="$label"/>
                </xsl:attribute>
            </img>
            <xsl:text>&#160;</xsl:text>
        </xsl:if>
        <!-- Create text -->
        <xsl:value-of select="$label"/>
    </xsl:template>
    
	<!-- Generate the HTML for a combined image and text link.  It creates both an image and a text 
       link pointing to the same href with a single space between them.  The image is optional so 
       this template can also generate normal links too.
    
       Parameters:
         link   - The value for href of both links.
         image  - The image to display before the text.
         text   - The text to display next to the image.
         target - The optional value for the target attribute.
  -->
    <xsl:template name="outputImageAndTextLinkHTML">
        <xsl:param name="link"/>
        <xsl:param name="image" select="''"/>
        <xsl:param name="text" select="@name"/>
        <xsl:param name="target" select="''"/>
        <xsl:param name="ttip" select="@publish:qualifiedname"/>
        <xsl:variable name="label">
            <xsl:if test="$text != ''">
                <xsl:value-of select="$text"/>
            </xsl:if>
            <xsl:if test="$text = ''">
                <xsl:text>Unnamed</xsl:text>
            </xsl:if>
        </xsl:variable>
        <!-- Create a link using the image -->
        <xsl:if test="$image != ''">
            <a>
                <xsl:if test="$ttip != ''">
                    <xsl:attribute name="title">
                        <xsl:value-of select="$ttip"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:if test="$target != ''">
                    <xsl:attribute name="target">
                        <xsl:value-of select="$target"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:if test="$link != ''">
                    <xsl:attribute name="href">
                        <xsl:value-of select="$link"/>
                    </xsl:attribute>
                </xsl:if>
                <img border="0">
                    <xsl:attribute name="src">
                        <xsl:value-of select="$image"/>
                    </xsl:attribute>
                    <xsl:attribute name="alt">
                        <xsl:value-of select="$label"/>
                    </xsl:attribute>
                </img>
            </a>
            <xsl:text>&#160;</xsl:text>
        </xsl:if>
        <!-- Create a link using the text -->
        <a>
            <xsl:if test="$ttip != ''">
                <xsl:attribute name="title">
                    <xsl:value-of select="$ttip"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$target != ''">
                <xsl:attribute name="target">
                    <xsl:value-of select="$target"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$link != ''">
                <xsl:attribute name="href">
                    <xsl:value-of select="$link"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="$label"/>
        </a>
    </xsl:template>
    
    <!-- Generate the HTML for an element. If there exists an element of type 
    	packagedElement with this element's id, the text created links to it, 
    	otherwise, text is generated without a link.
  -->
    <xsl:template name="outputLinkIfExists">
    	<xsl:param name="text" select="@name"/>
		<xsl:choose>
			<xsl:when test="count(//packagedElement[@xmi:id=current()/@xmi:id]) &gt; 0">
				<xsl:call-template
					name="outputImageAndTextLinkHTML">
					<xsl:with-param name="link">
						<xsl:call-template name="createElementContentFileName">
							<xsl:with-param name="element" select="." />
						</xsl:call-template>
					</xsl:with-param>
					<xsl:with-param name="image">
						<xsl:if
							test="@publish:icon != ''">
							<xsl:call-template
								name="createElementIconFileName" />
						</xsl:if>
					</xsl:with-param>
					<xsl:with-param name="text" select="$text" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template
					name="outputImageAndTextHTML">
					<xsl:with-param name="image">
						<xsl:if
							test="@publish:icon != ''">
							<xsl:call-template
								name="createElementIconFileName" />
						</xsl:if>
					</xsl:with-param>
					<xsl:with-param name="text" select="$text" />
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
    
    <!-- Summary table title HTML -->
    <xsl:template name="createTableTitle">
        <xsl:param name="title"/>
        <tr>
            <td class="SummaryTitle" colspan="2">
                <a name="{$title}">
                    <xsl:copy-of select="$title"/>
                </a>
            </td>
        </tr>
    </xsl:template>
    
    <!-- Summary table content HTML -->
    <xsl:template name="createSummaryTableContentRow">
        <tr>
            <td>
                <xsl:call-template name="outputImageAndTextLinkHTML">
                    <xsl:with-param name="link">
                        <xsl:text>#</xsl:text>
                        <xsl:value-of select="@xmi:id"/>
                    </xsl:with-param>
                    <xsl:with-param name="image">
                        <xsl:if test="@publish:icon != ''">
                            <xsl:call-template name="createElementIconFileName"/>
                        </xsl:if>
                    </xsl:with-param>
                    <xsl:with-param name="text" select="@name"/>
                </xsl:call-template>
                <td>
                    <!-- Documentation -->
                    <xsl:call-template name="createDocumentation"/>
                </td>
            </td>
        </tr>
    </xsl:template>
    
    <!-- Details table content -->
    <xsl:template name="createDetailTableContentRow">
        <br/>
        <span class="ListItem">
            <xsl:if test="@publish:icon != ''">
                <!-- Put the element image next the title -->
                <img>
                    <xsl:attribute name="src">
                        <xsl:call-template name="createElementIconFileName"/>
                    </xsl:attribute>
                    <xsl:attribute name="alt">
                        <xsl:value-of select="''"/>
                    </xsl:attribute>
                </img>
                <xsl:text>&#160;</xsl:text>
            </xsl:if>
            <a name="{@xmi:id}">
                <xsl:if test="@name != ''">
                    <xsl:value-of select="@name"/>
                </xsl:if>
                <xsl:if test="@name = ''">
                    <xsl:text>Unnamed</xsl:text>
                </xsl:if>
            </a>
        </span>
        
        <!-- Documentation -->
        <xsl:call-template name="createDocumentation"/>
        
        <dl>
            <dt>
                <b>
                    <xsl:copy-of select="$propertiesTitle"/>
                </b>
            </dt>
            <dd>
                <!-- Output the properties table -->
                <xsl:apply-templates select="publish:properties">
                    <xsl:with-param name="indentCount" select="1"/>
                    <xsl:with-param name="includeTitle" select="false()"/>
                </xsl:apply-templates>
            </dd>
        </dl>
    </xsl:template>
    
    <!-- Summary table content row HTML -->
    <xsl:template name="createSummaryTableContentRowForTemplateParamters">
        <tr>
            <td>
                <xsl:for-each select="ownedParameteredElement">
                    <xsl:call-template name="outputImageAndTextLinkHTML">
                        <xsl:with-param name="link">
                            <xsl:text>#</xsl:text>
                            <xsl:value-of select="@xmi:id"/>
                        </xsl:with-param>
                        <xsl:with-param name="image">
                            <xsl:if test="@publish:icon != ''">
                                <xsl:call-template name="createElementIconFileName"/>
                            </xsl:if>
                        </xsl:with-param>
                        <xsl:with-param name="text" select="@name"/>
                    </xsl:call-template>
                </xsl:for-each> &#160; </td>
        </tr>
    </xsl:template>
    
    <!-- -->
    <xsl:template name="createDetailTableForTemplateBinding">
        <br/>
        <span class="ListItem">
            <xsl:if test="@publish:icon != ''">
                <xsl:variable name="image">
                    <xsl:call-template name="createElementIconFileName"/>
                </xsl:variable>
                <img border="0">
                    <xsl:attribute name="src">
                        <xsl:value-of select="$image"/>
                    </xsl:attribute>
                    <xsl:attribute name="alt">
                        <xsl:value-of select="@name"/>
                    </xsl:attribute>
                </img>
                <xsl:text>&#160;</xsl:text>
            </xsl:if>
            <a name="{@xmi:id}">
                <xsl:if test="@name != ''">
                    <xsl:value-of select="@name"/>
                </xsl:if>
                <xsl:if test="@name = ''">
                    <xsl:text>Unnamed</xsl:text>
                </xsl:if>
            </a>
        </span>
        <dl>
            <dt>
                <b>
                    <xsl:copy-of select="$propertiesTitle"/>
                </b>
            </dt>
            <dd>
                <!-- Output the properties table -->
                <xsl:apply-templates select="publish:properties">
                    <xsl:with-param name="indentCount" select="1"/>
                    <xsl:with-param name="includeTitle" select="false()"/>
                </xsl:apply-templates>
            </dd>
        </dl>
    </xsl:template>
    <!-- Template Parameter Substitution -->
    <xsl:template name="createDetailTableForTemplateParameterSubstitutions">
        <br/>
        <xsl:for-each select="parameterSubstitution">
            <span class="ListItem">
                <xsl:if test="@publish:icon != ''">
                    <xsl:variable name="image">
                        <xsl:call-template name="createElementIconFileName"/>
                    </xsl:variable>
                    <img border="0">
                        <xsl:attribute name="src">
                            <xsl:value-of select="$image"/>
                        </xsl:attribute>
                        <xsl:attribute name="alt">
                            <xsl:value-of select="@name"/>
                        </xsl:attribute>
                    </img>
                    <xsl:text>&#160;</xsl:text>
                </xsl:if>
                <a name="{@xmi:id}">
                    <xsl:if test="@name != ''">
                        <xsl:value-of select="@name"/>
                    </xsl:if>
                    <xsl:if test="@name = ''">
                        <xsl:text>Unnamed</xsl:text>
                    </xsl:if>
                </a>
            </span>
            <dl>
                <dt>
                    <b>
                        <xsl:copy-of select="$propertiesTitle"/>
                    </b>
                </dt>
                <dd>
                    <!-- Output the properties table -->
                    <xsl:apply-templates select="publish:properties">
                        <xsl:with-param name="indentCount" select="1"/>
                        <xsl:with-param name="includeTitle" select="false()"/>
                    </xsl:apply-templates>
                </dd>
            </dl>
        </xsl:for-each>
    </xsl:template>
    
    <!--  -->
    <xsl:template name="createDetailTableContentRowForTemplateParamters">
        <br/>
        <span class="ListItem">
            <xsl:for-each select="ownedParameteredElement">
                <xsl:if test="@publish:icon != ''">
                    <xsl:variable name="image">
                        <xsl:call-template name="createElementIconFileName"/>
                    </xsl:variable>
                    <img border="0">
                        <xsl:attribute name="src">
                            <xsl:value-of select="$image"/>
                        </xsl:attribute>
                        <xsl:attribute name="alt">
                            <xsl:value-of select="@name"/>
                        </xsl:attribute>
                    </img>
                    <xsl:text>&#160;</xsl:text>
                </xsl:if>
                <a name="{@xmi:id}">
                    <xsl:if test="@name != ''">
                        <xsl:value-of select="@name"/>
                    </xsl:if>
                </a>
            </xsl:for-each>
        </span>
        <dl>
            <dt>
                <b>
                    <xsl:copy-of select="$propertiesTitle"/>
                </b>
            </dt>
            <dd>
                <!-- Output the properties table -->
                <xsl:apply-templates select="publish:properties">
                    <xsl:with-param name="indentCount" select="1"/>
                    <xsl:with-param name="includeTitle" select="false()"/>
                </xsl:apply-templates>
            </dd>
        </dl>
    </xsl:template>
    
    
    <!-- useful utility for showing text with line breaks in it 
        Note that XML style end-of-line characters will not be displayed properly
        in the output HTML file unless you call this template
    -->
    <xsl:template name="replaceXML2HTMLLineBreaks">
        <xsl:param name="theText">ERROR-MISSING-PARAMETER</xsl:param>
        <!-- pass #1: replace DOS style end of line characters -->
        <xsl:variable name="replaceDosEols">
            <xsl:call-template name="replaceSubString">
                <xsl:with-param name="theString">
                    <xsl:value-of select="$theText"/>
                </xsl:with-param>
                <xsl:with-param name="matchSubString">
                    <xsl:text>&#xd;&#xa;</xsl:text>
                </xsl:with-param>
                <xsl:with-param name="replaceWith">
                    <xsl:text>&lt;/br&gt;</xsl:text>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <!-- pass #2: replace UNIX style end of line characters -->
        <xsl:variable name="replaceUnixEols">
            <xsl:call-template name="replaceSubString">
                <xsl:with-param name="theString">
                    <xsl:value-of select="$replaceDosEols"/>
                </xsl:with-param>
                <xsl:with-param name="matchSubString">
                    <xsl:text>&#xa;</xsl:text>
                </xsl:with-param>
                <xsl:with-param name="replaceWith">
                    <xsl:text>&lt;/br&gt;</xsl:text>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <xsl:value-of select="$replaceUnixEols" disable-output-escaping="yes"/>
    </xsl:template>
</xsl:stylesheet>
