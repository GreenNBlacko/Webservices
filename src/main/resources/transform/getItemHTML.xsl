<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:ns2="http://schema.sb-sample.rgenzuras.eif.viko.lt">
    <xsl:output method="html" encoding="UTF-8"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Get item</title>
                <style>
                    body {
                    font-family: Arial, sans-serif;
                    background-color: #f8f9fa;
                    color: #212529;
                    padding: 2rem;
                    }

                    .card {
                    background-color: white;
                    border-radius: 8px;
                    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                    padding: 2rem;
                    max-width: 600px;
                    margin: 0 auto;
                    }

                    .card h1 {
                    margin-top: 0;
                    font-size: 1.75rem;
                    color: #007bff;
                    }

                    .field {
                    margin-bottom: 1rem;
                    display: flex;
                    justify-content: space-between;
                    border-bottom: 1px solid #e0e0e0;
                    padding-bottom: 0.5rem;
                    }

                    .label {
                    font-weight: bold;
                    color: #555;
                    }

                    .value {
                    text-align: right;
                    color: #333;
                    }
                </style>
            </head>
            <body>
                <div class="card">
                    <h1>Item Details</h1>

                    <div class="field">
                        <div class="label">ID:</div>
                        <div class="value"><xsl:value-of select="//ns2:item/ns2:ID"/></div>
                    </div>

                    <div class="field">
                        <div class="label">Name:</div>
                        <div class="value"><xsl:value-of select="//ns2:item/ns2:Name"/></div>
                    </div>

                    <div class="field">
                        <div class="label">Price:</div>
                        <div class="value"><xsl:value-of select="//ns2:item/ns2:Price"/></div>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
