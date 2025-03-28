<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:ns2="http://schema.sb-sample.rgenzuras.eif.viko.lt"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:output method="xml" encoding="UTF-8"/>

    <xsl:template match="/ns2:addCustomerResponse | /ns2:addItemResponse | /ns2:removeCustomerResponse | /ns2:removeItemResponse">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="main" page-height="297mm" page-width="210mm">
                    <fo:region-body margin="20mm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="main">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="16pt" font-weight="bold" text-align="center">Modification Result</fo:block>
                    <xsl:choose>
                        <xsl:when test="ns2:result = 'true'">
                            <fo:block font-size="14pt" color="green">Operation successful!</fo:block>
                        </xsl:when>
                        <xsl:otherwise>
                            <fo:block font-size="14pt" color="red">Operation failed.</fo:block>
                        </xsl:otherwise>
                    </xsl:choose>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

</xsl:stylesheet>
