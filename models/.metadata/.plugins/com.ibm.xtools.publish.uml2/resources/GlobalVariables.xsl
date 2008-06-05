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
    <xsl:output method="html"/>
    <!-- ======================================================================================== -->
    <!-- Contains global variables -->
    <!-- ======================================================================================== -->
    <xsl:variable name="fileSep">/</xsl:variable>
    <xsl:variable name="newLine">
        <xsl:text/>
    </xsl:variable>
    <xsl:variable name="qualifiedNameSep">::</xsl:variable>
    <xsl:variable name="globalGeneratorName" select="&apos;IBM Rational Model Publisher&apos;"/>
    <xsl:variable name="topLevelIsModel" select="not(/packagedElement[1]/@xsi:type='uml:Package')"/>
    <xsl:variable name="propertiesTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'ContentHeading.Properties'"/>
        </xsl:call-template>
    </xsl:variable>
    
    <!-- Image File format for Diagrams -->
    <xsl:variable name="globalDiagramImageFormat">
        <xsl:choose>
            <xsl:when test="/ = packagedElement">
                <xsl:value-of select="/packagedElement/@publish:diagramFormat"/>
            </xsl:when>
            <xsl:when test="/ = uml:Model">
                <xsl:value-of select="/uml:Model/@publish:diagramFormat"/>
            </xsl:when>
            <xsl:when test="/ = uml:Profile">
                <xsl:value-of select="/uml:Profile/@publish:diagramFormat"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:message terminate="yes">
                    Information about top level GUID is missing. Unable to continue.
                </xsl:message>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>
    
    <!-- Split XML per package approach -->
    <!-- PERFORMANCE -->
    <!-- this key holds fast access to all ownedComments in the model by their 'xmi:id' attribute -->
    <xsl:key name="TableCommentsByIds" match="ownedComment" use="@xmi:id"/>
    
    <!-- this key holds fast access to all ownedComments in the model by their 'annotedElement' attribute -->
    <xsl:key name="TableCommentsByAttachedElement" match="ownedComment" use="@annotatedElement"/>
    
    <!-- this key holds fast access to all ownedRules in the model by their 'xmi:id' attribute -->
    <xsl:key name="TableConstraintsByIds" match="ownedRule" use="@xmi:id"/>
    
    <!-- this key holds fast access to all ownedRules in the model by their 'constrainedElement' attribute -->
    <xsl:key name="TableConstraintsByAttachedElement" match="ownedRule" use="@constrainedElement"/>
    
    <!-- this key holds fast access to all ownedRules in the model by their 'xmi:id' attribute -->
    <xsl:key name="TableModelsByIds" match="uml:Model" use="@xmi:id"/>
    
    <xsl:variable name="globalTopLevelId">
        <xsl:choose>
            <xsl:when test="/ = packagedElement">
                <xsl:value-of select="/packagedElement/@publish:toplevelguid"/>
            </xsl:when>
            <xsl:when test="/ = uml:Model">
                <xsl:value-of select="/uml:Model/@publish:toplevelguid"/>
            </xsl:when>
            <xsl:when test="/ = uml:Profile">
                <xsl:value-of select="/uml:Profile/@publish:toplevelguid"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:message terminate="yes">
                    Information about top level GUID is missing. Unable to continue.
                </xsl:message>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>
    
     <!-- Load customer visible strings from the string table -->
    <xsl:variable name="topLeftFrameTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.TopLeftFrame'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="bottomLeftFrameTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.BottomLeftFrame'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="rightFrameTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.RightFrame'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="enumLiteralTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.EnumLiterals'"/>
        </xsl:call-template>
    </xsl:variable>
    
    <xsl:variable name="urlsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.URLs'"/>
        </xsl:call-template>
    </xsl:variable>
    
    <xsl:variable name="ActivityNodeTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.ActivityNode'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="ActivityEdgeTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.ActivityEdges'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="interactionTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.Interactions'"/>
        </xsl:call-template>
    </xsl:variable>
	<xsl:variable name="lifelineTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.Lifelines'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="combinedFragmentTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.CombinedFragments'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="executionOccurrenceTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.ExecutionOccurrences'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="interactionOccurrenceTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.InteractionOccurrences'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="stopTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.Stops'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="templateBindingTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.TemplateBinding'"/>
        </xsl:call-template>
    </xsl:variable>
        <xsl:variable name="templateParameterSubstitutionTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.TemplateParameterSubstitution'"/>
        </xsl:call-template>
    </xsl:variable>    
    <xsl:variable name="templateParameterTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.TemplateParameters'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="templateSignatureTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.TemplateSignature'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="stateTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.States'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="pseudoStateTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.PseudoStates'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="enumLiteralDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.EnumLiteralDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    
	<xsl:variable name="diagramsTableHeading">
		<xsl:call-template name="getLocalizedString">
			<xsl:with-param name="key"
				select="'TableHeading.Diagrams'" />
		</xsl:call-template>
	</xsl:variable>
    <xsl:variable name="ActivityNodeDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.ActivityNodeDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="ActivityEdgeDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.ActivityEdgeDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="lifelineDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.LifelineDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="combinedFragmentDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.CombinedFragmentDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="executionOccurrenceDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.ExecutionOccurrenceDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="interactionOccurrenceDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.InteractionOccurrenceDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="stopDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.Stops'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="templateBindingDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.TemplateBindingDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="templateParameterSubstitutionDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.TemplateParameterSubstitutionDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="templateParametersDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.TemplateParameterDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="templateSignatureDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.TemplateSignatureDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="stateDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.StateDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="pseudoStateDetailsTableTitle">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'TableHeading.PseudoStateDetails'"/>
        </xsl:call-template>
    </xsl:variable>
    
    
    <xsl:variable name="enumLiteralsNavName">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'Nav.EnumLiterals'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="urlsNavName">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'Nav.URLs'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="nestedClassifiersNavName">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'Nav.NestedClassifiers'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="nestedActivitiesNavName">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'Nav.NestedActivities'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="nestedComponentsNavName">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'Nav.NestedComponents'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="ActivityNodeNavName">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'Nav.ActivityNode'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="ActivityEdgeNavName">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'Nav.ActivityEdge'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="propertiesNavName">
        <xsl:call-template name="getLocalizedString">
            <xsl:with-param name="key" select="'Nav.Properties'"/>
        </xsl:call-template>
    </xsl:variable>
        
    <!-- This list defines what elements will have their own dedicated XXX-content.html pages. 
    Another approach used by publishing is to re-use parent's content page. In this case the 
    variable below should not contain the type. 
    
    E.g. #1 attributes and operations are not here because they are published into their owner's content page
    E.g. #2 Combined Fragments have their own page due to possibility of indefinite nesting levels between 
    fragments (frag1 contains frag2, frag2 contains frag3, ... etc).
    
    If an element doesn't have the expected content page check the following places:
    a) this variable (check both xsi:type and element name)
    b) NamedElementContent.xsl (check element name in main match block)
    c) OwnedMemberContent.xsl (make sure that both xml name and xsi type are there)
     -->
    <xsl:variable 
            name="globalTopLevelAllSupportedElementTypes" select="
                            //contents[@xsi:type='notation:Diagram'] |
                            //packagedElement[@xsi:type='uml:Class'] | 
                            //packagedElement[@xsi:type='uml:Interface']  | 
                            //packagedElement[@xsi:type='uml:Enumeration'] |    
                            //packagedElement[@xsi:type='uml:DataType'] |    
                            //packagedElement[@xsi:type='uml:Signal'] |                                                                         
                            //packagedElement[@xsi:type='uml:Node'] |
                            //packagedElement[@xsi:type='uml:Component'] |
                            //packagedElement[@xsi:type='uml:ComponentInstance'] |
                            //packagedElement[@xsi:type='uml:Actor'] |
                            //packagedElement[@xsi:type='uml:UseCase'] |
                            //packagedElement[@xsi:type='uml:StateMachine'] |
                            //packagedElement[@xsi:type='uml:Artifact'] |
                            //packagedElement[@xsi:type='uml:InstanceSpecification'] | 
                            //packagedElement[@xsi:type='uml:ExecutionEnvironment'] | 
                            //packagedElement[@xsi:type='uml:Device'] | 
                            //packagedElement[@xsi:type='uml:DeploymentSpecification'] |
                            //packagedElement[@xsi:type='uml:Collaboration'] |
                            //packagedElement[@xsi:type='uml:Activity'] |
                            //packagedElement[@xsi:type='uml:Association'] |
                            //packagedElement[@xsi:type='uml:AssociationClass'] |
                            //realization[@xsi:type='uml:Realization'] |
                            //realization[@xsi:type='uml:ComponentRealization'] |
                            //interfaceRealization[@xsi:type='uml:InterfaceRealization'] |
                            //realization[@xsi:type='uml:Substitution'] |
                            //packagedElement[@xsi:type='uml:Dependency'] |
                            //packagedElement[@xsi:type='uml:Usage'] |
                            //packagedElement[@xsi:type='uml:Abstraction'] |
                            //packagedElement[@xsi:type='uml:CommunicationPath'] |
                            //packagedElement[@xsi:type='uml:PrimitiveType'] |
                            //packagedElement[@xsi:type='uml:Stereotype'] |
                            //packagedElement[@xsi:type='uml:SendOperationEvent'] |
                            //packagedElement[@xsi:type='uml:ReceiveOperationEvent'] |
                            //packagedElement[@xsi:type='uml:ExecutionEvent'] |
                            //packagedElement[@xsi:type='uml:DestructionEvent'] |
                            //extensionPoint[@xsi:type='uml:ExtensionPoint'] |
                            //ownedTrigger[@xsi:type='uml:CallTrigger'] |
                            //generalization[@xsi:type='uml:Generalization'] |
                            //implementation[@xsi:type='uml:Implementation'] |
                            //substitution[@xsi:type='uml:Substitution'] |
                            //packageImport[@xsi:type='uml:PackageImport'] |
                            //include[@xsi:type='uml:Include'] |
                            //extend[@xsi:type='uml:Extend'] |
                            //manifestation[@xsi:type='uml:Manifestation'] |
                            //deployment[@xsi:type='uml:Deployment'] |
                            //elementImport[@xsi:type='uml:ElementImport'] |
                            //ownedBehavior[@xsi:type='uml:Interaction'] |
                            //ownedBehavior[@xsi:type='uml:Activity'] |
                            //region[@xsi:type='uml:Region'] |
                            //subvertex[@xsi:type='uml:State'] |
                            //subvertex[@xsi:type='uml:FinalState'] |
                            //subvertex[@xsi:type='uml:Pseudostate'] |
                            //transition[@xsi:type='uml:Transition'] |
                            //node[@xsi:type='uml:ApplyFunctionAction'] |
                            //node[@xsi:type='uml:StructuredActivityNode'] |
                            //node[@xsi:type='uml:DecisionNode'] |
                            //node[@xsi:type='uml:MergeNode'] |
                            //node[@xsi:type='uml:ForkNode'] |
                            //node[@xsi:type='uml:JoinNode'] |
                            //node[@xsi:type='uml:InitialNode'] |
                            //node[@xsi:type='uml:ActivityFinalNode'] |
                            //node[@xsi:type='uml:FlowFinalNode'] |
                            //node[@xsi:type='uml:CentralBufferNode'] |
                            //node[@xsi:type='uml:DataStoreNode'] |
                            //node[@xsi:type='uml:CallOperationAction'] |
                            //containedNode[@xsi:type='uml:ApplyFunctionAction'] |           
                            //containedNode[@xsi:type='uml:StructuredActivityNode'] |
                            //containedNode[@xsi:type='uml:DecisionNode'] |
                            //containedNode[@xsi:type='uml:MergeNode'] |
                            //containedNode[@xsi:type='uml:ForkNode'] |
                            //containedNode[@xsi:type='uml:JoinNode'] |
                            //containedNode[@xsi:type='uml:InitialNode'] |
                            //containedNode[@xsi:type='uml:ActivityFinalNode'] |
                            //containedNode[@xsi:type='uml:FlowFinalNode'] |
                            //containedNode[@xsi:type='uml:CentralBufferNode'] |
                            //containedNode[@xsi:type='uml:DataStoreNode'] |
                            //containedNode[@xsi:type='uml:Action'] |
                            //containedNode[@xsi:type='uml:CallOperationAction'] |
                            //edge[@xsi:type='uml:ControlFlow'] |
                            //edge[@xsi:type='uml:ObjectFlow'] |
                            //target[@xsi:type='uml:InputPin'] |
                            //argument[@xsi:type='uml:InputPin'] |
                            //result[@xsi:type='uml:OutputPin'] |
                            //containedEdge[@xsi:type='uml:ControlFlow'] |
                            //containedEdge[@xsi:type='uml:ObjectFlow'] |
                            //lifeline[@xsi:type='uml:Lifeline'] |
                            //message[@xsi:type='uml:Message'] |
                            //nestedClassifier[@xsi:type='uml:Class'] |
                            //nestedClassifier[@xsi:type='uml:Component'] |
                            //nestedClassifier[@xsi:type='uml:Collaboration'] |
                            //nestedClassifier[@xsi:type='uml:Enumeration'] |
                            //nestedClassifier[@xsi:type='uml:DataType'] |
                            //nestedClassifier[@xsi:type='uml:Interface'] | 
                            //nestedClassifier[@xsi:type='uml:Signal'] | 
                            //nestedClassifier[@xsi:type='uml:Actor'] | 
                            //nestedClassifier[@xsi:type='uml:UseCase'] | 
                            //ownedUseCase[@xsi:type='uml:UseCase'] | 
                            //ownedPort[@xsi:type='uml:Port'] |
                            //occurrence[@xsi:type='uml:CollaborationOccurrence'] |
                            //ownedConnector[@xsi:type='uml:Connector'] |
                            //exit[@xsi:type='uml:Activity'] |
                            //entry[@xsi:type='uml:Activity'] |
                            //doActivity[@xsi:type='uml:Activity'] |
                            //configuration[@xsi:type='uml:DeploymentSpecification'] |
                            //fragment[@xsi:type='uml:CombinedFragment'] |
                            //fragment[@xsi:type='uml:OccurrenceSpecification'] |
                            //ownedParameteredElement[@xsi:type='uml:Class']">
    </xsl:variable>
</xsl:stylesheet>
