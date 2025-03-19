package lt.viko.eif.rgenzuras.sb_sample.util;

import jakarta.xml.bind.*;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Class that transforms POJO to XML and vice versa
 * @author ramunas.genzuras@stud.viko.lt
 */
public class MarshallerXML implements Marshaller {
    /**
     * Converts an object into an XML string
     * @param object the object to convert
     * @apiNote add prettyPrint to format the XML output. (optional)
     * @return XML string
     * @param <T> type of the object
     */
    @Override
    public <T> String Marshal(T object) {
        return Marshal(object, false);
    }

    /**
     * Converts an object into an XML string
     * @param object the object to convert
     * @param prettyPrint whether the XML string should be formatted (true) or minified (false). (optional)
     * @return XML string
     * @param <T> type of the object
     */
    @Override
    public <T> String Marshal(T object, boolean prettyPrint) {
        GenericList<T> Data = new GenericList<>();

        try {
            StringWriter writer = new StringWriter();
            var context = JAXBContext.newInstance(object.getClass(), Data.getClass());
            var marshaller = context.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, prettyPrint);
            marshaller.marshal(object, writer);
            return writer.toString();
        } catch (JAXBException ignored) {
            return null;
        }
    }

    @Override
    public <T> T Unmarshal(Class<T> objectClass, String xml) {
        try {
            var context = JAXBContext.newInstance(objectClass);
            var unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException ignored) {
            return null;
        }
    }
}
