<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:ns2="http://schema.sb-sample.rgenzuras.eif.viko.lt">
    <xsl:output method="html" encoding="UTF-8"/>

    <xsl:template match="/">
        <html>
            <head>
                <META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
                <title>Get customer</title>
            </head>
            <body>
                <h1>Customer Details</h1>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Email</th>
                    </tr>
                    <tr>
                        <td><xsl:value-of select="//ns2:customer/ns2:ID"/></td>
                        <td><xsl:value-of select="//ns2:customer/ns2:Name"/></td>
                        <td><xsl:value-of select="//ns2:customer/ns2:Surname"/></td>
                        <td><xsl:value-of select="//ns2:customer/ns2:Email"/></td>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
