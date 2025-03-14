package lt.viko.eif.rgenzuras.sb_sample.util;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "List")
public class GenericList<T> {
    private List<T> data;

    public GenericList() {}

    public GenericList(List<T> source) {
        data = source;
    }

    public List<T> getData() {
        return data;
    }
}
