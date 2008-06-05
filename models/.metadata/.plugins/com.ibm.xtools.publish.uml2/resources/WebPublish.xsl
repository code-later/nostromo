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
    
    <!-- Localization support -->
    <xsl:import href="l10n/Localize.xsl" />
    
    <!-- Global Variables -->
    <xsl:import href="GlobalVariables.xsl"/>
    
    <!-- Publish information for Publish UI plug-in -->
    <publish:report name="Web Publish" author="IBM Rational Software">
        <publish:style>Web</publish:style>
        <publish:description>This is the publish for web style sheet.</publish:description>
    </publish:report>
    <xsl:output method="html"/>
    <!-- Style sheet parameters -->
    <!-- Location for root of output -->
    <xsl:param name="outputDir"/>
    <!-- Location of images -->
    <xsl:param name="imagesDir"/>
    <!-- Location of html content files -->
    <xsl:param name="contentDir"/>
    <!-- The name of the root html file -->
    <xsl:param name="rootFile" select="'root.html'"/>
    <!-- The name of the overview html file (start page)  -->
    <xsl:param name="overviewFile"/>
    <!-- The file name for localization lookup, default  is English -->
    <xsl:param name="stringTableFileName" select="'messages.xml'"/> 
    
    <!-- contains name of the containing model. This information is necessary to strip off model prefix for packages
         frame and other places in HTMLs. -->
    <xsl:param name="modelName" select="'UNNAMED'"/>
    
    <xsl:param name="navigation_layout" select="'NAVI_JAVADOC'"/>
    
    <!-- Common utility templates -->
    <xsl:include href="utilities/HTMLUtility.xsl"/>
    <!-- UML2 Web Publish Utilities-->
    <xsl:include href="utilities/UML2Utility.xsl"/>
    <xsl:include href="NavigationBar.xsl"/>
    
    <!-- ======================================================================================== -->
    <!-- Main template - currently this expects that there will be a single root object, either a 
       model or package -->
    <!-- ======================================================================================== -->
    <xsl:template match="/">
        <!-- Verify that redirect extension is available
            Commented out to get past XSLTC obstacles. For some reason this
            condition is false when XSLTC processor is active. -->
        
        <xsl:if test="element-available('redirect:write') = false()">
            <xsl:message terminate="yes">The extension to support multiple file
                output (redirect:write) is not available - unable to continue.</xsl:message>
        </xsl:if>

         
        <xsl:variable name="titlePattern">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'Title.TopPattern'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="descriptionPattern">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'Description.TopPattern'"/>
            </xsl:call-template>
        </xsl:variable>
        
        <!-- Generate the Cascading Style Sheets File -->
        <xsl:call-template name="generateCSSFile"/>
        
        <!-- This is the new approach based on multiple intermediate xmls for each package in selection -->
        
        <!-- Optimize query results -->
        <xsl:variable name="nodesetEntryElements" select="uml:Model | packagedElement[@xsi:type='uml:Package'] | uml:Profile"/>
        
        <!-- TOP level elements are processed -->
        <xsl:variable name="isTopLevelElement" select="$globalTopLevelId = $nodesetEntryElements/@xmi:id"/>
        
        <xsl:if test="$isTopLevelElement = 'true()'">
            <!-- Create frames layout: 
                    - navigation frame (top left),  
                    - package frame (bottom left),
                    - content frame (right) -->
            <xsl:apply-templates
                select="$nodesetEntryElements" mode="createBaseFrame">
                <xsl:with-param name="titlePattern" select="$titlePattern"/>
                <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
            </xsl:apply-templates>
            
            <!-- Create initial top left frame contents (xxx-navigation-frame.html) TLF 
                 Only executed for top level elements to ensure a single xxx-navigation-frame.html file -->
            <xsl:apply-templates
                select="$nodesetEntryElements" mode="SplitMode_PackagesNavigationFrame">
                <xsl:with-param name="titlePattern" select="$titlePattern"/>
                <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
            </xsl:apply-templates>
            
            <xsl:if test="$navigation_layout = 'NAVI_DOJO'">
	            <!-- Create Dojo based navigation frame content
	                 Only executed for top level elements to ensure a single xxx-navigation-frame.html file -->
	            <xsl:apply-templates
	                select="$nodesetEntryElements" mode="createDojoNavigation">
	                <xsl:with-param name="titlePattern" select="$titlePattern"/>
	                <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
	            </xsl:apply-templates> 
            </xsl:if>
            
            <!-- Create the navigation page for the lower left hand all elements/diagrams navigation frame page
             
             GenerateAllElementsList.xsl-->
            <xsl:apply-templates
                select="$nodesetEntryElements" mode="SplitMode_AllElementsNavigationHtml">
                <xsl:with-param name="titlePattern" select="$titlePattern"/>
            </xsl:apply-templates>
        </xsl:if>
        
        <!-- ALL other intermediate XMLs are processed now  -->
        
        <!-- Create package navigation contents for each package (BLF, xxx-package-frame.html) 
        
             Params:
                - titlePattern text defining the way title is displayed
                - descriptionPattern text defining the way description is displayed
                - topLevel true/false indicates whether this node is a top level selection element 
            See GeneratePackagesList.xsl -->
        <xsl:apply-templates
            select="$nodesetEntryElements" mode="SplitMode_PackageSummaryFrame">
            <xsl:with-param name="titlePattern" select="$titlePattern"/>
            <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
        </xsl:apply-templates>
          
        <!-- Create element contents for each element in the package (RF, xxx-content.html) 
            See GeneratePackagesList.xsl -->
        <xsl:apply-templates
            select="$nodesetEntryElements" mode="SplitMode_ElementContentsForPackage">
            <xsl:with-param name="titlePattern" select="$titlePattern"/>
        </xsl:apply-templates>
        
    </xsl:template>
 
    
    
    <xsl:include href="FileNameGeneration.xsl"/>
    <!-- ======================================================================================== -->
    <!-- Navigation page templates (package content page are generated simultaneously with 
       navigation list generation and these kick off content page generation as elements are 
       encountered) -->
    <!-- ======================================================================================== -->
    <xsl:include href="GenerateFrameSet.xsl"/>
    <xsl:include href="GeneratePackagesList.xsl"/>
    
  	<xsl:include href="GenerateDojoNavigation.xsl"/>
    
    <xsl:include href="GenerateAllElementsList.xsl"/>
    <xsl:include href="GeneratePackageElementsList.xsl"/>
    <!-- ======================================================================================== -->
    <!-- Content page templates -->
    <!-- ======================================================================================== -->
    <xsl:include href="uml2content/OwnedMemberContent.xsl"/>
    <xsl:include href="uml2content/DiagramContent.xsl"/>
    <xsl:include href="uml2content/OwnedTriggerContent.xsl"/>
    <xsl:include href="uml2content/OwnedParameteredElementContent.xsl"/>
    
    <!-- Start Relationships Support -->
    <!-- Class Diagrams -->
    <xsl:include href="uml2content/GeneralizationContent.xsl"/>
    <xsl:include href="uml2content/ImplementationContent.xsl"/>
    <xsl:include href="uml2content/SubstitutionContent.xsl"/>
    <xsl:include href="uml2content/PackageImportContent.xsl"/>
    <xsl:include href="uml2content/ElementImportContent.xsl"/>
    <!-- Usecase Diagrams -->
    <xsl:include href="uml2content/IncludeContent.xsl"/>
    <xsl:include href="uml2content/ExtendContent.xsl"/>
    <!-- Deployment Diagrams -->
    <xsl:include href="uml2content/ManifestationContent.xsl"/>
    <xsl:include href="uml2content/DeploymentContent.xsl"/>
    <xsl:include href="uml2content/ConfigurationContent.xsl"/>
    <!-- State Machine Diagrams -->
    <xsl:include href="uml2content/TransitionContent.xsl"/>
    <!-- Composite Structure Diagrams -->
    <xsl:include href="uml2content/OwnedConnectorContent.xsl"/>
    <!-- End Relationships Support -->
    
    <!-- Start StateMachine support -->
    <xsl:include href="uml2content/RegionContent.xsl"/>
    <xsl:include href="uml2content/SubVertexContent.xsl"/>
    <xsl:include href="uml2content/EntryContent.xsl"/>
    <xsl:include href="uml2content/ExitContent.xsl"/>
    <xsl:include href="uml2content/DoActivityContent.xsl"/>
    <xsl:include href="uml2content/PinContent.xsl"/>
    <!-- End StateMachine support -->
    <!-- Activity Diagram support -->
    <xsl:include href="uml2content/ActivityNodesContent.xsl"/>
    <xsl:include href="uml2content/EdgeContent.xsl"/> 
	<xsl:include href="uml2content/FragmentContent.xsl"/>
    <!-- Nested Classifier support -->
    <xsl:include href="uml2content/NestedClassifierContent.xsl"/> 
    <!-- Owned UseCase support -->
    <xsl:include href="uml2content/OwnedUseCaseContent.xsl"/> 
    <!-- Start Composite Structure support -->
    <xsl:include href="uml2content/OwnedPortContent.xsl"/> 
    <xsl:include href="uml2content/OccurrenceContent.xsl"/> 
    <!-- End Composite Structure support -->
    <!-- OwnedBehavior support -->
    <xsl:include href="uml2content/OwnedBehaviorContent.xsl"/>
    <!-- Start Sequence Diagram support -->
    <xsl:include href="uml2content/LifelineContent.xsl"/>
    <xsl:include href="uml2content/MessageContent.xsl"/>
    <!-- End Sequence Diagram support -->
    <!-- ======================================================================================== -->
    <!-- Cascading Style Sheets -->
    <!-- ======================================================================================== -->
    <xsl:include href="Css.xsl"/>
</xsl:stylesheet>
