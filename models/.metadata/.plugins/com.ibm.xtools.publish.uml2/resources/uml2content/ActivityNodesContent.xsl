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
    exclude-result-prefixes="xmi uml xsi publish">
    <!-- Include content generation style sheets for specific types of subvertex -->
    <xsl:import href="NamedElementContent.xsl"/>
    <xsl:output method="html"/>
    <!-- ======================================================================================== -->
    <!-- Create content for Activity Node -->
    <!-- ======================================================================================== -->
    <xsl:template name="createActivityNodeContent" match="node | containedNode" mode="createElementContent">
        <xsl:param name="titlePattern" select="@name"/>
        <xsl:param name="descriptionPattern">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'Description.Content'"/>
            </xsl:call-template>
        </xsl:param>
        <xsl:param name="keywordsWithPatterns" select="''"/>
        <xsl:choose>
                <xsl:when test="@xsi:type='uml:ApplyFunctionAction' or @xsi:type='uml:StructuredActivityNode'   
                    or @xsi:type='uml:DecisionNode' or @xsi:type='uml:MergeNode'
                    or @xsi:type='uml:ForkNode' or @xsi:type='uml:JoinNode'
                    or @xsi:type='uml:InitialNode' or  @xsi:type='uml:ActivityFinalNode' 
                    or @xsi:type='uml:FlowFinalNode' or  @xsi:type='uml:CentralBufferNode'
                    or @xsi:type='uml:DataStoreNode' or @xsi:type='uml:Action' 
                    or @xsi:type='uml:CallOperationAction'">
    
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.ActivityNode'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            
            <xsl:otherwise>
                <!-- This is an error - since it means we have hit a activity node that we do not know 
             how to generate contents for -->
                <xsl:message terminate="no">
                    <xsl:text>The current element was not handled by the
                        template createActivityNodeContent.</xsl:text>
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
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    
	<!-- Creates HTML content for Activity Node -->
	<xsl:template name="createActivityNodeInfo">
		<xsl:param name="mode" select="''" />
		<xsl:if test="count(node) &gt; 0">
			<xsl:choose>
				<!-- SUMMARY LINK -->
				<xsl:when test="$mode = 'link'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$ActivityNodeNavName" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'nodeSummary')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$ActivityNodeNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<!-- DETAILS -->
				<xsl:when test="$mode = 'details'">
					<publish:bookmarklink>
						<xsl:attribute name="name">
							<xsl:copy-of select="$ActivityNodeNavName" />
						</xsl:attribute>
						<xsl:attribute name="link">
							<xsl:value-of
								select="concat('#', 'nodeDetail')" />
						</xsl:attribute>
						<xsl:attribute name="title">
							<xsl:copy-of select="$ActivityNodeNavName" />
						</xsl:attribute>
					</publish:bookmarklink>
				</xsl:when>
				<!-- SUMMARY TABLE-->
				<xsl:when test="$mode = 'summary'">
					<p />
					<!-- Create an 'activity node' table for the content HTML -->
					<!-- Begin a table for the contained elements -->
					<table class="SummaryTable" border="1">
						<tr>
							<td class="SummaryTitle" colspan="2">
								<a name="nodeSummary">
									<xsl:copy-of
										select="$ActivityNodeTitle" />
								</a>
							</td>
						</tr>
						<!-- Loop for each attribute creating an entry in the table -->
						<xsl:for-each
							select="node | node//containedNode">
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
						<!-- Close out the activity node table -->
					</table>
				</xsl:when>
				<!-- DETAILS TABLE-->
				<xsl:when test="$mode = 'detailsTable'">
					<p />
					<!-- Create an 'Activity Node Detail' table header for the content HTML -->
					<table class="DetailsTable">
						<tr>
							<td class="DetailsTitle" colspan="2">
								<a name="nodeDetail">
									<xsl:copy-of
										select="$ActivityNodeDetailsTableTitle" />
								</a>
							</td>
						</tr>
					</table>
					<!-- Loop for each node creating HTML for the full deatils of the node -->
					<xsl:for-each select="node | node//containedNode">
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
									<xsl:copy-of select="$propertiesTitle" />
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
    
</xsl:stylesheet>
