<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
                xmlns:publish="http://www.ibm.com/Rational/XTools/Publish">
  <!-- Global Variables -->
  <xsl:variable name="fileSep">/</xsl:variable>
  
  <xsl:variable name="newLine">
    <xsl:text>
    </xsl:text>
  </xsl:variable>
  
  <xsl:variable name="topLevelIsModel" select="not(/publish:report[1]/packagedElement[1]/@xsi:type='uml:Package')"/>

</xsl:stylesheet>