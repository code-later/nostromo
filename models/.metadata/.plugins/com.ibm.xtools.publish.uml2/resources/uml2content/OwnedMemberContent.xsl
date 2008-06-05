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
    
    <!-- Include content generation style sheets for specific types of owned members -->
    <xsl:import href="NamedElementContent.xsl"/>
    
    <xsl:output method="html" indent="yes"/>
    
    <!-- ======================================================================================== -->
    <!-- Create content for owned members -->
    <!-- ======================================================================================== -->
    <xsl:template name="createOwnedMemberContent" match="packagedElement | realization | interfaceRealization | extensionPoint" mode="createElementContent">
        <xsl:param name="titlePattern" select="@name"/>
        <xsl:param name="descriptionPattern">
            <xsl:call-template name="getLocalizedString">
                <xsl:with-param name="key" select="'Description.Content'"/>
            </xsl:call-template>
        </xsl:param>
        <xsl:param name="keywordsWithPatterns" select="''"/>
        <xsl:choose>
            <xsl:when test="@xsi:type='uml:Class'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Class'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:Interface'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Interface'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
			<xsl:when test="@xsi:type='uml:Enumeration'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Enumeration'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
			<xsl:when test="@xsi:type='uml:DataType'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.DataType'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:Signal'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Signal'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:Stereotype'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Stereotype'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <!-- Node and its subclasses -->
            <xsl:when test="@xsi:type='uml:Node'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Node'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:DeploymentSpecification'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.DeploymentSpecification'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:ExecutionEnvironment'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.ExecutionEnvironment'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            <!-- End of Node and its subclasses -->

            <!-- Artifact and its subclasses -->            
            <xsl:when test="@xsi:type='uml:Artifact'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Artifact'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:Device'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Device'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            <!-- End of Artifact and its subclasses -->            
            
            <xsl:when test="@xsi:type='uml:Component'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Component'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:Activity'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Activity'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            
            <xsl:when test="@xsi:type='uml:Actor'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Actor'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:UseCase'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.UseCase'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:StateMachine'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.StateMachine'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:InstanceSpecification'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.InstanceSpecification'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:Collaboration'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Collaboration'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:PrimitiveType'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.PrimitiveType'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
                        
            <!-- Start relationships -->
            <!-- Class Diagram  -->
            <xsl:when test="@xsi:type='uml:Association'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Association'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:AssociationClass'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.AssociationClass'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="(@xsi:type='uml:Realization') or 
                            (@xsi:type='uml:ComponentRealization') or 
                            (@xsi:type='uml:InterfaceRealization') or
							(@xsi:type='uml:Substitution')">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Realization'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="(@xsi:type='uml:SendOperationEvent') or 
                            (@xsi:type='uml:ReceiveOperationEvent') or 
                            (@xsi:type='uml:ExecutionEvent') or
							(@xsi:type='uml:DestructionEvent')">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Event'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:ExtensionPoint'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.ExtensionPoint'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:Dependency'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Dependency'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:Usage'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Usage'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            
            <xsl:when test="@xsi:type='uml:Abstraction'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.Abstraction'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            <!-- Deployment Diagram  -->
            <xsl:when test="@xsi:type='uml:CommunicationPath'">
                <xsl:apply-templates select="." mode="NamedElement">
                    <xsl:with-param name="titleKey" select="'Heading.CommunicationPath'"/>
                    <xsl:with-param name="titlePattern" select="$titlePattern"/>
                    <xsl:with-param name="descriptionPattern" select="$descriptionPattern"/>
                    <xsl:with-param name="keywordsWithPatterns" select="$keywordsWithPatterns"/>
                </xsl:apply-templates>
            </xsl:when>
            <!-- End relationships -->          
            
            <xsl:otherwise>
                <!-- This is an error - since it means we have hit an owned member that we do not know 
             how to generate contents for -->
                <xsl:message terminate="no">
                    <xsl:text>The current element was not handled by the
                        template packagedElementElements.</xsl:text>
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
    
</xsl:stylesheet>
