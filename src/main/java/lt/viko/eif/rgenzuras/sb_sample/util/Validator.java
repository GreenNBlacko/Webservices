package lt.viko.eif.rgenzuras.sb_sample.util;

/**
 * Interface to define data validating classes
 * @author ramunas.genzuras@stud.viko.lt
 */
public interface Validator {
    boolean validate(String xmlData, String validationSchemaPath);
}
