package service.xml;

import org.jdom2.Element;

import java.util.ArrayList;

public class UDFValueHelper extends ArrayList<UDFValue> {
    public UDFValueHelper(Element element) {
        for (Element udfValue : element.getChildren()) {
            this.add(new UDFValue(udfValue));
        }
    }
}
