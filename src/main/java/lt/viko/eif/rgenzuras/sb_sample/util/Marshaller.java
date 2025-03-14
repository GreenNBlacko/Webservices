package lt.viko.eif.rgenzuras.sb_sample.util;

/**
 * Interface to define marshalling classes
 * @apiNote example being POJO to XML
 * @author ramunas.genzuras@stud.viko.lt
 */
public interface Marshaller {
    <T> String Marshal(T object);

    <T> String Marshal(T object, boolean prettyPrint);

    <T> T Unmarshal(Class<T> objectClass, String xml);
}
