<?xml version="1.0"?>
<!--                                                                        -->
<!-- Licensed Materials - Property of IBM                                   -->
<!-- Copyright IBM Corp. 2003, 2004.  All Rights Reserved.                  -->
<!--                                                                        -->
<!-- US Government Users Restricted Rights - Use, duplication or disclosure -->
<!-- restricted by GSA ADP Schedule Contract with IBM Corp.                 -->
<!--                                                                        -->
<stylesheet version="1.0" xmlns="http://www.w3.org/1999/XSL/Transform" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:redirect="http://xml.apache.org/xalan/redirect"
    extension-element-prefixes="redirect"
>

	<xsl:output method="html"/>
 
    <!-- 
    This file generates the cascading stylesheets (CSS) needed for web publishing.
    Currently, all CSS elements are bundled into a single file. They will eventually 
    be split into multiple files and only those files that are needed will be generated.
    -->
	<xsl:template name="generateCSSFile">
		<!-- Currently the CSS filename is fixed. -->
		<xsl:variable name="cssFileName" select="&apos;WebPublish.css&apos;"/>

		<xsl:variable name="cssFullPathName">
			<xsl:call-template name="prependFullPathToFileName">
				<xsl:with-param name="fileName" select="$cssFileName"/>
			</xsl:call-template>
		</xsl:variable>

		<redirect:write file="{$cssFullPathName}"> 
		
		.NavigationBar
		{
			background-color:#CCFFFF;
			border-collapse:collapse;
			width:100%;
		}

		.NavBarLeft
		{
			font-weight:500;
			font-size:larger;
		}

		.NavBarLeft td
		{
			padding-right: 10;
			padding-left: 10;
		}

		.NavBarRight
		{
			text-align:right;
			font-weight:500;
			font-size:larger;
			width:25%;
		}

		.NavBarMainHighlight
		{
			background-color: #006699;
			color:#FFFFFF;
		}

		.NavBarBookmarks
		{
			text-align:left;
			font-size:small;
		}

		.BookMarkSections
		{
			padding-right:20px;
		}

		.BookMarkElements
		{
			padding-right:10px;
		}

		.ElementTitle
		{
			font-size:large;
			font-weight:600;
		}
		
		.PackageFullyQualifiedName
		{
			font-size:medium;
			font-weight:500;
		}

		.SummaryTable
		{
			border-style:solid;
			border-width: thin;
			width: 100%;
			border-collapse:collapse;
		}

		.SummaryTitle
		{
			background-color: #009BB9;
			font-size:large;
		}

		.DetailsTable
		{
			border-style:none;
			border-width: thin;
			width: 100%;
			border-collapse:collapse;
		}

		.DetailsTitle
		{
			background-color: #009BB9;
			font-size:large;
		}

		.PackagesTable
		{
			width: 100%;
			border-collapse:collapse;
		}

		.PackagesTitle
		{
			background-color: #009BB9;
			font-size:large;
		}
		
		.ListItem
		{
			font-size: larger;
			font-weight: 500;
		}
		
		.OwnedMemberType
		{
			width: 25%;
		}
		
		.LargeTitle
		{
			font-size:large;
			font-weight:600;
		}
		
		.RegularTitle
		{
			font-size: larger;
			font-weight:400;
		}
		
		.Title
		{
			font-size: larger;
			font-weight:500;
		}
		
		.PackageList
		{
			border-style:none;
			width: 100%;
		}
		
		.ContainedElements
		{
			border-style: solid;
			border-width: thin;
			border-collapse: collapse;
			width: 100%;
			cellspacing: 0;
			cellpadding: 3;
		}
		
		.PackageNavigationList
		{
			border-style: none;
			width: 100%;
		}
		
		.PackageList
		{
			font-weight: 500;
			border-style: none;
		}
		
		.ClassesTableHeading
		{
			font-weight: 500;
			font-size: larger;
		}
		
		.ClassTableEntry
		{
			background-color: white;
		}
		
		.ClassTableEntryLink
		{
			width: 15%;
		}
		
		.ElementDocumentation
		{
			font-style: normal;

			font-family: Times New Roman;
		}
		
		.ImageTable
		{
			border-style: solid;
			border-width: thin;
			border-collapse: collapse;
			cellspacing: 0;
			cellpadding: 3;
		}
		
		.PropertiesTable
		{
			border-style: solid;
			border-width: thin;
			border-collapse: collapse;
			cellspacing: 0;
			cellpadding: 4;
		}
		
		.CategoryTitle
		{
			font-weight: 600;
		}
		
		.PropertyName
		{
			width: 25%;
			font-weight: 600;
		}
		
		.PropertyValue
		{
			width: 25%;
			font-weight: 400;
		}
		
		</redirect:write>
	</xsl:template>
</stylesheet>