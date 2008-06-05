<!--                                                                        -->
<!-- Licensed Materials - Property of IBM                                   -->
<!-- Copyright IBM Corp. 2003, 2004.  All Rights Reserved.                  -->
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

	<xsl:output method="html" />

	<xsl:include href="AbstractContent.xsl" />
	
	<xsl:include href="AttributesContent.xsl" />
	<xsl:include href="RolesContent.xsl" />
	<xsl:include href="OperationContent.xsl" />

	<!-- ======================================================================================== -->
	<!-- Create content for various tags we encounter in the intermediate xml-->
	<!-- ======================================================================================== -->
	<xsl:template
		match="packagedElement | region | node | containedNode | nestedClassifier | ownedPort | occurrence | ownedBehavior | generalization | subvertex | edge | containedEdge | lifeline | message | configuration | 
        target | result | argument | fragment | realization | interfaceRealization | extensionPoint | 
        ownedTrigger | transition | implementation | substitution | packageImport | elementImport | include | extend | manifestation | deployment | ownedConnector | entry | exit | doActivity | ownedParameteredElement | ownedUseCase"
		mode="NamedElement">
		<xsl:param name="titleKey" select="''" />
		<xsl:param name="titlePattern" select="@name" />
		<xsl:param name="descriptionPattern" select="''" />
		<xsl:param name="keywordsWithPatterns" select="''" />
		<!-- obtain the title text -->
		<xsl:variable name="elementTitle">
			<xsl:call-template name="getLocalizedString">
				<xsl:with-param name="key" select="$titleKey" />
			</xsl:call-template>
		</xsl:variable>
		<!-- Generate the file name needed for output of the navigation list -->
		<xsl:variable name="elementContentFileName">
			<xsl:call-template name="createElementContentFileName" />
		</xsl:variable>
		<xsl:variable name="elementContentFullPathFileName">
			<xsl:call-template name="prependFullPathToFileName">
				<xsl:with-param name="fileName"
					select="$elementContentFileName" />
			</xsl:call-template>
		</xsl:variable>
		<!-- Generate the package's fully qualified name -->
		<xsl:variable name="packageFullyQualifiedName">
			<xsl:call-template name="generateFullyQualifiedName">
				<xsl:with-param name="includeMyName" select="false ()" />
			</xsl:call-template>
		</xsl:variable>
		<!-- Perform pattern replacement for any '#name' with the attribute 'name' -->
		<xsl:variable name="masterDocTitle">
			<xsl:call-template name="replaceSubString">
				<xsl:with-param name="theString">
					<xsl:value-of select="$titlePattern" />
				</xsl:with-param>
				<xsl:with-param name="matchSubString">
					<xsl:text>#name</xsl:text>
				</xsl:with-param>
				<xsl:with-param name="replaceWith">
					<xsl:value-of select="@name" />
				</xsl:with-param>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="bookmarks">
			<publish:navbookmarks>
				<!-- LINKS -->
				<publish:summary>
					<xsl:call-template name="fillBookmarkLinksSection" />

					<!-- properties link-->
					<xsl:call-template
						name="createPropertiesSummaryLink" />

				</publish:summary>

				<!-- DETAILS -->
				<publish:detail>
					<xsl:call-template
						name="fillBookmarkDetailsSection" />
				</publish:detail>

			</publish:navbookmarks>
		</xsl:variable>
		<!-- Create the navigation bar once (to be copied twice into the document) -->
		<xsl:variable name="navBar">
			<xsl:call-template name="outputNavigationBar">
				<xsl:with-param name="docTitle"
					select="$masterDocTitle" />
				<xsl:with-param name="bookMarkLinks"
					select="xalan:nodeset($bookmarks)" />
			</xsl:call-template>
		</xsl:variable>
		<redirect:write file="{$elementContentFullPathFileName}">
			<html lang="en">
				<xsl:call-template name="outputHTMLHeader">
					<xsl:with-param name="titlePattern"
						select="$titlePattern" />
					<xsl:with-param name="titleReplaceToken">
						<xsl:text>#name</xsl:text>
					</xsl:with-param>
					<xsl:with-param name="titleReplaceWith"
						select="@name" />
					<xsl:with-param name="descriptionPattern"
						select="$descriptionPattern" />
					<xsl:with-param name="descriptionReplaceToken">
						<xsl:text>#name</xsl:text>
					</xsl:with-param>
					<xsl:with-param name="descriptionReplaceWith"
						select="@name" />
					<xsl:with-param name="keywordsPattern"
						select="$keywordsWithPatterns" />
					<xsl:with-param name="keywordsReplaceToken">
						<xsl:text>#name</xsl:text>
					</xsl:with-param>
					<xsl:with-param name="keywordsReplaceWith"
						select="@name" />
				</xsl:call-template>
				<body>
					<xsl:copy-of select="$navBar" />
					<hr />
					<span class="PackageFullyQualifiedName">
						<xsl:value-of
							select="$packageFullyQualifiedName" />
					</span>
					<br />
					<span class="ElementTitle">
						<xsl:copy-of select="$elementTitle" />
						<xsl:value-of select="@name" />
					</span>
					<!-- documentation information is always on top -->
					<xsl:call-template name="createDocumentation" />

					<xsl:call-template name="fillSummarySection" />

					<!-- Output the properties table -->
					<!--
					<xsl:apply-templates select="publish:properties">
						<xsl:with-param name="bookMark"
							select="'propertiesTable'" />
					</xsl:apply-templates>
					-->

					<xsl:call-template name="fillFullDetailsSection" />

					<hr />
					<br />
					<xsl:copy-of select="$navBar" />
				</body>
			</html>
		</redirect:write>
	</xsl:template>



	<!-- There are 3 kinds of comments that are currently used by UML model:
		1) regular comments (no stereotype set)
		2) documentation (<<documentation>> stereotyped comments, see them 
		in the properties view under "Documentation" tab. The trick about this type of comments
		is that the comment is only used for "Properties" view of the element that directly 
		owns the comment).
		3) urls (<<URL>> stereotyped comments)
		
		Both are represented by UMLComment class but for documentation it is stereotyped as
		<<documentation>>.
	-->
	<!-- Comments link/details -->
	<xsl:template name="createCommentsInfo">
		<xsl:param name="mode" select="''" />
		<xsl:variable name="elementId" select="@xmi:id" />
		<xsl:variable name="attachedComments"
			select="key('TableCommentsByAttachedElement', $elementId)" />
		<xsl:variable name="commentsTableHeading">
			<xsl:call-template name="getLocalizedString">
				<xsl:with-param name="key"
					select="'TableHeading.Comments'" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="normalComments"
			select="$attachedComments[@isURL != 'true' and @isDocumentation != 'true']" />
		<xsl:if test="count($normalComments) &gt; 0">
			<xsl:choose>
				<!-- TOP/BOTTOM PAGE LINK -->
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$commentsTableHeading" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'commentsTable')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$commentsTableHeading" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>

				<!-- TABLE of DETAILS -->
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="ContainedElements" border="1">
						<tr>
							<td class="DetailsTitle" colspan="2">
								<a>
									<xsl:attribute name="name">
										<xsl:value-of select="'commentsTable'" />
									</xsl:attribute>
									<xsl:copy-of
										select="$commentsTableHeading" />
								</a>
							</td>
						</tr>
					</table>
					<ul>
						<xsl:for-each select="$normalComments">
							<li>
								<tr class="ClassTableEntry">
									<td>
										<xsl:call-template
											name="replaceXML2HTMLLineBreaks">
											<xsl:with-param
												name="theText">
												<xsl:value-of
													select="@body" />
											</xsl:with-param>
										</xsl:call-template>
									</td>
								</tr>
							</li>
						</xsl:for-each>
					</ul>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>




	<!-- create HTML contents for Constraints (link, details) -->
	<xsl:template name="createConstraintsInfo">
		<xsl:param name="mode" select="''" />
		<xsl:variable name="elementId" select="@xmi:id" />
		<xsl:variable name="attachedConstraints"
			select="key('TableConstraintsByAttachedElement', $elementId)" />

		<xsl:variable name="constraintsTableHeading">
			<xsl:call-template name="getLocalizedString">
				<xsl:with-param name="key"
					select="'TableHeading.Constraints'" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:if test="count($attachedConstraints) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$constraintsTableHeading" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'constraintsTable')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$constraintsTableHeading" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable" border="1">
						<tr>
							<td class="DetailsTitle" colspan="2">
								<a>
									<xsl:attribute name="name">
										<xsl:value-of select="'constraintsTable'" />
									</xsl:attribute>
									<xsl:copy-of
										select="$constraintsTableHeading" />
								</a>
							</td>
						</tr>
						<xsl:for-each select="$attachedConstraints">
							<p>
								<tr class="ClassTableEntry">
									<td>
										<xsl:value-of select="@name" />
									</td>
									<td>
										<xsl:value-of select="@body" />
									</td>
								</tr>
							</p>
						</xsl:for-each>
					</table>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>


	<!-- process documentation, i.e. <<documentation>> stereotyped comments -->
	<xsl:template name="createDocumentation">
		<xsl:for-each
			select="ownedComment[@isDocumentation = 'true']">
			<p>
				<a>
					<xsl:attribute name="name">
						<xsl:value-of select="'documentationSection'" />
					</xsl:attribute>
				</a>
				<xsl:call-template name="replaceXML2HTMLLineBreaks">
					<xsl:with-param name="theText">
						<xsl:value-of select="@body" />
					</xsl:with-param>
				</xsl:call-template>
			</p>
		</xsl:for-each>
	</xsl:template>
	
	<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4 -->
	<!-- Creates html contents for properties -->
	<xsl:template name="createPropertyInfo">
		<xsl:param name="mode" select="''" />		
			<xsl:choose>
				<!-- TOP/BOTTOM PAGE LINK -->
				<xsl:when test="$mode = 'link'">
					<!-- do nothing -->
				</xsl:when>
				<!-- details -->
				<xsl:when test="$mode = 'details'">
					<!-- do nothing -->
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<!-- publish the table -->
					<xsl:apply-templates select="publish:properties">
						<xsl:with-param name="bookMark"
							select="'propertiesTable'" />
					</xsl:apply-templates>					
				</xsl:when>
				<!-- summary -->
				<xsl:when test="$mode = 'summary'">
					<!-- do nothing -->
				</xsl:when>
			</xsl:choose>		
	</xsl:template>

	<!-- Creates html contents for literals -->
	<xsl:template name="createLiteralsInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if test="count(ownedLiteral) &gt; 0">
			<xsl:choose>
				<!-- TOP/BOTTOM PAGE LINK -->
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$enumLiteralsNavName" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $enumLiteralsNavName)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$enumLiteralsNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<!-- details -->
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$enumLiteralsNavName" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'enumLiteralDetail')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$enumLiteralsNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<!-- summary -->
				<xsl:when test="$mode = 'summary'">
					<p />
					<table class="SummaryTable" border="1">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$enumLiteralTableTitle" />
						</xsl:call-template>
						<!-- Loop for each element creating an entry in the table -->
						<xsl:for-each select="ownedLiteral">
							<xsl:sort select="@name" />
							<xsl:call-template
								name="createSummaryTableContentRow" />
						</xsl:for-each>
					</table>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable">
						<tr>
							<td class="DetailsTitle" colspan="2">
								<a name="enumLiteralDetail">
									<xsl:copy-of
										select="$enumLiteralDetailsTableTitle" />
								</a>
							</td>
						</tr>
					</table>
					<xsl:for-each select="ownedLiteral">
						<xsl:sort select="@name" />
						<xsl:call-template
							name="createDetailTableContentRow" />
						<xsl:if test="position() != last()">
							<hr />
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>




	<!-- Creates link to Nested Activities summary -->
	<xsl:template name="createNestedActivitiesInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if test="count(ownedBehavior[@xsi:type='uml:Activity']) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$nestedActivitiesNavName" />
						</xsl:attribute>
						<!-- DON'T FORMAT THE LINE BELOW!!! -->
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'nestedActivitiesSummary')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$nestedActivitiesNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<!-- Create an 'Nested classifiers' table for the content HTML -->
					<!-- Begin a table for the contained elements -->
					<table class="SummaryTable" border="1">
						<tr>
							<td class="SummaryTitle" colspan="2">
								<a name="nestedActivitiesSummary">
									<xsl:copy-of
										select="$nestedActivitiesNavName" />
								</a>
							</td>
						</tr>
						<!-- Loop for each attribute creating an entry in the table -->
						<xsl:for-each select="ownedBehavior[@xsi:type='uml:Activity']">
							<xsl:sort select="@name" />
							<tr>
								<td>
									<xsl:call-template
										name="outputImageAndTextLinkHTML">
										<xsl:with-param name="link">
											<xsl:call-template
												name="createElementContentFileName" />
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
									<br />
								</td>
								<td>
									<!-- Documentation -->
									<xsl:call-template
										name="createDocumentation" />
								</td>
							</tr>
						</xsl:for-each>
						<!-- Close out the nested classifiers table -->
					</table>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>


	<!-- Creates link to Nested Classifiers summary -->
	<xsl:template name="createNestedClassifiersInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if test="count(nestedClassifier) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$nestedClassifiersNavName" />
						</xsl:attribute>
						<!-- DON'T FORMAT THE LINE BELOW!!! -->
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'nestedClassifiersSummary')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$nestedClassifiersNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<!-- Create an 'Nested classifiers' table for the content HTML -->
					<!-- Begin a table for the contained elements -->
					<table class="SummaryTable" border="1">
						<tr>
							<td class="SummaryTitle" colspan="2">
								<a name="nestedClassifiersSummary">
									<xsl:copy-of
										select="$nestedClassifiersNavName" />
								</a>
							</td>
						</tr>
						<!-- Loop for each attribute creating an entry in the table -->
						<xsl:for-each select="nestedClassifier">
							<xsl:sort select="@name" />
							<tr>
								<td>
									<xsl:call-template
										name="outputImageAndTextLinkHTML">
										<xsl:with-param name="link">
											<xsl:call-template
												name="createElementContentFileName" />
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
									<br />
								</td>
								<td>
									<!-- Documentation -->
									<xsl:call-template
										name="createDocumentation" />
								</td>
							</tr>
						</xsl:for-each>
						<!-- Close out the nested classifiers table -->
					</table>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>


	<!-- Creates link to Components (possibly nested) summary -->
	<xsl:template name="createComponentsInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if test="count(packagedElement[@xsi:type='uml:Component']) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$nestedComponentsNavName" />
						</xsl:attribute>
						<!-- DON'T FORMAT THE LINE BELOW!!! -->
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'nestedComponentsSummary')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$nestedComponentsNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<!-- Create an 'Nested Components' table for the content HTML -->
					<!-- Begin a table for the contained elements -->
					<table class="SummaryTable" border="1">
						<tr>
							<td class="SummaryTitle" colspan="2">
								<a name="nestedComponentsSummary">
									<xsl:copy-of
										select="$nestedComponentsNavName" />
								</a>
							</td>
						</tr>
						<!-- Loop for each attribute creating an entry in the table -->
						<xsl:for-each select="packagedElement[@xsi:type='uml:Component']">
							<xsl:sort select="@name" />
							<tr>
								<td>
									<xsl:call-template
										name="outputImageAndTextLinkHTML">
										<xsl:with-param name="link">
											<xsl:call-template
												name="createElementContentFileName" />
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
									<br />
								</td>
								<td>
									<!-- Documentation -->
									<xsl:call-template
										name="createDocumentation" />
								</td>
							</tr>
						</xsl:for-each>
						<!-- Close out the nested components table -->
					</table>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	
	
	


	<!-- Creates HTML contents for URLs -->
	<xsl:template name="createURLsInfo">
		<xsl:param name="mode" select="''" />
		<xsl:variable name="elementId" select="@xmi:id" />
		<xsl:variable name="attachedURLs"
			select="key('TableCommentsByAttachedElement', $elementId)" />
		<xsl:if test="count($attachedURLs[@isURL = 'true']) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$urlsNavName" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'urlsSummary')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$urlsNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<!-- Create an 'URLs' table for the content HTML -->
					<!-- Begin a table for the contained elements -->
					<table class="SummaryTable" border="1">
						<tr>
							<td class="SummaryTitle" colspan="1">
								<a name="urlsSummary">
									<xsl:copy-of
										select="$urlsTableTitle" />
								</a>
							</td>
						</tr>
						<!-- Loop for each comment creating an entry in the table -->
						<xsl:for-each
							select="$attachedURLs[@isURL = 'true']">
							<tr>
								<td>
									<!-- URLs -->
									<xsl:call-template name="outputImageAndTextLinkHTML">
										<xsl:with-param name="link"
											select="@body" />
										<xsl:with-param name="text"
											select="@body" />
										<!-- this is done to prevent broken links in the lower left -->
										<xsl:with-param name="target"
											select="'_top'" />
									</xsl:call-template>
								</td>
							</tr>
						</xsl:for-each>
						<!-- Close out the URLs table -->
					</table>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<!-- Creates HTML contents for Activity Edge -->
	<xsl:template name="createActivityEdgeInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if test="count(edge | node//containedEdge) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$ActivityEdgeNavName" />
						</xsl:attribute>
						<xsl:attribute name="link">#edgeSummary</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$ActivityEdgeNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$ActivityEdgeNavName" />
						</xsl:attribute>
						<xsl:attribute name="link">#edgeDetail</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$ActivityEdgeNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<!-- Create an 'Activity Edge' table for the content HTML -->
					<!-- Begin a table for the contained elements -->
					<table class="SummaryTable" border="1">
						<tr>
							<td class="SummaryTitle" colspan="2">
								<a name="edgeSummary">
									<xsl:copy-of
										select="$ActivityEdgeTitle" />
								</a>
							</td>
						</tr>
						<!-- Loop for each attribute creating an entry in the table -->
						<xsl:for-each
							select="edge | node//containedEdge">
							<xsl:sort select="@name" />
							<tr>
								<td class="packagedElementType">
									<xsl:apply-templates
										select="./publish:properties/publish:property[@name='Type']" />
								</td>
								<td>
									<xsl:call-template	name="outputImageAndTextLinkHTML">
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
									<br />
									<!-- Documentation -->
									<xsl:call-template
										name="createDocumentation" />
								</td>
							</tr>
						</xsl:for-each>
						<!-- Close out the activity edge table -->
					</table>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<!-- Create an 'Activity edge Detail' table header for the content HTML -->
					<table class="DetailsTable">
						<tr>
							<td class="DetailsTitle" colspan="2">
								<a name="edgeDetail">
									<xsl:copy-of
										select="$ActivityEdgeDetailsTableTitle" />
								</a>
							</td>
						</tr>
					</table>
					<!-- Loop for each operation creating HTML for the full deatils of the attribute -->
					<xsl:for-each select="edge | node//containedEdge">
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
	</xsl:template>

	<!-- Creates HTML contents for Interactions -->
	<xsl:template name="createInteractionsInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if
			test="count(ownedBehavior[@xsi:type='uml:Interaction']) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$interactionTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">#Interactions</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$interactionTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<table class="PackagesTable" border="1">
						<tr>
							<td class="PackagesTitle" colspan="2">
								<a name="Interactions">
									<xsl:copy-of
										select="$interactionTableTitle" />
								</a>
							</td>
						</tr>
						<xsl:for-each
							select="descendant::ownedBehavior[@xsi:type='uml:Interaction']">
							<xsl:sort select="@name" />
							<xsl:variable
								name="interactionContentFileName">
								<xsl:call-template
									name="createElementContentFileName" />
							</xsl:variable>
							<xsl:variable name="iconImageFileName">
								<xsl:call-template
									name="createElementIconFileName" />
							</xsl:variable>
							<tr>
								<td class="ClassTableEntryLink">
									<xsl:call-template	name="outputImageAndTextLinkHTML">
										<xsl:with-param name="link"
											select="$interactionContentFileName" />
										<xsl:with-param name="image">
											<xsl:if
												test="@publish:icon != ''">
												<xsl:value-of
													select="$iconImageFileName" />
											</xsl:if>
										</xsl:with-param>
										<xsl:with-param name="text">
											<xsl:value-of
												select="@name" />
										</xsl:with-param>
									</xsl:call-template>
								</td>
								<td>
									<!-- Documentation -->
									<xsl:call-template
										name="createDocumentation" />
								</td>
							</tr>
						</xsl:for-each>
						<!-- Close the Interactions table -->
					</table>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<!-- Creates html contents for Lifeline -->
	<xsl:template name="createLifelineInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if
			test="count(lifeline[@xsi:type='uml:Lifeline']) &gt; 0">
			<xsl:choose>
				<!-- TOP/BOTTOM PAGE LINK -->
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$lifelineTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $lifelineTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$lifelineTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<!-- details -->
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$lifelineTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $lifelineDetailsTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$lifelineTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<!-- summary -->
				<xsl:when test="$mode = 'summary'">
					<p />
					<table class="SummaryTable" border="1">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$lifelineTableTitle" />
						</xsl:call-template>
						<!-- Loop for each element creating an entry in the table -->
						<xsl:for-each select="lifeline">
							<xsl:sort select="@name" />
							<xsl:call-template
								name="createSummaryTableContentRow" />
						</xsl:for-each>
					</table>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$lifelineDetailsTableTitle" />
						</xsl:call-template>
					</table>
					<xsl:for-each select="lifeline">
						<xsl:sort select="@name" />
						<xsl:call-template
							name="createDetailTableContentRow" />
						<xsl:if test="position() != last()">
							<hr />
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<!-- Creates HTML contents for Combined Fragment -->
	<xsl:template name="createCombinedFragmentInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if
			test="count(fragment[@xsi:type='uml:CombinedFragment']) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$combinedFragmentTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $combinedFragmentTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$combinedFragmentTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<table class="PackagesTable" border="1">
						<tr>
							<td class="PackagesTitle" colspan="2">
								<xsl:call-template name="createTableTitle">
									<xsl:with-param name="title"
										select="$combinedFragmentTableTitle" />
								</xsl:call-template>
							</td>
						</tr>
						<xsl:for-each
							select="fragment[@xsi:type='uml:CombinedFragment']">
							<xsl:sort select="@name" />
							<xsl:variable
								name="contentFileName">
								<xsl:call-template
									name="createElementContentFileName" />
							</xsl:variable>
							<xsl:variable name="iconImageFileName">
								<xsl:call-template
									name="createElementIconFileName" />
							</xsl:variable>
							<tr>
								<td class="ClassTableEntryLink">
									<xsl:call-template	name="outputImageAndTextLinkHTML">
										<xsl:with-param name="link"
											select="$contentFileName" />
										<xsl:with-param name="image">
											<xsl:if
												test="@publish:icon != ''">
												<xsl:value-of
													select="$iconImageFileName" />
											</xsl:if>
										</xsl:with-param>
										<xsl:with-param name="text">
											<xsl:value-of
												select="@name" />
										</xsl:with-param>
									</xsl:call-template>
								</td>
								<td>
									<!-- Documentation -->
									<xsl:call-template
										name="createDocumentation" />
								</td>
							</tr>
						</xsl:for-each>
						<!-- Close the Interactions table -->
					</table>
				</xsl:when>
				<!--xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$combinedFragmentTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $combinedFragmentDetailsTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$combinedFragmentTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when-->
				<!--xsl:when test="$mode = 'summary'">
					<p />
					<table class="SummaryTable" border="1">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$combinedFragmentTableTitle" />
						</xsl:call-template>
						<xsl:for-each
							select="fragment[@xsi:type='uml:CombinedFragment']">
							<xsl:sort select="@name" />
							<xsl:call-template
								name="createSummaryTableContentRow" />
						</xsl:for-each>
					</table>
				</xsl:when-->
				<!--xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$combinedFragmentDetailsTableTitle" />
						</xsl:call-template>
					</table>
					<xsl:for-each
						select="fragment[@xsi:type='uml:CombinedFragment']">
						<xsl:sort select="@name" />
						<xsl:call-template
							name="createDetailTableContentRow" />
						<xsl:if test="position() != last()">
							<hr />
						</xsl:if>
					</xsl:for-each>
				</xsl:when-->
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<!-- Creates HTML contents for Execution Occurrence -->
	<xsl:template name="createExecutionOccurenceInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if
			test="count(fragment[@xsi:type='uml:BehaviorExecutionSpecification']) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$executionOccurrenceTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $executionOccurrenceTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$executionOccurrenceTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$executionOccurrenceTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $executionOccurrenceDetailsTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$executionOccurrenceTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<table class="SummaryTable" border="1">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$executionOccurrenceTableTitle" />
						</xsl:call-template>
						<!-- Loop for each element creating an entry in the table -->
						<xsl:for-each
							select="fragment[@xsi:type='uml:BehaviorExecutionSpecification']">
							<xsl:sort select="@name" />
							<xsl:call-template
								name="createSummaryTableContentRow" />
						</xsl:for-each>
					</table>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$executionOccurrenceDetailsTableTitle" />
						</xsl:call-template>
					</table>
					<xsl:for-each
						select="fragment[@xsi:type='uml:BehaviorExecutionSpecification']">
						<xsl:sort select="@name" />
						<xsl:call-template
							name="createDetailTableContentRow" />
						<xsl:if test="position() != last()">
							<hr />
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<!-- Creates HTML contents for Interaction Occurrence -->
	<xsl:template name="createInteractionOccurenceInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if
			test="count(fragment[@xsi:type='uml:InteractionOccurrence']) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$interactionOccurrenceTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $interactionOccurrenceTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$interactionOccurrenceTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$interactionOccurrenceTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $interactionOccurrenceDetailsTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$interactionOccurrenceTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<table class="SummaryTable" border="1">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$interactionOccurrenceTableTitle" />
						</xsl:call-template>
						<!-- Loop for each element creating an entry in the table -->
						<xsl:for-each
							select="fragment[@xsi:type='uml:InteractionOccurrence']">
							<xsl:sort select="@name" />
							<xsl:call-template
								name="createSummaryTableContentRow" />
						</xsl:for-each>
					</table>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$interactionOccurrenceDetailsTableTitle" />
						</xsl:call-template>
					</table>
					<xsl:for-each
						select="fragment[@xsi:type='uml:InteractionOccurrence']">
						<xsl:sort select="@name" />
						<xsl:call-template
							name="createDetailTableContentRow" />
						<xsl:if test="position() != last()">
							<hr />
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<!-- Creates HTML contents for Stop -->
	<xsl:template name="createStopInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if test="count(fragment[@xsi:type='uml:Stop']) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$stopTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $stopTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$stopTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$stopTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $stopDetailsTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$stopTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<table class="SummaryTable" border="1">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$stopTableTitle" />
						</xsl:call-template>
						<!-- Loop for each element creating an entry in the table -->
						<xsl:for-each
							select="fragment[@xsi:type='uml:Stop']">
							<xsl:sort select="@name" />
							<xsl:call-template
								name="createSummaryTableContentRow" />
						</xsl:for-each>
					</table>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$stopDetailsTableTitle" />
						</xsl:call-template>
					</table>
					<xsl:for-each
						select="fragment[@xsi:type='uml:Stop']">
						<xsl:sort select="@name" />
						<xsl:call-template
							name="createDetailTableContentRow" />
						<xsl:if test="position() != last()">
							<hr />
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<!-- Creates HTML info for Template Bindings -->
	<xsl:template name="createTemplateBindingsInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if
			test="count(templateBinding[@xsi:type='uml:TemplateBinding']) &gt; 0">
			<!-- Template Bindings and Parameter substitutions summary bookmarks -->
			<!-- generate the Template Bindings summary bookmark -->
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$templateBindingTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">#TemplateBindingsSummary</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$templateBindingTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$templateBindingTableTitle" />
						</xsl:attribute>

						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $templateBindingDetailsTableTitle)" />
						</xsl:attribute>

						<xsl:attribute name="title">
							<xsl:copy-of
								select="$templateBindingTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<!-- Create a 'templateBinding' table for the content HTML -->
					<!-- Begin a table for the contained elements -->
					<table class="SummaryTable" border="1">
						<tr>
							<td class="SummaryTitle" colspan="2">
								<a name="TemplateBindingsSummary">
									<xsl:copy-of
										select="$templateBindingTableTitle" />
								</a>
							</td>
						</tr>
						<!-- Loop for each attribute creating an entry in the table -->
						<xsl:for-each select="templateBinding">
							<xsl:sort select="@name" />
							<tr>
								<!--
									<td class="packagedElementType">
									<xsl:apply-templates select="./publish:properties/publish:property[@name='Type']"/>
									</td>
								-->
								<td>
									<xsl:call-template	name="outputImageAndTextLinkHTML">
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
									<br />
								</td>
								<td>
									<!-- Documentation -->
									<xsl:call-template
										name="createDocumentation" />
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$templateBindingDetailsTableTitle" />
						</xsl:call-template>
					</table>
					<!-- create detail tables for each binding	-->
					<xsl:for-each select="templateBinding">
						<xsl:sort select="@name" />
						<xsl:call-template
							name="createDetailTableForTemplateBinding" />
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<!-- Create HTML contents for Template Parameter Substitution -->
	<xsl:template name="createTemplateParameterSubstitutionInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if
			test="count(templateBinding/parameterSubstitution[@xsi:type='uml:TemplateParameterSubstitution']) &gt; 0">
			<xsl:choose>
				<!-- generate the Template Parameter Substitution summary bookmark -->
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$templateParameterSubstitutionTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">#TemplateParameterSubstitutionSummary</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$templateParameterSubstitutionTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$templateParameterSubstitutionTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $templateParameterSubstitutionDetailsTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$templateParameterSubstitutionTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<!-- Create a 'parameterSubstitution' table for the content HTML -->
					<!-- Begin a table for the contained elements -->
					<table class="SummaryTable" border="1">
						<tr>
							<td class="SummaryTitle" colspan="2">
								<a
									name="TemplateParameterSubstitutionSummary">
									<xsl:copy-of
										select="$templateParameterSubstitutionTableTitle" />
								</a>
							</td>
						</tr>
						<!-- Loop for each TPS creating an entry in the table -->
						<xsl:for-each
							select="templateBinding/parameterSubstitution">
							<xsl:sort select="@name" />
							<tr>
								<!--
									<td class="packagedElementType">
									<xsl:apply-templates select="./publish:properties/publish:property[@name='Type']"/>
									</td>
								-->
								<td>
									<xsl:call-template	name="outputImageAndTextLinkHTML">
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
									<br />
								</td>
								<td>
									<!-- Documentation -->
									<xsl:call-template
										name="createDocumentation" />
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$templateParameterSubstitutionDetailsTableTitle" />
						</xsl:call-template>
					</table>
					<!-- create detail tables for each binding	-->
					<xsl:for-each select="templateBinding">
						<xsl:sort select="@name" />
						<xsl:call-template
							name="createDetailTableForTemplateParameterSubstitutions" />
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<!-- Create HTML contents for Template Signature -->
	<xsl:template name="createTemplateSignatureInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if
			test="count(ownedTemplateSignature[@xsi:type='uml:TemplateSignature'] |
			      		ownedTemplateSignature[@xsi:type='uml:RedefinableTemplateSignature']) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<!-- Template Signature summary -->
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$templateSignatureTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $templateSignatureTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$templateSignatureTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$templateSignatureTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $templateSignatureDetailsTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$templateSignatureTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<table class="SummaryTable" border="1">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$templateSignatureTableTitle" />
						</xsl:call-template>
						<!-- Loop for each element creating an entry in the table -->
						<xsl:for-each
							select="ownedTemplateSignature/ownedParameter">
							<xsl:sort select="@name" />
							<xsl:call-template
								name="createSummaryTableContentRowForTemplateParamters" />
						</xsl:for-each>
					</table>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$templateSignatureDetailsTableTitle" />
						</xsl:call-template>
					</table>
					<!-- Loop for each element creating an entry in the table	-->
					<xsl:for-each
						select="ownedTemplateSignature/ownedParameter">
						<xsl:sort select="@name" />
						<xsl:call-template
							name="createDetailTableContentRowForTemplateParamters" />
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<!-- Create link to Properties summary -->
	<xsl:template name="createPropertiesSummaryLink">
		<xsl:if test="count(publish:properties) &gt; 0">
			<publish:bookmarklink>
				<xsl:attribute name="name">
					<xsl:copy-of select="$propertiesNavName" />
				</xsl:attribute>
				<xsl:attribute name="link">#propertiesTable</xsl:attribute>
				<xsl:attribute name="title">
					<xsl:copy-of select="$propertiesNavName" />
				</xsl:attribute>
			</publish:bookmarklink>
		</xsl:if>
	</xsl:template>

	<!-- Create HTML contents for State -->
	<xsl:template name="createStateInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if
			test="count(subvertex[@xsi:type='uml:State'] | subvertex[@xsi:type='uml:FinalState']) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$stateTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $stateTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$stateTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$stateTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $stateDetailsTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$stateTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<table class="SummaryTable" border="1">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$stateTableTitle" />
						</xsl:call-template>
						<!-- Loop for each element creating an entry in the table -->
						<xsl:for-each
							select="subvertex[@xsi:type='uml:State'] | subvertex[@xsi:type='uml:FinalState']">
							<xsl:sort select="@name" />
							<xsl:call-template
								name="createSummaryTableContentRow" />
						</xsl:for-each>
					</table>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$stateDetailsTableTitle" />
						</xsl:call-template>
					</table>
					<xsl:for-each
						select="subvertex[@xsi:type='uml:State'] | subvertex[@xsi:type='uml:FinalState']">
						<xsl:sort select="@name" />
						<xsl:call-template
							name="createDetailTableContentRow" />
						<xsl:if test="position() != last()">
							<hr />
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<!-- Create HTML contents for Pseudo State -->
	<xsl:template name="createPseudoStateInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if
			test="count(subvertex[@xsi:type='uml:Pseudostate']) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$pseudoStateTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $pseudoStateTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$pseudoStateTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of
								select="$pseudoStateTableTitle" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', $pseudoStateDetailsTableTitle)" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of
								select="$pseudoStateTableTitle" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<table class="SummaryTable" border="1">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$pseudoStateTableTitle" />
						</xsl:call-template>
						<!-- Loop for each element creating an entry in the table -->
						<xsl:for-each
							select="subvertex[@xsi:type='uml:Pseudostate']">
							<xsl:sort select="@name" />
							<xsl:call-template
								name="createSummaryTableContentRow" />
						</xsl:for-each>
					</table>
				</xsl:when>
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<table class="DetailsTable">
						<xsl:call-template name="createTableTitle">
							<xsl:with-param name="title"
								select="$pseudoStateDetailsTableTitle" />
						</xsl:call-template>
					</table>
					<xsl:for-each
						select="subvertex[@xsi:type='uml:Pseudostate']">
						<xsl:sort select="@name" />
						<xsl:call-template
							name="createDetailTableContentRow" />
						<xsl:if test="position() != last()">
							<hr />
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<xsl:template name="createDiagramInfo">
		<xsl:param name="mode" select="''" />
		<xsl:variable name="packageNavigationListFileName">
			<xsl:call-template
				name="createPackageNavigationListFileName" />
		</xsl:variable>
		<xsl:variable name="packageNavigationListFullPathName">
			<xsl:call-template name="prependFullPathToFileName">
				<xsl:with-param name="fileName"
					select="$packageNavigationListFileName" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:if
			test="count(contents[@xsi:type='notation:Diagram']) &gt; 0">
			<xsl:choose>
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$diagramsTableHeading" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'diagramsSummary')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$diagramsTableHeading" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<xsl:when test="$mode = 'summary'">
					<p />
					<table border="1" class="ContainedElements">
						<tr>
							<td class="SummaryTitle" colspan="3">
								<a name="diagramsSummary">
									<xsl:copy-of
										select="$diagramsTableHeading" />
								</a>
							</td>
						</tr>
						<xsl:for-each
							select="descendant::contents[@xsi:type='notation:Diagram']">
							<xsl:sort select="@name" />
							<xsl:variable name="diagramContentFileName">
								<xsl:call-template
									name="createElementContentFileName" />
							</xsl:variable>
							<xsl:variable name="diagramIconImageFileName">
								<xsl:call-template
									name="createElementIconFileName" />
							</xsl:variable>
		
							<tr class="ClassTableEntry">
								<td class="ClassTableEntryLink">
									<xsl:call-template name="outputImageAndTextLinkHTML">
										<xsl:with-param name="link"
											select="$diagramContentFileName" />
										<xsl:with-param name="image">
											<xsl:if
												test="@publish:icon != ''">
												<xsl:value-of
													select="$diagramIconImageFileName" />
											</xsl:if>
										</xsl:with-param>
										<xsl:with-param name="text"
											select="@name" />
									</xsl:call-template>
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>



	<!-- =============================================== -->
	<!-- Create bookmark links to various sections in the HTML file -->
	<!-- =============================================== -->
	<xsl:template name="fillBookmarkLinksSection">
		<xsl:call-template name="fillSection">
			<xsl:with-param name="mode" select="'link'" />
		</xsl:call-template>
	</xsl:template>

	<!-- =============================================== -->
	<!-- Create bookmark details                         -->
	<!-- =============================================== -->
	<xsl:template name="fillBookmarkDetailsSection">
		<xsl:call-template name="fillSection">
			<xsl:with-param name="mode" select="'details'" />
		</xsl:call-template>
	</xsl:template>

	<!-- =============================================== -->
	<!-- Create summary sections                         -->
	<!-- =============================================== -->
	<xsl:template name="fillSummarySection">
		<xsl:call-template name="fillSection">
			<xsl:with-param name="mode" select="'summary'" />
		</xsl:call-template>
	</xsl:template>

	<!-- =============================================== -->
	<!-- Create detail tables for the contained elements -->
	<!-- =============================================== -->
	<xsl:template name="fillFullDetailsSection">
		<xsl:call-template name="fillSection">
			<xsl:with-param name="mode" select="'detailsTable'" />
		</xsl:call-template>
	</xsl:template>
</xsl:stylesheet>
