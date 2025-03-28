<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns2="http://schema.sb-sample.rgenzuras.eif.viko.lt"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:output method="xml" encoding="UTF-8"/>

    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="main" page-height="297mm" page-width="210mm">
                    <fo:region-body margin="20mm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="main">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="16pt" font-weight="bold" text-align="center">Item Details</fo:block>
                    <fo:table border="1px solid black" width="100%" margin-top="10mm">
                        <fo:table-column column-width="33%"/>
                        <fo:table-column column-width="33%"/>
                        <fo:table-column column-width="34%"/>
                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell font-weight="bold"><fo:block>ID</fo:block></fo:table-cell>
                                <fo:table-cell font-weight="bold"><fo:block>Name</fo:block></fo:table-cell>
                                <fo:table-cell font-weight="bold"><fo:block>Price</fo:block></fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell><fo:block><xsl:value-of select="//ns2:item/ns2:ID"/></fo:block></fo:table-cell>
                                <fo:table-cell><fo:block><xsl:value-of select="//ns2:item/ns2:Name"/></fo:block></fo:table-cell>
                                <fo:table-cell><fo:block><xsl:value-of select="//ns2:item/ns2:Price"/></fo:block></fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
