<?xml version="1.0"?>
<!--                                                                        -->
<!-- Licensed Materials - Property of IBM                                   -->
<!-- Copyright IBM Corp. 2003, 2004.  All Rights Reserved.                  -->
<!--                                                                        -->
<!-- US Government Users Restricted Rights - Use, duplication or disclosure -->
<!-- restricted by GSA ADP Schedule Contract with IBM Corp.                 -->
<!--                                                                        -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" 
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
  <!-- Generate <fo:simple-page-master> element with the passed in name to support printing to 
       Letter sized paper
       
       Parameters:
         name - the name to be given to the <fo:simple-page-master> element
  -->
  <xsl:template name="generateLetterMasterSet">
    <xsl:param name="name" select="'main'"/>
    
    <fo:simple-page-master master-name="{$name}" 
      margin-top="72pt" margin-bottom="72pt" 
      page-width="8.5in" page-height="11in" 
      margin-left="72pt" margin-right="72pt">
      <fo:region-body />
    </fo:simple-page-master>
  </xsl:template>

</xsl:stylesheet>