<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:ns2="http://schema.sb-sample.rgenzuras.eif.viko.lt">
    <xsl:output method="html" encoding="UTF-8"/>

    <!-- Template to handle addItemResponse, addCustomerResponse, removeCustomerResponse, removeItemResponse -->
    <xsl:template match="/ns2:addCustomerResponse | /ns2:addItemResponse | /ns2:removeCustomerResponse | /ns2:removeItemResponse">
        <html>
            <head>
                <title>Modification Result</title>
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
                    <h1>Modification Result</h1>

                    <div class="field">
                        <div class="label">Response:</div>
                        <div class="value"><xsl:choose>
                            <xsl:when test="ns2:result = 'true'">
                                Operation successful!
                            </xsl:when>
                            <xsl:otherwise>
                                Operation failed.
                            </xsl:otherwise>
                        </xsl:choose></div>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
