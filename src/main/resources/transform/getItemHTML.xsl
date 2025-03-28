<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns2="http://schema.sb-sample.rgenzuras.eif.viko.lt">
    <xsl:template match="/">
        <html>
            <head>
                <META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
                <title>Get item</title>
            </head>
            <body>
                <h1>Item Details</h1>
                <table border="1">
                    <tr>
                        <th>ID</th><th>Name</th><th>Price</th>
                    </tr>
                    <tr>
                        <td><xsl:value-of select="//ns2:item/ns2:ID"/></td>
                        <td><xsl:value-of select="//ns2:item/ns2:Name"/></td>
                        <td><xsl:value-of select="//ns2:item/ns2:Price"/></td>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
