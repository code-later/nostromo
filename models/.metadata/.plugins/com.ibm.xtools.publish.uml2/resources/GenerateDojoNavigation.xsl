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
	xmlns:redirect="http://xml.apache.org/xalan/redirect" xmlns:dojo="http://dojotoolkit.org"
	extension-element-prefixes="redirect" exclude-result-prefixes="xmi uml xsi publish dojo">
	<xsl:output method="html"/>
	<!-- 3. Create initial top left frame contents (xxx-navigation-frame.html) TLF -->
	<xsl:template match="packagedElement[@xsi:type='uml:Package'] | uml:Model | uml:Profile"
		mode="createDojoNavigation">
		<!-- variables -->
		<xsl:variable name="packageListFileName">
			<xsl:call-template name="createPackageListFileName"/>
		</xsl:variable>
		<!-- Generate the file name needed for output -->
		<xsl:variable name="dojoFullPathName">
			<xsl:call-template name="prependFullPathToFileName">
				<xsl:with-param name="fileName" select="$packageListFileName"/>
			</xsl:call-template>
		</xsl:variable>
		
	    <xsl:variable name="modelsTitle">
	        <xsl:call-template name="getLocalizedString">
	            <xsl:with-param name="key" select="'NavTree.Models'"/>
	        </xsl:call-template>
	    </xsl:variable>
		
	    <xsl:variable name="diagramsTitle">
	        <xsl:call-template name="getLocalizedString">
	            <xsl:with-param name="key" select="'NavTree.Diagrams'"/>
	        </xsl:call-template>
	    </xsl:variable>
		
		<!-- xxx-navigation-frame.html -->
		<redirect:write file="{$dojoFullPathName}"/>
		<redirect:write file="{$dojoFullPathName}" append="true">
			<html lang="en">
				<head>
					<title>Dojo Tree Widget Test</title>
					<script type="text/javascript">
						var djConfig = {isDebug: true, debugAtAllCosts:
						true };
					</script>
					<script type="text/javascript" src="dojo.js">
					</script>
					<script type="text/javascript">
						dojo.require("dojo.lang.*");
						dojo.require("dojo.widget.*");
						dojo.require("dojo.widget.Tree");
						dojo.require("dojo.widget.TreeSelector");
						dojo.require("dojo.i18n.common");
						dojo.hostenv.writeIncludes();


						dojo.addOnLoad(
							function() {
								dojo.event.topic.subscribe("nodeSelected",
									function(message) {
							   			parent.elementFrame.location.href = message.node.object;
									} 
								);
						});

					</script>
				</head>
				<body>
					<!-- use packages.xml file for the top level element -->
					<xsl:variable name="hierarchyFilePath">
						<xsl:call-template name="constructAuxilliaryXmlFilepath">
							<xsl:with-param name="guid" select="@xmi:id"/>
							<xsl:with-param name="suffix" select="'hierarchy.xml'"/>
						</xsl:call-template>
					</xsl:variable>
					<xsl:variable name="diagramsFilePath">
						<xsl:call-template name="constructAuxilliaryXmlFilepath">
							<xsl:with-param name="guid" select="@xmi:id"/>
							<xsl:with-param name="suffix" select="'diagrams.xml'"/>
						</xsl:call-template>
					</xsl:variable>
					<xsl:variable name="allElements" select="document($hierarchyFilePath)"/>
					<xsl:variable name="allDiagrams" select="document($diagramsFilePath)"/>
					<h2>
						<xsl:value-of select="$allElements/child::*/@name"/>
					</h2>
					
					
					<xsl:variable name="topHtmlPath">
						<xsl:value-of select="concat($allElements/child::*/@xmi:id, &apos;-content.html&apos;)"/>
					</xsl:variable>
					<xsl:variable name="topIconPath">
						<xsl:value-of select="concat(&apos;../images/&apos;, $allElements/child::*/@publish:icon)"/>
					</xsl:variable>
					
					
					<dojo:TreeSelector widgetId="treeSelector" eventNames="select:nodeSelected"> </dojo:TreeSelector>
					<div dojoType="Tree" selector="treeSelector" expandLevel="3">
					    <div id="_DIAGRAMS_ROOT_NODE_" object="{$topHtmlPath}" childIconSrc="{$topIconPath}" title="{$diagramsTitle}" dojoType="TreeNode">
							<xsl:apply-templates select="$allDiagrams/publish:diagrams/publish:diagram" mode="diagrams"/>
						</div>
					    <div id="_MODELS_ROOT_NODE_"  object="{$topHtmlPath}" childIconSrc="{$topIconPath}" title="{$modelsTitle}" dojoType="TreeNode">
							<xsl:apply-templates select="$allElements" mode="models"/>
						</div>
					</div>
				</body>
			</html>
		</redirect:write>
	</xsl:template>
	
	<!-- build the Model Explorer navigation tree: diagrams -->
	<xsl:template match="*" mode="diagrams">
		<xsl:variable name="iconPath">
			<xsl:value-of select="concat(&apos;../images/&apos;, @publish:icon)"/>
		</xsl:variable>
		<xsl:variable name="htmlPath">
			<xsl:value-of select="concat(@xmi:id, &apos;-content.html&apos;)"/>
		</xsl:variable>
		<div dojoType="TreeNode" title="{@publish:qualifiedname}" childIconSrc="{$iconPath}" object="{$htmlPath}"
			id="{@xmi:id}">
			<xsl:for-each select="./child::*">
				<xsl:apply-templates select="." mode="diagrams"/>
			</xsl:for-each>
		</div>
	</xsl:template>
	
	<!-- build the Model Explorer navigation tree: model elements -->
	<xsl:template match="*" mode="models">
	    <!-- we need to filter out 'appliedProfiles' nodes as they don't carry any page associations -->
		<xsl:if test=
					"name() != 'appliedProfiles' and 
					 name() != 'ancestor'">
			
			<xsl:variable name="iconPath">
				<xsl:value-of select="concat(&apos;../images/&apos;, @publish:icon)"/>
			</xsl:variable>
			<xsl:variable name="htmlPath">
				<xsl:choose >
					<xsl:when test="
							(@xsi:type = 'uml:Property' )    		or
							(@xsi:type = 'uml:Operation')  			or
							(@xsi:type = 'uml:OpaqueAction')  		or
							(@xsi:type = 'uml:AcceptEventAction') 	or
							(@xsi:type = 'uml:BehaviorExecutionSpecification')
					               ">
						<xsl:value-of select="concat(../@xmi:id, &apos;-content.html&apos;, '#', @xmi:id)"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="concat(@xmi:id, &apos;-content.html&apos;)"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<div dojoType="TreeNode" title="{@name}" childIconSrc="{$iconPath}" object="{$htmlPath}"
				id="{@xmi:id}" fqn="{name()}">
				<xsl:for-each select="./child::*">
					<xsl:apply-templates select="." mode="models"/>
				</xsl:for-each>
			</div>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
