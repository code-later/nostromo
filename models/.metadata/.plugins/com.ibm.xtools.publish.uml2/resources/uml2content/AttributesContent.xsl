<?xml version="1.0" encoding="UTF-8"?>
<!--                                                                        -->
<!-- Licensed Materials - Property of IBM                                   -->
<!-- Copyright IBM Corp. 2003, 2007.  All Rights Reserved.                  -->
<!--                                                                        -->
<!-- US Government Users Restricted Rights - Use, duplication or disclosure -->
<!-- restricted by GSA ADP Schedule Contract with IBM Corp.                 -->
<!--                                                                        -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns:xmi="http://www.omg.org/XMI"
	xmlns:uml="http://www.eclipse.org/uml2/1.0.0/UML"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:publish="http://www.ibm.com/Rational/XTools/Publish"
	xmlns:redirect="http://xml.apache.org/xalan/redirect"
	extension-element-prefixes="redirect"
	xmlns:xalan="http://xml.apache.org/xalan"
	exclude-result-prefixes="xmi uml xsi publish xalan">

    <xsl:variable name="attributeTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.Attributes'"/>
        </xsl:call-template>
    </xsl:variable>

    <xsl:variable name="attributeDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.AttributeDetails'"/>
        </xsl:call-template>
    </xsl:variable>

    <xsl:variable name="attributesNavName">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'Nav.Attributes'"/>
        </xsl:call-template>
    </xsl:variable>

    <xsl:variable name="inheritedAttributeTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.InheritedAttributes'"/>
        </xsl:call-template>
    </xsl:variable>

	<!-- Creates html contents for attributes -->
	<xsl:template name="createAttributesInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if
			test="count(ownedAttribute) &gt; 0">
			<xsl:choose>
				<!-- TOP/BOTTOM PAGE LINK -->
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$attributesNavName" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'attributeSummary')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$attributesNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<!-- details -->
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$attributesNavName" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'attributeDetail')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$attributesNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<!-- summary -->
				<xsl:when test="$mode = 'summary'">
					<p />
					<!-- Create an 'Attributes' table for the content HTML -->
					<!-- Begin a table for the contained elements -->
					<table class="SummaryTable" border="1">
						<tr>
							<td class="SummaryTitle" colspan="3">
								<a name="attributeSummary">
									<xsl:copy-of
										select="$attributeTableTitle" />
								</a>
							</td>
						</tr>
						<!-- Loop for each attribute creating an entry in the table -->
						<xsl:choose>
							<xsl:when test="parent::*/@publish:sortOrder = 'alphabetical'">
								<xsl:for-each
									select="ownedAttribute">
									<xsl:sort select="@name" />
									<xsl:call-template name="attributeSummaryRow"></xsl:call-template>
								</xsl:for-each>
							</xsl:when>
							<xsl:otherwise>
								<xsl:for-each
									select="ownedAttribute">
									<xsl:call-template name="attributeSummaryRow"></xsl:call-template>
								</xsl:for-each>
							</xsl:otherwise>
						</xsl:choose>
						<!-- Close out the attributes table -->
					</table>
				</xsl:when>

				<!-- Create an 'Attribute Detail' table header for the content HTML -->
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable">
						<tr>
							<td class="DetailsTitle" colspan="2">
								<a name="attributeDetail">
									<xsl:copy-of
										select="$attributeDetailsTableTitle" />
								</a>
							</td>
						</tr>
					</table>
					<!-- Loop for each attribute creating HTML for the full deatils of the attribute -->
					<xsl:for-each
						select="ownedAttribute">
						<xsl:sort select="@name" />
						<span class="ListItem">
							<br />
							<xsl:if test="@publish:icon != ''">
								<!-- Put the element image next the title -->
								<img>
									<xsl:attribute name="src">
										<xsl:call-template
											name="createElementIconFileName" />
									</xsl:attribute>
									<xsl:attribute name="alt">
										<xsl:value-of select="''" />
									</xsl:attribute>
								</img>
								<xsl:text>&#160;</xsl:text>
							</xsl:if>
							<a name="{@xmi:id}">
								<xsl:value-of select="@name" />
							</a>
						</span>
						<pre>
							<xsl:value-of
								select="publish:properties/publish:property[@name='Visibility']/@publish:value" />
							<xsl:text>&#160;</xsl:text>
							<xsl:apply-templates
								select="./publish:properties/publish:property[@name='Type']" />
							<xsl:value-of select="@name" />
						</pre>
						<dl>
							<dd>
								<!-- Documentation -->
								<xsl:call-template
									name="createDocumentation" />
							</dd>
							<dt>
								<b>
									<xsl:copy-of
										select="$propertiesTitle" />
								</b>
							</dt>
							<dd>
								<!-- Output the properties table -->
								<xsl:apply-templates
									select="publish:properties">
									<xsl:with-param name="indentCount"
										select="1" />
									<xsl:with-param name="includeTitle"
										select="false()" />
								</xsl:apply-templates>
							</dd>
						</dl>
						<xsl:if test="position() != last()">
							<hr />
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
		<xsl:if test="count(ancestor) &gt; 0">
			<xsl:if test="$mode = 'summary'">
				<xsl:for-each select="ancestor">
					<p/>
					<table class="SummaryTable" border="1">
						<tr>
							<td class="SummaryTitle" colspan="3">
								<xsl:copy-of
										select="$inheritedAttributeTableTitle" />
								<xsl:call-template name="outputLinkIfExists" >
									<xsl:with-param name="text" select="@publish:qualifiedname"/>
								</xsl:call-template>
							</td>
						</tr>
						<tr>
							<td>
								<xsl:variable name="lastId" select="generate-id(inheritedAttribute[last()])"/>
								<xsl:for-each select="inheritedAttribute">
									<xsl:call-template name="outputLinkIfExists"/>
									<xsl:if test="generate-id(.) != $lastId">, </xsl:if>
								</xsl:for-each>
							</td>
						</tr>							
					</table>
				</xsl:for-each>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	<xsl:template name="attributeSummaryRow">
		<tr>
			<td class="packagedElementType">
				<xsl:apply-templates
					select="./publish:properties/publish:property[@name='Type']" />
			</td>
			<td>
				<xsl:call-template
					name="outputImageAndTextLinkHTML">
					<xsl:with-param name="link">
						<xsl:text>#</xsl:text>
						<xsl:value-of
							select="@xmi:id" />
					</xsl:with-param>
					<xsl:with-param name="image">
						<xsl:if
							test="@publish:icon != ''">
							<xsl:call-template
								name="createElementIconFileName" />
						</xsl:if>
					</xsl:with-param>
					<xsl:with-param name="text"
						select="@name" />
				</xsl:call-template>
			</td>
			<td>
				<!-- Documentation -->
				<xsl:call-template
					name="createDocumentation" />
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>