<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:ns2="http://schema.sb-sample.rgenzuras.eif.viko.lt"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:output method="xml" encoding="UTF-8"/>

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="main" page-height="297mm" page-width="210mm">
                    <fo:region-body margin="25mm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="main">
                <fo:flow flow-name="xsl-region-body">

                    <!-- Title -->
                    <fo:block font-size="20pt"
                              font-weight="bold"
                              color="#333333"
                              text-align="center"
                              space-after="15pt"
                              font-family="Helvetica">
                        Customer Overview
                    </fo:block>

                    <fo:block border="1pt solid #cccccc"
                              padding="10pt"
                              background-color="#f9f9f9"
                              font-family="Helvetica"
                              font-size="11pt"
                              space-after="20pt">

                        <!-- Inline SVG Icon -->
                        <fo:block>
                            <fo:instream-foreign-object>
                                <svg width="40" height="40" xmlns="http://www.w3.org/2000/svg" viewBox="-300 -150 1500 1500">
                                    <circle cx="455" cy="600" stroke="#000" stroke-width="100" fill-opacity="0" r="650"/>
                                    <path transform="scale(0.9)" stroke-width="75" fill="#000000" style="padding: 10" d="M80 1120q-6.5-20.505-9.75-39c-2.167-12.33-3.25-22.67-3.25-31q0-55.5 45.75-99.25t123.75-71.5T412 845.5l1.5 17.5q-68.5-26-119.75-83.5t-79.5-138T186 467q0-113.5 40.5-200.75t114-136.75T512 80q98.5 0 171.75 49.5T797.5 266.25 838 467q0 94-28.25 174.5t-79.25 138T611 863l1-17.5q98 6 176 33.75t123.5 71.5T957 1050c0 8.33-.917 18.67-2.75 31q-2.75 18.495-9.75 39Zm28-40h808c1.333-4.33 2.25-9.17 2.75-14.5s.75-10.5.75-15.5c0-29.67-14.417-56.75-43.25-81.25Q833 932 755.25 908.5T574.5 879v-42.5Q644 814 694 762t77-127.5T798 467q0-105.5-35.25-183.5t-99.5-120.75T512 120q-86.5 0-151 42.75T261.25 283.5 226 467q0 92 27 167.5T330.25 762t119.25 74.5V879q-102.5 6-180.25 29.5T148 968.75c-29 24.5-43.5 51.58-43.5 81.25 0 5 .333 10.17 1 15.5s1.5 10.17 2.5 14.5"/>
                                </svg>
                            </fo:instream-foreign-object>
                        </fo:block>

                        <!-- Customer Details -->
                        <fo:block font-weight="bold" color="#444" font-size="13pt" space-after="4pt">
                            <xsl:value-of select="//ns2:customer/ns2:Name"/>
                            <xsl:text> </xsl:text>
                            <xsl:value-of select="//ns2:customer/ns2:Surname"/>
                        </fo:block>

                        <fo:block>
                            <fo:inline font-weight="bold">Customer ID:</fo:inline>
                            <fo:inline> <xsl:value-of select="//ns2:customer/ns2:ID"/> </fo:inline>
                        </fo:block>

                        <fo:block>
                            <fo:inline font-weight="bold">Email:</fo:inline>
                            <fo:inline> <xsl:value-of select="//ns2:customer/ns2:Email"/> </fo:inline>
                        </fo:block>

                    </fo:block>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
