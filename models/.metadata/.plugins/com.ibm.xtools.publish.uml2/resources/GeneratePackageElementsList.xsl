<?xml version="1.0"?>
<!--                                                                        -->
<!-- Licensed Materials - Property of IBM                                   -->
<!-- Copyright IBM Corp. 2003, 2004.  All Rights Reserved.                  -->
<!--                                                                        -->
<!-- US Government Users Restricted Rights - Use, duplication or disclosure -->
<!-- restricted by GSA ADP Schedule Contract with IBM Corp.                 -->
<!--                                                                        -->
<xsl:stylesheet version="1.0" exclude-result-prefixes="uml xsi publish"
    xmlns:publish="http://www.ibm.com/Rational/XTools/Publish"
	xmlns:redirect="http://xml.apache.org/xalan/redirect"
    extension-element-prefixes="redirect"
    xmlns:uml="http://www.eclipse.org/uml2/1.0.0/UML" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output indent="yes" method="html"/>

    <!-- ======================================================================================== -->
    <!--  Generate a package navigation list html file for the bottom left navigation window of 
		the current node and the package content html. This is SPLIT mode version.
    
        Main difference is that xxx-top-summary.html file should not be created here. It is done elsewhere.
      -->
    <!-- ======================================================================================== -->
    <xsl:template name="SplitMode_createAPackageElementList">
        <xsl:param name="masterDocTitle" select="@name"/>
        <xsl:param name="titlePattern" select="@name"/>
        <xsl:param name="descriptionPattern" select="''"/>
        <xsl:param name="keywordsWithPatterns" select="''"/>
        <!-- Perform pattern replacement for any '#name' with the attribute 'name' -->
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
        <!-- Generate the file name needed for output of the navigation list -->
        <xsl:variable name="packageNavigationListFileName">
            <xsl:call-template name="createPackageNavigationListFileName"/>
        </xsl:variable>
        <xsl:variable name="packageNavigationListFullPathName">
            <xsl:call-template name="prependFullPathToFileName">
                <xsl:with-param name="fileName" select="$packageNavigationListFileName"/>
            </xsl:call-template>
        </xsl:variable>
        <!-- Generate the file name needed for referencing the package's content and for recieving the content -->
        <xsl:variable name="packageContentFileName">
            <xsl:call-template name="createElementContentFileName"/>
        </xsl:variable>
        <xsl:variable name="packageContentFullPathName">
            <xsl:call-template name="prependFullPathToFileName">
                <xsl:with-param name="fileName" select="$packageContentFileName"/>
            </xsl:call-template>
        </xsl:variable>
        <!-- Generate the package's fully qualified name 
			Note we need to generate two variables, one with the package name
			and one that doesn't have the package name as part of the string
		-->
        <xsl:variable name="packageFullyQualifiedName">
            <xsl:call-template name="generateFullyQualifiedName">
                <!-- this is a temporary workaround until back-end passes info about model name.
                       As a result xxx-package-frame.html will have model name as a prefix before package name -->
                <xsl:with-param name="includeModelName" select="false()"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="packageOwner">
            <xsl:call-template name="generateFullyQualifiedName">
                <xsl:with-param name="includeMyName" select="false ()"/>
            </xsl:call-template>
        </xsl:variable>
        <!-- Localized strings -->
        <xsl:variable name="packageHeading">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'Heading.Package'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="modelHeading">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'Heading.Model'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="profileHeading">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'Heading.Profile'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="classesTableHeading">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'TableHeading.Classes'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="ComponentsTableHeading">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'TableHeading.Component'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="ComponentsWithSTTableHeading">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'TableHeading.ComponentWithST'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="ComponentInstancesTableHeading">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'TableHeading.ComponentInstance'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="diagramsTableHeading">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'TableHeading.Diagrams'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="packageIconImageFileName">
            <xsl:call-template name="createElementIconFileName"/>
        </xsl:variable>
        <!-- Create the navigation bar (to be copied twice into the document) -->
        <xsl:variable name="navBar">
            <xsl:call-template name="outputNavigationBar">
                <xsl:with-param name="docTitle" select="$masterDocTitle"/>
                <xsl:with-param name="appearsOn" select="'package'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="supportedTypesList" select="                                       
            packagedElement[                                                   
                @xsi:type='uml:Class' or                                                    
                @xsi:type='uml:Interface' or                                                   
                @xsi:type='uml:Enumeration' or                                                    
                @xsi:type='uml:DataType' or                                                    
                @xsi:type='uml:Signal' or                                                    
                @xsi:type='uml:Node' or                                                    
                @xsi:type='uml:Component' or                                                    
                @xsi:type='uml:ComponentInstance' or                                                    
                @xsi:type='uml:Actor' or                                                    
                @xsi:type='uml:UseCase' or                                                    
                @xsi:type='uml:StateMachine' or                                                    
                @xsi:type='uml:Artifact' or                                                   
                @xsi:type='uml:InstanceSpecification' or                                                   
                @xsi:type='uml:ExecutionEnvironment' or                                                   
                @xsi:type='uml:Device' or                                                   
                @xsi:type='uml:DeploymentSpecification' or                                                  
                @xsi:type='uml:Activity' or                                                   
                @xsi:type='uml:Collaboration' or                                                   
                @xsi:type='uml:PrimitiveType' or                                                   
                @xsi:type='uml:Stereotype'] |                                       
                region[@xsi:type='uml:Region'] |                                       
                subvertex[                                                   
                @xsi:type='uml:State' or                                                   
                @xsi:type='uml:FinalState' or                                                   
                @xsi:type='uml:Pseudostate'] |                                       
            node[                                                   
                @xsi:type='uml:ApplyFunctionAction' or                                                   
                @xsi:type='uml:StructuredActivityNode' or                                                   
                @xsi:type='uml:DecisionNode' or                                                   
                @xsi:type='uml:MergeNode' or                                                   
                @xsi:type='uml:ForkNode' or                                                    
                @xsi:type='uml:JoinNode' or                                                   
                @xsi:type='uml:InitialNode' or                                                   
                @xsi:type='uml:ActivityFinalNode' or                                                   
                @xsi:type='uml:FlowFinalNode' or                                                  
                @xsi:type='uml:CentralBufferNode' or                                                   
                @xsi:type='uml:DataStoreNode'] |                                        
            nestedClassifiers[                                                   
                @xsi:type='uml:Class' or                                                   
                @xsi:type='uml:Signal' or                                                   
                @xsi:type='uml:Component' or                                                   
                @xsi:type='uml:Collaboration' ] |                                        
            ownedBehavior[                                        
                @xsi:type='uml:Interaction' or
                @xsi:type='uml:Activity'] |                               
            lifeline[     
                @xsi:type='uml:Lifeline'    ] |                               
            fragment[                                        
                @xsi:type='uml:BehaviorExecutionSpecification' or                                        
                @xsi:type='uml:CombinedFragment' or                                        
                @xsi:type='uml:Stop'    ]                               
            "/>
        <!-- Bottom Left navigation windown -->
        <redirect:write file="{$packageNavigationListFullPathName}"/>
        <redirect:write file="{$packageNavigationListFullPathName}" append="true">
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
                    <table class="PackageNavigationList">
                        <tr>
                            <td class="PackageList">
                                <xsl:call-template name="outputImageAndTextLinkHTML">
                                    <xsl:with-param name="link" select="$packageContentFileName"/>
                                    <xsl:with-param name="image">
                                        <xsl:if test="@publish:icon != ''">
                                            <xsl:value-of select="$packageIconImageFileName"/>
                                        </xsl:if>
                                    </xsl:with-param>
                                    <xsl:with-param name="text" select="$packageFullyQualifiedName"/>
                                    <xsl:with-param name="target" select="'elementFrame'"/>
                                </xsl:call-template>
                                <p/>
                                <!-- Create the package content file and fill it with header information -->
                                <!-- commented out for XSLTC -->
                                <redirect:write file="{$packageContentFullPathName}"/>
                                <redirect:write file="{$packageContentFullPathName}" append="true">
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
                                            <xsl:copy-of select="$navBar"/>
                                            <hr/>
                                            <span class="PackageFullyQualifiedName">
                                                <xsl:value-of select="$packageOwner"/>
                                            </span>
                                            <br/>
                                            <span class="ElementTitle">
                                                <xsl:choose>
                                                    <xsl:when test="@xsi:type='uml:Model'">
                                                        <xsl:copy-of select="$modelHeading"/>
                                                    </xsl:when>
                                                    <xsl:when test="@xsi:type='uml:Package'">
                                                        <xsl:copy-of select="$packageHeading"/>
                                                    </xsl:when>
                                                    <xsl:when test="@xsi:type='uml:Profile'">
                                                        <xsl:copy-of select="$profileHeading"/>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <!-- This is an error. Leave it blank. -->
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                                <xsl:value-of select="@name"/>
                                            </span>
                                            <!-- comments are processed when anchor element is traversed -->
                                            <xsl:call-template name="createDocumentation"/>
                                            <!-- Display this Package's default diagram () RATLC00501902 -->
                                            <xsl:for-each select="contents[@xsi:type='notation:Diagram' and @isDefault='true']">
                                                <xsl:message terminate="no">
                                                    <xsl:value-of select="$newLine"/>
                                                    <xsl:text>Processing a package main diagram.</xsl:text>
                                                    <xsl:value-of select="$newLine"/>
                                                </xsl:message>
                                                <p/>
                                                <!-- Generate HTML for the main diagram image including hot spots -->
                                                <xsl:call-template name="outputDiagramHTML"/>
                                            </xsl:for-each>
                                            <xsl:variable name="packagesHeading">
                                                <xsl:call-template name="getLocalizedString">
                                                    <xsl:with-param name="key" select="&apos;Heading.Packages&apos;"/>
                                                </xsl:call-template>
                                            </xsl:variable>
                                            <!-- In the split mode we only deal with one package -->
                                            <xsl:variable name="allPackages" select="packagedElement[@xsi:type='uml:Package']"/>
                                            <table border="1" class="PackagesTable">
                                                <xsl:if test="count ($allPackages) &gt; 0">
                                                    <tr>
                                                        <td class="PackagesTitle" colspan="2">
                                                            <xsl:value-of select="$packagesHeading"/>
                                                        </td>
                                                    </tr>
                                                </xsl:if>
                                                <xsl:for-each select="$allPackages">
                                                    <xsl:sort select="@name"/>
                                                    <xsl:variable name="packageContentFileName">
                                                        <xsl:call-template name="createElementContentFileName"/>
                                                    </xsl:variable>
                                                    <xsl:variable name="iconImageFileName">
                                                        <xsl:call-template name="createElementIconFileName"/>
                                                    </xsl:variable>
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
                                                            <xsl:value-of select="@name"/>
                                                            </xsl:with-param>
                                                            </xsl:call-template>
                                                        </td>
                                                    </tr>
                                                </xsl:for-each>
                                            </table>
                                            <!-- Create a 'Classes' list for the navigation HTML and a 'Classes' table for the 
									            content HTML -->
                                            <xsl:if test="count($supportedTypesList) &gt; 0">
                                                <p/>
                                                <!-- Begin a table for the contained elements -->
                                                <table border="1" class="ContainedElements">
                                                    <tr>
                                                        <td class="DetailsTitle" colspan="2">
                                                            <xsl:copy-of select="$classesTableHeading"/>
                                                        </td>
                                                    </tr>
                                                    <!-- Put a title in the navigation HTML  -->
                                                    <redirect:write file="{$packageNavigationListFullPathName}" append="true">
                                                        <span class="ClassesTableHeading">
                                                            <xsl:copy-of select="$classesTableHeading"/>
                                                        </span>
                                                        <br/>
                                                    </redirect:write>
                                                    <!-- Loop for each owned class creating an entry in the navigation list and the element table -->
                                                    <xsl:for-each select="$supportedTypesList">
                                                        <xsl:sort select="@name"/>
                                                        <!-- Determine the name of the content file for referencing -->
                                                        <xsl:variable name="classContentFileName">
                                                            <xsl:call-template name="createElementContentFileName"/>
                                                        </xsl:variable>
                                                        <xsl:variable name="classIconImageFileName">
                                                            <xsl:call-template name="createElementIconFileName"/>
                                                        </xsl:variable>
                                                        <!-- Add an entry to the navigation HTML -->
                                                        <redirect:write file="{$packageNavigationListFullPathName}" append="true">
                                                            <!-- Create a refernece to each class in navigation list HTML file -->
                                                            <xsl:call-template name="outputImageAndTextLinkHTML">
                                                            <xsl:with-param name="link" select="$classContentFileName"/>
                                                            <xsl:with-param name="image">
	                                                            <xsl:if test="@publish:icon != ''">
		                                                            <xsl:value-of select="$classIconImageFileName"/>
	                                                            </xsl:if>
                                                            </xsl:with-param>
                                                            <xsl:with-param name="text" select="@name"/>
                                                            <xsl:with-param name="target" select="'elementFrame'"/>
                                                            </xsl:call-template>
                                                            <br/>
                                                        </redirect:write>
                                                        <!-- Create an entry in the classes table of the content HTML file -->
                                                        <tr class="ClassTableEntry">
                                                            <td class="ClassTableEntryLink">
                                                            <xsl:call-template name="outputImageAndTextLinkHTML">
                                                            <xsl:with-param name="link" select="$classContentFileName"/>
                                                            <xsl:with-param name="image">
	                                                            <xsl:if test="@publish:icon != ''">
		                                                            <xsl:value-of select="$classIconImageFileName"/>
	                                                            </xsl:if>
                                                            </xsl:with-param>
                                                            <xsl:with-param name="text" select="@name"/>
                                                            </xsl:call-template>
                                                            </td>
                                                            <!-- Add a non breaking space at the end to handle empty comments -->
                                                        </tr>
                                                    </xsl:for-each>
                                                    <redirect:write file="{$packageNavigationListFullPathName}" append="true">
                                                        <br/>
                                                    </redirect:write>
                                                    <!-- Close out the classes table -->
                                                </table>
                                            </xsl:if>
                                            <!-- Create a 'Diagrams' table for the content HTML -->
                                            <!-- Begin a table for the contained elements -->
                                            <xsl:variable name="diagramsForThePackage" select="descendant::contents[@xsi:type='notation:Diagram']"/>
                                            <xsl:if test="count($diagramsForThePackage) &gt; 0">
                                                <p/>
                                                <table border="1" class="ContainedElements">
                                                    <tr>
                                                        <td class="DetailsTitle" colspan="2">
                                                            <xsl:copy-of select="$diagramsTableHeading"/>
                                                        </td>
                                                    </tr>
                                                    <!-- Put a title in the navigation HTML  -->
                                                    <!-- Put a title in the navigation HTML  -->
                                                    <redirect:write file="{$packageNavigationListFullPathName}" append="true">
                                                        <span class="ClassesTableHeading">Diagrams</span>
                                                        <br/>
                                                    </redirect:write>
                                                    <!-- Loop for each diagram in this package creating an entry in the 
											            navigation list and the element table -->
                                                    <xsl:for-each select="$diagramsForThePackage">
                                                        <xsl:sort select="@name"/>
                                                        <!-- Determine the name of the content file for referencing -->
                                                        <xsl:variable name="diagramContentFileName">
                                                            <xsl:call-template name="createElementContentFileName"/>
                                                        </xsl:variable>
                                                        <xsl:variable name="diagramIconImageFileName">
                                                            <xsl:call-template name="createElementIconFileName"/>
                                                        </xsl:variable>
                                                        <!-- Add an entry to the navigation HTML -->
                                                        <redirect:write file="{$packageNavigationListFullPathName}" append="true">
                                                            <!-- Create a refernece to each diagram in navigation list HTML file -->
                                                            <xsl:call-template name="outputImageAndTextLinkHTML">
                                                            <xsl:with-param name="link" select="$diagramContentFileName"/>
                                                            <xsl:with-param name="image">
	                                                            <xsl:if test="@publish:icon != ''">
		                                                            <xsl:value-of select="$diagramIconImageFileName"/>
	                                                            </xsl:if>
                                                            </xsl:with-param>
                                                            <xsl:with-param name="text" select="@name"/>
                                                            <xsl:with-param name="target" select="'elementFrame'"/>
                                                            </xsl:call-template>
                                                            <br/>
                                                        </redirect:write>
                                                        <!-- Create an entry in the diagrams table of the content HTML file -->
                                                        <tr class="ClassTableEntry">
                                                            <td class="ClassTableEntryLink">
                                                            <xsl:call-template name="outputImageAndTextLinkHTML">
                                                            <xsl:with-param name="link" select="$diagramContentFileName"/>
                                                            <xsl:with-param name="image">
	                                                            <xsl:if test="@publish:icon != ''">
		                                                            <xsl:value-of select="$diagramIconImageFileName"/>
	                                                            </xsl:if>
                                                            </xsl:with-param>
                                                            <xsl:with-param name="text" select="@name"/>
                                                            </xsl:call-template>
                                                            </td>
                                                            <!-- Add a non breaking space at the end to handle empty comments -->
                                                        </tr>
                                                    </xsl:for-each>
                                                    <redirect:write file="{$packageNavigationListFullPathName}" append="true">
                                                        <br/>
                                                    </redirect:write>
                                                    <!-- Close out the diagram table -->
                                                </table>
                                            </xsl:if>
                                            
                                            <!-- Applied Profiles information for models only-->
                                            <xsl:if test="@xsi:type='uml:Model'">
                                            	<xsl:variable name="appliedProfiles" select="appliedProfiles/profile"/>
	                                            <p/>
	                                            <table border="1" class="ContainedElements">
	                                                <tr>
	                                                    <td class="DetailsTitle" colspan="1">
	                                                        <xsl:copy-of select="$profileHeading"/>
	                                                    </td>
	                                                </tr>
	                                                
                                                    <xsl:for-each select="$appliedProfiles">
                                                        <xsl:sort select="@name"/>
		                                                <tr>
		                                                    <td>
			                                                    <xsl:value-of select="@name"/>
		                                                    </td>
		                                                </tr>
	                                                </xsl:for-each>
	                                                
												</table>                                            
                                            </xsl:if>
                                            
                                            <!-- add URLs table in case there are any -->
                                            <xsl:call-template name="createURLsInfo">
                                                <xsl:with-param name="mode" select="'summary'"/>
                                            </xsl:call-template>
                                            
                                            <!-- Output the properties table -->
                                            <xsl:apply-templates select="publish:properties"/>
                                            <!-- TODO add other element tables... -->
                                            <hr/>
                                            <xsl:copy-of select="$navBar"/>
                                            <!-- Close out the content HTML and file-->
                                        </body>
                                    </html>
                                </redirect:write>
                                <!-- Close out the navigation HTML and file -->
                            </td>
                        </tr>
                    </table>
                </body>
            </html>
        </redirect:write>
    </xsl:template>


</xsl:stylesheet>
