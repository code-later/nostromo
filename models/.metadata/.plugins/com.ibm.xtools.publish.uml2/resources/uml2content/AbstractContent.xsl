<?xml version="1.0" encoding="UTF-8"?>
<!--                                                                        -->
<!-- Licensed Materials - Property of IBM                                   -->
<!-- Copyright IBM Corp. 2003, 2007.  All Rights Reserved.                  -->
<!--                                                                        -->
<!-- US Government Users Restricted Rights - Use, duplication or disclosure -->
<!-- restricted by GSA ADP Schedule Contract with IBM Corp.                 -->
<!--                                                                        -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <!-- ================================================================= -->
    <!-- template that defines the order of the content on the right frame -->
    <!-- ================================================================= -->
<xsl:include href="file:/C:/Programme/IBM/SDPShared/plugins/com.ibm.xtools.traceability_7.1.0.v20071025_2003/./templates/trace.xsl"/>
    <xsl:template name="fillSection">
        <xsl:param name="mode" select="''"/>
	   
	        <xsl:call-template name="createPropertyInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createDiagramInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createLiteralsInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createAttributesInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createRolesInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createOperationsInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createNestedClassifiersInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createNestedActivitiesInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createComponentsInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createURLsInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createActivityNodeInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createActivityEdgeInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createInteractionsInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createLifelineInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createCombinedFragmentInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createExecutionOccurenceInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createInteractionOccurenceInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createStopInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createTemplateSignatureInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createTemplateBindingsInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createTemplateParameterSubstitutionInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createStateInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createPseudoStateInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createCommentsInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createConstraintsInfo">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
	        <xsl:call-template name="createTraceRelationships">
	            <xsl:with-param name="mode" select="$mode"/>
	        </xsl:call-template>
	   
    </xsl:template>
</xsl:stylesheet>