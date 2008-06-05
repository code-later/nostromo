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
	
  <!-- Output the img tag formatted to dispaly a diagram.  
       
       Parameters: 
         element - The element which contains the diagram information by default it is the current 
                   context.  In UML2 this is a "contents" element but this method does not check 
                   that.  Callers are expected to call this template when they have a "content" 
                   element to produce HTML for.  This template expects the passed element to have 
                   the following:
                     Attributes:
                       xmi:id - The id of the diagram element and the file name for the image file 
                                that will be used as the src attribute along with the file 
                                extension
                       publish:imageType - The file extension to be used for the src attribute
                       name - Tht text for the alt text of the image
                     Elements:
                       publish:partInfo - optional (0..*) element describing a hot spot in the 
                                          image.  Each partInfo element must have the following 
                                          attributes:
                                            id - The id of the element this hot spot links to.
                                            publish:partUpperX - The x coordinate of the upper 
                                                                 left corner.
                                            publish:partUpperY - The y coordinate of the upper 
                                                                 left corner.
                                            publish:partLowerX - The x coordinate of the lower  
                                                                 right corner.
                                            publish:partLowerY - The x coordinate of the lower 
                                                                 right corner.
         
  -->
  <xsl:template name="outputDiagramHTML">
    <xsl:param name="element" select="."/>
    
		<!-- Generate the diagram file name to reference -->
    <xsl:variable name="diagramImageFileName">
      <xsl:call-template name="createDiagramImageFileName">
        <xsl:with-param name="element" select="$element"/>
  	    <xsl:with-param name="ext" select="$element/@publish:imageType"/>
      </xsl:call-template>
    </xsl:variable>
    
    <!-- Dyagnostic reporting -->
    <xsl:message terminate="no">
      <xsl:value-of select="$newLine"/>
      <xsl:text>Outputting HTML for diagram img tag file for: </xsl:text><xsl:value-of select="$element/@name"/>
      <xsl:value-of select="$newLine"/>
      
      <xsl:text>The value of diagramImageFileName is: "</xsl:text>
      <xsl:value-of select="$diagramImageFileName"/>
      <xsl:text>"</xsl:text>
      <xsl:value-of select="$newLine"/>
      
      <xsl:text>The value of @xmi:id is: "</xsl:text>
      <xsl:value-of select="$element/@xmi:id"/>
      <xsl:text>"</xsl:text>
      <xsl:value-of select="$newLine"/>
      
      <xsl:text>The value of @publish:imageType is: "</xsl:text>
      <xsl:value-of select="$element/@publish:imageType"/>
      <xsl:text>"</xsl:text>
      <xsl:value-of select="$newLine"/>

    </xsl:message>
    
    <!-- Place the image in a table so it has simple border (to turn off the blue link border the 
         img tag needs boder="0") -->
    <table class="ImageTable" border="1">
    <tr>
    <td>
        <xsl:choose>
            <xsl:when test="$globalDiagramImageFormat = 'SVG'">
                <!-- SVGs have a special way of embedding into HTML file -->
                <embed>
                    <xsl:attribute name="src">
                        <xsl:value-of select="$diagramImageFileName"/>
                    </xsl:attribute>
                    <xsl:attribute name="alt">
                        <xsl:value-of select="$element/@name"/>
                    </xsl:attribute>
                    <xsl:attribute name="type">
                        <xsl:value-of select="'image/svg+xml'"/>
                    </xsl:attribute>
                    <xsl:attribute name="width">
                        <xsl:value-of select="$element/@publish:imgWidth"/>
                    </xsl:attribute>
                    <xsl:attribute name="height">
                        <xsl:value-of select="$element/@publish:imgWidth"/>
                    </xsl:attribute>
                </embed>
            </xsl:when>
            <xsl:otherwise>
                <!-- JPEGs/GIFs/etc... Create the image element optionally with a hot spot map-->
                <img border="0">
                    <xsl:attribute name="src">
                        <xsl:value-of select="$diagramImageFileName"/>
                    </xsl:attribute>
                    <xsl:attribute name="alt">
                        <xsl:value-of select="$element/@name"/>
                    </xsl:attribute>
                    <xsl:if test="count($element/publish:partInfo) &gt; 0">
                        <!-- Include a reference to a map if there are partinfo elements -->
                        <xsl:attribute name="usemap">
                            <xsl:text>#map</xsl:text><xsl:value-of select="$element/@xmi:id"/>
                        </xsl:attribute>
                    </xsl:if>
                </img>
            </xsl:otherwise>
        </xsl:choose>
    </td></tr>
    </table>
    
    <xsl:if test="count($element/publish:partInfo) &gt; 0">
      <!-- Insert the hot spot map -->
      <map>
        <xsl:attribute name="name">
          <xsl:text>map</xsl:text><xsl:value-of select="$element/@xmi:id"/>
        </xsl:attribute>
        
        <!-- Insert the hot spot map entries -->
        <xsl:for-each select="$element/publish:partInfo">
          <!-- Since the partInfo element has an xmi:id it can be passed to createElementContentFileName to
               generate the link file name -->
          <xsl:variable name="partReferenceContentFileName">
            <xsl:call-template name="createElementContentFileName"/>
          </xsl:variable>
          
          <!-- Most shapes' hotspots are represented by their bounding boxes, i.e. rectangles -->
          <xsl:if test="@shape = 'rect'">
            <!-- Add an area element to the map -->
            <area shape="rect">
              <!-- mark the target frame if this is model type element -->
              <xsl:if test="(@publish:ismodellink = 'true') and ($globalTopLevelId != @xmi:id)">
                <xsl:attribute name="target">_top</xsl:attribute>
              </xsl:if>
              <!-- Calculate the coordinates for this part -->
              <xsl:attribute name="coords">
                <xsl:value-of select="@publish:partUpperX"/><xsl:text>,</xsl:text>
                <xsl:value-of select="@publish:partUpperY"/><xsl:text>,</xsl:text>
                <xsl:value-of select="@publish:partLowerX"/><xsl:text>,</xsl:text>
                <xsl:value-of select="@publish:partLowerY"/>
              </xsl:attribute>
              <!-- Reference the element this part views -->
              <xsl:attribute name="href">
                <xsl:value-of select="$partReferenceContentFileName"/>
              </xsl:attribute>
              <!-- Show the element's name of this part's view -->
              <xsl:attribute name="alt">
                <xsl:value-of select="@name"/>
              </xsl:attribute>
            </area>
          </xsl:if>
          
          <!-- Relationships' hotspots are represented by polylines -->
          <xsl:if test="@shape = 'poly'">
            <!-- Add an area element to the map -->
            <area shape="poly">
              <!-- Calculate the coordinates for this part -->
              <xsl:attribute name="coords">
                <xsl:value-of select="@coords"/>
              </xsl:attribute>
              <!-- Reference the element this part views -->
              <xsl:attribute name="href">
                <xsl:value-of select="$partReferenceContentFileName"/>
              </xsl:attribute>
              <!-- Show the element's name of this part's view -->
              <xsl:variable name="altTitle">
                <xsl:call-template name="getLocalizedString">
                  <xsl:with-param name="key" select="'Hotspot.PolyName'"/>
                </xsl:call-template>
              </xsl:variable>
              <xsl:attribute name="alt">
                <xsl:value-of select="$altTitle"/>
              </xsl:attribute>
            </area>
          </xsl:if>
          
        </xsl:for-each>
      </map>
    </xsl:if>
    
  </xsl:template>
	
	
  <!-- ======================================================================================== -->
  <!--  Given the current node return the fully qualified name (or optionally just packages) as 
        a string.  The separator is optional and has a default value determined by a global 
        variable -->
  <!-- ======================================================================================== -->
  <xsl:template name="generateFullyQualifiedName">
    <xsl:param name="includeModelName" select="false ()"/>
    <xsl:param name="includeMyName" select="true ()"/>
    
    <!-- Create variables that hold the optional parts of the name -->
    <xsl:variable name="topLevelName">
      <xsl:choose>
        <xsl:when test="$topLevelIsModel">
          <xsl:value-of select="/uml:Model/@publish:modelname"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="/packagedElement[1]/@publish:modelname"/>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:value-of select="$qualifiedNameSep"/>
    </xsl:variable>
    
    <xsl:variable name="elementName">
      <xsl:value-of select="$qualifiedNameSep"/>
      <xsl:value-of select="@name"/>
    </xsl:variable>

    <xsl:choose>
      <xsl:when test="$includeModelName and $includeMyName">
    	  <!-- Just return the whole qualifiedname -->
        <xsl:value-of select="@publish:qualifiedname"/>
      </xsl:when>
      <xsl:when test="not ($includeModelName) and $includeMyName">
    	  <!-- Strip off the optional top level name part -->
    	  <xsl:value-of select="substring-after (@publish:qualifiedname, $topLevelName)"/>
      </xsl:when>
      <xsl:when test="$includeModelName and not ($includeMyName)">
    	  <!-- Strip off the optional element name part -->
    	  <xsl:value-of select="substring-before (@publish:qualifiedname, $elementName)"/>
      </xsl:when>
    	<xsl:otherwise>
    	  <!-- not ($includeModelName) and not ($includeMyName) -->
    	  <!-- Strip off both optional parts -->
    	  <xsl:value-of select="substring-after (substring-before (@publish:qualifiedname, $elementName), $topLevelName)"/>
    	</xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- get the type from the main element attribute, parameter, etc... -->
  <xsl:template name="getType" match="publish:properties/publish:property[@name='Type']">
      <xsl:param name="useCommaSpace" select="false()"/>
      <xsl:value-of select="@publish:value"/>
      <!--if else -->
    <xsl:choose>
        <xsl:when test="$useCommaSpace = true()">
                <xsl:text>, </xsl:text>    
		</xsl:when>
		<xsl:otherwise>
            <xsl:text> </xsl:text>    
		</xsl:otherwise>
	</xsl:choose>
  </xsl:template>
  
  
  
  <!-- generate diagram properties table -->  
  <xsl:template name="generateDiagramPropertiesTable" match="contents">
    <!-- Localization variables -->
    <xsl:variable name="DiagramType">
      <xsl:call-template name="getLocalizedString">
      	<xsl:with-param name="key" select="'Diagram.Type'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="DiagramDiagram">
      <xsl:call-template name="getLocalizedString">
      	<xsl:with-param name="key" select="'Diagram.Diagram'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="DiagramName">
      <xsl:call-template name="getLocalizedString">
      	<xsl:with-param name="key" select="'Diagram.Name'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="propertiesTitle">
      <xsl:call-template name="getLocalizedString">
      	<xsl:with-param name="key" select="'ContentHeading.Properties'"/>
      </xsl:call-template>
    </xsl:variable>
    <p class="LargeTitle"><xsl:copy-of select="$propertiesTitle"/></p>
	<table class="PropertiesTable" cellpadding="4" cellspacing="0" border="1" >
  	<xsl:variable name="DiagramTypeValue" select="@Type"/>
	<xsl:variable name="DiagramNameValue" select="@name"/>
	<xsl:variable name="DiagramPublishCategoryValue" select="./publish:properties[1]/publish:property[1]/@publish:category"/>
        <tr>
        <td class="CategoryTitle" colspan="4"><xsl:value-of select="$DiagramPublishCategoryValue"/></td>	        
  		</tr>
  		<tr>
  			<td><b><xsl:value-of select="$DiagramName"/></b></td>  			
  			<td><xsl:value-of select="$DiagramNameValue"/></td>
  			<td><b><xsl:value-of select="$DiagramType"/></b></td>
  			<td><xsl:value-of select="$DiagramTypeValue"/><xsl:value-of select="$DiagramDiagram"/></td>
  		</tr>
	</table>
	</xsl:template>
  		



  <!-- For some reason fast grouping is not working for properties until we find a way to make it work use the slower way-->
  <!--<xsl:key name="properties-by-category" match="/*/publish:property" use="@publish:category"/>-->
  
  <xsl:template name="outputPropertyTable" match="publish:properties">
    <xsl:param name="indentCount" select="0"/>
    <xsl:param name="largeTitle" select="true()"/>
    <xsl:param name="includeTitle" select="true()"/>
    <xsl:param name="bookMark" select="''"/>
    
      
    <!--<h4>Properties fast grouping</h4>
    <xsl:for-each select="publish:property[count(. | key('properties-by-category', @publish:category)[1]) = 1]">
      <xsl:sort select="@publish:category"/>
      <xsl:value-of select="@publish:category"/><br/>
      <xsl:for-each select="key('properties-by-category', @publish:category)">
        <xsl:sort select="@name"/>
        &#160;&#160;&#160;&#160;<xsl:text>Property </xsl:text><xsl:value-of select="@name"/>&#160;<xsl:value-of select="@publish:value"/><br/>
      </xsl:for-each>
    </xsl:for-each>-->

    <!-- Localization variables -->
    <xsl:variable name="propertiesTitle">
      <xsl:call-template name="getLocalizedString">
      	<xsl:with-param name="key" select="'ContentHeading.Properties'"/>
      </xsl:call-template>
    </xsl:variable>
    
    <!-- Create the table title HTML in a variable for output below -->
    <xsl:variable name="titleHTML">
      <xsl:choose>
        <xsl:when test="$largeTitle">
			<p class="LargeTitle"><xsl:copy-of select="$propertiesTitle"/></p>
        </xsl:when>
        <xsl:otherwise>
			<p class="RegularTitle"><xsl:copy-of select="$propertiesTitle"/></p>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <br/>    
    
    <!-- Optional title -->
    <xsl:if test="$includeTitle">
      <!-- Optionally enclose the title in an achor -->
      <xsl:choose>
        <xsl:when test="string-length ($bookMark) = 0">
          <xsl:copy-of select="$titleHTML"/>
        </xsl:when>
        <xsl:otherwise>
          <a><xsl:attribute name="name"><xsl:value-of select="$bookMark"/></xsl:attribute>
            <xsl:copy-of select="$titleHTML"/>
          </a>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>   
    
    <!-- Performance sensitive area -->    
      <table class="PropertiesTable" cellpadding="4" cellspacing="0" border="1" >
      <xsl:for-each select="publish:property">
        <!-- <xsl:sort select="@publish:category"/> -->
        
        <!-- Loop for half the properties adding two pairs of columns per row (left hand pair 1 to half way right hand pair half way to last) -->
        <tr>
          <!-- Left hand pair -->
          <td class="PropertyName" >
            <b><xsl:value-of select="@name"/></b>
          </td>
          <!-- check if the value needs to be made a link -->
          <xsl:choose>
            <xsl:when test="@url_link">
              <td class="PropertyValue">
                <!-- Create a reference to each element's content HTML file -->
                <xsl:call-template name="outputImageAndTextLinkHTML">
	                <xsl:with-param name="link" select="@url_link"/>
	                <xsl:with-param name="text" select="@publish:value"/>
                </xsl:call-template>
              </td>
            </xsl:when>
            <xsl:otherwise>
              <td class="PropertyValue">
                <xsl:value-of select="@publish:value"/>
              </td>
            </xsl:otherwise>
          </xsl:choose>
        </tr>
      </xsl:for-each>
    </table>   
  </xsl:template>
  
  
  

</xsl:stylesheet>
