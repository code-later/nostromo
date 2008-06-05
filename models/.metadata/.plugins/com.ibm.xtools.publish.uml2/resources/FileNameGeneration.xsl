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
    xmlns:xalan="http://xml.apache.org/xalan"
    exclude-result-prefixes="xmi uml xsi publish xalan">
	
	<xsl:output method="html"/>
	
  <!-- ======================================================================================== -->
  <!--  Given a file name prepend the style sheet parameter &apos;outputDir&apos; and the appropriate 
        file separator to it.
        Parameters:
          fileName The file name to use when creating the full file path. -->
  <!-- ======================================================================================== -->
  <xsl:template name="prependFullPathToFileName">
    <xsl:param name="fileName"/>
    
    <xsl:value-of select="concat($outputDir, $fileSep, $contentDir, $fileName)"/>
  </xsl:template>

  
  <!-- ======================================================================================== -->
  <!--  Given an element create root for its file name.  Other template will append idetifier 
        text and file extension.  This method returns the same unique file name for any element 
        it is passed (assuming the attribute xmi:id is unique).  By default it will work on the 
        current context. -->
  <!-- ======================================================================================== -->
  <xsl:template name="createRootFileName">
    <xsl:param name="element" select="."/>
    <!-- Return this elemen&apos;s root for file names -->
    <xsl:value-of select="$element/@xmi:id"/>
  </xsl:template>


  <!-- ======================================================================================== -->
  <!--  Given an element creates the base frame file name.  This is the file name that is the 
        entry point for the web documentation and is controlled from outside by a parameter.    -->
  <!-- ======================================================================================== -->
  <xsl:template name="createBaseFrameFileName">
    
    <!-- Return the file name for the web documentation entry point -->
    <xsl:value-of select="$rootFile"/>
  </xsl:template>
  
  
  <xsl:template name="createPackageListFileName">
    <xsl:param name="element" select="."/>
    
    <xsl:variable name="rootFileName">
      <xsl:call-template name="createRootFileName">
        <xsl:with-param name="element" select="$element"/>
      </xsl:call-template>
    </xsl:variable>
    
    <xsl:value-of select="concat($rootFileName, &apos;-navigation-frame.html&apos;)"/>
  </xsl:template>
  
  <xsl:template name="createDojoNavFileName">
    <xsl:param name="element" select="."/>
    
    <xsl:variable name="rootFileName">
      <xsl:call-template name="createRootFileName">
        <xsl:with-param name="element" select="$element"/>
      </xsl:call-template>
    </xsl:variable>
    
    <xsl:value-of select="concat($rootFileName, &apos;-dojo.html&apos;)"/>
  </xsl:template>
  
  <xsl:template name="createAllElementsFileName">
    <xsl:param name="element" select="."/>
    
    <xsl:variable name="rootFileName">
      <xsl:call-template name="createRootFileName">
        <xsl:with-param name="element" select="$element"/>
      </xsl:call-template>
    </xsl:variable>
    
    <xsl:value-of select="concat($rootFileName, &apos;-allelements-frame.html&apos;)"/>
  </xsl:template>
  
  
  <xsl:template name="createTopSummaryFileName">
    <xsl:param name="element" select="."/>
    
    <xsl:value-of select="concat($globalTopLevelId, &apos;-top-summary.html&apos;)"/>
  </xsl:template>
  
  <xsl:template name="createPackageNavigationListFileName">
    <xsl:param name="element" select="."/>
    
    <xsl:variable name="rootFileName">
      <xsl:call-template name="createRootFileName">
        <xsl:with-param name="element" select="$element"/>
      </xsl:call-template>
    </xsl:variable>
    
    <xsl:value-of select="concat($rootFileName, &apos;-package-frame.html&apos;)"/>
  </xsl:template>
  
  
  <!-- Calculates hotspot's hyperreference based on the current 'publish:partinfo' element -->
  <xsl:template name="createElementContentFileName">
      <xsl:param name="element" select="."/>
      
      <xsl:variable name="elementId">
          <xsl:call-template name="createRootFileName">
              <xsl:with-param name="element" select="$element"/>
          </xsl:call-template>
      </xsl:variable>
    
     <xsl:choose>
         <!-- elements with 'anchor' attribute set (e.g. attached comments and constraints on diagram  -->
         <xsl:when test="$element/@anchor">
              <xsl:value-of select="concat($element/@xmi:id, &apos;-content.html&apos;, $element/@anchor)"/>
         </xsl:when>
         <!-- elements with already provided url (e.g. URLs on model elements "Add UML | URL" command) -->
         <xsl:when test="$element/@publish:url">
             <xsl:value-of select="$element/@publish:url"/>
         </xsl:when>
         <!-- this is the case of a top level element. Link will point to top summary page -->
         <xsl:when test="$globalTopLevelId = $element/@xmi:id">
              <xsl:value-of select="concat($element/@xmi:id, &apos;-top-summary.html&apos;)"/>
         </xsl:when>
         <!-- this is a link to UMLModel type element (possible another model reference from diagram)  -->
         <xsl:when test="($element/@publish:ismodellink = 'true') and ($globalTopLevelId != $element/@xmi:id)">
           <xsl:value-of select="concat($element/@xmi:id, &apos;_root.html&apos;)"/>
         </xsl:when>
       <xsl:otherwise>
              <xsl:value-of select="concat($element/@xmi:id, &apos;-content.html&apos;)"/>
         </xsl:otherwise>
     </xsl:choose>
  </xsl:template>
  
  <!-- constructs file name for the icon  -->  
  <xsl:template name="createElementIconFileName">
    <xsl:param name="element" select="."/>
    
    <xsl:value-of select="concat(&apos;..&apos;, $fileSep, $imagesDir, $element/@publish:icon)"/>    
  </xsl:template>  
  
  <xsl:template name="createDiagramImageFileName">
    <xsl:param name="element" select="."/>
    <xsl:param name="ext" select="&apos;&apos;"/>
    
    <xsl:variable name="rootFileName">
      <xsl:call-template name="createRootFileName">
        <xsl:with-param name="element" select="$element"/>
      </xsl:call-template>
    </xsl:variable>
    
    <xsl:value-of select="concat(&apos;..&apos;, $fileSep, $imagesDir, $rootFileName, &apos;.&apos;, $ext)"/>
  </xsl:template>
  
    <!-- this template returns full path to an auxilliary xml file (packages.xml, allelement.xml, etc)
         which is crucial during SPLIT mode publishing -->
    <xsl:template name="constructAuxilliaryXmlFilepath">
        <xsl:param name="suffix" select="'packages.xml'"></xsl:param>
        <xsl:param name="guid" select="''"></xsl:param>
        
        <xsl:variable name="fullPath">
            <xsl:call-template name="prependFullPathToFileName">
                <!-- e.g. _3QxPsNp1Edi_Xtx6KYPAvA-allelements.xml -->
                <xsl:with-param name="fileName" select="concat($guid, '-', $suffix)"/>
            </xsl:call-template>
        </xsl:variable>
        
        <xsl:variable name="fullPathURL">
            <xsl:value-of select="concat('file:///', $fullPath)"/>
        </xsl:variable>
        
        <xsl:value-of select="$fullPathURL"/>            
    </xsl:template>
  
</xsl:stylesheet>
