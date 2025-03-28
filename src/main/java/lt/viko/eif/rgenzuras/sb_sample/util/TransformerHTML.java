package lt.viko.eif.rgenzuras.sb_sample.util;

import org.springframework.core.io.ClassPathResource;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Class to handle XML to PDF transformation using XSLT and HTML-to-PDF conversion.
 */
public class TransformerHTML implements Transformer {

    @Override
    public String Transform(String xml, String transformationSchemaPath) throws Exception {
        ClassPathResource resource = new ClassPathResource(transformationSchemaPath);

        // Initialize the XSLT transformer for HTML
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xsltStream = new StreamSource(resource.getFile());
        var transformer = factory.newTransformer(xsltStream);

        // Prepare XML input
        StreamSource xmlStream = new StreamSource(new StringReader(xml));

        // Prepare output
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        // Perform the transformation
        transformer.transform(xmlStream, result);

        return writer.toString(); // Return the transformed HTML content
    }
}