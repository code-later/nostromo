<?xml version="1.0" encoding="UTF-8"?>
<!-- The stylesheet sorts XML elements by their guid (xmi:id attribute).
This is done only in testing mode in order to get consistent results for our 
JUnit tests. Without sorting essentially identical files will show up as different
if the sequence of elements is different. This happens due to the fact that
model traverse order is unpredictable.

This stylesheet will be applied to the following XML files during publish for JUnit testing:
- XXX-allelements.xml;
- XXX-diagrams.xml;
- XXX-packages.xml. 


-bdubauski 10/31/2004

-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:xmi="http://www.omg.org/XMI" xmlns:publish="http://www.ibm.com/Rational/XTools/Publish">
    <xsl:output method="xml" indent="yes"/>
    
    <!-- This template matches the document Root and any
        node not already specified by a template below. It copies the
        node to the output, then looks for attributes and children -->
    <xsl:template match="/ | node()">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:apply-templates select="node()"/>
        </xsl:copy>
    </xsl:template>
    
    <!-- This template copies the text of attributes to the output.
        We don't need one for textnode children of elements, because
        there is a built-in default template that does this for us.  -->
    <xsl:template match="@*">
        <xsl:copy/>
    </xsl:template>
    
    
    <!--This template should be set to match the
        parent of element. XXX-allelements.xml -->
    <xsl:template match="publish:elements">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:apply-templates select="publish:element">
                <xsl:sort select="@xmi:id"/>
            </xsl:apply-templates>
        </xsl:copy>
    </xsl:template>
    
    <!--This template should be set to match the
        parent of element. XXX-diagrams.xml -->
    <xsl:template match="publish:diagrams">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:apply-templates select="publish:diagram">
                <xsl:sort select="@xmi:id"/>
            </xsl:apply-templates>
        </xsl:copy>
    </xsl:template>
    
    <!--This template should be set to match the
        parent of element. XXX-packages.xml -->
    <xsl:template match="publish:packages">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:apply-templates select="publish:package">
                <xsl:sort select="@xmi:id"/>
            </xsl:apply-templates>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
