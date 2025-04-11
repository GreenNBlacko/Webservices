<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:ns2="http://schema.sb-sample.rgenzuras.eif.viko.lt"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:output method="xml" encoding="UTF-8"/>

    <xsl:template match="/ns2:addCustomerResponse | /ns2:addItemResponse | /ns2:removeCustomerResponse | /ns2:removeItemResponse">
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
                        Modification result
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
                                <svg width="40" height="40" xmlns="http://www.w3.org/2000/svg" viewBox="-300 -160 1040 1040">
                                    <circle cx="220" cy="360" stroke="#000" stroke-width="100" fill-opacity="0" r="470"/>
                                    <path transform="scale(0.6)" stroke-width="75" fill="#000000"
                                          d="M-27 1120V80h808.5v154.5l-40 41.5V120H13v960h728.5V721.5l40-34V1120Zm387-129q-38 0-58.75-8.75t-41.75-8.75q-13.5 0-25 6.5t-30.5 6.5q-11.5 0-30-6.25T144 974q-14 0-26.5 7L106 958.5q18.5-9.5 38-9.5 13 0 31.75 6.25T204 961.5q11.5 0 25.25-6.5t30.25-6.5q25 0 47 8.75T360 966q13 0 32.5-4.5t34-9.5l8.5 23q-18.5 7-39.75 11.5T360 991M223 822.5q-18.5 0-38.25-6t-31.25-6q-15 0-28 6.5L114 794.5q18-9 39.5-9 14 0 34 6t35.5 6q16 0 41.5-8.5t46.5-9v25q-12 .5-28.5 4.75t-32.5 8.5-27 4.25M453 966l-50-34.5L426 818q10 46.5 30.25 67.75T511.5 907q9.5 0 22-.75t29-2.25ZM123.5 698.5 112 676q18.5-9 38-9 11.5 0 23.25 2.75T193 672.5q11.5 0 28.5-8t34-8q23 0 46.25 4.25T357.5 665q27.5 0 62.25-3t60.25-7.5l5 24q-26.5 5-62.75 8.25T357.5 690q-36 0-59.5-4.25t-42.5-4.25q-13 0-28.75 8t-33.75 8q-11.5 0-21.5-2.75T150 692q-13.5 0-26.5 6.5m82-124.5q-23.5 0-32-8t-20-8q-15 0-28 6.5L114 542q18-9 39.5-9 16 0 26.75 8t25.25 8q10.5 0 27.75-6.5T266 536q20.5 0 41.5 11L295 568.5q-14-7.5-29-7.5-13.5 0-28.25 6.5T205.5 574m229 402q-17 0-29.5-11t-12.5-27.5q0-3.5 1-8.5l40-198q2.5-11.5 6.5-23.75T450.5 685l25 24.5q-3 6.5-5.5 13.25t-4 14.75l-32.5 162 32.5 28L624.5 839q10.5-5.5 18.75-11.75T655 816.5l24 23.5q-8.5 12.5-38 29L454 971.5q-8.5 4.5-19.5 4.5m-229-525q-23.5 0-32-8t-20-8q-15 0-28 6.5L114 419q18-9 39.5-9 16 0 26.75 8t25.25 8q11.5 0 30.5-6.5t35-6.5q20.5 0 33.75 5.25T335 423.5q27.5 0 53-9l9.5 23.5Q370 448.5 335 448.5q-24.5 0-35.5-5.25T271 438q-14 0-31.25 6.5T205.5 451m297 123q-23.5 0-32-8t-20-8q-15 0-28 6.5L411 542q18-9 39.5-9 16 0 26.75 8t25.25 8q10 0 27-6.25t36-6.75v25q-16.5.5-31.25 6.75T502.5 574M313 294.5q-24 0-39.25-9.25T241 276q-14 0-23.75 5.5T188.5 287q-12.5 0-21.5-3.25t-20.5-3.25q-13.5 0-26.5 6.5l-11.5-22.5q18.5-9 38-9 12 0 24.25 3.25T188.5 262q10.5 0 23.5-5.5t29-5.5q20.5 0 38.75 9.25T313 269.5q17.5 0 37.75-11.75T401.5 246q26.5 0 57.75 6.25T515 253.5l5.5 24q-29.5 6.5-58.75 0T401.5 271q-18.5 0-31.75 5.75a938 938 0 0 0-26 11.75q-12.75 6-30.75 6M641.5 850q-17.5 0-30.75-9.25T592 817q-8 1.5-14 2.5t-11 1q-19.5 0-37.5-11.5-24.5-15.5-27.5-50.5-27.5.5-42.5-11.5t-15-33.5q0-13 6-28.5L686 357.5l27.5 19.5L481 700.5q-1.5 3.5-1.5 7 0 8 6.25 13.75T500.5 727q8 0 14-4.5l219.5-307 27 19.5-226.5 316q.5 17 9 26.25t23.5 9.25q4 0 9-1t11-2.5l227-317 27.5 19.5-215 300.5q-2.5 5-2.5 10.5 0 8 6.25 13.75T645 816q8 0 14-4.5l231.5-324 27.5 19L679 840q-19.5 10-37.5 10m288-332.5q-39.5-1-73.25-13.25T778.5 463.5Q735 435 711.25 409T672 348l79.5-107 30.5 3-5 34-8.5-1.5L711 352q13 23 32.25 41.75t54.25 41.75q35.5 23 61.5 33.75t54.5 13.25l56-74.5q-1.5-3-2-4.25a6.7 6.7 0 0 0-1.5-2.25l29.5-17 12.5 27Zm40-90q-31.5-4.5-61-17T845 375q-38-25.5-60.25-47.5T748 280l23-30q12 27.5 32.5 49t60 48q38 25.5 66.5 36.25t62.5 12.25Zm7-.5-27-20.5 58-76.5q9-12 9-25 0-27-49.5-58.5l-41-26Q892.5 199 867.5 199q-22.5 0-36 18l-59 80-27-20 59-80q23.5-31.5 62.5-31.5 36 0 77 27l40.5 26.5c44.33 28.667 66.5 58 66.5 88 0 14.667-5.33 29-16 43Zm49-92q-28.5-5.5-56.25-18T909 283q-30-20.5-50.25-38.5T824 207l22.5-27.5q12.5 19.5 31.25 37t50.25 39q32 21.5 57 32 25.005 10.5 51 14.5Z"/>
                                </svg>
                            </fo:instream-foreign-object>
                        </fo:block>

                        <!-- Customer Details -->
                        <fo:block font-weight="bold" color="#444" font-size="13pt" space-after="4pt">
                            <xsl:choose>
                                <xsl:when test="ns2:result = 'true'">
                                    <fo:block font-size="14pt" color="green">Operation successful!</fo:block>
                                </xsl:when>
                                <xsl:otherwise>
                                    <fo:block font-size="14pt" color="red">Operation failed.</fo:block>
                                </xsl:otherwise>
                            </xsl:choose>
                        </fo:block>

                    </fo:block>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

</xsl:stylesheet>
