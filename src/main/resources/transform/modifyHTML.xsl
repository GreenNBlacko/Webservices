<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:ns2="http://schema.sb-sample.rgenzuras.eif.viko.lt">
    <xsl:output method="html" encoding="UTF-8"/>

    <!-- Template to handle addItemResponse, addCustomerResponse, removeCustomerResponse, removeItemResponse -->
    <xsl:template match="/ns2:addCustomerResponse | /ns2:addItemResponse | /ns2:removeCustomerResponse | /ns2:removeItemResponse">
        <html>
            <head>
                <title>Modification Result</title>
            </head>
            <body>
                <h1>Modification Result</h1>
                <xsl:choose>
                    <!-- Test for the result element with the correct namespace -->
                    <xsl:when test="ns2:result = 'true'">
                        <p>Operation successful!</p>
                    </xsl:when>
                    <xsl:otherwise>
                        <p>Operation failed.</p>
                    </xsl:otherwise>
                </xsl:choose>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
