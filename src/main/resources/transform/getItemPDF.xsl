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
                        Item Overview
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
                                <svg width="40" height="40" xmlns="http://www.w3.org/2000/svg" viewBox="-240 -120 1040 1040">
                                    <circle cx="280" cy="400" stroke="#000" stroke-width="100" fill-opacity="0" r="475"/>
                                    <path transform="scale(0.65)" stroke-width="75" fill="#000000" d="m675 707.5-627-41v-363l761-47 24-58.5q20.5-50 59-66l49.5-20.5L956 148l-47.5 19.5q-25 10.5-39 45.5ZM51 956l-29-29q33-44.5 98.5-44.5l368 1.5q23.5 0 41.25-16.75T547.5 829q0-11.5-7.5-29t-22-38l-54-78 32.5-24 51.5 77q19.5 29 29.25 52.25T587 829q0 29.5-20 54h89.5q23 0 38.5-15.75T710.5 829q0-12-7.25-29.5T683 763l-46.5-68.5 32-24L717 741q16 23.5 24.75 46.25T750.5 829q0 40.5-27.25 67.25T656.5 923l-536-1q-46 0-69.5 34m129-300-29-2 16-341.5 28.5 2Zm96.5 8-28-4L296 303l28.5 4Zm-215-98v-28.5H711V566Zm312 107-28.5-7.5L425 293l28 6.5Zm274.5-7.5L792.5 297 88 341v288ZM56 456l-1.5-28.5 702.5-25 1.5 29Zm414.5 225-28-8L554 283.5l28 8Zm97 7-28-8.5 144-405.5 26.5 10Zm388-516q-18.5 0-32-13.5t-13.5-33q0-19 13.5-32.25t32-13.25q19.5 0 33 13.25t13.5 32.25-13.5 32.75-33 13.75m-775 948c-22.667 0-42.167-8.08-58.5-24.25s-24.5-35.58-24.5-58.25c0-23.33 8.167-43.167 24.5-59.5q24.5-24.5 58.5-24.5 35 0 59.25 24.5c16.167 16.333 24.25 36.17 24.25 59.5 0 22.33-8.167 41.67-24.5 58s-36 24.5-59 24.5m0-36c13.333 0 24.667-4.58 34-13.75s14-20.08 14-32.75c0-13.33-4.583-24.67-13.75-34q-13.75-14-34.25-14-19.5 0-33.5 14c-9.333 9.33-14 20.67-14 34 0 12.67 4.667 23.58 14 32.75s20.5 13.75 33.5 13.75m.5-30.5q-6.5 0-12-6c-3.667-4-6.167-9.17-7.5-15.5L138 914.5h86L200.5 1032c-1.333 6.33-3.75 11.5-7.25 15.5q-5.25 6-12.25 6m429.5 66.5c-22.667 0-42.167-8.08-58.5-24.25s-24.5-35.58-24.5-58.25c0-23.33 8.167-43.167 24.5-59.5q24.5-24.5 58.5-24.5 35 0 59.25 24.5c16.167 16.333 24.25 36.17 24.25 59.5 0 22.33-8.167 41.67-24.5 58s-36 24.5-59 24.5m0-36c13.333 0 24.667-4.58 34-13.75s14-20.08 14-32.75c0-13.33-4.583-24.67-13.75-34q-13.75-14-34.25-14-19.5 0-33.5 14c-9.333 9.33-14 20.67-14 34 0 12.67 4.667 23.58 14 32.75s20.5 13.75 33.5 13.75m.5-30.5q-6.5 0-12-6c-3.667-4-6.167-9.17-7.5-15.5L568 914.5h86L630.5 1032c-1.333 6.33-3.75 11.5-7.25 15.5q-5.25 6-12.25 6"/>
                                </svg>
                            </fo:instream-foreign-object>
                        </fo:block>

                        <!-- Customer Details -->
                        <fo:block font-weight="bold" color="#444" font-size="13pt" space-after="4pt">
                            <xsl:value-of select="//ns2:item/ns2:Name"/>
                        </fo:block>

                        <fo:block>
                            <fo:inline font-weight="bold">Item ID:</fo:inline>
                            <fo:inline> <xsl:value-of select="//ns2:item/ns2:ID"/> </fo:inline>
                        </fo:block>

                        <fo:block>
                            <fo:inline font-weight="bold">Price:</fo:inline>
                            <fo:inline> $<xsl:value-of select="//ns2:item/ns2:Price"/> </fo:inline>
                        </fo:block>

                    </fo:block>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
