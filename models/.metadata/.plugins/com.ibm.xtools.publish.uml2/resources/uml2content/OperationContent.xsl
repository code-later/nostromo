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

    <xsl:variable name="operationTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.Operations'"/>
        </xsl:call-template>
    </xsl:variable>
    
    <xsl:variable name="operationDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.OperationDetails'"/>
        </xsl:call-template>
    </xsl:variable>

    <xsl:variable name="operationParametersListTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'ContentHeading.Parameters'"/>
        </xsl:call-template>
    </xsl:variable>
    
    <xsl:variable name="operationReturnsTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'ContentHeading.Returns'"/>
        </xsl:call-template>
    </xsl:variable>
    
    <xsl:variable name="operationConstraintsTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'ContentHeading.Constraints'"/>
        </xsl:call-template>
    </xsl:variable>
   
    <xsl:variable name="operationsNavName">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'Nav.Operations'"/>
        </xsl:call-template>
    </xsl:variable>
    
    <xsl:variable name="inheritedOperationTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.InheritedOperations'"/>
        </xsl:call-template>
    </xsl:variable>
    


	<!-- ================================================================= -->
	<!-- 			templates that generate UML Operations data 		   -->
	<!-- ================================================================= -->
	<!-- Creates html contents for operations -->
	<xsl:template name="createOperationsInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if test="count(ownedOperation) &gt; 0">
			<xsl:choose>
				<!-- TOP/BOTTOM PAGE LINK -->
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$operationsNavName" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'operationSummary')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$operationsNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<!-- details -->
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$operationsNavName" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'operationDetail')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$operationsNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<!-- summary -->
				<xsl:when test="$mode = 'summary'">
					<p />
					<!-- Create an 'Operations' table for the content HTML -->
					<!-- Begin a table for the contained elements -->
					<table class="SummaryTable" border="1">
						<tr>
							<td class="SummaryTitle" colspan="3">
								<a name="operationSummary">
									<xsl:copy-of
										select="$operationTableTitle" />
								</a>
							</td>
						</tr>
						<!-- Loop for each operation creating an entry in the table -->
						<xsl:choose>
							<xsl:when test="parent::*/@publish:sortOrder = 'alphabetical'">
								<xsl:for-each
									select="ownedOperation">
									<xsl:sort select="@name" />
									<xsl:call-template name="operationSummaryRow"></xsl:call-template>
								</xsl:for-each>
							</xsl:when>
							<xsl:otherwise>
								<xsl:for-each
									select="ownedOperation">
									<xsl:call-template name="operationSummaryRow"></xsl:call-template>
								</xsl:for-each>
							</xsl:otherwise>
						</xsl:choose>
						<!-- Close out the operations table -->
					</table>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<!-- Create an 'Operation Detail' table header for the content HTML -->
					<table class="DetailsTable">
						<tr>
							<td class="DetailsTitle" colspan="2">
								<a name="operationDetail">
									<xsl:copy-of
										select="$operationDetailsTableTitle" />
								</a>
							</td>
						</tr>
					</table>
					<!-- Loop for each operation creating HTML for the full deatils of the attribute -->
					<xsl:for-each select="ownedOperation">
						<xsl:sort select="@name" />
						<br />
						<span class="ListItem">
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
							<xsl:call-template
								name="getParameterTypeList" />
						</pre>
						<dl>
							<dd />
							<!-- Documentation -->
							<xsl:call-template
								name="createDocumentation" />

							<!-- the parameter info -->
							<dt>
								<b>
									<xsl:copy-of
										select="$operationParametersListTitle" />
								</b>
							</dt>
							<xsl:call-template name="getParameters" />

							<!-- return type of operation -->
							<dt>
								<b>
									<xsl:copy-of
										select="$operationReturnsTitle" />
								</b>
							</dt>
							<dd>
								<xsl:apply-templates
									select="./publish:properties/publish:property[@name='Type']" />
							</dd>

							<!-- operation constraints are here -->
							<dt>
								<a>
									<xsl:attribute name="name">
										<xsl:value-of select="'operationConstraintsTable'" />
									</xsl:attribute>
									<b>
										<xsl:copy-of select="$operationConstraintsTitle" />
									</b>
								</a>
							</dt>
							<xsl:call-template
								name="showOperationConstraints" />

							<!-- operation properties table -->
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
										select="$inheritedOperationTableTitle" />
								<xsl:call-template name="outputLinkIfExists" >
									<xsl:with-param name="text" select="@publish:qualifiedname"/>
								</xsl:call-template>
							</td>
						</tr>
						<tr>
							<td>
								<xsl:variable name="lastId" select="generate-id(inheritedOperation[last()])"/>
								<xsl:for-each select="inheritedOperation">
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

	<!--generates a list of parameters : type name  -->
	<xsl:template name="getParameters" match="ownedOperation">
		<xsl:for-each select="ownedParameter[@direction != 'return']">
			<xsl:sort select="@name" />
			<dd>
				<xsl:apply-templates
					select="./publish:properties/publish:property[@name='Type']" />
				<xsl:value-of select="@name" />
			</dd>
		</xsl:for-each>
	</xsl:template>

	<!-- generates constraints info: pre- and post- constraints -->
	<xsl:template name="showOperationConstraints" match="ownedOperation">
		<xsl:for-each select="ownedRule">
			<dd>
				<xsl:value-of select="@name" />
				<xsl:text> = "</xsl:text>
					<b> <xsl:value-of select="@body" /> </b>
				<xsl:text>"</xsl:text>
			</dd>
		</xsl:for-each>
	</xsl:template>

	<!--  generates a list of parameters : (type1, type2, type3) etc....-->
	<xsl:template name="getParameterTypeList" match="ownedOperation">
		<xsl:text>(</xsl:text>
		<xsl:for-each select="ownedParameter[@direction != 'return']">
			<xsl:sort select="@name" />

			<xsl:choose>
				<xsl:when test="position() != last()">
					<xsl:apply-templates
						select="./publish:properties/publish:property[@name='Type']">
						<xsl:with-param name="useCommaSpace"
							select="true()" />
					</xsl:apply-templates>
				</xsl:when>
				<xsl:otherwise>
					<!-- default value of parameter useCommaSpace is false() -->
					<xsl:apply-templates
						select="./publish:properties/publish:property[@name='Type']" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
		<xsl:text>)</xsl:text>
	</xsl:template>
	<xsl:template name="operationSummaryRow">
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