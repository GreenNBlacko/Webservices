package lt.viko.eif.rgenzuras.sb_sample.util;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.core.io.ClassPathResource;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

public class TransformerPDF implements Transformer {

    @Override
    public String Transform(String xml, String transformationSchemaPath) throws Exception {
        try {
            // Prepare the XML data source
            StringReader xmlReader = new StringReader(xml);
            StreamSource xmlSource = new StreamSource(xmlReader);

            // Load the XSLT file
            ClassPathResource resource = new ClassPathResource(transformationSchemaPath);
            StreamSource xsltStream = new StreamSource(resource.getFile());

            // Prepare the output stream for the PDF
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

            // Create a transformer factory and a transformer object
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer(xsltStream);

            // Set up the FOP factory and user agent
            FopFactory fopFactory = FopFactory.newInstance(new ClassPathResource("fop.xconf").getFile());
            FOUserAgent userAgent = fopFactory.newFOUserAgent();

            // Create FOP processor to generate PDF from the XSLT transformation
            fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, pdfOutputStream);

            // Perform the transformation and output to FOP for PDF generation
            transformer.transform(xmlSource, new SAXResult(fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, pdfOutputStream).getDefaultHandler()));

            // Encode PDF output to Base64
            return encodeBase64(pdfOutputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper function to encode byte array to Base64 string
    private String encodeBase64(byte[] data) {
        return java.util.Base64.getEncoder().encodeToString(data);
    }
}

