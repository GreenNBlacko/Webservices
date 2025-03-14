package lt.viko.eif.rgenzuras.sb_sample.util;

import org.springframework.core.io.ClassPathResource;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.StringReader;

public class ValidatorXSD implements Validator {

    @Override
    public boolean validate(String xmlData, String validationSchemaPath) {
        try {
            ClassPathResource resource = new ClassPathResource(validationSchemaPath);
            var schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            var schema = schemaFactory.newSchema(resource.getFile());
            var validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlData)));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
